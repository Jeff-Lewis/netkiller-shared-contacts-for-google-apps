package com.netkiller.entity;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.listener.StoreCallback;

import com.google.appengine.api.datastore.Key;
import com.google.gdata.data.DateTime;

/**
 * evaluation scheme component entity.
 * 
 * @author Jitender
 * 
 */
@PersistenceCapable(detachable = "true", identityType = IdentityType.APPLICATION)
public class EvaluationComponent implements Serializable, StoreCallback {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private Key evalSchemeKey;

	@Persistent
	private String componentName;

	@Persistent
	private Key componentTypeKey;
	
	@Persistent
	private Key componentStructureKey;
	
	@Persistent
	private Key componentCategoryKey;
	
	@Persistent
	private Key componentCalcMethodKey;
	
	public Key getComponentCalcMethodKey() {
		return componentCalcMethodKey;
	}



	public void setComponentCalcMethodKey(Key componentCalcMethodKey) {
		this.componentCalcMethodKey = componentCalcMethodKey;
	}

	@Persistent
	String calculationMethod;
	
	@Persistent
	private Integer displaySequence = 0;

	@Persistent
	private Boolean applyMinimum;
	
	@Persistent
	private Boolean termOneApplicability;
	
	@Persistent
	private Boolean termTwoApplicability;
	
	@Persistent
	private Boolean termThreeApplicability;
	
	@Persistent
	private Boolean termFourApplicability;
	
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
	private String remarks;
	
	@Persistent
	private String desctiption;
	
	public String getRemarks() {
		return remarks;
	}



	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



	public String getDesctiption() {
		return desctiption;
	}



