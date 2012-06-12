<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src='/js/jquery-1.5.2.min.js'></script>

<script type="text/javascript" src='/js/jquery-ui-1.8.12.custom.min.js'></script>
<script type="text/javascript">
$(document).ready(function(){
	
	var groupName="";
	while(groupName.length<4)	{
	groupName=prompt("Please enter Shared Contacts Group Name(At least 4 characters):","NK Shared Contacts");
	}
	var $data = { 
			cmd: 'initializeContacts' ,
			group:groupName
			
		};
	$.ajax({
		url:'/sharedcontacts/main.do',
		type:'post',
		data: $data,
		//success:handleSuccess,
		//error:handleError,
		success:function(xml){
			//alert(xml);
			
			//var xml_text = $(xml).text();
			//alert(xml_text);			

			var code = $(xml).find('code').text();
			var message = $(xml).find('message').text();
			//alert(code);
			if(code == "success"){
				alert(message);
				window.location.href = "/sharedcontacts/main.do?cmd=list&groupName="+groupName;
				//window.location.href = "/sharedcontacts/main.do?cmd=list&groupName="+groupName+"&defaultGridOrder=lastModifiedDate"; 
			}
			else{
			//	alert(message);
			}
			
			//alert(message);
			
			//$(xml).find('cat').each(function(){
			//	var item_text=$(this).text();
			//	alert(item_text);
			//});
		},
		error:function(xhr,status,e){
			//alert("User Contacts will get sychronized in a while.");
			window.location.href = "/sharedcontacts/main.do?cmd=list&groupName="+groupName;
		}
	}); //end ajax		



});


</script>
<title>Netkiller Shared Contacts</title>

<!-- Google Analytics  -->
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-32320031-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>
</head>
<body>

</body>
</html>