/**
 * 
 */
package com.metacube.ipathshala.util;

import java.util.Comparator;

import com.metacube.ipathshala.entity.GradingScaleSteps;

/**
 * @author vishesh
 *
 */
public class GradingScaleStepsComparator implements Comparator<GradingScaleSteps>{

	@Override
	public int compare(GradingScaleSteps o1, GradingScaleSteps o2) {
		if (o1.getStepWeight() > o2.getStepWeight()) {
			return -1;
		} else if (o1.getStepWeight() < o2.getStepWeight()) {
			return 1;
		} else {
			return 0;
		}
	}

}
