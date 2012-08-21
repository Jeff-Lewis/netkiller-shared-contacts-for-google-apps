package com.metacube.ipathshala.util;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.validation.BindingResult;

import com.google.appengine.api.datastore.Key;
import com.metacube.ipathshala.ServerCommonConstant;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.entity.EvaluationComponent;
import com.metacube.ipathshala.entity.EvaluationStage;
import com.metacube.ipathshala.entity.GradingScale;
import com.metacube.ipathshala.entity.GradingScaleSteps;
import com.metacube.ipathshala.entity.SubjectEvaluationEvent;
import com.metacube.ipathshala.entity.Term;

public class EvaluationUtil {

	
	private static Map<Integer, Term> getTermMap(List<Term> terms) {
		Map<Integer, Term> termMap = new HashMap<Integer, Term>();
		for (Term term : terms) {
			termMap.put(Integer.parseInt(term.getSequenceValue().getValue()), term);
		}
		return termMap;
	}

	private static Map<Integer, EvaluationStage> getEvaluationStageMap(List<EvaluationStage> stages) {
		Map<Integer, EvaluationStage> stagesMap = new HashMap<Integer, EvaluationStage>();
		for (EvaluationStage evaluationStage : stages) {
			stagesMap.put(Integer.parseInt(evaluationStage.getStageSequenceValue().getValue()), evaluationStage);
		}
		return stagesMap;
	}

	public static Set<Term> getTermsFromEvaluationComponent(List<Term> terms, EvaluationComponent evaluationComponent) {
		Map<Integer, Term> termMap = getTermMap(terms);
		Set<Term> applicableTerms = new HashSet<Term>();

		if (evaluationComponent.getStage11() != null && evaluationComponent.getStage11()) {
			if (termMap.get(1) != null) {
				applicableTerms.add(termMap.get(1));
			}
		}
		if (evaluationComponent.getStage21() != null && evaluationComponent.getStage21()) {
			if (termMap.get(2) != null) {
				applicableTerms.add(termMap.get(2));
			}
		}
		if (evaluationComponent.getStage31() != null && evaluationComponent.getStage31()) {
			if (termMap.get(3) != null) {
				applicableTerms.add(termMap.get(3));
			}
		}
		if (evaluationComponent.getStage41() != null && evaluationComponent.getStage41()) {
			if (termMap.get(4) != null) {
				applicableTerms.add(termMap.get(4));
			}
		}
		if (evaluationComponent.getStage12() != null && evaluationComponent.getStage12()) {
			if (termMap.get(1) != null) {
				applicableTerms.add(termMap.get(1));
			}
		}
		if (evaluationComponent.getStage22() != null && evaluationComponent.getStage22()) {
			if (termMap.get(2) != null) {
				applicableTerms.add(termMap.get(2));
			}
		}
		if (evaluationComponent.getStage32() != null && evaluationComponent.getStage32()) {
			if (termMap.get(3) != null) {
				applicableTerms.add(termMap.get(3));
			}
		}
		if (evaluationComponent.getStage42() != null && evaluationComponent.getStage42()) {
			if (termMap.get(4) != null) {
				applicableTerms.add(termMap.get(4));
			}
		}
		if (evaluationComponent.getStage13() != null && evaluationComponent.getStage13()) {
			if (termMap.get(1) != null) {
				applicableTerms.add(termMap.get(1));
			}
		}
		if (evaluationComponent.getStage23() != null && evaluationComponent.getStage23()) {
			if (termMap.get(2) != null) {
				applicableTerms.add(termMap.get(2));
			}
		}
		if (evaluationComponent.getStage33() != null && evaluationComponent.getStage33()) {
			if (termMap.get(3) != null) {
				applicableTerms.add(termMap.get(3));
			}
		}
		if (evaluationComponent.getStage43() != null && evaluationComponent.getStage43()) {
			if (termMap.get(4) != null) {
				applicableTerms.add(termMap.get(4));
			}
		}
		if (evaluationComponent.getStage14() != null && evaluationComponent.getStage14()) {
			if (termMap.get(1) != null) {
				applicableTerms.add(termMap.get(1));
			}
		}
		if (evaluationComponent.getStage24() != null && evaluationComponent.getStage24()) {
			if (termMap.get(2) != null) {
				applicableTerms.add(termMap.get(2));
			}
		}
		if (evaluationComponent.getStage34() != null && evaluationComponent.getStage34()) {
			if (termMap.get(3) != null) {
				applicableTerms.add(termMap.get(3));
			}
		}
		if (evaluationComponent.getStage44() != null && evaluationComponent.getStage44()) {
			if (termMap.get(4) != null) {
				applicableTerms.add(termMap.get(4));
			}
		}
		return applicableTerms;
	}

