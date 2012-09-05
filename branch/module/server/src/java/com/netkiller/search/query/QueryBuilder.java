package com.netkiller.search.query;

import com.netkiller.core.AppException;
import com.netkiller.search.FilterRecordRange;
import com.netkiller.search.property.OrderByProperty;
import com.netkiller.search.property.SearchProperty;
import com.netkiller.search.property.operator.FilterGroupOperatorType;

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
