package com.metacube.ipathshala.workflow.impl.task;

import java.util.List;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gdata.data.contacts.ContactEntry;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.service.ContactsService;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.workflow.AbstractWorkflowTask;
import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowExecutionException;
import com.metacube.ipathshala.workflow.impl.context.SyncUserContactsContext;

public class SyncUserContactsTask extends AbstractWorkflowTask {

	@Autowired
	private final static AppLogger log = AppLogger
			.getLogger(SyncUserContactsTask.class);

	@Autowired
	private ContactsService service;

	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {
		SyncUserContactsContext syncUserContactsContext = (SyncUserContactsContext) context;
		String usermail = syncUserContactsContext.getUserEmail();
		List<ContactEntry> entries = null;
		try {
			entries = service.getContacts(1,
					syncUserContactsContext.getTotalLimit(),
					syncUserContactsContext.getGroupId(),
					syncUserContactsContext.getIsUseForSharedContacts(), null);
		} catch (AppException e) {
			log.error("Error while fecthing Contact entries");
		}
		service.syncUserContacts(usermail, entries);
		return context;
	}

}
