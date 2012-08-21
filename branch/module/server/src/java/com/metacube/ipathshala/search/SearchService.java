package com.metacube.ipathshala.search;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.metacube.ipathshala.cache.AppCache;
import com.metacube.ipathshala.cache.AppCacheConfig;
import com.metacube.ipathshala.cache.AppCacheManager;
import com.metacube.ipathshala.cache.EntityCacheValue;
import com.metacube.ipathshala.cache.EntityCacheValueComparator;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData.ColumnType;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.search.SearchRequest.ResultType;
import com.metacube.ipathshala.search.property.OrderByProperty;
import com.metacube.ipathshala.search.property.SearchProperty;
import com.metacube.ipathshala.search.property.StringSearchProperty;
import com.metacube.ipathshala.search.property.operator.FilterGroupOperatorType;
import com.metacube.ipathshala.search.property.operator.FilterOperatorType;
import com.metacube.ipathshala.search.query.GaeJdoQueryBuilder;
import com.metacube.ipathshala.search.query.InequalityRestrictionHandler;
import com.metacube.ipathshala.search.query.QueryBuilder;
import com.metacube.ipathshala.service.KeyListService;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.util.CacheUtil;
import com.metacube.ipathshala.util.EntityComparator;
import com.metacube.ipathshala.util.KeyUtil;
import com.metacube.ipathshala.util.PerfLogger;

/**
 * 
 * A generic search service that given a search request would perform a search
 * using <code>SearchRequest</code> and return results <code>SearchResult</code>
 * .This service internally uses a <code>SearchQueryDirector</code> to
 * orchestrate the building of queries using a <code>QueryBuilder</code>. This
 * is a built on a the Builder Design pattern to be able to leverage different
 * implementations of QueryBuilder wherever necessary.
 * 
 * 
 * 
 * @author prateek
 * 
 */
@Repository
public class SearchService {
	private PersistenceManagerFactory managerFactory;

	private static final AppLogger log = AppLogger
			.getLogger(SearchService.class);

	@Autowired
	private AppCacheConfig cacheConfig;

	@Autowired
	private KeyListService keyListService;

	@Autowired
	private AppCacheManager cacheManager;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	public SearchService(PersistenceManagerFactory persistenceManagerFactory) {
		managerFactory = persistenceManagerFactory;
	}

