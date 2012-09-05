package com.netkiller.calendar;

/**
 * 
 * @author administrator
 * 
 */
public class Calendar {

	private String title;

	private String id;

	private String editLink;

	private String selfLink;

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
	 * @return the editLink
	 */
	public String getEditLink() {
		return editLink;
	}

	/**
	 * @param editLink
	 *            the editLink to set
	 */
	public void setEditLink(String editLink) {
		this.editLink = editLink;
	}

	/**
	 * @return the selfLink
	 */
	public String getSelfLink() {
		return selfLink;
	}

	/**
	 * @param selfLink
	 *            the selfLink to set
	 */
	public void setSelfLink(String selfLink) {
		this.selfLink = selfLink;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Calendar [title=" + title + ", id=" + id + ", editLink="
				+ editLink + ", selfLink=" + selfLink + "]";
	}

}
