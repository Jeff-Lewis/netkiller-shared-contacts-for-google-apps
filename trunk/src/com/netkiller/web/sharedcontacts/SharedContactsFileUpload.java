package com.netkiller.web.sharedcontacts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.blobstore.BlobKey;
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
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.netkiller.service.sharedcontacts.SharedContactsService;
import com.netkiller.util.CommonUtil;
import com.netkiller.util.CommonWebUtil;
import com.netkiller.vo.AppProperties;
import com.netkiller.vo.Customer;
import com.netkiller.vo.UserInfo;
import com.netkiller.web.sharedcontacts.upload.CSVFileReader;

/**
 * Ã«Å’â‚¬Ã«Å¸â€° Contact Ã«â€œÂ±Ã«Â¡ï¿½Ã¬ï¿½â€ž Ã¬Å“â€žÃ­â€¢Å“
 * Ã¬â€”â€˜Ã¬â€¦â‚¬Ã­Å’Å’Ã¬ï¿½Â¼ Ã¬â€”â€¦Ã«Â¡Å“Ã«â€œÅ
 * 
 * “Ã«Â¥Â¼ Ã¬Â²ËœÃ«Â¦Â¬Ã­â€¢Â¨
 * 
 * @author ykko
 * @modified Munawar Saeed Update it to support mapper,, converts .xls file to
 *           .csv and save in Blobstore
 * 
 */
@org.springframework.stereotype.Controller
public class SharedContactsFileUpload {

	protected final Logger logger = Logger.getLogger(getClass().getName());

	// Ã­â€�â€žÃ«Â¡Å“Ã­ï¿½Â¼Ã­â€¹Â° Ã¬â€žÅ“Ã«Â¹â€žÃ¬Å Â¤
	@Autowired
	private AppProperties appProperties;

