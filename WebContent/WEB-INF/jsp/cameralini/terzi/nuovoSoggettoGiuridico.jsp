<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione" value="/procedimento/nuovo/terzi/ricerca.htm" />
			<form:form method="post" action="${azione}" commandName="form">
				<form:hidden path="soggettoForm.idSoggetto" />
				<form:hidden path="soggettoForm.idVersione" />
				<form:hidden path="soggettoForm.tipoSoggetto" value="<%= it.giustizia.smp.utility.Constants.SOGGETTO_GIURIDICO%>"/>
				<form:hidden path="soggettoForm.descrRuolo" />
				<form:hidden path="soggettoForm.idRuolo" />
				<form:hidden path="soggettoForm.chiave"/>	
				<form:hidden path="soggettoForm.from"/>		
				
				<p class="titolo_page"><spring:message code="nuovosoggettogiuridico.titolo" /></p>
				<logic:present name="procedimento">
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td colspan="6" align="left"><spring:message code="cameralini.dettaglioProcedimento" /></td>
						</tr>
			  			<jsp:include page="/WEB-INF/jsp/cameralini/intestazioneDettaglio.jsp" />
				     </table>
				</logic:present>
				<br/>	
										
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>

					<tr>
						<td><form:label path="soggettoForm.ragioneSociale" cssClass="required"><spring:message code="soggettogiuridico.ragioneSociale" /></form:label></td>
						<td><form:input path="soggettoForm.ragioneSociale"  maxlength="120" size="40"/> 
							<form:errors path="soggettoForm.ragioneSociale" class="testoRosso"/></td>
						<td><form:label path="soggettoForm.partitaIva" ><spring:message code="soggettogiuridico.partitaIva" /></form:label></td>
						<td>
							<form:select id="id_StatoSiglaPiva" path="soggettoForm.partitaIvaSiglaStato" size="1" >
								<%-- <form:option value="" label="  "/> --%>
								<form:options items="${statiPiva}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							
							<form:input path="soggettoForm.partitaIva" maxlength="12" size="18"/>
							<input id="verificaPI" name="verificaPI" type="submit" value="Verifica PI" title="Verifica della Partita Iva">
							<form:errors path="soggettoForm.partitaIva" class="testoRosso"/>
							
						</td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.tipoSocieta" >
								<spring:message code="soggettogiuridico.tipoSocieta" />
							</form:label></td>
						<td>
							<form:select path="soggettoForm.idTipoSocieta" size="1">
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${tipiSocieta}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:errors path="soggettoForm.idTipoSocieta" class="testoRosso"/>
						</td>
						<td><form:label path="soggettoForm.numeroCCIAA">
								<spring:message code="soggettogiuridico.numeroCCIAA" /></form:label></td>
						<td><form:input path="soggettoForm.numeroCCIAA"  maxlength="30" size="30"/>
						</td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettogiuridico.titolo1" /></td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.statoSedeLegale.id" ><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoSedeLegale"  path="soggettoForm.statoSedeLegale.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneSedeLegale')" >
								<form:option value="-1" label="---Seleziona Stato---"/>
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:errors path="soggettoForm.statoSedeLegale.id" class="testoRosso"/>
						</td>		
						<td><form:label path="soggettoForm.comuneSedeLegale.id" ><spring:message code="comune"/></form:label></td>
			        	<td>
							<div class="ui-widget">		
								<form:input id="id_descrizioneComuneSedeLegale" path="soggettoForm.comuneSedeLegale.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComune"></div>
								<form:hidden id="id_hiddenComuneSedeLegale" path="soggettoForm.comuneSedeLegale.id"/>
							</div>
							<form:errors path="soggettoForm.comuneSedeLegale.id" class="testoRosso"/>
			        	</td>	
					</tr>
					<tr>
						<td><form:label path="soggettoForm.indirizzoSedeLegale.toponimo" ><spring:message code="indirizzo" /></form:label></td>
						<td>						
							<form:select path="soggettoForm.indirizzoSedeLegale.toponimo.id" size="1">	
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:input path="soggettoForm.indirizzoSedeLegale.denominazione" maxlength="50" size="40"/>
						</td>
						<td><form:label path="soggettoForm.indirizzoSedeLegale.civico">
								<spring:message code="civico" />
							</form:label></td>
						<td colspan="2"><form:input path="soggettoForm.indirizzoSedeLegale.civico" maxlength="20" size="20"/></td>
					</tr>
					
					<tr>
						<td  class="td_titolo" colspan="4"><spring:message code="nuovosoggettogiuridico.titolo2" /></td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.statoDomicilioEletto.id"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoDomicilioEletto"  path="soggettoForm.statoDomicilioEletto.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneDomicilioEletto')">	
								<form:option value="-1" label="---Seleziona Stato---"/>
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>					
						<td><form:label path="soggettoForm.comuneDomicilioEletto.id" ><spring:message code="comune"/></form:label></td>
			        	 <td>
				        	<div class="ui-widget">		
								<form:input id="id_descrizioneComuneDomicilioEletto" path="soggettoForm.comuneDomicilioEletto.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComuneDomicilioEletto"></div>
								<form:hidden id="id_hiddenComuneDomicilioEletto" path="soggettoForm.comuneDomicilioEletto.id"/>
							</div>
				        </td>		
					</tr>
					<tr>
						<td><form:label path="soggettoForm.indirizzoDomicilioEletto.toponimo.id">
								<spring:message code="indirizzo" />
							</form:label></td>
						<td>
							<form:select path="soggettoForm.indirizzoDomicilioEletto.toponimo.id" size="1">	
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:input path="soggettoForm.indirizzoDomicilioEletto.denominazione" maxlength="50" size="40"/>
						</td>
						<td><form:label path="soggettoForm.indirizzoDomicilioEletto.civico">
								<spring:message code="civico" />
							</form:label></td>
						<td colspan="2"><form:input path="soggettoForm.indirizzoDomicilioEletto.civico" maxlength="20" size="20"/></td>	
						</tr>
						<tr>
						<td><form:label path="soggettoForm.pressoIndirizzoDomicilioEletto">
								<spring:message code="presso" />
							</form:label></td>
						<td colspan="2"><form:input path="soggettoForm.pressoIndirizzoDomicilioEletto" maxlength="20" size="20"/></td>	
										
					</tr>
				
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>

					<tr>
						<td><form:label path="soggettoForm.rappresentanteSociale"><spring:message code="soggettogiuridico.rappresentanteSociale" /></form:label></td>
						<td><form:input path="soggettoForm.rappresentanteSociale" maxlength="200" size="40"/>
							<form:errors path="soggettoForm.rappresentanteSociale" class="testoRosso"/></td>
						<td><form:label path="soggettoForm.amministratore"><spring:message code="soggettogiuridico.amministratore" /></form:label></td>
						<td><form:input path="soggettoForm.amministratore" maxlength="200" size="40"/>
							<form:errors path="soggettoForm.amministratore" class="testoRosso"/></td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.capitaleSociale"><spring:message code="soggettogiuridico.capitaleSociale" /></form:label></td>
						<td><form:input path="soggettoForm.capitaleSociale" maxlength="15" size="20"/>
							<form:errors path="soggettoForm.capitaleSociale" class="testoRosso"/>
						</td>
						<td><form:label path="soggettoForm.settoreAttivita.id">
								<spring:message code="soggettogiuridico.settoreAttivita" />
							</form:label></td>
							<td><form:select path="soggettoForm.settoreAttivita.id" size="1">	
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${settoriAttivita}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select></td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.annotazioni"><spring:message code="annotazioni" /></form:label></td>
						<td colspan="3"><form:textarea path="soggettoForm.annotazioni" cssStyle="width:98%" rows="5" /></td>
					</tr>
				</table>

				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" name="InserisciNuovoSoggettoGiuridico"	title="Inserisci nuovo soggetto giuridico"
							value="Inserisci" onclick="return submitWithAutocompleteComuni(['StatoSedeLegale','StatoDomicilioEletto'],['ComuneSedeLegale','ComuneDomicilioEletto'],'write')"></td>
						<td class="td_tasto">
							<input id="backButton" type="button" value="Indietro" title="Torna alla ricerca dei soggetti giuridici da associare">
						</td>
							
					</tr>
				</table>
				<br/>
				<br/>
			</form:form>
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>
			
			

			<c:if test="${form.soggettoForm.idSoggetto == null}">
			<script type="text/javascript">
				$(document).ready(function() {		
					inizializzaSubmitAutocomplete('Inserisci');
					inizializzaSubmitAutocomplete('Salva');
					inizializzaSubmitAutocomplete('InserisciEAssocia');
					controllaInizializzazioneCampi($( "#id_StatoSedeLegale" ).val(),"ComuneSedeLegale");
					controllaInizializzazioneCampi($( "#id_StatoDomicilioEletto" ).val(),"ComuneDomicilioEletto");
				});
			</script>
			</c:if>
			<c:if test="${form.soggettoForm.idSoggetto != null}">
			<script type="text/javascript">
				$(document).ready(function() {					
					controllaInizializzazioneCampi($( "#id_StatoDomicilioEletto" ).val(),"ComuneDomicilioEletto");
				});
			</script>			
			</c:if>
			
			<script type="text/javascript">
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