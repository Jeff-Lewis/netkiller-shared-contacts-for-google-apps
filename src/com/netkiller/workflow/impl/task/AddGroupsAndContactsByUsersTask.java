package com.netkiller.workflow.impl.task;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.netkiller.exception.AppException;
import com.netkiller.service.sharedcontacts.SharedContactsService;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowExecutionException;
import com.netkiller.workflow.impl.context.AddGroupsAndContactsByUsersContext;

public class AddGroupsAndContactsByUsersTask extends AbstractWorkflowTask{

	@Autowired
	private SharedContactsService sharedContactsService;
	
	protected final Logger logger = Logger.getLogger(getClass().getName());

	@Override
	public WorkflowContext execute(WorkflowContext context) throws WorkflowExecutionException {
		AddGroupsAndContactsByUsersContext usersContext = (AddGroupsAndContactsByUsersContext) context;
		String domain = usersContext.getDomain();
		List<String> users = usersContext.getUsers();
		try {
			sharedContactsService.addGroupsAndContactsByUsers(users, domain);
		} catch (AppException e) {
			logger.log(Level.SEVERE,"Error while fecthing Contact entries");
		}
		return null;
	}
	
	

}
