package com.metacube.ipathshala.util;

import java.util.Comparator;

import com.metacube.ipathshala.entity.EvaluationStage;

public class EvaluationStageComparator implements Comparator<EvaluationStage> {

	@Override
	public int compare(EvaluationStage o1, EvaluationStage o2) {
		int result = 0;

		if (o1 == null || o2 == null)
			return result;

		if (o1.getName() == null || o2.getName() == null)
			return result;

		result = o1.getName().compareTo(o2.getName());

		return result;
	}

}
