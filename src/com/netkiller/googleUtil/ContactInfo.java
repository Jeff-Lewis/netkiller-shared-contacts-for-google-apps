package com.netkiller.googleUtil;

import java.io.Serializable;
import java.util.Date;

import com.google.appengine.api.datastore.Key;

public class ContactInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2541537116758286949L;
	private String fullname;
	private String givenname;
	private String familyname;
	private String companyname;
	private String companydept;
	private String companytitle;
	private String workemail;
	private String homeemail;
	private String otheremail;
	private String workphone;
	private String homephone;
	private String mobilephone;
	private String workaddress;
	private String homeaddress;
	private String otheraddress;
	private String notes;
	
	
	/* added for db */
	private String domain;
	private String id;
	private Key key;
	private Date modifiedDate;
	/*private String createdBy;
	private String udpatedBy;
	private Date createdDate;
	private Date updatedDate;*/

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getGivenname() {
		return givenname;
	}

	public void setGivenname(String givenname) {
		this.givenname = givenname;
	}

	public String getFamilyname() {
		return familyname;
	}

	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanydept() {
		return companydept;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setCompanydept(String companydept) {
		this.companydept = companydept;
	}

	public String getCompanytitle() {
		return companytitle;
	}

	public void setCompanytitle(String companytitle) {
		this.companytitle = companytitle;
	}


	public String getWorkemail() {
		return workemail;
	}

	public void setWorkemail(String workemail) {
		this.workemail = workemail;
	}

	public String getHomeemail() {
		return homeemail;
	}

	public void setHomeemail(String homeemail) {
		this.homeemail = homeemail;
	}

	public String getOtheremail() {
		return otheremail;
	}

	public void setOtheremail(String otheremail) {
		this.otheremail = otheremail;
	}

	public String getWorkphone() {
		return workphone;
	}

	public void setWorkphone(String workphone) {
		this.workphone = workphone;
	}

	public String getHomephone() {
		return homephone;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setHomephone(String homephone) {
		this.homephone = homephone;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getWorkaddress() {
		return workaddress;
	}

	public void setWorkaddress(String workaddress) {
		this.workaddress = workaddress;
	}

	public String getHomeaddress() {
		return homeaddress;
	}

	public void setHomeaddress(String homeaddress) {
		this.homeaddress = homeaddress;
	}

	public String getOtheraddress() {
		return otheraddress;
	}

	public void setOtheraddress(String otheraddress) {
		this.otheraddress = otheraddress;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "ContactInfo [fullname=" + fullname + ", givenname=" + givenname + ", familyname=" + familyname
				+ ", companyname=" + companyname + ", companydept=" + companydept + ", companytitle=" + companytitle
				+ ", workemail=" + workemail + ", homeemail=" + homeemail + ", otheremail=" + otheremail
				+ ", workphone=" + workphone + ", homephone=" + homephone + ", mobilephone=" + mobilephone
				+ ", workaddress=" + workaddress + ", homeaddress=" + homeaddress + ", otheraddress=" + otheraddress
				+ ", notes=" + notes + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
