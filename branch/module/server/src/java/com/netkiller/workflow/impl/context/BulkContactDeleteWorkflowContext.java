package com.netkiller.workflow.impl.context;

import java.io.Serializable;
import java.util.List;

import com.google.appengine.api.datastore.Key;
import com.netkiller.core.DataContext;
import com.netkiller.entity.Contact;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowInfo;

@SuppressWarnings("serial")
public class BulkContactDeleteWorkflowContext implements WorkflowContext,
		Serializable {

	WorkflowInfo workflowInfo;

	Contact contact;

	DataContext dataContext;

	String userEmail;

	@Override
	public String toString() {
		return "BulkContactDeleteWorkflowContext [workflowInfo=" + workflowInfo
				+ ", contacts=" + contact + ", dataContext=" + dataContext
				+ ", userEmail=" + userEmail + "]";
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

}
