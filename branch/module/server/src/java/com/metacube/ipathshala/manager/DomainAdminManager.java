package com.metacube.ipathshala.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.FilterInfo;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.entity.DomainAdmin;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.search.SearchResult;
import com.metacube.ipathshala.service.DomainAdminService;

@Component
public class DomainAdminManager extends AbstractManager implements
		EntityManager {

	@Autowired
	private DomainAdminService domainAdminService;

	@Override
	public EntityMetaData getEntityMetaData() {
		return domainAdminService.getEntityMetaData();
	}

	@Override
	public SearchResult doSearch(FilterInfo filterInfo, DataContext dataContext)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getById(Key key) throws AppException {
		return domainAdminService.getById(key);
	}

	@Override
	public Object restoreObject(Object object) throws AppException {
		return domainAdminService.restoreEntity(object);
	}

	public DomainAdmin createDomainAdmin(DomainAdmin domainAdmin)
			throws AppException {
		return domainAdminService.createDomainAdmin(domainAdmin);
	}

	public DomainAdmin getDomainAdminByDomainName(String domain) {
		return domainAdminService.getDomainAdminByDomainName(domain);
	}

	/*
	 * public DomainAdmin getDomainAdminByDomainName(String domainName) { return
	 * domainAdminService.getDomainAdminByDomainName(domainName); }
	 */
}
