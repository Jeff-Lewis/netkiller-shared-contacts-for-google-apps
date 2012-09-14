package com.netkiller.dao;

import java.util.Collection;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.netkiller.entity.EntityCounter;

public interface EntityCounterDao {

	public EntityCounter create(EntityCounter object);

	public EntityCounter get(Object id);

	public Collection<EntityCounter> getAll();

	public Collection<EntityCounter> getByKeys(List<Key> EntityCounterKeyList);

	public EntityCounter update(EntityCounter entityCounter);

	public void remove(Object id);

	public EntityCounter getByEntityName(String entityname);
}