	public static void populateTermStageApplicablityForNumericScheme(EvaluationComponent evaluationComponent) {
		if (evaluationComponent.getStage11Maxmarks() != null && evaluationComponent.getStage11Maxmarks() != 0) {
			evaluationComponent.setStage11(true);
		}
		if (evaluationComponent.getStage12Maxmarks() != null && evaluationComponent.getStage12Maxmarks() != 0) {
			evaluationComponent.setStage12(true);
		}
		if (evaluationComponent.getStage13Maxmarks() != null && evaluationComponent.getStage13Maxmarks() != 0) {
			evaluationComponent.setStage13(true);
		}
		if (evaluationComponent.getStage14Maxmarks() != null && evaluationComponent.getStage14Maxmarks() != 0) {
			evaluationComponent.setStage14(true);
		}
		if (evaluationComponent.getStage21Maxmarks() != null && evaluationComponent.getStage21Maxmarks() != 0) {
			evaluationComponent.setStage21(true);
		}
		if (evaluationComponent.getStage22Maxmarks() != null && evaluationComponent.getStage22Maxmarks() != 0) {
			evaluationComponent.setStage22(true);
		}
		if (evaluationComponent.getStage23Maxmarks() != null && evaluationComponent.getStage23Maxmarks() != 0) {
			evaluationComponent.setStage23(true);
		}
		if (evaluationComponent.getStage24Maxmarks() != null && evaluationComponent.getStage24Maxmarks() != 0) {
			evaluationComponent.setStage24(true);
		}
		if (evaluationComponent.getStage31Maxmarks() != null && evaluationComponent.getStage31Maxmarks() != 0) {
			evaluationComponent.setStage31(true);
		}
		if (evaluationComponent.getStage32Maxmarks() != null && evaluationComponent.getStage32Maxmarks() != 0) {
			evaluationComponent.setStage32(true);
		}
		if (evaluationComponent.getStage33Maxmarks() != null && evaluationComponent.getStage33Maxmarks() != 0) {
			evaluationComponent.setStage33(true);
		}
		if (evaluationComponent.getStage34Maxmarks() != null && evaluationComponent.getStage34Maxmarks() != 0) {
			evaluationComponent.setStage34(true);
		}
		if (evaluationComponent.getStage41Maxmarks() != null && evaluationComponent.getStage41Maxmarks() != 0) {
			evaluationComponent.setStage41(true);
		}
		if (evaluationComponent.getStage42Maxmarks() != null && evaluationComponent.getStage42Maxmarks() != 0) {
			evaluationComponent.setStage42(true);
		}
		if (evaluationComponent.getStage43Maxmarks() != null && evaluationComponent.getStage43Maxmarks() != 0) {
			evaluationComponent.setStage43(true);
		}
		if (evaluationComponent.getStage44Maxmarks() != null && evaluationComponent.getStage44Maxmarks() != 0) {
			evaluationComponent.setStage44(true);
		}

	}

	public static Map<Term, List<EvaluationStage>> populateMap(Map<Integer, EvaluationStage> evalStageMap,
			Map<Integer, Term> termMap, Map<Term, List<EvaluationStage>> termStageMap, int term, int stage) {
		if (termMap.get(term) != null && evalStageMap.get(stage) != null) {
			if (!termStageMap.containsKey(termMap.get(term))) {
				List<EvaluationStage> stageList = new ArrayList<EvaluationStage>();
				stageList.add(evalStageMap.get(stage));
				termStageMap.put(termMap.get(term), stageList);
			} else {
				termStageMap.get(termMap.get(term)).add(evalStageMap.get(stage));
			}
		}
		return termStageMap;
	}

