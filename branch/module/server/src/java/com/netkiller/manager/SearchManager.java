/**
 *
 */
package com.netkiller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netkiller.FilterInfo;
import com.netkiller.GridRequest;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.metadata.EntityMetaData;
import com.netkiller.manager.util.SearchRequestAssembler;
import com.netkiller.search.SearchRequest;
import com.netkiller.search.SearchResult;
import com.netkiller.search.SearchService;
import com.netkiller.service.GlobalFilterSearchService;

/**
 * This manager class will handle all request for data fetch, plain or based on
 * some criteria.
 * 
 * @author vnarang
 * 
 */
@Component
public class SearchManager {

	/**
	 * Handle of service taking care of data fetch.
	 */

	@Autowired
	private SearchService dataService;
	
	@Autowired
	private GlobalFilterSearchService globalFilterSearchService;


	/**
	 * @return the dataService
	 */
	public SearchService getDataService() {
		return dataService;
	}

	/**
	 * @param dataService
	 *            the dataService to set
	 */
	public void setDataService(SearchService dataService) {
		this.dataService = dataService;
	}

	/**
	 * This will call the service to search data.
	 * 
	 * @param entityClazz
	 *            the entity type reference
	 * @param dataCriteria
	 *            the criteria to perform search, also having sort and
	 *            pagination info.
	 * @param dataContext TODO
	 * @return the collection of entity found
	 * @throws AppException
	 */
	public SearchResult doSearch(Class entityClazz, EntityMetaData entityMetaData,GridRequest dataCriteria, DataContext dataContext) throws AppException {
		SearchRequest request = SearchRequestAssembler.assemble(entityClazz, entityMetaData, dataCriteria, dataContext);
		SearchResult searchResult = null;
		if(dataContext!=null)
			searchResult = globalFilterSearchService.doSearch(dataContext, entityMetaData, request);
		else
			searchResult = dataService.doSearch(request);
		return searchResult;
	}

	public SearchResult doSearch(Class entityClazz, EntityMetaData entityMetaData, FilterInfo filterInfo, DataContext dataContext) throws AppException {
		SearchRequest request = SearchRequestAssembler.assemble(entityClazz, entityMetaData, filterInfo, dataContext);
		SearchResult searchResult = null;
		if(dataContext!=null)
			searchResult = globalFilterSearchService.doSearch(dataContext, entityMetaData, request);
		else
			searchResult = dataService.doSearch(request);
		return searchResult;
	}
	
	public SearchResult doSearch(SearchRequest searchRequest) throws AppException{
		return dataService.doSearch(searchRequest);
	}

}
