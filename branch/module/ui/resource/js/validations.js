/**
 * JavaScript file for custom validations on forms
 */

function validateTextAreaMaxLength(field) {
	var value = field.value;
	if (value != null && value != "") {
		var newLines = (value.match(/\n/g));
		var numNewLines = 0;
		if (newLines != null)
			numNewLines = newLines.length;
		if ((value.length + numNewLines) >= 500) {
			field.value = value.substring(0, (500 - numNewLines));
			return false;
		}
	}
	return true;
}

/*function validateNumber(evt) {
	
	 // Get the ASCII value of the key that the user entered
if(window.event)
var key = window.event.keyCode; 
else
var key = evt.which;

//Verify if the key entered was a numeric character (0-9) or a backspace (.)
if ( (key > 47 && key < 58)||key==8||key==0  )
   {
   // If it was, then allow the entry to continue
   return;
  }
else
   {
     if(window.event)
   	  window.event.returnValue = null; // IE hack
     else
   	  evt.preventDefault(); // standard method
  }
	
	
	// var theEvent = evt || window.event;
	//  var keyAscii = theEvent.keyCode || theEvent.which;
	//  key = String.fromCharCode( keyAscii );
	//  var regex = /[0-9]|\./;
	//  if( !regex.test(key) && keyAscii!='8' ) { // The unicode for Bachspace is '8'
	//    theEvent.returnValue = false;
	//    if(theEvent.preventDefault) theEvent.preventDefault();
	//  }
}*/


function validateNumber(evt) {
	
	var theEvent = evt || window.event;
	var key = theEvent.keyCode || theEvent.which;
	key = String.fromCharCode(key);
	var regex = /[0-9]|\.|[\b]/;
	if (!regex.test(key)) {
		theEvent.returnValue = false;
		if (theEvent.preventDefault)
			theEvent.preventDefault();
	}
}
