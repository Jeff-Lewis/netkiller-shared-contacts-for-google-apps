package com.netkiller.manager;

import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.netkiller.FilterInfo;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.search.SearchResult;

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
