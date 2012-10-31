package com.netkiller.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springmodules.validation.bean.conf.loader.annotation.handler.MaxSize;

import com.google.appengine.api.datastore.Key;
import com.netkiller.FilterInfo;
import com.netkiller.GridRequest;
import com.netkiller.dao.AbstractDao;
import com.netkiller.dao.ContactsDao;
import com.netkiller.entity.Contact;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class ContactsDaoImpl extends AbstractDao<Contact> implements
		ContactsDao {

	@Autowired
	public ContactsDaoImpl(PersistenceManagerFactory persistenceManagerFactory) {
		super.setPersistenceManagerFactory(persistenceManagerFactory);
	}

	@Override
	public Contact create(Contact object) {
		return super.create(object);
	}

	@Override
	@Transactional(readOnly = true)
	public Contact get(Object id) {
		return super.get(Contact.class, id);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Contact> getAll() {
		return super.getAll(Contact.class);
	}

	@Override
	public Collection<Contact> getByKeys(List<Key> ContactsKeyList) {
		return super.getByKeys(Contact.class, ContactsKeyList);
	}

	@Override
	public Contact update(Contact contact) {
		return super.update(contact);
	}

	@Override
	public void remove(Object id) {
		super.remove(Contact.class, id);

	}

	@Override
	public List<Contact> doSearch(GridRequest gridRequest) {
		int maxResults = 0;
		int start = 0;
		String sortIndex = null;
		String sortOrder = null;
		String filters = "isDeleted==false && ";

		if (gridRequest.getPaginationInfo() != null) {
			maxResults = gridRequest.getPaginationInfo().getRecordsPerPage();
			start = (gridRequest.getPaginationInfo().getPageNumber() - 1)
					* maxResults;
		}
		if (gridRequest.getSortInfo() != null) {
			sortIndex = gridRequest.getSortInfo().getSortField();
			sortOrder = gridRequest.getSortInfo().getSortOrder();
		}
		if (gridRequest.getFilterInfo() != null) {
			List<FilterInfo.Rule> ruleList = gridRequest.getFilterInfo()
					.getRules();
			if (ruleList != null && !ruleList.isEmpty()) {
				for (FilterInfo.Rule rule : ruleList) {
					filters += rule.getField() + ">='" + rule.getData()
							+ "' && ";
					filters += rule.getField() + "<'" + rule.getData()
							+ "\ufffd' && ";
				}
			}

		}
		filters = filters.substring(0, filters.length() - 3);
		// String type = gridRequest.getSearchBean().getType();
		/*
		 * UserService userService = UserServiceFactory.getUserService(); User
		 * user = userService.getCurrentUser(); String domainName =
		 * CommonWebUtil.getDomain(user.getEmail()); String filter =
		 * "domainName=='" + domainName + "'";
		 */
		PersistenceManager pm = getPersistenceManager();
		Query query = pm.newQuery(Contact.class);
		query.setRange(start, start + maxResults);
		query.setFilter(filters);
		if (sortIndex != null) {
			// query.setOrdering(sortIndex + " " + sortOrder);
		}
		List<Contact> objectList = new ArrayList<Contact>();

		try {
			List<Contact> results = (List<Contact>) query.execute();
			if (!results.isEmpty()) {
				for (Contact template : results) {
					Contact detached = pm.detachCopy(template);
					objectList.add(detached);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			query.closeAll();
			pm.close();
		}

		return objectList;
	}

	@Override
	public List<Contact> getTotalContactList() {
		int maxResults = 1000;
		int start = 0;
		int resultSize = 0;
		String filters = "isDeleted==false";
		List<Contact> contactList = new ArrayList<Contact>();
		PersistenceManager pm = getPersistenceManager();
		while (resultSize >= start) {
			Query query = pm.newQuery(Contact.class);
			query.setRange(start, start + maxResults);
			query.setResult("key");
			query.setResultClass(Key.class);
			query.setFilter(filters);
			List<Contact> results;
			try {
				results = (List<Contact>) query.execute();

				if (!results.isEmpty()) {

					resultSize += results.size();
					start = maxResults;
					maxResults += 1000;
					contactList.addAll(results);

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				query.closeAll();

			}
		}

		return contactList;

	}
}
