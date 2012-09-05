package com.netkiller.service.googleservice.util;

import java.util.Date;
import java.util.TimeZone;

import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.acl.AclEntry;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.WebContent;
import com.google.gdata.data.extensions.When;
import com.netkiller.calendar.AclForCalendar;
import com.netkiller.calendar.Calendar;
import com.netkiller.calendar.CalendarEvent;
import com.netkiller.calendar.CalendarRoleType;
import com.netkiller.calendar.CalendarScope;
import com.netkiller.calendar.CalendarWebContent;

/**
 * 
 * @author administrator
 * 
 */
public class GoogleCalendarUtil {

	private static final String IST_TIME_ZONE = "GMT+05:30";

	/**
	 * This method copies the calendarEvent Object of ipathshala and convert it
	 * into a object of calendarEventEntry of google
	 * 
	 * @param calenderEvent
	 * @return
	 */
	public static CalendarEventEntry convertCalendarEventToCalendarEventEntry(CalendarEvent calenderEvent) {

		if (calenderEvent == null) {
			return null;
		}

		CalendarEventEntry calendarEventEntry = new CalendarEventEntry();
		calendarEventEntry.setTitle(new PlainTextConstruct(calenderEvent.getTitle()));
		calendarEventEntry.setContent(new PlainTextConstruct(calenderEvent.getContent()));
		calendarEventEntry.setIcalUID(calenderEvent.getId());
		When eventTimes = new When();
		if (!calenderEvent.getStartDate().equals(calenderEvent.getEndDate())) {

			DateTime startTime = new DateTime(calenderEvent.getStartDate(), TimeZone.getTimeZone(IST_TIME_ZONE));
			DateTime endTime = new DateTime(calenderEvent.getEndDate(), TimeZone.getTimeZone(IST_TIME_ZONE));
			eventTimes.setStartTime(startTime);
			eventTimes.setEndTime(endTime);
		} else {
			System.out.println("Event Start Date:" + calenderEvent.getStartDate());
			DateTime startTime = new DateTime(calenderEvent.getStartDate(), TimeZone.getTimeZone("GMT"));
			startTime.setValue(calenderEvent.getEndDate().getTime() + 11 * 30 * 60 * 1000);
			startTime.setDateOnly(true);
			eventTimes.setStartTime(startTime);
		}

		calendarEventEntry.addTime(eventTimes);
		if (calenderEvent.getCalendarWebContent() != null) {
			WebContent wc = convertCalendarWebContentToWebContent(calenderEvent.getCalendarWebContent());
			calendarEventEntry.setWebContent(wc);
		}
		return calendarEventEntry;
	}

	/**
	 * This method copies the calendarEventEntry Object of google and convert it
	 * into a object of calendarEvent of ipathshal
	 * 
	 * @param calendarEventEntry
	 * @return
	 */
	public static CalendarEvent convertCalendarEventEntryToCalendarEvent(CalendarEventEntry calendarEventEntry) {

		if (calendarEventEntry == null) {
			return null;
		}

		CalendarEvent calendarEvent = new CalendarEvent();
		calendarEvent.setTitle(calendarEventEntry.getTitle().getPlainText());
		calendarEvent.setContent(calendarEventEntry.getContent().toString());
		calendarEvent.setStartDate(new Date(calendarEventEntry.getTimes().get(0).getStartTime().getValue()));
		calendarEvent.setEndDate(new Date(calendarEventEntry.getTimes().get(0).getEndTime().getValue()));
		calendarEvent.setEditLink(calendarEventEntry.getEditLink().getHref());
		calendarEvent.setSelfLink(calendarEventEntry.getSelfLink().getHref());
		calendarEvent.setId(calendarEventEntry.getIcalUID());

		if (calendarEventEntry.getWebContent() != null) {
			calendarEvent.setCalendarWebContent(convertWebContentToCalendarWebContent(calendarEventEntry
					.getWebContent()));
		}

		return calendarEvent;
	}

	/**
	 * This method copies the CalendarWebContent Object of ipathshala and
	 * convert it into a object of WebContent of google
	 * 
	 * @param webContent
	 * @return
	 */
	public static CalendarWebContent convertWebContentToCalendarWebContent(WebContent webContent) {
		if (webContent == null) {
			return null;
		}

		CalendarWebContent calWebContent = new CalendarWebContent();
		calWebContent.setContentUrl(webContent.getUrl());
		calWebContent.setIcon(webContent.getIcon());
		calWebContent.setHeight(webContent.getHeight());
		calWebContent.setPrefrences(webContent.getGadgetPrefs());
		calWebContent.setWidth(webContent.getWidth());
		calWebContent.setTitle(webContent.getTitle());
		calWebContent.setType(webContent.getType());

		return calWebContent;
	}

