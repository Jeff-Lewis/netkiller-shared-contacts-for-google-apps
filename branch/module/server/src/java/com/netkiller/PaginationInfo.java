package com.netkiller;

/**
 * A class whose object hold Pagination related data coming from a Grid like component
 * @author prateek
 *
 */
public class PaginationInfo {

	private int pageNumber;

	private int recordsPerPage;

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber
	 *            the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the recordsPerPage
	 */
	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	/**
	 * @param recordsPerPage
	 *            the recordsPerPage to set
	 */
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	
}
