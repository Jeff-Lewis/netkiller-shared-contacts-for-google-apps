package com.metacube.ipathshala.manager;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.FilterInfo;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.entity.UserContact;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.search.SearchResult;
import com.metacube.ipathshala.service.UserContactService;

@Component
public class UserContactManager extends AbstractManager implements
		EntityManager {

	@Autowired
	private UserContactService service;

	@Override
	public EntityMetaData getEntityMetaData() {
		return service.getEntityMetaData();
	}

	@Override
	public SearchResult doSearch(FilterInfo filterInfo, DataContext dataContext)
			throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getById(Key key) throws AppException {
		return service.getById(key);
	}

	@Override
	public Object restoreObject(Object object) throws AppException {
		return null;
	}

	public Collection<UserContact> getAllGlobalFilteredUserContacts(
			DataContext dataContext) throws AppException {
		return service.getAllGlobalFilteredUserContacts(dataContext);
	}

	public UserContact createUserContact(UserContact userContact)
			throws AppException {
		return service.createUserContact(userContact);
	}

	public UserContact updateUserContact(UserContact userContacts)
			throws AppException {
		return service.updateUserContact(userContacts);
	}

	public void deleteUserContact(UserContact userContact,
			DataContext dataContext) throws AppException {
		service.deleteUserContact(userContact, dataContext);
	}

	public Collection<UserContact> getByKeys(List<Key> userContactsKeyList)
			throws AppException {
		return service.getByKeys(userContactsKeyList);
	}

}
