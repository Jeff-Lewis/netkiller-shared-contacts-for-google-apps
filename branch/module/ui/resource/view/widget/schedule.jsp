
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/tld/breadcrumb-taglib.tld" prefix="brc"%>
<%@ taglib uri="/tld/security-taglib.tld" prefix="sec"%>
<%@ taglib uri="/tld/listofvalues-taglib.tld" prefix="lov"%>

<style type="text/css">
a:link {text-decoration:none;}    /* unvisited link */
a:visited {text-decoration:none;} /* visited link */
a:hover {text-decoration:none;}   /* mouse over link */
a:active {text-decoration:none;}  /* selected link */


.anchorClass{
color:#008cd4; 
text-decoration: none;" 
}

#table td{
white-space:pre-wrap;
}

</style>
<input type="hidden" id="isTeacher" value="false"/>
<input type="hidden" id="isParentOrStudent" value="false"/>

<c:if test="${appUser.defaultAppGroup.groupName=='ipath_app_group_teacher' && scheduleVisibility=='SELF'}">
<script type="text/javascript">
$(function(){
   $('#isTeacher').val("true");
});

</script>
</c:if>

<c:if test="${(appUser.defaultAppGroup.groupName=='ipath_app_group_parent' || appUser.defaultAppGroup.groupName=='ipath_app_group_student') && scheduleVisibility=='SELF'}">
<script type="text/javascript">
$(function(){
   $('#isParentOrStudent').val("true");
});

</script>
</c:if>

<script type="text/javascript" src="/js/google/jsapi.js"></script>

<script type="text/javascript">
      google.load('visualization', '1', {packages: ['table']});
      
    </script>
    
<script type="text/javascript">


var allTeacherList;
var allSubjectList ;

$(document).ready(function(){
	$("#menu-contaner a").removeClass("selectmenu");
	$("#scheduleTab").addClass("selectmenu");
	
	/* $("#classLevel").html($("#classLevel option").sort(function (a, b) {
	    return parseInt(a.text) == parseInt(b.text) ? 0 : parseInt(a.text) < parseInt(b.text) ? -1 : 1
	})); */
	
	$("#classLevel option[value='none']").remove();
	$('#classLevel option:last-child').attr("selected", "selected");
	
	if(${appUser.defaultAppGroup.groupName=='ipath_app_group_teacher'} ){
		
		setInitialDayViewPropForTeacher();
		setInitialWeekViewPropForTeacher();
		
	}else if(${appUser.defaultAppGroup.groupName=='ipath_app_group_student'}){
		setWeekDataProp();
	}else if(${appUser.defaultAppGroup.groupName=='ipath_app_group_parent'}){
		setWeekDataProp();
	}else{
		//drawTodaysViewTimeTable($("#classLevel").val());
		 setDailyDataProp();
		 setInitialDayViewPropForAdmin();
	}
	
	
//	$("#classLevel option[value='none']").html("--Select--");
	$("#classLevel").addClass("schedule-select");
	$("#classLevel").css( "margin-left","10px" );
	initializeDefault();
	$("#classLevel").change(function(){
		setClassLevel($("#classLevel").val());
	});
	
});


function setInitialDayViewPropForTeacher(){
	
	showTeacherDropDownProp();
	//$("#classLevel").attr('disabled',true)	;
	//$("#subjectList").attr('disabled',true)	;
	 $('input[name="by-class-day"][value="teacherListRadio"]').attr('checked',true);
	// $("#teacherListDayView").attr('disabled',false);	 
}

function setInitialWeekViewPropForTeacher(){
	$('#weekViewFilter').show();$('#dayViewFilter').hide();
	$("#weekDataLink").attr('style','font-size:150%');
	$("#dailyDataLink").attr('style','font-size:80%');
	$('#teacherList1').removeAttr('disabled');
	getAllTeachers1();
	$('#classBusinessKey').attr('disabled','disabled');
	 $('input[name="by-class"][value="teacherListWeekRadio"]').attr('checked',true);
	 getTeacherWeeklyData('${teacherKey}');
	 
}

