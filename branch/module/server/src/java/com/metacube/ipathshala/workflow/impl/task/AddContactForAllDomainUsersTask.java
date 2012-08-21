package com.metacube.ipathshala.workflow.impl.task;

import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.entity.Contacts;
import com.metacube.ipathshala.service.ContactsService;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.workflow.AbstractWorkflowTask;
import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowExecutionException;
import com.metacube.ipathshala.workflow.impl.context.AddContactForAllDomainUsersContext;

public class AddContactForAllDomainUsersTask extends AbstractWorkflowTask {

	private static final AppLogger log = AppLogger
			.getLogger(AddContactForAllDomainUsersTask.class);

	@Autowired
	private ContactsService service;

	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {
		AddContactForAllDomainUsersContext userContext = (AddContactForAllDomainUsersContext) context;
		String domain = userContext.getDomain();
		Contacts contacts = userContext.getContactInfo();
/*
		for (String userId : service
				.getAllDomainUsersWithReadAndWritePErmissionIncludingAdmin(domain)) {
			System.out.println("contacts creation  uiser id  = " + userId);
			ContactEntry newentry = makeContact(contacts);
			String userGroupId = getUserGroupId(userId + "@" + domain, domain); // added

			GroupMembershipInfo userGmInfo = new GroupMembershipInfo(); // added
			userGmInfo.setHref(userGroupId); // added
			newentry.addGroupMembershipInfo(userGmInfo);
			try {
				sharedContactsService.createUserContact(newentry, userId + "@"
						+ domain);
			} catch (AppException e) {
				logger.log(Level.SEVERE, "Unable to Create user in workflow");
			}
		}*/

		return null;
	}

}
