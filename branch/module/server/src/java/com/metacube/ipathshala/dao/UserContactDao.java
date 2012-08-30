package com.metacube.ipathshala.dao;

import java.util.Collection;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.entity.UserContact;

public interface UserContactDao {

	public UserContact create(UserContact object);

	public UserContact get(Object id);

	public Collection<UserContact> getByKeys(List<Key> userContactsKeyList);

	public UserContact update(UserContact student);

	public void remove(Object id);

}