function setInitialDayViewPropForAdmin(){
	 $("#teacherListDayView").attr("disabled",true);
	 $("#subjectList").attr("disabled",true);
}


var selectedDay = "";

function initializeDefault(){
	 var d = new Date();
		
	var weekday=new Array(7);
	weekday[0]="Sunday";
	weekday[1]="Monday";
	weekday[2]="Tuesday";
	weekday[3]="Wednesday";
	weekday[4]="Thursday";
	weekday[5]="Friday";
	weekday[6]="Saturday";
	
	selectedDay = weekday[d.getDay()];
	
	$('#dayList').find('li').each(function(index) {
		var elem = $(this).find('a');
		var day =  elem.text();
		if(day==selectedDay){
			elem.text(day+"(Today)");
			elem.addClass('selecttab');
		}else{
			elem.removeClass('selecttab');
		}
	});
} 

function populateClassList(){
	document.getElementById("classBusinessKey").options.length = 0;
	var selectListData = '<option selected="selected" > --Select-- </option>';
	selectListData += '<option value="' + '<c:out value="${defaultClass.key.id}"></c:out>' + '">' + '<c:out value="${defaultClass.name}"></c:out>' + '</option>';
	<c:forEach var="class" items="${allClasses}">
	selectListData += '<option value="' + '<c:out value="${class.key.id}"></c:out>' + '">' + '<c:out value="${class.name}"></c:out>' + '</option>';
	</c:forEach>
	$('#classBusinessKey').append(selectListData);
	
}
 function getAllTeachers1() {

	 if($('#isTeacher').val()=="true"){
		 getTeacherWeeklyData(${teacherKey});
	 }	 
	 
	if(allTeacherList==undefined || allTeacherList==null || allTeacherList==''){
	 	$.ajax({
	 		type : "POST",
	 		url : '/gadget/getAllGlobalFilteredTeachers.do' ,
	 		data : "",
	 		success : function(teachers) {
	 			allTeacherList = eval(teachers);
	 			createTeacherDropDownFromList(allTeacherList,"teacherList1");
	 			
	 		},
	 		error: function(x,e) {
	 		}
	 	});
	}else{
		createTeacherDropDownFromList(allTeacherList,"teacherList1");
	}
	
 }   
    
    function getTeacherWeeklyData(teacherKey){
	    google.load('visualization', '1', {packages:['table']});
	    google.setOnLoadCallback(subjectPeriodData(teacherKey));
    }
    
    function subjectPeriodData(teacherKey){
         var data = new google.visualization.DataTable();
         var colN = addColumnsWeekView(data);
         for ( name in colN ) {    
	         if (colN.hasOwnProperty(name)) { 
	         	data.addColumn('string',colN[name]);
	         }
         }
         var dataUrl = "/gadget/weeklyTeachersScheduleClassSubjectView.do?teacherKey=" + teacherKey;
         makeTable(data,dataUrl);
	    }
    
    function makeTable(data,dataUrl)  {
       $.ajax(
       {
          type : "POST",
          url : dataUrl,
          dataType : "json",
          data : "",
          success : function(tableData)
          {
             var html = "";
             var p = tableData;
             for (var key in p)
             {
                if (p.hasOwnProperty("rows") && key == "rows" )
                {
                   var row = 0;
                   var length = p[key].length;
                   data.addRows(length);
                   jQuery.each(p[key],function(){

                      var innerList = this;
                      for (var innerKey in innerList)
                      {
                         var col = 0;
                         if (innerList.hasOwnProperty("cell") && innerKey == "cell" )
                         {
                            jQuery.each(innerList[innerKey],
                            function()
                            {
                               data.setCell(row, col, this.toString());
                               col ++ ;
                            }
                            );
                            row ++
                         }
                      }
                   }
                   );
                }
             }
             var options = {allowHtml: true, sort: 'disable', cssClassNames:{headerRow: 'tableHeader'}};
             visualization = new google.visualization.Table(document.getElementById('table'));
             visualization.draw(data, options);
             $(".google-visualization-table-table").attr('style', 'width:100%');
          }
          ,
       });
    }
  	    
    </script>

