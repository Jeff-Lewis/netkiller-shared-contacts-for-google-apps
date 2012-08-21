package com.metacube.ipathshala.dao.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.dao.AbstractDao;
import com.metacube.ipathshala.dao.UserSyncDao;
import com.metacube.ipathshala.entity.Student;
import com.metacube.ipathshala.entity.UserSync;

@Component
public class UserSyncDaoImpl extends AbstractDao<UserSync> implements
		UserSyncDao {

	@Autowired
	public UserSyncDaoImpl(PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public UserSync get(Object id) {
		return super.get(UserSync.class, id);
	}

	@Override
	public Collection<UserSync> getByKeys(List<Key> keyList) {
		return super.getByKeys(UserSync.class, keyList);
	}

	@Override
	public void remove(Object id) {
		super.remove(UserSync.class, id);
	}

	public UserSync create(UserSync userSync) {
		return super.create(userSync);
	}

	public UserSync update(UserSync userSync) {
		return super.update(userSync);
	}

	@Transactional(readOnly = true)
	public UserSync getUserSyncByEmailAndDate(String email, Date date) {
		PersistenceManager pm = null;
		try {
			pm = getPersistenceManager();
			Query query = pm.newQuery(UserSync.class);
			query.setFilter("date == date1 && userEmail == email");
			query.declareParameters("String email");
			query.declareParameters("Date date1");
			return pm.detachCopy((UserSync) query.execute(email, date));
		} finally {
			releasePersistenceManager(pm);
		}
	}

}
