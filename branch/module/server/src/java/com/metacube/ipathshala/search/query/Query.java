package com.metacube.ipathshala.search.query;

import java.util.List;

import com.metacube.ipathshala.search.FilterRecordRange;

/**
 * 
 * @author prateek
 *
 */
public class Query {
	private Class fromFragment;
	private String whereClause;
	private String orderByClause;
	private List<Object> parameterValues;
	private FilterRecordRange range;
	public Query(Class fromFragment, String whereClause, String orderByClause, List<Object> parameterValues,FilterRecordRange range) {
		super();
		this.fromFragment = fromFragment;
		this.whereClause = whereClause;
		this.orderByClause = orderByClause;
		this.parameterValues = parameterValues;
		this.range= range;
	}
	public Class getFromFragment() {
		return fromFragment;
	}
	public String getWhereClause() {
		return whereClause;
	}
	public String getOrderByClause() {
		return orderByClause;
	}
	public List<Object> getParameterValues() {
		return parameterValues;
	}
	public FilterRecordRange getRange() {
		return range;
	}
	
}
