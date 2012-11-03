package com.netkiller.entity;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class EntityCounter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2215279694921894539L;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String domain;

	@Persistent
	private String entityName;

	@Persistent
	private int count;

	@Persistent
	private int shardNumber;

	public int getShardNumber() {
		return shardNumber;
	}

	public void setShardNumber(int shardNumber) {
		this.shardNumber = shardNumber;
	}

	@Override
	public String toString() {
		return "EntityCounter [key=" + key + ", domain=" + domain
				+ ", entityName=" + entityName + ", count=" + count
				+ ", shardNumber=" + shardNumber + "]";
	}

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

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
