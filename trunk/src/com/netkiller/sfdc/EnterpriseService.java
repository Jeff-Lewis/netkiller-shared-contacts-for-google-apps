package com.netkiller.sfdc;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.netkiller.vo.Customer;
import com.sforce.soap.enterprise.EnterpriseConnection;
import com.sforce.soap.enterprise.QueryResult;
import com.sforce.soap.enterprise.UpsertResult;
import com.sforce.soap.enterprise.sobject.NETKILLER_SaaS__c;
import com.sforce.ws.ConnectionException;

public class EnterpriseService {
	private static final Logger log = Logger.getLogger(EnterpriseService.class.getName());
	private String prefix = ""; 
	private EnterpriseConnection connection;
	public EnterpriseService(EnterpriseConnection connection, String prefix) {
		this.connection = connection;
		this.prefix = prefix;
	}
	
	
	public QueryResult getNSaaSs() {
		QueryResult queryResults = new QueryResult();
		StringBuilder query = new StringBuilder();
		query.append("SELECT										\n");
		query.append("	 Id 				 						\n");
		query.append("	," +prefix+ "Account__c						\n");
		query.append("	," +prefix+ "Contact__c              		\n");
		query.append("	," +prefix+ "Domain__c               		\n");
		query.append("	," +prefix+ "End_Date__c             		\n");
		query.append("	," +prefix+ "List_Total_Price__c     		\n");
		query.append("	," +prefix+ "Numbers__c              		\n");
		query.append("	," +prefix+ "Ordered__c              		\n");
		query.append("	," +prefix+ "Products__c             		\n");
		query.append("	," +prefix+ "Remarks__c              		\n");
		query.append("	," +prefix+ "Renewal_Email_Alert__c  		\n");
		query.append("	," +prefix+ "Renewed__c              		\n");
		query.append("	," +prefix+ "Reseller_Pannel__c      		\n");
		query.append("	," +prefix+ "Thanks__c               		\n");
		query.append("	," +prefix+ "Start_Date__c           		\n");
		query.append("	," +prefix+ "Status__c               		\n");
		query.append("	," +prefix+ "Suggested_Total_Price__c		\n");
		query.append("	," +prefix+ "Suggested_Unit_Price__c 		\n");
		query.append("	," +prefix+ "Type__c                 		\n");
		query.append("	," +prefix+ "Unit_Price__c           		\n");
		query.append("FROM											\n");
		query.append("	" +prefix+ "NETKILLER_SaaS__c 				\n");
		query.append("WHERE											\n");
		query.append("	IsDeleted = false							\n");
		
		try {
			queryResults = connection.query(query.toString());
		} catch (ConnectionException ce) {
			log.log(Level.SEVERE, ce.getMessage(), ce);
		}
		return queryResults;
	}
	
	/**
	 * create NSaaSs records
	 * @param customers in GAE DB
	 * @return
	 */
	public UpsertResult[] createNSaaSs(List<Customer> customers) {
		UpsertResult[] upsertResults= null;
		try {
			log.warning("customers size: " +  customers.size());
			
			NETKILLER_SaaS__c saas = null; //SFDC Object
			int toUpsertCnt = 100; //batch process size
			int offset = 0; //offset of customer list
			NETKILLER_SaaS__c[] records = new NETKILLER_SaaS__c[toUpsertCnt];
			Customer customer = null;
			
			for(int i=0; i<customers.size(); i++){
				customer = (Customer) customers.get(i);
				saas = new com.sforce.soap.enterprise.sobject.NETKILLER_SaaS__c();
				
				//start setting data
				Calendar registeredDate = Calendar.getInstance();
				if (customer.getRegisteredDate() != null) {
					registeredDate.setTime(customer.getRegisteredDate());
				}
			    
				saas.setNSCID__c(customer.getId()+"");
				saas.setDomain__c(customer.getDomain());
			    saas.setProducts__c("NETKILLER GAS Shared Contacts");
			    saas.setStart_Date__c(registeredDate);
			    saas.setNumbers__c(customer.getTotalContacts().doubleValue());
			    saas.setRemarks__c("Account type: " + customer.getAccountType() + ", " + 
			    		"Admin email: " + customer.getAdminEmail() + ", " +
			    		"App Engine Customer ID: " + customer.getId());
			   //end setting data
			    
			    records[offset > 0 ? i - offset : i] = saas;
		    	log.warning("i: "  + i + ", offset: "  + offset + ", " + (offset - i));
		    	if ((i > 0 && (i + 1) % toUpsertCnt == 0) || i == customers.size() - 1) { //toUpsertCnt = 100 
		    		log.warning("upsert i: "  + i);
		    		upsertResults = connection.upsert("NSCID__c", records);
		    		// Check the returned results for any errors
					int successCnt = 0;
					int failedCnt = 0;
					if (upsertResults != null) {
						for (int j = 0; j < upsertResults.length; j++) {
							if (upsertResults[j].isSuccess()) {
								log.warning(j + ". Successfully created record - Id: "
										+ upsertResults[j].getId());
								successCnt++;
							} else {
								com.sforce.soap.enterprise.Error[] errors = upsertResults[j].getErrors();
								for (int k = 0; k < errors.length; k++) {
									log.warning(k + ". creat record failed - Id: "
											+ upsertResults[i].getId());
								}
								failedCnt++;
							}
						}
					} else {
						log.warning("upsertResult is null");
					}
					log.warning("success: " + successCnt + ", failed: " + failedCnt);
					if (i != customers.size() - 1) {
						offset += toUpsertCnt;
						log.warning("offset: " + offset + " customers.size(): " + customers.size());
						if (offset + 100 > customers.size()) {
							log.warning("offset - customers.size(): " + (customers.size() - offset));
							records = new NETKILLER_SaaS__c[customers.size() - offset];
						} else {
							records = new NETKILLER_SaaS__c[toUpsertCnt];
						}
						log.warning("records length: " + records.length);
					}
		    	}
		     }				
			
			// Create agent records in Salesforce.com [NETKILLER]
		} catch (ConnectionException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		
		return upsertResults;
	}
}
