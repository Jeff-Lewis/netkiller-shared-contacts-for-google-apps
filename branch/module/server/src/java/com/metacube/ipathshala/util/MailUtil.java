package com.metacube.ipathshala.util;

import javax.activation.MimetypesFileTypeMap;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.mail.MailAttachment;
import com.metacube.ipathshala.mail.MailMessage;
import com.metacube.ipathshala.mail.RecipientType;

public class MailUtil {

	private static MimetypesFileTypeMap mediaTypes;
	private static final AppLogger log = AppLogger.getLogger(MailUtil.class);

	public static Message.RecipientType getMessageRecipientType(RecipientType type) {
		Message.RecipientType recipientType = null;
		switch (type) {
		case TO:
			recipientType = MimeMessage.RecipientType.TO;
			break;
		case CC:
			recipientType = MimeMessage.RecipientType.CC;
			break;
		case BCC:
			recipientType = MimeMessage.RecipientType.BCC;
			break;
		}
		return recipientType;
	}

	public static MimeBodyPart getAttachmentBodyPart(MailAttachment mailAttachment) throws AppException {
		MimeBodyPart attachment = new MimeBodyPart();
		try {
			attachment.setFileName(mailAttachment.getFilename());
			attachment.setContent(mailAttachment.getFile(), mediaTypes.getContentType(mailAttachment.getFilename()));
		} catch (MessagingException messagingException) {
			String message = "Unable to access Mail Attachment ";
			log.error(message, messagingException);
			throw new AppException("MessagingException: " + message, messagingException);
		}
		return attachment;
	}

	public static MimeBodyPart getHTMLBodyPart(String htmlBody) throws AppException {
		MimeBodyPart attachment = new MimeBodyPart();
		try {
			attachment.setContent(htmlBody, "text/html");
		} catch (MessagingException messagingException) {
			String message = "Unable to access HTML contet in mail ";
			log.error(message, messagingException);
			throw new AppException("MessagingException: " + message, messagingException);
		}
		return attachment;
	}

	public static boolean isValidMail(MailMessage mailMessage) {
		boolean isValid = false;
		if (mailMessage.getRecipients().size() != 0 && mailMessage.getSender() != null) {
			if (mailMessage.getHtmlBody() != null || mailMessage.getTextMessage() != null) {
				isValid = true;
			}
		}
		return isValid;
	}

	public static String getQualifiedName(String templateName) {
		return "emailTemplate/" + templateName;
	}

}
