<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Netkiller Shared Contacts</title>
<link rel='stylesheet' type='text/css'
	href='/css/jquery-ui-1.8.12.custom.css' />
<link rel='stylesheet' type='text/css' href='/css/ui.jqgrid.css' />
<link rel="stylesheet" type="text/css" href="/css/fileinput.css" />

<script type="text/javascript" src='/js/jquery-1.5.2.min.js'></script>

<script type="text/javascript" src='/js/jquery-ui-1.8.12.custom.min.js'></script>
<script type="text/javascript" src='/js/jquery.form.js'></script>
<script type="text/javascript" src='/js/i18n/grid.locale-en.js'></script>
<script type="text/javascript" src='/js/jquery.jqGrid.min.js'></script>
<script type="text/javascript" src="/js/jquery.fileinput.min.js"></script>
<link href="/css/style.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="/img/favicon.ico" type="image/x-icon" />
</head>
<body>
<table width="950px" align="center" border="0">
		<tr>
			<td align="center"><jsp:include
					page="/sharedcontacts/main.do?cmd=getheader" flush="true" /></td>
		</tr>
		<tr>
		<td>
		<div class="thanks">Thank You !</div>
<div class="thanks_note">
	Your Shared contacts with up to 30,000 contact for all domain user is now enabled.
</div>


</td>
		</tr>
		<tr >
			<td align="center" valign="top">
			
			<br>
			<br>
			<br>
			<br><jsp:include
					page="/sharedcontacts/main.do?cmd=getfooter" flush="true" /></td>
		</tr>
		</table>
</body>
</html>