<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/soggetto/misuraPersonale.htm" />
		<p class="titolo_page">
			<spring:message code="soggetto.misurapersonale" />
		</p>
		<form:form method="post" action="${azione}" commandName="soggettoForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<form:hidden path="idSoggetto" />
				<form:hidden path="idVersione" />
				<form:hidden path="tipoSoggetto" />
				<form:hidden path="misura.idMisureCaricate" />
				<form:hidden path="idAtto" />
				<form:hidden path="misura.idVecchiePrescrizioni" />


				<tr>
					<td class="td_titolo" colspan="4"><c:out value="${soggettoForm.descrizioneSoggetto}" /></td>
				</tr>
			</table>
			<br />
			<br />
			<table class="tb_form" border="0">
				<tr>
					<td><form:label path="tipologiaPrevenzione.id">
							<spring:message code="procedimento.tipologiaPrevenzione" />
						</form:label></td>
					<td><c:out value="${soggettoForm.tipologiaPrevenzione.descrizione}" /></td>
					<td><form:label path="normativa.id">
							<spring:message code="procedimento.normativa" />
						</form:label></td>
					<td><c:out value="${soggettoForm.normativa.descrizione}" /></td>
				</tr>
				<tr>
					<td><form:label path="misura.tipoMisura">
							<spring:message code="soggetto.misurapersonale" />
						</form:label></td>
					<td><c:out value="${soggettoForm.misura.tipoMisura.descrizione}" /></td>
					<td><form:label path="misura.normativaDiRiferimento">
							<spring:message code="procedimento.normativa" />
						</form:label></td>
					<td><c:out value="${soggettoForm.misura.normativaDiRiferimento.descrizione}" /></td>
				</tr>
				<tr>
					<td><form:label path="misura.localita">
							<spring:message code="soggetto.misurapersonale.localita" />
						</form:label></td>
					<td colspan="3"><form:textarea readonly="true" path="misura.localita" rows="2" cssStyle="width:98%" /></td>
				</tr>
				<tr>
					<td><form:label path="misura.anni">
							<spring:message code="soggetto.misurapersonale.durata" />
						</form:label></td>
					<td colspan="3"><form:input path="misura.anni" maxlength="1" size="2" readonly="true" />&nbsp;&nbsp; <form:input path="misura.mesi" maxlength="2" size="4" readonly="true" />&nbsp;&nbsp; <form:input path="misura.giorni" maxlength="2" size="4" readonly="true" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.prescrizioni" /></td>
				</tr>


				<c:forEach var="prescrizione" items="${listaPrescrizioni}">
					<tr>
						<td colspan="4"><form:checkbox path="misura.idSelezionati" value="${prescrizione.id}" label="${prescrizione.descrizione}" disabled="true" /></td>
					</tr>
				</c:forEach>
				<tr>
					<td><form:label path="misura.ulterioriPrescrizioni">
							<spring:message code="soggetto.misurapersonale.ulterioriprescrizioni" />
						</form:label></td>
					<td colspan="3"><form:textarea path="misura.ulterioriPrescrizioni" id="note" readonly="true" rows="2" cssStyle="width:98%" /></td>
				</tr>

				<logic:present name="listaVecchiePrescrizioni">
					<c:forEach var="vecchiePrescrizioni" items="${listaVecchiePrescrizioni}">
						<tr>
							<td colspan="4"><form:checkbox path="misura.idVecchiePrescrizioni" value="${vecchiePrescrizioni.id}" label="${vecchiePrescrizioni.descrizione}" checked="checked" disabled="true" /></td>
						</tr>
						<c:if test="${(vecchiePrescrizioni.id == '13')}">
							<tr>
								<td><form:label path="misura.ulterioriPrescrizioniVecchie">
										<spring:message code="soggetto.misurapersonale.ulterioriprescrizioni" />
									</form:label></td>
								<td colspan="3"><form:textarea path="misura.ulterioriPrescrizioniVecchie" id="note" readonly="true" rows="2" cssStyle="width:98%" /></td>
							</tr>
						</c:if>
					</c:forEach>
				</logic:present>

				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.cauzione" /></td>
				</tr>
				<tr>
					<td><form:label path="misura.cauzione">
							<spring:message code="soggetto.misurapersonale.cauzione" />
						</form:label></td>
					<td colspan="3"><form:radiobutton path="misura.cauzione" value="S" disabled="true" /> <form:label path="misura.cauzione">
							<spring:message code="si" />
						</form:label> <form:radiobutton path="misura.cauzione" value="N" disabled="true" /> <form:label path="misura.cauzione">
							<spring:message code="no" />
						</form:label>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.articolo67" /></td>
				</tr>
				<tr>
					<td><form:label path="misura.art67">
							<spring:message code="soggetto.misurapersonale.applicazioneart67" />
						</form:label></td>
					<td colspan="3"><form:radiobutton path="misura.art67" value="S" disabled="true" /> <form:label path="misura.art67">
							<spring:message code="si" />
						</form:label> <form:radiobutton path="misura.art67" value="N" disabled="true" /> <form:label path="misura.art67">
							<spring:message code="no" />
						</form:label></td>
				</tr>
			</table>
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="button" value="Indietro" onClick="history.back()"></td>
				</tr>
			</table>
		</form:form>

		<script type="text/javascript">
				$(document).ready(function() {
					abilita();
				});
				function abilitaUlterioriPrescrizioni(campo) {
			
					if (campo.value == "32") {
						var id = document.getElementById("note");
						if (campo.checked) {
							id.disabled = false;
						} else {
							id.value = "";
							id.disabled = true;
						}
			
					}
				}
			
				function abilita() {
					//var id = $("#id_prescrizioni :checked");
					var id = document.getElementsByName("misura.idSelezionati");
					for (i = 0; i < id.length; i++) {
						if (id[i].checked)
							abilitaUlterioriPrescrizioni(id[i]);
					}
			
				}
			</script>

	</tiles:putAttribute>
</tiles:insertDefinition>
