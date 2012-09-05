package com.netkiller.workflow.impl.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.Key;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.Contact;
import com.netkiller.manager.ContactsManager;
import com.netkiller.util.AppLogger;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowExecutionException;
import com.netkiller.workflow.impl.context.BulkContactDeleteWorkflowContext;

public class BulkContactDeleteTask extends AbstractWorkflowTask {

	private static final AppLogger log = AppLogger
			.getLogger(BulkContactDeleteTask.class);

	@Autowired
	private ContactsManager contactsManager;

	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {
		BulkContactDeleteWorkflowContext bulkContactDeleteWorkflowContext = (BulkContactDeleteWorkflowContext) context;
		List<Key> contactKeyList = bulkContactDeleteWorkflowContext
				.getContacts();
		DataContext dataContext = bulkContactDeleteWorkflowContext
				.getDataContext();
		if (contactKeyList == null || contactKeyList.isEmpty()) {
			throw new WorkflowExecutionException("NO contacts to be deleted");
		}

		try {
			List<Contact> contactList = (List<Contact>) contactsManager
					.getByKeys(contactKeyList);
			for (Contact contacts : contactList) {
				contactsManager.deleteContact(contacts, dataContext);
			}
		} catch (AppException e) {
			String msg = "delete operation failed";
			log.error(msg, e);

		}
		return context;
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
				contactsManager.createContact(deletedContact);
			}

		} catch (AppException e) {
			log.error(
					"rollback operation failed. relation could not be re-created",
					e);
		}
	}

}
