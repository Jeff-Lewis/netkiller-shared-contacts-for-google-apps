<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c"%>

<html>
<title>Error: Not an admin</title>
<p>
You are not allowed to access this resource, as you are not an admin user.<br/>
Please <a href='<c:out value="${logoutPage}"/>'>click here</a> to logout and login as an administrator.
</p>
</html>