package com.metacube.ipathshala.dao;

import java.util.Collection;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.entity.Contact;

public interface ContactsDao {

	public Contact create(Contact object);

	public Contact get(Object id);

	public Collection<Contact> getAll();

	public Collection<Contact> getByKeys(List<Key> ContactsKeyList);

	public Contact update(Contact student);

	public void remove(Object id);

}
