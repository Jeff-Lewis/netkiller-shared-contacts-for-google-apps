package com.netkiller.web.sharedcontacts;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.contacts.UserDefinedField;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.FamilyName;
import com.google.gdata.data.extensions.FormattedAddress;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.GivenName;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.OrgDepartment;
import com.google.gdata.data.extensions.OrgName;
import com.google.gdata.data.extensions.OrgTitle;
import com.google.gdata.data.extensions.Organization;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.data.extensions.StructuredPostalAddress;
import com.netkiller.entity.Workflow;
import com.netkiller.exception.AppException;
import com.netkiller.googleUtil.ContactInfo;
import com.netkiller.mail.GoogleMailService;
import com.netkiller.mail.MailAddress;
import com.netkiller.mail.MailMessage;
import com.netkiller.mail.Recipient;
import com.netkiller.mail.RecipientType;
import com.netkiller.manager.WorkflowManager;
import com.netkiller.payment.PaymentGateway;
import com.netkiller.search.GridRequest;
import com.netkiller.service.sharedcontacts.SharedContactsService;
import com.netkiller.util.CommonUtil;
import com.netkiller.util.CommonWebUtil;
import com.netkiller.util.GridRequestParser;
import com.netkiller.util.SharedContactsUtil;
import com.netkiller.vo.AppProperties;
import com.netkiller.vo.Customer;
import com.netkiller.vo.DomainSettings;
import com.netkiller.vo.StaticProperties;
import com.netkiller.vo.UserInfo;
import com.netkiller.vo.UserLogging;
import com.netkiller.vo.UserSync;
import com.netkiller.workflow.WorkflowInfo;
import com.netkiller.workflow.impl.context.AddContactForAllDomainUsersContext;
import com.netkiller.workflow.impl.context.AddGroupsAndContactsByUsersContext;
import com.netkiller.workflow.impl.context.AddInitialContactsAndGroupContext;
import com.netkiller.workflow.impl.context.DeleteGroupsAndContactsByUsersContext;
import com.netkiller.workflow.impl.context.SyncUserContactsContext;
import com.netkiller.workflow.impl.processor.WorkflowStatusType;

/**
 * ÃƒÂ¬Ã¢â‚¬Å¡Ã‚Â¬ÃƒÂ¬Ã…Â¡Ã‚Â©ÃƒÂ¬Ã…Â¾
 * Ã¯Â¿Â½
 * ÃƒÂ¬Ã¢â‚¬ÂºÃ‚Â¹ÃƒÂ«Ã‚Â¸Ã…â€™Ã�
 * �Â«Ã¯Â¿
 * Â½Ã‚Â¼ÃƒÂ¬Ã…Â¡Ã‚Â°ÃƒÂ¬Ã‚Â Ã‚Â¸
 * ÃƒÂ¬Ã¯Â¿Â½Ã‹Å“
 * ÃƒÂ¬Ã‚Â¡Ã‚Â°ÃƒÂ­Ã…Â¡Ã�
 * �â€™/ÃƒÂ¬Ã†â€™Ã�
 * �Â¿Â½ÃƒÂ¬Ã¢â‚¬Å¾Ã‚Â±/Ã�
 * �ÂªÃ‚Â°Ã‚Â±ÃƒÂ¬Ã¢â‚�
 * �Â¹Ã‚Â /ÃƒÂ¬Ã¢â‚¬Å¡Ã‚Â­ÃƒÂ¬Ã‚Â Ã…â€œ
 * URLÃ�
 * �Â¬Ã…Â¡Ã¢â‚¬ï¿½ÃƒÂ¬Ã‚Â²Ã‚Â­ÃƒÂ¬Ã¯Â�
 * �Â½Ã¢â‚¬Å¾
 * ÃƒÂ¬Ã¢â‚¬Å¾Ã…â€œÃƒÂ«Ã‚Â¹Ã¢â‚�
 * �Å¾ÃƒÂ¬Ã…Â Ã‚Â¤
 * ÃƒÂ¬Ã‚Â»Ã‚Â´ÃƒÂ­Ã¯Â¿Â½
 * Ã‚Â¬ÃƒÂ«Ã¢â‚¬Å¾
 * Ã…â€™ÃƒÂ­Ã…Â Ã‚Â¸ÃƒÂ«Ã‚Â¥Ã‚Â¼
 * ÃƒÂ­Ã¢â‚¬Â Ã‚ÂµÃƒÂ­Ã¢â‚¬Â¢Ã‚Â´
 * ÃƒÂ¬Ã‚Â²Ã‹Å“ÃƒÂ«Ã‚Â¦Ã‚Â¬
 * 
 * @author ykko
 */
@org.springframework.stereotype.Controller
public class SharedContactsController {

	@Resource
	private GridRequestParser gridRequestParser;

	public static final Integer SYNC_LIMIT = 10;

	public void setGridRequestParser(GridRequestParser gridRequestParser) {
		this.gridRequestParser = gridRequestParser;
	}

	protected final Logger logger = Logger.getLogger(getClass().getName());

	// ÃƒÂ­Ã¢â‚¬ï¿½Ã¢â‚¬Å¾ÃƒÂ«Ã‚Â¡Ã…â€œÃƒÂ­Ã¯Â¿Â½Ã‚Â¼ÃƒÂ­Ã¢â‚¬Â¹Ã‚Â°
	// ÃƒÂ¬Ã¢â‚¬Å¾Ã…â€œÃƒÂ«Ã‚Â¹Ã¢â‚¬Å¾ÃƒÂ¬Ã…Â Ã‚Â¤
	@Autowired
	private AppProperties appProperties;

	@Autowired
	private WorkflowManager workflowManager;

	public void setAppProperties(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	// ÃƒÂ«Ã‚Â©Ã¢â‚¬ï¿½ÃƒÂ¬Ã¢â‚¬Â¹Ã…â€œÃƒÂ¬Ã‚Â§Ã¢â€šÂ¬
	// ÃƒÂ¬Ã¢â‚¬Å¾Ã…â€œÃƒÂ«Ã‚Â¹Ã¢â‚¬Å¾ÃƒÂ¬Ã…Â Ã‚Â¤
	@Autowired
	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	// ÃƒÂ«Ã¯Â¿Â½Ã¢â‚¬Å¾ÃƒÂ«Ã‚Â©Ã¢â‚¬ï¿½ÃƒÂ¬Ã¯Â¿Â½Ã‚Â¸ÃƒÂªÃ‚Â³Ã‚ÂµÃƒÂ¬Ã…â€œÃ‚Â ÃƒÂ¬Ã‚Â£Ã‚Â¼ÃƒÂ¬Ã¢â‚¬Â Ã…â€™ÃƒÂ«Ã‚Â¡Ã¯Â¿Â½
	// ÃƒÂ¬Ã¢â‚¬Å¾Ã…â€œÃƒÂ«Ã‚Â¹Ã¢â‚¬Å¾ÃƒÂ¬Ã…Â Ã‚Â¤ÃƒÂ¬Ã‚Â»Ã‚Â´ÃƒÂ­Ã¯Â¿Â½Ã‚Â¬ÃƒÂ«Ã¢â‚¬Å¾Ã…â€™ÃƒÂ­Ã…Â Ã‚Â¸
	@Autowired
	private SharedContactsService sharedContactsService;

	public void setSharedContactsService(
			SharedContactsService sharedContactsService) {
		this.sharedContactsService = sharedContactsService;
	}

	/**
	 * ÃƒÂ«Ã‚Â¸Ã…â€™ÃƒÂ«Ã¯Â¿Â½Ã‚Â¼ÃƒÂ¬Ã…�
	 * �¡Ã‚Â°ÃƒÂ¬Ã‚Â Ã‚Â¸ÃƒÂ«Ã‚Â¡Ã…â€œÃƒÂ
	 * «Ã‚Â¶Ã¢â€šÂ¬ÃƒÂ­Ã¢â‚¬Å¾Ã‚Â°
	 * ÃƒÂ«Ã¢â‚�
	 * �Å¾Ã‹Å“ÃƒÂ¬Ã¢â‚¬â€œÃ‚Â´ÃƒÂ
	 * ¬Ã‹Å“Ã‚Â¨
	 * ÃƒÂªÃ‚Â°Ã¯Â¿Â½ÃƒÂ¬Ã‚Â¢Ã¢â‚¬Â¦
	 * ÃƒÂ­Ã�
	 * �â€™Ã…â€™ÃƒÂ«Ã¯Â¿Â½Ã‚Â¼ÃƒÂ«Ã‚�
	 * �¯Ã‚Â¸ÃƒÂ­Ã¢â‚¬Å¾Ã‚Â°ÃƒÂ«Ã‚Â¥Ã‚Â¼
	 * ÃƒÂ¬Ã‚Â¶Ã…â€œÃƒÂ«Ã‚Â Ã‚Â¥
	 * 
	 * @param request
	 */
	private void printParams(HttpServletRequest request) {
		Enumeration enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			logger.info("name: " + name + "\t value:"
					+ request.getParameter(name));
		}
	}

