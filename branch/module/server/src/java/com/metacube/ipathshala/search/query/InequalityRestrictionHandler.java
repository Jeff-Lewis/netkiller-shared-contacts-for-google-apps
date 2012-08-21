package com.metacube.ipathshala.search.query;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.search.SearchCriteria;
import com.metacube.ipathshala.search.SearchRequest;
import com.metacube.ipathshala.search.property.SearchProperty;
import com.metacube.ipathshala.search.property.operator.FilterOperatorType;

public class InequalityRestrictionHandler {
	private SearchRequest searchRequest;
	private List<SearchProperty> searchPropertyWithInequalityFiltersInDifferentFields = null;
	private EnumSet<FilterOperatorType> INEQUALITY_OPERATOR_SET = EnumSet.<FilterOperatorType> of(
			FilterOperatorType.GREATER_THAN, FilterOperatorType.LESS_THAN, FilterOperatorType.NOT_EQUAL,
			FilterOperatorType.STARTS_WITH, FilterOperatorType.ENDS_WITH, FilterOperatorType.GREATER_THAN_EQUAL,
			FilterOperatorType.LESS_THAN_EQUAL);
	public static final String STR_IMPLICIT_PARAM_PREFIX = ":";
	private SearchProperty inEqualityFilterProperty = null;

	public InequalityRestrictionHandler(SearchRequest searchRequest) {
		this.searchRequest = searchRequest;
	}

	public boolean isInequalityRestrictedSearchCriteria() throws AppException {
		boolean isInequalityRestriction = false;
		if (searchRequest != null) {
			SearchCriteria searchCriteria = searchRequest.getRootEntitySearchCriteria();
			if (searchCriteria != null) {
				if (searchCriteria.getSearchProperties() != null) {
					for (SearchProperty searchProperty : searchCriteria.getSearchProperties()) {
						if (isInequalityRestrictedSearchProperty(searchProperty)) {
							if (searchPropertyWithInequalityFiltersInDifferentFields == null) {
								searchPropertyWithInequalityFiltersInDifferentFields = new ArrayList<SearchProperty>();
								isInequalityRestriction = true;
							}
							searchPropertyWithInequalityFiltersInDifferentFields.add(searchProperty);
						}
					}
				}
			}
		}
		return isInequalityRestriction;
	}

	public void extractNonInEqualitySearchParameters() {
		if (searchRequest != null) {
			SearchCriteria searchCriteria = searchRequest.getRootEntitySearchCriteria();
			if (searchCriteria != null) {
				if (searchCriteria.getSearchProperties() != null
						&& searchPropertyWithInequalityFiltersInDifferentFields != null) {
					searchCriteria.getSearchProperties()
							.removeAll(searchPropertyWithInequalityFiltersInDifferentFields);
				}
			}
		}
	}

	public List<SearchRequest> divideSearchRequestPerRestrictedInequality() throws AppException {
		List<SearchRequest> searchRequestList = new ArrayList<SearchRequest>();
		searchRequestList.add(new SearchRequest(searchRequest.getRootEntitySearchCriteria(), searchRequest
				.getSearchCriterias(), searchRequest.getOrderbyProperties(), searchRequest.getGroupOp(), null, searchRequest.getResultType()));
		for (SearchProperty searchProperty : searchPropertyWithInequalityFiltersInDifferentFields) {
			List<SearchProperty> searchPropertyList = new ArrayList<SearchProperty>();
			searchPropertyList.add(searchProperty);
			SearchCriteria searchCriteria = new SearchCriteria(searchPropertyList, searchRequest
					.getRootEntitySearchCriteria().getEntityType());
			searchRequestList.add(new SearchRequest(searchCriteria, searchRequest.getSearchCriterias(), null, searchRequest.getGroupOp(), null, searchRequest.getResultType()));
		}
		return searchRequestList;
	}

	private boolean isInequalityRestrictedSearchProperty(SearchProperty searchProperty) throws AppException {
		boolean isInequalityRestriction = false;
		if (INEQUALITY_OPERATOR_SET.contains(searchProperty.getFilterOperator())) {
			if (inEqualityFilterProperty == null) {
				inEqualityFilterProperty = searchProperty;
			//	isInequalityRestriction = true;
			} else {
				if (!searchProperty.getPropertyName().equals(inEqualityFilterProperty.getPropertyName())) {
					isInequalityRestriction = true;
				}
			}
		}
		return isInequalityRestriction;
	}

}
