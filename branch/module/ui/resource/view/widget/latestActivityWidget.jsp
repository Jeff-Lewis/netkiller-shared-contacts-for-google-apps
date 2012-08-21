<%@ taglib uri="/tld/security-taglib.tld" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
var pageSize=20;
//var prefs = new gadgets.Prefs();
//var domainName =  window.location.href;
//alert(domainName);
//  google.load('visualization', '1', {packages: ['table']});
	  
function makeActive(element){ 
	if( element.className!="selecttab")	{
		$(".filter-result").html("");
	}
     element.className="selecttab";
     
}
function removeActive(element1,element2,element3,element4){
	var elem1 = document.getElementById(element1);
	var elem2 = document.getElementById(element2);
	var elem3 = document.getElementById(element3);
	var elem4 = document.getElementById(element4);
	if(elem1)
		elem1.className="tab";
	if(elem2)
		elem2.className="tab";
	if(elem3)
		elem3.className="tab";
	if(elem4)
		elem4.className="tab";
}	

function checkDuration(element1,element2,element3,element4){
	if(element1.className!="selecttab"){
	makeActive(element1);
	removeActive(element2,element3,element4);
	setDurationType(element1);
	}
}

function checkActivity(element1, element2, element3, element4, element5){
	if(element1.className!="selecttab"){
		makeActive(element1);
		removeActive(element2,element3,element4,element5);
		setActivityType(element1);
		}
}

	  
   </script>
<script type="text/javascript">
 $(document).ready(function(){
	 
	     initializeCounters();
	     //drawVisualization();
		 makeJSONRequest();
 }); 
//var data1 = null;
var eventSize ;
var assignmentSize ;
var announcementSize  ;
var noteSize;
 var row = 0;
 var postData  ;
 var activityType ="all";
 var durationType ="one";
//var options;
/*   function drawVisualization() {
	  var pare = gup('parent');
	  var temp = new Array();
	  temp = pare.split('/');
	   
	  var sitename = temp[5] ;
	   postData = {field : "classSiteName"};
	   initializeCounters();
	  // initializeTable();
	    //makeJSONRequest();
 }; */
    </script>

