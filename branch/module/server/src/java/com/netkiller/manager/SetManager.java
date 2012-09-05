package com.netkiller.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.netkiller.FilterInfo;
import com.netkiller.GridRequest;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.AcademicYear;
import com.netkiller.entity.Period;
import com.netkiller.entity.Set;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.search.SearchResult;
import com.netkiller.service.SetService;

/**
 * @author dhruvsharma
 * 
 */
@Component
public class SetManager implements EntityManager {

	@Autowired
	SetService service;

	@Autowired
	SearchManager searchManager;

	/**
	 * @return the service
	 */
	public SetService getService() {
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(SetService service) {
		this.service = service;
	}

	/**
	 * Creates new set account.
	 * 
	 * @param set
	 * @return
	 * @throws AppException
	 */
	public Set createSet(Set set) throws AppException {
		return service.createSet(set);
	}

	/**
	 * Updates existing set account.
	 * 
	 * @param set
	 * @return
	 * @throws AppException
	 */
	public Set updateSet(Set set) throws AppException {
		return service.updateSet(set);
	}

	/**
	 * Delete existing set account.
	 * 
	 * @param set
	 * @throws AppException
	 */
	public void deleteSet(Set set) throws AppException {
		service.deleteSet(set);
	}

	/**
	 * Get set account based on primary key.
	 * 
	 * @param id
	 *            the set id to get the match.
	 * @return
	 * @throws AppException
	 */
	@Override
	public Object getById(Key id) throws AppException {
		return (Object)service.getById(Set.class, id);
	}

	/**
	 * Get the list of all academicyear account.
	 * 
	 * @return
	 * @throws AppException
	 */
	public Collection<Set> getAllSets() throws AppException {
		return service.getAll();
	}

	/**
	 * Returns a Collection Of Set Type Objects having the child element at
	 * position 0, it' parent at position 1 and so on
	 * 
	 * @param setKey
	 * @return
	 */
	public Collection<Set> getSetHierarchy(Key setKey) {
		return service.getSetHierarchy(setKey);
	}

	/**
	 * Returns a Set Object on the basis of the Name provided as a parameter
	 * 
	 * @param setName
	 * @return
	 */
	public Set getBySetName(String setName) {
		return service.getSetByName(setName);
	}

	/**
	 * Searches the Set Entity for the given parameters
	 * 
	 * @param gridRequest
	 * @return
	 * @throws AppException
	 */
	public SearchResult doSearch(GridRequest gridRequest) throws AppException {
		return searchManager.doSearch(Set.class, service.getEntityMetaData(),
				gridRequest, null);
	}

	@Override
	public EntityMetaData getEntityMetaData() {
		return service.getEntityMetaData();
	}

	@Override
	public SearchResult doSearch(FilterInfo filterInfo, DataContext dataContext) throws AppException {
		return searchManager.doSearch(Set.class, service.getEntityMetaData(),
				filterInfo, null);
	}

	@Override
	public Object restoreObject(Object object) throws AppException {
		return service.restoreEntity(object);
	}
}
