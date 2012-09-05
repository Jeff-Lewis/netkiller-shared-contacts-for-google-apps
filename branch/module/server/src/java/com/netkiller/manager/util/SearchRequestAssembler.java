package com.netkiller.manager.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.netkiller.FilterInfo;
import com.netkiller.GridRequest;
import com.netkiller.PaginationInfo;
import com.netkiller.SortInfo;
import com.netkiller.FilterInfo.Rule;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.metadata.ColumnMetaData;
import com.netkiller.entity.metadata.ColumnRelationShipMetaData;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.ColumnMetaData.ColumnType;
import com.netkiller.search.FilterRecordRange;
import com.netkiller.search.SearchCriteria;
import com.netkiller.search.SearchRequest;
import com.netkiller.search.property.BooleanSearchProperty;
import com.netkiller.search.property.DateSearchProperty;
import com.netkiller.search.property.KeySearchProperty;
import com.netkiller.search.property.NumberSearchProperty;
import com.netkiller.search.property.OrderByProperty;
import com.netkiller.search.property.SearchProperty;
import com.netkiller.search.property.StringSearchProperty;
import com.netkiller.search.property.operator.FilterGroupOperatorType;
import com.netkiller.search.property.operator.FilterOperatorType;
import com.netkiller.search.property.operator.InputFilterGroupOperatorType;
import com.netkiller.search.property.operator.InputFilterOperatorType;
import com.netkiller.search.property.operator.InputOrderByOperatorType;
import com.netkiller.search.property.operator.OrderByOperatorType;
import com.netkiller.util.LocalizationUtil;

/**
 * A class used for taking various type of input search requests and assemble a
 * <code>SearchRequest</code> that could given to the generic SearchService for
 * all kinds of search needs.
 * 
 * @author prateek
 * 
 */
public class SearchRequestAssembler {
	private static final Map<String, FilterOperatorType> filterOperator = new HashMap<String, FilterOperatorType>();
	static {
		filterOperator.put(InputFilterOperatorType.EQUAL, FilterOperatorType.EQUAL);
		filterOperator.put(InputFilterOperatorType.LESS_THAN, FilterOperatorType.LESS_THAN);
		filterOperator.put(InputFilterOperatorType.GREATER_THAN, FilterOperatorType.GREATER_THAN);
		filterOperator.put(InputFilterOperatorType.STARTS_WITH, FilterOperatorType.STARTS_WITH);
		filterOperator.put(InputFilterOperatorType.GREATER_THAN_EQUAL, FilterOperatorType.GREATER_THAN_EQUAL);
		filterOperator.put(InputFilterOperatorType.LESS_THAN_EQUAL, FilterOperatorType.LESS_THAN_EQUAL);
		filterOperator.put(InputFilterOperatorType.NOT_EQUAL, FilterOperatorType.NOT_EQUAL);
		// filterOperator.put(InputFilterOperatorType.ENDS_WITH,
		// FilterOperatorType.ENDS_WITH);
	}
	private static final Map<String, FilterGroupOperatorType> groupOperator = new HashMap<String, FilterGroupOperatorType>();
	static {
		groupOperator.put(InputFilterGroupOperatorType.AND, FilterGroupOperatorType.AND);
		groupOperator.put(InputFilterGroupOperatorType.OR, FilterGroupOperatorType.OR);

	}
	private static final Map<String, OrderByOperatorType> sortOperator = new HashMap<String, OrderByOperatorType>();
	static {
		sortOperator.put(InputOrderByOperatorType.ASC, OrderByOperatorType.ASC);
		sortOperator.put(InputOrderByOperatorType.DESC, OrderByOperatorType.DESC);
	}
	
	private static final Map<GridRequest.ResultType, SearchRequest.ResultType> resultTypeMap = new HashMap<GridRequest.ResultType, SearchRequest.ResultType>();
	static {
		resultTypeMap.put(GridRequest.ResultType.Entity, SearchRequest.ResultType.Entity);
		resultTypeMap.put(GridRequest.ResultType.Key, SearchRequest.ResultType.Key);
	}
	private static String defaultDateTimePattern = "dd/MM/yyyy HH:mm:ss";

