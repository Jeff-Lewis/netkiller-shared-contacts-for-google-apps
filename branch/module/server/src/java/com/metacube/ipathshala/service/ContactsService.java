package com.metacube.ipathshala.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import jxl.Cell;
import jxl.CellView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailService.Message;
import com.google.appengine.api.mail.MailServiceFactory;
import com.google.gdata.client.Query;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthParameters.OAuthType;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.appsforyourdomain.AppsForYourDomainException;
import com.google.gdata.data.appsforyourdomain.provisioning.UserEntry;
import com.google.gdata.data.appsforyourdomain.provisioning.UserFeed;
import com.google.gdata.data.batch.BatchOperationType;
import com.google.gdata.data.batch.BatchStatus;
import com.google.gdata.data.batch.BatchUtils;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.ContactGroupFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.util.ServiceException;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.dao.ContactsDao;
import com.metacube.ipathshala.entity.Contacts;
import com.metacube.ipathshala.entity.DomainAdmin;
import com.metacube.ipathshala.entity.DomainGroup;
import com.metacube.ipathshala.entity.Workflow;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.security.DomainConfig;
import com.metacube.ipathshala.security.acl.Permission.PermissionType;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.util.CommonUtil;
import com.metacube.ipathshala.util.CommonWebUtil;
import com.metacube.ipathshala.vo.UserPermission;
import com.metacube.ipathshala.vo.UserSync;
import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowInfo;
import com.metacube.ipathshala.workflow.impl.context.AddContactForAllDomainUsersContext;
import com.metacube.ipathshala.workflow.impl.context.AddGroupToAllContactsForDomainContext;
import com.metacube.ipathshala.workflow.impl.context.BulkContactDeleteWorkflowContext;
import com.metacube.ipathshala.workflow.impl.context.BulkContactDuplicateWorkflowContext;
import com.metacube.ipathshala.workflow.impl.processor.WorkflowStatusType;

@Service
public class ContactsService extends AbstractService {

	private static final AppLogger log = AppLogger
			.getLogger(ContactsService.class);

	private final DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	@Autowired
	@Qualifier("ContactsMetaData")
	private EntityMetaData entityMetaData;

	@Autowired
	private ContactsDao contactsDao;

	public void setContactsService(
			com.google.gdata.client.contacts.ContactsService contactsService) {
		this.contactsService = contactsService;
	}

	private com.google.gdata.client.contacts.ContactsService contactsService;

	@Autowired
	private DomainConfig domainConfig;

	@Autowired
	private KeyListService keyListService;

	@Autowired
	private GlobalFilterSearchService globalFilterSearchService;

	@Autowired
	private UserSyncService userSyncService;

	private String userEmail;

	private boolean isUserAdmin;

	public boolean isUserAdmin() {
		return isUserAdmin;
	}

	public void setUserAdmin(boolean isUserAdmin) {
		this.isUserAdmin = isUserAdmin;
	}

	public void setUserEamil(String email) {
		this.userEmail = email;
	}

	public String getUserEamil() {
		return this.userEmail;
	}

	public EntityMetaData getEntityMetaData() {
		return entityMetaData;
	}

