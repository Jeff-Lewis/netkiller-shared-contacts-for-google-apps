package com.metacube.ipathshala;

public class UICommonConstants {
	/**
	 * Request attribute defining user operation selected.
	 */
	public static final String ATTRIB_OPER = "operation";

	// user operation create.
	public static final String OPER_CREATE = "create";
	// user operation edit.
	public static final String OPER_EDIT = "edit";
	public static final String OPER_EDIT_VERSION = "editVersion";

	public static final String PARAM_ENTITY_ID = "paramid";

	public static final String ADV_SEARCH_TEXT_PARAM = "advSearchField";

	public static final String ATTRIB_ADV_SEARCH_TXT = "advSearchText";
	public static final String ATTRIB_VALUE_UNDEFINED = "undefined";
	public static final String ATTRIB_VALUE_NULL = "null";
	public static final String MINUTE_LIST = "minList";
	public static final String STUDENT_LIST = "globalStudentList";

	/********************************************************************************************************************/
	/**
	 * spring model attribute studentMiscelleneous
	 */
	public static final String ATTRIB_STUDENT_MISCELLANEOUS = "studentMiscelleneous";

	// Context view defining create studentMiscelleneous page.
	public static final String CONTEXT_CREATE_STUDENT_MISCELLANEOUS = "studentmiscellaneous/createStudentMiscellaneous.jsp";

	// Context view defining studentMiscelleneous detail page.
	public static final String CONTEXT_STUDENT_MISCELLANEOUS_DETAIL = "studentmiscellaneous/studentMiscellaneousDetail.jsp";

	public static final String CONTEXT_STUDENT_MISCELLANEOUS_INDEX = "studentmiscellaneous/createStudentMiscellaneous.jsp";
	/********************************************************************************************************************/
	/**
	 * Spring model attribute student.
	 */
	public static final String ATTRIB_STUDENT = "student";

	public static final String ATTRIB_SCHOOL = "school";
	/**
	 * Name defining context view attribute
	 */
	public static final String ATTRIB_CONTEXT_VIEW = "_ipContextView";

	// View name to resolve index page.
	public static final String VIEW_INDEX = "index";

	// Context view defining create student page.
	public static final String CONTEXT_GADGET_CONTAINER = "gadgetContainer.jsp";

	// Context view defining create student page.
	public static final String CONTEXT_CREATE_STUDENT = "student/createStudent.jsp";
	// Context view defining student detail page.
	public static final String CONTEXT_STUDENT_DETAIL = "student/studentDetail.jsp";

	// Context view defining teacher index page
	public static final String CONTEXT_STUDENT_INDEX = "student/studentHome.jsp";

	// Name for student form used in defining validation rule.
	public static final String FORM_STUDENT = "StudentForm";

	// Parent Key (long) in Student Object
	public static final String PARAM_PARENT_KEY = "parentKeyString";

	// MyClass Key (long) in Student Object
	public static final String PARAM_CLASS_KEY = "classKeyString";

	// Parent Business Key Element
	public static final String PARAM_PARENT_BUSINESSKEY = "parentBusinessKey";

	// MyClass Business Key Element
	public static final String PARAM_CLASS_BUSINESSKEY = "classBusinessKey";

	// Gender Key Element
	public static final String PARAM_GENDER_KEY = "genderKey";
	public static final String PARAM_LIVING_STATUS_KEY = "livingStatusKeyValue";

	public static final String PARAM_PRESENT_ADDRESS_CITY_KEY = "addressCityKey";
	public static final String PARAM_PRESENT_ADDRESS_CITY = "addressCity";

	public static final String PARAM_PRESENT_ADDRESS_STATE_KEY = "addressStateKey";

	public static final String PARAM_PRESENT_ADDRESS_COUNTRY_KEY = "addressCountryKey";

	public static final String PARAM_PRESENT_ADDRESS_CITY_TEACHER = "presentAddressCityText";
	public static final String PARAM_PERMANENT_ADDRESS_CITY_TEACHER = "permanentAddressCityText";
	public static final String PARAM_BLOOD_GROUP_KEY = "bloodGroupKeyLong";
	public static final String PARAM_TITLE_KEY = "titleKeyLong";

	/********************************************************************************************************************/
	/**
	 * Spring model attribute subject.
	 */
	public static final String ATTRIB_SUBJECT = "subject";

	// Context view defining create subject page.
	public static final String CONTEXT_CREATE_SUBJECT = "subject/createSubject.jsp";
	// Context view defining subject detail page.
	public static final String CONTEXT_SUBJECT_DETAIL = "subject/subjectDetail.jsp";

	public static final String CONTEXT_SUBJECT_INDEX = "subject/subjectHome.jsp";

	public static final String CONTEXT_CREATE_SUBJECT_VERSION = "subject/createSubjectVersion.jsp";

	// Name for subject form used in defining validation rule.
	public static final String FORM_SUBJECT = "SubjectForm";

	// Academic Year Key (long) in Student,Period & Student Object
	public static final String PARAM_ACADEMICYEAR_KEY = "academicYearKeyString";

