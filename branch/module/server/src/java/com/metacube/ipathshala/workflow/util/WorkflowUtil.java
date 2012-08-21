package com.metacube.ipathshala.workflow.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.google.appengine.api.datastore.Blob;
import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.util.AppLogger;
import com.metacube.ipathshala.workflow.WorkflowContext;

/**
 * @author dhruvsharma
 * 
 *         Has methods to convert Blob to WorkflowContext and vice-versa
 */
public class WorkflowUtil {

	private static AppLogger log = AppLogger.getLogger(WorkflowUtil.class);

	public static WorkflowContext convertBlobToWorkflowContext(Blob workflowcontext) throws AppException {
		byte[] bytes = workflowcontext.getBytes();
		Object object = null;
		try {
			object = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bytes)).readObject();
		} catch (IOException e) {
			log.error("Cannot Convert Blob to WorkflowContext" + workflowcontext.toString());
			throw new AppException("Cannot convert Blob to WorkflowContext", e);
		} catch (ClassNotFoundException e) {
			log.error("Cannot Convert Blob to WorkflowContext" + workflowcontext.toString());
			throw new AppException("Cannot convert Blob to WorkflowContext", e);
		}
		WorkflowContext context = (WorkflowContext) object;
		return context;
	}

	public static Blob convertWorkflowContextToBlob(WorkflowContext context) throws AppException {
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream objectoutputstream = new ObjectOutputStream(bytearrayoutputstream);
			objectoutputstream.writeObject(context);
			return new Blob(bytearrayoutputstream.toByteArray());
		} catch (java.io.IOException ioe) {
			log.error("Cannot Convert WorkflowContext to Blob " + context.getWorkflowInfo().getWorkflowInstance());
			throw new AppException("Cannot convert WorkflowContext to Blob", ioe);
		}
	}

}
