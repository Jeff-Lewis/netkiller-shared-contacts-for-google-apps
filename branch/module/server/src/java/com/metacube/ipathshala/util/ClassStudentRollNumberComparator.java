package com.metacube.ipathshala.util;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.entity.ClassStudent;
import com.metacube.ipathshala.entity.Student;
import com.metacube.ipathshala.entity.StudentMiscellaneous;

public class ClassStudentRollNumberComparator implements
		java.util.Comparator<ClassStudent> {

	public Map<Key, StudentMiscellaneous> getStudentMiscMap() {
		return studentMiscMap;
	}

	public void setStudentMiscMap(Map<Key, StudentMiscellaneous> studentMiscMap) {
		this.studentMiscMap = studentMiscMap;
	}

	private Map<Key, StudentMiscellaneous> studentMiscMap;

	public ClassStudentRollNumberComparator(
			Map<Key, StudentMiscellaneous> studentMiscmap) {
		this.studentMiscMap = studentMiscmap;
	}

	@Override
	public int compare(ClassStudent relation1, ClassStudent relation2) {
		final int BLANK_VALUE = 999999999;
		Integer rollNumber1 = BLANK_VALUE;
		Integer rollNumber2 = BLANK_VALUE;
		try {
			if (relation1 != null) {
				Student student1 = relation1.getStudent();
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
			}
		} catch (NumberFormatException numberFormatException) {
			rollNumber1 = BLANK_VALUE;
		}
		try {
			if (relation2 != null) {
				Student student2 = relation2.getStudent();
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

			}
		} catch (NumberFormatException numberFormatException) {
			rollNumber2 = BLANK_VALUE;
		}

		return rollNumber1.compareTo(rollNumber2);
	}

}
