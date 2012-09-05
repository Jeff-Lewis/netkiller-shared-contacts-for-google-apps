package com.netkiller.search.property;

import com.netkiller.search.property.operator.FilterOperatorType;
import com.netkiller.search.property.operator.OrderByOperatorType;


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
