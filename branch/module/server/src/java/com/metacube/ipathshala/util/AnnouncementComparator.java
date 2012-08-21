package com.metacube.ipathshala.util;

import java.util.Comparator;

import com.metacube.ipathshala.entity.Announcement;

public class AnnouncementComparator implements Comparator<Announcement> {
	private static final AppLogger log = AppLogger.getLogger(AnnouncementComparator.class);
	@Override
	public int compare(Announcement o1, Announcement o2) {
		int result = 0;
		
		if(o1==null || o2 ==null)
			return result;
		
		result = o1.getLastModifiedDate().compareTo(o2.getLastModifiedDate());		
		
		return result;
	}

}
