package com.netkiller.security.acl;

import java.io.Serializable;

public abstract class EntityPermission implements ResourcePermission,Serializable {

	private String entityName;
	private PermissionType permissionType;

	public EntityPermission(String entityName, PermissionType permissionType) {
		this.entityName = entityName;
		this.permissionType = permissionType;
	}

	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * @return the permissionType
	 */
	@Override
	public Permission.PermissionType getPermissionType() {
		return permissionType;
	}

	@Override
	public String getResourceName() {
		return entityName;
	}
	
	

}