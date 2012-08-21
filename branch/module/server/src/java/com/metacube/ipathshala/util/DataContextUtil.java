package com.metacube.ipathshala.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.metacube.ipathshala.ServerCommonConstant;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.entity.AcademicYear;
import com.metacube.ipathshala.entity.metadata.impl.GlobalFilterType;
import com.metacube.ipathshala.globalfilter.Delete;
import com.metacube.ipathshala.globalfilter.GlobalFilter;
import com.metacube.ipathshala.globalfilter.GlobalFilterMap;

public class DataContextUtil {
	private static final AppLogger log = AppLogger.getLogger(DataContextUtil.class);
    /**
     * This Method is deprecated
     * Use the other method containing two parameters.
     * @param academicYear
     * @return
     * @throws AppException
     */
	@Deprecated
	public static DataContext getDataContextByAcademicYear(AcademicYear academicYear) throws AppException {
		DataContext destDataContext = new DataContext();
		Map<GlobalFilterType, GlobalFilter> globalFilterHashMap = new HashMap<GlobalFilterType, GlobalFilter>();
		com.metacube.ipathshala.globalfilter.AcademicYear destYear = new com.metacube.ipathshala.globalfilter.AcademicYear(
				academicYear.getKey(), academicYear.getFromDate(), academicYear.getToDate(), academicYear.getActive());
		globalFilterHashMap.put(GlobalFilterType.GLOBAL_ACADEMIC_YEAR, destYear);
		GlobalFilterMap globalFilterMap = new GlobalFilterMap(globalFilterHashMap);
		destDataContext.setGlobalFilterMap(globalFilterMap);

		return destDataContext;

	}
	
	/**
	 * This method is used in place of deprecated the above one.
	 * @param academicYear
	 * @param isDeleted
	 * @return
	 * @throws AppException
	 */
	public static DataContext getDataContextByAcademicYear(AcademicYear academicYear,Boolean isDeleted) throws AppException {
		DataContext destDataContext = new DataContext();
		Map<GlobalFilterType, GlobalFilter> globalFilterHashMap = new HashMap<GlobalFilterType, GlobalFilter>();
		com.metacube.ipathshala.globalfilter.AcademicYear destYear = new com.metacube.ipathshala.globalfilter.AcademicYear(
				academicYear.getKey(), academicYear.getFromDate(), academicYear.getToDate(), academicYear.getActive());
		
		globalFilterHashMap.put(GlobalFilterType.GLOBAL_ACADEMIC_YEAR, destYear);
		globalFilterHashMap.put(GlobalFilterType.DELETE, new Delete(isDeleted));
		GlobalFilterMap globalFilterMap = new GlobalFilterMap(globalFilterHashMap);
		destDataContext.setGlobalFilterMap(globalFilterMap);

		return destDataContext;

	}

	public static DataContext getDataContext(HttpSession session) {
		
		return (DataContext) session.getAttribute(ServerCommonConstant.ATTRIB_DATA_CONTEXT);
	}

}