<script type="text/javascript">
 
	function moreHandle() {
		var hLink = $('.view-older-post a');
		if (row % 10 != 0) {
			hLink.hide();
		} else {
			hLink.show();
		}
	}

	function gup(name) {
		name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
		var regexS = "[\\?&]" + name + "=([^&#]*)";
		var regex = new RegExp(regexS);
		var results = regex.exec(window.location.href);
		if (results == null)
			return "";
		else
			return results[1];
	}

	function initializeCounters() {
		eventSize = 0;
		assignmentSize = 0;
		announcementSize = 0;
		noteSize = 0;
	}

	/*  function initializeTable(){
	 options = {allowHtml: true};
	 data1 = new google.visualization.DataTable();
	 var myView = new google.visualization.DataView(data1)
	 data1.addColumn('string', '');
	 } */

	function setActivityType(radioButton) {
	
			$('#loading-icon').show();
		if (radioButton == document.getElementById('allActivity')) {
			activityType = "all";
		} else if (radioButton == document.getElementById('allEvent')) {
			activityType = "event";
		} else if (radioButton == document.getElementById('allAssignment')) {
			activityType = "assignment";
		} else if (radioButton == document.getElementById('allAnnouncement')) {
			activityType = "announcement";
		} else if (radioButton == document.getElementById('allNote')) {
			activityType = 'note';
		}

		// clear the row
		//data1.removeRows(0,  data1.getNumberOfRows());
		/*  row = 0 ;
		 initializeTable();
		 */
		 //set duration
	//	 setDurationType(radiobutton);
		
		 // clear the Counters
			 
		initializeCounters();

		// If the activity type is changed then fetch records.
		makeJSONRequest();

	}
	 
	 function setDurationType(radioButton) {
		 $('#loading-icon').show();
			if (radioButton == document.getElementById('allOne')) {
				durationType = "one";
			} else if (radioButton == document.getElementById('allThree')) {
				durationType = "three";
			} else if (radioButton == document.getElementById('allSeven')) {
				durationType = "seven";
			} else if (radioButton == document.getElementById('allFifteen')) {
				durationType = "fifteen";
			}
				
		 // clear the Counters
				 
		initializeCounters();

		// If the activity type is changed then fetch records.
		makeJSONRequest();
			 
	 }
	 
	 

	function getDateValue(date) {
		var dt = new Date(date);
		var month = "";
		switch (dt.getMonth()) {
		case 0:
			month = "January";
			break;
		case 1:
			month = "February";
			break;
		case 2:
			month = "March";
			break;
		case 3:
			month = "April";
			break;
		case 4:
			month = "May";
			break;
		case 5:
			month = "June";
			break;
		case 6:
			month = "July";
			break;
		case 7:
			month = "August";
			break;
		case 8:
			month = "September";
			break;
		case 9:
			month = "October";
			break;
		case 10:
			month = "November";
			break;
		case 11:
			month = "December";
			break;
		}
		return dt.getDate() + " " + month + " " + dt.getUTCFullYear();
	}

	function makeJSONRequest() {
		/* var params = {};
		params[gadgets.io.RequestParameters.CONTENT_TYPE] = gadgets.io.ContentType.JSON;
		params[gadgets.io.RequestParameters.POST_DATA] = gadgets.io.encodeValues(postData);
		params[gadgets.io.RequestParameters.METHOD] = gadgets.io.MethodType.POST;
		 // This URL returns a JSON-encoded string that represents a JavaScript object
		 var url = domainName+"/gadget/latestactivitygadget.do?eventCount="+ eventSize+"&assignmentCount="+assignmentSize+"&announcementCount="+announcementSize+"&activityType="+activityType;
		 gadgets.io.makeRequest(url, response, params); 
		 */
		$.ajax({
			url : "/gadget/latestactivitygadgetmodified.do?eventCount=" + eventSize
					+ "&assignmentCount=" + assignmentSize
					+ "&announcementCount=" + announcementSize
					+"&noteCount=" + noteSize
					+ "&activityType=" + activityType
					+ "&durationType=" + durationType,
			dataTypeString : "json",
			//data : postData,
			success : function(result) {
				$('#loading-icon').hide();
				response(result);
			},
			error : function() {
			}

		});
	};

	function response(obj) {
		var jsondata = obj;
		// Process returned JS object as an associative array
		var entities = jsondata;
		var strDiv = "";
		// alert(entities.length);
		// data1.addRows(entities.length);
	//	$(".filter-result").html("");
		for ( var i = 0; i < entities.length; i++) {
			strDiv += "<div class='filter-result-row'>";
			var dataM = entities[i];
			var date = dataM.date;
			var data = eval(dataM.data);
			var type = data.type;
			//alert(type);
			if (type == 'Event') {
				var content = getEventContent(data, date);
				strDiv += content;
				eventSize++;
				//alert("eventSize "+eventSize);
			} else if (type == 'Assignment') {
				var content = getAssignmentContent(data, date);
				strDiv += content;
				assignmentSize++;
			//	alert("assignmentSize " + assignmentSize);
			} else if (type == 'Announcement') {
				var content = getAnnouncementContent(data, date);
				strDiv += content;
				announcementSize++;
				//alert("announcementSize  "+announcementSize);
			} else if (type =='Note') {
				var content = getNoteContent(data, date);
				strDiv+= content;	
				noteSize++;
			}
			row++;
			strDiv += "</div>";
		}
		moreHandle();
		$(".filter-result").html($(".filter-result").html()+strDiv);
	}

	/*    var event= eval(entities[0]);
	   var date = event.date;
	   var data = eval(event.data);
	   var content = getEventContent(data,date);
	   var strDiv="<div class='filter-result-row'>";
	   strDiv += content+"</div>";
	   
	 */
	/* for(var j=0;j<entities.length;j++){
	 strDiv = document.createElement("div");
	 strDiv.setAttribute('class','filter-result-row');
	} */
	//var colNum = 0;

	/*  for(var i in entities){
	 	strDiv = "<div class='filter-result-row'>";
	  	var dataMap = eval(entities[0]);
	    var date = dataMap.date;
	     var data = eval(dataMap.data);
	     var type= data.type ;
	     if(type == 'Event') {  
	        var content = getEventContent(data,date);
	        //moreHandle(eventSize);   
	        //data1.setCell(row, colNum,content ,undefined,undefined);
	         strDiv += content+"</div>";
			eventSize ++ ;
	     }else if(type=='Assignment'){
	        var content = getAssignmentContent(data,date);
	       // moreHandle(assignmentSize );
	       // data1.setCell(row, colNum,content ,undefined, {style: 'text-align:left;width:30%'});
	        assignmentSize ++ ;
	     }else if(type=='Announcement'){
	        var content = getAnnouncementContent(data,date);
	         //moreHandle(announcementSize);  
	      //  data1.setCell(row, colNum,content ,undefined, {style: 'text-align:left;width:30%'});
	        announcementSize ++ ;
	     }  
	    
	    //row++;
	} */

	// moreHandle();
	/*  visualization = new google.visualization.Table(document.getElementById('table'));  
	  var view = new google.visualization.DataView(data1);
	   options['pagingSymbols'] = {prev: 'prev', next: 'next'};
	    options['pagingButtonsConfiguration'] = 'auto';
	   visualization.draw( view, options); */
	//alert("Complete");  
	//  $(".google-visualization-table-table").attr('style','width:100%');
	//  }

	function getEventContent(data, date) {
		var dt = new Date(date);
		var toDate = new Date(data.toDate);
		var content = getEventImage()
				+ getEventClassInfo(data)
				+"<a href='#' style='color:#008CD4;text-decoration:none' onClick = 'renderEvent("+data.id+")'>"
				+ data.title +"</a>"
				+ "</div><div class='filter-date'>"
				+ getDateValue(date)
				+ "</div> <div class='clear'></div></div><div class='filter-desc'>"
				+ data.description + "</div><div class='desc-auther'>" 
				+ getEventDateInfo(date, data)
				+"</div><div class='desc-auther'>"
				+ "By: " + data.teacher
				+ "</div></div><div class='clear'></div>";
		//var content = "<div>"+getEventImage()+"<b>"+data.teacher+"</b> :  " +data.title + "<br />" +"<div style='padding-left: 45px;padding-bottom:5px;'>"+getEventDateInfo(date,data)+"</div><div style='padding-left: 45px;'>"+ data.description+"</div><br />"+getFooter(date)+"</div>";
		return content;
	}

	function getAssignmentContent(data, date) {
		var content = getAssignmentImage()
				+ "<div class='filter-desc-div'><div class='fullwidth'><div class='filter-title'>" + data.subject.subjectName +"</div><div class='filter-title'>" + "&nbsp; : &nbsp;" +"</div><div class='filter-title'><a href='#' style='color:#008CD4;text-decoration:none' onClick='renderAssignment("+data.id+")'>"
				+ data.title + "</a>" 
				+ "</div><div class='filter-date'>"
				+ getDateValue(date)
				+ "</div> <div class='clear'></div></div><div class='filter-desc'>"
				+ data.description
				+ "</div><div class='desc-auther'> Due On: &nbsp;"
				+ getDateValue(data.submissionDate)
				+ "</div><div class='desc-auther'>"
				+ "By: "
				+ data.teacher
				+ "</div><div class='download-icon' onClick='download(\""+data.parentLink+"\")'>Download</div></div><div class='clear'></div>";

		//var content = "<div>"+getAssignmentImage()+ "<b>"+data.teacher+"</b> : " +data.subjectName +" : "+data.title +"<br />"+"<div style='padding-left: 45px;padding-bottom:5px;'>"+"Due on "+ getDateValue(data.submissionDate) + "</div><div style='padding-left: 45px;'>"+ data.description+" </div><br /> "+getFooter(date)+ "<div style='float:left;'> : " +"<a href='"+data.attachmentId+"'>Download</a></div>"+"</div>"
		return content;
	}

	function getAnnouncementContent(data, date) {
		//alert(data.link);
		var content = getAnnouncementImage()
				+ getAnnouncementSiteValue(data)
				+ "<a href='#' style='color:#008CD4;text-decoration:none' onClick = 'renderAnnouncement(\""+ data.link +"\")'>"
				+ data.title+"</a>"
				+ "</div><div class='filter-date'>"+getDateValue(date)
				+ "</div> <div class='clear'></div></div><div class='filter-desc'>"
				+ data.description + "</div>" + "<div class='desc-auther'>"
				+ "By: " + data.author
				+ "</div></div><div class='clear'></div>";
		//var content = "<div>"+getAnnouncementImage()+"<b>"+data.author+"</b> : "+data.title + "<br />"+"<div style='padding-left: 45px;'>"+data.description+"</div><br />"+getFooter(date)+"</div>";
		return content;
	}
	
	function getNoteContent (data, date) {
		
		var content = getNoteImage()
			+ "<div class='filter-desc-div'><div class='fullwidth'><div class='filter-title'>"+data.className +"&nbsp; : &nbsp;"+data.student+"&nbsp; : &nbsp;"+ "</div><div class='filter-title'><a href='#' style='color:#008CD4;text-decoration:none'onClick='renderNote("+data.id+")'> "
			+ data.subject + "</a>" 
			+ "</div><div class='filter-date'>"+getDateValue(date)
			+ "</div> <div class='clear'></div></div><div class='filter-desc'>"
			+ data.description+ "</div>" + "<div class='desc-auther'>"
			+ "From: " + data.sender
			+"</div></div><div class='clear'></div>";
			
			return content;
	}

	function getEventImage() {
		return "<div class='filter-icon-div'><div class='events-icon'></div></div>";
	}

	function getAssignmentImage() {
		return "<div class='filter-icon-div'><div class='assignments-icon'></div></div>";
	}
	
	function getNoteImage() {
		return "<div class='filter-icon-div'><div class='notes-icon'></div></div>";
	}

	function getAnnouncementImage() {
		return "<div class='filter-icon-div'><div class='announcement-icon'></div></div>";
	}

	function getFooter(date) {
		return "<div style='color:#A9A9A9; float:left'>." + getDateValue(date)
				+ "</div>";
	}
	
	function getAnnouncementSiteValue(data) {
		var announcementSiteInfo= "";
		if ((data.site ==null) || (data.site == "NA")){
			announcementSiteInfo= "<div class='filter-desc-div'><div class='fullwidth'> <div class='filter-title'>";
		}
		else{
			announcementSiteInfo= "<div class='filter-desc-div'><div class='fullwidth'> <div class='filter-title'>" + data.site +"</div><div class='filter-title'>" + "&nbsp; : &nbsp;" +"</div><div class='filter-title'>"
		}
		return announcementSiteInfo;
	}
	
	function getEventClassInfo(data){
		eventClassInfo= "";
		if(data.className =="NA" || data.className== null){
			eventClassInfo=	"<div class='filter-desc-div'><div class='fullwidth'> <div class='filter-title' >";
		}else{
			eventClassInfo="<div class='filter-desc-div'><div class='fullwidth'> <div class='filter-title' >"+ data.className+"</div><div class='filter-title'>" + "&nbsp; : &nbsp;" +"</div><div class='filter-title'>";
		}
		return eventClassInfo;
	}
	function getEventDateInfo(date, data) {
		// It is assumed that there are only two types of events, all day event and other type.
		// This code may need some change if other type of events are added.

		var dt = new Date(data.fromDate);
		var toDate = new Date(data.toDate);
		var eventDateInfo = "";
		if (data.eventType == 'allDayEvent') {
			if (data.fromDate == data.toDate) {
				eventDateInfo = "Event will be held On:&nbsp;"+getDateValue(data.fromDate);
			} else {
				eventDateInfo = "Event will be held From:&nbsp;"+ getDateValue(data.fromDate) + " to "
						+ getDateValue(data.toDate);
			}
		} else {
			if (getDateValue(data.fromDate) == getDateValue(data.toDate)) {
				eventDateInfo = "Event will be held From:&nbsp;"+getDateValue(data.fromDate) + "  "
						+ getHoursInAmOrPm(dt) + ":" + getMinutesValue(dt)
						+ " " + getAmOrPmTag(dt) + " to "
						+ getHoursInAmOrPm(toDate) + ":"
						+ getMinutesValue(toDate) + " " + getAmOrPmTag(toDate);
			} else {
				eventDateInfo = "Event will be held From:&nbsp;"+getDateValue(data.fromDate) + " "
						+ getHoursInAmOrPm(dt) + ":" + getMinutesValue(dt)
						+ " " + getAmOrPmTag(dt) + " to "
						+ getDateValue(data.toDate) + " "
						+ getHoursInAmOrPm(toDate) + ":"
						+ getMinutesValue(toDate) + " " + getAmOrPmTag(toDate);
			}
		}

		return eventDateInfo;
	}
	function getMinutesValue(dt) {
		var min = dt.getMinutes();
		if (min < 10) {
			min = "0" + min;
		}

		return min;
	}

	function getHoursInAmOrPm(dt) {
		var hour = dt.getHours();
		if (hour > 12) {
			hour = 23 - 12;
		}
		if (hour < 10) {
			hour = "0" + hour;
		}
		return hour;
	}

	function getAmOrPmTag(dt) {
		var hour = dt.getHours();
		if (hour < 12) {
			return "AM";
		} else {
			return "PM";
		}
	}
	
	 function renderAnnouncement(linkStr){
		
		window.location.href = linkStr;
	} 
	 
	 function renderEvent(id){
			window.location.href = "/event/showDetail.do?paramid="+id;
		}
	 
	 function renderAssignment(id){
			window.location.href = "/assignment/showDetail.do?paramid="+id;
		}
	 
	 function renderNote(id){
		 window.location.href = "/note/showDetail.do?paramid="+id;
	 }
	 
	 function download(id){
		 if(id != "emptyLink"){
		 window.location.href = id;
		 }
		 
		 
	 }

