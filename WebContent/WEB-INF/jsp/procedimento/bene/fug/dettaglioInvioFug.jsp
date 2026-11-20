<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/fug/dettaglioInvioFug.htm" />
		<p class="titolo_page">
			<spring:message code="titolo.dettaglio.invio" />
		</p>
		<form:form method="post" action="${azione}" commandName="fugModelloForm">
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="1">
					<spring:message code="fug.modello.label" /> <c:out value="${fugModelloForm.descModello}" /></td>
					<td class="td_titolo" colspan="1">
					<spring:message code="fug.stato.label" /> <c:out value="${fugModelloForm.statoInvio}" /></td>
					<td class="td_titolo" colspan="1">
					<spring:message code="fug.protocollo.label" /> <c:out value="${fugModelloForm.descProtocollo}" /></td>
				</tr>
			</table>
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene" /> <c:out value="${fugModelloForm.idBene}" /></td>
				</tr>
			</table>
			<table class="tb_form" border=0>
				<tr>
					<td><form:label path="tipologia.descrizione">
							<spring:message code="bene.tipologia" />
						</form:label></td>
					<td colspan="3"><c:out value="${fugModelloForm.tipologia.descrizione}" /></td>
				</tr>
				<tr>
					<td width="15%"><form:label path="categoria.descrizione">
							<spring:message code="bene.categoria" />
						</form:label></td>
					<td width="35%"><c:out value="${fugModelloForm.categoria.descrizione}" /></td>
					<td><form:label path="sottocategoria.descrizione">
							<spring:message code="bene.sottocategoria" />
						</form:label></td>
					<td width="35%"><c:out value="${fugModelloForm.sottocategoria.descrizione}" /></td>
				</tr>
		
	</table>
			<br />
			<c:if test="${(fugModelloForm.richiestaXML != null) || (fugModelloForm.esitoXML != null)}" >
				<table class="tb_form" border=0>
					<tr>
						<td class="td_titolo"><spring:message code="FUG.dettaglioXML.richiesta" /></td>
						<c:if test="${fugModelloForm.esitoXML != null}" >
							<td class="td_titolo"><spring:message code="FUG.dettaglioXML.risposta" /></td>
						</c:if>		        	   
					</tr>
					<tr>
						<td width="50%"><textarea rows="10" cols="50" readonly>${fugModelloForm.richiestaXML}</textarea></td>
						<c:if test="${fugModelloForm.esitoXML != null}" >
							<td width="50%"><textarea rows="10" cols="50" readonly>${fugModelloForm.esitoXML}</textarea></td>
						</c:if>		        	   
					</tr>
				</table>
				<br />
			</c:if>		        	   
			<c:if test="${magistratoForm.tipoMagistrato!=null}" >
				<form:input path="dataFineAssegnazione" maxlength="10" id="dataFineAssegnazione" readonly="true" />
			</c:if>


			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro"></td>
				</tr>
			</table>
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>
