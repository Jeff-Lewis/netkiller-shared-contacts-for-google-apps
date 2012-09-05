package com.netkiller.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netkiller.core.AppException;

public interface AppUserService {

	void createUser(String id, String givenName, String familyName, String password) throws AppException;

	AppUser getUser(String id) throws AppException;

	public AppUser getCurrentUser() throws AppException;

	public AppUser doLogin(HttpServletRequest request, HttpServletResponse response) throws AppException;

	public String createLogoutURL(String requestURI);

	public String createLoginURL(String requestURI);

	public boolean isUserLoggedIn();

	public List<AppGroup> getUserGroups(String uniqueUserId) throws AppException;

	void addUserToGroup(String userId, String groupName) throws AppException;

}
