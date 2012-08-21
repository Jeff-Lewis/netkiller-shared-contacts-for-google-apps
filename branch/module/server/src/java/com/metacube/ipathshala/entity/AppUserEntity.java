package com.metacube.ipathshala.entity;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

/**
 * 
 * @author amit.c
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public final class AppUserEntity implements Serializable {
	public static String APPUSER_SESSION_VAR = "appUser";

	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@PrimaryKey
	private Key key;
	// the unique user id
	@Persistent
	private String userId;

	@Persistent
	private String email;

	@Persistent
	private String nickName;

	@Persistent
	private String firstName;
	@Persistent
	private String lastName;
	@Persistent
	private String domain;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public AppUserEntity(String userId, String email, String firstName,
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
