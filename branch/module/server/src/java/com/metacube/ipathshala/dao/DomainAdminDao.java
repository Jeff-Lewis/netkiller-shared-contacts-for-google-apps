package com.metacube.ipathshala.dao;

import java.util.Collection;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.entity.DomainAdmin;

public interface DomainAdminDao {

	public DomainAdmin create(DomainAdmin domainAdmin);

	public DomainAdmin get(Object id);

	public Collection<DomainAdmin> getByKeys(List<Key> keyList);

	public DomainAdmin update(DomainAdmin domainAdmin);

	public void remove(Object id);

	public DomainAdmin getDomainAdminByDomainName(String domain);

	//public DomainAdmin getDomainAdminByDomainName(String domainName);

}
