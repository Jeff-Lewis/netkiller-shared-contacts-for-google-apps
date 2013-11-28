package com.netkiller.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sample.appsforyourdomain.AppsForYourDomainClient;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.Link;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.ContactGroupFeed;
import com.netkiller.entity.Workflow;
import com.netkiller.exception.AppException;
import com.netkiller.manager.WorkflowManager;
import com.netkiller.service.sharedcontacts.SharedContactsService;
import com.netkiller.util.CommonUtil;
import com.netkiller.util.CommonWebUtil;
import com.netkiller.vo.AppProperties;
import com.netkiller.vo.Customer;
import com.netkiller.vo.DomainSettings;
import com.netkiller.workflow.WorkflowInfo;
import com.netkiller.workflow.impl.context.DomainUpdateContext;
import com.netkiller.workflow.impl.context.SyncUserContactsContext;
import com.netkiller.workflow.impl.processor.WorkflowStatusType;

@Controller
public class StatisticsController {

	private static final String NO_ACCESS_MESSAGE = "Cannot access User List";

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	AppProperties appProperties;

	@Autowired
	private WorkflowManager workflowManager;

	@Autowired
	SharedContactsService sharedContactsService;

	@RequestMapping("/getCount.do")
	@ResponseBody
	public void getDomainUserCount() throws Exception {
		List<Customer> customers = sharedContactsService.getAllCustomers();
		for (Customer customer : customers) {
			List<String> list = sharedContactsService
					.getAllDomainUsers(customer.getDomain());
			if (list != null) {
				Integer usersCount = list.size();
				sharedContactsService.updateTotalContacts(customer.getId(),
						usersCount);
			}
		}
	}

	@RequestMapping("/getTotalUsers.do")
	@ResponseBody
	public String getDomainUsers(@RequestParam("domainName") String domainName)
			throws Exception {
		/*
		 * String returnStr = ""; List<String> list =
		 * sharedContactsService.getAllDomainUsers(domainName); if (list != null
		 * && !list.isEmpty()) { returnStr = list.toString(); returnStr =
		 * returnStr.substring(1, returnStr.length() - 1); } return returnStr;
		 */
		String returnStr = null;
		DomainSettings settings = sharedContactsService
				.getDomainSettings(domainName);
		if (settings != null) {
			String blobKeyStr = settings.getAllUserBlobKey();
			if (!StringUtils.isBlank(blobKeyStr)) {
				returnStr = getStringFromBlob(blobKeyStr);
			} else {
				List<String> list = sharedContactsService
						.getAllDomainUsersIncludingAdmin(domainName);
				if (list != null && !list.isEmpty()) {
					returnStr = list.toString();
					returnStr = returnStr.substring(1, returnStr.length() - 1);
				}

			}
		}
		Queue queue = QueueFactory.getQueue("default");
		TaskOptions options = TaskOptions.Builder.withUrl(
				"/async/updateTotalUsers.do").param("domainName", domainName);
		queue.add(options);
		return returnStr;

	}

	@RequestMapping("/getSyncUsers.do")
	@ResponseBody
	public String getSyncUsers(@RequestParam("domainName") String domainName)
			throws Exception {
		String returnStr = null;
		DomainSettings settings = sharedContactsService
				.getDomainSettings(domainName);
		if (settings != null) {
			String blobKeyStr = settings.getSyncUserBlobKey();
			returnStr = getStringFromBlob(blobKeyStr);
		}
		Queue queue = QueueFactory.getQueue("default");
		TaskOptions options = TaskOptions.Builder.withUrl(
				"/async/updateSyncUsers.do").param("domainName", domainName);
		queue.add(options);
		return returnStr;

	}

	@RequestMapping("/getNscUsers.do")
	@ResponseBody
	public String getNscUsers(@RequestParam("domainName") String domainName)
			throws Exception {
		String returnStr = null;
		DomainSettings settings = sharedContactsService
				.getDomainSettings(domainName);
		if (settings != null) {
			String blobKeyStr = settings.getNscUserBlobKey();
			returnStr = getStringFromBlob(blobKeyStr);
		}
		Queue queue = QueueFactory.getQueue("default");
		TaskOptions options = TaskOptions.Builder.withUrl(
				"/async/updateNscUsers.do").param("domainName", domainName);
		queue.add(options);
		return returnStr;

	}

