package com.metacube.ipathshala.dao.impl;

import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.dao.AbstractDao;
import com.metacube.ipathshala.dao.ContactsDao;
import com.metacube.ipathshala.entity.Contacts;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class ContactsDaoImpl extends AbstractDao<Contacts> implements
		ContactsDao {

	@Autowired
	public ContactsDaoImpl(PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Contacts create(Contacts object) {
		return super.create(object);
	}

	@Override
	@Transactional(readOnly = true)
	public Contacts get(Object id) {
		return super.get(Contacts.class, id);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Contacts> getAll() {
		return super.getAll(Contacts.class);
	}

	@Override
	public Collection<Contacts> getByKeys(List<Key> ContactsKeyList) {
		return super.getByKeys(Contacts.class, ContactsKeyList);
	}

	@Override
	public Contacts update(Contacts contact) {
		return super.update(contact);
	}

	@Override
	public void remove(Object id) {
		super.remove(Contacts.class, id);

	}

}