	// Academic Year Business Key Element
	public static final String PARAM_ACADEMICYEAR_BUSINESSKEY = "academicYearBusinessKey";
	public static final String PARAM_SUBJECT_LEVEL_KEY = "subjectLevel";

	/*************************************************************************************************************/
	/**
	 * Spring model attribute teacher.
	 */
	public static final String ATTRIB_TEACHER = "teacher";

	// Context view defining create teacher page.
	public static final String CONTEXT_CREATE_TEACHER = "teacher/createTeacher.jsp";

	// Context view defining teacher detail page.
	public static final String CONTEXT_TEACHER_DETAIL = "teacher/teacherDetail.jsp";

	public static final String CONTEXT_TEACHER_INDEX = "teacher/teacherHome.jsp";

	// Name for teacher form used in defining validation rule.
	public static final String FORM_TEACHER = "TeacherForm";

	public static final String PARAM_PERMANENT_ADDRESS_CITY_KEY = "paddressCityKey";

	public static final String PARAM_PERMANENT_ADDRESS_STATE_KEY = "paddressStateKey";

	public static final String PARAM_PERMANENT_ADDRESS_COUNTRY_KEY = "paddressCountryKey";

	public static final String PARAM_TEACHING_LEVEL_KEY = "teacherLevelKey";
	public static final String PARAM_GENDER = "genderKeyParam";

	/*************************************************************************************************************/
	/**
	 * Spring model attribute location.
	 */
	public static final String ATTRIB_GRADINGSCALE = "gradingscale";

	// Context view defining create gradingscale page.
	public static final String CONTEXT_CREATE_GRADINGSCALE = "gradingscale/createGradingScale.jsp";
	// Context view defining gradingscale detail page.
	public static final String CONTEXT_GRADINGSCALE_DETAIL = "gradingscale/gradingScaleDetail.jsp";

	public static final String CONTEXT_GRADINGSCALE_INDEX = "gradingscale/gradingScaleHome.jsp";

	// Name for gradingscale form used in defining validation rule.
	public static final String FORM_GRADINGSCALE = "GradingScaleForm";

	/*************************************************************************************************************/
	/**
	 * Spring model attribute location.
	 */
	public static final String ATTRIB_LOCATION = "location";

	// Context view defining create location page.
	public static final String CONTEXT_CREATE_LOCATION = "location/createLocation.jsp";
	// Context view defining location detail page.
	public static final String CONTEXT_LOCATION_DETAIL = "location/locationDetail.jsp";

	public static final String CONTEXT_LOCATION_INDEX = "location/locationHome.jsp";

	// Name for location form used in defining validation rule.
	public static final String FORM_LOCATION = "LocationForm";

	/*************************************************************************************************************/

	/**
	 * Spring model attribute academicyear.
	 */
	public static final String ATTRIB_ACADEMICYEAR = "academicyear";

	// Context view defining create academicyear page.
	public static final String CONTEXT_CREATE_ACADEMICYEAR = "academicyear/createAcademicYear.jsp";
	// Context view defining academicyear detail page.
	public static final String CONTEXT_ACADEMICYEAR_DETAIL = "academicyear/academicyearDetail.jsp";

	public static final String CONTEXT_ACADEMICYEAR_INDEX = "academicyear/academicyearHome.jsp";

	// Name for academicyear form used in defining validation rule.
	public static final String FORM_ACADEMICYEAR = "AcademicYearForm";

	public static final String PARAM_ACADEMIC_YEAR_TYPE_KEY = "academicYearTypeKey";

	/**
	 * Spring model attribute academicyearstructure.
	 */
	public static final String ATTRIB_ACADEMIC_YEAR_STRUCTURE = "academicyearstructure";

	public static final String CONTEXT_CREATE_ACADEMIC_YEAR_STRUCTURE = "academicyearstructure/createAcademicYearStructure.jsp";
	public static final String CONTEXT_ACADEMIC_YEAR_STRUCTURE_DETAIL = "academicyearstructure/academicYearStructureDetail.jsp";

	public static final String CONTEXT_ACADEMIC_YEAR_STRUCTURE_INDEX = "academicyearstructure/academicYearStructureHome.jsp";

	public static final String FORM_ACADEMIC_YEAR_STRUCTURE = "academicYearStructureForm";

	public static final String PARAM_ACADEMIC_YEAR_STRUCTURE_KEY = "academicYearStructureKey";

	public static final String PARAM_ACADEMIC_YEAR_STRUCTURE_FROM_CLASS = "fromClassLevelKey";

	public static final String PARAM_ACADEMIC_YEAR_STRUCTURE_TO_CLASS = "toClassLevelKey";

	/*************************************************************************************************************/
	/**
	 * spring model attribute term.
	 * 
	 */
	public static final String ATTRIB_TERM = "term";

	public static final String TERM_KEY = "paramid";

	public static final String TERM_ACADEMIC_YEAR_KEY = "academicYearKeyLong";