	public static SearchRequest assemble(Class<Object> entityClazz, EntityMetaData metaData, FilterInfo filterInfo,
			DataContext dataContext) throws AppException {

		if (entityClazz == null) {
			throw new AppException("Entity type cannot be null");
		}
		if (filterInfo == null) {
			throw new AppException("Input Filter Request cannot be null");
		}
		if (metaData == null) {
			throw new AppException("Metadata cannot be null");
		}
		List<SearchProperty> searchProperties = assembleSearchProperties(filterInfo, metaData, true);
		SearchCriteria rootCriteria = new SearchCriteria(searchProperties, entityClazz);
		FilterGroupOperatorType groupOperator;
		if (filterInfo.getGroupOp() != null) {
			groupOperator = SearchRequestAssembler.groupOperator.get(filterInfo.getGroupOp());
		} else {
			groupOperator = FilterGroupOperatorType.AND;// dummy group operator
		}

		SearchRequest searchRequest = new SearchRequest(rootCriteria, null, null, groupOperator, null, SearchRequest.ResultType.Entity);
		return searchRequest;
	}

	public static SearchRequest assemble(Class<Object> entityClazz, EntityMetaData metaData, GridRequest inputRequest,
			DataContext dataContext) throws AppException {
		if (entityClazz == null) {
			throw new AppException("Entity type cannot be null");
		}
		if (inputRequest == null) {
			throw new AppException("Input Search Request cannot be null");
		}
		if (metaData == null) {
			throw new AppException("Metadata cannot be null");
		}
		SearchCriteria rootCriteria = null;
		List<OrderByProperty> orderByProperties = null;

		FilterRecordRange recordRange = null;
		List<SearchProperty> searchProperties = null;
		if (inputRequest.isSearch()) {
			// assemble filter criterias
			searchProperties = assembleSearchProperties(inputRequest.getFilterInfo(), metaData, true);
			rootCriteria = new SearchCriteria(searchProperties, entityClazz);
		} else if (inputRequest.isAdvancedSearchTerm()) {
			searchProperties = new ArrayList<SearchProperty>();
			if (inputRequest.getFilterInfo() != null)
				searchProperties.addAll(assembleSearchProperties(inputRequest.getFilterInfo(), metaData, true));
			String advanceSearchTerm = inputRequest.getAdvanceSearchTerm();
			if (!StringUtils.isEmpty(advanceSearchTerm)) {
				ColumnMetaData columnMetadata = metaData.getDefaultSearchColumn();
				if (columnMetadata == null) {
					throw new AppException("Default search column not available for given entity for searching:"
							+ advanceSearchTerm);
				}
				SearchProperty searchProperty = assembleSearchProperty(columnMetadata.getColumnName(),
						advanceSearchTerm, InputFilterOperatorType.STARTS_WITH, columnMetadata.getColumnType(), true,
						metaData);
				searchProperties.add(searchProperty);
				rootCriteria = new SearchCriteria(searchProperties, entityClazz);
			} else {
				rootCriteria = new SearchCriteria(searchProperties, entityClazz);
			}
		} else {
			rootCriteria = new SearchCriteria(searchProperties, entityClazz);
		}
		// assemble order by
		SortInfo sortInfo = inputRequest.getSortInfo();
		if (sortInfo != null) {
			orderByProperties = assembleOrderByProperties(sortInfo);
		}
		SearchRequest.ResultType searchResultType = SearchRequest.ResultType.Entity;
		GridRequest.ResultType resultType = inputRequest.getResultType();
		if(resultType !=null)	{
			 searchResultType = resultTypeMap.get(resultType);
		}
		// assemble record range
		PaginationInfo pageInfo = inputRequest.getPaginationInfo();
		if (pageInfo != null) {
			recordRange = assembleFilterRecordRange(pageInfo);
		}
		FilterGroupOperatorType groupOperator;
		if (inputRequest.getFilterInfo() != null) {
			groupOperator = SearchRequestAssembler.groupOperator.get(inputRequest.getFilterInfo().getGroupOp());
		} else {
			groupOperator = FilterGroupOperatorType.AND;// dummy group operator
		}

		SearchRequest searchRequest = new SearchRequest(rootCriteria, null, orderByProperties, groupOperator,
				recordRange, searchResultType);
		return searchRequest;

	}

	private static FilterRecordRange assembleFilterRecordRange(PaginationInfo pageInfo) {
		FilterRecordRange recordRange = null;
		if (pageInfo != null) {
			int startRecord = (pageInfo.getPageNumber() - 1) * pageInfo.getRecordsPerPage();
			int endRecord = startRecord + pageInfo.getRecordsPerPage();
			recordRange = new FilterRecordRange(startRecord, endRecord);
		}
		return recordRange;
	}

