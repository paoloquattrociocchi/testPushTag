<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tr>
	<td class="td_titolo" colspan="4"><spring:message code="bene.denaro.dati" /></td>
</tr>

<tr>
	<td width="15%"><form:label path="codiceAbi"><spring:message code="bene.finanziario.abi" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormValido.codiceAbi}"/><form:errors path="codiceAbi" class="testoRosso"/></td>
</tr>

<tr>
	<td width="15%"><form:label path=""><spring:message code="bene.finanziario.descrizioneente" /></form:label></td>
	<td colspan="3">
		<c:if test="${beneFormValido.descOperFinanz.descrizione!=null}">
			<c:out value="${beneFormValido.descOperFinanz.descrizione}"/>
		</c:if>
		<c:if test="${beneFormValido.descrizioneEnte!=null}">
			<c:out value="${beneFormValido.descrizioneEnte}"/>
		</c:if>
	</td>
</tr>

<tr>
	<td width="15%"><form:label path="codiceCab"><spring:message code="bene.finanziario.cab" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormValido.codiceCab.id}"/><form:errors path="codiceCab" class="testoRosso"/></td>
</tr>

<%-- <tr>
	<td width="15%"><form:label path="indirizzoBeneFin"><spring:message code="bene.finanziario.indirizzo" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormValido.indirizzoBeneFin}"/></td>
</tr>  --%>

<tr>
	<td width="15%"><form:label path="codiceIban"><spring:message code="bene.finanziario.iban" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormValido.codiceIban}" /><form:errors path="codiceIban" class="testoRosso"/></td>
<%-- 	<td width="15%"><form:label path="codiceSwift"><spring:message code="bene.finanziario.swift" /></form:label></td>
	<td colspan="1"><c:out value="${beneFormValido.codiceSwift}" /></td> --%>
</tr>	

<tr>
	<td width="15%"><form:label path="numeroRegistrazione"><spring:message code="bene.denaro.conto" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormValido.numeroRegistrazione}" /><form:errors path="numeroRegistrazione" class="testoRosso"/></td>
</tr>

<%-- <tr>
	<td width="15%"><form:label path="valutaEur"><spring:message code="bene.finanziario.importo.euro" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormValido.valutaEur}" /></td>
</tr>

<tr>
	<td width="15%"><form:label path="tipoValuta"><spring:message code="bene.finanziario.valuta"/></form:label></td>
	<td width="35%"><c:out value="${beneFormValido.tipoValuta.descrizione}" /></td>
	<td><form:label path="valutaEst"><spring:message code="bene.finanziario.importo.estero" /></form:label></td>
	<td width="35%"><c:out value="${beneFormValido.valutaEst}" /></td>
</tr> --%>

<%-- <c:if test="${beneFormValido.sottocategoria.id != null && beneFormValido.sottocategoria.id != 35}">
	<tr>
		<td width="15%"><form:label path="direttoreProtempore"><spring:message code="bene.denaro.direttore" /></form:label></td>
		<td colspan="3"><c:out value="${beneFormValido.direttoreProtempore}" /></td>
	</tr>
</c:if>
<c:if test="${beneFormValido.sottocategoria.id != null && beneFormValido.sottocategoria.id == 35}">
	<tr>
	    <td width="15%"><form:label path="intestatario"><spring:message code="bene.denaro.contante.intestatario"/></form:label></td>
		<td width="35%"><c:out value="${beneFormValido.intestatario}" /></td>
		<td><form:label path="numModelloTribunale"><spring:message code="bene.denaro.contante.nummodellotribunale"/></form:label></td>
		<td width="35%"><c:out value="${beneFormValido.numModelloTribunale}" /></td>
	</tr>
	<tr>
		<td width="15%"><form:label path="numBollettino"><spring:message code="bene.denaro.contante.numerobollettino" /></form:label></td>
		<td width="35%"><c:out value="${beneFormValido.numBollettino}" /></td>
		<td colspan="2">&nbsp;</td>
	</tr>
</c:if>

<tr>
	<td width="15%"><form:label path="gravame"><spring:message code="bene.gravame" /></form:label></td>
	<td width="35%">
	  <form:radiobutton disabled="true" path="gravame" value="S" label="SI"/>
	  <form:radiobutton disabled="true" path="gravame" value="N" label="NO"/>
    </td>
    <td><form:label path="note"><spring:message code="note" /></form:label></td>
	<td width="15%"><c:out value="${beneFormValido.note}" /></td>
</tr> --%>