<c:if test="${showAllClassTimeTable=='true'}">
	<script type="text/javascript">
		$("document").ready( function (){
			$("#showAllClassTimeTable").val(true);
		});
</script>
</c:if>
<c:choose>    
<c:when test="${appUser.defaultAppGroup.groupName=='ipath_app_group_teacher' && showAllClassTimeTable!='true'}">
	<script>
		$("document").ready( function (){			
			setHiddenFieldValue("entityId","");
			//drawtimetable();
		
		});
	</script>
</c:when>
<c:otherwise>
<script>
		$("document").ready( function (){
			var defaultClassKey = ${defaultClass.key.id} ;
			if(defaultClassKey!=null && defaultClassKey!='' ){
				setHiddenFieldValue("entityId","${defaultClass.key.id}");
				//drawtimetable();
			}
		});
	</script>

</c:otherwise>
</c:choose>

<script type="text/javascript">
/* $(function() {

	$("#classBusinessKey").autocomplete({
	    source :"/getSearchedEntities.do?Entity=myClass",
		minLength : 1,
		search : function (event,ui) {
	   	    setHiddenFieldValue("entityId","");
     	},
	    select : function (event,ui) {
		var selectedItem=ui.item;
 	    setHiddenFieldValue("entityId",parseString(selectedItem.desc));
 	    drawtimetable();
	}
	}).data( "autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li></li>" )
		.data( "item.autocomplete", item )
		.append( "<a>" + item.label + "<br><font size='1'>" + item.desc + "</font></a>" )
		.appendTo( ul ); 
		};
		
		
		 
});
*/

function setClassValue(selectedValue)
{
	if (selectedValue == '0')
	{
		document.getElementById('table').innerHTML='';
	}
	else
	{
	setHiddenFieldValue("entityId",selectedValue);
	drawtimetable();
	}
}
function addColumnsWeekView(data){   
	var colN;
	$.ajax(
		    {
		       type: "POST",
		       url: "/gadget/getTimeTableColumns.do",
		       data: "",
		       dataType: "json",
		       async: false,
		       success: function(result)
		       {	
		    	  colN = result.colNames;			       
		       },
		       error: function(x, e)
		       {
		          //  alert(x.readyState + " "+ x.status +" "+ e.msg);   
		       }
		    });	
	return colN;
}

function drawtimetable() {
	  // Create and populate the data table.
	  var options = {allowHtml: true, sort: 'disable', cssClassNames:{headerRow: 'tableHeader'}};
	  var data = new google.visualization.DataTable();
	  var colN = addColumnsWeekView(data);
      for ( name in colN ) {		    		
   	   if (colN.hasOwnProperty(name)) { 
   		   data.addColumn('string',colN[name]);
   		   }
      }
      var dataUrl = '/gadget/timeTableview.do?&entryEntityId='+ $("#entityId").val()+'&showAllClassTimeTable='+$("#showAllClassTimeTable").val();
      makeTable(data,dataUrl)
	}  

function getCurrentWindowWidth()
{
   var winW = 630, winH = 460;
   if (document.body && document.body.offsetWidth)
   {
      winW = document.body.offsetWidth;
      winH = document.body.offsetHeight;
   }
   if (document.compatMode == 'CSS1Compat' &&
   document.documentElement &&
   document.documentElement.offsetWidth )
   {
      winW = document.documentElement.offsetWidth;
      winH = document.documentElement.offsetHeight;
   }
   if (window.innerWidth && window.innerHeight)
   {
      winW = window.innerWidth;
      winH = window.innerHeight;
   }
   return winW;
}

