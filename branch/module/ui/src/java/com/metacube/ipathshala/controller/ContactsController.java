package com.metacube.ipathshala.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
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
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.metacube.ipathshala.GridRequest;
import com.metacube.ipathshala.SortInfo;
import com.metacube.ipathshala.UICommonConstants;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.core.UniqueValidationException;
import com.metacube.ipathshala.core.UserIdConflictException;
import com.metacube.ipathshala.entity.Contacts;
import com.metacube.ipathshala.entity.DomainAdmin;
import com.metacube.ipathshala.entity.DomainGroup;
import com.metacube.ipathshala.entity.Workflow;
import com.metacube.ipathshala.manager.ContactsManager;
import com.metacube.ipathshala.manager.DomainGroupManager;
import com.metacube.ipathshala.manager.WorkflowManager;
import com.metacube.ipathshala.search.SearchResult;
import com.metacube.ipathshala.search.property.operator.InputOrderByOperatorType;
import com.metacube.ipathshala.service.IPathshalaQueueService;
import com.metacube.ipathshala.service.WorkflowService;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.util.CommonWebUtil;
import com.metacube.ipathshala.util.GridRequestParser;
import com.metacube.ipathshala.util.UserUtil;
import com.metacube.ipathshala.view.validators.EntityValidator;
import com.metacube.ipathshala.vo.UserInfo;
import com.metacube.ipathshala.vo.UserSync;
import com.metacube.ipathshala.workflow.WorkflowInfo;
import com.metacube.ipathshala.workflow.impl.context.ContactImportContext;
import com.metacube.ipathshala.workflow.impl.context.SyncUserContactsContext;
import com.metacube.ipathshala.workflow.impl.processor.WorkflowStatusType;

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
		if (!StringUtils.isBlank(groupName) && isAdmin) {

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

	@RequestMapping("/contact/createForm.do")
	public String getCreateForm(HttpServletRequest request, Model model) {
		log.debug("Presenting create-Contact form view.");
		addToNavigationTrail("Create", false, request, false, false);
		model.addAttribute(UICommonConstants.ATTRIB_CONTACTS, new Contacts());
		model.addAttribute(UICommonConstants.ATTRIB_OPER,
				UICommonConstants.OPER_CREATE);
		/*
		 * model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
		 * UICommonConstants.CONTEXT_CREATE_CONTACT);
		 */
		return UICommonConstants.CONTEXT_CREATE_CONTACT;
	}

	@RequestMapping("/contact/create.do")
	public String create(
			@ModelAttribute(value = UICommonConstants.ATTRIB_CONTACTS) Contacts contact,
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
				UserService userService = UserServiceFactory.getUserService();
				User user = userService.getCurrentUser();

				Contacts createdcontact = contactsManager
						.createContact(contact);
				contactsManager.addContactForAllDomainUsers(
						CommonWebUtil.getDomain(user.getEmail()),
						createdcontact);
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

	@RequestMapping("/contact/update.do")
	public String updateContact(
			@ModelAttribute(value = UICommonConstants.ATTRIB_CONTACTS) Contacts contact,
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
		Key contactKey = KeyFactory.createKey(Contacts.class.getSimpleName(),
				Long.parseLong(contactId));
		Contacts currentContact = (Contacts) contactsManager
				.getById(contactKey);

		model.addAttribute(UICommonConstants.ATTRIB_CONTACTS, currentContact);
		model.addAttribute(UICommonConstants.ATTRIB_OPER,
				UICommonConstants.OPER_EDIT);
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_CREATE_CONTACT);
		log.debug("Presenting edit-contact form view.");
		addToNavigationTrail("Edit", false, request, false, false);
		return UICommonConstants.VIEW_INDEX;
	}

	@RequestMapping("/contact/contactMassUpdate.do")
	public @ResponseBody
	String contactMassUpdate(HttpServletRequest request) throws AppException {
		DataContext dataContext = (DataContext) request.getSession()
				.getAttribute(UICommonConstants.DATA_CONTEXT);
		String cmpanyName = request.getParameter("name");
		String cmpnyDept = request.getParameter("dept");
		String wrkAddress = request.getParameter("workAddr");
		String otherAddress = request.getParameter("otherAddr");
		String notes = request.getParameter("notes");
		List<Key> contactKeyList = getContactKeyList(request);
		if (!contactKeyList.isEmpty() && contactKeyList != null) {
			List<Contacts> contactsList = (List<Contacts>) contactsManager
					.getByKeys(contactKeyList);
			for (Contacts con : contactsList) {
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
			}

		}

		return UICommonConstants.SUCCESS_PAGE;
	}

	@RequestMapping("/contact/mailTo.do")
	public @ResponseBody
	void mailToSelectedContacts(HttpServletRequest request)
			throws AppException, IOException {
		List<Key> contactKeyList = getContactKeyList(request);

		contactsManager.sendMailToSelectedContacts();
	}

	@RequestMapping("/contact/delete.do")
	public void delete(Model model, HttpServletRequest request,
			HttpServletResponse response) throws AppException, IOException {
		log.debug("Processing detete contact request.");
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
						Contacts.class.getSimpleName(),
						Long.parseLong(contactId));
				contactKeyList.add(contactKey);
			}

		}
		contactsManager.deleteContactandExecuteWorkflow(
				contactKeyList,
				(DataContext) request.getSession().getAttribute(
						UICommonConstants.DATA_CONTEXT));
		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_CONTACTS_HOME);
		response.sendRedirect("/contacts.do");
	}

	@RequestMapping("/contact/close.do")
	public void close(HttpServletRequest request, HttpServletResponse response) {
		removeFromNavigationTrail(request);
		redirectToPreviousBreadcrumb(request, response);

	}

	@RequestMapping("/contact/showDetail.do")
	public String showDetail(HttpServletRequest request, Model model)
			throws NumberFormatException, AppException {
		String contactId = request
				.getParameter(UICommonConstants.PARAM_ENTITY_ID);
		Key contactKey = KeyFactory.createKey(Contacts.class.getSimpleName(),
				Long.parseLong(contactId));
		Contacts currentContact = (Contacts) contactsManager
				.getById(contactKey);
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
	public void makeDuplicateContact(HttpServletRequest request, Model model,
			HttpServletResponse response) throws AppException, IOException {
		String id = request.getParameter("contactIdList");
		List<Key> contactKeyList = getContactKeyList(request);
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String domain = CommonWebUtil.getDomain(user.getEmail());
		if (contactKeyList != null && !contactKeyList.isEmpty()) {
			contactsManager.duplicateContactandExecuteWorkflow(
					contactKeyList,
					(DataContext) request.getSession().getAttribute(
							UICommonConstants.DATA_CONTEXT),domain);
		}

		model.addAttribute(UICommonConstants.ATTRIB_CONTEXT_VIEW,
				UICommonConstants.CONTEXT_CONTACTS_HOME);
		response.sendRedirect("/contacts.do");
		/*
		 * removeFromNavigationTrail(request);
		 * redirectToPreviousBreadcrumb(request, response);
		 */

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
						Contacts.class.getSimpleName(),
						Long.parseLong(contactId));
				contactKeyList.add(contactKey);
			}
		}
		return contactKeyList;
	}

	@RequestMapping("/contact/export.do")
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

	@RequestMapping("/contact/import.do")
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

	private void doSomeThing(ArrayList<ArrayList<String>> storedValueList)
			throws IOException {
		FileService fileService = FileServiceFactory.getFileService();
		AppEngineFile file = fileService.createNewBlobFile("text/csv");
		boolean lock = false;
		FileWriteChannel writeChannel = fileService
				.openWriteChannel(file, lock);
		PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel,
				"UTF-8"));
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < storedValueList.size(); i++) {
			ArrayList<String> row = storedValueList.get(i);
			String firstname = row.get(0);
			if (firstname.equals("&&&&")) {
				break;
			}
			if (i != 0) {
				for (int j = 0; j < 16; j++) {

					if (row.size() > j && !StringUtils.isEmpty(row.get(j))) {
						sb.append((row.get(j)));
					} else {
						sb.append("-");
					}

					if (j + 1 != 16) {
						sb.append("\t");
					}
				}

				sb.append("\n");
			}

		}
		out.close();
		String path = file.getFullPath();
		file = new AppEngineFile(path);
		lock = true;
		writeChannel = fileService.openWriteChannel(file, lock);
		writeChannel.write(ByteBuffer.wrap(sb.toString().getBytes("UTF-8")));
		writeChannel.closeFinally();
		lock = false; // Let other people read at the same time
		FileReadChannel readChannel = fileService.openReadChannel(file, false);

		// Again, different standard Java ways of reading from the channel.
		BufferedReader reader = new BufferedReader(Channels.newReader(
				readChannel, "UTF-8"));
		reader.readLine();
		// line = "The woods are lovely dark and deep."

		readChannel.close();

		// Now read from the file using the Blobstore API
		BlobKey blobKey = fileService.getBlobKey(file);
		importData(blobKey.getKeyString());
	}

	private void importData(String blobKey) {
		TaskOptions task = buildStartJob("Import Shared Contacts");
		addJobParam(task,
				"mapreduce.mapper.inputformat.blobstoreinputformat.blobkeys",
				blobKey);

		/*
		 * String id = getGroupId(); addJobParam(task,
		 * "mapreduce.mapper.inputformat.datastoreinputformat.entitykind", id);
		 * 
		 * 
		 * addJobParam(task,
		 * "mapreduce.mapper.inputformat.datastoreinputformat.useremail",
		 * 
		 * // contactsManager.getUserEamil());
		 */

		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(task);
	}

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

	private static TaskOptions buildStartJob(String jobName) {
		return TaskOptions.Builder.withUrl("/mapreduce/command/start_job")
				// .method(Method.POST)
				.header("X-Requested-With", "XMLHttpRequest")
				.param("name", jobName);
	}

	private static void addJobParam(TaskOptions task, String paramName,
			String paramValue) {
		task.param("mapper_params." + paramName, paramValue);
	}

	private String getStringValue(Cell cell) {
		String result = "&&&&";
		if (cell != null) {
			String value = cell.getStringCellValue();
			if (value != null && !value.trim().equals("")) {
				result = value;
			}
		}
		return result;
	}

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
			HttpServletResponse response) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		/*
		 * int total_pages = 0; double start = 0;
		 */
		String groupId = null;
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
		com.metacube.ipathshala.entity.UserSync userSync = /*
															 * contactsManager.
															 * getUserSync
															 * (getCurrentUser
															 * (request )
															 * .getEmail(),
															 * dateString);
															 */null;
		String message = null;
		if (userSync == null
				|| (userSync != null && !userSync.getSyncDate().equals(
						dateString))) {
			SyncUserContactsContext syncUserContactsContext = new SyncUserContactsContext();
			syncUserContactsContext.setUserEmail(getCurrentUser(request)
					.getEmail());
			syncUserContactsContext.setGroupId(groupId);
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

			syncUserContactsContext.setWorkflowInfo(workflowInfo);
			workflowManager.triggerWorkflow(workflow);
			// sharedContactsService.syncUserContacts(getCurrentUser(request).getEmail(),
			// entries);
			message = "User Contacts will get syncronized within 10 minutes";
		} else {
			message = "Cannot sync. User limit crossed for the day ";
		}
		/*
		 * if (userSync == null) { contactsManager.createUserSync(email,
		 * CommonWebUtil.getDomain(email), dateString); } else {
		 * contactsManager.createUserSync(email, CommonWebUtil.getDomain(email),
		 * dateString); }
		 */
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

	@RequestMapping("/contact/getSelectedContactData.do")
	public @ResponseBody
	List<Map<String, Object>> getMapOfSelectedContacts(
			HttpServletRequest request, Model model) throws AppException {
		List<Map<String, Object>> modelMapList = new ArrayList<Map<String, Object>>();
		List<Key> contactKeyList = getContactKeyList(request);
		if (contactKeyList != null && !contactKeyList.isEmpty()) {
			List<Contacts> contactList = (List<Contacts>) contactsManager
					.getByKeys(contactKeyList);
			for (Contacts contacts : contactList) {
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

		Map<String, Object> modalMap = new HashMap<String, Object>();
		GridRequest gridRequest = gridRequestParser.parseDataCriteria(request);

		if (gridRequest.getSortInfo() == null) {
			SortInfo sortInfo = new SortInfo();
			sortInfo.setSortField("key");
			sortInfo.setSortOrder(InputOrderByOperatorType.ASC);
		}

		SearchResult searchResult = contactsManager.doSearch(gridRequest,
				(DataContext) session.getAttribute("dataContext"));
		List<Object> contactsList = searchResult.getResultObjects();
		int totalRecords = Integer.parseInt(String.valueOf(searchResult
				.getTotalRecordSize()));
		ArrayList<HashMap<String, Object>> rows = new ArrayList<HashMap<String, Object>>();
		int id = 0;
		String activeValue;
		for (Iterator iterator = contactsList.iterator(); iterator.hasNext();) {
			Contacts contact = (Contacts) iterator.next();
			HashMap<String, Object> data = new HashMap<String, Object>();
			data.put("key", ++id);

			ArrayList<Object> row = new ArrayList<Object>();
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

	@ModelAttribute
	public void preprocessContact(
			@ModelAttribute(value = UICommonConstants.ATTRIB_CONTACTS) Contacts contacts,
			HttpServletRequest request) throws AppException {
		Key key = null;

		if (request.getParameterMap().containsKey(
				UICommonConstants.PARAM_ENTITY_ID)
				&& request.getParameter(UICommonConstants.PARAM_ENTITY_ID) != null) {
			key = KeyFactory.createKey(Contacts.class.getSimpleName(), Long
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
}
