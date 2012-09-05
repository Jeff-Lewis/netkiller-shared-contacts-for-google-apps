package com.netkiller.cache.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.Key;
import com.netkiller.cache.AppCache;
import com.netkiller.entity.Student;

public class KeyCache extends MemCache implements AppCache {
	

	
	public static final String COL_ENTITY_NAME = "entityName";
	public static final String COL_ENTITY_KEY_LIST= "entityKeyList";
//	private AppCache appCache;
	

	public KeyCache(String entityName)	{
		super(entityName);
		
	}
	
	public List<String> getPropertyNameList()	{
		List<String> propertyNames = new ArrayList<String>();
		propertyNames.add(COL_ENTITY_NAME);
		propertyNames.add(COL_ENTITY_KEY_LIST);
		return propertyNames;
		
	}
	
	
	public void addCacheValue(Object object)	{
		Map.Entry<String,List<Key>> mapEntry = (Map.Entry<String,List<Key>>)object;
		Map<String,Object> propertyMap = new HashMap<String, Object>();
		propertyMap.put(COL_ENTITY_KEY_LIST, mapEntry.getValue());
		this.setCacheValue(mapEntry.getKey(), new EntityCacheVO(propertyMap));
		
		
	}



	

}
