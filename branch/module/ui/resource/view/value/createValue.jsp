<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tld/breadcrumb-taglib.tld" prefix="brc"%>

<div class="breadcrumb">
        		
                    <brc:breadcrumb></brc:breadcrumb>
                </div>
             
                <div class="clear"></div>
                
                
<c:if test="${value.setKey != null}">
	<script>
$("document").ready( function (){
    setHiddenFieldValue("setKeyLong",${value.setKey.id}); 
});
</script>
</c:if>
<c:if test="${value.parentValueKey != null}">
	<script>
$("document").ready( function (){
    setHiddenFieldValue("parentValueKeyLong",${value.parentValueKey.id}); 
});
</script>
</c:if>




<form:form id="valueForm" action="/value/create.do"
	modelAttribute="value" method="post"	>
	<c:if test="${operation=='edit'}">
		<input type="hidden" name="paramid"
			value="<c:out value="${value.key.id}" />" />
	</c:if>
	
	
 <div class="student-detail">
                
                	<div class="fr">
                	<c:if test="${operation=='edit'}">
					 <span class="student-save single-click-disable" onclick="javascript:updateValue(this)">Update</span>
					</c:if> 
					<c:if test="${operation=='create'}">
					<span class="student-save single-click-disable" onclick="javascript:createValue(this)">Save</span>
					</c:if>
                     
                      <span class="student-save" onclick="javascript:closeForm()">Cancel</span>                        
                        <div class="clear"></div>
                     </div>
                     <div class="clear"></div>
                </div>
                <div class="student-grid-block">
                <div class="pbError" id="errorDiv_ep" style="display: none;">
	Error: Invalid Data. <br>
	Review all error messages below to correct your data.</div>
                	<div class="student-title">
                    	
                        <c:if test="${operation=='create'}">
                 	 <span class="fl"> New Value </span>                 
                        </c:if>
                        
                        <c:if test="${operation=='edit'}">
                        <span class="fl"> <c:out value="${subject.subjectName}" /> </span>					
						</c:if>
                     
                        <div class="clear"></div>
                    </div>
                 </div>
                 <div class="student-info-block">
                 
                 	<div class="student-info">
                 	
                 	<div class="left-div">
                 	<!-- left div start -->
                        	<div class="student-general-info">General Information</div>
                        	                                   <div class="student-general-info-row mrt10">
                            	<div class="leftdiv">Value<div class="requiredBlock">&nbsp;</div></div>
                                <div class="rightdiv">
                                <form:input cssClass="student-edit-textbox" path="value" /> 
                                <div class="clear"></div>
                                <span class="error">                                 
                                <form:errors path="value" cssClass="error" /></span></div>
                                 <div class="clear"></div>
                                 </div>
                                 
                                                                    <div class="student-general-info-row mrt10">
                            	<div class="leftdiv">Parent Set <div class="requiredBlock">&nbsp;</div></div>
                                <div class="rightdiv">
                                <form:input cssClass="student-edit-textbox" path="setBusinessKey" /> 
                                <div class="clear"></div>
                                <span class="error">                                 
                                <form:errors path="setBusinessKey" cssClass="error" /></span></div>
                                 <div class="clear"></div>
                                 <form:hidden path="setKeyString" id="setKeyLong" />
                                 </div>
                        <!-- end of left div -->
                      </div>
                      
                   <div class="right-div">
                 	<!-- right div start -->
                       <div class="student-general-info">&nbsp;</div> 	
                       
                                                          <div class="student-general-info-row mrt10">
                            	<div class="leftdiv">Order Index</div>
                                <div class="rightdiv">
                                <form:input cssClass="student-edit-textbox" path="orderIndex" /> 
                                <div class="clear"></div>
                                <span class="error">                                 
                                <form:errors path="orderIndex" cssClass="error" /></span></div>
                                 <div class="clear"></div>
                                 </div>
                                 
                                                                    <div class="student-general-info-row mrt10">
                            	<div class="leftdiv">Parent Value  </div>
                                <div class="rightdiv">
                                <form:input cssClass="student-edit-textbox" path="parentValueBusinessKey" /> 
                                <div class="clear"></div>
                                <span class="error">                                 
                                <form:errors path="parentValueBusinessKey" cssClass="error" /></span></div>
                                 <div class="clear"></div>
                                 <form:hidden path="parentValueKeyString" id="parentValueKeyLong" />
                                 </div>
                        	
                    <!-- end of right div -->
                      </div>
                      
                 	<!--  end of student info -->
                 	</div>
                 	
                 </div>


</form:form>
<script type="text/javascript">
$(function () {
	$("#menu-contaner a").removeClass("selectmenu");
	$("#valueTab ").addClass("selectmenu");
	
	
	$("#setBusinessKey").autocomplete({
	    source :"/getSearchedEntities.do?Entity=set",
		minLength : 1,
		search : function (event,ui) {
	   	    setHiddenFieldValue("setKeyLong","");
     	},
	    select : function (event,ui) {
		 var selectedItem=ui.item;
		 setHiddenFieldValue("setKeyLong",parseString(selectedItem.desc));
	}
	}).data( "autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li></li>" )
		.data( "item.autocomplete", item )
		.append( "<a>" + item.label + "<br><font size='1'>" + item.desc + "</font></a>" )
		.appendTo( ul ); 
		};

		
	$("#parentValueBusinessKey").autocomplete({
	    source :"/getSearchedEntities.do?Entity=value",
		minLength : 1,
		search : function (event,ui) {
	   	    setHiddenFieldValue("parentValueKeyLong","");
     	},
	    select : function (event,ui) {
		 var selectedItem=ui.item;
		 setHiddenFieldValue("parentValueKeyLong",parseString(selectedItem.desc));
	}
	}).data( "autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li></li>" )
		.data( "item.autocomplete", item )
		.append( "<a>" + item.label + "<br><font size='1'>" + item.desc + "</font></a>" )
		.appendTo( ul ); 
		};
});

	function closeForm() {
		window.location = "/value/close.do";
	}
	
	function updateValue(elm) {
		singleClickDisabled(elm);
	    checkAutoCompleteMinLength("setBusinessKey","setKeyLong");
	    checkAutoCompleteMinLength("parentValueBusinessKey","parentValueKeyLong");
		document.getElementById('valueForm').action = "/value/update.do";
		document.getElementById('valueForm').submit();
	}
	
	function createValue(elm) {
		singleClickDisabled(elm);
	    checkAutoCompleteMinLength("setBusinessKey","setKeyLong");
	    checkAutoCompleteMinLength("parentValueBusinessKey","parentValueKeyLong");
		document.getElementById('valueForm').action = "/value/create.do";
		document.getElementById('valueForm').submit();
	}
	

	function deleteValue() {
		var response = confirm('Are you sure?');
		if (response) {
			document.getElementById('valueForm').action = "/value/delete.do";
			document.getElementById('valueForm').submit();
		}
	}
</script>
