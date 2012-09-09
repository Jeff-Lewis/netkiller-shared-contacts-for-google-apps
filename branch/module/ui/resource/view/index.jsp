<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tld/security-taglib.tld" prefix="sec"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page isELIgnored="false"%>



<html>
<head>
<link type="text/css" href="/css/themes/redmond/jquery.ui.all.css"
	rel="stylesheet" />


<script type="text/javascript" src="/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/jqgrid/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="/js/prevent-duplicat-form.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.8.7.custom.min.js"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.core.js"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.widget.js"></script>
<script type="text/javascript" src="/js/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript"
	src="/js/ui/jquery-ui-timepicker-addon.js"></script>
<script type="text/javascript"
	src="/js/ui/i18n/jquery.ui.datepicker-en-GB.js"></script>
<script type="text/javascript" src="/js/util/autocompleteutil.js"></script>
<script src="/js/jqgrid/i18n/grid.locale-en.js" type="text/javascript"></script>
<script src="/js/jqgrid/jquery.jqGrid.min.js" type="text/javascript"></script>
<script src="/js/ui/i18n/jquery-ui-i18n.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/validations.js"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="/css/jqgrid/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="/css/themes/base/jquery.ui.datepicker.css" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>NSC</title>
<link href="/css/style.css" rel="stylesheet" type="text/css">
<link href="/css/form.css" rel="stylesheet" type="text/css" />
<link href="/css/ie-css3.htc" rel="stylesheet" type="text/x-component">
<script src="/js/custom-form-elements.js" type="text/javascript"></script>
<script src="/js/jquery.corner.js" type="text/javascript"></script>
<script src="/js/modernizr-2.5.3.js" type="text/javascript"></script>
<script src="/SpryAssets/SpryMenuBar.js" type="text/javascript"></script>
<link href="/SpryAssets/SpryMenuBarVertical.css" rel="stylesheet"
	type="text/css">
</head>


<style type="text/css">
.more {
	position: relative;
	z-index: 200;
}

#listOnMoreHover {
	position: absolute;
	visibility: hidden;
	z-index: 200;
}

ul#add {
	padding: 0px;
	margin: 0px;
	height: auto;
	margin-left: 0px;
}

ul#add li {
	list-style-type: none;
	padding: 0px;
	margin: 0px;
	line-height: 1.5;
	padding-left: 5px;
}
</style>
<body>
	<div id="main-contaner">
		<div class="header" style="width:100%">
			<div class="logo"></div>

			<!--  <div class="top_most_nav" style="margin-left:120px;">admin@mellong.com | Manage | Logout</div>  -->
			<div style="float: right; font-size: 13px;margin-left: 200px;">
				harish@nicefact.com | Manage |
				<c:if test="${isAdmin}">|
				Manage
					<!-- <a href="/basicconfiguration.do"> <fmt:message
							key="jsp.index.manage" /> </a> -->|
				</c:if>
				<a href="/logout.do" style="text-decoration: none ;font-family: verdana;font-size: 13px;"> Logout </a>
			</div>

		</div>

		<div id="middle-contaner" style="width: 100%">
			<div id="middle-contaner-block"
				style="width: 100%; border-radius: 0 0 0 0">
				<jsp:include page="${_ipContextView}"></jsp:include>

				<div class="clear"></div>
			</div>
			<div id="footer-contaner"></div>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
		<div id="bottom" style="clear: both; margin-top: 10px;">
			<table width="100%" border="0" class="gen">
				<tr>
					<td style="height: 5px;">&nbsp;</td>
				</tr>
				<tr>
					<td>
						<hr noshade="" size="1" width="100%"></td>
				</tr>
				<tr>
					<td valign="middle" align="center"
						style="font-family: tahoma; font-size: 12px;" colspan="2">
						<table width="100%">
							<tr>
								<td><a
									href="http://netkilleramerica.blogspot.kr/2012/03/top-10-reasons-to-use-netkiller-shared.html"
									target="_blank"><img src="/images/pure_google_security.png" />
								</a></td>
								<td style="vertical-align: bottom" align="right"><font
									style="color: #42426F;"> &copy; 2011 Netkiller | <a
										href="https://docs.google.com/a/netkiller.com/presentation/d/1_vJ5a7UmdEUdEPlR5Bmt_kgtTAJkVi-xYh76QQrZo0g/edit#slide=id.p"
										target="_blank" style="text-decoration: none; color: #42426F;">User
											guide</a> | <a href="http://www.netkiller.com/" target="_blank"
										style="text-decoration: none; color: #42426F;">About us</a> |
										<a href="http://www.netkiller.com/contact" target="_blank"
										style="text-decoration: none; color: #42426F;">Contact</a> | <a
										href="https://groups.google.com/a/netkiller.com/group/ims4vph/topics"
										target="_blank" style="text-decoration: none; color: #42426F;">Secured
											Forum</a> | <a href="http://code.google.com/p/vph-ims"
										target="_blank" style="text-decoration: none; color: #42426F;">Open
											Source</a> </font></td>
							</tr>
						</table></td>
				</tr>
			</table>
		</div>


	</div>

	<script type="text/javascript">
		jQuery('#hdr-popup').bind('click', function(e) {
			e.stopPropagation();
		});
		jQuery(document).bind('click', function() {
			jQuery('#hdr-popup').hide();
			jQuery('#mysite-dropdown').removeClass('edu-username-red-arrow');
			jQuery('#mysite-dropdown').addClass('edu-username-white-arrow');
		});
		var MenuBar1 = new Spry.Widget.MenuBar("MenuBar1", {
			imgRight : "SpryAssets/SpryMenuBarRightHover.gif"
		});
	</script>
</body>
</html>

