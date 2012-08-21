package com.metacube.ipathshala.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.UserRoleType;
import com.metacube.ipathshala.security.AppUser;
import com.metacube.ipathshala.security.DomainConfig;
import com.metacube.ipathshala.security.impl.GoogleUserService;

public class UserUtil {
	
	private static final AppLogger log = AppLogger.getLogger(UserUtil.class);

	public static boolean isValidUserId(String userId) {
		boolean isValid = false;
		Pattern pattern = Pattern.compile("^[a-z0-9._-]{4,20}$");
		if (userId.length() >= 4 || userId.length() <= 20) {
			if (!StringUtils.isNumeric(userId)
					&& pattern.matcher(userId).matches())
				isValid = true;
		}
		return isValid;
	}

	public static Collection<String> getRelatedUserIdSuggestions(
			String firstName, String lastName) {
		Collection<String> userIds = new ArrayList<String>();
		firstName = firstName.toLowerCase();
		if (!StringUtils.isBlank(lastName)) {
			lastName = lastName.toLowerCase();
			userIds.add(firstName + "." + lastName);
			userIds.add(lastName + "." + firstName);
			userIds.add(firstName + Character.toString(lastName.charAt(0)));
			userIds.add(Character.toString(firstName.charAt(0)) + lastName);
			userIds.add(firstName + lastName);
		}else	{
			lastName="";
		}
		
		userIds.add(lastName + firstName);
		
		return userIds;

	}

	public static Collection<String> getRandomUserIdSuggestions(
			String firstName, String lastName, String userId) {
		firstName = firstName.toLowerCase();
		if(StringUtils.isBlank(lastName))	{
			lastName="";
		}
		else{
			lastName = lastName.toLowerCase();
		}
		
		int numberOfSuggestions = 10;
		Collection<String> userIds = new ArrayList<String>();
		for (int i = 0; i < numberOfSuggestions; i++) {
			switch (i % 5) {
			case 0:
				if (!(StringUtils.isBlank(userId)))
					userIds.add(userId + RandomStringUtils.randomNumeric(2));
				else
					numberOfSuggestions++;
				break;
			case 1:
				if (!(StringUtils.isBlank(firstName) ))
					userIds.add(firstName + RandomStringUtils.randomNumeric(1)
							+ lastName);
				else
					numberOfSuggestions++;
				break;
			case 2:
				if (!(StringUtils.isBlank(firstName) || StringUtils
						.isBlank(lastName)))
					userIds.add(lastName + RandomStringUtils.randomNumeric(1)
							+ firstName);
				else
					numberOfSuggestions++;
				break;
			case 3:
				if (!(StringUtils.isBlank(firstName) ))
					if (firstName.length() + lastName.length() < 6) {
						userIds.add(lastName
								+ firstName
								+ RandomStringUtils
										.randomNumeric(6 - (firstName.length() + lastName
												.length())));
					} else
						numberOfSuggestions++;
				break;

			case 4:
				if (!(StringUtils.isBlank(firstName) || StringUtils
						.isBlank(lastName)))
					if (firstName.length() + lastName.length() > 20) {
						if (lastName.length() < 20) {
							firstName = firstName.substring(0,
									20 - lastName.length() - 2);
						} else {
							lastName = lastName.substring(0, 9);
							if (firstName.length() > 9) {
								firstName = firstName.substring(0, 9);
							}
						}
					} else
						numberOfSuggestions++;
				break;

			}

		}
		return userIds;
	}

	/*
	 * utility to retrieve all users and delete them
	 */
	public static void main(String args[]) {
		GoogleUserService userService = new GoogleUserService();
		DomainConfig config = new DomainConfig();
		config.setDomainName("xavierjaipur.org");
		config.setDomainAdminEmail("admin@xavierjaipur.org");
		config.setDomainAdminPassword("xavier");
		try {
			config.init();
			userService.setDomainConfig(config);

			Collection<AppUser> users = userService.getAllUsers();
			System.out.println(users.size());
			/*
			 * for(AppUser user : users){
			 * //System.out.println(user.getUserId()); try{
			 * userService.deleteUser(user.getUserId()); }catch (AppException e)
			 * {e.printStackTrace();} }
			 */

			/*
			 * userService.deleteUser("yokozuna"); AppUser deletedUser =
			 * userService.getUser("yokozuna");
			 * System.out.println(deletedUser!=null
			 * ?deletedUser.getUserId():null);
			 */

		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static AppUser getCurrentUser(HttpSession session)	{
		AppUser appUser = (AppUser) session.getAttribute(AppUser.APPUSER_SESSION_VAR);
		if(appUser==null)	{
			log.error("User not logged in");
		}
		return appUser;
	}
	
	public static UserRoleType getUserRoleType(AppUser appUser) throws AppException	{
		
		UserRoleType userRoleType = null;
		String groupName = null;
		if (appUser != null && appUser.getDefaultAppGroup() != null) {
			groupName = appUser.getDefaultAppGroup().getGroupName();
			userRoleType = UserRoleType.get(groupName);
		}
		if (userRoleType == null) {
			String message = "User do not have any group assigned";
			log.error(message);
			throw new AppException(message);
		}
		return userRoleType;
	}
	
public static UserRoleType getUserRoleType(HttpSession session) throws AppException	{
		AppUser appUser = getCurrentUser(session);
		UserRoleType userRoleType = null;
		String groupName = null;
		if (appUser != null && appUser.getDefaultAppGroup() != null) {
			groupName = appUser.getDefaultAppGroup().getGroupName();
			userRoleType = UserRoleType.get(groupName);
		}
		if (userRoleType == null) {
			String message = "User do not have any group assigned";
			log.error(message);
			throw new AppException(message);
		}
		return userRoleType;
	}
}
