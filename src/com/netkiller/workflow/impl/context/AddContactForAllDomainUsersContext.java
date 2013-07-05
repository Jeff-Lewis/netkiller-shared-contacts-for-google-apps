package com.netkiller.workflow.impl.context;

import java.io.Serializable;

import com.netkiller.googleUtil.ContactInfo;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowInfo;

public class AddContactForAllDomainUsersContext implements WorkflowContext, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8187108278263548021L;
	private WorkflowInfo workflowInfo;
	private String domain;
	private ContactInfo contactInfo;
	private Boolean isUseForSharedContacts;
	private Integer start = 1;

	public Boolean getIsUseForSharedContacts() {
		return isUseForSharedContacts;
	}

	public void setIsUseForSharedContacts(Boolean isUseForSharedContacts) {
		this.isUseForSharedContacts = isUseForSharedContacts;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}

	@Override
	public WorkflowInfo getWorkflowInfo() {
		return workflowInfo;
	}

	@Override
	public void setWorkflowInfo(WorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;
	}

	@Override
	public String toString() {
		return "AddContactForAllDomainUsersContext [workflowInfo=" + workflowInfo + ", domain=" + domain
				+ ", contactInfo=" + contactInfo + ", isUseForSharedContacts=" + isUseForSharedContacts
				+ ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}
