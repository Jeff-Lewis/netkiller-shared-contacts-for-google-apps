package com.netkiller.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Key;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;
import com.netkiller.entity.metadata.ColumnMetaData.ColumnType;
import com.netkiller.entity.metadata.impl.GlobalFilterType;
import com.netkiller.entity.metadata.impl.GlobalFilterValueType;
import com.netkiller.globalfilter.AcademicYear;
import com.netkiller.globalfilter.Active;
import com.netkiller.globalfilter.GlobalFilterMap;
import com.netkiller.search.property.BooleanSearchProperty;
import com.netkiller.search.property.DateSearchProperty;
import com.netkiller.search.property.KeySearchProperty;
import com.netkiller.search.property.SearchProperty;
import com.netkiller.search.property.operator.FilterOperatorType;
import com.netkiller.util.AppLogger;

@Repository
public class KeyListService {
	private static final AppLogger log = AppLogger.getLogger(KeyListService.class);
	private PersistenceManagerFactory persistenceManagerFactory;
	

	@Autowired
	public KeyListService(PersistenceManagerFactory persistenceManagerFactory) {
		this.persistenceManagerFactory = persistenceManagerFactory;
	}

	/**
	 * @return the persistenceManagerFactory
	 */
	public PersistenceManagerFactory getPersistenceManagerFactory() {
		return persistenceManagerFactory;
	}

	public PersistenceManager getPersistenceManager() {
		return this.persistenceManagerFactory.getPersistenceManager();
	}

	public <E> Collection<E> getByKeys(Collection<Key> keyList, Class<E> type) {
		PersistenceManager persistenceManager = getPersistenceManager();
		Collection<E> results = new ArrayList<E>();
		/*List<Object> oidList = new ArrayList<Object>();
		if (!keyList.isEmpty() && keyList != null) {
			for (Key key : keyList) {
				Object oid = persistenceManager.newObjectIdInstance(type, key);
				oidList.add(oid);
			}
			results.addAll((Collection<E>) persistenceManager.getObjectsById(oidList));
		}*/
		if(keyList==null || keyList.isEmpty())
			return results;
		Query query = persistenceManager.newQuery(type);
		query.declareImports("import com.google.appengine.api.datastore.Key");
		query.setFilter(":keyList.contains(key)");
		results.addAll(persistenceManager.detachCopyAll((Collection<E>) query.execute(keyList)));
		return results;

	}
	
	
	public <E> Collection<E> getByKeys(Collection<Key> keyList, Class<E> type, String field) {
		PersistenceManager persistenceManager = getPersistenceManager();
		Collection<E> results = new ArrayList<E>();
		/*List<Object> oidList = new ArrayList<Object>();
		if (!keyList.isEmpty() && keyList != null) {
			for (Key key : keyList) {
				Object oid = persistenceManager.newObjectIdInstance(type, key);
				oidList.add(oid);
			}
			results.addAll((Collection<E>) persistenceManager.getObjectsById(oidList));
		}*/
		if(keyList==null || keyList.isEmpty())
			return results;
		Query query = persistenceManager.newQuery(type);
		query.declareImports("import com.google.appengine.api.datastore.Key");
		query.setFilter(":keyList.contains("+field+")");
		results.addAll(persistenceManager.detachCopyAll((Collection<E>) query.execute(keyList)));
		return results;

	}
	
