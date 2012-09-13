package com.netkiller.vo;

import java.util.Date;

import com.google.appengine.api.datastore.Key;

public class UserSync {
	private Key key;
	
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	private String userEmail;
	private String date;
	private String domain;
	Integer noOfSyncs;
	
	
	public Integer getNoOfSyncs() {
		return noOfSyncs;
	}
	public void setNoOfSyncs(Integer noOfSyncs) {
		this.noOfSyncs = noOfSyncs;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}

}
