package com.metacube.ipathshala.security.acl;

public class EntityReadPermission extends EntityPermission {

	public EntityReadPermission(String entityName) {
		super(entityName, PermissionType.READ);
	}

}
