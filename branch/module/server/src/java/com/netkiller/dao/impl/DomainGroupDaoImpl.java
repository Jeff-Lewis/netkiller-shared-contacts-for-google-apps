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
import com.netkiller.dao.DomainGroupDao;
import com.netkiller.entity.DomainGroup;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class DomainGroupDaoImpl extends AbstractDao<DomainGroup> implements
		DomainGroupDao {

	@Autowired
	public DomainGroupDaoImpl(
			PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public DomainGroup create(DomainGroup domainGroup) {
		return super.create(domainGroup);
	}

	@Override
	public DomainGroup get(Object id) {
		return super.get(DomainGroup.class, id);
	}

	@Override
	public Collection<DomainGroup> getByKeys(List<Key> keyList) {
		return super.getAll(DomainGroup.class);
	}

	@Override
	public DomainGroup update(DomainGroup domainGroup) {
		return super.update(domainGroup);
	}

	@Override
	public void remove(Object id) {
		super.remove(DomainGroup.class, id);

	}

	@Override
	public DomainGroup getDomainGroupByDomainName(String domain) {
		PersistenceManager pm = null;
		try {
			pm = getPersistenceManager();
			Query query = pm
					.newQuery(DomainGroup.class, "domainName == domain");
			query.declareParameters("String domain");
			Collection<DomainGroup> users = (Collection<DomainGroup>) query
					.execute(domain);
			DomainGroup domainGroup = null;
			if (users != null && users.size() > 0) {
				List<DomainGroup> domainGroups = (List<DomainGroup>) pm
						.detachCopyAll(users);
				domainGroup = domainGroups.get(0);
			}
			return domainGroup;

			// return pm.detachCopy((DomainAdmin) query.execute(domain));
		} finally {
			releasePersistenceManager(pm);
		}
	}
}
