package com.netkiller.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.netkiller.GridRequest;
import com.netkiller.core.DataContext;
import com.netkiller.core.FetchType;
import com.netkiller.entity.metadata.impl.GlobalFilterType;
import com.netkiller.globalfilter.GlobalFilter;
import com.netkiller.globalfilter.GlobalFilterMap;

public class IpathshalaListHandler extends ValueListHandler implements Serializable {

	public void setResultList(List<Object> resultList) {
		this.resultList = resultList;
	}

	// use ProjectTO as a template to determine
	// search criteria
	private GridRequest projectCriteria;
	private List<Object> resultList;
	private Class<?> entityType;

	private DataContext dataContext;

	// Client creates a ProjectTO instance, sets the
	// values to use for search criteria and passes
	// the ProjectTO instance as projectCriteria
	// to the constructor and to setCriteria() method

	public void setDataContext(DataContext dataContext) {
		this.dataContext = dataContext;
	}

	public void setEntityType(Class<?> entityType) {
		this.entityType = entityType;
	}

	public IpathshalaListHandler() {
		this.projectCriteria = null;
		this.resultList = null;
	}

	public IpathshalaListHandler(GridRequest projectCriteria,
			List<Object> resultList) {
		try {
			this.projectCriteria = projectCriteria;
			this.resultList = resultList;
			executeSearch();
		} catch (Exception e) {
			// Handle exception, throw ListHandlerException
		}
	}

	public void setCriteria(GridRequest projectCriteria) {
		this.projectCriteria = projectCriteria;
	}

	public void initializeAndExecute(GridRequest projectCriteria,
			List<Object> resultList, Class<?> entityType,
			DataContext dataContext) {
		try {
			this.projectCriteria = projectCriteria;
			this.resultList = resultList;
			this.entityType = entityType;
			if (dataContext != null) {
				this.dataContext = new DataContext();
				if (dataContext.getGlobalFilterMap() != null) {
					this.dataContext.setGlobalFilterMap(new GlobalFilterMap(
							new HashMap<GlobalFilterType, GlobalFilter>(
									dataContext.getGlobalFilterMap()
											.getGlobalFilterHashMap())));
				}
				if (dataContext.getDefaultAcademicYear() != null) {
					this.dataContext.setDefaultAcademicYear(dataContext
							.getDefaultAcademicYear());
				}
			}
			executeSearch();
		} catch (Exception e) {
			// Handle exception, throw ListHandlerException
		}
	}

	// executes search. Client can invoke this
	// provided that the search criteria has been
	// properly set. Used to perform search to refresh
	// the list with the latest data.
	public void executeSearch() {
		try {

			setList(resultList);
		} catch (Exception e) {
			// Handle exception, throw ListHandlerException
		}
	}

	public boolean isValidDataCriteria(GridRequest projectCriteria,
			Class<?> entityType, DataContext dataContext) {
		boolean isValid = false;
		if (this.projectCriteria != null && this.entityType.equals(entityType)) {
			if (this.projectCriteria.getFilterInfo() != null) {
				if (this.projectCriteria.getFilterInfo().equals(
						projectCriteria.getFilterInfo())
						&& (this.projectCriteria.getSortInfo()
								.equals(projectCriteria.getSortInfo()))) {
					isValid = true;
				}
			} else {
				if (projectCriteria.getFilterInfo() == null
						&& this.projectCriteria.getSortInfo().equals(
								projectCriteria.getSortInfo())) {
					isValid = true;
				}
			}
		}
		if (isValid) {
			if (this.dataContext != null) {
				if (!this.dataContext.equals(dataContext)) {
					isValid = false;
				}
			} else {
				if (dataContext != null) {
					isValid = false;
				}
			}
		}
		if (!isValid)
			System.out.println("Not valid data criteria");

		return isValid;
	}
}