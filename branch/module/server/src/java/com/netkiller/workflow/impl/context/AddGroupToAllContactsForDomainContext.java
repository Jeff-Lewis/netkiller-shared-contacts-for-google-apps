package com.netkiller.workflow.impl.context;

import java.io.Serializable;

import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowInfo;

public class AddGroupToAllContactsForDomainContext implements WorkflowContext,
		Serializable {

	private String domainName;

	private String groupName;

	private String email;

	private WorkflowInfo workflowInfo;

	@Override
	public WorkflowInfo getWorkflowInfo() {

		return workflowInfo;
	}

	@Override
	public String toString() {
		return "AddGroupToAllContactsForDomainContext [domainName="
				+ domainName + ", groupName=" + groupName + ", email=" + email
				+ ", workflowInfo=" + workflowInfo + "]";
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void setWorkflowInfo(WorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;

	}

}
