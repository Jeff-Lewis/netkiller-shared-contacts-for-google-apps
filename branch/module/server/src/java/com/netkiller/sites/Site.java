package com.netkiller.sites;

/**
 * 
 * @author administrator
 * 
 */
public class Site {

	/**
	 * 
	 * @param title
	 * @param summary
	 * @param theme
	 */
	public Site(String title, String summary, String theme) {
		this.title = title;
		this.summary = summary;
		this.theme = theme;
	}

	/**
	 * 
	 */
	public Site() {
	}

	private String title;

	private String summary;

	private String theme;

	private String siteHref;

	private String siteName;

	private String id;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

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
	 * 
	 * @return
	 */
	public String getSiteHref() {
		return siteHref;
	}

	/**
	 * 
	 * @param siteHref
	 */
	public void setSiteHref(String siteHref) {
		this.siteHref = siteHref;
	}

	/**
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * 
	 * @param summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * 
	 * @return
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * 
	 * @param theme
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Site [title=" + title + ", summary=" + summary + ", theme="
				+ theme + ", siteHref=" + siteHref + ", siteName=" + siteName
				+ ", id=" + id + "]";
	}

}
