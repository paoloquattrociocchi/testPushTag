<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"
				value="/procedimento/nuovo/nuovoSoggettoGiuridico.htm" />
			<form:form method="post" action="${azione}" commandName="soggettoForm">
				<form:hidden path="idSoggetto" />
				<form:hidden path="idVersione" />
				<form:hidden path="tipoSoggetto" value="<%= it.giustizia.smp.utility.Constants.SOGGETTO_GIURIDICO%>"/>
				<form:hidden path="descrRuolo" />
				<form:hidden path="idRuolo" />
				<form:hidden path="chiave"/>	
				<form:hidden path="from"/>		
				<c:if test="${soggettoForm.idSoggetto == null}">
					<p class="titolo_page"><spring:message code="nuovosoggettogiuridico.titolo" /></p>
				</c:if>
				<c:if test="${soggettoForm.idSoggetto != null && (soggettoForm.from == 'modifica' || soggettoForm.from == 'MODIFICA')}">
					<p class="titolo_page"><spring:message code="soggettogiuridico.modifica"/></p>
				</c:if>	
				<c:if test="${soggettoForm.idSoggetto != null && (soggettoForm.from == 'associa' || soggettoForm.from == 'ASSOCIA')}">
					<p class="titolo_page"><spring:message code="soggettogiuridico.associa"/></p>
				</c:if>		
				<logic:present name="procedimento">
					<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
					 <br/>
					 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				</logic:present>
				<br/>	
										
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						
					<logic:equal name="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE%>" scope="session" value="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE_SMP%>" >
					<logic:equal name="soggettoForm" property="descrRuolo" value="DIRETTO" >
					<tr>
						<td><form:label path="tipologiaPrevenzione.id" cssClass="required"><spring:message code="procedimento.tipologiaPrevenzione" /></form:label></td>
						<td><form:select path="tipologiaPrevenzione.id" size="1" onchange="this.form.submit()">
								<form:option value="-1" label="--- Seleziona Tipo Prevenzione ---" />
								<form:options items="${tipiPrevenzione}" itemValue="id" itemLabel="descrizione" /> 
							</form:select>
							<form:errors path="tipologiaPrevenzione.id" class="testoRosso"/>
						</td>
						<td><form:label path="ipotesiMisura.id" cssClass="required"><spring:message code="procedimento.ipotesiMisura" /></form:label></td>
						<td><form:select path="ipotesiMisura.id" size="1" onchange="this.form.submit()">
								<form:option value="-1" label="--- Seleziona Ipotesi Misura ---" />
								<form:options items="${tipiProcedimentoCondizionati}" itemValue="id" itemLabel="descrizione" /> 	
							</form:select>
							<form:errors path="ipotesiMisura.id" class="testoRosso"/>
						</td>
					</tr>
					<tr>
						<td><form:label path="normativa.id" cssClass="required"><spring:message code="procedimento.normativa" /></form:label></td>
						<td colspan="3">
							<form:select path="normativa.id" size="1">
								<form:option value="-1" label="--- Seleziona Normativa ---" />
								<form:options items="${normative}" itemValue="id" itemLabel="descrizione" /> 
							</form:select>
							<form:errors path="normativa.id" class="testoRosso"/>
						</td>
					</tr>
					</logic:equal>
					</logic:equal>
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>
					<c:if test="${soggettoForm.idSoggetto == null}">
					<tr>
						<td><form:label path="ragioneSociale" cssClass="required"><spring:message code="soggettogiuridico.ragioneSociale" /></form:label></td>
						<td><form:input path="ragioneSociale"  maxlength="120" size="40"/> 
							<form:errors path="ragioneSociale" class="testoRosso"/></td>
						<td>
							<c:choose>
								<c:when  test="${ soggettoForm.idRuolo != 3 }">
									<form:label path="partitaIva" cssClass="required"><spring:message code="soggettogiuridico.partitaIva" /></form:label>
								</c:when>
								<c:otherwise>
									<form:label path="partitaIva"><spring:message code="soggettogiuridico.partitaIva" /></form:label>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<form:select id="id_StatoSiglaPiva" path="partitaIvaSiglaStato" size="1" >
								<form:option value="" label="---Seleziona---"/>
								<%-- <form:option value="" label="  "/> --%>
								<form:options items="${statiPiva}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							
							<form:input path="partitaIva" maxlength="12" size="18"/>
							<input id="verificaPI" name="verificaPI" type="submit" value="Verifica Partita Iva" title="Verifica della Partita Iva">
							<form:errors path="partitaIva" class="testoRosso"/>
							
						</td>
					</tr>
					<tr>
						<td>
							<c:choose>
								<c:when  test="${ soggettoForm.idRuolo != 3 }">
									<form:label path="tipoSocieta" cssClass="required"><spring:message code="soggettogiuridico.tipoSocieta" /></form:label>
								</c:when>
								<c:otherwise>
									<form:label path="tipoSocieta"><spring:message code="soggettogiuridico.tipoSocieta" /></form:label>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<form:select path="idTipoSocieta" size="1">
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${tipiSocieta}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:errors path="idTipoSocieta" class="testoRosso"/>
						</td>
						<td><form:label path="numeroCCIAA">
								<spring:message code="soggettogiuridico.numeroCCIAA" /></form:label></td>
						<td><form:input path="numeroCCIAA"  maxlength="30" size="30"/>
						</td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettogiuridico.titolo1" /></td>
					</tr>
					<tr>
						<td>
							<c:choose>
								<c:when  test="${ soggettoForm.idRuolo != 3 }">
									<form:label path="statoSedeLegale.id" cssClass="required"><spring:message code="stato" /></form:label>
								</c:when>
								<c:otherwise>
									<form:label path="statoSedeLegale.id"><spring:message code="stato" /></form:label>
								</c:otherwise>
							</c:choose>							
						</td>
						<td>
							<form:select id="id_StatoSedeLegale"  path="statoSedeLegale.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneSedeLegale')" >
								<form:option value="-1" label="---Seleziona Stato---"/>
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:errors path="statoSedeLegale.id" class="testoRosso"/>
						</td>		
						<td>
							<c:choose>
								<c:when  test="${ soggettoForm.idRuolo != 3 }">
									<form:label path="comuneSedeLegale.id" cssClass="required"><spring:message code="comune" /></form:label>
								</c:when>
								<c:otherwise>
									<form:label path="comuneSedeLegale.id"><spring:message code="comune" /></form:label>
								</c:otherwise>
							</c:choose>							
						</td>
			        	<td>
							<div class="ui-widget">		
								<form:input id="id_descrizioneComuneSedeLegale" path="comuneSedeLegale.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComune"></div>
								<form:hidden id="id_hiddenComuneSedeLegale" path="comuneSedeLegale.id"/>
							</div>
							<form:errors path="comuneSedeLegale.id" class="testoRosso"/>
			        	</td>	
					</tr>
					<tr>
						<td><form:label path="indirizzoSedeLegale.toponimo" ><spring:message code="indirizzo" /></form:label></td>
						<td>						
							<form:select path="indirizzoSedeLegale.toponimo.id" size="1">	
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:input path="indirizzoSedeLegale.denominazione" maxlength="50" size="40"/>
						</td>
						<td><form:label path="indirizzoSedeLegale.civico">
								<spring:message code="civico" />
							</form:label></td>
						<td colspan="2"><form:input path="indirizzoSedeLegale.civico" maxlength="20" size="20"/></td>
					</tr>
					</c:if>

					<c:if test="${soggettoForm.idSoggetto != null}">
					<tr>
						<td><form:label path="ragioneSociale" cssClass="required"><spring:message code="soggettogiuridico.ragioneSociale" /></form:label></td>
						<td><c:out value="${soggettoForm.ragioneSociale}" /><form:hidden path="ragioneSociale" /></td>
						<td><form:label path="partitaIva"><spring:message code="soggettogiuridico.partitaIva" /></form:label></td>
						<td><c:out value="${soggettoForm.partitaIvaCompleta}" /><form:hidden path="partitaIvaCompleta" />
			
						</td>
					</tr>
					<tr>
						<td><form:label path="tipoSocieta" cssClass="required">
								<spring:message code="soggettogiuridico.tipoSocieta" />
							</form:label></td>
						<td>
							<c:out value="${soggettoForm.tipoSocieta}" /><form:hidden path="idTipoSocieta" /><form:hidden path="tipoSocieta" />
						</td>
						<td><form:label path="numeroCCIAA">
								<spring:message code="soggettogiuridico.numeroCCIAA" /></form:label></td>
						<td><form:input path="numeroCCIAA"  maxlength="30" size="30"/>
						</td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettogiuridico.titolo1" /></td>
					</tr>
					<tr>
						<td><form:label path="statoSedeLegale.id"><spring:message code="stato" /></form:label></td>
						<td>
							<c:out value="${soggettoForm.statoSedeLegale.descrizione}" /><form:hidden path="statoSedeLegale.id" /><form:hidden path="statoSedeLegale.descrizione" />
						</td>		
						<td><form:label path="comuneSedeLegale.id" ><spring:message code="comune"/></form:label></td>
			        	<td>
							<c:out value="${soggettoForm.descrizioneComuneSedeLegaleItalianoEstero}" /><form:hidden path="comuneSedeLegale.id" /><form:hidden path="comuneSedeLegale.descrizione" /> <form:hidden path="siglaProvinciaSedeLegale" />
			        	</td>	
					</tr>
					<tr>
						<td><form:label path="indirizzoSedeLegale.toponimo" ><spring:message code="indirizzo" /></form:label></td>
						<td>
							<c:out value="${soggettoForm.indirizzoSedeLegale.toponimo.descrizione}" /><form:hidden path="indirizzoSedeLegale.toponimo.id" /> <form:hidden path="indirizzoSedeLegale.toponimo.descrizione" />
							<c:out value="${soggettoForm.indirizzoSedeLegale.denominazione}" /><form:hidden path="indirizzoSedeLegale.denominazione" />
						</td>
						<td><form:label path="indirizzoSedeLegale.civico">
								<spring:message code="civico" />
							</form:label></td>
						<td colspan="2">
						<c:out value="${soggettoForm.indirizzoSedeLegale.civico}" /><form:hidden path="indirizzoSedeLegale.civico" />
						</td>
					</tr>
					</c:if>					
					<tr>
						<td  class="td_titolo" colspan="4"><spring:message code="nuovosoggettogiuridico.titolo2" /></td>
					</tr>
					<tr>
						<td><form:label path="statoDomicilioEletto.id"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoDomicilioEletto"  path="statoDomicilioEletto.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneDomicilioEletto')">	
								<form:option value="-1" label="---Seleziona Stato---"/>
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>					
						<td><form:label path="comuneDomicilioEletto.id" ><spring:message code="comune"/></form:label></td>
			        	 <td>
				        	<div class="ui-widget">		
								<form:input id="id_descrizioneComuneDomicilioEletto" path="comuneDomicilioEletto.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComuneDomicilioEletto"></div>
								<form:hidden id="id_hiddenComuneDomicilioEletto" path="comuneDomicilioEletto.id"/>
							</div>
				        </td>		
					</tr>
					<tr>
						<td><form:label path="indirizzoDomicilioEletto.toponimo.id">
								<spring:message code="indirizzo" />
							</form:label></td>
						<td>
							<form:select path="indirizzoDomicilioEletto.toponimo.id" size="1">	
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:input path="indirizzoDomicilioEletto.denominazione" maxlength="50" size="40"/>
						</td>
						<td><form:label path="indirizzoDomicilioEletto.civico">
								<spring:message code="civico" />
							</form:label></td>
						<td colspan="2"><form:input path="indirizzoDomicilioEletto.civico" maxlength="20" size="20"/></td>	
						</tr>
						<tr>
						<td><form:label path="pressoIndirizzoDomicilioEletto">
								<spring:message code="presso" />
							</form:label></td>
						<td colspan="2"><form:input path="pressoIndirizzoDomicilioEletto" maxlength="20" size="20"/></td>	
										
					</tr>
				
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>

					<tr>
						<td><form:label path="rappresentanteSociale"><spring:message code="soggettogiuridico.rappresentanteSociale" /></form:label></td>
						<td><form:input path="rappresentanteSociale" maxlength="200" size="40"/>
							<form:errors path="rappresentanteSociale" class="testoRosso"/></td>
						<td><form:label path="amministratore"><spring:message code="soggettogiuridico.amministratore" /></form:label></td>
						<td><form:input path="amministratore" maxlength="200" size="40"/>
							<form:errors path="amministratore" class="testoRosso"/></td>
					</tr>
					<tr>
						<td><form:label path="capitaleSociale"><spring:message code="soggettogiuridico.capitaleSociale" /></form:label></td>
						<td><form:input path="capitaleSociale" maxlength="15" size="20"/>
							<form:errors path="capitaleSociale" class="testoRosso"/>
						</td>
						<td><form:label path="settoreAttivita.id">
								<spring:message code="soggettogiuridico.settoreAttivita" />
							</form:label></td>
							<td><form:select path="settoreAttivita.id" size="1">	
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${settoriAttivita}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select></td>
					</tr>
					<tr>
						<td><form:label path="annotazioni"><spring:message code="annotazioni" /></form:label></td>
						<td colspan="3"><form:textarea path="annotazioni" cssStyle="width:98%" rows="5" /></td>
					</tr>
				</table>

				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
					<c:if test="${soggettoForm.idSoggetto == null}">
						<td class="td_tasto"><input type="submit" name="InserisciEAssocia"	title="Inserisci nuovo soggetto giuridico"
							value="Inserisci" onclick="return submitWithAutocompleteComuni(['StatoSedeLegale','StatoDomicilioEletto'],['ComuneSedeLegale','ComuneDomicilioEletto'],'write')"></td>
					</c:if>
					<c:if test="${soggettoForm.idSoggetto != null && (soggettoForm.from == 'modifica' || soggettoForm.from == 'MODIFICA')}">
						<td class="td_tasto">
							<input type="submit" name="Salva" title="Salva" value="Salva"
								onclick="return submitWithAutocompleteComuni(['StatoSedeLegale','StatoDomicilioEletto'],['ComuneSedeLegale','ComuneDomicilioEletto'],'write')">
						</td>
						<!--  
						<td class="td_tasto">
							<logic:notEqual name="soggettoForm" property="descrRuolo" value="TERZO" >
								<input type="submit" value="Indietro" name="INDIETRO" title="Torna alla ricerca dei soggetti giuridici da associare">
							</logic:notEqual>
							<logic:equal name="soggettoForm" property="descrRuolo" value="TERZO" >
								<input type="button" value="Indietro" onClick="indietro('/procedimento/soggetto/visualizzaDirettiInteressatiTerzi.htm')" />
							</logic:equal>
						</td>
						-->
					</c:if>
					<c:if test="${soggettoForm.idSoggetto != null && (soggettoForm.from == 'associa' || soggettoForm.from == 'ASSOCIA')}">
						<td class="td_tasto">
							<input type="submit" name="InserisciEAssocia"	title="Associa soggetto al procedimento" value="Associa"
								onclick="return submitWithAutocompleteComuni(['StatoSedeLegale','StatoDomicilioEletto'],['ComuneSedeLegale','ComuneDomicilioEletto'],'write')">
						</td>
						<!-- 
							<td class="td_tasto"><input type="submit" value="Indietro" name="INDIETRObis" title="Torna alla ricerca dei soggetti giuridici da associare"></td>
							-->
					</c:if>
					
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
			
			

			<c:if test="${soggettoForm.idSoggetto == null}">
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
			<c:if test="${soggettoForm.idSoggetto != null}">
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