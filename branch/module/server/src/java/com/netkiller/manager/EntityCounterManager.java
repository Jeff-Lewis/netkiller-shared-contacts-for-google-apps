package com.netkiller.manager;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.netkiller.FilterInfo;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.EntityCounter;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.search.SearchResult;
import com.netkiller.service.EntityCounterService;

@Component
public class EntityCounterManager extends AbstractManager implements
		EntityManager {

	@Autowired
	private EntityCounterService service;

	@Override
	public EntityMetaData getEntityMetaData() {
		return service.getEntityMetaData();
	}

	@Override
	public SearchResult doSearch(FilterInfo filterInfo, DataContext dataContext)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getById(Key key) throws AppException {
		return service.getById(key);
	}

	@Override
	public Object restoreObject(Object object) throws AppException {
		return null;
	}

	public EntityCounter create(EntityCounter entityCounter)
			throws AppException {
		return service.create(entityCounter);
	}

	public EntityCounter update(EntityCounter entityCounter)
			throws AppException {
		return service.update(entityCounter);
	}

	public void delete(EntityCounter entityCounter) throws AppException {
		service.delete(entityCounter);
	}

	public Collection<EntityCounter> getByKeys(List<Key> entityCounterKeyList)
			throws AppException {
		return service.getByKeys(entityCounterKeyList);
	}

}
