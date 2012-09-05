package com.netkiller.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

import com.netkiller.core.AppException;
import com.netkiller.entity.CacheSetting;
import com.netkiller.service.CacheSettingService;
import com.netkiller.util.AppLogger;

public class AppCacheConfig {
	
	private static final AppLogger log = AppLogger.getLogger(AppCacheConfig.class);

	@Autowired
	private CacheSettingService cacheSettingService;

	private boolean cacheEnabled;
	private Map<String, AppCache> entityCacheMap;
	private Map<String, AppCache> activeEntityCacheMap;
	private AppCache keyCache;

	public AppCache getKeyCache() {
		if (!keyCache.isCacheIntialized())
			keyCache.initializeCache();
		return keyCache;
	}

	public void setKeyCache(AppCache keyCache) {
		this.keyCache = keyCache;
	}

	public Map<String, AppCache> getEntityCacheMap() {
		for (String entityName : entityCacheMap.keySet()) {
			if (entityCacheMap.get(entityName) != null && !entityCacheMap.get(entityName).isCacheIntialized())
				entityCacheMap.get(entityName).initializeCache();
		}
		return entityCacheMap;
	}

	public void setEntityCacheMap(Map<String, AppCache> entityCacheMap) {
		this.entityCacheMap = entityCacheMap;
	}

	public boolean isCacheEnabled() {

		return cacheSettingService.getCurrentCacheSetting().getCacheEnabled();
	}

	public void setCacheEnabled(boolean cacheEnabled) {

		CacheSetting cacheSetting = cacheSettingService.getCurrentCacheSetting();
		try {
			cacheSetting.setCacheEnabled(cacheEnabled);
			cacheSettingService.updateRelation(cacheSetting);
		} catch (AppException e) {
			log.debug("Unable to update cache Settings");
		}

		this.cacheEnabled = cacheEnabled;
	}

	public Map<String, AppCache> getActiveEntityCacheMap() {
		CacheSetting cacheSetting = cacheSettingService.getCurrentCacheSetting();
		if (entityCacheMap != null)
			if(activeEntityCacheMap == null)	{
				activeEntityCacheMap = new HashMap<String, AppCache>();
			}
			for (String entityName : cacheSetting.getActiveCacheEntities()) {			
				if ( activeEntityCacheMap.get(entityName) == null) {
					AppCache entityCache = entityCacheMap.get(entityName);
					if(!entityCache.isCacheIntialized())
						entityCache.initializeCache();
					this.activeEntityCacheMap.put(entityName, entityCache);
				}
			}
		return this.activeEntityCacheMap;
	}

	public void setActiveEntityCacheMap(Map<String, AppCache> entityCacheMap) {

		CacheSetting cacheSetting = cacheSettingService.getCurrentCacheSetting();
		try {
			cacheSetting.setActiveCacheEntities(new ArrayList<String>(entityCacheMap.keySet()));
			cacheSettingService.updateRelation(cacheSetting);
		} catch (AppException e) {
			log.debug("Unable to update cache Settings");
		}
		this.activeEntityCacheMap = entityCacheMap;
	}

}
