package com.netkiller.service.sharedcontacts;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import jxl.Cell;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.Query;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthParameters.OAuthType;
import com.google.gdata.client.contacts.ContactsService;
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
import com.google.gdata.util.ServiceException;
import com.netkiller.exception.AppException;
import com.netkiller.googleUtil.ContactInfo;
import com.netkiller.search.GridRequest;
import com.netkiller.security.acl.PermissionType;
import com.netkiller.util.CommonUtil;
import com.netkiller.util.CommonWebUtil;
import com.netkiller.util.SharedContactsUtil;
import com.netkiller.util.UnicodeUtils;
import com.netkiller.vo.AppProperties;
import com.netkiller.vo.Customer;
import com.netkiller.vo.DomainSettings;
import com.netkiller.vo.StaticProperties;
import com.netkiller.vo.UserLogging;
import com.netkiller.vo.UserPermission;
import com.netkiller.vo.UserSync;

public class SharedContactsServiceImpl implements SharedContactsService {

	protected final Logger logger = Logger.getLogger(getClass().getName());
	private final DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();

	private AppProperties appProperties;
	private MessageSource messageSource;
	private String userEmail;

	private boolean isUserAdmin;

	@Override
	public boolean isUserAdmin() {
		return isUserAdmin;
	}

	public void setUserAdmin(boolean isUserAdmin) {
		this.isUserAdmin = isUserAdmin;
	}

	public void setAppProperties(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public void setUserEamil(String email) {
		this.userEmail = email;
	}

	@Override
	public String getUserEamil() {
		return this.userEmail;
	}

	private ContactsService service;

	private ContactsService getContactsService() throws Exception {
		service = new ContactsService("ykko-test");
		String consumerKey = appProperties.getConsumerKey();
		String consumerSecret = appProperties.getConsumerKeySecret();

		GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
		oauthParameters.setOAuthConsumerKey(consumerKey);
		oauthParameters.setOAuthConsumerSecret(consumerSecret);

		try {
			service.setOAuthCredentials(oauthParameters,
					new OAuthHmacSha1Signer());
			service.setReadTimeout(20000);
			service.setConnectTimeout(20000);
		} catch (OAuthException e) {
			e.printStackTrace();
			throw new ServletException("Unable to initialize contacts service",
					e);
		}

		return service;
	}

	@Override
	public Customer verifyUser(String userEmail) {
		Customer result = null;
		String feedurl = appProperties.getGroupFeedUrl();
		feedurl = feedurl + CommonWebUtil.getDomain(userEmail)
				+ "/full?xoauth_requestor_id=" + userEmail;
		// feedurl = feedurl + userEmail + "/full?xoauth_requestor_id=" +
		// userEmail;
		ContactsService service;
		try {
			service = getContactsService();
			this.isUserAdmin = false;
			service.getFeed(new URL(feedurl), ContactGroupFeed.class);
			// success,,, so its admin user
			this.isUserAdmin = true;
			setUserEamil(userEmail);
			result = getDomainAdminEmail(CommonWebUtil.getDomain(userEmail));
			if (result == null) {
				// create new
				createDomainAdminEmail(userEmail);
			}
		} catch (Exception e) {
			System.out.println("******************** not admin user");
			// logger.log(Level.SEVERE, e.getMessage(), e);
			result = getDomainAdminEmail(CommonWebUtil.getDomain(userEmail));
			setUserEamil(result.getAdminEmail());
		}

		/*
		 * try { service = getContactsService(); service.getFeed(new
		 * URL(feedurl), ContactGroupFeed.class); // success,,, so its admin
		 * user setUserEamil(userEmail); List<Customer> customers =
		 * getDomainAdminEmails(CommonWebUtil.getDomain(userEmail)); if
		 * (customers != null) { for (Customer customer : customers) { if
		 * (customer.getAdminEmail().equals(userEmail)) {
		 * System.out.println("Email are equal" + customer.getAdminEmail());
		 * result = customer; } } } result =
		 * getDomainAdminEmail(CommonWebUtil.getDomain(userEmail)); if (result
		 * == null || (result != null &&
		 * !result.getAdminEmail().equals(userEmail))) { // create new
		 * createDomainAdminEmail(userEmail);
		 * 
		 * for (Customer customer :
		 * getDomainAdminEmails(CommonWebUtil.getDomain(userEmail))) { if
		 * (customer.getAdminEmail().equals(userEmail)) { result = customer; } }
		 * 
		 * } } catch (Exception e) {
		 * System.out.println("******************** not admin user"); result =
		 * getDomainAdminEmail(CommonWebUtil.getDomain(userEmail));
		 * setUserEamil(result.getAdminEmail()); }
		 */
		return result;
	}

	public Customer getDomainAdminEmail(String domain) {
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"domain-admin");
		query.addFilter("domain", FilterOperator.EQUAL, domain);
		List<Entity> list = datastore.prepare(query).asList(
				FetchOptions.Builder.withLimit(1));
		if (list == null || list.size() <= 0) {
			return null;
		}
		return getCustomer(list.get(0));
	}

	public void removeDomainAdmin(String domain) {
		Customer customer = getDomainAdminEmail(domain);
		datastore
				.delete(KeyFactory.createKey("domain-admin", customer.getId()));
	}