function setWeekDataProp(){
	$('#weekViewFilter').show();$('#dayViewFilter').hide();
	$("#weekDataLink").attr('style','font-size:150%');
	$("#dailyDataLink").attr('style','font-size:80%');
	
    if($('#isParentOrStudent').val()=="true"){
    	setClassValue(${defaultClass.key.id});
    }else{
	if(!$("#classBusinessKey").attr('disabled')){
		setClassValue($('#classBusinessKey').val());
	}else if(!$("#teacherList1").attr('disabled')){
		getTeacherWeeklyData($("#teacherList1").val());
	}
    }
}

function setDailyDataProp(){
	$('#dayViewFilter').show();$('#weekViewFilter').hide();	
	$("#dailyDataLink").attr('style','font-size:150%');
	$("#weekDataLink").attr('style','font-size:80%');

	if($('#isTeacher').val()=="true"){
		getTeacherTodaysView(${teacherKey});
	}else{
	if(!$("#classLevel").attr('disabled')){
		drawTodaysViewTimeTable($("#classLevel").val());
	}else if(!$("#subjectList").attr('disabled')){
		getSubjectTodaysView($("#subjectList").val());
	}else if(!$("#teacherListDayView").attr('disabled')){
		getTeacherTodaysView($("#teacherListDayView").val());
	}
}
}

</script>














<script type="text/javascript">
$(document).ready(function(){
	
	
 });


</script>

<script type="text/javascript">

function getAllClasses(){
var classList ='<option selected="selected">--Select--</option>' + 
'<option value="Primary1">Primary1</option> ' +
'<option value="Primary2">Primary2</option>' +
'<option value="Secondary1">Secondary1</option>' +
'<option value="Secondary2">Secondary2</option>' +
'<option value="Senior Secondary">Senior Secondary</option>' 
$('#classLevel').append(classList);
}
function setClassLevel(classLevel) {

 if (classLevel == '0'){
 	//$("#pageBody").height(0);
	document.getElementById('table').innerHTML='';
	//parent.resizeCaller();
}else {
//$("#pageBody").height(0);
drawTodaysViewTimeTable(classLevel);
	}
}

function addColumns(data){   
var colN;
	$.ajax(
	    {
	       type: "POST",
	       url: "/gadget/getTimeTableColumnsForTodaysView.do",
	       data: "",
	       dataType: "json",
	       async: false,
	       success: function(result)
	       {	
	    	  colN = result.colNames;			       
	       },
	       error: function(x, e)
	       {
	          //  alert(x.readyState + " "+ x.status +" "+ e.msg);   
	       }
	    });	
return colN;
} 

function drawTodaysViewTimeTable(classLevel) {
	 var options = {allowHtml: true,sortAscending:true,sortColumn:0, cssClassNames:{headerRow: 'tableHeader'}};
var data = new google.visualization.DataTable();
var colN = addColumns(data);
for ( name in colN ) {		    		
  if (colN.hasOwnProperty(name)) { 
	   data.addColumn('string',colN[name]);
	   }

}
	$.ajax ({
		type : "POST",
		url : '/gadget/todaysScheduleView.do?classLevel='+classLevel+"&weekDay="+ selectedDay,
		data : "",
		success : function(tableData) {
			var html = "";
				var p = eval(tableData);
				var length= p.length;
				data.addRows(length);
				var row = 0 ;
				for (var key in p) {
					var col = 0 ;
					var map = eval(p[key]);
					var className = map.className ;	  					
					var cell = eval(map.cell) ;
					
					data.setCell(row, col,className);
					col ++ ;
					jQuery.each(cell,function(){		  						
						data.setCell(row, col,this.toString());
						col ++ ;
					});
					row ++ ;
				}  					
			visualization = new google.visualization.Table(document.getElementById('table'));
		 	visualization.draw(data, options);
		 	$(".google-visualization-table-table").attr('style','width:100%');
		 	//parent.resizeCaller();
		},
		error: function(x,e) {
		}
	});
} 

