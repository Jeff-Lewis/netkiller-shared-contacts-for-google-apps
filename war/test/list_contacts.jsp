<%@page contentType="text/plain; charset=UTF-8"%>
<%@page import="java.util.logging.Logger" %>

<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>

<%! 
protected final Logger logger = Logger.getLogger(getClass().getName());
%>

<%
logger.info("- list_response.jsp -");
%>

{
	"page":"1",
	"total":2,
	"records":"13",
	"rows":[
			{"id":"13", "cell":["13", "False", "Json","ABCccc","json@abc.com","429-394-2378","2033 Stc st. San Jose",null]},
			{"id":"12","cell":["12", "False", "Peter","Apple","bird@apple.com","394-293-4899","394 gt st. Los Altos",null]},
			{"id":"11","cell":["11", "False", "Scott","Google","scott@google.com","212-394-3948","399 rose st. Santa Losa",null]},
			{"id":"10","cell":["10", "False", "Daiv","Ericson","daiv@ericson.com","344-394-0900","21 bird st. Santa Cruze",null]},
			{"id":"9","cell":["9", "False", "David","Samsung","david@samsung.com","299-304-0098","409 boat st. San Francisco",null]},
			{"id":"8","cell":["8", "False", "Chali","Motorola","ch@motorola.com","430-8-309-0493","900 Market st. San Francisco",null]},
			{"id":"7","cell":["7", "False", "Kei","LG","kei@lg.com","200-203-0987","678 hamilton st. San Jose",null]},
			{"id":"6","cell":["6", "False", "Lio","HTC","lio@htc.com","210-039-0909","890 plant st. Los Gatos",""]},
			{"id":"5","cell":["5", "False", "Charls","Hynix","charls@hynix.com","987-039-3039","900 risa st. San Jose","no tax at all"]},
			{"id":"4","cell":["4", "False", "Lina","Luscent","lina@luscent.com","967-203-3099","354 Gateway San Jose","no tax"]}
		],
	"userdata":{"amount":3220,"tax":342,"total":3564,"name":"Totals:"}
}