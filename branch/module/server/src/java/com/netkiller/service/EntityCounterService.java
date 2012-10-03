package com.netkiller.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.netkiller.core.AppException;
import com.netkiller.dao.EntityCounterDao;
import com.netkiller.entity.EntityCounter;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.util.AppLogger;

@Service
public class EntityCounterService extends AbstractService {

	private static final AppLogger log = AppLogger
			.getLogger(EntityCounterService.class);

	@Autowired
	@Qualifier("EntityCounterMetaData")
	private EntityMetaData entityMetaData;

	@Autowired
	private EntityCounterDao entityCounterDao;

	@Autowired
	private KeyListService keyListService;

	@Autowired
	private GlobalFilterSearchService globalFilterSearchService;

	public EntityMetaData getEntityMetaData() {
		return entityMetaData;
	}

	public void setEntityMetaData(EntityMetaData entityMetaData) {
		this.entityMetaData = entityMetaData;
	}

	public EntityCounter create(EntityCounter entityCounter)
			throws AppException {
		try {

			super.validate(entityCounter, entityMetaData,
					globalFilterSearchService, null);

			return entityCounterDao.create(entityCounter);
		} catch (DataAccessException dae) {
			String message = "Unable to create EntityCounter:" + entityCounter;
			log.error(message, dae);
			throw new AppException(message, dae);
		}

	}

	public EntityCounter getByEntityName(String entityname,String domain) {
		return entityCounterDao.getByEntityName(entityname,domain);
	}

	public EntityCounter update(EntityCounter entityCounter)
			throws AppException {
		try {
			super.validate(entityCounter, entityMetaData,
					globalFilterSearchService, null);
			return entityCounterDao.update(entityCounter);
		} catch (DataAccessException dae) {
			String message = "Unable to Update EntityCounter:" + entityCounter;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public void delete(EntityCounter entityCounter) throws AppException {
		try {
			entityCounterDao.remove(entityCounter.getKey());
		} catch (DataAccessException dae) {
			String message = "Unable to Delete EntityCounter:" + entityCounter;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public EntityCounter getById(Key key) {
		return entityCounterDao.get(key);
	}

	public Collection<EntityCounter> getByKeys(List<Key> entityCounterKeyList)
			throws AppException {
		try {
			return keyListService.getByKeys(entityCounterKeyList,
					EntityCounter.class);
		} catch (DataAccessException dae) {
			String message = "Unable to retrieve entityCounter from given key list";
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

}
