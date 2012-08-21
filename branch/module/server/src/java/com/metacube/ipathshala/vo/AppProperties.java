package com.metacube.ipathshala.vo;

import java.util.List;

import javax.jdo.PersistenceManager;

import org.springframework.orm.jdo.support.JdoDaoSupport;

public class AppProperties extends JdoDaoSupport{
	private String username;
	private String password;
	private String feedurl;
	private String sharedContactsGroupName;
	private String adminDomain;
	private String domainCheck;
	private String groupFeedUrl;
	private String isUseForSharedContacts;
	private String isSortingSupported;
	private String useDb;
	private String consumerKey;
	private String consumerKeySecret;
	private String paymentGatewayAppid;
	private String paymentGatewayUsername;
	private String paymentGatewayPassword;
	private String paymentGatewaySignature;
	private String paymentGatewayAccountEmail;

	public String getIsSortingSupported() {
		if ("true".equals(useDb))
			return getPropertyDB("isSortingSupported");
		else
			return isSortingSupported;
	}

	public void setIsSortingSupported(String isSortingSupported) {
		this.isSortingSupported = isSortingSupported;
	}

	public String getIsUseForSharedContacts() {
		if ("true".equals(useDb))
			return getPropertyDB("isUseForSharedContacts");
		else
			return isUseForSharedContacts;
	}

	public void setIsUseForSharedContacts(String isUseForSharedContacts) {
		this.isUseForSharedContacts = isUseForSharedContacts;
	}

	public String getGroupFeedUrl() {
		if ("true".equals(useDb))
			return getPropertyDB("groupFeedUrl");
		else
			return groupFeedUrl;
	}

	public void setGroupFeedUrl(String groupFeedUrl) {
		this.groupFeedUrl = groupFeedUrl;
	}

	public String getDomainCheck() {
		if ("true".equals(useDb))
			return getPropertyDB("domainCheck");
		else
			return domainCheck;
	}

	public void setDomainCheck(String domainCheck) {
		this.domainCheck = domainCheck;
	}

	public String getAdminDomain() {
		if ("true".equals(useDb))
			return getPropertyDB("adminDomain");
		else
			return adminDomain;
	}

	public void setAdminDomain(String adminDomain) {
		this.adminDomain = adminDomain;
	}

	public String getSharedContactsGroupName() {
		if ("true".equals(useDb))
			return getPropertyDB("sharedContactsGroupName");
		else
			return sharedContactsGroupName;
	}

	public void setSharedContactsGroupName(String sharedContactsGroupName) {
		this.sharedContactsGroupName = sharedContactsGroupName;
	}

	public String getFeedurl() {
		if ("true".equals(useDb))
			return getPropertyDB("feedurl");
		else
			return feedurl;
	}

	public void setFeedurl(String feedurl) {
		this.feedurl = feedurl;
	}

	public String getUsername() {
		if ("true".equals(useDb))
			return getPropertyDB("username");
		else
			return username;
	}

	public String getUseDb() {
		return useDb;
	}

	public void setUseDb(String useDb) {
		this.useDb = useDb;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		if ("true".equals(useDb))
			return getPropertyDB("password");
		else
			return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private String getPropertyDB(String propId) {
		PersistenceManager pm = getPersistenceManager();
		String query = "select from " + Property.class.getName()
				+ " order by seq asc";
		javax.jdo.Query nQuery = pm.newQuery(query);
		List<Property> properties = (List<Property>) nQuery.execute();

		String propVal = null;
		for (int i = 0; i < properties.size(); i++) {
			if (((Property) properties.get(i)).getPropId().equals(propId)) {
				propVal = ((Property) properties.get(i)).getPropValue().trim();
			}
		}

		return propVal;
	}

	public String getConsumerKey() {
		if ("true".equals(useDb))
			return getPropertyDB("consumerKey");
		else
			return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerKeySecret() {
		if ("true".equals(useDb))
			return getPropertyDB("consumerKeySecret");
		else
			return consumerKeySecret;
	}

	public void setConsumerKeySecret(String consumerKeySecret) {
		this.consumerKeySecret = consumerKeySecret;
	}

	public String getPaymentGatewayAppid() {
		if ("true".equals(useDb))
			return getPropertyDB("paymentGatewayAppid");
		else
			return paymentGatewayAppid;
	}

	public String getPaymentGatewayUsername() {
		if ("true".equals(useDb))
			return getPropertyDB("paymentGatewayUsername");
		else
			return paymentGatewayUsername;
	}

	public String getPaymentGatewayPassword() {
		if ("true".equals(useDb))
			return getPropertyDB("paymentGatewayPassword");
		else
			return paymentGatewayPassword;
	}

	public String getPaymentGatewaySignature() {
		if ("true".equals(useDb))
			return getPropertyDB("paymentGatewaySignature");
		else
			return paymentGatewaySignature;
	}

	public String getPaymentGatewayAccountEmail() {
		if ("true".equals(useDb))
			return getPropertyDB("paymentGatewayAccountEmail");
		else
			return paymentGatewayAccountEmail;
	}

	public void setPaymentGatewayAppid(String paymentGatewayAppid) {
		this.paymentGatewayAppid = paymentGatewayAppid;
	}

	public void setPaymentGatewayUsername(String paymentGatewayUsername) {
		this.paymentGatewayUsername = paymentGatewayUsername;
	}

	public void setPaymentGatewayPassword(String paymentGatewayPassword) {
		this.paymentGatewayPassword = paymentGatewayPassword;
	}

	public void setPaymentGatewaySignature(String paymentGatewaySignature) {
		this.paymentGatewaySignature = paymentGatewaySignature;
	}

	public void setPaymentGatewayAccountEmail(String paymentGatewayAccountEmail) {
		this.paymentGatewayAccountEmail = paymentGatewayAccountEmail;
	}

}
