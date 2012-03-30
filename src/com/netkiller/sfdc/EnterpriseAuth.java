package com.netkiller.sfdc;

import java.util.logging.Logger;

import com.sforce.soap.enterprise.Connector;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;
import com.sforce.ws.transport.GaeHttpTransport;

public class EnterpriseAuth {
	private static final Logger log = Logger.getLogger(EnterpriseAuth.class.getName());
	public EnterpriseConnection login(String username, String password) {
		EnterpriseConnection connection = null;
		ConnectorConfig config = new ConnectorConfig();
		config = new ConnectorConfig();
		config.setReadTimeout(10000);
		config.setConnectionTimeout(10000);
		config.setTransport(GaeHttpTransport.class);
		config.setUsername(username);
		config.setPassword(password);
		config.setTraceMessage(true);

		try {
			connection = Connector.newConnection(config);
			
			if (connection.getUserInfo() != null) {
				System.out.println(
						connection.getUserInfo().getOrganizationName() + ": " + 
						connection.getUserInfo().getOrganizationId() + ": " +
						connection.getUserInfo().getUserFullName());
				// display some current settings
				log.warning("Auth EndPoint: " + config.getAuthEndpoint());
				log.warning("Service EndPoint: " + config.getServiceEndpoint());
				log.warning("Username: " + config.getUsername());
				log.warning("SessionId: " + config.getSessionId());
			} else {
				connection = null;
			}
		} catch (ConnectionException e) {
			log.severe(e.getMessage());
		}
		return connection;
	}
}
