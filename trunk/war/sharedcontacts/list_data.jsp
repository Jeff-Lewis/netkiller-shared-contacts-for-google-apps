<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="com.netkiller.util.CommonWebUtil"%>
<%@ page import="com.netkiller.util.SharedContactsUtil"%>
<%@ page import="com.netkiller.search.GridRequest"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="com.google.gdata.data.contacts.ContactEntry"%>


<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>


<%! Logger logger = Logger.getLogger(getClass().getName()); %>

<%	request.setCharacterEncoding("UTF-8"); %>

<%  logger.info("-- list_data.jsp --"); %>


<%
	Map result = (Map)request.getAttribute("result");
	List<ContactEntry> entries = null;
	JSONArray jsonArray = null;
	JSONObject jsonObj = new JSONObject();
	if(result != null){
		int records = Integer.parseInt((String)result.get("records"));
		int startIndex = Integer.parseInt((String)result.get("startIndex"));
		int rows = Integer.parseInt((String)result.get("rows"));
		String sidx = (String)result.get("sidx");
		String sord = (String)result.get("sord");
		GridRequest gridRequest = (GridRequest)result.get("gridRequest");
		
		logger.info("records: " + records);
		logger.info("startIndex: " + startIndex);
		logger.info("rows: " + rows);
		
		entries = (List<ContactEntry>)result.get("entries");
		
		logger.info("AAA1==> entries.size(): "  + entries.size());
		
		SharedContactsUtil util = SharedContactsUtil.getInstance();
		
		jsonArray = util.convertContactsToJsonArrayForList(
						entries, 
						records, 
						startIndex, 
						(String)result.get("page"), 
						Integer.toString(rows), 
						sidx, 
						sord, gridRequest
					);
		logger.info("AAA2==> jsonArray.size(): "  + jsonArray.size());
		int totalRecords = jsonArray.size();
		int total_pages = (int)Math.ceil( jsonArray.size() / rows )+1;
		jsonArray = util.getListForPaging(jsonArray, startIndex, rows);	
		logger.info("AAA3===> jsonArray.size(): " + jsonArray.size());		
		jsonObj.put("rows", jsonArray);
		jsonObj.put("page", result.get("page"));
		jsonObj.put("total", total_pages);
		jsonObj.put("records", totalRecords);
		//jsonObj.put("url", "/sharedcontacts/main.do?cmd=list_data");
		
		//jsonObj.put("page", pageNum);
		logger.info(jsonObj.toString());
		//logger.info(jsonObj.);
		out.write(jsonObj.toString());
		//out.write(jsonObj);
		//jsonObj.write(out);
		out.flush();
	}
%>



