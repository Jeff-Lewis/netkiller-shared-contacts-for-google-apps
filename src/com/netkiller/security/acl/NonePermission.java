package com.netkiller.security.acl;

import java.io.Serializable;

public final class NonePermission implements Permission,Serializable {

	public PermissionType getPermissionType() {
		return PermissionType.NONE;
	}

}
