<%@page import="com.netkiller.util.CommonUtil"%>
<%@page import="java.util.Map"%>

<%
	Map<String, String> result = (Map<String, String>)request.getAttribute("result");
	String count = "0";
	String endDate =  CommonUtil.getNotNullString((String)result.get("endDate"));
	
	boolean isPurchased = false;
	if(result != null){
		count = CommonUtil.getNotNullString((String)result.get("total"));
		String perchaseValue = CommonUtil.getNotNullString((String)result.get("isPurchased"));
		 
		isPurchased = Boolean.parseBoolean(perchaseValue);
	}
%>
<div style="float:left;font-family:Arial;font-size:12px">
<a href="http://www.netkiller.com/pure-google-apps" target="_blank"><img src="/img/securenew.png"/></a>
</div>
<%if(isPurchased) {%>
<div style="float:right;font-family:Arial;font-size:12px;padding: 5px 1px 2px 3px;">
	Thanks for using the Paid version (valid until <%=endDate%>). <font color="red"><%=count %></font> of 30,000 shared-contacts enabled. 
</div>
<%} else {%>
<div style="float:right;text-align:right;font-family:Arial;font-size:12px;padding: 5px 1px 2px 3px;">
	You have <font color="red"><%=count %></font> shared Contacts (Free up to 50 Shared Contacts).<br/>Paid version supports up to 30,000 Contacts. 
	<a href="/sharedcontacts/buy.jsp"><font color="red">Buy it now</font></a>
</div>
<%} %>
<div style="float:right;font-family:Arial;font-size:13px">
<font style="color:#42426F;">
 &copy; 2011 Netkiller
 | 
<a href="http://www.netkiller.com/shared-contacts/guide" target="_blank" style="text-decoration:none;color:#42426F;">User guide</a>
 | 
<a href="http://www.netkiller.com/" target="_blank" style="text-decoration:none;color:#42426F;">About us</a>
 | 
<a href="http://www.netkiller.com/contact" target="_blank" style="text-decoration:none;color:#42426F;">Contact</a>
 | 
<a href="http://goo.gl/mod/uip4" target="_blank" style="text-decoration:none;color:#42426F;">Tell us what you think</a>
</font>
</div>