	public static final String TERM_SEQUENCE_KEY = "sequenceKeyLong";
	// Context view defining create term page.
	public static final String CONTEXT_CREATE_TERM = "term/createTerm.jsp";
	public static final String CONTEXT_TERM_INDEX = "term/termHome.jsp";

	public static final String CONTEXT_TERM_DETAIL = "term/termDetail.jsp";

	// Name for term form used in defining validation rule.
	public static final String FORM_TERM = "TermForm";

	public static final String SUBJECT_EVALUATION_EVENT_FORM = "SubjectEvaluationEventForm";

	/*************************************************************************************************************/
	/**
	 * spring model attribute Report Card.
	 * 
	 */

	/*************************************************************************************************************/

	public static final String ATTRIB_REPORT_CARD = "reportCard";

	public static final String CONTEXT_CREATE_REPORT_CARD = "reportCard/createReportCard.jsp";

	public static final String CONTEXT_REPORT_CARD_DETAIL = "reportCard/reportCardDetail.jsp";

	public static final String FROM_LEVEL_KEY = "fromClassLevelKey";

	public static final String TO_LEVEL_KEY = "toClassLevelKey";

	public static final String REPORT_CARD_TYPE_KEY = "reportCardTypeBusinessKey";

	public static final String ACADEMIC_YEAR_KEY = "academicYearKeyLong";

	public static final String TERM_KEY_LONG = "termKeyLong";

	public static final String STAGE_KEY_LONG = "stageKeyLong";

	/**
	 * spring model attribute EvaluationStage.
	 * 
	 */
	public static final String ATTRIB_EVALUATIONSTAGE = "evaluationStage";

	public static final String TERM_STAGE_KEY = "termStageid";

	public static final String EVALUATIONSTAGE_TERM_KEY = "paramEntryid";

	public static final String EVALUATIONSTAGE_KEY = "paramid";

	public static final String EVALUATIONSTAGE_SEQUENCE_KEY = "sequenceKeyLong";
	// Context view defining create EvaluationStage page.
	public static final String CONTEXT_CREATE_EVALUATIONSTAGE = "evaluationStage/createEvaluationStage.jsp";
	public static final String CONTEXT_EVALUATIONSTAGE_INDEX = "evaluationStage/evaluationStageHome.jsp";

	public static final String CONTEXT_EVALUATIONSTAGE_DETAIL = "evaluationStage/evaluationStageDetail.jsp";

	// Name for EvaluationStage form used in defining validation rule.
	public static final String FORM_EVALUATIONSTAGE = "EvaluationStageForm";

	/*************************************************************************************************************/
	/**
	 * Spring model attribute address.
	 */
	public static final String ATTRIB_ADDRESS = "address";

	// Context view defining create address page.
	public static final String CONTEXT_CREATE_ADDRESS = "address/createAddress.jsp";
	// Context view defining address detail page.
	public static final String CONTEXT_ADDRESS_DETAIL = "address/addressDetail.jsp";
	// Context view defining address index page
	public static final String CONTEXT_ADDRESS_INDEX = "address/addressHome.jsp";

	// Name for address form used in defining validation rule.
	public static final String FORM_ADDRESS = "AddressForm";

	/*************************************************************************************************************/

	/**
	 * Spring model attribute period.
	 */
	public static final String ATTRIB_PERIOD = "period";

	// Context view defining create period page.
	public static final String CONTEXT_CREATE_PERIOD = "period/createPeriod.jsp";
	// Context view defining period detail page.
	public static final String CONTEXT_PERIOD_DETAIL = "period/periodDetail.jsp";
	// Context view defining period index page
	public static final String CONTEXT_PERIOD_INDEX = "period/periodHome.jsp";

	// Name for period form used in defining validation rule.
	public static final String FORM_PERIOD = "PeriodForm";

	public static final String PARAM_PERIOD_TYPE_KEY = "periodTypeKey";

	/********************************************************************************************************************/
	/**
	 * Spring model attribute parent.
	 */
	public static final String ATTRIB_PARENT = "parent";

	// Context view defining create parent page.
	public static final String CONTEXT_CREATE_PARENT = "parent/createParent.jsp";
	// Context view defining parent detail page.
	public static final String CONTEXT_PARENT_DETAIL = "parent/parentDetail.jsp";

	public static final String CONTEXT_PARENT_INDEX = "parent/parentHome.jsp";

	// Name for parent form used in defining validation rule.
	public static final String FORM_PARENT = "ParentForm";

	/*************************************************************************************************************/
	/**
	 * Spring model attribute myclass.
	 */
	public static final String ATTRIB_MYCLASS = "myclass";

	// Context view defining create myclass page.
	public static final String CONTEXT_CREATE_MYCLASS = "myclass/createMyClass.jsp";

	// Context view defining myclass detail page.
	public static final String CONTEXT_MYCLASS_DETAIL = "myclass/myclassDetail.jsp";

	public static final String CONTEXT_MYCLASS_INDEX = "myclass/myclassHome.jsp";