	public void setAppProperties(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	// Ã«Â©â€�Ã¬â€¹Å“Ã¬Â§â‚¬ Ã¬â€žÅ“Ã«Â¹â€žÃ¬Å Â¤
	@Autowired
	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	// Ã«ï¿½â€žÃ«Â©â€�Ã¬ï¿½Â¸ÃªÂ³ÂµÃ¬Å“Â Ã¬Â£Â¼Ã¬â€ Å’Ã«Â¡ï¿½
	// Ã¬â€žÅ“Ã«Â¹â€žÃ¬Å Â¤Ã¬Â»Â´Ã­ï¿½Â¬Ã«â€žÅ’Ã­Å Â¸
	@Autowired
	private SharedContactsService sharedContactsService;

	public void setSharedContactsService(
			SharedContactsService sharedContactsService) {
		this.sharedContactsService = sharedContactsService;
	}

	/**
	 * app.properties Ã­Å’Å’Ã¬ï¿½Â¼Ã¬â€”ï¿½ Ã«â€œÂ±Ã«Â¡ï¿½Ã«ï¿½Å“
	 * SharedContactsGroupNameÃ¬ï¿½Â´ Google Domain
	 * 
	 * Shared ContactsÃ¬â€”ï¿½ Ã¬â€”â€ Ã¬ï¿½â€ž ÃªÂ²Â½Ã¬Å¡Â°
	 * SharedContactsGroupNameÃ¬ï¿½â€ž Ã¬Æ’ï¿½Ã¬â€žÂ±, Ã¬Å¾Ë†Ã¬ï¿½â€ž
	 * ÃªÂ²Â½Ã¬Å¡Â° Ã­â€ ÂµÃªÂ³Â¼
	 * 
	 * @return
	 */
	private String getGroupId() {

		String groupId = null;

		try {
			UserService userService = UserServiceFactory.getUserService();
			User user = userService.getCurrentUser();
			String sharedContactsGroupName = sharedContactsService.getGroupName(CommonWebUtil.getDomain(user.getEmail()));

			groupId = sharedContactsService.getSharedContactsGroupId(sharedContactsGroupName);
			logger.info("sharedContactsGroupName ===> " + sharedContactsGroupName);
			logger.info("groupId ===> " + groupId);
			if (groupId == null || groupId.equals("")) {
				ContactGroupEntry group = new ContactGroupEntry();
				group.setSummary(new PlainTextConstruct(sharedContactsGroupName));
				group.setTitle(new PlainTextConstruct(sharedContactsGroupName));
				sharedContactsService.create(group);
				groupId = sharedContactsService.getSharedContactsGroupId(sharedContactsGroupName);

				GroupMembershipInfo gmInfo = new GroupMembershipInfo(); // added
				gmInfo.setHref(groupId); // added
				
				

			}
		} catch (Exception e) {
			logger.log(Level.SEVERE,e.getMessage(),e);
		}
		return groupId;
	}
	

	/**
	 * Ã¬â€”â€˜Ã¬â€¦â‚¬Ã­Å’Å’Ã¬ï¿½Â¼ Ã¬â€”â€¦Ã«Â¡Å“Ã«â€œÅ“Ã¬â€¹Å“
	 * Ã­â€�â€žÃ«Â Ë†Ã¬Å¾â€žÃ¬â€ºÅ’Ã­ï¿½Â¬Ã¬â€”ï¿½
	 * 
	 * Ã¬ï¿½ËœÃ­â€¢Â´ Ã¬â€¹Â¤Ã­â€“â€°Ã«ï¿½Â¨
	 */
	@RequestMapping("/sharedcontacts/fileupload.do")
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.info("- SharedContactsFileUpload -");
		Map<String, String> result = new HashMap<String, String>();

		String msgCd = "";

		try {
			ServletFileUpload upload = new ServletFileUpload();
			response.setContentType("text/plain");

			FileItemIterator iterator = upload.getItemIterator(request);
			Customer currentCustomer = null;
			try {
				currentCustomer = sharedContactsService
						.verifyUser(getCurrentUser(request).getEmail());
				// sharedContactsService.setUserEamil(getCurrentUser(request).getEmail());
			} catch (Exception e) {
				// response.sendRedirect("/login.jsp");
				// String loginURL =
				// userService.createLoginURL(request.getRequestURI());
				// response.sendRedirect(loginURL); //ë¡œê·¸ì�¸ ì•ˆë�˜ì–´ ìžˆì�„
				// ê²½ìš° ë¡œê·¸ì�¸ íŽ˜ì�´ì§€ë¡œ ë¦¬ë‹¤ì�´ë ‰íŠ¸
			}
			while (iterator.hasNext()) {

				FileItemStream item = iterator.next();
				InputStream stream = item.openStream();
				CSVFileReader x = new CSVFileReader(stream);
				x.ReadFile();
				
				List<ContactEntry> list = sharedContactsService.getContacts(1, 100, getGroupId(),
						true, null);
				int count = list.size();
				
				if ((!currentCustomer.getAccountType().equals("Paid")
						&& (count+x.getStoreValuesList().size()) > 100)
							|| (!currentCustomer.getAccountType().equals("Paid") 
								&& (count+x.getStoreValuesList().size()) > 50 
								&& CommonUtil.isTheSecondTypeCustomer(currentCustomer))) {
					String message = messageSource.getMessage("account.limit",
							null, Locale.US);
					Map result1 = new HashMap();
					result1.put("code", "error");
					result1.put("message", message);
					return new ModelAndView("/sharedcontacts/comm_result_html",
							"result", result1);
				} else {

					if (item.isFormField()) {
						logger.warning("Got a form field: "
								+ item.getFieldName());
					} else {
						logger.warning("Got an uploaded file: "
								+ item.getFieldName() + ", name = " +

								item.getName());
						/*
						 * Workbook wb = WorkbookFactory.create(stream); Sheet
						 * sheet = wb.getSheetAt(0);
						 */

						doSomething(x.getStoreValuesList());
					}
				}
			}// end while
			String message = messageSource.getMessage("creation.success", null,
					Locale.US);
			result.put("code", "success");
			result.put("message", message);
			logger.info("No error!");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.log(Level.SEVERE, ex.getMessage(), ex);
			if (msgCd.equals("")) {
				msgCd = "error.default";
			}
			String message = messageSource.getMessage(msgCd, null, Locale.US);
			result.put("code", "error");
			result.put("message", message);
		}

		return new ModelAndView("/sharedcontacts/comm_result_html", "result",
				result);
	}

	private void doSomething(ArrayList<ArrayList<String>> storedValueList)
			throws Exception {
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
		importData(blobKey.getKeyString());
		// String segment = new String(blobStoreService.fetchData(blobKey, 30,
		// 40));
		// System.out.println(segment);
	}

	private void importData(String blobKey) {

		TaskOptions task = buildStartJob("Import Shared Contacts");
		addJobParam(task,
				"mapreduce.mapper.inputformat.blobstoreinputformat.blobkeys",
				blobKey);
		String id = getGroupId();
		addJobParam(task,
				"mapreduce.mapper.inputformat.datastoreinputformat.entitykind",
				id);
		addJobParam(task,
				"mapreduce.mapper.inputformat.datastoreinputformat.useremail",

				sharedContactsService.getUserEamil());

		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(task);
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
}
