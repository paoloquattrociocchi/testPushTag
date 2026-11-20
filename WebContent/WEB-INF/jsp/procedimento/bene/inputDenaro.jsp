<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>



<table class="tb_form" border=0>
	<tr>
		<td width="15%"><form:label path="tipologia.id">
				<spring:message code="bene.tipologia" />
			</form:label>
		</td>
		<td colspan="3">
			<select style="width:20%" disabled="disabled">
				<option value="${beneForm.tipologia.descrizione}">${beneForm.tipologia.descrizione}</option>
			</select>
			<%--
			<form:select path="tipologia.id" size="1" disabled="true" cssStyle="width:20%">
				<form:option value="4" label="${beneForm.tipologia.descrizione}"/>
			</form:select>
			 --%>
		</td>
	</tr>
	<tr>
		<td width="15%"><form:label path="categoria.id" cssClass="required">
				<spring:message code="bene.categoria" />
			</form:label>
		</td>
		<td colspan="3">
			<select style="width:20%" disabled="disabled">
				<option value="${beneForm.categoria.id}">${beneForm.categoria.descrizione}</option>
			</select>
			<%--
			<form:select id="id_cat" path="categoria.id" size="1" disabled="true" cssStyle="width:20%">
				<form:option value="4" label="${beneForm.categoria.descrizione}"/>
			</form:select>
			 --%>
		</td>
	</tr>
	<tr>
		<td width="15%"><form:label path="sottocategoria.id" cssClass="required">
				<spring:message code="bene.sottocategoria" />
			</form:label>
		</td>
		<td colspan="3">
			<form:select path="sottocategoria.id" size="1" onchange="submit()">
				<form:option value="0" label="------Seleziona Sottocategoria-------" />
				<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
			</form:select>
			<form:errors path="sottocategoria.id" class="testoRosso"/>
		</td>
	</tr>
	<tr>
		<td class="td_titolo" colspan="4"><spring:message code="bene.denaro.datirelativi" /></td>
	</tr>
	<tr>
		<td width="15%"><form:label path="descOperFinanz">
				<spring:message code="bene.finanziario.descrizioneente" />
			</form:label>
		</td>
		<td colspan="3">
       		<div class="ui-widget">
				<form:input id="id_descrizioneDescOperFinanz" path="descOperFinanz.descrizione" onBlur="svuota()"
							cssStyle="width:95%" value="${beneForm.descrizioneEnte}"
							title="Immettere almeno 5 lettere per avviare la ricerca"/>
				<div id="id_loadingDescOperFinanz"></div>
			</div>
			<form:hidden id="id_hiddenDescOperFinanz" path="descOperFinanz.id"/>
			<form:errors path="descOperFinanz.id" class="testoRosso"/>
       	</td>
	</tr>
	<tr>
		<td width="15%"><form:label path="codiceAbi">
				<spring:message code="bene.finanziario.abi" />
			</form:label>
		</td>
		<td colspan="3">
			<form:input type="text" id="id_codiceAbi" path="codiceAbi" 
						title="Immettere le 5 cifre del codice ABI" cssStyle="width:10%" maxlength="5" 
						onkeypress="return soloNumeri(event)" onchange="submit()"/>
			<form:errors path="codiceAbi" class="testoRosso"/>
		</td>
	</tr>
	<tr>
		<td width="15%"><form:label path="provincia">
				<spring:message code="bene.finanziario.provincia" />
			</form:label>
		</td>
		<td colspan="3">
			<form:select path="provincia.id" size="1" disabled="${beneForm.disabledProvincia}" onchange="submit()">
				<form:option value="-1" label="------Seleziona Provincia------------" />
				<form:options items="${provinceAbi}" itemValue="id" itemLabel="descrizione" />
			</form:select>
			<form:errors path="provincia.id" class="testoRosso"/>
		</td>
	</tr>	
	<tr>
		<td width="15%"><form:label path="codiceCab">
				<spring:message code="bene.finanziario.cab" />
			</form:label>
		</td>
		<td colspan="3">
			<c:if test="${beneForm.provincia.id==null || beneForm.provincia.id == -1}">
        		<div class="ui-widget">
					<form:input id="id_descrizioneCodiceCab" path="codiceCab.descrizione" cssStyle="width:20%" 
								title="Immettere almeno 2 lettere per avviare la ricerca"
								maxlength="5" onkeypress="return soloNumeri(event)"/>
					<div id="id_loadingCodiceCab"></div>
				</div>
					<form:errors path="codiceCab.id" class="testoRosso"/>
					<form:hidden id="id_hiddenCodiceCab" path="codiceCab.id"/>
			</c:if>
			<c:if test="${beneForm.provincia.id!=null && beneForm.provincia.id != -1}">
				<form:select path="codiceCab.id" size="1" onchange="submit()">
					<form:option value="0" label="-----Seleziona Codice CAB-----" />
					<form:options items="${codiciCab}" itemValue="id" itemLabel="descrizione" />
				</form:select>
				<form:errors path="codiceCab.id" class="testoRosso"/>
			</c:if>
       	</td>
	</tr>
	<c:if test="${beneForm.sottocategoria.id != null && beneForm.sottocategoria.id != 35}">
		<tr>
			<td width="15%"><form:label path="indirizzoBeneFin">
					<spring:message code="bene.finanziario.indirizzo" />
				</form:label>
			</td>
			<td colspan="3">
				<form:input path="indirizzoBeneFin" value="${beneForm.indirizzoBeneFin}" cssStyle="width:95%" disabled="true"/>
			</td>
		</tr>
	</c:if>
	<tr>
		<td width="15%"><form:label path="codiceIban">
				<spring:message code="bene.finanziario.iban" />
			</form:label>
		</td>
		<td colspan="1">
			<form:input path="codiceIban" title="Immettere il codice IBAN" 
						maxlength="34" size="50"/>
			<form:errors path="codiceIban" class="testoRosso"/>
		</td>
		
		<td width="15%"><form:label path="codiceSwift">
				<spring:message code="bene.finanziario.swift" />
			</form:label>
		</td>
		<td colspan="1">
			<form:input path="codiceSwift" title="Immettere il codice BIC/SWIFT" 
						maxlength="11" size="15"/>
			<form:errors path="codiceSwift" class="testoRosso"/>
		</td>
	</tr>
	<tr>
		<td width="15%"><form:label path="numeroRegistrazione" >
				<spring:message code="bene.denaro.conto" />
			</form:label>
		</td>
		<td>
			<form:input path="numeroRegistrazione" maxlength="12" size="20"
					    onkeypress="return soloNumeri(event)"/>
					    <form:errors path="numeroRegistrazione" class="testoRosso"/>
		</td>
		<c:if test="${beneForm.sottocategoria.id != 35 && beneForm.sottocategoria.id != 206}">
			<td colspan="2"></td>
		</c:if>
		<c:if test="${beneForm.sottocategoria.id != null && (beneForm.sottocategoria.id == 35 || beneForm.sottocategoria.id == 206)}">
			<td><form:label path="flgVersamento">
					<spring:message code="bene.denaro.contante.flgversamento" />
				</form:label>
			</td>
			<td>
				<form:select path="flgVersamento.id" size="1">
					<form:option value="-1" label="----Seleziona tipo versamento---" />
					<form:options items="${listaVersato}" itemValue="id" itemLabel="descrizione" />
				</form:select>
			</td>
		</c:if>
	</tr>
	<tr>
		<td width="15%"><form:label path="valutaEur">
				<spring:message code="bene.finanziario.importo.euro" />
			</form:label>
		</td>
		<td colspan="3">
			<form:input path="valutaEur" maxlength="15" size="20"/>
			<form:errors path="valutaEur" class="testoRosso"/>
		</td>
	</tr>
	<tr>
	    <td width="15%"><form:label path="tipoValuta"><spring:message code="bene.finanziario.valuta"/></form:label></td>
		<td width="35%">							
			<form:select path="tipoValuta.id" size="1" onchange="submit()">
				<form:option value="-1" label="-----Seleziona Valuta------" />
				<form:options items="${valuta}" itemValue="id" itemLabel="descrizione" /> 							
			</form:select>
		</td>
		<td><form:label path="valutaEst">
				<spring:message code="bene.finanziario.importo.estero"/>
			</form:label>
		</td>
		<td width="35%">
			<form:input path="valutaEst" maxlength="15" size="20" 
						disabled="${beneForm.disabledValutaEst}" />
			<form:errors path="valutaEst" class="testoRosso"/>
		</td>
	</tr>
	<c:if test="${beneForm.sottocategoria.id != null && beneForm.sottocategoria.id != 35}">
		<tr>
			<td width="15%"><form:label path="direttoreProtempore">
					<spring:message code="bene.denaro.direttore" />
				</form:label>
			</td>
			<td colspan="3">
				<form:input path="direttoreProtempore" size="50"/>
			</td>
		</tr>
	</c:if>
	<c:if test="${beneForm.sottocategoria.id != null && beneForm.sottocategoria.id == 35}">
		<tr>
		    <td width="15%"><form:label path="intestatario">
		    		<spring:message code="bene.denaro.contante.intestatario"/>
		    	</form:label>
		    </td>
			<td width="35%">							
				<form:input path="intestatario"/>
			</td>
			<td><form:label path="numModelloTribunale">
					<spring:message code="bene.denaro.contante.nummodellotribunale"/>
				</form:label>
			</td>
			<td width="35%">
				<form:input path="numModelloTribunale" maxlength="10"
						    onkeypress="return soloNumeri(event)"/>
			</td>
		</tr>
		<tr>
			<td width="15%"><form:label path="numBollettino">
					<spring:message code="bene.denaro.contante.numerobollettino" />
				</form:label>
			</td>
			<td colspan="3">
				<form:input path="numBollettino" title="Immettere le 27 cifre del Bollettino" 
							maxlength="27" size="50" />
			</td>
		</tr>
	</c:if>
	<tr>
		<td width="15%"><form:label path="gravame">
				<spring:message code="bene.gravame" />
			</form:label>
		</td>
		<td width="35%">
		  <form:radiobutton path="gravame" value="S" label="SI"/>
		  <form:radiobutton path="gravame" value="N" label="NO"/>
	 	</td>
		<td><form:label path="note">
				<spring:message code="note" />
			</form:label>
		</td>
		<td width="35%">
			<form:textarea path="note" rows="5" />
		</td>
	</tr>
</table>