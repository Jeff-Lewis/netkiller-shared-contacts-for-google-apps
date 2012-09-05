package com.netkiller.mail.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.netkiller.core.AppException;
import com.netkiller.mail.MailAttachment;
import com.netkiller.mail.MailMessage;
import com.netkiller.mail.MailService;
import com.netkiller.mail.Recipient;
import com.netkiller.util.AppLogger;
import com.netkiller.util.MailUtil;

/**
 * 
 * @author saurab
 * 
 */

@Component
public class GoogleMailService implements MailService {

	private static final AppLogger log = AppLogger.getLogger(GoogleMailService.class);
	private VelocityEngine velocityEngine;

	/**
	 * @param velocityEngine
	 *            the velocityEngine to set
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	@Override
	public void sendMail(MailMessage mail) throws AppException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage msg = null;
		try {
			if (MailUtil.isValidMail(mail)) {
				msg = new MimeMessage(session);
				Multipart mp = new MimeMultipart();
				InternetAddress fromAddress = new InternetAddress(mail.getSender().getEmail(), mail.getSender()
						.getName());
				msg.setFrom(fromAddress);
				msg.setSubject(mail.getSubject());
				if (mail.getTextMessage() != null) {
					msg.setText(mail.getTextMessage());
				}

				if (mail.getRecipients() != null) {
					for (Recipient recipient : mail.getRecipients()) {
						InternetAddress toAddress = new InternetAddress(recipient.getMailAddress().getEmail(),
								recipient.getMailAddress().getName());
						msg.addRecipient(MailUtil.getMessageRecipientType(recipient.getRecipientType()), toAddress);
					}
				}

				if (mail.getAttachments() != null) {
					for (MailAttachment mailAttachment : mail.getAttachments()) {
						MimeBodyPart attachment = MailUtil.getAttachmentBodyPart(mailAttachment);
						mp.addBodyPart(attachment);
					}
				}

				MimeBodyPart html = null;
				if (mail.getHtmlBody() != null) {
					html = MailUtil.getHTMLBodyPart(mail.getHtmlBody());
					mp.addBodyPart(html);
				}
				msg.setContent(mp);
				Transport.send(msg);
			} else {
				log.debug("Invalid Mail Message");
			}
		} catch (AddressException addressException) {
			String message = "Failed to send mail. Wrongly Formatted Address Encountered";
			log.error(message, addressException);
			throw new AppException("AddressException: " + message, addressException);
		} catch (MessagingException messagingException) {
			String message = "Failed to send mail.";
			log.error(message, messagingException);
			throw new AppException("MessagingException: " + message, messagingException);
		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			String message = "Failed to send mail. ";
			log.error(message, unsupportedEncodingException);
			throw new AppException("UnsupportedEncodingException: " + message, unsupportedEncodingException);
		}

	}

	@Override
	public void sendMailTemplate(MailMessage mail, String template, Map<String, Object> objects) throws AppException {
		Map<String, Object> model = new HashMap<String, Object>();
		for (String objectName : objects.keySet()) {
			model.put(objectName, objects.get(objectName));
		}
		String html = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, MailUtil.getQualifiedName(template), model);
		mail.setHtmlBody(html);
		this.sendMail(mail);

	}
	
	public String getHtml(MailMessage mail, String template, Map<String, Object> objects)	{
		Map<String, Object> model = new HashMap<String, Object>();
		for (String objectName : objects.keySet()) {
			model.put(objectName, objects.get(objectName));
		}
		String html = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, MailUtil.getQualifiedName(template), model);
		return html;
		
	}
}