	public  Collection<Object> getObjectsByKeys(Collection<Object> keyList, Class<?> type) {
		PersistenceManager persistenceManager = getPersistenceManager();
		Collection<Object> results = new ArrayList<Object>();
		/*List<Object> oidList = new ArrayList<Object>();
		if (!keyList.isEmpty() && keyList != null) {
			for (Key key : keyList) {
				Object oid = persistenceManager.newObjectIdInstance(type, key);
				oidList.add(oid);
			}
			results.addAll((Collection<E>) persistenceManager.getObjectsById(oidList));
		}*/
		if(keyList==null || keyList.isEmpty())
			return results;
		Query query = persistenceManager.newQuery(type);
		query.declareImports("import com.google.appengine.api.datastore.Key");
		query.setFilter(":keyList.contains(key)");
		results.addAll(persistenceManager.detachCopyAll((Collection<Object>) query.execute(keyList)));
		return results;

	}
	

	
	  /**
	 * Assembles Global filter Properties as per the Global filter values
	 * available in the dataContext.
	 * 
	 * @param dataContext
	 * @param metaData
	 * @return
	 * @throws AppException
	 */
	private List<SearchProperty> assembleGlobalFilterSearchProperties(DataContext dataContext, EntityMetaData metaData)
			throws AppException {
		GlobalFilterMap globalFilterMap = dataContext.getGlobalFilterMap();
		List<SearchProperty> searchProperties = new ArrayList<SearchProperty>();
		for (Iterator iterator = globalFilterMap.getGlobalFilterHashMap().keySet().iterator(); iterator.hasNext();) {
			GlobalFilterType globalFilterType = (GlobalFilterType) iterator.next();
			SearchProperty searchProperty = null;
			for (String propertyName : metaData.getFilterEntitiesColumnMetaDataMap().keySet()) {
				searchProperty = assembleGlobalFilterSearchProperty(propertyName, metaData, globalFilterType,
						dataContext);
				if (searchProperty != null) {
					searchProperties.add(searchProperty);
				}
			}
		}
		return searchProperties;
	}
	
	
	/**
	 * Assembles a Global filter Property as per the Global filter values
	 * available in the dataContext.
	 * 
	 * @param propertyName
	 * @param metaData
	 * @param globalFilterType
	 * @param dataContext
	 * @return
	 * @throws AppException
	 */
	private SearchProperty assembleGlobalFilterSearchProperty(String propertyName, EntityMetaData metaData,
			GlobalFilterType globalFilterType, DataContext dataContext) throws AppException {
		SearchProperty searchProperty = null;
		Object propertyValue = null;
		ColumnType columnType = metaData.getColumnMetaData(propertyName).getColumnType();
		FilterMetaData filterMetaData = metaData.getFilterEntitiesColumnMetaDataMap().get(propertyName);
		AcademicYear academicYear = dataContext.getCurrentSelectedAcademicYear();
		Active isActive  = (Active)dataContext.getGlobalFilterMap().getGlobalFilterHashMap().get(GlobalFilterType.ACTIVE);
		switch (globalFilterType) {
		case GLOBAL_ACADEMIC_YEAR:
			if(filterMetaData.getType().equals(globalFilterType))	{
				if (filterMetaData.getValueType().equals(GlobalFilterValueType.EXACT_MATCH)) {
					propertyValue = academicYear.getEntityKey();
					searchProperty = new KeySearchProperty(propertyName, (Key) propertyValue, FilterOperatorType.EQUAL);
				} else if (filterMetaData.getValueType().equals(GlobalFilterValueType.RANGE_FROM)) {
					propertyValue = academicYear.getFromDate();
					FilterOperatorType operatorType = FilterOperatorType.LESS_THAN_EQUAL;
					searchProperty = new DateSearchProperty(propertyName, (Date) propertyValue, operatorType);
				} else if (filterMetaData.getValueType().equals(GlobalFilterValueType.RANGE_TO)) {
					propertyValue = academicYear.getToDate();
					FilterOperatorType operatorType = FilterOperatorType.GREATER_THAN_EQUAL;
					searchProperty = new DateSearchProperty(propertyName, (Date) propertyValue, operatorType);
				}
			}
			break;
		case ACTIVE:
			if(filterMetaData.getType().equals(globalFilterType))	{
				if (filterMetaData.getValueType().equals(GlobalFilterValueType.EXACT_MATCH)) {
					propertyValue = true;
					searchProperty = new BooleanSearchProperty(propertyName, (Boolean) propertyValue, FilterOperatorType.EQUAL);
				}
			}
			break;
		case DELETE:
			if(filterMetaData.getType().equals(globalFilterType))	{
				if (filterMetaData.getValueType().equals(GlobalFilterValueType.EXACT_MATCH)) {
					propertyValue = false;
					searchProperty = new BooleanSearchProperty(propertyName, (Boolean) propertyValue, FilterOperatorType.EQUAL);
				}
			}
			break;
		}
		return searchProperty;
	}
}
