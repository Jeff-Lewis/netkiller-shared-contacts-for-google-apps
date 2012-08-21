package com.metacube.ipathshala.search;

import java.util.List;

import com.metacube.ipathshala.search.property.SearchProperty;


/**
 * Represent the search filters for a given entity type.
 * Each search property represents the filter operation.
 * @author prateek
 *
 */
public class SearchCriteria {

	private List<SearchProperty> searchProperties;
	private Class entityType;
	public SearchCriteria(List<SearchProperty> searchProperties, Class entityType) {
		super();
		this.searchProperties = searchProperties;
		this.entityType = entityType;
	}
	public List<SearchProperty> getSearchProperties() {
		return searchProperties;
	}
	public Class getEntityType() {
		return entityType;
	}
	
}
