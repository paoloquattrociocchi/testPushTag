<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>
<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%
	AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/giudiceDelegato/associa.htm" />
		<p class="titolo_page">
			<spring:message code="magistrato.titolo.giudiceDelegato" />
		</p>
		<form:form method="post" action="${azione}" commandName="procedimentoForm">

			<div>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			</div>
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="magistrato.titolo.giudiceDelegato" /></td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="magistratiAssociati" name="${magistratiAssociati}" pagesize="10" requestURI="${azione}">
				<display:column property="generalita" title="Cognome Nome" sortable="false" />
				<display:column property="dataAssegnazioneFascicolo" title="Data assegnazione fascicolo" format="{0,date,dd/MM/yyyy}" sortable="false" />
				<display:column property="dataFineAssegnazione" title="Data fine assegnazione" format="{0,date,dd/MM/yyyy}" sortable="false" />
				<display:column property="tipoSostituzione" title="Tipo sostituzione" sortable="false" />
				<display:column property="ruolo.descrizione" title="Ruolo" sortable="true" />
			</display:table>

			<br>
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="magistrato.titolo.ricerca" /></td>
				</tr>
				<tr>
					<td><form:label path="magistratoForm.cognome">
							<spring:message code="cognome" />
						</form:label></td>
					<td><form:input path="magistratoForm.cognome" maxlength="30" /> <form:errors path="magistratoForm.cognome" class="testoRosso" /></td>
					<td><form:label path="magistratoForm.nome">
							<spring:message code="nome" />
						</form:label></td>
					<td><form:input path="magistratoForm.nome" maxlength="30" /> <form:errors path="magistratoForm.nome" class="testoRosso" /></td>
				</tr>
			</table>

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Ricerca" name="Ricerca"></td>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
				</tr>
			</table>

			<logic:present name="risultati">
				<display:table class="tb_lista" uid="risultati" name="${risultati}" pagesize="10" requestURI="${azione}">
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:radiobutton path="magistratoForm.id" value="${risultati.id}" />
					</display:column>
					<display:column property="cognome" title="Cognome" sortable="false" />
					<display:column property="nome" title="Nome" sortable="false" />
				</display:table>
			</logic:present>
			<br>
			<c:if test="${atto==null}">
				<logic:notEmpty name="risultati">
						<table class="tb_form">
							<tr>
								<td><form:label path="magistratoForm.tipoSostituzione">
										<spring:message code="magistrato.tipoSostituzione" />
									</form:label></td>
								<td><form:select path="magistratoForm.tipoSostituzione" size="1" onchange="submit()">
										<form:option value="" label="--- Seleziona Tipo di Sostituzione ---" />
										<form:options items="${tipiSostituzioneMagistrato}" itemValue="id" itemLabel="descrizione" />
									</form:select></td>
								
							</tr>
						</table>	
				</logic:notEmpty>
			</c:if>
			<br>
			<table class="tb_bottoni" align="center">
				<tr>
					<logic:notEmpty name="risultati">
						<td class="td_tasto"><input type="submit" value="Associa" name="Associa"></td>
					</logic:notEmpty>
				</tr>
			</table>
		</form:form>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
		<smp:back buttonId="backButton"></smp:back>

		<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataAssegnazioneFascicolo",true);
					prendiData("#dataFineAssegnazione",false);
				});
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
