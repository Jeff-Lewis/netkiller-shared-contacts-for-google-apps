package com.metacube.ipathshala.search;

import java.util.List;

public class SearchResult {
	public void setRange(FilterRecordRange range) {
		this.range = range;
	}

	private List<Object> resultObjects;
	private Class resultObjectype;
	private FilterRecordRange range;
	private Long totalRecordSize;

	public SearchResult(List<Object> resultObjects, Class resultObjectype,
			FilterRecordRange range, Long totalRecordSize) {
		super();
		this.resultObjects = resultObjects;
		this.resultObjectype = resultObjectype;
		this.range = range;
		this.totalRecordSize = totalRecordSize;
	}

	public List<Object> getResultObjects() {
		return resultObjects;
	}

	public void setResultObjects(List<Object> resultObjects) {
		this.resultObjects = resultObjects;
	}

	public Class getResultObjectype() {
		return resultObjectype;
	}

	public FilterRecordRange getRange() {
		return range;
	}

	public Long getTotalRecordSize() {
		return totalRecordSize;
	}

	public Integer getResultsSize() {
		if (resultObjects != null) {
			return resultObjects.size();
		}
		return 0;
	}
}
