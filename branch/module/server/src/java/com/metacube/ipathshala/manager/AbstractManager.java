package com.metacube.ipathshala.manager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.metacube.ipathshala.GridRequest;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.core.FetchType;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.FilterMetaData;
import com.metacube.ipathshala.entity.metadata.impl.GlobalFilterType;
import com.metacube.ipathshala.entity.metadata.impl.GlobalFilterValueType;
import com.metacube.ipathshala.globalfilter.AcademicYear;
import com.metacube.ipathshala.globalfilter.Active;
import com.metacube.ipathshala.globalfilter.GlobalFilter;
import com.metacube.ipathshala.globalfilter.GlobalFilterMap;
import com.metacube.ipathshala.search.SearchResult;
import com.metacube.ipathshala.service.KeyListService;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.util.GlobalFilterUtil;
import com.metacube.ipathshala.util.IpathshalaListHandler;
import com.metacube.ipathshala.util.KeyUtil;

/**
 * 
 * @author sparakh
 * 
 */

public abstract class AbstractManager {

	private static final AppLogger log = AppLogger.getLogger(AbstractManager.class);

	/**
	 * 
	 * Sets the Global Filter Properties with the currently selected Global
	 * Filter in dataContext
	 * 
	 * @param object
	 * @param dataContext
	 * @param entityMetaData
	 * @throws AppException
	 */
	protected void setGlobalFilterProperties(Object object, DataContext dataContext, EntityMetaData entityMetaData)
			throws AppException {
		GlobalFilterMap globalFilterMap = dataContext.getGlobalFilterMap();
		if (globalFilterMap != null) {
			for (Iterator iterator = globalFilterMap.getGlobalFilterHashMap().keySet().iterator(); iterator.hasNext();) {
				GlobalFilterType globalFilterType = (GlobalFilterType) iterator.next();
				switch(globalFilterType)	{
				case ACTIVE:
				case DELETE:
					
					break;
				case GLOBAL_ACADEMIC_YEAR:
					for (String propertyName : entityMetaData.getFilterEntitiesColumnMetaDataMap().keySet()) {

						FilterMetaData filterMetaData = entityMetaData.getFilterEntitiesColumnMetaDataMap().get(
								propertyName);
						if(filterMetaData.getType().equals(globalFilterType))	{
							Object propertyValue = getPropertyValue(globalFilterType, dataContext,
									filterMetaData.getValueType());
							try {
								BeanUtils.setProperty(object, propertyName, propertyValue);
							} catch (IllegalAccessException e) {
								String message = "Unable to fetch " + propertyName + " from currently set Global Filter";
								log.error(message, e);
								throw new AppException("message", e);
							} catch (InvocationTargetException e) {
								String message = "Unable to fetch " + propertyName + " from currently set Global Filter";
								log.error(message, e);
								throw new AppException("message", e);
							}
						}

					}
				
				}
				
			}
		}
	}

	private Object getPropertyValue(GlobalFilterType globalFilterType, DataContext dataContext,
			GlobalFilterValueType valueType) throws AppException {
		Object propertyValue = null;
		AcademicYear academicYear = dataContext.getCurrentSelectedAcademicYear();
		switch (globalFilterType) {
		case GLOBAL_ACADEMIC_YEAR:
			if (valueType.equals(GlobalFilterValueType.EXACT_MATCH)) {
				propertyValue = academicYear.getEntityKey();
			} else if (valueType.equals(GlobalFilterValueType.RANGE_FROM)) {
				propertyValue = academicYear.getFromDate();
			} else if (valueType.equals(GlobalFilterValueType.RANGE_TO)) {
				propertyValue = GlobalFilterUtil.getStaticFutureDateForGlobalFilter();
			}
		
		break;
		case ACTIVE:
				if (valueType.equals(GlobalFilterValueType.EXACT_MATCH)) {
					propertyValue = (Boolean)true;
				}
		}
		return propertyValue;
	}
	
	

