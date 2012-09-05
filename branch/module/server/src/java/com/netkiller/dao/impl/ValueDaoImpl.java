package com.netkiller.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
import com.netkiller.dao.ValueDao;
import com.netkiller.entity.Value;

/**
 * @author dhruvsharma
 * 
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class ValueDaoImpl extends AbstractDao<Value> implements ValueDao {

	@Autowired
	public ValueDaoImpl(PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Value create(Value object) {
		return super.create(object);
	}

	@Override
	@Transactional(readOnly = true)
	public Value get(Object id) {
		return super.get(Value.class, id);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Value> getAll() {
		return super.getAll(Value.class);
	}

	@Override
	public void remove(Object id) {
		super.remove(Value.class, id);
	}

	@Override
	public Value update(Value object) {
		return super.update(object);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Collection<Value> getBySetKey(Key setkey, String sortColumn) {
		PersistenceManager pm = null;
		try {
			pm = getPersistenceManager();
			Query query = pm.newQuery(Value.class);
			query
					.declareImports("import com.google.appengine.api.datastore.Key");
			query.setFilter("setKey == setkey");
			query.declareParameters("Key setkey");
			query.setOrdering(sortColumn + " " + "asc");
			return pm.detachCopyAll((Collection<Value>) query.execute(setkey));
		} finally {
			releasePersistenceManager(pm);
		}
	}

	@Override
	public Collection<Value> getByKeys(List<Key> valueKeyList) {
		return super.getByKeys(Value.class, valueKeyList);
	}

	@Override
	public Collection<Value> getBySetKeyandParentValueKey(Key setkey, Key parentvalueKey, String sortColumn) {
		PersistenceManager pm = null;
		try {
			pm = getPersistenceManager();
			Query query = pm.newQuery(Value.class);
			query
					.declareImports("import com.google.appengine.api.datastore.Key");
			query.setFilter("setKey == setkey && parentValueKey == parentvalueKey");
			query.declareParameters("Key setkey, Key parentvalueKey");
			query.setOrdering(sortColumn + " " + "asc");
			return pm.detachCopyAll((Collection<Value>) query.execute(setkey,parentvalueKey));
		} finally {
			releasePersistenceManager(pm);
		}
	}
}
