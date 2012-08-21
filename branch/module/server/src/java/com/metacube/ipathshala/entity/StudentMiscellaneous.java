package com.metacube.ipathshala.entity;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.globalfilter.GlobalFilter;

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class StudentMiscellaneous {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String rollNumber;

	@Persistent
	private Double height;

	@Persistent
	private Double weight;

	@Persistent
	private String leftVision;

	@Persistent
	private String rightVision;

	@Persistent
	private String dentalHygiene;

	@Persistent
	private String myGoals;

	@Persistent
	private String strengths;

	@NotPersistent
	private AcademicYear academicYear;

	@Persistent
	private boolean isDeleted;
	
	@Persistent
	private Key studentKey;

	@NotPersistent
	private Student student;

	@Persistent
	private Key academicYearKey;

	@Persistent
	private Date lastModifiedDate;

	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;
	
	@Persistent
	private String interestAndHobbies;

	@Persistent
	private String exceptionalAchievements;
	
	@Persistent
	private BlobKey blobKey;
	
	@NotPersistent
    private Blob image;


	public boolean isDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public AcademicYear getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(AcademicYear academicYear) {
		this.academicYear = academicYear;
	}


	@Override
	public String toString() {
		return "StudentMiscellaneous [key=" + key + ", rollNumber="
				+ rollNumber + ", height=" + height + ", weight=" + weight
				+ ", leftVision=" + leftVision + ", rightVision=" + rightVision
				+ ", dentalHygiene=" + dentalHygiene + ", myGoals=" + myGoals
				+ ", strengths=" + strengths + ", academicYear=" + academicYear
				+ ", interestAndHobbies=" + interestAndHobbies
				+ ", exceptionalAchievements=" + exceptionalAchievements
				+ ", studentKey=" + studentKey + ", student=" + student
				+ ", academicYearKey=" + academicYearKey
				+ ", lastModifiedDate=" + lastModifiedDate + ", createdDate="
				+ createdDate + ", createdBy=" + createdBy
				+ ", lastModifiedBy=" + lastModifiedBy + "]";
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getLeftVision() {
		return leftVision;
	}

	public void setLeftVision(String leftVision) {
		this.leftVision = leftVision;
	}

	public String getRightVision() {
		return rightVision;
	}

	public void setRightVision(String rightVision) {
		this.rightVision = rightVision;
	}

	public String getDentalHygiene() {
		return dentalHygiene;
	}

	public void setDentalHygiene(String dentalHygiene) {
		this.dentalHygiene = dentalHygiene;
	}

	public String getMyGoals() {
		return myGoals;
	}

	public void setMyGoals(String myGoals) {
		this.myGoals = myGoals;
	}

	public String getStrengths() {
		return strengths;
	}

	public void setStrengths(String strengths) {
		this.strengths = strengths;
	}

	public String getInterestAndHobbies() {
		return interestAndHobbies;
	}

	public void setInterestAndHobbies(String interestAndHobbies) {
		this.interestAndHobbies = interestAndHobbies;
	}

	public String getExceptionalAchievements() {
		return exceptionalAchievements;
	}

	public void setExceptionalAchievements(String exceptionalAchievements) {
		this.exceptionalAchievements = exceptionalAchievements;
	}

	public Key getStudentKey() {
		return studentKey;
	}

	public void setStudentKey(Key studentKey) {
		this.studentKey = studentKey;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Key getAcademicYearKey() {
		return academicYearKey;
	}

	public void setAcademicYearKey(Key academicYearKey) {
		this.academicYearKey = academicYearKey;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public BlobKey getBlobKey() {
		return blobKey;
	}

	public void setBlobKey(BlobKey blobKey) {
		this.blobKey = blobKey;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	

}
