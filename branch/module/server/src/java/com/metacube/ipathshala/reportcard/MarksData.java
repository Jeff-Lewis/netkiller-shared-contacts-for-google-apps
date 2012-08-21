package com.metacube.ipathshala.reportcard;

import java.util.HashMap;
import java.util.Map;

public class MarksData {
	// map of subject name and Marks info
	private Map<String, MarksInfo> subjectMarksInfoMap;

	// map of component name and Marks info
	private Map<String, MarksInfo> componentMarksInfoMap;

	// map of sub component name and Marks info
	private Map<String, MarksInfo> subComponentMarksInfoMap;

	// map of child sub component name( Multi occurrence case) and Marks info
	private Map<String, MarksInfo> childSubComponentMarksInfoMap;

	// map of component name and map of sub component name and Marks info
	private Map<String, Map<String, MarksInfo>> componentSubComponentMap;

	public MarksData() {
		subjectMarksInfoMap = new HashMap<String, MarksInfo>();
		componentMarksInfoMap = new HashMap<String, MarksInfo>();
		subComponentMarksInfoMap = new HashMap<String, MarksInfo>();
		childSubComponentMarksInfoMap = new HashMap<String, MarksInfo>();

		componentSubComponentMap = new HashMap<String, Map<String, MarksInfo>>();
	}

	public Map<String, Map<String, MarksInfo>> getComponentSubComponentMap() {
		return componentSubComponentMap;
	}

	public void setComponentSubComponentMap(
			Map<String, Map<String, MarksInfo>> componentSubComponentMap) {
		this.componentSubComponentMap = componentSubComponentMap;
	}

	public Map<String, MarksInfo> getSubjectMarksInfoMap() {
		return subjectMarksInfoMap;
	}

	public void setSubjectMarksInfoMap(
			Map<String, MarksInfo> subjectMarksInfoMap) {
		this.subjectMarksInfoMap = subjectMarksInfoMap;
	}

	public Map<String, MarksInfo> getComponentMarksInfoMap() {
		return componentMarksInfoMap;
	}

	public void setComponentMarksInfoMap(
			Map<String, MarksInfo> componentMarksInfoMap) {
		this.componentMarksInfoMap = componentMarksInfoMap;
	}

	public Map<String, MarksInfo> getSubComponentMarksInfoMap() {
		return subComponentMarksInfoMap;
	}

	public void setSubComponentMarksInfoMap(
			Map<String, MarksInfo> subComponentMarksInfoMap) {
		this.subComponentMarksInfoMap = subComponentMarksInfoMap;
	}

	public Map<String, MarksInfo> getChildSubComponentMarksInfoMap() {
		return childSubComponentMarksInfoMap;
	}

	public void setChildSubComponentMarksInfoMap(
			Map<String, MarksInfo> childSubComponentMarksInfoMap) {
		this.childSubComponentMarksInfoMap = childSubComponentMarksInfoMap;
	}

	@Override
	public String toString() {
		return "MarksData [subjectMarksInfoMap=" + subjectMarksInfoMap
				+ ", componentMarksInfoMap=" + componentMarksInfoMap
				+ ", subComponentMarksInfoMap=" + subComponentMarksInfoMap
				+ ", childSubComponentMarksInfoMap="
				+ childSubComponentMarksInfoMap + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
