package com.netkiller.workflow.impl.task;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.netkiller.exception.AppException;
import com.netkiller.service.sharedcontacts.SharedContactsService;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowExecutionException;
import com.netkiller.workflow.impl.context.AddInitialContactsAndGroupContext;

public class AddInitialContactsAndGroupTask extends AbstractWorkflowTask{

	
	@Autowired
	private SharedContactsService sharedContactsService;
	
	protected final Logger logger = Logger.getLogger(getClass().getName());
	
	@Override
	public WorkflowContext execute(WorkflowContext context) throws WorkflowExecutionException {
		AddInitialContactsAndGroupContext groupContext = (AddInitialContactsAndGroupContext)context;
		String domain = groupContext.getDomain();
		String group = groupContext.getGroup();
		String email = groupContext.getEmail();
		
		for (String userId : sharedContactsService.getAllDomainUsersIncludingAdmin(domain)) {
			List<ContactEntry> contactEntries = new ArrayList<ContactEntry>();
			for (ContactEntry entry : makeInitialContacts()) {
				String userGroupId = getUserGroupId(userId + "@" + domain,group); // added
				GroupMembershipInfo userGmInfo = new GroupMembershipInfo(); // added
				userGmInfo.setHref(userGroupId); // added
				entry.addGroupMembershipInfo(userGmInfo);
				contactEntries.add(entry);
			}
			
			try {
				sharedContactsService.multipleCreateUserContacts(contactEntries, email);
			} catch (AppException e) {
				logger.log(Level.SEVERE,"Error while creating multiple Contact entries");
			}
		
	}
		return groupContext;

}

	private List<ContactEntry> makeInitialContacts() {
			List<ContactEntry> contactEntries = new ArrayList<ContactEntry>();
			contactEntries.add(sharedContactsService.makeContact("Netkiller Support", "Netkiller", "Support", "Support", "support@netkiller.com",
					"1-424-785-0180", "2033 Gateway Place, Ste 500, San Jose, CA 95110"));
			contactEntries.add(sharedContactsService.makeContact("Netkiller Sales", "Netkiller", "Sales", "Sales", "sales@netkiller.com",
					"1-424-785-0180", "2033 Gateway Place, Ste 500, San Jose, CA 95110"));
			contactEntries.add(sharedContactsService.makeContact("Netkiller Korea", "Netkiller", "Korea", "Support", "support@netkiller.com",
					"82-2-2052-0453", "16F, Gangnam BLDG.,1321-1 Seocho-dong, Seocho-gu, Seoul 137070, South Korea"));
			return contactEntries;
	}
	
	private String getUserGroupId(String email, String groupName) {

		String groupId = null;

		try {
			String sharedContactsGroupName = groupName;

			groupId = sharedContactsService.getUserContactsGroupId(sharedContactsGroupName, email);
			logger.info("sharedContactsGroupName ===> " + sharedContactsGroupName);
			logger.info("groupId ===> " + groupId);
			while (groupId == null || groupId.equals("")) {
				ContactGroupEntry group = new ContactGroupEntry();
				group.setSummary(new PlainTextConstruct(sharedContactsGroupName));
				group.setTitle(new PlainTextConstruct(sharedContactsGroupName));
				// sharedContactsService.createGroup(group,
				// getCurrentUser(request).getEmail());
				sharedContactsService.createGroup(group, email);

				groupId = sharedContactsService.getUserContactsGroupId(sharedContactsGroupName, email);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
		return groupId;
	}

}
	
