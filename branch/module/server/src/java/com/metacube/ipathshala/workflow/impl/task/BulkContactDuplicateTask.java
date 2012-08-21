package com.metacube.ipathshala.workflow.impl.task;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.entity.Contacts;
import com.metacube.ipathshala.manager.ContactsManager;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.workflow.AbstractWorkflowTask;
import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowExecutionException;
import com.metacube.ipathshala.workflow.impl.context.BulkContactDuplicateWorkflowContext;

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
		if (contactKeyList == null || contactKeyList.isEmpty()) {
			throw new WorkflowExecutionException("NO contacts to be duplicated");
		}

		try {
			List<Contacts> contactList = (List<Contacts>) contactsManager
					.getByKeys(contactKeyList);
			for (Contacts contacts : contactList) {
				try {
					contacts = (Contacts) BeanUtils.cloneBean(contacts);
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

			}
		} catch (AppException e) {
			String msg = "duplicate operation failed";
			log.error(msg, e);

		}
		return context;
	}

}
