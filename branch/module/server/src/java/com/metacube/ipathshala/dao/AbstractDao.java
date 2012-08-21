/**
 *
 */
package com.metacube.ipathshala.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jdo.support.JdoDaoSupport;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData;
import com.metacube.ipathshala.entity.metadata.ColumnMetaData.ColumnType;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.entity.metadata.impl.OperationType;
import com.metacube.ipathshala.security.AppUser;
import com.metacube.ipathshala.security.AppUserService;
import com.metacube.ipathshala.security.DomainConfig;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.util.KeyUtil;
import com.metacube.ipathshala.util.LocalizationUtil;
import com.metacube.ipathshala.util.StringUtil;

/**
 * Abstract DAO class containing common DAO subset implementation.
 * 
 * @author vnarang
 */
public abstract class AbstractDao<E> extends JdoDaoSupport {
	
	@Autowired
	private AppUserService appUserService;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private DomainConfig domainConfig;

	protected final AppLogger log = AppLogger.getLogger(getClass());


	/**
	 * Create an object in the persistence storage.
	 * 
	 * @param object
	 *            to persist.
	 * @return persisted object.
	 */
	public E create(E object) {
		log.debug("Create Method started for: " + object);
		E newEntity = null;
		/*
		 * Persist the new entry in store.
		 */
		setSystemProperties(object, (Class<E>) object.getClass());
		newEntity = getJdoTemplate().makePersistent(object);
		return newEntity;

	}
	
	public E createWithoutSystemProperties(E object) {
		log.debug("Create Method started for: " + object);
		E newEntity = null;
		/*
		 * Persist the new entry in store.
		 */
		newEntity = getJdoTemplate().makePersistent(object);
		return newEntity;

	}
	
	
	/**
	 * Create objects at once in the persistence storage.
	 * 
	 * @param entities
	 *            the collection objects to persist.
	 * @return collection of persisted objects.
	 */
	public Collection<E> createAll(Collection<E> entities) {
		log.debug("Create method started for all entities in collection.");
		for(Object obj : entities)
			setSystemProperties(obj, (Class<E>)obj.getClass());
		return getJdoTemplate().makePersistentAll(entities);
	}

	/**
	 * Remove an object from the persistence storage, Big Table in this case.
	 * 
	 * @param object to remove.
	 */
	public void remove(Class<E> type, Object id) {
		log.debug("Remove Method started");
		//JdoTemplate template = getJdoTemplate();
		E object = this.get(type, id);
		if (object == null) {
			log.warn("Unable to delete" + type + "of id:" + id + ", unable to find it.");
		} else {
			
			//template.deletePersistent(object);
			String propertyName = "isDeleted";
			try {
				E newObject = (E) BeanUtils.cloneBean(object);
				Field field = type
						.getDeclaredField(propertyName);
				field.setAccessible(true);
				field.set(newObject,true);
				field.setAccessible(false);
				object  = this.update(newObject);
			} catch (SecurityException e) {
				String message = "Security related Exception";
				log.error(message, e);
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				String message = "Unable to fetch property from currently set Global Filter";
				log.error(message, e);
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				String message = "Illegal Argument Exception";
				log.error(message, e);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				String message = "Illegal Access Exception";
				log.error(message, e);
				e.printStackTrace();
			} catch (InstantiationException e) {
				String message = "Instantiation Exception";
				log.error(message, e);
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				String message = "Invocation Target Exception";
				log.error(message, e);
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				String message = "No Such Method Exception";
				log.error(message, e);
				e.printStackTrace();
			}
			
			
		}

		log.debug("Remove Method ended");
	}

	/**
	 * Get an object of given type and id.
	 * 
	 * @param type
	 *            of object to retrieve.
	 * @param id
	 *            unique identifier of the object.
	 * @return
	 */
	public E get(Class<E> type, Object id) {
		log.debug("Get Method started for: " + id);
		PersistenceManager pm = null;
		try {
			pm = getPersistenceManager();
			Object oid = pm.newObjectIdInstance(type, id);

			Object entity = pm.getObjectById(oid);

			log.debug("Get Method ended for: " + id);
			return pm.detachCopy((E) entity);
		} finally {
			releasePersistenceManager(pm);
		}
	}

	/**
	 * Updates an object in the persistence storage, BigTable in this case.
	 * 
	 * @param object
	 *            to update
	 * @return updated object.
	 */
	public E update(E object) {
		log.debug("Update Method started for: " + object);

		E updatedEntity = null;
		/*
		 * Update the existing entry in the store, or will persist if does not
		 * exist.
		 */
		setSystemProperties(object, (Class<E>) object.getClass());
		updatedEntity = getPersistenceManager().makePersistent(object);
		log.debug("Update Method ended for: " + object);

		return updatedEntity;
	}

