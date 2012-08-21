

/*
Copyright (c) 2010 tgerm.com
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. The name of the author may not be used to endorse or promote products
   derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, 
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.metacube.ipathshala.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.net.SMTPAppender;
import org.apache.log4j.spi.LoggingEvent;

import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.mail.MailAddress;
import com.metacube.ipathshala.mail.MailMessage;
import com.metacube.ipathshala.mail.Recipient;
import com.metacube.ipathshala.mail.RecipientType;
import com.metacube.ipathshala.mail.impl.GoogleMailService;
import com.sun.mail.smtp.SMTPTransport;

/**
 * Extension of Log4j {@link SMTPAppender} for Gmail support
 * 
 * @author abhinav@tgerm.com
 * @see <a href="http://code.google.com/p/log4j-gmail-smtp-appender/">Google Code Project</a> <br/>
 *      <a href="http://www.tgerm.com">My Blog</a>
 */
public class GmailSMTPAppender extends SMTPAppender {
	/**
	 * Cached session for later use i.e. while sending emails
	 */
	protected Session session;

	public GmailSMTPAppender() {
		super();
	}

	/**
	 * Create mail session.
	 * 
	 * @return mail session, may not be null.
	 */
	protected Session createSession() {
		Properties props = new Properties();
		/*props.put("mail.smtps.host", getSMTPHost());
		//props.put("mail.smtps.auth", "true");
		props.put("mail.smtp.port", "587");*/

		Authenticator auth = null;
	/*	if (getSMTPPassword() != null && getSMTPUsername() != null) {
			auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(getSMTPUsername(),
							getSMTPPassword());
				}
			};
		}*/
		session = Session.getDefaultInstance(props, auth);
		/*if (getSMTPProtocol() != null) {
			session.setProtocolForAddress("rfc822", getSMTPProtocol());
		}*/
		/*if (getSMTPDebug()) {
			session.setDebug(getSMTPDebug());
		}*/
		return session;
	}

	/**
	 * Send the contents of the cyclic buffer as an e-mail message.
	 */
	protected void sendBuffer() {
		try {
			MimeBodyPart part = new MimeBodyPart();

			StringBuffer sbuf = new StringBuffer();
			String t = layout.getHeader();
			if (t != null)
				sbuf.append(t);
			int len = cb.length();
			for (int i = 0; i < len; i++) {
				LoggingEvent event = cb.get();
				sbuf.append(layout.format(event));
				if (layout.ignoresThrowable()) {
					String[] s = event.getThrowableStrRep();
					if (s != null) {
						for (int j = 0; j < s.length; j++) {
							sbuf.append(s[j]);
							sbuf.append(Layout.LINE_SEP);
						}
					}
				}
			}
			t = layout.getFooter();
			if (t != null)
				sbuf.append(t);
			part.setContent(sbuf.toString(), layout.getContentType());

			Multipart mp = new MimeMultipart();
			mp.addBodyPart(part);
			msg.setContent(mp);

			msg.setSentDate(new Date());
			send(msg);
		} catch (Exception e) {
			LogLog.error("Error occured while sending e-mail notification.", e);
		}
	}

	/**
	 * Pulled email send stuff i.e. Transport.send()/Transport.sendMessage(). So
	 * that on required this logic can be enhanced.
	 * 
	 * @param msg
	 *            Email Message
	 * @throws MessagingException
	 */
	protected void send(Message mail) throws MessagingException {
		/*SMTPTransport t = (SMTPTransport) session.getTransport("smtps");
		try {
			t.connect(getSMTPHost(),587, getSMTPUsername(), getSMTPPassword());
			t.sendMessage(msg, msg.getAllRecipients());
		} finally {
			System.out.println("Response: " + t.getLastServerResponse());
			t.close();
		}*/		
		try {
			
			mail.setSubject(getSubject());
			InternetAddress fromAddress = new InternetAddress(getFrom(),getFrom());
			mail.setFrom(fromAddress);
			
			InternetAddress toAddress = null;
			Collection<InternetAddress> addresses = new ArrayList<InternetAddress>();
			String toAddresses = getTo();
			String [] toAddr = StringUtils.split(toAddresses, ",");
			if(toAddr !=null && toAddr.length>0){			
				for(int iCount =0;iCount<toAddr.length;iCount++){
					toAddress = new InternetAddress(toAddr[iCount],toAddr[iCount]);
					addresses.add(toAddress);
				}
				
				InternetAddress [] addrArr = (InternetAddress [])addresses.toArray(new InternetAddress[addresses.size()]);			
				msg.addRecipients(MimeMessage.RecipientType.TO, addrArr);			
				Transport.send(mail);			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

