package com.netkiller.service.sms;

import java.util.List;

public interface SMSSender {	
	
	public boolean sendSMS(String messageText ,List<String> destinationNos);

}
