<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tld/security-taglib.tld" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page isELIgnored="false"%>



<html>
<head>
<link type="text/css" href="/css/themes/redmond/jquery.ui.all.css"
	rel="stylesheet" />


<script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/jqgrid/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="/js/prevent-duplicat-form.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.8.7.custom.min.js"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript"
	src="/js/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript"
	src="/js/ui/i18n/jquery.ui.datepicker-en-GB.js"></script>
<script type="text/javascript" src="/js/util/autocompleteutil.js"></script>
<script src="/js/jqgrid/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="/js/jqgrid/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="/js/ui/i18n/jquery-ui-i18n.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/validations.js"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="/css/jqgrid/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="/css/themes/base/jquery.ui.datepicker.css" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>NSC</title>
<link href="/css/style.css" rel="stylesheet" type="text/css">
<link href="/css/form.css" rel="stylesheet" type="text/css" />
<link href="/css/ie-css3.htc" rel="stylesheet" type="text/x-component">
<script src="/js/custom-form-elements.js" type="text/javascript"></script>
<script src="/js/jquery.corner.js" type="text/javascript"></script>
<script src="/js/modernizr-2.5.3.js" type="text/javascript"></script>
<script src="/SpryAssets/SpryMenuBar.js" type="text/javascript"></script>
<link href="/SpryAssets/SpryMenuBarVertical.css" rel="stylesheet"
	type="text/css">
</head>

<%-- <c:if test="${dataContext.currentSelectedAcademicYear==null}">
	<script type="text/javascript">
		var acadYear = "";
		var notSelectedOption = "<option value='Not Selected'>Not Selected</option>";
	</script>
</c:if>
<c:if test="${dataContext.currentSelectedAcademicYear!=null}">
	<script type="text/javascript">
		var acadYear = $
		{
			dataContext.currentSelectedAcademicYear.entityKey.id
		};
		var notSelectedOption = "";
	</script>
</c:if>

<c:if test="${dataContext.currentSelectedStudent!=null}">
	<script type="text/javascript">
		var stud = $
		{
			dataContext.currentSelectedStudent.key.id
		};
	</script>
</c:if> --%>
<%-- <c:if test="${dataContext.currentSelectedStudent==null}">
	<script type="text/javascript">
		var stud = undefined;
	</script>
</c:if>


