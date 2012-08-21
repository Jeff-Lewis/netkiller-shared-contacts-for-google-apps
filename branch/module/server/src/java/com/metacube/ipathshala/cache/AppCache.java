package com.metacube.ipathshala.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.core.AppException;

public interface AppCache {
	
	

	public EntityCacheValue getCachedValue(Object entityKey);
	
	public void remove(Object key);
	
	public boolean containsKey(Object key);
	
	public void addCacheValue(Object object);
	
	public Collection<EntityCacheValue> getAllCacheValues();
	
	public void setCacheValue(Object key, EntityCacheValue entityCacheValue);
	
	public void clear();
	
	public void initializeCache();
	
	
	public List<String> getPropertyNameList();
	
	public Map<String,EntityCacheValue> getByKeys(List<Key> keys) throws AppException;
	
	public boolean isCacheIntialized();
	
	
}
