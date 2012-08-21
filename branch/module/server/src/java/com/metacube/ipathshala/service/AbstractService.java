package com.metacube.ipathshala.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.core.UniqueValidationException;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData.ColumnType;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.search.SearchCriteria;
import com.metacube.ipathshala.search.SearchRequest;
import com.metacube.ipathshala.search.SearchResult;
import com.metacube.ipathshala.search.property.BooleanSearchProperty;
import com.metacube.ipathshala.search.property.DateSearchProperty;
import com.metacube.ipathshala.search.property.KeySearchProperty;
import com.metacube.ipathshala.search.property.NumberSearchProperty;
import com.metacube.ipathshala.search.property.SearchProperty;
import com.metacube.ipathshala.search.property.StringSearchProperty;
import com.metacube.ipathshala.search.property.operator.FilterGroupOperatorType;
import com.metacube.ipathshala.search.property.operator.FilterOperatorType;
import com.metacube.ipathshala.util.AppLogger;

public class AbstractService {

	private static final AppLogger log = AppLogger.getLogger(AbstractService.class);

	protected void validate(Object object, EntityMetaData entityMetaData,
			GlobalFilterSearchService searchService, DataContext dataContext) throws AppException {
		boolean isValid = false;
		Map<String, Boolean> uniqueValidationMap = new HashMap<String, Boolean>();
		try {

			for (String propertyName : entityMetaData.getUniqueColumnMetaDataMap().keySet()) {
				Field propertyField = entityMetaData.getEntityClass().getDeclaredField(propertyName);
				propertyField.setAccessible(true);
				Object propertyValue = (Object) propertyField.get(object);
				propertyField.setAccessible(false);
				SearchProperty property = assembleSearchProperty(propertyName, propertyValue, FilterOperatorType.EQUAL,
						entityMetaData.getUniqueColumnMetaDataMap().get(propertyName).getColumnType(), true);
				List<SearchProperty> filterList = new ArrayList<SearchProperty>();
				filterList.add(property);
				SearchCriteria searchCriteria = new SearchCriteria(filterList, entityMetaData.getEntityClass());
				SearchRequest request = new SearchRequest(searchCriteria, null, null, FilterGroupOperatorType.AND, null,SearchRequest.ResultType.Entity);
				SearchResult result = searchService.doSearch(dataContext, entityMetaData, request);

				if (result.getResultsSize() <= 1) {
					if (BeanUtils.getProperty(object, "key") != null) {
						if (result.getResultsSize() == 1) {
							if (BeanUtils.getProperty(object, "key.id").equals(
									BeanUtils.getProperty(result.getResultObjects().get(0), "key.id"))) {
								isValid = true;
							}
						}
						else	{
							isValid = true;
						}
					} else {
						if(result.getResultsSize() < 1)
						isValid = true;
					}
				}
				uniqueValidationMap.put(propertyName, isValid);
				
			}
			for (String uniquePropertyName : uniqueValidationMap.keySet())
				if (!uniqueValidationMap.get(uniquePropertyName)) {
					/*
					 * If propertyValue is not unique then throw UniqueValidation
					 * Exception
					 */
					UniqueValidationException uniqueValidationException = new UniqueValidationException(
							"Unable to validate "+entityMetaData.getEntityName()+" Object", uniquePropertyName,
							uniqueValidationMap.get(uniquePropertyName));
					throw uniqueValidationException;
			}
		} catch (IllegalAccessException e) {
			String message = "Unable to fetch  property value for unique validation";
			log.error(message, e);
			throw new AppException("message", e);
		} catch (NoSuchFieldException e) {
			String message = "Unable to fetch  property value for unique validation";
			log.error(message, e);
			throw new AppException("message", e);
		} catch (InvocationTargetException e) {
			String message = "Unable to fetch  property value for unique validation";
			log.error(message, e);
		} catch (NoSuchMethodException e) {
			String message = "Unable to fetch  property value for unique validation";
			log.error(message, e);
		}

	}

	private SearchProperty assembleSearchProperty(String propertyName, Object propertyValue,
			FilterOperatorType filterOperator, ColumnType columnType, boolean ignoreCaseWhileMatching)
			throws AppException {
		SearchProperty searchProperty = null;
		if (filterOperator == null) {
			throw new AppException("Filter operator cannot be null for propertyName:" + propertyName);
		}
		if (propertyValue == null) {
			throw new AppException("Filter value cannot be null for propertyName:" + propertyName);
		}
		if (columnType == ColumnType.STRING) {
			searchProperty = new StringSearchProperty(propertyName, (String) propertyValue, filterOperator,
					ignoreCaseWhileMatching);
		} else if (columnType == ColumnType.DATE) {
			searchProperty = new DateSearchProperty(propertyName, (Date) propertyValue, filterOperator);
		} else if (columnType == ColumnType.DOUBLE) {
			searchProperty = new NumberSearchProperty(propertyName, (Double) propertyValue, filterOperator);
		} else if (columnType == ColumnType.BOOL) {
			searchProperty = new BooleanSearchProperty(propertyName, (Boolean) propertyValue, filterOperator);

		} else if (columnType == ColumnType.Key) {
			searchProperty = new KeySearchProperty(propertyName, (Key) propertyValue, filterOperator);
		}

		return searchProperty;
	}
	
	
}
