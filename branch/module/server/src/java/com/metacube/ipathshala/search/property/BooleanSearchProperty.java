package com.metacube.ipathshala.search.property;

import com.metacube.ipathshala.search.property.operator.FilterOperatorType;
import com.metacube.ipathshala.search.property.operator.OrderByOperatorType;


public class BooleanSearchProperty implements SearchProperty {

	private String propertyName;
	private Boolean propertyValue;
	private FilterOperatorType filterOperator;
	
	public BooleanSearchProperty(String propertyName, Boolean propertyValue, FilterOperatorType filterOperator) {
		super();
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
		this.filterOperator = filterOperator;
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
	public Boolean getPropertyValue() {
		return propertyValue;
	}
	public OrderByOperatorType getDefaultSortOrder() {
		return OrderByOperatorType.ASC;
	}

	
	

}
