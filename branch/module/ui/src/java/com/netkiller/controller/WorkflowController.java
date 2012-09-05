package com.netkiller.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.netkiller.GridRequest;
import com.netkiller.UICommonConstants;
import com.netkiller.core.AppException;
import com.netkiller.entity.Workflow;
import com.netkiller.manager.WorkflowManager;
import com.netkiller.search.SearchResult;
import com.netkiller.util.AppLogger;
import com.netkiller.util.GridRequestParser;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowProcessor;
import com.netkiller.workflow.impl.processor.WorkflowStatusType;

/**
 * @author dhruvsharma
 * 
 *         This controller handles the execution of the work flow tasks when
 *         they are put in the Queue
 * 
 */
@Controller
public class WorkflowController extends AbstractController {

	private static final AppLogger log = AppLogger.getLogger(WorkflowController.class);

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	WorkflowManager workflowManager;

	@Resource
	GridRequestParser gridRequestParser;

	@RequestMapping("/async/task/performworkflowtasks.do")
	public void performWorkflow(Model model, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String workflowinstance = request.getParameter("workflowinstanceid");
		CacheManager cmanager = CacheManager.getInstance();
		Cache cache = cmanager.getCache("WorkflowContextCache");
		WorkflowContext context = (WorkflowContext) cache.get(workflowinstance);
		WorkflowProcessor workflowprocessor = (WorkflowProcessor) applicationContext.getBean(context.getWorkflowInfo().getWorkflowName());
		workflowprocessor.setContext(context);
		workflowprocessor.doTasks();
	}

	@RequestMapping("/workflow/trigger.do")
	public void triggerWorkflow(HttpServletRequest request) throws AppException {
		String workflowid = request.getParameter("workflowId");
		Key workflowKey = KeyFactory.createKey(Workflow.class.getSimpleName(), Long.parseLong(workflowid));
		Workflow currentWorkflow = (Workflow) workflowManager.getById(workflowKey);
		currentWorkflow.setWorkflowStatus(WorkflowStatusType.INPROGRESS.toString());
		workflowManager.updateWorkflow(currentWorkflow);
		workflowManager.triggerWorkflow(currentWorkflow);
	}

	@RequestMapping("/workflow.do")
	public String showSubjectTab(Model model, HttpServletRequest request) {
		log.debug("Presenting workflow view.");
		addToNavigationTrail("Workflow", true, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW, UICommonConstants.CONTEXT_WORKFLOW_INDEX);
		return UICommonConstants.VIEW_INDEX;
	}

	@RequestMapping("/workflow/showDetail.do")
	public String showDetail(HttpServletRequest request, Model model) throws NumberFormatException, AppException {
		String workflowId = request.getParameter(UICommonConstants.PARAM_ENTITY_ID);
		Key key = KeyFactory.createKey(Workflow.class.getSimpleName(), Long.parseLong(workflowId));
		Workflow currentWorkflow = (Workflow) workflowManager.getById(key);
		model.addAttribute(UICommonConstants.ATTRIB_WORKFLOW, currentWorkflow);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW, UICommonConstants.CONTEXT_WORKFLOW_DETAIL);
		log.debug("Presenting workflow view.");
		addToNavigationTrail("Workflow Detail-" + currentWorkflow.getWorkflowInstanceId(), false, request, false, false);
		return UICommonConstants.VIEW_INDEX;
	}

	@RequestMapping("/workflow/close.do")
	public void close(HttpServletRequest request, HttpServletResponse response) {
		removeFromNavigationTrail(request);
		redirectToPreviousBreadcrumb(request, response);
	}
	
	@RequestMapping("/workflow/advancedsearch.do")
	public String doAdvancedSearchForWorkflowData(HttpServletRequest request, Model model) throws AppException {

		String searchText = request.getParameter(UICommonConstants.ADV_SEARCH_TEXT_PARAM);
		addToNavigationTrail("Workflow", true, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_ADV_SEARCH_TXT, searchText);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW, UICommonConstants.CONTEXT_WORKFLOW_INDEX);
		return UICommonConstants.VIEW_INDEX;
	}

	@RequestMapping("/workflow/data.do")
	public @ResponseBody
	Map<String, Object> searchWorkflowsData(HttpServletRequest request) throws AppException {
		Map<String, Object> modalMap = new HashMap<String, Object>();

		GridRequest dataCriteria = gridRequestParser.parseDataCriteria(request);
		SearchResult searchResult = workflowManager.doSearch(dataCriteria);

		List<Object> workflows = searchResult.getResultObjects();

		Long totalRecordsLong = (Long) searchResult.getTotalRecordSize();
		int totalRecords = totalRecordsLong.intValue();

		ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
		int id = 0;
		for (Iterator<Object> iterator = workflows.iterator(); iterator.hasNext();) {
			Workflow workflow = (Workflow) iterator.next();

			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("key", ++id);

			ArrayList<Object> row = new ArrayList<Object>();
			row.add(workflow.getKey().getId());
			row.add(workflow.getWorkflowName());
			row.add(workflow.getWorkflowInstanceId());
			row.add(workflow.getWorkflowStatus());
			row.add(workflow.getContext().getWorkflowInfo().getLastFailedTaskName());
			row.add(workflow.getKey().getId());
			data.put("cell", row);
			rows.add(data);
		}

		int page = dataCriteria.getPaginationInfo().getPageNumber();
		int recordsPerPage = dataCriteria.getPaginationInfo().getRecordsPerPage();

		modalMap.put("rows", rows);
		modalMap.put("page", rows.size() == 0 ? 0 : page);
		modalMap.put("total", (int) Math.ceil(totalRecords / (recordsPerPage + 0d)));

		modalMap.put("records", totalRecords);

		return modalMap;
	}

}