	public SearchResult doSearch(SearchRequest searchRequest)
			throws AppException {
		// TODO PM need to autowire the director and the implementation of the
		// builder.Had issues with inner classes during autowiring.
		final int batchSize = 5000;
		FilterRecordRange currentFilterRecordRange = searchRequest.getRange();
		SearchResultVO resultsVO = null;
		List<Object> resultObjects = new ArrayList<Object>();
		Long totalRecordsSize = 0l;
		FilterRecordRange recordRange = searchRequest.getRange();
		InequalityRestrictionHandler inequalityRestrictionHandler = new InequalityRestrictionHandler(
				searchRequest);
		List<SearchRequest> searchRequestList = null;
		SearchCriteria searchCriteria = searchRequest
				.getRootEntitySearchCriteria();
		// Check whether Inequality Restriction Exists or not
		if (inequalityRestrictionHandler.isInequalityRestrictedSearchCriteria()) {
			if (cacheConfig.isCacheEnabled()
					&& CacheUtil.isValidCacheEntity(cacheConfig, searchCriteria
							.getEntityType().getSimpleName())
					&& cacheConfig.getKeyCache().isCacheIntialized()
					&& isValidSearchRequestForCache(searchRequest,
							searchCriteria.getEntityType().getSimpleName())) {
				List<Object> keyList;
				PerfLogger perfLogger = new PerfLogger(log,
						"Cache query handler");
				perfLogger.startTime();
				keyList = fetchSearchResultsFromCache(searchRequest);

				totalRecordsSize = Long
						.parseLong(String.valueOf(keyList.size()));
				keyList = getRecordsPerPage(keyList, recordRange);
				if (searchRequest.getResultType() != null
						&& searchRequest.getResultType().equals(
								SearchRequest.ResultType.Key)) {
					resultObjects.addAll(keyList);
				} else {
					List<Object> finalResults = (List<Object>) keyListService
							.getObjectsByKeys(keyList,
									searchCriteria.getEntityType());
					Map<Key, Object> keyObjectMap = new HashMap<Key, Object>();
					for (Object object : finalResults) {
						try {
							Key key = KeyFactory.createKey(object.getClass()
									.getSimpleName(), Long.parseLong(BeanUtils
									.getProperty(object, "key.id")));
							keyObjectMap.put(key, object);
						} catch (NumberFormatException e) {
							log.error("Cannot fetch key from the object");
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							log.error("Cannot fetch key from the object");
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							log.error("Cannot fetch key from the object");
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							log.error("Cannot fetch key from the object");
							e.printStackTrace();
						}
					}

					for (Object object : keyList) {
						resultObjects.add(keyObjectMap.get(object));
					}
				}
				perfLogger.stopTime();

			} else {
				inequalityRestrictionHandler
						.extractNonInEqualitySearchParameters();
				searchRequestList = inequalityRestrictionHandler
						.divideSearchRequestPerRestrictedInequality();
				List<Object> totalObjects = new ArrayList<Object>();

				Map<OrderByProperty, ColumnType> orderByPropertyTypeMap = new LinkedHashMap<OrderByProperty, ColumnMetaData.ColumnType>();
				if (searchRequest.getOrderbyProperties() != null) {

					for (OrderByProperty orderByProp : searchRequest
							.getOrderbyProperties()) {
						ColumnType columnType = getColumnType(searchRequest
								.getRootEntitySearchCriteria().getEntityType(),
								orderByProp.getOrderByPropertyName());
						orderByPropertyTypeMap.put(orderByProp, columnType);
					}
				}

				// Gets the intersection of objects if the Filter operator is
				// AND
				if (searchRequest.getGroupOp().equals(
						FilterGroupOperatorType.AND)) {

					// For the AND filterGroupOperator we get the data by any of
					// the one search request criteria.
					// After that we do in memory filtering of the result for
					// any other request criteria that we might have.
					// Since we need intersection of objects for the AND case we
					// can go for this approach
					List<SearchRequest> searchRequestSubList = new ArrayList<SearchRequest>();
					if (searchRequestList.size() > 1) {
						searchRequestSubList = searchRequestList.subList(1,
								searchRequestList.size());
					}

					List<Object> finalList = new ArrayList<Object>();
					try {
						int count = 0;
						do {
							FilterRecordRange filterRecordRange = new FilterRecordRange(
									count, count + batchSize);
							searchRequestList.get(0)
									.setRange(filterRecordRange);
							PerfLogger perfLogger = new PerfLogger(log,
									"Whole query process");
							perfLogger.startTime();
							finalList.addAll(fetchSearchResults(
									searchRequestList.get(0))
									.getResultObjects());
							perfLogger.stopTime();
							count += batchSize;
						} while (finalList.size() % batchSize == 0
								&& finalList.size() != 0);
					} catch (IllegalArgumentException illegalArgumentException) {
						log.debug("Total records size is a multiple of batch size i.e "
								+ batchSize);
					}

					totalObjects.addAll(finalList);

					// resultsVO = fetchSearchResults(searchRequestList.get(0));
					// totalObjects.addAll(resultsVO.getResultObjects());
					PerfLogger perfLogger = new PerfLogger(log,
							"Inmemory filtering process");
					perfLogger.startTime();
					resultObjects = getIntersectionOfObjects(
							searchRequestSubList, totalObjects);
					perfLogger.stopTime();
					/*
					 * resultObjects = getIntersesctionOfObjects(totalObjects,
					 * searchRequestList.size(), searchRequest
					 * .getRootEntitySearchCriteria().getEntityType());
					 */

					/*
					 * Collections.sort(resultObjects, new EntityComparator(
					 * orderByPropertyTypeMap, searchRequest
					 * .getRootEntitySearchCriteria() .getEntityType()));
					 */
					// Gets the union of objects if the Filter operator is AND
				} else if (searchRequest.getGroupOp().equals(
						FilterGroupOperatorType.OR)) {

					// For the 'OR' filterGroupOperator we merge all the data
					// obtained by all search request criteria.
					// Since we need union of objects for the OR case, we go for
					// this approach
					for (SearchRequest srchRequest : searchRequestList) {
						resultsVO = fetchSearchResults(srchRequest);
						totalObjects.addAll(resultsVO.getResultObjects());
					}
					resultObjects = getUnionOfObjects(totalObjects,
							searchRequestList.size(), searchRequest
									.getRootEntitySearchCriteria()
									.getEntityType());
					Collections.sort(resultObjects, new EntityComparator(
							orderByPropertyTypeMap, searchRequest
									.getRootEntitySearchCriteria()
									.getEntityType()));
				}
				try {
					totalRecordsSize = Long.parseLong(String
							.valueOf(resultObjects.size()));
				} catch (NumberFormatException ex) {
					log.info("result size parse", ex);
				}
				resultObjects = getRecordsPerPage(resultObjects, recordRange);
				if (searchRequest.getResultType().equals(
						SearchRequest.ResultType.Key)) {
					List<Object> keyList = new ArrayList<Object>();
					for (Object object : resultObjects) {
						keyList.add(KeyUtil.geyKeyFromObject(object));
					}
					resultObjects = keyList;
				}

			}
		} else {

			List<Object> finalList = new ArrayList<Object>();
			if (currentFilterRecordRange == null) {
				
				try {
					int count = 0;
					List currentObjectList = null;
					do {
						FilterRecordRange filterRecordRange = new FilterRecordRange(
								count, count + batchSize);

						searchRequest.setRange(filterRecordRange);
						currentObjectList =fetchSearchResults(searchRequest)
						.getResultObjects();
						finalList.addAll(currentObjectList);
						count += batchSize;
					} while (finalList.size() % batchSize == 0
							&& !currentObjectList.isEmpty() && finalList.size()!=0);
				} catch (IllegalArgumentException illegalArgumentException) {
					log.debug("Total records size is a multiple of batch size i.e "
							+ batchSize);
				}

				totalRecordsSize = Long.parseLong(String.valueOf(finalList
						.size()));
				resultObjects.addAll(finalList);
			} else {
				resultsVO = fetchSearchResults(searchRequest);
				totalRecordsSize = resultsVO.getTotalSearchResultSize();
				resultObjects = resultsVO.getResultObjects();
			}
		}

		SearchResult searchResult = new SearchResult(resultObjects,
				searchRequest.getRootEntitySearchCriteria().getEntityType(),
				currentFilterRecordRange, totalRecordsSize);
		return searchResult;
	}

