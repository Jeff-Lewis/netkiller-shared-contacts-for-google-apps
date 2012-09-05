package com.netkiller.util;

import java.util.Comparator;

import com.netkiller.entity.ReportCardEvent;

public class ReportCardEventComparator implements Comparator<ReportCardEvent> {
	private static final AppLogger log = AppLogger.getLogger(ReportCardEventComparator.class);
	@Override
	public int compare(ReportCardEvent o1, ReportCardEvent o2) {
		int result = 0;
		
		if(o1==null || o2 ==null)
			return result;
		
		result = o1.getLastModifiedDate().compareTo(o2.getLastModifiedDate());		
		
		return result;
	}

}
