package com.netkiller.sites;

import java.io.File;
import java.util.Date;

/**
 * 
 * @author administrator
 * 
 */
public class Attachment {

	private String title;

	private String summary;

	private String parentLink;

	private String url;

	private File file;

	private String id;
	
	private Date lastUpdatedDate ;

	/**
	 * @return the lastUpdatedDate
	 */
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	/**
	 * @param lastUpdatedDate the lastUpdatedDate to set
	 */
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
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

	/**
	 * 
	 * @return
	 */
	public File getFile() {
		return file;
	}

	/**
	 * 
	 * @param file
	 */
	public void setFile(File file) {
		this.file = file;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Attachment [title=" + title + ", summary=" + summary
				+ ", parentLink=" + parentLink + ", url=" + url + ", file="
				+ file + ", id=" + id + ", lastUpdatedDate=" + lastUpdatedDate
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
}
