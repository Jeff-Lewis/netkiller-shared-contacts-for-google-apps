

package com.metacube.ipathshala.dao.impl;

import java.util.Collection;
import java.util.Iterator;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.dao.AbstractDao;
import com.metacube.ipathshala.dao.CacheSettingDao;
import com.metacube.ipathshala.entity.CacheSetting;
import com.metacube.ipathshala.entity.ClassSubjectTeacher;

/**
 * DAO containing object access methods specific to CacheSetting entity.
 * 
 * <p>
 * <b>Note:</b> This implementing class would throw unchecked spring exception
 * <code>DataAccessException</code> which should be handled by the caller of
 * this DAO appropriately.
 * </p>
 * 
 * @author sabir
 * 
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class CacheSettingDaoImpl extends AbstractDao<CacheSetting> implements
		CacheSettingDao {

	@Autowired
	public CacheSettingDaoImpl(
			PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public CacheSetting create(CacheSetting cacheSetting) {
		return super.create(cacheSetting);
	}

	@Override
	@Transactional(readOnly = true)
	public CacheSetting get(Object id) {
		return super.get(CacheSetting.class, id);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<CacheSetting> getAll() {
		return super.getAll(CacheSetting.class);
	}

	@Override
	public void remove(Object id) {
		super.remove(CacheSetting.class, id);
	}

	@Override
	public CacheSetting update(CacheSetting cacheSetting) {
		return super.update(cacheSetting);
	}

	
}
