package com.netkiller.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.netkiller.manager.WorkflowManager;
import com.netkiller.service.sharedcontacts.SharedContactsService;
import com.netkiller.vo.Customer;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowProcessor;

@Controller
public class WorkflowController {
	
	@Autowired
	ApplicationContext applicationContext;
	
	@Autowired
	WorkflowManager workflowManager ;
	
	@Autowired
	private SharedContactsService sharedContactsService;
	
	@RequestMapping("async/task/performworkflowtasks.do")
	public void performWorkflow(Model model, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String workflowinstance = request.getParameter("workflowinstanceid");
		CacheManager cmanager = CacheManager.getInstance();
		Cache cache = cmanager.getCache("WorkflowContextCache");
		WorkflowContext context = (WorkflowContext) cache.get(workflowinstance);
		try {
			WorkflowProcessor workflowprocessor = (WorkflowProcessor) applicationContext
					.getBean(context.getWorkflowInfo().getWorkflowName());
			workflowprocessor.setContext(context);
			workflowprocessor.doTasks();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("sfdc/customers.do")
	public void syncWithSFDC(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, JSONException {
		
		System.err.println(request.getRemoteAddr());
		
		if ("horid121dldytjq".equals(request.getHeader("Authorization"))
				&& request.getHeader("User-Agent").contains("appid: s~sfdc-integration")) {
			JSONArray arrCustomers = new JSONArray();
			JSONObject joCustomer = null;
			JSONObject result = new JSONObject();
			List<Customer> customers = sharedContactsService.getAllCustomers();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for (Customer customer: customers) {
				joCustomer = new JSONObject();
				joCustomer.put("accountType", customer.getAccountType());
				joCustomer.put("adminEmail", customer.getAdminEmail());
				joCustomer.put("domain", customer.getDomain());
				joCustomer.put("id", customer.getId());
				joCustomer.put("registeredDate", customer.getRegisteredDate()!=null?sdf.format(customer.getRegisteredDate()):"");
				joCustomer.put("totalContacts", customer.getTotalContacts());
				joCustomer.put("upgradedDate", customer.getUpgradedDate()!=null?sdf.format(customer.getUpgradedDate()):"");
				
				arrCustomers.put(joCustomer);
			}
			result.put("result", arrCustomers);
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().println(result);
		}
	}
	
	@RequestMapping("/test/testpath.do")
	public String test(Model model, HttpServletRequest request, HttpServletResponse response){
		workflowManager.test();
		return "/sharedcontacts/authorize";
	}
}
