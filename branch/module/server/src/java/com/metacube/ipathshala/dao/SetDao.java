package com.metacube.ipathshala.dao;

import java.util.Collection;

import com.metacube.ipathshala.entity.Set;

/**
 * @author dhruvsharma
 * 
 */
public interface SetDao {

	public Set create(Set object);

	public Set get(Object id);

	public Collection<Set> getBySetName(String setName);

	public Collection<Set> getAll();

	public Set update(Set set);

	public void remove(Object id);

}
