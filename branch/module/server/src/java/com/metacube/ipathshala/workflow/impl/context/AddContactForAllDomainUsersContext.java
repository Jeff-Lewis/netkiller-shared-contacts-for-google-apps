package com.metacube.ipathshala.workflow.impl.context;

import com.metacube.ipathshala.entity.Contacts;
import com.metacube.ipathshala.workflow.WorkflowInfo;

public class AddContactForAllDomainUsersContext {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8187108278263548021L;
	private WorkflowInfo workflowInfo;
	private String domain;
	private Contacts contacts;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Contacts getContactInfo() {
		return contacts;
	}

	public void setContactInfo(Contacts contactInfo) {
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
