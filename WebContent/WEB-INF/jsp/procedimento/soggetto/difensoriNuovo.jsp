<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>



<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/soggetto/difensoriNuovo.htm" />
		<p class="titolo_page"><spring:message code="soggetto.difensori.nuovo" /></p>
		<form:form method="post" action="${azione}" commandName="difensoreSoggettoForm">
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br/>
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><c:out value="${difensoreSoggettoForm.soggettoForm.descrizioneSoggetto}" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetti.difensore.difensore" /></td>
				</tr>
				<tr>
					<td><form:label path="difensoreForm.cognome" cssClass="required"><spring:message code="cognome" /></form:label></td>
					<td><form:input path="difensoreForm.cognome" maxlength="35" size="30"/>
									<form:errors path="difensoreForm.cognome" class="testoRosso"/></td>
					<td><form:label path="difensoreForm.nome" cssClass="required"><spring:message code="nome" /></form:label></td>
					<td><form:input path="difensoreForm.nome" maxlength="35" size="30"/>
									<form:errors path="difensoreForm.nome" class="testoRosso"/></td>
				</tr>
				<tr>
					<td><form:label path="dataNomina" cssClass="required"><spring:message code="soggetto.difensore.datanomina" /></form:label></td>
					<td><form:input path="dataNomina" maxlength="10" size="12" id="dataNomina"/>
							<form:errors path="dataNomina" class="testoRosso" /></td>
					<td><form:label path="tipoNomina" cssClass="required"><spring:message code="soggetto.difensore.tiponomina" /></form:label></td>
					<td><form:select path="tipoNomina.id" size="1">
							<form:option value="-1" label="----Seleziona tipo nomina-----"/>
							<form:option value="U" label="D'ufficio"/>
							<form:option value="F" label="Di fiducia"/> 																	
						</form:select>
						<form:errors path="tipoNomina.id" class="testoRosso" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo1" /></td>
				</tr>
				<tr>
					<td><form:label path="difensoreForm.dataNascita"><spring:message code="dataNascita" /></form:label></td>
					<td colspan="3">
							<form:input path="difensoreForm.dataNascita" maxlength="10" size="12" id="dataNascita"/>
							<form:errors path="difensoreForm.dataNascita" class="testoRosso" />
						
					</td>								
				</tr>
				<tr>
					<td><form:label path="difensoreForm.statoNascita.descrizione"><spring:message code="stato" /></form:label></td>
					<td><form:select id="id_StatoNascita" path="difensoreForm.statoNascita.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneNascitaCombo'); assegnaDescrizione('StatoNascita') ">
							<form:option value="-1" label="---Seleziona Stato---"/>
							<form:options items="${statiNascita}"  itemValue="id" itemLabel="descrizione" /> 																	
						</form:select>
						<form:hidden path="difensoreForm.statoNascita.descrizione" id="descrizione_StatoNascita"/>
					</td>						
					<td><form:label path="difensoreForm.comuneNascitaCombo.id" ><spring:message code="comune"/></form:label></td>
		        	<td>
						<div class="ui-widget">		
							<form:input id="id_descrizioneComuneNascitaCombo" path="difensoreForm.comuneNascitaCombo.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
							<div id="id_loadingComuneNascitaCombo"></div>
							<form:hidden id="id_hiddenComuneNascitaCombo" path="difensoreForm.comuneNascitaCombo.id"/>
						</div>
		        	</td>
		        								
		        		
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo2" /></td>
				</tr>
				<tr>
					<td><form:label path="difensoreForm.codiceFiscale"><spring:message code="codiceFiscale" /></form:label></td>
					<td><form:input path="difensoreForm.codiceFiscale" maxlength="16" size="25"/>
						<form:errors path="difensoreForm.codiceFiscale" class="testoRosso" /></td>
					<td><form:label path="difensoreForm.pec"><spring:message code="pec" /></form:label></td>
					<td><form:input path="difensoreForm.pec" maxlength="255" size="30"/>
						<form:errors path="difensoreForm.pec" class="testoRosso" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo3" /></td>
				</tr>
				<tr>
					<td><form:label path="difensoreForm.statoResidenza.descrizione"><spring:message code="stato" /></form:label></td>
					<td>
						<form:select id="id_StatoResidenza" path="difensoreForm.statoResidenza.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneResidenzaCombo'); assegnaDescrizione('StatoResidenza')">	
							<form:option value="-1" label="---Seleziona Stato---"/>
							<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
						</form:select>
						<form:hidden path="difensoreForm.statoResidenza.descrizione" id="descrizione_StatoResidenza"/>
					</td>						
					<td><form:label path="difensoreForm.comuneResidenzaCombo.descrizione" ><spring:message code="comune"/></form:label></td>
		        	<td>
						<div class="ui-widget">		
							<form:input id="id_descrizioneComuneResidenzaCombo" path="difensoreForm.comuneResidenzaCombo.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
							<div id="id_loadingComuneResidenzaCombo"></div>
							<form:hidden id="id_hiddenComuneResidenzaCombo" path="difensoreForm.comuneResidenzaCombo.id"/>
						</div>
		        	</td>	
				</tr>
				<tr>
					<td><form:label path="difensoreForm.indirizzoResidenzaForm"><spring:message code="indirizzo" /></form:label></td>
					<td>
						<form:select path="difensoreForm.indirizzoResidenzaForm.toponimo.descrizione" size="1">
						 <form:option value="-1" label="------Seleziona Tipo Strada-------"/>
							<form:options items="${tipiStrada}"  itemValue="descrizione" itemLabel="descrizione" />
						</form:select> 	
						<form:input path="difensoreForm.indirizzoResidenzaForm.denominazione" maxlength="50" size="30"/>
					</td>
					<td><form:label path="difensoreForm.indirizzoResidenzaForm.civico"><spring:message code="civico" /></form:label></td>
					<td><form:input path="difensoreForm.indirizzoResidenzaForm.civico" maxlength="20" size="20"/>
						<form:label path="difensoreForm.capResidenza"><spring:message code="cap" /></form:label>
						<form:input path="difensoreForm.capResidenza" maxlength="5" size="6"/>
						<form:errors path="difensoreForm.capResidenza" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="difensoreForm.telefonoResidenza"><spring:message code="soggetto.difensore.telefono" /></form:label></td>
					<td><form:input path="difensoreForm.telefonoResidenza" maxlength="15" size="17"/>
						<form:errors path="difensoreForm.telefonoResidenza" class="testoRosso" /></td>
					<td><form:label path="difensoreForm.faxResidenza"><spring:message code="soggetto.difensore.fax" /></form:label></td>
					<td><form:input path="difensoreForm.faxResidenza" maxlength="15" size="17"/>
						<form:errors path="difensoreForm.faxResidenza" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="difensoreForm.emailResidenza"><spring:message code="soggetto.difensore.email" /></form:label></td>
					<td colspan="3"><form:input path="difensoreForm.emailResidenza" maxlength="256" size="40"/>
						<form:errors path="difensoreForm.emailResidenza" class="testoRosso" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.difensore.domiciliolegale" /></td>
				</tr>
				<tr>
					<td><form:label path="difensoreForm.statoDomicilioLegale.descrizione"><spring:message code="stato" /></form:label></td>
					<td>
						<form:select id="id_StatoDomicilioLegale" path="difensoreForm.statoDomicilioLegale.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneDomicilioLegaleCombo'); assegnaDescrizione('StatoDomicilioLegale')" >
							<form:option value="-1" label="---Seleziona Stato---"/>
							<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
						</form:select>
						<form:hidden path="difensoreForm.statoDomicilioLegale.descrizione" id="descrizione_StatoDomicilioLegale"/>
					</td>								
					<td><form:label path="difensoreForm.comuneDomicilioLegaleCombo.descrizione" ><spring:message code="comune"/></form:label></td>
		        	<td>
						<div class="ui-widget">		
							<form:input id="id_descrizioneComuneDomicilioLegaleCombo" path="difensoreForm.comuneDomicilioLegaleCombo.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
							<div id="id_loadingComuneDomicilioLegaleCombo"></div>
							<form:hidden id="id_hiddenComuneDomicilioLegaleCombo" path="difensoreForm.comuneDomicilioLegaleCombo.id"/>
						</div>
		        	</td>	
				</tr>
				<tr>
					<td><form:label path="difensoreForm.indirizzoDomicilioLegaleForm"><spring:message code="nuovosoggettofisico.indirizzo" /></form:label></td>
					<td>
						<form:select path="difensoreForm.indirizzoDomicilioLegaleForm.toponimo.descrizione" size="1">
						 <form:option value="-1" label="------Seleziona Tipo Strada-------"/>
							<form:options items="${tipiStrada}"  itemValue="descrizione" itemLabel="descrizione" />
						</form:select> 
						<form:input path="difensoreForm.indirizzoDomicilioLegaleForm.denominazione" maxlength="50" size="30"/></td>
					
					<td><form:label path="difensoreForm.indirizzoDomicilioLegaleForm.civico"><spring:message code="civico" /></form:label></td>
					<td><form:input path="difensoreForm.indirizzoDomicilioLegaleForm.civico" maxlength="20" size="20"/>
						<form:label path="difensoreForm.capDomicilioLegale"><spring:message code="cap" /></form:label>
						<form:input path="difensoreForm.capDomicilioLegale" maxlength="5" size="6"/>
						<form:errors path="difensoreForm.capDomicilioLegale" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="difensoreForm.telefonoDomicilioLegale"><spring:message code="soggetto.difensore.telefono" /></form:label></td>
					<td><form:input path="difensoreForm.telefonoDomicilioLegale" maxlength="15" size="17"/>
						<form:errors path="difensoreForm.telefonoDomicilioLegale" class="testoRosso" /></td>
						
					<td><form:label path="difensoreForm.faxDomicilioLegale"><spring:message code="soggetto.difensore.fax" /></form:label></td>
					<td><form:input path="difensoreForm.faxDomicilioLegale" maxlength="15" size="17"/>
						<form:errors path="difensoreForm.faxDomicilioLegale" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="difensoreForm.emailDomicilioLegale"><spring:message code="soggetto.difensore.email" /></form:label></td>
					<td colspan="3"><form:input path="difensoreForm.emailDomicilioLegale" maxlength="256" size="40"/>
						<form:errors path="difensoreForm.emailDomicilioLegale" class="testoRosso" /></td>
				</tr>
			</table>
			
			<form:hidden path="soggettoForm.idSoggetto"/>
			<form:hidden path="soggettoForm.idVersione"/>
			<form:hidden path="soggettoForm.tipoSoggetto"/>
			<form:hidden path="gratuitoPatrocinioCaricato"/>
			
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto">
						<input type="submit" value="Salva" name="SalvaDifensore"
							onclick="return submitWithAutocompleteComuni(['StatoNascita','StatoResidenza','StatoDomicilioLegale'],['ComuneNascitaCombo','ComuneResidenzaCombo','ComuneDomicilioLegaleCombo'],'write') "/>
					</td>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
		</form:form>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
		<smp:back buttonId="backButton"></smp:back>
		
		<script type="text/javascript">
			$(document).ready(function() {
				prendiData("#dataNascita",true);
				prendiData("#dataNomina",true);
				inizializzaSubmitAutocomplete('SalvaDifensore');
				controllaInizializzazioneCampi($( "#id_StatoNascita" ).val(),"ComuneNascitaCombo");
				controllaInizializzazioneCampi($( "#id_StatoResidenza" ).val(),"ComuneResidenzaCombo");
				controllaInizializzazioneCampi($( "#id_StatoDomicilioLegale" ).val(),"ComuneDomicilioLegaleCombo");
			});
		</script>
		<script type="text/javascript">
		function assegnaDescrizione(nomeCampo){
			document.getElementById('descrizione_'+nomeCampo).value = document.getElementById('id_'+nomeCampo).options[document.getElementById('id_'+nomeCampo).selectedIndex].innerHTML;
			};
		</script>
		
		<script type="text/javascript">
				if($( "#id_StatoNascita" ).val() == 1)
				{
					doAutocomplete('ComuneNascitaCombo');
				}
				if($( "#id_StatoResidenza" ).val() == 1)	
				{
					doAutocomplete('ComuneResidenzaCombo');
				}
				if($( "#id_StatoDomicilioLegale" ).val() == 1)
				{
					doAutocomplete('ComuneDomicilioLegaleCombo');
				}
			</script>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
