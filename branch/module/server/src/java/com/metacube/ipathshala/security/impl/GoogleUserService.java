package com.metacube.ipathshala.security.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.data.appsforyourdomain.AppsForYourDomainException;
import com.google.gdata.data.appsforyourdomain.generic.GenericEntry;
import com.google.gdata.data.appsforyourdomain.generic.GenericFeed;
import com.google.gdata.data.appsforyourdomain.provisioning.UserEntry;
import com.google.gdata.data.appsforyourdomain.provisioning.UserFeed;
import com.google.gdata.util.ServiceException;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.dao.AppUserDao;
import com.metacube.ipathshala.security.AppGroup;
import com.metacube.ipathshala.security.AppUser;
import com.metacube.ipathshala.security.AppUserService;
import com.metacube.ipathshala.security.DomainConfig;
import com.metacube.ipathshala.security.UserAccessInfo;
import com.metacube.ipathshala.security.acl.Acl;
import com.metacube.ipathshala.security.acl.AclManager;
import com.metacube.ipathshala.security.acl.ResourcePermission;
import com.metacube.ipathshala.util.AppLogger;

/**
 * 
 * @author prateek
 * 
 */
@Component("GoogleUserService")
public class GoogleUserService implements AppUserService {
	private static final AppLogger log = AppLogger
			.getLogger(GoogleUserService.class);
	@Autowired
	private AclManager aclManager;

	@Autowired
	private DomainConfig domainConfig;

	@Autowired
	private AppUserDao appUserDao;

	public AclManager getAclManager() {
		return aclManager;
	}

	public void setAclManager(AclManager aclManager) {
		this.aclManager = aclManager;
	}

	@Override
	public void createUser(String id, String givenName, String familyName,
			String password) throws AppException {
		log.debug("inside create user method.");

		try {
			if (StringUtils.isBlank(familyName)) {
				familyName = givenName;
			}
			domainConfig.getDomainClient().createUser(id, givenName,
					familyName, password);
		} catch (IOException ioException) {
			log.error("IOException: Unable to create user - " + id, ioException);
			throw new AppException(
					"IOException: Unable to create user - " + id, ioException);
		} catch (AppsForYourDomainException e) {
			log.error("Unable to create user - " + id, e);
			throw new AppException("Unable to create user - " + id, e);
		} catch (com.google.gdata.util.ServiceException e) {
			throw new AppException("Unable to create user - " + id, e);
		} catch (RuntimeException runtimeException) {
			if (runtimeException.getCause() instanceof IOException) {
				if (this.getAppUser(id) != null) {
					log.error("RuntimeException: User Created with Id - " + id);
				}
			} else {
				log.error("IOException: Unable to create user - " + id,
						runtimeException);
				throw new AppException(
						"RuntimeException: Unable to create user - " + id,
						runtimeException);
			}
		}

	}

	@Override
	public AppUser getUser(String id) throws AppException {
		log.debug("Finding user in app domain for id: " + id);
		AppUser appUser = getAppUser(id);
		if (appUser != null) {
			populateAclToGroups(appUser);
		}
		return appUser;
	}

	@Override
	public AppUser getCurrentUser() throws AppException {
		AppUser appUser = null;
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		if (user != null) {
			String userId = getUserNameOutOfEmail(user.getEmail());
			if (getDomainNameOutOfEmail(user.getEmail()).equals(
					domainConfig.getDomainName())) {
				appUser = getAppUser(userId);
			} else {
				throw new AppException("Invalid Domain User");
			}
			// TODO how will we get the FirstName and Lastname here?
			/*
			 * appUser = new AppUser(getUserNameOutOfEmail(user.getEmail()),
			 * user.getEmail(), user.get, null, user.getNickname());
			 */
			if (appUser != null) {
				populateAclToGroups(appUser);
			}
		}
		return appUser;
	}

	private String getUserNameOutOfEmail(String email) {
		return StringUtils.split(email, "@")[0];
	}

