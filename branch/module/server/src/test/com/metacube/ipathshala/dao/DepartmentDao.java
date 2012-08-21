package com.metacube.ipathshala.dao;

import javax.jdo.PersistenceManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DepartmentDao extends AbstractDao<Department> {

	@Autowired
	public DepartmentDao(PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.metacube.ipathshala.dao.AbstractDao#create(java.lang.Object)
	 */
	@Override
	public Department create(Department object) {
		// TODO Auto-generated method stub
		return super.create(object);
	}

	public void remove(Object id) {
		// TODO Auto-generated method stub
		super.remove(Department.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.metacube.ipathshala.dao.AbstractDao#get(java.lang.Class,
	 * java.lang.Object)
	 */

	public Department get(Object id) {
		// TODO Auto-generated method stub
		return super.get(Department.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.metacube.ipathshala.dao.AbstractDao#update(java.lang.Object)
	 */
	@Override
	public Department update(Department object) {
		// TODO Auto-generated method stub
		return super.update(object);
	}

}
