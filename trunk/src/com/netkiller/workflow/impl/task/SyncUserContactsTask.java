package com.netkiller.workflow.impl.task;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gdata.data.contacts.ContactEntry;
import com.netkiller.exception.AppException;
import com.netkiller.service.sharedcontacts.SharedContactsService;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowExecutionException;
import com.netkiller.workflow.impl.context.SyncUserContactsContext;

public class SyncUserContactsTask extends AbstractWorkflowTask{
	
	@Autowired
	private SharedContactsService sharedContactsService;
	
	protected final Logger logger = Logger.getLogger(getClass().getName());

	@Override
	public WorkflowContext execute(WorkflowContext context) throws WorkflowExecutionException {
		
		SyncUserContactsContext syncUserContactsContext = (SyncUserContactsContext)context;
		String usermail = syncUserContactsContext.getUserEmail();
		List<ContactEntry> entries = null;
		try {
			System.out.println("group Id = " +   syncUserContactsContext.getGroupId());
			entries = sharedContactsService.getContacts(1, syncUserContactsContext.getTotalLimit(), syncUserContactsContext.getGroupId(), syncUserContactsContext.getIsUseForSharedContacts(), null);
			System.out.println("sync contacts " + entries);
		} catch (AppException e) {
			logger.log(Level.SEVERE,"Error while fecthing Contact entries");
		}
		
		if (entries!=null) {
			sharedContactsService.syncUserContacts(usermail, entries);
		}
		return context;	
	}

}
