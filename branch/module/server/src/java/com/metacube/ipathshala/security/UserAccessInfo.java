package com.metacube.ipathshala.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.metacube.ipathshala.security.acl.Permission;
import com.metacube.ipathshala.security.acl.ResourcePermission;

/**
 * Contains a user's access information on resources. This is a consolidated
 * information collected from the groups the user belongs to and the highest
 * possible access on each resource is calculated and stored here.
 * 
 * @author prateek
 * 
 */
public class UserAccessInfo implements Serializable {

	/**
	 * Key= resource name Value= ResourcePermission
	 */
	private Map<String, ResourcePermission> resourcePermissionMap = new HashMap<String, ResourcePermission>();

	public void addResourcePermission(ResourcePermission permission) {
		if (permission != null) {
			String resourceName = permission.getResourceName();
			ResourcePermission existingResourcePermission = resourcePermissionMap.get(resourceName);

			// determine if existing permission is higher or lower than the
			// permission being added.
			if (existingResourcePermission == null
					|| permission.getPermissionType().weightage() > existingResourcePermission.getPermissionType()
							.weightage()) {
				resourcePermissionMap.put(resourceName, permission);
			}
		}
	}

	public boolean hasAccess(String resourceName, Permission.PermissionType permission) {
		boolean hasAccess = false;
		ResourcePermission resourcePermission = resourcePermissionMap.get(resourceName);
		if (resourcePermission != null) {
			if (resourcePermission.getPermissionType().weightage() >= permission.weightage()) {
				hasAccess = true;
			}

		}
		return hasAccess;
	}

}
