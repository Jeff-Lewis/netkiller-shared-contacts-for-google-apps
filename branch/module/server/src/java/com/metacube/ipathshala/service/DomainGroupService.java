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
import com.metacube.ipathshala.dao.DomainGroupDao;
import com.metacube.ipathshala.entity.DomainGroup;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.util.AppLogger;

@Service
public class DomainGroupService extends AbstractService {
	private static final AppLogger log = AppLogger
			.getLogger(DomainGroupService.class);

	@Autowired
	@Qualifier("DomainGroupMetaData")
	private EntityMetaData entityMetaData;

	public EntityMetaData getEntityMetaData() {
		return entityMetaData;
	}

	@Autowired
	private DomainGroupDao domainGroupDao;

	@Autowired
	private GlobalFilterSearchService globalFilterSearchService;

	public DomainGroup getById(Object id) {
		return domainGroupDao.get(id);
	}

	public DomainGroup createDomainGroup(DomainGroup domainGroup)
			throws AppException {
		try {

			super.validate(domainGroup, entityMetaData,
					globalFilterSearchService, null);
			return domainGroupDao.create(domainGroup);
		} catch (DataAccessException dae) {
			String message = "Unable to create Domain Group:" + domainGroup;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public DomainGroup updateDomainGroup(DomainGroup domainGroup)
			throws AppException {
		try {

			validate(domainGroup, entityMetaData, globalFilterSearchService,
					null);
			return domainGroupDao.update(domainGroup);
		} catch (DataAccessException dae) {
			String message = "Unable to create Domain Group:" + domainGroup;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public void deleteDomainGroup(DomainGroup domainGroup) throws AppException {
		log.debug("Calling delete DomainGroup for domainGroup id: "
				+ domainGroup.getKey());
		try {

			domainGroupDao.remove(domainGroup.getKey());
			// userService.deleteUser(userId);
		} catch (DataAccessException dae) {
			String message = "Unable to delete domainGroup:" + domainGroup;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	@Autowired
	private KeyListService keyListService;

	public Map<Key, DomainGroup> getByKeys(List<Key> valueKeyList)
			throws AppException {
		List<Key> refinedDomainGroupKeyList = new ArrayList<Key>();
		for (Iterator<Key> valueKeyListIterator = valueKeyList.iterator(); valueKeyListIterator
				.hasNext();) {
			Key currentKey = (Key) valueKeyListIterator.next();
			if (!(refinedDomainGroupKeyList.contains(currentKey))) {
				refinedDomainGroupKeyList.add(currentKey);
			}
		}
		Map<Key, DomainGroup> keyDomainGroupMap = new HashMap<Key, DomainGroup>();
		if (refinedDomainGroupKeyList != null
				&& refinedDomainGroupKeyList.size() != 0) {
			Collection<DomainGroup> collection = keyListService.getByKeys(
					refinedDomainGroupKeyList, DomainGroup.class);
			List<DomainGroup> refinedMyClasss = (List<DomainGroup>) collection;
			// refinedMyClasss = populateRelationShipFields(refinedMyClasss);
			for (Iterator<DomainGroup> iterator = refinedMyClasss.iterator(); iterator
					.hasNext();) {
				DomainGroup currentDomainGroup = (DomainGroup) iterator.next();
				keyDomainGroupMap.put(currentDomainGroup.getKey(),
						currentDomainGroup);
			}
			return keyDomainGroupMap;
		}
		return null;
	}

	public DomainGroup getDomainGroupByDomainName(String domainName) {
		return domainGroupDao.getDomainGroupByDomainName(domainName);
	}

}
