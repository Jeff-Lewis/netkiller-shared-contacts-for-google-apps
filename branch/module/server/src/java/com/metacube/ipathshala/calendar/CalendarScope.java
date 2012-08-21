package com.metacube.ipathshala.calendar;

/**
 * 
 * @author administrator
 *
 */
public class CalendarScope {
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
	public CalendarScope() {
	}

	private CalendarScope.Type scopeType;

	/**
	 * The constructor to set the scope with the type of scopeType and the scope
	 * value
	 * 
	 * @param type
	 * @param value
	 */
	public CalendarScope(CalendarScope.Type type, String value) {
		scopeType = type;
		scopeValue = value;
	}

	/**
	 * 
	 * @return
	 */
	public CalendarScope.Type getScopeType() {
		return scopeType;
	}

	/**
	 * 
	 * @param type
	 */
	public void setScopeType(CalendarScope.Type type) {
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
