package com.metacube.ipathshala.vo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Property {
	public static String[] propertyId = { "sharedContactsGroupName",
			"adminDomain", "domainCheck", "isUseForSharedContacts", "username",
			"password", "feedurl", "groupFeedUrl", "isSortingSupported" };

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String propId;

	@Persistent
	private String propValue;

	@Persistent
	private String seq;

	public String getPropId() {
		return propId;
	}

	public void setPropId(String propId) {
		this.propId = propId;
	}

	public String getPropValue() {
		return propValue;
	}

	public void setPropValue(String propValue) {
		this.propValue = propValue;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

}
