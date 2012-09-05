package com.netkiller.core;

import java.io.Serializable;

import com.netkiller.entity.metadata.impl.GlobalFilterType;
import com.netkiller.globalfilter.AcademicYear;
import com.netkiller.globalfilter.GlobalFilterMap;
import com.netkiller.globalfilter.Student;

public class DataContext implements Serializable {
	
	private GlobalFilterMap globalFilterMap;
	private AcademicYear defaultAcademicYear;
	
	
	public AcademicYear getCurrentSelectedAcademicYear()	{
		AcademicYear currentSelectedAcademicYear = null;
		if(this.globalFilterMap !=null)
			currentSelectedAcademicYear = (AcademicYear) (this.globalFilterMap.getGlobalFilterHashMap().get(GlobalFilterType.GLOBAL_ACADEMIC_YEAR));
			return currentSelectedAcademicYear;
	}
	public Student getCurrentSelectedStudent()	{
		Student currentSelectedStudent = null;
		if(this.globalFilterMap !=null)
			currentSelectedStudent = (Student) (this.globalFilterMap.getGlobalFilterHashMap().get(GlobalFilterType.STUDENT));
			return currentSelectedStudent;
	}
	
	public AcademicYear getDefaultAcademicYear()	{
		return defaultAcademicYear;
	}
	
	public void setDefaultAcademicYear(AcademicYear defaultAcademicYear)	{
		this.defaultAcademicYear = defaultAcademicYear;
	}
	
	public void setGlobalFilterMap(GlobalFilterMap globalFilterMap)	{
		this.globalFilterMap = globalFilterMap;
	}
	
	public GlobalFilterMap getGlobalFilterMap()	{
		return this.globalFilterMap;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((defaultAcademicYear == null) ? 0 : defaultAcademicYear.hashCode());
		result = prime * result + ((globalFilterMap == null) ? 0 : globalFilterMap.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataContext other = (DataContext) obj;
		if (defaultAcademicYear == null) {
			if (other.defaultAcademicYear != null)
				return false;
		} else if (!defaultAcademicYear.equals(other.defaultAcademicYear))
			return false;
		if (globalFilterMap == null) {
			if (other.globalFilterMap != null)
				return false;
		} else if (!globalFilterMap.equals(other.globalFilterMap))
			return false;
		return true;
	}

}