	// Name for myclass form used in defining validation rule.
	public static final String FORM_MYCLASS = "MyClassForm";

	// location key in long form
	public static final String PARAM_LOCATION_KEY = "locationKeyString";

	// Teacher Key in long form
	public static final String PARAM_CLASSTEACHER_KEY = "classTeacherKeyString";

	// Teacher's Business Key Element
	public static final String PARAM_TEACHER_BUSINESSKEY = "teacherBusinessKey";

	// Location's Business Key Element
	public static final String PARAM_LOCATION_BUSINESSKEY = "locationBusinessKey";

	public static final String PARAM_CLASS_TYPE_KEY = "typeKey";

	public static final String PARAM_CLASS_LEVEL_KEY = "classLevelKey";

	public static final String PARAM_GRADING_COLOR_KEY = "gradingColorKey";

	/*************************************************************************************************************/

	/*************************************************************************************************************/

	/**
	 * Spring model attribute set..
	 */
	public static final String ATTRIB_SET = "set";

	// Context view defining create set page.
	public static final String CONTEXT_CREATE_SET = "set/createSet.jsp";
	// Context view defining set detail page.
	public static final String CONTEXT_SET_DETAIL = "set/setDetail.jsp";

	public static final String CONTEXT_SET_INDEX = "set/setHome.jsp";

	// Name for set form used in defining validation rule.
	public static final String FORM_SET = "SetForm";

	/*************************************************************************************************************/

	/*************************************************************************************************************/

	/**
	 * Spring model attribute set..
	 */
	public static final String ATTRIB_DATAUPLOAD = "dataupload";

	public static final String CONTEXT_DATAUPLOAD_INDEX = "dataupload/datauploadHome.jsp";

	public static final String ATTRIB_SENDSMS = "sendsms";

	public static final String CONTEXT_SENDSMS_INDEX = "sendsms/sendsmsHome.jsp";

	// Name for send sms form used in defining validation rule.
	public static final String FORM_SENDSMS = "sendSMSForm";

	public static final String ATTRIB_SENDSMSVO = "sendSMSVO";
	/*************************************************************************************************************/

	/*************************************************************************************************************/

	/**
	 * Spring model attribute set..
	 */
	public static final String ATTRIB_VALUE = "value";

	// Context view defining create value page.
	public static final String CONTEXT_CREATE_VALUE = "value/createValue.jsp";
	// Context view defining value detail page.
	public static final String CONTEXT_VALUE_DETAIL = "value/valueDetail.jsp";

	public static final String CONTEXT_VALUE_INDEX = "value/valueHome.jsp";

	// Name for value form used in defining validation rule.
	public static final String FORM_VALUE = "ValueForm";

	/*************************************************************************************************************/

	public static final String LOV_NULL_VALUE = "none";

	public static final String VIEW_LIST_OF_VALUES = "set/listOfValues";

	/**
	 * Spring model attribute note.
	 */
	public static final String ATTRIB_NOTE = "note";

	// Context view defining create note page.
	public static final String CONTEXT_CREATE_NOTE = "note/createNote.jsp";
	// Context view defining note detail page.
	public static final String CONTEXT_NOTE_DETAIL = "note/noteDetail.jsp";
	// Context view defining note index page
	public static final String CONTEXT_NOTE_INDEX = "note/noteHome.jsp";

	// Name for note form used in defining validation rule.
	public static final String FORM_NOTE = "NoteForm";

	public static final String PARAM_NOTE_TYPE_KEY = "noteTypeKey";
	public static final String PARAM_NOTE_STATUS_KEY = "noteStatKey";
	public static final String PARAM_NOTE_PRIORITY_KEY = "notePriorKey";
	public static final String PARAM_NOTE_SENDER_ID = "senderId";
	public static final String PARAM_NOTE_SENDER_BUSINESSKEY = "senderBusinessKey";
	public static final String PARAM_NOTE_STUDENT_ID = "studId";
	public static final String PARAM_NOTE_STUDENT_BUSINESS_KEY = "studBusinessKey";
	public static final String PARAM_NOTE_SENDER = "sender";
	public static final String OPER_REPLY = "reply";
	public static final String PARAM_NOTE_SUBJECT = "subject";

	/********************************************************************************************************************/

	/********************************************************************************************************************/
	/**
	 * Spring model attribute event.
	 */
	public static final String ATTRIB_EVENT = "event";

	// Context view defining create event page.
	public static final String CONTEXT_CREATE_EVENT = "event/createEvent.jsp";
	// Context view defining event detail page.
	public static final String CONTEXT_EVENT_DETAIL = "event/eventDetail.jsp";

	public static final String CONTEXT_EVENT_INDEX = "event/eventHome.jsp";

	// Name for event form used in defining validation rule.
	public static final String FORM_EVENT = "EventForm";

	public static final String PARAM_EVENT_TYPE = "eventType";

	public static final String USER_ROLE_TYPE = "userRole";

