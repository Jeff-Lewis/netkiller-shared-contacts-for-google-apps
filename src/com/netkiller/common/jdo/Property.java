package com.netkiller.common.jdo;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Property {
	public static String[] propertyId = {"sharedContactsGroupName", "adminDomain", "domainCheck"
				, "isUseForSharedContacts", "username", "password", "feedurl", "groupFeedUrl"
				, "isSortingSupported"};
	
//	@PrimaryKey
//    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
//    private Key key;

//	@Persistent
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String propId;
	
	@Persistent
	private String propValue;
	
	@Persistent
	private String seq;

//	public Key getKey() {
//		return key;
//	}
//
//	public void setKey(Key key) {
//		this.key = key;
//	}

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
