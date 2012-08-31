package com.metacube.ipathshala.manager;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.metacube.ipathshala.FilterInfo;
import com.metacube.ipathshala.GridRequest;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.entity.Contacts;
import com.metacube.ipathshala.entity.DomainAdmin;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.search.SearchResult;
import com.metacube.ipathshala.service.ContactsService;
import com.metacube.ipathshala.util.AppLogger;

@Component
public class ContactsManager extends AbstractManager implements EntityManager {

	private static final AppLogger log = AppLogger
			.getLogger(ContactsManager.class);

	@Autowired
	private ContactsService service;

	@Autowired
	private SearchManager searchManager;

	/*
	 * public ContactsService getService() { return service; }
	 * 
	 * public void setService(ContactsService service) { this.service = service;
	 * }
	 */

	@Override
	public EntityMetaData getEntityMetaData() {
		return service.getEntityMetaData();
	}

	@Override
	public SearchResult doSearch(FilterInfo filterInfo, DataContext dataContext)
			throws AppException {

		SearchResult searchResult = searchManager.doSearch(Contacts.class,
				service.getEntityMetaData(), filterInfo, null);
		return searchResult;
	}

	@Override
	public Object getById(Key key) throws AppException {
		return service.getById(key);

	}

	@Override
	public Object restoreObject(Object object) throws AppException {
		return service.restoreEntity(object);

	}

	public Collection<Contacts> getAll() throws AppException {
		return service.getAll();
	}

	public Collection<Contacts> getAllGlobalFilteredContacts(
			DataContext dataContext) throws AppException {
		return service.getAllGlobalFilteredContacts(null);
	}

	public Contacts createContact(Contacts contacts) throws AppException {
		/*
		 * super.setGlobalFilterProperties(contacts, dataContext,
		 * this.getEntityMetaData());
		 */return service.createContact(contacts);
	}

	public Contacts updateContact(Contacts contacts, DataContext dataContext)
			throws AppException {
		// super.updateGlobalFilterProperties(contacts, this);
		return service.updateContact(contacts);
	}
	
	
	public void updateContactAndExecuteWorkflow(Contacts contacts, DataContext dataContext){
		service.updateContactAndExecuteWorkflow(contacts,dataContext);
	}

	public void deleteContact(Contacts contacts, DataContext dataContext)
			throws AppException {

		service.deleteContact(contacts, null);
	}

	public SearchResult doSearch(GridRequest gridRequest,
			DataContext dataContext) throws AppException {
		return searchManager.doSearch(Contacts.class,
				service.getEntityMetaData(), gridRequest, dataContext);
	}

	public void exportContacts(DataContext dataContext,
			ServletOutputStream outputStream) throws AppException {
		service.exportContacts(null, outputStream);
	}

	public void deleteContactandExecuteWorkflow(List<Key> contactKeyList,
			DataContext dataContext) throws AppException {
		service.deleteContactandExecuteWorkflow(contactKeyList, null);

	}

	public void addContactForAllDomainUsers(String domain, Contacts contact)
			throws AppException {
		service.addContactForAllDomainUsers(domain, contact);
	}

	public void addGroupToAllContactForDomain(String domain, String group,
			String email) throws AppException {
		service.addGroupToAllContactForDomain(domain, group, email);
	}

	public String getGroupName(String domainName) {
		return service.getGroupName(domainName);
	}

	public Collection<Contacts> getByKeys(List<Key> contactsKeyList)
			throws AppException {
		return service.getByKeys(contactsKeyList);
	}

	public void duplicateContactandExecuteWorkflow(List<Key> contactKeyList,
			DataContext dataContext,String domain) throws AppException {
		service.duplicateContactandExecuteWorkflow(contactKeyList, null,domain);

	}

	public com.metacube.ipathshala.entity.UserSync getUserSync(String userEmail, Date date)
			throws AppException {
		return service.getUserSync(userEmail, date);
	}

	public void createUserSync(String userEmail, String domain, Date date)
			throws AppException {
		service.createUserSync(userEmail, domain, date);
	}

	public DomainAdmin verifyUser(String userEmail) {
		return service.verifyUser(userEmail);
	}

	public Boolean isAdmin(String email) {
		return service.isAdmin(email);
	}

	public void createGroup(ContactGroupEntry entry, String userEmail)
			throws AppException {
		service.createGroup(entry, userEmail);
	}

	public void sendMailToSelectedContacts() throws IOException {
		service.sendMailToSelectedContacts();

	}
}
