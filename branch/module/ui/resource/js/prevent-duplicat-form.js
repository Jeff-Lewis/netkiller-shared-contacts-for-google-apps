function singleClickDisabled(elm){
	var classname = elm.className;
	var classArray = classname.split(" ");
	var classNameOfSpan = classArray[1];
	var test = $("."+classNameOfSpan).attr("disabled");
	if(!test){
		    $("."+classNameOfSpan).removeAttr("onclick")
			$("."+classNameOfSpan).attr("disabled","disabled");
	}	
}