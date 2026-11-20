<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/collegio/modifica.htm" />
		<form:form method="post" action="${azione}" commandName="magistratoForm">
			<p class="titolo_page">
				<spring:message code="magistrato.modifica.titolo" />
			</p>

			<div>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			</div>
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="magistrato.modifica.titolo.magistratoScelto" /></td>
				</tr>
				<tr>
					<td><form:label path="cognome">
							<spring:message code="cognome" />
						</form:label></td>
					<td><c:out value="${magistratoForm.cognome}" /></td>
					<form:hidden path="cognome" />
					<td><form:label path="nome">
							<spring:message code="nome" />
						</form:label></td>
					<td><c:out value="${magistratoForm.nome}" /></td>
					<form:hidden path="nome" />
				</tr>
				<tr>
					<td><form:label path="ruolo.id">
							<spring:message code="magistrato.ruolo" />
						</form:label></td>
					<td><form:select path="ruolo.id" size="1">
							<form:option value="-1" label="------ Seleziona Ruolo Magistrato--------" />
							<form:options items="${tipoRuoliMagistrati}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>
				<tr>
					<td><form:label path="relatore">
							<spring:message code="magistrato.relatore" />
						</form:label></td>
					<td colspan="3">
						<table>
							<tr>
								<td style="border-bottom: none;"><form:radiobutton path="relatore" value="S" /></td>
								<td style="border-bottom: none;">Si</td>
							</tr>
							<tr>
								<td style="border-bottom: none;"><form:radiobutton path="relatore" value="N" /></td>
								<td style="border-bottom: none;">No</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td><form:label path="dataAssegnazioneFascicolo" cssClass="required">
							<spring:message code="magistrato.dataAssegnazioneFascicolo" />
						</form:label></td>
					<td><form:input path="dataAssegnazioneFascicolo" id="dataAssegnazioneFascicolo" maxlength="10" size="12" /> <form:errors path="dataAssegnazioneFascicolo" class="testoRosso" /></td>
					<td><form:label path="dataFineAssegnazione">
							<spring:message code="magistrato.dataFineAssegnazione" />
						</form:label></td>
					<td><form:input path="dataFineAssegnazione" id="dataFineAssegnazione" maxlength="10" size="12" /> <form:errors path="dataFineAssegnazione" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="tipoSostituzione">
							<spring:message code="magistrato.tipoSostituzione" />
						</form:label></td>
					<td><form:select path="tipoSostituzione" size="1" onchange="submit()">
							<form:option value="" label="--- Seleziona Tipo di Sostituzione ---" />
							<form:options items="${tipiSostituzioneMagistrato}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
					<c:if test="${magistratoForm.tipoSostituzione != null}">
						<c:if test="${magistratoForm.tipoSostituzione == 'Altro' || magistratoForm.tipoSostituzione == 'ALTRO'}">
							<td><form:label path="altroMotivo">
									<spring:message code="magistrato.altroMotivo" />
								</form:label></td>
							<td><form:input path="altroMotivo" /></td>
						</c:if>
						<c:if test="${magistratoForm.tipoSostituzione != 'Altro' && magistratoForm.tipoSostituzione != 'ALTRO'}">
							<td colspan="2"></td>
						</c:if>
					</c:if>
					<c:if test="${magistratoForm.tipoSostituzione == null}">
						<td colspan="2"></td>
					</c:if>
				</tr>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Conferma" name="Conferma"></td>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
				</tr>
			</table>

			<!-- questi hidden servono per passare sia l'id,sia ruolo ante modifica, perchè il ruolo è
                     parte della chiave primaria, sia il relatore perchè serve nel controllo che non deve esistere un altro 
                     relatore nell'elenco già appartente all'atto -->

			<form:hidden path="id" />
			<input id="ruoloAnteModifica" name="ruoloAnteModifica" type="hidden" value="${magistratoForm.ruolo.id}" />
			<input id="relatoreAnteModifica" name="relatoreAnteModifica" type="hidden" value="${magistratoForm.relatore}" />

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
