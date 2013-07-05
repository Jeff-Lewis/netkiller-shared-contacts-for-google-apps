package com.netkiller.service.sharedcontacts;

import java.util.List;

import javax.servlet.ServletOutputStream;

import org.springframework.context.MessageSource;

import com.google.appengine.api.datastore.Entity;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.netkiller.exception.AppException;
import com.netkiller.googleUtil.ContactInfo;
import com.netkiller.search.GridRequest;
import com.netkiller.vo.AppProperties;
import com.netkiller.vo.Customer;
import com.netkiller.vo.DomainSettings;
import com.netkiller.vo.UserLogging;
import com.netkiller.vo.UserPermission;
import com.netkiller.vo.UserSync;

public interface SharedContactsService {
	
	public boolean isUserAdmin();

	public void setAppProperties(AppProperties appProperties);
	
	public void setMessageSource(MessageSource messageSource);
	
	//public List<ContactEntry> getContacts(String page, String rows, String sidx, String sord) throws AppException;
	public List<ContactEntry> getContacts(int start, int limit, String groupId, boolean isUseForSharedContacts, GridRequest gridRequest) throws AppException;
	public List<ContactEntry> getContacts(int start, int limit, String groupId,
			boolean isUseForSharedContacts, GridRequest gridRequest,String email)
			throws AppException ;
	public ContactEntry getContact(String url) throws AppException;
	
	UserLogging updateUserLogging(UserLogging userLogging)throws AppException;
	UserLogging getUserLogging(String domainName, String userId)throws AppException;
	
	
	public ContactEntry create(ContactEntry contact) throws AppException;
	
	public void createUserContact(ContactEntry contact, String userEmail) throws AppException;
	
	public boolean hasSharedContactsGroup(String name) throws AppException;
	
	public String getSharedContactsGroupId(String name) ;
	public Customer getDomainAdminEmail(String domain) ;
	
	public String getUserContactsGroupId(String name, String userEmail) ;
	
	public ContactGroupEntry create(ContactGroupEntry entry) throws AppException;
	public ContactGroupEntry createGroup(ContactGroupEntry entry, String userEmail) throws AppException;
	
	public void multipleCreate(List<ContactEntry> contactEntries) throws AppException;
	
	public void multipleCreateUserContacts(List<ContactEntry> contactEntries, String userEmail) throws AppException;
	
	public ContactEntry update(ContactEntry contact) throws AppException;
	
	public void remove(List<String> ids) throws AppException;
	
	public void download(List<ContactEntry> contacts, ServletOutputStream outputStream) throws AppException;
	
	public void setUserEamil(String email) throws AppException;
	public void removeDomainAdmin(String domain);
	
	public Customer verifyUser(String userEmail);
	public String getUserEamil();
	public Boolean upgradeMembership(Long customerId);
	
	public Boolean updateMembership(Long customerId,String accountType);
	public List<Customer> getAllCustomers();
	
	public Boolean updateTotalContacts(Long customerId, Integer contacts);
	
	public Boolean updateCustomers(String domainName,String field, Object value);

	public List<String> getAllDomainUsers(String domain);
	
	public List<String> getAllDomainUsersIncludingAdmin(String domain);

	public void assignUpdatePermissions(List<String> usersWithUpdatePermission, String string);

	public List<String> getAllUserNamesWithWritePermissions(String domain);

	public List<String> getAllUserWithReadPermissions(String domain);

	public void removeUpdatePermissions(List<String> usersToBeRemoved,
			String domain);

	public DomainSettings getDomainSettings(String domain);
	
	public List<String> getDomainList();

	public DomainSettings updateDomainSettings(DomainSettings domainSettings);
	
	public UserPermission getUserPermission(String userEmail);
	
	public boolean isUserPermitted(String userEmail);

	public List<String> getAllUserNamesWithNoPermissions(String domain);

	public void assignNoPermissions(List<String> usersToBeRemoved, String domain);

	public void removeNoPermissions(List<String> noPermissionUsersToBeRemoved, String domain);
	public boolean isUserAuthorized(String userEmail);

	public void setGroupName(String domainName ,String groupName);
	public String getGroupName(String domainName );

	public void syncUserContacts(String email, List<ContactEntry> entries);
	public List<ContactEntry> getUserContacts(int start, int limit,
			String groupId, String userEmail) throws AppException;
	public UserSync getUserSync(String userEmail, String date);
	public List<String> getEmailFromUserLoggingForDomain(String domainName)
	throws AppException;
	public UserSync getUserSync(String userEmail);
	public void updateUserSync(UserSync userSync);
	public UserSync updateExistingUserSync(UserSync userSync);
	
	public void createUserSync(String userEmail, String domain, String date);

	public void deleteGroupsAndContactsByUsers(List<String> usersToBeAdded, String domain) throws AppException;

	public  List<String> getAllDomainUsersWithReadAndWritePErmissionIncludingAdmin(String domain);

	public void addGroupsAndContactsByUsers(List<String> usersToBeRemoved, String domain) throws AppException;

	public ContactEntry makeContact(String fullname, String givenname, String familyname, String companydept,
			String workemail, String workphone, String workaddress);
	public void removeDuplicateGroups(String groupName, String userEmail);
	public String getMyContactsGroupId(String email)  ;
	
	public ContactInfo createContactInfo(ContactInfo  contactInfo) ;
	public ContactInfo getContactInfo(Entity entity) ;
	
	public boolean removeContactInfo(String id);
	
	public List<ContactInfo> getAllDomainContacts(String domain,Integer totalLimit,String sidx, String sord);
	
	public List<ContactInfo> getDomainContacts(String domain, Integer limit, Integer offset, String sidx, String sord) ;
	
	public void updateContactCount(String domainName, Integer count);
	public void incrementContactCount(String domainName);
	public void decrementContactCount(String domainName);
	public Integer getContactCount(String domainName) ;

	public List<ContactInfo> isDuplicateEmail(String domain, String email);
	public void mailCSV(List<ContactEntry> contacts,
			String email) throws AppException;
}
