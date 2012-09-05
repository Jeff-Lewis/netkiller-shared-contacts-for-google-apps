package com.netkiller.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.netkiller.ServerCommonConstant;
import com.netkiller.core.AppException;
import com.netkiller.dao.SetDao;
import com.netkiller.entity.Set;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.util.AppLogger;

/**
 * @author dhruvsharma
 * 
 */
@Service
public class SetService {

	private static final AppLogger log = AppLogger.getLogger(SetService.class);

	@Autowired
	private SetDao setDao;

	@Autowired
	@Qualifier("SetMetaData")
	private EntityMetaData entityMetaData;

	public Collection<Set> getAll() throws AppException {
		try {
			return setDao.getAll();
		} catch (DataAccessException dae) {
			String message = "Unable to retrieve all sets";
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Set getById(Class<Set> type, Object id) throws AppException {
		try {
			Set currentSet = setDao.get(id);
			if (currentSet.getParentSetKey() != null) {
				Set parentSet = setDao.get(currentSet.getParentSetKey());
				currentSet.setParentSet(parentSet);
			}
			return currentSet;
		} catch (DataAccessException dae) {
			String message = "Unable to get Set by id:" + id;
			log.error(message, dae);
			throw new AppException(message, dae);
		}

	}

	public Set createSet(Set set) throws AppException {
		try {
			return setDao.create(set);
		} catch (DataAccessException dae) {
			String message = "Unable to create Set:" + set;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Set updateSet(Set set) throws AppException {
		try {
			return setDao.update(set);
		} catch (DataAccessException dae) {
			String message = "Unable to update Set:" + set;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}
	/**
	 * Delete Set entity from store.
	 * 
	 * @param Set
	 */
	public void deleteSet(Set set) throws AppException {
		log.debug("Calling delete Set for Set id: " + set.getKey());
		try {
			Key setKey = set.getKey();
			setDao.remove(setKey);
			Cache cache = getValueListCache();
			if(cache.containsKey(setKey.toString())) {
				cache.remove(setKey.toString());
			}
		} catch (DataAccessException dae) {
			String message = "Unable to delete Set:" + set;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	private Cache getValueListCache() {
		CacheManager cacheManager= CacheManager.getInstance();
		Cache cache=cacheManager.getCache(ServerCommonConstant.STR_VALUE_LIST_CACHE);
		return cache ;
	}
	

	/**
	 * @return the entityMetaData
	 */
	public EntityMetaData getEntityMetaData() {
		return entityMetaData;
	}

	/**
	 * @return Set using setName
	 */
	/**
	 * @param setName
	 * @return
	 */
	public Set getSetByName(String setName) {
		List<Set> setList = (List<Set>) setDao.getBySetName(setName);
		if (setList.size() != 0) {
			return setList.get(0);
		} else {
			return null;
		}
	}

	public Collection<Set> getSetHierarchy(Key setKey) {
		Collection<Set> setCollection = new ArrayList<Set>();
		Set rootSet = setDao.get(setKey);
		setCollection.add(rootSet);
		Set childSet = rootSet;
		while (childSet.getParentSetKey() != null) {
			childSet = setDao.get(childSet.getParentSetKey());
			setCollection.add(childSet);
		}
		return setCollection;
	}

	public Object restoreEntity(Object object) {
		Set set = (Set)object; 
		set.setIsDeleted(false);
		return setDao.update(set);
	}
}
