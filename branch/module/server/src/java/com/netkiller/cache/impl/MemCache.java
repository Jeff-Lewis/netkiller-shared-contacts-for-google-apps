package com.netkiller.cache.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.datastore.Key;
import com.netkiller.cache.EntityCacheValue;
import com.netkiller.core.AppException;
import com.netkiller.util.CacheUtil;

public abstract class MemCache {
	
	
	private String entityName;
	
	
	public String getEntityName() {
		return this.entityName;
	}


	private Cache cache;
	
	protected MemCache(String cacheName) {
		this.entityName = cacheName;
	}

	
	public EntityCacheValue getCachedValue(Object entityKey){
		return (EntityCacheValue)this.cache.get(entityKey);
	}

	public Collection<EntityCacheValue> getAllCacheValues()	{
		
		return this.cache.values();
	}
	
	public void remove(Object key) {
		this.cache.remove(key);
	}

	public Map<String,EntityCacheValue> getByKeys(List<Key> keys) throws AppException	{
		try {
			return this.cache.getAll(keys);
		} catch (CacheException cacheException) {
			throw new AppException("Failed to get value from Cache",cacheException);
		}
	}
	
	public boolean containsKey(Object key) {
		return this.cache.containsKey(key);
	}

	
	public void setCacheValue(Object key, EntityCacheValue entityCacheValue){
		this.cache.put(key, entityCacheValue);
		
	}

	
	public void clear() {
		this.cache.clear();		
	}


	public void initializeCache() {
		CacheManager cacheManger = CacheUtil.getCacheManger();
		this.cache=cacheManger.getCache(this.entityName);
		
	}
	
	public boolean isCacheIntialized()	{
		if(this.cache != null)
			return true;
		else return false;
	}
	

}
