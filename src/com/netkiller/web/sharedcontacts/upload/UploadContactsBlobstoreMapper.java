package com.netkiller.web.sharedcontacts.upload;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.hadoop.io.NullWritable;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.tools.mapreduce.AppEngineMapper;
import com.google.appengine.tools.mapreduce.BlobstoreRecordKey;
import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.authn.oauth.OAuthParameters.OAuthType;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.Link;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.appsforyourdomain.AppsForYourDomainException;
import com.google.gdata.data.appsforyourdomain.provisioning.UserEntry;
import com.google.gdata.data.appsforyourdomain.provisioning.UserFeed;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.ContactGroupFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.contacts.UserDefinedField;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.FamilyName;
import com.google.gdata.data.extensions.FormattedAddress;
import com.google.gdata.data.extensions.FullName;
import com.google.gdata.data.extensions.GivenName;
import com.google.gdata.data.extensions.Name;
import com.google.gdata.data.extensions.OrgDepartment;
import com.google.gdata.data.extensions.OrgName;
import com.google.gdata.data.extensions.OrgTitle;
import com.google.gdata.data.extensions.Organization;
import com.google.gdata.data.extensions.PhoneNumber;
import com.google.gdata.data.extensions.StructuredPostalAddress;
import com.google.gdata.util.ServiceException;
import com.netkiller.exception.AppException;
import com.netkiller.security.acl.PermissionType;
import com.netkiller.util.CommonUtil;
import com.netkiller.util.CommonWebUtil;
import com.netkiller.vo.StaticProperties;
import com.netkiller.vo.UserPermission;

/**
 *
 * This Mapper imports from a file in the Blobstore. 
 *
 *
 * @author MUNAWAR
 *
 */
