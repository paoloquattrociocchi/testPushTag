<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/procedimento/nuovo/nuovoSoggettoFisico.htm" />
			<form:form method="post" action="${azione}" commandName="soggettoForm" id="idForm">
				<form:hidden path="idSoggetto" />
				<form:hidden path="idVersione" />
				<form:hidden path="tipoSoggetto" value="<%= it.giustizia.smp.utility.Constants.SOGGETTO_FISICO%>"/>
				<form:hidden path="descrRuolo" />
				<form:hidden path="idRuolo" />
				<form:hidden path="chiave"/>
				<c:if test="${soggettoForm.idSoggetto == null}">
					<p class="titolo_page"><spring:message code="soggettoFisico.nuovo"/></p>
				</c:if>
				<c:if test="${soggettoForm.idSoggetto != null}">
					<p class="titolo_page"><spring:message code="soggettoFisico.associa"/></p>
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
							<td><form:label path="ipotesiMisura.id" cssClass="required"><spring:message code="procedimento.ipotesiMisura" /> </form:label></td>
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
								<form:errors path="cognome" class="testoRosso"/>
							</td>
							<td><form:label path="nome" cssClass="required"><spring:message code="nome" /></form:label></td>
							<td><form:input path="nome" maxlength="35" size="30"/>
								<form:errors path="nome" class="testoRosso"/>
							</td>
						</tr>
						<tr>
							<td>
								<c:choose>
									<c:when  test="${ soggettoForm.idRuolo != 3 }">
										<form:label path="dataNascita" cssClass="required"><spring:message code="dataNascita" /></form:label>
									</c:when>
									<c:otherwise>
										<form:label path="dataNascita"><spring:message code="dataNascita" /></form:label>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<div>
									<form:input path="dataNascita" maxlength="10" size="12" id="dataNascita"/>
									<form:errors path="dataNascita" class="testoRosso" />
									<!--  
									<br/>
									<form:checkbox path="persDaIdent"/>
									<spring:message code="nuovosoggettofisico.persDaIdent" />
									-->
									<form:hidden path="persDaIdent"/>
								</div>
							</td>
							<td><form:label path="presunta"><spring:message code="dataNascitaPresunta" /></form:label></td>
							<td><form:checkbox path="presunta"/></td>							
						</tr>
						<tr>
							<td>
								<c:choose>
									<c:when  test="${ soggettoForm.idRuolo != 3 }">
										<form:label path="statoNascita.id" cssClass="required"><spring:message code="stato" /></form:label>
									</c:when>
									<c:otherwise>
										<form:label path="statoNascita.id"><spring:message code="stato" /></form:label>
									</c:otherwise>
								</c:choose>
							</td>
							<td><form:select id="id_StatoNascita" path="statoNascita.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneNascita')">
									<form:option value="-1" label="---Seleziona Stato---"/>
									<form:options items="${statiNascita}"  itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>
								<form:errors path="statoNascita.id" class="testoRosso" />
							</td>						
							<td>
								<c:choose>
									<c:when  test="${ soggettoForm.idRuolo != 3 }">
										<form:label path="comuneNascita.id" cssClass="required"><spring:message code="comune" /></form:label>
									</c:when>
									<c:otherwise>
										<form:label path="comuneNascita.id"><spring:message code="comune" /></form:label>
									</c:otherwise>
								</c:choose>
							</td>
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
							<td>
								<c:choose>
									<c:when  test="${ soggettoForm.idRuolo != 3 }">
										<form:label path="sesso" cssClass="required"><spring:message code="nuovosoggettofisico.sesso" /></form:label>
									</c:when>
									<c:otherwise>
										<form:label path="sesso"><spring:message code="nuovosoggettofisico.sesso" /></form:label>
									</c:otherwise>
								</c:choose>
							</td>
							<td><form:select path="sesso" size="1">
									<form:option value="-1">Seleziona Sesso</form:option>
									<form:option value="M" label="Maschio" />
									<form:option value="F" label="Femmina" />
								</form:select> <form:errors path="sesso" class="testoRosso" />
							</td>
							<td><form:label path="codiceFiscale"><spring:message code="codiceFiscale" /></form:label></td>
							<td>
								<form:input path="codiceFiscale" maxlength="16" size="25"/>
								<!-- MEV 22 del PLO sett 2017 -->
								<c:set var="checkCF" value="disabled"/>
								<c:if test="${(sessionScope.profilo.idTipoUfficio==19) || (sessionScope.profilo.idTipoUfficio==6)}">
								</c:if>
								<input id="verificaCF" name="verificaCF" type="submit" value="Verifica CF" title="Verifica del Codice Fiscale" <c:out value="${checkCF}"/>>
							</td>
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
									<!-- 
									<br>
									<form:checkbox path="persDaIdent" disabled="true"/>
									<spring:message code="nuovosoggettofisico.persDaIdent" />
									 -->
									<form:hidden path="persDaIdent"/>
								</div>
							</td>								
						</tr>
						<tr>
							<td><form:label path="statoNascita.id"><spring:message code="stato" /></form:label></td>
							<td><c:out value="${soggettoForm.statoNascita.descrizione}" /><form:hidden path="statoNascita.id" /><form:hidden path="statoNascita.descrizione" />
							</td>						
							<td><form:label path="comuneNascita.id" ><spring:message code="comune"/></form:label></td>
				        	<td><c:out value="${soggettoForm.descrizioneComuneNascitaItalianoEstero}" /><form:hidden path="comuneNascita.id" /><form:hidden path="comuneNascita.descrizione" /><form:hidden path="siglaProvinciaNascita" /> <form:hidden path="descrizioneComuneEsteroNascita" />
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
						<td><form:label path="professione.id"><spring:message code="nuovosoggettofisico.professione" /></form:label></td>
						<td>
							<form:select path="professione.id" size="1" onchange="this.form.submit()">	
								<form:option value="-1" label="------Seleziona Professione-------"/>
								<form:options items="${professioni}" itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>
						<c:if test="${soggettoForm.idSoggetto != null}">
						<td><form:label path="cui"><spring:message code="soggettofisico.cui" /></form:label></td>
						<td><c:out value="${soggettoForm.cui}" /><form:hidden path="cui" /></td>
						</c:if>
						<c:if test="${soggettoForm.idSoggetto == null}">
						<td><form:label path="cui"><spring:message code="soggettofisico.cui" /></form:label></td>
						<td><form:input path="cui" maxlength="7" size="7" /></td>					
						</c:if>
					</tr>
					<tr>
						<td><form:label path="condizioneGiuridica.id"><spring:message code="nuovosoggettofisico.condizionegiuridica" /></form:label></td>
						<td>
							<form:select path="condizioneGiuridica.id" size="1" onchange="this.form.submit()">
								<form:option value="-1" label="------ Seleziona Condizione Giuridica--------" />
								<form:options items="${condizioniGiuridiche}" itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>
							<logic:notEmpty name="soggettoForm" property="condizioneGiuridica">
							<logic:equal name="soggettoForm" property="condizioneGiuridica.id" value="<%=Integer.toString(it.giustizia.smp.utility.Constants.DETENUTO)%>">
							<td><form:label path="istitutoDiDetenzione.id">
									<spring:message code="nuovosoggettofisico.istitutodidetenzione" />
								</form:label>
							</td>						
							<td>
								<form:select path="istitutoDiDetenzione.id" size="1" onchange="this.form.submit()">	
									<form:option value="-1" label="------Seleziona Istituto-------"/>
									<form:options items="${istitutiDetenzione}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>						
							</td>
						</logic:equal>
						</logic:notEmpty>
					</tr>					
					<tr>
					
						<logic:notEmpty name="soggettoForm" property="istitutoDiDetenzione">
						<logic:equal name="soggettoForm" property="istitutoDiDetenzione.id" value="<%=it.giustizia.smp.utility.Constants.ISTITUTO_DETENZIONE_ESTERO%>">
							<td>
								<form:label path="descIstitutoDetenzioneEstero">
									<spring:message code="nuovosoggettofisico.istitutodidetenzioneEstero" />
								</form:label>
							</td>		
							<td>
								<form:input path="descIstitutoDetenzioneEstero" maxlength="50" size="30"/>
							</td>	
						</logic:equal>		
						</logic:notEmpty>
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
								<form:errors path="comuneResidenza.descrizione" class="testoRosso" />
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
								<form:errors path="comuneDomicilio.descrizione" class="testoRosso" />
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
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo5" /></td>
					</tr>
					<tr>
						<td><form:label path="statoDimora.id"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoDimora" path="statoDimora.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneDimora')">
								<form:option value="-1" label="---Seleziona Stato---"/>
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>							
						<td><form:label path="comuneDimora.id" ><spring:message code="comune"/></form:label></td>
			        	<td>
							<div class="ui-widget">		
								<form:input id="id_descrizioneComuneDimora" path="comuneDimora.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComuneDimora"></div>
								<form:hidden id="id_hiddenComuneDimora" path="comuneDimora.id"/>
								<form:errors path="comuneDimora.descrizione" class="testoRosso" />
							</div>
			        	</td>
					</tr>
					<tr>
						<td><form:label path="indirizzoDimora"><spring:message code="nuovosoggettofisico.indirizzo" /></form:label></td>
						<td>
							<form:select path="indirizzoDimora.toponimo.id" size="1">
							 <form:option value="-1" label="------Seleziona Tipo Strada-------"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" />
							</form:select> 
							<form:input path="indirizzoDimora.denominazione" maxlength="50" size="30"/></td>
						<td><form:label path="indirizzoDimora.civico"><spring:message code="civico" /></form:label></td>
						<td><form:input path="indirizzoDimora.civico" maxlength="20" size="20"/></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo6" /></td>
					</tr>
					<tr>
						<td><form:label path="statoDomicilioEletto.id"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoDomicilioEletto" path="statoDomicilioEletto.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneDomicilioEletto')">	
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
								<form:errors path="comuneDomicilioEletto.descrizione" class="testoRosso" />
							</div>
			        	</td>
					</tr>
					<tr>
						<td><form:label path="indirizzoDomicilioEletto"><spring:message code="nuovosoggettofisico.indirizzo" /></form:label></td>
						<td>
							<form:select path="indirizzoDomicilioEletto.toponimo.id" size="1">
							 <form:option value="-1" label="------Seleziona Tipo Strada-------"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" />
							</form:select> 
							<form:input path="indirizzoDomicilioEletto.denominazione" maxlength="50" size="30"/></td>
						<td><form:label path="indirizzoDomicilioEletto.civico"><spring:message code="civico" /></form:label></td>
						<td><form:input path="indirizzoDomicilioEletto.civico" maxlength="20" size="20"/></td>
					</tr>	
					<tr>
						<td><form:label path="pressoIndirizzoDomicilioEletto"><spring:message code="presso" /></form:label></td>
						<td>
							<form:select path="pressoIndirizzoDomicilioEletto" size="1">
								<form:option value="-1" label="------Seleziona presso-------"/>
								<form:option value="1" label="Avvocato" />
								<form:option value="2" label="Altro" />
							</form:select>
						</td>
						<td colspan="2"><form:input path="pressoIndirizzoDomicilioElettoSpecifica" maxlength="150" size="50"/></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo7" /></td>
					</tr>
					
		        	<c:if test="${soggettoForm.idSoggetto == null}">
					<tr>
						<td><form:label path="statoMorte.id"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoMorte" path="statoMorte.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneMorte')" >
								<form:option value="-1" label="---Seleziona Stato---"/>	
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>										
						<td><form:label path="comuneMorte.id" ><spring:message code="comune"/></form:label></td>
			        	<td>
							<div class="ui-widget">		
								<form:input id="id_descrizioneComuneMorte" path="comuneMorte.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComuneMorte"></div>
								<form:hidden id="id_hiddenComuneMorte" path="comuneMorte.id"/>
								<form:errors path="comuneMorte.descrizione" class="testoRosso" />
							</div>
			        	</td>
					</tr>
					<tr>
						<td><form:label path="dataMorte"><spring:message code="nuovosoggettofisico.datadecesso" /></form:label></td>
						<td colspan="3"><form:input path="dataMorte" maxlength="10" size="12" id="dataMorte"/>
									<form:errors path="dataMorte" class="testoRosso" /></td>
					</tr>
					</c:if> 
					<c:if test="${soggettoForm.idSoggetto != null}">
					<tr>
						<td><form:label path="statoMorte.id"><spring:message code="stato" /></form:label></td>
						<td><c:out value="${soggettoForm.statoMorte.descrizione}" /><form:hidden path="statoMorte.id" /><form:hidden path="statoMorte.descrizione" />
						</td>										
						<td><form:label path="comuneMorte.id" ><spring:message code="comune"/></form:label></td>
						<td><c:out value="${soggettoForm.descrizioneComuneMorteItalianoEstero}" /><form:hidden path="comuneMorte.id" /><form:hidden path="comuneMorte.descrizione" /> <form:hidden path="descrizioneComuneEsteroMorte" /></td>
					</tr>
					<tr>
						<td><form:label path="dataMorte"><spring:message code="nuovosoggettofisico.datadecesso" /></form:label></td>
						<td colspan="3"><fmt:formatDate value="${soggettoForm.dataMorte}" type="date" pattern="dd/MM/yyyy" /></td>
										<form:hidden path="dataMorte"/>
					</tr>	
									
					</c:if> 
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo8"/></td>
					</tr>
					<c:if test="${soggettoForm.idSoggetto == null}">
					<tr>
						<td><form:label path="cognomePaternita"><spring:message code="cognome" /></form:label></td>
						<td><form:input path="cognomePaternita" maxlength="30" size="30"/>
										<form:errors path="cognomePaternita" class="testoRosso"/></td>
						<td><form:label path="nomePaternita"><spring:message code="nome" /></form:label></td>
						<td><form:input path="nomePaternita" maxlength="30" size="30"/>
										<form:errors path="nomePaternita" class="testoRosso"/></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo9" /></td>
					</tr>
					<tr>
						<td><form:label path="cognomeMaternita"><spring:message code="cognome" /></form:label></td>
						<td><form:input path="cognomeMaternita" maxlength="30" size="30"/>
							<form:errors path="cognomeMaternita" class="testoRosso"/></td>
						<td><form:label path="nomeMaternita"><spring:message code="nome" /></form:label></td>
						<td><form:input path="nomeMaternita" maxlength="30" size="30"/>
							<form:errors path="nomeMaternita" class="testoRosso"/></td>
					</tr>
					</c:if> 
					<c:if test="${soggettoForm.idSoggetto != null}">
					<tr>
						<td><form:label path="cognomePaternita"><spring:message code="cognome" /></form:label></td>
						<td><c:out value="${soggettoForm.cognomePaternita}" /><form:hidden path="cognomePaternita"/></td>
						<td><form:label path="nomePaternita"><spring:message code="nome" /></form:label></td>
						<td><c:out value="${soggettoForm.nomePaternita}" /><form:hidden path="nomePaternita"/></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo9" /></td>
					</tr>
					<tr>
						<td><form:label path="cognomeMaternita"><spring:message code="cognome" /></form:label></td>
						<td><c:out value="${soggettoForm.cognomeMaternita}" /><form:hidden path="cognomeMaternita" /></td>
						<td><form:label path="nomeMaternita"><spring:message code="nome" /></form:label></td>
						<td><c:out value="${soggettoForm.nomeMaternita}" /><form:hidden path="nomeMaternita" /></td>
					</tr>					
					</c:if> 
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
						<td class="td_tasto"><input id="backButton" type="button" value="Indietro" title="Torna alla ricerca dei soggetti fisici da associare"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
			
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>
			
			
			
			
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