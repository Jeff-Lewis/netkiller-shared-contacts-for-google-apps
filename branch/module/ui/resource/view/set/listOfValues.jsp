<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tld/listofvalues-taglib.tld" prefix="lov"%>
<lov:listofvalues name="${name}" valueSetName="${setName}"
	selectedValueId="" />
