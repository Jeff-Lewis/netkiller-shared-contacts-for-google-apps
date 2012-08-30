package com.metacube.ipathshala.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.dao.UserContactDao;
import com.metacube.ipathshala.entity.UserContact;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.util.AppLogger;

@Service
public class UserContactService extends AbstractService {

	private static final AppLogger log = AppLogger
			.getLogger(UserContactService.class);

	@Autowired
	private UserContactDao userContactDao;

	@Autowired
	private KeyListService keyListService;

	public EntityMetaData getEntityMetaData() {
		return entityMetaData;
	}

	public void setEntityMetaData(EntityMetaData entityMetaData) {
		this.entityMetaData = entityMetaData;
	}

	@Autowired
	private GlobalFilterSearchService globalFilterSearchService;

	@Autowired
	@Qualifier("UserContactMetaData")
	private EntityMetaData entityMetaData;

	public Collection<UserContact> getAllGlobalFilteredUserContacts(
			DataContext dataContext) throws AppException {
		try {
			Collection<UserContact> userContacts = new ArrayList<UserContact>();
			Collection<Object> searchedObjects = globalFilterSearchService
					.doSearch(dataContext, entityMetaData).getResultObjects();
			for (Object contact : searchedObjects) {
				userContacts.add((UserContact) contact);
			}
			return userContacts;
		} catch (DataAccessException dae) {
			String message = "Unable to retrieve all User contacts";
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public UserContact createUserContact(UserContact userContact)
			throws AppException {
		try {

			super.validate(userContact, entityMetaData,
					globalFilterSearchService, null);

			return userContactDao.create(userContact);
		} catch (DataAccessException dae) {
			String message = "Unable to create userContact:" + userContact;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public UserContact updateUserContact(UserContact userContacts)
			throws AppException {
		try {

			validate(userContacts, entityMetaData, globalFilterSearchService,
					null);
			UserContact currentUserContact = userContactDao
					.update(userContacts);
			return currentUserContact;

		} catch (DataAccessException dae) {
			String message = "Unable to update userContact:" + userContacts;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public void deleteUserContact(UserContact userContact,
			DataContext dataContext) throws AppException {
		log.debug("Calling delete User Contact for user contact key: "
				+ userContact.getKey());
		try {

			userContactDao.remove(userContact.getKey());
		} catch (DataAccessException dae) {
			String message = "Unable to delete user Contact:" + userContact;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Collection<UserContact> getByKeys(List<Key> userContactsKeyList)
			throws AppException {
		try {
			return keyListService.getByKeys(userContactsKeyList,
					UserContact.class);
		} catch (DataAccessException dae) {
			String message = "Unable to retrieve user contact fronm given key list";
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Object getById(Key key) {
		return userContactDao.get(key);
	}

}
