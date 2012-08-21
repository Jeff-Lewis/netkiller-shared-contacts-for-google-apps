package com.metacube.ipathshala.search.query;

import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.search.FilterRecordRange;
import com.metacube.ipathshala.search.property.operator.FilterGroupOperatorType;
import com.metacube.ipathshala.search.property.OrderByProperty;
import com.metacube.ipathshala.search.property.SearchProperty;

/**
 * A QueryBuilder contract for building a Query using the general fragments of a
 * query framework i.e a From, Filter(Where), Order BY and Range limit. Can be
 * enhanced to add Projections(Select) if needed.
 * 
 * @author prateek
 * 
 */
public interface QueryBuilder {
	void addFrom(Class entityType) throws AppException;

	void addFilter(SearchProperty searchProperty, FilterGroupOperatorType groupOperator) throws AppException;

	void addOrderBy(OrderByProperty property) throws AppException;

	void addFetchRange(FilterRecordRange range) throws AppException;

	Query buildCompleteQuery() throws AppException;
}
