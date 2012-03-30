package com.netkiller.workflow.impl.context;

import java.io.Serializable;
import java.util.List;

import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowInfo;

public class DeleteGroupsAndContactsByUsersContext implements WorkflowContext, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2449820152952392123L;

	private WorkflowInfo workflowInfo;
	private String domain;
	private List<String> users;

	@Override
	public WorkflowInfo getWorkflowInfo() {
		return workflowInfo;
	}

	@Override
	public void setWorkflowInfo(WorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "DeleteGroupsAndContactsByUsersContext [workflowInfo=" + workflowInfo + ", domain=" + domain
				+ ", users=" + users + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
