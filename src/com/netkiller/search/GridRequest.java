package com.netkiller.search;




public class GridRequest {
	private FilterInfo filterInfo;

	/*private SortInfo sortInfo;

	private PaginationInfo paginationInfo;*/
	
	private String advanceSearchTerm;
	
	private boolean isAdvancedSearchTerm =false;
	
	public String getAdvanceSearchTerm() {
		return advanceSearchTerm;
	}

	public void setAdvanceSearchTerm(String advanceSearchTerm) {
		this.advanceSearchTerm = advanceSearchTerm;
	}

	public boolean isAdvancedSearchTerm() {
		return isAdvancedSearchTerm;
	}

	public void setAdvancedSearchTerm(boolean isAdvancedSearchTerm) {
		this.isAdvancedSearchTerm = isAdvancedSearchTerm;
	}

	private boolean isSearch =false;
	
	private boolean isSort =false;
	

	public boolean isSearch() {
		return isSearch;
	}

	public void setSearch(boolean isSearch) {
		this.isSearch = isSearch;
	}

	public boolean isSort() {
		return isSort;
	}

	public void setSort(boolean isSort) {
		this.isSort = isSort;
	}

	public FilterInfo getFilterInfo() {
		return filterInfo;
	}

	/**
	 * @return the searchCriteria
	 */
	public void setFilterInfo(FilterInfo filterInfo) {
		this.filterInfo = filterInfo;
	}

/*	*//**
	 * @return the sortInfo
	 *//*
	public SortInfo getSortInfo() {
		return sortInfo;
	}

	*//**
	 * @param sortCriteria
	 *            the sortCriteria to set
	 *//*
	public void setSortInfo(SortInfo sortCriteria) {
		this.sortInfo = sortCriteria;
	}

	*//**
	 * @return the paginationInfo
	 *//*
	public PaginationInfo getPaginationInfo() {
		return paginationInfo;
	}

	*//**
	 * @param paginationInfo
	 *            the paginationInfo to set
	 *//*
	public void setPaginationInfo(PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
	}*/
}
