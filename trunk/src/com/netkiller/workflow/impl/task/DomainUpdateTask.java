package com.netkiller.workflow.impl.task;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.netkiller.controller.StatisticsController;
import com.netkiller.service.sharedcontacts.SharedContactsService;
import com.netkiller.vo.Customer;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowExecutionException;

public class DomainUpdateTask extends AbstractWorkflowTask {

	@Autowired
	private SharedContactsService sharedContactsService;

	@Autowired
	private StatisticsController statisticsController;

	protected final Logger logger = Logger.getLogger(getClass().getName());

	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {

		// List<Customer> customers = sharedContactsService.getAllCustomers();
		List<String> domains = sharedContactsService.getDomainList();
		for (String domainName : domains) {
			try {
				List<String> list = sharedContactsService
						.getAllDomainUsers(domainName);
				if (list == null || list.isEmpty()) {
System.out.println("deleting domain ");
//sharedContactsService.removeDomainAdmin(domainName);

				} else {
					statisticsController.updateTotalUsers(domainName);
					statisticsController.updateNSCUsers(domainName);
				}
			} catch (Exception e) {
				System.out.println("error updating " + domainName);
				// TODO: handle exception
			}			
		}
		System.out.println("domains updated");
		return context;
	}

}
