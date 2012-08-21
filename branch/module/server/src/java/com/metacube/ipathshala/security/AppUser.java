package com.metacube.ipathshala.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.security.acl.Permission;

/**
 * 
 * @author amit.c
 * 
 */

public final class AppUser implements Serializable {
	public static String APPUSER_SESSION_VAR = "appUser";
	// the unique user id

	private String userId;

	private String email;

	private String nickName;

	private String firstName;

	private String lastName;

	private String domain;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	// the default group of the user
	private AppGroup defaultAppGroup;

	// additional groups a user can be part of
	private List<AppGroup> appGroups = new ArrayList<AppGroup>();

	private UserAccessInfo accessInformation = new UserAccessInfo();

	public AppUser(String userId, String email, String firstName,
			String lastName, String nickName, String domain) {
		super();
		this.userId = userId;
		this.email = email;
		this.nickName = nickName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.domain = domain;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the defaultAppGroup
	 */
	public AppGroup getDefaultAppGroup() {
		return defaultAppGroup;
	}

	/**
	 * @param defaultAppGroup
	 *            the defaultAppGroup to set
	 */
	public void setDefaultAppGroup(AppGroup defaultAppGroup) {
		this.defaultAppGroup = defaultAppGroup;
	}

	/**
	 * @return the appGroups
	 */
	public List<AppGroup> getAppGroups() {
		return appGroups;
	}

	public void addAppGroup(AppGroup group) {
		this.appGroups.add(group);
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public UserAccessInfo getAccessInformation() {
		return accessInformation;
	}

	public void setAccessInformation(UserAccessInfo accessInformation) {
		this.accessInformation = accessInformation;
	}

	public void setAppGroups(List<AppGroup> appGroups) {
		this.appGroups = appGroups;
	}

	public boolean hasAccess(String resourceName,
			Permission.PermissionType permission) {
		return accessInformation.hasAccess(resourceName, permission);
	}

}
