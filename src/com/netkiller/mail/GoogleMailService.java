package com.netkiller.mail;





import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.google.appengine.api.mail.MailServicePb.MailAttachment;
import com.netkiller.exception.AppException;

/**
 * 
 * @author raj
 * 
 */


public class GoogleMailService  {

	

	
	protected final Logger logger = Logger.getLogger(getClass().getName());
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
				
				

				MimeBodyPart html = null;
				if (mail.getHtmlBody() != null) {
					html = MailUtil.getHTMLBodyPart(mail.getHtmlBody());
					mp.addBodyPart(html);
				}
				msg.setContent(mp);
				Transport.send(msg);
				System.out.println("Mail sent successfully");
			} else {
			//	log.debug("Invalid Mail Message");
			}
		} catch (AddressException addressException) {
			String message = "Failed to send mail. Wrongly Formatted Address Encountered";
			logger.log(Level.WARNING, message);
		//	log.error(message, addressException);
		//	throw new AppException("AddressException: " + message, addressException);
		} catch (MessagingException messagingException) {
			String message = "Failed to send mail.";
			logger.log(Level.WARNING, message);
		//	log.error(message, messagingException);
		//	throw new AppException("MessagingException: " + message, messagingException);
		} catch (UnsupportedEncodingException unsupportedEncodingException) {
			String message = "Failed to send mail. ";
			logger.log(Level.WARNING, message);
		//	log.error(message, unsupportedEncodingException);
		//	throw new AppException("UnsupportedEncodingException: " + message, unsupportedEncodingException);
		}

	}

	
}