	public Collection<Contacts> getAll() throws AppException {
		try {
			return contactsDao.getAll();

		} catch (DataAccessException dae) {
			String message = "Unable to retrieve all contacts";
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public void createUserSync(String userEmail, String domain, Date date)
			throws AppException {
		com.metacube.ipathshala.entity.UserSync userSync = new com.metacube.ipathshala.entity.UserSync();
		userSync.setUserEmail(userEmail);
		userSync.setDomainName(domain);
		userSync.setSyncDate(date);
		userSyncService.createUserSync(userSync);
	}

	public UserSync getUserSync(String userEmail, Date date)
			throws AppException {

		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"UserSync");
		query.addFilter("userEmail",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				userEmail);
		query.addFilter("date",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				date);

		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> userSyncs = preparedQuery.asList(FetchOptions.Builder
				.withDefaults());
		UserSync userSync = null;
		if (userSyncs != null && !userSyncs.isEmpty())
			userSync = getUserSync(userSyncs.get(0));
		return userSync;

		// return userSyncService.getUserSyncByEmailAndDate(userEmail, date);
	}

	private UserSync getUserSync(Entity entity) {
		UserSync userSync = new UserSync();
		userSync.setDomain((String) entity.getProperty("domain"));
		userSync.setDate((String) entity.getProperty("date"));
		userSync.setUserEmail((String) entity.getProperty("userEmail"));
		userSync.setKey(entity.getKey());
		return userSync;

	}

	public Collection<Contacts> getAllGlobalFilteredContacts(
			DataContext dataContext) throws AppException {
		try {
			Collection<Contacts> contacts = new ArrayList<Contacts>();
			Collection<Object> searchedObjects = globalFilterSearchService
					.doSearch(dataContext, entityMetaData).getResultObjects();
			for (Object contact : searchedObjects) {
				contacts.add((Contacts) contact);
			}
			return contacts;
		} catch (DataAccessException dae) {
			String message = "Unable to retrieve all contacts";
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Contacts createContact(Contacts contacts) throws AppException {
		try {

			super.validate(contacts, entityMetaData, globalFilterSearchService,
					null);

			return contactsDao.create(contacts);
		} catch (DataAccessException dae) {
			String message = "Unable to create contact:" + contacts;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	/*
	 * public Contacts createContactForAllDomainUsers(){
	 * 
	 * }
	 */
	public Contacts updateContact(Contacts contacts) throws AppException {
		try {

			validate(contacts, entityMetaData, globalFilterSearchService, null);
			Contacts currentContact = contactsDao.update(contacts);
			return currentContact;

		} catch (DataAccessException dae) {
			String message = "Unable to update contact:" + contacts;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public void deleteContact(Contacts contacts, DataContext dataContext)
			throws AppException {
		log.debug("Calling delete Contact for contact id: " + contacts.getKey());
		try {

			contactsDao.remove(contacts.getKey());
		} catch (DataAccessException dae) {
			String message = "Unable to delete contact:" + contacts;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Collection<Contacts> getByKeys(List<Key> contactsKeyList)
			throws AppException {
		try {
			return keyListService.getByKeys(contactsKeyList, Contacts.class);
		} catch (DataAccessException dae) {
			String message = "Unable to retrieve contact fronm given key list";
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Object restoreEntity(Object object) {
		Contacts contact = (Contacts) object;
		contact.setIsDeleted(false);
		return contactsDao.update(contact);
	}

	public Object getById(Key key) {
		return contactsDao.get(key);
	}

	public void exportContacts(DataContext dataContext,
			ServletOutputStream outputStream) throws AppException {
		List<Contacts> contacts = (List<Contacts>) getAllGlobalFilteredContacts(dataContext);
		WritableWorkbook workbook = null;
		try {
			workbook = Workbook.createWorkbook(outputStream);
		} catch (IOException e) {
			String msg = "Cannot create new workbook";
			throw new AppException(msg);
		}

		WritableSheet sheet = workbook.createSheet("Students", 0);
		WritableFont times16font = new WritableFont(WritableFont.TIMES, 10,
				WritableFont.BOLD, true);
		WritableCellFormat times16format = new WritableCellFormat(times16font);
		List<Cell> cellList = new ArrayList<Cell>();
		cellList.add(new Label(0, 0, "ID", times16format));
		cellList.add(new Label(1, 0, "FirstName ", times16format));
		cellList.add(new Label(2, 0, "LastName", times16format));
		cellList.add(new Label(3, 0, "FullName", times16format));
		cellList.add(new Label(4, 0, "WorkEmail", times16format));
		cellList.add(new Label(5, 0, "WorkPhone", times16format));
		cellList.add(new Label(6, 0, "WorkAddress", times16format));
		int rowNumber = 1;

		for (Contacts entry : contacts) {
			cellList.add(new Label(0, rowNumber, String.valueOf(entry.getKey()
					.getId())));
			cellList.add(new Label(1, rowNumber, entry.getFirstName(),
					times16format));
			cellList.add(new Label(2, rowNumber, entry.getLastName(),
					times16format));
			cellList.add(new Label(3, rowNumber, entry.getFullName(),
					times16format));
			cellList.add(new Label(4, rowNumber, entry.getWorkEmail(),
					times16format));
			cellList.add(new Label(5, rowNumber, entry.getWorkPhone(),
					times16format));
			cellList.add(new Label(6, rowNumber, entry.getWorkAddress(),
					times16format));
			rowNumber++;

		}

		// add each cell to the worksheet
		for (Cell cell : cellList) {
			try {

				sheet.addCell((WritableCell) cell);
				CellView columnView = sheet.getColumnView(cell.getColumn());
				// int highest = cell.getContents().length();
				columnView.setSize(30 * 256);
				sheet.setColumnView(cell.getColumn(), columnView);
			} catch (RowsExceededException e) {
				String msg = "Maximum number of row exceeded";
				throw new AppException(msg);
			} catch (WriteException e) {
				String msg = "Unable to write cell on the workbook";
				throw new AppException(msg);
			}
		}
		try {
			workbook.write();
			workbook.close();
		} catch (IOException e1) {
			String msg = "Unable to write the new workbook";
			throw new AppException(msg);
		} catch (WriteException e) {
			String msg = "Unable to write the new workbook";
			throw new AppException(msg);
		}
	}

	@Autowired
	private WorkflowService workflowService;

	public void deleteContactandExecuteWorkflow(List<Key> contactKeyList,
			DataContext dataContext) throws AppException {
		Workflow workflow = deleteContactWorkflow(contactKeyList, dataContext);
		if (workflow != null) {
			workflow.setWorkflowStatus(WorkflowStatusType.INPROGRESS.toString());
			workflowService.updateWorkflow(workflow);
			workflowService.triggerWorkflow(workflow);
		}
	}

	public Workflow deleteContactWorkflow(List<Key> contactList,
			DataContext dataContext) throws AppException {
		BulkContactDeleteWorkflowContext context = new BulkContactDeleteWorkflowContext();
		context.setContacts(contactList);
		WorkflowInfo info = new WorkflowInfo(
				"bulkdeleteContactWorkflowProcessor");
		info.setIsNewWorkflow(true);
		context.setWorkflowInfo(info);
		Workflow workflow = new Workflow();
		workflow.setWorkflowName("Bulk Contacts Delete");
		workflow.setWorkflowInstanceId(info.getWorkflowInstance());
		workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());
		workflow.setContext(context);
		workflowService.createWorkflow(workflow);
		return workflow;

	}

	public Workflow duplicateContactWorkflow(List<Key> contactKeyList,
			DataContext dataContext) throws AppException {
		BulkContactDuplicateWorkflowContext context = new BulkContactDuplicateWorkflowContext();
		context.setContacts(contactKeyList);
		context.setDataContext(dataContext);
		WorkflowInfo info = new WorkflowInfo(
				"bulkduplicateContactWorkflowProcessor");
		info.setIsNewWorkflow(true);
		context.setWorkflowInfo(info);
		Workflow workflow = new Workflow();
		workflow.setWorkflowName("Bulk Contacts duplicate");
		workflow.setWorkflowInstanceId(info.getWorkflowInstance());
		workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());
		workflow.setContext(context);
		workflowService.createWorkflow(workflow);
		return workflow;

	}

	public void duplicateContactandExecuteWorkflow(List<Key> contactKeyList,
			DataContext dataContext) throws AppException {
		Workflow workflow = duplicateContactWorkflow(contactKeyList,
				dataContext);
		if (workflow != null) {
			workflow.setWorkflowStatus(WorkflowStatusType.INPROGRESS.toString());
			workflowService.updateWorkflow(workflow);
			workflowService.triggerWorkflow(workflow);
		}
	}

	public void addContactForAllDomainUsers(String domain, Contacts contact)
			throws AppException {
		Workflow workflow = addContactForAllDomainUsersWorkflow(domain, contact);
		if (workflow != null) {
			workflow.setWorkflowStatus(WorkflowStatusType.INPROGRESS.toString());
			workflowService.updateWorkflow(workflow);
			workflowService.triggerWorkflow(workflow);
		}
	}

	private Workflow addContactForAllDomainUsersWorkflow(String domain,
			Contacts contact) throws AppException {
		AddContactForAllDomainUsersContext context = new AddContactForAllDomainUsersContext();
		context.setContactInfo(contact);
		context.setDomain(domain);
		WorkflowInfo info = new WorkflowInfo(
				"addContactForAllDomainUsersProcessor");
		info.setIsNewWorkflow(true);
		context.setWorkflowInfo(info);
		Workflow workflow = new Workflow();
		workflow.setWorkflowName("Add Contact for all domain Users");
		workflow.setWorkflowInstanceId(info.getWorkflowInstance());
		workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());
		workflow.setContext((WorkflowContext) context);
		workflowService.createWorkflow(workflow);
		return workflow;
	}

	public void addGroupToAllContactForDomain(String domain, String group,
			String email) throws AppException {
		Workflow workflow = addGroupToAllContactForDomainWorkflow(domain,
				group, email);
		if (workflow != null) {
			workflow.setWorkflowStatus(WorkflowStatusType.INPROGRESS.toString());
			workflowService.updateWorkflow(workflow);
			workflowService.triggerWorkflow(workflow);
		}
	}

	private Workflow addGroupToAllContactForDomainWorkflow(String domain,
			String group, String email) throws AppException {
		AddGroupToAllContactsForDomainContext context = new AddGroupToAllContactsForDomainContext();
		context.setDomainName(domain);
		context.setGroupName(group);
		context.setEmail(email);
		WorkflowInfo info = new WorkflowInfo(
				"addGroupToAllContactForDomainProcessor");
		info.setIsNewWorkflow(true);
		context.setWorkflowInfo(info);
		Workflow workflow = new Workflow();
		workflow.setWorkflowName("Add Group to all Contacts for domain");
		workflow.setWorkflowInstanceId(info.getWorkflowInstance());
		workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());
		workflow.setContext((WorkflowContext) context);
		workflowService.createWorkflow(workflow);
		return workflow;
	}

	private com.google.gdata.client.contacts.ContactsService getContactsService()
			throws Exception {
		contactsService = new com.google.gdata.client.contacts.ContactsService(
				"ykko-test");
		String consumerKey = domainConfig.getConsumerkey();
		String consumerSecret = domainConfig.getConsumerKeySecret();

		GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
		oauthParameters.setOAuthConsumerKey(consumerKey);
		oauthParameters.setOAuthConsumerSecret(consumerSecret);
		oauthParameters
				.setScope("http://www.google.com/m8/feeds/contacts/default/full");

		try {
			contactsService.setOAuthCredentials(oauthParameters,
					new OAuthHmacSha1Signer());
			contactsService.setReadTimeout(20000);
			contactsService.setConnectTimeout(20000);
		} catch (OAuthException e) {
			e.printStackTrace();
			throw new ServletException("Unable to initialize contacts service",
					e);
		}

		return contactsService;
	}

	public DomainAdmin verifyUser(String userEmail) {
		DomainAdmin result = null;
		String feedurl = domainConfig.getGroupFeedUrl();
		feedurl = feedurl + CommonWebUtil.getDomain(userEmail)
				+ "/full?xoauth_requestor_id=" + userEmail;
		com.google.gdata.client.contacts.ContactsService service;
		try {
			service = getContactsService();
			this.isUserAdmin = false;
			System.out.println(feedurl);
			service.getFeed(new URL(feedurl), ContactGroupFeed.class);
			// success,,, so its admin user
			this.isUserAdmin = true;
			setUserEamil(userEmail);
			result = getDomainAdminByDomainName(CommonWebUtil
					.getDomain(userEmail));
			if (result == null) {
				// create new
				createDomainAdminEmail(userEmail);
			}
		} catch (Exception e) {
			System.out.println("******************** not admin user");
			result = getDomainAdminByDomainName(CommonWebUtil
					.getDomain(userEmail));
			setUserEamil(result.getAdminEmail());
		}
		return result;
	}

	public boolean isAdmin(String email) {
		boolean isAdmin = false;
		// List<String> users = new ArrayList<String>();
		com.google.gdata.client.appsforyourdomain.UserService guserService = new com.google.gdata.client.appsforyourdomain.UserService(
				"ykko-test");
		String consumerKey = domainConfig.getConsumerkey();
		String consumerSecret = domainConfig.getConsumerKeySecret();
		String urlEscopo = "http://apps-apis.google.com/a/feeds/user/#readonly";
		GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
		oauthParameters.setOAuthConsumerKey(consumerKey);
		oauthParameters.setOAuthConsumerSecret(consumerSecret);
		oauthParameters.setOAuthType(OAuthType.TWO_LEGGED_OAUTH);

		oauthParameters.setScope(urlEscopo);

		try {
			guserService.setOAuthCredentials(oauthParameters,
					new OAuthHmacSha1Signer());
			guserService.setReadTimeout(20000);
			guserService.setConnectTimeout(20000);
			final String APPS_FEEDS_URL_BASE = "https://apps-apis.google.com/a/feeds/";
			final String SERVICE_VERSION = "2.0";

			String domainUrlBase = APPS_FEEDS_URL_BASE
					+ CommonWebUtil.getDomain(email) + "/";

			URL retrieveUrl = new URL(domainUrlBase + "user/" + SERVICE_VERSION);
			UserFeed genericFeed = new UserFeed();

			Link nextLink = null;

			do {
				try {
					UserFeed newGenericFeed = guserService.getFeed(retrieveUrl,
							UserFeed.class);

					genericFeed.getEntries()
							.addAll(newGenericFeed.getEntries());
					nextLink = newGenericFeed.getLink(Link.Rel.NEXT,
							Link.Type.ATOM);
					if (nextLink != null) {
						retrieveUrl = new URL(nextLink.getHref());
					}
				} catch (AppsForYourDomainException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (nextLink != null);

			if (genericFeed != null && genericFeed.getEntries() != null
					&& !genericFeed.getEntries().isEmpty()) {
				for (UserEntry genericEntry : genericFeed.getEntries()) {
					// System.out.println(genericEntry.getLogin().getUserName());
					if (genericEntry.getLogin().getUserName()
							.equalsIgnoreCase(CommonWebUtil.getUserId(email))
							&& genericEntry.getLogin().getAdmin()) {
						isAdmin = true;
						break;
					}
				}
			}
		} catch (OAuthException e) {
			e.printStackTrace();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isAdmin;
	}

	@Autowired
	private DomainAdminService domainAdminService;

	private DomainAdmin getDomainAdminByDomainName(String domain) {
		return domainAdminService.getDomainAdminByDomainName(domain);

	}

	public void removeDomainAdmin(String domain) {
		DomainAdmin domainAdmin = getDomainAdminByDomainName(domain);

		/*
		 * datastore.delete(KeyFactory.createKey("domain-admin", domainAdmin
		 * .getKey().getId()));
		 */
	}

	/*
	 * private List<Customer> getDomainAdminEmails(String domain) {
	 * com.google.appengine.api.datastore.Query query = new
	 * com.google.appengine.api.datastore.Query( "domain-admin");
	 * query.addFilter("domain", FilterOperator.EQUAL, domain); List<Entity>
	 * list = datastore.prepare(query).asList(
	 * FetchOptions.Builder.withDefaults()); if (list == null || list.size() <=
	 * 0) { return null; } List<Customer> customers = new ArrayList<Customer>();
	 * for (Entity entity : list) { customers.add(getCustomer(entity)); } return
	 * customers; }
	 */
	private void createDomainAdminEmail(String userEmail) {

		Entity entity = new Entity("domain-admin");
		entity.setProperty("domain", CommonWebUtil.getDomain(userEmail));
		entity.setProperty("email", userEmail);
		entity.setProperty("registeredData", new Date());
		entity.setProperty("accountType", "Free");
		entity.setProperty("totalContacts", 0);
		entity.setProperty("upgradedDate", null);
		datastore.put(entity);
	}

	/*
	 * public List<Contacts> getAllContactsByGroup(){
	 * 
	 * }
	 */
	/*
	 * private Customer getCustomer(Entity entity) { Customer cust = new
	 * Customer();
	 * cust.setDomain(CommonUtil.getNotNullValue(entity.getProperty("domain")));
	 * cust.setAccountType(CommonUtil.getNotNullValue(entity
	 * .getProperty("accountType")));
	 * cust.setAdminEmail(CommonUtil.getNotNullValue(entity
	 * .getProperty("email"))); Date registeredDate =
	 * entity.getProperty("registeredData") == null ? null : (Date)
	 * entity.getProperty("registeredData"); Date upgradedDate =
	 * entity.getProperty("upgradedDate") == null ? null : (Date)
	 * entity.getProperty("upgradedDate");
	 * cust.setRegisteredDate(registeredDate);
	 * cust.setUpgradedDate(upgradedDate);
	 * cust.setTotalContacts(entity.getProperty("totalContacts") == null ? 0 :
	 * Integer.parseInt(entity.getProperty("totalContacts") .toString()));
	 * cust.setId(entity.getKey().getId()); return cust; }
	 */

	public List<String> getAllDomainUsers(String domain) {
		List<String> users = new ArrayList<String>();
		com.google.gdata.client.appsforyourdomain.UserService guserService = new com.google.gdata.client.appsforyourdomain.UserService(
				"ykko-test");
		String consumerKey = domainConfig.getConsumerkey();
		String consumerSecret = domainConfig.getConsumerKeySecret();
		String urlEscopo = "http://apps-apis.google.com/a/feeds/user/#readonly";
		GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
		oauthParameters.setOAuthConsumerKey(consumerKey);
		oauthParameters.setOAuthConsumerSecret(consumerSecret);
		oauthParameters.setOAuthType(OAuthType.TWO_LEGGED_OAUTH);

		oauthParameters.setScope(urlEscopo);

		try {
			guserService.setOAuthCredentials(oauthParameters,
					new OAuthHmacSha1Signer());
			guserService.setReadTimeout(20000);
			guserService.setConnectTimeout(20000);

			final String APPS_FEEDS_URL_BASE = "https://apps-apis.google.com/a/feeds/";
			final String SERVICE_VERSION = "2.0";

			String domainUrlBase = APPS_FEEDS_URL_BASE + domain + "/";

			URL retrieveUrl = new URL(domainUrlBase + "user/" + SERVICE_VERSION);
			UserFeed genericFeed = new UserFeed();

			Link nextLink = null;

			do {
				try {
					System.out.println("retrieveUrl" + retrieveUrl);
					UserFeed newGenericFeed = guserService.getFeed(retrieveUrl,
							UserFeed.class);

					genericFeed.getEntries()
							.addAll(newGenericFeed.getEntries());
					nextLink = newGenericFeed.getLink(Link.Rel.NEXT,
							Link.Type.ATOM);
					if (nextLink != null) {
						retrieveUrl = new URL(nextLink.getHref());
					}
				} catch (AppsForYourDomainException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (nextLink != null);

			if (genericFeed != null && genericFeed.getEntries() != null
					&& !genericFeed.getEntries().isEmpty()) {
				System.out.println("$$$$$$$$$$$$$$$$");
				for (UserEntry genericEntry : genericFeed.getEntries()) {
					System.out.println(genericEntry.getLogin().getUserName());
					if (!genericEntry.getLogin().getAdmin()) {
						users.add(genericEntry.getLogin().getUserName());
					}
				}
			}
		} catch (OAuthException e) {
			e.printStackTrace();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (users != null && !users.isEmpty()) {
			users = new ArrayList<String>(new HashSet<String>(users));
		}
		return users;
	}

	public List<String> getAllDomainUsersIncludingAdmin(String domain) {
		List<String> users = new ArrayList<String>();
		com.google.gdata.client.appsforyourdomain.UserService guserService = new com.google.gdata.client.appsforyourdomain.UserService(
				"ykko-test");
		String consumerKey = domainConfig.getConsumerkey();
		String consumerSecret = domainConfig.getConsumerKeySecret();
		String urlEscopo = "http://apps-apis.google.com/a/feeds/user/#readonly";
		GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
		oauthParameters.setOAuthConsumerKey(consumerKey);
		oauthParameters.setOAuthConsumerSecret(consumerSecret);
		oauthParameters.setOAuthType(OAuthType.TWO_LEGGED_OAUTH);

		oauthParameters.setScope(urlEscopo);

		try {
			guserService.setOAuthCredentials(oauthParameters,
					new OAuthHmacSha1Signer());
			guserService.setReadTimeout(20000);
			guserService.setConnectTimeout(20000);

			final String APPS_FEEDS_URL_BASE = "https://apps-apis.google.com/a/feeds/";
			final String SERVICE_VERSION = "2.0";

			String domainUrlBase = APPS_FEEDS_URL_BASE + domain + "/";

			URL retrieveUrl = new URL(domainUrlBase + "user/" + SERVICE_VERSION);
			UserFeed genericFeed = new UserFeed();

			Link nextLink = null;

			do {
				try {
					System.out.println("retrieveUrl" + retrieveUrl);
					genericFeed = guserService.getFeed(retrieveUrl,
							UserFeed.class);

					genericFeed.getEntries().addAll(genericFeed.getEntries());
					nextLink = genericFeed.getLink(Link.Rel.NEXT,
							Link.Type.ATOM);
					if (nextLink != null) {
						retrieveUrl = new URL(nextLink.getHref());
					}
				} catch (AppsForYourDomainException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (nextLink != null);

			if (genericFeed != null && genericFeed.getEntries() != null
					&& !genericFeed.getEntries().isEmpty()) {

				for (UserEntry genericEntry : genericFeed.getEntries()) {
					System.out.println(genericEntry.getLogin().getUserName());

					System.out.println("USer:"
							+ genericEntry.getLogin().getUserName());
					users.add(genericEntry.getLogin().getUserName());

					// appUsers.add(appUser);
				}
			}
		} catch (OAuthException e) {
			e.printStackTrace();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		users = new ArrayList<String>(new HashSet<String>(users));
		return users;
	}

	public List<String> getAllDomainUsersWithReadAndWritePErmissionIncludingAdmin(
			String domain) {
		List<String> allDomainUsers = getAllDomainUsersIncludingAdmin(domain);
		/*
		 * List<String> restrtictedUsers =
		 * getAllUserNamesWithNoPermissions(domain); if (restrtictedUsers !=
		 * null) { allDomainUsers.removeAll(restrtictedUsers); }
		 */
		return allDomainUsers;
	}

	/*
	 * public void setGroupName(String domainName, String groupName) { Entity
	 * entity = new Entity("DomainGroup"); entity.setProperty("domainName",
	 * domainName); entity.setProperty("groupName", groupName);
	 * datastore.put(entity); }
	 */

	@Autowired
	private DomainGroupService domainGroupService;

	public String getGroupName(String domainName) {
		DomainGroup domainGroup = domainGroupService
				.getDomainGroupByDomainName(domainName);
		if (domainGroup.getGroupName() != null) {
			return domainGroup.getGroupName();
		}
		return null;
	}

	private void createUserPermission(String userId, String domain,
			PermissionType permissionType) {
		Entity entity = new Entity("UserPermission");
		entity.setProperty("userId", userId);
		entity.setProperty("permissionType", permissionType.toString());
		entity.setProperty("domain", domain);

		datastore.put(entity);
	}

	public List<Entity> getAllUserWithWritePermissions(String domain) {
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"UserPermission");
		query.addFilter("permissionType",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				"write");
		query.addFilter("domain",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				domain);
		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> userPermissions = preparedQuery
				.asList(FetchOptions.Builder.withDefaults());
		return userPermissions;
	}

	public List<Entity> getAllUserWithNoPermissions(String domain) {
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"UserPermission");
		query.addFilter("permissionType",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				"none");
		query.addFilter("domain",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				domain);
		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> userPermissions = preparedQuery
				.asList(FetchOptions.Builder.withDefaults());
		return userPermissions;
	}

	public List<String> getAllUserNamesWithNoPermissions(String domain) {
		List<Entity> userWithWritePermissions = getAllUserWithNoPermissions(domain);
		List<String> users = new ArrayList<String>();
		;
		for (Entity userPermission : userWithWritePermissions) {
			users.add(getUserPermission(userPermission).getUserID());
		}
		return users;
	}

	private UserPermission getUserPermission(Entity entity) {
		UserPermission userPermission = new UserPermission();
		userPermission.setDomain(CommonUtil.getNotNullValue(entity
				.getProperty("domain")));
		userPermission.setKey(entity.getKey());
		/*
		 * userPermission.setPermissionType(PermissionType
		 * .getPermissionType(CommonUtil.getNotNullValue(entity
		 * .getProperty("permissionType"))));
		 */
		userPermission.setUserID(CommonUtil.getNotNullValue(entity
				.getProperty("userId")));
		return userPermission;
	}

	public void syncUserContacts(String userEmail, List<ContactEntry> entries) {
		String groupName = getGroupName(CommonWebUtil.getDomain(userEmail));
		try {
			String groupId = getUserContactsGroupId(groupName, userEmail);
			while (groupId == null || groupId.equals("")) {
				ContactGroupEntry group = new ContactGroupEntry();
				group.setSummary(new PlainTextConstruct(groupName));
				group.setTitle(new PlainTextConstruct(groupName));
				createGroup(group, userEmail);

				groupId = getUserContactsGroupId(groupName, userEmail);
			}
			List<ContactEntry> contacts = null;

			contacts = getUserContacts(1, 30000, groupId, userEmail);
			System.out.println("Fteched " + contacts.size() + " to be deleted");
			int currentSize = contacts.size();
			List<List> container = split(contacts);
			for (int i = 0; i < container.size(); i++) {
				multipleDeleteUserContacts(container.get(i), userEmail);
				int retry = 0;
				if (currentSize == getUserContacts(1, 30000, groupId, userEmail)
						.size() && currentSize != 0 & retry <= 5) {
					i--;
					retry++;
				} else {
					if (retry > 5) {
						continue;
					}
					if (currentSize - 100 >= 0) {
						currentSize = currentSize - 100;
					} else
						currentSize = 0;

				}
			}
			System.out.println(" Successfully deleted");
			System.out.println("GroupId is" + groupId);
			for (ContactEntry entry : entries) {
				GroupMembershipInfo gmInfo = new GroupMembershipInfo(); // added
				gmInfo.setHref(groupId); // added
				entry.getGroupMembershipInfos().remove(0);

				entry.addGroupMembershipInfo(gmInfo);

			}

			List<List> container1 = split(entries);
			for (int i = 0; i < container1.size(); i++) {
				System.out.println("Creating batch" + i);
				multipleCreateUserContacts(container1.get(i), userEmail);
				int retry = 0;
				if (currentSize == getUserContacts(1, 30000, groupId, userEmail)
						.size() && currentSize != contacts.size() & retry <= 5) {
					i--;
					retry++;
					System.out.println("will retry for batch" + i);
				} else {
					if (retry > 5) {
						continue;
					}
					if (currentSize + 100 < contacts.size()) {
						currentSize = currentSize + 100;
					} else
						currentSize = contacts.size();

				}
			}

		} catch (AppException e) {
			System.out.println("Exception caught");
		}
	}

	public void multipleCreateUserContacts(List<ContactEntry> contactEntries,
			String userEmail) throws AppException {
		try {
			ContactEntry contactEntry = null;
			String feedurlStr = getUserFeedUrl(domainConfig.getFeedurl(),
					userEmail);
			ContactFeed batchFeed = null;
			URL feedUrl = new URL(feedurlStr);
			com.google.gdata.client.contacts.ContactsService service = getContactsService();
			ContactFeed feed = service.getFeed(feedUrl, ContactFeed.class);

			List<List> container = split(contactEntries);
			log.info("==> container size: " + container.size());

			int batchCnt = 0;
			int batchCnt1 = 0;

			for (int i = 0; i < container.size(); i++) {

				batchFeed = new ContactFeed();

				List<ContactEntry> splittedList = (List) container.get(i); // Ã«Â¶â€žÃ«Â¦Â¬Ã«ï¿½Å“
																			// List
																			// Ã¬â€“Â»ÃªÂ¸Â°
				log.info("==> splittedList size: " + splittedList.size());

				for (int j = 0; j < splittedList.size(); j++) {
					contactEntry = (ContactEntry) splittedList.get(j);
					BatchUtils.setBatchId(contactEntry,
							String.valueOf(batchCnt++));
					BatchUtils.setBatchOperationType(contactEntry,
							BatchOperationType.INSERT);
					batchFeed.getEntries().add(contactEntry);

				}

				Link batchLink = feed.getLink(Link.Rel.FEED_BATCH,
						Link.Type.ATOM);
				String url = batchLink.getHref() + "?xoauth_requestor_id="
						+ userEmail;
				ContactFeed batchResultFeed = service.batch(new URL(url),
						batchFeed);

				for (ContactEntry ent : batchResultFeed.getEntries()) {
					String batchId = BatchUtils.getBatchId(ent);
					if (!BatchUtils.isSuccess(ent)) {
						log.info("===> Insert Failed!");
						BatchStatus status = BatchUtils.getBatchStatus(ent);
						log.info("\t" + batchId + " failed ("
								+ status.getReason() + ") "
								+ status.getContent());
					} else {
						log.info("===> Inserted Sucessfully!" + "(" + batchId
								+ ")");
					}
				}// end of inner for loop
				System.out.println("Created batch" + i);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					log.error(e.getMessage(), e);
				}

			}// end of outer for loop
			log.info("====================================");
			log.info("Total " + batchCnt + " inserted!!");
			log.info("====================================");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw new AppException(e.getMessage());
		}
	}

	public String getUserContactsGroupId(String name, String userEmail)
			throws AppException {
		String result = null;
		try {
			String feedurl = getUserFeedUrl(domainConfig.getGroupFeedUrl(),
					userEmail);
			String scGrpName = name;
			log.info("scGrpName: " + scGrpName);
			com.google.gdata.client.contacts.ContactsService service = getContactsService();
			ContactGroupFeed resultFeed = service.getFeed(new URL(feedurl),
					ContactGroupFeed.class);
			if (resultFeed != null) {
				String titleTmp = null;
				TextConstruct tc = null;
				for (int i = 0; i < resultFeed.getEntries().size(); i++) {
					ContactGroupEntry groupEntry = resultFeed.getEntries().get(
							i);
					tc = groupEntry.getTitle();
					if (tc != null) {
						titleTmp = tc.getPlainText();
						// logger.info("Id: " + groupEntry.getId());
						if (titleTmp.equals(scGrpName)) {
							result = groupEntry.getId();
							log.info("Group Name: " + titleTmp);
							log.info("Group Id: " + result);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("e.getMessage: " + e.getMessage());
			// throw new AppException();
		}
		return result;
	}

	private String getUserFeedUrl(String url, String email) {
		url = url + email + "/full?xoauth_requestor_id=" + email;

		log.error("getUserFeedUrl == " + url);
		return url;
	}

	public void createGroup(ContactGroupEntry entry, String userEmail)
			throws AppException {
		try {
			com.google.gdata.client.contacts.ContactsService service = getContactsService();
			service.insert(
					new URL(getUserFeedUrl(domainConfig.getGroupFeedUrl(),
							userEmail)), entry);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("e.getMessage: " + e.getMessage());
			throw new AppException(e.getMessage());
		}
	}

	public void createUserContact(ContactEntry contact, String userEmail)
			throws AppException {
		try {
			com.google.gdata.client.contacts.ContactsService service = getContactsService();
			String feedurl = getUserFeedUrl(domainConfig.getFeedurl(),
					userEmail);
			URL postUrl = new URL(feedurl);
			service.insert(postUrl, contact);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new AppException(e.getMessage());
		}
	}

	public List<ContactEntry> getUserContacts(int start, int limit,
			String groupId, String userEmail) throws AppException {
		List<ContactEntry> contactVOs = null;
		try {

			com.google.gdata.client.contacts.ContactsService service = getContactsService();

			log.info("start ==> " + start);

			String feedurl = domainConfig.getFeedurl();
			feedurl = feedurl + "default" + "/full";
			URL feedUrl = new URL(feedurl); // Contacts
			Query query = new Query(feedUrl);
			if (limit != -1 || start != -1) {
				query.setMaxResults(limit); // paging
				query.setStartIndex(start); // paging
			}
			query.setStartIndex(1); // paging
			query.setStringCustomParameter("showdeleted", "false");
			query.setStringCustomParameter("xoauth_requestor_id", userEmail);

			query.setStringCustomParameter("group", groupId);

			// ContactFeed resultFeed = service.getFeed(feedUrl,
			// ContactFeed.class);
			ContactFeed resultFeed = service.query(query, ContactFeed.class);

			contactVOs = resultFeed.getEntries();
			System.out.println("Total size contactsVO" + contactVOs.size());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactVOs;
	}

	private List<List> split(List list) {
		List container = null;
		int totalSize = list.size();
		int containerSize = 1;
		final int size = 100;
		/*
		 * if(list.size() > 100){ containerSize = (list.size() / 100) + 1; } //
		 */
		if (list.size() > size) {
			containerSize = list.size() / size;
			if ((list.size() % size) > 0) {
				containerSize += 1;
			}
		}
		// */
		container = new ArrayList(containerSize);
		List innerList = null;
		int tmpCnt = 0;
		for (int i = 0; i < containerSize; i++) {
			innerList = new ArrayList();
			for (int j = 0; j < size; j++) {
				if (!list.isEmpty()) {
					innerList.add(list.get(tmpCnt++));
				}
				if (tmpCnt == totalSize) {
					break;
				}
			}
			container.add(innerList);
		}
		log.info("=====> container size: " + container.size());
		return container;
	}

	public void multipleDeleteUserContacts(List<ContactEntry> contactEntries,
			String userEmail) throws AppException {
		if (contactEntries != null && !contactEntries.isEmpty()) {
			try {
				ContactEntry contactEntry = null;
				String feedurlStr = getUserFeedUrl(domainConfig.getFeedurl(),
						userEmail);
				ContactFeed batchFeed = null;
				URL feedUrl = new URL(feedurlStr);
				com.google.gdata.client.contacts.ContactsService service = getContactsService();
				ContactFeed feed = service.getFeed(feedUrl, ContactFeed.class);

				List<List> container = split(contactEntries);
				log.info("==> container size: " + container.size());

				int batchCnt = 0;
				int batchCnt1 = 0;

				for (int i = 0; i < container.size(); i++) {

					batchFeed = new ContactFeed();

					List<ContactEntry> splittedList = (List) container.get(i); // Ã«Â¶â€žÃ«Â¦Â¬Ã«ï¿½Å“
																				// List
																				// Ã¬â€“Â»ÃªÂ¸Â°
					log.info("==> splittedList size: " + splittedList.size());

					for (int j = 0; j < splittedList.size(); j++) {
						contactEntry = (ContactEntry) splittedList.get(j);
						BatchUtils.setBatchId(contactEntry,
								String.valueOf(batchCnt++));
						BatchUtils.setBatchOperationType(contactEntry,
								BatchOperationType.DELETE);
						batchFeed.getEntries().add(contactEntry);
					}

					Link batchLink = feed.getLink(Link.Rel.FEED_BATCH,
							Link.Type.ATOM);
					String url = batchLink.getHref() + "?xoauth_requestor_id="
							+ userEmail;
					ContactFeed batchResultFeed = service.batch(new URL(url),
							batchFeed);

					for (ContactEntry ent : batchResultFeed.getEntries()) {
						String batchId = BatchUtils.getBatchId(ent);
						if (!BatchUtils.isSuccess(ent)) {
							log.info("===> Insert Failed!");
							BatchStatus status = BatchUtils.getBatchStatus(ent);
							log.info("\t" + batchId + " failed ("
									+ status.getReason() + ") "
									+ status.getContent());
						} else {
							log.info("===> Inserted Sucessfully!" + "("
									+ batchId + ")");
						}
					}// end of inner for loop
					System.out.println("Deleted batch " + i);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
						log.error(e.getMessage(), e);
					}

				}// end of outer for loop
				log.info("====================================");
				log.info("Total " + batchCnt + " inserted!!");
				log.info("====================================");
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
				throw new AppException(e.getMessage());
			}
		}
	}

	public void sendMailToSelectedContacts() throws IOException {
		Message message = new Message();
		MailService mailService = MailServiceFactory.getMailService();
		message.setSender("harish@nicefact.com");
		message.setTo("harrygenius099@gmail.com");
		message.setTextBody("HI HERE IS A SAMPLE MAIL SEBD BY GOOGLE MAIL SERVICE API");
		mailService.send(message);

	}

}
