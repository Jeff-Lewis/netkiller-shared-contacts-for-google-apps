package com.metacube.ipathshala.sites;

import com.metacube.ipathshala.service.googleservice.gdatatype.EntryContainerType;
import com.metacube.ipathshala.service.googleservice.gdatatype.EntryType;

/**
 * 
 * @author administrator
 *
 */
public class SitePage {
	
	private String id ;
	
	private String title ;
	
	private String summary ;
	
	private String kind ;
	
	private Boolean canEdit ;
	
	private String editLink ;
	
	private String selfLink ;
	
	private EntryType entryType ;
	
	private EntryContainerType entryContainerType ;	
	
	/**
	 * @return the entryType
	 */
	public EntryType getEntryType() {
		return entryType;
	}

	/**
	 * @param entryType the entryType to set
	 */
	public void setEntryType(EntryType entryType) {
		this.entryType = entryType;
	}

	/**
	 * @return the entryContainerType
	 */
	public EntryContainerType getEntryContainerType() {
		return entryContainerType;
	}

	/**
	 * @param entryContainerType the entryContainerType to set
	 */
	public void setEntryContainerType(EntryContainerType entryContainerType) {
		this.entryContainerType = entryContainerType;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the kind
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * @param kind the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * @return the canEdit
	 */
	public Boolean getCanEdit() {
		return canEdit;
	}

	/**
	 * @param canEdit the canEdit to set
	 */
	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	/**
	 * @return the editLink
	 */
	public String getEditLink() {
		return editLink;
	}

	/**
	 * @param editLink the editLink to set
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
	 * @param selfLink the selfLink to set
	 */
	public void setSelfLink(String selfLink) {
		this.selfLink = selfLink;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SitePage [id=" + id + ", title=" + title + ", summary="
				+ summary + ", kind=" + kind + ", canEdit=" + canEdit
				+ ", editLink=" + editLink + ", selfLink=" + selfLink
				+ ", entryType=" + entryType + ", entryContainerType="
				+ entryContainerType + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}	
}
