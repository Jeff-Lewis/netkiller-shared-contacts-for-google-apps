package com.netkiller.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.netkiller.core.AppException;
import com.netkiller.manager.DomainServiceManager;

/**
 * This will handle user request, outside of that request. Request will be
 * passed to this controller by app engine, as configured by user for background
 * processing.
 * 
 * <p>
 * <b>Note:</b> do not use this controller to handle user's direct request, app
 * engine will automatically forward any background task request by user to this
 * controller.
 * </p>
 * 
 * @author sabir
 * 
 */
public class QueueTaskController {

	/*@Autowired
	private DomainServiceManager domainServiceManager;

	@RequestMapping("/async/task/createUserInAppDomain.do")
	public void createUserInAppDomain(HttpServletRequest request) throws AppException {
		String userId = request.getParameter("userId");
		String givenName = request.getParameter("firstName");
		String familyName = request.getParameter("lastName");
		String password = request.getParameter("passwd");
		String groupId = request.getParameter("groupId");

		domainServiceManager.createUserUnderGroup(userId, givenName, familyName, password, groupId);

	}*/
}
