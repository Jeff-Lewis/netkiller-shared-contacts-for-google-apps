package com.metacube.ipathshala.dao;

import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.entity.ConnectContact;

public interface ConnectContactDao {

	public ConnectContact create(ConnectContact object);

	public ConnectContact get(Key key);

	public List<ConnectContact> getByUrl(String url);
	
	public ConnectContact update(ConnectContact object);

	public void remove(Key key);
}