<script type="text/javascript">
	function getHeaderTitle() {
		$.ajax({
			url : "/getHeaderTitle.do",
			success : function(title) {
				$("#logo .siteTitle").html(title);
			}
		});

	}

	function getSchoolsite() {
		$
				.ajax({
					url : "/getSchoolSite.do",
					success : function(title) {
						var opt = "<option value ='" + title +"'> School Site </option>";
						$("#selectedSite").append(opt);
					}
				});
	}

	function getUrlParameter(name) {
		name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
		var regexS = "[\\?&]" + name + "=([^&#]*)";
		var regex = new RegExp(regexS);
		var results = regex.exec(window.location.href);
		if (results == null)
			return "";
		else
			return results[1];
	}

	$(function() {

		getHeaderTitle();
		getSchoolsite();
		//$("#acadYear").html(notSelectedOption + $("#acadYear").html())

		var academicYearList = document.getElementById("acadYear");
		for ( var i = 0; i < academicYearList.length; i++) {

			if (academicYearList.options[i].value == acadYear)
				academicYearList.value = academicYearList.options[i].value;
		}
		$("#acadYear").change(function() {
			document.globalFilterForm.submit();
		});

		if (stud) {
			$("#stud").val(stud);
		}
		$("#stud").change(function() {
			document.globalFilterForm.action = "/setGlobalFilterStudent.do";
			document.globalFilterForm.submit();
		});

	});
	$(document)
			.ready(
					function() {

						$("#selectedSite").change(function() {
							document.getElementById("classSiteForm").submit();
						});

						$("#entityList").val(getUrlParameter("entity"));
						$("#entityButton")
								.click(
										function() {
											var selectListValue = $(
													"#entityList :selected")
													.val().toLowerCase();
											var redirectTo = "/"
													+ selectListValue
													+ "/advancedsearch.do?entity="
													+ $("#entityList :selected")
															.val();
											$("#entityForm").attr("action",
													redirectTo);
										});
						$("#More ul").empty();
						var pos = $("#More").position();
						$("#listOnMoreHover").css("left", pos.left);
						$("#listOnMoreHover").css("top", pos.top + 40);
						$("#listOnMoreHover").css({
							'visibility' : 'visible',
							'width' : '12%',
							'background-color' : '#B46D6D',
							'color' : 'black'
						});
						if (('${appUser.defaultAppGroup.groupName}') == "ipath_app_group_admin") {
							var currentSelect = "academicyear";
							var currentSelectText = "AcademicYear";
						}
						if (('${appUser.defaultAppGroup.groupName}') == "ipath_app_group_teacher") {
							var currentSelect = "myclass";
							var currentSelectText = "Class";
						}
						if (('${appUser.defaultAppGroup.groupName}') == "ipath_app_group_parent"
								|| ('${appUser.defaultAppGroup.groupName}') == "ipath_app_group_student") {
							$("#More").hide();
						}
						$("#add").empty();
						var curTop, curLeft, curBottom, curRight;
						var currSel = "${currentSelectTab}";
						var currSelText = "${currentSelectTabVal}";
						if (currSel != '') {
							currentSelect = currSel;
							tabAlreadySelected(currentSelect, currSelText);
						}

						$('.more')
								.hover(
										function() {
											if (('${appUser.defaultAppGroup.groupName}') == "ipath_app_group_admin") {
												$("#listOnMoreHover")
														.css('visibility',
																'visible');
												$("#add").empty();
												$("#add")
														.append(
																'<li id="parent" >Parent</li><li id="evaluationScheme">Evaluation Scheme</li><li id="sendsms" >Send Sms</li>'
																		+ '<li id="gradingscale" >Grading Scale</li><li id="subject" >Subject</li>'
																		+ '<li id="period" >Period</li><li id="location">Location</li><li id="studentMiscellaneous">Student Record</li><li id="school">School</li><li id="reportCard">Report Card Config</li><li id="showannouncement">Announcement</li>');
												if (currentSelect != "academicyear") {
													$("#" + currentSelect)
															.remove();
													$("#add")
															.append(
																	'<li id="academicyear">Academic Year</li>');
												}
											} else if (('${appUser.defaultAppGroup.groupName}') == "ipath_app_group_teacher") {
												$("#listOnMoreHover")
														.css('visibility',
																'visible');
												$("#add").empty();
												$("#add")
														.append(
																'<li id="student">Student</li>'
																		+ '<li id="parent" >Parent</li>'
																		+ '<li id="subject">Subject</li>'
																		+ '<li id="studentMiscellaneous">Student Record</li><li id="showannouncement">Announcement</li>');
												if (currentSelect != "myclass") {
													$("#" + currentSelect)
															.remove();
													$("#add")
															.append(
																	'<li id="myclass">Class</li>');
												}
											}

											var pos = $("#More").position();
											$("#listOnMoreHover").css("left",
													pos.left);
											$("#listOnMoreHover").css("top",
													pos.top + 40);
											$("#listOnMoreHover").css({
												'visibility' : 'visible',
												'width' : '12%',
												'background-color' : '#B46D6D',
												'color' : 'black'
											});

											$("#add li")
													.click(
															function() {
																$(
																		"#navigationList li:last")
																		.prev()
																		.hide();
																var id = $(this)
																		.attr(
																				"id");
																currentSelect = this.id;
																currentSelectText = $(
																		this)
																		.text();
																$(
																		"#navigationList li:last")
																		.prev()
																		.after(
																				'<li><a id="'+id+'Tab'+' "href="/'+id+'.do">'
																						+ $(
																								this)
																								.text()
																						+ '</a></li>');
																$
																		.ajax({
																			url : "/index.do?currentSelectTab="
																					+ currentSelect
																					+ "&currentSelectTabVal="
																					+ currentSelectText,
																			success : function(
																					data) {
																				window.location.href = '/'
																						+ currentSelect
																						+ '.do';
																			}

																		});
																$("#add")
																		.empty();
															});
											$("#add li")
													.mouseover(
															function() {
																$(this)
																		.css(
																				{
																					'background-color' : 'white',
																					'cursor' : 'pointer'
																				});
															})
													.mouseout(
															function() {
																$(this)
																		.css(
																				'background-color',
																				'#B46D6D');
															});

										},
										function(e) {
											curLeft = $(".more").position().left;
											curRight = curLeft + 40;
											curTop = $(".more").position().top;
											curBottom = curTop + 50;
											if (e.pageX > curLeft
													&& e.pageY > curTop
													&& e.pageX < curRight
													&& e.pageY < curBottom) {
												$("#listOnMoreHover")
														.css('visibility',
																'visible');
											} else {
												$("#listOnMoreHover").css(
														'visibility', 'hidden');
											}
										});
						$('#listOnMoreHover').hover(function() {
							$("#listOnMoreHover").css('visibility', 'visible');
						}, function() {
							//$("#add").empty();
							$("#listOnMoreHover").css('visibility', 'hidden');
						});

					});
	function tabAlreadySelected(currentSelection, currSelText) {
		$("#navigationList li:last").prev().hide();
		$("#navigationList li:last").prev().after(
				'<li><a id="'+currentSelection+'Tab'+'" href="/'+currentSelection+'.do">'
						+ currSelText + '</a></li>');
		$("#add").empty();

	}
