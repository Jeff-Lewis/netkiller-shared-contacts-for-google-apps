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
public class EmployeeDao extends AbstractDao<Employee> {

	
	@Autowired
	public EmployeeDao(PersistenceManagerFactory persistenceManagerFactory){
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.metacube.ipathshala.dao.AbstractDao#create(java.lang.Object)
	 */
	@Override
	public Employee create(Employee object) {

		return super.create(object);
	}

	public void remove(Object id) {

		super.remove(Employee.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.metacube.ipathshala.dao.AbstractDao#get(java.lang.Class,
	 * java.lang.Object)
	 */
	public Employee get(Object id) {

		return super.get(Employee.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.metacube.ipathshala.dao.AbstractDao#update(java.lang.Object)
	 */
	@Override
	public Employee update(Employee object) {

		return super.update(object);
	}

}
