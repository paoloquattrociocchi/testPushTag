<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tr>
	<td class="td_titolo" colspan="4"><spring:message code="bene.bene.immobile.dati" /></td>
</tr>

<tr>
	<td><form:label path="numRegBeneMobileRegistrato">
			<spring:message code="bene.mobileRegistrato.numeroRegistrazione" />
		</form:label>
	</td>
	<td colspan="3"><c:out value="${beneFormValido.numRegBeneMobileRegistrato}" /><form:errors path="numRegBeneMobileRegistrato" class="testoRosso"/></td>
</tr>
<tr>
	<td><form:label path="dataRegistrazione"><spring:message code="bene.dataRegistrazione" /></form:label></td>
	<td colspan="3"><fmt:formatDate value="${beneFormValido.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /><form:errors path="dataRegistrazione" class="testoRosso"/></td>
	
<%-- 	<td><form:label path="dataScadenza"><spring:message code="bene.mobileRegistrato.dataScadenza" /></form:label></td>
	<td><fmt:formatDate value="${beneFormValido.dataScadenza}" type="date" pattern="dd/MM/yyyy" /></td>
 --%></tr>
<%-- <tr>
	<td><form:label path="gravame"><spring:message code="bene.gravame" /></form:label></td>
	<td>
	  <form:radiobutton disabled="true" path="gravame" value="S" label="SI"/>
	  <form:radiobutton disabled="true" path="gravame" value="N" label="NO"/>
    </td>
    <td><form:label path="note"><spring:message code="note" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormValido.note}" /></td>
</tr>
<tr>
	<td><form:label path="costituitoInAzienda"><spring:message code="bene.azienda.costituitoinazienda" /></form:label></td>
	<td colspan="6">
	  <form:radiobutton disabled="true" path="costituitoInAzienda" value="S" label="SI"/>
	  <form:radiobutton disabled="true" path="costituitoInAzienda" value="N" label="NO"/>
    </td>
</tr>	 --%>