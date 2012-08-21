package com.metacube.ipathshala.entity;

import java.io.Serializable;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.data.DateTime;

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class CacheSetting implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private List<String> activeCacheEntities;
	
	@Persistent
	private DateTime lastModifiedDate;

	@Override
	public String toString() {
		return "CacheSetting [key=" + key + ", activeCacheEntities="
				+ activeCacheEntities + ", cacheEnabled=" + cacheEnabled + "]";
	}

	public DateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(DateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	@Persistent
	private DateTime createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;

	
	public List<String> getActiveCacheEntities() {
		return activeCacheEntities;
	}

	public void setActiveCacheEntities(List<String> activeCacheEntities) {
		this.activeCacheEntities = activeCacheEntities;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Boolean getCacheEnabled() {
		return cacheEnabled;
	}

	public void setCacheEnabled(Boolean cacheEnabled) {
		this.cacheEnabled = cacheEnabled;
	}

	@Persistent
	private Boolean cacheEnabled;

}