	/*************************************************************************************************************/
	/**
	 * Spring model attribute for relation between class, subject and teacher.
	 */
	public static final String ATTRIB_CLASS_SUBJECT_TEACHER = "classSubjectTeacher";

	public static final String CONTEXT_CREATE_CLASS_SUBJECT_TEACHER = "relation/relateClassSubjectTeacher.jsp";

	public static final String CONTEXT_CLASS_SUBJECT_TEACHER_DETAIL = "relation/classSubjectTeacherDetail.jsp";

	public static final String CONTEXT_CLASS_SUBJECT_TEACHER_INDEX = "relation/classSubjectTeacherHome.jsp";

	/*************************************************************************************************************/
	/**
	 * Context index showing options to create all type of relationship in the
	 * application.
	 */
	public static final String CONTEXT_RELATION_INDEX = "relation/options.jsp";
	/*************************************************************************************************************/

	/**
	 * Spring model attribute for relation between class and student.
	 */
	public static final String ATTRIB_CLASS_STUDENT = "classStudent";

	public static final String CONTEXT_CREATE_CLASS_STUDENT = "relation/relateClassStudent.jsp";

	public static final String CONTEXT_CLASS_STUDENT_DETAIL = "relation/classStudentDetail.jsp";

	public static final String CONTEXT_CLASS_STUDENT_INDEX = "relation/classStudentHome.jsp";
	public static final String PARAM_ENTRY_ENTITY_ID = "entryEntityId";
	public static final String PARAM_CLASS_ID = "classId";
	public static final String PARAM_RELATION_FIELD_NAME = "relationFieldName";
	public static final String PARAM_VALUE_SET_NAME = "valueSetName";

	public static final String PARAM_RELATION_ID = "relationshipId";

	/**
	 * Spring model attribute for relation between class and timetable.
	 */
	public static final String ATTRIB_CLASS_TIMETABLE = "timeTable";

	public static final String CONTEXT_CREATE_CLASS_TIMETABLE = "relation/createTimeTable.jsp";

	public static final String CONTEXT_ADD_STUDENTS = "relation/addStudents.jsp";

	/*************************************************************************************************************/
	/**
	 * Spring model attribute for relation between class, subject and teacher.
	 */
	public static final String ATTRIB_CLASS_SUBJECT = "classSubjectTeacher";

	/*************************************************************************************************************/
	/**
	 * Spring model attribute for Period
	 */
	public static final String CONTEXT_PERIODS_COPY = "period/copyPeriodsForm.jsp";

	/**
	 * constansts for widgets
	 */
	public static final String CONTEXT_NOTE_WIDGET = "widget/notewidget.jsp";
	public static final String CONTEXT_ANNOUNCEMENT_WIDGET = "widget/announcementwidget.jsp";
	public static final String CONTEXT_LATEST_ACTIVITY_WIDGET = "widget/latestActivityWidget.jsp";
	public static final String CONTEXT_LATEST_ACTIVITY_WIDGET_RETURN = "widget/latestActivityWidget";
	public static final String CONTEXT_NOTE_WIDGET_RETURN = "widget/notewidget";
	public static final String CONTEXT_ANNOUNCEMENT_WIDGET_RETURN = "widget/announcementwidget";
	public static final String CONTEXT_CLASSTIMETABLE_WIDGET = "widget/classTimeTableWidget.jsp";
	public static final String CONTEXT_CLASSTIMETABLE_WIDGET_RETURN = "widget/classTimeTableWidget";
	public static final Object CONTEXT_TODAYS_SCHEDULE_WIDGET = "widget/todaysSchedule.jsp";
	public static final String CONTEXT_TODAYS_SCHEDULE_WIDGET_RETURN = "widget/todaysSchedule";

	/**
	 * constants for assignments
	 */
	public static final String CONTEXT_ASSIGNMENT = "assignment/assignmenthome.jsp";
	public static final String ATTRIB_ASSIGNMENT = "assignment";
	public static final String CONTEXT_CREATE_ASSIGNMENT = "assignment/createAssignment.jsp";
	public static final String CONTEXT_ASSIGNMENT_DETAIL = "assignment/assignmentDetail.jsp";
	public static final String CONTEXT_ASSIGNMENT_DOWNLOAD = "assignment/assignmentDownload.jsp";

	// Name for assignment form used in defining validation rule.
	public static final String FORM_ASSIGNMENT = "AssignmentForm";

	public static final String CONTEXT_CREATE_CLASS_SUBJECT = "myclass/createClassSubjectTeacher.jsp";

	public static final String CONTEXT_CLASS_SUBJECT_DETAIL = "myclass/classSubjectTeacherDetail.jsp";

	public static final String CONTEXT_CLASS_SUBJECT_INDEX = "myclass/classSubjectTeacherHome.jsp";

	public static final String CONTEXT_WORKFLOW_DETAIL = "workflow/workflowDetail.jsp";

	public static final String ATTRIB_BREADCRUMB_LIST = "breadcrumblist";

	public static final String ATTRIB_WORKFLOW = "workflow";

