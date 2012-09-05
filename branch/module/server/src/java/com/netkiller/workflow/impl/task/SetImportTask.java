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
import com.netkiller.core.jaxb.set.SetElement;
import com.netkiller.core.jaxb.set.SetList;
import com.netkiller.entity.Set;
import com.netkiller.manager.SetManager;
import com.netkiller.util.AppLogger;
import com.netkiller.workflow.AbstractWorkflowTask;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.impl.context.ExImpWorkflowContext;

@Component
public class SetImportTask extends AbstractWorkflowTask {

	private static AppLogger log = AppLogger.getLogger(SetImportTask.class);

	@Autowired
	SetManager setManager;

	SetImportTask() {
		super();
	}

	@Override
	public WorkflowContext execute(WorkflowContext context) {
		ExImpWorkflowContext workflowContext = (ExImpWorkflowContext) context;
		SetList setList = (SetList) workflowContext.getXmlObject();
		if (setList != null) {
			List<SetElement> setlist = setList.getSetElement();
			Map<String, Key> setKeyMap = new HashMap<String, Key>();
			List<Set> existingSetList = new ArrayList<Set>();
			try {
				existingSetList = (List<Set>) setManager.getAllSets();
				for (Iterator<Set> iter = existingSetList.iterator(); iter.hasNext();) {
					Set currentSet = (Set) iter.next();
					setKeyMap.put(currentSet.getSetName(), currentSet.getKey());
				}
			} catch (AppException e) {
				log.error("Failed to Get a List Of Existing Sets", e);
			}
			// creating a map of all setElements in SetList
			Map<String, SetElementProcessInfo> infoMap = createMap(setList);
			// process all the nodes of this map
			for (Iterator<SetElement> iterator = setlist.iterator(); iterator.hasNext();) {
				processNode((SetElement) iterator.next(), setKeyMap, infoMap);
			}
		}
		return workflowContext;
	}

	private Map<String, SetElementProcessInfo> createMap(SetList setList) {
		List<SetElement> setElementList = setList.getSetElement();
		Map<String, SetElementProcessInfo> infoMap = new HashMap<String, SetElementProcessInfo>();
		for (Iterator<SetElement> iterator = setElementList.iterator(); iterator.hasNext();) {
			SetElement currentSetElement = (SetElement) iterator.next();
			SetElementProcessInfo currentSetElementInfo = new SetElementProcessInfo(currentSetElement, false);
			infoMap.put(currentSetElement.getSetName(), currentSetElementInfo);
		}
		return infoMap;
	}

	private void processNode(SetElement node, Map<String, Key> existingNodes, Map<String, SetElementProcessInfo> infoMap) {
		String setName = node.getSetName();
		SetElementProcessInfo processInfo = infoMap.get(setName);
		String parentSetName = node.getParentSetName();
		if (processInfo.isProcessed() == true) {
		}
		if (existingNodes.containsKey(setName)) {
			infoMap.get(setName).setProcessed(true);
		} else {
			Set newSet = new Set();
			newSet.setSetName(setName);
			newSet.setSetOrder(node.getSetOrder());
			if (parentSetName != null) {
				SetElement parentNode = infoMap.get(parentSetName).getSetElement();
				processNode(parentNode, existingNodes, infoMap);
				newSet.setParentSetKey(existingNodes.get(node.getParentSetName()));
			}
			try {
				Set createdSet = setManager.createSet(newSet);
				existingNodes.put(createdSet.getSetName(), createdSet.getKey());
			} catch (AppException e) {
				log.error("Failed to create New Set", e);
			}
		}
	}

}
