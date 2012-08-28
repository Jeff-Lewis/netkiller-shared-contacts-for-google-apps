package com.metacube.ipathshala.workflow.impl.task;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.files.AppEngineFile;
import com.google.appengine.api.files.FileReadChannel;
import com.google.appengine.api.files.FileService;
import com.google.appengine.api.files.FileServiceFactory;
import com.google.appengine.api.files.FileWriteChannel;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.ContactGroupFeed;
import com.metacube.ipathshala.security.DomainConfig;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.util.CSVFileReader;
import com.metacube.ipathshala.util.CommonWebUtil;
import com.metacube.ipathshala.vo.AppProperties;
import com.metacube.ipathshala.workflow.AbstractWorkflowTask;
import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.impl.context.ContactImportContext;

@Component
public class ContactImportTask extends AbstractWorkflowTask {

	private static AppLogger log = AppLogger.getLogger(ContactImportTask.class);

	@Autowired
	com.metacube.ipathshala.service.ContactsService contactsService;
	
	@Autowired DomainConfig appProperties;

	@Override
	public WorkflowContext execute(WorkflowContext context) {
		System.out.println("inside workflow");
		ContactImportContext workflowContext = (ContactImportContext) context;
		/*String blobKeyStr = workflowContext.getBlobKeyStr();
		String email = workflowContext.getEmail();
		BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		BlobKey blobKey = new BlobKey(blobKeyStr);
		BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
		byte[] data = blobstoreService
				.fetchData(blobKey, 0, blobInfo.getSize());
		InputStream stream = new ByteArrayInputStream(data);
		CSVFileReader x = new CSVFileReader(stream);
		x.ReadFile();
		try {
			doSomething(x.getStoreValuesList(), email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return workflowContext;
	}

	private void doSomething(ArrayList<ArrayList<String>> storedValueList,
			String email) throws Exception {
		// Get a file service
		FileService fileService = FileServiceFactory.getFileService();

		// Create a new Blob file with mime-type "text/plain"
		AppEngineFile file = fileService.createNewBlobFile("text/csv");

		// Open a channel to write to it
		boolean lock = false;
		FileWriteChannel writeChannel = fileService
				.openWriteChannel(file, lock);

		// Different standard Java ways of writing to the channel
		// are possible. Here we use a PrintWriter:
		PrintWriter out = new PrintWriter(Channels.newWriter(writeChannel,
				"UTF8"));
		StringBuffer sb = new StringBuffer();
		System.out.println("Size of list" + storedValueList.size());
		for (int i = 0; i < storedValueList.size(); i++) {
			ArrayList<String> row = storedValueList.get(i);
			// System.out.println("Size of row is" + row.size());
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

		// Close without finalizing and save the file path for writing later
		out.close();
		String path = file.getFullPath();

		// Write more to the file in a separate request:
		file = new AppEngineFile(path);

		// This time lock because we intend to finalize
		lock = true;
		writeChannel = fileService.openWriteChannel(file, lock);

		// This time we write to the channel using standard Java
		writeChannel.write(ByteBuffer.wrap(sb.toString().getBytes("UTF-8")));

		// Now finalize
		writeChannel.closeFinally();

		// Later, read from the file using the file API
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
		System.out.println("" + blobKey);
		// BlobstoreService blobStoreService =
		// BlobstoreServiceFactory.getBlobstoreService();
		importData(blobKey.getKeyString(), email);
		// String segment = new String(blobStoreService.fetchData(blobKey, 30,
		// 40));
		// System.out.println(segment);
	}

	private void importData(String blobKey, String email) {

		TaskOptions task = buildStartJob("Import Shared Contacts");
		addJobParam(task,
				"mapreduce.mapper.inputformat.blobstoreinputformat.blobkeys",
				blobKey);
		String groupName = contactsService.getGroupName(CommonWebUtil.getDomain(email));
		String id = getSharedContactsGroupId(groupName,email);
		addJobParam(task,
				"mapreduce.mapper.inputformat.datastoreinputformat.entitykind",
				id);
		addJobParam(task,
				"mapreduce.mapper.inputformat.datastoreinputformat.useremail",

				email);

		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(task);
	}

	private String getFeedUrl(String url, String userEmail) {
		url = url + CommonWebUtil.getDomain(userEmail)
				+ "/full?xoauth_requestor_id=" + userEmail;
		// url = url + userEmail + "/full?xoauth_requestor_id=" + userEmail;
		System.out.println("url == " + url);
		return url;
	}
	
	private ContactsService getContactsService() throws Exception {
		ContactsService	service = new ContactsService("ykko-test");
		String consumerKey = appProperties.getConsumerkey();
		String consumerSecret = appProperties.getConsumerKeySecret();

		GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
		oauthParameters.setOAuthConsumerKey(consumerKey);
		oauthParameters.setOAuthConsumerSecret(consumerSecret);

		service.setOAuthCredentials(oauthParameters,
				new OAuthHmacSha1Signer());
		service.setReadTimeout(20000);
		service.setConnectTimeout(20000);

		return service;
	}
	
	public String getSharedContactsGroupId(String name, String email) {
		String result = null;
		try {
			String feedurl = getFeedUrl(appProperties.getGroupFeedUrl(), email);
			String scGrpName = name;
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
							result = groupEntry.getId();
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			// logger.severe("e.getMessage: " + e.getMessage());
		}
		return result;
	}

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

}
