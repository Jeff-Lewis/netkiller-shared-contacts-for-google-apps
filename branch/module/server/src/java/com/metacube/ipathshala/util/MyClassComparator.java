package com.metacube.ipathshala.util;

import java.util.Comparator;

import org.apache.commons.lang.StringUtils;

import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.entity.MyClass;

public class MyClassComparator implements Comparator<MyClass> {
	private static final AppLogger log = AppLogger.getLogger(MyClassComparator.class);

	@Override
	public int compare(MyClass myclass1, MyClass myclass2) {
		int result = 0;
		if (myclass1.getLevelValue() != null && myclass2.getLevelValue() != null) {
			try {
				if (Integer.valueOf(myclass1.getLevelValue().getOrderIndex()).compareTo(
						Integer.valueOf(myclass2.getLevelValue().getOrderIndex())) == 0) {
					result = myclass1.getName().compareTo(myclass2.getName());
				} else {
					result = Integer.valueOf(myclass1.getLevelValue().getOrderIndex()).compareTo(Integer.valueOf(myclass2.getLevelValue().getOrderIndex()));
				}
			} catch (NumberFormatException numberFormatException) {
				String message = "Unable to parse class level to Integer";
				log.error(message, numberFormatException);
			}
		} else {
			result = myclass1.getName().toLowerCase().compareTo(myclass2.getName().toLowerCase());
		}
		return result;
	}

}
