<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lista Monitoraggio</title>
</head>


<link href="${pageContext.request.contextPath}/css/generale.css" rel="stylesheet" type="text/css" />

<body>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<p>LISTA</p>
		<spring:url var="azione" value="/monitoraggio/selezioneMonitoraggio.htm" />
	<form:form method="post" action="${azione}" commandName="monitoraggio">
		<form:input path="id" />
	</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>