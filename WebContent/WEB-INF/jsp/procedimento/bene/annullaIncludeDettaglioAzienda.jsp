<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tr>
	<td class="td_titolo" colspan="4"><spring:message code="bene.azienda.dati" /></td>
</tr>

<tr>
	<td><form:label path="ragioneSociale"><spring:message code="soggettogiuridico.ragioneSociale" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormValido.ragioneSociale}" /><form:errors path="ragioneSociale" class="testoRosso"/></td>
</tr>
<tr>
	<td><form:label path="stato.descrizione" ><spring:message code="stato"/></form:label></td>
    		    <td colspan="3"><c:out value="${beneFormValido.stato.descrizione}" /><form:errors path="stato.descrizione" class="testoRosso"/></td>
</tr>	
<tr>
    <td><form:label path="comune.descrizione" ><spring:message code="comune"/></form:label></td>					
     	    <td colspan="3"><c:out value="${beneFormValido.comune.descrizione}" /><form:errors path="comune.descrizione" class="testoRosso"/></td>
</tr>					
<%-- <tr>
    <td><form:label path="indirizzo"><spring:message code="indirizzo" /></form:label></td>
	<td><c:out value="${beneFormValido.indirizzo.toponimo.descrizione}" /> <c:out value="${beneFormValido.indirizzo.denominazione}" /></td>
	
	<td><form:label path="indirizzo.civico"><spring:message code="civico" /></form:label></td>
	<td><c:out value="${beneFormValido.indirizzo.civico}" /></td>
</tr>	 --%>													

<tr>
	<td><form:label path="partitaIva"><spring:message code="soggettogiuridico.partitaIva" /></form:label></td>
	<td><c:out value="${beneFormValido.partitaIvaCompleta}" /><form:errors path="partitaIva" class="testoRosso"/></td>
	
	<td><form:label path="codiceFiscale"><spring:message code="codiceFiscale" /></form:label></td>
	<td><c:out value="${beneFormValido.codiceFiscale}" /><form:errors path="codiceFiscale" class="testoRosso"/></td>
</tr>
<%-- <tr>
	<td><form:label path="numeroCCIAA"><spring:message code="soggettogiuridico.numeroCCIAA" /></form:label></td>
	<td><c:out value="${beneFormValido.numeroCCIAA}" /></td>	

	<td><form:label path="capitaleSociale"><spring:message code="soggettogiuridico.capitaleSociale" /></form:label></td>
	<td>
		<c:if test="${beneFormValido.capSocDescr=='int'}">
			<fmt:formatNumber type="number" maxFractionDigits="2" value="${beneFormValido.capitaleSociale}" />
		</c:if>
		<c:if test="${beneFormValido.capSocDescr=='noInt'}">
			<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${beneFormValido.capitaleSociale}" />
		</c:if>
	</td>		
</tr>
<tr>
	<td><form:label path="amministratore"><spring:message code="soggettogiuridico.amministratore" /></form:label></td>
	<td><c:out value="${beneFormValido.amministratore}" /></td>	

	<td><form:label path="rappresentanteSociale"><spring:message code="soggettogiuridico.rappresentanteSociale" /></form:label></td>
	<td><c:out value="${beneFormValido.rappresentanteSociale}" /></td>		
</tr>
<tr>
	<td><form:label path="numeroDipendenti"><spring:message code="bene.azienda.numerodipendenti" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormValido.numeroDipendenti}" /></td>							
</tr>
<tr>
	<td><form:label path="statoConservazione.descrizione"><spring:message code="bene.statoconservazione" /></form:label></td>
	<c:if test="${beneFormValido.dettAssBene=='Dettaglio'}">
		<td colspan="3"><c:out value="${beneFormValido.statoConservazione.descrizione}" /></td>
	</c:if>
	
	<c:if test="${beneFormValido.dettAssBene=='Associa'}">
		<td><form:select path="statoConservazione.id" size="1">
				<form:option value="0" label="-------Seleziona Stato Conservazione-------" />
				<form:options items="${statiConservazione}" itemValue="id" itemLabel="descrizione"/>
			</form:select>
		</td>
	</c:if>
</tr>
<tr>
	<td><form:label path="settoreAttivita.descrizione"><spring:message code="soggettogiuridico.settoreAttivita" /></form:label></td>
	<td colspan="3"><c:out value="${beneFormValido.settoreAttivita.descrizione}" /></td>
</tr>
<tr>
	<td><form:label path="gravame"><spring:message code="bene.gravame" /></form:label></td>
	<td>
	 <form:radiobutton disabled="true" path="gravame" value="S" label="SI"/>
	 <form:radiobutton disabled="true" path="gravame" value="N" label="NO"/>
   </td>
	<td><form:label path="note"><spring:message code="note" /></form:label></td>
	<td><c:out value="${beneFormValido.note}" /></td>							
</tr>
<tr>
	<td><form:label path="costituitoInAzienda"><spring:message code="bene.azienda.costituitoinazienda" /></form:label>
	</td>
	<td>
	 <form:radiobutton disabled="true" path="costituitoInAzienda" value="S" label="SI"/>
	 <form:radiobutton disabled="true" path="costituitoInAzienda" value="N" label="NO"/>
	</td>
	
	<td><form:label path="stima"><spring:message code="bene.azienda.stima" /></form:label></td>
	<td><c:out value="${beneFormValido.stima}" /></td>						
</tr>
 --%>
