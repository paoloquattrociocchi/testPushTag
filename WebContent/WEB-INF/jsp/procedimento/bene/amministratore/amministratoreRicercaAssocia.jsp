<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<c:set var="AGENZIA_BENI_CONFISCATI" value="<%=Constants.AGENZIA_BENI_CONFISCATI%>"/>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/amministratore/ricercaAssocia.htm" />
		<spring:url var="paginazione" value="/procedimento/amministratore/paginazioneRicercaAssocia.htm" />
		<form:form method="post" action="${azione}" commandName="amministratoreForm">
		<form:hidden path="jspProvenienza" />
		<form:hidden path="dettaglio" />
			<p class="titolo_page">
				<spring:message code="ricerca.amministratore.sottotitolo" />
			</p>

			<div>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			</div>
			<br />
				
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="ricerca.amministratore.sottotitolo" /></td>
				</tr>
				<tr>
					<td><form:label path="cognome">
							<spring:message code="cognome" />
						</form:label></td>
					<td><form:input path="cognome" /></td>

					<td><form:label path="nome">
							<spring:message code="nome" />
						</form:label></td>
					<td><form:input path="nome" /></td>
				</tr>
				<tr>
					<td><form:label path="codiceFiscale">
							<spring:message code="codiceFiscale" />
						</form:label></td>
					<td><form:input path="codiceFiscale" maxlength="16" /></td>
				</tr>

				<tr>
					<td><form:label path="alboProfessionale">
							<spring:message code="albo.ordineappartenenza" />
						</form:label></td>
					<td>
						<table>
							<tr>
								<td><form:radiobutton disabled="true" path="alboProfessionale.id" value="<%=Constants.OrdineConstants.K_ORDINE_COMMERCIALISTI%>" style="cursor:pointer" onclick="submit()" /></td>
								<td><form:label path="alboProfessionale.id">
										<spring:message code="albo.ordineappartenenza.commercialisti" />
									</form:label></td>
							</tr>
							<tr>
								<td><form:radiobutton disabled="true" path="alboProfessionale.id" value="<%=Constants.OrdineConstants.K_ORDINE_AVVOCATI%>" style="cursor:pointer" onclick="submit()" /></td>
								<td><form:label path="alboProfessionale.id">
										<spring:message code="albo.ordineappartenenza.avvocati" />
									</form:label></td>
							</tr>
						</table>
					</td>
					<td><form:label path="ambitoTerritoriale">
							<spring:message code="albo.ordineappartenenza.sede" />
						</form:label></td>
					<td><form:select disabled="true" path="ambitoTerritoriale.id" size="1">
							<form:option value="" label="--- Seleziona Sede ---" />
							<form:options items="${sediOrdini}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>

				<tr>
					<td><form:label path="ambitoTerritoriale">
							<spring:message code="albo.titolo.sezione" />
						</form:label></td>
					<td>
						<table>
							<tr>
								<td><form:checkbox disabled="true" path="sezione" value="<%=Constants.SezioniConstants.K_SEZIONE_ORDINARIA%>" style="cursor:pointer" /></td>
								<td><form:label path="sezione">
										<spring:message code="albo.sezione.ordinaria" />
									</form:label></td>
							</tr>
							<tr>
								<td><form:checkbox disabled="true" path="sezione" value="<%=Constants.SezioniConstants.K_SEZIONE_ESPERTI%>" style="cursor:pointer" /></td>
								<td><form:label path="sezione">
										<spring:message code="albo.sezione.esperti" />
									</form:label></td>
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td><form:label path="provinciaDomProf.id">
							<spring:message code="albo.indirizzo.provincia" />
						</form:label></td>
					<td><form:select path="provinciaDomProf.id" size="1" onchange="submit()" style="width:65%">
							<form:option value="" label="--- Seleziona Provincia ---" />
							<form:options items="${provinceDomicilio}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
					<td><form:label path="comuneDomProf.id">
							<spring:message code="albo.indirizzo.comune" />
						</form:label></td>
					<td colspan="2"><form:select path="comuneDomProf.id" size="1" style="width:70%">
							<form:option value="" label="--- Seleziona Comune ---" />
							<form:options items="${comuniDomicilio}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>
			</table>
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Ricerca" name="Ricerca"></td>
				</tr>
			</table>
			<br />
			<br />
			<logic:present name="amministratoriDaAssociare">
				<display:table id="row" name="${amministratoriDaAssociare}" class="tb_lista" uid="amministratoriDaAssociare" pagesize="10" requestURI="${paginazione}">
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
					<form:radiobutton path="id" value="${amministratoriDaAssociare.id}" onclick="disableButtons('${amministratoriDaAssociare.id}')" />
		    		</display:column>
					<display:column property="cognome" title="Cognome" sortable="true" />
					<display:column property="nome" title="Nome" sortable="true" />
					<display:column property="codiceFiscale" title="Codice fiscale" sortable="true" />
					<display:column property="descrIscrizioneAlbo" title="Ordine professionale" sortable="true" />
					<display:column property="dataIscrizioneAlbo" title="Data iscrizione Albo" format="{0,date,dd/MM/yyyy}" sortable="true" />
					<display:column property="comuneDomicilioLegale" title="Domicilio legale" sortable="true" />
				</display:table>
				
				<br />
				<br />
			</logic:present>
		
			
				<table class="tb_bottoni" align="center">
					<tr>
					<logic:notEmpty name="amministratoriDaAssociare">
						<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio" id="btnDettaglio"></td>
					</logic:notEmpty>
					<logic:notEmpty name="amministratoriDaAssociare">
						<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica"></td>
					</logic:notEmpty>
					<logic:notEmpty name="amministratoriDaAssociare">		
						<td class="td_tasto"><input type="submit" value="Associa Beni" name="Associa"></td>
					</logic:notEmpty>
					<logic:present name="amministratoriDaAssociare">
						<td class="td_tasto"><input type="submit" value="Nuovo" name="Nuovo"></td>
					</logic:present>	
					</tr>
				</table>
				<br />
				<br />
				
				
			<script type="text/javascript">

			function disableButtons(idAmm){
				var idAgenzia = ${AGENZIA_BENI_CONFISCATI};
				if(idAmm == idAgenzia) {
					//J.C. Disabilita tasti Agenzia nella ricerca
					if(document.getElementsByName("btnDettaglio").length != 0) {
						document.getElementsByName("btnDettaglio")[0].disabled=true;
					}
					if(document.getElementsByName("Modifica").length != 0) {
						document.getElementsByName("Modifica")[0].disabled=true;
					}

					if(document.getElementsByName("Associa").length != 0) {
						document.getElementsByName("Associa")[0].disabled=false;
					}		
						
					if(document.getElementsByName("Nuovo").length != 0) {
						document.getElementsByName("Nuovo")[0].disabled=false;
					}		
			
				} else {

					//J.C. Disabilita tasti Agenzia nella ricerca
					if(document.getElementsByName("btnDettaglio").length != 0) {
					document.getElementsByName("btnDettaglio")[0].disabled=false;
					}
					
					if(document.getElementsByName("Modifica").length != 0) {
					document.getElementsByName("Modifica")[0].disabled=false;
					}

					if(document.getElementsByName("Associa").length != 0) {
						document.getElementsByName("Associa")[0].disabled=false;
					}		

					if(document.getElementsByName("Nuovo").length != 0) {
						document.getElementsByName("Nuovo")[0].disabled=false;
					}		
												
				}

			}
			
		</script>
				
				
		
		</form:form>

	</tiles:putAttribute>
</tiles:insertDefinition>