function getAllTeachers() {
 
 $("#teacherListDayView").removeAttr('disabled');
 $("#classLevel").attr("disabled","disabled");
 $("#subjectList").attr("disabled","disabled");
 
 if(allTeacherList==undefined || allTeacherList==null || allTeacherList==''){
	$.ajax({
		type : "POST",
		url : '/gadget/getAllGlobalFilteredTeachers.do' ,
		data : "",
		success : function(teachers) {
			
			allTeacherList = eval(teachers);
			 createTeacherDropDownFromList(allTeacherList,"teacherListDayView");
			
		},
		error: function(x,e) {
		}
	});
 }else{
	 createTeacherDropDownFromList(allTeacherList, "teacherListDayView");
 }
 

}   

function getTeacherTodaysView(teacherId){

var options = {allowHtml: true, sort: 'disable', cssClassNames:{headerRow: 'tableHeader'}};
  var data = new google.visualization.DataTable();
  var colN = addColumns(data);
  for ( name in colN ) {		    		
	   if (colN.hasOwnProperty(name)) { 
		   data.addColumn('string',colN[name]);
		   }

  }
  
	$.ajax({
		type : "POST",
		url : '/gadget/todaysScheduleTeacherView.do?teacherId='+teacherId +"&weekDay="+selectedDay,
		data : "",
		success : function(tableData) {
			var html = "";
				var p = eval(tableData);
				var length= p.length;
				data.addRows(length);
				var row = 0 ;
				for (var key in p) {
					var col = 0 ;
					var map = eval(p[key]);
					var className = map.className ;	 
					data.setCell(row, col,className);
					col ++ ;
					
					var cell = eval(map.cell) ;
					for(var i in cell) {
						var colNumber  = cell[i].periodColumnNumer;
						var cellValue = cell[i].rowValue;
						data.setCell(row, colNumber,cellValue);
					}
					row ++ ;
				}  					
			visualization = new google.visualization.Table(document.getElementById('table'));
		 	visualization.draw(data, options);
		 	$(".google-visualization-table-table").attr('style','width:100%');
		 	//parent.resizeCaller();
		},
		error: function(x,e) {
		}
	});
}

function getAllSubjects() {

 $("#subjectList").removeAttr('disabled');
	$("#classLevel").attr("disabled","disabled");
$("#teacherList").attr("disabled","disabled");
 
 if(allSubjectList== undefined || allSubjectList==null || allSubjectList=='') {
 
	$.ajax({
		type : "POST",
		url : '/gadget/getAllGlobalFilteredSubjects.do' ,
		data : "",
		success : function(subjects) {
			allSubjectList =  eval(subjects);
			createSubjectDropDownFromList(allSubjectList);
		},
		error: function(x,e) {
		}
	});
 }else{
	 createSubjectDropDownFromList(allSubjectList);
 }
 
 
}  

function createSubjectDropDownFromList(subjectList){
	document.getElementById("subjectList").options.length = 0;
	$("#subjectList").html($("#subjectList").html()+'<option value="">--Select--</option>');
	var subjectList = subjectList;
	for(var i in subjectList) {
		var data = subjectList[i];
		var name = data.name;
		var id = data.id;
		$("#subjectList").html($("#subjectList").html()+"<option value=\""+id+"\">"+name+"</option>");
	}
}

function createTeacherDropDownFromList(teacherList,listId){
	document.getElementById(listId).options.length = 0;
	$("#"+listId).html($("#"+listId).html()+'<option value="">--Select--</option>');
		var teacherList = allTeacherList;
		for(var i in teacherList) {
			var data = teacherList[i];
			var name = data.name;
			var id = data.id;
			var selectedString = '';
			if(${appUser.defaultAppGroup.groupName=='ipath_app_group_teacher'} &&  id == '${teacherKey}'){
				selectedString = " selected ";
			}
			$("#"+listId).html($("#"+listId).html()+"<option "+selectedString+" value=\""+id+"\">"+name+"</option>");
		}
}


