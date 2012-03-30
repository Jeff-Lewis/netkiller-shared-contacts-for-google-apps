package com.netkiller.vo;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;
import com.netkiller.security.acl.PermissionType;

public class UserPermission implements Serializable {

	private static final long serialVersionUID = -9221177907987541714L;
	private Key key;
	private String domain;
	private String userID;

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public PermissionType getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(PermissionType permissionType) {
		this.permissionType = permissionType;
	}

	private PermissionType permissionType;

	@Override
	public String toString() {
		return "UserPermission [key=" + key + ", domain=" + domain
				+ ", userID=" + userID + ", permissionType=" + permissionType
				+ "]";
	}
}
