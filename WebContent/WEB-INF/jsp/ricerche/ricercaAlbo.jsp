<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ page import="it.giustizia.smp.utility.Constants"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">

		<spring:url var="azione" value="/ricerche/albo/risultatiRicerca.htm" />

		<form:form method="post" action="${azione}" commandName="amministratoreForm">
			<p class="titolo_page">
				<spring:message code="albo.ricerca" />

			</p>


			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td><form:label path="cognome">
							<spring:message code="cognome" />
						</form:label></td>
					<td><form:input path="cognome" /></td>

					<td><form:label path="nome">
							<spring:message code="nome" />
						</form:label></td>
					<td><form:input path="nome" /></td>
				</tr>
				<tr>
					<td><form:label path="codiceFiscale">
							<spring:message code="codiceFiscale" />
						</form:label></td>
					<td><form:input path="codiceFiscale" maxlength="16" /></td>
				</tr>

				<tr>
					<td><form:label path="alboProfessionale">
							<spring:message code="albo.ordineappartenenza" />
						</form:label></td>
					<td>
						<table>
							<tr>
								<td><form:radiobutton path="alboProfessionale.id" value="<%=Constants.OrdineConstants.K_ORDINE_COMMERCIALISTI%>" style="cursor:pointer" onclick="submit()" /></td>
								<td><form:label path="alboProfessionale.id">
										<spring:message code="albo.ordineappartenenza.commercialisti" />
									</form:label></td>
							</tr>
							<tr>
								<td><form:radiobutton path="alboProfessionale.id" value="<%=Constants.OrdineConstants.K_ORDINE_AVVOCATI%>" style="cursor:pointer" onclick="submit()" /></td>
								<td><form:label path="alboProfessionale.id">
										<spring:message code="albo.ordineappartenenza.avvocati" />
									</form:label></td>
							</tr>
						</table>
					</td>
					<td><form:label path="ambitoTerritoriale">
							<spring:message code="albo.ordineappartenenza.sede" />
						</form:label></td>
					<td><form:select path="ambitoTerritoriale.id" size="1">
							<form:option value="" label="--- Seleziona Sede ---" />
							<form:options items="${sediOrdini}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>

				<tr>
					<td><form:label path="ambitoTerritoriale">
							<spring:message code="albo.titolo.sezione" />
						</form:label></td>
					<td>
						<table>
							<tr>
								<td><form:checkbox path="sezione" value="<%=Constants.SezioniConstants.K_SEZIONE_ORDINARIA%>" style="cursor:pointer" /></td>
								<td><form:label path="sezione">
										<spring:message code="albo.sezione.ordinaria" />
									</form:label></td>
							</tr>
							<tr>
								<td><form:checkbox path="sezione" value="<%=Constants.SezioniConstants.K_SEZIONE_ESPERTI%>" style="cursor:pointer" /></td>
								<td><form:label path="sezione">
										<spring:message code="albo.sezione.esperti" />
									</form:label></td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td><form:label path="provinciaDomProf.id">
							<spring:message code="albo.indirizzo.provincia" />
						</form:label></td>
					<td><form:select path="provinciaDomProf.id" size="1" onchange="submit()" style="width:65%">
							<form:option value="" label="--- Seleziona Provincia ---" />
							<form:options items="${provinceDomicilio}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
					<td><form:label path="comuneDomProf.id">
							<spring:message code="albo.indirizzo.comune" />
						</form:label></td>
					<td colspan="2"><form:select path="comuneDomProf.id" size="1" style="width:70%">
							<form:option value="" label="--- Seleziona Comune ---" />
							<form:options items="${comuniDomicilio}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>

			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" name="RICERCA" title="Ricerca Albo degli Amministratori" value="Ricerca" /></td>
				</tr>
			</table>
			<br />
			<br />
		</form:form>

	</tiles:putAttribute>
</tiles:insertDefinition>
