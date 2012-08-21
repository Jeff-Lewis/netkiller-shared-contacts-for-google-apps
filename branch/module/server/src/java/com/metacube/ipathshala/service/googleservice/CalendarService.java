package com.metacube.ipathshala.service.googleservice;

import java.util.Date;
import java.util.List;

import com.metacube.ipathshala.calendar.AclForCalendar;
import com.metacube.ipathshala.calendar.Calendar;
import com.metacube.ipathshala.calendar.CalendarEvent;
import com.metacube.ipathshala.calendar.CalendarRoleType;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.UserRoleType;

public interface CalendarService {

	public Calendar createNewCalendar(String title) throws AppException;

	public Calendar updateCalendar(Calendar toBeUpdatedCalendar)
			throws AppException;

	public void deleteCalendar(Calendar toBeDeletedCalendar)
			throws AppException;

	public List<CalendarEvent> retrieveEventsWithDateRange(String calendarId,
			Date startDateTime, Date endDateTime) throws AppException;

	public CalendarEvent createEvent(String calendarId,
			CalendarEvent newEventEntry) throws AppException;

	public CalendarEvent updateEvent(String calendarId,
			CalendarEvent toUpdateEntry) throws AppException;

	public void deleteCalendarEvent(String eventId, String calendarId)
			throws AppException;

	public void setUserCredentials(String userName, String password)
			throws AppException;

	public CalendarEvent getCalendarEventById(String eventId, String calendarId)
			throws AppException;

	public Calendar retrieveCalendarById(String calendarId) throws AppException;

	public AclForCalendar getAclForCalendarById(String aclForCalendarId)
			throws AppException;

	public AclForCalendar updateAclForCalendar(
			CalendarRoleType calendarRoleType, String aclForCalendarId)
			throws AppException;

	public void deleteAclForCalendar(String aclEntryId) throws AppException;

	public AclForCalendar addGroupRole(CalendarRoleType roleType,
			UserRoleType group,String calendarId) throws AppException;

	public AclForCalendar addUserRole(CalendarRoleType roleType, String username,String calendarId)
			throws AppException;
}
