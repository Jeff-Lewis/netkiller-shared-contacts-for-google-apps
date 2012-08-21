package com.metacube.ipathshala.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.metacube.ipathshala.core.AppException;

public class EntityUtil {
	private static final AppLogger log = AppLogger.getLogger(EntityUtil.class);

	public static <E> Map<Key, E> getEntityMapFromEntityList(
			Collection<E> entityList) throws AppException {
		Map<Key, E> entityMap = new HashMap<Key, E>();
		for (E entity : entityList) {
			Key key = KeyUtil.geyKeyFromObject(entity);
			if (key != null) {
				entityMap.put(key, entity);
			}
		}
		return entityMap;
	}

	public static <E> List<E> getEntityList(Collection<Object> records) {
		List<E> entityList = new ArrayList<E>();
		for (Object record : records) {
			E entity = null;
			try {
				entity = (E) record;
				entityList.add(entity);
			} catch (ClassCastException e) {
				String message = "Cannot cast " + record.getClass().getName()
						+ " to " + entity.getClass().getName();
				log.error(message);
			}

		}
		return entityList;
	}

	public static <E> List<Object> getObjectList(Collection<E> records) {
		List<Object> entityList = new ArrayList<Object>();
		for (E record : records) {
			entityList.add(record);
		}
		return entityList;
	}
	
	public  static <E> List<E> getPaginatedList(List<E> objectList, int pageNumber,int recordPerPage){
		List<E> newList = new ArrayList<E>();
		int totalSize = objectList.size();
		int totalPages = totalSize / recordPerPage + 1;
		if (pageNumber > totalPages) {
			return objectList;
		}
		int beginIndex = (pageNumber - 1) * recordPerPage;
		int endIndex = beginIndex + recordPerPage;
		if (totalSize < endIndex) {
			endIndex = totalSize;
		}
		if(beginIndex>=0 &&  endIndex>=0){
			newList = objectList.subList(beginIndex, endIndex);
			return newList;
		}else{
			return objectList;
		}
		
	}
	
	public static <E> List<E> filterDeletedRecords(Collection<E> records) {
		List<E> entityList = new ArrayList<E>();
		for (E record : records) {
			boolean isDeleted = false;
			try {
				String id = BeanUtils.getProperty(record, "isDeleted");
				
				if (id != null) {
					isDeleted = Boolean.valueOf(id);
				}
			
			}
		 catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!isDeleted)	{
				entityList.add(record);
			}

		}
		return entityList;
	}

}
