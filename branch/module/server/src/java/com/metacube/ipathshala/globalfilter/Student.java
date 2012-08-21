package com.metacube.ipathshala.globalfilter;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.entity.metadata.impl.GlobalFilterType;

public class Student implements GlobalFilter,Serializable {

	public Student(Key key, String userId) {
		this.key = key;
		this.userId = userId;
	}

	public Student(Key key, String userId, Key classKey) {
		this.key = key;
		this.userId = userId;
		this.classKey = classKey;
	}

	private Key key;

	private String userId;

	private Key classKey;

	/**
	 * @return the classKey
	 */
	public Key getClassKey() {
		return classKey;
	}

	/**
	 * @param classKey
	 *            the classKey to set
	 */
	public void setClassKey(Key classKey) {
		this.classKey = classKey;
	}

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

	@Override
	public GlobalFilterType getGlobalFilterType() {
		return GlobalFilterType.STUDENT;
	}

}
