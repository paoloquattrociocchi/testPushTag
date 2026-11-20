<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/apri/ricercaPerCriteri.htm" />
		<form:form method="post" action="${azione}" commandName="procedimento">
			<p class="titolo_page">
				<spring:message code="procedimento.ricercaCriteri" />
			</p>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="6"><spring:message code="procedimento.titoloDivOpposizione" /></td>
				</tr>
				<tr>
					<td style="border-bottom: none;">&nbsp;</td>
				</tr>
				<tr>
					<td><form:label path="anno">
							<spring:message code="procedimento.anno" />
						</form:label></td>
					<td><form:input path="anno" id="anno" maxlength="4" size="5" /> <form:errors path="anno" class="testoRosso" /></td>
					<td><form:label path="numeroMin">
							<spring:message code="procedimento.numeroMin" />
						</form:label> <form:input path="numeroMin" maxlength="12" /> <form:errors path="numeroMin" class="testoRosso" /></td>
					<td><form:label path="numeroMax">
							<spring:message code="procedimento.numeroMax" />
						</form:label> <form:input path="numeroMax" maxlength="12" /> <form:errors path="numeroMax" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="dataIscrizioneMin">
							<spring:message code="procedimento.dataIscrizione" />
						</form:label></td>
					<td><form:label path="dataIscrizioneMin">
							<spring:message code="procedimento.dataIscrizioneMin" />
						</form:label> <form:input path="dataIscrizioneMin" id="dataIscrizioneMin" maxlength="10" size="12"/> <form:errors path="dataIscrizioneMin" class="testoRosso" /></td>
					<td colspan="2"><form:label path="dataIscrizioneMax">
							<spring:message code="procedimento.dataIscrizioneMax" />
						</form:label> <form:input path="dataIscrizioneMax" id="dataIscrizioneMax" maxlength="10" size="12" /> <form:errors path="dataIscrizioneMax" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="statoProcedimento.id">
							<spring:message code="procedimento.statoProcedimento" />
						</form:label></td>
					<td colspan="3"><form:select path="statoProcedimento.id" size="1">
							<form:option value="-1" label="--- Seleziona Stato ---" />
							<form:options items="${statiProcedimento}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>
				<tr>
					<td><form:label path="cognomeSoggetto">
							<spring:message code="procedimento.soggetto" />
						</form:label></td>
					<td><form:label path="cognomeSoggetto">
							<spring:message code="procedimento.cognomeSoggetto" />
						</form:label> <form:input path="cognomeSoggetto" /> <form:errors path="cognomeSoggetto" class="testoRosso" /></td>
					<td colspan="2"><form:label path="nomeSoggetto">
							<spring:message code="procedimento.nomeSoggetto" />
						</form:label> <form:input path="nomeSoggetto" /> <form:errors path="nomeSoggetto" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="annoDecretoDecisorio">
							<spring:message code="procedimento.decretoDecisorio" />
						</form:label></td>
					<td><form:label path="annoDecretoDecisorio">
							<spring:message code="procedimento.anno" />
						</form:label> <form:input path="annoDecretoDecisorio" /> <form:errors path="annoDecretoDecisorio" class="testoRosso" /></td>
					<td colspan="2"><form:label path="numeroDecretoDecisorio">
							<spring:message code="procedimento.numero" />
						</form:label> <form:input path="numeroDecretoDecisorio" /> <form:errors path="numeroDecretoDecisorio" class="testoRosso" /> <form:hidden path="pmAssegnatario.id" value="-1" /> <form:hidden path="tipoProcedente.id" value="-1" /> <form:hidden
							path="procedente.id" value="-1" /></td>
				</tr>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" name="Ricerca" title="Ricerca procedimento" value="Ricerca" /></td>

				</tr>
			</table>
			<br />
			<br />

			<logic:present name="risultati">

				<display:table id="row" name="${risultati}" class="tb_lista" uid="risultati" pagesize="10" requestURI="${azione}">
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:radiobutton path="idProcedimento" value="${risultati.idProcedimento}" />
					</display:column>
					<display:column property="annoNumeroProcedimento" sortable="false" title="Procedimento" />
					<display:column property="dataApertura" sortable="false" title="Data Iscrizione" format="{0,date,dd/MM/yyyy}" />
					<display:column property="statoProcedimento.descrizione" sortable="false" title="Stato" />
				</display:table>
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" name="Dettaglio" title="Dettaglio" value="Dettaglio" /></td>
						<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/apri/riesame/visualizzaRicercaPerCriteri.htm')"></td>
					</tr>
				</table>
				<br />
				<br />



			</logic:present>
		</form:form>

		<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataIscrizioneMin",false);
					prendiData("#dataIscrizioneMax",false);
				});
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>