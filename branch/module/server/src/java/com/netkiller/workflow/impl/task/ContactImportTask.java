package com.netkiller.workflow.impl.task;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.netkiller.entity.Contact;
import com.netkiller.manager.ContactsManager;
import com.netkiller.security.DomainConfig;
import com.netkiller.util.CSVFileReader;
import com.netkiller.util.CommonWebUtil;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.impl.context.ContactImportContext;

@Component
public class ContactImportTask extends AbstractWorkflowTask {

	// private static AppLogger log =
	// AppLogger.getLogger(ContactImportTask.class);

	@Autowired
	com.netkiller.service.ContactsService contactsService;

	@Autowired
	DomainConfig appProperties;

	@Autowired
	private ContactsManager contactsManager;

	@Override
	public WorkflowContext execute(WorkflowContext context) {
		System.out.println("inside workflow");
		ContactImportContext workflowContext = (ContactImportContext) context;
		String blobKeyStr = workflowContext.getBlobKeyStr();
		String email = workflowContext.getEmail();
		int startCount = workflowContext.getStartLimit();
		int maxCount = workflowContext.getEndLimit();
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
			doSomething(x.getStoreValuesList(), startCount, maxCount, email);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("contacts import failed");
			e.printStackTrace();
		}
		System.out.println("contacts imported");
		blobstoreService.delete(blobKey);
		return workflowContext;
	}

	private void doSomething(ArrayList<ArrayList<String>> storedValueList,
			int startCount, int maxCount, String email) throws Exception {
		// StringBuffer sb = new StringBuffer();
		for (int i = startCount; i <= maxCount; i++) {
			ArrayList<String> row = storedValueList.get(i);
			Contact contact = createNewContact(row);
			if (contact != null) {
				contact = contactsManager.createContact(contact,
						CommonWebUtil.getDomain(email));
				contactsManager.addContactForAllDomainUsers(
						CommonWebUtil.getDomain(email), contact);
			}
		}
	}

	private Contact createNewContact(ArrayList<String> row) {
		String fullName = StringUtils.isBlank(row.get(0)) ? "" : row.get(0);
		String firstName = StringUtils.isBlank(row.get(1)) ? "" : row.get(1);
		String lastName = StringUtils.isBlank(row.get(2)) ? "" : row.get(2);
		String company = StringUtils.isBlank(row.get(3)) ? "" : row.get(3);
		String jobTitle = StringUtils.isBlank(row.get(4)) ? "" : row.get(4);
		String department = StringUtils.isBlank(row.get(5)) ? "" : row.get(5);
		String emailWork = StringUtils.isBlank(row.get(6)) ? "" : row.get(6);
		String emailHome = StringUtils.isBlank(row.get(7)) ? "" : row.get(7);
		String emailOther = StringUtils.isBlank(row.get(8)) ? "" : row.get(8);
		String phoneWork = StringUtils.isBlank(row.get(9)) ? "" : row.get(9);
		String phoneHome = StringUtils.isBlank(row.get(10)) ? "" : row.get(10);
		String phoneMobile = StringUtils.isBlank(row.get(11)) ? "" : row
				.get(11);
		String addressWork = StringUtils.isBlank(row.get(12)) ? "" : row
				.get(12);
		String addressHome = StringUtils.isBlank(row.get(13)) ? "" : row
				.get(13);
		String addressOther = StringUtils.isBlank(row.get(14)) ? "" : row
				.get(14);
		String notes = StringUtils.isBlank(row.get(15)) ? "" : row.get(15);

		Contact contact = new Contact();
		contact.setFullName(fullName);
		contact.setFirstName(firstName);
		contact.setLastName(lastName);
		contact.setCmpnyName(company);
		contact.setCmpnyTitle(jobTitle);
		contact.setCmpnyDepartment(department);
		contact.setWorkEmail(emailWork);
		contact.setHomeEmail(emailHome);
		contact.setOtherEmail(emailOther);
		contact.setWorkPhone(phoneWork);
		contact.setHomePhone(phoneHome);
		contact.setMobileNumber(phoneMobile);
		contact.setWorkAddress(addressWork);
		contact.setHomeAddress(addressHome);
		contact.setOtherAddress(addressOther);
		contact.setNotes(notes);
		return contact;
	}

}
