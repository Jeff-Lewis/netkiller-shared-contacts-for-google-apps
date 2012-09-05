package com.netkiller.dao.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.netkiller.dao.AbstractDao;
import com.netkiller.dao.WorkflowDao;
import com.netkiller.entity.Value;
import com.netkiller.entity.Workflow;

/**
 * @author dhruvsharma
 *
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class WorkflowDaoImpl extends AbstractDao<Workflow> implements WorkflowDao{

	@Autowired
	public WorkflowDaoImpl(PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Workflow create(Workflow object) {
		return super.create(object);
	}

	@Override
	@Transactional(readOnly = true)
	public Workflow get(Object id) {
		return super.get(Workflow.class, id);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Workflow> getAll() {
		return super.getAll(Workflow.class);
	}

	@Override
	public void remove(Object id) {
		super.remove(Workflow.class, id);
	}

	@Override
	public Workflow update(Workflow object) {
		return super.update(object);
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public Workflow getByWorkflowInstanceId(String workflowInstanceId) {
		PersistenceManager pm = null;
		try {
			pm = getPersistenceManager();
			Query query = pm.newQuery(Workflow.class);
			query.setFilter("workflowInstanceId == instanceid");
			query.declareParameters("String instanceid");
			Collection<Workflow> workflowl= pm.detachCopyAll((Collection<Workflow>) query.execute(workflowInstanceId));
		    for (Iterator<Workflow> iterator=workflowl.iterator();iterator.hasNext();) {
		    	return (Workflow)iterator.next();
		    }
		} finally {
			releasePersistenceManager(pm);
		}
		return null;
	}
}