	/**
	 * Get all the objects of a required type.
	 * 
	 * @param type
	 *            entity type
	 * @return collection of all objects of a given type.
	 */
	public Collection<E> getAll(Class<E> type) {
		log.debug("getAll Method started");
		PersistenceManager pm = null;
		try {
			pm = getPersistenceManager();
			Query query = pm.newQuery(type);
			log.debug("getAll Method ended");
			return pm.detachCopyAll((Collection<E>) query.execute());
		} finally {
			releasePersistenceManager(pm);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Collection<E> getByKeys (Class<E> type, Collection<Key> keyList) {
		PersistenceManager pm = null;
		if (keyList == null || keyList.size() == 0) {
			return null;
		}
		for(Iterator<Key> iterator = keyList.iterator();iterator.hasNext();) {
			Key key = iterator.next();
			if(key==null) {
				iterator.remove();
			}
		}
		try {
			pm = getPersistenceManager();
			Query query = pm.newQuery(type);
			query.declareImports("import com.google.appengine.api.datastore.Key");
			pm = getPersistenceManager();
			Collection<E> classList = new ArrayList<E>();
			int i=0;
			for (Iterator<Key> valueKeyListIterator = keyList.iterator(); valueKeyListIterator.hasNext();) {
				query.setFilter("key == currentkey");
				query.declareParameters("Key currentkey"+i);
				Key key= valueKeyListIterator.next();
				i++;
			}
			classList.addAll(pm.detachCopyAll((Collection<E>) query.executeWithArray(keyList)));
			return classList;
		} finally {
			releasePersistenceManager(pm);
		}
		

	}
	
	public void setSystemProperties(Object object, Class<E> type) {
		try {
			String entityName = type.getSimpleName();
		/*	entityName = entityName.substring(0, 1).toLowerCase()
					+ entityName.substring(1, entityName.length());*/
			EntityMetaData entityMetaData = (EntityMetaData) applicationContext
					.getBean(entityName + "MetaData");
			ColumnMetaData columnMetaData;
			AppUser currentUser = appUserService.getCurrentUser();
			if (currentUser == null) {
				currentUser = new AppUser(
						StringUtil.getUserNameOutOfEmail(domainConfig
								.getDomainAdminEmail()),
						domainConfig.getDomainAdminEmail(), null, null,
						StringUtil.getUserNameOutOfEmail(domainConfig
								.getDomainAdminEmail()),null);
			}

			String currentUserId = currentUser.getUserId();
			Date currentDate = new Date();
			LocalizationUtil.formatDate(currentDate, null);

			for (String propertyName : entityMetaData
					.getSystemPropertiesColumnMetaDataMap().keySet()) {

				columnMetaData = entityMetaData.getColumnMetaData(propertyName);
				Key key = KeyUtil.geyKeyFromObject(object);
				if (key != null) {
					if (entityMetaData.getSystemPropertiesColumnMetaDataMap()
							.get(propertyName).getOpType()
							.equals(OperationType.MODIFY)) {
						if (columnMetaData.getColumnType() == ColumnType.STRING)
							BeanUtils.setProperty(object, propertyName,
									currentUserId);
						else if (columnMetaData.getColumnType() == ColumnType.DATE)
							BeanUtils.setProperty(object, propertyName,
									currentDate);
					} else {
						Object entityFromDataStore = get(type, (Object) key);
						Object propertyValue = null;
						try {
							Field field = entityMetaData.getEntityClass()
									.getDeclaredField(propertyName);
							field.setAccessible(true);
							propertyValue = field.get(entityFromDataStore);
							field.setAccessible(false);
						} catch (SecurityException e) {
							String message = "Security related Exception";
							log.error(message, e);
							e.printStackTrace();
						} catch (NoSuchFieldException e) {
							String message = "Unable to fetch property from currently set Global Filter";
							log.error(message, e);
							e.printStackTrace();
						}
						BeanUtils.setProperty(object, propertyName,
								propertyValue);
					}
				} else {

					if (columnMetaData.getColumnType() == ColumnType.STRING)
						BeanUtils.setProperty(object, propertyName,
								currentUserId);
					else if (columnMetaData.getColumnType() == ColumnType.DATE)
						BeanUtils
								.setProperty(object, propertyName, currentDate);

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
}