package com.metacube.ipathshala.mail;

import java.util.Map;

import com.metacube.ipathshala.core.AppException;

/**
 * @author saurab
 */

public interface MailService {

	public void sendMail(MailMessage message) throws AppException;
	
	public void sendMailTemplate(MailMessage mail, String template, Map<String, Object> objects) throws AppException;

}
