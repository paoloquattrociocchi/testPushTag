<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>



<table class="tb_form" border=0>
	<tr>
		<td colspan="4" class="td_titolo"><form:label path="" cssClass="required">
				<spring:message code="bene.mobileRegistrato.datirelativi" />
			</form:label></td>
	</tr>
	<tr>
		<td><form:label path="categoria.id" cssClass="required">
				<spring:message code="bene.categoria" />
			</form:label></td>
		<td colspan="4"><form:select path="categoria.id" size="1" disabled="true">
				<form:option value="" label="------Seleziona Categoria-------" />
				<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
				<form:errors path="categoria.id" class="testoRosso" />
			</form:select>
		</td>
	</tr>
	<tr>
		<td><form:label path="sottocategoria.id" cssClass="required">
				<spring:message code="bene.sottocategoria" />
			</form:label></td>
		<td colspan="4"><form:select id="id_sottocategoria" path="sottocategoria.id" size="1">
				<!-- da fare obbligatoria -->
				<form:option value="" label="------Seleziona Sottocategoria-------" />
				<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
			</form:select> <form:hidden id="desc_sottocategoria" path="sottocategoria.descrizione" /> <form:errors path="sottocategoria.id" class="testoRosso" /></td>
	</tr>
	
	<tr>
		<td><form:label path="numRegBeneMobileRegistrato">
				<spring:message code="bene.mobileRegistrato.numeroRegistrazione" />
			</form:label></td>
		<td colspan="3"><form:input path="numRegBeneMobileRegistrato" maxlength="10" size="15" /> <form:errors path="numRegBeneMobileRegistrato" class="testoRosso" /></td>
	</tr>
	<tr>
		<td><form:label path="dataRegistrazione">
				<spring:message code="bene.dataRegistrazione" />
			</form:label></td>
		<td colspan="3"><form:input path="dataRegistrazione" id="dataRegistrazione" maxlength="10" size="12" /> <br> <form:errors path="dataRegistrazione" class="testoRosso" /></td>
	</tr>
	<tr>
		<td><form:label path="modelloVeicolo">
				<spring:message code="bene.mobileRegistrato.veicolo.modello" />
			</form:label></td>
		<td><form:input path="modelloVeicolo" maxlength="40" size="70" /> <form:errors path="modelloVeicolo" class="testoRosso" /></td>
		<td><form:label path="codImmatricolazioneVeicolo">
				<spring:message code="bene.mobileRegistrato.veicolo.codiceImmatricolazione" />
			</form:label></td>
		<td><form:input path="codImmatricolazioneVeicolo" maxlength="10" size="20" /> <form:errors path="codImmatricolazioneVeicolo" class="testoRosso" /></td>
	</tr>
	<tr>
		<td><form:label path="targa">
				<spring:message code="bene.mobileRegistrato.targa" />
			</form:label></td>
		<td><form:input path="targa" maxlength="10" size="20" /> <form:errors path="targa" class="testoRosso" /></td>
		<td><form:label path="telaio">
				<spring:message code="bene.mobileRegistrato.veicolo.telaio" />
			</form:label></td>
		<td><form:input path="telaio" maxlength="30" size="50" /> <form:errors path="telaio" class="testoRosso" /></td>
	</tr>
	<tr>
		<td><form:label path="coloreVeicolo">
				<spring:message code="bene.mobileRegistrato.veicolo.colore" />
			</form:label></td>
		<td><form:input path="coloreVeicolo" maxlength="30" size="50" /> <form:errors path="coloreVeicolo" class="testoRosso" /></td>
		<td><form:label path="numeroIdentificazione">
				<spring:message code="bene.mobileRegistrato.veicolo.numeroIdentificazione" />
			</form:label></td>
		<td><form:input path="numeroIdentificazione" maxlength="10" size="20" /> <form:errors path="numeroIdentificazione" class="testoRosso" /></td>
	</tr>
	<tr>
		<td><form:label path="comboDisponibilita">
				<spring:message code="bene.disponibilita" />
			</form:label></td>
		<td colspan="3"><form:select path="comboDisponibilita.id" size="1">
				<option value="" label="----------------Seleziona Disponibilità-----------------" />
				<form:options items="${disponibilita}" itemValue="id" itemLabel="descrizione" />

			</form:select></td>
	</tr>
	<tr>
		<td><form:label path="gravame">
				<spring:message code="bene.gravame" />
			</form:label></td>
		<td><form:radiobutton path="gravame" value="S" label="SI" /> <form:radiobutton path="gravame" value="N" label="NO" /></td>
		<td><form:label path="note">
				<spring:message code="note" />
			</form:label></td>
		<td><form:textarea path="note" rows="5" /></td>
	</tr>
</table>

			
