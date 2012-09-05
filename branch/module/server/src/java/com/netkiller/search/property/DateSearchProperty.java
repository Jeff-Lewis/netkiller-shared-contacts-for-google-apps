package com.netkiller.search.property;

import java.util.Date;

import com.netkiller.search.property.operator.FilterOperatorType;
import com.netkiller.search.property.operator.OrderByOperatorType;

public class DateSearchProperty implements SearchProperty {

	private String propertyName;
	private Date propertyDate;
	private FilterOperatorType filterOperator;

	public DateSearchProperty(String propertyName, Date propertyDate, FilterOperatorType filterOperator) {
		super();
		this.propertyName = propertyName;
		this.propertyDate = propertyDate;
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
	public Date getPropertyValue() {
		return propertyDate;
	}

}
