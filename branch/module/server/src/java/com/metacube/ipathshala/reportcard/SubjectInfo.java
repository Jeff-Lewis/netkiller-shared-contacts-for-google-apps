package com.metacube.ipathshala.reportcard;

import java.util.HashMap;
import java.util.Map;

public class SubjectInfo {

	private String subjectName;

	/**
	 * Map of component name and ComponentInfo
	 */
	private Map<String, ComponentInfo> ComponentMap;

	private MarksInfo marksInfo;
	
	public SubjectInfo() {
		ComponentMap = new HashMap<String, ComponentInfo>();
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Map<String, ComponentInfo> getComponentMap() {
		return ComponentMap;
	}

	public void setComponentMap(Map<String, ComponentInfo> componentMap) {
		ComponentMap = componentMap;
	}

	public MarksInfo getMarksInfo() {
		return marksInfo;
	}

	public void setMarksInfo(MarksInfo marksInfo) {
		this.marksInfo = marksInfo;
	}

	@Override
	public String toString() {
		return "SubjectInfo [subjectName=" + subjectName + ", ComponentMap="
				+ ComponentMap + ", marksInfo=" + marksInfo + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
