package com.netkiller.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.netkiller.FilterInfo;
import com.netkiller.FilterInfo.Rule;
import com.netkiller.GridRequest;
import com.netkiller.SortInfo;
import com.netkiller.UICommonConstants;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.core.UniqueValidationException;
import com.netkiller.core.UserIdConflictException;
import com.netkiller.entity.ConnectContact;
import com.netkiller.entity.Contact;
import com.netkiller.entity.DomainAdmin;
import com.netkiller.entity.DomainGroup;
import com.netkiller.entity.EntityCounter;
import com.netkiller.entity.Workflow;
import com.netkiller.entity.metadata.impl.ConnectContactMetaData;
import com.netkiller.entity.metadata.impl.ContactsMetaData;
import com.netkiller.manager.ConnectContactManager;
import com.netkiller.manager.ContactsManager;
import com.netkiller.manager.DomainGroupManager;
import com.netkiller.manager.EntityCounterManager;
import com.netkiller.manager.WorkflowManager;
import com.netkiller.search.SearchResult;
import com.netkiller.search.property.operator.InputFilterOperatorType;
import com.netkiller.search.property.operator.InputOrderByOperatorType;
import com.netkiller.service.IPathshalaQueueService;
import com.netkiller.service.WorkflowService;
import com.netkiller.util.AppLogger;
import com.netkiller.util.CommonWebUtil;
import com.netkiller.util.GridRequestParser;
import com.netkiller.view.validators.EntityValidator;
import com.netkiller.vo.UserInfo;
import com.netkiller.workflow.WorkflowInfo;
import com.netkiller.workflow.impl.context.ContactImportContext;
import com.netkiller.workflow.impl.context.SyncUserContactsContext;
import com.netkiller.workflow.impl.processor.WorkflowStatusType;

@Controller
public class ContactsController extends AbstractController {
	private static final AppLogger log = AppLogger
			.getLogger(ContactsController.class);

	@Autowired
	private ContactsManager contactsManager;

	@Autowired
	private EntityValidator validator;

	@Autowired
	private DomainGroupManager domainGroupManager;

	@Autowired
	private WorkflowService workflowService;

	@Autowired
	private ConnectContactManager connectContactManager;

	@Autowired
	private EntityCounterManager entityCounterManager;

	public ContactsManager getContactsManager() {
		return contactsManager;
	}

	public void setContactsManager(ContactsManager contactsManager) {
		this.contactsManager = contactsManager;
	}

	public EntityValidator getValidator() {
		return validator;
	}

	public void setValidator(EntityValidator validator) {
		this.validator = validator;
	}

	@RequestMapping("contacts/connect.do")
	@ResponseBody
	public String connectContacts(@RequestParam("contacts") String contactKeys,
			@RequestParam("toName") String toName,
			@RequestParam("toEmail") String toEmail,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws AppException {
		connectContactManager.createAndExecuteConnectContactWorkflow(
				contactKeys, toName, toEmail);

		return "success";
		/*
		 * model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
		 * UICommonConstants.CONNECT_PAGE); return UICommonConstants.VIEW_INDEX;
		 */
	}

	@RequestMapping("/connect/connect.do")
	public String showConnectContacts(@RequestParam("id") String randomUrl,
			HttpServletRequest request, HttpServletResponse response,
			Model model) throws AppException {
		model.addAttribute("randomUrl", randomUrl);
		model.addAttribute("domainName",
				connectContactManager.getDomainName(randomUrl));
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONNECT_HOME);
		return UICommonConstants.VIEW_INDEX;
	}

