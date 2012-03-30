/**
 *
 */
package com.netkiller.dao;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.jdo.JdoTemplate;
import org.springframework.orm.jdo.support.JdoDaoSupport;



/**
 * Abstract DAO class containing common DAO subset implementation.
 * 
 * @author vnarang
 */
public abstract class AbstractDao<E> extends JdoDaoSupport {
	
	protected final Logger log = Logger.getLogger(getClass().getName());


	/**
	 * Create an object in the persistence storage.
	 * 
	 * @param object
	 *            to persist.
	 * @return persisted object.
	 */
	public E create(E object) {
		//log.debug("Create Method started for: " + object);
		E newEntity = null;
		/*
		 * Persist the new entry in store.
		 */
		//setSystemProperties(object, (Class<E>) object.getClass());
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
		log.log(Level.INFO,"Create method started for all entities in collection.");
	/*	for(Object obj : entities)
			setSystemProperties(obj, (Class<E>)obj.getClass());*/
		return getJdoTemplate().makePersistentAll(entities);
	}

	/**
	 * Remove an object from the persistence storage, Big Table in this case.
	 * 
	 * @param object to remove.
	 */
	public void remove(Class<E> type, Object id) {
		log.log(Level.INFO,"Remove Method started");
		JdoTemplate template = getJdoTemplate();
		Object object = template.getObjectById(type, id);
		if (object == null) {
			log.log(Level.WARNING,"Unable to delete" + type + "of id:" + id + ", unable to find it.");
		} else {
			template.deletePersistent(object);
		}

		log.log(Level.INFO,"Remove Method ended");
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
		log.log(Level.INFO,"Get Method started for: " + id);
		PersistenceManager pm = null;
		try {
			pm = getPersistenceManager();
			Object oid = pm.newObjectIdInstance(type, id);

			Object entity = pm.getObjectById(oid);

			log.log(Level.INFO,"Get Method ended for: " + id);
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
		log.log(Level.INFO,"Update Method started for: " + object);

		E updatedEntity = null;
		/*
		 * Update the existing entry in the store, or will persist if does not
		 * exist.
		 */
		//setSystemProperties(object, (Class<E>) object.getClass());
		updatedEntity = getJdoTemplate().makePersistent(object);
		log.log(Level.INFO,"Update Method ended for: " + object);

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
		log.log(Level.INFO,"getAll Method started");
		PersistenceManager pm = null;
		try {
			pm = getPersistenceManager();
			Query query = pm.newQuery(type);
			log.log(Level.INFO,"getAll Method ended");
			return pm.detachCopyAll((Collection<E>) query.execute());
		} finally {
			releasePersistenceManager(pm);
		}
	}	
}