	public static final String CONTEXT_WORKFLOW_INDEX = "workflow/workflowHome.jsp";

	public static final String VIEW_AUTHORIZATION_ERROR = "/authorisation_error";

	public static final String AUTHORIZATION_ERROR_URL = "/authorisationerror.do";

	public static final String ATTRIB_FILE_ERROR = "FileError";

	public static final String MSG_FILE_ERROR = "Either No or Incorrect File Selected";

	public static final String ATTRIB_ADMIN = "admin";

	public static final String ATTRIB_USER = "user";

	public static final String USER_SITE_LIST = "UserSiteList";

	public static final String ALL_SITE_LIST = "AllSiteList";

	public static final String VAR_SCHOOL_SITE = "SchoolSiteName";

	public static final String CLASS_SITE_TEMPLATE_NAME = "classsitetemplate";

	public static final String CLASS_SITE_TEMPLATE_PROPERTY_NAME = "classsitetemplatename";

	public static final String ATTRIB_CLASS_SITE_NAME = "classSiteName";

	public static final String GOOGLE_SITES_PROPERTY_FILE = "googlesites.properties";

	public static final String DATA_CONTEXT = "dataContext";

	public static final String GLOBAL_ACADEMIC_YEAR_KEY = "acadYear";

	public static final String VIEW_RESOURCE_MISSING_ERROR = "/resourcenotfound_error";

	public static final String USER_ACADEMIC_YEAR_LIST = "userAcademicYearList";

	public static final String CONTEXT_ATTACHMENT_WIDGET = "widget/attachmentwidget.jsp";

	public static final String CONTEXT_EVENT_WIDGET = "widget/eventwidget.jsp";

	public static final String CONTEXT_EVENT_WIDGET_RETURN = "widget/eventwidget";

	public static final String CONTEXT_ATTACHMENT_WIDGET_RETURN = "widget/attachmentwidget";

	public static final String CONTEXT_DUE_ASSIGNMENT_WIDGET = "widget/dueAssignmentWidget.jsp";

	public static final String CONTEXT_DUE_ASSIGNMENT_WIDGET_RETURN = "widget/dueAssignmentWidget";

	public static final String ATTRIB_DATAUPLOAD_MSG = "DataUploadMsg";

	public static final String MSG_DATA_UPLOAD = "DATA import Workflow is scheduled sucessfully";

	public static final String PARAM_NOTE_RECIEVER_ID = "recieverId";

	public static final String PARAM_NOTE_RECIEVER_BUSINESSKEY = "recieverBusinessKey";

	public static final String ATTRIB_SMS_MSG = "SMSMsg";

	public static final String MSG_SMS_SUCCESS = "SMS Messages sent successfully";

	public static final String MSG_SMS_FAILURE = "SMS Messages could not be sent";

	public static final String SCHOOL_SITE_NAME = "schoolSiteName";
	/**
	 * Spring model attribute recycle bin ..
	 */

	public static final String CONTEXT_RECYCLEB_IN_INDEX = "recyclebin/recycleBinHome.jsp";

	/**
	 * Spring model attribute attendance ..
	 */
	public static final String CONTEXT_ATTENDANCE_INDEX = "attendance/attendanceHome.jsp";;

	public static final String CONTEXT_MYCLASS_ATTENDANCE = "attendance/myclassAttendance.jsp";

	public static final String MYCLASS_ATTENDANCE = "myClassAttendance";

	public static final String ATTENDANCE_DATE = "myClassAttendanceDate";

	public static final String ATTENDANCE_TIME = "myClassAttendanceTime";

	public static final String ATTENDANCE_TOTAL_STUDENTS = "totalStudents";

	/** evaluation component */
	public static final String CONTEXT_EVALUATION_SCHEME_INDEX = "evaluation/evaluationSchemeHome.jsp";

	public static final String CONTEXT_CREATE_EVALUATION_SCHEME = "evaluation/createEvaluationScheme.jsp";

	public static final String CONTEXT_EVALUATION_SCHEME_DETAIL = "evaluation/evaluationSchemeDetail.jsp";

	public static final String CONTEXT_EVALUATION_SCHEME_DETAILVIEW = "evaluation/evaluationSchemeDetailView.jsp";

	public static final String ATTRIB_EVALUATION_SCHEME = "evaluationScheme";

	public static final String ATTRIB_SCHEME_KEY = "schemeKey";

	public static final String FORM_EVALUATION_SCHEME = "EvaluationSchemeForm";

	public static final String CONTEXT_EVALUATION_SCHEME_COMPONENT_INDEX = "evaluation/evaluationComponentHome.jsp";

	public static final String CONTEXT_CREATE_EVALUATION_COMPONENT = "evaluation/createEvaluationComponent.jsp";
	public static final String CONTEXT_EVALUATION_STRUCTURE_INDEX = "evaluation/evaluationStructureHome.jsp";

	public static final String CONTEXT_EVALUATION_SUB_COMPONENT_EVENT_INDEX = "evaluation/subComponentEventHome.jsp";

