package com.netkiller.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.netkiller.ServerCommonConstant;
import com.netkiller.core.AppException;
import com.netkiller.dao.SetDao;
import com.netkiller.dao.ValueDao;
import com.netkiller.entity.Set;
import com.netkiller.entity.Value;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.util.AppLogger;

/**
 * @author dhruvsharma
 * 
 */
@Service
public class ValueService {

	private static final AppLogger log = AppLogger
			.getLogger(ValueService.class);

	private static final String setSortOrderAlpha = "Alpha";

	private static final String setSortOrderCustom = "Custom";


	@Autowired
	private ValueDao valueDao;
	
	@Autowired
	private KeyListService keyListService ;

	@Autowired
	private SetDao setDao;	
	
	@Autowired
	private SetService setService;

	@Autowired
	@Qualifier("ValueMetaData")
	private EntityMetaData entityMetaData;

	public Value createValue(Value value) throws AppException {
		try {
			Cache valueListCache = getValueListCache();
			if(valueListCache.containsKey(value.getSetKey().getId())){
				valueListCache.remove(value.getSetKey().getId());
			}
			return valueDao.create(value);
		} catch (DataAccessException dae) {
			String message = "Unable to create Value:" + value;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	/**
	 * Delete Value entity from store.
	 * 
	 * @param Value
	 */
	public void deleteValue(Value value) throws AppException {
		log.debug("Calling delete Value for Value id: " + value.getKey());
		try {			
			Key valueKey = value.getKey();
			
			Cache cache = getValueCache();
			if(cache.containsKey(value.getKey().getId())) {
				cache.remove(value.getKey().getId());
			}
			
			Cache valueListCache = getValueListCache();
			if(valueListCache.containsKey(value.getSetKey().getId())){
				valueListCache.remove(value.getSetKey().getId());
			}
			valueDao.remove(valueKey);
			
		} catch (DataAccessException dae) {
			String message = "Unable to delete Value:" + value;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Collection<Value> getAll() throws AppException {
		try {
			return valueDao.getAll();
		} catch (DataAccessException dae) {
			String message = "Unable to retrieve all Values";
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Value getById(Key id) throws AppException {
		try {
			Cache cache = getValueCache();
		/*	if(cache.containsKey(id)){		
				return (Value) cache.get(id);
			}else{*/
				Value currentValue = valueDao.get(id);
				populateTransientField(currentValue);
				cache.put(id.getId(), currentValue);
				return currentValue;
	//		}
			
		} catch (DataAccessException dae) {
			String message = "Unable to get Value by id:" + id;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	private void populateTransientField(Value currentValue) {
		if (currentValue.getParentValueKey() != null) {
			Value parentValue = valueDao.get(currentValue
					.getParentValueKey());
			currentValue.setParentValue(parentValue);
		}
		if (currentValue.getSetKey() != null) {
			Set parentSet = setDao.get(currentValue.getSetKey());
			currentValue.setSet(parentSet);
		}
	}

	/**
	 * @return the entityMetaData
	 */
	public EntityMetaData getEntityMetaData() {
		return entityMetaData;
	}

	/**
	 * Get all the values belonging to a particular Set
	 * 
	 * @param set
	 * @return
	 */
	public Collection<Value> getValueBySetKey(Set set) {
		String sortOrder = new String();
		if (set.getSetOrder().equals(setSortOrderAlpha)) {
			sortOrder = "value";
		} else if (set.getSetOrder().equals(setSortOrderCustom)) {
			sortOrder = "orderIndex";
		}

		Cache cache = getValueListCache();
	/*	if (cache.containsKey(set.getKey())) {
			return (Collection<Value>) cache.get(set.getKey().getId());
		} else {*/
			Collection<Value> valueList = valueDao.getBySetKey(set.getKey(),
					sortOrder);
	//		cache.put(set.getKey().getId(), valueList);
			return valueList;
	//	}

	}

	private Cache getValueListCache() {
		CacheManager cacheManager= CacheManager.getInstance();
		Cache cache=cacheManager.getCache(ServerCommonConstant.STR_VALUE_LIST_CACHE);
		return cache ;
	}

	/**
	 * @return Collection Of Value Hierarchy
	 */

	public Collection<Value> getValueHierarchy(Key valueKey) {
		Collection<Value> valueCollection = new ArrayList<Value>();
		Value rootValue = valueDao.get(valueKey);
		valueCollection.add(rootValue);
		while (rootValue.getParentValueKey() != null) {
			rootValue = valueDao.get(rootValue.getParentValueKey());
			valueCollection.add(rootValue);
		}
		return valueCollection;
	}

	public Value updateValue(Value value) throws AppException {
		try {
			Value updatedValue = valueDao.update(value);
			Cache cache = getValueCache();
			if(cache.containsKey(value.getKey().getId())) {
				cache.remove(value.getKey().getId());
			}
			
			Cache valueListCache = getValueListCache();
			if(valueListCache.containsKey(value.getSetKey().getId())){
				valueListCache.remove(value.getSetKey().getId());
			}
			return updatedValue;
		} catch (DataAccessException dae) {
			String message = "Unable to update Value:" + value;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}
	
	public Map<Key, Value> getByKeys(List<Key> valueKeyList)
			throws AppException {
		List<Key> refinedValueKeyList = new ArrayList<Key>();
		for (Iterator<Key> valueKeyListIterator = valueKeyList.iterator(); valueKeyListIterator
				.hasNext();) {
			Key currentKey = (Key) valueKeyListIterator.next();
			if (!(refinedValueKeyList.contains(currentKey))) {
				refinedValueKeyList.add(currentKey);
			}
		}
		
		Map<Key, Value> keyValueMap = new HashMap<Key, Value>();
		Cache valueCache = getValueCache() ;
		List<Key> notInCacheValueKeyList = new ArrayList<Key>();
		for(Key key : refinedValueKeyList) {
			/*if(valueCache.containsKey(key.getId())) {
				Value value = (Value)valueCache.get(key.getId());
				keyValueMap.put(key, value);
			}else {*/
				notInCacheValueKeyList.add(key);
			//}
		}
		if (notInCacheValueKeyList != null && notInCacheValueKeyList.size() != 0) {
			
			Collection<Value> collection = keyListService
					.getByKeys(notInCacheValueKeyList,Value.class);
			List<Value> refinedValues = (List<Value>) collection;
			for (Iterator<Value> iterator = refinedValues.iterator(); iterator
					.hasNext();) {
				Value currentValue = (Value) iterator.next();
				populateTransientField(currentValue);
				keyValueMap.put(currentValue.getKey(), currentValue);
				valueCache.put(currentValue.getKey().getId(), currentValue);
			}
			
		}
		return keyValueMap;
	}
	
	private Cache getValueCache(){
		CacheManager cacheManager= CacheManager.getInstance();
		Cache cache=cacheManager.getCache(ServerCommonConstant.STR_VALUE_CACHE);
		return cache;
	}
	
	public Collection<Object> populateRelationshipFields(List<Object> objectList) throws AppException {
		
		List<Object> valueList = new ArrayList<Object>();
		Cache valueCache = getValueCache();
		
		List<Long> keyList = new ArrayList<Long>();
		for (Iterator<Object> objectIterator = objectList.iterator(); objectIterator.hasNext();) {
			Value currentValue = (Value) objectIterator.next();
			keyList.add(currentValue.getKey().getId());
		}
	
		Map<Long,Value> keyValueMap = null ;
		try {
			keyValueMap = valueCache.getAll(keyList);
		} catch (CacheException e) {
			String msg = "Cache exception occurred.";
			throw new AppException(msg,e);
		}
		
		for (Iterator<Object> objectIterator = objectList.iterator(); objectIterator.hasNext();) {
			Value currentValue = (Value) objectIterator.next();
			if(!keyValueMap.containsKey(currentValue.getKey().getId())){
				populateTransientField(currentValue);
				valueCache.put(currentValue.getKey().getId(), currentValue);
			}else{
				currentValue = (Value) keyValueMap.get(currentValue.getKey().getId());
			}
			valueList.add(currentValue);
		}
		return valueList;
	}
	
	/**
	 * Get all the values belonging to a particular Set and parent value
	 * 
	 * @param set
	 * @return
	 */
	public Collection<Value> getValueBySetKeyandParentValueKey(Set set,Key parent) {
		String sortOrder = new String();
		if (set.getSetOrder().equals(setSortOrderAlpha)) {
			sortOrder = "value";
		} else if (set.getSetOrder().equals(setSortOrderCustom)) {
			sortOrder = "orderIndex";
		}
		return valueDao.getBySetKeyandParentValueKey(set.getKey(), parent, sortOrder);
	}
	
	public Object restoreEntity(Object object) {
		Value value = (Value)object; 
		value.setIsDeleted(false);
		return valueDao.update(value);
	}
	
	public List<Value> getValuesBySetName(String setName){
		List<Value> valueList = null;
		Set set = setService.getSetByName(setName.trim());
		if(set!=null){
			valueList = new ArrayList<Value>( getValueBySetKey(set));
		}
		return valueList;
	}

}
