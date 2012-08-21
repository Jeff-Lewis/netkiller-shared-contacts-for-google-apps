package com.metacube.ipathshala.search.property;

import com.metacube.ipathshala.search.property.operator.FilterOperatorType;
import com.metacube.ipathshala.search.property.operator.OrderByOperatorType;


/**
 * 
 * Represents a filter of a search request.
 * @author prateek
 *
 */
public interface SearchProperty {	
	String getPropertyName();
	Object getPropertyValue();
	FilterOperatorType getFilterOperator();
	public OrderByOperatorType getDefaultSortOrder();
	
}
