/**
 * 
 */
package com.netkiller.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.netkiller.entity.Workflow;
import com.netkiller.exception.AppException;
import com.netkiller.service.WorkflowService;

@Component
public class WorkflowManager {
	
	@Autowired
	WorkflowService service;

	/**
	 * @return the service
	 */
	public WorkflowService getService() {
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(WorkflowService service) {
		this.service = service;
	}

	/**
	 * Creates new workflow account.
	 * 
	 * @param workflow
	 * @return
	 * @throws AppException
	 */
	public Workflow createWorkflow(Workflow workflow) throws AppException {
		return service.createWorkflow(workflow);
	}

	/**
	 * Updates existing workflow account.
	 * 
	 * @param workflow
	 * @return
	 * @throws AppException
	 */
	public Workflow updateWorkflow(Workflow workflow) throws AppException {
		return service.updateWorkflow(workflow);
	}

	/**
	 * Delete existing workflow account.
	 * 
	 * @param workflow
	 * @throws AppException
	 */
	public void deleteWorkflow(Workflow workflow) throws AppException {
		service.deleteWorkflow(workflow);
	}

	/**
	 * Get workflow account based on primary key.
	 * 
	 * @param id
	 *            the workflow id to get the match.
	 * @return
	 * @throws AppException
	 */
	public Object getById(Key id) throws AppException {
		return (Object) service.getById(Workflow.class, id);
	}

	/**
	 * Get the list of all workflow account.
	 * 
	 * @return
	 * @throws AppException
	 */
	public Collection<Workflow> getAllWorkflows() throws AppException {
		return service.getAll();
	}

	public Workflow getByWorkflowInstanceId(String instanceid) throws AppException {
		return service.getByWorkflowInstanceId(instanceid);
	}

	
	public void triggerWorkflow(Workflow workflow) {
		service.triggerWorkflow(workflow);
	}

	public void test() {
		service.test();
		
	}
}
