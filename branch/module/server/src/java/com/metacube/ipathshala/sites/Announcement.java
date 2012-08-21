package com.metacube.ipathshala.sites;

import java.util.Date;

/**
 * 
 * @author administrator
 * 
 */
public class Announcement implements Comparable<Announcement>{

	private String title;
	
	private Date lastUpdatedDate;

	private String link;

	private String content;

	private String parentLink;
	
	private String siteName ;
	
	private String author;
	
	

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the siteName
	 */
	public String getSiteName() {
		return siteName;
	}

	/**
	 * @param siteName the siteName to set
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

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
	 * default constructor
	 */
	public Announcement() {

	}

	/**
	 * 
	 * @param title
	 * @param link
	 * @param content
	 * @param parentLink
	 */
	public Announcement(String title, String link, String content, String parentLink,String siteName) {
		super();
		this.title = title;
		this.link = link;
		this.content = content;
		this.parentLink = parentLink;
		this.siteName = siteName ;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the parentLink
	 */
	public String getParentLink() {
		return parentLink;
	}

	/**
	 * @param parentLink
	 *            the parentLink to set
	 */
	public void setParentLink(String parentLink) {
		this.parentLink = parentLink;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Announcement [title=" + title + ", lastUpdatedDate=" + lastUpdatedDate + ", link=" + link
				+ ", content=" + content + ", parentLink=" + parentLink + ", siteName=" + siteName + ", author="
				+ author + ", getClass()=" + getClass() + ", toString()=" + super.toString() + "]";
	}

	@Override
	public boolean equals(Object object) {
		Announcement announcement = (Announcement) object;
		boolean isEqual = false;
		if (announcement.getContent().equals(this.content) && announcement.getTitle().equals(this.title) && announcement.getLink().equals(this.link)) {
			isEqual = true;
		}
		return isEqual;

	}

	@Override
	public int compareTo(Announcement announcement) {
		int result = 0;
		if(this.lastUpdatedDate.after(announcement.getLastUpdatedDate()))	{
			result = -1;
		}
		else if(this.lastUpdatedDate.before(announcement.getLastUpdatedDate()))	{
			result = 1;
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		return (int)this.lastUpdatedDate.getTime();
		
	}
}