function getSubjectTodaysView(subjectId){
 
 var options = {allowHtml: true, sort: 'disable', cssClassNames:{headerRow: 'tableHeader'}};
	  var data = new google.visualization.DataTable();
	  var colN = addColumns(data);
   for ( name in colN ) {		    		
	   if (colN.hasOwnProperty(name)) { 
		   data.addColumn('string',colN[name]);
		   }

   }
   
	$.ajax({
		type : "POST",
		url : '/gadget/todaysScheduleSubjectView.do?subjectId='+subjectId+"&weekDay="+selectedDay ,
		data : "",
		success : function(tableData) {
			var html = "";
				var p = eval(tableData);
				var length= p.length;
				data.addRows(length);
				var row = 0 ;
				for (var key in p) {
					var col = 0 ;
					var map = eval(p[key]);
					var className = map.className ;	 
					data.setCell(row, col,className);
					col ++ ;
					
					var cell = eval(map.cell) ;
					for(var i in cell) {
						var colNumber  = cell[i].periodColumnNumer;
						var cellValue = cell[i].rowValue;
						data.setCell(row, colNumber,cellValue);
					}
					row ++ ;
				}  					
			visualization = new google.visualization.Table(document.getElementById('table'));
		 	visualization.draw(data, options);
		 	$(".google-visualization-table-table").attr('style','width:100%');
		 	//parent.resizeCaller();
		},
		error: function(x,e) {
		}
	});
}


function getDayData(element,day){
	selectedDay = day;
	
	if(!$("#classLevel").attr('disabled')){
		drawTodaysViewTimeTable($("#classLevel").val());
	}else if(!$("#subjectList").attr('disabled')){
		getSubjectTodaysView($("#subjectList").val());
	}else if(!$("#teacherListDayView").attr('disabled')){
		getTeacherTodaysView($("#teacherListDayView").val());
	}
	
	$('#dayList').find('li').each(function(index) {
		var elem = $(this).find('a');
		elem.removeClass('selecttab');
	});
	
	$(element).addClass("selecttab");
	
}

function showTeacherDropDownProp(){
	$('#teacherListDayView').removeAttr('disabled');
	getAllTeachers();
	$('#classLevel').attr('disabled','disabled');
	$('#subjectList').attr('disabled','disabled');
}

function showSubjectDropDownProp(){
	$('#teacherListDayView').empty();
	$('#teacherListDayView').attr('disabled','disabled');
	$('#classLevel').attr('disabled','disabled');
	getAllSubjects();
}

</script>






















