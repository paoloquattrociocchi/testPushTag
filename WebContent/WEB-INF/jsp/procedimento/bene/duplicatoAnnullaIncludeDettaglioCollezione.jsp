<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tr>
	<td class="td_titolo" colspan="4"><spring:message code="bene.collezione.dati" /></td>
</tr>

<tr>
	<td><form:label path="descrizioneCollezione"><spring:message code="bene.mobile.collezione.descrizionecollezione" /></form:label></td>
	<td><c:out value="${beneFormDuplicato.descrizioneCollezione}" /><form:errors path="descrizioneCollezione" class="testoRosso"/></td>
							
<%-- 	<td><form:label path="numeroPezzi"><spring:message code="bene.mobile.collezione.numeropezzi" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormDuplicato.numeroPezzi}" /></td> --%>
</tr>
<%-- <tr>
	<td><form:label path="descUbicazione" ><spring:message code="bene.mobile.ubicazione"/></form:label></td>
	<td colspan="6"><c:out value="${beneFormDuplicato.descUbicazione}" /></td>
</tr>
<tr>
	<td><form:label path="stato.descrizione" ><spring:message code="stato"/></form:label></td>
    		    <td colspan="6"><c:out value="${beneFormDuplicato.stato.descrizione}" /></td>
</tr>	
<tr>
    <td><form:label path="comune.descrizione" ><spring:message code="comune"/></form:label></td>					
     	    <td colspan="6"><c:out value="${beneFormDuplicato.comune.descrizione}" /></td>
</tr>
<tr>
    <td><form:label path="indirizzo"><spring:message code="indirizzo" /></form:label></td>
	<td><c:out value="${beneFormDuplicato.indirizzo.toponimo.descrizione}" /> <c:out value="${beneFormDuplicato.indirizzo.denominazione}" /></td>
	
	<td><form:label path="indirizzo.civico"><spring:message code="civico" /></form:label></td>
	<td colspan="6"><c:out value="${beneFormDuplicato.indirizzo.civico}" /></td>
</tr>
<tr>
	<td><form:label path="gravame"><spring:message code="bene.gravame" /></form:label></td>
	<td>
	  <form:radiobutton disabled="true" path="gravame" value="S" label="SI"/>
	  <form:radiobutton disabled="true" path="gravame" value="N" label="NO"/>
    </td>
    <td><form:label path="note"><spring:message code="note" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormDuplicato.note}" /></td>
</tr>
<tr>
	<td><form:label path="costituitoInAzienda"><spring:message code="bene.azienda.costituitoinazienda" /></form:label></td>
	<td colspan="6">
	  <form:radiobutton disabled="true" path="costituitoInAzienda" value="S" label="SI"/>
	  <form:radiobutton disabled="true" path="costituitoInAzienda" value="N" label="NO"/>
    </td>
</tr> --%>	