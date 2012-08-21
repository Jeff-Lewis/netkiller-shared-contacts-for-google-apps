<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tld/breadcrumb-taglib.tld" prefix="brc"%>

<div class="breadcrumb">
        		
                    <brc:breadcrumb></brc:breadcrumb>
                </div>
             
                <div class="clear"></div>
                
<c:if test="${set.parentSetKey != null}">
	<script>
$("document").ready( function (){
    setHiddenFieldValue("parentSetKeyLong",${set.parentSetKey.id}); 
});
</script>
</c:if>





<form:form id="setForm" action="/set/create.do" modelAttribute="set"
	method="post">
	<c:if test="${operation=='edit'}">
		<input type="hidden" name="paramid"
			value="<c:out value="${set.key.id}" />" />
	</c:if>
	
	 <div class="student-detail">
                
                	<div class="fr">
                	<c:if test="${operation=='edit'}">
					 <span class="student-save single-click-disable" onclick="javascript:updateSet(this)">Update</span>
					</c:if> 
					<c:if test="${operation=='create'}">
					<span class="student-save single-click-disable" onclick="javascript:createSet(this)">Save</span>
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
                 	 <span class="fl"> New Set </span>                 
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
                            	<div class="leftdiv">Set Name<div class="requiredBlock">&nbsp;</div></div>
                                <div class="rightdiv">
                                <form:input cssClass="student-edit-textbox" path="setName" /> 
                                <div class="clear"></div>
                                <span class="error">                                 
                                <form:errors path="setName" cssClass="error" /></span></div>
                                 <div class="clear"></div>
                                 </div>
                                 
                                                                    <div class="student-general-info-row mrt10">
                            	<div class="leftdiv">Parent Set</div>
                                <div class="rightdiv">
                                <form:input cssClass="student-edit-textbox" path="parentSetBusinessKey" /> 
                                <div class="clear"></div>
                               
                                <span class="error">      
                                 <form:errors path="parentSetBusinessKey" cssClass="error" />                           
                                 </span></div>
                                 <div class="clear"></div>
                                 <form:hidden path="parentSetKeyString" id="parentSetKeyLong" />
                                 </div>
                        <!-- end of left div -->
                      </div>
                      
                   <div class="right-div">
                 	<!-- right div start -->
                       <div class="student-general-info">&nbsp;</div> 	
                        	                        	  <div class="student-general-info-row">
                            	<div class="leftdiv">Order</div>
                                <div class="rightdiv">
                               <form:select path="setOrder">
					<form:option value="Alpha">Alpha</form:option>
					<form:option value="Custom">Custom</form:option>
				</form:select>
                                 
                                </div>
                                <div class="clear"></div>
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
	$("#setTab ").addClass("selectmenu");
	
	
	$("#parentSetBusinessKey").autocomplete({
	    source :"/getSearchedEntities.do?Entity=set",
		minLength : 1,
		search : function (event,ui) {
	   	    setHiddenFieldValue("parentSetKeyLong","");
     	},
	    select : function (event,ui) {
		 var selectedItem=ui.item;
		 setHiddenFieldValue("parentSetKeyLong",parseString(selectedItem.desc));
	     validateAutoComplete("errorDiv","parentSetKeyLong","parentSetBusinessKey");
	}
	}).data( "autocomplete" )._renderItem = function( ul, item ) {
		return $( "<li></li>" )
		.data( "item.autocomplete", item )
		.append( "<a>" + item.label + "<br><font size='1'>" + item.desc + "</font></a>" )
		.appendTo( ul ); 
		};
});

	function closeForm() {
		window.location = "/set/close.do";
	}
	
	function updateSet(elm) {
		singleClickDisabled(elm);
	    checkAutoCompleteMinLength("parentSetBusinessKey","parentSetKeyLong");
	 	document.getElementById('setForm').action = "/set/update.do";
		document.getElementById('setForm').submit();
	}
	
	function createSet(elm) {
		singleClickDisabled(elm);
		 checkAutoCompleteMinLength("parentSetBusinessKey","parentSetKeyLong");
	 	document.getElementById('setForm').action = "/set/create.do";
		document.getElementById('setForm').submit();
	}
	

	function deleteSet() {
		var response = confirm('Are you sure?');
		if (response) {
			document.getElementById('setForm').action = "/set/delete.do";
			document.getElementById('setForm').submit();
		}
	}
</script>
