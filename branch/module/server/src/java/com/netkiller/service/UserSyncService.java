package com.netkiller.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.netkiller.core.AppException;
import com.netkiller.dao.UserSyncDao;
import com.netkiller.entity.UserSync;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.util.AppLogger;

@Service
public class UserSyncService extends AbstractService {
	private static final AppLogger log = AppLogger
			.getLogger(UserSyncService.class);

	@Autowired
	@Qualifier("UserSyncMetaData")
	private EntityMetaData entityMetaData;

	public EntityMetaData getEntityMetaData() {
		return entityMetaData;
	}

	@Autowired
	private UserSyncDao userSyncDao;

	@Autowired
	private GlobalFilterSearchService globalFilterSearchService;

	public UserSync getById(Object id) {
		return userSyncDao.get(id);
	}

	public UserSync createUserSync(UserSync userSync) throws AppException {
		try {

			super.validate(userSync, entityMetaData, globalFilterSearchService,
					null);
			return userSyncDao.create(userSync);
		} catch (DataAccessException dae) {
			String message = "Unable to create User Sync:" + userSync;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public UserSync updateUserSync(UserSync userSync) throws AppException {
		try {

			validate(userSync, entityMetaData, globalFilterSearchService, null);
			return userSyncDao.update(userSync);
		} catch (DataAccessException dae) {
			String message = "Unable to create User Sync:" + userSync;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public void deleteUserSync(UserSync userSync) throws AppException {
		log.debug("Calling delete UserSync for userSync id: "
				+ userSync.getKey());
		try {

			userSyncDao.remove(userSync.getKey());
			// userService.deleteUser(userId);
		} catch (DataAccessException dae) {
			String message = "Unable to delete userSync:" + userSync;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	@Autowired
	private KeyListService keyListService;

	public Map<Key, UserSync> getByKeys(List<Key> valueKeyList)
			throws AppException {
		List<Key> refinedUserSyncKeyList = new ArrayList<Key>();
		for (Iterator<Key> valueKeyListIterator = valueKeyList.iterator(); valueKeyListIterator
				.hasNext();) {
			Key currentKey = (Key) valueKeyListIterator.next();
			if (!(refinedUserSyncKeyList.contains(currentKey))) {
				refinedUserSyncKeyList.add(currentKey);
			}
		}
		Map<Key, UserSync> keyUserSyncMap = new HashMap<Key, UserSync>();
		if (refinedUserSyncKeyList != null
				&& refinedUserSyncKeyList.size() != 0) {
			Collection<UserSync> collection = keyListService.getByKeys(
					refinedUserSyncKeyList, UserSync.class);
			List<UserSync> refinedUserSync = (List<UserSync>) collection;
			// refinedMyClasss = populateRelationShipFields(refinedMyClasss);
			for (Iterator<UserSync> iterator = refinedUserSync.iterator(); iterator
					.hasNext();) {
				UserSync currentUserSync = (UserSync) iterator.next();
				keyUserSyncMap.put(currentUserSync.getKey(), currentUserSync);
			}
			return keyUserSyncMap;
		}
		return null;
	}

	public UserSync getUserSyncByEmailAndDate(String userEmail, Date date)
			throws AppException {
		try {
			return userSyncDao.getUserSyncByEmailAndDate(userEmail, date);
		} catch (DataAccessException dae) {
			String message = "Unable to get UserSync by Email :" + userEmail
					+ " and date :" + date;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

}
