package com.metacube.ipathshala.workflow.impl.task;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;

import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.core.DataContext;
import com.metacube.ipathshala.dataupload.DataUploadEntityType;
import com.metacube.ipathshala.dataupload.PathshalaDataUploadContext;
import com.metacube.ipathshala.dataupload.PathshalaDataUploader;
import com.metacube.ipathshala.dataupload.XLSDataReader;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.workflow.AbstractWorkflowTask;
import com.metacube.ipathshala.workflow.WorkflowContext;
import com.metacube.ipathshala.workflow.WorkflowExecutionException;
import com.metacube.ipathshala.workflow.impl.context.DataUploadWorkflowContext;

/**
 * 
 * 
 * 
 * @author gaurav
 *
 */

public class DataUploadTask extends AbstractWorkflowTask {

	private static final AppLogger log = AppLogger.getLogger(DataUploadTask.class);
	
	@Autowired
	private PathshalaDataUploader uploader;	

	@Override
	/**
	 * This method performs read of excel based data and upload the data in database
	 */
	public WorkflowContext execute(WorkflowContext context) throws WorkflowExecutionException {
		
		DataUploadWorkflowContext workflowContext = (DataUploadWorkflowContext) context;
		InputStream stream = new ByteArrayInputStream(workflowContext.getXLSObject());
		
		PathshalaDataUploadContext uploadContext = new PathshalaDataUploadContext();
		uploadContext.setReader(new XLSDataReader());
		uploadContext.setDataContext(new DataContext());
		LinkedList<String> entities = uploadContext.getEntityProcessingQueue();
		entities.add(DataUploadEntityType.S_ACADEMIC_YEAR_ENTITY);
		entities.add(DataUploadEntityType.S_MYCLASS_ENTITY);
		entities.add(DataUploadEntityType.S_PARENT_ENTITY);
		entities.add(DataUploadEntityType.S_STUDENT_ENTITY);
		entities.add(DataUploadEntityType.S_SUBJECT_ENTITY);
		entities.add(DataUploadEntityType.S_TEACHER_ENTITY);
		entities.add(DataUploadEntityType.S_CLASSSTUDENT_ENTITY);
		entities.add(DataUploadEntityType.S_CLASSSUBJECTTEACHER_ENTITY);
		entities.add(DataUploadEntityType.S_TIME_TABLE_ENTRY_ENTITY);
		entities.add(DataUploadEntityType.S_STUDENT_UPDATE_ENTITY);
		try {
			uploader.uploadData(stream, uploadContext);
		} catch (AppException ex) {
			log.error("Failed to import Data", ex);			
			throw new WorkflowExecutionException("Import Data Execution Failed",ex);
		}
		return context;	
	}
}
