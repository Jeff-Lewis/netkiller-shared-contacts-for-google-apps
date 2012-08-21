package com.metacube.ipathshala.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.FilterInfo;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.entity.DomainGroup;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.search.SearchResult;
import com.metacube.ipathshala.service.DomainGroupService;

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
