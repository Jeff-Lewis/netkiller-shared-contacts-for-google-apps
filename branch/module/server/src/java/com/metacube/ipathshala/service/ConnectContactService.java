package com.metacube.ipathshala.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.GridRequest;
import com.metacube.ipathshala.dao.ConnectContactDao;
import com.metacube.ipathshala.entity.ConnectContact;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.search.SearchService;
import com.metacube.ipathshala.util.AppLogger;

@Service
public class ConnectContactService extends AbstractService{
	
	private static final AppLogger log = AppLogger
	.getLogger(ConnectContactService.class);
	
	@Autowired
	@Qualifier("ConnectContactMetaData")
	private EntityMetaData entityMetaData;
	
	@Autowired
	private SearchService searchService;
	
	public EntityMetaData getEntityMetaData() {
		return entityMetaData;
	}

	public ConnectContactDao getConnectContactDao() {
		return connectContactDao;
	}

	@Autowired
	private ConnectContactDao connectContactDao;
	
	public ConnectContact create(ConnectContact object) {
		return connectContactDao.create(object);
	}

	public void remove(Key key) {
		connectContactDao.remove( key);

	}
	
	public List<ConnectContact> getByUrl(String url) {
		return connectContactDao.getByUrl(url);
	}
	
	public ConnectContact getByKey(Key key) {
		return connectContactDao.get( key);
	}

	
	
}
