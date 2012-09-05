package com.netkiller.search.property;

import com.netkiller.search.property.operator.FilterOperatorType;
import com.netkiller.search.property.operator.OrderByOperatorType;


public class NumberSearchProperty implements SearchProperty {

	private String propertyName;
	private Double propertyValue;
	private FilterOperatorType filterOperator;
	
	public NumberSearchProperty(String propertyName, Double propertyValue, FilterOperatorType filterOperator) {
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
	public Double getPropertyValue() {
		return propertyValue;
	}

	@Override
	public OrderByOperatorType getDefaultSortOrder() {
		return OrderByOperatorType.ASC;
	}

}
