package com.netkiller.dao;

import java.util.Collection;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.netkiller.entity.DomainGroup;

public interface DomainGroupDao {
	public DomainGroup create(DomainGroup domainGroup);

	public DomainGroup get(Object id);

	public Collection<DomainGroup> getByKeys(List<Key> keyList);

	public DomainGroup update(DomainGroup domainGroup);

	public void remove(Object id);
	
	public DomainGroup getDomainGroupByDomainName(String domain);

}
