package com.netkiller.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.netkiller.core.AppException;
import com.netkiller.dao.WorkflowDao;
import com.netkiller.entity.Workflow;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.util.AppLogger;
import com.netkiller.workflow.util.WorkflowUtil;

/**
 * @author dhruvsharma
 * 
 */
@Service
public class WorkflowService {

	private static final AppLogger log = AppLogger.getLogger(WorkflowService.class);

	@Autowired
	private WorkflowDao workflowDao;

	@Autowired
	@Qualifier("WorkflowMetaData")
	private EntityMetaData entityMetaData;

	@Autowired
	IPathshalaQueueService workflowQueueService;

	public Collection<Workflow> getAll() throws AppException {
		try {
			return workflowDao.getAll();
		} catch (DataAccessException dae) {
			String message = "Unable to retrieve all locations";
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Workflow getById(Class<Workflow> type, Object id) throws AppException {
		try {
			Workflow workflow = workflowDao.get(id);
			workflow.setContext(WorkflowUtil.convertBlobToWorkflowContext(workflow.getWorkflowContext()));
			return workflow;
		} catch (DataAccessException dae) {
			String message = "Unable to get location by id:" + id;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Workflow createWorkflow(Workflow workflow) throws AppException {
		try {
			// only Context has to be set. Not WorkflowContext which is of type
			// Blob
			workflow.setWorkflowContext(WorkflowUtil.convertWorkflowContextToBlob(workflow.getContext()));
			return workflowDao.create(workflow);
		} catch (DataAccessException dae) {
			String message = "Unable to create workflow:" + workflow;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Workflow updateWorkflow(Workflow workflow) throws AppException {
		try {
			workflow.setWorkflowContext(WorkflowUtil.convertWorkflowContextToBlob(workflow.getContext()));
			return workflowDao.update(workflow);
		}

		catch (DataAccessException dae) {
			String message = "Unable to update workflow:" + workflow;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	/**
	 * Delete location entity from store.
	 * 
	 * @param location
	 */
	public void deleteWorkflow(Workflow workflow) throws AppException {
		log.debug("Calling delete workflow for workflow id: " + workflow.getKey());
		try {
			workflowDao.remove(workflow.getKey());
		} catch (DataAccessException dae) {
			String message = "Unable to delete workflow:" + workflow;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Workflow getByWorkflowInstanceId(String instanceid) throws AppException {
		Workflow workflow = workflowDao.getByWorkflowInstanceId(instanceid);
		workflow.setContext(WorkflowUtil.convertBlobToWorkflowContext(workflow.getWorkflowContext()));
		return workflow;
	}

	/**
	 * @return the entityMetaData
	 */
	public EntityMetaData getEntityMetaData() {
		return entityMetaData;
	}

	public Collection<Object> populateRelationshipFields(List<Object> objectList) throws AppException {
		List<Object> returnObjectList = new ArrayList<Object>();
		for (Iterator<Object> iterator = objectList.iterator(); iterator.hasNext();) {
			Workflow currentWorkflow = (Workflow) iterator.next();
			try {
				currentWorkflow.setContext(WorkflowUtil.convertBlobToWorkflowContext(currentWorkflow
						.getWorkflowContext()));
				returnObjectList.add((Object) currentWorkflow);
			} catch (AppException e) {
				throw new AppException("Cannoot Convert Blob to WorkflowContext", e);
			}
		}
		return returnObjectList;
	}

	public void triggerWorkflow(Workflow workflow) {
		workflowQueueService.triggerWorkflow(workflow.getContext());
	}

}
