<%@page import="com.netkiller.vo.Customer"%>
<%@ page language="java" contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.logging.Logger"%>
<%@ page import="com.netkiller.util.CommonWebUtil"%>
<%@ page import="com.netkiller.util.SharedContactsUtil"%>
<%@ page import="org.json.simple.JSONArray"%>
<%@ page import="org.json.simple.JSONObject"%>


<%
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Pragma","no-cache"); 
response.setDateHeader ("Expires", -1); 
%>


<%! Logger logger = Logger.getLogger(getClass().getName()); %>

<%	request.setCharacterEncoding("UTF-8"); %>

<%  logger.info("-- customers-list-data.jsp --"); %>


<%
	Map result = (Map)request.getAttribute("result");
	List<Customer> entries = null;
	JSONArray jsonArray = null;
	JSONObject jsonObj = new JSONObject();
	if(result != null){
		int records = Integer.parseInt((String)result.get("records"));
		int startIndex = Integer.parseInt((String)result.get("startIndex"));
		int rows = Integer.parseInt((String)result.get("rows"));
		String sidx = (String)result.get("sidx");
		String sord = (String)result.get("sord");
		
		logger.info("records: " + records);
		logger.info("startIndex: " + startIndex);
		logger.info("rows: " + rows);
		
		System.out.println("records: " + records);
		System.out.println("startIndex: " + startIndex);
		System.out.println("rows: " + rows);
		
		entries = (List<Customer>)result.get("entries");
		
		jsonArray = SharedContactsUtil.getInstance().convertCustomersToJsonArrayForList(
						entries, 
						records, 
						startIndex, 
						(String)result.get("page"), 
						Integer.toString(rows), 
						sidx, 
						sord, null
					);
		
		jsonArray = SharedContactsUtil.getInstance().getListForPaging(jsonArray, startIndex, rows);	
		
		jsonObj.put("rows", jsonArray);
		jsonObj.put("page", result.get("page"));
		jsonObj.put("total", result.get("total"));
		jsonObj.put("records", result.get("records"));
		
		logger.info(jsonObj.toString());
		out.write(jsonObj.toString());
		out.flush();
	}
%>



