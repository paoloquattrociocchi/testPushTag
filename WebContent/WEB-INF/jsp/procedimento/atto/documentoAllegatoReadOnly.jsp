<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<c:set var="STATOATTO_REGISTRATO" value="<%=Constants.STATOATTO_REGISTRATO%>"/>
<c:set var="STATOATTO_INOLTRATO" value="<%=Constants.STATOATTO_INOLTRATO%>"/>
<c:set var="STATOATTO_ACQUISITO" value="<%=Constants.STATOATTO_ACQUISITO%>"/>

<table class="tb_form">
	<tr>
		<td class="td_titolo" colspan="4"><spring:message code="atto.inseriscidocumento" /></td>
	</tr>
	
	<c:if test="${attoForm.allegato.fileName!=null && attoForm.allegato.fileName!=''}">
		<tr>
			<td>
				<form:hidden path="allegato.fileName" /> 
				<form:hidden path="allegato.contentType" />		
				<span><c:out value="${attoForm.allegato.fileName}"></c:out></span> &nbsp;&nbsp;  
			</td>
			<td>
				<img src="${pageContext.request.contextPath}/images/download.gif" style="cursor: pointer; border: none" title="Visualizza documento" onclick="gestioneDocumento('${pageContext.request.contextPath}','downloadDocumentale');"></img>&nbsp;&nbsp;
			</td>
			<td colspan="2" style="width:80%">
				<input id="id_buttonGestioneDocumento" type="submit" name="" value="" style="visibility: hidden" class="nowait" ></td>
			</td>
		</tr>
	</c:if>
</table>
