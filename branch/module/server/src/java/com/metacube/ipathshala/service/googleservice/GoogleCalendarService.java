package com.metacube.ipathshala.service.googleservice;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.acl.AclEntry;
import com.google.gdata.data.acl.AclNamespace;
import com.google.gdata.data.acl.AclRole;
import com.google.gdata.data.acl.AclScope;
import com.google.gdata.data.calendar.CalendarAclRole;
import com.google.gdata.data.calendar.CalendarEntry;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.calendar.CalendarFeed;
import com.google.gdata.data.calendar.HiddenProperty;
import com.google.gdata.data.calendar.SelectedProperty;
import com.google.gdata.data.calendar.TimeZoneProperty;
import com.google.gdata.data.calendar.WebContent;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import com.metacube.ipathshala.calendar.AclForCalendar;
import com.metacube.ipathshala.calendar.Calendar;
import com.metacube.ipathshala.calendar.CalendarEvent;
import com.metacube.ipathshala.calendar.CalendarRoleType;
import com.metacube.ipathshala.calendar.CalendarScope;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.UserRoleType;
import com.metacube.ipathshala.security.DomainConfig;
import com.metacube.ipathshala.service.googleservice.util.GoogleCalendarUtil;
import com.metacube.ipathshala.util.AppLogger;

