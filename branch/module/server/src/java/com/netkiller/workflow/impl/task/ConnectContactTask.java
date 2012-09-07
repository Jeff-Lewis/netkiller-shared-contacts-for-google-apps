package com.netkiller.workflow.impl.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.netkiller.core.AppException;
import com.netkiller.entity.ConnectContact;
import com.netkiller.entity.Contact;
import com.netkiller.mail.MailAddress;
import com.netkiller.mail.MailMessage;
import com.netkiller.mail.Recipient;
import com.netkiller.mail.RecipientType;
import com.netkiller.mail.impl.GoogleMailService;
import com.netkiller.manager.ConnectContactManager;
import com.netkiller.security.DomainConfig;
import com.netkiller.service.KeyListService;
import com.netkiller.util.AppLogger;
import com.netkiller.util.CommonWebUtil;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowExecutionException;
import com.netkiller.workflow.impl.context.ConnectContactContext;

public class ConnectContactTask extends AbstractWorkflowTask {

	private static AppLogger log = AppLogger
			.getLogger(ConnectContactTask.class);

	@Autowired
	DomainConfig appProperties;

	@Autowired
	ConnectContactManager connectContactManager;
	
	@Autowired private DomainConfig domainConfig;
	
	@Autowired private KeyListService keyListService;
	
	@Autowired private GoogleMailService mailService;

	@Override
	public WorkflowContext execute(WorkflowContext context)
			throws WorkflowExecutionException {

		ConnectContactContext connectContactContext = (ConnectContactContext) context;
		String contactKeys = connectContactContext.getContactKeysCSV();
		String email = connectContactContext.getOwnerEmail();
		String domain = CommonWebUtil.getDomain(email);
		Scanner sc = new Scanner(contactKeys).useDelimiter(",");
		
		StringBuffer sb = new StringBuffer();
		for (int x = 0; x < 8; x++) {
			sb.append((char) ((int) (Math.random() * 26) + 97));
		}
		String randomUrl =domain + (new Date()).getTime() + sb.toString();
		List<Key> contactKeyList = new ArrayList<Key>();
		Date date = new Date();
		while (sc.hasNextLong()) {
			Key key = KeyFactory.createKey(Contact.class.getSimpleName(),
					sc.nextLong());
			ConnectContact connectContact = new ConnectContact();
			connectContact.setRandomUrl(randomUrl);
			connectContact.setContactKey(key);
			connectContact.setCreatedBy(email);			
			connectContact.setCreatedDate(date);
			connectContact.setDomainName(domain);
			connectContactManager.create(connectContact);			
			contactKeyList.add(key);
		}

		sendUrlMailToContact(connectContactContext.getToName(), connectContactContext.getToEmail(), randomUrl, email);
		
		System.out.println("connect contacts complete");
		return context;
	}

	/*private void sendMailsToContact(List<Key> contactKeyList, String ownerEmail, String randomUrl) {
		Collection<Contact> contacts = keyListService.getByKeys(contactKeyList, Contact.class);
		for(Contact contact : contacts){
			sendUrlMailToContact(contact,randomUrl,ownerEmail);
		}
		
	}*/

	private void sendUrlMailToContact(String toName,String toEmail, String randomUrl, String ownerEmail) {
		String domain = CommonWebUtil.getDomain(ownerEmail);
		MailMessage mailMessage = new MailMessage();
		List<Recipient> recipients = new ArrayList<Recipient>();
		randomUrl = domainConfig.getApplicationUrl() + "/connect/connect.do?id=" + randomUrl;		
		/* contact as recipient in to */
		Recipient recipient = new Recipient();
		MailAddress recipientMailAddress = new MailAddress(toName, toEmail);
		recipient.setMailAddress(recipientMailAddress );
		recipient.setRecipientType(RecipientType.TO);
		recipients.add(recipient);
		
		/* owner as recipient in cc */
		Recipient owner = new Recipient();
		MailAddress ownerMailAddress = new MailAddress(CommonWebUtil.getUserId(ownerEmail), ownerEmail);
		owner.setMailAddress(ownerMailAddress );
		owner.setRecipientType(RecipientType.CC);
		recipients.add(owner);

		mailMessage.setRecipients(recipients );
		
		mailMessage.setSender(ownerMailAddress);
		String subject =toName + " are invited to login " + domain + " Shared Contacts.";
		mailMessage.setSubject(subject );
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fullName", toName);
		map.put("randomUrl", randomUrl);
		map.put("domain", domain);
		try {
			mailService.sendMailTemplate(mailMessage, "connectContactTemplate.vm", map);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