	public void updateGlobalFilterProperties(Object object, EntityManager entityManager) throws AppException{
		try {
			Object entityValueFromDataStore = entityManager.getById(KeyFactory.createKey(entityManager
					.getEntityMetaData().getEntityClass().getSimpleName(),
					Long.parseLong(BeanUtils.getProperty(object, "key.id"))));
			for (String propertyName : entityManager.getEntityMetaData().getFilterEntitiesColumnMetaDataMap().keySet()) {
				String propertyValue = BeanUtils.getProperty(object, propertyName);
				if (propertyValue == null || StringUtils.isBlank(propertyValue)) {
					Field propertyField = entityManager.getEntityMetaData().getEntityClass().getDeclaredField(propertyName);
					propertyField.setAccessible(true);
					Object propertyValueFromDataStore = (Object)propertyField.get(entityValueFromDataStore);
					BeanUtils.setProperty(object, propertyName, propertyValueFromDataStore);
					propertyField.setAccessible(false);
				}
			}
		} catch (IllegalAccessException e) {
			String message = "Unable to fetch property from currently set Global Filter";
			log.error(message, e);
			throw new AppException("message", e);
		} catch (InvocationTargetException e) {
			String message = "Unable to fetch property from currently set Global Filter";
			log.error(message, e);
			throw new AppException("message", e);
		} catch (NoSuchMethodException e) {
			String message = "Unable to fetch property from currently set Global Filter";
			log.error(message, e);
			throw new AppException("message", e);
		} catch (NoSuchFieldException e) {
			String message = "Unable to fetch property from currently set Global Filter";
			log.error(message, e);
			throw new AppException("message", e);
		}

	}

	public void updateActiveFilter(DataContext dataContext, FetchType fetchType) {
			if(dataContext!=null && dataContext.getGlobalFilterMap()!=null)	{
				Map<GlobalFilterType,GlobalFilter> globalFilterHashMap =  dataContext.getGlobalFilterMap().getGlobalFilterHashMap();
				if(fetchType !=null)	{
					switch(fetchType)	{
					case ACTIVE:
						globalFilterHashMap.put(GlobalFilterType.ACTIVE, new Active(true));
					break;
					case ALL:
						if(globalFilterHashMap.containsKey(GlobalFilterType.ACTIVE))	{
							globalFilterHashMap.remove(GlobalFilterType.ACTIVE);
						}
						break;
					}
				}
			
			}
	}
	
	public void unsetActiveFilter(DataContext dataContext) {
		if(dataContext!=null && dataContext.getGlobalFilterMap()!=null)	{
			Map<GlobalFilterType,GlobalFilter> globalFilterHashMap =  dataContext.getGlobalFilterMap().getGlobalFilterHashMap();
			if(globalFilterHashMap.containsKey(GlobalFilterType.ACTIVE))	{
				globalFilterHashMap.remove(GlobalFilterType.ACTIVE);
			}
		
		}
}

	public void setCurrentPageRecords(IpathshalaListHandler ipathshalaListHandler, GridRequest gridRequest, SearchResult searchResult, KeyListService keyListService, Class<?> entityType) throws AppException {
		searchResult.setResultObjects(ipathshalaListHandler.getCurrentPageRecords(gridRequest.getPaginationInfo()));
		if (gridRequest.getResultType().equals(GridRequest.ResultType.Key)) {
			List<Object> resultObjects = new ArrayList<Object>();
			List<Object> finalResults = (List<Object>) keyListService.getObjectsByKeys(searchResult.getResultObjects(),
					entityType);

			Map<Key, Object> keyObjectMap = new HashMap<Key, Object>();
			for (Object object : finalResults) {
				Key key = KeyUtil.geyKeyFromObject(object);
				keyObjectMap.put(key, object);
			}

			for (Object object : searchResult.getResultObjects()) {
				resultObjects.add(keyObjectMap.get(object));
			}
			searchResult.setResultObjects(resultObjects);
			gridRequest.setResultType(GridRequest.ResultType.Entity);
		}
		
	}
}
