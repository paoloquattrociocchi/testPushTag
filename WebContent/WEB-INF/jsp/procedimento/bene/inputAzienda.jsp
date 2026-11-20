<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>



<table class="tb_form" border=0>
	<tr>
		<td class="td_titolo" colspan="6"><form:label path="">
				<spring:message code="bene.azienda.datirelativi" />
			</form:label></td>
	</tr>
	<tr>
		<td>
			<form:label path="categoria.id" cssClass="required">
				<spring:message code="bene.categoria" />
			</form:label></td>
		<td colspan="3">
			<form:select id="id_categoria"
				path="categoria.id" size="1">
				<form:option value="-1" label="------Seleziona Categoria-------" />
				<form:options items="${categorie}" itemValue="id"
					itemLabel="descrizione" />
			</form:select> <form:hidden id="desc_categoria" path="categoria.descrizione" />

			<form:errors path="categoria.id" class="testoRosso" /></td>
	</tr>
	<tr>
		<td><form:label path="stato.id" cssClass="required">
				<spring:message code="stato" />
			</form:label></td>
		<td colspan="3">
			<form:select id="id_Stato" path="stato.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'Comune')">
				<form:option value="-1" label="-------------------------Seleziona Stato-----------------------" />
				<form:options items="${stati}" itemValue="id" itemLabel="descrizione" />
			</form:select> 
			<form:errors path="stato.id" class="testoRosso" />
		</td>
	</tr>
	<tr>
		<td><form:label path="comune.id">
				<spring:message code="comune" />
			</form:label></td>
		<td colspan="3">
			<div class="ui-widget">
				<form:input id="id_descrizioneComune" path="comune.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca" />
				<div id="id_loadingComune"></div>
				<form:hidden id="id_hiddenComune" path="comune.id" />
			</div>
		</td>
	</tr>
	<tr>
		<td><form:label path="indirizzo">
				<spring:message code="indirizzo" />
			</form:label></td>
		<td><form:select id="id_tipostrada"
				path="indirizzo.toponimo.id" size="1">
				<form:option value="-1" label="------Seleziona Tipo Strada-------" />
				<form:options items="${tipiStrada}" itemValue="id" itemLabel="descrizione" />
			</form:select> 
			<form:hidden id="desc_tipostrada" path="indirizzo.toponimo.descrizione" /> 
			<form:input path="indirizzo.denominazione" maxlength="50" size="30" />
		</td>
		<td><form:label path="indirizzo.civico">
				<spring:message code="civico" />
			</form:label></td>
		<td><form:input path="indirizzo.civico" /></td>

	</tr>
	<tr>
		<td><form:label path="ragioneSociale" cssClass="required">
				<spring:message code="soggettogiuridico.ragioneSociale" />
			</form:label></td>
		<td colspan="3"><form:input path="ragioneSociale"
				maxlength="200" size="70" /> <form:errors path="ragioneSociale"
				class="testoRosso" /></td>
	</tr>


	<tr>
		<td><form:label path="partitaIva" cssClass="required">
				<spring:message code="soggettogiuridico.partitaIva" />
			</form:label></td>
		<td><form:select id="id_StatoSiglaPiva"
				path="partitaIvaSiglaStato" size="1">
				<%-- <form:option value="" label="  " /> --%>
				<form:options items="${statiPiva}" itemValue="id"
					itemLabel="descrizione" />
			</form:select> <form:input path="partitaIva" maxlength="12" size="18" /> <form:errors
				path="partitaIva" class="testoRosso" /></td>
		<td><form:label path="codiceFiscale" cssClass="required">
				<spring:message code="codiceFiscale" />
			</form:label></td>
		<td colspan="3"><form:input path="codiceFiscale"
				maxlength="11" size="20" /> <form:errors path="codiceFiscale"
				class="testoRosso" /></td>
	</tr>
	<tr>
		<td><form:label path="numeroCCIAA">
				<spring:message code="soggettogiuridico.numeroCCIAA" />
			</form:label></td>
		<td><form:input path="numeroCCIAA" maxlength="30" size="30"/> <form:errors
				path="numeroCCIAA" class="testoRosso" /></td>
		<td><form:label path="capitaleSociale">
				<spring:message code="soggettogiuridico.capitaleSociale" />
			</form:label></td>
		<td colspan="3"><form:input path="capitaleSociale"
				maxlength="15" size="20" /> <form:errors path="capitaleSociale"
				class="testoRosso" /></td>
	</tr>
	<tr>
		<td><form:label path="amministratore">
				<spring:message code="soggettogiuridico.amministratore" />

			</form:label></td>
		<td><form:input path="amministratore" /> <form:errors
				path="amministratore" class="testoRosso" /></td>
		<td><form:label path="rappresentanteSociale">
				<spring:message code="soggettogiuridico.rappresentanteSociale" />
			</form:label></td>
		<td colspan="3"><form:input path="rappresentanteSociale" /> <form:errors
				path="rappresentanteSociale" class="testoRosso" /></td>
	</tr>
	<tr>
		<td><form:label path="numeroDipendenti">
				<spring:message code="bene.azienda.numerodipendenti" />
			</form:label></td>
		<td colspan="3"><form:input path="numeroDipendenti" /> <form:errors
				path="numeroDipendenti" class="testoRosso" /></td>
	</tr>
	<tr>
		<td><form:label path="statoConservazione.id">
				<spring:message code="bene.statoconservazione" />
			</form:label></td>
		<td colspan="3"><form:select path="statoConservazione.id"
				size="1">
				<option value=""
					label="----------------Seleziona Stato di Conservazione-----------------" />
				<form:options items="${statiConservazione}" itemValue="id"
					itemLabel="descrizione" />
			</form:select></td>
	</tr>
	<tr>
		<td><form:label path="settoreAttivita.id">
				<spring:message code="soggettogiuridico.settoreAttivita" />
			</form:label></td>
		<td colspan="3"><form:select path="settoreAttivita.id"
				size="1">
				<option value=""
					label="--------------Seleziona Settore di Attività-----------------------" />
				<form:options items="${settoriAttivita}" itemValue="id"
					itemLabel="descrizione" />
			</form:select></td>
	</tr>
	<tr>
		<td><form:label path="gravame">
				<spring:message code="bene.gravame" />
			</form:label></td>
		<td><form:radiobutton path="gravame" value="S" label="SI" />
			<form:radiobutton path="gravame" value="N" label="NO" /></td>
		<td><form:label path="note">
				<spring:message code="note" />
			</form:label></td>
		<td><form:textarea path="note" rows="5" /></td>
	</tr>
	<tr>
		<td><form:label path="costituitoInAzienda">
				<spring:message code="bene.azienda.costituitoinazienda" />
			</form:label></td>
		<td><form:radiobutton path="costituitoInAzienda" value="S"
				label="SI" /> <form:radiobutton path="costituitoInAzienda"
				value="N" label="NO" /></td>
		<td><form:label path="stima">
				<spring:message code="bene.azienda.stima" />
			</form:label></td>
		<td colspan="3"><form:input path="stima" /></td>
	</tr>
</table>

<script type="text/javascript">
	if($( "#id_Stato" ).val() == 1)
	{
		doAutocomplete('Comune');
	}
</script>