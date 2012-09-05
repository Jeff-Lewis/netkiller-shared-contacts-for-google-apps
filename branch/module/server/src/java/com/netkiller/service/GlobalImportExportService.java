package com.netkiller.service;

import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netkiller.core.AppException;
import com.netkiller.core.EntityType;
import com.netkiller.core.ImportEntityWorkflowConfig;
import com.netkiller.core.JAXBEntityPackageConfig;
import com.netkiller.entity.Workflow;
import com.netkiller.util.AppLogger;
import com.netkiller.vo.ImportVO;
import com.netkiller.workflow.WorkflowContext;
import com.netkiller.workflow.WorkflowInfo;
import com.netkiller.workflow.impl.context.DataUploadWorkflowContext;
import com.netkiller.workflow.impl.context.ExImpWorkflowContext;
import com.netkiller.workflow.impl.processor.WorkflowStatusType;

/**
 * @author dhruvsharma
 * 
 */
@Service
public class GlobalImportExportService {

	private static final AppLogger log = AppLogger
			.getLogger(GlobalImportExportService.class);

	@Autowired
	private JAXBEntityPackageConfig jaxbEntityPackageConfig;

	@Autowired
	private ImportEntityWorkflowConfig entityWorkflowConfig;

	@Autowired
	private WorkflowService workflowService;

	@Autowired
	IPathshalaQueueService iPathshalaQueueService;

	public void importFile(ImportVO importvo) throws AppException {		
		WorkflowContext workflowContext = null;		
			if(importvo!=null && importvo.getEntityType()!=null && importvo.getEntityType().equals(EntityType.DATA)){
				workflowContext = getDataWorkFlowContext(importvo);
			}else{
				workflowContext = getJAXBWorkFlowContext(importvo);
			}			
			WorkflowInfo info = new WorkflowInfo(entityWorkflowConfig
					.getWntityWorkflow(importvo.getEntityType()));
			info.setIsNewWorkflow(true);
			workflowContext.setWorkflowInfo(info);
			Workflow workflow = new Workflow();
			workflow.setWorkflowName(info.getWorkflowName());
			workflow.setWorkflowInstanceId(info.getWorkflowInstance());
			workflow.setWorkflowStatus(WorkflowStatusType.QUEUED.toString());
			workflow.setContext(workflowContext);
			workflowService.createWorkflow(workflow);
			iPathshalaQueueService.triggerWorkflow(workflow.getContext());
		
	}
	
	/**
	 * Get jaxb based object
	 * @param importvo
	 * @return
	 * @throws AppException
	 */
	private WorkflowContext getJAXBWorkFlowContext(ImportVO importvo) throws AppException{
		JAXBContext jaxbcontext;
		ExImpWorkflowContext workflowContext = new ExImpWorkflowContext();
		try {
			jaxbcontext = JAXBContext.newInstance(jaxbEntityPackageConfig
					.getEntityPackage(importvo.getEntityType()));
			Unmarshaller unmarshaller = jaxbcontext.createUnmarshaller();
			Object xmlObjectList = (Object) unmarshaller.unmarshal(importvo
					.getStream());
			
			workflowContext.setXmlObject(xmlObjectList);			
		} catch (JAXBException jaxbexception) {
			String msg = "Could not parse XML file selected does not match schema";
			log.error(msg, jaxbexception);
			throw new AppException(msg, jaxbexception);
		}
		
		return workflowContext;
	}
	
	/**
	 * Simple delegate the input stream object onto context
	 * @param importvo
	 * @return
	 * @throws AppException
	 */
	private WorkflowContext getDataWorkFlowContext(ImportVO importvo) throws AppException{
		
		DataUploadWorkflowContext workflowContext = new DataUploadWorkflowContext();
		try {
			workflowContext.setXLSObject(IOUtils.toByteArray(importvo.getStream()));
		} catch (IOException ex) {
			String msg = "Could not read file selected";
			log.error(msg, ex);
			throw new AppException(msg, ex);
		}
		
		return workflowContext;
	}
	
	

}
