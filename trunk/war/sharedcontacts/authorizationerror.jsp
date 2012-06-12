<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<link rel='stylesheet' type='text/css'
	href='/css/jquery-ui-1.8.12.custom.css' />


<script type="text/javascript" src='/js/jquery-1.5.2.min.js'></script>

<script type="text/javascript" src='/js/jquery-ui-1.8.12.custom.min.js'></script>
<script type="text/javascript" src='/js/jquery.form.js'></script>
<script type="text/javascript" src='/js/i18n/grid.locale-en.js'></script>
<script type="text/javascript" src='/js/jquery.jqGrid.min.js'></script>
<script type="text/javascript" src="/js/jquery.fileinput.min.js"></script>
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
<table width="950px" align="center" border="0">
		<tr>
			<td colspan="3" align="center"><jsp:include
					page="/sharedcontacts/main.do?cmd=getheader" flush="true" /></td>
		</tr>
<tr>
<td style="padding:100px 0 0 60px;color:blue;text-align:center" >
<h2>You are not authorized to see this page. Please ask your domain administrator to assign permission.</h2>

</td>
</tr>
</table>
</body>
</html>