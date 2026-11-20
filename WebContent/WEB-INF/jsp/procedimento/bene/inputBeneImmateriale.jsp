<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>



<table class="tb_form" border=0>
	<tr>
		<td colspan="8" class="td_titolo">
			<form:label path="">
				<spring:message code="bene.mobileRegistrato.datirelativi" />
			</form:label>
		</td>
	</tr>
	<tr>
		<td><form:label path="categoria.id" cssClass="required">
				<spring:message code="bene.categoria" />
			</form:label>
		</td>
		<td colspan="8">
			<form:select path="categoria.id" size="1" disabled="true">
				<form:option value="" label="------Seleziona Categoria-------" />
				<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
			</form:select>
			<form:errors path="categoria.id" cssClass="testoRosso"/>
		</td>
	</tr>				
	<tr>
		<td><form:label path="sottocategoria.id" cssClass="required"><spring:message code="bene.sottocategoria" /></form:label></td>
		<td  colspan="8">
			<form:select id="id_sottocategoria" path="sottocategoria.id" size="1">
				<form:option value="" label="------Seleziona Sottocategoria-------" />
				<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
			</form:select>
			<form:hidden id="desc_sottocategoria" path="sottocategoria.descrizione" />
			<form:errors path="sottocategoria.id" cssClass="testoRosso"/>
		</td>
	</tr>
	<tr>
		<td><form:label path="numRegBeneMobileRegistrato">
			<spring:message code="bene.mobileRegistrato.numeroRegistrazione" />
			</form:label>
		</td>
		<td colspan="8"><form:input path="numRegBeneMobileRegistrato" maxlength="10" size="15" />
		<form:errors path="numRegBeneMobileRegistrato" class="testoRosso"/>
		</td>
	</tr>
	<tr>
		<td><form:label path="dataRegistrazione">
				<spring:message code="bene.dataRegistrazione" />
			</form:label>
		</td>
		<td> 
			<form:input path="dataRegistrazione" id="dataRegistrazione" maxlength="10" size="12"/> 
			<br> 
			<form:errors path="dataRegistrazione" class="testoRosso" />
		</td>
		<td><form:label path="dataScadenza">
				<spring:message code="bene.mobileRegistrato.dataScadenza" />
			</form:label>
		</td>
		<td colspan="3"> 
			<form:input path="dataScadenza" id="dataScadenza" maxlength="10" size="12"/> 
			<br> 
			<form:errors path="dataScadenza" class="testoRosso" />
		</td>
	</tr>
	<tr>
		<td><form:label path="gravame">
				<spring:message code="bene.gravame" />
			</form:label>
		</td>
		<td> 
		 <form:radiobutton path="gravame" value="S" label="SI"/>
		 <form:radiobutton path="gravame" value="N" label="NO"/>
	   </td>
		<td><form:label path="note">
				<spring:message code="note" />
			</form:label>
		</td>
		<td colspan="3">
			<form:textarea path="note" rows="5" />
		</td>
	</tr>
	<tr>
		<td><form:label path="costituitoInAzienda"><spring:message code="bene.azienda.costituitoinazienda" /></form:label></td>
		<td colspan="8">
		 <form:radiobutton path="costituitoInAzienda" value="S" label="SI"/>
		 <form:radiobutton path="costituitoInAzienda" value="N" label="NO"/>
	   </td>
	</tr>
</table>