/**
 * 
 */
package com.netkiller.sites;

/**
 * @author administrator
 * 
 */
public class AclForSite {

	private Scope scope;

	private GoogleSiteRoleType roleType;
	
	private String id ;

	/**
	 * @return the scope
	 */
	public Scope getScope() {
		return scope;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(Scope scope) {
		this.scope = scope;
	}

	/**
	 * @return the role
	 */
	public GoogleSiteRoleType getRoleType() {
		return roleType;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRoleType(GoogleSiteRoleType role) {
		this.roleType = role;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AclForSites [scope=" + scope + ", roleType=" + roleType
				+ ", id=" + id + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

}
