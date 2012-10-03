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
import com.netkiller.core.AppException;
import com.netkiller.dao.AbstractDao;
import com.netkiller.dao.EntityCounterDao;
import com.netkiller.entity.DomainAdmin;
import com.netkiller.entity.EntityCounter;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class EntityCounterDaoImpl extends AbstractDao<EntityCounter> implements
		EntityCounterDao {

	@Autowired
	public EntityCounterDaoImpl(
			PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public EntityCounter get(Object id) {
		return super.get(EntityCounter.class, id);
	}

	@Override
	public Collection<EntityCounter> getAll() {
		return super.getAll(EntityCounter.class);
	}

	@Override
	public Collection<EntityCounter> getByKeys(List<Key> entityCounterKeyList) {
		return super.getByKeys(EntityCounter.class, entityCounterKeyList);
	}

	@Override
	public void remove(Object id) {
		super.remove(EntityCounter.class, id);

	}

	@Override
	public EntityCounter create(EntityCounter entityCounter) {
		return super.create(entityCounter);
	}

	@Override
	public EntityCounter update(EntityCounter entityCounter) {
		return super.update(entityCounter);
	}

	@Transactional(readOnly = true)
	public EntityCounter getByEntityName(String entityname, String domain) {
		PersistenceManager pm = null;
		try {
			pm = getPersistenceManager();
			Query query = pm.newQuery(EntityCounter.class,
					"entityName == entityname && domain == domainName");
			query.declareParameters("String entityname,String domainName");
			Collection<EntityCounter> users = (Collection<EntityCounter>) query
					.execute(entityname, domain);
			EntityCounter entityCounter = null;
			if (users != null && users.size() > 0) {
				List<EntityCounter> entityCounters = (List<EntityCounter>) pm
						.detachCopyAll(users);
				entityCounter = entityCounters.get(0);
			}
			return entityCounter;

		} finally {
			releasePersistenceManager(pm);
		}

	}

}
