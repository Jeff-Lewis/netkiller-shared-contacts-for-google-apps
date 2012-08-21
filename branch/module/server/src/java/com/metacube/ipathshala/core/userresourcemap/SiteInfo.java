package com.metacube.ipathshala.core.userresourcemap;

import java.io.Serializable;

public class SiteInfo implements Serializable {

	private String siteName;
	
	private String siteDisplayValue;

	/**
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}

	/**
	 * @param siteName
	 *            the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	/**
	 * @return the siteDisplayValue
	 */
	public String getSiteDisplayValue() {
		return siteDisplayValue;
	}

	/**
	 * @param siteDisplayValue the siteDisplayValue to set
	 */
	public void setSiteDisplayValue(String siteDisplayValue) {
		this.siteDisplayValue = siteDisplayValue;
	}

}
