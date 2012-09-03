package com.metacube.ipathshala.dao.impl;

import java.util.ArrayList;
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
import com.metacube.ipathshala.dao.AbstractDao;
import com.metacube.ipathshala.dao.ContactsDao;
import com.metacube.ipathshala.entity.Contact;
import com.metacube.ipathshala.entity.UserSync;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class ContactsDaoImpl extends AbstractDao<Contact> implements
		ContactsDao {

	@Autowired
	public ContactsDaoImpl(PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Contact create(Contact object) {
		return super.create(object);
	}

	@Override
	@Transactional(readOnly = true)
	public Contact get(Object id) {
		return super.get(Contact.class, id);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Contact> getAll() {
		return super.getAll(Contact.class);
	}

	@Override
	public Collection<Contact> getByKeys(List<Key> ContactsKeyList) {
		return super.getByKeys(Contact.class, ContactsKeyList);
	}

	@Override
	public Contact update(Contact contact) {
		return super.update(contact);
	}

	@Override
	public void remove(Object id) {
		super.remove(Contact.class, id);

	}


}
