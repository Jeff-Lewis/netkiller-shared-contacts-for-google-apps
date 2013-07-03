package com.netkiller.workflow.impl.task;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.contacts.UserDefinedField;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.FamilyName;
import com.google.gdata.data.extensions.FormattedAddress;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.GivenName;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.OrgDepartment;
import com.google.gdata.data.extensions.OrgName;
import com.google.gdata.data.extensions.OrgTitle;
import com.google.gdata.data.extensions.Organization;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.data.extensions.StructuredPostalAddress;
import com.netkiller.exception.AppException;
import com.netkiller.googleUtil.ContactInfo;
import com.netkiller.service.sharedcontacts.SharedContactsService;
import com.netkiller.util.SharedContactsUtil;
import com.netkiller.vo.StaticProperties;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowExecutionException;
import com.netkiller.workflow.impl.context.AddContactForAllDomainUsersContext;

public class AddContactForAllDomainUsersTask extends AbstractWorkflowTask {

	@Autowired
	private SharedContactsService sharedContactsService;

	protected final Logger logger = Logger.getLogger(getClass().getName());

	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {
		AddContactForAllDomainUsersContext userContext = (AddContactForAllDomainUsersContext) context;
		String domain = userContext.getDomain();
		ContactInfo contactInfo = userContext.getContactInfo();

		for (String userId : sharedContactsService
				.getAllDomainUsersWithReadAndWritePErmissionIncludingAdmin(domain)) {
			System.out.println("contacts creation  uiser id  = " + userId);
			ContactEntry newentry = SharedContactsUtil.getInstance().makeContactEntry(contactInfo);
			String email = userId + "@" + domain;
			String userGroupId = getUserGroupId(email, domain); // added
			// String userGroupId =
			// sharedContactsService.getMyContactsGroupId(userId + "@" +
			// domain);
			if (!StringUtils.isBlank(userGroupId)) {
				GroupMembershipInfo userGmInfo = new GroupMembershipInfo(); // added
				userGmInfo.setHref(userGroupId); // added
				newentry.addGroupMembershipInfo(userGmInfo);
				String myContactsGroupId = sharedContactsService
						.getMyContactsGroupId(email);
				GroupMembershipInfo myContactsGmInfo = new GroupMembershipInfo(); // added
				myContactsGmInfo.setHref(myContactsGroupId); // added
				newentry.addGroupMembershipInfo(myContactsGmInfo);
				try {
					sharedContactsService.createUserContact(newentry, email);
				} catch (AppException e) {
					logger.log(Level.SEVERE,
							"Unable to Create user in workflow");
				}
			}
		}

		return userContext;
	}



	private String getUserGroupId(String email, String domain) {

		String groupId = null;

		try {
			String sharedContactsGroupName = sharedContactsService
					.getGroupName(domain);

			groupId = sharedContactsService.getUserContactsGroupId(
					sharedContactsGroupName, email);
			logger.info("sharedContactsGroupName ===> "
					+ sharedContactsGroupName);
			logger.info("groupId ===> " + groupId);
			while (groupId == null || groupId.equals("")) {
				ContactGroupEntry group = new ContactGroupEntry();
				group.setSummary(new PlainTextConstruct(sharedContactsGroupName));
				group.setTitle(new PlainTextConstruct(sharedContactsGroupName));
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					// TODO: handle exception
				}

				// sharedContactsService.createGroup(group,
				// getCurrentUser(request).getEmail());
				sharedContactsService.createGroup(group, email);

				groupId = sharedContactsService.getUserContactsGroupId(
						sharedContactsGroupName, email);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
		return groupId;
	}

}
