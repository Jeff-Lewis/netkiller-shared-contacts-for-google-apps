package com.netkiller.calendar;

import java.util.Date;

/**
 * 
 * @author administrator
 * 
 */
public class CalendarEvent {

	private String title;

	private String content;

	private Date startDate;

	private Date endDate;

	private String editLink;

	private String selfLink;

	private String id;

	private CalendarWebContent calendarWebContent;

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
	 * @return the calendarWebContent
	 */
	public CalendarWebContent getCalendarWebContent() {
		return calendarWebContent;
	}

	/**
	 * @param calendarWebContent
	 *            the calendarWebContent to set
	 */
	public void setCalendarWebContent(CalendarWebContent calendarWebContent) {
		this.calendarWebContent = calendarWebContent;
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
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalendarEvent [title=" + title + ", content=" + content
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", editLink=" + editLink + ", selfLink=" + selfLink + ", id="
				+ id + ", calendarWebContent=" + calendarWebContent
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

	

}