	/**
	 * This method copies the CalendarWebContent Object of ipathshala and
	 * convert it into a object of WebContent of google
	 * 
	 * @param calWebContent
	 * @return
	 */
	public static WebContent convertCalendarWebContentToWebContent(CalendarWebContent calWebContent) {

		if (calWebContent == null) {
			return null;
		}
		WebContent webContent = new WebContent();
		webContent.setGadgetPrefs(calWebContent.getPrefrences());
		webContent.setHeight(calWebContent.getHeight());
		webContent.setWidth(calWebContent.getWidth());
		webContent.setIcon(calWebContent.getIcon());
		webContent.setTitle(calWebContent.getTitle());
		webContent.setType(calWebContent.getType());
		webContent.setUrl(calWebContent.getContentUrl());

		return webContent;
	}

	/**
	 * This method copy one calendar event to another
	 * 
	 * @param sourceEvent
	 * @param destinationEvent
	 * @return
	 */
	public static CalendarEvent copyCalendarEvent(CalendarEvent sourceEvent, CalendarEvent destinationEvent) {
		destinationEvent.setTitle(sourceEvent.getTitle());
		destinationEvent.setContent(sourceEvent.getContent().toString());
		destinationEvent.setStartDate(sourceEvent.getStartDate());
		destinationEvent.setEndDate(sourceEvent.getEndDate());
		destinationEvent.setEditLink(sourceEvent.getEditLink());
		destinationEvent.setSelfLink(sourceEvent.getSelfLink());
		destinationEvent.setId(sourceEvent.getId());
		destinationEvent.setCalendarWebContent(sourceEvent.getCalendarWebContent());

		return destinationEvent;
	}

	/**
	 * This method copies the CalendarEntry Object of google and convert it into
	 * a object of Calendar of ipathshala
	 * 
	 * @param calendarEntry
	 * @return
	 */
	public static Calendar calendarFromCalendarEntry(CalendarEntry calendarEntry) {
		Calendar newCalendar = new Calendar();
		newCalendar.setTitle(calendarEntry.getTitle().getPlainText());
		newCalendar.setId(calendarEntry.getId());
		newCalendar.setEditLink(calendarEntry.getEditLink().getHref());
		newCalendar.setSelfLink(calendarEntry.getSelfLink().getHref());

		return newCalendar;
	}

	/**
	 * This method copies the Calendar Object of ipathshala and convert it into
	 * a object of CalendarEntry of google
	 * 
	 * @param calendar
	 * @return
	 */
	public static CalendarEntry calendarEntryFromCalendar(Calendar calendar) {
		CalendarEntry calendarEntry = new CalendarEntry();
		calendarEntry.setTitle(new PlainTextConstruct(calendar.getTitle()));
		calendarEntry.setId(calendar.getId());
		return calendarEntry;
	}

	/**
	 * 
	 * @param aclEntry
	 * @return
	 */
	public static AclForCalendar convertAclEntriesToAclForCalendar(AclEntry aclEntry) {
		AclForCalendar aclForCalendar = new AclForCalendar();

		CalendarRoleType roleType = null;
		String roleValue = aclEntry.getRole().getValue();
		if (roleValue.equalsIgnoreCase("editor")) {
			roleType = CalendarRoleType.EDITOR;
		} else if (roleValue.equalsIgnoreCase("freebusy")) {
			roleType = CalendarRoleType.FREEBUSY;
		} else if (roleValue.equalsIgnoreCase("none")) {
			roleType = CalendarRoleType.NONE;
		} else if (roleValue.equalsIgnoreCase("override")) {
			roleType = CalendarRoleType.OVERRIDE;
		} else if (roleValue.equalsIgnoreCase("owner")) {
			roleType = CalendarRoleType.OWNER;
		} else if (roleValue.equalsIgnoreCase("read")) {
			roleType = CalendarRoleType.READ;
		} else if (roleValue.equalsIgnoreCase("respond")) {
			roleType = CalendarRoleType.RESPOND;
		} else if (roleValue.equalsIgnoreCase("root")) {
			roleType = CalendarRoleType.ROOT;
		}

		aclForCalendar.setCalendarRoleType(roleType);
		CalendarScope.Type type = null;
		switch (aclEntry.getScope().getType()) {
		case USER:
			type = CalendarScope.Type.USER;
			break;
		case DOMAIN:
			type = CalendarScope.Type.DOMAIN;
			break;
		case GROUP:
			type = CalendarScope.Type.GROUP;
			break;
		case DEFAULT:
			type = CalendarScope.Type.DEFAULT;
			break;
		}
		aclForCalendar.setCalendarScope(new CalendarScope(type, aclEntry.getScope().getValue()));
		aclForCalendar.setId(aclEntry.getId());
		return aclForCalendar;
	}

}