	@RequestMapping("/logout.do")
	public void doLogout(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws AppException, IOException {
		// session.setAttribute("logoutAction", new Boolean(true));
		session.invalidate();
		UserService userService = UserServiceFactory.getUserService();
		String logoutUrl = userService.createLogoutURL("/contacts.do");
		response.sendRedirect(logoutUrl);

	}

	@RequestMapping("/contact/triggercsvmail.do")
	@ResponseBody
	public String triggerExport(HttpServletRequest request) {
		Map<String, Object> params = request.getParameterMap();
		Queue queue = QueueFactory.getQueue("default");
		String toName = CommonWebUtil.getParameter(request, "toName");
		String toEmail = CommonWebUtil.getParameter(request, "toEmail");
		User user = UserServiceFactory.getUserService().getCurrentUser();
		TaskOptions options = TaskOptions.Builder.withUrl(
				"/resource/csvmail.do").param("toEmail", toEmail);
		for (String key : params.keySet()) {
			options.param(key, CommonWebUtil.getParameter(request, key));
		}
		options.param("toName", toName);
		options.param("fromEmail", user.getEmail());
		queue.add(options);
		return toEmail;
	}

	@RequestMapping("/resource/csvmail.do")
	@ResponseBody
	public void getCSV(HttpServletRequest request) throws IOException,
			AppException {
		String toName = CommonWebUtil.getParameter(request, "toName");
		String toEmail = CommonWebUtil.getParameter(request, "toEmail");
		String fromEmail = CommonWebUtil.getParameter(request, "fromEmail");
		contactsManager.generateCSVMail(toEmail, toName, fromEmail);
	}

	@RequestMapping("/connect/data.do")
	public @ResponseBody
	Map<String, Object> connectData(
			@RequestParam("randomUrl") String randomUrl,
			HttpServletRequest request, HttpSession session)
			throws AppException {

		Map<String, Object> modalMap = new HashMap<String, Object>();
		GridRequest gridRequest = gridRequestParser.parseDataCriteria(request);
		gridRequest.setSort(false);
		gridRequest.setSortInfo(null);
		FilterInfo filterInfo = gridRequest.getFilterInfo();
		if (filterInfo == null) {
			filterInfo = new FilterInfo();
			gridRequest.setAdvancedSearchTerm(true);
			gridRequest.setFilterInfo(filterInfo);
		}
		List<Rule> rules = filterInfo.getRules();
		FilterInfo.Rule rule = new FilterInfo.Rule();
		rule.setField(ConnectContactMetaData.COL_RANDOM_URL);
		rule.setOp(InputFilterOperatorType.EQUAL);
		rule.setData(randomUrl);
		rules.add(rule);
		SearchResult searchResult = connectContactManager.doSearch(gridRequest);
		List<Object> contactsList = searchResult.getResultObjects();
		int totalRecords = Integer.parseInt(String.valueOf(searchResult
				.getTotalRecordSize()));
		ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
		int id = 0;
		String activeValue;
		for (Iterator iterator = contactsList.iterator(); iterator.hasNext();) {
			Contact contact = (Contact) iterator.next();
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("key", ++id);

			ArrayList<Object> row = new ArrayList<Object>();
			row.add(contact.getKey().getId());
			row.add(contact.getKey().getId());
			row.add(contact.getFirstName());
			if (contact.getLastName() != null) {
				row.add(contact.getLastName());
			} else {
				row.add("");
			}
			if (contact.getCmpnyName() != null) {
				row.add(contact.getCmpnyName());
			} else {
				row.add("");
			}
			if (contact.getWorkEmail() != null) {
				row.add(contact.getWorkEmail());
			} else {
				row.add("");
			}

			if (contact.getWorkPhone() != null) {
				row.add(contact.getWorkPhone());
			} else {
				row.add("");
			}

			if (contact.getWorkAddress() != null) {
				row.add(contact.getWorkAddress());
			} else {
				row.add("");
			}

			data.put("cell", row);
			rows.add(data);
		}

		int page = gridRequest.getPaginationInfo().getPageNumber();
		int recordsPerPage = gridRequest.getPaginationInfo()
				.getRecordsPerPage();

		modalMap.put("rows", rows);
		modalMap.put("page", rows.size() == 0 ? 0 : page);
		modalMap.put("total",
				(int) Math.ceil(totalRecords / (recordsPerPage + 0d)));

		modalMap.put("records", totalRecords);

		return modalMap;
	}

	@RequestMapping("/resource/createGroupHome.do")
	public String createGroupHome(HttpServletRequest request,
			HttpServletResponse response, Model model) throws AppException {
		return UICommonConstants.WELCOME_ADMIN_PAGE;
	}

	@RequestMapping("/contacts/createGroup.do")
	public String createGroup(HttpServletRequest request, Model model,
			HttpServletResponse response) throws AppException, IOException {
		System.out.println("in create group");
		String groupName = request.getParameter("groupName");
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		Boolean isAdmin = contactsManager.isAdmin(user.getEmail());
		if (!StringUtils.isBlank(groupName)) {

			DomainGroup domainGroup = new DomainGroup();
			domainGroup.setDomainName(CommonWebUtil.getDomain(user.getEmail()));
			domainGroup.setGroupName(groupName);
			domainGroupManager.createDomainGroup(domainGroup);
			contactsManager.addGroupToAllContactForDomain(
					CommonWebUtil.getDomain(user.getEmail()), groupName,
					user.getEmail());
			/* contactsManager.addGroupToAllDomainUsers(); */
			return showContacts(request, model, response);
		}
		return UICommonConstants.VIEW_INDEX;
	}

	@RequestMapping("/contacts.do")
	public String showContacts(HttpServletRequest request, Model model,
			HttpServletResponse resp) throws IOException {
		log.debug("Presenting Contacts View");
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if (user == null) {
			resp.sendRedirect(userService.createLoginURL(request
					.getRequestURI()));
		}

		addToNavigationTrail("Contact", true, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_CONTACTS_HOME);
		return UICommonConstants.VIEW_INDEX;
	}

	@RequestMapping({ "/contact/createForm.do", "/connect/createForm.do" })
	public String getCreateForm(HttpServletRequest request, Model model) {
		log.debug("Presenting create-Contact form view.");
		addToNavigationTrail("Create", false, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_CONTACTS, new Contact());
		model.addAttribute(UICommonConstants.ATTRIB_OPER,
				UICommonConstants.OPER_CREATE);
		/*
		 * model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
		 * UICommonConstants.CONTEXT_CREATE_CONTACT);
		 */
		return UICommonConstants.CONTEXT_CREATE_CONTACT;
	}

	@RequestMapping("/connect/create.do")
	public String create(
			@ModelAttribute(value = UICommonConstants.ATTRIB_CONTACTS) Contact contact,
			BindingResult result, Model model, HttpServletRequest request,
			HttpSession session) throws AppException {
		validator.validate(contact, result, "ContactForm");
		if (result.hasErrors()) {
			log.warn("Unable to validate contact data for creation of new contact.");
			model.addAttribute(UICommonConstants.ATTRIB_OPER,
					UICommonConstants.OPER_CREATE);
			/*
			 * model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
			 * UICommonConstants.CONTEXT_CREATE_CONTACT);
			 */
			return UICommonConstants.CONTEXT_CREATE_CONTACT;
		} else {
			log.debug("New contact creation request processing.");
			/*
			 * model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
			 * UICommonConstants.CONTEXT_CONTACTS_HOME);
			 */
			try {

				Contact createdcontact = contactsManager.createContact(contact);
				/* entityCounterManager.create(entityCounter); */

				String email = null;
				String urlId = request.getParameter("urlId");
				if (StringUtils.isBlank(urlId)) {
					UserService userService = UserServiceFactory
							.getUserService();
					User user = userService.getCurrentUser();
					email = user.getEmail();
				} else {
					List<ConnectContact> list = connectContactManager
							.getByUrl(urlId);
					if (list != null && !list.isEmpty()) {
						email = list.get(0).getCreatedBy();
						ConnectContact connectContact = new ConnectContact();
						connectContact.setRandomUrl(urlId);
						connectContact.setContactKey(createdcontact.getKey());
						connectContact.setDomainName(list.get(0)
								.getDomainName());
						connectContactManager.create(connectContact);
					}

				}

				contactsManager.addContactForAllDomainUsers(
						CommonWebUtil.getDomain(email), createdcontact);
				removeFromNavigationTrail(request);
			} catch (UniqueValidationException exception) {
				result.rejectValue(
						exception.getUniquePropertyName(),
						((UniqueValidationException) exception)
								.getUniquePropertyName() + ".unique");
				log.warn("Unable to validate contact data for creation of new student.");
				model.addAttribute(UICommonConstants.ATTRIB_OPER,
						UICommonConstants.OPER_CREATE);
				/*
				 * model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				 * UICommonConstants.CONTEXT_CREATE_CONTACT);
				 */
				return UICommonConstants.CONTEXT_CREATE_CONTACT;
			} catch (UserIdConflictException exception) {
				result.rejectValue(exception.getPropertyName(),
						((UserIdConflictException) exception).getPropertyName()
								+ ".unique");
				log.warn("Unable to validate contact data for creation of new contact.");
				model.addAttribute(UICommonConstants.ATTRIB_OPER,
						UICommonConstants.OPER_CREATE);
				/*
				 * model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				 * UICommonConstants.CONTEXT_CREATE_CONTACT);
				 */
				return UICommonConstants.CONTEXT_CREATE_CONTACT;
			}

		}

		return UICommonConstants.SUCCESS_PAGE;
	}

	@RequestMapping({ "/contact/gridUpdate.do", "/connect/gridUpdate.do" })
	@ResponseBody
	public Boolean updateContactFromGrid(HttpServletRequest request)
			throws AppException {
		String keyLong = request.getParameter("keyLong");
		if (!StringUtils.isBlank(keyLong)) {
			String lastName = request.getParameter("lastName");
			String companyName = request.getParameter("cmpnyName");
			String workEmail = request.getParameter("workEmail");
			String workPhone = request.getParameter("workPhone");
			String workAddress = request.getParameter("workAddress");

			Key key = KeyFactory.createKey(Contact.class.getSimpleName(),
					Long.parseLong(keyLong));
			Contact contact = (Contact) contactsManager.getById(key);
			contact.setLastName(lastName);
			contact.setCmpnyName(companyName);
			contact.setWorkEmail(workEmail);
			contact.setWorkPhone(workPhone);
			contact.setWorkAddress(workAddress);

			contactsManager.createContact(contact);

		}
		return true;
	}

	@RequestMapping("/contact/update.do")
	public String updateContact(
			@ModelAttribute(value = UICommonConstants.ATTRIB_CONTACTS) Contact contact,
			BindingResult result, Model model, HttpServletRequest request)
			throws AppException {
		validator.validate(contact, result, UICommonConstants.FORM_CONTACT);
		DataContext dataContext = (DataContext) request.getSession()
				.getAttribute(UICommonConstants.DATA_CONTEXT);
		if (result.hasErrors()) {
			log.debug("Could not validate update contact form becuase of form errors.");
			model.addAttribute(UICommonConstants.ATTRIB_OPER,
					UICommonConstants.OPER_EDIT);
			/*
			 * model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
			 * UICommonConstants.CONTEXT_CREATE_CONTACT);
			 */
			model.addAttribute(UICommonConstants.ATTRIB_CONTACTS, contact);
			return UICommonConstants.CONTEXT_CREATE_CONTACT;
		} else {
			log.debug("Update contact data...");
			try {
				contactsManager.updateContact(contact, dataContext);
				removeFromNavigationTrail(request);
				model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
						UICommonConstants.CONTEXT_CONTACT_DETAIL);
			} catch (UniqueValidationException exception) {
				result.rejectValue(
						exception.getUniquePropertyName(),
						((UniqueValidationException) exception)
								.getUniquePropertyName() + ".unique");
				/*
				 * model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				 * UICommonConstants.CONTEXT_CREATE_CONTACT);
				 */
				model.addAttribute(UICommonConstants.ATTRIB_OPER,
						UICommonConstants.OPER_EDIT);
				log.warn("Unable to validate contact data for creation of new contact.");
				model.addAttribute(UICommonConstants.ATTRIB_CONTACTS, contact);
				return UICommonConstants.CONTEXT_CREATE_CONTACT;

			}

		}
		return UICommonConstants.SUCCESS_PAGE;
	}

