package com.netkiller.workflow.impl.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.appengine.api.datastore.Key;
import com.netkiller.core.AppException;
import com.netkiller.core.jaxb.value.ValueElement;
import com.netkiller.core.jaxb.value.ValueList;
import com.netkiller.entity.Set;
import com.netkiller.entity.Value;
import com.netkiller.manager.SetManager;
import com.netkiller.manager.ValueManager;
import com.netkiller.util.AppLogger;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.impl.context.ExImpWorkflowContext;

@Component
public class ValueImportTask extends AbstractWorkflowTask {

	private static AppLogger log = AppLogger.getLogger(ValueImportTask.class);

	@Autowired
	ValueManager valueManager;

	@Autowired
	SetManager setManager;

	@Override
	public WorkflowContext execute(WorkflowContext context) {
		ExImpWorkflowContext workflowContext = (ExImpWorkflowContext) context;
		ValueList valueList = (ValueList) workflowContext.getXmlObject();
		if (valueList != null) {
			List<ValueElement> valueElementList = valueList.getValueElement();
			Map<String, Set> setMap = new HashMap<String, Set>();
			Map<String, List<Value>> setValueMap = new HashMap<String, List<Value>>();
			List<Set> existingSetList = new ArrayList<Set>();
			try {
				existingSetList = (List<Set>) setManager.getAllSets();
				for (Iterator<Set> iter = existingSetList.iterator(); iter
						.hasNext();) {
					Set currentSet = (Set) iter.next();
					setMap.put(currentSet.getSetName(), currentSet);
					setValueMap.put(currentSet.getSetName(),
							(List<Value>) valueManager
									.getValueBySetKey(currentSet));
				}
			} catch (AppException e) {
				log.error("Failed to Get a List Of Existing Sets or Values", e);
			}
			// creating a map of all valueElements in ValueList
			Map<String, ValueElementProcessInfo> infoMap = createMap(valueList);
			for (Iterator<ValueElement> iterator = valueElementList.iterator(); iterator
					.hasNext();) {
				processNode((ValueElement) iterator.next(), setMap,
						setValueMap, infoMap);
			}
		}
		return workflowContext;
	}

	private Map<String, ValueElementProcessInfo> createMap(ValueList valueList) {
		List<ValueElement> valueElementList = valueList.getValueElement();
		Map<String, ValueElementProcessInfo> infoMap = new HashMap<String, ValueElementProcessInfo>();
		for (Iterator<ValueElement> iterator = valueElementList.iterator(); iterator
				.hasNext();) {
			ValueElement currentValueElement = (ValueElement) iterator.next();
			ValueElementProcessInfo currentValueElementInfo = new ValueElementProcessInfo(
					currentValueElement, false);
			infoMap.put(currentValueElement.getVal(), currentValueElementInfo);
		}
		return infoMap;
	}

	private void processNode(ValueElement node, Map<String, Set> setMap,
			Map<String, List<Value>> existingNodes,
			Map<String, ValueElementProcessInfo> infoMap) {
		ValueElementProcessInfo processInfo = infoMap.get(node.getVal());
		if (processInfo.isProcessed() == true) {
			// already processed value
		}
		if (setMap.containsKey(node.getSet())) {
			// set exists
			// checking if value already exists in the set
			if (containsValue(node, existingNodes) != null) {
				processInfo.setProcessed(true);
			} else {
				try {
					Value newValue = new Value();
					newValue.setOrderIndex(node.getOrderIndex().intValue());
					newValue.setSetKey(setMap.get(node.getSet()).getKey());
					newValue.setValue(node.getVal());
					if (node.getParentValue() != null
							&& setMap.get(node.getSet()).getParentSetKey() != null) {
						ValueElement parentValue = infoMap.get(
								node.getParentValue()).getValueElement();
						if (!parentValue.getVal().equals(newValue.getValue())) {
							processNode(parentValue, setMap, existingNodes,
									infoMap);
							// save parent Node in existingNode List
							newValue.setParentValueKey(containsValue(
									parentValue, existingNodes));
						}
					}
					Value createdValue = valueManager.createValue(newValue);
					existingNodes.get(node.getSet()).add(createdValue);
				} catch (AppException e) {
					log.error("Failed to create New Value", e);
				}
			}
		}

	}

	private Key containsValue(ValueElement node,
			Map<String, List<Value>> existingNodes) {
		for (Iterator<Value> iterator = existingNodes.get(node.getSet())
				.iterator(); iterator.hasNext();) {
			Value currentValue = iterator.next();
			if (node.getVal().equals(currentValue.getValue())) {
				return currentValue.getKey();
			}
		}
		return null;
	}

}
