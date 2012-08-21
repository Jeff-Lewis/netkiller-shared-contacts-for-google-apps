package com.metacube.ipathshala.search.property;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.search.property.operator.FilterOperatorType;
import com.metacube.ipathshala.search.property.operator.OrderByOperatorType;

public class KeySearchProperty implements SearchProperty {

	private String propertyName;
	private Key propertyValue;
	private FilterOperatorType filterOperator;
	
	
	public KeySearchProperty(String propertyName, Key propertyValue, FilterOperatorType filterOperator) {
		super();
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.filterOperator = filterOperator;
	}

	@Override
	public OrderByOperatorType getDefaultSortOrder() {
		return OrderByOperatorType.ASC;
	}

	@Override
	public FilterOperatorType getFilterOperator() {
		return filterOperator;
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public Key getPropertyValue() {
		return propertyValue;
	}
}