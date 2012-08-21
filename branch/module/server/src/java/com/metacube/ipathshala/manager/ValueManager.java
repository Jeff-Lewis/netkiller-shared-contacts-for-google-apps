package com.metacube.ipathshala.manager;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.FilterInfo;
import com.metacube.ipathshala.GridRequest;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.entity.Set;
import com.metacube.ipathshala.entity.Value;
import com.metacube.ipathshala.entity.metadata.EntityMetaData;
import com.metacube.ipathshala.search.SearchResult;
import com.metacube.ipathshala.service.ValueService;

/**
 * @author dhruvsharma
 * 
 */
@Component
public class ValueManager implements EntityManager {

	@Autowired
	private ValueService service;

	@Autowired
	private SearchManager searchManager;

	/**
	 * @return the service
	 */
	public ValueService getService() {
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(ValueService service) {
		this.service = service;
	}

	/**
	 * Creates new Value account.
	 * 
	 * @param Value
	 * @return
	 * @throws AppException
	 */
	public Value createValue(Value value) throws AppException {
		return service.createValue(value);
	}

	/**
	 * Updates existing Value account.
	 * 
	 * @param Value
	 * @return
	 * @throws AppException
	 */
	public Value updateValue(Value value) throws AppException {
		return service.updateValue(value);
	}

	/**
	 * Delete existing Value account.
	 * 
	 * @param Value
	 * @throws AppException
	 */
	public void deleteValue(Value value) throws AppException {
		service.deleteValue(value);
	}

	/**
	 * Get Value account based on primary key.
	 * 
	 * @param id
	 *            the Value id to get the match.
	 * @return
	 * @throws AppException
	 */
	@Override
	public Object getById(Key id) throws AppException {
		return (Object)service.getById(id);
	}


	/**
	 * Get the list of all Value account.
	 * 
	 * @return
	 * @throws AppException
	 */
	public Collection<Value> getAllValues() throws AppException {
		return service.getAll();
	}

	public Collection<Value> getValueBySetKey(Set set) {
		return service.getValueBySetKey(set);
	}
	
	public Collection<Value> getValueBySetKeyandParentValuekey(Set set, Key parent) {
		return service.getValueBySetKeyandParentValueKey(set, parent);
	}

	public Collection<Value> getValueHierarchy(Key rootValueKey) {
		return service.getValueHierarchy(rootValueKey);
	}

	public SearchResult doSearch(GridRequest gridRequest) throws AppException {
		SearchResult searchResult = searchManager.doSearch(Value.class,
				service.getEntityMetaData(), gridRequest, null);
		List<Object> objectList = searchResult.getResultObjects();
		List<Object> valueList = (List<Object>) service
				.populateRelationshipFields(objectList);
		searchResult.setResultObjects(valueList);
		return searchResult;
	}

	public Map<Key, Value> getByKeys(List<Key> valueKeyList)
			throws AppException {
		return service.getByKeys(valueKeyList);
	}

	@Override
	public EntityMetaData getEntityMetaData() {
		return service.getEntityMetaData();
	}

	@Override
	public SearchResult doSearch(FilterInfo filterInfo, DataContext dataContext) throws AppException {
		SearchResult searchResult = searchManager.doSearch(Value.class,
				service.getEntityMetaData(), filterInfo, dataContext);
		List<Object> objectList = searchResult.getResultObjects();
		List<Object> valueList = (List<Object>) service
				.populateRelationshipFields(objectList);
		searchResult.setResultObjects(valueList);
		return searchResult;
	}
	@Override
	public Object restoreObject(Object object) throws AppException {
		return service.restoreEntity(object);
	}

}