	@RequestMapping("/async/updateNscUsers.do")
	@ResponseBody
	public boolean updateNSCUsers(@RequestParam("domainName") String domainName)
			throws Exception {
		String data = "No Users Use NSC";
		List<String> list = sharedContactsService
				.getEmailFromUserLoggingForDomain(domainName);
		if (list != null && !list.isEmpty()) {
			data = list.toString();
			data = data.substring(1, data.length() - 1);
			Integer total = list.size();
			sharedContactsService
					.updateCustomers(domainName, "nscUsers", total);

		}
		String nscUserBlobKey = saveFileToBlobStore(data, "totalNSCUsers.txt");
		DomainSettings settings = sharedContactsService
				.getDomainSettings(domainName);
		if (settings != null) {
			deleteFileFromBlobStore(settings.getNscUserBlobKey());
			settings.setNscUserBlobKey(nscUserBlobKey);
			sharedContactsService.updateDomainSettings(settings);
		}
		return true;

	}

	@RequestMapping("/async/updateTotalUsers.do")
	@ResponseBody
	public boolean updateTotalUsers(
			@RequestParam("domainName") String domainName) throws Exception {
		String data = NO_ACCESS_MESSAGE;
		List<String> list = sharedContactsService
				.getAllDomainUsersIncludingAdmin(domainName);
		if (list != null && !list.isEmpty()) {
			data = list.toString();
			data = data.substring(1, data.length() - 1);
			Integer total = list.size();
			sharedContactsService.updateCustomers(domainName, "totalUsers",
					total);
		}
		String allUserBlobKey = saveFileToBlobStore(data, "totalUsers.txt");
		DomainSettings settings = sharedContactsService
				.getDomainSettings(domainName);
		if (settings != null) {
			deleteFileFromBlobStore(settings.getAllUserBlobKey());
			settings.setAllUserBlobKey(allUserBlobKey);
			sharedContactsService.updateDomainSettings(settings);
		}
		return true;

	}

	@RequestMapping("/async/updateSyncUsers.do")
	@ResponseBody
	public boolean updateSyncUsers(@RequestParam("domainName") String domainName)
			throws Exception {
		String data = "No Users are synced with application";
		List<String> list = getSyncUserList(domainName);
		if (list != null && !list.isEmpty()) {
			data = list.toString();
			data = data.substring(1, data.length() - 1);
			Integer total = list.size();
			sharedContactsService.updateCustomers(domainName, "syncedUsers",
					total);
		}
		String syncUserBlobKey = saveFileToBlobStore(data, "syncUsers.txt");
		DomainSettings settings = sharedContactsService
				.getDomainSettings(domainName);
		if (settings != null) {
			deleteFileFromBlobStore(settings.getSyncUserBlobKey());
			settings.setSyncUserBlobKey(syncUserBlobKey);
			sharedContactsService.updateDomainSettings(settings);
		}

		return true;
	}