	public static Map<Term, List<EvaluationStage>> getTermStageApplicabilityFromEvaluationComponent(
			List<EvaluationStage> stages, List<Term> terms, EvaluationComponent component) {
		Map<Integer, EvaluationStage> evalStageMap = getEvaluationStageMap(stages);
		Map<Integer, Term> termMap = getTermMap(terms);
		Map<Term, List<EvaluationStage>> termStageMap = new HashMap<Term, List<EvaluationStage>>();
		if (component.getStage11() != null && component.getStage11()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 1, 1);
		}
		if (component.getStage12() != null && component.getStage12()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 1, 2);

		}
		if (component.getStage13() != null && component.getStage13()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 1, 3);

		}
		if (component.getStage14() != null && component.getStage14()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 1, 4);

		}
		if (component.getStage21() != null && component.getStage21()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 2, 1);

		}
		if (component.getStage22() != null && component.getStage22()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 2, 2);

		}
		if (component.getStage23() != null && component.getStage23()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 2, 3);

		}
		if (component.getStage24() != null && component.getStage24()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 2, 4);

		}
		if (component.getStage31() != null && component.getStage31()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 3, 1);

		}
		if (component.getStage32() != null && component.getStage32()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 3, 2);

		}
		if (component.getStage33() != null && component.getStage33()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 3, 3);

		}
		if (component.getStage34() != null && component.getStage34()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 3, 4);

		}
		if (component.getStage41() != null && component.getStage41()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 4, 1);

		}
		if (component.getStage42() != null && component.getStage42()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 4, 2);

		}
		if (component.getStage43() != null && component.getStage43()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 4, 3);

		}
		if (component.getStage44() != null && component.getStage44()) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 4, 4);

		}
		return termStageMap;
	}

	public static Map<Term, List<EvaluationStage>> getTermStageApplicabilityFromNumericEvaluationComponent(
			List<EvaluationStage> stages, List<Term> terms, EvaluationComponent component) {
		Map<Integer, EvaluationStage> evalStageMap = getEvaluationStageMap(stages);
		Map<Integer, Term> termMap = getTermMap(terms);
		Map<Term, List<EvaluationStage>> termStageMap = new HashMap<Term, List<EvaluationStage>>();
		if (component.getStage11Maxmarks() != null && component.getStage11Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 1, 1);
		}
		if (component.getStage12Maxmarks() != null && component.getStage12Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 1, 2);

		}
		if (component.getStage13Maxmarks() != null && component.getStage13Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 1, 3);

		}
		if (component.getStage14Maxmarks() != null && component.getStage14Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 1, 4);

		}
		if (component.getStage21Maxmarks() != null && component.getStage21Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 2, 1);

		}
		if (component.getStage22Maxmarks() != null && component.getStage22Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 2, 2);

		}
		if (component.getStage23Maxmarks() != null && component.getStage23Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 2, 3);

		}
		if (component.getStage24Maxmarks() != null && component.getStage24Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 2, 4);

		}
		if (component.getStage31Maxmarks() != null && component.getStage31Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 3, 1);

		}
		if (component.getStage32Maxmarks() != null && component.getStage32Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 3, 2);

		}
		if (component.getStage33Maxmarks() != null && component.getStage33Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 3, 3);

		}
		if (component.getStage34Maxmarks() != null && component.getStage34Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 3, 4);

		}
		if (component.getStage41Maxmarks() != null && component.getStage41Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 4, 1);

		}
		if (component.getStage42Maxmarks() != null && component.getStage42Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 4, 2);

		}
		if (component.getStage43Maxmarks() != null && component.getStage43Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 4, 3);

		}
		if (component.getStage44Maxmarks() != null && component.getStage44Maxmarks() != 0) {
			termStageMap = populateMap(evalStageMap, termMap, termStageMap, 4, 4);

		}
		return termStageMap;
	}

	public static Set<EvaluationStage> getStagesFromEvaluationComponent(List<EvaluationStage> stages,
			EvaluationComponent evaluationComponent) {
		Map<Integer, EvaluationStage> evalStageMap = getEvaluationStageMap(stages);
		Set<EvaluationStage> evaluationStages = new HashSet<EvaluationStage>();
		if (evaluationComponent.getStage11() != null && evaluationComponent.getStage11()) {
			if (evalStageMap.get(1) != null) {
				evaluationStages.add(evalStageMap.get(1));
			}
		}
		if (evaluationComponent.getStage12() != null && evaluationComponent.getStage12()) {
			if (evalStageMap.get(2) != null) {
				evaluationStages.add(evalStageMap.get(2));
			}
		}
		if (evaluationComponent.getStage13() != null && evaluationComponent.getStage13()) {
			if (evalStageMap.get(3) != null) {
				evaluationStages.add(evalStageMap.get(3));
			}
		}
		if (evaluationComponent.getStage14() != null && evaluationComponent.getStage14()) {
			if (evalStageMap.get(4) != null) {
				evaluationStages.add(evalStageMap.get(4));
			}
		}
		if (evaluationComponent.getStage21() != null && evaluationComponent.getStage21()) {
			if (evalStageMap.get(1) != null) {
				evaluationStages.add(evalStageMap.get(1));
			}
		}
		if (evaluationComponent.getStage22() != null && evaluationComponent.getStage22()) {
			if (evalStageMap.get(2) != null) {
				evaluationStages.add(evalStageMap.get(2));
			}
		}
		if (evaluationComponent.getStage23() != null && evaluationComponent.getStage23()) {
			if (evalStageMap.get(3) != null) {
				evaluationStages.add(evalStageMap.get(3));
			}
		}
		if (evaluationComponent.getStage24() != null && evaluationComponent.getStage24()) {
			if (evalStageMap.get(4) != null) {
				evaluationStages.add(evalStageMap.get(4));
			}
		}
		if (evaluationComponent.getStage31() != null && evaluationComponent.getStage31()) {
			if (evalStageMap.get(1) != null) {
				evaluationStages.add(evalStageMap.get(1));
			}
		}
		if (evaluationComponent.getStage32() != null && evaluationComponent.getStage32()) {
			if (evalStageMap.get(2) != null) {
				evaluationStages.add(evalStageMap.get(2));
			}
		}
		if (evaluationComponent.getStage33() != null && evaluationComponent.getStage33()) {
			if (evalStageMap.get(3) != null) {
				evaluationStages.add(evalStageMap.get(3));
			}
		}
		if (evaluationComponent.getStage34() != null && evaluationComponent.getStage34()) {
			if (evalStageMap.get(4) != null) {
				evaluationStages.add(evalStageMap.get(4));
			}
		}
		if (evaluationComponent.getStage41() != null && evaluationComponent.getStage41()) {
			if (evalStageMap.get(1) != null) {
				evaluationStages.add(evalStageMap.get(1));
			}
		}
		if (evaluationComponent.getStage42() != null && evaluationComponent.getStage42()) {
			if (evalStageMap.get(2) != null) {
				evaluationStages.add(evalStageMap.get(2));
			}
		}
		if (evaluationComponent.getStage43() != null && evaluationComponent.getStage43()) {
			if (evalStageMap.get(3) != null) {
				evaluationStages.add(evalStageMap.get(3));
			}
		}
		if (evaluationComponent.getStage44() != null && evaluationComponent.getStage44()) {
			if (evalStageMap.get(4) != null) {
				evaluationStages.add(evalStageMap.get(4));
			}
		}
		return evaluationStages;
	}

	public static Set<EvaluationStage> getApplicableStagesFromEvaluationComponentByTerm(
			List<EvaluationStage> allStagesByTerm, EvaluationComponent evaluationComponent, Term term) {
		Map<Integer, EvaluationStage> evalStageMap = getEvaluationStageMap(allStagesByTerm);
		Set<EvaluationStage> evaluationStages = new HashSet<EvaluationStage>();
		switch (Integer.parseInt(term.getSequenceValue().getValue())) {
		case 1:
			if (evaluationComponent.getStage11() != null && evaluationComponent.getStage11()) {
				if (evalStageMap.get(1) != null) {
					evaluationStages.add(evalStageMap.get(1));
				}
			}
			if (evaluationComponent.getStage12() != null && evaluationComponent.getStage12()) {
				if (evalStageMap.get(2) != null) {
					evaluationStages.add(evalStageMap.get(2));
				}
			}
			if (evaluationComponent.getStage13() != null && evaluationComponent.getStage13()) {
				if (evalStageMap.get(3) != null) {
					evaluationStages.add(evalStageMap.get(3));
				}
			}
			if (evaluationComponent.getStage14() != null && evaluationComponent.getStage14()) {
				if (evalStageMap.get(4) != null) {
					evaluationStages.add(evalStageMap.get(4));
				}
			}
			break;
		case 2:
			if (evaluationComponent.getStage21() != null && evaluationComponent.getStage21()) {
				if (evalStageMap.get(1) != null) {
					evaluationStages.add(evalStageMap.get(1));
				}
			}
			if (evaluationComponent.getStage22() != null && evaluationComponent.getStage22()) {
				if (evalStageMap.get(2) != null) {
					evaluationStages.add(evalStageMap.get(2));
				}
			}
			if (evaluationComponent.getStage23() != null && evaluationComponent.getStage23()) {
				if (evalStageMap.get(3) != null) {
					evaluationStages.add(evalStageMap.get(3));
				}
			}
			if (evaluationComponent.getStage24() != null && evaluationComponent.getStage24()) {
				if (evalStageMap.get(4) != null) {
					evaluationStages.add(evalStageMap.get(4));
				}
			}
			break;
		case 3:
			if (evaluationComponent.getStage31() != null && evaluationComponent.getStage31()) {
				if (evalStageMap.get(1) != null) {
					evaluationStages.add(evalStageMap.get(1));
				}
			}
			if (evaluationComponent.getStage32() != null && evaluationComponent.getStage32()) {
				if (evalStageMap.get(2) != null) {
					evaluationStages.add(evalStageMap.get(2));
				}
			}
			if (evaluationComponent.getStage33() != null && evaluationComponent.getStage33()) {
				if (evalStageMap.get(3) != null) {
					evaluationStages.add(evalStageMap.get(3));
				}
			}
			if (evaluationComponent.getStage34() != null && evaluationComponent.getStage34()) {
				if (evalStageMap.get(4) != null) {
					evaluationStages.add(evalStageMap.get(4));
				}
			}
			break;
		case 4:
			if (evaluationComponent.getStage41() != null && evaluationComponent.getStage41()) {
				if (evalStageMap.get(1) != null) {
					evaluationStages.add(evalStageMap.get(1));
				}
			}

			if (evaluationComponent.getStage42() != null && evaluationComponent.getStage42()) {
				if (evalStageMap.get(2) != null) {
					evaluationStages.add(evalStageMap.get(2));
				}
			}
			if (evaluationComponent.getStage43() != null && evaluationComponent.getStage43()) {
				if (evalStageMap.get(3) != null) {
					evaluationStages.add(evalStageMap.get(3));
				}
			}
			if (evaluationComponent.getStage44() != null && evaluationComponent.getStage44()) {
				if (evalStageMap.get(4) != null) {
					evaluationStages.add(evalStageMap.get(4));
				}
			}
			break;
		}
		return evaluationStages;
	}

	public static boolean isMaxMarksFieldNull(Boolean termApplicability, Double maxMarks, String schemeType) {
		if (schemeType.equalsIgnoreCase("Numeric")) {
			if (termApplicability) {
				if (maxMarks == null) {
					return true;
				}
			}
		}
		return false;
	}

	public static void maxMarksFieldCheck(EvaluationComponent evaluationComponent, String schemeType,
			BindingResult result) {
		if (EvaluationUtil.isMaxMarksFieldNull(evaluationComponent.getTermOneApplicability(),
				evaluationComponent.getTermOneMaxMarks(), schemeType)) {
			result.rejectValue("termOneMaxMarks", "evaluationComponentForm.termOneMaxMarks.required");
		}
		if (EvaluationUtil.isMaxMarksFieldNull(evaluationComponent.getTermTwoApplicability(),
				evaluationComponent.getTermTwoMaxMarks(), schemeType)) {
			result.rejectValue("termTwoMaxMarks", "evaluationComponentForm.termTwoMaxMarks.required");
		}
		if (EvaluationUtil.isMaxMarksFieldNull(evaluationComponent.getTermThreeApplicability(),
				evaluationComponent.getTermThreeMaxMarks(), schemeType)) {
			result.rejectValue("termThreeMaxMarks", "evaluationComponentForm.termThreeMaxMarks.required");
		}
		if (EvaluationUtil.isMaxMarksFieldNull(evaluationComponent.getTermFourApplicability(),
				evaluationComponent.getTermFourMaxMarks(), schemeType)) {
			result.rejectValue("termFourMaxMarks", "evaluationComponentForm.termFourMaxMarks.required");
		}
	}

	public static void populateMaximumMarksInSubjectEvaluationEvent(SubjectEvaluationEvent event, Term term,
			EvaluationStage stage, EvaluationComponent component) {
		if (term != null) {
			switch (Integer.parseInt(term.getSequenceValue().getValue())) {
			case 1:
				if (stage != null) {
					switch (Integer.parseInt(stage.getStageSequenceValue().getValue())) {
					case 1:
						event.setMaxMarks(component.getStage11Maxmarks());
						break;
					case 2:
						event.setMaxMarks(component.getStage12Maxmarks());
						break;

					case 3:
						event.setMaxMarks(component.getStage13Maxmarks());
						break;

					case 4:
						event.setMaxMarks(component.getStage14Maxmarks());
						break;

					}
				} else {
					event.setMaxMarks(component.getStage11Maxmarks());
				}
				break;
			case 2:
				if (stage != null) {
					switch (Integer.parseInt(stage.getStageSequenceValue().getValue())) {
					case 1:
						event.setMaxMarks(component.getStage21Maxmarks());
						break;
					case 2:
						event.setMaxMarks(component.getStage22Maxmarks());
						break;

					case 3:
						event.setMaxMarks(component.getStage23Maxmarks());
						break;

					case 4:
						event.setMaxMarks(component.getStage24Maxmarks());
						break;

					}
				} else {
					event.setMaxMarks(component.getStage21Maxmarks());
				}
				break;
			case 3:
				if (stage != null) {
					switch (Integer.parseInt(stage.getStageSequenceValue().getValue())) {
					case 1:
						event.setMaxMarks(component.getStage31Maxmarks());
						break;
					case 2:
						event.setMaxMarks(component.getStage32Maxmarks());
						break;

					case 3:
						event.setMaxMarks(component.getStage33Maxmarks());
						break;

					case 4:
						event.setMaxMarks(component.getStage34Maxmarks());
						break;

					}
				} else {
					event.setMaxMarks(component.getStage31Maxmarks());
				}
				break;
			case 4:
				if (stage != null) {
					switch (Integer.parseInt(stage.getStageSequenceValue().getValue())) {
					case 1:
						event.setMaxMarks(component.getStage41Maxmarks());
						break;
					case 2:
						event.setMaxMarks(component.getStage42Maxmarks());
						break;

					case 3:
						event.setMaxMarks(component.getStage43Maxmarks());
						break;

					case 4:
						event.setMaxMarks(component.getStage44Maxmarks());
						break;

					}
				} else {
					event.setMaxMarks(component.getStage41Maxmarks());
				}
				break;
			}
		} else {
			event.setMaxMarks(component.getStage11Maxmarks());
		}

	}

	public static Map<String, String> getAllStagesFieldMap() {
		Map<String, String> stageMap = new LinkedHashMap<String, String>();
		stageMap.put("stage11", "Stage 1 ");
		stageMap.put("stage12", "Stage 2 ");
		stageMap.put("stage13", "Stage 3 ");
		stageMap.put("stage14", "Stage 4 ");
		stageMap.put("stage21", "Stage 1 ");
		stageMap.put("stage22", "Stage 2 ");
		stageMap.put("stage23", "Stage 3 ");
		stageMap.put("stage24", "Stage 4 ");
		stageMap.put("stage31", "Stage 1 ");
		stageMap.put("stage32", "Stage 2 ");
		stageMap.put("stage33", "Stage 3 ");
		stageMap.put("stage34", "Stage 4 ");
		stageMap.put("stage41", "Stage 1 ");
		stageMap.put("stage42", "Stage 2 ");
		stageMap.put("stage43", "Stage 3 ");
		stageMap.put("stage44", "Stage 4 ");
		return stageMap;
	}

	public static Map<String, String> getAllStagesFieldMap(Integer terms, Integer stages) {
		Map<String, String> stageMap = new LinkedHashMap<String, String>();
		if (terms != null) {
			for (int i = 1; i <= terms; i++) {
				if (stages != null) {
					for (int j = 1; j <= stages; j++) {
						stageMap.put("stage" + i + j, "Stage " + j + " ");
					}
					if (stages >= 1) {
						stageMap.put("total" + i, "Total " + " ");
					}
				}
			}
		}
		return stageMap;
	}

	public static Map<Integer, Boolean> getStageVariableMap(int noOfTerms, int noOfStages) {
		Map<Integer, Boolean> stageMap = new LinkedHashMap<Integer, Boolean>();
		for (int i = 1; i <= noOfTerms; i++) {
			for (int j = 1; j <= noOfStages; j++) {
				stageMap.put(Integer.parseInt(String.valueOf(i) + String.valueOf(j)), true);

			}
		}
		return stageMap;

	}

	public static void populateTermAndStageApplicability(String schemeType, Integer noOfTerms, Integer noOfStages,
			EvaluationComponent evaluationComponent) {
		if (schemeType.equalsIgnoreCase("Grading")) {
			Map<Integer, Boolean> stageMap = getStageVariableMap(noOfTerms, noOfStages);
			if (!stageMap.isEmpty() && stageMap != null) {
				for (Integer integer : stageMap.keySet()) {
					switch (integer) {
					case 11:
						evaluationComponent.setStage11(true);
						break;
					case 12:
						evaluationComponent.setStage12(true);
						break;
					case 13:
						evaluationComponent.setStage13(true);
						break;
					case 14:
						evaluationComponent.setStage14(true);
						break;
					case 21:
						evaluationComponent.setStage21(true);
						break;
					case 22:
						evaluationComponent.setStage22(true);
						break;
					case 23:
						evaluationComponent.setStage23(true);
						break;
					case 24:
						evaluationComponent.setStage24(true);
						break;
					case 31:
						evaluationComponent.setStage31(true);
						break;
					case 32:
						evaluationComponent.setStage32(true);
						break;
					case 33:
						evaluationComponent.setStage33(true);
						break;
					case 34:
						evaluationComponent.setStage34(true);
						break;
					case 41:
						evaluationComponent.setStage41(true);
						break;
					case 42:
						evaluationComponent.setStage42(true);
						break;
					case 43:
						evaluationComponent.setStage43(true);
						break;
					case 44:
						evaluationComponent.setStage44(true);
						break;
					default:
						break;
					}

				}

			}
		}

	}

	public static List<Double> getApplicableTermAndStageMaxMarksListInSortedOrder(String schemeType, Integer noOfTerms,
			Integer noOfStages, EvaluationComponent evaluationComponent) {
		List<Double> maxMarksList = new ArrayList<Double>();
		if (schemeType.equalsIgnoreCase("Numeric")) {

			Map<Integer, Boolean> stageMap = getStageVariableMap(noOfTerms, noOfStages);
			if (!stageMap.isEmpty() && stageMap != null) {
				for (Integer integer : stageMap.keySet()) {
					switch (integer) {
					case 11:
						maxMarksList.add(evaluationComponent.getStage11Maxmarks());
						break;
					case 12:
						maxMarksList.add(evaluationComponent.getStage12Maxmarks());
						break;
					case 13:
						maxMarksList.add(evaluationComponent.getStage13Maxmarks());
						break;
					case 14:
						maxMarksList.add(evaluationComponent.getStage14Maxmarks());
						break;
					case 21:
						maxMarksList.add(evaluationComponent.getStage21Maxmarks());
						break;
					case 22:
						maxMarksList.add(evaluationComponent.getStage22Maxmarks());
						break;
					case 23:
						maxMarksList.add(evaluationComponent.getStage23Maxmarks());
						break;
					case 24:
						maxMarksList.add(evaluationComponent.getStage24Maxmarks());
						break;
					case 31:
						maxMarksList.add(evaluationComponent.getStage31Maxmarks());
						break;
					case 32:
						maxMarksList.add(evaluationComponent.getStage32Maxmarks());
						break;
					case 33:
						maxMarksList.add(evaluationComponent.getStage33Maxmarks());
						break;
					case 34:
						maxMarksList.add(evaluationComponent.getStage34Maxmarks());
						break;
					case 41:
						maxMarksList.add(evaluationComponent.getStage41Maxmarks());
						break;
					case 42:
						maxMarksList.add(evaluationComponent.getStage42Maxmarks());
						break;
					case 43:
						maxMarksList.add(evaluationComponent.getStage43Maxmarks());
						break;
					case 44:
						maxMarksList.add(evaluationComponent.getStage44Maxmarks());
						break;
					default:
						break;
					}

				}

			}
		}
		return maxMarksList;

	}
	
	/**
	 * 
	 * @param calculationMethodName is the name of the method as obtained from EvaluationComponent
	 * @param totalChildEvents is the total child Child events for which the over all marks/grade would be calculated
	 * @param totalMaxMarks is the sum of maximum marks of all child events 
	 * @param totalMarksObtained is the sum of marks obtained in an event(Obtained from StudentMarks for that event )
	 * @param parentCompMaxMarks is the maximum marks specified in the EvaluationComponent
	 * @param proportionalMarksArr is the list of  (marks obtained in event) divided by( event max marks)
	 * @return 
	 */
	public static double getComputedMarksBasedOnCalculationMethod(
			String calculationMethodName, int totalChildEvents,
			double totalMaxMarks, double totalMarksObtained,
			double parentCompMaxMarks, List<Double> proportionalMarksArr) {

		// This is a pattern for avg(k) case
		String pattern = "avg['('][0-9]*[')']";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(calculationMethodName);
		if(m.matches()){	// For the case the method name is avg(k) .. k could be any number
			int k = Integer.valueOf(calculationMethodName.substring(calculationMethodName.indexOf('(')+1, calculationMethodName.indexOf(')')));
			if(proportionalMarksArr!=null && proportionalMarksArr.size()>k){				
				Collections.sort(proportionalMarksArr);
				proportionalMarksArr = proportionalMarksArr.subList( proportionalMarksArr.size()-k,  proportionalMarksArr.size());
				double result = getAverageNMarks(proportionalMarksArr,k,parentCompMaxMarks);
				return getRoundOf(result, 2);
			}else if( proportionalMarksArr.size()<=k){
				double result = getAverageNMarks(proportionalMarksArr,totalChildEvents,parentCompMaxMarks);
				return getRoundOf(result, 2);
			}
		}else if (calculationMethodName.equalsIgnoreCase("avg(all)")) {
			double result = getAverageNMarks(proportionalMarksArr,totalChildEvents,parentCompMaxMarks);
			return getRoundOf(result, 2);
		} else if (calculationMethodName.equalsIgnoreCase("wavg(all)")) {
			double result = (totalMarksObtained / totalMaxMarks)
					* parentCompMaxMarks;
			return getRoundOf(result, 2);
		}

		return 0;
	}

	private static double getAverageNMarks(List<Double> proportionalMarksArr, int totalChildEvents, double parentCompMaxMarks){
		double totalProportionalMarks = 0;
		for(Double d : proportionalMarksArr){
			totalProportionalMarks += d;
		}
		double result = (totalProportionalMarks / totalChildEvents)
				* parentCompMaxMarks;
		return result;
	}
	
	public static double getRoundOf(double Rval, int Rpl) {
		double p = (double) Math.pow(10, Rpl);
		Rval = Rval * p;
		double tmp = Math.round(Rval);
		return (double) tmp / p;
	}
	
	public static String getOverallGradeBasedOnAverage(Double totalWeight, int totalEvents,
			Map<String, Double> stepWeightMap) {

		double avgWeight = totalWeight/totalEvents;
		double first = 0.0;
		String prevStr = "";
		
		Set<String> keys = stepWeightMap.keySet();
		List<String> keyList =  new ArrayList<String>(keys);
		Collections.sort(keyList);
		Set<String> keySet = new LinkedHashSet<String>(keyList);
		
		Map<String, Double> newMap = new LinkedHashMap<String, Double>();
		
		for(String str : keySet){
			newMap.put(str, stepWeightMap.get(str));
		}
		
		for (String str : newMap.keySet()) {
			double next = newMap.get(str);
			if (avgWeight <= next) {
			//	double avg = (next + first) / 2;
				double avg = next;
				/*if (avgWeight > avg) {
					return str;
				} else {
					return prevStr;
				}*/
				return str;
			}
			first = next;
			prevStr = str;
		}
		return "";
	}
	
	public static Object createGradingScaleStepsDisplayMap(
			List<GradingScaleSteps> gradingScaleSteps) throws AppException {
		Map<String, String> stepsMap = new LinkedHashMap<String, String>();
		String gradingScaleType = PropertyUtil.getProperty(ServerCommonConstant.PROPERTY_KEY_GRADING_SCALE_STEPS_DISPLAY_TYPE);
		stepsMap.put("", "");
		if (gradingScaleSteps != null) {
			if(gradingScaleType.equalsIgnoreCase(ServerCommonConstant.GRADING_SCALE_STEP_DISPLAY_GRADING)){
				for (GradingScaleSteps gradingScaleSteps2 : gradingScaleSteps) {
					stepsMap.put(gradingScaleSteps2.getStepDisplay(),
							gradingScaleSteps2.getStepDisplay());
				}
			}else{
				for (GradingScaleSteps gradingScaleSteps2 : gradingScaleSteps) {
					stepsMap.put(gradingScaleSteps2.getNumericDisplay(),
							gradingScaleSteps2.getNumericDisplay());
				}
			}
			
		}
		return stepsMap;
	}
	
	public static Map<String, String> getGradeRemarkMap(
			List<GradingScaleSteps> gradingScaleSteps, String remarks) throws AppException {
		Map<String, String> gradeRemarkMap = new LinkedHashMap<String, String>();
		if (null == remarks) {
			return gradeRemarkMap;
		}

		String[] remarkArr = parseSubComponentRemark(remarks);
		String gradingScaleType = PropertyUtil.getProperty(ServerCommonConstant.PROPERTY_KEY_GRADING_SCALE_STEPS_DISPLAY_TYPE);
		// It is assumed that the remarks are in the pattern
		// <remark1,remark2,remark3>

		for (int i = 0; i < gradingScaleSteps.size(); i++) {
			if (remarkArr == null) {
				gradeRemarkMap.put(gradingScaleSteps.get(i).getStepDisplay(),
						"");
			} else {
				if(gradingScaleType.equalsIgnoreCase(ServerCommonConstant.GRADING_SCALE_STEP_DISPLAY_GRADING)){
					if (i < remarkArr.length) {
						gradeRemarkMap.put(gradingScaleSteps.get(i)
								.getStepDisplay(), remarkArr[i]);
					} else {
						gradeRemarkMap.put(gradingScaleSteps.get(i)
								.getStepDisplay(), "");
					}
				}else{
					if (i < remarkArr.length) {
						gradeRemarkMap.put(gradingScaleSteps.get(i)
								.getNumericDisplay(), remarkArr[i]);
					} else {
						gradeRemarkMap.put(gradingScaleSteps.get(i)
								.getNumericDisplay(), "");
					}
				}
				
			}
		}

		return gradeRemarkMap;
	}

	public static String[] parseSubComponentRemark(String remarks) {
		String[] remarkArr = null;
		if (remarks.contains("<") && remarks.contains(">")) {
			int startPos = remarks.indexOf("<");
			int endPos = remarks.indexOf(">");
			String subStr = remarks.substring(startPos, endPos + 1);
			String remarkString = subStr.replace("<", "");
			remarkString = remarkString.replace(">", "");
			if (remarkString.contains(",")) {
				remarkArr = remarkString.split(",");
			}
		}
		return remarkArr;
	}
	
	public static Double getComponentMaxMarksByTermAndStage(EvaluationComponent component,Term term, EvaluationStage stage) throws AppException{
		String maxMarksPropertyName = "";
		if (term == null && stage == null) {
			maxMarksPropertyName = "stage11Maxmarks";
		} else if (term != null && stage == null) {
			String termSeqValue = term.getSequenceValue().getValue().trim();
			maxMarksPropertyName = "stage" + termSeqValue + "1Maxmarks";
		} else if (term != null && stage != null) {
			String termSeqValue = term.getSequenceValue().getValue().trim();
			String stageSeqNum = stage.getStageSequenceValue().getValue().trim();
			maxMarksPropertyName = "stage" + termSeqValue + stageSeqNum + "Maxmarks";
		}

		double parentCompMaxMarks = 0;
		try {
			String compMaxMarks = String.valueOf(BeanUtils.getProperty(component, maxMarksPropertyName));
			parentCompMaxMarks = Double.valueOf(compMaxMarks);
			return parentCompMaxMarks;
		} catch (IllegalAccessException e) {
			throw new AppException("Cannot find method with name " + maxMarksPropertyName + " in EvaluationComponent",
					e);
		} catch (InvocationTargetException e) {
			throw new AppException("Cannot find method with name getStage " + maxMarksPropertyName
					+ " Maxmarks in EvaluationComponent", e);
		} catch (NoSuchMethodException e) {
			throw new AppException("Cannot find method with name getStage " + maxMarksPropertyName
					+ " Maxmarks in EvaluationComponent", e);
		}
	}
	
	public static String getGradeForMarks(List<GradingScaleSteps> steps,  double marks, double weightage){
		int noOfSteps = steps.size();
		
		// marks in percent
		double proportonalMarks = marks/weightage * 100;
	//	double gradeWeight = proportonalMarks*noOfSteps;
		String prev=steps.get(0).getStepDisplay();
		String next = steps.get(0).getStepDisplay();
		for(GradingScaleSteps step : steps){
			next = step.getStepDisplay();
			double max = Double.valueOf(step.getMaximum());
			double min =  Double.valueOf( step.getMinimum());
			if(proportonalMarks >= min && proportonalMarks<= max){
				return next;
			}else{
				prev = next;
			}
		}
		return next;
	}
}
