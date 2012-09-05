package com.netkiller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.netkiller.FilterInfo;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.DomainGroup;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.search.SearchResult;
import com.netkiller.service.DomainGroupService;

@Component
public class DomainGroupManager extends AbstractManager implements
		EntityManager {

	@Autowired
	private DomainGroupService domainGroupService;

	@Override
	public EntityMetaData getEntityMetaData() {
		return domainGroupService.getEntityMetaData();
	}

	@Override
	public SearchResult doSearch(FilterInfo filterInfo, DataContext dataContext)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getById(Key key) throws AppException {
		return domainGroupService.getById(key);
	}

	public DomainGroup createDomainGroup(DomainGroup domainGroup)
			throws AppException {
		return domainGroupService.createDomainGroup(domainGroup);
	}

	public DomainGroup updateDomainGroup(DomainGroup domainGroup)
			throws AppException {
		return domainGroupService.updateDomainGroup(domainGroup);
	}

	public void deleteDomainGroup(DomainGroup domainGroup) throws AppException {
		domainGroupService.deleteDomainGroup(domainGroup);
	}

	public DomainGroup getDomainGroupByDomainName(String domainName) {
		return domainGroupService.getDomainGroupByDomainName(domainName);
	}

	@Override
	public Object restoreObject(Object object) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

}
