<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/procedimento/atto/versionamentoSoggettoGiuridico.htm" />
			<form:form method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			
				<form:hidden path="soggetto.idSoggetto" />
				<form:hidden path="soggetto.idVersione" />
				<form:hidden path="soggetto.tipoSoggetto" value="<%= it.giustizia.smp.utility.Constants.SOGGETTO_GIURIDICO%>"/>
			
				<p class="titolo_page"><spring:message code="soggettogiuridico.versionamento.dettaglio"/></p>
				<logic:present name="procedimento">
					<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
					 <br/>
					 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				</logic:present>
				<br/>				
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">	
					
					<tr>
						<td><form:label path="soggetto.ragioneSociale" cssClass="required"><spring:message code="soggettogiuridico.ragioneSociale" /></form:label></td>
						<td><form:input path="soggetto.ragioneSociale"  maxlength="120" size="40"/> 
							<form:errors path="soggetto.ragioneSociale" class="testoRosso"/></td>
						<td><form:label path="soggetto.partitaIva" cssClass="required"><spring:message code="soggettogiuridico.partitaIva" /></form:label></td>
						<td>
							<form:select id="id_StatoSiglaPiva" path="soggetto.partitaIvaSiglaStato" size="1" >
								<form:options items="${statiPiva}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							
							<form:input path="soggetto.partitaIva" maxlength="12" size="18"/>
							<input id="verificaPI" name="verificaPI" type="submit" value="Verifica PI" title="Verifica della Partita Iva">
						
							<form:errors path="soggetto.partitaIva" class="testoRosso"/>
						</td>
					</tr>
					<tr>
						<td><form:label path="soggetto.tipoSocieta" cssClass="required">
								<spring:message code="soggettogiuridico.tipoSocieta" />
							</form:label></td>
						<td>
							<form:select path="soggetto.idTipoSocieta" size="1">
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${tipiSocieta}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:errors path="soggetto.idTipoSocieta" class="testoRosso"/>
						</td>
						<td><form:label path="soggetto.numeroCCIAA">
								<spring:message code="soggettogiuridico.numeroCCIAA" /></form:label></td>
						<td><form:input path="soggetto.numeroCCIAA"  maxlength="30" size="30"/>
						</td>
					</tr>			
					<tr>
						<td class="td_titolo" colspan="4"><spring:message
								code="nuovosoggettogiuridico.titolo1" /></td>
					</tr>
					<tr>
						<td><form:label path="soggetto.statoSedeLegale.id" cssClass="required"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoSedeLegale"  path="soggetto.statoSedeLegale.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneSedeLegale')" >
								<form:option value="-1" label="---Seleziona Stato---"/>
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:errors path="soggetto.statoSedeLegale.id" class="testoRosso" />
						</td>		
						<td><form:label path="soggetto.comuneSedeLegale.id" cssClass="required"><spring:message code="comune"/></form:label></td>
			        	<td>
							<div class="ui-widget">		
								<form:input id="id_descrizioneComuneSedeLegale" path="soggetto.comuneSedeLegale.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComune"></div>
								<form:hidden id="id_hiddenComuneSedeLegale" path="soggetto.comuneSedeLegale.id"/>
							</div>
							<form:errors path="soggetto.comuneSedeLegale.id" class="testoRosso" />
			        	</td>	
					</tr>
					<tr>
						<td><form:label path="soggetto.indirizzoSedeLegale.toponimo" ><spring:message code="indirizzo" /></form:label></td>
						<td>						
							<form:select path="soggetto.indirizzoSedeLegale.toponimo.id" size="1">	
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:input path="soggetto.indirizzoSedeLegale.denominazione" maxlength="50" size="40"/>
						</td>
						<td><form:label path="soggetto.indirizzoSedeLegale.civico">
								<spring:message code="civico" />
							</form:label></td>
						<td colspan="2"><form:input path="soggetto.indirizzoSedeLegale.civico" maxlength="20" size="20"/></td>
					</tr>
				
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>
					<tr>
						<td><form:label path="soggetto.rappresentanteSociale"><spring:message code="soggettogiuridico.rappresentanteSociale" /></form:label></td>
						<td><form:input path="soggetto.rappresentanteSociale" maxlength="200" size="40"/>
							<form:errors path="soggetto.rappresentanteSociale" class="testoRosso"/></td>
						<td><form:label path="soggetto.amministratore"><spring:message code="soggettogiuridico.amministratore" /></form:label></td>
						<td><form:input path="soggetto.amministratore" maxlength="200" size="40"/>
							<form:errors path="soggetto.amministratore" class="testoRosso"/></td>
					</tr>
					<tr>
						<td><form:label path="soggetto.capitaleSociale"><spring:message code="soggettogiuridico.capitaleSociale" /></form:label></td>
						<td><form:input path="soggetto.capitaleSociale" maxlength="15" size="20"/>
							<form:errors path="soggetto.capitaleSociale" class="testoRosso"/>
						</td>
						<td><form:label path="soggetto.settoreAttivita.id">
								<spring:message code="soggettogiuridico.settoreAttivita" />
							</form:label></td>
							<td><form:select path="soggetto.settoreAttivita.id" size="1">	
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${settoriAttivita}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select></td>
					</tr>
					<tr>
						<td><form:label path="soggetto.annotazioni"><spring:message code="annotazioni" /></form:label></td>
						<td colspan="3"><form:textarea path="soggetto.annotazioni" cssStyle="width:98%" rows="5" /></td>
					</tr>
					<tr>
						<td>&nbsp;</td>	
					</tr>		
				   <tr>
						<td> <form:label path="soggetto.magistrato.id"><spring:message code="atto.versionamento.magistrato" /></form:label></td>
						<td colspan="3"><form:select  path="soggetto.magistrato.id" size="1" >
								<form:option value="-1" label="---Seleziona Magistrato---"/>
								<form:options items="${magistrati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:errors path="soggetto.magistrato.id" class="testoRosso" />
						</td>	
				 </tr>					

				  <tr>
					<td><form:label path="dataEmissione">
							<spring:message code="atto.dataemissione" />
						</form:label></td>
					<td><form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" /> <form:errors path="dataEmissione" class="testoRosso" /></td>
					<td><form:label path="dataDeposito">
							<spring:message code="atto.datadeposito" />
						</form:label></td>
					<td><form:input path="dataDeposito" maxlength="10" id="id_dataDeposito" /> <form:errors path="dataDeposito" class="testoRosso" /></td>
				  </tr>	
			    </table>
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp"/>
		    
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" name="Conferma" title="Conferma Versionamento" value="Conferma"></td>
					
						<td class="td_tasto"><input type="button" id="backButton" value="Indietro" name="Indietro"></td>
					</tr>
				</table>				

			</form:form>
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>
			
			
			<script type="text/javascript">
				$(document).ready(function() {		

					prendiData("#id_dataEmissione",true);
					prendiData("#id_dataDeposito",true);
					
					controllaInizializzazioneCampi($( "#id_StatoSedeLegale" ).val(),"ComuneSedeLegale");
					controllaInizializzazioneCampi($( "#id_StatoDomicilioEletto" ).val(),"ComuneDomicilioEletto");
				});
	

				if($( "#id_StatoSedeLegale" ).val() == 1)
				{
					doAutocomplete('ComuneSedeLegale');
				}
				if($( "#id_StatoDomicilioEletto" ).val() == 1)
				{
					doAutocomplete('ComuneDomicilioEletto');
				}
			</script>			
		</tiles:putAttribute>
	</tiles:insertDefinition>
