package com.netkiller.vo;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

public class UserLogging implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String userId;
    private String domain;

    Key key;
    
    
    
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
    
    

}
