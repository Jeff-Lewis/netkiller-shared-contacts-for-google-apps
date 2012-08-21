package com.metacube.ipathshala.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.entity.UserSync;

public interface UserSyncDao {

	public UserSync create(UserSync userSync);

	public UserSync get(Object id);

	public Collection<UserSync> getByKeys(List<Key> keyList);

	public UserSync update(UserSync userSync);

	public void remove(Object id);

	public UserSync getUserSyncByEmailAndDate(String userEmail, Date date);
}
