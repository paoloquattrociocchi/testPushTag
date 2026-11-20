<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/salvaUdienzaDecretoSeqConv.htm" />
		<form:form id="id_form_udienza" method="post" action="${azione}" commandName="udienzaForm" enctype="multipart/form-data">
			<form:hidden path="codUdienza" />
			<form:hidden path="codAtto" />
			<form:hidden path="dataRegistrazione" />

			<p class="titolo_page">
				<spring:message code="atto.udienza" />
			</p>

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<br />
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="atto.udienza.dati" /></td>
				</tr>
				<tr>
					<c:if test="${udienzaForm.readOnly==false}">
						<td><form:label path="dataUdienza" cssClass="required">
								<spring:message code="atto.udienza.data" />
							</form:label></td>
						<td><form:input id="id_dataUdienza" path="dataUdienza" maxlength="10" /> <form:errors path="dataUdienza" class="testoRosso" /></td>
						<td><form:label path="oraInizioUdienza.id" cssClass="required">
								<spring:message code="atto.udienza.ora" />
							</form:label></td>
						<td><form:select path="oraInizioUdienza.id" size="1">
								<form:option value="-1" label="--hh--" />
								<form:options items="${ore}" itemValue="id" itemLabel="descrizione" />
							</form:select> : <form:select path="minutiInizioUdienza.id" size="1">
								<form:options items="${minuti}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:errors path="oraInizioUdienza.id" class="testoRosso" /></td>
					</c:if>
					<c:if test="${udienzaForm.readOnly==true}">
						<td><form:label path="dataUdienza">
								<spring:message code="atto.udienza.data" />
							</form:label></td>
						<td><fmt:formatDate value="${udienzaForm.dataUdienza}" type="date" pattern="dd/MM/yyyy" />
							<form:hidden path="dataUdienza" /></td>
						<td><form:label path="oraInizioUdienzaStr">
								<spring:message code="atto.udienza.ora" />
							</form:label></td>
						<td><c:out value="${udienzaForm.oraInizioUdienzaStr}" /></td>
					</c:if>
				</tr>
				<tr>
					<c:if test="${udienzaForm.readOnly==false}">
						<td><form:label path="indirizzoAulaUdienza" cssClass="required">
								<spring:message code="indirizzo" />
							</form:label></td>
						<td colspan="3"><form:input path="indirizzoAulaUdienza" maxlength="115" size="115" /> <form:errors path="indirizzoAulaUdienza" class="testoRosso" /></td>
					</c:if>
					<c:if test="${udienzaForm.readOnly==true}">
						<td><form:label path="indirizzoAulaUdienza">
								<spring:message code="indirizzo" />
							</form:label></td>
						<td colspan="3"><c:out value="${udienzaForm.indirizzoAulaUdienza}" /></td>
					</c:if>
				</tr>
				<tr>
					<c:if test="${udienzaForm.readOnly==false}">
						<td><form:label path="aulaUdienza" cssClass="required">
								<spring:message code="atto.udienza.aula" />
							</form:label></td>
						<td colspan="3"><form:input path="aulaUdienza" maxlength="30" size="30" /> <form:errors path="aulaUdienza" class="testoRosso" /></td>
					</c:if>
					<c:if test="${udienzaForm.readOnly==true}">
						<td><form:label path="aulaUdienza">
								<spring:message code="atto.udienza.aula" />
							</form:label></td>
						<td colspan="3"><c:out value="${udienzaForm.aulaUdienza}" /></td>
					</c:if>
				</tr>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<c:if test="${udienzaForm.readOnly==false}">
						<td class="td_tasto"><input type="submit" name="SalvaUdienzaSeqConv" title="Salva l'udienza" value="Salva"></td>
						<td class="td_tasto"><input type="submit" name="EliminaUdienzaSeqConv" title="Elimina l'udienza" value="Elimina"></td>
					</c:if>
					<c:if test="${udienzaForm.readOnly==true}">
						<td class="td_tasto"><input type="submit" title="Salva l'udienza" value="Salva" disabled="true"></td>
						<td class="td_tasto"><input type="submit" title="Elimina l'udienza" value="Elimina" disabled="true"></td>
					</c:if>
				</tr>
			</table>
		</form:form>

		<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#id_dataUdienza",false);
				});	
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>