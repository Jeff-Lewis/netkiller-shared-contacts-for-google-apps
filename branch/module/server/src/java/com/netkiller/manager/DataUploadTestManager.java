
package com.netkiller.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.netkiller.core.AppException;

@Component
public class DataUploadTestManager {
	
	private List<String> students;
	
	private List<String> newStudents;

	public List<String> getStudents() {
		return students;
	}

	public void setStudents(List<String> students) {
		this.students = students;
	}

	
	public List<String> getNewStudents() {
		return newStudents;
	}

	public void setNewStudents(List<String> newStudents) {
		this.newStudents = newStudents;
	}


}
