package com.netkiller.workflow.impl.context;

import java.io.Serializable;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.netkiller.core.DataContext;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowInfo;

@SuppressWarnings("serial")
public class BulkContactDuplicateWorkflowContext implements WorkflowContext,
		Serializable {

	WorkflowInfo workflowInfo;

	List<Key> contacts;

	DataContext dataContext;
	
	String urlId;
	
	Boolean addToConnect = false;	
	
	String domain;

	@Override
	public String toString() {
		return "BulkContactDuplicateWorkflowContext [workflowInfo="
				+ workflowInfo + ", contacts=" + contacts + ", dataContext="
				+ dataContext + ", domain=" + domain + "]";
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	
	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	public Boolean getAddToConnect() {
		return addToConnect;
	}

	public void setAddToConnect(Boolean addToConnect) {
		this.addToConnect = addToConnect;
	}

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
