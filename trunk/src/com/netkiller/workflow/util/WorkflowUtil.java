package com.netkiller.workflow.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.google.appengine.api.datastore.Blob;
import com.netkiller.exception.AppException;
import com.netkiller.workflow.WorkflowContext;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author dhruvsharma
 * 
 *         Has methods to convert Blob to WorkflowContext and vice-versa
 */
public class WorkflowUtil {

	protected final static Logger log = Logger.getLogger(WorkflowUtil.class.getSimpleName());

	public static WorkflowContext convertBlobToWorkflowContext(Blob workflowcontext) throws AppException {
		byte[] bytes = workflowcontext.getBytes();
		Object object = null;
		try {
			object = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bytes)).readObject();
		} catch (IOException e) {
			log.log(Level.SEVERE,"Cannot Convert Blob to WorkflowContext" + workflowcontext.toString());
			throw new AppException("Cannot convert Blob to WorkflowContext", e);
		} catch (ClassNotFoundException e) {
			log.log(Level.SEVERE,"Cannot Convert Blob to WorkflowContext" + workflowcontext.toString());
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
			log.log(Level.SEVERE,"Cannot Convert WorkflowContext to Blob " + context.getWorkflowInfo().getWorkflowInstance());
			throw new AppException("Cannot convert WorkflowContext to Blob", ioe);
		}
	}

}
