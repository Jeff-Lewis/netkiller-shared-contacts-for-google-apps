package com.metacube.ipathshala.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.dao.DomainAdminDao;
import com.metacube.ipathshala.entity.DomainAdmin;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.util.AppLogger;

@Service
public class DomainAdminService extends AbstractService {
	private static final AppLogger log = AppLogger
			.getLogger(DomainAdminService.class);

	@Autowired
	@Qualifier("DomainAdminMetaData")
	private EntityMetaData entityMetaData;

	public EntityMetaData getEntityMetaData() {
		return entityMetaData;
	}

	@Autowired
	private DomainAdminDao domainAdminDao;

	@Autowired
	private GlobalFilterSearchService globalFilterSearchService;

	public DomainAdmin getById(Object id) {
		return domainAdminDao.get(id);
	}

	public DomainAdmin createDomainAdmin(DomainAdmin domainAdmin)
			throws AppException {
		try {

			super.validate(domainAdmin, entityMetaData,
					globalFilterSearchService, null);
			return domainAdminDao.create(domainAdmin);
		} catch (DataAccessException dae) {
			String message = "Unable to create Domain Admin:" + domainAdmin;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public DomainAdmin updateDomainAdmin(DomainAdmin domainAdmin)
			throws AppException {
		try {

			validate(domainAdmin, entityMetaData, globalFilterSearchService,
					null);
			return domainAdminDao.update(domainAdmin);
		} catch (DataAccessException dae) {
			String message = "Unable to create Domain Admin:" + domainAdmin;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public void deleteDomainAdmin(DomainAdmin domainAdmin) throws AppException {
		log.debug("Calling delete domainAdmin for domainAdmin id: "
				+ domainAdmin.getKey());
		try {

			domainAdminDao.remove(domainAdmin.getKey());
			// userService.deleteUser(userId);
		} catch (DataAccessException dae) {
			String message = "Unable to delete domainAdmin:" + domainAdmin;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	@Autowired
	private KeyListService keyListService;

	public Map<Key, DomainAdmin> getByKeys(List<Key> valueKeyList)
			throws AppException {
		List<Key> refinedDomainAdminKeyList = new ArrayList<Key>();
		for (Iterator<Key> valueKeyListIterator = valueKeyList.iterator(); valueKeyListIterator
				.hasNext();) {
			Key currentKey = (Key) valueKeyListIterator.next();
			if (!(refinedDomainAdminKeyList.contains(currentKey))) {
				refinedDomainAdminKeyList.add(currentKey);
			}
		}
		Map<Key, DomainAdmin> keyStudentMap = new HashMap<Key, DomainAdmin>();
		if (refinedDomainAdminKeyList != null
				&& refinedDomainAdminKeyList.size() != 0) {
			Collection<DomainAdmin> collection = keyListService.getByKeys(
					refinedDomainAdminKeyList, DomainAdmin.class);
			List<DomainAdmin> refinedMyClasss = (List<DomainAdmin>) collection;
			// refinedMyClasss = populateRelationShipFields(refinedMyClasss);
			for (Iterator<DomainAdmin> iterator = refinedMyClasss.iterator(); iterator
					.hasNext();) {
				DomainAdmin currentDomainAdmin = (DomainAdmin) iterator.next();
				keyStudentMap.put(currentDomainAdmin.getKey(),
						currentDomainAdmin);
			}
			return keyStudentMap;
		}
		return null;
	}

	public DomainAdmin getDomainAdminByDomainName(String domain) {
		return domainAdminDao.getDomainAdminByDomainName(domain);
	}

	public Object restoreEntity(Object object) {
		/*
		 * DomainAdmin domainAdmin = (DomainAdmin) object;
		 * DomainAdmin.setIsDeleted(false);
		 */
		// return studentDao.update(student);
		return null;
	}

	/*public DomainAdmin getDomainAdminByDomainName(String domainName) {
		return domainAdminDao.getDomainAdminByDomainName(domainName);
	}*/
}
