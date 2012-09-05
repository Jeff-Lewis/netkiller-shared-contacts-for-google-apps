package com.netkiller.view.navigation;

import java.io.Serializable;

/**
 * @author dhruvsharma
 *
 */
public class BreadcrumbNode implements Serializable {

	private String displayValue;

	private String linkValue;

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getLinkValue() {
		return linkValue;
	}

	public void setLinkValue(String linkValue) {
		this.linkValue = linkValue;
	}

	public BreadcrumbNode(String displayValue, String linkValue) {
		super();
		this.displayValue = displayValue;
		this.linkValue = linkValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((displayValue == null) ? 0 : displayValue.hashCode());
		result = prime * result + ((linkValue == null) ? 0 : linkValue.hashCode());
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
		BreadcrumbNode other = (BreadcrumbNode) obj;
		if (displayValue == null) {
			if (other.displayValue != null)
				return false;
		} else if (!displayValue.equals(other.displayValue))
			return false;
		if (linkValue == null) {
			if (other.linkValue != null)
				return false;
		} else if (!linkValue.equals(other.linkValue))
			return false;
		return true;
	}
	
	

}
