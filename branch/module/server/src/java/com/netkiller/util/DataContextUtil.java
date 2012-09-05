package com.netkiller.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.netkiller.ServerCommonConstant;
import com.netkiller.core.AppException;
import com.netkiller.core.DataContext;
import com.netkiller.entity.AcademicYear;
import com.netkiller.entity.metadata.impl.GlobalFilterType;
import com.netkiller.globalfilter.Delete;
import com.netkiller.globalfilter.GlobalFilter;
import com.netkiller.globalfilter.GlobalFilterMap;

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
		com.netkiller.globalfilter.AcademicYear destYear = new com.netkiller.globalfilter.AcademicYear(
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
		com.netkiller.globalfilter.AcademicYear destYear = new com.netkiller.globalfilter.AcademicYear(
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