	/**
	 * gets domain name from email.
	 * 
	 * @param email
	 * @return
	 * @throws AppException
	 *             if invalid user Email found.
	 */
	private String getDomainNameOutOfEmail(String email) throws AppException {
		String domainName = null;
		try {
			domainName = StringUtils.split(email, "@")[1];
		} catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
			throw new AppException("Invalid User Email",
					stringIndexOutOfBoundsException);
		} catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
			throw new AppException("Invalid User Email",
					arrayIndexOutOfBoundsException);
		}
		return domainName;
	}

	public List<AppGroup> getUserGroups(String userId) throws AppException {
		log.debug("Finding group for user : " + userId);
		List<AppGroup> appGroups = null;
		try {
			GenericFeed genericFeed = domainConfig.getGroupsService()
					.retrieveGroups(userId, true);

			if (genericFeed != null && genericFeed.getEntries() != null
					&& !genericFeed.getEntries().isEmpty()) {
				appGroups = new ArrayList<AppGroup>();
				for (GenericEntry genericEntry : genericFeed.getEntries()) {
					AppGroup appGroup = toAppGroup(genericEntry);
					appGroups.add(appGroup);
				}
			}
		} catch (AppsForYourDomainException domainException) {
			log.warn(domainException.getMessage());
		} catch (ServiceException serviceException) {
			throw new AppException(
					"ServiceException: Failed to get group for user - "
							+ userId, serviceException);
		} catch (IOException ioException) {
			throw new AppException(
					"IOException: Failed to get group for user - " + userId,
					ioException);
		}

		return appGroups;
	}

	private void populateAclToGroups(AppUser appUser) throws AppException {
		List<AppGroup> appGroups = getUserGroups(appUser.getUserId());
		UserAccessInfo accessInfo = appUser.getAccessInformation();
		if (appGroups != null && !appGroups.isEmpty()) {
			for (AppGroup grp : appGroups) {
				Acl acl = aclManager.getAcl(grp.getGroupName());
				if (acl != null) {
					grp.setAccessControlList(acl);
					if (acl.isDefaultGroup()) {
						appUser.setDefaultAppGroup(grp);
					} else {
						appUser.addAppGroup(grp);
					}
					populateUserAccessInformation(accessInfo, acl);
				}
			}
		}

	}

	private void populateUserAccessInformation(UserAccessInfo accessInfo,
			Acl acl) {
		List<ResourcePermission> resourcePermissionList = acl
				.getPermissionList();
		if (resourcePermissionList != null && !resourcePermissionList.isEmpty()) {
			for (ResourcePermission rp : resourcePermissionList) {
				accessInfo.addResourcePermission(rp);
			}

		}
	}

	public AppGroup getGroup(String id) throws AppException {
		log.debug("Finding group for group id: " + id);
		AppGroup appGroup = null;

		try {
			GenericEntry genericEntry = domainConfig.getGroupsService()
					.retrieveGroup(id);
			if (genericEntry != null) {
				appGroup = toAppGroup(genericEntry);
			}
		} catch (AppsForYourDomainException domainException) {
			log.warn(domainException.getMessage());
		} catch (ServiceException serviceException) {
			throw new AppException(
					"ServiceException: Failed to get group for id - " + id,
					serviceException);
		} catch (IOException ioException) {
			throw new AppException("IOException: Failed to get group for id - "
					+ id, ioException);
		}

		return appGroup;
	}

	private AppGroup toAppGroup(GenericEntry genericEntry) {
		return new AppGroup(genericEntry.getAllProperties().get("groupName"));
	}

	public void addUserToGroup(String userId, String groupName)
			throws AppException {
		try {
			domainConfig.getGroupsService().addMemberToGroup(groupName, userId);
		} catch (IOException ioException) {
			throw new AppException(
					"IOException: Unable to add user to the group - "
							+ groupName, ioException);
		} catch (ServiceException serviceException) {
			throw new AppException(
					"ServiceException: Unable to add user to the group - "
							+ groupName, serviceException);
		}
	}

	@Override
	public AppUser doLogin(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String requestURI = request.getRequestURI();
		UserService userService = UserServiceFactory.getUserService();
		String logoutURL = userService.createLogoutURL(requestURI);
		request.setAttribute("logoutPage", logoutURL);
		AppUser appUser = null;

		if (isUserLoggedIn()) {
			appUser = getCurrentUser();
		} else {
			// log.debug("User is not logged in.");
			String loginURL = userService.createLoginURL(requestURI);
			try {
				((HttpServletResponse) response).sendRedirect(loginURL);
			} catch (IOException e) {
				throw new AppException("Failed to redirect", e);
			}
		}
		return appUser;
	}

	public boolean isUserLoggedIn() {
		boolean loggedIn = false;
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user != null) {
			loggedIn = true;
		}

		return loggedIn;
	}

	@Override
	public String createLoginURL(String requestURI) {
		UserService userService = UserServiceFactory.getUserService();
		return userService.createLoginURL(requestURI);

	}

	@Override
	public String createLogoutURL(String requestURI) {
		UserService userService = UserServiceFactory.getUserService();
		return userService.createLogoutURL(requestURI);
	}

	private AppUser getAppUser(String id) throws AppException {
		AppUser appUser = null;
		UserEntry userEntry;
		try {
			userEntry = domainConfig.getDomainClient().retrieveUser(id);
			if (userEntry != null) {
				String email = userEntry.getLogin().getUserName() + "@"
						+ domainConfig.getDomainName();
				appUser = new AppUser(id, email, userEntry.getName()
						.getGivenName().toString(), userEntry.getName()
						.getFamilyName().toString(), null, null);
			}
		} catch (AppsForYourDomainException domainException) {
			/*
			 * This is Error Code for EntityDoesNotExists. So if this occurs
			 * method returns null reference
			 */
			if (domainException.getErrorCode().getErrorCodeAsInt() != 1301) {
				log.error("Failed to load user for id-" + id, domainException);
			}
		} catch (IOException ioException) {
			log.error("Failed to load user for id-" + id, ioException);
			throw new AppException("IOException: Failed to get user for id - "
					+ id, ioException);
		} catch (com.google.gdata.util.ServiceException e) {
			log.error("Failed to load user for id-" + id, e);
			throw new AppException("Failed to get user for id - " + id, e);
		}

		return appUser;

	}

	/**
	 * Checks whether User with given userId exists in the domain or not
	 * 
	 * @param userId
	 * @return true if the user exists else returns false
	 * @throws AppException
	 */
	public boolean isAppUser(String userId) throws AppException {
		boolean isUser = false;
		if (this.getAppUser(userId) != null)
			isUser = true;
		return isUser;

	}

	public List<AppUser> getAllUsers() throws AppException {
		log.debug("Finding all users : ");
		List<AppUser> appUsers = null;
		AppUser appUser = null;
		try {
			UserFeed genericFeed = domainConfig.getDomainClient()
					.retrieveAllUsers();

			if (genericFeed != null && genericFeed.getEntries() != null
					&& !genericFeed.getEntries().isEmpty()) {
				appUsers = new ArrayList<AppUser>();
				for (UserEntry genericEntry : genericFeed.getEntries()) {
					appUser = new AppUser(
							genericEntry.getLogin().getUserName(), null, null,
							null, null, null);
					appUsers.add(appUser);
				}
			}
		} catch (AppsForYourDomainException domainException) {
			log.warn(domainException.getMessage());
		} catch (ServiceException serviceException) {
			throw new AppException(
					"ServiceException: Failed to get all user - ",
					serviceException);
		} catch (IOException ioException) {
			throw new AppException("IOException: Failed to get all user - ",
					ioException);
		}

		return appUsers;
	}

	public void setDomainConfig(DomainConfig domainConfig) {
		this.domainConfig = domainConfig;
	}

	public void deleteUser(String id) throws AppException {
		log.debug("inside create user method.");

		try {
			domainConfig.getDomainClient().deleteUser(id);
		} catch (IOException ioException) {
			log.error("IOException: Unable to delete user - " + id, ioException);
			throw new AppException(
					"IOException: Unable to delete user - " + id, ioException);
		} catch (AppsForYourDomainException e) {
			log.error("Unable to delete user - " + id, e);
			throw new AppException("Unable to delete user - " + id, e);
		} catch (com.google.gdata.util.ServiceException e) {
			throw new AppException("Unable to delete user - " + id, e);
		} catch (RuntimeException runtimeException) {
			log.error("IOException: Unable to delete user - " + id,
					runtimeException);
			throw new AppException("RuntimeException: Unable to delete user - "
					+ id, runtimeException);

		}

	}

	
}