	// This method do the in memory filtering of the objects on the basis of the
	// various search properties.
	private List<Object> getIntersectionOfObjects(
			List<SearchRequest> searchRequestSubList, List<Object> totalObjects) {
		List<Object> resultlist = new ArrayList<Object>();
		Class<?> entityType = null;

		List<SearchProperty> searchProperties = new ArrayList<SearchProperty>();
		if (searchRequestSubList != null && searchRequestSubList.size() != 0) {
			for (SearchRequest searchRequest : searchRequestSubList) {
				entityType = searchRequest.getRootEntitySearchCriteria()
						.getEntityType();
				if (searchRequest.getSearchCriterias() != null) {
					for (SearchCriteria searchCriteria : searchRequest
							.getSearchCriterias()) {
						if (searchCriteria.getSearchProperties() != null) {
							searchProperties.addAll(searchCriteria
									.getSearchProperties());
						}
					}
				}

				if (searchRequest.getRootEntitySearchCriteria() != null) {
					if (searchRequest.getRootEntitySearchCriteria()
							.getSearchProperties() != null) {
						searchProperties.addAll(searchRequest
								.getRootEntitySearchCriteria()
								.getSearchProperties());
					}
				}
			}

			for (Object obj : totalObjects) {
				boolean isValidObject = true;

				for (SearchProperty sp : searchProperties) {

					Object searchPropertyValue = sp.getPropertyValue();

					// if the search property is for string and is case
					// insensitive search, then convert it to upper case
					if (sp instanceof StringSearchProperty) {
						StringSearchProperty stringSearchProperty = (StringSearchProperty) sp;
						if (stringSearchProperty.isIgnoreCaseWhileMatching()) {
							searchPropertyValue = ((String) searchPropertyValue)
									.toUpperCase();
						}
					}
					Field propertyField = null;
					try {
						propertyField = entityType.getDeclaredField(sp
								.getPropertyName());
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					propertyField.setAccessible(true);
					Object propertyValue = null;
					try {
						propertyValue = (Object) propertyField.get(obj);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (!compareObjects(propertyValue, searchPropertyValue,
							sp.getFilterOperator())) {
						isValidObject = false;
						break;

					}

				}
				if (isValidObject) {
					resultlist.add(obj);
				}

			}

		}
		return resultlist;
	}

	private ColumnType getColumnType(Class<?> entityType, String propertyName)
			throws AppException {
		EntityMetaData entityMetaData = (EntityMetaData) applicationContext
				.getBean(entityType.getSimpleName() + "MetaData");
		ColumnMetaData columnMetaData = entityMetaData
				.getColumnMetaData(propertyName);
		return columnMetaData.getColumnType();
	}

	private PersistenceManager getPersistenceManager() {
		return managerFactory.getPersistenceManager();
	}

	private SearchResultVO executeQuery(
			com.metacube.ipathshala.search.query.Query builtQuery,
			PersistenceManager manager, ResultType resultType) {
		List<Object> keys = null;
		List results = new ArrayList();
		boolean isRangeSet = false;
		Long totalSearchSize = new Long(0);
		Query query = manager.newQuery(builtQuery.getFromFragment(),
				getFilterString(builtQuery));
		if (builtQuery.getRange() != null) {
			query.setResult("key");
			query.setResultClass(Key.class);
			isRangeSet = true;
		}
		if (builtQuery.getOrderByClause() != null) {
			query.setOrdering(builtQuery.getOrderByClause());
		}
		if (builtQuery.getParameterValues() != null) {
			Object[] parameterValues = builtQuery.getParameterValues()
					.toArray();
			keys = (List) query.executeWithArray(parameterValues);
		} else {
			keys = (List) query.execute();
		}

		if (keys != null) {
			totalSearchSize = new Long(keys.size());
		}
		if (isRangeSet && (keys != null && keys.size() > 0)) {
			int endRange = builtQuery.getRange().getEndRange();
			int startRange = builtQuery.getRange().getStartRange();
			if (endRange > totalSearchSize) {
				endRange = totalSearchSize.intValue();
			}
			List rangeSublist = null;
			if (startRange >= totalSearchSize.intValue()) {
				rangeSublist = new ArrayList();
			} else {
				rangeSublist = keys.subList(
						builtQuery.getRange().startRange, endRange);
			}
			/*
			 * List oidList = new ArrayList(); for (Iterator iterator =
			 * rangeSublist.iterator(); iterator.hasNext();) { Key key = (Key)
			 * iterator.next(); Object oid =
			 * manager.newObjectIdInstance(builtQuery.getFromFragment(), key);
			 * oidList.add(oid); } results = (List)
			 * manager.getObjectsById(oidList);
			 */
			List finalResults = null;
			if (rangeSublist != null && !rangeSublist.isEmpty()) {
				if (!resultType.equals(SearchRequest.ResultType.Key)) {
					query = manager.newQuery(builtQuery.getFromFragment());
					query.declareImports("import com.google.appengine.api.datastore.Key");
					query.setFilter(":keyList.contains(key)");
					finalResults = (List) manager
							.detachCopyAll((Collection) query
									.execute(rangeSublist));
					Map<Key, Object> keyObjectMap = new HashMap<Key, Object>();
					for (Object object : finalResults) {
						try {
							Key key = KeyFactory.createKey(object.getClass()
									.getSimpleName(), Long.parseLong(BeanUtils
									.getProperty(object, "key.id")));
							keyObjectMap.put(key, object);
						} catch (NumberFormatException e) {
							log.error("Cannot fetch key from the object");
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							log.error("Cannot fetch key from the object");
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							log.error("Cannot fetch key from the object");
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							log.error("Cannot fetch key from the object");
							e.printStackTrace();
						}
					}

					for (Object object : rangeSublist) {
						results.add(keyObjectMap.get(object));
					}
				} else {
					results = rangeSublist;
				}
			} 
		} else {
			results = keys;
		}
		return new SearchResultVO(results, totalSearchSize);
	}

	private String getFilterString(
			com.metacube.ipathshala.search.query.Query query) {
		String whereClause = query.getWhereClause();
		if (StringUtils.isEmpty(whereClause)) {
			return null;
		} else {
			return whereClause;
		}
	}

	/**
	 * The SearchQueryDirector is a the director of a builder design pattern. It
	 * knows to interact with a the QueryBuilder interface to build up a Query.
	 * Note that specifics of query building should always be in the
	 * implementing builder. At no point should the director have any
	 * implementation specific logic within it and should only contain generic
	 * logic of orchestrating the building up of a query in steps from a
	 * SearchRequest.
	 */
	private class SearchQueryDirector {
		private QueryBuilder builder;

		public SearchQueryDirector(QueryBuilder builder) {
			this.builder = builder;
		}

		public com.metacube.ipathshala.search.query.Query buildQuery(
				SearchRequest searchRequest) throws AppException {
			SearchCriteria searchCriteria = searchRequest
					.getRootEntitySearchCriteria();

			
			// build the from piece
			builder.addFrom(searchCriteria.getEntityType());

			// build the filters
			List<SearchProperty> searchProperties = searchCriteria
					.getSearchProperties();
			if (searchProperties != null) {
				for (Iterator iterator = searchProperties.iterator(); iterator
						.hasNext();) {
					SearchProperty searchProperty = (SearchProperty) iterator
							.next();
					//Filter added in only case when property value is not blank.
					if("".equals(searchProperty.getPropertyValue())&& FilterOperatorType.STARTS_WITH.equals(searchProperty.getFilterOperator()))	{
						
					} else	{
						builder.addFilter(searchProperty, searchRequest.getGroupOp());
					}
				}
			}

			// build the order by piece
			List<OrderByProperty> orderByProperties = searchRequest
					.getOrderbyProperties();
			if (orderByProperties != null) {
				for (Iterator iterator = orderByProperties.iterator(); iterator
						.hasNext();) {
					OrderByProperty orderByProperty = (OrderByProperty) iterator
							.next();
					builder.addOrderBy(orderByProperty);
				}
			}
			if (searchRequest.getRange() != null) {
				builder.addFetchRange(searchRequest.getRange());
			}
			return builder.buildCompleteQuery();
		}

	}

	private class SearchResultVO {
		private List<Object> resultObjects;
		private Long totalSearchResultSize;

		public SearchResultVO(List<Object> resultObjects,
				Long totalSearchResultSize) {
			super();
			this.resultObjects = resultObjects;
			this.totalSearchResultSize = totalSearchResultSize;
		}

		public List<Object> getResultObjects() {
			return resultObjects;
		}

		public Long getTotalSearchResultSize() {
			return totalSearchResultSize;
		}

	}

	private SearchResultVO fetchSearchResults(SearchRequest srchRequest)
			throws AppException {
		com.metacube.ipathshala.search.query.Query builtQuery = null;
		PersistenceManager manager = null;
		SearchResultVO resultsVO = null;
		SearchQueryDirector director = new SearchQueryDirector(
				new GaeJdoQueryBuilder());
		builtQuery = director.buildQuery(srchRequest);
		manager = getPersistenceManager();
		resultsVO = executeQuery(builtQuery, manager,
				srchRequest.getResultType());
		return resultsVO;
	}

	private List<Object> getUnionOfObjects(List<Object> totalObjects, int size,
			Class<?> entityType) throws AppException {
		Map<Long, List<Object>> recordFrequencyMap = getRecordKeyFrequencyMap(totalObjects);
		List<Object> resultObjects = new ArrayList<Object>();
		for (Long id : recordFrequencyMap.keySet()) {
			resultObjects.addAll(recordFrequencyMap.get(id));
		}

		return resultObjects;
	}

	private List<Object> getIntersesctionOfObjects(List<Object> totalObjects,
			Integer size, Class<?> entityType) throws AppException {
		Map<Long, List<Object>> recordFrequencyMap = getRecordKeyFrequencyMap(totalObjects);
		List<Object> resultObjects = new ArrayList<Object>();
		for (Long id : recordFrequencyMap.keySet()) {
			if (recordFrequencyMap.get(id).size() == size) {
				resultObjects.add(recordFrequencyMap.get(id).get(0));
			}
		}
		return resultObjects;
	}

	public List<Object> getRecordsPerPage(List<Object> objects,
			FilterRecordRange recordRange) {
		List<Object> records = new ArrayList<Object>();
		if (recordRange != null) {
			int endRange = recordRange.getEndRange();
			if (endRange > objects.size())
				endRange = objects.size();
			for (int i = recordRange.getStartRange(); i < endRange; i++) {
				records.add(objects.get(i));
			}
		} else
			records = objects;

		return records;
	}

	private Map<Long, List<Object>> getRecordKeyFrequencyMap(
			List<Object> totalObjects) throws AppException {
		Map<Long, List<Object>> recordFrequencyMap = new LinkedHashMap<Long, List<Object>>();
		Long id;
		try {
			for (Object object : totalObjects) {
				String key = BeanUtils.getProperty(object, "key.id");
				id = Long.parseLong(key);
				if (!recordFrequencyMap.containsKey(id)) {
					List<Object> objectList = new ArrayList<Object>();
					objectList.add(object);
					recordFrequencyMap.put(id, objectList);
				} else {
					recordFrequencyMap.get(id).add(object);
					recordFrequencyMap.put(id, recordFrequencyMap.get(id));
				}
			}
		} catch (IllegalAccessException e) {
			String message = "Unable to fetch Key from the searrched records";
			log.error(message, e);
			throw new AppException("message", e);
		} catch (InvocationTargetException e) {
			String message = "Unable to fetch Key from the searrched records";
			log.error(message, e);
			throw new AppException("message", e);
		} catch (NoSuchMethodException e) {
			String message = "Unable to fetch Key from the searrched records";
			log.error(message, e);
			throw new AppException("message", e);
		}
		return recordFrequencyMap;
	}

	private List<Object> fetchSearchResultsFromCache(SearchRequest searchRequest)
			throws AppException {

		SearchCriteria searchCriteria = searchRequest
				.getRootEntitySearchCriteria();
		String entityName = searchCriteria.getEntityType().getSimpleName();
		List<SearchProperty> searchProperties = searchCriteria
				.getSearchProperties();
		AppCache entityCache = cacheManager.getEntityCache(entityName);
		List<Object> keyList = new ArrayList<Object>();
		AppCache keyCache = cacheManager.getKeyCache();
		EntityCacheValue keyCacheValue = keyCache.getCachedValue(entityName);
		Collection<EntityCacheValue> cacheValueList = entityCache.getByKeys(
				(List<Key>) keyCacheValue.getProperty("entityKeyList"))
				.values();

		Map<OrderByProperty, ColumnType> orderByPropertyTypeMap = new LinkedHashMap<OrderByProperty, ColumnMetaData.ColumnType>();
		if (searchRequest.getOrderbyProperties() != null) {

			for (OrderByProperty orderByProp : searchRequest
					.getOrderbyProperties()) {
				ColumnType columnType = getColumnType(searchRequest
						.getRootEntitySearchCriteria().getEntityType(),
						orderByProp.getOrderByPropertyName());
				orderByPropertyTypeMap.put(orderByProp, columnType);
			}
		}

		List<EntityCacheValue> finalResultList = new ArrayList<EntityCacheValue>();

		for (EntityCacheValue entityCacheValue : cacheValueList) {
			if (isValidSearchResult(entityCacheValue, searchProperties)) {
				finalResultList.add(entityCacheValue);
			}
		}
		List<EntityCacheValueComparator> comparatorList = new ArrayList<EntityCacheValueComparator>();
		for (OrderByProperty orderByProperty : orderByPropertyTypeMap.keySet()) {
			comparatorList
					.add(new EntityCacheValueComparator(orderByProperty,
							searchRequest.getRootEntitySearchCriteria()
									.getEntityType(), orderByPropertyTypeMap
									.get(orderByProperty)));
		}
		ComparatorChain chain = new ComparatorChain(comparatorList);

		Collections.sort(finalResultList, chain);

		for (EntityCacheValue entityCacheValue : finalResultList) {
			keyList.add(entityCacheValue.getProperty("key"));

		}
		return keyList;
	}

	private boolean isValidSearchResult(EntityCacheValue cacheValue,
			List<SearchProperty> searchProperties) {
		boolean isValidValue = true;
		for (SearchProperty searchProperty : searchProperties) {
			if (!isValidSearchValue(cacheValue, searchProperty)) {
				isValidValue = false;
				break;
			}

		}
		return isValidValue;
	}

	private boolean isValidSearchValue(EntityCacheValue cacheValue,
			SearchProperty searchProperty) {
		Object propertyCacheValue = cacheValue.getProperty(searchProperty
				.getPropertyName());
		FilterOperatorType operatorType = searchProperty.getFilterOperator();
		Object propertyValue = searchProperty.getPropertyValue();

		return compareObjects(propertyCacheValue, propertyValue, operatorType);
	}

	private boolean compareObjects(Object property1, Object property2,
			FilterOperatorType operatorType) {
		boolean isValidSearchValue = false;
		switch (operatorType) {

		case EQUAL:
			if (property1 == null && property2 == null)
				return true;
			isValidSearchValue = property1.equals(property2);
			break;
		case NOT_EQUAL:
			if (property1 == null && property2 != null)
				return true;
			isValidSearchValue = !property1.equals(property2);
			break;
		case GREATER_THAN:
			if (property1 != null) {
				if (property1 instanceof Date) {
					isValidSearchValue = ((Date) property1)
							.after((Date) property2);
				} else if (property1 instanceof Double) {
					isValidSearchValue = (Double) property1 > (Double) property2;
				}
			}
			break;
		case LESS_THAN:
			if (property1 != null) {
				if (property1 instanceof Date) {
					isValidSearchValue = ((Date) property1)
							.before((Date) property2);
				} else if (property1 instanceof Double) {
					isValidSearchValue = (Double) property1 < (Double) property2;
				}
			}
			break;
		case STARTS_WITH:
			if (property1 != null) {
				if (property1 instanceof String) {
					isValidSearchValue = ((String) property1)
							.startsWith(((String) property2));
				}
			}
			break;
		case ENDS_WITH:
			if (property1 != null) {
				if (property1 instanceof String) {
					isValidSearchValue = ((String) property1)
							.endsWith(((String) property2));
				}
			}
			break;
		case GREATER_THAN_EQUAL:
			if (property1 != null) {
				if (property1 instanceof Date) {
					isValidSearchValue = ((Date) property1)
							.after((Date) property2);
				} else if (property1 instanceof Double) {
					isValidSearchValue = (Double) property1 > (Double) property2;
				}
				isValidSearchValue = isValidSearchValue
						|| property1.equals(property2);
			}
			break;
		case LESS_THAN_EQUAL:
			if (property1 != null) {
				if (property1 instanceof Date) {
					isValidSearchValue = ((Date) property1)
							.before((Date) property2);
				} else if (property1 instanceof Double) {
					isValidSearchValue = (Double) property1 < (Double) property2;
				}
				isValidSearchValue = (isValidSearchValue || property1
						.equals(property2));
			}
			break;

		}
		return isValidSearchValue;
	}

	private boolean isValidSearchRequestForCache(SearchRequest searchRequest,
			String entityName) {
		boolean isValid = true;
		SearchCriteria searchCriteria = searchRequest
				.getRootEntitySearchCriteria();
		AppCache keyCache = cacheManager.getKeyCache();
		AppCache entityCache = cacheManager.getEntityCache(entityName);
		if (entityCache == null)
			isValid = false;
		else {
			for (SearchProperty searchProperty : searchCriteria
					.getSearchProperties()) {
				EntityCacheValue keyCacheValue = keyCache
						.getCachedValue(entityName);
				if (!entityCache.getPropertyNameList().contains(
						searchProperty.getPropertyName())
						|| keyCacheValue == null) {
					isValid = false;
				}
			}
			if (searchRequest.getOrderbyProperties() != null)
				for (OrderByProperty orderByProperty : searchRequest
						.getOrderbyProperties()) {
					if (!entityCache.getPropertyNameList().contains(
							orderByProperty.getOrderByPropertyName())) {
						isValid = false;
					}
				}
		}
		return isValid;
	}

}