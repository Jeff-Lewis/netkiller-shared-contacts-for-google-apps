package com.metacube.ipathshala.cache.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.cache.EntityCacheValue;

public class EntityCacheVO implements EntityCacheValue,Serializable {
	
	private Map<String,Object> propertyMap;
	
	
	public EntityCacheVO( Map<String,Object> propertyMap) {
		
		this.propertyMap = propertyMap;
		
	
	}


	@Override
	public Object getProperty(String propertyName) {
		// TODO Auto-generated method stub
		return this.propertyMap.get(propertyName);
	}

	
}
