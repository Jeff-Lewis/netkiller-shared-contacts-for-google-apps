function setHiddenFieldValue(fieldID,value) {
	var fieldToSet=document.getElementById(fieldID);
	 fieldToSet.value=value; 
}

function checkAutoCompleteMinLength(fieldid,hiddenfieldid) {
	var element=document.getElementById(fieldid);
	var minLength=$("#"+fieldid).autocomplete( "option" , "minLength");
	if ((element.value.length < minLength)) {
		setHiddenFieldValue(hiddenfieldid,"");
	}
}

function parseString(stringToParse) {
	var beginingIndex=stringToParse.indexOf("{");
	var endingIndex=stringToParse.indexOf("}");
	var subst=stringToParse.substring(beginingIndex+1,endingIndex);
	return subst;
}

function populateKey(fieldid,fieldvalue) {
	var element=document.getElementById(fieldid);
	element.value=fieldvalue;
}

function validateAutoComplete (divid,fieldid,businesskey) {
	   var field=document.getElementById(fieldid);
	   var bkfield=document.getElementById(businesskey);
	   if ((bkfield.value===null || bkfield.value=="")&&(field.value != null || field != "")) {
		   setHiddenFieldValue(fieldid,"");
		   return true;
	   }
	   if ((field.value===null || field.value=="")&&(bkfield.value != null || bkfield.value != "")) {
		   showAutoCompleteError(divid);
		   return false;
	   }
	   else {
		hideAutoCompleteError(divid);   
	   return true;
	   }
}

function showAutoCompleteError(divid){
	 	   document.getElementById(divid).style.visibility="visible";
}

function hideAutoCompleteError(divid){
	   document.getElementById(divid).style.visibility="hidden";
}