	private List<Customer> getDomainAdminEmails(String domain) {
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"domain-admin");
		query.addFilter("domain", FilterOperator.EQUAL, domain);
		List<Entity> list = datastore.prepare(query).asList(
				FetchOptions.Builder.withDefaults());
		if (list == null || list.size() <= 0) {
			return null;
		}
		List<Customer> customers = new ArrayList<Customer>();
		for (Entity entity : list) {
			customers.add(getCustomer(entity));
		}
		return customers;
	}

	private void createDomainAdminEmail(String userEmail) {
		Entity entity = new Entity("domain-admin");
		entity.setProperty("domain", CommonWebUtil.getDomain(userEmail));
		entity.setProperty("email", userEmail);
		entity.setProperty("registeredData", new Date());
		entity.setProperty("accountType", "Free");
		entity.setProperty("totalContacts", 0);
		entity.setProperty("nscUsers", 0);
		entity.setProperty("syncedContacts", 0);
		entity.setProperty("upgradedDate", null);

		datastore.put(entity);
	}

	public Boolean upgradeMembership(Long customerId) {
		Key key = KeyFactory.createKey("domain-admin", customerId);
		Entity entity;
		try {
			entity = datastore.get(key);

			entity.setProperty("accountType", "Paid");
			entity.setProperty("upgradedDate", new Date());

			datastore.put(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
	}

	public Boolean updateMembership(Long customerId, String accountType) {
		Key key = KeyFactory.createKey("domain-admin", customerId);
		Entity entity;
		try {
			entity = datastore.get(key);

			if (!entity.getProperty("accountType").equals(accountType)
					&& accountType.equals("Paid"))
				entity.setProperty("upgradedDate", new Date());
			entity.setProperty("accountType", accountType);
			datastore.put(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
	}

	public Boolean updateTotalContacts(Long customerId, Integer contacts) {
		Key key = KeyFactory.createKey("domain-admin", customerId);
		Entity entity;
		try {
			entity = datastore.get(key);
			entity.setProperty("totalContacts", contacts);
			datastore.put(entity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
	}

	public Boolean updateCustomers(String domainName, String field, Object value) {
		if (!StringUtils.isBlank(domainName)) {
			Customer customer = getDomainAdminEmail(domainName);
			if (customer != null) {
				Key customerKey = KeyFactory.createKey("domain-admin",
						customer.getId());
				Entity entity;
				try {
					entity = datastore.get(customerKey);
					entity.setProperty(field, value);
					datastore.put(entity);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					logger.log(Level.SEVERE, e.getMessage(), e);
					return false;
				}
			}
		}
		return false;
	}

	public List<Customer> getAllCustomers() {
		List<Customer> result = new ArrayList<Customer>();
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"domain-admin");
		List<Entity> list = datastore.prepare(query).asList(
				FetchOptions.Builder.withDefaults());
		if (list != null && list.size() > 0) {
			for (Entity entity : list) {
				result.add(getCustomer(entity));
			}
		}

		return result;
	}

	/**
	 * <<<<<<< .mine
	 * ÃƒÂ¬Ã…Â¾Ã¢â‚¬Â¦ÃƒÂ«Ã‚Â Ã‚Â¥ÃƒÂ«Ã¯Â�
	 * �Â½Ã…â€œ List ElementsÃƒÂ«Ã‚Â¥Ã‚Â¼
	 * 100ÃƒÂªÃ‚Â°Ã…â€œ ÃƒÂ¬Ã¢â‚¬ï¿½Ã‚Â©
	 * ÃƒÂ«Ã‚Â¬Ã‚Â¶ÃƒÂ¬Ã¢â‚¬â€œÃ‚Â´
	 * ListÃƒÂ¬Ã¢â‚¬â€�Ã¯Â¿Â½
	 * ÃƒÂ«Ã¢â‚¬Å¾Ã‚Â£ÃƒÂ¬Ã¯Â¿Â½Ã¢â€šÂ¬
	 * ÃƒÂ­Ã¢â‚¬ÂºÃ¢â‚¬Å¾ Container
	 * ListÃƒÂ¬Ã¢â‚¬â€�Ã¯Â¿Â½
	 * ÃƒÂ«Ã¢â‚¬Â¹Ã‚Â´ÃƒÂ¬Ã¢â‚¬Â¢Ã¢â‚¬Å¾
	 * ÃƒÂ«Ã‚Â°Ã‹Å“ÃƒÂ­Ã¢â€žÂ¢Ã‹Å“ =======
	 * Ãƒ�
	 * �¬Ã…Â¾Ã¢â‚¬Â¦ÃƒÂ«Ã‚Â Ã‚Â¥ÃƒÂ«Ã¯Â�
	 * �Â½Ã…â€œ List ElementsÃƒÂ«Ã‚Â¥Ã‚Â¼
	 * 100ÃƒÂªÃ‚Â°Ã…â€œ ÃƒÂ¬Ã¢â‚¬ï¿½Ã‚Â©
	 * ÃƒÂ«Ã‚Â¬Ã‚Â¶ÃƒÂ¬Ã¢â‚¬â€œÃ‚Â´
	 * ListÃƒÂ¬Ã¢â‚¬â€�Ã¯Â¿Â½
	 * ÃƒÂ«Ã¢â‚¬Å¾Ã‚Â£ÃƒÂ¬Ã¯Â¿Â½Ã¢â€šÂ¬
	 * ÃƒÂ­Ã¢â‚¬ÂºÃ¢â‚¬Å¾ Container
	 * ListÃƒÂ¬Ã¢â‚¬â€�Ã¯Â¿Â½
	 * ÃƒÂ«Ã¢â‚¬Â¹Ã‚Â´ÃƒÂ¬Ã¢â‚¬Â¢Ã¢â‚¬Å¾
	 * ÃƒÂ«Ã‚Â°Ã‹Å“ÃƒÂ­Ã¢â€žÂ¢Ã‹Å“ >>>>>>>
	 * .r108
	 * 
	 * @param list
	 * @return
	 */
	// private List<List> split(List<ContactEntry> list){
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
		logger.info("=====> container size: " + container.size());
		return container;
	}

	public void multipleCreate(List<ContactEntry> contactEntries)
			throws AppException {
		try {
			ContactEntry contactEntry = null;
			String feedurlStr = getFeedUrl(appProperties.getFeedurl());
			ContactFeed batchFeed = null;
			URL feedUrl = new URL(feedurlStr);
			ContactsService service = getContactsService();
			ContactFeed feed = service.getFeed(feedUrl, ContactFeed.class);

			List<List> container = split(contactEntries);
			logger.info("==> container size: " + container.size());

			int batchCnt = 0;
			int batchCnt1 = 0;

			for (int i = 0; i < container.size(); i++) {

				batchFeed = new ContactFeed();

				List<ContactEntry> splittedList = (List) container.get(i); // ÃƒÂ«Ã‚Â¶Ã¢â‚¬Å¾ÃƒÂ«Ã‚Â¦Ã‚Â¬ÃƒÂ«Ã¯Â¿Â½Ã…â€œ
																			// List
																			// ÃƒÂ¬Ã¢â‚¬â€œÃ‚Â»ÃƒÂªÃ‚Â¸Ã‚Â°
				logger.info("==> splittedList size: " + splittedList.size());

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
						logger.info("===> Insert Failed!");
						BatchStatus status = BatchUtils.getBatchStatus(ent);
						logger.info("\t" + batchId + " failed ("
								+ status.getReason() + ") "
								+ status.getContent());
					} else {
						logger.info("===> Inserted Sucessfully!" + "("
								+ batchId + ")");
					}
				}// end of inner for loop

				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					logger.log(Level.SEVERE, e.getMessage(), e);
				}

			}// end of outer for loop
			logger.info("====================================");
			logger.info("Total " + batchCnt + " inserted!!");
			logger.info("====================================");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AppException();
		}
	}

	public void multipleDeleteUserContacts(List<ContactEntry> contactEntries,
			String userEmail) throws AppException {
		if (contactEntries != null && !contactEntries.isEmpty()) {
			try {
				ContactEntry contactEntry = null;
				String feedurlStr = getUserFeedUrl(appProperties.getFeedurl(),
						userEmail);
				ContactFeed batchFeed = null;
				URL feedUrl = new URL(feedurlStr);
				ContactsService service = getContactsService();
				ContactFeed feed = service.getFeed(feedUrl, ContactFeed.class);

				List<List> container = split(contactEntries);
				logger.info("==> container size: " + container.size());

				int batchCnt = 0;
				int batchCnt1 = 0;

				for (int i = 0; i < container.size(); i++) {

					batchFeed = new ContactFeed();

					List<ContactEntry> splittedList = (List) container.get(i); // ÃƒÂ«Ã‚Â¶Ã¢â‚¬Å¾ÃƒÂ«Ã‚Â¦Ã‚Â¬ÃƒÂ«Ã¯Â¿Â½Ã…â€œ
																				// List
																				// ÃƒÂ¬Ã¢â‚¬â€œÃ‚Â»ÃƒÂªÃ‚Â¸Ã‚Â°
					logger.info("==> splittedList size: " + splittedList.size());

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
							logger.info("===> Insert Failed!");
							BatchStatus status = BatchUtils.getBatchStatus(ent);
							logger.info("\t" + batchId + " failed ("
									+ status.getReason() + ") "
									+ status.getContent());
						} else {
							logger.info("===> Inserted Sucessfully!" + "("
									+ batchId + ")");
						}
					}// end of inner for loop
					System.out.println("Deleted batch " + i);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
						logger.log(Level.SEVERE, e.getMessage(), e);
					}

				}// end of outer for loop
				logger.info("====================================");
				logger.info("Total " + batchCnt + " inserted!!");
				logger.info("====================================");
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.SEVERE, e.getMessage(), e);
				throw new AppException();
			}
		}
	}

	/*
	 * public void multipleCreateUserContacts(List<ContactEntry> contactEntries,
	 * String userEmail) throws AppException { try { ContactEntry contactEntry =
	 * null; String feedurlStr = appProperties.getFeedurl() + userEmail +
	 * "/full"; ContactFeed batchFeed = null; URL feedUrl = new URL(feedurlStr);
	 * ContactsService service = getContactsService(); //ContactFeed feed =
	 * service.getFeed(feedUrl, ContactFeed.class);
	 * 
	 * List<List> container = split(contactEntries);
	 * logger.info("==> container size: " + container.size());
	 * 
	 * int batchCnt = 0; int batchCnt1 = 0;
	 * 
	 * for (int i = 0; i < container.size(); i++) {
	 * 
	 * batchFeed = new ContactFeed();
	 * 
	 * List<ContactEntry> splittedList = (List) container.get(i); //
	 * ÃƒÂ«Ã‚�
	 * �¶Ã¢â‚¬Å¾ÃƒÂ«Ã‚Â¦Ã‚Â¬ÃƒÂ«Ã¯Â¿
	 * Â½Ã…â€œ // List //
	 * ÃƒÂ¬Ã¢â‚¬â€œÃ‚Â»ÃƒÂªÃ‚Â¸Ã‚Â°
	 * logger.info("==> splittedList size: " + splittedList.size());
	 * 
	 * for (int j = 0; j < splittedList.size(); j++) { contactEntry =
	 * (ContactEntry) splittedList.get(j); BatchUtils.setBatchId(contactEntry,
	 * String.valueOf(batchCnt++));
	 * BatchUtils.setBatchOperationType(contactEntry,
	 * BatchOperationType.INSERT); batchFeed.getEntries().add(contactEntry);
	 * 
	 * }
	 * 
	 * Link batchLink = feed.getLink(Link.Rel.FEED_BATCH, Link.Type.ATOM);
	 * String url = batchLink.getHref() + "?xoauth_requestor_id=" + userEmail;
	 * Query query = new Query(feedUrl); query.setMaxResults(1); // paging
	 * query.setStartIndex(30000); // paging
	 * query.setStringCustomParameter("orderby", "lastmodified");
	 * query.setStringCustomParameter("sortorder", "descending"); // "ascending"
	 * // or // "descending" query.setStringCustomParameter("showdeleted",
	 * "false"); query.setStringCustomParameter("xoauth_requestor_id",
	 * userEmail);
	 * 
	 * // ContactFeed resultFeed = service.getFeed(feedUrl, //
	 * ContactFeed.class); ContactFeed batchResultFeed = service.query(query,
	 * ContactFeed.class);
	 * 
	 * List<ContactEntry> batches = batchResultFeed.getEntries();
	 * 
	 * for (ContactEntry ent : batches) { String batchId =
	 * BatchUtils.getBatchId(ent); if (!BatchUtils.isSuccess(ent)) {
	 * logger.info("===> Insert Failed!"); BatchStatus status =
	 * BatchUtils.getBatchStatus(ent); logger.info("\t" + batchId + " failed ("
	 * + status.getReason() + ") " + status.getContent()); } else {
	 * logger.info("===> Inserted Sucessfully!" + "(" + batchId + ")"); } }//
	 * end of inner for loop System.out.println("Created batch" + i); try {
	 * Thread.sleep(500); } catch (InterruptedException e) {
	 * e.printStackTrace(); logger.log(Level.SEVERE, e.getMessage(), e); }
	 * 
	 * }// end of outer for loop
	 * logger.info("===================================="); logger.info("Total "
	 * + batchCnt + " inserted!!");
	 * logger.info("===================================="); } catch (Exception
	 * e) { e.printStackTrace(); logger.log(Level.SEVERE, e.getMessage(), e);
	 * throw new AppException(); } }
	 */

	public void multipleCreateUserContacts(List<ContactEntry> contactEntries,
			String userEmail) throws AppException {
		try {
			ContactEntry contactEntry = null;
			String feedurlStr = getUserFeedUrl(appProperties.getFeedurl(),
					userEmail);
			ContactFeed batchFeed = null;
			URL feedUrl = new URL(feedurlStr);
			ContactsService service = getContactsService();
			ContactFeed feed = null;
			for (int i = 0; i < 5; i++) {
				try {
					feed = service.getFeed(feedUrl, ContactFeed.class);
					break;
				} catch (Exception e) {
					Thread.sleep(800);
				}
			}

			List<List> container = split(contactEntries);
			logger.info("==> container size: " + container.size());

			int batchCnt = 0;
			int batchCnt1 = 0;

			for (int i = 0; i < container.size(); i++) {

				batchFeed = new ContactFeed();

				List<ContactEntry> splittedList = (List) container.get(i); // Ã«Â¶â€žÃ«Â¦Â¬Ã«ï¿½Å“
																			// List
																			// Ã¬â€“Â»ÃªÂ¸Â°
				logger.info("==> splittedList size: " + splittedList.size());

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
						logger.info("===> Insert Failed!");
						BatchStatus status = BatchUtils.getBatchStatus(ent);
						logger.info("\t" + batchId + " failed ("
								+ status.getReason() + ") "
								+ status.getContent());
					} else {
						logger.info("===> Inserted Sucessfully!" + "("
								+ batchId + ")");
					}
				}// end of inner for loop
				System.out.println("Created batch" + i);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
					logger.log(Level.SEVERE, e.getMessage(), e);
				}

			}// end of outer for loop
			logger.info("====================================");
			logger.info("Total " + batchCnt + " inserted!!");
			logger.info("====================================");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AppException();
		}
	}

	public ContactEntry create(ContactEntry contact) throws AppException {
		try {
			ContactsService service = getContactsService();
			String feedurl = getFeedUrl(appProperties.getFeedurl());
			URL postUrl = new URL(feedurl);
			return service.insert(postUrl, contact);
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
			throw new AppException();
		}
	}

	public void createUserContact(ContactEntry contact, String userEmail)
			throws AppException {
		try {
			ContactsService service = getContactsService();
			String feedurl = getUserFeedUrl(appProperties.getFeedurl(),
					userEmail);
			URL postUrl = new URL(feedurl);
			service.insert(postUrl, contact);
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
			throw new AppException();
		}
	}

	public String getSharedContactsGroupId(String name){
		String result = null;
		if (!StringUtils.isBlank(name)) {
			try {
				String feedurl = appProperties.getGroupFeedUrl()
						+ CommonWebUtil.getDomain(userEmail) + "/full";
				String scGrpName = name;
				logger.info("scGrpName: " + scGrpName);
				ContactsService service = getContactsService();
				// Collection<ContactGroupEntry> contactGroupEntries = new
				// ArrayList<ContactGroupEntry>();
				URL retrieveUrl = new URL(feedurl);
				// Link nextLink = null;
				Query query = new Query(retrieveUrl);
				query.setMaxResults(100000); // paging
				query.setStartIndex(1);
				query.setStringCustomParameter("showdeleted", "false");
				query.setStringCustomParameter("xoauth_requestor_id", userEmail);

				ContactGroupFeed resultFeed = service.query(query,
						ContactGroupFeed.class);
				Collection<ContactGroupEntry> contactGroupEntries = resultFeed
						.getEntries();
				/*
				 * ContactGroupFeed resultFeed = service.getFeed(new
				 * URL(feedurl), ContactGroupFeed.class);
				 * 
				 * do {
				 * 
				 * ContactGroupFeed resultFeed = service.getFeed(retrieveUrl,
				 * ContactGroupFeed.class);
				 * contactGroupEntries.addAll(resultFeed.getEntries()); nextLink
				 * = resultFeed.getLink(Link.Rel.NEXT, Link.Type.ATOM); if
				 * (nextLink != null) { retrieveUrl = new
				 * URL(nextLink.getHref()); }
				 * 
				 * } while (nextLink != null);
				 */
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
								logger.info("Group Name: " + titleTmp);
								logger.info("Group Id: " + result);
								break;
							}
						}
					}
				}
			} catch (Exception e) {
				// e.printStackTrace();
				// logger.severe("e.getMessage: " + e.getMessage());
				logger.log(Level.SEVERE, e.getMessage(), e);
			
			}
		}
		return result;
	}

	public boolean hasSharedContactsGroup(String name) throws AppException {
		boolean result = false;
		try {
			String feedurl = getFeedUrl(appProperties.getGroupFeedUrl());
			String scGrpName = name;
			logger.info("scGrpName: " + scGrpName);
			ContactsService service = getContactsService();
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
						logger.info("Group Name: " + titleTmp);
						if (titleTmp.equals(scGrpName)) {
							return true;
						}
					}
				}
			} else {
				result = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("e.getMessage: " + e.getMessage());
			throw new AppException();
		}
		return result;
	}

	private String getUserFeedUrl(String url, String email) {
		url = url + email + "/full?xoauth_requestor_id=" + email;

		logger.finer("getUserFeedUrl == " + url);
		return url;
	}

	public ContactGroupEntry create(ContactGroupEntry entry)
			throws AppException {
		try {
			System.out.println("creating group");
			String groupId = getSharedContactsGroupId(entry.getTitle()
					.getPlainText());
			if (StringUtils.isEmpty(groupId)) {
				ContactsService service = getContactsService();
				entry = service.insert(
						new URL(getFeedUrl(appProperties.getGroupFeedUrl())),
						entry);
			} else {
				entry.setId(groupId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "e.getMessage: " + e.getMessage(), e);
			throw new AppException();
		}
		return entry;
	}

	public ContactGroupEntry createGroup(ContactGroupEntry entry,
			String userEmail) throws AppException {
		try {
			ContactsService service = getContactsService();
			// System.out.println("Url:"+appProperties.getGroupFeedUrl()+"agent_noreply@netkiller.com/full?xoauth_requestor_id="
			// + "agent_noreply@netkiller.com");
			// service.insert(new
			// URL(appProperties.getGroupFeedUrl()+"agent_noreply@netkiller.com/full?xoauth_requestor_id="
			// + "agent_noreply@netkiller.com"), entry);

			String groupId = getUserContactsGroupId(entry.getTitle()
					.getPlainText(), userEmail);
			if (StringUtils.isBlank(groupId)) {
				entry = service.insert(
						new URL(getUserFeedUrl(appProperties.getGroupFeedUrl(),
								userEmail)), entry);
			} else {
				entry.setId(groupId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("e.getMessage: " + e.getMessage());
			throw new AppException();
		}
		return entry;
	}

	public String getUserContactsGroupId(String name, String userEmail)
			 {
		String result = null;
		try {
			String feedurl = appProperties.getGroupFeedUrl() + userEmail
					+ "/full";

			String scGrpName = name;
			logger.info("scGrpName: " + scGrpName);
			ContactsService service = getContactsService();

			URL retrieveUrl = new URL(feedurl);
			Query query = new Query(retrieveUrl);
			query.setMaxResults(100000); // paging
			query.setStartIndex(1);
			query.setStringCustomParameter("showdeleted", "false");
			query.setStringCustomParameter("xoauth_requestor_id", userEmail);

			for (int i = 0; i < 5; i++) {
				try {
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
									logger.info("Group Name: " + titleTmp);
									logger.info("Group Id: " + result);
									break;
								}
							}
						}
					}
					break;
				} catch (Exception e) {
					System.out.println("sleeping!!!!!!!!!!!!!!!!!1111");
					Thread.sleep(800);
					
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			// logger.severe("e.getMessage: " + e.getMessage());
			logger.log(Level.SEVERE, e.getMessage(), e);
			//throw new AppException();
			System.out.println("errrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
			System.out.println(e.getMessage());
			System.out.println(e.getCause().getMessage());
		}
		return result;
	}

	// public List<ContactEntry> getContacts(String page, String rows, String
	// sidx, String sord) throws AppException{
	/*
	 * public List<ContactEntry> getContacts(int start) throws AppException{
	 * List<ContactEntry> contactVOs = null; try{
	 * 
	 * ContactsService service = getContactsService();
	 * 
	 * logger.info("start ==> " + start); String feedurl =
	 * getFeedUrl(appProperties.getFeedurl()); URL feedUrl = new URL(feedurl);
	 * // Contacts Query query = new Query(feedUrl);
	 * query.setMaxResults(100000); //paging query.setStartIndex(start);
	 * //paging //query.setStartIndex(1); //paging
	 * //query.setStringCustomParameter("showdeleted","true");
	 * query.setStringCustomParameter("orderby","lastmodified");
	 * query.setStringCustomParameter("sortorder", "descending"); //"ascending"
	 * or "descending" //ContactFeed resultFeed = service.getFeed(feedUrl,
	 * ContactFeed.class); ContactFeed resultFeed = service.query(query,
	 * ContactFeed.class);
	 * 
	 * contactVOs = resultFeed.getEntries(); } catch(Exception e){
	 * e.printStackTrace(); logger.severe(e.getMessage()); throw new
	 * AppException(); } return contactVOs; } //
	 */
	
	public List<ContactEntry> getContacts(int start, int limit, String groupId,
			boolean isUseForSharedContacts, GridRequest gridRequest,String email)
			throws AppException {
		
		
		List<ContactEntry> contactVOs = null;
		try {

			ContactsService service = getContactsService();
			Customer cust = getDomainAdminEmail(CommonWebUtil
					.getDomain(email));
			logger.info("start ==> " + start);
			String feedurl = appProperties.getFeedurl();
			feedurl = feedurl + CommonWebUtil.getDomain(email) + "/full";
			// feedurl = feedurl + userEmail + "/full";
			URL feedUrl = new URL(feedurl); // Contacts
			Query query = new Query(feedUrl);
			if (limit != -1 || start != -1) {
				query.setMaxResults(limit); // paging
				query.setStartIndex(start); // paging
			}
			// query.setStartIndex(1); //paging
			// query.setStringCustomParameter("showdeleted","true");
			query.setStringCustomParameter("orderby", "lastmodified");
			query.setStringCustomParameter("sortorder", "ascending"); // "ascending"
																		// or
																		// "descending"
			query.setStringCustomParameter("showdeleted", "false");
			// query.setStringCustomParameter("q", "saurab");
			query.setStringCustomParameter("xoauth_requestor_id", cust.getAdminEmail());
			if (isUseForSharedContacts) {
				query.setStringCustomParameter("group", groupId);
			}

			// ContactFeed resultFeed = service.getFeed(feedUrl,
			// ContactFeed.class);
			ContactFeed resultFeed = service.query(query, ContactFeed.class);

			contactVOs = resultFeed.getEntries();
			System.out.println("Total size contactsVO" + contactVOs.size());

			if (cust != null && contactVOs.size() != cust.getTotalContacts()) {
				updateTotalContacts(cust.getId(), contactVOs.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// logger.log(Level.SEVERE, e.getMessage(), e);
			// throw new AppException();
			System.out.println("error : " + e.getMessage() );
			if(e.getCause()!=null){
				System.out.println(e.getCause().getMessage());
			}
		}
		return contactVOs;
	}
	
	
	public List<ContactEntry> getContacts(int start, int limit, String groupId,
			boolean isUseForSharedContacts, GridRequest gridRequest)
			throws AppException {
		List<ContactEntry> contactVOs = null;
		try {

			ContactsService service = getContactsService();

			logger.info("start ==> " + start);
			String feedurl = appProperties.getFeedurl();
			feedurl = feedurl + CommonWebUtil.getDomain(userEmail) + "/full";
			// feedurl = feedurl + userEmail + "/full";
			URL feedUrl = new URL(feedurl); // Contacts
			Query query = new Query(feedUrl);
			if (limit != -1 || start != -1) {
				query.setMaxResults(limit); // paging
				query.setStartIndex(start); // paging
			}
			// query.setStartIndex(1); //paging
			// query.setStringCustomParameter("showdeleted","true");
			query.setStringCustomParameter("orderby", "lastmodified");
			query.setStringCustomParameter("sortorder", "descending"); // "ascending"
																		// or
																		// "descending"
			query.setStringCustomParameter("showdeleted", "false");
			// query.setStringCustomParameter("q", "saurab");
			query.setStringCustomParameter("xoauth_requestor_id", userEmail);
			if (isUseForSharedContacts) {
				query.setStringCustomParameter("group", groupId);
			}

			// ContactFeed resultFeed = service.getFeed(feedUrl,
			// ContactFeed.class);
			long startTime = new Date().getTime();
			ContactFeed resultFeed = service.query(query, ContactFeed.class);
			System.out.println("Time taken : " + (new Date().getTime() - startTime)/1000);
			contactVOs = resultFeed.getEntries();
			System.out.println("Total size contactsVO" + contactVOs.size());
			Customer cust = getDomainAdminEmail(CommonWebUtil
					.getDomain(userEmail));
			if (cust != null && contactVOs.size() != cust.getTotalContacts()) {
				updateTotalContacts(cust.getId(), contactVOs.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// logger.log(Level.SEVERE, e.getMessage(), e);
			// throw new AppException();
			System.out.println("error : " + e.getMessage() );
			if(e.getCause()!=null){
				System.out.println(e.getCause().getMessage());
			}
		}
		return contactVOs;
	}

	public ContactEntry getContact(String urlStr) throws AppException {

		ContactEntry entry = null;
		try {
			logger.finer(" ========= " + urlStr);
			System.out.println(" ========= " + urlStr);
			if (urlStr.indexOf("?") != -1) {
				urlStr = urlStr + "&xoauth_requestor_id=" + userEmail;
			} else {
				urlStr = urlStr + "?xoauth_requestor_id=" + userEmail;
			}
			URL url = new URL(urlStr);
			ContactsService service = getContactsService();
			entry = service.getEntry(url, ContactEntry.class);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage() + " ========= " + urlStr, e);
			throw new AppException();
		}
		return entry;
	}

	public ContactEntry update(ContactEntry contact) throws AppException {
		try {
			ContactsService service = getContactsService();
			URL editUrl = new URL(contact.getEditLink().getHref()
					+ "?xoauth_requestor_id=" + userEmail);
			return service.update(editUrl, contact);
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe(e.getMessage());
			throw new AppException();
		}
	}

	public void remove(List<String> ids) throws AppException {

		if (ids != null) {
			try {
				ContactsService service = getContactsService();
				service.setHeader("If-Match", "*");
				ContactFeed feed = service.getFeed(new URL(
						getFeedUrl(appProperties.getFeedurl())),
						ContactFeed.class);
				ContactFeed batchFeed = null;
				ContactEntry contact = null;

				List<List> container = split(ids);
				logger.info("==> container size: " + container.size());

				int batchCnt = 0;

				for (int i = 0; i < container.size(); i++) {

					batchFeed = new ContactFeed();

					List<String> splittedList = (List) container.get(i); // ÃƒÂ«Ã‚Â¶Ã¢â‚¬Å¾ÃƒÂ«Ã‚Â¦Ã‚Â¬ÃƒÂ«Ã¯Â¿Â½Ã…â€œ
																			// List
																			// ÃƒÂ¬Ã¢â‚¬â€œÃ‚Â»ÃƒÂªÃ‚Â¸Ã‚Â°
					logger.info("==> splittedList size: " + splittedList.size());

					for (int j = 0; j < splittedList.size(); j++) {
						contact = new ContactEntry();
						String id =(String) splittedList.get(j);
						contact.setId(id);
						removeContactInfo(id);
						decrementContactCount(CommonWebUtil.getDomain(userEmail));
						BatchUtils.setBatchId(contact,
								String.valueOf(batchCnt++));
						BatchUtils.setBatchOperationType(contact,
								BatchOperationType.DELETE);
						batchFeed.getEntries().add(contact);
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
							logger.info("===> Delete Failed!");
							BatchStatus status = BatchUtils.getBatchStatus(ent);
							logger.info("\t" + batchId + " failed ("
									+ status.getReason() + ") "
									+ status.getContent());
						} else {
							logger.info("===> Deleted Sucessfully!" + " ("
									+ batchId + ")");
						}
					}
				}
				logger.info("====================================");
				logger.info("Total " + batchCnt + " deleted!!");
				logger.info("====================================");
			} catch (Exception e) {
				e.printStackTrace();
				logger.log(Level.SEVERE, e.getMessage(), e);
				throw new AppException();
			}
		}
	}

	private String getFeedUrl(String url) {
		url = url + CommonWebUtil.getDomain(userEmail)
				+ "/full?xoauth_requestor_id=" + userEmail;
		// url = url + userEmail + "/full?xoauth_requestor_id=" + userEmail;
		System.out.println("url == " + url);
		logger.finer("getFeedUrl == " + url);
		return url;
	}

	private Customer getCustomer(Entity entity) {
		Customer cust = new Customer();
		cust.setDomain(CommonUtil.getNotNullValue(entity.getProperty("domain")));
		cust.setAccountType(CommonUtil.getNotNullValue(entity
				.getProperty("accountType")));
		cust.setAdminEmail(CommonUtil.getNotNullValue(entity
				.getProperty("email")));
		Date registeredDate = entity.getProperty("registeredData") == null ? null
				: (Date) entity.getProperty("registeredData");
		Date upgradedDate = entity.getProperty("upgradedDate") == null ? null
				: (Date) entity.getProperty("upgradedDate");
		cust.setRegisteredDate(registeredDate);
		cust.setUpgradedDate(upgradedDate);
		cust.setTotalContacts(entity.getProperty("totalContacts") == null ? 0
				: Integer.parseInt(entity.getProperty("totalContacts")
						.toString()));
		cust.setTotalUsers(entity.getProperty("totalUsers") == null ? 0
				: Integer.parseInt(entity.getProperty("totalUsers").toString()));
		cust.setSyncedUsers(entity.getProperty("syncedUsers") == null ? 0
				: Integer
						.parseInt(entity.getProperty("syncedUsers").toString()));
		cust.setNscUsers(entity.getProperty("nscUsers") == null ? 0 : Integer
				.parseInt(entity.getProperty("nscUsers").toString()));
		cust.setId(entity.getKey().getId());
		return cust;
	}

	@Override
	public void download(List<ContactEntry> contacts,
			ServletOutputStream outputStream) throws AppException {

		StringBuffer csvData = new StringBuffer("");

		WritableFont times16font = new WritableFont(WritableFont.TIMES, 10,
				WritableFont.BOLD, true);
		WritableCellFormat times16format = new WritableCellFormat(times16font);
		List<Cell> cellList = new ArrayList<Cell>();

		cellList.add(new Label(0, 0, "Full Name", times16format));
		cellList.add(new Label(1, 0, "First Name", times16format));
		cellList.add(new Label(2, 0, "Last Name", times16format));
		cellList.add(new Label(3, 0, "Company", times16format));
		cellList.add(new Label(4, 0, "Job Title", times16format));
		cellList.add(new Label(5, 0, "Department", times16format));
		cellList.add(new Label(6, 0, "E-Mail Address", times16format));
		cellList.add(new Label(7, 0, "E-mail 2 Address", times16format));
		cellList.add(new Label(8, 0, "E-mail 3 Address", times16format));
		cellList.add(new Label(9, 0, "Business Phone", times16format));
		cellList.add(new Label(10, 0, "Home Phone", times16format));
		cellList.add(new Label(11, 0, "Mobile Phone", times16format));
		cellList.add(new Label(12, 0, "Business Address", times16format));
		cellList.add(new Label(13, 0, "Home Address", times16format));
		cellList.add(new Label(14, 0, "Other Address", times16format));
		cellList.add(new Label(15, 0, "Notes", times16format));

		int rowNumber = 1;

		for (ContactEntry entry : contacts) {

			SharedContactsUtil sharedContactsUtil = SharedContactsUtil
					.getInstance();
			cellList.add(new Label(0, rowNumber, sharedContactsUtil
					.getFullName(entry), times16format));
			cellList.add(new Label(1, rowNumber, sharedContactsUtil
					.getGivenName(entry), times16format));
			cellList.add(new Label(2, rowNumber, sharedContactsUtil
					.getFamilyName(entry), times16format));
			cellList.add(new Label(3, rowNumber, sharedContactsUtil
					.getOrganization(entry, "work"), times16format));
			cellList.add(new Label(4, rowNumber, sharedContactsUtil
					.getOrganizationTitle(entry, "work"), times16format));
			cellList.add(new Label(5, rowNumber, sharedContactsUtil
					.getOrganizationDept(entry, "work"), times16format));
			String workEmailStr = "";
			Email workEmail = sharedContactsUtil.getEmailObj(entry, "work");
			if (workEmail != null) {
				workEmailStr = workEmail.getAddress();
			}
			cellList.add(new Label(6, rowNumber, workEmailStr, times16format));

			String homeEmailStr = "";
			Email homeEmail = sharedContactsUtil.getEmailObj(entry, "home");
			if (homeEmail != null) {
				homeEmailStr = homeEmail.getAddress();
			}
			cellList.add(new Label(7, rowNumber, homeEmailStr, times16format));

			String otherEmailStr = "";
			Email otherEmail = sharedContactsUtil.getEmailObj(entry, "other");
			if (otherEmail != null) {
				otherEmailStr = workEmail.getAddress();
			}
			cellList.add(new Label(8, rowNumber, otherEmailStr, times16format));

			String workPhoneNumberStr = "";
			PhoneNumber workPhoneNumber = sharedContactsUtil.getPhoneNumberObj(
					entry, "work");
			if (workPhoneNumber != null) {
				workPhoneNumberStr = workPhoneNumber.getPhoneNumber();
			}
			cellList.add(new Label(9, rowNumber, workPhoneNumberStr,
					times16format));

			String homePhoneNumberStr = "";
			PhoneNumber homePhoneNumber = sharedContactsUtil.getPhoneNumberObj(
					entry, "home");
			if (homePhoneNumber != null) {
				homePhoneNumberStr = homePhoneNumber.getPhoneNumber();
			}
			cellList.add(new Label(10, rowNumber, homePhoneNumberStr,
					times16format));

			String mobilePhoneNumberStr = "";
			PhoneNumber mobilePhoneNumber = sharedContactsUtil
					.getPhoneNumberObj(entry, "mobile");
			if (mobilePhoneNumber != null) {
				mobilePhoneNumberStr = mobilePhoneNumber.getPhoneNumber();
			}
			cellList.add(new Label(11, rowNumber, mobilePhoneNumberStr,
					times16format));

			String workAddressStr = "";
			StructuredPostalAddress address = sharedContactsUtil
					.getStructuredPostalAddress(entry,
							StaticProperties.WORK_REL);
			if (address != null) {
				FormattedAddress formattedAddress = address
						.getFormattedAddress();
				logger.info("formattedAddress is null? "
						+ (formattedAddress == null));
				if (formattedAddress != null) {
					workAddressStr = formattedAddress.getValue();
				}
			}
			cellList.add(new Label(12, rowNumber, workAddressStr, times16format));

			String homeAddressStr = "";
			StructuredPostalAddress homeAddress = sharedContactsUtil
					.getStructuredPostalAddress(entry,
							StaticProperties.HOME_REL);
			if (homeAddress != null) {
				FormattedAddress formattedAddress = homeAddress
						.getFormattedAddress();
				logger.info("formattedAddress is null? "
						+ (formattedAddress == null));
				if (formattedAddress != null) {
					homeAddressStr = formattedAddress.getValue();
				}
			}
			cellList.add(new Label(13, rowNumber, homeAddressStr, times16format));

			String otherAddressStr = "";
			StructuredPostalAddress otherAddress = sharedContactsUtil
					.getStructuredPostalAddress(entry,
							StaticProperties.OTHER_REL);
			if (otherAddress != null) {
				FormattedAddress formattedAddress = otherAddress
						.getFormattedAddress();
				logger.info("formattedAddress is null? "
						+ (formattedAddress == null));
				if (formattedAddress != null) {
					otherAddressStr = formattedAddress.getValue();
				}
			}
			cellList.add(new Label(14, rowNumber, otherAddressStr,
					times16format));
			cellList.add(new Label(15, rowNumber, sharedContactsUtil
					.getNotes(entry), times16format));
			rowNumber++;

		}
		int rowNum = 0;

		for (Cell cell : cellList) {
			if (rowNum != cell.getRow()) {
				rowNum = cell.getRow();
				csvData.append('\n');

			}
			csvData.append('"');
			csvData.append(cell.getContents());
			csvData.append('"');
			csvData.append(',');
		}

		try {
			byte[] byteArray = csvData.toString().getBytes("UTF-16");

			try {
				byteArray = UnicodeUtils.convert(byteArray, "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}

			outputStream.write(byteArray);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getAllDomainUsers(String domain) {
		List<String> users = new ArrayList<String>();
		com.google.gdata.client.appsforyourdomain.UserService guserService = new com.google.gdata.client.appsforyourdomain.UserService(
				"ykko-test");
		String consumerKey = appProperties.getConsumerKey();
		String consumerSecret = appProperties.getConsumerKeySecret();
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
					// System.out.println("retrieveUrl" + retrieveUrl);
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
				// System.out.println("$$$$$$$$$$$$$$$$");
				for (UserEntry genericEntry : genericFeed.getEntries()) {
					// System.out.println(genericEntry.getLogin().getUserName());
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
		String consumerKey = appProperties.getConsumerKey();
		String consumerSecret = appProperties.getConsumerKeySecret();
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
			Collection<UserEntry> userEntries = new ArrayList<UserEntry>();

			do {
				try {
					// System.out.println("retrieveUrl" + retrieveUrl);
					genericFeed = guserService.getFeed(retrieveUrl,
							UserFeed.class);

					// genericFeed.getEntries().addAll(genericFeed.getEntries());
					userEntries.addAll(genericFeed.getEntries());
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

			if (userEntries != null && !userEntries.isEmpty()) {

				for (UserEntry genericEntry : userEntries) {
					/*
					 * System.out.println(genericEntry.getLogin().getUserName());
					 * 
					 * System.out.println("USer:" +
					 * genericEntry.getLogin().getUserName());
					 */

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
		List<String> restrtictedUsers = getAllUserNamesWithNoPermissions(domain);
		if (restrtictedUsers != null) {
			allDomainUsers.removeAll(restrtictedUsers);
		}
		return allDomainUsers;
	}

	public void setGroupName(String domainName, String groupName) {
		Entity entity = new Entity("DomainGroup");
		entity.setProperty("domainName", domainName);
		entity.setProperty("groupName", groupName);
		datastore.put(entity);
	}
	
	public void updateContactCount(String domainName, Integer count) {
		Entity entity  = getDomainGroupEntity(domainName);
		if (entity != null ){
			entity.setProperty("count", count);
			datastore.put(entity);
		}
			
	}
	
	public Integer getContactCount(String domainName) {
		Integer count = 0;
		Entity entity = getDomainGroupEntity(domainName);

		if (entity != null && entity.getProperty("count") != null) {
			count = ((Long) entity.getProperty("count")).intValue();
		}

		return count;
	}

	private Entity getDomainGroupEntity(String domainName) {
		Entity entity = null;
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"DomainGroup");
		query.addFilter("domainName",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				domainName);

		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> groupNames = preparedQuery.asList(FetchOptions.Builder
				.withDefaults());
		if (groupNames != null && !groupNames.isEmpty()) {
			entity = groupNames.get(0);
		}
		return entity;
	}
	public String getGroupName(String domainName) {
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"DomainGroup");
		query.addFilter("domainName",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				domainName);

		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> groupNames = preparedQuery.asList(FetchOptions.Builder
				.withDefaults());
		String groupName = null;
		if (groupNames != null && !groupNames.isEmpty())
			groupName = (String) groupNames.get(0).getProperty("groupName");
		return groupName;
	}

	private void createUserPermission(String userId, String domain,
			PermissionType permissionType) {
		Entity entity = new Entity("UserPermission");
		entity.setProperty("userId", userId);
		entity.setProperty("permissionType", permissionType.toString());
		entity.setProperty("domain", domain);

		datastore.put(entity);
	}

	@Override
	public void assignUpdatePermissions(List<String> usersWithUpdatePermission,
			String domain) {
		for (String user : usersWithUpdatePermission) {
			createUserPermission(user, domain,
					com.netkiller.security.acl.PermissionType.WRITE);
		}

	}

	@Override
	public void assignNoPermissions(List<String> usersWithNoPermissions,
			String domain) {
		for (String user : usersWithNoPermissions) {
			createUserPermission(user, domain,
					com.netkiller.security.acl.PermissionType.NONE);
		}
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

	@Override
	public List<String> getAllUserNamesWithWritePermissions(String domain) {
		List<Entity> userWithWritePermissions = getAllUserWithWritePermissions(domain);
		List<String> users = new ArrayList<String>();
		;
		for (Entity userPermission : userWithWritePermissions) {
			users.add(getUserPermission(userPermission).getUserID());
		}
		return users;
	}

	@Override
	public List<String> getAllUserNamesWithNoPermissions(String domain) {
		List<Entity> userWithWritePermissions = getAllUserWithNoPermissions(domain);
		List<String> users = new ArrayList<String>();
		;
		for (Entity userPermission : userWithWritePermissions) {
			users.add(getUserPermission(userPermission).getUserID());
		}
		return users;
	}

	public List<Entity> getAllUserPermissions(String domain) {
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"UserPermission");
		query.addFilter("domain",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				domain);

		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> userPermissions = preparedQuery
				.asList(FetchOptions.Builder.withDefaults());
		return userPermissions;
	}

	@Override
	public List<String> getAllUserWithReadPermissions(String domain) {
		List<String> allDomainUsers = getAllDomainUsers(domain);
		List<Entity> usersWithWritePermission = getAllUserWithWritePermissions(domain);
		for (Entity user : usersWithWritePermission) {
			allDomainUsers.remove(getUserPermission(user).getUserID());

		}
		List<Entity> usersWithNoPermission = getAllUserWithNoPermissions(domain);
		for (Entity user : usersWithNoPermission) {
			allDomainUsers.remove(getUserPermission(user).getUserID());

		}

		return allDomainUsers;
	}

	private UserPermission getUserPermission(Entity entity) {
		UserPermission userPermission = new UserPermission();
		userPermission.setDomain(CommonUtil.getNotNullValue(entity
				.getProperty("domain")));
		userPermission.setKey(entity.getKey());
		userPermission.setPermissionType(PermissionType
				.getPermissionType(CommonUtil.getNotNullValue(entity
						.getProperty("permissionType"))));
		userPermission.setUserID(CommonUtil.getNotNullValue(entity
				.getProperty("userId")));
		return userPermission;
	}

	@Override
	public void removeUpdatePermissions(List<String> usersToBeRemoved,
			String domain) {
		List<Entity> users = getAllUserPermissions(domain);
		List<Key> keyList = new ArrayList<Key>();
		for (Entity user : users) {
			if (usersToBeRemoved.contains(user.getProperty("userId"))) {
				keyList.add(user.getKey());
			}
		}
		datastore.delete(keyList);

	}

	public void removeNoPermissions(List<String> noPermissionUsersToBeRemoved,
			String domain) {
		List<Entity> users = getAllUserPermissions(domain);
		List<Key> keyList = new ArrayList<Key>();
		for (Entity user : users) {
			if (noPermissionUsersToBeRemoved.contains(user
					.getProperty("userId"))) {
				keyList.add(user.getKey());
			}
		}
		datastore.delete(keyList);
	}

	@Override
	public DomainSettings getDomainSettings(String domain) {
		// TODO Auto-generated method stub

		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"DomainSettings");
		query.addFilter("domain",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				domain);

		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> domainSettings = preparedQuery.asList(FetchOptions.Builder
				.withDefaults());
		if (domainSettings != null && !domainSettings.isEmpty())

			return getDomainSettings(domainSettings.get(0));
		else {
			return createDomainSettings(domain);
		}

	}

	private DomainSettings createDomainSettings(String domain) {
		Entity entity = new Entity("DomainSettings");
		entity.setProperty("domain", domain);
		entity.setProperty("onlyAdminPermitted", false);
		entity.setProperty("allUserPermitted", false);

		try {
			return getDomainSettings(datastore.get(datastore.put(entity)));
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<ContactInfo> getAllDomainContacts(String domain,Integer totalLimit,String sidx, String sord){
		List<ContactInfo> contacts = new ArrayList<ContactInfo>();
		int start = 0;
		int limit = totalLimit<900?totalLimit:900;
		while(start<totalLimit){			
			List<ContactInfo> contacts1 = getDomainContacts(domain, limit, start, sidx, sord);
			if(!contacts1.isEmpty()){
				contacts.addAll(contacts1);
			}else{
				break;
			}
			start += limit;
		}
		return contacts;
	}
	
	public List<ContactInfo> getDomainContacts(String domain, Integer limit, Integer offset,String sidx, String sord) {
		List<ContactInfo> contacts = new ArrayList<ContactInfo>();

		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				ContactInfo.class.getSimpleName());
		query.addFilter("domain",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				domain);
		SortDirection direction = SortDirection.ASCENDING;
		if(sord.equalsIgnoreCase("desc")){
			direction = SortDirection.DESCENDING;
		}
		query.addSort(sidx, direction);
		PreparedQuery preparedQuery = datastore.prepare(query);
		
		List<Entity> fetchedContacts = preparedQuery.asList(FetchOptions.Builder
				.withLimit(limit).offset(offset));
		if (fetchedContacts != null && !fetchedContacts.isEmpty()){
			for(Entity entity : fetchedContacts){
				contacts.add(getContactInfo(entity));
			}
		}
return contacts;
	}
	
	public ContactInfo createContactInfo(ContactInfo  contactInfo) {
		Key key = KeyFactory.createKey(ContactInfo.class.getSimpleName(), contactInfo.getId());
		Entity entity = new Entity(key);
		if(StringUtils.isBlank(contactInfo.getDomain())){
			contactInfo.setDomain(CommonWebUtil
					.getDomain(UserServiceFactory.getUserService().getCurrentUser().getEmail()));
		}
		entity.setProperty("domain", contactInfo.getDomain());
		entity.setProperty("id", contactInfo.getId());
		
		entity.setProperty("fullname",contactInfo.getFullname());
		entity.setProperty("givenname",contactInfo.getGivenname());
		entity.setProperty("familyname",contactInfo.getFamilyname());
		entity.setProperty("companyname",contactInfo.getCompanyname());
		entity.setProperty("companydept",contactInfo.getCompanydept());
		entity.setProperty("companytitle",contactInfo.getCompanytitle());
		entity.setProperty("workemail",contactInfo.getWorkemail());
		entity.setProperty("homeemail",contactInfo.getHomeemail());
		entity.setProperty("otheremail",contactInfo.getOtheremail());
		entity.setProperty("workphone",contactInfo.getWorkphone());
		entity.setProperty("homephone",contactInfo.getHomephone());
		entity.setProperty("mobilephone",contactInfo.getMobilephone());
		entity.setProperty("workaddress",contactInfo.getWorkaddress());
		entity.setProperty("homeaddress",contactInfo.getHomeaddress());
		entity.setProperty("otheraddress",contactInfo.getOtheraddress());
		entity.setProperty("notes",contactInfo.getNotes());
		entity.setProperty("modifiedDate",new Date());

		try {
			return getContactInfo(datastore.get(datastore.put(entity)));
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public ContactInfo getContactInfo(Entity entity) {
		ContactInfo contactInfo = new ContactInfo();
		contactInfo.setDomain(CommonUtil.getNotNullValue(entity
				.getProperty("domain")));
		contactInfo.setId(CommonUtil.getNotNullValue(entity
				.getProperty("id")));
		contactInfo.setKey(entity.getKey());

		contactInfo.setCompanydept(CommonUtil.getNotNullValue(entity.getProperty("companydept")));
		contactInfo.setCompanyname(CommonUtil.getNotNullValue(entity.getProperty("companyname")));
		contactInfo.setCompanytitle(CommonUtil.getNotNullValue(entity.getProperty("companytitle")));
		contactInfo.setFamilyname(CommonUtil.getNotNullValue(entity.getProperty("familyname")));
		contactInfo.setFullname(CommonUtil.getNotNullValue(entity.getProperty("fullname")));
		contactInfo.setGivenname(CommonUtil.getNotNullValue(entity.getProperty("givenname")));
		contactInfo.setHomeaddress(CommonUtil.getNotNullValue(entity.getProperty("homeaddress")));
		contactInfo.setHomeemail(CommonUtil.getNotNullValue(entity.getProperty("homeemail")));
		contactInfo.setHomephone(CommonUtil.getNotNullValue(entity.getProperty("homephone")));
		contactInfo.setMobilephone(CommonUtil.getNotNullValue(entity.getProperty("mobilephone")));
		contactInfo.setNotes(CommonUtil.getNotNullValue(entity.getProperty("notes")));
		contactInfo.setOtheraddress(CommonUtil.getNotNullValue(entity.getProperty("otheraddress")));
		contactInfo.setOtheremail(CommonUtil.getNotNullValue(entity.getProperty("otheremail")));
		contactInfo.setWorkaddress(CommonUtil.getNotNullValue(entity.getProperty("workaddress")));
		contactInfo.setWorkemail(CommonUtil.getNotNullValue(entity.getProperty("workemail")));
		contactInfo.setWorkphone(CommonUtil.getNotNullValue(entity.getProperty("workphone")));
		
		return contactInfo;
	}
	
	private DomainSettings getDomainSettings(Entity entity) {
		DomainSettings domainSettings = new DomainSettings();
		domainSettings.setDomain(CommonUtil.getNotNullValue(entity
				.getProperty("domain")));
		domainSettings.setKey(entity.getKey());
		domainSettings.setOnlyAdminPermitted((Boolean) entity
				.getProperty("onlyAdminPermitted"));
		domainSettings.setAllUserPermitted((Boolean) entity
				.getProperty("allUserPermitted"));
		domainSettings.setAllUserBlobKey(CommonUtil.getNotNullValue(entity
				.getProperty("allUserBlobKey")));
		domainSettings.setNscUserBlobKey(CommonUtil.getNotNullValue(entity
				.getProperty("nscUserBlobKey")));
		domainSettings.setSyncUserBlobKey(CommonUtil.getNotNullValue(entity
				.getProperty("syncUserBlobKey")));
		return domainSettings;
	}

	@Override
	public DomainSettings updateDomainSettings(DomainSettings domainSettings) {
		Entity currentSettings = null;
		try {
			currentSettings = datastore.get(domainSettings.getKey());
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		currentSettings.setProperty("domain", domainSettings.getDomain());
		currentSettings.setProperty("onlyAdminPermitted",
				domainSettings.isOnlyAdminPermitted());
		currentSettings.setProperty("allUserPermitted",
				domainSettings.isAllUserPermitted());
		currentSettings.setProperty("syncUserBlobKey",
				domainSettings.getSyncUserBlobKey());
		currentSettings.setProperty("allUserBlobKey",
				domainSettings.getAllUserBlobKey());
		currentSettings.setProperty("nscUserBlobKey",
				domainSettings.getNscUserBlobKey());

		try {
			return getDomainSettings(datastore.get(datastore
					.put(currentSettings)));
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public UserPermission getUserPermission(String userEmail) {
		UserPermission userPermission = null;
		String domain = CommonWebUtil.getDomain(userEmail);
		String userId = CommonWebUtil.getUserId(userEmail);
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"UserPermission");

		query.addFilter("domain",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				domain);
		query.addFilter("userId",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				userId);
		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> userPermissions = preparedQuery
				.asList(FetchOptions.Builder.withDefaults());
		if (userPermissions != null && !userPermissions.isEmpty()) {
			userPermission = getUserPermission(userPermissions.get(0));
		}
		return userPermission;

	}

	@Override
	public boolean isUserPermitted(String userEmail) {
		boolean isPermitted = false;
		String domain = CommonWebUtil.getDomain(userEmail);
		String userId = CommonWebUtil.getUserId(userEmail);
		DomainSettings currentSettings = getDomainSettings(domain);
		if (currentSettings.isAllUserPermitted()) {
			isPermitted = true;
		} else {
			UserPermission userPermission = getUserPermission(userEmail);
			if (userPermission != null
					&& userPermission.getPermissionType().equals(
							PermissionType.WRITE)) {
				isPermitted = true;
			}
		}
		if (currentSettings.isOnlyAdminPermitted()) {
			return false;
		}
		return isPermitted;

	}

	@Override
	public boolean isUserAuthorized(String userEmail) {
		boolean isPermitted = true;
		String domain = CommonWebUtil.getDomain(userEmail);
		String userId = CommonWebUtil.getUserId(userEmail);

		UserPermission userPermission = getUserPermission(userEmail);
		if (userPermission != null
				&& userPermission.getPermissionType().equals(
						PermissionType.NONE)) {
			isPermitted = false;
		}

		return isPermitted;

	}

	/*
	 * @Override public void syncUserContacts(String userEmail,
	 * List<ContactEntry> entries) { String groupName =
	 * getGroupName(CommonWebUtil.getDomain(userEmail)); try { String groupId =
	 * getUserContactsGroupId(groupName, userEmail); while (groupId == null ||
	 * groupId.equals("")) { ContactGroupEntry group = new ContactGroupEntry();
	 * group.setSummary(new PlainTextConstruct(groupName)); group.setTitle(new
	 * PlainTextConstruct(groupName)); createGroup(group, userEmail);
	 * 
	 * groupId = getUserContactsGroupId(groupName, userEmail); }
	 * List<ContactEntry> contacts = null;
	 * 
	 * contacts = getUserContacts(1, 30000, groupId, userEmail);
	 * if(contacts!=null){ System.out.println("Fteched " + contacts.size() +
	 * " to be deleted");
	 * 
	 * for(ContactEntry ce : contacts){ ce.delete(); } int currentSize =
	 * contacts.size(); List<List> container = split(contacts); for (int i = 0;
	 * i < container.size(); i++) { multipleDeleteUserContacts(container.get(i),
	 * userEmail); int retry = 0; if (currentSize == getUserContacts(1, 30000,
	 * groupId, userEmail) .size() && currentSize != 0 & retry <= 5) { i--;
	 * retry++; } else { if (retry > 5) { continue; } if (currentSize - 100 >=
	 * 0) { currentSize = currentSize - 100; } else currentSize = 0;
	 * 
	 * }
	 * 
	 * } System.out.println(" Successfully deleted");
	 * System.out.println("GroupId is" + groupId); if(entries!=null){ for
	 * (ContactEntry entry : entries) { GroupMembershipInfo gmInfo = new
	 * GroupMembershipInfo(); // added gmInfo.setHref(groupId); // added
	 * entry.getGroupMembershipInfos().remove(0);
	 * entry.addGroupMembershipInfo(gmInfo); createUserContact(entry,
	 * userEmail); } }
	 * 
	 * List<List> container1 = split(entries); for (int i = 0; i <
	 * container1.size(); i++) { System.out.println("Creating batch" + i);
	 * multipleCreateUserContacts(container1.get(i), userEmail); int retry = 0;
	 * if (currentSize == getUserContacts(1, 30000, groupId, userEmail) .size()
	 * && currentSize != contacts.size() & retry <= 5) { i--; retry++;
	 * System.out.println("will retry for batch" + i); } else { if (retry > 5) {
	 * continue; } if (currentSize + 100 < contacts.size()) { currentSize =
	 * currentSize + 100; } else currentSize = contacts.size();
	 * 
	 * }} }
	 * 
	 * } catch (AppException e) { System.out.println("Exception caught"); }
	 * catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } catch (ServiceException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } }
	 */

	/*@Override
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
			if (entries != null) {
				for (ContactEntry entry : entries) {
					GroupMembershipInfo gmInfo = new GroupMembershipInfo(); // added
					gmInfo.setHref(groupId); // added
					entry.getGroupMembershipInfos().remove(0);
					entry.addGroupMembershipInfo(gmInfo);
					String myContactsGroupId = getMyContactsGroupId(userEmail);
					GroupMembershipInfo myContactsGmInfo = new GroupMembershipInfo(); // added
					myContactsGmInfo.setHref(myContactsGroupId); // added
					entry.addGroupMembershipInfo(myContactsGmInfo);

				}
				List<List> container1 = split(entries);

				for (int i = 0; i < container1.size(); i++) {
					System.out.println("Creating batch" + i);
					multipleCreateUserContacts(container1.get(i), userEmail);
					int retry = 0;
					if (currentSize == getUserContacts(1, 30000, groupId,
							userEmail).size()
							&& currentSize != contacts.size() & retry <= 5) {
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
			}

		} catch (AppException e) {
			System.out.println("Exception caught");
		}
	}*/

	@Override
	public void syncUserContacts(String userEmail, List<ContactEntry> entries) {
		String groupName = getGroupName(CommonWebUtil.getDomain(userEmail));
		try {
			System.out.println("00000000000000000 start sync");
			String groupId = getUserContactsGroupId(groupName, userEmail);
			int counter =0;
			while (groupId == null || groupId.equals("")) {
				ContactGroupEntry group = new ContactGroupEntry();
				group.setSummary(new PlainTextConstruct(groupName));
				group.setTitle(new PlainTextConstruct(groupName));
				createGroup(group, userEmail);
				try {
					Thread.sleep(5000);
				} catch (Exception e) {
					// TODO: handle exception
				}
				groupId = getUserContactsGroupId(groupName, userEmail);
				System.out.println(++counter + "id : " + groupId);
			}
			System.out.println("00000000000000000 gid" + groupId);
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
			if (entries!=null) {
				GroupMembershipInfo gmInfo = new GroupMembershipInfo(); // added
				gmInfo.setHref(groupId); // added
				for (ContactEntry entry : entries) {					
					entry.getGroupMembershipInfos().remove(0);
					entry.addGroupMembershipInfo(gmInfo);
					String myContactsGroupId = getMyContactsGroupId(userEmail);
					GroupMembershipInfo myContactsGmInfo = new GroupMembershipInfo(); // added
					myContactsGmInfo.setHref(myContactsGroupId); // added
					entry.addGroupMembershipInfo(myContactsGmInfo);
				}
				System.out.println("size77777777777777777:" + entries.size());
				List<List> container1 = split(entries);
				int retry = 0;
				for (int i = 0; i < container1.size(); i++) {
					System.out.println("Creating batch" + i);
					multipleCreateUserContacts(container1.get(i), userEmail);
					
					/*int userContactsSize = getUserContacts(1, 30000, groupId,
							userEmail).size();
					if (currentSize == userContactsSize
							&& currentSize != entries.size() && retry <= 5) {
						i--;
						retry++;
						System.out.println("will retry for batch" + i + "\t currentSize : " +
								currentSize +"\t userContactsSize : " + userContactsSize + "\t retry : " + retry + "\t entries(): " +entries.size() );
					} else {
						if (retry > 5) {
							retry = 0;
							continue;
						}
						if (currentSize + 100 < entries.size()) {
							currentSize = currentSize + 100;
						} else
							currentSize = entries.size();

					}*/
				}
			}

		} catch (AppException e) {
System.out.println("Exception caught");
		}
	}
	
	private void removeGroup(String groupName, String userEmail) {
		String result = null;
		try {
			String feedurl = getUserFeedUrl(appProperties.getGroupFeedUrl(),
					userEmail);
			String scGrpName = groupName;
			logger.info("scGrpName: " + scGrpName);
			ContactsService service = getContactsService();
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
							groupEntry.delete();
							logger.info("Group Name: " + titleTmp);
							logger.info("Group Id: " + result);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("e.getMessage: " + e.getMessage());

		}

	}

	@Override
	public void removeDuplicateGroups(String groupName, String userEmail) {
		String result = null;
		try {
			String feedurl = appProperties.getGroupFeedUrl()
					+ userEmail + "/full";
			String scGrpName = groupName;
			logger.info("scGrpName: " + scGrpName);
			ContactsService service = getContactsService();
			// Collection<ContactGroupEntry> contactGroupEntries = new
			// ArrayList<ContactGroupEntry>();
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
			/*
			 * Link nextLink = null;
			 * 
			 * do {
			 * 
			 * ContactGroupFeed resultFeed = service.getFeed(retrieveUrl,
			 * ContactGroupFeed.class);
			 * contactGroupEntries.addAll(resultFeed.getEntries()); nextLink =
			 * resultFeed.getLink(Link.Rel.NEXT, Link.Type.ATOM); if (nextLink
			 * != null) { retrieveUrl = new URL(nextLink.getHref()); }
			 * 
			 * } while (nextLink != null);
			 */

			if (!contactGroupEntries.isEmpty()) {
				String titleTmp = null;
				TextConstruct tc = null;
				int count = 0;
				for (ContactGroupEntry groupEntry : contactGroupEntries) {
					tc = groupEntry.getTitle();
					/*
					 * To delete all uncomment this, and comment lines following
					 * this block try { //groupEntry.delete();
					 * System.out.println("deleting " + tc.getPlainText()); }
					 * catch (Exception e) {
					 * System.out.println(tc.getPlainText() + "delete failed");
					 * }
					 */

					if (tc != null) {
						titleTmp = tc.getPlainText();
						// System.out.println("Contacts group Name:" +
						// titleTmp);
						// logger.info("Id: " + groupEntry.getId());
						if (titleTmp.equals(scGrpName)) {
							if (count != 0) {
								groupEntry.delete();
								logger.info("Deleted Group Name: " + titleTmp);
								// logger.info("Deleted Group Id: " + result);
							}
							count++;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("e.getMessage: " + e.getMessage());

		}

	}

	public List<ContactEntry> getUserContacts(int start, int limit,
			String groupId, String userEmail) throws AppException {
		List<ContactEntry> contactVOs = null;
		try {

			ContactsService service = getContactsService();

			logger.info("start ==> " + start);

			String feedurl = appProperties.getFeedurl();
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

	public void createUserSync(String userEmail, String domain, String date) {
		Entity entity = new Entity("UserSync");
		entity.setProperty("userEmail", userEmail);
		entity.setProperty("domain", domain);
		entity.setProperty("date", date);
		entity.setProperty("noOfSyncs", 1);

		datastore.put(entity);
	}

	public UserSync getUserSync(String userEmail) {
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"UserSync");
		query.addFilter("userEmail",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				userEmail);
		// query.addFilter("date",
		// com.google.appengine.api.datastore.Query.FilterOperator.EQUAL, date);

		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> userSyncs = preparedQuery.asList(FetchOptions.Builder
				.withDefaults());
		UserSync userSync = null;
		if (userSyncs != null && !userSyncs.isEmpty())
			userSync = getUserSync(userSyncs.get(0));
		return userSync;

	}

	public UserSync getUserSync(String userEmail, String date) {
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

	}

	private UserSync getUserSync(Entity entity) {
		UserSync userSync = new UserSync();
		userSync.setDomain((String) entity.getProperty("domain"));
		userSync.setDate((String) entity.getProperty("date"));
		userSync.setUserEmail((String) entity.getProperty("userEmail"));
		userSync.setKey(entity.getKey());
		Long noOfSyncs = (Long) entity.getProperty("noOfSyncs");
		if (noOfSyncs != null) {
			userSync.setNoOfSyncs(noOfSyncs.intValue());
		}
		return userSync;

	}

	public void updateUserSync(UserSync userSync) {

		Entity entity = new Entity("UserSync");
		entity.setProperty("userEmail", userSync.getUserEmail());
		entity.setProperty("domain", userSync.getDomain());
		entity.setProperty("date", userSync.getDate());

		datastore.put(entity);

	}

	public void deleteGroupsAndContactsByUsers(List<String> usersToBeAdded,
			String domain) throws AppException {
		for (String userId : usersToBeAdded) {

			String userGroupId = getUserContactsGroupId(getGroupName(domain),
					userId + "@" + domain);// getUserGroupId(userId + "@" +
											// domain,domain); // added

			if (userGroupId != null) {
				List<ContactEntry> contactEntryList = this.getUserContacts(-1,
						-1, userGroupId, userId + "@" + domain);
				if (contactEntryList != null) {
					multipleDeleteUserContacts(contactEntryList, userId + "@"
							+ domain);
				}
				removeGroup(getGroupName(domain), userId + "@" + domain);
			}
		}
	}

	public void addGroupsAndContactsByUsers(List<String> usersToBeRemoved,
			String domain) throws AppException {

		String groupName = getGroupName(domain);

		for (String userId : usersToBeRemoved) {
			String email = userId + "@" + domain;
			ContactGroupEntry group = new ContactGroupEntry();
			group.setSummary(new PlainTextConstruct(groupName));
			group.setTitle(new PlainTextConstruct(groupName));
			this.createGroup(group, email);

			String userContactsGroupId = this.getUserContactsGroupId(groupName,
					email);
			String groupId = this.getSharedContactsGroupId(groupName);
			List<ContactEntry> contactEntryList = getContacts(-1, -1, groupId,
					true, null);
			if (contactEntryList != null) {
				for (ContactEntry entry : contactEntryList) {
					entry.getGroupMembershipInfos().remove(0);
					GroupMembershipInfo groupMembershipInfo = new GroupMembershipInfo();
					groupMembershipInfo.setHref(userContactsGroupId);
					entry.getGroupMembershipInfos().add(groupMembershipInfo);
				}
				multipleCreateUserContacts(contactEntryList, email);
			}

		}
	}

	public ContactEntry makeContact(String fullname, String givenname,
			String familyname, String companydept, String workemail,
			String workphone, String workaddress) {

		String companyname = "";
		String companytitle = "";
		String homeemail = "";
		String otheremail = "";
		String homephone = "";
		String mobilephone = "";
		String homeaddress = "";
		String otheraddress = "";
		String notes = "";

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

	@Override
	public List<String> getDomainList() {
		List<String> domains = new ArrayList<String>();
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"domain-admin");
		List<Entity> list = datastore.prepare(query).asList(
				FetchOptions.Builder.withDefaults());
		if (list != null && list.size() > 0) {
			for (Entity entity : list) {
				domains.add((String) entity.getProperty("domain"));
			}
		}
		return domains;
	}

	@Override
	public UserLogging updateUserLogging(UserLogging userLogging)
			throws AppException {
		Entity currentSettings = null;
		if (currentSettings == null) {
			currentSettings = new Entity("UserLogging");
		} else {
			try {
				currentSettings = datastore.get(userLogging.getKey());
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		currentSettings.setProperty("domain", userLogging.getDomain());
		currentSettings.setProperty("userId", userLogging.getUserId());

		try {
			return getUserLogging(datastore.get(datastore.put(currentSettings)));
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	UserLogging getUserLogging(Entity userLoggingEntity) {
		UserLogging userLogging = new UserLogging();
		userLogging.setKey(userLoggingEntity.getKey());
		userLogging.setDomain((String) userLoggingEntity.getProperty("domain"));
		userLogging.setUserId((String) userLoggingEntity.getProperty("userId"));
		return userLogging;
	}

	@Override
	public UserLogging getUserLogging(String domainName, String userId)
			throws AppException {
		UserLogging userLogging = null;
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"UserLogging");
		query.addFilter("domain",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				domainName);
		query.addFilter("userId",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				userId);
		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> userLoggings = preparedQuery.asList(FetchOptions.Builder
				.withDefaults());
		if (userLoggings != null && !userLoggings.isEmpty()) {
			userLogging = getUserLogging(userLoggings.get(0));
		}
		return userLogging;

	}

	public List<String> getEmailFromUserLoggingForDomain(String domainName)
			throws AppException {
		List<String> list = new ArrayList<String>();
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"UserLogging");
		query.addFilter("domain",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				domainName);
		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> userLoggings = preparedQuery.asList(FetchOptions.Builder
				.withDefaults());
		if (userLoggings != null && !userLoggings.isEmpty()) {
			for (Entity e : userLoggings) {
				list.add((String) e.getProperty("userId"));
			}
		}
		return list;

	}

	@Override
	public UserSync updateExistingUserSync(UserSync userSync) {
		Entity currentSync = null;
		try {
			currentSync = datastore.get(userSync.getKey());
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		currentSync.setProperty("domain", userSync.getDomain());
		currentSync.setProperty("date", userSync.getDate());
		currentSync.setProperty("userEmail", userSync.getUserEmail());
		currentSync.setProperty("noOfSyncs", userSync.getNoOfSyncs());

		try {
			return getUserSync(datastore.get(datastore.put(currentSync)));
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getMyContactsGroupId(String email) {
		String result = null;

		try {

			String feedurl = appProperties.getGroupFeedUrl() + email + "/full";

			String scGrpName = "System Group: My Contacts";
			logger.info("scGrpName: " + scGrpName);
			ContactsService service = getContactsService();

			URL retrieveUrl = new URL(feedurl);
			Query query = new Query(retrieveUrl);
			query.setMaxResults(100000); // paging
			query.setStartIndex(1);
			query.setStringCustomParameter("showdeleted", "false");
			query.setStringCustomParameter("xoauth_requestor_id", email);

			for (int counter = 0; counter < 5; counter++) {
				try {					
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
									logger.info("Group Name: " + titleTmp);
									logger.info("Group Id: " + result);
									break;
								}
							}
						}
					}
					break;
				} catch (Exception e) {
					System.out.println("retrying due to IO Error : "
							+ e.getMessage());
					System.out.println("sleeping!!!!!!!!!!!!!!!!!1111");
					Thread.sleep(800);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			// logger.severe("e.getMessage: " + e.getMessage());
			logger.log(Level.SEVERE, e.getMessage(), e);
		}

		return result;
	}
	
	public boolean removeContactInfo(String id){
		if(id.indexOf("?")>-1){
			id = id.substring(0,id.indexOf("?"));
		}
		Key key = KeyFactory.createKey(ContactInfo.class.getSimpleName(), id);
		datastore.delete(key);
		return true;
	}

	@Override
	public void incrementContactCount(String domainName) {
		Integer count = 0;
		Entity entity = getDomainGroupEntity(domainName);

		if (entity != null ) {
			count =  entity.getProperty("count")==null? null :((Long) entity.getProperty("count")).intValue();
			if(count==null || count <0){
				count = 1;
			}else{
				count++;
			}
			entity.setProperty("count", count);
			datastore.put(entity);
		}

		
	}

	@Override
	public void decrementContactCount(String domainName) {
		Integer count = 0;
		Entity entity = getDomainGroupEntity(domainName);

		if (entity != null ) {
			count =  entity.getProperty("count")==null? null :((Long) entity.getProperty("count")).intValue();
			if(count==null || count <=0){
				count = 0;
			}else{
				count--;
			}
			entity.setProperty("count", count);
			datastore.put(entity);
		}
		
	}
	
	public List<ContactInfo> isDuplicateEmail(String domain, String email) {
		List<ContactInfo> contacts = new ArrayList<ContactInfo>();

		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				ContactInfo.class.getSimpleName());
		query.addFilter("domain",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				domain);
		query.addFilter("workemail",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				email);
		SortDirection direction = SortDirection.ASCENDING;
		PreparedQuery preparedQuery = datastore.prepare(query);

		List<Entity> fetchedContacts = preparedQuery
				.asList(FetchOptions.Builder.withLimit(5));
		if (fetchedContacts != null && !fetchedContacts.isEmpty()) {
			for (Entity entity : fetchedContacts) {
				contacts.add(getContactInfo(entity));
			}
		}
		return contacts;
	}
}
