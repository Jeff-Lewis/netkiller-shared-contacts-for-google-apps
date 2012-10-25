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
<a href="http://www.netkiller.com/shared-contacts/guide" target="_blank" style="text-decoration:none;color:#42426F;">User guide</a>
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
<!-- OggChat Tab Button : Contact Us for Help Customizing -->
<div id="oggchat"></div>



<div id="oggwindowholder"><span style="display:none"></span></div>
<script type="text/javascript">
var page ={/*Modify or uncomment the variables below to customize*/
'tab_align':'bottom',/*Fixed location of Tab Button, valid values are top, bottom, left, right*/
'tab_margin_right':'45px',/*When tab is top or bottom, the right margin*/
//'tab_margin_left':'45px',/*When tab is top or bottom, the left margin*/
//'tab_margin_top':'45px',/*When tab is left or right, the top margin*/
//'tab_margin_bottom':'45px',/*When tab is left or right, the bottom margin*/
'popup_margin_right':'40px',/*The right margin of popup chat window*/
'popup_margin_bottom':'30px',/*The top margin of popup chat window*/
//'popup_margin_top':'30px',/*The top margin of popup chat window*/
'tab_bg_color':'#0066ff',/*Tab Background Color*/
'tab_hover_color':'red',/*Tab Hover Color*/
'website':'NSC Support Chat',/*Your Domain Name*/
//'i' : 'custom',/*Uncomment this to use your custom uploaded image buttons for the tabs*/
'p':'0',/*Proactive Chat Timer in Seconds*/
'online_text':'Need Help? Click to Chat',/*Custom Text to display in tab when online*/
'offline_text': 'Offline - Leave a message',/*Custom Text to display in tab when offline*/
'font_family':'Verdana',/*When using text, font to use*/
'font_size':'10pt',/*When using custom text, font size*/
'font_color':'#FFFFFF', /*When using custom text, font color*/
'animate':'true',
'host':'oggchat3.icoa.com',
'cid':'c6a6ceed3a198068013a1d8c5a5e01c1',
'act':'c6a6ceed39e91be80139ff5f8f7a0577'
};
(function() {function oggchat(){
var base = (("https:" == document.location.protocol) ? "https://oggchat3.icoa.com" : "http://oggchat3.icoa.com");
var s = document.createElement('script');s.type = 'text/javascript';s.async = true;s.src = base+'/js/oggwindow.js';
var x = document.getElementsByTagName('script')[0];x.parentNode.insertBefore(s, x);};
if (window.attachEvent)window.attachEvent('onload', oggchat);else window.addEventListener('load', oggchat, false);
})();
</script>