</script> --%>
<style type="text/css">
.more {
	position: relative;
	z-index: 200;
}

#listOnMoreHover {
	position: absolute;
	visibility: hidden;
	z-index: 200;
}

ul#add {
	padding: 0px;
	margin: 0px;
	height: auto;
	margin-left: 0px;
}

ul#add li {
	list-style-type: none;
	padding: 0px;
	margin: 0px;
	line-height: 1.5;
	padding-left: 5px;
}
</style>
<body>
	<div id="main-contaner">
		<div class="header">
			<div class="logo"></div>

			<div class="top_most_nav">admin@mellong.com | Manage | Logout</div>


		</div>
		<%-- <div id="header-contaner">
			<div id="header">
				<div id="logo">
					<h1 class="siteTitle"></h1>
					<div class="school-year">
						<form name="globalFilterForm" method="post"
							action="/setDataContext.do">
							<input name="contextView" type="hidden" id="contextView"
								value="${_ipContextView}"> <select name="acadYear"
								id="acadYear" class="styled"
								style="border: 1px solid #dcdcdc; width: 85px;">
								<c:forEach var="academicYear" items="${userAcademicYearList}">
									<option value=<c:out value='${academicYear.key.id}'/>>
										<c:out value='${academicYear.name}' />
										<c:if test="${academicYear.isDefaultAcademicYear}">*</c:if>
									</option>
								</c:forEach>
							</select>
							<c:if
								test="${appUser.defaultAppGroup.groupName == 'ipath_app_group_parent'}"></c:if>
							<select
								<c:if test="${fn:length(globalStudentList)<=1}">disabled = "disabled"</c:if>
								name="stud" id="stud" class="styled"
								style="border: 1px solid #dcdcdc; width: 85px;">
								<c:if test="${fn:length(globalStudentList)>1}">
									<option value="All">All</option>
								</c:if>
								<c:forEach var="student" items="${globalStudentList}">
									<option value=<c:out value='${student.key.id}'/>>
										<c:out value='${student.firstName}' />
									</option>
								</c:forEach>
							</select>
						</form>
					</div>
				</div>
				<div id="header-right-block">
					<div class="hearer-top">


						<div class="fr">
							<div class="welcome-msg">Welcome ${appUser.firstName}</div>

							<div class="mysite-dropdown">

								<form id="classSiteForm" method="POST"
									action="/gotoclasssite.do">
									<select name="selectedSite" id="selectedSite" class="styled"
										style="width: 100px">
										<option>My Site</option>
										<c:if
											test="${UserSiteList != null && appUser.defaultAppGroup.groupName != 'ipath_app_group_admin' && dataContext.currentSelectedAcademicYear.entityKey == dataContext.defaultAcademicYear.entityKey}">
											<c:forEach var="siteList" items="${UserSiteList}">
												<option value=<c:out value='${siteList.siteName}'/>>
													<c:out value='${siteList.siteDisplayValue}' />
												</option>
											</c:forEach>
										</c:if>
									</select>
								</form>

							</div>
							<div class="my-setting">
								&nbsp;|&nbsp; <a href="#">My Settings</a>&nbsp;| &nbsp; <a
									href="/logout.do">Logout</a>
							</div>

						</div>


						<div class="clear"></div>
					</div>
					<div
						<c:if test="${appUser.defaultAppGroup.groupName=='ipath_app_group_parent' || appUser.defaultAppGroup.groupName=='ipath_app_group_student' }">style="display:none;"</c:if>
						class="header-search-block">
						<form id="entityForm" method="post">
							<div class="class-dd">
								<select id="entityList" class="styled2"
									style="border: 1px solid #dcdcdc; width: 160px">
									<c:if
										test="${sec:hasReadPermission(appUser,'Assignment',dataContext)}">
										<option value="Assignment">Assignment(Title)</option>
									</c:if>
									<c:if
										test="${sec:hasReadPermission(appUser,'AcademicYear',dataContext)}">
										<option value="AcademicYear">Academic Year(Name)</option>
									</c:if>
									<c:if
										test="${sec:hasReadPermission(appUser,'MyClass',dataContext)}">
										<option value="MyClass">Class(Name)</option>
									</c:if>
									<c:if
										test="${sec:hasReadPermission(appUser,'Location',dataContext)}">
										<option value="Location">Location(Name)</option>
									</c:if>
									<c:if
										test="${sec:hasReadPermission(appUser,'Parent',dataContext)}">
										<option value="Parent">Parent(Primary Contact)</option>
									</c:if>
									<c:if
										test="${sec:hasReadPermission(appUser,'Period',dataContext)}">
										<option value="Period">Period(Name)</option>
									</c:if>

									<c:if
										test="${sec:hasReadPermission(appUser,'Student',dataContext)}">
										<c:if
											test="${appUser.defaultAppGroup.groupName != 'ipath_app_group_parent' && appUser.defaultAppGroup.groupName != 'ipath_app_group_student'}">
											<option value="Student">Student(Name)</option>
										</c:if>
									</c:if>

									<c:if
										test="${sec:hasReadPermission(appUser,'Subject',dataContext)}">
										<option value="Subject">Subject(Name)</option>
									</c:if>
									<c:if
										test="${sec:hasReadPermission(appUser,'Teacher',dataContext)}">
										<option value="Teacher">Teacher(Name)</option>
									</c:if>
									<c:if
										test="${sec:hasReadPermission(appUser,'Set',dataContext)}">
										<option value="Set">Set(Name)</option>
									</c:if>
									<c:if
										test="${sec:hasReadPermission(appUser,'Value',dataContext)}">
										<option value="Value">Value(Name)</option>
									</c:if>
									<c:if
										test="${sec:hasReadPermission(appUser,'Workflow',dataContext)}">
										<option value="Workflow">Workflow(Name)</option>
									</c:if>
									<c:if
										test="${sec:hasReadPermission(appUser,'Event',dataContext)}">
										<option value="Event">Event(Title)</option>
									</c:if>
									<c:if
										test="${sec:hasReadPermission(appUser,'Note',dataContext)}">
										<option value="Note">Note(Subject)</option>
									</c:if>
									<c:if
										test="${sec:hasReadPermission(appUser,'Term',dataContext)}">
										<option value="Term">Term(Name)</option>
									</c:if>
									<c:if
										test="${sec:hasReadPermission(appUser,'EvaluationStage',dataContext)}">
										<option value="EvaluationStage">EvaluationStage(Name)</option>
									</c:if>
								</select>
							</div>
							<div class="find-div">
								<input type="text" name="advSearchField" class="find-txtbox"
									value='<c:out value="${advSearchText}"/>'> <input
									type="submit" name="entityButton" value="Find"
									id="entityButton" class="find-btn">
							</div>
						</form>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
			</div>
		</div> --%>
		<%-- <div id="menu-contaner">
			<div id="navigation">
				<ul id="navigationList">
					<li><a href="/index.do" class="selectmenu">Home</a></li>

					<li><a id="scheduleTab" href="/schedule.do">Schedule</a>
					</li>

					<c:if
						test="${sec:hasReadPermission(appUser,'Evaluation',dataContext)}">
						<li><a id="evaluationTab" href="/evaluation.do">Evaluation</a>
						</li>
					</c:if>
					<c:if
						test="${sec:hasReadPermission(appUser,'Assignment',dataContext)}">
						<li><a id="assignmentTab" href="/assignment.do">Assignment</a>
						</li>
					</c:if>

					<c:if
						test="${appUser.defaultAppGroup.groupName=='ipath_app_group_student'}">
						<li><a id="showannouncement" href="/showannouncement.do">Announcement</a>
						</li>
					</c:if>

					<c:if
						test="${appUser.defaultAppGroup.groupName=='ipath_app_group_admin'}">
						<li><a id="contacts" href="/contacts.do">Contacts</a>
						</li>
					</c:if>

					<c:if
						test="${appUser.defaultAppGroup.groupName=='ipath_app_group_parent'}">
						<li><a id="showannouncement" href="/showannouncement.do">Announcement</a>
						</li>
					</c:if>


					<c:if test="${sec:hasReadPermission(appUser,'Event',dataContext)}">
					<li><a id="eventTab" href="/event.do">Event</a>
					</li>
					</c:if>

					<c:if test="${sec:hasReadPermission(appUser,'Note',dataContext)}">
						<li><a id="noteTab" href="/note.do">Note</a>
						</li>
					</c:if>



					<c:if test="${sec:hasReadPermission(appUser,'EvaluationScheme',dataContext)}">
					<li><a id="evaluationschemeTab" href="/evaluationScheme.do">Evaluation Scheme</a></li>
					</c:if>

					<c:if
						test="${sec:hasReadPermission(appUser,'EvaluationStructure',dataContext)}">
						<li><a id="subjectEvaluationEventHomeTab"
							href="/subjectEvaluationEventHome.do?index=true">Evaluation
								Event</a>
						</li>
					</c:if>
					<c:if
						test="${sec:hasReadPermission(appUser,'MyClass',dataContext)}">
						<li><a id="myclassTab" href="/myclass.do">Class</a>
						</li>
					</c:if>


					<c:if
						test="${appUser.defaultAppGroup.groupName=='ipath_app_group_admin'}">
						<c:if
							test="${sec:hasReadPermission(appUser,'Teacher',dataContext)}">
							<li><a id="teacherTab" href="/teacher.do">Teacher</a>
							</li>
						</c:if>
					</c:if>

					<c:if
						test="${appUser.defaultAppGroup.groupName=='ipath_app_group_admin'}">
						<c:if
							test="${sec:hasReadPermission(appUser,'Student',dataContext)}">
							<li><a id="studentTab" href="/student.do">Student</a>
							</li>
						</c:if>
					</c:if>

					<c:if
						test="${appUser.defaultAppGroup.groupName=='ipath_app_group_student'}">
						<li><a id="myDetailTab" href="/student/showDetail.do">My
								Detail</a>
						</li>
					</c:if>

					<c:if
						test="${appUser.defaultAppGroup.groupName=='ipath_app_group_parent'}">
						<li><a id="myDetailTab2" href="/parent/showDetail.do">My
								Detail</a>
						</li>
					</c:if>

					<c:if
						test="${appUser.defaultAppGroup.groupName=='ipath_app_group_parent'}">
						<c:choose>
							<c:when test="${dataContext.currentSelectedStudent!=null}">
								<li><a id="wardDetailTab" href="/student/showDetail.do">Ward
										Detail</a>
								</li>
							</c:when>
							<c:otherwise>
								<li><a id="wardDetailTab2" href="/student.do">Wards</a>
								</li>
							</c:otherwise>
						</c:choose>
					</c:if>

					<c:if
						test="${appUser.defaultAppGroup.groupName=='ipath_app_group_parent'}">
						<li><a id="wardPerformanceTab" href="/wardPerformance.do">Ward
								Performance</a>
						</li>
					</c:if>

					<c:if test="${sec:hasDeletePermission(appUser,'StudentMiscellaneous',dataContext)}">
						<li><a id="studentMiscTab" href="/studentmiscellaneous.do">Student Miscellaneous</a></li>	
					</c:if>


					<c:if test="${appUser.defaultAppGroup.groupName=='ipath_app_group_admin'}">
					<c:if test="${sec:hasReadPermission(appUser,'EvaluationScheme',dataContext)}">
					<li><a id="evaluationSchemeTab" href="/evaluationScheme.do">Evaluation Scheme</a></li>
					</c:if></c:if>

					<c:if test="${appUser.defaultAppGroup.groupName=='ipath_app_group_admin'}">
					<c:if test="${sec:hasReadPermission(appUser,'Parent',dataContext)}">
					<li><a id="parentTab" href="/parent.do">Parent</a></li>
					</c:if></c:if>

					<c:if
						test="${appUser.defaultAppGroup.groupName=='ipath_app_group_admin'}">
						<c:if
							test="${sec:hasReadPermission(appUser,'AcademicYear',dataContext)}">
							<li><a id="academicyearTab" href="/academicyear.do">Academic
									Year</a>
							</li>
						</c:if>
					</c:if>

					<c:if test="${sec:hasReadPermission(appUser,'Set',dataContext)}">
						<li><a id="setTab" href="/set.do">Set</a>
						</li>
					</c:if>
					<c:if test="${sec:hasReadPermission(appUser,'Value',dataContext)}">
						<li><a id="valueTab" href="/value.do">Value</a>
						</li>
					</c:if>
					<c:if
						test="${sec:hasReadPermission(appUser,'Workflow',dataContext)}">
						<li><a id="workflowTab" href="/workflow.do">Workflow</a>
						</li>
					</c:if>
						<c:if test="${sec:hasReadPermission(appUser,'Subject',dataContext)}">
					<li><a id="subjectTab" href="/subject.do">Subjects</a></li>
					</c:if>
					<c:if test="${appUser.defaultAppGroup.groupName=='ipath_app_group_teacher'}">
					<c:if test="${sec:hasReadPermission(appUser,'Note',dataContext)}">
					<li><a id="noteTab" href="/note.do">Note</a></li>
					</c:if>	</c:if>
					<c:if test="${appUser.defaultAppGroup.groupName=='ipath_app_group_teacher'}">
					<c:if test="${sec:hasReadPermission(appUser,'Assignment',dataContext)}">
					<li><a id="assignmentTab" href="/assignment.do">Assignment</a></li>
					</c:if>	</c:if>
					<c:if test="${sec:hasReadPermission(appUser,'Period',dataContext)}">
					<li id="period"><a id="periodTab" href="/period.do">Periods</a></li>
					</c:if>

					<li
						<c:if test="${appUser.defaultAppGroup.groupName=='ipath_app_group_parent' || appUser.defaultAppGroup.groupName=='ipath_app_group_student' }">
					style="display:none;"
					</c:if>
						id="More" class="more"><a class="more" href="#"><span>More</span>
					</a></li>
					<div class="clear"></div>
				</ul>
			</div>
			</form>
		</div>
		<div id="listOnMoreHover">
			<ul id="add"></ul>
		</div> --%>
		<div id="middle-contaner" style="width: 100%">
			<div id="middle-contaner-block"
				style="width: 100%; border-radius: 0 0 0 0">
				<jsp:include page="${_ipContextView}"></jsp:include>

				<div class="clear"></div>
			</div>
			<div id="footer-contaner">
			
			</div>
			<div class="clear"></div>
		</div>
	</div>

	<script type="text/javascript">
		jQuery('#hdr-popup').bind('click', function(e) {
			e.stopPropagation();
		});
		jQuery(document).bind('click', function() {
			jQuery('#hdr-popup').hide();
			jQuery('#mysite-dropdown').removeClass('edu-username-red-arrow');
			jQuery('#mysite-dropdown').addClass('edu-username-white-arrow');
		});
		var MenuBar1 = new Spry.Widget.MenuBar("MenuBar1", {
			imgRight : "SpryAssets/SpryMenuBarRightHover.gif"
		});
	</script>
</body>
</html>

