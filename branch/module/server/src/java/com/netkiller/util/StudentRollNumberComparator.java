package com.netkiller.util;

import java.util.Comparator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Key;
import com.netkiller.entity.Student;
import com.netkiller.entity.StudentMiscellaneous;

public class StudentRollNumberComparator implements Comparator<Student> {

	public StudentRollNumberComparator(
			Map<Key, StudentMiscellaneous> studentMiscMap) {
		this.studentMiscMap = studentMiscMap;
	}

	private Map<Key, StudentMiscellaneous> studentMiscMap;

	@Override
	public int compare(Student student1, Student student2) {
		final int BLANK_VALUE = 999999999;
		Integer rollNumber1 = BLANK_VALUE;
		Integer rollNumber2 = BLANK_VALUE;
		try {
			if (student1 != null) {
				if (studentMiscMap.get(student1.getKey()) != null) {
					StudentMiscellaneous studentMiscellaneous1 = studentMiscMap
							.get(student1.getKey());
					if (!StringUtils.isBlank(studentMiscellaneous1
							.getRollNumber())) {
						rollNumber1 = Integer.valueOf(studentMiscellaneous1
								.getRollNumber());
					}
				}
			}
		} catch (NumberFormatException numberFormatException) {
			rollNumber1 = BLANK_VALUE;
		}
		try {
			if (student2 != null) {
				if (studentMiscMap.get(student2.getKey()) != null) {
					StudentMiscellaneous studentMiscellaneous2 = studentMiscMap
							.get(student2.getKey());
					if (!StringUtils.isBlank(studentMiscellaneous2
							.getRollNumber())) {
						rollNumber2 = Integer.valueOf(studentMiscellaneous2
								.getRollNumber());
					}
				}

			}
		} catch (NumberFormatException numberFormatException) {
			rollNumber2 = BLANK_VALUE;
		}

		return rollNumber1.compareTo(rollNumber2);
	}
}