	/**
	 * app.properties
	 * ÃƒÂ­Ã…â€™Ã…â€™ÃƒÂ¬Ã¯Â¿Â½Ã‚�
	 * �¼ÃƒÂ¬Ã¢â‚¬â€�Ã¯Â¿Â½
	 * ÃƒÂ«Ã¢â‚¬Å“Ã‚Â
	 * ±ÃƒÂ«Ã‚Â¡Ã¯Â¿Â½ÃƒÂ«Ã¯Â¿Â½Ã…â€œ
	 * SharedContactsGroupNameÃƒÂ¬Ã¯Â¿Â½Ã‚Â´ Google Domain Shared
	 * ContactsÃƒÂ¬Ã¢â‚¬â€�Ã¯Â¿Â½
	 * ÃƒÂ¬Ã¢â‚¬â€�
	 * �Ã¢â‚¬Â ÃƒÂ¬Ã¯Â¿Â½Ã¢â‚¬Å¾
	 * ÃƒÂªÃ‚Â²Ã‚Â½ÃƒÂ¬Ã…Â¡Ã‚Â°
	 * SharedContactsGroupNameÃƒÂ¬Ã¯Â¿Â½Ã¢â‚¬Å¾
	 * ÃƒÂ¬Ã†â€™Ã¯Â¿Â½ÃƒÂ¬Ã¢â‚¬Å¾Ã‚Â±,
	 * ÃƒÂ¬Ã…Â¾Ã‹â€ ÃƒÂ¬Ã¯Â¿Â½Ã¢â‚¬Å¾
	 * ÃƒÂªÃ‚Â²Ã‚Â½ÃƒÂ¬Ã…Â¡Ã‚Â°
	 * ÃƒÂ­Ã¢â‚¬Â Ã‚ÂµÃƒÂªÃ‚Â³Ã‚Â¼
	 * 
	 * @return
	 */
	private String getGroupId() {

		String groupId = null;

		try {
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			if (user != null) {
				String sharedContactsGroupName = sharedContactsService
						.getGroupName(CommonWebUtil.getDomain(user.getEmail()));

				groupId = sharedContactsService
						.getSharedContactsGroupId(sharedContactsGroupName);
				logger.info("sharedContactsGroupName ===> "
						+ sharedContactsGroupName);
				logger.info("groupId ===> " + groupId);
				if ((groupId == null || groupId.equals(""))
						&& !StringUtils.isBlank(sharedContactsGroupName)) {
					ContactGroupEntry group = new ContactGroupEntry();
					group.setSummary(new PlainTextConstruct(
							sharedContactsGroupName));
					group.setTitle(new PlainTextConstruct(
							sharedContactsGroupName));
					group = sharedContactsService.create(group);
					groupId = group.getId();
					// System.out.println("created group : " +
					// sharedContactsGroupName + "\t with id : " +groupId);
					if (!StringUtils.isBlank(groupId)) {
						GroupMembershipInfo gmInfo = new GroupMembershipInfo(); // added
						gmInfo.setHref(groupId); // added
						for (ContactEntry entry : makeInitialContacts()) {
							entry.addGroupMembershipInfo(gmInfo);
							sharedContactsService.create(entry);
						}
					}
				} else {
					System.out.println("!!!!!!!!!!!!!!!!!!!user is null");
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		return groupId;
	}

	private List<ContactEntry> makeInitialContacts() {
		List<ContactEntry> contactEntries = new ArrayList<ContactEntry>();
		contactEntries.add(sharedContactsService.makeContact(
				"Netkiller Support", "Netkiller", "Support", "Support",
				"support@netkiller.com", "1-424-785-0180",
				"2033 Gateway Place, Ste 500, San Jose, CA 95110"));
		contactEntries.add(sharedContactsService.makeContact("Netkiller Sales",
				"Netkiller", "Sales", "Sales", "sales@netkiller.com",
				"1-424-785-0180",
				"2033 Gateway Place, Ste 500, San Jose, CA 95110"));
		contactEntries
				.add(sharedContactsService
						.makeContact("Netkiller Korea", "Netkiller", "Korea",
								"Support", "support@netkiller.com",
								"82-2-2052-0453",
								"16F, Gangnam BLDG.,1321-1 Seocho-dong, Seocho-gu, Seoul 137070, South Korea"));
		return contactEntries;
	}

	/*
	 * // This method is now called from the "AddInitialGroupAndContactsTask"
	 * workflow private List<ContactEntry> makeInitialContacts() {
	 * List<ContactEntry> contactEntries = new ArrayList<ContactEntry>();
	 * contactEntries.add(makeContact("Netkiller Support", "Netkiller",
	 * "Support", "Support", "support@netkiller.com", "1-424-785-0180",
	 * "2033 Gateway Place, Ste 500, San Jose, CA 95110"));
	 * contactEntries.add(makeContact("Netkiller Sales", "Netkiller", "Sales",
	 * "Sales", "sales@netkiller.com", "1-424-785-0180",
	 * "2033 Gateway Place, Ste 500, San Jose, CA 95110"));
	 * contactEntries.add(makeContact("Netkiller Korea", "Netkiller", "Korea",
	 * "Support", "support@netkiller.com", "82-2-2052-0453",
	 * "16F, Gangnam BLDG.,1321-1 Seocho-dong, Seocho-gu, Seoul 137070, South Korea"
	 * )); return contactEntries; }
	 */

	private String getUserGroupId(String email) {

		String groupId = null;

		try {
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			String sharedContactsGroupName = sharedContactsService
					.getGroupName(CommonWebUtil.getDomain(user.getEmail()));

			groupId = sharedContactsService.getUserContactsGroupId(
					sharedContactsGroupName, email);
			logger.info("sharedContactsGroupName ===> "
					+ sharedContactsGroupName);
			logger.info("groupId ===> " + groupId);
			while (groupId == null || groupId.equals("")) {
				ContactGroupEntry group = new ContactGroupEntry();
				group.setSummary(new PlainTextConstruct(sharedContactsGroupName));
				group.setTitle(new PlainTextConstruct(sharedContactsGroupName));
				// sharedContactsService.createGroup(group,
				// getCurrentUser(request).getEmail());
				sharedContactsService.createGroup(group, email);

				groupId = sharedContactsService.getUserContactsGroupId(
						sharedContactsGroupName, email);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
		return groupId;
	}

	private String getUserGroupId(String email, String groupName) {

		String groupId = null;

		try {
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			String sharedContactsGroupName = groupName;

			groupId = sharedContactsService.getUserContactsGroupId(
					sharedContactsGroupName, email);
			logger.info("sharedContactsGroupName ===> "
					+ sharedContactsGroupName);
			logger.info("groupId ===> " + groupId);
			while (groupId == null || groupId.equals("")) {
				ContactGroupEntry group = new ContactGroupEntry();
				group.setSummary(new PlainTextConstruct(sharedContactsGroupName));
				group.setTitle(new PlainTextConstruct(sharedContactsGroupName));
				// sharedContactsService.createGroup(group,
				// getCurrentUser(request).getEmail());
				sharedContactsService.createGroup(group, email);

				groupId = sharedContactsService.getUserContactsGroupId(
						sharedContactsGroupName, email);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
		}
		return groupId;
	}

	private String userDomain;
	private String adminDomain;
	private boolean domainCheck;
	private boolean isUseForSharedContacts;
	private boolean arePropertiesLoaded;
	private static double count = 0d;
	private int groupCreationRetry = 0;

	/*
	 * @RequestMapping("/deleteDuplicateGroups.do") public
	 */

	/**
	 * ÃƒÂ¬Ã¢â‚¬ÂºÃ‚Â¹ÃƒÂ«Ã‚Â¸Ã…â€™ÃƒÂ«Ã�
	 * �Â¿Â½Ã‚Â¼ÃƒÂ¬Ã…Â¡Ã‚Â°ÃƒÂ¬Ã‚Â Ã‚Â¸
	 * URL ÃƒÂ¬Ã…Â¡Ã¢â‚¬ï¿½ÃƒÂ¬Ã‚Â²Ã‚Â­ÃƒÂ¬
	 * Ã¢â‚¬Â¹Ã…â€œ
	 * ÃƒÂ­Ã¢â‚¬ï¿½Ã¢â‚¬Å¾ÃƒÂ
	 * «Ã‚Â Ã‹â€ Ãƒ�
	 * �¬Ã…Â¾Ã¢â‚¬Å¾ÃƒÂ¬Ã¢â‚¬
	 * ÂºÃ…â€™ÃƒÂ­Ã�
	 * �Â¿Â½Ã‚Â¬ÃƒÂ¬Ã¢â‚¬â€�Ã¯Â¿Â½
	 * ÃƒÂ¬Ã¯Â¿Â½Ã‹Å“ÃƒÂ­Ã¢â‚¬Â¢Ã‚Â´
	 * ÃƒÂ¬Ã…Â¾Ã¯Â¿Â½ÃƒÂ«Ã¯Â¿Â½Ã¢â€žÂ¢
	 * ÃƒÂ¬Ã¢â‚�
	 * �Â¹Ã‚Â¤ÃƒÂ­Ã¢â‚¬â€œÃ¢â‚¬Â°,
	 * ÃƒÂªÃ‚Â°Ã¯Â¿Â½ÃƒÂ¬Ã‚Â¢Ã¢â‚¬Â¦
	 * ÃƒÂ¬Ã�
	 * �Â¡Ã¢â‚¬ï¿½ÃƒÂ¬Ã‚Â²Ã‚Â­ÃƒÂ¬Ã¯Â
	 * ¿Â½Ã¢â‚¬Å¾
	 * ÃƒÂ¬Ã‚Â²Ã‹Å“ÃƒÂ«Ã‚Â¦Ã‚Â¬
	 * 
	 * @throws AppException
	 */
	@RequestMapping("/sharedcontacts/main.do")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			ParseException, AppException {

		ModelAndView mnv = null;
		UserService userService = UserServiceFactory.getUserService();
		Customer currentCustomer = null;
		User user = userService.getCurrentUser();
		if (user != null) {

			String email = user.getEmail();
			String userId = CommonWebUtil.getUserId(email);
			String domain = CommonWebUtil.getDomain(email);

			try {
				request.getSession().setAttribute("user",
						new UserInfo(userId, email, null, null));

				// set User Email in contact service
				currentCustomer = sharedContactsService.verifyUser(email);
				request.getSession().setAttribute("useDatabase", currentCustomer.getUseDatabase());
				UserLogging userLogging = sharedContactsService.getUserLogging(
						domain, userId);
				if (userLogging == null) {
					userLogging = new UserLogging();
					userLogging.setDomain(domain);
					userLogging.setUserId(userId);
					sharedContactsService.updateUserLogging(userLogging);
				}

				// sharedContactsService.setUserEamil(getCurrentUser(request).getEmail());
			} catch (Exception e) {
				// response.sendRedirect("/login.jsp");
				// String loginURL =
				// userService.createLoginURL(request.getRequestURI());
				// response.sendRedirect(loginURL);
				// //ÃƒÂ«Ã‚Â¡Ã…â€œÃƒÂªÃ‚Â·Ã‚Â¸ÃƒÂ¬Ã¯Â¿Â½Ã‚Â¸
				// ÃƒÂ¬Ã¢â‚¬Â¢Ã‹â€ ÃƒÂ«Ã¯Â¿Â½Ã‹Å“ÃƒÂ¬Ã¢â‚¬â€œÃ‚Â´
				// ÃƒÂ¬Ã…Â¾Ã‹â€ ÃƒÂ¬Ã¯Â¿Â½Ã¢â‚¬Å¾
				// ÃƒÂªÃ‚Â²Ã‚Â½ÃƒÂ¬Ã…Â¡Ã‚Â°
				// ÃƒÂ«Ã‚Â¡Ã…â€œÃƒÂªÃ‚Â·Ã‚Â¸ÃƒÂ¬Ã¯Â¿Â½Ã‚Â¸
				// ÃƒÂ­Ã…Â½Ã‹Å“ÃƒÂ¬Ã¯Â¿Â½Ã‚Â´ÃƒÂ¬Ã‚Â§Ã¢â€šÂ¬ÃƒÂ«Ã‚Â¡Ã…â€œ
				// ÃƒÂ«Ã‚Â¦Ã‚Â¬ÃƒÂ«Ã¢â‚¬Â¹Ã‚Â¤ÃƒÂ¬Ã¯Â¿Â½Ã‚Â´ÃƒÂ«Ã‚Â Ã¢â‚¬Â°ÃƒÂ­Ã…Â Ã‚Â¸
			}
		}
		if (user == null) {
			String loginURL = userService.createLoginURL(request
					.getRequestURI());
			response.sendRedirect(loginURL); // ÃƒÂ«Ã‚Â¡Ã…â€œÃƒÂªÃ‚Â·Ã‚Â¸ÃƒÂ¬Ã¯Â¿Â½Ã‚Â¸
												// ÃƒÂ¬Ã¢â‚¬Â¢Ã‹â€ ÃƒÂ«Ã¯Â¿Â½Ã‹Å“ÃƒÂ¬Ã¢â‚¬â€œÃ‚Â´
												// ÃƒÂ¬Ã…Â¾Ã‹â€ ÃƒÂ¬Ã¯Â¿Â½Ã¢â‚¬Å¾
												// ÃƒÂªÃ‚Â²Ã‚Â½ÃƒÂ¬Ã…Â¡Ã‚Â°
												// ÃƒÂ«Ã‚Â¡Ã…â€œÃƒÂªÃ‚Â·Ã‚Â¸ÃƒÂ¬Ã¯Â¿Â½Ã‚Â¸
												// ÃƒÂ­Ã…Â½Ã‹Å“ÃƒÂ¬Ã¯Â¿Â½Ã‚Â´ÃƒÂ¬Ã‚Â§Ã¢â€šÂ¬ÃƒÂ«Ã‚Â¡Ã…â€œ
												// ÃƒÂ«Ã‚Â¦Ã‚Â¬ÃƒÂ«Ã¢â‚¬Â¹Ã‚Â¤ÃƒÂ¬Ã¯Â¿Â½Ã‚Â´ÃƒÂ«Ã‚Â Ã¢â‚¬Â°ÃƒÂ­Ã…Â Ã‚Â¸

		}

		/* else{ */
		if (!arePropertiesLoaded) { // ÃƒÂ­Ã¢â‚¬ï¿½Ã¢â‚¬Å¾ÃƒÂ«Ã‚Â¡Ã…â€œÃƒÂ­Ã¯Â¿Â½Ã‚Â¼ÃƒÂ­Ã¢â‚¬Â¹Ã‚Â°
									// ÃƒÂªÃ‚Â°Ã¢â‚¬â„¢ÃƒÂ«Ã¢â‚¬Å“Ã‚Â¤ÃƒÂ¬Ã¯Â¿Â½Ã‚Â´
									// Assign
									// ÃƒÂ«Ã¯Â¿Â½Ã‹Å“ÃƒÂ¬Ã¢â‚¬â€�Ã‹â€ ÃƒÂ«Ã…Â Ã¢â‚¬ï¿½ÃƒÂ¬Ã‚Â§Ã¢â€šÂ¬
									// ÃƒÂ­Ã¢â€žÂ¢Ã¢â‚¬Â¢ÃƒÂ¬Ã¯Â¿Â½Ã‚Â¸

			// userEmail = user.getEmail();
			// userDomain = CommonWebUtil.getDomain(userEmail);
			// adminUsername = appProperties.getUsername();
			// adminDomain = appProperties.getAdminDomain();

			domainCheck = Boolean.parseBoolean(appProperties.getDomainCheck());
			isUseForSharedContacts = Boolean.parseBoolean(appProperties
					.getIsUseForSharedContacts());

			// logger.info("userEmail: " + userEmail);
			// logger.info("userDomain: " + userDomain);
			// logger.info("adminUsername: " + adminUsername);
			logger.info("adminDomain: " + adminDomain);
			logger.info("domainCheck: " + domainCheck);
			logger.info("isUseForSharedContacts: " + isUseForSharedContacts);
			arePropertiesLoaded = true;
		}

		if (domainCheck) { // ÃƒÂ­Ã…Â Ã‚Â¹ÃƒÂ¬Ã‚Â Ã¢â‚¬Â¢
							// ÃƒÂ«Ã¯Â¿Â½Ã¢â‚¬Å¾ÃƒÂ«Ã‚Â©Ã¢â‚¬ï¿½ÃƒÂ¬Ã¯Â¿Â½Ã‚Â¸
							// ÃƒÂ¬Ã…â€œÃ‚Â ÃƒÂ¬Ã‚Â Ã¢â€šÂ¬ÃƒÂªÃ‚Â°Ã¢â€šÂ¬
							// ÃƒÂ¬Ã¢â‚¬Â¢Ã¢â‚¬Å¾ÃƒÂ«Ã¢â‚¬Â¹Ã…â€™
							// ÃƒÂ¬Ã‚Â Ã¢â‚¬ËœÃƒÂªÃ‚Â·Ã‚Â¼ÃƒÂ¬Ã¯Â¿Â½Ã‚Â¸
							// ÃƒÂªÃ‚Â²Ã‚Â½ÃƒÂ¬Ã…Â¡Ã‚Â°
							// permission error
							// ÃƒÂ«Ã‚Â©Ã¢â‚¬ï¿½ÃƒÂ¬Ã¢â‚¬Â¹Ã…â€œÃƒÂ¬Ã‚Â§Ã¢â€šÂ¬ÃƒÂ«Ã‚Â¥Ã‚Â¼
							// ÃƒÂ«Ã‚Â³Ã‚Â´ÃƒÂ«Ã†â€™Ã¢â‚¬Å¾
			if (!userDomain.equals(adminDomain)) {
				Map<String, String> result = new HashMap<String, String>();
				result.put("logoutUrl",
						userService.createLogoutURL(request.getRequestURI()));
				return new ModelAndView("/sharedcontacts/permission_error",
						"result", result);
			}
		}

		if (!StringUtils.isBlank(CommonWebUtil.getParameter(request, "group"))
				&& CommonWebUtil.getParameter(request, "cmd").equals(
						"initializeContacts")) {
			String domain = CommonWebUtil.getDomain(user.getEmail());
			System.out.println("init contacts");
			getGroupId();
			// Create a workflow to create the initial contacts and groups
			AddInitialContactsAndGroupContext context = new AddInitialContactsAndGroupContext();
			context.setDomain(domain);
			context.setEmail(user.getEmail());
			context.setGroup(CommonWebUtil.getParameter(request, "group"));

			WorkflowInfo workflowInfo = new WorkflowInfo(
					"addInitialContactsAndGroupWorkflowProcessor");
			workflowInfo.setIsNewWorkflow(true);

			Workflow workflow = new Workflow();
			workflow.setContext(context);
			workflow.setWorkflowName(workflowInfo.getWorkflowName());
			workflow.setWorkflowInstanceId(workflowInfo.getWorkflowInstance());
			workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());

			context.setWorkflowInfo(workflowInfo);
			workflowManager.triggerWorkflow(workflow);

			String message = "Shared Groups Succesfully Created";
			Map result = new HashMap();
			result.put("code", "success");
			result.put("message", message);
			return new ModelAndView("/sharedcontacts/comm_result_xml",
					"result", result);

			/*
			 * for (String userId :
			 * sharedContactsService.getAllDomainUsersIncludingAdmin(domain)) {
			 * List<ContactEntry> contactEntries = new
			 * ArrayList<ContactEntry>(); for (ContactEntry entry :
			 * makeInitialContacts()) { String userGroupId =
			 * getUserGroupId(userId + "@" +
			 * domain,CommonWebUtil.getParameter(request, "group")); // added
			 * GroupMembershipInfo userGmInfo = new GroupMembershipInfo(); //
			 * added userGmInfo.setHref(userGroupId); // added
			 * entry.addGroupMembershipInfo(userGmInfo);
			 * contactEntries.add(entry); }
			 * 
			 * try {
			 * sharedContactsService.multipleCreateUserContacts(contactEntries,
			 * user.getEmail()); } catch (AppException e) { e.printStackTrace();
			 * } }
			 */
		}
		if (getGroupId() == null
				&& !StringUtils.isBlank(CommonWebUtil.getParameter(request,
						"groupName"))) {
			sharedContactsService
					.setGroupName(CommonWebUtil.getDomain(getCurrentUser(
							request).getEmail()), CommonWebUtil.getParameter(
							request, "groupName"));
		}
		if (user != null && getGroupId() == null) {
			String sharedContactsGroupName = sharedContactsService
					.getGroupName(CommonWebUtil.getDomain(user.getEmail()));
			if (StringUtils.isBlank(sharedContactsGroupName)) {
				groupCreationRetry++;
				return new ModelAndView(
						"/sharedcontacts/promptSharedContactsGroupName",
						"result", null);
			}
		}

		String cmd = CommonWebUtil.getParameter(request, "cmd");
		logger.info("cmd:" + cmd);

		if (cmd.equals("getheader")) { // UI
										// ÃƒÂ­Ã¢â€žÂ¢Ã¢â‚¬ï¿½ÃƒÂ«Ã‚Â©Ã‚Â´
										// ÃƒÂ¬Ã†â€™Ã¯Â¿Â½ÃƒÂ«Ã¢â‚¬Â¹Ã‚Â¨

			Map<String, Object> result = new HashMap<String, Object>();

			// String adminUserName = appProperties.getUsername();
			result.put("adminUserName", currentCustomer.getAdminEmail());
			result.put("userEmail", getCurrentUser(request).getEmail());
			result.put("logoutUrl",
					"https://mail.google.com/mail/?logout&hl=en");
			result.put(
					"isUserAdmin",
					!sharedContactsService.getAllDomainUsers(
							CommonWebUtil.getDomain(getCurrentUser(request)
									.getEmail())).contains(
							CommonWebUtil.getUserId(getCurrentUser(request)
									.getEmail())));
			// result.put("logoutUrl",
			// userService.createLogoutURL(request.getRequestURI()));

			mnv = new ModelAndView("/sharedcontacts/header", "result", result);

		} else if (!sharedContactsService.isUserAuthorized(user.getEmail())) {
			Map<String, String> result = new HashMap<String, String>();
			result.put("logoutUrl",
					userService.createLogoutURL(request.getRequestURI()));
			return new ModelAndView("/sharedcontacts/authorizationerror",
					"result", result);

		}

		else if (cmd.equals("") || cmd.equals("list")) {

			// String adminUserName = appProperties.getUsername()
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("adminUserName", currentCustomer.getAdminEmail());
			result.put("userEmail", getCurrentUser(request).getEmail());
			if (sharedContactsService.isUserPermitted(getCurrentUser(request)
					.getEmail()))
				result.put("isUserPermitted", true);
			else
				result.put("isUserPermitted", false);
			String defaultGridOrder = CommonWebUtil.getParameter(request,
					"defaultGridOrder");
			result.put("defaultGridOrder", defaultGridOrder);

			result.put("isUserAdmin", sharedContactsService.isUserAdmin());
			mnv = new ModelAndView("/sharedcontacts/list", "result", result);

		} else if (cmd.equals("actcustomerremove")) {// Contact
														// ÃƒÂ¬Ã†â€™Ã¯Â¿Â½ÃƒÂ¬Ã¢â‚¬Å¾Ã‚Â±
														// ÃƒÂ¬Ã‚Â²Ã‹Å“ÃƒÂ«Ã‚Â¦Ã‚Â¬
			String domain = CommonWebUtil.getParameter(request,
					"domainToBeDeleted");
			if (domain == null) {
				String message = "Delete Operation failed";
				Map result = new HashMap();
				result.put("code", "error");
				result.put("message", message);
				return new ModelAndView("/sharedcontacts/comm_result_xml",
						"result", result);
			} else {
				sharedContactsService.removeDomainAdmin(domain);
				String message = "Succesfully Deleted";
				Map result = new HashMap();
				result.put("code", "success");
				result.put("message", message);
				return new ModelAndView("/sharedcontacts/comm_result_xml",
						"result", result);
			}
		} else if (cmd.equals("customers")) {
			Map<String, String> result = new HashMap<String, String>();
			String domainName = CommonWebUtil.getParameter(request, "domain");
			if (!StringUtils.isBlank(domainName)) {
				List<String> list = sharedContactsService
						.getAllDomainUsers(domainName);
				System.out.println("list is 888888888888" + list);
				if (list == null || list.isEmpty()) {
					result.put("domainStatus", "notInUse");
					result.put("domainToBeDeleted", domainName);
				} else
					result.put("domainStatus", "inUse");
			}
			mnv = new ModelAndView("/sharedcontacts/customers-list", "result",
					result);
		} else if (cmd.equals("actgroupcreate")) {
			// getUserGroupId(request);
			Map<String, String> result = new HashMap<String, String>();
			mnv = new ModelAndView("/sharedcontacts/customers-list", "result",
					result);
		} else if (cmd.equals("getfooter")) { // UI
												// ÃƒÂ­Ã¢â€žÂ¢Ã¢â‚¬ï¿½ÃƒÂ«Ã‚Â©Ã‚Â´
												// ÃƒÂ­Ã¢â‚¬Â¢Ã‹Å“ÃƒÂ«Ã¢â‚¬Â¹Ã‚Â¨
			Map<String, String> result = new HashMap<String, String>();
				
	

			if (!currentCustomer.getUseDatabase()) {
				int limit = 100;
				if (currentCustomer.getAccountType().equalsIgnoreCase("Paid")) {
					limit = 30000;
				} else {
					if (CommonUtil.isTheSecondTypeCustomer(currentCustomer)) {
						limit = 50;
					}
				}
				List<ContactEntry> list = sharedContactsService.getContacts(1,
						limit, getGroupId(), isUseForSharedContacts, null);
				count = list.size();
			}else{
				count = sharedContactsService.getContactCount(CommonWebUtil.getDomain(getCurrentUser(request).getEmail()));
			}
			if (!currentCustomer.getAccountType().equalsIgnoreCase("Paid")) {
				int limit = 100;
				if (CommonUtil.isTheSecondTypeCustomer(currentCustomer)) {
					limit = 50;
				}
				if(count> limit){
					count = limit;
				}
			}
			
			result.put("total", (int) count + "");
			if (currentCustomer.getAccountType().equalsIgnoreCase("Paid")) {
				Date subscribedDate = currentCustomer.getUpgradedDate();
				if (subscribedDate == null) {
					subscribedDate = new Date();
				}
				DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
				subscribedDate.setYear(subscribedDate.getYear() + 1);
				result.put("endDate", formatter.format(subscribedDate));
				result.put("isPurchased", "true"); // dummy for now
			} else
				result.put("isPurchased", "false");
			mnv = new ModelAndView("/sharedcontacts/footer", "result", result);
		} else if (cmd.equals("list_data")) { // Grid
												// ÃƒÂ«Ã¯Â¿Â½Ã‚Â°ÃƒÂ¬Ã¯Â¿Â½Ã‚Â´ÃƒÂ­Ã¢â‚¬Å¾Ã‚Â°
												// ÃƒÂ¬Ã…Â¡Ã¢â‚¬ï¿½ÃƒÂ¬Ã‚Â²Ã‚Â­
			mnv = getContacts(request, response, currentCustomer);
		} else if (cmd.equals("syncContacts")) { // Grid
													// ÃƒÂ«Ã¯Â¿Â½Ã‚Â°ÃƒÂ¬Ã¯Â¿Â½Ã‚Â´ÃƒÂ­Ã¢â‚¬Å¾Ã‚Â°
													// ÃƒÂ¬Ã…Â¡Ã¢â‚¬ï¿½ÃƒÂ¬Ã‚Â²Ã‚Â­
			mnv = syncContacts(request, response, currentCustomer);
		} else if (cmd.equals("deleteDuplicate")) { // Grid
													// ÃƒÂ«Ã¯Â¿Â½Ã‚Â°ÃƒÂ¬Ã¯Â¿Â½Ã‚Â´ÃƒÂ­Ã¢â‚¬Å¾Ã‚Â°
													// ÃƒÂ¬Ã…Â¡Ã¢â‚¬ï¿½ÃƒÂ¬Ã‚Â²Ã‚Â­
			mnv = deleteDuplicate(request, response, currentCustomer);
		} else if (cmd.equals("customers-list-data")) { // Grid
														// ÃƒÂ«Ã¯Â¿Â½Ã‚Â°ÃƒÂ¬Ã¯Â¿Â½Ã‚Â´ÃƒÂ­Ã¢â‚¬Å¾Ã‚Â°
														// ÃƒÂ¬Ã…Â¡Ã¢â‚¬ï¿½ÃƒÂ¬Ã‚Â²Ã‚Â­
			mnv = getCustomersList(request, response);
		} else if (cmd.equals("details")) { // ÃƒÂ­Ã…Â Ã‚Â¹ÃƒÂ¬Ã‚Â Ã¢â‚¬Â¢
											// ContactÃƒÂ¬Ã¯Â¿Â½Ã‹Å“
											// ÃƒÂ¬Ã†â€™Ã¯Â¿Â½ÃƒÂ¬Ã¢â‚¬Å¾Ã‚Â¸ÃƒÂ¬Ã‚Â Ã¢â‚¬Â¢ÃƒÂ«Ã‚Â³Ã‚Â´
											// ÃƒÂ¬Ã…Â¡Ã¢â‚¬ï¿½ÃƒÂ¬Ã‚Â²Ã‚Â­

			// return new ModelAndView("/sharedcontacts/details", "result",
			// result);
			ContactEntry contact = getContact(request, response);

			String page = CommonWebUtil.getParameter(request, "page"); // get
																		// the
																		// requested
																		// page
			String rows = CommonWebUtil.getParameter(request, "rows"); // get
																		// how
																		// many
																		// rows
																		// we
																		// want
																		// to
																		// have
																		// into
																		// the
																		// grid
			String sidx = CommonWebUtil.getParameter(request, "sidx"); // orderby
																		// Sorting
																		// criterion.
																		// The
																		// only
																		// supported
																		// value
																		// is
																		// lastmodified.
			String sord = CommonWebUtil.getParameter(request, "sord"); // ascending
																		// or
																		// descending.
			logger.info("### PARAMS: page: " + page + ", " + "rows: " + rows
					+ ", " + "sidx: " + sidx + ", " + "sord: " + sord);

			Map result = new HashMap();
			result.put("page", page);
			result.put("rows", rows);
			result.put("sidx", sidx);
			result.put("sord", sord);
			result.put("contact", contact);
			result.put("userEmail", getCurrentUser(request).getEmail());
			// String adminUserName = appProperties.getUsername();			
			result.put("adminUserName", currentCustomer.getAdminEmail());
			if (sharedContactsService.isUserPermitted(getCurrentUser(request)
					.getEmail()))
				result.put("isUserPermitted", true);
			else
				result.put("isUserPermitted", false);
			result.put("isUserAdmin", sharedContactsService.isUserAdmin());
			mnv = new ModelAndView("/sharedcontacts/details", "result", result);
		} else if (cmd.equals("create")) { // Contact
											// ÃƒÂ¬Ã†â€™Ã¯Â¿Â½ÃƒÂ¬Ã¢â‚¬Å¾Ã‚Â±ÃƒÂ¬Ã¯Â¿Â½Ã¢â‚¬Å¾
											// ÃƒÂ¬Ã…â€œÃ¢â‚¬Å¾ÃƒÂ­Ã¢â‚¬Â¢Ã…â€œ
											// ÃƒÂ­Ã¢â€žÂ¢Ã¢â‚¬ï¿½ÃƒÂ«Ã‚Â©Ã‚Â´
											// ÃƒÂ¬Ã…Â¡Ã¢â‚¬ï¿½ÃƒÂ¬Ã‚Â²Ã‚Â­
			mnv = new ModelAndView("/sharedcontacts/create");
		} else if (cmd.equals("actcreate")) {// Contact
												// ÃƒÂ¬Ã†â€™Ã¯Â¿Â½ÃƒÂ¬Ã¢â‚¬Å¾Ã‚Â±
												// ÃƒÂ¬Ã‚Â²Ã‹Å“ÃƒÂ«Ã‚Â¦Ã‚Â¬
			if ((!currentCustomer.getAccountType().equals("Paid") && count == 100)
					|| (!currentCustomer.getAccountType().equals("Paid")
							&& count == 50 && CommonUtil
							.isTheSecondTypeCustomer(currentCustomer))) {
				String message = messageSource.getMessage("account.limit",
						null, Locale.US);
				Map result = new HashMap();
				result.put("code", "error");
				result.put("message", message);
				return new ModelAndView("/sharedcontacts/comm_result_xml",
						"result", result);
			} else {
				mnv = create(request, response);
			}
		} else if (cmd.equals("modify")) { // Contact
											// ÃƒÂªÃ‚Â°Ã‚Â±ÃƒÂ¬Ã¢â‚¬Â¹Ã‚Â ÃƒÂ¬Ã¯Â¿Â½Ã¢â‚¬Å¾
											// ÃƒÂ¬Ã…â€œÃ¢â‚¬Å¾ÃƒÂ­Ã¢â‚¬Â¢Ã…â€œ
											// ÃƒÂ­Ã¢â€žÂ¢Ã¢â‚¬ï¿½ÃƒÂ«Ã‚Â©Ã‚Â´
											// ÃƒÂ¬Ã…Â¡Ã¢â‚¬ï¿½ÃƒÂ¬Ã‚Â²Ã‚Â­
			// mnv = getContact(request, response);
			ContactEntry contact = getContact(request, response);

			String page = CommonWebUtil.getParameter(request, "page"); // get
																		// the
																		// requested
																		// page
			String rows = CommonWebUtil.getParameter(request, "rows"); // get
																		// how
																		// many
																		// rows
																		// we
																		// want
																		// to
																		// have
																		// into
																		// the
																		// grid
			String sidx = CommonWebUtil.getParameter(request, "sidx"); // orderby
																		// Sorting
																		// criterion.
																		// The
																		// only
																		// supported
																		// value
																		// is
																		// lastmodified.
			String sord = CommonWebUtil.getParameter(request, "sord"); // ascending
																		// or
																		// descending.
			logger.info("### PARAMS: page: " + page + ", " + "rows: " + rows
					+ ", " + "sidx: " + sidx + ", " + "sord: " + sord);

			Map result = new HashMap();
			result.put("page", page);
			result.put("rows", rows);
			result.put("sidx", sidx);
			result.put("sord", sord);
			result.put("contact", contact);
			result.put("userEmail", getCurrentUser(request).getEmail());
			// String adminUserName = appProperties.getUsername();
			result.put("adminUserName", currentCustomer.getAdminEmail());
			mnv = new ModelAndView("/sharedcontacts/modify", "result", result);
		} else if (cmd.equals("actmodify")) { // Contact
												// ÃƒÂªÃ‚Â°Ã‚Â±ÃƒÂ¬Ã¢â‚¬Â¹Ã‚Â 
												// ÃƒÂ¬Ã‚Â²Ã‹Å“ÃƒÂ«Ã‚Â¦Ã‚Â¬
			mnv = modify(request, response);
		} else if (cmd.equals("actmodifysimply")) { // ÃƒÂ¬Ã¢â‚¬Å¡Ã‚Â¬ÃƒÂ¬Ã…Â¡Ã‚Â©ÃƒÂ¬Ã…Â¾Ã¯Â¿Â½
													// ÃƒÂ­Ã¢â€žÂ¢Ã¢â‚¬ï¿½ÃƒÂ«Ã‚Â©Ã‚Â´ÃƒÂ¬Ã¯Â¿Â½Ã‹Å“
													// Contact Grid
													// ÃƒÂ¬Ã†â€™Ã¯Â¿Â½
													// ÃƒÂªÃ‚Â°Ã¢â‚¬Å¾ÃƒÂ«Ã¢â‚¬Â¹Ã‚Â¨ÃƒÂ­Ã¢â‚¬Â¢Ã…â€œ
													// Contact
													// ÃƒÂªÃ‚Â°Ã‚Â±ÃƒÂ¬Ã¢â‚¬Â¹Ã‚Â 
													// ÃƒÂ¬Ã‚Â²Ã‹Å“ÃƒÂ«Ã‚Â¦Ã‚Â¬
			mnv = modifySimply(request, response);
		} else if (cmd.equals("actremove")) { // Contact
												// ÃƒÂ¬Ã¢â‚¬Å¡Ã‚Â­ÃƒÂ¬Ã‚Â Ã…â€œ
												// ÃƒÂ¬Ã‚Â²Ã‹Å“ÃƒÂ«Ã‚Â¦Ã‚Â¬
			mnv = remove(request, response);
		} else if (cmd.equals("actdownload")) {
		//	mnv = download(request, response, currentCustomer);
		} else if (cmd.equals("multicreate")) {
			mnv = new ModelAndView("/sharedcontacts/multicreate");
		} else if (cmd.equals("upgrade")) {
			mnv = upgrade(request, response, currentCustomer);
		} else if (cmd.equals("updateStatus")) {
			mnv = updateStatus(request, response, currentCustomer);
		} else if (cmd.equals("showmap")) {

			Map result = new HashMap();
			result.put("address",
					CommonWebUtil.getParameter(request, "address"));
			mnv = new ModelAndView("/sharedcontacts/map", "result", result);
		} else if (cmd.equals("authorizeForm")) {

			mnv = getAuthorizeForm(request, response, currentCustomer);
			;
		}

		else if (cmd.equals("authorize")) {

			mnv = authorize(request, response, currentCustomer);
			;
		} else if (cmd.equals("unauthorizeForm")) {

			mnv = getUnAuthorizeForm(request, response, currentCustomer);

		}

		else if (cmd.equals("unauthorize")) {

			mnv = unauthorize(request, response, currentCustomer);
			;
		}
		// }//end else
		return mnv;
	}

	private ModelAndView deleteDuplicate(HttpServletRequest request,
			HttpServletResponse response, Customer currentCustomer) {
		// TODO Auto-generated method stub

		String email = getCurrentUser(request).getEmail();
		String domain = CommonWebUtil.getDomain(email);
		Map<String, String> result = new HashMap<String, String>();
		String message = null;
		String groupName = sharedContactsService.getGroupName(domain);
		boolean success = true;
		try {
			sharedContactsService.removeDuplicateGroups(groupName, email);
			message = "Duplicate Groups removed successfully";
		} catch (Exception e) {
			e.printStackTrace();
			success = false;
			message = "Error Occured";
		}
		if (success) {
			result.put("code", "success");
		} else {
			result.put("code", "error");
		}
		result.put("message", message);

		return new ModelAndView("/sharedcontacts/comm_result_xml", "result",
				result);
	}

	private ModelAndView syncContacts(HttpServletRequest request,
			HttpServletResponse response, Customer currentCustomer) {
		// TODO Auto-generated method stub
		//List<ContactEntry> entries = null;
		int total_pages = 0;
		double start = 0;
		String groupId = null;
		int totalLimit = 100;
		try {

			// entries = sharedContactsService.getContacts(page, rows, sidx,
			// sord);

			if (isUseForSharedContacts) {
				groupId = getGroupId();
			}

			if (currentCustomer.getAccountType().equalsIgnoreCase("Paid")) {
				totalLimit = 30000;
			} else {
				if (CommonUtil.isTheSecondTypeCustomer(currentCustomer)) {
					totalLimit = 50;
				}
			}

			/*entries = sharedContactsService.getContacts(1, totalLimit, groupId,
					isUseForSharedContacts, null);
			logger.info("### 1. entries.size() ===> " + entries.size());*/

		} catch (Exception e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		Date date = new Date();
		Integer noOfSyncs = 1;

		DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String dateString = formatter.format(date);
		String email = getCurrentUser(request).getEmail();
		UserSync userSync = sharedContactsService
				.getUserSync(email, dateString);
		if (userSync != null) {
			if (userSync.getDate().equals(dateString)) {
				noOfSyncs = userSync.getNoOfSyncs();
				if (noOfSyncs == null) {
					noOfSyncs = 1;
				} else {
					noOfSyncs++;
				}
				userSync.setNoOfSyncs(noOfSyncs);
				userSync.setDate(dateString);
			}
		}
		Map<String, String> result = new HashMap<String, String>();
		String message = null;
		if (userSync == null || (userSync != null && noOfSyncs <= SYNC_LIMIT)) {
			SyncUserContactsContext syncUserContactsContext = new SyncUserContactsContext();
			syncUserContactsContext.setUserEmail(getCurrentUser(request)
					.getEmail());
			syncUserContactsContext.setGroupId(groupId);
			syncUserContactsContext
					.setIsUseForSharedContacts(isUseForSharedContacts);
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
		if (userSync == null) {
			sharedContactsService.createUserSync(email,
					CommonWebUtil.getDomain(email), dateString);
		} else {
			sharedContactsService.updateExistingUserSync(userSync);
		}

		result.put("code", "success");
		result.put("message", message);

		return new ModelAndView("/sharedcontacts/comm_result_xml", "result",
				result);

	}

	private ContactInfo getContactInfo(HttpServletRequest request) {
		String fullname = CommonWebUtil.getParameter(request, "fullname");
		String givenname = CommonWebUtil.getParameter(request, "givenname");
		String familyname = CommonWebUtil.getParameter(request, "familyname");
		String companyname = CommonWebUtil.getParameter(request, "companyname");
		String companydept = CommonWebUtil.getParameter(request, "companydept");
		String companytitle = CommonWebUtil.getParameter(request,
				"companytitle");
		String workemail = CommonWebUtil.getParameter(request, "workemail");
		String homeemail = CommonWebUtil.getParameter(request, "homeemail");
		String otheremail = CommonWebUtil.getParameter(request, "otheremail");
		String workphone = CommonWebUtil.getParameter(request, "workphone");
		String homephone = CommonWebUtil.getParameter(request, "homephone");
		String mobilephone = CommonWebUtil.getParameter(request, "mobilephone");
		String workaddress = CommonWebUtil.getParameter(request, "workaddress");
		String homeaddress = CommonWebUtil.getParameter(request, "homeaddress");
		String otheraddress = CommonWebUtil.getParameter(request,
				"otheraddress");
		String notes = CommonWebUtil.getParameter(request, "notes");

		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setCompanydept(companydept);
		contactInfo.setCompanyname(companyname);
		contactInfo.setCompanytitle(companytitle);
		contactInfo.setFamilyname(familyname);
		contactInfo.setFullname(fullname);
		contactInfo.setGivenname(givenname);
		contactInfo.setHomeaddress(homeaddress);
		contactInfo.setHomeemail(homeemail);
		contactInfo.setHomephone(homephone);
		contactInfo.setMobilephone(mobilephone);
		contactInfo.setNotes(notes);
		contactInfo.setOtheraddress(otheraddress);
		contactInfo.setOtheremail(otheremail);
		contactInfo.setWorkaddress(workaddress);
		contactInfo.setWorkemail(workemail);
		contactInfo.setWorkphone(workphone);

		return contactInfo;

	}

	/**
	 * ÃƒÂ¬Ã¢â‚¬ÂºÃ‚Â¹ÃƒÂ«Ã‚Â¸Ã…â€™ÃƒÂ«Ã�
	 * �Â¿Â½Ã‚Â¼ÃƒÂ¬Ã…Â¡Ã‚Â°ÃƒÂ¬Ã‚Â Ã‚Â¸Ã�
	 * �Â«Ã‚Â¡Ã…â€œÃƒÂ«Ã‚Â¶Ã¢â€šÂ¬ÃƒÂ
	 * ­Ã¢â‚¬Å¾Ã‚Â°
	 * ÃƒÂ¬Ã‚Â Ã¢â‚¬Å¾ÃƒÂ¬Ã¢â‚�
	 * �Â Ã‚Â¡ÃƒÂ«Ã¯Â¿Â½Ã…â€œ
	 * ÃƒÂ«Ã¯Â¿Â½Ã‚Â
	 * °ÃƒÂ¬Ã¯Â¿Â½Ã‚Â´ÃƒÂ­Ã¢â‚�
	 * �Å¾Ã‚Â°ÃƒÂ«Ã¢â‚�
	 * �Å“Ã‚Â¤ÃƒÂ¬Ã¯Â¿Â½Ã¢â‚¬Å¾
	 * ÃƒÂ¬Ã¯Â¿Â½Ã‚�
	 * �´ÃƒÂ¬Ã…Â¡Ã‚Â©ÃƒÂ­Ã¢â‚¬Â¢Ã‚Â´
	 * ContactEntryÃƒÂ«Ã‚Â¥Ã‚Â¼
	 * ÃƒÂ¬Ã†â€™Ã¯Â¿Â½ÃƒÂ¬Ã¢â‚¬Å¾Ã‚Â±
	 * 
	 * @param request
	 * @return
	 */
	private ContactEntry makeContact(HttpServletRequest request) {

		String fullname = CommonWebUtil.getParameter(request, "fullname");
		String givenname = CommonWebUtil.getParameter(request, "givenname");
		String familyname = CommonWebUtil.getParameter(request, "familyname");
		String companyname = CommonWebUtil.getParameter(request, "companyname");
		String companydept = CommonWebUtil.getParameter(request, "companydept");
		String companytitle = CommonWebUtil.getParameter(request,
				"companytitle");
		String workemail = CommonWebUtil.getParameter(request, "workemail");
		String homeemail = CommonWebUtil.getParameter(request, "homeemail");
		String otheremail = CommonWebUtil.getParameter(request, "otheremail");
		String workphone = CommonWebUtil.getParameter(request, "workphone");
		String homephone = CommonWebUtil.getParameter(request, "homephone");
		String mobilephone = CommonWebUtil.getParameter(request, "mobilephone");
		String workaddress = CommonWebUtil.getParameter(request, "workaddress");
		String homeaddress = CommonWebUtil.getParameter(request, "homeaddress");
		String otheraddress = CommonWebUtil.getParameter(request,
				"otheraddress");
		String notes = CommonWebUtil.getParameter(request, "notes");

		logger.info("fullname: " + fullname);
		logger.info("givenname: " + givenname);
		logger.info("familyname: " + familyname);
		logger.info("companyname: " + companyname);
		logger.info("companydept: " + companydept);
		logger.info("companytitle: " + companytitle);
		logger.info("workemail: " + workemail);
		logger.info("homeemail: " + homeemail);
		logger.info("otheremail: " + otheremail);
		logger.info("workphone: " + workphone);
		logger.info("homephone: " + homephone);
		logger.info("mobilephone: " + mobilephone);
		logger.info("workaddress: " + workaddress);
		logger.info("homeaddress: " + homeaddress);
		logger.info("otheraddress: " + otheraddress);
		logger.info("notes: " + notes);

		// String homeRel = "http://schemas.google.com/g/2005#home";
		// String workRel = "http://schemas.google.com/g/2005#work";
		// String otherRel = "http://schemas.google.com/g/2005#other";
		// String mobileRel = "http://schemas.google.com/g/2005#mobile";

		ContactEntry contact = new ContactEntry();

		Name name = new Name();
		if (!fullname.equals("")) {
			name.setFullName(new FullName(fullname, null));
		}
		if (!givenname.equals("")) {
			name.setGivenName(new GivenName(givenname, null));
		}
		if (!familyname.equals("")) {
			name.setFamilyName(new FamilyName(familyname, null));
		}
		contact.setName(name);

		if (!companyname.equals("") || !companytitle.equals("")) {
			Organization org = new Organization();
			if (!companyname.equals("")) {
				org.setOrgName(new OrgName(companyname));
			}
			if (!companydept.equals("")) {
				org.setOrgDepartment(new OrgDepartment(companydept));
			}
			if (!companytitle.equals("")) {
				org.setOrgTitle(new OrgTitle(companytitle));
			}
			org.setRel(StaticProperties.WORK_REL);
			contact.addOrganization(org);
		}

		if (!workemail.equals("")) {
			Email workEmail = new Email();
			workEmail.setAddress(workemail);
			workEmail.setRel(StaticProperties.WORK_REL);
			contact.addEmailAddress(workEmail);
		}

		if (!homeemail.equals("")) {
			Email homeEmail = new Email();
			homeEmail.setAddress(homeemail);
			homeEmail.setRel(StaticProperties.HOME_REL);
			contact.addEmailAddress(homeEmail);
		}

		if (!otheremail.equals("")) {
			Email otherEmail = new Email();
			otherEmail.setAddress(otheremail);
			otherEmail.setRel(StaticProperties.OTHER_REL);
			contact.addEmailAddress(otherEmail);
		}

		if (!workphone.equals("")) {
			PhoneNumber workPhone = new PhoneNumber();
			workPhone.setPhoneNumber(workphone);
			workPhone.setRel(StaticProperties.WORK_REL);
			contact.addPhoneNumber(workPhone);
		}

		if (!homephone.equals("")) {
			PhoneNumber homePhone = new PhoneNumber();
			homePhone.setPhoneNumber(homephone);
			homePhone.setRel(StaticProperties.HOME_REL);
			contact.addPhoneNumber(homePhone);
		}

		if (!mobilephone.equals("")) {
			PhoneNumber mobilePhone = new PhoneNumber();
			mobilePhone.setPhoneNumber(mobilephone);
			mobilePhone.setRel(StaticProperties.MOBILE_REL);
			contact.addPhoneNumber(mobilePhone);
		}

		/*
		 * if(!workaddress.equals("")){ ExtendedProperty extProp = new
		 * ExtendedProperty(); extProp.setName("workAddress");
		 * extProp.setValue(workaddress); contact.addExtendedProperty(extProp);
		 * } if(!homeaddress.equals("")){ ExtendedProperty extProp = new
		 * ExtendedProperty(); extProp.setName("homeAddress");
		 * extProp.setValue(homeaddress); contact.addExtendedProperty(extProp);
		 * } if(!otheraddress.equals("")){ ExtendedProperty extProp = new
		 * ExtendedProperty(); extProp.setName("otherAddress");
		 * extProp.setValue(otheraddress); contact.addExtendedProperty(extProp);
		 * }//
		 */

		if (!workaddress.equals("")) {
			FormattedAddress formattedAddress = new FormattedAddress(
					workaddress);
			StructuredPostalAddress postalAddress = new StructuredPostalAddress();
			postalAddress.setFormattedAddress(formattedAddress);
			postalAddress.setRel(StaticProperties.WORK_REL);
			// contact.addExtension(postalAddress);
			contact.addRepeatingExtension(postalAddress);
		}

		if (!homeaddress.equals("")) {
			FormattedAddress formattedAddress = new FormattedAddress(
					homeaddress);
			StructuredPostalAddress postalAddress = new StructuredPostalAddress();
			postalAddress.setFormattedAddress(formattedAddress);
			postalAddress.setRel(StaticProperties.HOME_REL);
			// contact.addExtension(postalAddress);
			contact.addRepeatingExtension(postalAddress);
		}

		if (!otheraddress.equals("")) {
			FormattedAddress formattedAddress = new FormattedAddress(
					otheraddress);
			StructuredPostalAddress postalAddress = new StructuredPostalAddress();
			postalAddress.setFormattedAddress(formattedAddress);
			postalAddress.setRel(StaticProperties.OTHER_REL);
			// contact.addExtension(postalAddress);
			contact.addRepeatingExtension(postalAddress);
		}

		/*
		 * if(!workaddress.equals("")){ PostalAddress workAddress = new
		 * PostalAddress(); workAddress.setValue(workaddress);
		 * workAddress.setRel(workRel); contact.addPostalAddress(workAddress); }
		 * 
		 * if(!homeaddress.equals("")){ PostalAddress homeAddress = new
		 * PostalAddress(); homeAddress.setValue(homeaddress);
		 * homeAddress.setRel(homeRel); contact.addPostalAddress(homeAddress); }
		 * 
		 * if(!otheraddress.equals("")){ PostalAddress otherAddress = new
		 * PostalAddress(); otherAddress.setValue(otheraddress);
		 * otherAddress.setRel(otherRel);
		 * contact.addPostalAddress(otherAddress); }
		 */

		if (!notes.equals("")) {
			// contact.setContent(new PlainTextConstruct(notes));
			contact.getUserDefinedFields().add(
					new UserDefinedField("Notes", notes));
		}

		return contact;
	}

	public ModelAndView modifySimply(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		printParams(request);
		Customer currentCustomer = sharedContactsService.verifyUser(getCurrentUser(request).getEmail());
		String id = CommonWebUtil.getParameter(request, "id");
		logger.info("=====> id: " + id);

		// String name = CommonWebUtil.getParameter(request, "name");
		String givenname = CommonWebUtil.getParameter(request, "givenname");
		String familyname = CommonWebUtil.getParameter(request, "familyname");

		String company = CommonWebUtil.getParameter(request, "company");
		String email = CommonWebUtil.getParameter(request, "email");
		String phone = CommonWebUtil.getParameter(request, "phone");
		String workaddress = CommonWebUtil.getParameter(request, "address");
		String notes = CommonWebUtil.getParameter(request, "notes");

		// if(true) return new ModelAndView("/sharedcontacts/comm_result_xml",
		// "result", null);

		// String homeRel = "http://schemas.google.com/g/2005#home";
		// String workRel = "http://schemas.google.com/g/2005#work";
		// String otherRel = "http://schemas.google.com/g/2005#other";
		// String mobileRel = "http://schemas.google.com/g/2005#mobile";

		Map result = new HashMap();

		try {

			ContactEntry entry = sharedContactsService.getContact(id);
			SharedContactsUtil util = SharedContactsUtil.getInstance();

			Name nameObj = entry.getName();
			if (nameObj != null) {
				// FullName fullName = nameObj.getFullName();
				// if(fullName != null){
				// fullName.setValue(name);
				// }
				// else{
				// fullName = new FullName(name, null);
				// nameObj.setFullName(fullName);
				// }
				GivenName givenName = nameObj.getGivenName();
				if (givenName != null) {
					givenName.setValue(givenname);
				} else {
					givenName = new GivenName(givenname, null);
					nameObj.setGivenName(givenName);
				}

				FamilyName familyName = nameObj.getFamilyName();
				if (familyName != null) {
					familyName.setValue(familyname);
				} else {
					familyName = new FamilyName(familyname, null);
					nameObj.setFamilyName(familyName);
				}
			} else {
				GivenName givenName = new GivenName(givenname, null);
				FamilyName familyName = new FamilyName(familyname, null);
				nameObj = new Name();
				nameObj.setGivenName(givenName);
				nameObj.setFamilyName(familyName);
				entry.setName(nameObj);
			}

			if (!company.equals("")) {
				Organization org = util.getOrganizationObj(entry, "work");
				if (org != null) {
					OrgName orgName = org.getOrgName();
					if (orgName != null) {
						orgName.setValue(company);
					} else {
						orgName = new OrgName();
						orgName.setValue(company);
						org.setOrgName(orgName);
					}
				} else {
					OrgName orgName = new OrgName(company, null);
					org = new Organization();
					org.setOrgName(orgName);
					org.setRel(StaticProperties.WORK_REL);
					entry.addOrganization(org);
				}
			}

			if (!email.equals("")) {
				Email emailObj = util.getEmailObj(entry, "work");
				if (emailObj != null) {
					emailObj.setAddress(email);
				} else {
					emailObj = new Email();
					emailObj.setAddress(email);
					emailObj.setRel(StaticProperties.WORK_REL);
					emailObj.setPrimary(false);
					entry.addEmailAddress(emailObj);
				}
			}

			if (!phone.equals("")) {
				PhoneNumber phoneObj = util.getPhoneNumberObj(entry, "work");
				if (phoneObj != null) {
					phoneObj.setPhoneNumber(phone);
				} else {
					phoneObj = new PhoneNumber();
					phoneObj.setPhoneNumber(phone);
					phoneObj.setRel(StaticProperties.WORK_REL);
					phoneObj.setPrimary(false);
					entry.addPhoneNumber(phoneObj);
				}
			}

			if (workaddress.equals("")) {
				util.removeStructuredPostalAddress(entry,
						StaticProperties.WORK_REL);
			} else {
				StructuredPostalAddress address = util
						.getStructuredPostalAddress(entry,
								StaticProperties.WORK_REL);

				logger.info("address is null? " + (address == null));

				if (address != null) {
					FormattedAddress formattedAddress = address
							.getFormattedAddress();
					logger.info("formattedAddress is null? "
							+ (formattedAddress == null));
					if (formattedAddress != null) {
						formattedAddress.setValue(workaddress);
					} else {
						formattedAddress = new FormattedAddress();
						formattedAddress.setValue(workaddress);
						address.setFormattedAddress(formattedAddress);
					}
				} else {
					address = new StructuredPostalAddress();
					FormattedAddress formattedAddress = new FormattedAddress();
					formattedAddress.setValue(workaddress);
					address.setFormattedAddress(formattedAddress);
					// address.setLabel("Work Address");
					address.setRel(StaticProperties.WORK_REL);
					entry.addStructuredPostalAddress(address);
				}
			}

			// if(!notes.equals("")){
			// entry.setContent(new PlainTextConstruct(notes));
			// }

			entry = sharedContactsService.update(entry);
			ContactInfo contactInfo = util.makeContactInfo(entry);
			if (currentCustomer.getUseDatabase()) {
				sharedContactsService.createContactInfo(contactInfo);
			}
			String message = messageSource.getMessage("modification.success",
					null, Locale.US);

			result.put("code", "success");
			result.put("message", message);
			logger.info("No error!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
			String message = messageSource.getMessage("error.default", null,
					Locale.US);
			result.put("code", "error");
			result.put("message", message);
		}
		return new ModelAndView("/sharedcontacts/comm_result_xml", "result",
				result);
	}

	public ModelAndView modify(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String id = CommonWebUtil.getParameter(request, "id");
		logger.info("=====> id: " + id);

		Map result = new HashMap();

		try {
			String email = getCurrentUser(request).getEmail();
			Customer currentCustomer = sharedContactsService.verifyUser(email);
			ContactEntry contact = sharedContactsService.getContact(id);
			logger.info("contact is null? " + (contact == null));
			logger.info("contact.getEditLink().getHref()? "
					+ contact.getEditLink().getHref());
			update(request, contact);
			contact = sharedContactsService.update(contact);
			ContactInfo contactInfo = SharedContactsUtil.getInstance().makeContactInfo(contact);
			if (currentCustomer.getUseDatabase()) {
				sharedContactsService.createContactInfo(contactInfo);
			}
			String message = messageSource.getMessage("modification.success",
					null, Locale.US);
			result.put("code", "success");
			result.put("message", message);
			logger.info("No error!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
			String message = messageSource.getMessage("error.default", null,
					Locale.US);
			result.put("code", "error");
			result.put("message", message);
		}
		return new ModelAndView("/sharedcontacts/comm_result_xml", "result",
				result);
	}

	private void update(HttpServletRequest request, ContactEntry contact) {

		String fullname = CommonWebUtil.getParameter(request, "fullname");
		String givenname = CommonWebUtil.getParameter(request, "givenname");
		String familyname = CommonWebUtil.getParameter(request, "familyname");
		String companyname = CommonWebUtil.getParameter(request, "companyname");
		String companydept = CommonWebUtil.getParameter(request, "companydept");
		String companytitle = CommonWebUtil.getParameter(request,
				"companytitle");
		String workemail = CommonWebUtil.getParameter(request, "workemail");
		String homeemail = CommonWebUtil.getParameter(request, "homeemail");
		String otheremail = CommonWebUtil.getParameter(request, "otheremail");
		String workphone = CommonWebUtil.getParameter(request, "workphone");
		String homephone = CommonWebUtil.getParameter(request, "homephone");
		String mobilephone = CommonWebUtil.getParameter(request, "mobilephone");
		String workaddress = CommonWebUtil.getParameter(request, "workaddress");
		String homeaddress = CommonWebUtil.getParameter(request, "homeaddress");
		String otheraddress = CommonWebUtil.getParameter(request,
				"otheraddress");
		String notes = CommonWebUtil.getParameter(request, "notes");

		logger.info("fullname: " + fullname);
		logger.info("givenname: " + givenname);
		logger.info("familyname: " + familyname);
		logger.info("companyname: " + companyname);
		logger.info("companydept: " + companydept);
		logger.info("companytitle: " + companytitle);
		logger.info("workemail: " + workemail);
		logger.info("homeemail: " + homeemail);
		logger.info("otheremail: " + otheremail);
		logger.info("workphone: " + workphone);
		logger.info("homephone: " + homephone);
		logger.info("mobilephone: " + mobilephone);
		logger.info("workaddress: " + workaddress);
		logger.info("homeaddress: " + homeaddress);
		logger.info("otheraddress: " + otheraddress);
		logger.info("notes: " + notes);

		// String homeRel = "http://schemas.google.com/g/2005#home";
		// String workRel = "http://schemas.google.com/g/2005#work";
		// String otherRel = "http://schemas.google.com/g/2005#other";
		// String mobileRel = "http://schemas.google.com/g/2005#mobile";

		Name name = new Name();
		if (!fullname.equals("")) {
			FullName fullName = new FullName(fullname, null);
			name.setFullName(fullName);
		}
		if (!givenname.equals("")) {
			GivenName givenName = new GivenName(givenname, null);
			name.setGivenName(givenName);
		}
		if (!familyname.equals("")) {
			FamilyName familyName = new FamilyName(familyname, null);
			name.setFamilyName(familyName);
		}
		contact.setName(name);

		SharedContactsUtil util = SharedContactsUtil.getInstance();
		Organization org = util.getOrganizationObj(contact, "work");
		if (org != null) {
			if (!companyname.equals("")) {
				org.setOrgName(new OrgName(companyname));
			} else {
				org.setOrgName(null);
			}
			if (!companydept.equals("")) {
				org.setOrgDepartment(new OrgDepartment(companydept));
			} else {
				org.setOrgDepartment(null);
			}
			if (!companytitle.equals("")) {
				org.setOrgTitle(new OrgTitle(companytitle));
			} else {
				org.setOrgTitle(null);
			}
		} else {
			if (!companyname.equals("") || !companytitle.equals("")) {
				org = new Organization();
				if (!companyname.equals("")) {
					org.setOrgName(new OrgName(companyname));
				}
				if (!companydept.equals("")) {
					org.setOrgDepartment(new OrgDepartment(companydept));
				}
				if (!companytitle.equals("")) {
					org.setOrgTitle(new OrgTitle(companytitle));
				}
				org.setRel(StaticProperties.WORK_REL);
				contact.addOrganization(org);
			}
		}

		if (workemail.equals("")) {
			util.removeEmailObj(contact, "work");
		} else {
			Email workEmail = util.getEmailObj(contact, "work");
			if (workEmail != null) {
				workEmail.setAddress(workemail);
			} else {
				if (!workemail.equals("")) {
					workEmail = new Email();
					workEmail.setAddress(workemail);
					workEmail.setRel(StaticProperties.WORK_REL);
					workEmail.setPrimary(false);
					contact.addEmailAddress(workEmail);
				}
			}
		}

		if (homeemail.equals("")) {
			util.removeEmailObj(contact, "home");
		} else {
			Email homeEmail = util.getEmailObj(contact, "home");
			if (homeEmail != null) {
				homeEmail.setAddress(homeemail);
			} else {
				if (!homeemail.equals("")) {
					homeEmail = new Email();
					homeEmail.setAddress(homeemail);
					homeEmail.setRel(StaticProperties.HOME_REL);
					homeEmail.setPrimary(false);
					contact.addEmailAddress(homeEmail);
				}
			}
		}

		if (otheremail.equals("")) {
			util.removeEmailObj(contact, "other");
		} else {
			Email otherEmail = util.getEmailObj(contact, "other");
			if (otherEmail != null) {
				otherEmail.setAddress(otheremail);
			} else {
				if (!otheremail.equals("")) {
					otherEmail = new Email();
					otherEmail.setAddress(otheremail);
					otherEmail.setRel(StaticProperties.OTHER_REL);
					otherEmail.setPrimary(false);
					contact.addEmailAddress(otherEmail);
				}
			}
		}

		if (workphone.equals("")) {
			util.removePhoneNumberObj(contact, "work");
		} else {
			PhoneNumber workPhoneNumber = util.getPhoneNumberObj(contact,
					"work");
			if (workPhoneNumber != null) {
				workPhoneNumber.setPhoneNumber(workphone);
			} else {
				if (!workphone.equals("")) {
					workPhoneNumber = new PhoneNumber();
					workPhoneNumber.setPhoneNumber(workphone);
					workPhoneNumber.setRel(StaticProperties.WORK_REL);
					workPhoneNumber.setPrimary(false);
					contact.addPhoneNumber(workPhoneNumber);
				}
			}
		}

		if (homephone.equals("")) {
			util.removePhoneNumberObj(contact, "home");
		} else {
			PhoneNumber homePhoneNumber = util.getPhoneNumberObj(contact,
					"home");
			if (homePhoneNumber != null) {
				homePhoneNumber.setPhoneNumber(homephone);
			} else {
				if (!homephone.equals("")) {
					homePhoneNumber = new PhoneNumber();
					homePhoneNumber.setPhoneNumber(homephone);
					homePhoneNumber.setRel(StaticProperties.HOME_REL);
					homePhoneNumber.setPrimary(false);
					contact.addPhoneNumber(homePhoneNumber);
				}
			}
		}

		if (mobilephone.equals("")) {
			util.removePhoneNumberObj(contact, "mobile");
		} else {
			PhoneNumber mobilePhoneNumber = util.getPhoneNumberObj(contact,
					"mobile");
			if (mobilePhoneNumber != null) {
				mobilePhoneNumber.setPhoneNumber(mobilephone);
			} else {
				if (!mobilephone.equals("")) {
					mobilePhoneNumber = new PhoneNumber();
					mobilePhoneNumber.setPhoneNumber(mobilephone);
					mobilePhoneNumber.setRel(StaticProperties.MOBILE_REL);
					mobilePhoneNumber.setPrimary(false);
					contact.addPhoneNumber(mobilePhoneNumber);
				}
			}
		}

		if (workaddress.equals("")) {
			util.removeStructuredPostalAddress(contact,
					StaticProperties.WORK_REL);
		} else {
			StructuredPostalAddress address = util.getStructuredPostalAddress(
					contact, StaticProperties.WORK_REL);

			logger.info("address is null? " + (address == null));

			if (address != null) {
				FormattedAddress formattedAddress = address
						.getFormattedAddress();
				logger.info("formattedAddress is null? "
						+ (formattedAddress == null));
				if (formattedAddress != null) {
					formattedAddress.setValue(workaddress);
				} else {
					formattedAddress = new FormattedAddress();
					formattedAddress.setValue(workaddress);
					address.setFormattedAddress(formattedAddress);
				}
			} else {
				address = new StructuredPostalAddress();
				FormattedAddress formattedAddress = new FormattedAddress();
				formattedAddress.setValue(workaddress);
				address.setFormattedAddress(formattedAddress);
				// address.setLabel("Work Address");
				address.setRel(StaticProperties.WORK_REL);
				contact.addStructuredPostalAddress(address);
			}
		}

		if (homeaddress.equals("")) {
			util.removeStructuredPostalAddress(contact,
					StaticProperties.HOME_REL);
		} else {
			StructuredPostalAddress address = util.getStructuredPostalAddress(
					contact, StaticProperties.HOME_REL);
			if (address != null) {
				FormattedAddress formattedAddress = address
						.getFormattedAddress();
				if (formattedAddress != null) {
					formattedAddress.setValue(homeaddress);
				} else {
					formattedAddress = new FormattedAddress();
					formattedAddress.setValue(homeaddress);
					address.setFormattedAddress(formattedAddress);
				}
			} else {
				address = new StructuredPostalAddress();
				FormattedAddress formattedAddress = new FormattedAddress();
				formattedAddress.setValue(homeaddress);
				address.setFormattedAddress(formattedAddress);
				// address.setLabel("Home Address");
				address.setRel(StaticProperties.HOME_REL);
				contact.addStructuredPostalAddress(address);
			}
		}

		if (otheraddress.equals("")) {
			util.removeStructuredPostalAddress(contact,
					StaticProperties.OTHER_REL);
		} else {
			StructuredPostalAddress address = util.getStructuredPostalAddress(
					contact, StaticProperties.OTHER_REL);
			if (address != null) {
				FormattedAddress formattedAddress = address
						.getFormattedAddress();
				if (formattedAddress != null) {
					formattedAddress.setValue(otheraddress);
				} else {
					formattedAddress = new FormattedAddress();
					formattedAddress.setValue(otheraddress);
					address.setFormattedAddress(formattedAddress);
				}
			} else {
				address = new StructuredPostalAddress();
				FormattedAddress formattedAddress = new FormattedAddress();
				formattedAddress.setValue(otheraddress);
				address.setFormattedAddress(formattedAddress);
				// address.setLabel("Other Address");
				address.setRel(StaticProperties.OTHER_REL);
				contact.addStructuredPostalAddress(address);
			}
		}

		// contact.setContent(new PlainTextConstruct(notes));
		if (!notes.equals("")) {
			// contact.setContent(new PlainTextConstruct(notes));
			// contact.getUserDefinedFields().add(new UserDefinedField("Notes",
			// notes));
			util.updateUserDefinedField(contact, "Notes", notes);
		} else {
			util.removeUserDefinedField(contact, "Notes");
		}
	}

	public ModelAndView create(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = getCurrentUser(request).getEmail();
		Map result = new HashMap();
		String domain = CommonWebUtil.getDomain(email);

		try {
			Customer currentCustomer = sharedContactsService.verifyUser(email);
			ContactEntry entry = makeContact(request);

			// ContactEntry entry = makeContact(request);

			if (isUseForSharedContacts) {
				String groupId = getGroupId(); // added
				GroupMembershipInfo gmInfo = new GroupMembershipInfo(); // added
				gmInfo.setHref(groupId); // added
				entry.addGroupMembershipInfo(gmInfo); // added
			
				// TEST
				// entry.getUserDefinedFields().add(new
				// UserDefinedField("ÃƒÂ«Ã‚Â¶Ã¢â€šÂ¬ÃƒÂ«Ã‚Â¬Ã‚Â¸",
				// "ÃƒÂªÃ‚Â²Ã‚Â½ÃƒÂ¬Ã‹Å“Ã¯Â¿Â½ÃƒÂªÃ‚Â´Ã¢â€šÂ¬ÃƒÂ«Ã‚Â¦Ã‚Â¬ÃƒÂ«Ã‚Â¶Ã¢â€šÂ¬-TEST"));
				// entry.getUserDefinedFields().add(new
				// UserDefinedField("Bumun", "Management-TEST"));
			} else {
				// TEST
				// entry.getUserDefinedFields().add(new
				// UserDefinedField("ÃƒÂ«Ã‚Â¶Ã¢â€šÂ¬ÃƒÂ«Ã‚Â¬Ã‚Â¸",
				// "ÃƒÂªÃ‚Â²Ã‚Â½ÃƒÂ¬Ã‹Å“Ã¯Â¿Â½ÃƒÂªÃ‚Â´Ã¢â€šÂ¬ÃƒÂ«Ã‚Â¦Ã‚Â¬ÃƒÂ«Ã‚Â¶Ã¢â€šÂ¬"));
				// entry.getUserDefinedFields().add(new
				// UserDefinedField("Bumun", "Management"));
			}
			
			entry = sharedContactsService.create(entry);
			ContactInfo contactInfo = getContactInfo(request);
			contactInfo.setId(entry.getEditLink().getHref());
			contactInfo.setDomain(domain);
			if (currentCustomer.getUseDatabase()) {
				sharedContactsService.createContactInfo(contactInfo);
				sharedContactsService.incrementContactCount(domain);
			}
			AddContactForAllDomainUsersContext context = new AddContactForAllDomainUsersContext();

			context.setContactInfo(contactInfo);
			context.setDomain(domain);
			context.setIsUseForSharedContacts(isUseForSharedContacts);

			WorkflowInfo workflowInfo = new WorkflowInfo(
					"addContactForAllDomainUsersWorkflowProcessor");
			workflowInfo.setIsNewWorkflow(true);

			Workflow workflow = new Workflow();
			workflow.setContext(context);
			workflow.setWorkflowName(workflowInfo.getWorkflowName());
			workflow.setWorkflowInstanceId(workflowInfo.getWorkflowInstance());
			workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());

			context.setWorkflowInfo(workflowInfo);
			workflowManager.triggerWorkflow(workflow);

			/*
			 * for (String userId : sharedContactsService.
			 * getAllDomainUsersWithReadAndWritePErmissionIncludingAdmin
			 * (domain)) { System.out.println("contacts creation  uiser id  = "
			 * + userId); ContactEntry newentry = makeContact(request); String
			 * userGroupId = getUserGroupId(userId + "@" + domain); // added
			 * 
			 * GroupMembershipInfo userGmInfo = new GroupMembershipInfo(); //
			 * added userGmInfo.setHref(userGroupId); // added
			 * newentry.addGroupMembershipInfo(userGmInfo);
			 * sharedContactsService.createUserContact(newentry, userId + "@" +
			 * domain); }
			 */
			String message = messageSource.getMessage("creation.success", null,
					Locale.US);
			result.put("code", "success");
			result.put("message", message);
			logger.info("No error!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
			String message = messageSource.getMessage("error.default", null,
					Locale.US);
			result.put("code", "error");
			result.put("message", message);
		}
		return new ModelAndView("/sharedcontacts/comm_result_xml", "result",
				result);
	}

	public ContactEntry getContact(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String page = CommonWebUtil.getParameter(request, "page"); // get the
																	// requested
																	// page
		String rows = CommonWebUtil.getParameter(request, "rows"); // get how
																	// many rows
																	// we want
																	// to have
																	// into the
																	// grid
		String sidx = CommonWebUtil.getParameter(request, "sidx"); // orderby
																	// Sorting
																	// criterion.
																	// The only
																	// supported
																	// value is
																	// lastmodified.
		String sord = CommonWebUtil.getParameter(request, "sord"); // ascending
																	// or
																	// descending.
		logger.info("### PARAMS: page: " + page + ", " + "rows: " + rows + ", "
				+ "sidx: " + sidx + ", " + "sord: " + sord);

		String id = CommonWebUtil.getParameter(request, "id");
		logger.info("id: " + id);

		Map result = new HashMap();
		result.put("page", page);
		result.put("rows", rows);
		result.put("sidx", sidx);
		result.put("sord", sord);

		ContactEntry contact = null;

		try {
			contact = sharedContactsService.getContact(id);
			// result.put("contact", contact);
			// result.put("userEmail", userEmail);
			// result.put("adminUserName", adminUserName);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
		// return new ModelAndView("/sharedcontacts/details", "result", result);
		return contact;
	}

	public ModelAndView getCustomersList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String page = CommonWebUtil.getParameter(request, "page"); // get the
																	// requested
																	// page
		String page1 = CommonWebUtil.getParameter(request, "page1"); // get the
																		// requested
																		// page

		if (!page1.equals("")) {
			page = page1;
		}

		String rows = CommonWebUtil.getParameter(request, "rows"); // get how
																	// many rows
																	// we want
																	// to have
																	// into the
																	// grid
		String sidx = CommonWebUtil.getParameter(request, "sidx"); // orderby
																	// Sorting
																	// criterion.
																	// The only
																	// supported
																	// value is
																	// lastmodified.
		String sord = CommonWebUtil.getParameter(request, "sord"); // ascending
																	// or
																	// descending.

		int pageNum = Integer.parseInt(page);
		double limit = Double.parseDouble(rows);

		List<Customer> customers = null;
		int total_pages = 0;
		double start = 0;
		int cunt = 0;
		try {
			// entries = sharedContactsService.getContacts(1, groupId,
			// isUseForSharedContacts);
			customers = sharedContactsService.getAllCustomers();
			if (customers != null) {
				cunt = customers.size();
				total_pages = (int) Math.ceil(cunt / limit);
			} else {
				total_pages = 0;
			}

			if (pageNum > total_pages)
				pageNum = total_pages;

			start = limit * pageNum - limit;
		} catch (Exception e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}

		Map result = new HashMap();
		result.put("entries", customers);
		result.put("page", page);
		result.put("total", Integer.toString(total_pages));
		result.put("records", Integer.toString((int) cunt));
		result.put("startIndex", Integer.toString((int) start));

		result.put("rows", rows);
		result.put("sidx", sidx);
		result.put("sord", sord);

		return new ModelAndView("/sharedcontacts/customers-list-data",
				"result", result);
	}

	public ModelAndView getContacts(HttpServletRequest request,
			HttpServletResponse response, Customer currentCustomer)
			throws ServletException, IOException {
		GridRequest dataCriteria = gridRequestParser.parseDataCriteria(request);
		String page = CommonWebUtil.getParameter(request, "page"); // get the
																	// requested
																	// page
		String page1 = CommonWebUtil.getParameter(request, "page1"); // get the
																		// requested
																		// page

		logger.info("===> page: " + page + ", " + "page1: " + page1);
		if (!page1.equals("")) {
			page = page1;
		}

		String rows = CommonWebUtil.getParameter(request, "rows"); // get how
																	// many rows
																	// we want
																	// to have
																	// into the
																	// grid
		String sidx = CommonWebUtil.getParameter(request, "sidx"); // orderby
																	// Sorting
																	// criterion.
																	// The only
																	// supported
																	// value is
																	// lastmodified.
		String sord = CommonWebUtil.getParameter(request, "sord"); // ascending
																	// or
																	// descending.
		logger.info("### PARAMS: page: " + page + ", " + "rows: " + rows + ", "
				+ "sidx: " + sidx + ", " + "sord: " + sord);

		int pageNum = Integer.parseInt(page);
		double limit = Double.parseDouble(rows);

		List<ContactEntry> entries = null;
		int total_pages = 0;
		double start = 0;

		try {

			// entries = sharedContactsService.getContacts(page, rows, sidx,
			// sord);
			String groupId = null;
			if (isUseForSharedContacts) {
				groupId = getGroupId();
			}

			int totalLimit = 100;
			if (currentCustomer.getAccountType().equalsIgnoreCase("Paid")) {
				totalLimit = 30000;
			} else {
				if (CommonUtil.isTheSecondTypeCustomer(currentCustomer)) {
					totalLimit = 50;
				}
			}

			entries = sharedContactsService.getContacts(1, totalLimit, groupId,
					isUseForSharedContacts, dataCriteria);
			logger.info("### 1. entries.size() ===> " + entries.size());

			if (entries != null) {
				count = entries.size();
				total_pages = (int) Math.ceil(count / limit);
			} else {
				total_pages = 0;
			}

			/*
			 * paymentGateway.makeRequest("http://www.google.com",request,
			 * response);
			 */

			if (pageNum > total_pages)
				pageNum = total_pages;

			start = limit * pageNum - limit;
			logger.info("start: " + start);
			logger.info("(int)start: " + (int) start);
			// logger.info("start: " + start);
			logger.info("count: " + count);
			logger.info("total_pages: " + total_pages);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}

		Map result = new HashMap();
		result.put("entries", entries);
		result.put("page", page);
		result.put("total", Integer.toString(total_pages));
		result.put("records", Integer.toString((int) count));
		result.put("startIndex", Integer.toString((int) start));
		result.put("gridRequest", dataCriteria);

		result.put("rows", rows);
		if (CommonWebUtil.getParameter(request, "defaultGridOrder").equals(
				"lastModifiedDate")) {

			result.put("sidx", "no");
			result.put("sord", "");
		} else {

			result.put("sidx", sidx);
			result.put("sord", sord);
		}

		return new ModelAndView("/sharedcontacts/list_data", "result", result);
	}

	public ModelAndView remove(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Map result = new HashMap();
		String ids_to_del = CommonWebUtil.getParameter(request, "ids_to_del");
		logger.info("ids_to_del: " + ids_to_del);
		StringTokenizer st = new StringTokenizer(ids_to_del, "|");
		List<String> ids = new ArrayList<String>();
		while (st.hasMoreElements()) {
			String token = st.nextToken();
			logger.info("token: " + token);
			ids.add(token);
		}
		try {
			sharedContactsService.remove(ids);
			String message = messageSource.getMessage("deletion.success", null,
					Locale.US);
			result.put("code", "success");
			result.put("message", message);
			logger.info("No error!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
			String message = messageSource.getMessage("error.default", null,
					Locale.US);
			result.put("code", "error");
			result.put("message", message);
		}

		return new ModelAndView("/sharedcontacts/comm_result_xml", "result",
				result);
	}

	
	@RequestMapping("/sharedcontacts/mailContacts.do")
	@ResponseBody
	public boolean download(@RequestParam("email")String email)
			throws ServletException, IOException {
		Map result = new HashMap();
		try {
			int totalLimit = 100;
			Customer currentCustomer = sharedContactsService.verifyUser(email);
			if (currentCustomer.getAccountType().equalsIgnoreCase("Paid")) {
				totalLimit = 30000;
			} else {
				if (CommonUtil.isTheSecondTypeCustomer(currentCustomer)) {
					totalLimit = 50;
				}
			}
			
			List<ContactEntry> contacts = SharedContactsUtil.getInstance().getContactEntriesFromContactInfo(sharedContactsService.getAllDomainContacts(currentCustomer.getDomain(), totalLimit, "givenname", "asc"));
			
			sharedContactsService.mailCSV(contacts,  email);
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
			String message = messageSource.getMessage("error.default", null,
					Locale.US);
			result.put("code", "error");
			result.put("message", message);
		}

		return true;
	}
/*
public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response, Customer currentCustomer)
			throws ServletException, IOException {
		Map result = new HashMap();
		try {
			int totalLimit = 100;
			if (currentCustomer.getAccountType().equalsIgnoreCase("Paid")) {
				totalLimit = 30000;
			} else {
				if (CommonUtil.isTheSecondTypeCustomer(currentCustomer)) {
					totalLimit = 50;
				}
			}
			/*List<ContactEntry> contacts = sharedContactsService.getContacts(1,
					totalLimit, getGroupId(), isUseForSharedContacts, null);*/
			
	/*		List<ContactEntry> contacts = SharedContactsUtil.getInstance().getContactEntriesFromContactInfo(sharedContactsService.getAllDomainContacts(currentCustomer.getDomain(), totalLimit, "givenname", "asc"));
			
			String sheetName = "SharedContacts";
			/*
			 * response.setContentType("application/vnd.ms-excel");
			 * response.setHeader("Content-Disposition", "attachment; filename="
			 * + sheetName + ".xls");
			 */
		/*	response.setContentType("application/CSV");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ sheetName + ".csv");

			sharedContactsService
					.download(contacts, response.getOutputStream());
			String message = messageSource.getMessage("deletion.success", null,
					Locale.US);
			result.put("code", "success");
			result.put("message", message);
			logger.info("No error!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
			String message = messageSource.getMessage("error.default", null,
					Locale.US);
			result.put("code", "error");
			result.put("message", message);
		}

		return null;
	}
*/
	private ModelAndView updateStatus(HttpServletRequest request,
			HttpServletResponse response, Customer currentCustomer) {

		sharedContactsService.updateMembership(
				Long.parseLong(CommonWebUtil.getParameter(request, "id")),
				CommonWebUtil.getParameter(request, "accountType"));

		return null;
	}

	public ModelAndView upgrade(HttpServletRequest request,
			HttpServletResponse response, Customer currentCustomer)
			throws ServletException, IOException {
		Map result = new HashMap();

		try {

			if (!StringUtils.isBlank(request.getParameter("paykey"))
					&& request.getSession().getAttribute("paykey")
							.equals(request.getParameter("paykey"))) {
				request.getSession().removeAttribute("paykey");
				try {
					sharedContactsService.upgradeMembership(currentCustomer
							.getId());

					String message = messageSource.getMessage(
							"upgraded.success", null, Locale.US);
					result.put("code", "success");
					result.put("message", message);

				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.SEVERE, e.getMessage(), e);
					String message = messageSource.getMessage("error.default",
							null, Locale.US);
					result.put("code", "error");
					result.put("message", message);
				}
				String orderNumber = RandomStringUtils.randomNumeric(5);
				sendMail(getCurrentUser(request), orderNumber);
				sendAdminMail(getCurrentUser(request), orderNumber);
				return new ModelAndView("/sharedcontacts/paymentsuccess",
						"result", result);

			} else {

				PaymentGateway paymentGateway = new PaymentGateway(
						appProperties.getPaymentGatewayAppid(),
						appProperties.getPaymentGatewayUsername(),
						appProperties.getPaymentGatewayPassword(),
						appProperties.getPaymentGatewaySignature(),
						appProperties.getPaymentGatewayAccountEmail());

				String url = paymentGateway.makeRequest(HttpUtils
						.getRequestURL(request).toString(), request, response);
				result.put("url", url);

			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		/*
		 * 
		 */
		return null;

	}

	private void sendAdminMail(UserInfo currentCustomer, String orderNumber)
			throws AppException {
		MailMessage mail = new MailMessage();
		mail.setSubject("Netkiller Shared Contacts Order Receipt - "
				+ orderNumber);
		mail.setSender(new MailAddress("Netkiller Admin",
				"agent_noreply@netkiller.com"));
		Recipient recipient = new Recipient();
		recipient.setMailAddress(new MailAddress("Netkiller Admin",
				"admin@netkiller.com"));
		recipient.setRecipientType(RecipientType.TO);
		String firstName = "";
		if (currentCustomer.getFirstName() != null) {
			firstName = currentCustomer.getFirstName();
		}
		mail.addRecipient(recipient);

		String html = "<html><body><p>Payment Process for "
				+ currentCustomer.getFirstName()
				+ " ("
				+ currentCustomer.getEmail()
				+ ") was successfully completed. Respective domain has been succcessfully upgraded</p><p> Order details are below:</p><p>Order Summary:<br>Order #:"
				+ RandomStringUtils.randomNumeric(5)
				+ "<br>Order Total: $199.00<br>Date Paid: "
				+ new Date()
				+ "<br>Order Details:</p><p>Payment Information:</p><p>Merchant Name: Netkiller America, Inc.<br>Payment Method: PayPal</p><p>Respectfully,<br>Netkiller.com</p><p>=======================================================================================</p><p>Netkiller ; Global IT Innovator - Cloud System Integrationsweb. www.netkiller.com, email. info@netkiller.com</p><p>=======================================================================================</p><p>THIS MESSAGE IS CONFIDENTIAL.This e-mail message and any attachments are proprietary and confidential information intended only for the use of the recipient(s) named above. If you are not the intended recipient, you may not print, distribute, or copy this message or any attachments. If you have received this communication in error, please notify the sender by return e-mail and delete this message and any attachments from your computer.</p><p>=======================================================================================</p></body></html>";
		mail.setHtmlBody(html);
		GoogleMailService mailService = new GoogleMailService();
		mailService.sendMail(mail);

	}

	private void sendMail(UserInfo currentCustomer, String orderNumber)
			throws AppException {
		MailMessage mail = new MailMessage();
		String firstName = "Netkiller";
		String lastName = "Customer";
		if (currentCustomer.getFirstName() != null
				&& !StringUtils.isBlank(currentCustomer.getFirstName())) {
			firstName = currentCustomer.getFirstName();
		}
		if (currentCustomer.getLastName() != null
				&& !StringUtils.isBlank(currentCustomer.getLastName())) {
			lastName = currentCustomer.getLastName();
		}
		mail.setSubject("Netkiller Shared Contacts Order Receipt - "
				+ orderNumber);
		mail.setSender(new MailAddress("Netkiller",
				"agent_noreply@netkiller.com"));
		Recipient recipient = new Recipient();
		recipient.setMailAddress(new MailAddress(firstName + " " + lastName,
				currentCustomer.getEmail()));
		recipient.setRecipientType(RecipientType.TO);
		Recipient ccrecipient = new Recipient();
		ccrecipient.setMailAddress(new MailAddress("Netkiller Agent",
				"agent_noreply@netkiller.com"));
		ccrecipient.setRecipientType(RecipientType.CC);
		mail.addRecipient(ccrecipient);
		mail.addRecipient(recipient);
		mail.addRecipient(ccrecipient);
		String html = "<html><body><p>Dear "
				+ firstName
				+ " "
				+ lastName
				+ "</p><p>Thank you for your recent order with Netkiller. Your order details are below:</p><p>Order Summary:<br>Order #:"
				+ orderNumber
				+ "<br>Order Total: $199.00<br>Date Paid: "
				+ new Date()
				+ "<br>Order Details:</p><p>Payment Information:</p><p>Merchant Name: Netkiller America, Inc.<br>Payment Method: PayPal</p><p>Respectfully,Netkiller.com</p><p>=======================================================================================</p><p>Netkiller ; Global IT Innovator - Cloud System Integrationsweb. www.netkiller.com, email. info@netkiller.com</p><p>=======================================================================================</p><p>THIS MESSAGE IS CONFIDENTIAL.This e-mail message and any attachments are proprietary and confidential information intended only for the use of the recipient(s) named above. If you are not the intended recipient, you may not print, distribute, or copy this message or any attachments. If you have received this communication in error, please notify the sender by return e-mail and delete this message and any attachments from your computer.</p><p>=======================================================================================</p></body></html>";
		mail.setHtmlBody(html);
		GoogleMailService mailService = new GoogleMailService();
		mailService.sendMail(mail);
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

	private ModelAndView getAuthorizeForm(HttpServletRequest request,
			HttpServletResponse response, Customer currentCustomer) {
		Map result = new HashMap();
		String domain = CommonWebUtil
				.getDomain(currentCustomer.getAdminEmail());
		DomainSettings domainSettings = sharedContactsService
				.getDomainSettings(domain);
		List<String> users = sharedContactsService.getAllDomainUsers(domain);
		List<String> usersWithWritePermission = sharedContactsService
				.getAllUserNamesWithWritePermissions(domain);
		List<String> usersWithReadPermission = sharedContactsService
				.getAllUserWithReadPermissions(domain);
		result.put("allUsersPermitted", domainSettings.isAllUserPermitted());
		result.put("onlyAdminPermitted", domainSettings.isOnlyAdminPermitted());
		result.put("usersWithWritePermission", usersWithWritePermission);
		result.put("usersWithReadPermission", usersWithReadPermission);
		result.put("domainName",
				CommonWebUtil.getDomain(getCurrentUser(request).getEmail()));
		return new ModelAndView("/sharedcontacts/authorize", "result", result);
	}

	private ModelAndView authorize(HttpServletRequest request,
			HttpServletResponse response, Customer currentCustomer) {
		Map result = new HashMap();
		String domain = CommonWebUtil
				.getDomain(currentCustomer.getAdminEmail());
		List<String> users = sharedContactsService.getAllDomainUsers(domain);
		List<String> usersWithWritePermission = new ArrayList<String>();
		String newDomainSettings = CommonWebUtil.getParameter(request,
				"domainSettings");
		DomainSettings domainSettings = sharedContactsService
				.getDomainSettings(domain);
		if (newDomainSettings.equals("allUsersPermitted")) {
			domainSettings.setAllUserPermitted(true);
			domainSettings.setOnlyAdminPermitted(false);
		} else if (newDomainSettings.equals("onlyAdminPermitted")) {
			domainSettings.setOnlyAdminPermitted(true);
			domainSettings.setAllUserPermitted(false);
		} else if(StringUtils.isNotBlank(request
					.getParameter("updateUserList"))){
			domainSettings.setOnlyAdminPermitted(false);
			domainSettings.setAllUserPermitted(false);
			List<String> newUsersWithUpdatePermission = Arrays.asList(request
					.getParameter("updateUserList").split(","));
			usersWithWritePermission = sharedContactsService
					.getAllUserNamesWithWritePermissions(domain);
			List<String> usersToBeAdded = new ArrayList<String>();
			for (String user : newUsersWithUpdatePermission) {
				if (!usersWithWritePermission.contains(user))
					usersToBeAdded.add(user);
			}
			usersToBeAdded = new ArrayList<String>(new HashSet<String>(
					usersToBeAdded));
			List<String> usersToBeRemoved = new ArrayList<String>();
			for (String user : usersWithWritePermission) {
				if (!newUsersWithUpdatePermission.contains(user))
					usersToBeRemoved.add(user);
			}
			usersToBeRemoved = new ArrayList<String>(new HashSet<String>(
					usersToBeRemoved));
			sharedContactsService.assignUpdatePermissions(usersToBeAdded,
					CommonWebUtil.getDomain(currentCustomer.getAdminEmail()));
			sharedContactsService.removeUpdatePermissions(usersToBeRemoved,
					CommonWebUtil.getDomain(currentCustomer.getAdminEmail()));
			
			
		}
		
		String useDatabase = CommonWebUtil.getParameter(request,
		"useDatabase");
		sharedContactsService.updateCustomers(domain, "useDatabase",useDatabase.equalsIgnoreCase("on"));
		if(!useDatabase.equalsIgnoreCase("on")){
			Queue queue =  QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("")
										.url("/sharedcontacts/deleteContactInfo.do")
										.param("domainName", domain));
		}

		domainSettings = sharedContactsService
				.updateDomainSettings(domainSettings);
		usersWithWritePermission = sharedContactsService
				.getAllUserNamesWithWritePermissions(domain);
		List<String> usersWithReadPermission = sharedContactsService
				.getAllUserWithReadPermissions(domain);
		result.put("usersWithWritePermission", usersWithWritePermission);
		result.put("usersWithReadPermission", usersWithReadPermission);
		result.put("allUsersPermitted", domainSettings.isAllUserPermitted());
		result.put("onlyAdminPermitted", domainSettings.isOnlyAdminPermitted());
		result.put("success", true);
		return new ModelAndView("/sharedcontacts/authorize", "result", result);

	}

	private ModelAndView getUnAuthorizeForm(HttpServletRequest request,
			HttpServletResponse response, Customer currentCustomer) {
		Map result = new HashMap();
		String domain = CommonWebUtil
				.getDomain(currentCustomer.getAdminEmail());
		DomainSettings domainSettings = sharedContactsService
				.getDomainSettings(domain);
		List<String> users = sharedContactsService.getAllDomainUsers(domain);
		List<String> usersWithNoPermission = sharedContactsService
				.getAllUserNamesWithNoPermissions(domain);
		List<String> usersWithWritePermission = sharedContactsService
				.getAllUserNamesWithWritePermissions(domain);
		List<String> usersWithReadPermission = sharedContactsService
				.getAllUserWithReadPermissions(domain);
		System.out.println(users + "\n\n" + 
				usersWithNoPermission+ "\n\n" + 
				usersWithWritePermission	+ "\n\n" + 
				usersWithReadPermission+ "\n\n" );
		usersWithReadPermission.addAll(usersWithWritePermission);
		result.put("allUsersPermitted", domainSettings.isAllUserPermitted());
		result.put("onlyAdminPermitted", domainSettings.isOnlyAdminPermitted());
		result.put("usersWithNoPermission", usersWithNoPermission);
		result.put("usersWithReadPermission", usersWithReadPermission);
		result.put("domainName",
				CommonWebUtil.getDomain(getCurrentUser(request).getEmail()));
		return new ModelAndView("/sharedcontacts/unauthorize", "result", result);
	}

	private ModelAndView unauthorize(HttpServletRequest request,
			HttpServletResponse response, Customer currentCustomer) {
		Map result = new HashMap();
		String domain = CommonWebUtil
				.getDomain(currentCustomer.getAdminEmail());
		List<String> users = sharedContactsService.getAllDomainUsers(domain);
		List<String> usersWithWritePermission = new ArrayList<String>();
		String newDomainSettings = CommonWebUtil.getParameter(request,
				"domainSettings");
		DomainSettings domainSettings = sharedContactsService
				.getDomainSettings(domain);
		List<String> usersWithNoPermission = sharedContactsService
				.getAllUserNamesWithNoPermissions(domain);
		List<String> newUsersWithNoPermission = Arrays.asList(request
				.getParameter("updateUserList").split(","));
		usersWithWritePermission = sharedContactsService
				.getAllUserNamesWithWritePermissions(domain);

		/*
		 * List<String> usersToBeAdded = new ArrayList<String>(); for (String
		 * user : newUsersWithNoPermission) { if
		 * (!usersWithWritePermission.contains(user)) usersToBeAdded.add(user);
		 * } usersToBeAdded = new ArrayList<String>(new
		 * HashSet<String>(usersToBeAdded));
		 */
		List<String> usersToBeRemoved = new ArrayList<String>();
		for (String user : usersWithWritePermission) {
			if (newUsersWithNoPermission.contains(user))
				usersToBeRemoved.add(user);
		}
		List<String> noPermissionUsersToBeRemoved = new ArrayList<String>();
		for (String user : usersWithNoPermission) {
			if (!newUsersWithNoPermission.contains(user)
					&& !StringUtils.isEmpty(user))
				noPermissionUsersToBeRemoved.add(user);
		}
		List<String> usersToBeAdded = new ArrayList<String>();
		for (String user : newUsersWithNoPermission) {
			if (!usersWithNoPermission.contains(user)
					&& !StringUtils.isEmpty(user))
				usersToBeAdded.add(user);
		}
		usersToBeRemoved = new ArrayList<String>(new HashSet<String>(
				usersToBeRemoved));
		/*
		 * sharedContactsService.assignUpdatePermissions(usersToBeAdded,
		 * CommonWebUtil.getDomain(currentCustomer.getAdminEmail()));
		 */
		sharedContactsService.removeUpdatePermissions(usersToBeRemoved,
				CommonWebUtil.getDomain(currentCustomer.getAdminEmail()));

		sharedContactsService.removeNoPermissions(noPermissionUsersToBeRemoved,
				CommonWebUtil.getDomain(currentCustomer.getAdminEmail()));
		sharedContactsService.assignNoPermissions(usersToBeAdded,
				CommonWebUtil.getDomain(currentCustomer.getAdminEmail()));

		if (usersToBeAdded != null && !usersToBeAdded.isEmpty()) {

			DeleteGroupsAndContactsByUsersContext context = new DeleteGroupsAndContactsByUsersContext();
			context.setDomain(CommonWebUtil.getDomain(currentCustomer
					.getAdminEmail()));
			context.setUsers(usersToBeAdded);

			WorkflowInfo workflowInfo = new WorkflowInfo(
					"deleteGroupsAndContactsByUsersWorkflowProcessor");
			workflowInfo.setIsNewWorkflow(true);

			Workflow workflow = new Workflow();
			workflow.setContext(context);
			workflow.setWorkflowName(workflowInfo.getWorkflowName());
			workflow.setWorkflowInstanceId(workflowInfo.getWorkflowInstance());
			workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());

			context.setWorkflowInfo(workflowInfo);
			workflowManager.triggerWorkflow(workflow);

			// sharedContactsService.deleteGroupsAndContactsByUsers(usersToBeAdded,CommonWebUtil.getDomain(currentCustomer.getAdminEmail()));
		}

		if (noPermissionUsersToBeRemoved != null
				&& !noPermissionUsersToBeRemoved.isEmpty()) {
			System.out
					.println("MEthod entered to be executed..!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

			AddGroupsAndContactsByUsersContext context = new AddGroupsAndContactsByUsersContext();
			context.setDomain(CommonWebUtil.getDomain(currentCustomer
					.getAdminEmail()));
			context.setUsers(noPermissionUsersToBeRemoved);

			WorkflowInfo workflowInfo = new WorkflowInfo(
					"addGroupsAndContactsByUsersWorkflowProcessor");
			workflowInfo.setIsNewWorkflow(true);

			Workflow workflow = new Workflow();
			workflow.setContext(context);
			workflow.setWorkflowName(workflowInfo.getWorkflowName());
			workflow.setWorkflowInstanceId(workflowInfo.getWorkflowInstance());
			workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());

			context.setWorkflowInfo(workflowInfo);
			workflowManager.triggerWorkflow(workflow);

			// sharedContactsService.addGroupsAndContactsByUsers(noPermissionUsersToBeRemoved,CommonWebUtil.getDomain(currentCustomer.getAdminEmail()));
		}

		// domainSettings =
		// sharedContactsService.updateDomainSettings(domainSettings);
		usersWithWritePermission = sharedContactsService
				.getAllUserNamesWithWritePermissions(domain);
		usersWithNoPermission = sharedContactsService
				.getAllUserNamesWithNoPermissions(domain);
		List<String> usersWithReadPermission = sharedContactsService
				.getAllUserWithReadPermissions(domain);
		usersWithReadPermission.addAll(usersWithWritePermission);
		result.put("usersWithNoPermission", usersWithNoPermission);
		result.put("usersWithReadPermission", usersWithReadPermission);
		result.put("allUsersPermitted", domainSettings.isAllUserPermitted());
		result.put("onlyAdminPermitted", domainSettings.isOnlyAdminPermitted());
		result.put("success", true);
		return new ModelAndView("/sharedcontacts/unauthorize", "result", result);

	}
	
	Set<String> domainWithContactsBeingUpdated = new HashSet<String>();
	
	@RequestMapping("/sharedcontacts/griddata.do")
	public void getGridData(HttpServletRequest request,HttpServletResponse response) throws IOException{
		JSONArray jsonArray = new JSONArray();
		GridRequest gridRequest = gridRequestParser.parseDataCriteria(request);
		String page = CommonWebUtil.getParameter(request, "page"); // get the
																	// requested
																	// page
		String page1 = CommonWebUtil.getParameter(request, "page1"); // get the
																		// requested
																		// page

		logger.info("===> page: " + page + ", " + "page1: " + page1);
		if (!page1.equals("")) {
			page = page1;
		}
		
		String rows = CommonWebUtil.getParameter(request, "rows"); // get how
																	// many rows
																	// we want
																	// to have
																	// into the
																	// grid
		String sidx = CommonWebUtil.getParameter(request, "sidx"); // orderby
																	// Sorting
																	// criterion.
																	// The only
																	// supported
																	// value is
																	// lastmodified.
		String sord = CommonWebUtil.getParameter(request, "sord"); // ascending
																	// or
																	// descending.
		long startTime = new Date().getTime();
		int pageNum = Integer.parseInt(page);
		int limit = Integer.parseInt(rows);
		int fetchLimit = 0;
		int total_pages = 0;
		int start = ((pageNum-1) * limit) ;
		int totalRecords = 0;
		int searchStart = start;
	
			Customer currentCustomer =  sharedContactsService
						.verifyUser(getCurrentUser(request).getEmail());
			String groupId = null;
			if (isUseForSharedContacts) {
				groupId = getGroupId();
			}

			int totalLimit = 100;
			if (currentCustomer.getAccountType().equalsIgnoreCase("Paid")) {
				totalLimit = 30000;
			} else {
				if (CommonUtil.isTheSecondTypeCustomer(currentCustomer)) {
					totalLimit = 50;
				}
				
				if(start+limit > totalLimit){
					limit = totalLimit - start;
				}
			}
			boolean isSearch = (gridRequest != null && gridRequest.isSearch());
			SharedContactsUtil util = SharedContactsUtil.getInstance();
			List<ContactInfo> entries = null;
			int rowNum = 0;
			if(!isSearch){
				fetchLimit= limit;
				totalRecords = sharedContactsService.getContactCount(currentCustomer.getDomain());
				if (currentCustomer.getAccountType().equalsIgnoreCase("Paid")) {
					rowNum =  totalRecords - start;
					} else {
						rowNum =  totalLimit - start;
					}
			}else{
				fetchLimit = 900 ;
			}
			
			entries =sharedContactsService.getDomainContacts(currentCustomer.getDomain(), fetchLimit,start,sidx,sord);
			
			if(searchStart ==0 &&  entries.size()<=0){
				if(domainWithContactsBeingUpdated.add(currentCustomer.getDomain())){
					triggerDBContactUpdateTask(currentCustomer);
				}
			}
			
			int counter = -1;
			
			do{
			for(ContactInfo contactInfo : entries){
				JSONObject jsonObj = new JSONObject();
				if(!isSearch){
					jsonObj.put("no", rowNum--);
				}else{
					jsonObj.put("no", start + jsonArray.size()+1);
				}
				jsonObj.put("id", contactInfo.getId());
				jsonObj.put("editlink", contactInfo.getId());
				jsonObj.put("name", contactInfo.getFullname());
				jsonObj.put("givenname", contactInfo.getGivenname());
				jsonObj.put("familyname", contactInfo.getFamilyname());
				jsonObj.put("company", contactInfo.getCompanyname());
				jsonObj.put("email", contactInfo.getWorkemail());
				jsonObj.put("phone", contactInfo.getWorkphone());
				jsonObj.put("address", contactInfo.getWorkaddress());
				jsonObj.put("notes", contactInfo.getNotes());
				jsonObj.put("delete", "False");
				if (isSearch) {
					if (!util.isValidSearchObject(jsonObj, gridRequest.getFilterInfo())){
						continue;
					}
					counter++;
					if(counter >= start && counter < start + limit){
						jsonArray.add(jsonObj);
					}
				}else{
					jsonArray.add(jsonObj);
				}
				
			// End of for loop	
			}
			
			//deadline check
			if(new Date().getTime() - startTime >=45000){
				break;
			}
			
			if (isSearch && jsonArray.size()>=Integer.parseInt(rows)) {
			 break;
			}else{
				if(entries.size()<fetchLimit){
					break;
				}else{
				searchStart += fetchLimit;
					entries =sharedContactsService.getDomainContacts(currentCustomer.getDomain(), fetchLimit,searchStart,sidx,sord);
					if(entries.isEmpty()){
						break;
					}
				}
			}
			}while(isSearch );
			
			
			if(isSearch){
				count = counter+1;
				//util.insertNo(jsonArray);
			}
			
			
			if(count>totalLimit){
				count = totalLimit;
			}
			total_pages = (int) Math.ceil(count / limit);

			if (pageNum > total_pages)
				pageNum = total_pages;

			
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("total",total_pages );
		jsonObj.put("page",pageNum );
		jsonObj.put("records", count);
		jsonObj.put("rows",jsonArray );
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jsonObj);
	}
	
	@RequestMapping("/sharedcontacts/triggerDBContactUpdateTask.do")
	@ResponseBody
	public boolean triggerDBContactUpdateTask(HttpServletRequest request){
		Customer currentCustomer =  sharedContactsService
		.verifyUser(getCurrentUser(request).getEmail());
		triggerDBContactUpdateTask(currentCustomer);
		return true;
	}
	
	private void triggerDBContactUpdateTask(Customer currentCustomer){
		AddContactForAllDomainUsersContext context = new AddContactForAllDomainUsersContext();

		context.setDomain(currentCustomer.getDomain());
		context.setIsUseForSharedContacts(isUseForSharedContacts);

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
	}
	
	@RequestMapping("/test.do")
	public void add(){
		sharedContactsService.verifyUser("admin@vph.com");
	}
	
	@RequestMapping("/sharedcontacts/triggerdeleteContactInfo.do")
	@ResponseBody
	public boolean triggerDeleteContactInfo(@RequestParam("domainName")String domainName){
		Queue queue =  QueueFactory.getDefaultQueue();
		queue.add(TaskOptions.Builder.withUrl("")
									.url("/sharedcontacts/deleteContactInfo.do")
									.param("domainName", domainName));
		 
		return true;
	}
	
	@RequestMapping("/sharedcontacts/deleteContactInfo.do")
	@ResponseBody
	public boolean deleteContactInfo(@RequestParam("domainName")String domainName){
		 List<ContactInfo> entries = sharedContactsService.getDomainContacts( domainName, 30000,0,"modifiedDate","desc");
		 for(ContactInfo info : entries){
			 sharedContactsService.removeContactInfo(info.getId());
		 }
		 
		return true;
	}
	
	@RequestMapping("/sharedcontacts/checkDuplicateEmail.do")
	@ResponseBody
	public Map<String,Object> isDuplicateEmail(@RequestParam("email")String email, HttpServletRequest request) throws JSONException{
		Map<String,Object> result = new HashMap<String, Object>();
		 List<ContactInfo> entries = sharedContactsService.isDuplicateEmail( CommonWebUtil.getDomain( getCurrentUser(request).getEmail()), email);
		if(!entries.isEmpty()){
			result.put("isDuplicateEmailFound", true);
			result.put("msg", "Duplicate mail with email Id " + email + " found");
		}else{
			result.put("isDuplicateEmailFound", false);
		}
		return result;
	}
	
	@RequestMapping("/sharedcontacts/download.do")
	@ResponseBody
	public boolean triggerDownload(  HttpServletRequest request){
		String email = getCurrentUser(request).getEmail();
		Queue queue =  QueueFactory.getDefaultQueue();
		queue.add(TaskOptions.Builder.withUrl("")
									.url("/sharedcontacts/mailContacts.do")
									.param("email", email)
									.header("Host", BackendServiceFactory.getBackendService().getBackendAddress("worker"))
);
		return false;
	}
	
}
