package com.netkiller.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.metadata.ColumnMetaData.ColumnType;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.FilterMetaData;
import com.netkiller.entity.metadata.impl.GlobalFilterType;
import com.netkiller.entity.metadata.impl.GlobalFilterValueType;
import com.netkiller.globalfilter.AcademicYear;
import com.netkiller.globalfilter.Active;
import com.netkiller.globalfilter.GlobalFilterMap;
import com.netkiller.globalfilter.Student;
import com.netkiller.search.FilterRecordRange;
import com.netkiller.search.SearchCriteria;
import com.netkiller.search.SearchRequest;
import com.netkiller.search.SearchResult;
import com.netkiller.search.SearchService;
import com.netkiller.search.property.BooleanSearchProperty;
import com.netkiller.search.property.DateSearchProperty;
import com.netkiller.search.property.KeySearchProperty;
import com.netkiller.search.property.SearchProperty;
import com.netkiller.search.property.StringSearchProperty;
import com.netkiller.search.property.operator.FilterGroupOperatorType;
import com.netkiller.search.property.operator.FilterOperatorType;
import com.netkiller.util.AppLogger;

/**
 * Global Filter Search Service, this will provide service for searching data as
 * per the current values of global Filters maintained in the DataContext.
 * 
 * TODO Currently Global Filter is applied with filterOperator set to 'And'.
 * This will work for Search with the FilterGroupOperator of type &(And). But in
 * case we have some filters with FilterGroupOperator 'OR' then we need a change
 * in our search framework since we do not support a mixture of 'And' and 'OR'
 * operator in our search framework.
 * 
 * @author sparakh
 * 
 */
@Service
public class GlobalFilterSearchService {

	private static final AppLogger log = AppLogger.getLogger(GlobalFilterSearchService.class);

	@Autowired
	private SearchService searchService;

	/**
	 * This method searches data according to the given SearchRequest after
	 * applying current global filters. All the global filters are added to the
	 * provided SearchRequest.
	 * 
	 * @param dataContext
	 * @param metaData
	 * @param searchRequest
	 * @return
	 * @throws AppException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */
	public SearchResult doSearch(DataContext dataContext, EntityMetaData metaData, SearchRequest searchRequest)
			throws AppException {
		List<SearchProperty> searchProperties = null;
		FilterRecordRange recordRange = null;
		if (searchRequest != null && searchRequest.getRootEntitySearchCriteria() != null) {
			searchProperties = searchRequest.getRootEntitySearchCriteria().getSearchProperties();
			recordRange = searchRequest.getRange();
		}
		if (dataContext != null && dataContext.getGlobalFilterMap() != null) {
			if (searchProperties == null)
				searchProperties = assembleGlobalFilterSearchProperties(dataContext, metaData);
			else
				searchProperties.addAll(assembleGlobalFilterSearchProperties(dataContext, metaData));
		}
		SearchCriteria rootCriteria = new SearchCriteria(searchProperties, metaData.getEntityClass());
		FilterGroupOperatorType groupOperator = FilterGroupOperatorType.AND;
		SearchRequest newSearchRequest = new SearchRequest(rootCriteria, null, searchRequest.getOrderbyProperties(),
				groupOperator, recordRange, searchRequest.getResultType());
		return searchService.doSearch(newSearchRequest);
	}

	/**
	 * 
	 * This method gets all the data for an Entity after applying current global
	 * filters.
	 * 
	 * @param dataContext
	 * @param metaData
	 * @return
	 * @throws AppException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */
	public SearchResult doSearch(DataContext dataContext, EntityMetaData metaData) throws AppException {
		List<SearchProperty> searchProperties = null;
		if (dataContext != null && dataContext.getGlobalFilterMap() != null) {
			searchProperties = assembleGlobalFilterSearchProperties(dataContext, metaData);
		}
		SearchCriteria rootCriteria = new SearchCriteria(searchProperties, metaData.getEntityClass());
		FilterGroupOperatorType groupOperator = FilterGroupOperatorType.AND;
		SearchRequest searchRequest = new SearchRequest(rootCriteria, null, null, groupOperator, null,
				SearchRequest.ResultType.Entity);
		return searchService.doSearch(searchRequest);
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
		Student student = dataContext.getCurrentSelectedStudent();
		Active isActive = (Active) dataContext.getGlobalFilterMap().getGlobalFilterHashMap()
				.get(GlobalFilterType.ACTIVE);
		switch (globalFilterType) {
		case GLOBAL_ACADEMIC_YEAR:
			if (filterMetaData.getType().equals(globalFilterType)) {
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
			if (filterMetaData.getType().equals(globalFilterType)) {
				if (filterMetaData.getValueType().equals(GlobalFilterValueType.EXACT_MATCH)) {
					propertyValue = true;
					searchProperty = new BooleanSearchProperty(propertyName, (Boolean) propertyValue,
							FilterOperatorType.EQUAL);
				}
			}
			break;
		case DELETE:
			if (filterMetaData.getType().equals(globalFilterType)) {
				if (filterMetaData.getValueType().equals(GlobalFilterValueType.EXACT_MATCH)) {
					propertyValue = false;
					searchProperty = new BooleanSearchProperty(propertyName, (Boolean) propertyValue,
							FilterOperatorType.EQUAL);
				}
			}
			break;
		case STUDENT:
			if (filterMetaData.getType().equals(globalFilterType)) {
				if (filterMetaData.getValueType().equals(GlobalFilterValueType.EXACT_MATCH)) {
					if (student != null) {
						if(columnType.equals(ColumnType.STRING)){
							searchProperty = new StringSearchProperty(propertyName, (String) student.getUserId(),
									FilterOperatorType.EQUAL,false);
						}
						// TODO : Need to validate this step.
						/*else if(columnType.equals(ColumnType.Key) && metaData.getColumnMetaData(propertyName).getRelationShip()!=null
								&& metaData.getColumnMetaData(propertyName).getRelationShip().getRelatedEntityMetaData().getEntityClass().equals(MyClass.class)){
							propertyValue = student.getClassKey();
							searchProperty = new KeySearchProperty(propertyName, (Key) propertyValue,
									FilterOperatorType.EQUAL);
						}	*/	
						else if(columnType.equals(ColumnType.Key)){
							propertyValue = student.getKey();
							searchProperty = new KeySearchProperty(propertyName, (Key) propertyValue,
									FilterOperatorType.EQUAL);
						}
						
										
					}
				}
			}
			break;
		}

		return searchProperty;
	}

}
