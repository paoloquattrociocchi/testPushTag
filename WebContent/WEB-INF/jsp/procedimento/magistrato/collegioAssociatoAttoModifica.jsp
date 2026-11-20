<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/collegio/associati/modifica.htm" />
		<form:form method="post" action="${azione}" commandName="magistratoForm">
			<p class="titolo_page">
				<spring:message code="magistrato.modifica.titolo" />
			</p>

			<div>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
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
					<td><form:label path="ruolo">
							<spring:message code="magistrato.ruolo" />
						</form:label></td>
					<td colspan="3">					
						<form:select path="ruolo.id" size="1">
							<form:option value="-1" label="------ Seleziona Ruolo Magistrato--------" />
							<form:options items="${tipoRuoliMagistrati}" itemValue="id" itemLabel="descrizione" />
						</form:select>				
					</td>
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
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Conferma" name="Conferma"></td>
					<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/atto/colleggio/elencoCollegioAssociatoAtto.htm')"></td>
				</tr>
			</table>

			<!-- questi hidden servono per passare sia l'id,sia ruolo ante modifica, perchè il ruolo è
                     parte della chiave primaria, sia il relatore perchè serve nel controllo che non deve esistere un altro 
                     relatore nell'elenco già appartente all'atto -->

			<form:hidden path="id" />
			<input id="ruoloAnteModifica" name="ruoloAnteModifica" type="hidden" value="${magistratoForm.ruolo.id}" />
			<input id="relatoreAnteModifica" name="relatoreAnteModifica" type="hidden" value="${magistratoForm.relatore}" />

		</form:form>

	</tiles:putAttribute>
</tiles:insertDefinition>
