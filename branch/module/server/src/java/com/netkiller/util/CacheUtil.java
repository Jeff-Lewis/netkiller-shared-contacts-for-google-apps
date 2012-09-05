package com.netkiller.util;

import net.sf.jsr107cache.CacheManager;

import com.netkiller.cache.AppCacheConfig;

public class CacheUtil {

	private static final AppLogger log = AppLogger.getLogger(CacheUtil.class);

	public static CacheManager getCacheManger() {
		CacheManager cacheManager = CacheManager.getInstance();
		return cacheManager;
	}

	public static boolean isValidCacheEntity(AppCacheConfig cacheConfig, String entityName) {
		boolean isValid = false;
		if (cacheConfig.getActiveEntityCacheMap() != null) {
			if (cacheConfig.getActiveEntityCacheMap().keySet().contains(entityName)) {
				isValid = true;
			}
		}
		return isValid;
	}

}
