package com.metacube.ipathshala.vo;

import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.ContactGroupFeed;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.util.CommonWebUtil;

public class UploadContactsBlobstoreMapper {

	private static final AppLogger log = AppLogger
			.getLogger(UploadContactsBlobstoreMapper.class);
	private final DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	private com.google.gdata.client.contacts.ContactsService contactService = null;

	public String getGroupName(String domainName) {
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query(
				"DomainGroup");
		/*
		 * q.addFilter("lastName",
		 * com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
		 * lastNameParam); q.addFilter("height",
		 * com.google.appengine.api.datastore.Query.FilterOperator.LESS_THAN,
		 * maxHeightParam);
		 */
		query.addFilter("domainName",
				com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
				domainName);

		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> groupNames = preparedQuery.asList(FetchOptions.Builder
				.withDefaults());
		String groupName = null;
		if (groupNames != null && !groupNames.isEmpty())
			groupName = (String) groupNames.get(0).getProperty("groupName");
		return groupName;
	}

	private String getGroupId(String userEmail) {

		String groupId = null;

		try {

			String sharedContactsGroupName = getGroupName(CommonWebUtil
					.getDomain(userEmail));

			groupId = getSharedContactsGroupId(sharedContactsGroupName,
					userEmail);

			if (groupId == null || groupId.equals("")) {
				ContactGroupEntry group = new ContactGroupEntry();
				group.setSummary(new PlainTextConstruct(sharedContactsGroupName));
				group.setTitle(new PlainTextConstruct(sharedContactsGroupName));
				//create(group, userEmail);
				groupId = getSharedContactsGroupId(sharedContactsGroupName,
						userEmail);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return groupId;
	}

	public String getSharedContactsGroupId(String name, String email)
			throws AppException {
		String result = null;
		try {
			String feedurl = getFeedUrl(
					"https://www.google.com/m8/feeds/groups/", email);
			String scGrpName = name;

			ContactsService service = getContactsService();
			ContactGroupFeed resultFeed = service.getFeed(new URL(feedurl),
					ContactGroupFeed.class);
			if (resultFeed != null) {
				String titleTmp = null;
				TextConstruct tc = null;
				for (int i = 0; i < resultFeed.getEntries().size(); i++) {
					ContactGroupEntry groupEntry = resultFeed.getEntries().get(
							i);
					tc = groupEntry.getTitle();
					if (tc != null) {
						titleTmp = tc.getPlainText();
						// logger.info("Id: " + groupEntry.getId());
						if (titleTmp.equals(scGrpName)) {
							result = groupEntry.getId();

							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			throw new AppException(result);
		}
		return result;
	}
	
	private String getFeedUrl(String url, String userEmail) {
		url = url + CommonWebUtil.getDomain(userEmail) + "/full?xoauth_requestor_id=" + userEmail;
		System.out.println("url == " + url);
		
		return url;
	}
	
	private ContactsService getContactsService() throws Exception{
		if(contactService != null){
			return contactService;
		}
		contactService = new ContactsService("ykko-test");	
		String consumerKey = "495526208353.apps.googleusercontent.com";
        String consumerSecret = "98eakUfOqFfMTI1NGZuKs4el";

        GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
        oauthParameters.setOAuthConsumerKey(consumerKey);
        oauthParameters.setOAuthConsumerSecret(consumerSecret);

        try {
        	contactService.setOAuthCredentials(oauthParameters, new OAuthHmacSha1Signer());
        	contactService.setReadTimeout(20000);
    		contactService.setConnectTimeout(20000);
        } catch (OAuthException e) {
        	e.printStackTrace();
            throw new ServletException("Unable to initialize contacts service in upload", e);
        }
		
		return contactService;
	}

}
