package com.metacube.ipathshala.search.query;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.jdo.Query;

import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.search.FilterRecordRange;
import com.metacube.ipathshala.search.property.operator.FilterGroupOperatorType;
import com.metacube.ipathshala.search.property.operator.FilterOperatorType;
import com.metacube.ipathshala.search.property.OrderByProperty;
import com.metacube.ipathshala.search.property.SearchProperty;
import com.metacube.ipathshala.search.property.StringSearchProperty;

/**
 * A JDO implementation of the Query builder. Not that this implementation has
 * an implementation keeping in mind the GAE JDO implementation by Data Nucleus.
 * 
 * @author prateek
 * 
 */
public class GaeJdoQueryBuilder implements QueryBuilder {

	private EnumSet<FilterOperatorType> INEQUALITY_OPERATOR_SET = EnumSet.<FilterOperatorType> of(
			FilterOperatorType.GREATER_THAN, FilterOperatorType.LESS_THAN, FilterOperatorType.NOT_EQUAL,
			FilterOperatorType.STARTS_WITH, FilterOperatorType.ENDS_WITH,FilterOperatorType.GREATER_THAN_EQUAL, FilterOperatorType.LESS_THAN_EQUAL);

	private EnumSet<FilterOperatorType> FUNCTIONAL_OPERATOR_SET = EnumSet.<FilterOperatorType> of(
			FilterOperatorType.STARTS_WITH, FilterOperatorType.ENDS_WITH);
	public static final String STR_IMPLICIT_PARAM_PREFIX = ":";

	private Query query = null;
	private SearchProperty inEqualityFilterProperty = null;
	private Class rootEntity;
	private StringBuilder filterFragment = null;
	private StringBuilder orderByFragment = null;
	private List<Object> parameterBindingValuesList = null;
	private FilterRecordRange range = null;

	public GaeJdoQueryBuilder() {
	}

	@Override
	public void addFrom(Class rootEntity) throws AppException {
		this.rootEntity = rootEntity;
	}

	@Override
	public void addFilter(SearchProperty searchProperty, FilterGroupOperatorType groupOperator) throws AppException {
		FilterOperatorType currentSearchFilter = searchProperty.getFilterOperator();
		checkForInequalityFilters(searchProperty, currentSearchFilter);
		boolean applyGroupOperator = true;
		if (filterFragment == null) {
			filterFragment = new StringBuilder();
			parameterBindingValuesList = new ArrayList<Object>();
			applyGroupOperator = false;
		}
		if (applyGroupOperator && groupOperator != null) {
			filterFragment.append(" ").append(groupOperator.toString()).append(" ");
		}

		// check for functional filter operators and handle them appropriately
		if (FUNCTIONAL_OPERATOR_SET.contains(searchProperty.getFilterOperator())) {
			if (searchProperty instanceof StringSearchProperty) {
				boolean ignoreCase = ((StringSearchProperty) searchProperty).isIgnoreCaseWhileMatching();
				filterFragment.append(searchProperty.getPropertyName());
				filterFragment.append(".");
				filterFragment.append(searchProperty.getFilterOperator().toString());
				filterFragment.append("(");
				if (ignoreCase) {
					filterFragment.append("\"" + ((String) searchProperty.getPropertyValue()).toUpperCase() + "\"");
				} else {
					filterFragment.append("\"" + searchProperty.getPropertyValue() + "\"");
				}

				filterFragment.append(")");
			} else {
				throw new AppException("Functional search operation:" + searchProperty.getFilterOperator()
						+ "can only be applied on string properties");
			}
		} else {
			Object value = searchProperty.getPropertyValue();
			if (searchProperty instanceof StringSearchProperty) {
				boolean ignoreCase = ((StringSearchProperty) searchProperty).isIgnoreCaseWhileMatching();
				if (ignoreCase) {
					value = ((String) value).toUpperCase();
				}
			}			
			
			filterFragment.append(searchProperty.getPropertyName());
			filterFragment.append(searchProperty.getFilterOperator().toString());
			filterFragment.append(STR_IMPLICIT_PARAM_PREFIX);
			filterFragment.append(searchProperty.getPropertyName());
			parameterBindingValuesList.add(value);
		}
	}

	private void checkForInequalityFilters(SearchProperty searchProperty, FilterOperatorType currentSearchFilter)
			throws AppException {
		if (INEQUALITY_OPERATOR_SET.contains(currentSearchFilter)) {
			if (inEqualityFilterProperty == null) {
				inEqualityFilterProperty = searchProperty;
			} else {
				if (!searchProperty.getPropertyName().equals(inEqualityFilterProperty.getPropertyName())) {
					throw new AppException(
							"A single query cannot use inequality filters (< <= >= > !=) on more than one property");
				}

			}
		}
	}

	@Override
	public void addOrderBy(OrderByProperty property) throws AppException {
		if (orderByFragment == null) {
			orderByFragment = new StringBuilder();
			if (inEqualityFilterProperty != null) {
				orderByFragment.append(inEqualityFilterProperty.getPropertyName()).append(" ").append(
						inEqualityFilterProperty.getDefaultSortOrder().toString());
				orderByFragment.append(",");
			}
		} else {
			orderByFragment.append(",");
		}
		orderByFragment.append(property.getOrderByPropertyName()).append(" ").append(property.getOrderBy().toString());
	}

	@Override
	public com.metacube.ipathshala.search.query.Query buildCompleteQuery() throws AppException {
		String filterFragmentString = null;
		String orderByFragmentString = null;
		if (rootEntity == null) {
			throw new AppException("Bad query. From part cannot be empty.");
		}

		if (filterFragment != null) {
			filterFragmentString = filterFragment.toString();
		}
		if (orderByFragment != null) {
			orderByFragmentString = orderByFragment.toString();
		}
		
		com.metacube.ipathshala.search.query.Query query =  new com.metacube.ipathshala.search.query.Query(rootEntity, filterFragmentString, orderByFragmentString,
				parameterBindingValuesList, range);
		
		return query;
	}

	@Override
	public void addFetchRange(FilterRecordRange range) throws AppException {
		this.range = range;

	}

}
