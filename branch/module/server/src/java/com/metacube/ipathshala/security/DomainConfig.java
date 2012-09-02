package com.metacube.ipathshala.security;

import sample.appsforyourdomain.AppsForYourDomainClient;

import com.google.gdata.client.appsforyourdomain.AppsGroupsService;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.util.AppLogger;

public class DomainConfig {
	private static final AppLogger log = AppLogger
			.getLogger(DomainConfig.class);

	private String domainName;

	private String domainAdminEmail;

	private String domainAdminPassword;

	private AppsForYourDomainClient domainClient;

	private String consumerkey;

	private String consumerKeySecret;

	private String groupFeedUrl;

	private String feedurl;
	
	private String applicationUrl;
	
	

	public String getApplicationUrl() {
		return applicationUrl;
	}

	public void setApplicationUrl(String applicationUrl) {
		this.applicationUrl = applicationUrl;
	}

	public String getGroupFeedUrl() {
		return groupFeedUrl;
	}

	public void setGroupFeedUrl(String groupFeedUrl) {
		this.groupFeedUrl = groupFeedUrl;
	}

	public String getFeedurl() {
		return feedurl;
	}

	public void setFeedurl(String feedurl) {
		this.feedurl = feedurl;
	}

	public String getConsumerkey() {
		return consumerkey;
	}

	public void setConsumerkey(String consumerkey) {
		this.consumerkey = consumerkey;
	}

	public String getConsumerKeySecret() {
		return consumerKeySecret;
	}

	public void setConsumerKeySecret(String consumerKeySecret) {
		this.consumerKeySecret = consumerKeySecret;
	}

	/*
	 * Group service to handle group related request for google app domain.
	 */
	private AppsGroupsService groupsService;

	private String dataAdminEmail;

	private String dataAdminPassword;

	public String getDataAdminEmail() {
		return dataAdminEmail;
	}

	public void setDataAdminEmail(String dataAdminEmail) {
		this.dataAdminEmail = dataAdminEmail;
	}

	public String getDataAdminPassword() {
		return dataAdminPassword;
	}

	public void setDataAdminPassword(String dataAdminPassword) {
		this.dataAdminPassword = dataAdminPassword;
	}

	/**
	 * This will initialise various google app domain related services.
	 * 
	 * @throws AppException
	 */
	public void init() throws AppException {
		try {
			log.debug("Domain detail: " + domainName + ", " + domainAdminEmail);
			domainClient = new AppsForYourDomainClient(domainAdminEmail,
					domainAdminPassword, domainName);
			groupsService = domainClient.getGroupService();
		} catch (Exception e) {
			log.error("Unable to create domain client.", e);
			throw new AppException("Unable to create domain client.", e);
		}
	}

	public AppsForYourDomainClient getDomainClient() {
		return domainClient;
	}

	public void setDomainClient(AppsForYourDomainClient domainClient) {
		this.domainClient = domainClient;
	}

	public AppsGroupsService getGroupsService() {
		return groupsService;
	}

	public void setGroupsService(AppsGroupsService groupsService) {
		this.groupsService = groupsService;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getDomainAdminEmail() {
		return domainAdminEmail;
	}

	public void setDomainAdminEmail(String domainAdminEmail) {
		this.domainAdminEmail = domainAdminEmail;
	}

	public String getDomainAdminPassword() {
		return domainAdminPassword;
	}

	public void setDomainAdminPassword(String domainAdminPassword) {
		this.domainAdminPassword = domainAdminPassword;
	}
}
