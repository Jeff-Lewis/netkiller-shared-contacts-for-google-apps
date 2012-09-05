package com.netkiller;

/**
 * A class that represents the data that comes from UI grid components. 
 * @author prateek
 *
 */
public class GridRequest {

	@Override
	public String toString() {
		return "GridRequest [filterInfo=" + filterInfo + ", sortInfo=" + sortInfo + ", paginationInfo="
				+ paginationInfo + ", advanceSearchTerm=" + advanceSearchTerm + ", resultType=" + resultType
				+ ", isAdvancedSearchTerm=" + isAdvancedSearchTerm + ", isSearch=" + isSearch + ", isSort=" + isSort
				+ "]";
	}

	private FilterInfo filterInfo;

	private SortInfo sortInfo;

	private PaginationInfo paginationInfo;
	
	private String advanceSearchTerm;
	
	public enum ResultType	{
		Key,Entity;
	} 
	
	private ResultType resultType;
	
	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

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

	/**
	 * @return the searchCriteria
	 */
	public FilterInfo getFilterInfo() {
		return filterInfo;
	}

	/**
	 * @param filterInfo
	 *            the searchCriteria to set
	 */
	public void setFilterInfo(FilterInfo filterInfo) {
		this.filterInfo = filterInfo;
	}

	/**
	 * @return the sortInfo
	 */
	public SortInfo getSortInfo() {
		return sortInfo;
	}

	/**
	 * @param sortCriteria
	 *            the sortCriteria to set
	 */
	public void setSortInfo(SortInfo sortCriteria) {
		this.sortInfo = sortCriteria;
	}

	/**
	 * @return the paginationInfo
	 */
	public PaginationInfo getPaginationInfo() {
		return paginationInfo;
	}

	/**
	 * @param paginationInfo
	 *            the paginationInfo to set
	 */
	public void setPaginationInfo(PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
	}
}
