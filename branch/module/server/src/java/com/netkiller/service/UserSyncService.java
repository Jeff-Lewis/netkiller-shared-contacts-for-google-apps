package com.netkiller.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.netkiller.core.AppException;
import com.netkiller.dao.EntityCounterDao;
import com.netkiller.dao.UserSyncDao;
import com.netkiller.entity.Contact;
import com.netkiller.entity.EntityCounter;
import com.netkiller.entity.UserContact;
import com.netkiller.entity.UserSync;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.manager.EntityCounterManager;
import com.netkiller.util.AppLogger;
import com.netkiller.util.CommonWebUtil;

@Service
public class UserSyncService extends AbstractService {
	private static final AppLogger log = AppLogger
			.getLogger(UserSyncService.class);

	@Autowired
	@Qualifier("UserSyncMetaData")
	private EntityMetaData entityMetaData;

	@Autowired
	private EntityCounterDao entityCounterDao;

	@Autowired
	private EntityCounterManager entityCounterManager;

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
			userSync = userSyncDao.create(userSync);
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			EntityCounter entityCounter = entityCounterDao.getByEntityName(
					UserSync.class.getSimpleName(),
					CommonWebUtil.getDomain(user.getEmail()));
			if (entityCounter == null) {
				entityCounter = new EntityCounter();
				entityCounter.setCount(1);
				entityCounter.setEntityName(UserSync.class.getSimpleName());
				entityCounter
						.setDomain(CommonWebUtil.getDomain(user.getEmail()));
				entityCounter = entityCounterManager.create(entityCounter);

			} else {
				int count = entityCounter.getCount();
				count++;
				entityCounter.setCount(count);
				try {
					entityCounter = (EntityCounter) BeanUtils
							.cloneBean(entityCounter);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				entityCounterManager.update(entityCounter);
			}
			return userSync;
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
