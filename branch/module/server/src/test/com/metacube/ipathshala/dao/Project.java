package com.metacube.ipathshala.dao;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Project {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;

	@Persistent
	private String projectName;

	@Persistent
	private String projectType;

	/**
	 * @return the id
	 */
	public final Key getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(Key id) {
		this.id = id;
	}

	/**
	 * @return the projectName
	 */
	public final String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public final void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * @return the projectType
	 */
	public final String getProjectType() {
		return projectType;
	}

	/**
	 * @param projectType
	 *            the projectType to set
	 */
	public final void setProjectType(String projectType) {
		this.projectType = projectType;
	}

}
