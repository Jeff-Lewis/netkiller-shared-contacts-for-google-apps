package com.netkiller.workflow.impl.context;

import java.io.Serializable;
import java.util.List;

import com.google.gdata.data.contacts.ContactEntry;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowInfo;

public class SyncUserContactsContext implements WorkflowContext, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7007353076319985798L;

	private WorkflowInfo workflowInfo;

	private String userEmail;

	private Integer totalLimit;

	private String groupId;

	private Boolean isUseForSharedContacts;

	@Override
	public WorkflowInfo getWorkflowInfo() {
		return workflowInfo;
	}

	@Override
	public void setWorkflowInfo(WorkflowInfo workflowInfo) {
		this.workflowInfo = workflowInfo;

	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getTotalLimit() {
		return totalLimit;
	}

	public void setTotalLimit(Integer totalLimit) {
		this.totalLimit = totalLimit;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Boolean getIsUseForSharedContacts() {
		return isUseForSharedContacts;
	}

	public void setIsUseForSharedContacts(Boolean isUseForSharedContacts) {
		this.isUseForSharedContacts = isUseForSharedContacts;
	}

	@Override
	public String toString() {
		return "SyncUserContactsContext [workflowInfo=" + workflowInfo + ", userEmail=" + userEmail + ", totalLimit="
				+ totalLimit + ", groupId=" + groupId + ", isUseForSharedContacts=" + isUseForSharedContacts
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
