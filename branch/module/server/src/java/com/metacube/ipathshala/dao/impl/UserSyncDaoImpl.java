package com.metacube.ipathshala.dao.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.dao.AbstractDao;
import com.metacube.ipathshala.dao.UserSyncDao;
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
			query.setFilter("syncDate == date1 && userEmail == email");
			query.declareImports("import java.util.Date");
			query.declareParameters("Date date1,String email");
			// query.declareParameters("");
			Collection<UserSync> users = (Collection<UserSync>) query.execute(
					date, email);
			UserSync userSync = null;
			if (users != null && !users.isEmpty()) {
				List<UserSync> userSyncList = (List<UserSync>) pm
						.detachCopyAll(users);
				userSync = userSyncList.get(0);
			}
			return userSync;
		}finally {
			releasePersistenceManager(pm);
		}
	}
}