	private List<String> getSyncUserList(String domainName) {
		List<String> syncUsers = new ArrayList<String>();
		String sharedContactsGroupName = sharedContactsService
				.getGroupName(domainName);

		String groupId = null;
		Integer sharedContactsSize = null;
		try {

			Customer customer = sharedContactsService
					.getDomainAdminEmail(domainName);
			sharedContactsService.setUserEamil(customer.getAdminEmail());
			groupId = sharedContactsService
					.getSharedContactsGroupId(sharedContactsGroupName);

			List<ContactEntry> entries = sharedContactsService.getContacts(1,
					30000, groupId, true, null);
			/*
			 * for(ContactEntry ce : entries){ String name =
			 * ce.getName().getFullName().getValue(); }
			 */
			sharedContactsSize = entries.size();
		} catch (AppException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<String> list = sharedContactsService
				.getAllDomainUsersIncludingAdmin(domainName);
		for (String user : list) {
			try {
				/*
				 * if(user.equals("jitender")){ System.out.println(); }
				 */
				String email = user + "@" + domainName;
				String userGroupId = sharedContactsService
						.getUserContactsGroupId(sharedContactsGroupName, email);
				List<ContactEntry> contacts = sharedContactsService
						.getUserContacts(1, 30000, userGroupId, email);
				/*
				 * for(ContactEntry ce : contacts){ String name =
				 * ce.getName().getFullName().getValue();
				 * System.out.println(name); }
				 */if (contacts != null
						&& contacts.size() == sharedContactsSize) {
					syncUsers.add(user);
					System.out.println(user + " is synced");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return syncUsers;
	}

	private String saveFileToBlobStore(String data, String fileName)
			throws IOException {

		FileService fileService = FileServiceFactory.getFileService();
		AppEngineFile aeF = fileService.createNewBlobFile("", fileName);
		FileWriteChannel writeChannel = (FileWriteChannel) fileService
				.openWriteChannel(aeF, true);
		BufferedOutputStream bos = new BufferedOutputStream(
				Channels.newOutputStream(writeChannel));
		bos.write(data.getBytes());
		bos.flush();
		bos.close();
		writeChannel.closeFinally();
		String blobKey = fileService.getBlobKey(aeF).getKeyString();
		return blobKey;
	}

	private String getStringFromBlob(String blobKeyStr) {
		String returnStr = "Calculating . Please check after some time";
		if (!StringUtils.isBlank(blobKeyStr)) {
			BlobstoreService blobstoreService = BlobstoreServiceFactory
					.getBlobstoreService();

			BlobKey blobKey = new BlobKey(blobKeyStr);
			BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);

			byte[] ob = blobstoreService.fetchData(blobKey, 0, new Long(
					blobInfo.getSize()));
			returnStr = new String(ob);
			returnStr += ", Last Updated On : "
					+ blobInfo.getCreation().toString();
			System.out.println(returnStr);
		}
		return returnStr;

	}

	@RequestMapping("/getUsers.do")
	@ResponseBody
	public List<String> getUsers(@RequestParam("domainName") String domainName)
			throws Exception {
		List<String> list = sharedContactsService.getAllDomainUsers(domainName);
		FileService fileService = FileServiceFactory.getFileService();
		AppEngineFile aeF = fileService.createNewBlobFile("", "totalUsers.txt");
		FileWriteChannel writeChannel = (FileWriteChannel) fileService
				.openWriteChannel(aeF, true);
		BufferedOutputStream bos = new BufferedOutputStream(
				Channels.newOutputStream(writeChannel));
		bos.write(list.toString().getBytes());
		bos.flush();
		bos.close();
		writeChannel.closeFinally();

		return list;

	}

	private void deleteFileFromBlobStore(String blobKeyStr) {
		if (!StringUtils.isBlank(blobKeyStr)) {
			BlobstoreService blobstoreService = BlobstoreServiceFactory
					.getBlobstoreService();
			BlobKey blobKey = new BlobKey(blobKeyStr);
			blobstoreService.delete(blobKey);
		}

	}

	@RequestMapping("/getBlob.do")
	@ResponseBody
	public String getBlob(@RequestParam("key") String blobKeyStr,
			HttpServletResponse response) throws IOException {
		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		// String blobKeyStr = "OnsfvO5wgQ-m8vEiugz0Kg";

		BlobKey blobKey = new BlobKey(blobKeyStr);
		BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
		byte[] ob = blobstoreService.fetchData(blobKey, 0,
				new Long(blobInfo.getSize()));
		String str = new String(ob);
		return str;
	}

	AppsForYourDomainClient getDomainClient(String email, String pass,
			String domainName) throws Exception {
		AppsForYourDomainClient domainClient = null;
		try {
			domainClient = new AppsForYourDomainClient(email, pass, domainName);
		} catch (Exception e) {
			throw new Exception("Unable to create domain client.", e);
		}
		return domainClient;
	}

	@RequestMapping("/async/updateDomains.do")
	@ResponseBody
	public boolean updateDomains() {
		WorkflowInfo workflowInfo = new WorkflowInfo(
				"domainUpdateWorkflowProcessor");
		workflowInfo.setIsNewWorkflow(true);
		DomainUpdateContext domainUpdateContext = new DomainUpdateContext();
		Workflow workflow = new Workflow();
		workflow.setContext(domainUpdateContext);
		workflow.setWorkflowName(workflowInfo.getWorkflowName());
		workflow.setWorkflowInstanceId(workflowInfo.getWorkflowInstance());
		workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());
		domainUpdateContext.setWorkflowInfo(workflowInfo);
		workflowManager.triggerWorkflow(workflow);
		return true;
	}

	@RequestMapping("/deleteAll.do")
	@ResponseBody
	public boolean removeDuplicateGroups() {
		try {
			String userEmail = UserServiceFactory.getUserService()
					.getCurrentUser().getEmail();
			String feedurl = appProperties.getGroupFeedUrl() + userEmail
					+ "/full?xoauth_requestor_id=" + userEmail;
			ContactsService service = getContactsService();
			Collection<ContactGroupEntry> contactGroupEntries = new ArrayList<ContactGroupEntry>();
			URL retrieveUrl = new URL(feedurl);
			Link nextLink = null;

			do {

				ContactGroupFeed resultFeed = service.getFeed(retrieveUrl,
						ContactGroupFeed.class);
				contactGroupEntries.addAll(resultFeed.getEntries());
				nextLink = resultFeed.getLink(Link.Rel.NEXT, Link.Type.ATOM);
				if (nextLink != null) {
					retrieveUrl = new URL(nextLink.getHref());
				}

			} while (nextLink != null);

			if (!contactGroupEntries.isEmpty()) {
				TextConstruct tc = null;
				for (ContactGroupEntry groupEntry : contactGroupEntries) {
					tc = groupEntry.getTitle();
					try {
						groupEntry.delete();
						System.out.println("deleting " + tc.getPlainText());
					} catch (Exception e) {
						System.out.println(tc.getPlainText() + "delete failed");
					}

				}
			}

			// delete from shared api

			feedurl = appProperties.getGroupFeedUrl()
					+ CommonWebUtil.getDomain(userEmail)
					+ "/full?xoauth_requestor_id=" + userEmail;
			Collection<ContactGroupEntry> domainContactGroupEntries = new ArrayList<ContactGroupEntry>();
			retrieveUrl = new URL(feedurl);

			/*
			 * ContactGroupFeed resultFeed = service.getFeed(new URL(feedurl),
			 * ContactGroupFeed.class);
			 */

			do {

				ContactGroupFeed resultFeed = service.getFeed(retrieveUrl,
						ContactGroupFeed.class);
				domainContactGroupEntries.addAll(resultFeed.getEntries());
				nextLink = resultFeed.getLink(Link.Rel.NEXT, Link.Type.ATOM);
				if (nextLink != null) {
					retrieveUrl = new URL(nextLink.getHref());
				}

			} while (nextLink != null);

			if (!domainContactGroupEntries.isEmpty()) {
				for (ContactGroupEntry groupEntry : domainContactGroupEntries) {
					TextConstruct tc = groupEntry.getTitle();
					try {
						groupEntry.delete();
						System.out.println("deleting " + tc.getPlainText());
					} catch (Exception e) {
						System.out.println(tc.getPlainText() + "delete failed");
					}
				}
			}

			System.out.println("deletion done");
		} catch (Exception e) {
			e.printStackTrace();

		}
		return true;
	}

	private ContactsService getContactsService() throws Exception {
		ContactsService service = new ContactsService("ykko-test");
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
	
	@RequestMapping("/async/syncAllUsers.do")
	@ResponseBody
	public boolean syncAllUsers()
			throws Exception {
		Boolean isUseForSharedContacts = Boolean.parseBoolean(appProperties
				.getIsUseForSharedContacts());
		List<Customer> customers = sharedContactsService.getAllCustomers();
		for (Customer currentCustomer:customers) {
			int totalLimit = 100;
			String groupId = getSharedContactsGroupId(currentCustomer
					.getAdminEmail());
			if (!StringUtils.isBlank(groupId)) {
				if (currentCustomer.getAccountType().equalsIgnoreCase("Paid")) {
					totalLimit = 30000;
				} else {
					if (CommonUtil.isTheSecondTypeCustomer(currentCustomer)) {
						totalLimit = 50;
					}
				}

				List<String> users = sharedContactsService
						.getAllDomainUsersIncludingAdmin(currentCustomer
								.getDomain());
				for (String user : users) {
					String email = user + "@" + currentCustomer.getDomain();
					triggerSyncUserWorkflow(email, groupId,
							isUseForSharedContacts, totalLimit);
				}
			}
		}
		return true;
	}

	private String getSharedContactsGroupId(String email) {

		String groupId = null;

		try {
				String sharedContactsGroupName = sharedContactsService
						.getGroupName(CommonWebUtil.getDomain(email));

				groupId = sharedContactsService
						.getSharedContactsGroupId(sharedContactsGroupName,email);
		} catch (Exception e) {
			System.out.println("error fetching group id");
		}
		return groupId;
	}
	
	@RequestMapping("/async/triggerSyncAllTask.do")
	@ResponseBody
	public boolean triggerSyncAllTask(){
		Queue queue = QueueFactory.getQueue("sync-contacts");
		TaskOptions options = TaskOptions.Builder.withUrl("/async/syncAllUsers.do");
		queue.add(options);
		return true;
	}
	
	private void triggerSyncUserWorkflow(String email, String groupId,
			Boolean isUseForSharedContacts, Integer totalLimit) {
		SyncUserContactsContext syncUserContactsContext = new SyncUserContactsContext();
		syncUserContactsContext.setUserEmail(email);
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
		workflowManager.getService().triggerContactsSyncWorkflow(workflow);
	}
}