	public static final String CONTEXT_EVALUATION_COMPONENT_EVENT_INDEX = "evaluation/componentEventHome.jsp";

	public static final String CONTEXT_APPLY_TO_SUBJECTS_FORM = "evaluation/applyToSubjectsForm.jsp";

	public static final String CONTEXT_REMOVE_FROM_SUBJECTS_FORM = "evaluation/removeFromSubjectsForm.jsp";

	public static final String CONTEXT_EVALUATION_CREATION_STATUS = "evaluation/success";

	public static final String CONTEXT_EVALUATION_COMPONENT_DETAIL = "evaluation/evaluationComponentDetail.jsp";

	public static final String FORM_COMPONENT_SCHEME = "EvaluationComponentForm";

	public static final String EVALUATION_SCHEME_NO_OF_TERMS = "noOfTerms";

	public static final String EVALUATION_SCHEME_NO_OF_STAGES = "noOfStages";

	public static final String ATTRIB_EVALUATION_COMPONENT = "evaluationComponent";

	public static final String CONTEXT_EVALUATION_SCHEME_SUBCOMPONENT_INDEX = "evaluation/evaluationSchemeSubComponentHome.jsp";

	public static final String CONTEXT_CLASS_SUBJECT_EVALUATION_COMPONENT_INDEX = "evaluation/classSubjectEvaluationComponentHome.jsp";

	public static final String CONTEXT_EVALUATION_SUB_COMPONENT_INDEX = "evaluation/evaluationSubComponentHome.jsp";

	public static final String CONTEXT_EVALUATION_SUB_COMPONENT_DETAIL = "evaluation/evaluationSubComponentDetail.jsp";

	public static final String CONTEXT_CREATE_EVALUATION_SUB_COMPONENT = "evaluation/createEvaluationSubComponent.jsp";

	public static final String CONTEXT_EVALUATION_PRINT_VIEW = "evaluation/printView";
	public static final String CONTEXT_CLASS_STUDENT_PRINT_VIEW = "relation/printView";

	public static final String ATTRIB_EVALUATION_SUB_COMPONENT = "evaluationSubComponent";

	public static final String FORM_SUB_COMPONENT_SCHEME = "EvaluationSubComponentForm";

	public static final String GRADE_SET_NAME = "Grade";

	public static final String COMPONENT_TERMS_STAGES = "termsStages";

	public static final String COMPONENT_SINGLE_TERM_SINGLE_STAGE = "singleTermSingleStage";

	public static final String COMPONENT_MULTIPLE_TERM_SINGLE_STAGE = "mulitpleTermSingleStage";

	public static final String COMPONENT_MULTIPLE_TERM_MULTIPLE_STAGE = "mulitpleTermMultipleStage";

	public static final String EVALUATION_SCHEME_COMPONENT_KEY = "key";
	/** option for grading */
	public static final String EVALUATION_SCHEME_GRADING = "GRADING";

	public static final String EVALUATION_SCHEME_NUMERIC = "NUMERIC";

	public static final String CONTEXT_STUDENT_CLASS_SUBJECT_EVALUATION_INDEX = "evaluation/studentClassSubjectEvaluationHome.jsp";

	public static final String CONTEXT_EVALUATION_INDEX = "evaluation/evaluationHome.jsp";

	// TODO : Need to be sure that value name is this only.
	public static final String EVAL_SCHEME_TYPE_GRADE = "Grading";

	public static final String CONTEXT_GRADING_SCALE_STEPS_HOME = "gradingScaleSteps/gradingScaleStepsHome.jsp";

	public static final String ATTRIB_GRADING_SCALE_STEPS = "gradingScaleSteps";

	public static final String CONTEXT_CREATE_GRADING_SCALE_STEPS = "gradingScaleSteps/createGradingScaleSteps.jsp";

	public static final String CONTEXT_GRADING_SCALE_STEPS_DETAIL = "gradingScaleSteps/gradingScaleStepsDetail.jsp";

	public static final Object CONTEXT_SUBJECT_EVALUATION_EVENT_DETAIL = "evaluation/subjectEvaluationEventDetail.jsp";

	public static final String ATTRIB_SUBJECT_EVALUATION_EVENT = "subjectEvaluationEvent";

	public static final Object CONTEXT_CREATE_SUBJECT_EVALUATION_EVENT = "evaluation/createSubjectEvaluationEvent.jsp";

	public static final String EVALUATION_COMPONENT_MULTI_OCCURRENCE = "MultiOccurrence";

	public static final String EVALUATION_COMPONENT_COMPOSITE = "composite";

	public static final String EVALUATION_COMPONENT_SIMPLE = "simple";

	public static final String CONTEXT_SUBJECT_EVALUATION_EVENT_HOME = "evaluation/subjectEvaluationEventHome.jsp";

	public static final String CONTEXT_CLASS_GROUP_HOME = "classGroup/classGroupHome.jsp";

	public static final String CONTEXT_CLASS_GROUP_DETAIL = "classGroup/classGroupDetail.jsp";

