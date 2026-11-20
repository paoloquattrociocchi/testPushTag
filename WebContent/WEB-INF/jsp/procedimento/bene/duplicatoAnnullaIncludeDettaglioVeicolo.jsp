<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<tr>
	<td class="td_titolo" colspan="4"><spring:message code="bene.veicolo.dati" /></td>
</tr>

<%-- <tr>
	<td><form:label path="numRegBeneMobileRegistrato"><spring:message code="bene.mobileRegistrato.numeroRegistrazione" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormDuplicato.numRegBeneMobileRegistrato}"/></td>
</tr>
<tr>
	<td><form:label path="dataRegistrazione"><spring:message code="bene.dataRegistrazione" /></form:label></td>
	<td colspan="3"><fmt:formatDate value="${beneFormDuplicato.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /></td>
</tr> --%>
<tr>
	<td><form:label path="modelloVeicolo"><spring:message code="bene.mobileRegistrato.veicolo.modello" /></form:label></td>
	<td><c:out value="${beneFormDuplicato.modelloVeicolo}" /><form:errors path="modelloVeicolo" class="testoRosso"/></td>
	
	<td><form:label path="codImmatricolazioneVeicolo"><spring:message code="bene.mobileRegistrato.veicolo.codiceImmatricolazione" /></form:label></td>
	<td><c:out value="${beneFormDuplicato.codImmatricolazioneVeicolo}" /><form:errors path="codImmatricolazioneVeicolo" class="testoRosso"/></td>
</tr>
<tr>
	<td><form:label path="targa"><spring:message code="bene.mobileRegistrato.targa" /></form:label></td>
	<td><c:out value="${beneFormDuplicato.targa}" /><form:errors path="targa" class="testoRosso"/></td>
	
	<td><form:label path="telaio"><spring:message code="bene.mobileRegistrato.veicolo.telaio" /></form:label></td>
	<td><c:out value="${beneFormDuplicato.telaio}" /><form:errors path="telaio" class="testoRosso"/></td>
</tr>
<%-- <tr>
	<td><form:label path="coloreVeicolo"><spring:message code="bene.mobileRegistrato.veicolo.colore" /></form:label></td>
	<td><c:out value="${beneFormDuplicato.coloreVeicolo}" /></td>
	
	<td><form:label path="numeroIdentificazione"><spring:message code="bene.mobileRegistrato.veicolo.numeroIdentificazione" /></form:label></td>
	<td><c:out value="${beneFormDuplicato.numeroIdentificazione}" /></td>
</tr>

<tr>
	<td><form:label path="comboDisponibilita"><spring:message code="bene.disponibilita" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormDuplicato.comboDisponibilita.descrizione}" /></td>
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