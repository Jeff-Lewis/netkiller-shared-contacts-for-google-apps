package com.netkiller.workflow.impl.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.util.ResourceNotFoundException;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.Contact;
import com.netkiller.entity.UserContact;
import com.netkiller.service.ContactsService;
import com.netkiller.util.AppLogger;
import com.netkiller.util.CommonWebUtil;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowExecutionException;
import com.netkiller.workflow.impl.context.BulkContactDeleteWorkflowContext;

public class BulkContactDeleteTask extends AbstractWorkflowTask {

	private static final AppLogger log = AppLogger
			.getLogger(BulkContactDeleteTask.class);

	@Autowired
	private ContactsService service;

	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {
		BulkContactDeleteWorkflowContext bulkContactDeleteWorkflowContext = (BulkContactDeleteWorkflowContext) context;
		Contact contact = bulkContactDeleteWorkflowContext.getContact();
		List<Key> contactKeyList = bulkContactDeleteWorkflowContext
				.getContactKeyList();
		DataContext dataContext = bulkContactDeleteWorkflowContext
				.getDataContext();
		String userEmail = bulkContactDeleteWorkflowContext.getUserEmail();
		List<UserContact> userContactList = service
				.getUserContactForDomain(CommonWebUtil.getDomain(userEmail));
		List<UserContact> filteredUserContactList = new ArrayList<UserContact>();
		if (userContactList != null && !userContactList.isEmpty()) {
			for (UserContact userContact : userContactList) {
				if (contactKeyList.contains(userContact.getContactKey())) {
					filteredUserContactList.add(userContact);
				}
			}
		}

		for (String userId : service
				.getAllDomainUsersWithReadAndWritePErmissionIncludingAdmin(CommonWebUtil
						.getDomain(userEmail))) {
			if (filteredUserContactList != null
					&& !filteredUserContactList.isEmpty()) {
				try {
					List<ContactEntry> tobeDeletedContactEntryList = new ArrayList<ContactEntry>();
					for (UserContact userContact : filteredUserContactList) {
						ContactEntry tobeDeletedContactEntry = null;
						try {
							tobeDeletedContactEntry = service.getContact(
									userContact.getContactId(),
									userContact.getUserEmail());
							tobeDeletedContactEntryList
									.add(tobeDeletedContactEntry);
						} catch (ResourceNotFoundException e) {
							log.error("Can not find contact for usercontact "
									+ userContact);
							continue;
						}
					}
					service.multipleDeleteUserContacts(
							tobeDeletedContactEntryList, userId + "@"
									+ CommonWebUtil.getDomain(userEmail));
				} catch (AppException e) {
					log.error("Contact Deletion failed");
					e.printStackTrace();
				}
			}

		}

		/*
		 * if (filteredUserContactList != null &&
		 * !filteredUserContactList.isEmpty()) { try { List<ContactEntry>
		 * tobeDeletedContactEntryList = new ArrayList<ContactEntry>(); for
		 * (UserContact userContact : filteredUserContactList) {
		 * 
		 * ContactEntry tobeDeletedContactEntry = null; try {
		 * tobeDeletedContactEntry = service.getContact(
		 * userContact.getContactId(), userContact.getUserEmail());
		 * tobeDeletedContactEntryList .add(tobeDeletedContactEntry); } catch
		 * (ResourceNotFoundException e) {
		 * log.error("Can not find contact for usercontact " + userContact);
		 * continue; } service.delete(tobeDeletedContactEntry,
		 * userContact.getUserEmail());
		 * 
		 * }
		 * 
		 * service.multipleDeleteUserContacts(tobeDeletedContactEntryList ,
		 * userEmail);
		 * 
		 * } catch (AppException e) { log.error("Contact Deletion failed");
		 * e.printStackTrace(); } }
		 */
		/*
		 * if (contactKeyList == null || contactKeyList.isEmpty()) { throw new
		 * WorkflowExecutionException("NO contacts to be deleted"); }
		 * 
		 * try { List<Contact> contactList = (List<Contact>) contactsManager
		 * .getByKeys(contactKeyList); for (Contact contacts : contactList) {
		 * contactsManager.deleteContact(contacts, dataContext); } } catch
		 * (AppException e) { String msg = "delete operation failed";
		 * log.error(msg, e);
		 * 
		 * }
		 */
		return bulkContactDeleteWorkflowContext;
	}

	/**
	 * 
	 * @param deletedStudents
	 */
	private void rollbackDelete(List<Contact> deletedcontacts,
			DataContext dataContext) {
		try {
			for (Contact deletedContact : deletedcontacts) {
				// nullify existing key in the object
				deletedContact.setKey(null);
				service.createContact(deletedContact);
			}

		} catch (AppException e) {
			log.error(
					"rollback operation failed. relation could not be re-created",
					e);
		}
	}

}
