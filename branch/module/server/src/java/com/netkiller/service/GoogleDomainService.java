/*package com.netkiller.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sample.appsforyourdomain.AppsForYourDomainClient;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.appsforyourdomain.AppsGroupsService;
import com.google.gdata.data.appsforyourdomain.AppsForYourDomainException;
import com.google.gdata.data.appsforyourdomain.generic.GenericEntry;
import com.google.gdata.data.appsforyourdomain.generic.GenericFeed;
import com.google.gdata.data.appsforyourdomain.provisioning.UserEntry;
import com.google.gdata.util.ServiceException;
import com.netkiller.core.AppException;
import com.netkiller.core.UserRoleType;
import com.netkiller.util.AppLogger;

*//**
 * The service class that will interact with google app domain to fulfil any
 * request by this application.
 * 
 * @author sabir
 * 
 *//*
public class GoogleDomainService {

	private static final AppLogger log = AppLogger.getLogger(GoogleDomainService.class);

	private String domainName;

	private String domainAdminEmail;

	private String domainAdminPassword;
	
	private String dataAdminEmail;

	private String dataAdminPassword;


	public String getDataAdminEmail() {
		return dataAdminEmail;
	}

	public void setDataAdminEmail(String dataAdminEmail) {
		this.dataAdminEmail = dataAdminEmail;
	}

	public String getDataAdminPassword() {
		return dataAdminPassword;
	}

	public void setDataAdminPassword(String dataAdminPassword) {
		this.dataAdminPassword = dataAdminPassword;
	}

	
	 * client to communicate with google app domain.
	 
	private AppsForYourDomainClient domainClient;

	
	 * Group service to handle group related request for google app domain.
	 
	private AppsGroupsService groupsService;
	

	*//**
	 * This will initialise various google app domain related services.
	 * 
	 * @throws AppException
	 *//*
	public void init() throws AppException {
		try {
			log.debug("Domain detail: " + domainName + ", " + domainAdminEmail);
			domainClient = new AppsForYourDomainClient(domainAdminEmail, domainAdminPassword, domainName);
			groupsService = domainClient.getGroupService();
		} catch (Exception e) {
			throw new AppException("Unable to create domain client.", e);
		}
	}

	*//**
	 * This will call google app domain group service to create new group in app
	 * domain.
	 * 
	 * @param id
	 *            the group identifier.
	 * @param name
	 *            the name of group.
	 * @param description
	 *            the description of group.
	 * @return the groupId of newly created group.
	 * @throws AppException
	 *             if group creation fail.
	 *//*
	public String createGroup(String id, String name, String description) throws AppException {

		try {
			groupsService.createGroup(id + "@" + domainName, name, description, "");
		} catch (ServiceException serviceException) {
			throw new AppException("ServiceException: Unable to create group - " + id, serviceException);
		} catch (IOException ioException) {
			throw new AppException("IOException: Unable to create group - " + id, ioException);
		}

		return id + "@" + domainName;
	}

	*//**
	 * This will call google app domain group service to get the domain group
	 * for provided id.
	 * 
	 * @param id
	 *            the group identifier.
	 * @return the GenericEntry, google API don't have GroupEntry for now.
	 * @throws AppException
	 *             if get group process interrupted
	 *//*
	public GenericEntry getGroup(String id) throws AppException {
		log.debug("Finding group for group id: " + id);
		GenericEntry genericEntry = null;
		
		try {
			genericEntry = groupsService.retrieveGroup(id);
		} catch (AppsForYourDomainException domainException) {
			log.warn(domainException.getMessage());
		} catch (ServiceException serviceException) {
			throw new AppException("ServiceException: Failed to get group for id - " + id, serviceException);
		} catch (IOException ioException) {
			throw new AppException("IOException: Failed to get group for id - " + id, ioException);
		}

		return genericEntry;
	}

	*//**
	 * This will call google app domain group service to get the domain group of
	 * user.
	 * 
	 * @param userId
	 *            the user identifier.
	 * @return group to which user belong.
	 * @throws AppException
	 *             if get group process interrupted
	 *//*
	public GenericEntry getUserGroup(String userId) throws AppException {
		log.debug("Finding group for user : " + userId);

		GenericEntry genericEntry = null;

		try {
			GenericFeed genericFeed = groupsService.retrieveGroups(userId, true);
			// TODO: Presently we consider that user belong to one group only.
			if (!genericFeed.getEntries().isEmpty()) {
				genericEntry = genericFeed.getEntries().get(0);
			}
		} catch (AppsForYourDomainException domainException) {
			log.warn(domainException.getMessage());
		} catch (ServiceException serviceException) {
			throw new AppException("ServiceException: Failed to get group for user - " + userId, serviceException);
		} catch (IOException ioException) {
			throw new AppException("IOException: Failed to get group for user - " + userId, ioException);
		}

		return genericEntry;
	}

	*//**
	 * This will call google domain service to create new user inside app
	 * domain.
	 * 
	 * @param id
	 *            the user identifier.
	 * @param givenName
	 *            the given name.
	 * @param familyName
	 *            the family name.
	 * @param password
	 *            the password for login.
	 * @throws AppException
	 *             if user creation fail.
	 *//*
	public void createUser(String id, String givenName, String familyName, String password) throws AppException {
		log.debug("inside create user method.");

		try {
			domainClient.createUser(id, givenName, familyName, password);
		} catch (ServiceException serviceException) {
			throw new AppException("ServiceException: Unable to create user - " + id, serviceException);
		} catch (IOException ioException) {
			throw new AppException("IOException: Unable to create user - " + id, ioException);
		}
	}

	*//**
	 * This will call the google app domain service to find the user for
	 * provided id.
	 * 
	 * @param id
	 *            the user identifier.
	 * @return the UserEntry
	 * @see UserEntry
	 * @throws AppException
	 *             if get user process interrupted
	 *//*
	public UserEntry getUser(String id) throws AppException {
		log.debug("Finding user in app domain for id: " + id);
		UserEntry userEntry = null;
		
		try {
			userEntry = domainClient.retrieveUser(id);
		} catch (AppsForYourDomainException domainException) {
			log.warn(domainException.getMessage());
		} catch (IOException ioException) {
			throw new AppException("IOException: Failed to get user for id - " + id, ioException);
		} catch (ServiceException serviceException) {
			throw new AppException("ServiceException: Failed to get user for id - " + id, serviceException);
		}

		return userEntry;
	}

	*//**
	 * This will call google app domain group service to add any user to any
	 * group.
	 * 
	 * @param userId
	 *            the user identifier to find the user to add.
	 * @param groupId
	 *            the group identifier to find the group.
	 * @throws AppException
	 *             if adding user to group operation fails.
	 *//*
	public void addUserToGroup(String userId, String groupId) throws AppException {
		try {
			groupsService.addMemberToGroup(groupId, userId);
		} catch (IOException ioException) {
			throw new AppException("IOException: Unable to add user to the group - " + groupId, ioException);
		} catch (ServiceException serviceException) {
			throw new AppException("ServiceException: Unable to add user to the group - " + groupId, serviceException);
		}
	}

	*//**
	 * This will call google app domain group service to add a group under a
	 * group, or to create sub groups.
	 * 
	 * @param parentGroupId
	 *            the parent group identifier under which the group will be
	 *            added.
	 * @param subGroupId
	 *            the sub group identifier.
	 * @throws AppException
	 *             if operation fail.
	 *//*
	public void addGroupUnderGroup(String parentGroupId, String subGroupId) throws AppException {
		try {
			groupsService.addMemberToGroup(parentGroupId, subGroupId);
		} catch (IOException ioException) {
			throw new AppException("IOException: Unable to add group under the group - " + parentGroupId, ioException);
		} catch (ServiceException serviceException) {
			throw new AppException("ServiceException: Unable to add group under the group - " + parentGroupId,
					serviceException);
		}
	}
	
	*//**
	 * This method is temporarily created to get current logged in user information
	 * @return Current Logged in User
	 *//*
	public User getCurrentUser()	{
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}
	
	
	
	public UserRoleType getCurrentUserRole(String userId) throws AppException	{
		List<GenericEntry> genericEntries = null;
		UserRoleType role = null;
		try {
			GenericFeed genericFeed = groupsService.retrieveGroups(userId, true);
			// TODO: Presently we consider that user belong to one group only.
			if (!genericFeed.getEntries().isEmpty()) {
				genericEntries = genericFeed.getEntries();
			}
			List<String> groups = new ArrayList<String>();
			for(GenericEntry entry:genericEntries)	{
				groups.add(entry.getAllProperties().get("groupName"));
			}
			if(groups.contains(UserRoleType.TEACHER.toString()))
				role = UserRoleType.TEACHER;
			else if(groups.contains(UserRoleType.ADMIN.toString()))
				role = UserRoleType.ADMIN;
			else if(groups.contains(UserRoleType.STUDENT.toString()))
				role = UserRoleType.TEACHER;
			else if(groups.contains(UserRoleType.PARENT.toString()))
				role = UserRoleType.PARENT;
			else if(groups.contains(UserRoleType.DOMAIN.toString()))
				role = UserRoleType.DOMAIN;
							
		} catch (AppsForYourDomainException domainException) {
			log.warn(domainException.getMessage());
		} catch (ServiceException serviceException) {
			throw new AppException("ServiceException: Failed to get group for user - " + userId, serviceException);
		} catch (IOException ioException) {
			throw new AppException("IOException: Failed to get group for user - " + userId, ioException);
		}
		 
		return role;
	}

	

	*//**
	 * @return the domainName
	 *//*
	public String getDomainName() {
		return domainName;
	}

	*//**
	 * @param domainName
	 *            the domainName to set
	 *//*
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	*//**
	 * @return the domainAdminEmail
	 *//*
	public String getDomainAdminEmail() {
		return domainAdminEmail;
	}

	*//**
	 * @param domainAdminEmail
	 *            the domainAdminEmail to set
	 *//*
	public void setDomainAdminEmail(String domainAdminEmail) {
		this.domainAdminEmail = domainAdminEmail;
	}

	*//**
	 * @return the domainAdminPassword
	 *//*
	public String getDomainAdminPassword() {
		return domainAdminPassword;
	}

	*//**
	 * @param domainAdminPassword
	 *            the domainAdminPassword to set
	 *//*
	public void setDomainAdminPassword(String domainAdminPassword) {
		this.domainAdminPassword = domainAdminPassword;
	}

}
*/