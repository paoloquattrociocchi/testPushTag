<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tr>
	<td class="td_titolo" colspan="4"><spring:message code="bene.finanziario.dati" /></td>
</tr>

<tr>
	<td width="15%"><form:label path="codiceAbi"><spring:message code="bene.finanziario.abi" /></form:label></td>
	<td colspan="5"><c:out value="${beneFormDuplicato.codiceAbi}"/><form:errors path="codiceAbi" class="testoRosso"/></td>
</tr>

<tr>
	<td width="15%"><form:label path=""><spring:message code="bene.finanziario.descrizioneente" /></form:label></td>
	<td colspan="5">
		<c:if test="${beneFormDuplicato.descOperFinanz.descrizione!=null}">
			<c:out value="${beneFormDuplicato.descOperFinanz.descrizione}"/>
		</c:if>
		<c:if test="${beneFormDuplicato.descrizioneEnte!=null}">
			<c:out value="${beneFormDuplicato.descrizioneEnte}"/>
		</c:if>
	</td>
</tr>

<tr>
	<td><form:label path="codiceCab"><spring:message code="bene.finanziario.cab" /></form:label></td>
	<td colspan="5"><c:out value="${beneFormDuplicato.codiceCab.id}"/><form:errors path="codiceCab" class="testoRosso"/></td>
</tr>

<%-- <c:if test="${beneFormDuplicato.indirizzoBeneFin!=null}">
	<tr>
		<td><form:label path="indirizzoBeneFin"><spring:message code="bene.finanziario.indirizzo" /></form:label></td>
		<td colspan="5"><c:out value="${beneFormDuplicato.indirizzoBeneFin}"/></td>
	</tr>
</c:if>

<c:if test="${beneFormDuplicato.indirizzoBeneFin==null}">
	<tr>
		<td><form:label path="indirizzo.toponimo.id"><spring:message code="indirizzo" /></form:label></td>
		<td>
			<c:if test="${beneFormDuplicato.indirizzo.toponimo.id=='1'}"><c:out value="Via" />&nbsp;&nbsp;</c:if>
			<c:if test="${beneFormDuplicato.indirizzo.toponimo.id=='2'}"><c:out value="C/da" />&nbsp;&nbsp;</c:if>
			<c:if test="${beneFormDuplicato.indirizzo.toponimo.id=='3'}"><c:out value="Largo" />&nbsp;&nbsp;</c:if>
			<c:if test="${beneFormDuplicato.indirizzo.toponimo.id=='4'}"><c:out value="Piazza" />&nbsp;&nbsp;</c:if>
			<c:out value="${beneFormDuplicato.indirizzo.denominazione}"/>
		</td>
		<c:if test="${beneFormDuplicato.indirizzo.civico!=null}">
			<td><form:label path="indirizzo.civico"><spring:message code="civico" /></form:label></td>
			<td colspan="3"><c:out value="${beneFormDuplicato.indirizzo.civico}"/></td>
		</c:if>
		<c:if test="${beneFormDuplicato.indirizzo.civico==null}">
			<td colspan="4"></td>
		</c:if>
	</tr>
</c:if>

<c:if test="${beneFormDuplicato.descrizioneFiliale!=null}">
	<tr>
		<td><form:label path="descrizioneFiliale"><spring:message code="bene.finanziario.descrizionefiliale" /></form:label></td>
		<td colspan="5"><c:out value="${beneFormDuplicato.descrizioneFiliale}" /></td>
	</tr>
</c:if>

<c:if test="${beneFormDuplicato.stato.descrizione!=null}">
	<tr>
       	<td><form:label path="stato.id" ><spring:message code="stato"/></form:label></td>
       	<td colspan="5"><c:out value="${beneFormDuplicato.stato.descrizione}" /></td>							
	</tr>
</c:if>

<c:if test="${beneFormDuplicato.comune.descrizione!=null}">
	<tr>
		<td><form:label path="comune.id" ><spring:message code="comune"/></form:label></td>
       	<td colspan="5"><c:out value="${beneFormDuplicato.comune.descrizione}" /></td>								
	</tr>	
</c:if>
 --%>
<tr>
	<td><form:label path="codiceIban"><spring:message code="bene.finanziario.iban" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormDuplicato.codiceIban}" /><form:errors path="codiceIban" class="testoRosso"/></td>
<%-- 	<td><form:label path="codiceSwift"><spring:message code="bene.finanziario.swift" /></form:label></td>
	<td colspan="1"><c:out value="${beneFormDuplicato.codiceSwift}" /></td> --%>
</tr>	

<tr>
	<td><form:label path="numeroRegistrazione"><spring:message code="bene.finanziario.numeroregistrazione" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormDuplicato.numeroRegistrazione}" /><form:errors path="numeroRegistrazione" class="testoRosso"/></td>
</tr>
<%-- 
<tr>
	<td><form:label path="valutaEur"><spring:message code="bene.finanziario.importo.euro" /></form:label></td>
	<td colspan="5"><c:out value="${beneFormDuplicato.valutaEur}" /></td>
</tr>

<tr>
	<td><form:label path="tipoValuta"><spring:message code="bene.finanziario.valuta"/></form:label></td>
	<td><c:out value="${beneFormDuplicato.tipoValuta.descrizione}" /></td>
	<td><form:label path="valutaEst"><spring:message code="bene.finanziario.importo.estero" /></form:label></td>
	<td><c:out value="${beneFormDuplicato.valutaEst}" /></td>
</tr>

<tr>
	<td><form:label path="direttoreProtempore"><spring:message code="bene.finanziario.direttoreprotempore" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormDuplicato.direttoreProtempore}" /></td>
</tr>

<tr>
	<td><form:label path="gravame"><spring:message code="bene.gravame" /></form:label></td>
	<td>
	  <form:radiobutton disabled="true" path="gravame" value="S" label="SI"/>
	  <form:radiobutton disabled="true" path="gravame" value="N" label="NO"/>
    </td>
    <td><form:label path="note"><spring:message code="note" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormDuplicato.note}" /></td>
</tr> --%>