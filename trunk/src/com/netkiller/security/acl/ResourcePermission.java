package com.netkiller.security.acl;

/**
 * Contract for all implementations of Resources and their permissions.
 * 
 * @author prateek
 * 
 */
public interface ResourcePermission extends Permission {
	public String getResourceName();

	public Permission.PermissionType getPermissionType();
}
