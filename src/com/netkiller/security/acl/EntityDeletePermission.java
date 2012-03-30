package com.netkiller.security.acl;

public class EntityDeletePermission extends EntityPermission {

	public EntityDeletePermission(String entityName) {
		super(entityName, PermissionType.DELETE);
	}

}
