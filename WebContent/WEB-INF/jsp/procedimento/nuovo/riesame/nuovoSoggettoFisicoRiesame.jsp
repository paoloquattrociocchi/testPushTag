<%@page import="it.giustizia.smp.utility.Constants"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/procedimento/nuovo/nuovoSoggettoFisico.htm" />
			<form:form method="post" action="${azione}" commandName="soggettoForm">
				<form:hidden path="idSoggetto" />
				<form:hidden path="idVersione" />
				<form:hidden path="tipoSoggetto" value="<%= it.giustizia.smp.utility.Constants.SOGGETTO_FISICO%>"/>
				<form:hidden path="descrRuolo" />
				<form:hidden path="idRuolo" />
				<form:hidden path="chiave"/>
				<c:if test="${soggettoForm.idSoggetto == null}">
					<p class="titolo_page">
					<spring:message code="procedimento.riesameProvvedimentiQuestore.dettaglio" />&nbsp;-&nbsp;
					<spring:message code="soggettoFisico.nuovo"/>
				</c:if>
				<c:if test="${soggettoForm.idSoggetto != null}">
					<p class="titolo_page">
					<spring:message code="procedimento.riesameProvvedimentiQuestore.dettaglio" />&nbsp;-&nbsp;
					<spring:message code="soggettoFisico.associa"/></p>
				</c:if>				

				<logic:present name="procedimento">
					<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
					 <br/>
					    <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
					
				</logic:present>
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<logic:equal name="<%= Constants.TIPO_UTENTE%>" scope="session" value="<%= Constants.TIPO_UTENTE_SMP%>" >
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
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo1" /></td>
					</tr>
					<tr>
						<c:if test="${soggettoForm.idSoggetto == null}">
							<td><form:label path="cognome" cssClass="required"><spring:message code="cognome" /></form:label></td>
							<td><form:input path="cognome" maxlength="35" size="30"/>
								<form:errors path="cognome" class="testoRosso"/></td>
							<td><form:label path="nome" cssClass="required"><spring:message code="nome" /></form:label></td>
							<td><form:input path="nome" maxlength="35" size="30"/>
								<form:errors path="nome" class="testoRosso"/></td>
						</tr>
						<tr>
							<td><form:label path="dataNascita" cssClass="required"><spring:message code="dataNascita" /></form:label></td>
							<td colspan="3">
								<div>
									<form:input path="dataNascita" maxlength="10" size="12" id="dataNascita"/>
									<form:errors path="dataNascita" class="testoRosso" />
									<br/>
								</div>
							</td>								
						</tr>
						<tr>
							<td><form:label path="statoNascita.id" cssClass="required"><spring:message code="stato" /></form:label></td>
							<td><form:select id="id_StatoNascita" path="statoNascita.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneNascita')">
									<form:option value="-1" label="---Seleziona Stato---"/>
									<form:options items="${statiNascita}"  itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>
								<form:errors path="statoNascita.id" class="testoRosso" />
							</td>						
							<td><form:label path="comuneNascita.id" cssClass="required"><spring:message code="comune"/></form:label></td>
				        	<td>
								<div class="ui-widget">		
									<form:input id="id_descrizioneComuneNascita" path="comuneNascita.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
									<div id="id_loadingComuneNascita"></div>
									<form:hidden id="id_hiddenComuneNascita" path="comuneNascita.id"/>
									<form:errors path="comuneNascita.descrizione" class="testoRosso" />
								</div>
				        	</td>	
						</tr>
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo2" /></td>
						</tr>
						<tr>
							<td><form:label path="sesso" cssClass="required"><spring:message code="nuovosoggettofisico.sesso" /></form:label></td>
							<td><form:select path="sesso" size="1">
									<form:option value="-1">Seleziona Sesso</form:option>
									<form:option value="M" label="Maschio" />
									<form:option value="F" label="Femmina" />
								</form:select><form:errors path="sesso" class="testoRosso" />
							</td>
							<td><form:label path="codiceFiscale"><spring:message code="codiceFiscale" /></form:label></td>
							<td><form:input path="codiceFiscale" maxlength="16" size="25"/></td>
						</tr>
					
					</c:if>
					<c:if test="${soggettoForm.idSoggetto != null}">
							<td><form:label path="cognome"><spring:message code="cognome" /></form:label></td>
							<td><c:out value="${soggettoForm.cognome}" /><form:hidden path="cognome" /></td>
							<td><form:label path="nome"><spring:message code="nome" /></form:label></td>
							<td><c:out value="${soggettoForm.nome}" /><form:hidden path="nome" /></td>
						</tr>
						<tr>
							<td><form:label path="dataNascita"><spring:message code="dataNascita" /></form:label></td>
							<td colspan="3">
								<div>
									<fmt:formatDate value="${soggettoForm.dataNascita}" type="date" pattern="dd/MM/yyyy" /><form:hidden path="dataNascita" />
								</div>
							</td>								
						</tr>
						<tr>
							<td><form:label path="statoNascita.id"><spring:message code="stato" /></form:label></td>
							<td><c:out value="${soggettoForm.statoNascita.descrizione}" /><form:hidden path="statoNascita.id" /><form:hidden path="statoNascita.descrizione" />
							</td>						
							<td><form:label path="comuneNascita.id" ><spring:message code="comune"/></form:label></td>
				        	<td><c:out value="${soggettoForm.descrizioneComuneNascitaItalianoEstero}" /><form:hidden path="comuneNascita.id" /><form:hidden path="comuneNascita.descrizione" /> <form:hidden path="descrizioneComuneEsteroNascita" />
				        	</td>	
						</tr>
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo2" /></td>
						</tr>
						<tr>
							<td><form:label path="sesso"><spring:message code="nuovosoggettofisico.sesso" /></form:label></td>
							<td>
							<c:if test="${soggettoForm.sesso == 'M'}">
								<c:out value="Maschio" />
							</c:if>
							<c:if test="${soggettoForm.sesso == 'F'}">
								<c:out value="Femmina" />
							</c:if>
							<form:hidden path="sesso" />
							</td>
							<td><form:label path="codiceFiscale"><spring:message code="codiceFiscale" /></form:label></td>
							<td><c:out value="${soggettoForm.codiceFiscale}" /><form:hidden path="codiceFiscale" /></td>
						</tr>
					
					</c:if>					
					
					<tr>
						<c:if test="${soggettoForm.idSoggetto != null}">
							<td><form:label path="cui"><spring:message code="soggettofisico.cui" /></form:label></td>
							<td colspan="3"><c:out value="${soggettoForm.cui}" /><form:hidden path="cui" /></td>
						</c:if>
						<c:if test="${soggettoForm.idSoggetto == null}">
							<td><form:label path="cui"><spring:message code="soggettofisico.cui" /></form:label></td>
							<td colspan="3"><form:input path="cui" maxlength="7" size="7" /></td>					
						</c:if>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo3" /></td>
					</tr>
					<tr>
						<td><form:label path="statoResidenza.id"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoResidenza" path="statoResidenza.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneResidenza')">	
								<form:option value="-1" label="---Seleziona Stato---"/>
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>						
						<td><form:label path="comuneResidenza.id" ><spring:message code="comune"/></form:label></td>
			        	<td>
							<div class="ui-widget">		
								<form:input id="id_descrizioneComuneResidenza" path="comuneResidenza.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComuneResidenza"></div>
								<form:hidden id="id_hiddenComuneResidenza" path="comuneResidenza.id"/>
							</div>
			        	</td>	
					</tr>
					<tr>
						<td><form:label path="indirizzoResidenza"><spring:message code="indirizzo" /></form:label></td>
						<td>
							<form:select path="indirizzoResidenza.toponimo.id" size="1">
							 <form:option value="-1" label="------Seleziona Tipo Strada-------"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" />
							</form:select> 	
							<form:input path="indirizzoResidenza.denominazione" maxlength="50" size="30"/>
						</td>
						<td><form:label path="indirizzoResidenza.civico"><spring:message code="civico" /></form:label></td>
						<td><form:input path="indirizzoResidenza.civico" maxlength="20" size="20"/></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo4" /></td>
					</tr>
					<tr>
						<td><form:label path="statoDomicilio.id"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoDomicilio" path="statoDomicilio.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneDomicilio')" >
								<form:option value="-1" label="---Seleziona Stato---"/>
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>								
						<td><form:label path="comuneDomicilio.id" ><spring:message code="comune"/></form:label></td>
			        	<td>
							<div class="ui-widget">		
								<form:input id="id_descrizioneComuneDomicilio" path="comuneDomicilio.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComuneDomicilio"></div>
								<form:hidden id="id_hiddenComuneDomicilio" path="comuneDomicilio.id"/>
							</div>
			        	</td>	
					</tr>
					<tr>
						<td><form:label path="indirizzoDomicilio"><spring:message code="nuovosoggettofisico.indirizzo" /></form:label></td>
						<td>
							<form:select path="indirizzoDomicilio.toponimo.id" size="1">
							 <form:option value="-1" label="------Seleziona Tipo Strada-------"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" />
							</form:select> 
							<form:input path="indirizzoDomicilio.denominazione" maxlength="50" size="30"/></td>
						<td><form:label path="indirizzoDomicilio.civico"><spring:message code="civico" /></form:label></td>
						<td><form:input path="indirizzoDomicilio.civico" maxlength="20" size="20"/></td>
					</tr>
				</table>

				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<c:if test="${soggettoForm.idSoggetto == null}">
						<td class="td_tasto"><input type="submit"name="InserisciEAssocia" title="Inserisci nuovo soggetto fisico" value="Inserisci"></td>
						</c:if>
						<c:if test="${soggettoForm.idSoggetto != null}">
						<td class="td_tasto"><input type="submit"name="InserisciEAssocia" title="Inserisci nuovo soggetto fisico" value="Associa"></td>						
						</c:if>
						<td class="td_tasto"><input type="submit" name="IndietroRiesame" value="Indietro"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
			
			
			<c:if test="${soggettoForm.idSoggetto == null}">
				<script type="text/javascript">
					$(document).ready(function() {
						prendiData("#dataNascita",true);
						prendiData("#dataMorte",true);

						controllaInizializzazioneCampi($( "#id_StatoNascita" ).val(),"ComuneNascita");
						controllaInizializzazioneCampi($( "#id_StatoResidenza" ).val(),"ComuneResidenza");
						controllaInizializzazioneCampi($( "#id_StatoDomicilio" ).val(),"ComuneDomicilio");
						controllaInizializzazioneCampi($( "#id_StatoDimora" ).val(),"ComuneDimora");
						controllaInizializzazioneCampi($( "#id_StatoDomicilioEletto" ).val(),"ComuneDomicilioEletto");
						controllaInizializzazioneCampi($( "#id_StatoMorte" ).val(),"ComuneMorte");
						});
				</script>
			</c:if>		
		
			<c:if test="${soggettoForm.idSoggetto != null}">
				<script type="text/javascript">
					$(document).ready(function() {
						controllaInizializzazioneCampi($( "#id_StatoResidenza" ).val(),"ComuneResidenza");
						controllaInizializzazioneCampi($( "#id_StatoDomicilio" ).val(),"ComuneDomicilio");
						controllaInizializzazioneCampi($( "#id_StatoDimora" ).val(),"ComuneDimora");
						controllaInizializzazioneCampi($( "#id_StatoDomicilioEletto" ).val(),"ComuneDomicilioEletto");
					});
				</script>
			</c:if>		
			
			<script type="text/javascript">
				if($( "#id_StatoNascita" ).val() == 1)
				{
					doAutocomplete('ComuneNascita');
				}
				if($( "#id_StatoResidenza" ).val() == 1)	
				{
					doAutocomplete('ComuneResidenza');
				}
				if($( "#id_StatoDomicilio" ).val() == 1)
				{
					doAutocomplete('ComuneDomicilio');
				}
			</script>
							
		</tiles:putAttribute>
	</tiles:insertDefinition>
