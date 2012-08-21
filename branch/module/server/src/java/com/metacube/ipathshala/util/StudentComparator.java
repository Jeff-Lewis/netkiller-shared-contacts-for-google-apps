package com.metacube.ipathshala.util;

import java.util.Comparator;

import com.metacube.ipathshala.entity.Student;

public class StudentComparator implements Comparator<Student> {
	private static final AppLogger log = AppLogger.getLogger(StudentComparator.class);
	@Override
	public int compare(Student o1, Student o2) {
		int result = 0;
		
		if(o1==null || o2 ==null)
			return result;
		
		result = o1.getFirstNameUpperCase().compareTo(o2.getFirstNameUpperCase());		
		
		return result;
	}

}
