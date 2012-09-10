package com.netkiller.manager;

import com.netkiller.util.AppLogger;

/**
 * This will manage all communication with google app domain.
 * 
 * @author sabir
 * 
 */

public class DomainServiceManager {

	private static final AppLogger log = AppLogger.getLogger(DomainServiceManager.class);

	
	/*private GoogleDomainService domainService;

	*//**
	 * This will create new user in google app domain under specified group.
	 * 
	 * @param id
	 *            the userId
	 * @param givenName
	 *            the given name of user
	 * @param familyName
	 *            the family name of user
	 * @param password
	 *            the password used by user to login in app domain.
	 * @param groupId
	 *            the group identifier to which this new user will be added.
	 * @throws AppException
	 *             if any exception occur while creating user in app domain or
	 *             while adding the new user to group.
	 *//*
	public void createUserUnderGroup(String id, String givenName, String familyName, String password, String groupId)
			throws AppException {
		if (domainService.getUser(id) != null) {
			throw new AppException("User with id: " + id + " already exist in app domain.");
		}

		domainService.createUser(id, givenName, familyName, password);

		if (domainService.getGroup(groupId) == null) {
			log
					.warn("Unable to add user to the group at this time, as group for group id : '"
							+ groupId
							+ "' does not exist. However user is created successfully, and you can call create group and add user to group operation later saperatly.");
		} else {
			domainService.addUserToGroup(id, groupId);
		}
	}

	*//**
	 * This will return group to which user belong.
	 * 
	 * @param id
	 *            the user identifier.
	 * @return the user group or null if user belongs to no group.
	 * @throws AppException
	 *             if get user group operation fail or user does not exist in
	 *             the app domain.
	 *//*
	public GenericEntry getUserGroup(String id) throws AppException {
		if (domainService.getUser(id) == null) {
			throw new AppException("User with id: " + id + " does not exist in app domain.");
		}

		return domainService.getUserGroup(id);
	}
	
	*//**
	 * This method is temporarily created to get current logged in user information
	 * @return Current Logged in User
	 *//*
	public User getCurrentUser()	{
		
		return domainService.getCurrentUser();
	}
	
	public UserRoleType getCurrentUserRole(String userId) throws AppException	{
		
		
		return domainService.getCurrentUserRole(userId);
	}*/

}
