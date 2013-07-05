package com.netkiller.workflow.impl.task;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gdata.data.contacts.ContactEntry;
import com.netkiller.entity.Workflow;
import com.netkiller.exception.AppException;
import com.netkiller.googleUtil.ContactInfo;
import com.netkiller.manager.WorkflowManager;
import com.netkiller.service.sharedcontacts.SharedContactsService;
import com.netkiller.util.CommonUtil;
import com.netkiller.util.SharedContactsUtil;
import com.netkiller.vo.Customer;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowExecutionException;
import com.netkiller.workflow.WorkflowInfo;
import com.netkiller.workflow.impl.context.AddContactForAllDomainUsersContext;
import com.netkiller.workflow.impl.processor.WorkflowStatusType;

public class CreateContactsInDBTask extends AbstractWorkflowTask {

	@Autowired
	private SharedContactsService sharedContactsService;

	protected final Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private WorkflowManager workflowManager;
	
	public static final Integer FETCH_LIMIT = 5000 ;
	
	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {
		AddContactForAllDomainUsersContext userContext = (AddContactForAllDomainUsersContext) context;
		System.out.println("Start task");
		int start = userContext.getStart();
		String domain = userContext.getDomain();
		int totalContacts = start ;
		Customer domainAdmin = sharedContactsService.getDomainAdminEmail(domain);
		SharedContactsUtil util = SharedContactsUtil.getInstance();
		boolean triggerNextTask = false;
		if(domainAdmin!=null){
			try {
			String groupName = sharedContactsService.getGroupName(domain);
			sharedContactsService.setUserEamil(domainAdmin.getAdminEmail());
			String groupId = sharedContactsService.getSharedContactsGroupId(groupName);
			int totalLimit = 100;
			if (domainAdmin.getAccountType().equalsIgnoreCase("Paid")) {
				totalLimit = 30000;
				triggerNextTask = true;
			} else {
				if (CommonUtil.isTheSecondTypeCustomer(domainAdmin)) {
					totalLimit = 50;
				}
			}
			int contactsToFetch = totalLimit>FETCH_LIMIT ? FETCH_LIMIT : totalLimit;
			System.out.println("fetching contacts from shared api");
				List<ContactEntry> entries = sharedContactsService.getContacts(start, contactsToFetch, groupId, userContext.getIsUseForSharedContacts(), null, domainAdmin.getAdminEmail());
				System.out.println("fetched " + entries.size());				
				if(entries.isEmpty()){
					triggerNextTask = false;
				}else{		
					if(entries.size()<contactsToFetch){
						triggerNextTask = false;
					}
					totalContacts += entries.size();
					for(ContactEntry entry : entries){
						ContactInfo info = util.makeContactInfo(entry);
						info.setDomain(domain);
						sharedContactsService.createContactInfo(info);
					}
				}
				System.out.println("Done");
				
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(triggerNextTask){
				userContext.setStart(totalContacts);
				WorkflowInfo workflowInfo = new WorkflowInfo(
				"CreateContactsInDBWorkflowProcessor");
				workflowInfo.setIsNewWorkflow(true);
		
				Workflow workflow = new Workflow();
				workflow.setContext(context);
				workflow.setWorkflowName(workflowInfo.getWorkflowName());
				workflow.setWorkflowInstanceId(workflowInfo.getWorkflowInstance());
				workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());
		
				context.setWorkflowInfo(workflowInfo);
				workflowManager.triggerWorkflow(workflow);
			}else{
				sharedContactsService.updateContactCount(domain,totalContacts-1);
			}
			
		}
		
		return context;
	}

}
