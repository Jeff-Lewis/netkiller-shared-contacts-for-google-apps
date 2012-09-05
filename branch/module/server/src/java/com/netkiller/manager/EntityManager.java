package com.netkiller.manager;

import com.google.appengine.api.datastore.Key;
import com.netkiller.FilterInfo;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.search.SearchResult;

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
