package com.metacube.ipathshala.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import org.apache.commons.lang.StringUtils;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class EvaluationScheme implements Serializable, StoreCallback {

	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String name;
	
	@Persistent
	private String nameUpperCase;
	
	@Persistent
	private Date fromDate;
	
	@Persistent
	private Date toDate;
	
	@Persistent
	private Boolean template;
	
	@Persistent
	private Key schemeEvalTypeKey;
	
	@Persistent
	private Key gradingScaleKey;
	
	@Persistent
	private Boolean active;
	
	@Persistent
	private Boolean practicalComponent;
	
	public Boolean getPracticalComponent() {
		return practicalComponent;
	}



	public void setPracticalComponent(Boolean practicalComponent) {
		this.practicalComponent = practicalComponent;
	}

	@Persistent
	private Boolean stage11;
	
	@Persistent
	private Boolean stage12;
	
	@Persistent
	private Boolean stage13;
	
	@Persistent
	private Boolean stage14;
	
	@Persistent
	private Boolean stage21;
	
	@Persistent
	private Boolean stage22;
	
	@Persistent
	private Double maxMarks;
	
	public Double getMaxMarks() {
		return maxMarks;
	}



	public void setMaxMarks(Double maxMarks) {
		this.maxMarks = maxMarks;
	}



	public Boolean getStage11() {
		return stage11;
	}



	public void setStage11(Boolean stage11) {
		this.stage11 = stage11;
	}



	public Boolean getStage12() {
		return stage12;
	}



	public void setStage12(Boolean stage12) {
		this.stage12 = stage12;
	}



	public Boolean getStage13() {
		return stage13;
	}



	public void setStage13(Boolean stage13) {
		this.stage13 = stage13;
	}



	public Boolean getStage14() {
		return stage14;
	}



	public void setStage14(Boolean stage14) {
		this.stage14 = stage14;
	}



	public Boolean getStage21() {
		return stage21;
	}



	public void setStage21(Boolean stage21) {
		this.stage21 = stage21;
	}



	public Boolean getStage22() {
		return stage22;
	}



	public void setStage22(Boolean stage22) {
		this.stage22 = stage22;
	}



	public Boolean getStage23() {
		return stage23;
	}



	public void setStage23(Boolean stage23) {
		this.stage23 = stage23;
	}



	public Boolean getStage24() {
		return stage24;
	}



	public void setStage24(Boolean stage24) {
		this.stage24 = stage24;
	}



	public Boolean getStage31() {
		return stage31;
	}



	public void setStage31(Boolean stage31) {
		this.stage31 = stage31;
	}



	public Boolean getStage32() {
		return stage32;
	}



	public void setStage32(Boolean stage32) {
		this.stage32 = stage32;
	}



	public Boolean getStage33() {
		return stage33;
	}



	public void setStage33(Boolean stage33) {
		this.stage33 = stage33;
	}



	public Boolean getStage34() {
		return stage34;
	}



	public void setStage34(Boolean stage34) {
		this.stage34 = stage34;
	}



	public Boolean getStage41() {
		return stage41;
	}



	public void setStage41(Boolean stage41) {
		this.stage41 = stage41;
	}



	public Boolean getStage42() {
		return stage42;
	}



	public void setStage42(Boolean stage42) {
		this.stage42 = stage42;
	}



	public Boolean getStage43() {
		return stage43;
	}



	public void setStage43(Boolean stage43) {
		this.stage43 = stage43;
	}



	public Boolean getStage44() {
		return stage44;
	}



	public void setStage44(Boolean stage44) {
		this.stage44 = stage44;
	}

	@Persistent
	private Boolean stage23;
	
	@Persistent
	private Boolean stage24;
	
	@Persistent
	private Boolean stage31;
	
	@Persistent
	private Boolean stage32;
	
	@Persistent
	private Boolean stage33;
	
	@Persistent
	private Boolean stage34;
	
	@Persistent
	private Boolean stage41;
	
	@Persistent
	private Boolean stage42;
	
	@Persistent
	private Boolean stage43;
	
	@Persistent
	private Boolean stage44;

	@Persistent
	private Boolean valid = false;
	
	@Persistent
	private Double passPercentage ;
	
	@Persistent
	private Integer terms;
	
	@Persistent
	private Double termOneMaxMarks;
	
	@Persistent
	private Double termTwoMaxMarks;
	
	@Persistent
	private Double termThreeMaxMarks;
	
	@Persistent
	private Double termFourMaxMarks;
	
	@Persistent
	private Double stage11Maxmarks;
	
	@Persistent
	private Double stage12Maxmarks;
	
	@Persistent
	private Double stage13Maxmarks;
	
	@Persistent
	private Double stage14Maxmarks;
	
	@Persistent
	private Double stage21Maxmarks;
	
	@Persistent
	private Double stage22Maxmarks;
	
	@Persistent
	private Double stage23Maxmarks;
	
	@Persistent
	private Double stage24Maxmarks;
	
	@Persistent
	private Double stage31Maxmarks;
	
	@Persistent
	private Double stage32Maxmarks;
	
	@Persistent
	private Double stage33Maxmarks;
	
	@Persistent
	private Double stage34Maxmarks;
	
	@Persistent
	private Double stage41Maxmarks;
	
	@Persistent
	private Double stage42Maxmarks;
	
	@Persistent
	private Double stage43Maxmarks;
	
	@Persistent
	private Double stage44Maxmarks;
	
	public Double getStage11Maxmarks() {
		return stage11Maxmarks;
	}



	public void setStage11Maxmarks(Double stage11Maxmarks) {
		this.stage11Maxmarks = stage11Maxmarks;
	}



	public Double getStage12Maxmarks() {
		return stage12Maxmarks;
	}



	public void setStage12Maxmarks(Double stage12Maxmarks) {
		this.stage12Maxmarks = stage12Maxmarks;
	}



	public Double getStage13Maxmarks() {
		return stage13Maxmarks;
	}



	public void setStage13Maxmarks(Double stage13Maxmarks) {
		this.stage13Maxmarks = stage13Maxmarks;
	}



	public Double getStage14Maxmarks() {
		return stage14Maxmarks;
	}



	public void setStage14Maxmarks(Double stage14Maxmarks) {
		this.stage14Maxmarks = stage14Maxmarks;
	}



	public Double getStage21Maxmarks() {
		return stage21Maxmarks;
	}



	public void setStage21Maxmarks(Double stage21Maxmarks) {
		this.stage21Maxmarks = stage21Maxmarks;
	}



	public Double getStage22Maxmarks() {
		return stage22Maxmarks;
	}



	public void setStage22Maxmarks(Double stage22Maxmarks) {
		this.stage22Maxmarks = stage22Maxmarks;
	}



	public Double getStage23Maxmarks() {
		return stage23Maxmarks;
	}



	public void setStage23Maxmarks(Double stage23Maxmarks) {
		this.stage23Maxmarks = stage23Maxmarks;
	}



	public Double getStage24Maxmarks() {
		return stage24Maxmarks;
	}



	public void setStage24Maxmarks(Double stage24Maxmarks) {
		this.stage24Maxmarks = stage24Maxmarks;
	}



	public Double getStage31Maxmarks() {
		return stage31Maxmarks;
	}



	public void setStage31Maxmarks(Double stage31Maxmarks) {
		this.stage31Maxmarks = stage31Maxmarks;
	}



	public Double getStage32Maxmarks() {
		return stage32Maxmarks;
	}



	public void setStage32Maxmarks(Double stage32Maxmarks) {
		this.stage32Maxmarks = stage32Maxmarks;
	}



	public Double getStage33Maxmarks() {
		return stage33Maxmarks;
	}



	public void setStage33Maxmarks(Double stage33Maxmarks) {
		this.stage33Maxmarks = stage33Maxmarks;
	}



	public Double getStage34Maxmarks() {
		return stage34Maxmarks;
	}



	public void setStage34Maxmarks(Double stage34Maxmarks) {
		this.stage34Maxmarks = stage34Maxmarks;
	}



	public Double getStage41Maxmarks() {
		return stage41Maxmarks;
	}



	public void setStage41Maxmarks(Double stage41Maxmarks) {
		this.stage41Maxmarks = stage41Maxmarks;
	}



	public Double getStage42Maxmarks() {
		return stage42Maxmarks;
	}



	public void setStage42Maxmarks(Double stage42Maxmarks) {
		this.stage42Maxmarks = stage42Maxmarks;
	}



	public Double getStage43Maxmarks() {
		return stage43Maxmarks;
	}



	public void setStage43Maxmarks(Double stage43Maxmarks) {
		this.stage43Maxmarks = stage43Maxmarks;
	}



	public Double getStage44Maxmarks() {
		return stage44Maxmarks;
	}



	public void setStage44Maxmarks(Double stage44Maxmarks) {
		this.stage44Maxmarks = stage44Maxmarks;
	}

	@Persistent
	private Boolean isDeleted = false;
	
	@Persistent
	private Date lastModifiedDate;

	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;
	
	@Persistent
	private Integer stages;
	
	
	@NotPersistent
	private Value schemeEvalType;
	
	@NotPersistent
	private GradingScale gradingScale;
	

	

	@Persistent
	private Boolean isApplied=false;
	
	public Boolean getIsApplied() {
		return isApplied;
	}



	public void setIsApplied(Boolean isApplied) {
		this.isApplied = isApplied;
	}




	public Boolean getTemplate() {
		return template;
	}



	public GradingScale getGradingScale() {
		return gradingScale;
	}



	public void setGradingScale(GradingScale gradingScale) {
		this.gradingScale = gradingScale;
	}



	public void setTemplate(Boolean template) {
		this.template = template;
	}



	public Integer getTerms() {
		return terms;
	}



	public void setTerms(Integer terms) {
		this.terms = terms;
	}

	public Key getKey() {
		return key;
	}



	public void setKey(Key key) {
		this.key = key;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getNameUpperCase() {
		return nameUpperCase;
	}



	public void setNameUpperCase(String nameUpperCase) {
		this.nameUpperCase = nameUpperCase;
	}

	public Key getSchemeEvalTypeKey() {
		return schemeEvalTypeKey;
	}



	public Date getFromDate() {
		return fromDate;
	}



	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}



	public Date getToDate() {
		return toDate;
	}



	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}





	public void setSchemeEvalTypeKey(Key schemeEvalTypeKey) {
		this.schemeEvalTypeKey = schemeEvalTypeKey;
	}



	public Value getSchemeEvalType() {
		return schemeEvalType;
	}



	public void setSchemeEvalType(Value schemeEvalType) {
		this.schemeEvalType = schemeEvalType;
	}



	public Key getGradingScaleKey() {
		return gradingScaleKey;
	}



	public void setGradingScaleKey(Key gradingScaleKey) {
		this.gradingScaleKey = gradingScaleKey;
	}



	public Boolean getActive() {
		return active;
	}



	public void setActive(Boolean active) {
		this.active = active;
	}



	public Boolean getValid() {
		return valid;
	}



	public void setValid(Boolean valid) {
		this.valid = valid;
	}



	public Double getPassPercentage() {
		return passPercentage;
	}



	public void setPassPercentage(Double passPercentage) {
		this.passPercentage = passPercentage;
	}



	public Double getTermOneMaxMarks() {
		return termOneMaxMarks;
	}



	public void setTermOneMaxMarks(Double termOneMaxMarks) {
		this.termOneMaxMarks = termOneMaxMarks;
	}



	public Double getTermTwoMaxMarks() {
		return termTwoMaxMarks;
	}



	public void setTermTwoMaxMarks(Double termTwoMaxMarks) {
		this.termTwoMaxMarks = termTwoMaxMarks;
	}



	public Double getTermThreeMaxMarks() {
		return termThreeMaxMarks;
	}



	public void setTermThreeMaxMarks(Double termThreeMaxMarks) {
		this.termThreeMaxMarks = termThreeMaxMarks;
	}



	public Double getTermFourMaxMarks() {
		return termFourMaxMarks;
	}



	public void setTermFourMaxMarks(Double termFourMaxMarks) {
		this.termFourMaxMarks = termFourMaxMarks;
	}



	public Boolean getIsDeleted() {
		return isDeleted;
	}



	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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
	
	public Integer getStages() {
		return stages;
	}

	public void setStages(Integer stages) {
		this.stages = stages;
	}

	@Override
	public void jdoPreStore() {
		if (!StringUtils.isBlank(name)) {
			nameUpperCase = name.toUpperCase();
		} else {
			nameUpperCase = null;
		}
	}


}
