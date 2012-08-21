package com.metacube.ipathshala.search.property;

import com.metacube.ipathshala.search.property.operator.FilterOperatorType;
import com.metacube.ipathshala.search.property.operator.OrderByOperatorType;


public class StringSearchProperty implements SearchProperty {

	private String propertyName;
	private String propertyValue;
	private FilterOperatorType filterOperator;
	
	private boolean ignoreCaseWhileMatching=false;
	
	public StringSearchProperty(String propertyName, String propertyValue, FilterOperatorType filterOperator,boolean ignoreCaseWhileMatching) {
		super();
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.filterOperator = filterOperator;
		this.ignoreCaseWhileMatching=ignoreCaseWhileMatching;
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
	public String getPropertyValue() {
		return propertyValue;
	}
	public OrderByOperatorType getDefaultSortOrder() {
		return OrderByOperatorType.ASC;
	}

	public boolean isIgnoreCaseWhileMatching() {
		return ignoreCaseWhileMatching;
	}
	

}