<input type="hidden" id="showAllClassTimeTable" value="" />
<input type="hidden" id="entityId" />

        		<div class="breadcrumb">
					<brc:breadcrumb></brc:breadcrumb>
				</div>
                <div class="clear"></div>
                <div <c:if test="${((appUser.defaultAppGroup.groupName=='ipath_app_group_parent' ) || appUser.defaultAppGroup.groupName=='ipath_app_group_student') && scheduleVisibility=='SELF'}"> style="height:70px;"</c:if> class="schedule-grid-block">
                  <c:if test="${((appUser.defaultAppGroup.groupName=='ipath_app_group_parent' &&  dataContext.currentSelectedStudent!=null) || appUser.defaultAppGroup.groupName=='ipath_app_group_student') && scheduleVisibility=='SELF'}">
                      <div class="schedule-filter">
                      <div class="schedule-filter-div">
                      <div class="schedule-select">
                      <c:out value="${defaultClass.name}"></c:out>
                      </div>
                      </div>
                      </div>
                  </c:if>
                	<div <c:if test="${(appUser.defaultAppGroup.groupName=='ipath_app_group_parent' || appUser.defaultAppGroup.groupName=='ipath_app_group_student') && scheduleVisibility=='SELF'}"> style="display:none;"</c:if>  class="grids-title">
                    	<a href="#" id="dailyDataLink" class="anchorClass" onclick="setDailyDataProp();">Daily</a><a href="#" id="weekDataLink" class="grids-title-2" onclick="setWeekDataProp();"> <span class="black-color">| &nbsp;</span>Weekly</a>
                        <span class="fr expent-icon"></span>
                        <div class="clear"></div>
                    </div>
                    <div class="schedule-filter" id="weekViewFilter">
                    	<div <c:if test="${((appUser.defaultAppGroup.groupName=='ipath_app_group_parent' &&  dataContext.currentSelectedStudent!=null) || appUser.defaultAppGroup.groupName=='ipath_app_group_student') && scheduleVisibility=='SELF'}"> style="display:none;"</c:if> class="schedule-filter-div">
                        	<div class="schedule-select"><input type="radio" name="by-class" value="classListWeekRadio" checked="checked" onClick="$('#classBusinessKey').removeAttr('disabled');$('#teacherList1').attr('disabled','disabled');$('#teacherList1').empty();populateClassList();" >By Class</div>
                        	<select  class="schedule-select"  name="classBusinessKey" id="classBusinessKey" onchange="setClassValue(this.value)"; style="margin-left:10px; font-family: Arial,Helvetica,sans-serif; font-size: 85%;" >
							 <option  value='<c:out value="${defaultClass.key.id}"></c:out>' selected="selected"><c:out value="${defaultClass.name}"></c:out></option>
							 <c:forEach var="class" items="${allClasses}">
							 <option  value=<c:out value='${class.key.id}'/>><c:out value='${class.name}'></c:out>
							 </option>
							 </c:forEach>
							 </select></div>
                        <div <c:if test="${(appUser.defaultAppGroup.groupName=='ipath_app_group_parent' || appUser.defaultAppGroup.groupName=='ipath_app_group_student') && scheduleVisibility=='SELF'}"> style="display:none;"</c:if>  class="schedule-filter-div">
                        	<div class="schedule-non-select"><input type="radio" name="by-class" value="teacherListWeekRadio"  onClick="$('#teacherList1').removeAttr('disabled');$('#classBusinessKey').empty();getAllTeachers1();$('#classBusinessKey').attr('disabled','disabled');">By Teacher</div>
                        	 <select  <c:if test="${appUser.defaultAppGroup.groupName=='ipath_app_group_teacher' && scheduleVisibility=='SELF'}"> style="display:none;"</c:if>  class="schedule-select"  disabled="disabled"  name="teacherList1" id="teacherList1" onChange="getTeacherWeeklyData(this.value)" style="margin-left:10px; font-family: Arial,Helvetica,sans-serif; font-size: 85%;"></select>		</div>               	
                     
                        <div class="clear"></div>
                    </div>
                    <div class="schedule-filter" id="dayViewFilter" style="display: none;">
                    	<div <c:if test="${(appUser.defaultAppGroup.groupName=='ipath_app_group_teacher' ||appUser.defaultAppGroup.groupName=='ipath_app_group_parent' || appUser.defaultAppGroup.groupName=='ipath_app_group_student') && scheduleVisibility=='SELF'}"> style="display:none;"</c:if> class="schedule-filter-div" >
                        	<div class="schedule-select"><input type="radio" name="by-class-day" value="classLevelRadio" checked="checked" onClick="$('#classLevel').removeAttr('disabled');$('#subjectList').attr('disabled','disabled');$('#teacherListDayView').attr('disabled','disabled');$('#subjectList').empty();$('#teacherListDayView').empty();setClassLevel($('#classLevel').val())" >By Level</div>
                        	<!-- <select  class="schedule-select"  name="classLevel" id="classLevel" onchange="setClassLevel(this.value)" style="margin-left:10px; font-family: Arial,Helvetica,sans-serif; font-size: 85%;">
							<option value="Primary1" selected="selected">Primary1</option>
							<option value="Primary2">Primary2</option>
							<option value="Secondary1">Secondary1</option>
							<option value="Secondary2">Secondary2</option>
							<option value="Senior Secondary">Senior Secondary</option>
							</select> -->
							
							 <lov:listofvalues name="classLevel" valueSetName="ClassLevel" selectedValueId=""/>
							
							</div>
                        <div <c:if test="${(appUser.defaultAppGroup.groupName=='ipath_app_group_teacher' ||appUser.defaultAppGroup.groupName=='ipath_app_group_parent' || appUser.defaultAppGroup.groupName=='ipath_app_group_student') && scheduleVisibility=='SELF'}"> style="display:none;"</c:if> class="schedule-filter-div">
                        	<div class="schedule-non-select"><input type="radio" name="by-class-day"  value="teacherListRadio" onClick="showTeacherDropDownProp()">By Teacher</div><!-- $('#classBusinessKey').empty(); -->
                        	<select  class="schedule-select"  name="teacherListDayView" id="teacherListDayView" onChange="getTeacherTodaysView(this.value)" style="margin-left:10px; font-family: Arial,Helvetica,sans-serif; font-size: 85%;">
							</select>				
						</div>
                        <div <c:if test="${(appUser.defaultAppGroup.groupName=='ipath_app_group_teacher' ||appUser.defaultAppGroup.groupName=='ipath_app_group_parent' || appUser.defaultAppGroup.groupName=='ipath_app_group_student') && scheduleVisibility=='SELF'}"> style="display:none;"</c:if> class="schedule-filter-div">
                        	<div class="schedule-non-select"><input type="radio" name="by-class-day" value="subjectListRadio" onClick="showSubjectDropDownProp()">By Subject</div><!-- $('#classLevel').empty(); -->
                           <select  class="schedule-select"  name="subjectList" id="subjectList" onChange="getSubjectTodaysView(this.value)" style="margin-left:10px; font-family: Arial,Helvetica,sans-serif; font-size: 85%;">
							</select>
						</div>
                     	
                     
                        <div class="clear"></div>
                        <div class="schedule-date">
                    	<ul id="dayList">
                        	<li><a href="#" onclick="getDayData(this,'Monday')">Monday</a></li>
                            <li><a href="#" onclick="getDayData(this,'Tuesday')">Tuesday</a></li>
                            <li><a href="#" onclick="getDayData(this,'Wednesday')">Wednesday</a></li> <!-- class="selecttab" -->
                            <li><a href="#" onclick="getDayData(this,'Thursday')">Thursday</a></li>
                            <li><a href="#" onclick="getDayData(this,'Friday')">Friday</a></li>
                            <li><a href="#" onclick="getDayData(this,'Saturday')">Saturday</a></li>
                           
                        </ul>
                    </div>
                    </div>
                    
                 </div>
                <div class="schedule-result-block">
                    <div class="schedule-result">
                     	<div class="schedule-result-row">
                     	<div id="table"></div>              
                        <div class="clear"></div>
                        </div>
                      </div>
                        
                 <!--   <div class="clear"></div> -->
                 </div>
                     
                  <div class="clear"></div>
                
<script type="text/javascript">
jQuery('#mysite-dropdown').bind('click',function(){
	jQuery('#hdr-popup').toggle();
	jQuery('#hdr-popup').css({'margin-left':0});
	jQuery(this).toggleClass('edu-username-red-arrow');
	jQuery(this).toggleClass('edu-username-white-arrow');
	return false;
	});
jQuery('#hdr-popup').bind('click',function(e){
	e.stopPropagation();			 
	});
jQuery(document).bind('click',function(){
	jQuery('#hdr-popup').hide();
	jQuery('#mysite-dropdown').removeClass('edu-username-red-arrow');
	jQuery('#mysite-dropdown').addClass('edu-username-white-arrow');
 });
var MenuBar1 = new Spry.Widget.MenuBar("MenuBar1", {imgRight:"SpryAssets/SpryMenuBarRightHover.gif"});
</script>

