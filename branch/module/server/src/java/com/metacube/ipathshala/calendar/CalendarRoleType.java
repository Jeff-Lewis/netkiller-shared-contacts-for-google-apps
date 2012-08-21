package com.metacube.ipathshala.calendar;

/**
 * The role type for the calendar role
 * 
 * @author administrator
 * 
 */
public enum CalendarRoleType {
	EDITOR, FREEBUSY, NONE, OVERRIDE, OWNER, READ, RESPOND, ROOT;

	@Override
	public String toString() {
		switch (this) {
		case EDITOR:
			return "editor";
		case FREEBUSY:
			return "freebusy";
		case NONE:
			return "none";
		case OVERRIDE:
			return "override";
		case OWNER:
			return "owner";
		case READ:
			return "read";
		case RESPOND:
			return "respond";
		case ROOT:
			return "root";
		default:
			return null;
		}
	}
}