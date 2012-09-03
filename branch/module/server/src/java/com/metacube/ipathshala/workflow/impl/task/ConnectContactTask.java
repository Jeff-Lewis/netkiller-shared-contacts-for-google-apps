package com.metacube.ipathshala.workflow.impl.task;

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
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.entity.ConnectContact;
import com.metacube.ipathshala.entity.Contact;
import com.metacube.ipathshala.mail.MailAddress;
import com.metacube.ipathshala.mail.MailMessage;
import com.metacube.ipathshala.mail.Recipient;
import com.metacube.ipathshala.mail.RecipientType;
import com.metacube.ipathshala.mail.impl.GoogleMailService;
import com.metacube.ipathshala.manager.ConnectContactManager;
import com.metacube.ipathshala.security.DomainConfig;
import com.metacube.ipathshala.service.KeyListService;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.util.CommonWebUtil;
import com.metacube.ipathshala.workflow.AbstractWorkflowTask;
import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowExecutionException;
import com.metacube.ipathshala.workflow.impl.context.ConnectContactContext;

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

		Scanner sc = new Scanner(contactKeys).useDelimiter(",");
		String randomUrl = CommonWebUtil.getDomain(email);
		String domain = CommonWebUtil.getDomain(email);
		StringBuffer sb = new StringBuffer();
		for (int x = 0; x < 8; x++) {
			sb.append((char) ((int) (Math.random() * 26) + 97));
		}
		randomUrl += (new Date()).getTime() + sb.toString();
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

		sendMailsToContact(contactKeyList,email,randomUrl);
		
		System.out.println("connect contacts complete");
		return context;
	}

	private void sendMailsToContact(List<Key> contactKeyList, String ownerEmail, String randomUrl) {
		Collection<Contact> contacts = keyListService.getByKeys(contactKeyList, Contact.class);
		for(Contact contact : contacts){
			sendUrlMailToContact(contact,randomUrl,ownerEmail);
		}
		
	}

	private void sendUrlMailToContact(Contact contact, String randomUrl, String ownerEmail) {
		String domain = CommonWebUtil.getDomain(ownerEmail);
		MailMessage mailMessage = new MailMessage();
		List<Recipient> recipients = new ArrayList<Recipient>();
		randomUrl = domainConfig.getApplicationUrl() + "/connect/connect.do?id=" + randomUrl;		
		/* contact as recipient in to */
		Recipient recipient = new Recipient();
		String email = contact.getWorkEmail();
		if(!email.contains("@")){
			email = email + "@" + domain;
		}
		MailAddress recipientMailAddress = new MailAddress(contact.getFullName(), email);
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
		String subject = contact.getFullName() + " are invited to login " + domain + " Shared Contacts.";
		mailMessage.setSubject(subject );
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fullName", contact.getFullName());
		map.put("randomUrl", randomUrl);
		try {
			mailService.sendMailTemplate(mailMessage, "connectContactTemplate.vm", map);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