	private static List<OrderByProperty> assembleOrderByProperties(SortInfo sortInfo) {
		List<OrderByProperty> orderByPropertiesList = null;
		if (sortInfo != null) {
			orderByPropertiesList = new ArrayList<OrderByProperty>();
			String sortField = sortInfo.getSortField();
			String sortOrder = sortInfo.getSortOrder();
			OrderByProperty orderByProperty = new OrderByProperty(sortField, sortOperator.get(sortOrder));
			orderByPropertiesList.add(orderByProperty);
		}
		return orderByPropertiesList;
	}

	private static List<SearchProperty> assembleSearchProperties(FilterInfo filterInfo, EntityMetaData metaData,
			boolean ignoreCaseWhileMatching) throws AppException {
		List<Rule> rules = filterInfo.getRules();
		List<SearchProperty> searchPropertyList = new ArrayList<SearchProperty>();
		for (Iterator iterator = rules.iterator(); iterator.hasNext();) {
			Rule rule = (Rule) iterator.next();
			String propertyName = rule.getField();
			ColumnMetaData columnMetaData = metaData.getColumnMetaData(propertyName);
			ColumnType columnType = columnMetaData.getColumnType();
			String propertyValue = rule.getData();
			String filterOperator = rule.getOp();
			SearchProperty searchProperty = assembleSearchProperty(propertyName, propertyValue, filterOperator,
					columnType, ignoreCaseWhileMatching, metaData);
			searchPropertyList.add(searchProperty);
		}
		return searchPropertyList;
	}

	private static SearchProperty assembleSearchProperty(String propertyName, String propertyValue,
			String filterOperator, ColumnType columnType, boolean ignoreCaseWhileMatching, EntityMetaData metaData)
			throws AppException {
		SearchProperty searchProperty = null;
		FilterOperatorType operatorType = null;
		if (filterOperator == null) {
			throw new AppException("Filter operator cannot be null for propertyName:" + propertyName);
		} else {
			operatorType = SearchRequestAssembler.filterOperator.get(filterOperator);
		}

		if (propertyValue == null) {
			throw new AppException("Filter value cannot be null for propertyName:" + propertyName);
		}
		if (columnType == ColumnType.STRING) {
			ColumnMetaData columnMetaData = metaData.getDefaultSearchColumn();
			if(columnMetaData!=null && columnMetaData.getColumnName()!=null && columnMetaData.getColumnName().equalsIgnoreCase(propertyName)){
			searchProperty = new StringSearchProperty(propertyName, propertyValue, operatorType,
					ignoreCaseWhileMatching);}
			else{
				searchProperty = new StringSearchProperty(propertyName, propertyValue, operatorType,
						false);
			}
		} else if (columnType == ColumnType.DATE) {
			Date date = LocalizationUtil.parseDate(propertyValue, null);
			searchProperty = new DateSearchProperty(propertyName, date, operatorType);
		} else if (columnType == ColumnType.DATETIME) {
			Date date = LocalizationUtil.parseDateTime(propertyValue, null);
			searchProperty = new DateSearchProperty(propertyName, date, operatorType);
		} else if (columnType == ColumnType.DOUBLE) {
			searchProperty = new NumberSearchProperty(propertyName, new Double(propertyValue), operatorType);
		} else if (columnType == ColumnType.BOOL) {
			Boolean activeVal;
			if (propertyValue.equalsIgnoreCase("y"))
				activeVal = true;
			else
				activeVal = false;
			searchProperty = new BooleanSearchProperty(propertyName, activeVal, operatorType);

		} else if (columnType == ColumnType.Key) {

			ColumnMetaData columnMetaData = metaData.getColumnMetaData(propertyName);
			ColumnRelationShipMetaData relationShip = columnMetaData.getRelationShip();
			Key key = null;

			if (relationShip != null) {
				// for relationship keys.

				key = KeyFactory.createKey(relationShip.getRelatedEntityMetaData().getEntityName(),
						Long.parseLong(propertyValue));
			} else {
				// for primary keys.
				key = KeyFactory.createKey(metaData.getEntityName(), Long.parseLong(propertyValue));
			}

			searchProperty = new KeySearchProperty(propertyName, key, operatorType);

		}

		return searchProperty;
	}

}
