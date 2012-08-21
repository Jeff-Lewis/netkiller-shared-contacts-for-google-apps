package com.metacube.ipathshala.calendar;

import java.util.Map;

/**
 * 
 * @author administrator
 * 
 */
public class CalendarWebContent {

	private String title;

	private String type;

	private String icon;

	private String contentUrl;

	private String width;

	private String height;

	private Map<String, String> prefrences;

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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * @return the contentUrl
	 */
	public String getContentUrl() {
		return contentUrl;
	}

	/**
	 * @param contentUrl
	 *            the contentUrl to set
	 */
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	/**
	 * @return the width
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(String height) {
		this.height = height;
	}

	/**
	 * @return the prefrences
	 */
	public Map<String, String> getPrefrences() {
		return prefrences;
	}

	/**
	 * @param prefrences
	 *            the prefrences to set
	 */
	public void setPrefrences(Map<String, String> prefrences) {
		this.prefrences = prefrences;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WebContent [title=" + title + ", type=" + type + ", icon="
				+ icon + ", contentUrl=" + contentUrl + ", width=" + width
				+ ", height=" + height + ", prefrences=" + prefrences + "]";
	}

}
