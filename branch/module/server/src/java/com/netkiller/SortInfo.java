package com.netkiller;

/**
 * A class whose object holds sorting related info coming from a UI Component/layer.
 * @author prateek
 *
 */
public class SortInfo {

	

	private String sortField;

	private String sortOrder;

	/**
	 * @return the sortField
	 */
	public String getSortField() {
		return sortField;
	}

	/**
	 * @param sortField
	 *            the sortField to set
	 */
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	/**
	 * @return the sortOrder
	 */
	public String getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder
	 *            the sortOrder to set
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sortField == null) ? 0 : sortField.hashCode());
		result = prime * result + ((sortOrder == null) ? 0 : sortOrder.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SortInfo other = (SortInfo) obj;
		if (sortField == null) {
			if (other.sortField != null)
				return false;
		} else if (!sortField.equals(other.sortField))
			return false;
		if (sortOrder == null) {
			if (other.sortOrder != null)
				return false;
		} else if (!sortOrder.equals(other.sortOrder))
			return false;
		return true;
	}

}
