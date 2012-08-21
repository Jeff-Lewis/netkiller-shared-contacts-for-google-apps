package com.metacube.ipathshala.workflow.impl.context;

import java.io.Serializable;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowInfo;

@SuppressWarnings("serial")
public class BulkContactDuplicateWorkflowContext implements WorkflowContext,
		Serializable {

	WorkflowInfo workflowInfo;

	List<Key> contacts;

	DataContext dataContext;

	public DataContext getDataContext() {
		return dataContext;
	}

	public void setDataContext(DataContext dataContext) {
		this.dataContext = dataContext;
	}

	@Override
	public WorkflowInfo getWorkflowInfo() {
		return workflowInfo;
	}

	@Override
	public void setWorkflowInfo(WorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;
	}

	public List<Key> getContacts() {
		return contacts;
	}

	public void setContacts(List<Key> contacts) {
		this.contacts = contacts;
	}

}
