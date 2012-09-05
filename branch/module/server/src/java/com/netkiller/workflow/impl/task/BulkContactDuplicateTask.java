package com.netkiller.workflow.impl.task;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.Contact;
import com.netkiller.manager.ContactsManager;
import com.netkiller.util.AppLogger;
import com.netkiller.util.CommonWebUtil;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowExecutionException;
import com.netkiller.workflow.impl.context.BulkContactDuplicateWorkflowContext;

public class BulkContactDuplicateTask extends AbstractWorkflowTask {

	private static final AppLogger log = AppLogger
			.getLogger(BulkContactDeleteTask.class);

	@Autowired
	private ContactsManager contactsManager;

	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {
		BulkContactDuplicateWorkflowContext bulkContactDplicateWorkflowContext = (BulkContactDuplicateWorkflowContext) context;

		List<Key> contactKeyList = bulkContactDplicateWorkflowContext
				.getContacts();
		DataContext dataContext = bulkContactDplicateWorkflowContext
				.getDataContext();

		String domain = bulkContactDplicateWorkflowContext.getDomain();

		if (contactKeyList == null || contactKeyList.isEmpty()) {
			throw new WorkflowExecutionException("NO contacts to be duplicated");
		}

		try {
			List<Contact> contactList = (List<Contact>) contactsManager
					.getByKeys(contactKeyList);
			for (Contact contacts : contactList) {
				try {
					contacts = (Contact) BeanUtils.cloneBean(contacts);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				contacts.setKey(null);
				contacts.setFirstName(contacts.getFirstName() + "-copy");
				contactsManager.createContact(contacts);
				contactsManager.addContactForAllDomainUsers(domain, contacts);

			}
		} catch (AppException e) {
			String msg = "duplicate operation failed";
			log.error(msg, e);

		}
		return context;
	}

}
