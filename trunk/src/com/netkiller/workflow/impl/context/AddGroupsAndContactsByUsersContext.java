package com.netkiller.workflow.impl.context;

import java.io.Serializable;
import java.util.List;

import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowInfo;

public class AddGroupsAndContactsByUsersContext implements WorkflowContext, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5376738689733227749L;
	private WorkflowInfo workflowInfo;
	private List<String> users;
	private String domain;

	@Override
	public WorkflowInfo getWorkflowInfo() {
		return workflowInfo;
	}

	@Override
	public void setWorkflowInfo(WorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		return "AddGroupsAndContactsByUsersContext [workflowInfo=" + workflowInfo + ", users=" + users + ", domain="
				+ domain + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
