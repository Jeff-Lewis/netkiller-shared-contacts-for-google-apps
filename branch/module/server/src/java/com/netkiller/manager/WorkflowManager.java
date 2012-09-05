package com.netkiller.manager;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.netkiller.FilterInfo;
import com.netkiller.GridRequest;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.Workflow;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.search.SearchResult;
import com.netkiller.service.WorkflowService;

/**
 * @author dhruvsharma
 *
 */
@Component
public class WorkflowManager implements EntityManager {

	@Autowired
	WorkflowService service;

	@Autowired
	SearchManager searchManager;

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
	@Override
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

	public SearchResult doSearch(GridRequest gridRequest) throws AppException {
		SearchResult searchResult = searchManager.doSearch(Workflow.class, service.getEntityMetaData(), gridRequest, null);
		List<Object> objectList = searchResult.getResultObjects();
		List<Object> workflowList = (List<Object>) service.populateRelationshipFields(objectList);
		searchResult.setResultObjects(workflowList);
		return searchResult;
	}

	public Workflow getByWorkflowInstanceId(String instanceid) throws AppException {
		return service.getByWorkflowInstanceId(instanceid);
	}

	@Override
	public EntityMetaData getEntityMetaData() {
		return service.getEntityMetaData();
	}

	@Override
	public SearchResult doSearch(FilterInfo filterInfo, DataContext dataContext) throws AppException {
		SearchResult searchResult = searchManager.doSearch(Workflow.class, service.getEntityMetaData(), filterInfo, null);
		List<Object> objectList = searchResult.getResultObjects();
		List<Object> workflowList = (List<Object>) service.populateRelationshipFields(objectList);
		searchResult.setResultObjects(workflowList);
		return searchResult;
	}

	public void triggerWorkflow(Workflow workflow) {
		service.triggerWorkflow(workflow);
	}

	@Override
	public Object restoreObject(Object object) throws AppException {
		// TODO Auto-generated method stub
		return null;
	}

}
