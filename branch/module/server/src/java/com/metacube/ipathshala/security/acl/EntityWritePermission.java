package com.metacube.ipathshala.security.acl;

public class EntityWritePermission extends EntityPermission {

	public EntityWritePermission(String entityName) {
		super(entityName, PermissionType.WRITE);
	}

}
