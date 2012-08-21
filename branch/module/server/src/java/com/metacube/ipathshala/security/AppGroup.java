package com.metacube.ipathshala.security;

import java.io.Serializable;

import com.metacube.ipathshala.security.acl.Acl;

public class AppGroup implements Serializable{

	private String groupName;

	private Acl accessControlList;

	private String dashboardPageURI;

	
	public AppGroup(String groupName) {
		super();
		this.groupName = groupName;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName
	 *            the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the accessControlList
	 */
	public Acl getAccessControlList() {
		return accessControlList;
	}

	/**
	 * @param accessControlList
	 *            the accessControlList to set
	 */
	public void setAccessControlList(Acl accessControlList) {
		this.accessControlList = accessControlList;
	}

	/**
	 * @return the dashboardPageURI
	 */
	public String getDashboardPageURI() {
		return dashboardPageURI;
	}

	/**
	 * @param dashboardPageURI
	 *            the dashboardPageURI to set
	 */
	public void setDashboardPageURI(String dashboardPageURI) {
		this.dashboardPageURI = dashboardPageURI;
	}

}
