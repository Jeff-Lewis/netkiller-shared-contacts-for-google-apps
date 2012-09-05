package com.netkiller.cache;

import com.netkiller.core.AppException;

public interface AppCacheService {
	
	public AppCache getEntityCache(String cacheName);
	
	public AppCache getKeyCache();
	
	public void registerCache(String cacheName) throws AppException;
	
	public void registerKeyCache() throws AppException;
	
	public void clearCache(String cacheName);
	
	public void initialize() throws AppException;
	
	public void clearAllCache();

}