	public void setDesctiption(String desctiption) {
		this.desctiption = desctiption;
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
	private Double maxMarks ;
	

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






	public Double getMaxMarks() {
		return maxMarks;
	}



	public void setMaxMarks(Double maxMarks) {
		this.maxMarks = maxMarks;
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
	private Boolean active = true;
	
	@Persistent
	private Date lastModifiedDate;

	@Persistent
	private Date createdDate;

	@Persistent
	private String createdBy;

	@Persistent
	private String lastModifiedBy;
	
	@NotPersistent
	private EvaluationScheme evaluationScheme;
	
	@NotPersistent
	private Value componentType;
	
	@Persistent
	private Boolean isTheory;
	
	

	/**
	 * @return the isTheory
	 */
	public Boolean getIsTheory() {
		return isTheory;
	}



	/**
	 * @param isTheory the isTheory to set
	 */
	public void setIsTheory(Boolean isTheory) {
		this.isTheory = isTheory;
	}

	@NotPersistent
	private Value componentStructure;
	
	@NotPersistent
	private Value componentCategory;
	
	@NotPersistent
	private Value componentCalcMethod;

	@Persistent
	private Boolean isDeleted = false;
	
	/**
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}
 
	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}



	public EvaluationScheme getEvaluationScheme() {
		return evaluationScheme;
	}

	public void setEvaluationScheme(EvaluationScheme evaluationScheme) {
		this.evaluationScheme = evaluationScheme;
	}

	public Value getComponentType() {
		return componentType;
	}

	public void setComponentType(Value componentType) {
		this.componentType = componentType;
	}

	public Value getComponentStructure() {
		return componentStructure;
	}

	public void setComponentStructure(Value componentStructure) {
		this.componentStructure = componentStructure;
	}

	public Value getComponentCategory() {
		return componentCategory;
	}

	public void setComponentCategory(Value componentCategory) {
		this.componentCategory = componentCategory;
	}

	public Value getComponentCalcMethod() {
		return componentCalcMethod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((active == null) ? 0 : active.hashCode());
		result = prime * result + ((applyMinimum == null) ? 0 : applyMinimum.hashCode());
		result = prime * result + ((componentCalcMethodKey == null) ? 0 : componentCalcMethodKey.hashCode());
		result = prime * result + ((componentCategoryKey == null) ? 0 : componentCategoryKey.hashCode());
		result = prime * result + ((componentName == null) ? 0 : componentName.hashCode());
		result = prime * result + ((componentStructureKey == null) ? 0 : componentStructureKey.hashCode());
		result = prime * result + ((componentTypeKey == null) ? 0 : componentTypeKey.hashCode());
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((desctiption == null) ? 0 : desctiption.hashCode());
		result = prime * result + ((displaySequence == null) ? 0 : displaySequence.hashCode());
		result = prime * result + ((evalSchemeKey == null) ? 0 : evalSchemeKey.hashCode());
		result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((lastModifiedBy == null) ? 0 : lastModifiedBy.hashCode());
		result = prime * result + ((lastModifiedDate == null) ? 0 : lastModifiedDate.hashCode());
		result = prime * result + ((remarks == null) ? 0 : remarks.hashCode());
		result = prime * result + ((stage11 == null) ? 0 : stage11.hashCode());
		result = prime * result + ((stage11Maxmarks == null) ? 0 : stage11Maxmarks.hashCode());
		result = prime * result + ((stage12 == null) ? 0 : stage12.hashCode());
		result = prime * result + ((stage12Maxmarks == null) ? 0 : stage12Maxmarks.hashCode());
		result = prime * result + ((stage13 == null) ? 0 : stage13.hashCode());
		result = prime * result + ((stage13Maxmarks == null) ? 0 : stage13Maxmarks.hashCode());
		result = prime * result + ((stage14 == null) ? 0 : stage14.hashCode());
		result = prime * result + ((stage14Maxmarks == null) ? 0 : stage14Maxmarks.hashCode());
		result = prime * result + ((stage21 == null) ? 0 : stage21.hashCode());
		result = prime * result + ((stage21Maxmarks == null) ? 0 : stage21Maxmarks.hashCode());
		result = prime * result + ((stage22 == null) ? 0 : stage22.hashCode());
		result = prime * result + ((stage22Maxmarks == null) ? 0 : stage22Maxmarks.hashCode());
		result = prime * result + ((stage23 == null) ? 0 : stage23.hashCode());
		result = prime * result + ((stage23Maxmarks == null) ? 0 : stage23Maxmarks.hashCode());
		result = prime * result + ((stage24 == null) ? 0 : stage24.hashCode());
		result = prime * result + ((stage24Maxmarks == null) ? 0 : stage24Maxmarks.hashCode());
		result = prime * result + ((stage31 == null) ? 0 : stage31.hashCode());
		result = prime * result + ((stage31Maxmarks == null) ? 0 : stage31Maxmarks.hashCode());
		result = prime * result + ((stage32 == null) ? 0 : stage32.hashCode());
		result = prime * result + ((stage32Maxmarks == null) ? 0 : stage32Maxmarks.hashCode());
		result = prime * result + ((stage33 == null) ? 0 : stage33.hashCode());
		result = prime * result + ((stage33Maxmarks == null) ? 0 : stage33Maxmarks.hashCode());
		result = prime * result + ((stage34 == null) ? 0 : stage34.hashCode());
		result = prime * result + ((stage34Maxmarks == null) ? 0 : stage34Maxmarks.hashCode());
		result = prime * result + ((stage41 == null) ? 0 : stage41.hashCode());
		result = prime * result + ((stage41Maxmarks == null) ? 0 : stage41Maxmarks.hashCode());
		result = prime * result + ((stage42 == null) ? 0 : stage42.hashCode());
		result = prime * result + ((stage42Maxmarks == null) ? 0 : stage42Maxmarks.hashCode());
		result = prime * result + ((stage43 == null) ? 0 : stage43.hashCode());
		result = prime * result + ((stage43Maxmarks == null) ? 0 : stage43Maxmarks.hashCode());
		result = prime * result + ((stage44 == null) ? 0 : stage44.hashCode());
		result = prime * result + ((stage44Maxmarks == null) ? 0 : stage44Maxmarks.hashCode());
		result = prime * result + ((termFourApplicability == null) ? 0 : termFourApplicability.hashCode());
		result = prime * result + ((termFourMaxMarks == null) ? 0 : termFourMaxMarks.hashCode());
		result = prime * result + ((termOneApplicability == null) ? 0 : termOneApplicability.hashCode());
		result = prime * result + ((termOneMaxMarks == null) ? 0 : termOneMaxMarks.hashCode());
		result = prime * result + ((termThreeApplicability == null) ? 0 : termThreeApplicability.hashCode());
		result = prime * result + ((termThreeMaxMarks == null) ? 0 : termThreeMaxMarks.hashCode());
		result = prime * result + ((termTwoApplicability == null) ? 0 : termTwoApplicability.hashCode());
		result = prime * result + ((termTwoMaxMarks == null) ? 0 : termTwoMaxMarks.hashCode());
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
		EvaluationComponent other = (EvaluationComponent) obj;
		if (active == null) {
			if (other.active != null)
				return false;
		} else if (!active.equals(other.active))
			return false;
		if (applyMinimum == null) {
			if (other.applyMinimum != null)
				return false;
		} else if (!applyMinimum.equals(other.applyMinimum))
			return false;
		if (componentCalcMethodKey == null) {
			if (other.componentCalcMethodKey != null)
				return false;
		} else if (!componentCalcMethodKey.equals(other.componentCalcMethodKey))
			return false;
		if (componentCategoryKey == null) {
			if (other.componentCategoryKey != null)
				return false;
		} else if (!componentCategoryKey.equals(other.componentCategoryKey))
			return false;
		if (componentName == null) {
			if (other.componentName != null)
				return false;
		} else if (!componentName.equals(other.componentName))
			return false;
		if (componentStructureKey == null) {
			if (other.componentStructureKey != null)
				return false;
		} else if (!componentStructureKey.equals(other.componentStructureKey))
			return false;
		if (componentTypeKey == null) {
			if (other.componentTypeKey != null)
				return false;
		} else if (!componentTypeKey.equals(other.componentTypeKey))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (desctiption == null) {
			if (other.desctiption != null)
				return false;
		} else if (!desctiption.equals(other.desctiption))
			return false;
		if (displaySequence == null) {
			if (other.displaySequence != null)
				return false;
		} else if (!displaySequence.equals(other.displaySequence))
			return false;
		if (evalSchemeKey == null) {
			if (other.evalSchemeKey != null)
				return false;
		} else if (!evalSchemeKey.equals(other.evalSchemeKey))
			return false;
		if (isDeleted == null) {
			if (other.isDeleted != null)
				return false;
		} else if (!isDeleted.equals(other.isDeleted))
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (lastModifiedBy == null) {
			if (other.lastModifiedBy != null)
				return false;
		} else if (!lastModifiedBy.equals(other.lastModifiedBy))
			return false;
		if (lastModifiedDate == null) {
			if (other.lastModifiedDate != null)
				return false;
		} else if (!lastModifiedDate.equals(other.lastModifiedDate))
			return false;
		if (remarks == null) {
			if (other.remarks != null)
				return false;
		} else if (!remarks.equals(other.remarks))
			return false;
		if (stage11 == null) {
			if (other.stage11 != null)
				return false;
		} else if (!stage11.equals(other.stage11))
			return false;
		if (stage11Maxmarks == null) {
			if (other.stage11Maxmarks != null)
				return false;
		} else if (!stage11Maxmarks.equals(other.stage11Maxmarks))
			return false;
		if (stage12 == null) {
			if (other.stage12 != null)
				return false;
		} else if (!stage12.equals(other.stage12))
			return false;
		if (stage12Maxmarks == null) {
			if (other.stage12Maxmarks != null)
				return false;
		} else if (!stage12Maxmarks.equals(other.stage12Maxmarks))
			return false;
		if (stage13 == null) {
			if (other.stage13 != null)
				return false;
		} else if (!stage13.equals(other.stage13))
			return false;
		if (stage13Maxmarks == null) {
			if (other.stage13Maxmarks != null)
				return false;
		} else if (!stage13Maxmarks.equals(other.stage13Maxmarks))
			return false;
		if (stage14 == null) {
			if (other.stage14 != null)
				return false;
		} else if (!stage14.equals(other.stage14))
			return false;
		if (stage14Maxmarks == null) {
			if (other.stage14Maxmarks != null)
				return false;
		} else if (!stage14Maxmarks.equals(other.stage14Maxmarks))
			return false;
		if (stage21 == null) {
			if (other.stage21 != null)
				return false;
		} else if (!stage21.equals(other.stage21))
			return false;
		if (stage21Maxmarks == null) {
			if (other.stage21Maxmarks != null)
				return false;
		} else if (!stage21Maxmarks.equals(other.stage21Maxmarks))
			return false;
		if (stage22 == null) {
			if (other.stage22 != null)
				return false;
		} else if (!stage22.equals(other.stage22))
			return false;
		if (stage22Maxmarks == null) {
			if (other.stage22Maxmarks != null)
				return false;
		} else if (!stage22Maxmarks.equals(other.stage22Maxmarks))
			return false;
		if (stage23 == null) {
			if (other.stage23 != null)
				return false;
		} else if (!stage23.equals(other.stage23))
			return false;
		if (stage23Maxmarks == null) {
			if (other.stage23Maxmarks != null)
				return false;
		} else if (!stage23Maxmarks.equals(other.stage23Maxmarks))
			return false;
		if (stage24 == null) {
			if (other.stage24 != null)
				return false;
		} else if (!stage24.equals(other.stage24))
			return false;
		if (stage24Maxmarks == null) {
			if (other.stage24Maxmarks != null)
				return false;
		} else if (!stage24Maxmarks.equals(other.stage24Maxmarks))
			return false;
		if (stage31 == null) {
			if (other.stage31 != null)
				return false;
		} else if (!stage31.equals(other.stage31))
			return false;
		if (stage31Maxmarks == null) {
			if (other.stage31Maxmarks != null)
				return false;
		} else if (!stage31Maxmarks.equals(other.stage31Maxmarks))
			return false;
		if (stage32 == null) {
			if (other.stage32 != null)
				return false;
		} else if (!stage32.equals(other.stage32))
			return false;
		if (stage32Maxmarks == null) {
			if (other.stage32Maxmarks != null)
				return false;
		} else if (!stage32Maxmarks.equals(other.stage32Maxmarks))
			return false;
		if (stage33 == null) {
			if (other.stage33 != null)
				return false;
		} else if (!stage33.equals(other.stage33))
			return false;
		if (stage33Maxmarks == null) {
			if (other.stage33Maxmarks != null)
				return false;
		} else if (!stage33Maxmarks.equals(other.stage33Maxmarks))
			return false;
		if (stage34 == null) {
			if (other.stage34 != null)
				return false;
		} else if (!stage34.equals(other.stage34))
			return false;
		if (stage34Maxmarks == null) {
			if (other.stage34Maxmarks != null)
				return false;
		} else if (!stage34Maxmarks.equals(other.stage34Maxmarks))
			return false;
		if (stage41 == null) {
			if (other.stage41 != null)
				return false;
		} else if (!stage41.equals(other.stage41))
			return false;
		if (stage41Maxmarks == null) {
			if (other.stage41Maxmarks != null)
				return false;
		} else if (!stage41Maxmarks.equals(other.stage41Maxmarks))
			return false;
		if (stage42 == null) {
			if (other.stage42 != null)
				return false;
		} else if (!stage42.equals(other.stage42))
			return false;
		if (stage42Maxmarks == null) {
			if (other.stage42Maxmarks != null)
				return false;
		} else if (!stage42Maxmarks.equals(other.stage42Maxmarks))
			return false;
		if (stage43 == null) {
			if (other.stage43 != null)
				return false;
		} else if (!stage43.equals(other.stage43))
			return false;
		if (stage43Maxmarks == null) {
			if (other.stage43Maxmarks != null)
				return false;
		} else if (!stage43Maxmarks.equals(other.stage43Maxmarks))
			return false;
		if (stage44 == null) {
			if (other.stage44 != null)
				return false;
		} else if (!stage44.equals(other.stage44))
			return false;
		if (stage44Maxmarks == null) {
			if (other.stage44Maxmarks != null)
				return false;
		} else if (!stage44Maxmarks.equals(other.stage44Maxmarks))
			return false;
		if (termFourApplicability == null) {
			if (other.termFourApplicability != null)
				return false;
		} else if (!termFourApplicability.equals(other.termFourApplicability))
			return false;
		if (termFourMaxMarks == null) {
			if (other.termFourMaxMarks != null)
				return false;
		} else if (!termFourMaxMarks.equals(other.termFourMaxMarks))
			return false;
		if (termOneApplicability == null) {
			if (other.termOneApplicability != null)
				return false;
		} else if (!termOneApplicability.equals(other.termOneApplicability))
			return false;
		if (termOneMaxMarks == null) {
			if (other.termOneMaxMarks != null)
				return false;
		} else if (!termOneMaxMarks.equals(other.termOneMaxMarks))
			return false;
		if (termThreeApplicability == null) {
			if (other.termThreeApplicability != null)
				return false;
		} else if (!termThreeApplicability.equals(other.termThreeApplicability))
			return false;
		if (termThreeMaxMarks == null) {
			if (other.termThreeMaxMarks != null)
				return false;
		} else if (!termThreeMaxMarks.equals(other.termThreeMaxMarks))
			return false;
		if (termTwoApplicability == null) {
			if (other.termTwoApplicability != null)
				return false;
		} else if (!termTwoApplicability.equals(other.termTwoApplicability))
			return false;
		if (termTwoMaxMarks == null) {
			if (other.termTwoMaxMarks != null)
				return false;
		} else if (!termTwoMaxMarks.equals(other.termTwoMaxMarks))
			return false;
		return true;
	}



	public void setComponentCalcMethod(Value componentCalcMethod) {
		this.componentCalcMethod = componentCalcMethod;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Key getEvalSchemeKey() {
		return evalSchemeKey;
	}

	public void setEvalSchemeKey(Key evalSchemeKey) {
		this.evalSchemeKey = evalSchemeKey;
	}

	public String getComponentName() {
		return componentName;
	}

	public Boolean getApplyMinimum() {
		return applyMinimum;
	}

	public void setApplyMinimum(Boolean applyMinimum) {
		this.applyMinimum = applyMinimum;
	}

	public Boolean getTermOneApplicability() {
		return termOneApplicability;
	}

	public void setTermOneApplicability(Boolean termOneApplicability) {
		this.termOneApplicability = termOneApplicability;
	}

	public Boolean getTermTwoApplicability() {
		return termTwoApplicability;
	}

	public void setTermTwoApplicability(Boolean termTwoApplicability) {
		this.termTwoApplicability = termTwoApplicability;
	}

	public Boolean getTermThreeApplicability() {
		return termThreeApplicability;
	}

	public Key getComponentCategoryKey() {
		return componentCategoryKey;
	}

	public void setComponentCategoryKey(Key componentCategoryKey) {
		this.componentCategoryKey = componentCategoryKey;
	}

	public void setTermThreeApplicability(Boolean termThreeApplicability) {
		this.termThreeApplicability = termThreeApplicability;
	}

	public Boolean getTermFourApplicability() {
		return termFourApplicability;
	}

	public void setTermFourApplicability(Boolean termFourApplicability) {
		this.termFourApplicability = termFourApplicability;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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

	

	@Override
	public String toString() {
		return "EvaluationComponent [key=" + key + ", evalSchemeKey="
				+ evalSchemeKey + ", componentName=" + componentName
				+ ", componentTypeKey=" + componentTypeKey
				+ ", componentStructureKey=" + componentStructureKey
				+ ", displaySequence=" + displaySequence + ", applyMinimum="
				+ applyMinimum + ", termOneApplicability=" + termOneApplicability
				+ ", termTwoApplicability=" + termTwoApplicability
				+ ", termThreeApplicability=" + termThreeApplicability
				+ ", termFourApplicability=" + termFourApplicability
				+ ", termOneMaxMarks=" + termOneMaxMarks + ", termTwoMaxMarks="
				+ termTwoMaxMarks + ", termThreeMaxMarks=" + termThreeMaxMarks
				+ ", termFourMaxMarks=" + termFourMaxMarks + ", active="
				+ active + ", lastModifiedDate=" + lastModifiedDate
				+ ", createdDate=" + createdDate + ", createdBy=" + createdBy
				+ ", lastModifiedBy=" + lastModifiedBy + "]";
	}

	public Key getComponentTypeKey() {
		return componentTypeKey;
	}

	public void setComponentTypeKey(Key componentTypeKey) {
		this.componentTypeKey = componentTypeKey;
	}

	public Key getComponentStructureKey() {
		return componentStructureKey;
	}

	public void setComponentStructureKey(Key componentStructureKey) {
		this.componentStructureKey = componentStructureKey;
	}


	public String getCalculationMethod() {
		return calculationMethod;
	}



	public void setCalculationMethod(String calculationMethod) {
		this.calculationMethod = calculationMethod;
	}



	public Integer getDisplaySequence() {
		return displaySequence;
	}

	public void setDisplaySequence(Integer displaySequence) {
		this.displaySequence = displaySequence;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	@Override
	public void jdoPreStore() {
	}
}
