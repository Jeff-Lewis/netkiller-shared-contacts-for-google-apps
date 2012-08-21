package com.metacube.ipathshala.calendar;

/**
 * Gives the Acl permissions to the calendar
 * @author administrator
 *
 */
public class AclForCalendar {

	private CalendarRoleType calendarRoleType;

	private CalendarScope calendarScope;
	
	private String id ;

	/**
	 * @return the calendarRoleType
	 */
	public CalendarRoleType getCalendarRoleType() {
		return calendarRoleType;
	}

	/**
	 * @param calendarRoleType
	 *            the calendarRoleType to set
	 */
	public void setCalendarRoleType(CalendarRoleType calendarRoleType) {
		this.calendarRoleType = calendarRoleType;
	}

	/**
	 * @return the calendarScope
	 */
	public CalendarScope getCalendarScope() {
		return calendarScope;
	}

	/**
	 * @param calendarScope
	 *            the calendarScope to set
	 */
	public void setCalendarScope(CalendarScope calendarScope) {
		this.calendarScope = calendarScope;
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
		return "AclForCalendar [calendarRoleType=" + calendarRoleType
				+ ", calendarScope=" + calendarScope + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}
