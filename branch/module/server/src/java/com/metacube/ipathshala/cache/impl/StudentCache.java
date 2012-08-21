package com.metacube.ipathshala.cache.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.metacube.ipathshala.cache.AppCache;
import com.metacube.ipathshala.cache.AppCacheManager;
import com.metacube.ipathshala.entity.Student;


public class StudentCache extends MemCache implements AppCache {
	
	
	
	public static final String COL_STUDENT_KEY = "key";
	public static final String COL_ENROLLMENT_NUMBER = "enrollmentNumber";
	public static final String COL_FROM_DATE = "fromDate";
	public static final String COL_TO_DATE = "toDate";
	public static final String COL_FIRST_NAME = "firstName";
	public static final String COL_FIRST_NAME_UPPER_CASE = "firstNameUpperCase";
//	private AppCache appCache;
	

	public StudentCache(String entityName)	{
		super(entityName);
		
	}
	
	public List<String> getPropertyNameList()	{
		List<String> propertyNames = new ArrayList<String>();
		propertyNames.add(COL_STUDENT_KEY);
		propertyNames.add(COL_ENROLLMENT_NUMBER);
		propertyNames.add(COL_FROM_DATE);
		propertyNames.add(COL_TO_DATE);
		propertyNames.add(COL_FIRST_NAME);
		propertyNames.add(COL_FIRST_NAME_UPPER_CASE);
		return propertyNames;
		
	}
	
	
	
	
	public void addCacheValue(Object object)	{
		Student student = (Student)object;
		Map<String,Object> propertyMap = new HashMap<String, Object>();
		propertyMap.put(COL_STUDENT_KEY, student.getKey());
		propertyMap.put(COL_ENROLLMENT_NUMBER, student.getEnrollmentNumber());
		propertyMap.put(COL_FROM_DATE, student.getFromDate());
		propertyMap.put(COL_TO_DATE, student.getToDate());
		propertyMap.put(COL_FIRST_NAME, student.getFirstName());
		propertyMap.put(COL_FIRST_NAME_UPPER_CASE, student.getFirstNameUpperCase());
		this.setCacheValue(student.getKey(), new EntityCacheVO(propertyMap));
		
		
	}



	
	
	
	

}
