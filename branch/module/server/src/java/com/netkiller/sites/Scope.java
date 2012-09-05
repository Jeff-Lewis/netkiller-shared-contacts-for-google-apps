/**
 * 
 */
package com.netkiller.sites;

import com.google.gdata.data.ExtensionDescription.Default;

/**
 * @author administrator
 * 
 */
public class Scope {

	/**
	 * The enum which declares the type of scopes
	 * 
	 * @author administrator
	 * 
	 */
	public static enum Type {
		DEFAULT, DOMAIN, GROUP, USER;

		@Override
		public String toString() {
			switch (this) {
			case DEFAULT:
				return "default";
			case DOMAIN:
				return "domain";
			case GROUP:
				return "group";
			case USER:
				return "user";
			default:
				return null;
			}
		}
	}

	private String scopeValue;

	/**
	 * default constructor
	 */
	public Scope() {
	}

	private Scope.Type scopeType;

	/**
	 * The constructor to set the scope with the type of scopeType and the scope
	 * value
	 * 
	 * @param type
	 * @param value
	 */
	public Scope(Scope.Type type, String value) {
		scopeType = type;
		scopeValue = value;
	}

	/**
	 * 
	 * @return
	 */
	public Scope.Type getScopeType() {
		return scopeType;
	}

	/**
	 * 
	 * @param type
	 */
	public void setScopeType(Scope.Type type) {
		scopeType = type;
	}

	/**
	 * @return the scopeValue
	 */
	public String getScopeValue() {
		return scopeValue;
	}

	/**
	 * @param scopeValue
	 *            the scopeValue to set
	 */
	public void setScopeValue(String scopeValue) {
		this.scopeValue = scopeValue;
	}
}
