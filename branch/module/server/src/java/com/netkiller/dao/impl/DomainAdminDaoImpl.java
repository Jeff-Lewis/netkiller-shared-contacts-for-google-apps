package com.netkiller.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.Key;
import com.netkiller.dao.AbstractDao;
import com.netkiller.dao.DomainAdminDao;
import com.netkiller.entity.AppUserEntity;
import com.netkiller.entity.DomainAdmin;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class DomainAdminDaoImpl extends AbstractDao<DomainAdmin> implements
		DomainAdminDao {

	@Autowired
	public DomainAdminDaoImpl(
			PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public DomainAdmin create(DomainAdmin domainAdmin) {
		return super.create(domainAdmin);
	}

	@Override
	public DomainAdmin get(Object id) {
		return super.get(DomainAdmin.class, id);
	}

	@Override
	public Collection<DomainAdmin> getByKeys(List<Key> keyList) {
		return super.getByKeys(DomainAdmin.class, keyList);
	}

	@Override
	public void remove(Object id) {
		super.remove(DomainAdmin.class, id);
	}

	@Transactional(readOnly = true)
	public DomainAdmin getDomainAdminByDomainName(String domain) {
		PersistenceManager pm = null;
		try {
			pm = getPersistenceManager();
			Query query = pm
					.newQuery(DomainAdmin.class, "domainName == domain");
			query.declareParameters("String domain");
			Collection<DomainAdmin> users = (Collection<DomainAdmin>) query
					.execute(domain);
			DomainAdmin domainAdmin = null;
			if (users != null && users.size() > 0) {
				List<DomainAdmin> domainAdmins = (List<DomainAdmin>) pm
						.detachCopyAll(users);
				domainAdmin = domainAdmins.get(0);
			}
			return domainAdmin;

			// return pm.detachCopy((DomainAdmin) query.execute(domain));
		} finally {
			releasePersistenceManager(pm);
		}
	}

}
