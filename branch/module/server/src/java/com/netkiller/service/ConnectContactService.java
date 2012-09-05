package com.netkiller.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.google.appengine.api.datastore.Key;
import com.netkiller.GridRequest;
import com.netkiller.core.AppException;
import com.netkiller.dao.ConnectContactDao;
import com.netkiller.entity.ConnectContact;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.entity.metadata.impl.ConnectContactMetaData;
import com.netkiller.search.FilterRecordRange;
import com.netkiller.search.SearchCriteria;
import com.netkiller.search.SearchRequest;
import com.netkiller.search.SearchResult;
import com.netkiller.search.SearchService;
import com.netkiller.search.SearchRequest.ResultType;
import com.netkiller.search.property.SearchProperty;
import com.netkiller.search.property.StringSearchProperty;
import com.netkiller.search.property.operator.FilterGroupOperatorType;
import com.netkiller.search.property.operator.FilterOperatorType;
import com.netkiller.util.AppLogger;

@Service
public class ConnectContactService extends AbstractService{
	
	private static final AppLogger log = AppLogger
	.getLogger(ConnectContactService.class);
	
	@Autowired
	@Qualifier("ConnectContactMetaData")
	private EntityMetaData entityMetaData;
	
	@Autowired
	private SearchService searchService;
	
	public EntityMetaData getEntityMetaData() {
		return entityMetaData;
	}

	public ConnectContactDao getConnectContactDao() {
		return connectContactDao;
	}

	@Autowired
	private ConnectContactDao connectContactDao;
	
	public ConnectContact create(ConnectContact object) {
		return connectContactDao.create(object);
	}

	public void remove(Key key) {
		connectContactDao.remove( key);

	}
	
	public List<ConnectContact> getByUrl(String url) {
		return connectContactDao.getByUrl(url);
	}
	
	public ConnectContact getByKey(Key key) {
		return connectContactDao.get( key);
	}

	public String getDomainName(String randomUrl) throws AppException {
		SearchProperty stringProperty = new StringSearchProperty(ConnectContactMetaData.COL_RANDOM_URL, randomUrl, FilterOperatorType.EQUAL, false);
		List<SearchProperty> searchProperties = new ArrayList<SearchProperty>();
		searchProperties.add(stringProperty);
		SearchCriteria searchCriteria = new SearchCriteria(searchProperties, ConnectContact.class);
		FilterRecordRange range = new FilterRecordRange(0, 1);
		SearchRequest request = new SearchRequest(searchCriteria, null, null, FilterGroupOperatorType.AND, range, ResultType.Entity);
		SearchResult result = searchService.doSearch(request);
		ConnectContact connectContact = (ConnectContact) result.getResultObjects().get(0);
		return connectContact.getDomainName();
	}

	
	
}
