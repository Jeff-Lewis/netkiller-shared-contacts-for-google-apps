<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel='stylesheet' type='text/css' href='/css/jquery-ui-1.8.12.custom.css'/>
<script type="text/javascript" src='/js/jquery-1.5.2.min.js'></script>
<script type="text/javascript" src='/js/jquery-ui-1.8.12.custom.min.js'></script>
<script type="text/javascript">
$(document).ready(function() {
	$( "#upgrade" ).button();
	$( "#upgrade" ).click(function() {
		window.location.href = "/sharedcontacts/main.do?cmd=upgrade";
		var $data = { cmd: 'upgrade'}; 
	/* 	$.ajax({
			url:'/sharedcontacts/main.do',
			type:'post',
			timeout:20000,
			data: $data,
			//success:handleSuccess,
			//error:handleError,
			success:function(xml){
				var code = $(xml).find('code').text();
				var message = $(xml).find('message').text();
				var url = $(xml).find('url').text();
				if(code == "success"){
					alert(message);
					window.location.href = "/sharedcontacts/main.do?cmd=list";
				}
				else{
					alert(message);
				}				
			},
			error:function(xhr,status,e){
				alert("Error occured!");
			}
		}); //end ajax	 */
	});
	
});
</script>

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
<div id="confirmSubmitMessage" title=" "></div>
<table width="950px" align="center" border="0">
	<tr>
		<td align="center">
			<jsp:include page="/sharedcontacts/main.do?cmd=getheader" flush="true" />
		</td>
	</tr>
	<tr>
		<td align="center">
		<div style="text-align:left;margin-top:70px;margin-left:25%; margin-bottom:150px;">
			<table border="0">
			<tr>
				<td style="text-align:center;vertical-align: top;">
					<div style="font-size:28px;margin-right:60px;">
						<span style="font:bold 14px arial; color:#000">Current Membership<br /><br /><b>Free</b></span><br />
						<span style="font:bold 14px arial; color:#ff0000;"><br/><br/>Limited to 50 contacts</span>
					</div>
				<td style="border-left:2px dashed;padding-left:60px;text-align:center;">
					<div style="font-size:28px;">
						<span style="font:bold 15px arial; color:#000">Upgrade To</span><div style="width:320px; margin:10px 0 0 0;
   "><span style=" float: left;
    margin: 14px 0 0;
    font:bold 15px arial;
    color:#2E6E9E;
    position: relative;
    width: 119px;"><b>Unlimited</b></span><button id="upgrade" style="font-family:Arial;font-size:16px;height:40px;width:100px;text-align:center;padding:0px 0px 0px 0px;">Upgrade</button><br />
						<span style="font:bold 14px arial; color:#ff0000;"><br/><br/>Up to 30,000 contacts for all domain users<br /><br /></span>
					</div>
					<div  style="text-align:right"><img width="30%" height="30%" src="/css/images/paypal.png"/></div>
				</td>
			</tr>
			</table>
		</div>	
		</td>
	</tr>
	<tr valign="top">
		<td align="center" valign="top">
			<jsp:include page="/sharedcontacts/main.do?cmd=getfooter" flush="true" />
		</td>
	</tr>	
</table>
</body>
</html>