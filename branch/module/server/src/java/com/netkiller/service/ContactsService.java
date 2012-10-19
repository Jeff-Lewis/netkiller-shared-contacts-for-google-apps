package com.netkiller.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
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
import com.google.appengine.api.mail.MailService.Attachment;
import com.google.appengine.api.mail.MailService.Message;
import com.google.appengine.api.mail.MailServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
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
import com.google.gdata.util.PreconditionFailedException;
import com.google.gdata.util.ResourceNotFoundException;
import com.google.gdata.util.ServiceException;
import com.netkiller.GridRequest;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.dao.ContactsDao;
import com.netkiller.dao.EntityCounterDao;
import com.netkiller.dao.UserContactDao;
import com.netkiller.entity.Contact;
import com.netkiller.entity.DomainAdmin;
import com.netkiller.entity.DomainGroup;
import com.netkiller.entity.EntityCounter;
import com.netkiller.entity.UserContact;
import com.netkiller.entity.Workflow;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.manager.EntityCounterManager;
import com.netkiller.security.DomainConfig;
import com.netkiller.security.acl.Permission.PermissionType;
import com.netkiller.util.AppLogger;
import com.netkiller.util.CommonUtil;
import com.netkiller.util.CommonWebUtil;
import com.netkiller.vo.UserPermission;
import com.netkiller.vo.UserSync;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowInfo;
import com.netkiller.workflow.impl.context.AddContactForAllDomainUsersContext;
import com.netkiller.workflow.impl.context.AddGroupToAllContactsForDomainContext;
import com.netkiller.workflow.impl.context.BulkContactDeleteWorkflowContext;
import com.netkiller.workflow.impl.context.BulkContactDuplicateWorkflowContext;
import com.netkiller.workflow.impl.context.BulkContactUpdateWorkflowContext;
import com.netkiller.workflow.impl.processor.WorkflowStatusType;

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

	@Autowired
	private UserContactDao userContactDao;

	@Autowired
	private EntityCounterDao entityCounterDao;

	@Autowired
	private EntityCounterManager entityCounterManager;

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

	// private String userEmail;

	private boolean isUserAdmin;

	public boolean isUserAdmin() {
		return isUserAdmin;
	}

	public void setUserAdmin(boolean isUserAdmin) {
		this.isUserAdmin = isUserAdmin;
	}

	/*
	 * public void setUserEamil(String email) { this.userEmail = email; }
	 * 
	 * public String getUserEamil() { return this.userEmail; }
	 */

	public EntityMetaData getEntityMetaData() {
		return entityMetaData;
	}

	public Collection<Contact> getAll() throws AppException {
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
		com.netkiller.entity.UserSync userSync = new com.netkiller.entity.UserSync();
		userSync.setUserEmail(userEmail);
		userSync.setDomainName(domain);
		userSync.setSyncDate(date);
		userSyncService.createUserSync(userSync);
	}

	public com.netkiller.entity.UserSync getUserSync(String userEmail, Date date)
			throws AppException {

		/*
		 * com.google.appengine.api.datastore.Query query = new
		 * com.google.appengine.api.datastore.Query( "UserSync");
		 * query.addFilter("userEmail",
		 * com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
		 * userEmail); query.addFilter("date",
		 * com.google.appengine.api.datastore.Query.FilterOperator.EQUAL, date);
		 * 
		 * PreparedQuery preparedQuery = datastore.prepare(query); List<Entity>
		 * userSyncs = preparedQuery.asList(FetchOptions.Builder
		 * .withDefaults()); UserSync userSync = null; if (userSyncs != null &&
		 * !userSyncs.isEmpty()) userSync = getUserSync(userSyncs.get(0));
		 */
		return userSyncService.getUserSyncByEmailAndDate(userEmail, date);

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

	public Collection<Contact> getAllGlobalFilteredContacts(
			DataContext dataContext) throws AppException {
		try {
			Collection<Contact> contacts = new ArrayList<Contact>();
			Collection<Object> searchedObjects = globalFilterSearchService
					.doSearch(dataContext, entityMetaData).getResultObjects();
			for (Object contact : searchedObjects) {
				contacts.add((Contact) contact);
			}
			return contacts;
		} catch (DataAccessException dae) {
			String message = "Unable to retrieve all contacts";
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Contact createContact(Contact contacts) throws AppException {
		try {

			super.validate(contacts, entityMetaData, globalFilterSearchService,
					null);
			contacts = contactsDao.create(contacts);
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			EntityCounter entityCounter = entityCounterDao.getByEntityName(
					Contact.class.getSimpleName(),
					CommonWebUtil.getDomain(user.getEmail()));
			if (entityCounter == null) {
				entityCounter = new EntityCounter();
				entityCounter.setCount(1);
				entityCounter.setEntityName(Contact.class.getSimpleName());
				entityCounter
						.setDomain(CommonWebUtil.getDomain(user.getEmail()));
				entityCounter = entityCounterManager.create(entityCounter);

			} else {
				int count = entityCounter.getCount();
				count++;
				entityCounter.setCount(count);
				try {
					entityCounter = (EntityCounter) BeanUtils
							.cloneBean(entityCounter);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				entityCounterManager.update(entityCounter);
			}

			return contacts;

		} catch (DataAccessException dae) {
			String message = "Unable to create contact:" + contacts;
			log.error(message, dae);
			throw new AppException(message, dae);
		}

	}

	public List<UserContact> getUserContactForDomain(String domain) {
		return userContactDao.getUserContactListForDomain(domain);
	}

	public List<UserContact> getUserContactListForUserEmail(String userEmail) {
		return userContactDao.getUserContactListForUserEmail(userEmail);
	}

	/*
	 * public Contacts createContactForAllDomainUsers(){
	 * 
	 * }
	 */
	public Contact updateContact(Contact contacts) throws AppException {
		try {

			validate(contacts, entityMetaData, globalFilterSearchService, null);
			Contact currentContact = contactsDao.update(contacts);
			return currentContact;

		} catch (DataAccessException dae) {
			String message = "Unable to update contact:" + contacts;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public void updateContactAndExecuteWorkflow(Contact contact,
			String userEmail, DataContext dataContext) throws AppException {
		Workflow workflow = updateContactWorkflow(contact, userEmail,
				dataContext);
		if (workflow != null) {
			workflow.setWorkflowStatus(WorkflowStatusType.INPROGRESS.toString());
			workflowService.updateWorkflow(workflow);
			workflowService.triggerWorkflow(workflow);
		}

	}

	public Workflow updateContactWorkflow(Contact contact, String userEmail,
			DataContext dataContext) throws AppException {
		BulkContactUpdateWorkflowContext context = new BulkContactUpdateWorkflowContext();
		context.setContact(contact);
		context.setUserEmail(userEmail);
		WorkflowInfo info = new WorkflowInfo(
				"bulkUpdateContactWorkflowProcessor");
		info.setIsNewWorkflow(true);
		context.setWorkflowInfo(info);
		Workflow workflow = new Workflow();
		workflow.setWorkflowName("Bulk Contacts Update");
		workflow.setWorkflowInstanceId(info.getWorkflowInstance());
		workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());
		workflow.setContext(context);
		workflowService.createWorkflow(workflow);
		return workflow;
	}

	public ContactEntry update(ContactEntry contact, String userEmail)
			throws AppException {
		try {
			com.google.gdata.client.contacts.ContactsService service = getContactsService();
			URL editUrl = new URL(contact.getEditLink().getHref()
					+ "?xoauth_requestor_id=" + userEmail);
			ContactEntry contactEntry = service.update(editUrl, contact);
			return contactEntry;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new AppException("Update Failed");
		}

	}

	public ContactEntry getContact(String urlStr, String userEmail)
			throws AppException, ResourceNotFoundException {

		ContactEntry entry = null;
		try {
			log.info(" ========= " + urlStr);
			System.out.println(" ========= " + urlStr);
			if (urlStr.indexOf("?") != -1) {
				urlStr = urlStr + "&xoauth_requestor_id=" + userEmail;
			} else {
				urlStr = urlStr + "?xoauth_requestor_id=" + userEmail;
			}
			URL url = new URL(urlStr);
			com.google.gdata.client.contacts.ContactsService service = getContactsService();
			entry = service.getEntry(url, ContactEntry.class);
		} catch (ResourceNotFoundException rnf) {
			throw new ResourceNotFoundException(rnf.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage() + " ========= " + urlStr, e);
			throw new AppException(e.getMessage());
		}
		return entry;
	}

	/**
	 * Method to delete a contact from google account.
	 * 
	 * @param contactEntry
	 * @throws AppException
	 */
	public void delete(ContactEntry contactEntry, String userEmail)
			throws AppException {
		try {
			com.google.gdata.client.contacts.ContactsService service = getContactsService();
			URL url = new URL(contactEntry.getEditLink().getHref()
					+ "?xoauth_requestor_id=" + userEmail);
			ContactEntry toBeDeletedContactEntry = service.getEntry(url,
					ContactEntry.class);
			try {
				toBeDeletedContactEntry.delete();
			} catch (PreconditionFailedException pfe) {
				log.error("Etags not match for delte the contact Entry: "
						+ toBeDeletedContactEntry);
				throw new AppException(
						"Cannot delete the contact Entry as Etags are not matched for: "
								+ toBeDeletedContactEntry);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			throw new AppException("Cannot Delete" + contactEntry);
		}
	}

	/**
	 * Methos to delete a contact from DataBase of application.
	 * 
	 * @param contacts
	 * @param dataContext
	 * @throws AppException
	 */
	public void deleteContact(Contact contacts, DataContext dataContext)
			throws AppException {
		log.debug("Calling delete Contact for contact id: " + contacts.getKey());
		try {
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			contactsDao.remove(contacts);
			EntityCounter entityCounter = entityCounterDao.getByEntityName(
					Contact.class.getSimpleName(),
					CommonWebUtil.getDomain(user.getEmail()));
			int count = entityCounter.getCount();
			count--;
			entityCounter.setCount(count);
			try {
				entityCounter = (EntityCounter) BeanUtils
						.cloneBean(entityCounter);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			entityCounterManager.update(entityCounter);

		} catch (DataAccessException dae) {
			String message = "Unable to delete contact:" + contacts;
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Collection<Contact> getByKeys(List<Key> contactsKeyList)
			throws AppException {
		try {
			return keyListService.getByKeys(contactsKeyList, Contact.class);
		} catch (DataAccessException dae) {
			String message = "Unable to retrieve contact fronm given key list";
			log.error(message, dae);
			throw new AppException(message, dae);
		}
	}

	public Object restoreEntity(Object object) {
		Contact contact = (Contact) object;
		contact.setIsDeleted(false);
		return contactsDao.update(contact);
	}

	public Object getById(Key key) {
		return contactsDao.get(key);
	}

	public void generateCSVMail(String toEmail, String toName, String fromEmail)
			throws AppException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StringBuffer csvData = new StringBuffer();
		csvData.append("ID,FirstName,LastName,FullName,WorkEmail,WorkPhone,WorkAddress\n");
		List<Contact> contacts = (List<Contact>) getAllGlobalFilteredContacts(null);
		for (Contact entry : contacts) {
			csvData.append(entry.getKey().getId() + ",");
			csvData.append(entry.getFirstName() + ",");
			csvData.append(entry.getLastName() + ",");
			csvData.append(entry.getFullName() + ",");
			csvData.append(entry.getWorkEmail() + ",");
			csvData.append(entry.getWorkPhone() + ",");
			String address = entry.getWorkAddress();
			address = address.replace("\n", " ");
			address = address.replace(",", " ");
			csvData.append(address + "\n");
		}

		String domain = CommonWebUtil.getDomain(fromEmail);
		sendMail(toEmail, toName, "Admin", domainAdminService
				.getDomainAdminByDomainName(domain).getAdminEmail(), csvData);

	}

	public void sendMail(String toEmail, String toName, String fromName,
			String fromEmail, StringBuffer sb) throws AppException {
		byte[] byteArray;
		try {
			byteArray = sb.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			throw new AppException(e.getMessage());
		}

		Message mail = new Message();
		mail.setSubject("CSV Data");
		mail.setHtmlBody("Shared Contacts CSV");
		mail.setTo(toEmail);
		mail.setSender(fromEmail);
		Attachment attachment = new Attachment("CSV-File.csv", byteArray);
		mail.setAttachments(attachment);
		MailService service = MailServiceFactory.getMailService();
		try {
			service.send(mail);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("mail sent");

		/*
		 * MailMessage mailMessage = new MailMessage(); List<Recipient>
		 * recipients = new ArrayList<Recipient>(); Recipient recipient = new
		 * Recipient(); MailAddress recipientMailAddress = new
		 * MailAddress(toName, toEmail);
		 * recipient.setMailAddress(recipientMailAddress);
		 * recipient.setRecipientType(RecipientType.TO);
		 * recipients.add(recipient); mailMessage.setRecipients(recipients);
		 * List<MailAttachment> attachments = new ArrayList<MailAttachment>();
		 * MailAttachment attachment = new MailAttachment();
		 * attachments.add(attachment); attachment.setFile(byteArray);
		 * attachment.setFilename("Netkiller-shared.csv");
		 * mailMessage.setSubject("CSV Data"); mailMessage.setHtmlBody(fromEmail
		 * + " has shared the attached contacts with you.");
		 * mailMessage.setAttachments(attachments); mailMessage.setSender(new
		 * MailAddress(fromName, fromEmail)); mailService.sendMail(mailMessage);
		 */
	}

	public void exportContacts(DataContext dataContext,
			ServletOutputStream outputStream) throws AppException {
		List<Contact> contacts = (List<Contact>) getAllGlobalFilteredContacts(dataContext);
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

		for (Contact entry : contacts) {
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
			String userEmail, DataContext dataContext) throws AppException {
		Workflow workflow = deleteContactWorkflow(contactKeyList, userEmail,
				dataContext);
		if (workflow != null) {
			workflow.setWorkflowStatus(WorkflowStatusType.INPROGRESS.toString());
			workflowService.updateWorkflow(workflow);
			workflowService.triggerWorkflow(workflow);
		}
	}

	public Workflow deleteContactWorkflow(List<Key> contactKeyList,
			String userEmail, DataContext dataContext) throws AppException {
		BulkContactDeleteWorkflowContext context = new BulkContactDeleteWorkflowContext();
		context.setContactKeyList(contactKeyList);
		context.setUserEmail(userEmail);
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
			DataContext dataContext, String domain, String urlId)
			throws AppException {
		BulkContactDuplicateWorkflowContext context = new BulkContactDuplicateWorkflowContext();
		context.setContacts(contactKeyList);
		context.setDataContext(dataContext);
		context.setDomain(domain);
		if (!StringUtils.isBlank(urlId)) {
			context.setUrlId(urlId);
			context.setAddToConnect(true);
		}
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
			DataContext dataContext, String domain, String urlId)
			throws AppException {
		Workflow workflow = duplicateContactWorkflow(contactKeyList,
				dataContext, domain, urlId);
		if (workflow != null) {
			workflow.setWorkflowStatus(WorkflowStatusType.INPROGRESS.toString());
			workflowService.updateWorkflow(workflow);
			workflowService.triggerWorkflow(workflow);
		}
	}

	public void addContactForAllDomainUsers(String domain, Contact contact)
			throws AppException {
		Workflow workflow = addContactForAllDomainUsersWorkflow(domain, contact);
		if (workflow != null) {
			workflow.setWorkflowStatus(WorkflowStatusType.INPROGRESS.toString());
			workflowService.updateWorkflow(workflow);
			workflowService.triggerWorkflow(workflow);
		}
	}

	private Workflow addContactForAllDomainUsersWorkflow(String domain,
			Contact contact) throws AppException {
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
		workflow.setContext(context);
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
		/*
		 * oauthParameters
		 * .setScope("http://www.google.com/m8/feeds/contacts/default/full");
		 * oauthParameters.setOAuthType(OAuthType.TWO_LEGGED_OAUTH);
		 */
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
			// setUserEamil(userEmail);
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
			// setUserEamil(result.getAdminEmail());
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

					e.printStackTrace();
				} catch (ServiceException e) {

					e.printStackTrace();
				} catch (IOException e) {

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

					e.printStackTrace();
				} catch (ServiceException e) {

					e.printStackTrace();
				} catch (IOException e) {

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

					e.printStackTrace();
				} catch (ServiceException e) {

					e.printStackTrace();
				} catch (IOException e) {

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

	public List<ContactEntry> getContacts(int start, int limit, String groupId,
			boolean isUseForSharedContacts, GridRequest gridRequest,
			String userEmail) throws AppException {
		List<ContactEntry> contactVOs = null;
		try {

			com.google.gdata.client.contacts.ContactsService service = getContactsService();

			log.info("start ==> " + start);
			String feedurl = domainConfig.getFeedurl();
			feedurl = feedurl + CommonWebUtil.getDomain(userEmail) + "/full";
			URL feedUrl = new URL(feedurl); // Contacts
			Query query = new Query(feedUrl);
			if (limit != -1 || start != -1) {
				query.setMaxResults(limit); // paging
				query.setStartIndex(start); // paging
			}
			query.setStringCustomParameter("orderby", "lastModifiedDate");
			query.setStringCustomParameter("sortorder", "descending"); // "ascending"
																		// or
																		// "descending"
			query.setStringCustomParameter("isDeleted", "false");
			query.setStringCustomParameter("xoauth_requestor_id", userEmail);
			if (isUseForSharedContacts) {
				query.setStringCustomParameter("group", groupId);
			}
			ContactFeed resultFeed = service.query(query, ContactFeed.class);

			contactVOs = resultFeed.getEntries();
			System.out.println("Total size contactsVO" + contactVOs.size());
			DomainAdmin domainAdmin = domainAdminService
					.getDomainAdminByDomainName(CommonWebUtil
							.getDomain(userEmail));
			if (domainAdmin != null
					&& contactVOs.size() != domainAdmin.getTotalCounts()) {
				updateTotalContacts(domainAdmin.getKey(), contactVOs.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactVOs;
	}

	public Boolean updateTotalContacts(Key domainAdminKey, Integer contacts) {
		try {
			DomainAdmin domainAdmin = domainAdminService
					.getById(domainAdminKey);
			domainAdmin.setTotalCounts(contacts);
			domainAdminService.updateDomainAdmin(domainAdmin);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return false;
		}
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
				// entry.getGroupMembershipInfos().remove(0);

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

	/*
	 * public String getUserContactsGroupId(String name, String userEmail)
	 * throws AppException { String result = null; try { String feedurl =
	 * domainConfig.getGroupFeedUrl()+ userEmail + "/full"; String scGrpName =
	 * name; com.google.gdata.client.contacts.ContactsService service =
	 * getContactsService();
	 * 
	 * 
	 * ContactGroupFeed resultFeed = service.getFeed(new URL(feedurl),
	 * ContactGroupFeed.class);
	 * 
	 * 
	 * do {
	 * 
	 * ContactGroupFeed resultFeed = service.getFeed(retrieveUrl,
	 * ContactGroupFeed.class);
	 * contactGroupEntries.addAll(resultFeed.getEntries()); nextLink =
	 * resultFeed.getLink(Link.Rel.NEXT, Link.Type.ATOM); if (nextLink != null)
	 * { retrieveUrl = new URL(nextLink.getHref()); }
	 * 
	 * } while (nextLink != null);
	 * 
	 * 
	 * URL retrieveUrl = new URL(feedurl); Query query = new Query(retrieveUrl);
	 * query.setMaxResults(100000); // paging query.setStartIndex(1);
	 * query.setStringCustomParameter("showdeleted", "false");
	 * query.setStringCustomParameter("xoauth_requestor_id", userEmail);
	 * 
	 * ContactGroupFeed resultFeed = service.query(query,
	 * ContactGroupFeed.class); Collection<ContactGroupEntry>
	 * contactGroupEntries = resultFeed .getEntries();
	 * 
	 * if (!contactGroupEntries.isEmpty()) { String titleTmp = null;
	 * TextConstruct tc = null; for (ContactGroupEntry groupEntry :
	 * contactGroupEntries) { tc = groupEntry.getTitle(); if (tc != null) {
	 * titleTmp = tc.getPlainText(); // logger.info("Id: " +
	 * groupEntry.getId()); if (titleTmp.equals(scGrpName)) { result =
	 * groupEntry.getId(); break; } } } } } catch (Exception e) { //
	 * e.printStackTrace(); // logger.severe("e.getMessage: " + e.getMessage());
	 * e.printStackTrace(); } return result; }
	 */

	public String getUserContactsGroupId(String name, String userEmail)
			throws AppException {
		String result = null;
		try {
			String feedurl = domainConfig.getGroupFeedUrl() + userEmail
					+ "/full";
			String scGrpName = name;
			com.google.gdata.client.contacts.ContactsService service = getContactsService();

			URL retrieveUrl = new URL(feedurl);
			Query query = new Query(retrieveUrl);
			query.setMaxResults(100000); // paging
			query.setStartIndex(1);
			query.setStringCustomParameter("showdeleted", "false");
			query.setStringCustomParameter("xoauth_requestor_id", userEmail);

			ContactGroupFeed resultFeed = service.query(query,
					ContactGroupFeed.class);
			Collection<ContactGroupEntry> contactGroupEntries = resultFeed
					.getEntries();
			if (!contactGroupEntries.isEmpty()) {
				String titleTmp = null;
				TextConstruct tc = null;
				for (ContactGroupEntry groupEntry : contactGroupEntries) {
					tc = groupEntry.getTitle();
					if (tc != null) {
						titleTmp = tc.getPlainText();
						// logger.info("Id: " + groupEntry.getId());
						if (titleTmp.equals(scGrpName)) {
							result = groupEntry.getId();
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			// logger.severe("e.getMessage: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	private String getUserFeedUrl(String url, String email) {
		url = url + email + "/full?xoauth_requestor_id=" + email;

		log.error("getUserFeedUrl == " + url);
		return url;
	}

	public ContactGroupEntry createGroup(ContactGroupEntry entry,
			String userEmail) throws AppException {
		try {
			com.google.gdata.client.contacts.ContactsService service = getContactsService();
			entry = service.insert(
					new URL(getUserFeedUrl(domainConfig.getGroupFeedUrl(),
							userEmail)), entry);
			return entry;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("e.getMessage: " + e.getMessage());
			throw new AppException(e.getMessage());
		}
	}

	public ContactEntry createUserContact(ContactEntry contact, String userEmail)
			throws AppException {
		try {
			com.google.gdata.client.contacts.ContactsService service = getContactsService();
			String feedurl = getUserFeedUrl(domainConfig.getFeedurl(),
					userEmail);
			URL postUrl = new URL(feedurl);
			ContactEntry contactEntry = service.insert(postUrl, contact);
			return contactEntry;
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
			query.setStringCustomParameter("isDeleted", "false");
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

	public List<Contact> doSearch(GridRequest filterInfo) {
		return contactsDao.doSearch(filterInfo);
	}

	public String getSharedContactsGroupId(String name) throws AppException {
		String result = null;
		try {
			String feedurl = domainConfig.getFeedurl();
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
			// e.printStackTrace();
			// logger.severe("e.getMessage: " + e.getMessage());
			log.error(e.getMessage(), e);
			throw new AppException(e.getMessage());
		}
		return result;
	}

	/*
	 * Method to get the admin by any logged in user for a domain
	 * 
	 * @param email(Email ID of logged in user)
	 */
	public String getAdminEmailForFirstTimeByLoginWithAnyUserOfDomain(
			String email) {
		boolean isAdmin = false;
		String adminEmail = null;
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

					e.printStackTrace();
				} catch (ServiceException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}
			} while (nextLink != null);

			if (genericFeed != null && genericFeed.getEntries() != null
					&& !genericFeed.getEntries().isEmpty()) {

				for (UserEntry genericEntry : genericFeed.getEntries()) {
					System.out.println(genericEntry.getLogin().getUserName());

					if (genericEntry.getLogin().getAdmin()) {
						isAdmin = true;
						adminEmail = genericEntry.getLogin().getUserName()
								+ "@" + CommonWebUtil.getDomain(email);
						break;
					}
				}

			}
		} catch (OAuthException e) {
			e.printStackTrace();

		} catch (MalformedURLException e) {

			e.printStackTrace();
		}
		return adminEmail;
	}

	/*public String getAllContactOfAUser(String userEmailDec) {
		String emailStr = "";
		List<UserContact> userContactList = new ArrayList<UserContact>();
		userContactList = userContactDao
				.getUserContactListForUserEmail(userEmailDec);
		List<Key> contactKeyList = new ArrayList<Key>();
		if (userContactList != null && !userContactList.isEmpty()) {

			for (UserContact userContact : userContactList) {
				if (userContact.getContactKey() != null) {
					contactKeyList.add(userContact.getContactKey());
				}
			}
		}
		List<Contact> contactList = new ArrayList<Contact>();
		if (contactKeyList != null && !contactKeyList.isEmpty()) {
			contactList = (List<Contact>) keyListService.getByKeys(
					contactKeyList, Contact.class);
		}
		if (contactList != null && !contactList.isEmpty()) {
			for (Contact contact : contactList) {
				if (contact.getWorkEmail() != null) {
					emailStr += contact.getWorkEmail();
				}
			}
		}

		return emailStr;
	}*/
}
