<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tr>
	<td class="td_titolo" colspan="4"><spring:message code="bene.altrioggetti.dati" /></td>
</tr>
				
<%-- <tr>
	<td><form:label path="stato.descrizione" ><spring:message code="stato"/></form:label></td>
    		 <td colspan="6"><c:out value="${beneFormDuplicato.stato.descrizione}"/></td>
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
</tr> --%>
<tr>
	<td><form:label path="descrizioneBene"><spring:message code="bene.mobile.altro.descrizione" /></form:label>
	</td>
	<td colspan="3"><c:out value="${beneFormDuplicato.descrizioneBene}" /><form:errors path="descrizioneBene" class="testoRosso"/></td>						
</tr>
<%-- <tr>
	<td><form:label path="gravame"><spring:message code="bene.gravame" /></form:label></td>
	<td>
	  <form:radiobutton disabled="true" path="gravame" value="S" label="SI"/>
	  <form:radiobutton disabled="true" path="gravame" value="N" label="NO"/>
    </td>
    <td><form:label path="note"><spring:message code="note" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormDuplicato.note}" /></td>
</tr> --%>