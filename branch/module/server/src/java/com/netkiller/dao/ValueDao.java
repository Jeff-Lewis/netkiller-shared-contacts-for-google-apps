package com.netkiller.dao;

import java.util.Collection;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.netkiller.entity.Value;

/**
 * @author dhruvsharma
 * 
 */
public interface ValueDao {
	public Value create(Value object);

	public Value get(Object id);

	public Collection<Value> getAll();

	public Collection<Value> getBySetKey(Key setKey, String sortColumn);
	
	public Collection<Value> getBySetKeyandParentValueKey(Key setKey,Key parentValueKey, String sortColumn);

	public Collection<Value> getByKeys(List<Key> valueKeyList);

	public Value update(Value value);

	public void remove(Object id);

}
