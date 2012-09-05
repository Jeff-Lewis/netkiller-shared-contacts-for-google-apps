package com.netkiller.search;

import java.util.List;

import org.apache.commons.lang.Validate;

import com.netkiller.search.SearchRequest.ResultType;
import com.netkiller.search.property.OrderByProperty;
import com.netkiller.search.property.operator.FilterGroupOperatorType;

/**
 * This class represents all inputs required for a particular search request.
 * 
 * @author prateek
 * 
 */
public class SearchRequest {

	private SearchCriteria rootEntitySearchCriteria;
	
	//child or relationship entities criteria
	private List<SearchCriteria> searchCriterias;

	// order by
	private List<OrderByProperty> orderbyProperties;

	// group operation
	private FilterGroupOperatorType groupOp;
	
	public enum ResultType	{
		Key,Entity;
	} 
	
	private ResultType resultType;
	

	//filter range
	private FilterRecordRange range;

	
	public SearchRequest(SearchCriteria rootEntitySearchCriteria, List<SearchCriteria> searchCriterias,
			List<OrderByProperty> propertyNames, FilterGroupOperatorType groupOp,FilterRecordRange range, ResultType resultType) {
		super();		
		Validate.notNull(rootEntitySearchCriteria,"Root search criteria cannot be null");
		Validate.notNull(groupOp,"Group operator cannot be null");
		this.rootEntitySearchCriteria = rootEntitySearchCriteria;
		this.searchCriterias = searchCriterias;
		this.orderbyProperties = propertyNames;
		this.groupOp = groupOp;
		this.range = range;
		this.resultType = resultType;
	}

	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	public SearchCriteria getRootEntitySearchCriteria() {
		return rootEntitySearchCriteria;
	}

	public List<SearchCriteria> getSearchCriterias() {
		return searchCriterias;
	}

	public List<OrderByProperty> getOrderbyProperties() {
		return orderbyProperties;
	}
	
	public void setOrderbyProperties(List<OrderByProperty> orderbyProperties) {
		this.orderbyProperties = orderbyProperties;
	}

	public FilterGroupOperatorType getGroupOp() {
		return groupOp;
	}

	public FilterRecordRange getRange() {
		return range;
	}

	public void setRange(FilterRecordRange range) {
		this.range = range;
	}
	
	
}
