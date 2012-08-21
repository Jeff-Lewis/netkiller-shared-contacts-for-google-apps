package com.metacube.ipathshala.sites;

/**
 * 
 * @author administrator
 * 
 */
public class WebAttachment {

	String title;

	String summary;

	String parentLink;

	String url;

	String id;

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
	public String getParentLink() {
		return parentLink;
	}

	/**
	 * 
	 * @param parentLink
	 */
	public void setParentLink(String parentLink) {
		this.parentLink = parentLink;
	}

	/**
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WebAttachment [title=" + title + ", summary=" + summary
				+ ", parentLink=" + parentLink + ", url=" + url + ", id=" + id
				+ "]";
	}

}
