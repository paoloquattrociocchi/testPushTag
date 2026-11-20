<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


					<tr>
						<td><form:label path="ragioneSociale"><spring:message code="soggettogiuridico.ragioneSociale" /></form:label></td>
						<td colspan="3"><c:out value="${beneForm.ragioneSociale}" /></td>
					</tr>
					<tr>
						<td><form:label path="stato.descrizione" ><spring:message code="stato"/></form:label></td>
	        		    <td colspan="3"><c:out value="${beneForm.stato.descrizione}" /></td>
					</tr>	
					<tr>
					    <td><form:label path="comune.descrizione" ><spring:message code="comune"/></form:label></td>					
		        	    <td colspan="3"><c:out value="${beneForm.comune.descrizione}" /></td>
					</tr>					
					<tr>
					    <td><form:label path="indirizzo"><spring:message code="indirizzo" /></form:label></td>
						<td><c:out value="${beneForm.indirizzo.toponimo.descrizione}" /> <c:out value="${beneForm.indirizzo.denominazione}" /></td>
						
						<td><form:label path="indirizzo.civico"><spring:message code="civico" /></form:label></td>
						<td><c:out value="${beneForm.indirizzo.civico}" /></td>
					</tr>														
			
					<tr>
						<td><form:label path="partitaIva"><spring:message code="soggettogiuridico.partitaIva" /></form:label></td>
						<td><c:out value="${beneForm.partitaIvaCompleta}" /></td>
						
						<td><form:label path="codiceFiscale"><spring:message code="codiceFiscale" /></form:label></td>
						<td><c:out value="${beneForm.codiceFiscale}" /></td>
					</tr>
					<tr>
						<td><form:label path="numeroCCIAA"><spring:message code="soggettogiuridico.numeroCCIAA" /></form:label></td>
						<td><c:out value="${beneForm.numeroCCIAA}" /></td>	
		
						<td><form:label path="capitaleSociale"><spring:message code="soggettogiuridico.capitaleSociale" /></form:label></td>
						<td>
							<c:if test="${beneForm.capSocDescr=='int'}">
								<fmt:formatNumber type="number" maxFractionDigits="2" value="${beneForm.capitaleSociale}" />
							</c:if>
							<c:if test="${beneForm.capSocDescr=='noInt'}">
								<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${beneForm.capitaleSociale}" />
							</c:if>
						</td>		
					</tr>
					<tr>
						<td><form:label path="amministratore"><spring:message code="soggettogiuridico.amministratore" /></form:label></td>
						<td><c:out value="${beneForm.amministratore}" /></td>	

						<td><form:label path="rappresentanteSociale"><spring:message code="soggettogiuridico.rappresentanteSociale" /></form:label></td>
						<td><c:out value="${beneForm.rappresentanteSociale}" /></td>		
					</tr>
					<tr>
						<td><form:label path="numeroDipendenti"><spring:message code="bene.azienda.numerodipendenti" /></form:label></td>
						<td colspan="3"><c:out value="${beneForm.numeroDipendenti}" /></td>							
					</tr>
					<tr>
						<td><form:label path="statoConservazione.descrizione"><spring:message code="bene.statoconservazione" /></form:label></td>
						<c:if test="${beneForm.dettAssBene=='Dettaglio'}">
							<td colspan="3"><c:out value="${beneForm.statoConservazione.descrizione}" /></td>
						</c:if>
						
						<c:if test="${beneForm.dettAssBene=='Associa'}">
							<td><form:select path="statoConservazione.id" size="1">
									<form:option value="0" label="-------Seleziona Stato Conservazione-------" />
									<form:options items="${statiConservazione}" itemValue="id" itemLabel="descrizione"/>
								</form:select>
							</td>
						</c:if>
					</tr>
					<tr>
						<td><form:label path="settoreAttivita.descrizione"><spring:message code="soggettogiuridico.settoreAttivita" /></form:label></td>
						<td colspan="3"><c:out value="${beneForm.settoreAttivita.descrizione}" /></td>
					</tr>
					<tr>
						<td><form:label path="gravame"><spring:message code="bene.gravame" /></form:label></td>
						<td>
						 <form:radiobutton disabled="true" path="gravame" value="S" label="SI"/>
						 <form:radiobutton disabled="true" path="gravame" value="N" label="NO"/>
					   </td>
						<td><form:label path="note"><spring:message code="note" /></form:label></td>
						<td><c:out value="${beneForm.note}" /></td>							
					</tr>
					<tr>
						<td><form:label path="costituitoInAzienda"><spring:message code="bene.azienda.costituitoinazienda" /></form:label>
						</td>
						<td>
						 <form:radiobutton disabled="true" path="costituitoInAzienda" value="S" label="SI"/>
						 <form:radiobutton disabled="true" path="costituitoInAzienda" value="N" label="NO"/>
						</td>
						
						<td><form:label path="stima"><spring:message code="bene.azienda.stima" /></form:label></td>
						<td><c:out value="${beneForm.stima}" /></td>						
					</tr>

