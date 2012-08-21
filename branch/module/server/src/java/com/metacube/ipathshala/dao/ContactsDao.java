package com.metacube.ipathshala.dao;

import java.util.Collection;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.entity.Contacts;

public interface ContactsDao {

	public Contacts create(Contacts object);

	public Contacts get(Object id);

	public Collection<Contacts> getAll();

	public Collection<Contacts> getByKeys(List<Key> ContactsKeyList);

	public Contacts update(Contacts student);

	public void remove(Object id);

}
