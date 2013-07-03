package com.netkiller.workflow.impl.task;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gdata.data.contacts.ContactEntry;
import com.netkiller.exception.AppException;
import com.netkiller.googleUtil.ContactInfo;
import com.netkiller.service.sharedcontacts.SharedContactsService;
import com.netkiller.util.CommonUtil;
import com.netkiller.util.SharedContactsUtil;
import com.netkiller.vo.Customer;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowExecutionException;
import com.netkiller.workflow.impl.context.AddContactForAllDomainUsersContext;

public class CreateContactsInDBTask extends AbstractWorkflowTask {

	@Autowired
	private SharedContactsService sharedContactsService;

	protected final Logger logger = Logger.getLogger(getClass().getName());
	
	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {
		AddContactForAllDomainUsersContext userContext = (AddContactForAllDomainUsersContext) context;
		String domain = userContext.getDomain();
		Customer domainAdmin = sharedContactsService.getDomainAdminEmail(domain);
		SharedContactsUtil util = SharedContactsUtil.getInstance();
		if(domainAdmin!=null){
			try {
			String groupName = sharedContactsService.getGroupName(domain);
			sharedContactsService.setUserEamil(domainAdmin.getAdminEmail());
			String groupId = sharedContactsService.getSharedContactsGroupId(groupName);
			int totalLimit = 100;
			if (domainAdmin.getAccountType().equalsIgnoreCase("Paid")) {
				totalLimit = 30000;
			} else {
				if (CommonUtil.isTheSecondTypeCustomer(domainAdmin)) {
					totalLimit = 50;
				}
			}
			
				List<ContactEntry> entries = sharedContactsService.getContacts(1, totalLimit, groupId, userContext.getIsUseForSharedContacts(), null, domainAdmin.getAdminEmail());
				sharedContactsService.updateContactCount(domain,entries.size());
				for(ContactEntry entry : entries){
					ContactInfo info = util.makeContactInfo(entry);
					info.setDomain(domain);
					sharedContactsService.createContactInfo(info);
				}
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		return context;
	}

}
