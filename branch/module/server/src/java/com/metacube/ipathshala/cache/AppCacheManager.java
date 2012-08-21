package com.metacube.ipathshala.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.metacube.ipathshala.core.AppException;

@Component
public class AppCacheManager {
	
	@Autowired
	@Qualifier("MemCacheService")
	private AppCacheService cacheService;

	public AppCacheService getCacheService() {
		return cacheService;
	}

	public AppCache getEntityCache(String cacheName) {
		return cacheService.getEntityCache(cacheName);
	}

	public AppCache getKeyCache() {
		return cacheService.getKeyCache();
	}

	public void registerCache(String cacheName) throws AppException {
		cacheService.registerCache(cacheName);

	}

	public void clearCache(String cacheName) {
		cacheService.clearCache(cacheName);
	}

	public void initialize() throws AppException {
	cacheService.initialize();
	}

	public void clearAllCache() {
		cacheService.clearAllCache();
	}

	


}
