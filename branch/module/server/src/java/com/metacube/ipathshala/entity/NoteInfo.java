/**
 * 
 */
package com.metacube.ipathshala.entity;

import java.util.Date;

/**
 * @author Aayush
 *
 */
public class NoteInfo {
	private long totalNewNotes;
	private Date lastModified;
	/**
	 * @return the totalNewNotes
	 */
	public long getTotalNewNotes() {
		return totalNewNotes;
	}
	/**
	 * @param totalNewNotes the totalNewNotes to set
	 */
	public void setTotalNewNotes(long totalNewNotes) {
		this.totalNewNotes = totalNewNotes;
	}
	/**
	 * @return the lastModified
	 */
	public Date getLastModified() {
		return lastModified;
	}
	/**
	 * @param lastModified the lastModified to set
	 */
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	

}
