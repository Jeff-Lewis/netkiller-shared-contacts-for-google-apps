package com.metacube.ipathshala.dao;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class Employee {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;

	@Persistent
	private String name;

	@Persistent
	private Key departmentKey;

	@NotPersistent
	private Department department;

	@Persistent
	private Set<Key> projectKeySet;

	@NotPersistent
	private Set<Project> projects;

	public Employee() {
		this.projectKeySet = new HashSet<Key>();
		this.projects = new HashSet<Project>();
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Key id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the departmentKey
	 */
	public final Key getDepartmentKey() {
		return departmentKey;
	}

	/**
	 * @return the projectKeySet
	 */
	public final Set<Key> getProjectKeySet() {
		return projectKeySet;
	}

	/**
	 * @return the department
	 */
	public final Department getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public final void setDepartment(Department department) {
		this.department = department;
		this.departmentKey = department.getId();
	}

	/**
	 * @return the projects
	 */
	public final Set<Project> getProjects() {
		return projects;
	}

	/**
	 * @param projects
	 *            the projects to set
	 */
	public final void addProject(Project project) {
		this.projectKeySet.add(project.getId());
		this.projects.add(project);
	}

	/**
	 * @param projects
	 *            the projects to set
	 */
	public final void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	/**
	 * @return the id
	 */
	public final Key getId() {
		return id;
	}

}
