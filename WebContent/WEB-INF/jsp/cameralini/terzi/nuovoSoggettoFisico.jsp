<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione" value="/procedimento/nuovo/terzi/ricerca.htm" />
			<form:form method="post" action="${azione}" commandName="form" id="idForm">
				<form:hidden path="soggettoForm.idSoggetto" />
				<form:hidden path="soggettoForm.idVersione" />
				<form:hidden path="soggettoForm.tipoSoggetto" value="<%= it.giustizia.smp.utility.Constants.SOGGETTO_FISICO%>"/>
				<form:hidden path="soggettoForm.descrRuolo" />
				<form:hidden path="soggettoForm.idRuolo" />
				<form:hidden path="soggettoForm.chiave"/>
				<p class="titolo_page"><spring:message code="soggettoFisico.nuovo"/></p>

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
							<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo1" /></td>
						</tr>
						<tr>
							<td><form:label path="soggettoForm.cognome" cssClass="required"><spring:message code="cognome" /></form:label></td>
							<td><form:input path="soggettoForm.cognome" maxlength="35" size="30"/>
								<form:errors path="soggettoForm.cognome" class="testoRosso"/>
							</td>
							<td><form:label path="soggettoForm.nome" cssClass="required"><spring:message code="nome" /></form:label></td>
							<td><form:input path="soggettoForm.nome" maxlength="35" size="30"/>
								<form:errors path="soggettoForm.nome" class="testoRosso"/>
							</td>
						</tr>
						<tr>
							<td><form:label path="soggettoForm.dataNascita" ><spring:message code="dataNascita" /></form:label></td>
							<td>
								<div>
									<form:input path="soggettoForm.dataNascita" maxlength="10" size="12" id="dataNascita"/>
									<form:errors path="soggettoForm.dataNascita" class="testoRosso" />
									<!--  
									<br/>
									<form:checkbox path="soggettoForm.persDaIdent"/>
									<spring:message code="nuovosoggettofisico.persDaIdent" />
									-->
									<form:hidden path="soggettoForm.persDaIdent"/>
								</div>
							</td>
							<td><form:label path="soggettoForm.presunta"><spring:message code="dataNascitaPresunta" /></form:label></td>
							<td><form:checkbox path="soggettoForm.presunta"/></td>							
						</tr>
						<tr>
							<td><form:label path="soggettoForm.statoNascita.id" ><spring:message code="stato" /></form:label></td>
							<td><form:select id="id_StatoNascita" path="soggettoForm.statoNascita.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneNascita')">
									<form:option value="-1" label="---Seleziona Stato---"/>
									<form:options items="${statiNascita}"  itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>
								<form:errors path="soggettoForm.statoNascita.id" class="testoRosso" />
							</td>						
							<td><form:label path="soggettoForm.comuneNascita.id" ><spring:message code="comune"/></form:label></td>
				        	<td>
								<div class="ui-widget">		
									<form:input id="id_descrizioneComuneNascita" path="soggettoForm.comuneNascita.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
									<div id="id_loadingComuneNascita"></div>
									<form:hidden id="id_hiddenComuneNascita" path="soggettoForm.comuneNascita.id"/>
									<form:errors path="soggettoForm.comuneNascita.descrizione" class="testoRosso" />
								</div>
				        	</td>	
						</tr>
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo2" /></td>
						</tr>
						<tr>
							<td><form:label path="soggettoForm.sesso" ><spring:message code="nuovosoggettofisico.sesso" /></form:label></td>
							<td><form:select path="soggettoForm.sesso" size="1">
									<form:option value="-1">Seleziona Sesso</form:option>
									<form:option value="M" label="Maschio" />
									<form:option value="F" label="Femmina" />
								</form:select> <form:errors path="soggettoForm.sesso" class="testoRosso" />
							</td>
							<td><form:label path="soggettoForm.codiceFiscale"><spring:message code="codiceFiscale" /></form:label></td>
							<td>
								<form:input path="soggettoForm.codiceFiscale" maxlength="16" size="25"/>
								<!-- MEV 22 del PLO sett 2017 -->
								<c:set var="checkCF" value="disabled"/>
								<c:if test="${(sessionScope.profilo.idTipoUfficio==19) || (sessionScope.profilo.idTipoUfficio==6)}">
								</c:if>
								<input id="verificaCF" name="verificaCF" type="submit" value="Verifica CF" title="Verifica del Codice Fiscale" <c:out value="${checkCF}"/>>
							</td>
						</tr>
					<tr>
						<td><form:label path="soggettoForm.professione.id"><spring:message code="nuovosoggettofisico.professione" /></form:label></td>
						<td>
							<form:select path="soggettoForm.professione.id" size="1" onchange="this.form.submit()">	
								<form:option value="-1" label="------Seleziona Professione-------"/>
								<form:options items="${professioni}" itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>
						<c:if test="${form.soggettoForm.idSoggetto == null}">
							<td><form:label path="soggettoForm.cui"><spring:message code="soggettofisico.cui" /></form:label></td>
							<td><form:input path="soggettoForm.cui" maxlength="7" size="7" /></td>					
						</c:if>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.condizioneGiuridica.id"><spring:message code="nuovosoggettofisico.condizionegiuridica" /></form:label></td>
						<td>
							<form:select path="soggettoForm.condizioneGiuridica.id" size="1" onchange="this.form.submit()">
								<form:option value="-1" label="------ Seleziona Condizione Giuridica--------" />
								<form:options items="${condizioniGiuridiche}" itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>
							<logic:notEmpty name="form" property="soggettoForm.condizioneGiuridica">
							<logic:equal name="form" property="soggettoForm.condizioneGiuridica.id" value="<%=Integer.toString(it.giustizia.smp.utility.Constants.DETENUTO)%>">
							<td><form:label path="soggettoForm.istitutoDiDetenzione.id">
									<spring:message code="nuovosoggettofisico.istitutodidetenzione" />
								</form:label>
							</td>						
							<td>
								<form:select path="soggettoForm.istitutoDiDetenzione.id" size="1" onchange="this.form.submit()">	
									<form:option value="-1" label="------Seleziona Istituto-------"/>
									<form:options items="${istitutiDetenzione}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>						
							</td>
						</logic:equal>
						</logic:notEmpty>
					</tr>					
					<tr>
					
						<logic:notEmpty name="form" property="soggettoForm.istitutoDiDetenzione">
							<logic:equal name="form" property="soggettoForm.istitutoDiDetenzione.id" value="<%=it.giustizia.smp.utility.Constants.ISTITUTO_DETENZIONE_ESTERO%>">
								<td>
									<form:label path="soggettoForm.descIstitutoDetenzioneEstero">
										<spring:message code="nuovosoggettofisico.istitutodidetenzioneEstero" />
									</form:label>
								</td>		
								<td>
									<form:input path="soggettoForm.descIstitutoDetenzioneEstero" maxlength="50" size="30"/>
								</td>	
							</logic:equal>		
						</logic:notEmpty>
					</tr>		
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo3" /></td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.statoResidenza.id"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoResidenza" path="soggettoForm.statoResidenza.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneResidenza')">	
								<form:option value="-1" label="---Seleziona Stato---"/>
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>						
						<td><form:label path="soggettoForm.comuneResidenza.id" ><spring:message code="comune"/></form:label></td>
			        	<td>
							<div class="ui-widget">		
								<form:input id="id_descrizioneComuneResidenza" path="soggettoForm.comuneResidenza.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComuneResidenza"></div>
								<form:hidden id="id_hiddenComuneResidenza" path="soggettoForm.comuneResidenza.id"/>
								<form:errors path="soggettoForm.comuneResidenza.descrizione" class="testoRosso" />
							</div>
			        	</td>	
					</tr>
					<tr>
						<td><form:label path="soggettoForm.indirizzoResidenza"><spring:message code="indirizzo" /></form:label></td>
						<td>
							<form:select path="soggettoForm.indirizzoResidenza.toponimo.id" size="1">
							 <form:option value="-1" label="------Seleziona Tipo Strada-------"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" />
							</form:select> 	
							<form:input path="soggettoForm.indirizzoResidenza.denominazione" maxlength="50" size="30"/>
						</td>
						<td><form:label path="soggettoForm.indirizzoResidenza.civico"><spring:message code="civico" /></form:label></td>
						<td><form:input path="soggettoForm.indirizzoResidenza.civico" maxlength="20" size="20"/></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo4" /></td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.statoDomicilio.id"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoDomicilio" path="soggettoForm.statoDomicilio.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneDomicilio')" >
								<form:option value="-1" label="---Seleziona Stato---"/>
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>								
						<td><form:label path="soggettoForm.comuneDomicilio.id" ><spring:message code="comune"/></form:label></td>
			        	<td>
							<div class="ui-widget">		
								<form:input id="id_descrizioneComuneDomicilio" path="soggettoForm.comuneDomicilio.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComuneDomicilio"></div>
								<form:hidden id="id_hiddenComuneDomicilio" path="soggettoForm.comuneDomicilio.id"/>
								<form:errors path="soggettoForm.comuneDomicilio.descrizione" class="testoRosso" />
							</div>
			        	</td>	
					</tr>
					<tr>
						<td><form:label path="soggettoForm.indirizzoDomicilio"><spring:message code="nuovosoggettofisico.indirizzo" /></form:label></td>
						<td>
							<form:select path="soggettoForm.indirizzoDomicilio.toponimo.id" size="1">
							 <form:option value="-1" label="------Seleziona Tipo Strada-------"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" />
							</form:select> 
							<form:input path="soggettoForm.indirizzoDomicilio.denominazione" maxlength="50" size="30"/></td>
						<td><form:label path="soggettoForm.indirizzoDomicilio.civico"><spring:message code="civico" /></form:label></td>
						<td><form:input path="soggettoForm.indirizzoDomicilio.civico" maxlength="20" size="20"/></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo5" /></td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.statoDimora.id"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoDimora" path="soggettoForm.statoDimora.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneDimora')">
								<form:option value="-1" label="---Seleziona Stato---"/>
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>							
						<td><form:label path="soggettoForm.comuneDimora.id" ><spring:message code="comune"/></form:label></td>
			        	<td>
							<div class="ui-widget">		
								<form:input id="id_descrizioneComuneDimora" path="soggettoForm.comuneDimora.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComuneDimora"></div>
								<form:hidden id="id_hiddenComuneDimora" path="soggettoForm.comuneDimora.id"/>
								<form:errors path="soggettoForm.comuneDimora.descrizione" class="testoRosso" />
							</div>
			        	</td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.indirizzoDimora"><spring:message code="nuovosoggettofisico.indirizzo" /></form:label></td>
						<td>
							<form:select path="soggettoForm.indirizzoDimora.toponimo.id" size="1">
							 <form:option value="-1" label="------Seleziona Tipo Strada-------"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" />
							</form:select> 
							<form:input path="soggettoForm.indirizzoDimora.denominazione" maxlength="50" size="30"/></td>
						<td><form:label path="soggettoForm.indirizzoDimora.civico"><spring:message code="civico" /></form:label></td>
						<td><form:input path="soggettoForm.indirizzoDimora.civico" maxlength="20" size="20"/></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo6" /></td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.statoDomicilioEletto.id"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoDomicilioEletto" path="soggettoForm.statoDomicilioEletto.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneDomicilioEletto')">	
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
								<form:errors path="soggettoForm.comuneDomicilioEletto.descrizione" class="testoRosso" />
							</div>
			        	</td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.indirizzoDomicilioEletto"><spring:message code="nuovosoggettofisico.indirizzo" /></form:label></td>
						<td>
							<form:select path="soggettoForm.indirizzoDomicilioEletto.toponimo.id" size="1">
							 <form:option value="-1" label="------Seleziona Tipo Strada-------"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" />
							</form:select> 
							<form:input path="soggettoForm.indirizzoDomicilioEletto.denominazione" maxlength="50" size="30"/></td>
						<td><form:label path="soggettoForm.indirizzoDomicilioEletto.civico"><spring:message code="civico" /></form:label></td>
						<td><form:input path="soggettoForm.indirizzoDomicilioEletto.civico" maxlength="20" size="20"/></td>
					</tr>	
					<tr>
						<td><form:label path="soggettoForm.pressoIndirizzoDomicilioEletto"><spring:message code="presso" /></form:label></td>
						<td>
							<form:select path="soggettoForm.pressoIndirizzoDomicilioEletto" size="1">
								<form:option value="-1" label="------Seleziona presso-------"/>
								<form:option value="1" label="Avvocato" />
								<form:option value="2" label="Altro" />
							</form:select>
						</td>
						<td colspan="2"><form:input path="soggettoForm.pressoIndirizzoDomicilioElettoSpecifica" maxlength="150" size="50"/></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo7" /></td>
					</tr>
					
		        	<c:if test="${form.soggettoForm.idSoggetto == null}">
					<tr>
						<td><form:label path="soggettoForm.statoMorte.id"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoMorte" path="soggettoForm.statoMorte.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneMorte')" >
								<form:option value="-1" label="---Seleziona Stato---"/>	
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>										
						<td><form:label path="soggettoForm.comuneMorte.id" ><spring:message code="comune"/></form:label></td>
			        	<td>
							<div class="ui-widget">		
								<form:input id="id_descrizioneComuneMorte" path="soggettoForm.comuneMorte.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComuneMorte"></div>
								<form:hidden id="id_hiddenComuneMorte" path="soggettoForm.comuneMorte.id"/>
								<form:errors path="soggettoForm.comuneMorte.descrizione" class="testoRosso" />
							</div>
			        	</td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.dataMorte"><spring:message code="nuovosoggettofisico.datadecesso" /></form:label></td>
						<td colspan="3"><form:input path="soggettoForm.dataMorte" maxlength="10" size="12" id="dataMorte"/>
									<form:errors path="soggettoForm.dataMorte" class="testoRosso" /></td>
					</tr>
					</c:if> 
					<c:if test="${form.soggettoForm.idSoggetto != null}">
					<tr>
						<td><form:label path="soggettoForm.statoMorte.id"><spring:message code="stato" /></form:label></td>
						<td><c:out value="${form.soggettoForm.statoMorte.descrizione}" /><form:hidden path="soggettoForm.statoMorte.id" /><form:hidden path="soggettoForm.statoMorte.descrizione" />
						</td>										
						<td><form:label path="soggettoForm.comuneMorte.id" ><spring:message code="comune"/></form:label></td>
						<td><c:out value="${form.soggettoForm.descrizioneComuneMorteItalianoEstero}" /><form:hidden path="soggettoForm.comuneMorte.id" /><form:hidden path="soggettoForm.comuneMorte.descrizione" /> <form:hidden path="soggettoForm.descrizioneComuneEsteroMorte" /></td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.dataMorte"><spring:message code="nuovosoggettofisico.datadecesso" /></form:label></td>
						<td colspan="3"><fmt:formatDate value="${form.soggettoForm.dataMorte}" type="date" pattern="dd/MM/yyyy" /></td>
										<form:hidden path="soggettoForm.dataMorte"/>
					</tr>	
									
					</c:if> 
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo8"/></td>
					</tr>
					<c:if test="${form.soggettoForm.idSoggetto == null}">
					<tr>
						<td><form:label path="soggettoForm.cognomePaternita"><spring:message code="cognome" /></form:label></td>
						<td><form:input path="soggettoForm.cognomePaternita" maxlength="30" size="30"/>
										<form:errors path="soggettoForm.cognomePaternita" class="testoRosso"/></td>
						<td><form:label path="soggettoForm.nomePaternita"><spring:message code="nome" /></form:label></td>
						<td><form:input path="soggettoForm.nomePaternita" maxlength="30" size="30"/>
										<form:errors path="soggettoForm.nomePaternita" class="testoRosso"/></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo9" /></td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.cognomeMaternita"><spring:message code="cognome" /></form:label></td>
						<td><form:input path="soggettoForm.cognomeMaternita" maxlength="30" size="30"/>
							<form:errors path="soggettoForm.cognomeMaternita" class="testoRosso"/></td>
						<td><form:label path="soggettoForm.nomeMaternita"><spring:message code="nome" /></form:label></td>
						<td><form:input path="soggettoForm.nomeMaternita" maxlength="30" size="30"/>
							<form:errors path="soggettoForm.nomeMaternita" class="testoRosso"/></td>
					</tr>
					</c:if> 
					<c:if test="${form.soggettoForm.idSoggetto != null}">
					<tr>
						<td><form:label path="soggettoForm.cognomePaternita"><spring:message code="cognome" /></form:label></td>
						<td><c:out value="${form.soggettoForm.cognomePaternita}" /><form:hidden path="soggettoForm.cognomePaternita"/></td>
						<td><form:label path="soggettoForm.nomePaternita"><spring:message code="nome" /></form:label></td>
						<td><c:out value="${form.soggettoForm.nomePaternita}" /><form:hidden path="soggettoForm.nomePaternita"/></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo9" /></td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.cognomeMaternita"><spring:message code="cognome" /></form:label></td>
						<td><c:out value="${form.soggettoForm.cognomeMaternita}" /><form:hidden path="soggettoForm.cognomeMaternita" /></td>
						<td><form:label path="soggettoForm.nomeMaternita"><spring:message code="nome" /></form:label></td>
						<td><c:out value="${form.soggettoForm.nomeMaternita}" /><form:hidden path="soggettoForm.nomeMaternita" /></td>
					</tr>					
					</c:if> 
				</table>

				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" name="InserisciNuovoSoggettoFisico" title="Inserisci nuovo soggetto fisico" value="Inserisci"></td>
						<td class="td_tasto"><input id="backButton" type="button" value="Indietro" title="Torna alla ricerca dei soggetti fisici da associare"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
			
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>
			
			
			
			
			<c:if test="${form.soggettoForm.idSoggetto == null}">
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
		
			<c:if test="${form.soggettoForm.idSoggetto != null}">
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
				if($( "#id_StatoDimora" ).val() == 1)
				{
					doAutocomplete('ComuneDimora');
				}
				if($( "#id_StatoDomicilioEletto" ).val() == 1)
				{
					doAutocomplete('ComuneDomicilioEletto');
				}
				if($( "#id_StatoMorte" ).val() == 1)
				{
					doAutocomplete('ComuneMorte');
				}
			</script>
							
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>