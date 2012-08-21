<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.tableHeader	{
    background:  url("/images/ui-bg_glass_85_dfeffc_1x400.png") repeat-x scroll 50% 50% #DFEFFC;
    font-weight: bold;
    text-align: left;
}
</style>
<link rel="stylesheet" type="text/css" media="screen"
	href="/css/jqgrid/ui.jqgrid.css" />
<link type="text/css" rel="stylesheet"
	media="handheld,print,projection,screen,tty,tv"
	href="/css/themes/common.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="/css/main.css" />
<link type="text/css" href="/css/themes/redmond/jquery.ui.theme.css"
	rel="stylesheet" />
<link type="text/css" href="/css/themes/redmond/jquery.ui.autocomplete.css"
	rel="stylesheet" />

<script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
<script src="/js/jqgrid/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="/js/jqgrid/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="/js/ui/i18n/jquery-ui-i18n.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="/js/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript" src="/js/ui/i18n/jquery.ui.datepicker-en-GB.js"></script>
<script type="text/javascript" src="/js/google/jsapi.js"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.position.js"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.autocomplete.js"></script>
<script type="text/javascript" src="/js/util/autocompleteutil.js"></script>
<script type="text/javascript">
      google.load('visualization', '1', {packages: ['table']});
      
    </script>

    <script type = 'text/javascript'>
    $(function() {
    	$("#menu-contaner a").removeClass("selectmenu");
    	$("#myclassTab").addClass("selectmenu");
    });

function populateClassList(){
	var selectListData = '<option selected="selected" > --Select-- </option>';
	selectListData += '<option value="' + '<c:out value="${defaultClass.key.id}"></c:out>' + '">' + '<c:out value="${defaultClass.name}"></c:out>' + '</option>';
	<c:forEach var="class" items="${allClasses}">
	selectListData += '<option value="' + '<c:out value="${class.key.id}"></c:out>' + '">' + '<c:out value="${class.name}"></c:out>' + '</option>';
	</c:forEach>
	$('#classBusinessKey').append(selectListData);
	
}
 function getAllTeachers1() {	 
 	$.ajax({
 		type : "POST",
 		url : '/gadget/getAllGlobalFilteredTeachers.do' ,
 		data : "",
 		success : function(teachers) {
 			document.getElementById("teacherList1").options.length = 0;
			$("#teacherList1").html($("#teacherList1").html()+'<option value="">--Select--</option>');
 			var teacherList = eval(teachers);
 			for(var i in teacherList) {
 				var data = teacherList[i];
 				var name = data.name;
 				var id = data.id;
 				$("#teacherList1").html($("#teacherList1").html()+"<option value=\""+id+"\">"+name+"</option>");
 			}
 		},
 		error: function(x,e) {
 		}
 	});
 }   
    
    function getTeacherWeeklyData(teacherKey){
	    google.load('visualization', '1', {packages:['table']});
	    google.setOnLoadCallback(subjectPeriodData(teacherKey));
    }
    
    function subjectPeriodData(teacherKey){
         var data = new google.visualization.DataTable();
         var colN = addColumns(data);
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
             parent.resizeCaller();
          }
          ,
       });
    }
  	    
    </script>
<input type="hidden" id="showAllClassTimeTable" value="" />
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
			drawtimetable();
		
		});
	</script>
</c:when>
<c:otherwise>
<script>
		$("document").ready( function (){
			var defaultClassKey = ${defaultClass.key.id} ;
			if(defaultClassKey!=null && defaultClassKey!='' ){
				setHiddenFieldValue("entityId","${defaultClass.key.id}");
				drawtimetable();
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
		parent.resizeCaller();
	}
	else
	{
	setHiddenFieldValue("entityId",selectedValue);
	drawtimetable();
	}
}
function addColumns(data){   
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
	  var colN = addColumns(data);
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

//-->
</script>
<head>
</head>
<body >
<c:if test="${appUser.defaultAppGroup.groupName=='ipath_app_group_admin' || appUser.defaultAppGroup.groupName=='ipath_app_group_parent' || appUser.defaultAppGroup.groupName=='ipath_app_group_student'|| (appUser.defaultAppGroup.groupName=='ipath_app_group_teacher' && showAllClassTimeTable=='true') }">

<div class="ui-widget">	
			<input type="radio" name="ViewType" value="teacher" CHECKED onClick="$('#classBusinessKey').removeAttr('disabled');$('#teacherList1').attr('disabled','disabled');$('#teacherList1').empty();populateClassList();" /> Class										
			<!--<input type="text" id="classBusinessKey" />  -->
			 <select name="classBusinessKey" id="classBusinessKey" onchange="setClassValue(this.value)"; style="font-family: Arial,Helvetica,sans-serif; font-size: 85%;" >
			 <option  value='<c:out value="${defaultClass.key.id}"></c:out>' selected="selected"><c:out value="${defaultClass.name}"></c:out></option>
			 <c:forEach var="class" items="${allClasses}">
			 <option  value=<c:out value='${class.key.id}'/>><c:out value='${class.name}'></c:out>
			 </option>
			 </c:forEach>
			 </select>		
			 
			 <input type="radio" name="ViewType" value="teacher" onClick="$('#teacherList1').removeAttr('disabled');$('#classBusinessKey').empty();getAllTeachers1();$('#classBusinessKey').attr('disabled','disabled');" /> Teacher
			 <select name="teacherList1" id="teacherList1" onChange="getTeacherWeeklyData(this.value)" style="font-family: Arial,Helvetica,sans-serif; font-size: 85%;">
	</select>
<div>
</div>
</div>
<br>

</c:if>
<input type="hidden" id="entityId" />
<div id="table"></div>
</body>
</html>