package com.metacube.ipathshala.util;

import java.util.Comparator;

import com.metacube.ipathshala.entity.Subject;

public class SubjectComparator implements Comparator<Subject> {

	@Override
	public int compare(Subject o1, Subject o2) {
		int result;
		if(o1.getSubjectLevelValue()!=null && o2.getSubjectLevelValue()!=null){
			if( (Integer.valueOf(o1.getSubjectLevelValue().getOrderIndex())).compareTo(
					(Integer.valueOf(o2.getSubjectLevelValue().getOrderIndex())) )==0){
				result=o1.getSubjectName().compareTo(o2.getSubjectName());
			} else{
				result=Integer.valueOf(o1.getSubjectLevelValue().getOrderIndex()).compareTo(Integer.valueOf(o2.getSubjectLevelValue().getOrderIndex()));
			}
		} else{
			result=o1.getSubjectName().compareTo(o2.getSubjectName());
		}
		return result;
	}

}
