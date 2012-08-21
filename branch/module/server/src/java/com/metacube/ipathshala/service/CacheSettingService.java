package com.metacube.ipathshala.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.cache.AppCacheConfig;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.dao.CacheSettingDao;
import com.metacube.ipathshala.entity.CacheSetting;
import com.metacube.ipathshala.util.AppLogger;

/**
 * Service class having business methods to maintain relation between class,
 * subject and teacher.
 * 
 * @author sparakh
 * 
 */
@Service
public class CacheSettingService extends AbstractService {
	private static final AppLogger log = AppLogger.getLogger(CacheSettingService.class);

	@Autowired
	private CacheSettingDao cacheSettingDao;
	
	@Autowired
	private AppCacheConfig cacheConfig;

	

	/**
	 * This will call persistence layer to create relation between Class,
	 * Subject and Teacher entity.
	 * 
	 * @param cacheSetting
	 * @param dataContext
	 *            TODO
	 * @return
	 * @throws AppException
	 */
	public CacheSetting createRelation(CacheSetting cacheSetting, DataContext dataContext) throws AppException {
		log.debug("Inside createRelation(...)");
		try {
		
			return cacheSettingDao.create(cacheSetting);

		} catch (DataAccessException exception) {
			throw new AppException("Unable to create relation between class and student: " + cacheSetting, exception);
		}
	}

	/**
	 * This will call persistence layer to update relation between Class,
	 * Subject and Teacher entity.
	 * 
	 * @param cacheSetting
	 * @return
	 * @throws AppException
	 */
	public CacheSetting updateRelation(CacheSetting cacheSetting) throws AppException {
		log.debug("Inside updateRelation(...)");

		try {
			return cacheSettingDao.update(cacheSetting);
		} catch (DataAccessException exception) {
			throw new AppException("Unable to create relation between  class and student: " + cacheSetting, exception);
		}
	}

	/**
	 * This will call persistence layer to delete relation between Class and
	 * Student entity.
	 * 
	 * @param key
	 * @throws AppException
	 */
	public void deleteRelation(Key key) throws AppException {

		try {
			cacheSettingDao.remove(key);
		} catch (DataAccessException exception) {
			throw new AppException("Unable to delete relation between  class and student.", exception);
		}
	}

	/**
	 * This is to get the relation by key.
	 * 
	 * @param key
	 *            the identifier
	 * @return
	 * @throws AppException
	 */
	public CacheSetting getById(Key key) throws AppException {
		try {
			CacheSetting currentRelation = cacheSettingDao.get(key);

			return currentRelation;
		} catch (DataAccessException exception) {
			throw new AppException("Unable to get  class student relation for id: " + key.getId(), exception);
		}
	}

	/**
	 * This will return all entities defining relation between class and student
	 * 
	 * 
	 * @return
	 * @throws AppException
	 */
	public Collection<CacheSetting> getAllRelations() throws AppException {
		log.debug("Inside getAllRelations(...)");

		try {
			return cacheSettingDao.getAll();
		} catch (DataAccessException exception) {
			throw new AppException("Unable to get all entity defining relation between  class and student", exception);
		}
	}
	
	/**
	 * This will return all entities defining relation between class and student
	 * 
	 * 
	 * @return
	 * @throws AppException
	 */
	public CacheSetting getCurrentCacheSetting()  {
		log.debug("Inside getAllRelations(...)");
		CacheSetting cacheSetting= null;
		try {
			
			List<CacheSetting> cacheSettings = (List<CacheSetting>) getAllRelations();
			if(cacheSettings==null || cacheSettings.isEmpty())	{
				cacheSetting = new CacheSetting();
				cacheSetting.setCacheEnabled(false);
				cacheSetting.setActiveCacheEntities(new ArrayList<String>());
				cacheSetting = cacheSettingDao.create(cacheSetting);
			}
			else	{
				cacheSetting = cacheSettings.get(0);
			}
			
		} catch (DataAccessException exception) {
			log.debug("Unable to get cache Settings for current Domain");
		} catch (AppException e) {
			log.debug("Unable to get cache Settings for current Domain");
			e.printStackTrace();
		}
		return cacheSetting;
	}

	
	


}