@Service
@Component("GoogleCalendarService")
public class GoogleCalendarService implements
		com.metacube.ipathshala.service.googleservice.CalendarService {

	private static final String OWN_POST_URL = "https://www.google.com/calendar/feeds/default/owncalendars/full";
	private static final String ALL_POST_URL = "https://www.google.com/calendar/feeds/default/allcalendars/full";
	private static final String PRIVATE_POST_URL_PART_1 = "https://www.google.com/calendar/feeds/";
	private static final String PRIVATE_POST_URL_PART_2 = "/private/full";
	private static final String ACL_FEED_URL = "https://www.google.com/calendar/feeds/default/acl/full";
	private static final String IST_TIME_ZONE = "GMT+05:30";

	private static final AppLogger log = AppLogger
			.getLogger(GoogleCalendarService.class);
	private CalendarService calendarService;
	private CalendarQuery calendarQuery;
	private CalendarEventFeed calendarEventFeed;
	private URL postURL;
	private String adminEmail;
	private String domain;

	@Autowired
	public GoogleCalendarService(DomainConfig domainConfig) throws AppException {
		calendarService = new CalendarService("");
		adminEmail = domainConfig.getDomainAdminEmail();
		this.domain = domainConfig.getDomainName();
		try {
			calendarService.setUserCredentials(
					domainConfig.getDomainAdminEmail(),
					domainConfig.getDomainAdminPassword());
		} catch (AuthenticationException e) {
			String message = "Could not authenticate";
			log.error(message, e);
			throw new AppException(message, e);
		}
	}

	private String getAclFeedUrl(String aclEntryId) {
		return ACL_FEED_URL + "/" + getIdStringFromFullIdUrl(aclEntryId);
	}

	private String getIdStringFromFullIdUrl(String idUrlString) {
		return idUrlString.substring(idUrlString.lastIndexOf('/') + 1);
	}

	private String getPostUrl(String calendarId) {
		return PRIVATE_POST_URL_PART_1 + getIdStringFromFullIdUrl(calendarId)
				+ PRIVATE_POST_URL_PART_2;
	}

	private String getCalendarFeedUrl(String calendarId) {
		return ALL_POST_URL + "/" + getIdStringFromFullIdUrl(calendarId);
	}

	/**
	 * Creates new Calendar with the given title
	 * 
	 * @param title
	 * @return
	 * @throws AppException
	 */
	public Calendar createNewCalendar(String title) throws AppException {
		CalendarEntry toCreateEntry = new CalendarEntry();
		toCreateEntry.setTitle(new PlainTextConstruct(title));
		toCreateEntry.setHidden(HiddenProperty.FALSE);
		try {
			postURL = new URL(OWN_POST_URL);
			TimeZoneProperty IST_TIME_ZONE = new TimeZoneProperty("GMT+5:30");
			toCreateEntry.setTimeZone(IST_TIME_ZONE);
			CalendarEntry createdEntry = calendarService.insert(postURL,
					toCreateEntry);
			log.info("Calendar created successfully with ID : "
					+ createdEntry.getId() + " and title : "
					+ createdEntry.getTitle().getPlainText());
			return GoogleCalendarUtil.calendarFromCalendarEntry(createdEntry);
		} catch (MalformedURLException e) {
			String message = " No legal protocol could be found in a specification string or the string could not be parsed.";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Failed or interrupted I/O operations.";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Error while processing a GDataRequest.";
			log.error(message, e);
			throw new AppException(message, e);
		}
	}

	private CalendarEntry updateCalendarEntry(CalendarEntry toUpdateEntry,
			String title) throws AppException {
		toUpdateEntry.setSelected(SelectedProperty.TRUE);
		toUpdateEntry.setTitle(new PlainTextConstruct(title));
		try {
			CalendarEntry updatedEntry = toUpdateEntry.update();
			return updatedEntry;
		} catch (IOException e) {
			String message = "Failed or interrupted I/O operations.";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Error while processing a GDataRequest.";
			log.error(message, e);
			throw new AppException(message, e);
		}
	}

	/**
	 * This method updates the calendar
	 */
	public Calendar updateCalendar(Calendar toBeUpdatedCalendar)
			throws AppException {

		CalendarEntry calendarEntry = retrieveCalendarEntryById(toBeUpdatedCalendar
				.getId());
		calendarEntry.setSelected(SelectedProperty.TRUE);
		calendarEntry.setTitle(new PlainTextConstruct(toBeUpdatedCalendar
				.getTitle()));
		try {
			CalendarEntry updatedEntry = calendarEntry.update();
			return GoogleCalendarUtil.calendarFromCalendarEntry(updatedEntry);
		} catch (IOException e) {
			String message = "Failed or interrupted I/O operations.";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Error while processing a GDataRequest.";
			log.error(message, e);
			throw new AppException(message, e);
		}
	}

	/**
	 * This method deletes the calendar
	 * 
	 * @param toBeDeletedCalendar
	 * @throws AppException
	 */
	public void deleteCalendar(Calendar toBeDeletedCalendar)
			throws AppException {
		CalendarEntry calEntry = retrieveCalendarEntryById(toBeDeletedCalendar
				.getId());
		try {
			calEntry.delete();
		} catch (ServiceException se) {
			String msg = "Cannot Delete the calendar";
			throw new AppException(msg, se);
		} catch (IOException io) {
			String msg = "Cannot Delete the calendar";
			throw new AppException(msg, io);
		}
	}

	private List<CalendarEntry> getCalendarEntries() throws AppException {
		try {
			postURL = new URL(ALL_POST_URL);
			return calendarService.getFeed(postURL, CalendarFeed.class)
					.getEntries();
		} catch (IOException e) {
			String message = "Failed or interrupted I/O operations.";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Error while processing a GDataRequest.";
			log.error(message, e);
			throw new AppException(message, e);
		}
	}

	public List<CalendarEvent> getCalendarEventList(String calendarId)
			throws AppException {
		List<CalendarEventEntry> calEvent = getCalendarEventEntries(calendarId);
		List<CalendarEvent> calEventList = new ArrayList<CalendarEvent>();
		for (CalendarEventEntry event : calEvent) {
			calEventList.add(GoogleCalendarUtil
					.convertCalendarEventEntryToCalendarEvent(event));
		}
		return calEventList;
	}

	private List<CalendarEventEntry> getCalendarEventEntries(String calendarId)
			throws AppException {
		try {
			postURL = new URL(getPostUrl(calendarId));
			return calendarService.getFeed(postURL, CalendarEventFeed.class)
					.getEntries();
		} catch (MalformedURLException e) {
			String message = " No legal protocol could be found in a specification string or the string could not be parsed.";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Failed or interrupted I/O operations.";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Error while processing a GDataRequest.";
			log.error(message, e);
			throw new AppException(message, e);
		}
	}

	/**
	 * Retrieves the calendar event with the given date range
	 * 
	 * @param calendarId
	 * @param startDateTime
	 * @param endDateTime
	 * @return
	 * @throws AppException
	 */
	public List<CalendarEvent> retrieveEventsWithDateRange(String calendarId,
			Date startDateTime, Date endDateTime) throws AppException {
		try {
			postURL = new URL(getPostUrl(calendarId));
			calendarQuery = new CalendarQuery(postURL);
			calendarQuery.setMinimumStartTime(new DateTime(startDateTime));
			calendarQuery.setMaximumStartTime(new DateTime(endDateTime));
			calendarEventFeed = calendarService.query(calendarQuery,
					CalendarEventFeed.class);
			List<CalendarEventEntry> retrievedCalendarEventEntry = calendarEventFeed
					.getEntries();

			return retrieveCalendarEventList(retrievedCalendarEventEntry);
		} catch (MalformedURLException e) {
			String message = " No legal protocol could be found in a specification string or the string could not be parsed.";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Failed or interrupted I/O operations.";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Error while processing a GDataRequest.";
			log.error(message, e);
			throw new AppException(message, e);
		}
	}

	private List<CalendarEvent> retrieveCalendarEventList(
			List<CalendarEventEntry> retrievedCalendarEventEntry) {

		List<CalendarEvent> calendarEventList = new ArrayList<CalendarEvent>();
		for (CalendarEventEntry calEventEntry : retrievedCalendarEventEntry) {
			calendarEventList.add(GoogleCalendarUtil
					.convertCalendarEventEntryToCalendarEvent(calEventEntry));
		}
		return calendarEventList;
	}

	/**
	 * Creates event for the given calendar
	 * 
	 * @param calendarId
	 * @param newEventEntry
	 * @return
	 * @throws AppException
	 */
	public CalendarEvent createEvent(String calendarId,
			CalendarEvent newEventEntry) throws AppException {
		try {
			postURL = new URL(getPostUrl(calendarId));
			CalendarEventEntry calendarEventEntry = GoogleCalendarUtil
					.convertCalendarEventToCalendarEventEntry(newEventEntry);
			CalendarEventEntry insertedEntry = calendarService.insert(postURL,
					calendarEventEntry);

			insertedEntry.setWebContent(GoogleCalendarUtil
					.convertCalendarWebContentToWebContent(newEventEntry
							.getCalendarWebContent()));
			return GoogleCalendarUtil
					.convertCalendarEventEntryToCalendarEvent(insertedEntry);
		} catch (IOException e) {
			String message = "Failed or interrupted I/O operations.";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Error while processing a GDataRequest.";
			log.error(message, e);
			throw new AppException(message, e);
		}
	}

	private CalendarEventEntry updateCalendarEventEntry(
			CalendarEventEntry toUpdateEntry, String title, String content,
			Date fromDate, Date toDate) throws AppException {

		toUpdateEntry.setTitle(new PlainTextConstruct(title));
		toUpdateEntry.setContent(new PlainTextConstruct(content));
		When eventTimes = new When();
		if (!fromDate.equals(toDate)) {
			DateTime startTime = new DateTime(fromDate,
					TimeZone.getTimeZone(IST_TIME_ZONE));
			DateTime endTime = new DateTime(toDate,
					TimeZone.getTimeZone(IST_TIME_ZONE));
			eventTimes.setStartTime(startTime);
			eventTimes.setEndTime(endTime);
		} else {
			DateTime startTime = new DateTime(fromDate,
					TimeZone.getTimeZone("GMT"));
			startTime.setValue(toDate.getTime() + 11 * 30 * 60 * 1000);
			startTime.setDateOnly(true);
			eventTimes.setStartTime(startTime);
		}

		toUpdateEntry.getTimes().remove(0);
		toUpdateEntry.addTime(eventTimes);

		try {
			URL newURL = new URL(toUpdateEntry.getEditLink().getHref());
			CalendarEventEntry updatedEntry = calendarService.update(newURL,
					toUpdateEntry);
			return updatedEntry;
		} catch (MalformedURLException e) {
			String message = " No legal protocol could be found in a specification string or the string could not be parsed.";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Failed or interrupted I/O operations.";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Error while processing a GDataRequest.";
			log.error(message, e);
			throw new AppException(message, e);
		}
	}

	/**
	 * Update the calendar event
	 * 
	 * @param calendarId
	 * @param toUpdateEntry
	 * @return
	 * @throws AppException
	 */
	public CalendarEvent updateEvent(String calendarId,
			CalendarEvent toUpdateEntry) throws AppException {

		CalendarEventEntry calEvent = this.getCalendarEventEntryById(
				toUpdateEntry.getId(), calendarId);
		calEvent.setWebContent(GoogleCalendarUtil
				.convertCalendarWebContentToWebContent(toUpdateEntry
						.getCalendarWebContent()));

		CalendarEventEntry updatedEntry = this.updateCalendarEventEntry(
				calEvent, toUpdateEntry.getTitle(), toUpdateEntry.getContent(),
				toUpdateEntry.getStartDate(), toUpdateEntry.getEndDate());

		return GoogleCalendarUtil
				.convertCalendarEventEntryToCalendarEvent(updatedEntry);

	}

	private void deleteEvent(CalendarEventEntry toDeleteEntry)
			throws AppException {
		try {
			toDeleteEntry.delete();
		} catch (MalformedURLException e) {
			String message = " No legal protocol could be found in a specification string or the string could not be parsed.";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Failed or interrupted I/O operations.";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Error while processing a GDataRequest.";
			log.error(message, e);
			throw new AppException(message, e);
		}
	}

	/**
	 * Deletes the calendar event
	 * 
	 * @param eventId
	 * @param calendarId
	 * @throws AppException
	 */
	public void deleteCalendarEvent(String eventId, String calendarId)
			throws AppException {

		CalendarEventEntry toBeDeleted = getCalendarEventEntryById(eventId,
				calendarId);
		if (toBeDeleted == null) {
			String msg = "The calendar Event does not exist. It may have already been deleted from the calendar";
			// throw new AppException(msg);
			log.warn(msg);
		} else {
			this.deleteEvent(toBeDeleted);
		}
	}

	/**
	 * Set the user credentials
	 * 
	 * @param userName
	 * @param password
	 * @throws AppException
	 */
	public void setUserCredentials(String userName, String password)
			throws AppException {
		try {
			calendarService.setUserCredentials(userName, password);
		} catch (AuthenticationException ae) {
			String msg = "Unable to Authenticate";
			throw new AppException(msg, ae);
		}

	}

	/**
	 * 
	 * @param postUrl
	 * @param calenderEvent
	 * @return
	 * @throws AppException
	 */
	private CalendarEvent insert(URL postUrl, CalendarEvent calenderEvent)
			throws AppException {

		CalendarEventEntry calendarEventEntry = GoogleCalendarUtil
				.convertCalendarEventToCalendarEventEntry(calenderEvent);
		try {
			CalendarEventEntry insertedEntry = calendarService.insert(postUrl,
					calendarEventEntry);
			return GoogleCalendarUtil
					.convertCalendarEventEntryToCalendarEvent(insertedEntry);
		} catch (ServiceException se) {
			String msg = "Unable to Insert";
			throw new AppException(msg, se);
		} catch (IOException ioe) {
			String msg = "Unable to Insert";
			throw new AppException(msg, ioe);
		}
	}

	/**
	 * retrieves the calendar event for a given id
	 * 
	 * @param eventId
	 * @param calendarId
	 * @return
	 * @throws AppException
	 */
	public CalendarEvent getCalendarEventById(String eventId, String calendarId)
			throws AppException {
		CalendarEventEntry event = getCalendarEventEntryById(eventId,
				calendarId);
		return GoogleCalendarUtil
				.convertCalendarEventEntryToCalendarEvent(event);
	}

	private CalendarEventEntry getCalendarEventEntryById(String eventId,
			String calendarId) throws AppException {

		List<CalendarEventEntry> eventList = this
				.getCalendarEventEntries(calendarId);
		for (CalendarEventEntry event : eventList) {
			if (event.getIcalUID().equals(eventId)) {
				return event;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param postUrl
	 * @param webContent
	 * @param insertedEntry
	 * @return
	 * @throws AppException
	 */
	public CalendarEvent createCalendarEventGadget(URL postUrl,
			WebContent webContent, CalendarEventEntry insertedEntry)
			throws AppException {
		insertedEntry.setWebContent(webContent);
		CalendarEvent entryEventGadget = this
				.insert(postUrl,
						GoogleCalendarUtil
								.convertCalendarEventEntryToCalendarEvent(insertedEntry));
		return entryEventGadget;
	}

	/**
	 * 
	 * @param title
	 * @param iconUrl
	 * @param contentUrl
	 * @param contentType
	 * @param widthOfPopUpBox
	 * @param heightOfPopUpBox
	 * @param prefrences
	 * @return
	 */
	private WebContent setWebContent(String title, String iconUrl,
			String contentUrl, String contentType, String widthOfPopUpBox,
			String heightOfPopUpBox, Map<String, String> prefrences) {
		WebContent webContent = new WebContent();
		webContent.setTitle(title);
		webContent.setType(contentType);
		webContent.setIcon(iconUrl);
		webContent.setUrl(contentUrl);
		webContent.setWidth(widthOfPopUpBox);
		webContent.setHeight(heightOfPopUpBox);
		webContent.setGadgetPrefs(prefrences);
		return webContent;
	}

	private URL getFeedUrl() throws AppException {
		try {
			return new URL(ALL_POST_URL);
		} catch (MalformedURLException mue) {
			String msg = "Malformed url exception";
			throw new AppException(msg, mue);
		}
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws AppException
	 */
	private List<CalendarEntry> retrieveAllCalendar() throws AppException {
		URL feedUrl = this.getFeedUrl();
		CalendarFeed resultFeed;
		try {
			resultFeed = calendarService.getFeed(feedUrl, CalendarFeed.class);
			return resultFeed.getEntries();
		} catch (ServiceException se) {
			String msg = "Unable to obtain feed";
			throw new AppException(msg, se);
		} catch (IOException io) {
			String msg = "Unable to obtain feed";
			throw new AppException(msg, io);
		}
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param calendarId
	 * @return
	 * @throws AppException
	 */
	private CalendarEntry retrieveCalendarEntryById(String calendarId)
			throws AppException {

		String doodleCalendarUrl = getCalendarFeedUrl(calendarId);
		try {
			CalendarEntry calendarEntry = calendarService.getEntry(new URL(
					doodleCalendarUrl), CalendarEntry.class);
			return calendarEntry;
		} catch (MalformedURLException e) {
			String msg = "Unable to obtain calendarEntry";
			throw new AppException(msg, e);
		} catch (IOException e) {
			String msg = "Unable to obtain calendarEntry";
			throw new AppException(msg, e);
		} catch (ServiceException e) {
			String msg = "Unable to obtain calendarEntry";
			throw new AppException(msg, e);
		}
	}

	/**
	 * Retrieves calendar by id
	 * 
	 * @param calendarId
	 * @return
	 * @throws AppException
	 */
	public Calendar retrieveCalendarById(String calendarId) throws AppException {
		CalendarEntry calEntry = retrieveCalendarEntryById(calendarId);
		return GoogleCalendarUtil.calendarFromCalendarEntry(calEntry);
	}

	private URL getAclUrl(String calendarId) throws AppException {
		URL aclUrl;
		try {
			String url = "https://www.google.com/calendar/feeds/" + adminEmail
					+ "/acl/full/"; // +
									// getIdStringFromFullIdUrl(calendarId);

			System.out.println(url);
			aclUrl = new URL(url);
			return aclUrl;
		} catch (MalformedURLException mue) {
			String message = "The url is malformed";
			throw new AppException(message, mue);
		}

	}

	private AclEntry addAclRole(AclRole role, AclScope scope,
			CalendarEntry calEntry) throws AppException {
		Link link = calEntry.getLink(AclNamespace.LINK_REL_ACCESS_CONTROL_LIST,
				Link.Type.ATOM);
		AclEntry entry = new AclEntry();
		entry.setScope(scope);
		entry.setRole(role);
		try {
			AclEntry newAclEntry = calendarService.insert(
					new URL(link.getHref()), entry);
			System.out.println("aclEntry : " + newAclEntry);
			return newAclEntry;
		} catch (MalformedURLException e) {
			String message = "Unable to add new Acl Role";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (IOException e) {
			String message = "Unable to add new Acl Role";
			log.error(message, e);
			throw new AppException(message, e);
		} catch (ServiceException e) {
			String message = "Unable to add new Acl Role";
			log.error(message, e);
			throw new AppException(message, e);
		}
	}

	/**
	 * Add the user permissions
	 * 
	 * @param roleType
	 * @param username
	 * @return
	 * @throws AppException
	 */
	public AclForCalendar addUserRole(CalendarRoleType roleType,
			String username, String calendarId) throws AppException {
		CalendarEntry calEntry = this.retrieveCalendarEntryById(calendarId);
		String toBeAddedRole = getCalendarAclRoleValue(roleType);
		AclRole aclRole = new AclRole(toBeAddedRole);
		AclScope scope = new AclScope(AclScope.Type.USER, username + "@"
				+ domain);
		AclEntry aclEntry = addAclRole(aclRole, scope, calEntry);
		return GoogleCalendarUtil.convertAclEntriesToAclForCalendar(aclEntry);
	}

	private String getCalendarAclRoleValue(CalendarRoleType roleType) {
		String toBeAddedRole = null;
		if (roleType.toString().equalsIgnoreCase("EDITOR")) {
			toBeAddedRole = CalendarAclRole.EDITOR.getValue();
		} else if (roleType.toString().equalsIgnoreCase("FREEBUSY")) {
			toBeAddedRole = CalendarAclRole.FREEBUSY.getValue();
		} else if (roleType.toString().equalsIgnoreCase("NONE")) {
			toBeAddedRole = CalendarAclRole.NONE.getValue();
		} else if (roleType.toString().equalsIgnoreCase("OVERRIDE")) {
			toBeAddedRole = CalendarAclRole.OVERRIDE.getValue();
		} else if (roleType.toString().equalsIgnoreCase("OWNER")) {
			toBeAddedRole = CalendarAclRole.OWNER.getValue();
		} else if (roleType.toString().equalsIgnoreCase("READ")) {
			toBeAddedRole = CalendarAclRole.READ.getValue();
		} else if (roleType.toString().equalsIgnoreCase("RESPOND")) {
			toBeAddedRole = CalendarAclRole.RESPOND.getValue();
		} else if (roleType.toString().equalsIgnoreCase("ROOT")) {
			toBeAddedRole = CalendarAclRole.ROOT.getValue();
		}

		return toBeAddedRole;
	}

	/**
	 * Add the group permissions
	 * 
	 * @param roleType
	 * @param username
	 * @return
	 * @throws AppException
	 */
	public AclForCalendar addGroupRole(CalendarRoleType roleType,
			UserRoleType group, String calendarId) throws AppException {
		CalendarEntry calEntry = this.retrieveCalendarEntryById(calendarId);
		String toBeAddedRole = getCalendarAclRoleValue(roleType);
		AclRole aclRole = new AclRole(toBeAddedRole);
		AclScope scope = new AclScope(AclScope.Type.GROUP, group.toString()
				+ "@" + domain);
		AclEntry aclEntry = addAclRole(aclRole, scope, calEntry);

		return GoogleCalendarUtil.convertAclEntriesToAclForCalendar(aclEntry);
	}

	/**
	 * 
	 * @param roleType
	 * @param calendarScope
	 * @param calendarId
	 * @return
	 * @throws AppException
	 */
	public AclForCalendar addRoleAndScope(CalendarRoleType roleType,
			CalendarScope calendarScope, String calendarId) throws AppException {
		CalendarEntry calEntry = this.retrieveCalendarEntryById(calendarId);
		String toBeAddedRole = getCalendarAclRoleValue(roleType);
		AclRole aclRole = new AclRole(toBeAddedRole);

		String scopeValue = calendarScope.toString();
		AclScope.Type type = null;
		if (scopeValue.equalsIgnoreCase("user")) {
			type = AclScope.Type.USER;
		} else if (scopeValue.equalsIgnoreCase("default")) {
			type = AclScope.Type.DEFAULT;
		} else if (scopeValue.equalsIgnoreCase("domain")) {
			type = AclScope.Type.DOMAIN;
		} else if (scopeValue.equalsIgnoreCase("group")) {
			type = AclScope.Type.GROUP;
		}

		AclScope scope = new AclScope(type, calendarScope.getScopeValue());
		AclEntry aclEntry = addAclRole(aclRole, scope, calEntry);

		return GoogleCalendarUtil.convertAclEntriesToAclForCalendar(aclEntry);
	}

	/**
	 * @throws ServiceException
	 * @throws IOException
	 * @throws MalformedURLException
	 *             Updates the Acl role for calendar. Note : Only Role can be
	 *             modified and not the scope
	 * 
	 * @param calendarRoleType
	 * @param aclForCalendarId
	 * @param calendarId
	 * @return
	 * @throws AppException
	 * @throws ServiceException
	 * @throws IOException
	 * @throws
	 */
	public AclForCalendar updateAclForCalendar(
			CalendarRoleType calendarRoleType, String aclForCalendarId)
			throws AppException {
		AclEntry aclEntry = getAclEntryById(aclForCalendarId);

		String toBeAddedRole = getCalendarAclRoleValue(calendarRoleType);
		aclEntry.setRole(new AclRole(toBeAddedRole));
		AclEntry updatedEntry;
		try {
			updatedEntry = aclEntry.update();
		} catch (ServiceException se) {
			String message = "unable to retrieve feed for acl";
			throw new AppException(message, se);
		} catch (IOException io) {
			String message = "unable to retrieve feed for acl";
			throw new AppException(message, io);
		}
		return GoogleCalendarUtil
				.convertAclEntriesToAclForCalendar(updatedEntry);
	}

	private AclEntry getAclEntryById(String aclEntryId) throws AppException {

		String feedUrl = getAclFeedUrl(aclEntryId);
		AclEntry aclEntry = null;
		try {
			aclEntry = calendarService.getEntry(new URL(feedUrl),
					AclEntry.class);
			return aclEntry;
		} catch (MalformedURLException e) {
			String msg = "Unable to obtain AclEntry";
			throw new AppException(msg, e);
		} catch (IOException e) {
			String msg = "Unable to obtain AclEntry";
			throw new AppException(msg, e);
		} catch (ServiceException e) {
			String msg = "Unable to obtain AclEntry";
			throw new AppException(msg, e);
		}
	}

	/**
	 * Retrieves the AclFor Calendar object from its id
	 * 
	 * @param aclForCalendarId
	 * @return
	 * @throws AppException
	 */
	public AclForCalendar getAclForCalendarById(String aclForCalendarId)
			throws AppException {
		AclEntry aclEntry = getAclEntryById(aclForCalendarId);
		return GoogleCalendarUtil.convertAclEntriesToAclForCalendar(aclEntry);
	}

	/**
	 * 
	 * @param aclEntryId
	 * @param calendarId
	 * @throws AppException
	 */
	public void deleteAclForCalendar(String aclEntryId) throws AppException {
		AclEntry toBeDeletedEntry = this.getAclEntryById(aclEntryId);
		try {
			toBeDeletedEntry.delete();
		} catch (ServiceException se) {
			String message = "unable to delete the acl";
			throw new AppException(message, se);
		} catch (IOException io) {
			String message = "unable to delete the acl";
			throw new AppException(message, io);
		}
	}

}
