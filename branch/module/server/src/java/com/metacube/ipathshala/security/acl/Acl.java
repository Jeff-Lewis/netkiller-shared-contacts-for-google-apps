package com.metacube.ipathshala.security.acl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.metacube.ipathshala.security.acl.Permission.PermissionType;

/**
 * The collection of permissions for a given group
 * 
 * @author amit.c
 * 
 */
public class Acl implements Serializable {

	/**
	 * the unique name of this ACL
	 */
	private String aclName;

	/**
	 * the group to which this ACL belongs
	 */
	private String groupName;

	
	private boolean defaultGroup;

	/**
	 * permission collection
	 */
	private List<ResourcePermission> permissionList = new ArrayList<ResourcePermission>();

	/**
	 * @param group
	 */
	public Acl() {

	}

	/**
	 * @return the aclName
	 */
	public String getAclName() {
		return aclName;
	}

	/**
	 * @param aclName
	 *            the aclName to set
	 */
	public void setAclName(String aclName) {
		this.aclName = aclName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<ResourcePermission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<ResourcePermission> permissionList) {
		this.permissionList = permissionList;
	}
	

	public boolean hasAccess(String resourceName, PermissionType permissionType) {
		boolean hasAccess = false;
		if (resourceName == null || permissionType == null) {
			return false;
		}
		for (ResourcePermission rp : permissionList) {
			if (resourceName.equals(rp.getResourceName())) {
				if (permissionType.weightage() >= rp.getPermissionType().weightage()) {
					hasAccess = true;
				}
				break;
			}
		}
		return hasAccess;
	}

	public boolean isDefaultGroup() {
		return defaultGroup;
	}

	public void setDefaultGroup(boolean defaultGroup) {
		this.defaultGroup = defaultGroup;
	}
	

}
