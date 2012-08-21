package com.metacube.ipathshala.manager;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.FilterInfo;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.search.SearchResult;

/**
 * An interface for all entity managers.
 * @author prateek
 *
 */
public interface EntityManager {

	EntityMetaData getEntityMetaData();
	
	SearchResult doSearch(FilterInfo filterInfo, DataContext dataContext) throws AppException;
	
	Object getById(Key key) throws AppException;
	
	Object restoreObject(Object object) throws AppException;
	
}
