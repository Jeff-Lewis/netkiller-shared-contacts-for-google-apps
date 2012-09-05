package com.netkiller.util;

import java.util.Comparator;

import com.netkiller.entity.Teacher;

public class TeacherComparator implements Comparator<Teacher>{
	
	@Override
	public int compare(Teacher o1, Teacher o2) {
		int result = 0;
		
		if(o1==null || o2 ==null)
			return result;
		
		result = o1.getFirstNameUpperCase().compareTo(o2.getFirstNameUpperCase());		
		
		return result;
	}
	

}


