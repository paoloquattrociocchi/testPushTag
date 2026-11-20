<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tr>
	<td class="td_titolo" colspan="4"><spring:message code="bene.animali.dati" /></td>
</tr>

<%-- <tr>
	<td><form:label path="stato.id" ><spring:message code="stato"/></form:label></td>
      	<td colspan="3"><c:out value="${beneFormValido.stato.descrizione}" /></td>
</tr>	
<tr>
	<td><form:label path="comune.id" ><spring:message code="comune"/></form:label></td>					
   	<td colspan="3"><c:out value="${beneFormValido.comune.descrizione}" /></td>
</tr>	
<tr>
    <td><form:label path="indirizzo"><spring:message code="indirizzo" /></form:label></td>
	<td><c:out value="${beneFormValido.indirizzo.toponimo.descrizione}" /> <c:out value="${beneFormValido.indirizzo.denominazione}" /></td>
	
	<td><form:label path="indirizzo.civico"><spring:message code="civico" /></form:label></td>
	<td colspan="6"><c:out value="${beneFormValido.indirizzo.civico}" /></td>
</tr> --%>
<tr>
	<td><form:label path="descrizioneCapo"><spring:message code="bene.mobile.animali.descrizionecapo" /></form:label></td>
	<td><c:out value="${beneFormValido.descrizioneCapo}" /><form:errors path="descrizioneCapo" class="testoRosso"/></td>
	
<%-- 	<td><form:label path="numeroAnimali"><spring:message code="bene.mobile.animali.numerocapi" /></form:label></td>
	<td><c:out value="${beneFormValido.numeroAnimali}" /></td> --%>
</tr>
<%-- <tr>
	<td><form:label path="gravame"><spring:message code="bene.gravame" /></form:label></td>
	<td>
	  <form:radiobutton disabled="true" path="gravame" value="S" label="SI"/>
	  <form:radiobutton disabled="true" path="gravame" value="N" label="NO"/>
    </td>
    <td><form:label path="note"><spring:message code="note" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormValido.note}" /></td>
</tr> --%>