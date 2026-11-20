<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/inserimentoUdienza.htm" />
		<form:form id="id_form_udienza" method="post" action="${azione}" commandName="udienzaForm" enctype="multipart/form-data">
			<form:hidden path="codAtto" />
			<form:hidden path="codUdienza" />
			<form:hidden path="dataUdienza" />
			<form:hidden path="oraInizioUdienza.id" />
			<form:hidden path="minutiInizioUdienza.id" />
			<form:hidden path="indirizzoAulaUdienza" />
			<form:hidden path="aulaUdienza" />
			<form:hidden path="cognomeCancelliere" />
			<form:hidden path="nomeCancelliere" />


			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="atto.udienza" /></td>
				</tr>
				<tr>
					<td><form:label path="destinatario.id">
							<spring:message code="atto.destinazione" />
						</form:label></td>
					<td colspan="3"><form:select path="destinatario.id">
							<form:options items="${ufficiDestinatari}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>
				<tr>
					<td><form:label path="dataEmissione" cssClass="required">
							<spring:message code="atto.dataemissione" />
						</form:label></td>
					<td><form:input id="id_dataEmissione" path="dataEmissione" maxlength="10" /> <form:errors path="dataEmissione" class="testoRosso" /></td>
					<td><form:label path="dataDeposito" cssClass="required">
							<spring:message code="atto.datadeposito" />
						</form:label></td>
					<td><form:input id="id_dataDeposito" path="dataDeposito" maxlength="10" /> <form:errors path="dataDeposito" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="dataRegistrazione" cssClass="required">
							<spring:message code="atto.dataregistrazione" />
						</form:label></td>
					<td colspan="3"><form:input id="id_dataRegistrazione" path="dataRegistrazione" maxlength="10" readonly="true" /> <form:errors path="dataRegistrazione" class="testoRosso" /></td>
				</tr>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" name="Invia" title="Invia l'udienza corrente" value="Conferma e Invia"></td>
					<td class="td_tasto"><input type="submit" name="CaricaUdienza" value="Indietro" title="torna al form di inserimento"></td>
				</tr>
			</table>
		</form:form>

		<script type="text/javascript">
			
			$(document).ready(function() {
				prendiData("#id_dataEmissione",true);
				prendiData("#id_dataDeposito",true);
			});	
				
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>