/**
 * 
 */
package com.metacube.ipathshala.util;

import java.util.Comparator;

import com.metacube.ipathshala.entity.EvaluationSubComponent;

/**
 * @author vishesh
 *
 */
public class EvaluationSubComponentComparator implements
		Comparator<EvaluationSubComponent> {

	@Override
	public int compare(EvaluationSubComponent subComp1, EvaluationSubComponent subComp2) {
		if(subComp1.getDisplaySequence()!=null && subComp2.getDisplaySequence()!=null){
			if(subComp1.getDisplaySequence() > subComp2.getDisplaySequence()){
				return 1;
			}else if(subComp1.getDisplaySequence() < subComp2.getDisplaySequence()){
				return -1;
			}else{
				return 0;
			}
		}
		return 0;
	}

}
