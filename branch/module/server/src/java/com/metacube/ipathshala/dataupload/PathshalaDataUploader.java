package com.metacube.ipathshala.dataupload;

import java.io.InputStream;

import org.springframework.stereotype.Component;

import com.metacube.ipathshala.core.AppException;
import com.metacube.ipathshala.util.AppLogger;

@Component
public class PathshalaDataUploader implements DataUploader {

	private static final AppLogger log = AppLogger
			.getLogger(PathshalaDataUploader.class);

	@Override
	public void uploadData(InputStream stream, DataUploadContext context)
			throws AppException {
		// TODO Auto-generated method stub
		
	}

	/*
	 * @Autowired DataUploadAcademicYearProcessor academicYearProcessor;
	 * 
	 * @Autowired DataUploadMyClassProcessor myClassProcessor;
	 * 
	 * @Autowired DataUploadSubjectProcessor subjectProcessor;
	 * 
	 * @Autowired DataUploadStudentProcessor studentProcessor;
	 * 
	 * @Autowired DataUploadParentProcessor parentProcessor;
	 * 
	 * @Autowired DataUploadTeacherProcessor teacherProcessor;
	 * 
	 * @Autowired DataUploadClassStudentProcessor classStudentProcessor;
	 * 
	 * @Autowired DataUploadClassSubjectTeacherProcessor
	 * classSubjectTeacherProcessor;
	 * 
	 * @Autowired DataUploadTimeTableEntryProcessor timeTableEntryProcessor;
	 * 
	 * @Autowired DataUpdateStudentProcessor updateStudentProcessor;
	 * 
	 * 
	 * @Override public void uploadData(InputStream stream,DataUploadContext
	 * context) throws AppException { LinkedList<String> entities =
	 * context.getEntityProcessingQueue(); try{ DataUploadReader reader =
	 * context.getReader(); //initialize the reader reader.init(stream);
	 * 
	 * DataUploadEntityProcessor processor = null; for(String entity: entities){
	 * int entityType = DataUploadEntityType.toInt(entity); ArrayList data =
	 * reader.readData(entityType); processor = getProcessor(entityType);
	 * 
	 * if(processor!=null){ //first pre-populate context with existing in db
	 * processor.populateExistingEntitiesinContext(context); if(data!=null &&
	 * !data.isEmpty()) processor.uploadEntity(data, context); } }
	 * }catch(Exception ex){ log.error("Import Data Failed",ex);
	 * ex.printStackTrace(); //TODO: Data rollback needs to be discussed
	 * for(String entity: entities){ int entityType =
	 * DataUploadEntityType.toInt(entity); rollBackEntity(entityType, context);
	 * } }
	 * 
	 * }
	 * 
	 * 
	 * private DataUploadEntityProcessor getProcessor(int entityType){
	 * DataUploadEntityProcessor processor = null; switch(entityType){ case
	 * DataUploadEntityType.ACADEMIC_YEAR_ENTITY: { processor =
	 * academicYearProcessor; break;} case DataUploadEntityType.MYCLASS_ENTITY:
	 * { processor = myClassProcessor; break;} case
	 * DataUploadEntityType.STUDENT_ENTITY: { processor = studentProcessor;
	 * break;} case DataUploadEntityType.PARENT_ENTITY: { processor =
	 * parentProcessor; break;} case DataUploadEntityType.SUBJECT_ENTITY: {
	 * processor = subjectProcessor; break;} case
	 * DataUploadEntityType.TEACHER_ENTITY: { processor = teacherProcessor;
	 * break;} case DataUploadEntityType.CLASSSTUDENT_ENTITY: { processor =
	 * classStudentProcessor; break;} case
	 * DataUploadEntityType.CLASSSUBJECTTEACHER_ENTITY: { processor =
	 * classSubjectTeacherProcessor; break;} case
	 * DataUploadEntityType.TIME_TABLE_ENTRY_ENTITY: {processor =
	 * timeTableEntryProcessor; break;} case
	 * DataUploadEntityType.STUDENT_UPDATE_ENTITY:{processor =
	 * updateStudentProcessor; break;} }
	 * 
	 * return processor; }
	 * 
	 * private void rollBackEntity(int entityType, DataUploadContext context){
	 * switch(entityType){ case DataUploadEntityType.ACADEMIC_YEAR_ENTITY: {
	 * academicYearProcessor.rollbackEntity(context); break;} case
	 * DataUploadEntityType.MYCLASS_ENTITY: {
	 * myClassProcessor.rollbackEntity(context); break;} case
	 * DataUploadEntityType.STUDENT_ENTITY: {
	 * studentProcessor.rollbackEntity(context); break;} case
	 * DataUploadEntityType.PARENT_ENTITY: {
	 * parentProcessor.rollbackEntity(context); break;} case
	 * DataUploadEntityType.SUBJECT_ENTITY: {
	 * subjectProcessor.rollbackEntity(context); break;} case
	 * DataUploadEntityType.TEACHER_ENTITY: {
	 * teacherProcessor.rollbackEntity(context); break;} case
	 * DataUploadEntityType.CLASSSTUDENT_ENTITY: {
	 * classStudentProcessor.rollbackEntity(context); break;} case
	 * DataUploadEntityType.CLASSSUBJECTTEACHER_ENTITY: {
	 * classSubjectTeacherProcessor.rollbackEntity(context); break;} case
	 * DataUploadEntityType.TIME_TABLE_ENTRY_ENTITY:
	 * {timeTableEntryProcessor.rollbackEntity(context); break;} case
	 * DataUploadEntityType
	 * .STUDENT_UPDATE_ENTITY:{updateStudentProcessor.rollbackEntity(context);
	 * break;} } }
	 */

}
