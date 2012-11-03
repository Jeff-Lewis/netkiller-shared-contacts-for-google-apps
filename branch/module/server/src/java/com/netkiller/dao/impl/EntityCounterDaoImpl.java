package com.netkiller.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Random;

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
import com.netkiller.entity.Contact;
import com.netkiller.entity.DomainAdmin;
import com.netkiller.entity.EntityCounter;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class EntityCounterDaoImpl extends AbstractDao<EntityCounter> implements
		EntityCounterDao {

	private static final int NUM_SHARDS = 20;

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

	@Override
	public int getCount() {
		int sum = 0;
		PersistenceManager pm = null;

		try {
			pm = getPersistenceManager();
			String query = "select from " + EntityCounter.class.getName();
			List<EntityCounter> shards = (List<EntityCounter>) pm.newQuery(
					query).execute();
			if (shards != null && !shards.isEmpty()) {
				for (EntityCounter shard : shards) {
					sum += shard.getCount();
				}
			}
		} finally {
			pm.close();
		}

		return sum;
	}

	/**
	 * Increment the value of this sharded counter.
	 */
	@Override
	public void increment() {
		PersistenceManager pm = null;

		Random generator = new Random();
		int shardNum = generator.nextInt(NUM_SHARDS);

		try {
			pm = getPersistenceManager();
			Query shardQuery = pm.newQuery(EntityCounter.class);
			shardQuery.setFilter("shardNumber == numParam");
			shardQuery.declareParameters("int numParam");

			List<EntityCounter> shards = (List<EntityCounter>) shardQuery
					.execute(shardNum);
			EntityCounter entityCounter;

			// If the shard with the passed shard number exists, increment its
			// count
			// by 1. Otherwise, create a new shard object, set its count to 1,
			// and
			// persist it.
			if (shards != null && !shards.isEmpty()) {
				entityCounter = shards.get(0);
				entityCounter.setCount(entityCounter.getCount() + 1);
			} else {
				entityCounter = new EntityCounter();
				entityCounter.setShardNumber(shardNum);
				entityCounter.setCount(1);
			}

			pm.makePersistent(entityCounter);
		} finally {
			pm.close();
		}
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
