package com.metacube.ipathshala.manager;

import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.FilterInfo;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.search.SearchResult;

@Component
public class UserSyncManager extends AbstractManager implements EntityManager{

	@Override
	public EntityMetaData getEntityMetaData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchResult doSearch(FilterInfo filterInfo, DataContext dataContext)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getById(Key key) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object restoreObject(Object object) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

}
