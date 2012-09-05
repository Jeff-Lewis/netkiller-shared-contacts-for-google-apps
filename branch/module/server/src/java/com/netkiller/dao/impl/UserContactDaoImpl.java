package com.netkiller.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.Key;
import com.netkiller.dao.AbstractDao;
import com.netkiller.dao.UserContactDao;
import com.netkiller.entity.UserContact;
import com.netkiller.entity.UserSync;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class UserContactDaoImpl extends AbstractDao<UserContact> implements
		UserContactDao {

	@Autowired
	public UserContactDaoImpl(
			PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	@Transactional(readOnly = true)
	public UserContact get(Object id) {
		return super.get(UserContact.class, id);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<UserContact> getByKeys(List<Key> userContactsKeyList) {
		return super.getByKeys(UserContact.class, userContactsKeyList);
	}

	@Override
	public void remove(Object id) {
		super.remove(UserContact.class, id);

	}

	@Override
	public UserContact create(UserContact object) {
		return super.create(object);
	}

	@Override
	public UserContact update(UserContact object) {
		return super.update(object);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserContact> getUserContactListForDomain(String domain) {
		PersistenceManager pm = null;
		List<UserContact> userContactList = new ArrayList<UserContact>();
		try {
			pm = getPersistenceManager();
			javax.jdo.Query query = pm.newQuery(UserContact.class);
			query.setFilter("domainName == domain1");
			query.declareParameters("String domain1");
			Collection<UserContact> users = (Collection<UserContact>) query
					.execute(domain);
			if (users != null && !users.isEmpty()) {
				userContactList.addAll(users);
			}

			return userContactList;
		} finally {
			releasePersistenceManager(pm);
		}

	}

}
