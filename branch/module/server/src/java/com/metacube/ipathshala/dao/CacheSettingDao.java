package com.metacube.ipathshala.dao;

import java.util.Collection;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.entity.CacheSetting;

/**
 * Interface exposing data access operation on CacheSetting entity.
 * 
 * @author sabir
 * 
 */
public interface CacheSettingDao {

	CacheSetting create(CacheSetting cacheSetting);

	CacheSetting get(Object id);

	Collection<CacheSetting> getAll();

	CacheSetting update(CacheSetting cacheSetting);

	void remove(Object id);
	
}
