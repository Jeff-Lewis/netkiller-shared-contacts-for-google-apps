package com.metacube.ipathshala.util;

import java.util.Comparator;

import com.metacube.ipathshala.entity.AcademicYear;

public class AcademicYearComparator implements Comparator<AcademicYear>{

	@Override
	public int compare(AcademicYear o1, AcademicYear o2) {
		int result = 0;
		
		if(o1==null || o2 ==null)
			return result;
		
		if(o1.getFromDate()==null || o2.getFromDate()==null)
			return result;
		
		result = o1.getFromDate().compareTo(o2.getFromDate());		
		
		return result;
	}
	
	

}
