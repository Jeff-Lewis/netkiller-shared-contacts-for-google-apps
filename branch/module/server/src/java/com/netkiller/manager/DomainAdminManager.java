package com.netkiller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.netkiller.FilterInfo;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.DomainAdmin;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.search.SearchResult;
import com.netkiller.service.DomainAdminService;

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
