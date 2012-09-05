package com.netkiller.search.property;

import com.netkiller.search.property.operator.OrderByOperatorType;

/**
 * 
 * @author prateek
 *
 */
public class OrderByProperty {
	private String orderByPropertyName;
	private OrderByOperatorType orderBy;
	public OrderByProperty(String orderByPropertyName, OrderByOperatorType orderBy) {
		super();
		this.orderByPropertyName = orderByPropertyName;
		this.orderBy = orderBy;
	}
	public String getOrderByPropertyName() {
		return orderByPropertyName;
	}
	public OrderByOperatorType getOrderBy() {
		return orderBy;
	}
	public OrderByOperatorType getDefaultSortOrder() {
		return OrderByOperatorType.ASC;
	}
	
}