	@RequestMapping("/contact/editForm.do")
	public String getEditForm(HttpServletRequest request, Model model)
			throws NumberFormatException, AppException {
		String contactId = request
				.getParameter(UICommonConstants.PARAM_ENTITY_ID);
		Key contactKey = KeyFactory.createKey(Contact.class.getSimpleName(),
				Long.parseLong(contactId));
		Contact currentContact = (Contact) contactsManager.getById(contactKey);

		model.addAttribute(UICommonConstants.ATTRIB_CONTACTS, currentContact);
		model.addAttribute(UICommonConstants.ATTRIB_OPER,
				UICommonConstants.OPER_EDIT);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_CREATE_CONTACT);
		log.debug("Presenting edit-contact form view.");
		addToNavigationTrail("Edit", false, request, false, false);
		return UICommonConstants.VIEW_INDEX;
	}

	@RequestMapping({ "/contact/contactMassUpdate.do",
			"/connect/contactMassUpdate.do" })
	public @ResponseBody
	String contactMassUpdate(HttpServletRequest request) throws AppException {
		DataContext dataContext = (DataContext) request.getSession()
				.getAttribute(UICommonConstants.DATA_CONTEXT);

		String userEmail = null;
		String urlId = request.getParameter("urlId");
		if (StringUtils.isBlank(urlId)) {
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			userEmail = user.getEmail();
		} else {
			List<ConnectContact> list = connectContactManager.getByUrl(urlId);
			if (list != null && !list.isEmpty()) {
				userEmail = list.get(0).getCreatedBy();
			}
		}

		String cmpanyName = request.getParameter("name");
		String cmpnyDept = request.getParameter("dept");
		String wrkAddress = request.getParameter("workAddr");
		String otherAddress = request.getParameter("otherAddr");
		String notes = request.getParameter("notes");
		List<Key> contactKeyList = getContactKeyList(request);

		/*
		 * List<Contact> contactList = new ArrayList<Contact>(); if
		 * (contactKeyList != null && !contactKeyList.isEmpty()) {
		 * contactsManager.deleteContactandExecuteWorkflow( contactKeyList,
		 * userEmail, (DataContext) request.getSession().getAttribute(
		 * UICommonConstants.DATA_CONTEXT)); contactList = (List<Contact>)
		 * contactsManager .getByKeys(contactKeyList); } if (contactList != null
		 * && !contactList.isEmpty()) { for (Contact contact : contactList) {
		 * contactsManager.deleteContact( contact, (DataContext)
		 * request.getSession().getAttribute( UICommonConstants.DATA_CONTEXT));
		 * 
		 * } }
		 */

		if (!contactKeyList.isEmpty() && contactKeyList != null) {
			List<Contact> contactsList = (List<Contact>) contactsManager
					.getByKeys(contactKeyList);
			for (Contact con : contactsList) {
				if (!StringUtils.isBlank(cmpanyName))
					con.setCmpnyName(cmpanyName);
				if (!StringUtils.isBlank(cmpnyDept))
					con.setCmpnyDepartment(cmpnyDept);
				if (!StringUtils.isBlank(wrkAddress))
					con.setWorkAddress(wrkAddress);
				if (!StringUtils.isBlank(otherAddress))
					con.setOtherAddress(otherAddress);
				if (!StringUtils.isBlank(notes))
					con.setNotes(notes);
				contactsManager.updateContact(con, dataContext);
				contactsManager.updateContactAndExecuteWorkflow(con, userEmail,
						dataContext);
			}

		}

		return UICommonConstants.SUCCESS_PAGE;
	}

	@RequestMapping("/contact/mailTo.do")
	public @ResponseBody
	void mailToSelectedContacts(HttpServletRequest request)
			throws AppException, IOException {
		// List<Key> contactKeyList = getContactKeyList(request);

		contactsManager.sendMailToSelectedContacts();
	}

	@RequestMapping({ "/contact/delete.do" })
	public void delete(Model model, HttpServletRequest request,
			HttpServletResponse response) throws AppException, IOException {
		deleteContacts(model, request);
		response.sendRedirect("/contacts.do");
	}

	@RequestMapping({ "/connect/delete.do" })
	@ResponseBody
	public String deleteConnect(Model model, HttpServletRequest request,
			HttpServletResponse response) throws AppException, IOException {
		deleteContacts(model, request);
		return "success";
	}

	private void deleteContacts(Model model, HttpServletRequest request)
			throws AppException {
		log.debug("Processing detete contact request.");
		String userEmail = null;
		String urlId = request.getParameter("urlId");
		if (StringUtils.isBlank(urlId)) {
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			userEmail = user.getEmail();
		} else {
			List<ConnectContact> list = connectContactManager.getByUrl(urlId);
			if (list != null && !list.isEmpty()) {
				userEmail = list.get(0).getCreatedBy();

			}
		}
		String id = request.getParameter("contactIdList");
		List<Key> contactKeyList = new ArrayList<Key>();
		if (id != null && !id.isEmpty()) {
			String[] ids = null;
			if (id.indexOf(',') != -1) {
				ids = id.split(",");
			} else {
				ids = new String[1];
				ids[0] = id;
			}
			for (String contactId : ids) {
				Key contactKey = KeyFactory.createKey(
						Contact.class.getSimpleName(),
						Long.parseLong(contactId));
				contactKeyList.add(contactKey);
			}

		}

		List<Contact> contactList = new ArrayList<Contact>();
		if (contactKeyList != null && !contactKeyList.isEmpty()) {
			contactsManager.deleteContactandExecuteWorkflow(
					contactKeyList,
					userEmail,
					(DataContext) request.getSession().getAttribute(
							UICommonConstants.DATA_CONTEXT));
			contactList = (List<Contact>) contactsManager
					.getByKeys(contactKeyList);
		}
		if (contactList != null && !contactList.isEmpty()) {
			for (Contact contact : contactList) {
				contactsManager.deleteContact(
						contact,
						(DataContext) request.getSession().getAttribute(
								UICommonConstants.DATA_CONTEXT));

			}
		}
		/*
		 * contactsManager.deleteContactandExecuteWorkflow( contactKeyList,
		 * userEmail, (DataContext) request.getSession().getAttribute(
		 * UICommonConstants.DATA_CONTEXT));
		 */
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_CONTACTS_HOME);
	}

	@RequestMapping("/contact/close.do")
	public void close(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect("/contacts.do");
		} catch (IOException e) {
			log.error("Can not redirect to specified URL");
			e.printStackTrace();
		}
	}

	@RequestMapping("/contact/showDetail.do")
	public String showDetail(HttpServletRequest request, Model model)
			throws NumberFormatException, AppException {
		String contactId = request.getParameter("paramid");
		Key contactKey = KeyFactory.createKey(Contact.class.getSimpleName(),
				Long.parseLong(contactId));
		Contact currentContact = (Contact) contactsManager.getById(contactKey);
		model.addAttribute(UICommonConstants.ATTRIB_CONTACTS, currentContact);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_CONTACT_DETAIL);
		// model.addAttribute("currentTerm", currentTerm);
		log.debug("Presenting contact view.");
		addToNavigationTrail(currentContact.getFirstName(), false, request,
				false, false);
		return UICommonConstants.VIEW_INDEX;
	}

	@RequestMapping("/contact/duplicate.do")
	public String makeDuplicateContact(HttpServletRequest request, Model model,
			HttpServletResponse response) throws AppException, IOException {
		// String id = request.getParameter("contactIdList");
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String domain = CommonWebUtil.getDomain(user.getEmail());
		DataContext dataContext = (DataContext) request.getSession()
				.getAttribute(UICommonConstants.DATA_CONTEXT);
		doDuplicate(request, response, model, domain, dataContext, null);

		return showContacts(request, model, response);
	}

	@RequestMapping("/connect/duplicate.do")
	@ResponseBody
	public String makeDuplicateConnectContact(HttpServletRequest request,
			Model model, HttpServletResponse response) throws AppException,
			IOException {
		String domain = request.getParameter("domainName");
		String urlId = request.getParameter("urlId");
		DataContext dataContext = (DataContext) request.getSession()
				.getAttribute(UICommonConstants.DATA_CONTEXT);
		doDuplicate(request, response, model, domain, null, urlId);
		return "success";
	}

	private void doDuplicate(HttpServletRequest request,
			HttpServletResponse response, Model model, String domain,
			DataContext dataContext, String urlId) throws AppException,
			IOException {
		List<Key> contactKeyList = getContactKeyList(request);
		if (contactKeyList != null && !contactKeyList.isEmpty()) {

			contactsManager.duplicateContactandExecuteWorkflow(contactKeyList,
					dataContext, domain, urlId);
		}

	}

	private List<Key> getContactKeyList(HttpServletRequest request) {
		List<Key> contactKeyList = new ArrayList<Key>();
		String id = request.getParameter("contactIdList");
		if (id != null && !id.isEmpty()) {
			String[] ids = null;
			if (id.indexOf(',') != -1) {
				ids = id.split(",");
			} else {
				ids = new String[1];
				ids[0] = id;
			}
			for (String contactId : ids) {
				Key contactKey = KeyFactory.createKey(
						Contact.class.getSimpleName(),
						Long.parseLong(contactId));
				contactKeyList.add(contactKey);
			}
		}
		return contactKeyList;
	}

	@RequestMapping({ "/contact/export.do", "/connect/export.do" })
	public void exportContacts(Model model, HttpServletRequest request,
			HttpServletResponse response) throws AppException, IOException {
		log.debug("Presenting Student Form view.");
		String sheetName = "Contacts";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ sheetName + ".xls");
		DataContext dataContext = (DataContext) request.getSession()
				.getAttribute(UICommonConstants.DATA_CONTEXT);
		contactsManager.exportContacts(dataContext, response.getOutputStream());
	}

	/*
	 * @RequestMapping("/myclass/delete.do") public void delete(
	 * 
	 * @ModelAttribute(value = UICommonConstants.ATTRIB_MYCLASS) MyClass
	 * myclass, Model model, HttpServletRequest request, HttpServletResponse
	 * response) throws AppException {
	 * log.debug("Processing detete myclass request.");
	 * myclassManager.deleteMyClass(myclass);
	 * removeFromNavigationTrail(request); redirectToPreviousBreadcrumb(request,
	 * response); }
	 */

	/*
	 * @RequestMapping("/contacts/createGroup.do") public void
	 * createGroup(HttpServletRequest request){
	 * contactsManager.createGroup(entry, userEmail); }
	 */

	@Autowired
	IPathshalaQueueService iPathshalaQueueService;

	@RequestMapping({ "/contact/import.do", "/connect/import.do" })
	public String importContacts(HttpServletRequest request, Model model,
			HttpServletResponse response) throws AppException, IOException {
		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(request);
		BlobKey blobKey = blobs.get("file");
		/*
		 * Map<String, List<BlobKey>> blobs =
		 * blobstoreService.getUploads(request); BlobKey blobKey =
		 * blobs.get("file").get(0);
		 */
		String blobKeyStr;

		if (blobKey != null) {
			blobKeyStr = blobKey.getKeyString();
			String email = UserServiceFactory.getUserService().getCurrentUser()
					.getEmail();
			ContactImportContext workflowContext = new ContactImportContext();
			workflowContext.setBlobKeyStr(blobKeyStr);
			workflowContext.setEmail(email);
			WorkflowInfo info = new WorkflowInfo(
					"contactImportWorkflowProcessor");

			info.setIsNewWorkflow(true);
			workflowContext.setWorkflowInfo(info);
			Workflow workflow = new Workflow();
			workflow.setWorkflowName("ContactsUplaod");
			workflow.setWorkflowInstanceId(info.getWorkflowInstance());
			workflow.setContext(workflowContext);
			workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());
			workflow = workflowService.createWorkflow(workflow);
			System.out.println(workflow.getWorkflowInstanceId());
			if (workflow != null) {
				workflow.setWorkflowStatus(WorkflowStatusType.INPROGRESS
						.toString());
				workflowService.updateWorkflow(workflow);
				workflowService.triggerWorkflow(workflow);
			}
		}

		return showContacts(request, model, response);

	}

	/*
	 * private void doSomeThing(ArrayList<ArrayList<String>> storedValueList)
	 * throws IOException { FileService fileService =
	 * FileServiceFactory.getFileService(); AppEngineFile file =
	 * fileService.createNewBlobFile("text/csv"); boolean lock = false;
	 * FileWriteChannel writeChannel = fileService .openWriteChannel(file,
	 * lock); PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel,
	 * "UTF-8")); StringBuffer sb = new StringBuffer(); for (int i = 0; i <
	 * storedValueList.size(); i++) { ArrayList<String> row =
	 * storedValueList.get(i); String firstname = row.get(0); if
	 * (firstname.equals("&&&&")) { break; } if (i != 0) { for (int j = 0; j <
	 * 16; j++) {
	 * 
	 * if (row.size() > j && !StringUtils.isEmpty(row.get(j))) {
	 * sb.append((row.get(j))); } else { sb.append("-"); }
	 * 
	 * if (j + 1 != 16) { sb.append("\t"); } }
	 * 
	 * sb.append("\n"); }
	 * 
	 * } out.close(); String path = file.getFullPath(); file = new
	 * AppEngineFile(path); lock = true; writeChannel =
	 * fileService.openWriteChannel(file, lock);
	 * writeChannel.write(ByteBuffer.wrap(sb.toString().getBytes("UTF-8")));
	 * writeChannel.closeFinally(); lock = false; // Let other people read at
	 * the same time FileReadChannel readChannel =
	 * fileService.openReadChannel(file, false);
	 * 
	 * // Again, different standard Java ways of reading from the channel.
	 * BufferedReader reader = new BufferedReader(Channels.newReader(
	 * readChannel, "UTF-8")); reader.readLine(); // line =
	 * "The woods are lovely dark and deep."
	 * 
	 * readChannel.close();
	 * 
	 * // Now read from the file using the Blobstore API BlobKey blobKey =
	 * fileService.getBlobKey(file); importData(blobKey.getKeyString()); }
	 */

	/*
	 * private void importData(String blobKey) { TaskOptions task =
	 * buildStartJob("Import Shared Contacts"); addJobParam(task,
	 * "mapreduce.mapper.inputformat.blobstoreinputformat.blobkeys", blobKey);
	 * 
	 * 
	 * String id = getGroupId(); addJobParam(task,
	 * "mapreduce.mapper.inputformat.datastoreinputformat.entitykind", id);
	 * 
	 * 
	 * addJobParam(task,
	 * "mapreduce.mapper.inputformat.datastoreinputformat.useremail",
	 * 
	 * // contactsManager.getUserEamil());
	 * 
	 * 
	 * Queue queue = QueueFactory.getDefaultQueue(); queue.add(task); }
	 */

	/*
	 * private String getGroupId() {
	 * 
	 * String groupId = null;
	 * 
	 * try { UserService userService = UserServiceFactory.getUserService(); User
	 * user = userService.getCurrentUser(); String sharedContactsGroupName =
	 * contactsManager .getGroupName(CommonWebUtil.getDomain(user.getEmail()));
	 * 
	 * groupId = contactsManager
	 * .getSharedContactsGroupId(sharedContactsGroupName);
	 * log.info("sharedContactsGroupName ===> " + sharedContactsGroupName);
	 * log.info("groupId ===> " + groupId); if (groupId == null ||
	 * groupId.equals("")) { ContactGroupEntry group = new ContactGroupEntry();
	 * group.setSummary(new PlainTextConstruct(sharedContactsGroupName));
	 * group.setTitle(new PlainTextConstruct(sharedContactsGroupName));
	 * contactsManager.create(group); groupId = contactsManager
	 * .getSharedContactsGroupId(sharedContactsGroupName);
	 * 
	 * GroupMembershipInfo gmInfo = new GroupMembershipInfo(); // added
	 * gmInfo.setHref(groupId); // added
	 * 
	 * } } catch (Exception e) { logger.log(Level.SEVERE, e.getMessage(), e); }
	 * return groupId; }
	 */

	/*
	 * private static TaskOptions buildStartJob(String jobName) { return
	 * TaskOptions.Builder.withUrl("/mapreduce/command/start_job") //
	 * .method(Method.POST) .header("X-Requested-With", "XMLHttpRequest")
	 * .param("name", jobName); }
	 * 
	 * private static void addJobParam(TaskOptions task, String paramName,
	 * String paramValue) { task.param("mapper_params." + paramName,
	 * paramValue); }
	 * 
	 * private String getStringValue(Cell cell) { String result = "&&&&"; if
	 * (cell != null) { String value = cell.getStringCellValue(); if (value !=
	 * null && !value.trim().equals("")) { result = value; } } return result; }
	 */

	@Autowired
	private WorkflowManager workflowManager;

	@RequestMapping("/contact/syncContacts.do")
	public String syncContacts(HttpServletRequest request,
			HttpServletResponse response, Model model) throws AppException,
			IOException {
		UserService userService = UserServiceFactory.getUserService();
		DomainAdmin currentCustomer = null;
		User user = userService.getCurrentUser();
		if (user != null) {
			request.getSession()
					.setAttribute(
							"user",
							new UserInfo(user.getUserId(), user.getEmail(),
									null, null));
		}

		try {
			currentCustomer = contactsManager
					.verifyUser(getCurrentUser(request).getEmail());
		} catch (Exception e) {
			e.printStackTrace();

		}
		if (user == null) {
			String loginURL = userService.createLoginURL(request
					.getRequestURI());
			response.sendRedirect(loginURL);
		}

		syncContacts(request, response);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_CONTACTS_HOME);
		return UICommonConstants.VIEW_INDEX;
	}

	public WorkflowManager getWorkflowManager() {
		return workflowManager;
	}

	public void setWorkflowManager(WorkflowManager workflowManager) {
		this.workflowManager = workflowManager;
	}

	private Map<String, Object> syncContacts(HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int totalLimit = 100;
		/*
		 * try {
		 * 
		 * // entries = sharedContactsService.getContacts(page, rows, sidx, //
		 * sord);
		 * 
		 * 
		 * if (isUseForSharedContacts) { groupId = getGroupId(); }
		 * 
		 * 
		 * if (currentCustomer.getAccountType().equalsIgnoreCase("Paid")) {
		 * totalLimit = 30000; } else { if
		 * (CommonUtil.isTheSecondTypeCustomer(currentCustomer)) { totalLimit =
		 * 50; } }
		 * 
		 * 
		 * entries = service.getContacts(1, totalLimit, groupId,
		 * isUseForSharedContacts, null); log.info("### 1. entries.size() ===> "
		 * + entries.size());
		 * 
		 * 
		 * } catch (Exception e) { log.error(e.getMessage());
		 * e.printStackTrace(); }
		 */
		Date date = new Date();
		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String dateString = formatter.format(date);
		String email = getCurrentUser(request).getEmail();
		DomainGroup domainGroup = domainGroupManager
				.getDomainGroupByDomainName(CommonWebUtil.getDomain(email));
		com.netkiller.entity.UserSync userSync = contactsManager.getUserSync(
				email, date);
		String message = null;
		if (userSync == null
				|| (userSync != null && !userSync.getSyncDate().equals(
						dateString))) {
			SyncUserContactsContext syncUserContactsContext = new SyncUserContactsContext();
			syncUserContactsContext.setUserEmail(getCurrentUser(request)
					.getEmail());
			syncUserContactsContext.setGroupId(domainGroup.getGroupName());
			/*
			 * syncUserContactsContext
			 * .setIsUseForSharedContacts(isUseForSharedContacts);
			 */
			syncUserContactsContext.setTotalLimit(totalLimit);

			WorkflowInfo workflowInfo = new WorkflowInfo(
					"syncUserContactsWorkflowProcessor");
			workflowInfo.setIsNewWorkflow(true);

			Workflow workflow = new Workflow();
			workflow.setContext(syncUserContactsContext);
			workflow.setWorkflowName(workflowInfo.getWorkflowName());
			workflow.setWorkflowInstanceId(workflowInfo.getWorkflowInstance());
			workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());
			workflow = workflowService.createWorkflow(workflow);
			syncUserContactsContext.setWorkflowInfo(workflowInfo);
			workflowManager.updateWorkflow(workflow);
			workflowManager.triggerWorkflow(workflow);
			// service.syncUserContacts(getCurrentUser(request).getEmail(),
			// entries);
			message = "User Contacts will get syncronized within 10 minutes";
		} else {
			message = "Cannot sync. User limit crossed for the day ";
		}

		if (userSync == null) {
			contactsManager.createUserSync(email,
					CommonWebUtil.getDomain(email), date);
		} else {
			contactsManager.createUserSync(email,
					CommonWebUtil.getDomain(email), date);
		}

		modelMap.put("code", "success");
		modelMap.put("message", message);
		return modelMap;
	}

	private UserInfo getCurrentUser(HttpServletRequest request) /*
																 * throws
																 * AppException
																 */{
		UserInfo user = (UserInfo) request.getSession().getAttribute("user");
		/*
		 * if(user == null || user.getEmail() == null ){ throw new
		 * AppException("User not found in session"); }
		 */
		return user;
	}

	@RequestMapping({ "/contact/getSelectedContactData.do",
			"/connect/getSelectedContactData.do" })
	public @ResponseBody
	List<Map<String, Object>> getMapOfSelectedContacts(
			HttpServletRequest request, Model model) throws AppException {
		List<Map<String, Object>> modelMapList = new ArrayList<Map<String, Object>>();
		List<Key> contactKeyList = getContactKeyList(request);
		if (contactKeyList != null && !contactKeyList.isEmpty()) {
			List<Contact> contactList = (List<Contact>) contactsManager
					.getByKeys(contactKeyList);
			for (Contact contacts : contactList) {
				Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
				dataMap.put("fullName", contacts.getFullName());
				dataMap.put("companyName", contacts.getCmpnyName());
				dataMap.put("workEmail", contacts.getWorkEmail());
				dataMap.put("Phone", contacts.getWorkPhone());
				modelMapList.add(dataMap);
			}
		}

		return modelMapList;

	}

	/*
	 * @RequestMapping("/contact/massUpdate.do") public void
	 * massUpdate(HttpServletRequest request, HttpServletResponse response)
	 * throws AppException { List<Contacts> contactList = new
	 * ArrayList<Contacts>(); contactList = (List<Contacts>) contactsManager
	 * .getByKeys(getContactKeyList(request));
	 * 
	 * }
	 */

	@Autowired
	private GridRequestParser gridRequestParser;

	@RequestMapping("/contact/data.do")
	public @ResponseBody
	Map<String, Object> searchContactsData(HttpServletRequest request,
			HttpSession session) throws AppException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		System.out.println("!!!!!!!!!!!!!!!!User is :" + user);

		Map<String, Object> modalMap = new HashMap<String, Object>();
		GridRequest gridRequest = gridRequestParser.parseDataCriteria(request);
		// gridRequest = addIsDeletedChecktoGridRequest(gridRequest, false);
		if (gridRequest.getSortInfo() == null) {
			SortInfo sortInfo = new SortInfo();
			sortInfo.setSortField("key");
			sortInfo.setSortOrder(InputOrderByOperatorType.ASC);
		}

		// FilterInfo filterInfo = new FilterInfo();
		/*
		 * SearchResult searchResult = contactsManager.doSearch(gridRequest,
		 * (DataContext) session.getAttribute("dataContext")); List<Object>
		 * contactsList = searchResult.getResultObjects();
		 */
		List<Contact> contactList = contactsManager.doSearch(gridRequest);
		EntityCounter entityCounter = entityCounterManager.getByEntityName(
				Contact.class.getSimpleName(),
				CommonWebUtil.getDomain(user.getEmail()));
		int totalRecords = 0;
		if (entityCounter != null) {
			totalRecords = entityCounter.getCount();
		}
		ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
		int id = 0;
		String activeValue;
		for (Iterator iterator = contactList.iterator(); iterator.hasNext();) {
			Contact contact = (Contact) iterator.next();
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("key", ++id);

			ArrayList<Object> row = new ArrayList<Object>();
			row.add(contact.getKey().getId());
			row.add(contact.getKey().getId());
			row.add(contact.getFirstName());
			if (contact.getLastName() != null) {
				row.add(contact.getLastName());
			} else {
				row.add("");
			}
			if (contact.getCmpnyName() != null) {
				row.add(contact.getCmpnyName());
			} else {
				row.add("");
			}
			if (contact.getWorkEmail() != null) {
				row.add(contact.getWorkEmail());
			} else {
				row.add("");
			}

			if (contact.getWorkPhone() != null) {
				row.add(contact.getWorkPhone());
			} else {
				row.add("");
			}

			if (contact.getWorkAddress() != null) {
				row.add(contact.getWorkAddress());
			} else {
				row.add("");
			}

			data.put("cell", row);
			rows.add(data);
		}

		int page = gridRequest.getPaginationInfo().getPageNumber();
		int recordsPerPage = gridRequest.getPaginationInfo()
				.getRecordsPerPage();

		modalMap.put("rows", rows);
		modalMap.put("page", rows.size() == 0 ? 0 : page);
		modalMap.put("total",
				(int) Math.ceil(totalRecords / (recordsPerPage + 0d)));

		modalMap.put("records", totalRecords);

		return modalMap;
	}

	private GridRequest addIsDeletedChecktoGridRequest(GridRequest gridRequest,
			boolean showDeleted) {
		if (!showDeleted) {
			FilterInfo filterInfo = gridRequest.getFilterInfo();
			if (filterInfo == null) {
				filterInfo = new FilterInfo();
				gridRequest.setAdvancedSearchTerm(true);
				gridRequest.setFilterInfo(filterInfo);
			}
			List<Rule> rules = filterInfo.getRules();
			FilterInfo.Rule rule = new FilterInfo.Rule();
			rule.setField(ContactsMetaData.COL_IS_DELETED);
			rule.setOp(InputFilterOperatorType.EQUAL);
			rule.setData(String.valueOf(showDeleted));
			rules.add(rule);
		}
		return gridRequest;
	}

	@ModelAttribute
	public void preprocessContact(
			@ModelAttribute(value = UICommonConstants.ATTRIB_CONTACTS) Contact contacts,
			HttpServletRequest request) throws AppException {
		Key key = null;

		if (request.getParameterMap().containsKey(
				UICommonConstants.PARAM_ENTITY_ID)
				&& request.getParameter(UICommonConstants.PARAM_ENTITY_ID) != null) {
			key = KeyFactory.createKey(Contact.class.getSimpleName(), Long
					.parseLong(request
							.getParameter(UICommonConstants.PARAM_ENTITY_ID)));
			contacts.setKey(key);
		}

	}

	/*
	 * @Autowired private DomainConfig domainConfig;
	 * 
	 * private boolean isAdmin(String email) { boolean isAdmin = false; //
	 * List<String> users = new ArrayList<String>();
	 * com.google.gdata.client.appsforyourdomain.UserService guserService = new
	 * com.google.gdata.client.appsforyourdomain.UserService( "ykko-test");
	 * String consumerKey = domainConfig.getConsumerkey(); String consumerSecret
	 * = domainConfig.getConsumerKeySecret(); String urlEscopo =
	 * "http://apps-apis.google.com/a/feeds/user/#readonly";
	 * GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
	 * oauthParameters.setOAuthConsumerKey(consumerKey);
	 * oauthParameters.setOAuthConsumerSecret(consumerSecret);
	 * oauthParameters.setOAuthType(OAuthType.TWO_LEGGED_OAUTH);
	 * 
	 * oauthParameters.setScope(urlEscopo);
	 * 
	 * try { guserService.setOAuthCredentials(oauthParameters, new
	 * OAuthHmacSha1Signer()); guserService.setReadTimeout(20000);
	 * guserService.setConnectTimeout(20000); final String APPS_FEEDS_URL_BASE =
	 * "https://apps-apis.google.com/a/feeds/"; final String SERVICE_VERSION =
	 * "2.0";
	 * 
	 * String domainUrlBase = APPS_FEEDS_URL_BASE +
	 * CommonWebUtil.getDomain(email) + "/";
	 * 
	 * URL retrieveUrl = new URL(domainUrlBase + "user/" + SERVICE_VERSION);
	 * UserFeed genericFeed = new UserFeed();
	 * 
	 * Link nextLink = null;
	 * 
	 * do { try { UserFeed newGenericFeed = guserService.getFeed(retrieveUrl,
	 * UserFeed.class);
	 * 
	 * genericFeed.getEntries() .addAll(newGenericFeed.getEntries()); nextLink =
	 * newGenericFeed.getLink(Link.Rel.NEXT, Link.Type.ATOM); if (nextLink !=
	 * null) { retrieveUrl = new URL(nextLink.getHref()); } } catch
	 * (AppsForYourDomainException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (ServiceException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (IOException e) {
	 * // TODO Auto-generated catch block e.printStackTrace(); } } while
	 * (nextLink != null);
	 * 
	 * if (genericFeed != null && genericFeed.getEntries() != null &&
	 * !genericFeed.getEntries().isEmpty()) { for (UserEntry genericEntry :
	 * genericFeed.getEntries()) { //
	 * System.out.println(genericEntry.getLogin().getUserName()); if
	 * (genericEntry .getLogin() .getUserName() .equalsIgnoreCase(
	 * CommonWebUtil.getUserId(email)) && genericEntry.getLogin().getAdmin()) {
	 * isAdmin = true; break; } } } } catch (OAuthException e) {
	 * e.printStackTrace();
	 * 
	 * } catch (MalformedURLException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } return isAdmin; }
	 */

	@RequestMapping("/contact/MyContactGroupId.do")
	@ResponseBody
	public String getMyContactGroupId(HttpServletRequest request)
			throws AppException {
		String grpName = request.getParameter("grpName");

		String grpId = contactsManager.getMyContactGroupId(grpName);
		System.out
				.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!MYCONTACT GROUP ID IS :"
						+ grpId);
		return grpId;
	}

	/*
	 * @RequestMapping("/contact/getContactStringForAnEncryptedEmail.do")
	 * 
	 * @ResponseBody public String
	 * getContactStringForAnEncryptedEmail(HttpServletRequest request) throws
	 * Exception { String contactEmailList = ""; String userEmail =
	 * request.getParameter("email"); // String userEmailDec =
	 * AESencrp.decrypt(userEmail); contactEmailList =
	 * contactsManager.getAllContactOfAUser(userEmail);
	 * System.out.println("Contact Email List String  is:" + contactEmailList);
	 * contactEmailList = AESencrp.encrypt(contactEmailList);
	 * System.out.println("Encrypted Email String is :" + contactEmailList);
	 * return contactEmailList; }
	 */

}
