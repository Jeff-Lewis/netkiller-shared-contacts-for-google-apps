package com.metacube.ipathshala.workflow.impl.context;

import java.io.Serializable;

import com.metacube.ipathshala.entity.Contact;
import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowInfo;

public class AddContactForAllDomainUsersContext implements WorkflowContext,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8187108278263548021L;
	private WorkflowInfo workflowInfo;
	private String domain;
	private Contact contacts;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Contact getContactInfo() {
		return contacts;
	}

	public void setContactInfo(Contact contactInfo) {
		this.contacts = contactInfo;
	}

	public WorkflowInfo getWorkflowInfo() {
		return workflowInfo;
	}

	public void setWorkflowInfo(WorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;
	}

	@Override
	public String toString() {
		return "AddContactForAllDomainUsersContext [workflowInfo="
				+ workflowInfo + ", domain=" + domain + ", contactInfo="
				+ contacts + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
}
