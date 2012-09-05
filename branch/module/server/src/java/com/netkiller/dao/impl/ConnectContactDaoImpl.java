package com.netkiller.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.Key;
import com.netkiller.dao.AbstractDao;
import com.netkiller.dao.ConnectContactDao;
import com.netkiller.entity.ConnectContact;
import com.netkiller.entity.DomainAdmin;

@Repository
public class ConnectContactDaoImpl  extends AbstractDao<ConnectContact> implements
ConnectContactDao{
	
	@Autowired
	public ConnectContactDaoImpl(PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public ConnectContact create(ConnectContact object) {
		return super.create(object);
	}

	@Override
	@Transactional(readOnly = true)
	public ConnectContact get(Key key) {
		return super.get(ConnectContact.class, key);
	}
	
	@Override
	public void remove(Key key) {
		super.remove(ConnectContact.class, key);

	}

	@Override
	public List<ConnectContact> getByUrl(String url) {
		List<ConnectContact> list = new ArrayList<ConnectContact>();
		PersistenceManager pm = null;
		try {
			pm = getPersistenceManager();
			Query query = pm
					.newQuery(ConnectContact.class, "randomUrl == url");
			query.declareParameters("String url");
			Collection<ConnectContact> dbList = (Collection<ConnectContact>) query
					.execute(url);
			if (dbList != null && dbList.size() > 0) {
				list = (List<ConnectContact>) pm
						.detachCopyAll(dbList);
			}
			return list;

			// return pm.detachCopy((DomainAdmin) query.execute(domain));
		} finally {
			releasePersistenceManager(pm);
		}
	}
	
}
