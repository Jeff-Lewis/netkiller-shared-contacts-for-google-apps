package com.netkiller.vo;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

public class DomainSettings implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5779730954944204398L;


	@Override
	public String toString() {
		return "DomainSettings [key=" + key + ", onlyAdminPermitted="
				+ onlyAdminPermitted + ", allUserPermitted=" + allUserPermitted
				+ ", domain=" + domain + "]";
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	
	public boolean isOnlyAdminPermitted() {
		return onlyAdminPermitted;
	}

	public void setOnlyAdminPermitted(boolean onlyAdminPermitted) {
		this.onlyAdminPermitted = onlyAdminPermitted;
	}

	public boolean isAllUserPermitted() {
		return allUserPermitted;
	}

	public void setAllUserPermitted(boolean allUserPermitted) {
		this.allUserPermitted = allUserPermitted;
	}

	private Key key;
	
	private boolean onlyAdminPermitted;
	
	private boolean allUserPermitted;
	
	private String domain;


	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

}
