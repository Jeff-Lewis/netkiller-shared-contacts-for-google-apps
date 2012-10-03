

package com.netkiller.cache.impl;

import java.util.ArrayList;
import java.util.List;

import com.netkiller.cache.AppCache;


public class ParentCache extends MemCache implements AppCache {
	
	
	
	public static final String COL_PARENT_KEY = "key";
	public static final String COL_FATHER_FIRST_NAME = "fatherFirstName";
	public static final String COL_FATHER_FIRST_NAME_UPPER_CASE = "fatherFirstNameUpperCase";
	public static final String COL_FROM_DATE = "fromDate";
	public static final String COL_TO_DATE = "toDate";
	public static final String COL_MOTHER_FIRST_NAME = "motherFirstName";
	public static final String COL_FATHER_EMAIL_ADDRESS = "fatherEmailAddress";
//	private AppCache appCache;
	

	public ParentCache(String entityName)	{
		super(entityName);
		
	}
	
	public List<String> getPropertyNameList()	{
		List<String> propertyNames = new ArrayList<String>();
		propertyNames.add(COL_PARENT_KEY);
		propertyNames.add(COL_FATHER_FIRST_NAME);
		propertyNames.add(COL_FATHER_FIRST_NAME_UPPER_CASE);
		propertyNames.add(COL_TO_DATE);
		propertyNames.add(COL_FROM_DATE);
		propertyNames.add(COL_MOTHER_FIRST_NAME);
		propertyNames.add(COL_FATHER_EMAIL_ADDRESS);
		return propertyNames;
		
	}
	
	
	
	
	public void addCacheValue(Object object)	{
		/*Parent parent = (Parent)object;
		Map<String,Object> propertyMap = new HashMap<String, Object>();
		propertyMap.put(COL_PARENT_KEY, parent.getKey());
		propertyMap.put(COL_FATHER_FIRST_NAME, parent.getFatherFirstName());
		propertyMap.put(COL_FATHER_FIRST_NAME_UPPER_CASE, parent.getFatherFirstNameUpperCase());
		propertyMap.put(COL_TO_DATE, parent.getToDate());
		propertyMap.put(COL_FROM_DATE, parent.getFromDate());
		propertyMap.put(COL_MOTHER_FIRST_NAME, parent.getMotherFirstName());
		propertyMap.put(COL_FATHER_EMAIL_ADDRESS, parent.getFatherEmailAddress());
		this.setCacheValue(parent.getKey(), new EntityCacheVO(propertyMap));*/
		
		
	}



	
	
	
	

}
