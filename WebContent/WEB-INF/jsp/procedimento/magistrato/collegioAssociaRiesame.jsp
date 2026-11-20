<%@page import="it.giustizia.smp.utility.Constants"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/collegio/associaRiesame.htm" />
		<p class="titolo_page">
			<spring:message code="magistrato.titolo.associati" />
		</p>
		<form:form method="post" action="${azione}" commandName="procedimentoForm">

			<div>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			</div>
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="magistrato.lista.associati" /></td>
				</tr>
			</table>

			<display:table class="tb_lista" uid="magistratiAssociati" name="${magistratiAssociati}" pagesize="10" requestURI="${azione}">
				<display:column property="generalita" title="Cognome Nome" sortable="false" />
				<display:column property="dataAssegnazioneFascicolo" title="Data assegnazione fascicolo" format="{0,date,dd/MM/yyyy}" sortable="false" />
				<display:column property="dataFineAssegnazione" title="Data fine assegnazione" format="{0,date,dd/MM/yyyy}" sortable="false" />
				<display:column property="tipoSostituzione" title="Tipo sostituzione" sortable="false" />
				<display:column property="ruolo" title="Ruolo" sortable="true" />
				<display:column property="descrizioneCortaRelatore" title="Relatore" sortable="true" />
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
					<td class="td_tasto"><input type="button" value="Indietro" onClick="history.back()"></td>
				</tr>
			</table>

			<logic:present name="risultati">

				<display:table class="tb_lista" uid="risultati" name="${risultati}" pagesize="10" requestURI="${azione}">
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:radiobutton path="magistratoForm.id" value="${risultati.id}" />
					</display:column>
					<display:column property="cognome" title="Cognome" sortable="false" style="width: 40%"/>
					<display:column property="nome" title="Nome" sortable="false" />
				</display:table>
			</logic:present>
			<logic:notEmpty name="risultati">
				<table class="tb_form" align="center">

					<tr>
						<td><form:label path="magistratoForm.dataAssegnazioneFascicolo" cssClass="required">
								<spring:message code="magistrato.dataAssegnazioneFascicolo" />
							</form:label>&nbsp;&nbsp;&nbsp;
							<form:input path="magistratoForm.dataAssegnazioneFascicolo" id="dataAssegnazioneFascicolo" maxlength="10" size="12" /> <form:errors path="magistratoForm.dataAssegnazioneFascicolo" class="testoRosso" /> <form:hidden path="magistratoForm.ruolo.id" value="<%=Constants.ID_TIPO_RUOLO_PRES %>" /> <form:hidden path="magistratoForm.relatore" value="S" /></td>
					</tr>
				</table>
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" value="Associa" name="Associa"></td>
					</tr>
				</table>
			</logic:notEmpty>
		</form:form>

		<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataAssegnazioneFascicolo",true);
					prendiData("#dataFineAssegnazione",false);
				});
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