public class UploadContactsBlobstoreMapper extends
		AppEngineMapper<BlobstoreRecordKey, byte[], NullWritable, NullWritable> {
	private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	public String getGroupName(String domainName )	{
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query("DomainGroup");
		/*
		 * q.addFilter("lastName",
		 * com.google.appengine.api.datastore.Query.FilterOperator.EQUAL,
		 * lastNameParam); q.addFilter("height",
		 * com.google.appengine.api.datastore.Query.FilterOperator.LESS_THAN,
		 * maxHeightParam);
		 */
		query.addFilter("domainName", com.google.appengine.api.datastore.Query.FilterOperator.EQUAL, domainName);

		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> groupNames = preparedQuery.asList(FetchOptions.Builder.withDefaults());
		String groupName=null;
		if(groupNames!=null && !groupNames.isEmpty())
			groupName = (String)groupNames.get(0).getProperty("groupName");
		return groupName;
	}
	
	private final Logger log = Logger.getLogger(getClass().getName()); 
	private ContactsService service = null;
	
	
	private String getGroupId(String userEmail) {

		String groupId = null;

		try {
			
			String sharedContactsGroupName = getGroupName(CommonWebUtil.getDomain(userEmail));
			
			groupId = getSharedContactsGroupId(sharedContactsGroupName,userEmail);
			
			if (groupId == null || groupId.equals("")) {
				ContactGroupEntry group = new ContactGroupEntry();
				group.setSummary(new PlainTextConstruct(sharedContactsGroupName));
				group.setTitle(new PlainTextConstruct(sharedContactsGroupName));
				create(group,userEmail);
				groupId = getSharedContactsGroupId(sharedContactsGroupName,userEmail);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return groupId;
	}
	
	public String getSharedContactsGroupId(String name,String email) throws AppException {
		String result = null;
		try {
			String feedurl = getFeedUrl("https://www.google.com/m8/feeds/groups/",email);
			String scGrpName = name;
//			logger.info("scGrpName: " + scGrpName);
			ContactsService service = getContactsService();
			Collection<ContactGroupEntry> contactGroupEntries = new ArrayList<ContactGroupEntry>();
			URL retrieveUrl = new URL(feedurl);
			Link nextLink = null;
			
			
/*			ContactGroupFeed resultFeed = service.getFeed(new URL(feedurl),
					ContactGroupFeed.class);*/
			
			do {
				
				ContactGroupFeed resultFeed = service.getFeed(retrieveUrl,
						ContactGroupFeed.class);
				contactGroupEntries.addAll(resultFeed.getEntries());
				nextLink = resultFeed.getLink(Link.Rel.NEXT,
						Link.Type.ATOM);
				if (nextLink != null) {
					retrieveUrl = new URL(nextLink.getHref());
				}
				
				} while (nextLink != null);
			
			
			if (!contactGroupEntries.isEmpty()) {
				String titleTmp = null;
				TextConstruct tc = null;
				for (ContactGroupEntry groupEntry : contactGroupEntries) {
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
			// e.printStackTrace();
			// logger.severe("e.getMessage: " + e.getMessage());
	//		logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AppException();
		}
		return result;
	}
	
	
	@Override
	public void map(BlobstoreRecordKey key, byte[] segment, Context context) throws UnsupportedEncodingException {
		String line = new String(segment, "UTF-8");
		String groupid = context.getConfiguration().get("mapreduce.mapper.inputformat.datastoreinputformat.entitykind");
		String userEmail = context.getConfiguration().get("mapreduce.mapper.inputformat.datastoreinputformat.useremail");
		String domain = CommonWebUtil.getDomain(userEmail);
		ContactEntry contact = makeContact(line);
		GroupMembershipInfo gmInfo = new GroupMembershipInfo();
		gmInfo.setHref(getGroupId(userEmail));
		contact.addGroupMembershipInfo(gmInfo);
		
		try {
			create(contact, userEmail);
			
			for(String userId:getAllDomainUsersWithReadAndWritePErmissionIncludingAdmin(domain))	{
				System.out.println("File upload  NO restricted user shopuld be there : " + userId);
				ContactEntry newentry = makeContact(line);
				String userGroupId = getUserGroupId(userId+"@"+domain); // added
			System.out.println("userGroupId is"+userGroupId); 
				GroupMembershipInfo userGmInfo = new GroupMembershipInfo(); // added
				userGmInfo.setHref(userGroupId); // added
				newentry.addGroupMembershipInfo(userGmInfo);
				createUserContact(newentry,userId+"@"+domain);
			}
			log.warning("-----------------------------------------------------> uploaded..");
		} catch (AppException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void createUserContact(ContactEntry contact, String userEmail) throws AppException {
		try {
			ContactsService service = getContactsService();
			String feedurl = getUserFeedUrl("https://www.google.com/m8/feeds/contacts/",userEmail);
			URL postUrl = new URL(feedurl);
			ContactEntry newContact = service.insert(postUrl, contact);
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new AppException();
		}
	}
	
	public void create(ContactEntry contact, String userEmail) throws AppException{
		try{
			ContactsService service = getContactsService();
			String feedurl  = "https://www.google.com/m8/feeds/contacts/"+CommonWebUtil.getDomain(userEmail)+"/full?xoauth_requestor_id="+userEmail;
			URL postUrl = new URL(feedurl);
			service.insert(postUrl, contact);
		}
		catch(Exception e){
			e.printStackTrace();
			log.log(Level.SEVERE, e.getMessage(), e);
			throw new AppException();
		}
	}
	
	private ContactsService getContactsService() throws Exception{
		if(service != null){
			return service;
		}
		service = new ContactsService("ykko-test");	
		String consumerKey = "495526208353.apps.googleusercontent.com";
        String consumerSecret = "g3tjtU454QGr0lAknr2tK4rj";

        GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
        oauthParameters.setOAuthConsumerKey(consumerKey);
        oauthParameters.setOAuthConsumerSecret(consumerSecret);

        try {
        	service.setOAuthCredentials(oauthParameters, new OAuthHmacSha1Signer());
        	service.setReadTimeout(20000);
    		service.setConnectTimeout(20000);
        } catch (OAuthException e) {
        	e.printStackTrace();
            throw new ServletException("Unable to initialize contacts service in upload", e);
        }
		
		return service;
	}
	
	private ContactEntry makeContact(String data){
		StringTokenizer tokenizer = new StringTokenizer(data, "\t"); 
		
		String fullname = getValue(tokenizer.nextToken());
		String givenname = getValue(tokenizer.nextToken());
		String familyname = getValue(tokenizer.nextToken());
		
		String companyname = getValue(tokenizer.nextToken());
		String companytitle = getValue(tokenizer.nextToken());
		String companydept = getValue(tokenizer.nextToken());
		String workemail = getValue(tokenizer.nextToken());
		String homeemail = getValue(tokenizer.nextToken());
		String otheremail = getValue(tokenizer.nextToken());
		String workphone = getValue(tokenizer.nextToken());
		String homephone = getValue(tokenizer.nextToken());
		String mobilephone = getValue(tokenizer.nextToken());
		String workaddress = getValue(tokenizer.nextToken());
		String homeaddress = getValue(tokenizer.nextToken());
		String otheraddress = getValue(tokenizer.nextToken());
		String notes = getValue(tokenizer.nextToken());
		
		ContactEntry contact = new ContactEntry();
		
		String homeRel = "http://schemas.google.com/g/2005#home";
		String workRel = "http://schemas.google.com/g/2005#work";
		String otherRel = "http://schemas.google.com/g/2005#other";
		String mobileRel = "http://schemas.google.com/g/2005#mobile";
		
		Name name = new Name();
		if(!fullname.equals("")){
			name.setFullName(new FullName(fullname, null));
		}
		if(!givenname.equals("")){
			name.setGivenName(new GivenName(givenname, null));
		}
		if(!familyname.equals("")){
			name.setFamilyName(new FamilyName(familyname, null));
		}
		contact.setName(name);
		
		if( !companyname.equals("") || !companytitle.equals("")){
			Organization org = new Organization();
			if(!companyname.equals("")){
				org.setOrgName(new OrgName(companyname));
			}
			if(!companydept.equals("")){
				org.setOrgDepartment(new OrgDepartment(companydept));
			}
			if(!companytitle.equals("")){
				org.setOrgTitle(new OrgTitle(companytitle));
			}
			org.setRel(workRel);
			contact.addOrganization(org);
		}
		
		if(!workemail.equals("")){
			Email workEmail = new Email();
			workEmail.setAddress(workemail);
			workEmail.setRel(workRel);
			contact.addEmailAddress(workEmail);
		}
		
		if(!homeemail.equals("")){
			Email homeEmail = new Email();
			homeEmail.setAddress(homeemail);
			homeEmail.setRel(homeRel);
			contact.addEmailAddress(homeEmail);
		}
		
		if(!otheremail.equals("")){
			Email otherEmail = new Email();
			otherEmail.setAddress(otheremail);
			otherEmail.setRel(otherRel);
			contact.addEmailAddress(otherEmail);
		}
		
		if(!workphone.equals("")){
			PhoneNumber workPhone = new PhoneNumber();
			workPhone.setPhoneNumber(workphone);
			workPhone.setRel(workRel);
			contact.addPhoneNumber(workPhone);
		}
		
		if(!homephone.equals("")){
			PhoneNumber homePhone = new PhoneNumber();
			homePhone.setPhoneNumber(homephone);
			homePhone.setRel(homeRel);
			contact.addPhoneNumber(homePhone);
		}
		
		if(!mobilephone.equals("")){
			PhoneNumber mobilePhone = new PhoneNumber();
			mobilePhone.setPhoneNumber(mobilephone);
			mobilePhone.setRel(mobileRel);
			contact.addPhoneNumber(mobilePhone);
		}		
		
		if (!workaddress.equals("")) {
			FormattedAddress formattedAddress = new FormattedAddress(
					workaddress);
			StructuredPostalAddress postalAddress = new StructuredPostalAddress();
			postalAddress.setFormattedAddress(formattedAddress);
			postalAddress.setRel(StaticProperties.WORK_REL);
			// contact.addExtension(postalAddress);
			contact.addRepeatingExtension(postalAddress);
		}

		if (!homeaddress.equals("")) {
			FormattedAddress formattedAddress = new FormattedAddress(
					homeaddress);
			StructuredPostalAddress postalAddress = new StructuredPostalAddress();
			postalAddress.setFormattedAddress(formattedAddress);
			postalAddress.setRel(StaticProperties.HOME_REL);
			// contact.addExtension(postalAddress);
			contact.addRepeatingExtension(postalAddress);
		}

		if (!otheraddress.equals("")) {
			FormattedAddress formattedAddress = new FormattedAddress(
					otheraddress);
			StructuredPostalAddress postalAddress = new StructuredPostalAddress();
			postalAddress.setFormattedAddress(formattedAddress);
			postalAddress.setRel(StaticProperties.OTHER_REL);
			// contact.addExtension(postalAddress);
			contact.addRepeatingExtension(postalAddress);
		}

		/*
		 * if(!workaddress.equals("")){ PostalAddress workAddress = new
		 * PostalAddress(); workAddress.setValue(workaddress);
		 * workAddress.setRel(workRel); contact.addPostalAddress(workAddress); }
		 * 
		 * if(!homeaddress.equals("")){ PostalAddress homeAddress = new
		 * PostalAddress(); homeAddress.setValue(homeaddress);
		 * homeAddress.setRel(homeRel); contact.addPostalAddress(homeAddress); }
		 * 
		 * if(!otheraddress.equals("")){ PostalAddress otherAddress = new
		 * PostalAddress(); otherAddress.setValue(otheraddress);
		 * otherAddress.setRel(otherRel);
		 * contact.addPostalAddress(otherAddress); }
		 */

		if (!notes.equals("")) {
			// contact.setContent(new PlainTextConstruct(notes));
			contact.getUserDefinedFields().add(
					new UserDefinedField("Notes", notes));
		}

		
		/*if(!workaddress.equals("")){
			FormattedAddress formattedAddress = new FormattedAddress(workaddress);			
			StructuredPostalAddress postalAddress =  new StructuredPostalAddress();
			postalAddress.setFormattedAddress(formattedAddress);
			postalAddress.setRel(StaticProperties.WORK_REL);
			contact.addExtension(postalAddress);
		}
		
		if(!homeaddress.equals("")){
			FormattedAddress formattedAddress = new FormattedAddress(homeaddress);			
			StructuredPostalAddress postalAddress =  new StructuredPostalAddress();
			postalAddress.setFormattedAddress(formattedAddress);
			postalAddress.setRel(StaticProperties.HOME_REL);
			contact.addExtension(postalAddress);
		}
		
		if(!otheraddress.equals("")){
			FormattedAddress formattedAddress = new FormattedAddress(otheraddress);			
			StructuredPostalAddress postalAddress =  new StructuredPostalAddress();
			postalAddress.setFormattedAddress(formattedAddress);
			postalAddress.setRel(StaticProperties.OTHER_REL);
			contact.addExtension(postalAddress);
		}		
		
		if(!notes.equals("")){
			contact.setContent(new PlainTextConstruct(notes));
		}	*/	
		
		return contact;
	}
	
	private String getValue(String value){
		if(value.equals("&&&&")){
			return "";
		}else {
			return value;
		}
	}
	
	public  List<String> getAllDomainUsersWithReadAndWritePErmissionIncludingAdmin(String domain){
		List<String> allDomainUsers = getAllDomainUsersIncludingAdmin(domain);
		List<String> restrtictedUsers =  getAllUserNamesWithNoPermissions(domain);
		if(restrtictedUsers!=null) {
			allDomainUsers.removeAll(restrtictedUsers);
		}
		return allDomainUsers;
	}
	
	public List<String> getAllUserNamesWithNoPermissions(String domain) {
		List<Entity> userWithWritePermissions = getAllUserWithNoPermissions(domain);
		List<String> users = new ArrayList<String>();
		;
		for (Entity userPermission : userWithWritePermissions) {
			users.add(getUserPermission(userPermission).getUserID());
		}
		return users;
	}
	
	public List<Entity> getAllUserWithNoPermissions(String domain) {
		com.google.appengine.api.datastore.Query query = new com.google.appengine.api.datastore.Query("UserPermission");
		query.addFilter("permissionType", com.google.appengine.api.datastore.Query.FilterOperator.EQUAL, "none");
		query.addFilter("domain", com.google.appengine.api.datastore.Query.FilterOperator.EQUAL, domain);
		PreparedQuery preparedQuery = datastore.prepare(query);
		List<Entity> userPermissions = preparedQuery.asList(FetchOptions.Builder.withDefaults());
		return userPermissions;
	}
	
	private UserPermission getUserPermission(Entity entity) {
		UserPermission userPermission = new UserPermission();
		userPermission.setDomain(CommonUtil.getNotNullValue(entity.getProperty("domain")));
		userPermission.setKey(entity.getKey());
		userPermission.setPermissionType(PermissionType.getPermissionType(CommonUtil.getNotNullValue(entity
				.getProperty("permissionType"))));
		userPermission.setUserID(CommonUtil.getNotNullValue(entity.getProperty("userId")));
		return userPermission;
	}
	
	public List<String> getAllDomainUsersIncludingAdmin(String domain) {
		List<String> users = new ArrayList<String>();
		com.google.gdata.client.appsforyourdomain.UserService guserService = new com.google.gdata.client.appsforyourdomain.UserService(
				"ykko-test");
		String consumerKey = "495526208353.apps.googleusercontent.com";
        String consumerSecret = "g3tjtU454QGr0lAknr2tK4rj";
		String urlEscopo = "http://apps-apis.google.com/a/feeds/user/#readonly";
		GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
		oauthParameters.setOAuthConsumerKey(consumerKey);
		oauthParameters.setOAuthConsumerSecret(consumerSecret);
		oauthParameters.setOAuthType(OAuthType.TWO_LEGGED_OAUTH);

		oauthParameters.setScope(urlEscopo);

		try {
			guserService.setOAuthCredentials(oauthParameters, new OAuthHmacSha1Signer());
			guserService.setReadTimeout(20000);
			guserService.setConnectTimeout(20000);

			final String APPS_FEEDS_URL_BASE = "https://apps-apis.google.com/a/feeds/";
			final String SERVICE_VERSION = "2.0";

			String domainUrlBase = APPS_FEEDS_URL_BASE + domain + "/";

			URL retrieveUrl = new URL(domainUrlBase + "user/" + SERVICE_VERSION);
			UserFeed genericFeed = new UserFeed();

			Link nextLink = null;

			do {
				try {
					System.out.println("retrieveUrl" + retrieveUrl);
					genericFeed = guserService.getFeed(retrieveUrl, UserFeed.class);

					genericFeed.getEntries().addAll(genericFeed.getEntries());
					nextLink = genericFeed.getLink(Link.Rel.NEXT, Link.Type.ATOM);
					if (nextLink != null) {
						retrieveUrl = new URL(nextLink.getHref());
					}
				} catch (AppsForYourDomainException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} while (nextLink != null);

			if (genericFeed != null && genericFeed.getEntries() != null && !genericFeed.getEntries().isEmpty()) {

				for (UserEntry genericEntry : genericFeed.getEntries()) {
					System.out.println(genericEntry.getLogin().getUserName());
					
						System.out.println("USer:" + genericEntry.getLogin().getUserName());
						users.add(genericEntry.getLogin().getUserName());
					
					// appUsers.add(appUser);
				}
			}
		} catch (OAuthException e) {
			e.printStackTrace();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		users = new ArrayList<String>(new HashSet<String>(users));
		return users;
	}

	private String getUserGroupId(String email) {

		String groupId = null;

		try {
			String sharedContactsGroupName = getGroupName(CommonWebUtil.getDomain(email));
			if(sharedContactsGroupName == null)	
			sharedContactsGroupName = "NK Shared Contacts";
			groupId = getUserContactsGroupId(sharedContactsGroupName, email);
			
			while(groupId == null || groupId.equals("")) {
				ContactGroupEntry group = new ContactGroupEntry();
				group.setSummary(new PlainTextConstruct(sharedContactsGroupName));
				group.setTitle(new PlainTextConstruct(sharedContactsGroupName));
			//	sharedContactsService.createGroup(group, getCurrentUser(request).getEmail());
				createGroup(group, email);
				
				groupId = getUserContactsGroupId(sharedContactsGroupName, email);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return groupId;
	}
	public void create(ContactGroupEntry entry,String userEmail) throws AppException {
		try {
			ContactsService service = getContactsService();
			/*System.out.println("Url:"+appProperties.getGroupFeedUrl()+"agent_noreply@netkiller.com/full?xoauth_requestor_id=" + "agent_noreply@netkiller.com");
			service.insert(new URL(appProperties.getGroupFeedUrl()+"agent_noreply@netkiller.com/full?xoauth_requestor_id=" + "agent_noreply@netkiller.com"), entry);*/
			service.insert(new URL(getFeedUrl("https://www.google.com/m8/feeds/groups/",userEmail)), entry);
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new AppException();
		}
	}
	
	private String getFeedUrl(String url, String userEmail) {
		url = url + CommonWebUtil.getDomain(userEmail) + "/full?xoauth_requestor_id=" + userEmail;
		System.out.println("url == " + url);
		
		return url;
	}

	
	public void createGroup(ContactGroupEntry entry, String userEmail) throws AppException {
		try {
			ContactsService service = getContactsService();
		//	System.out.println("Url:"+appProperties.getGroupFeedUrl()+"agent_noreply@netkiller.com/full?xoauth_requestor_id=" + "agent_noreply@netkiller.com");
		//	service.insert(new URL(appProperties.getGroupFeedUrl()+"agent_noreply@netkiller.com/full?xoauth_requestor_id=" + "agent_noreply@netkiller.com"), entry);
			service.insert(new URL(getUserFeedUrl("https://www.google.com/m8/feeds/groups/",userEmail)), entry);
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new AppException();
		}
	}
	
	
	public String getUserContactsGroupId(String name, String userEmail) throws AppException {
		String result = null;
		try {
			String feedurl = getUserFeedUrl("https://www.google.com/m8/feeds/groups/",userEmail);
			String scGrpName = name;
		//	logger.info("scGrpName: " + scGrpName);
			ContactsService service = getContactsService();
			Collection<ContactGroupEntry> contactGroupEntries = new ArrayList<ContactGroupEntry>();
			URL retrieveUrl = new URL(feedurl);
			Link nextLink = null;
			
			
/*			ContactGroupFeed resultFeed = service.getFeed(new URL(feedurl),
					ContactGroupFeed.class);*/
			
			do {
				
				ContactGroupFeed resultFeed = service.getFeed(retrieveUrl,
						ContactGroupFeed.class);
				contactGroupEntries.addAll(resultFeed.getEntries());
				nextLink = resultFeed.getLink(Link.Rel.NEXT,
						Link.Type.ATOM);
				if (nextLink != null) {
					retrieveUrl = new URL(nextLink.getHref());
				}
				
				} while (nextLink != null);
			
			
			if (!contactGroupEntries.isEmpty()) {
				String titleTmp = null;
				TextConstruct tc = null;
				for (ContactGroupEntry groupEntry : contactGroupEntries) {
					tc = groupEntry.getTitle();
					if (tc != null) {
						titleTmp = tc.getPlainText();
						// logger.info("Id: " + groupEntry.getId());
						if (titleTmp.equals(scGrpName)) {
							result = groupEntry.getId();
					//		logger.info("Group Name: " + titleTmp);
					//		logger.info("Group Id: " + result);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			// logger.severe("e.getMessage: " + e.getMessage());
		//	logger.log(Level.SEVERE, e.getMessage(), e);
			throw new AppException();
		}
		return result;
	}
	
	private String getUserFeedUrl(String url,String email) {
		url = url + email + "/full?xoauth_requestor_id=" + email;
		System.out.println("url == " + url);
		
		return url;
	}
}
