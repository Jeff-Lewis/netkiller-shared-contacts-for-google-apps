package com.metacube.ipathshala.util;

import java.util.Comparator;

import com.metacube.ipathshala.entity.Period;

public class PeriodComparator implements Comparator<Period> {
	
	private static final AppLogger log = AppLogger.getLogger(PeriodComparator.class);

	@Override
	public int compare(Period o1, Period o2) {
		int result = 0;
		try{
		int stHour1 = Integer.parseInt(o1.getStFromHour());
		int stHour2 = Integer.parseInt(o2.getStFromHour());

		if (stHour1 == stHour2) 
		{
			int stMin1 = Integer.parseInt(o1.getStFromMin());
			int stMin2 = Integer.parseInt(o2.getStFromMin());
			if (stMin1 > stMin2)
			{
				result = 1;
			}
			else if (stMin1 == stMin2) 
			{
				result = 0;
			} 
			else
			{
				result = -1;
			}

		}
		else if (stHour1 > stHour2) 
		{
			result = 1;
		} 
		else
		{
			result = -1;
		}
		}catch(NumberFormatException nfe)
		{
			log.error("Unable to parse From Time",nfe);
		}
		return result;

	}
}
