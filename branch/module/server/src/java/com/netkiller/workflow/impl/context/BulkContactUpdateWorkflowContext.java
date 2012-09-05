package com.netkiller.workflow.impl.context;

import java.io.Serializable;

import com.netkiller.core.DataContext;
import com.netkiller.entity.Contact;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowInfo;

public class BulkContactUpdateWorkflowContext implements WorkflowContext,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private WorkflowInfo workflowInfo;

	private Contact contact;

	private DataContext dataContext;

	private String userEmail;

	@Override
	public String toString() {
		return "BulkContactUpdateWorkflowContext [workflowInfo=" + workflowInfo
				+ ", contact=" + contact + ", dataContext=" + dataContext
				+ ", userEmail=" + userEmail + "]";
	}

	public WorkflowInfo getWorkflowInfo() {
		return workflowInfo;
	}

	public void setWorkflowInfo(WorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public DataContext getDataContext() {
		return dataContext;
	}

	public void setDataContext(DataContext dataContext) {
		this.dataContext = dataContext;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
