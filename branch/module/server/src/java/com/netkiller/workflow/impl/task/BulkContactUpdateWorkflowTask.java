package com.netkiller.workflow.impl.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.util.ResourceNotFoundException;
import com.netkiller.core.AppException;
import com.netkiller.entity.Contact;
import com.netkiller.entity.UserContact;
import com.netkiller.service.ContactsService;
import com.netkiller.util.AppLogger;
import com.netkiller.util.CommonWebUtil;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowExecutionException;
import com.netkiller.workflow.impl.context.BulkContactUpdateWorkflowContext;

public class BulkContactUpdateWorkflowTask extends AbstractWorkflowTask {

	private static final AppLogger log = AppLogger
			.getLogger(BulkContactUpdateWorkflowTask.class);

	@Autowired
	private ContactsService service;

	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {
		BulkContactUpdateWorkflowContext contactUpdateWorkflowContext = (BulkContactUpdateWorkflowContext) context;
		Contact contact = contactUpdateWorkflowContext.getContact();
		// DataContext dataContext =
		// contactUpdateWorkflowContext.getDataContext();
		String userEmail = contactUpdateWorkflowContext.getUserEmail();
		List<UserContact> userContactList = service
				.getUserContactForDomain(CommonWebUtil.getDomain(userEmail));
		List<UserContact> filteredUserContactList = new ArrayList<UserContact>();
		if (userContactList != null && !userContactList.isEmpty()) {
			for (UserContact userContact : userContactList) {
				if (userContact.getContactKey().equals(contact.getKey())) {
					filteredUserContactList.add(userContact);
				}
			}
		}
		if (filteredUserContactList != null
				&& !filteredUserContactList.isEmpty()) {
			try {
				for (UserContact userContact : filteredUserContactList) {

					ContactEntry tobeUpdatedContactEntry = null;
					try {
						tobeUpdatedContactEntry = service.getContact(
								userContact.getContactId(),
								userContact.getUserEmail());
					} catch (ResourceNotFoundException e) {
						log.error("Can not find contact For userContact"+ userContact);
						continue;
					}
					service.update(tobeUpdatedContactEntry,
							userContact.getUserEmail());

				}
			} catch (AppException e) {
				log.error("Contact Update failed");
				e.printStackTrace();
			}
		}
		return contactUpdateWorkflowContext;
	}

}
