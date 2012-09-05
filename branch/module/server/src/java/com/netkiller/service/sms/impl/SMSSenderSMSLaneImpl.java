package com.netkiller.service.sms.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;
import com.google.gdata.util.common.base.CharEscapers;
import com.netkiller.core.AppException;
import com.netkiller.service.sms.SMSSender;
import com.netkiller.util.AppLogger;

/**
 * specific implementation for smslane as third party sms sender.
 * It would currently work with cell numbers within india only as there is a validation for '91'
 * @author gaurav
 *
 */
public class SMSSenderSMSLaneImpl implements SMSSender{
	
	private static final AppLogger log = AppLogger.getLogger(SMSSenderSMSLaneImpl.class);
	
	private String user;
	private String password;
	private String senderID;
	private String flashInput;
	private URLFetchService service;
	
	public void setUser(String user) {
		this.user = user;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}


	public void setFlashInput(String flashInput) {
		this.flashInput = flashInput;
	}


	/**
	 * This will initialise URLfetchService
	 * 
	 * @throws AppException
	 */
	public void init() throws AppException {
		try {
			log.debug("Initializing URL Fetch Service");
			service = URLFetchServiceFactory.getURLFetchService();
		} catch (Exception e) {
			throw new AppException("Unable to create URLFetchService", e);
		}
	}
	

	@Override
	public boolean sendSMS(String messageText, List<String> destinationNos) {		
		boolean bSuccess = Boolean.FALSE;
		if(destinationNos==null || destinationNos.isEmpty() || messageText==null || "".equals(messageText) || service==null)
			return bSuccess;
		
		StringBuffer msisdn = new StringBuffer();
		String phoneNumber = null;
		//send single message
		if(destinationNos.size()==1){
			phoneNumber = validate(destinationNos.get(0));
			if(phoneNumber!=null)
				msisdn.append(phoneNumber);
		}else{
		//send to bulk 
			int iCount = 0;
			for(String number : destinationNos){				
				phoneNumber = validate(number);
				if(phoneNumber!=null){
					if(iCount++>0)
						msisdn.append(",");
					msisdn.append(phoneNumber);
				}				
			}
		}
		
		String tobeSentList = msisdn.toString();
		if(tobeSentList.length()>10 && user!=null && password!=null && senderID!=null){		
			StringBuffer url = new StringBuffer();
			url.append("http://smslane.com/vendorsms/pushsms.aspx?");
			url.append("user="+user);	
			url.append("&password="+password);
			url.append("&msisdn="+tobeSentList);
			url.append("&sid="+senderID);
			url.append("&msg="+CharEscapers.uriEscaper(false).escape(messageText));
			if(flashInput==null)
				flashInput="0";
			url.append("&fl="+flashInput);
			
			String urlstring = url.toString();
			/*log.debug("destination list"+tobeSentList);
			log.debug("message"+messageText);
			log.debug("url formed"+urlstring);*/			
			try {
				URL postURL = new URL(urlstring);				
				HTTPResponse resp = service.fetch(postURL);	
				if(resp!=null){
					String respContent = new String(resp.getContent());
					if(respContent!=null && !respContent.contains("Failed")){
						bSuccess = Boolean.TRUE;
					}else{
						log.error("destination list"+tobeSentList);
						log.error("message"+messageText);
						log.error("url formed"+urlstring);	
					}
				}
			} catch (MalformedURLException e) {
				log.error("Eror while sending SMS", e);
				log.error("destination list"+tobeSentList);
				log.error("message"+messageText);
				log.error("url formed"+urlstring);
			}catch (IOException e) {
				log.error("Eror while sending SMS", e);
				log.error("destination list"+tobeSentList);
				log.error("message"+messageText);
				log.error("url formed"+urlstring);
			}
			
		}
		
		return bSuccess;
	}


	private String validate(String number) {
		String numberReturn = null;
		if(number!=null && !number.isEmpty() && StringUtils.isNumeric(number)){			
			if(number.startsWith("91")){
				numberReturn = number;
			}else if(number.length()==10){
				numberReturn = "91"+number;
			}
			
		}
		
		return numberReturn;
	}

}
