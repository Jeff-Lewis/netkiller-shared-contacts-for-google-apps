/**
 *
 */
package com.netkiller.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Component;

import com.netkiller.FilterInfo;
import com.netkiller.GridRequest;
import com.netkiller.PaginationInfo;
import com.netkiller.SortInfo;
import com.netkiller.UICommonConstants;
import com.netkiller.search.property.operator.InputFilterGroupOperatorType;
import com.netkiller.search.property.operator.InputFilterOperatorType;

/**
 * A parser class which fetches the required data from the request object making
 * the controller less cluttered and handling parameter based value object
 * population.
 * 
 * @author vnarang
 * 
 */
@Component
public class GridRequestParser {

	private static ObjectMapper mapper = new ObjectMapper();
	/**
	 * Parse http servlet request to get data criteria out of it.
	 * 
	 * @param request
	 * @return
	 */
	public GridRequest parseDataCriteria(HttpServletRequest request) {
		GridRequest gridRequest = new GridRequest();
		
		// Setting sort criteria
		gridRequest.setSortInfo(getSortInfo(request));

		boolean isSearch = Boolean.valueOf(request.getParameter("_search"));
		String advanceSearchTerm=request
		.getParameter(UICommonConstants.ATTRIB_ADV_SEARCH_TXT);	
		if (isSearch) {
			// Setting search criteria
			gridRequest.setFilterInfo(getFilterInfo(request));
			gridRequest.setSearch(true);
		}else if(advanceSearchTerm!=null && !StringUtils.isEmpty(advanceSearchTerm)){
			gridRequest.setAdvancedSearchTerm(true);
			gridRequest.setAdvanceSearchTerm(advanceSearchTerm);
		}
		else{
			gridRequest.setSort(true);
		}

		// Setting pagination info
		gridRequest.setPaginationInfo(getPaginationInfo(request));

		return gridRequest;
	}

	/**
	 * Parse http servlet request to get sort criteria.
	 * 
	 * @param request
	 * @return
	 */
	private SortInfo getSortInfo(HttpServletRequest request) {
		SortInfo sortCriteria = new SortInfo();
		String sortOn = request.getParameter("sidx");
		sortCriteria.setSortField(StringUtils.isBlank(sortOn) ? "key" : sortOn);
		sortCriteria.setSortOrder(request.getParameter("sord"));

		return sortCriteria;
	}

	/**
	 * Parse http servlet request to get Pagination info.
	 * 
	 * @param request
	 * @return
	 */
	private PaginationInfo getPaginationInfo(HttpServletRequest request) {
		PaginationInfo info = new PaginationInfo();
		int page = Integer.parseInt(request.getParameter("page"));

		info.setPageNumber(page > 0 ? page : 1);
		info.setRecordsPerPage(Integer.parseInt(request.getParameter("rows")));

		return info;
	}

	/**
	 * Parse http servlet request to get search criteria.
	 * 
	 * @param request
	 * @return
	 */
	private FilterInfo getFilterInfo(HttpServletRequest request) {
		FilterInfo gridFilter = new FilterInfo();
		String jsonSearchCriteriaString = request.getParameter("filters");

		if (jsonSearchCriteriaString != null && !jsonSearchCriteriaString.isEmpty()) {
			try {
				gridFilter = mapper.readValue(jsonSearchCriteriaString, FilterInfo.class);

			} catch (JsonMappingException e) {
				// TODO throw app exception
				e.printStackTrace();
			} catch (JsonParseException e) {
				// TODO throw app exception
				e.printStackTrace();
			} catch (IOException e) {
				// TODO throw app exception
				e.printStackTrace();
			}
		}
		else	{
			
			if (request.getParameterMap().containsKey("searchField")
					&& !StringUtils.isEmpty(request.getParameter("searchField"))) {
				FilterInfo.Rule rule = null;
				rule = new FilterInfo.Rule();
				rule.setField(request.getParameter("searchField"));
				rule.setData(request.getParameter("searchString"));
				rule.setOp(request.getParameter("searchOper"));
				List<FilterInfo.Rule> ruleList = new ArrayList<FilterInfo.Rule>();
				ruleList.add(rule);
				gridFilter.setRules(ruleList);
				gridFilter.setGroupOp(InputFilterGroupOperatorType.AND);
			}
		}
			
		
		
		return gridFilter;
	}

}
