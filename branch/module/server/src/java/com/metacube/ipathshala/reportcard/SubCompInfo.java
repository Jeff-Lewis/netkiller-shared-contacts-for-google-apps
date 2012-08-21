/**
 * 
 */
package com.metacube.ipathshala.reportcard;

/**
 * @author vishesh
 * 
 */
public class SubCompInfo {
	private String name;
	private MarksInfo marksInfo;

	public SubCompInfo() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MarksInfo getMarksInfo() {
		return marksInfo;
	}

	public void setMarksInfo(MarksInfo marksInfo) {
		this.marksInfo = marksInfo;
	}

	@Override
	public String toString() {
		return "SubCompInfo [name=" + name + ", marksInfo=" + marksInfo
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