	public static final String FORM_GRADING_SCALE_STEPS = "GradingScaleStepsForm";

	public static final String ATTRIB_CLASS_GROUP = "classGroup";

	public static final String CONTEXT_CREATE_CLASS_GROUP = "classGroup/createClassGroup.jsp";

	public static final String FORM_CLASS_GROUP = "ClassGroupForm";

	public static final String CONTEXT_MANAGE_STUDENT = "evaluation/manageStudent.jsp";

	public static final String CONTEXT_ADD_STUDENT_TO_SUBJECT_EVALUATION_EVENT = "evaluation/addStudentToSubjectEvaluationEvent.jsp";

	public static final String FILE_IPATHSHALA_PROPERTIES = "ipathshala.properties";

	/**
	 * Ward Performance Constants.
	 */
	public static final String CONTEXT_WARD_PERFORMANCE_INDEX = "evaluation/wardPerformance.jsp";

	public static final String CONTEXT_REPORT_CARD_INDEX = "reportCard/reportCardHome.jsp";

	public static final String ATTRIB_ANNOUNCEMENT = "announcement";

	public static final Object CONTEXT_CREATE_ANNOUNCEMENT = "announcement/createAnnouncement.jsp";

	public static final String PARAM_SUBJECT_KEY = "subjectKeyString";

	public static final Object CONTEXT_ANNOUNCEMENT_DETAIL = "announcement/announcementDetail.jsp";

	public static final String FORM_ANNOUNCEMENT = "AnnouncementForm";

	public static final Object CONTEXT_ANNOUNCEMENT_INDEX = "announcement/announcementHome.jsp";

	public static final String PARAM_ANNOUNCEMENT_TYPE_KEY = "typeKey";

	public static final String PARAM_ANNOUNCEMENT_TYPE_ID = "typeid";

	public static final String ATTRIB_REPORTCARDEVENT = "reportCardEvent";

	public static final Object CONTEXT_CREATE_REPORTCARDEVENT = "reportcardevent/createReportcardevent.jsp";

	public static final Object CONTEXT_REPORTCARDEVENT_DETAIL = "reportcardevent/reportcardeventDetail.jsp";

	public static final String FORM_REPORTCARDEVENT = "ReportCardEventForm";

	public static final Object CONTEXT_REPORTCARDEVENT_INDEX = "reportcardevent/reportcardeventHome.jsp";

	public static final String PARAM_REPORTCARDTYPE_KEY = "reportCardTypeKeyString";

	public static final String PARAM_STAGE_KEY = "stageKeyString";

	public static final String PARAM_TERM_KEY = "termKeyString";

	public static final String CONTEXT_SUBJECT_STATUS_INDEX = "reportcardevent/subjectResultStatusHome.jsp";

	public static final String ATTRIB_STUDENTREPORTCARDEVENT = "studentReportCardEvent";

	public static final Object CONTEXT_CREATE_STUDENTREPORTCARDEVENT = "studentreportcardevent/createStudentReportCardEvent.jsp";

	public static final Object CONTEXT_STUDENTREPORTCARDEVENT_DETAIL = "studentreportcardevent/studentreportcardeventDetail.jsp";

	public static final String FORM_STUDENTREPORTCARDEVENT = "StudentReportCardEventForm";

	public static final Object CONTEXT_STUDENTREPORTCARDEVENT_INDEX = "studentreportcardevent/studentreportcardeventHome.jsp";

	public static final Object CONTEXT_STUDENTREPORTCARDPATH_INDEX = "studentreportcardevent/studentreportcardeventPathHome.jsp";

	public static final String PARAM_STUDENT_KEY = "studentKeyString";

	public static final String PARAM_REPORT_CARD_EVENT_KEY = "reportCardEventKeyString";

	public static final String PARAM_REPORT_CARD_EVENT_ID = "reportCardEventId";

	public static final String CONTEXT_STUDENTREPORTCARDREMARKS_INDEX = "studentreportcardevent/studentreportcardeventRemarks.jsp";

	public static final String PROPERTY_KEY_EVALUATION_EDIT_RIGHTS = "evaluation.edit.rights";

	public static final String PROPERTY_KEY_EVALUATION_EVENT_EDIT_WINDOW = "evaluation.event.edit.window";

	// ----------------------------------------------------------Contacts
	// Constants---------------------------------------------------------------------------------

	public static final String ATTRIB_CONTACTS = "contact";

	public static final String CONTEXT_CONTACTS_HOME = "contacts/contacthome.jsp";

	public static final String CONTEXT_CREATE_CONTACT = "contacts/createcontact";

	public static final String FORM_CONTACT = "ContactForm";

	public static final String CONTEXT_CONTACT_DETAIL = "contacts/contactdetail.jsp";

	public static final String SUCCESS_PAGE = "contacts/success";

	public static final String NOT_AUTHORISED_ERROR_HOME = "contacts/error";

	public static final String WELCOME_ADMIN_PAGE = "contacts/welcome";
}
