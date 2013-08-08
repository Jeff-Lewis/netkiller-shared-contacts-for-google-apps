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
<a href="http://netkilleramerica.blogspot.com/2012/03/top-10-reasons-to-use-netkiller-shared.html" target="_blank"><img src="/img/pure_google_security.png"/></a>
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
<a href="https://sites.google.com/a/netkiller.com/nsc/" target="_blank" style="text-decoration:none;color:#42426F;">User guide</a>
 | 
<a href="http://www.netkiller.com/" target="_blank" style="text-decoration:none;color:#42426F;">About us</a>
 | 
<a href="http://www.netkiller.com/contact" target="_blank" style="text-decoration:none;color:#42426F;">Contact</a>
 | 
<a href="https://groups.google.com/forum/?hl=en&fromgroups#!forum/netkillersc" target="_blank" style="text-decoration:none;color:#42426F;">Support</a>
 | 
<a href="http://code.google.com/p/netkiller-shared-contacts-for-google-apps/" target="_blank" style="text-decoration:none;color:#42426F;">Open Source</a>
 | 
<a href="https://www.google.com/enterprise/marketplace/viewListing?productListingId=2333+5834727858349999229" target="_blank" style="text-decoration:none;color:#42426F;">Review.</a>
</div>

<script type="text/javascript" src="//assets.zendesk.com/external/zenbox/v2.6/zenbox.js"></script>
<style type="text/css" media="screen, projection">
  @import url(//assets.zendesk.com/external/zenbox/v2.6/zenbox.css);
</style>
<script type="text/javascript">
  if (typeof(Zenbox) !== "undefined") {
    Zenbox.init({
      dropboxID:   "20206918",
      url:         "https://collavate.zendesk.com",
      tabTooltip:  "Support",
      tabImageURL: "https://assets.zendesk.com/external/zenbox/images/tab_support.png",
      tabColor: "rgb(0, 127, 236)",
      tabPosition: "Left"
    });
  }
</script>