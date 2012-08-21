/**
 * 
 */
package com.metacube.ipathshala.reportcard;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vishesh
 * 
 */
public class ComponentInfo {
	private String name;
	private MarksInfo marksInfo;

	public ComponentInfo() {
		subComponentMap = new HashMap<String, SubCompInfo>();
	}
	
	/**
	 * Map of sub component name and subComponent Info
	 */
	private Map<String, SubCompInfo> subComponentMap;

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

	public Map<String, SubCompInfo> getSubComponentMap() {
		return subComponentMap;
	}

	public void setSubComponentMap(Map<String, SubCompInfo> subComponentMap) {
		this.subComponentMap = subComponentMap;
	}

	@Override
	public String toString() {
		return "ComponentInfo [name=" + name + ", marksInfo=" + marksInfo
				+ ", subComponentMap=" + subComponentMap + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
