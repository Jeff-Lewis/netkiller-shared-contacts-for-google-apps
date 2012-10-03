package com.netkiller.dao.impl;

import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netkiller.dao.AbstractDao;
import com.netkiller.dao.SetDao;
import com.netkiller.entity.Set;

/**
 * @author dhruvsharma
 * 
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class SetDaoImpl extends AbstractDao<Set> implements SetDao {

	@Autowired
	public SetDaoImpl(PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Set create(Set object) {
		return super.create(object);
	}

	@Override
	@Transactional(readOnly = true)
	public Set get(Object id) {
		return super.get(Set.class, id);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Set> getAll() {
		return super.getAll(Set.class);
	}

	@Override
	public void remove(Object id) {
		super.remove(Set.class, id);
	}

	@Override
	public Set update(Set object) {
		return super.update(object);
	}

	@Transactional(readOnly = true)
	public Collection<Set> getBySetName(String setname) {
		PersistenceManager pm = null;
		try {
			pm = getPersistenceManager();
			Query query = pm.newQuery(Set.class);
			query.setFilter("setName == setname");
			query.declareParameters("String setname");
			return pm.detachCopyAll((Collection<Set>) query.execute(setname));
		} finally {
			releasePersistenceManager(pm);
		}
	}

}
