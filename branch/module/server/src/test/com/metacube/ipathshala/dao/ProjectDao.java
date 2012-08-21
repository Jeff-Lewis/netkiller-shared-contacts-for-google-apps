/**
 * 
 */
package com.metacube.ipathshala.dao;

import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author amit
 * 
 */
@Repository
public class ProjectDao extends AbstractDao<Project> {

	@Autowired
	public ProjectDao(PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.metacube.ipathshala.dao.AbstractDao#create(java.lang.Object)
	 */
	@Override
	public Project create(Project object) {
		// TODO Auto-generated method stub
		return super.create(object);
	}

	public void remove(Object id) {
		super.remove(Project.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.metacube.ipathshala.dao.AbstractDao#get(java.lang.Class,
	 * java.lang.Object)
	 */

	public Project get(Object id) {
		// TODO Auto-generated method stub
		return super.get(Project.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.metacube.ipathshala.dao.AbstractDao#update(java.lang.Object)
	 */
	@Override
	public Project update(Project object) {
		// TODO Auto-generated method stub
		return super.update(object);
	}

}