</script>


<div id="left-div" style="width:100%">
					<div class="home-filter">
						<ul>
							<li><a href="#" class="selecttab" id="allActivity"
								value="All"
								onclick="checkActivity(this , 'allEvent','allAssignment','allAnnouncement', 'allNote');">All
									Posts</a></li>
							<li><a href="#" class="tab" id="allAnnouncement"
								value="Announcement"
								onclick="checkActivity(this, 'allEvent','allAssignment','allActivity','allNote');">Announcement</a></li>
							<c:if test="${sec:hasReadPermission(appUser,'Assignment',dataContext)}">		
							<li><a href="#" class="tab" id="allAssignment"
								value="Assignment"
								onclick="checkActivity(this, 'allEvent','allAnnouncement','allActivity','allNote');">Assignments</a>
							</li></c:if>
							<li><a href="#" class="tab" id="allEvent" value="Event"
								onclick="checkActivity(this, 'allAnnouncement','allAssignment','allActivity','allNote');">Events</a>
							</li>
							<c:if test="${sec:hasReadPermission(appUser,'Note',dataContext)}">	
							<li><a href="#" class="tab" id="allNote" value="Note"
								onclick="checkActivity(this, 'allAnnouncement','allAssignment','allActivity','allEvent');">Notes</a>
							</li></c:if>
							
							<div class="clear"></div>

						</ul>
						<div class="clear"></div>
					</div>
					
					<div class="home-filter">
						<ul>
							<li><a href="#" class="selecttab" id="allOne"
								value="One"
								onclick="checkDuration(this, 'allThree' , 'allSeven', 'allFifteen');"> 1Day </a>
							</li>
							<li><a href="#" class="tab" id="allThree"
								value="Three"
								onclick="checkDuration(this, 'allOne' , 'allSeven', 'allFifteen');"> 3Days </a>
							</li>
							<li><a href="#" class="tab" id="allSeven"
								value="Seven"
								onclick="checkDuration(this, 'allThree' , 'allOne', 'allFifteen');"> 7Days </a>
							</li>							
							<li><a href="#" class="tab" id="allFifteen" 
								value="Fifteen"
								onclick="checkDuration(this, 'allThree' , 'allSeven', 'allOne');"> 15Days </a>
							</li>
							
							<div class="clear"></div>

						</ul>
						<div class="clear"></div>
					</div>
					
					<div class="filter-result">
							 
					 </div>
					 <div id="loading-icon" class="filter-result-row loading-icon-div">
								<img src="/images/loading.gif" alt="Loading">
					</div>
					<div class="view-older-post">
						<a href="#" id="hyperLink" onclick="makeJSONRequest()">view
							older posts</a>
					</div>
				</div>
				
<style>
.download-icon:hover{cursor:pointer;}
</style>				
				