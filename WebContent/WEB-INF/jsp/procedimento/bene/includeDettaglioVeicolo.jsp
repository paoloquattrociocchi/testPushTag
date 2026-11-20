<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

					<tr>
						<td><form:label path="numRegBeneMobileRegistrato"><spring:message code="bene.mobileRegistrato.numeroRegistrazione" /></form:label></td>
						<td colspan="3"><c:out value="${beneForm.numRegBeneMobileRegistrato}" /></td>
					</tr>
					<tr>
						<td><form:label path="dataRegistrazione"><spring:message code="bene.dataRegistrazione" /></form:label></td>
						<td colspan="3"><fmt:formatDate value="${beneForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>
					<tr>
						<td><form:label path="modelloVeicolo"><spring:message code="bene.mobileRegistrato.veicolo.modello" /></form:label></td>
						<td><c:out value="${beneForm.modelloVeicolo}" /></td>
						
						<td><form:label path="codImmatricolazioneVeicolo"><spring:message code="bene.mobileRegistrato.veicolo.codiceImmatricolazione" /></form:label></td>
						<td><c:out value="${beneForm.codImmatricolazioneVeicolo}" /></td>
					</tr>
					<tr>
						<td><form:label path="targa"><spring:message code="bene.mobileRegistrato.targa" /></form:label></td>
						<td><c:out value="${beneForm.targa}" /></td>
						
						<td><form:label path="telaio"><spring:message code="bene.mobileRegistrato.veicolo.telaio" /></form:label></td>
						<td><c:out value="${beneForm.telaio}" /></td>
					</tr>
					<tr>
						<td><form:label path="coloreVeicolo"><spring:message code="bene.mobileRegistrato.veicolo.colore" /></form:label></td>
						<td><c:out value="${beneForm.coloreVeicolo}" /></td>
						
						<td><form:label path="numeroIdentificazione"><spring:message code="bene.mobileRegistrato.veicolo.numeroIdentificazione" /></form:label></td>
						<td><c:out value="${beneForm.numeroIdentificazione}" /></td>
					</tr>
					
					<tr>
						<td><form:label path="comboDisponibilita"><spring:message code="bene.disponibilita" /></form:label></td>
						<td colspan="3"><c:out value="${beneForm.comboDisponibilita.descrizione}" /></td>
					</tr>
					<tr>
						<td><form:label path="gravame"><spring:message code="bene.gravame" /></form:label></td>
						<td>
						  <form:radiobutton disabled="true" path="gravame" value="S" label="SI"/>
						  <form:radiobutton disabled="true" path="gravame" value="N" label="NO"/>
					    </td>
					    <td><form:label path="note"><spring:message code="note" /></form:label></td>
						<td colspan="3"><c:out value="${beneForm.note}" /></td>
					</tr>