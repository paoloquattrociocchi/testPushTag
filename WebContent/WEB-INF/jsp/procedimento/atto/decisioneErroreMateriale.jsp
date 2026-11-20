<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<%
	AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content" >
			<spring:url var="azione" value="/procedimento/soggetto/decisioneErrore.htm" />
			<p class="titolo_page">
				<spring:message code="errore.materiale.decisione" />
			</p>
			<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
			<form:form method="post" action="${azione}" commandName="soggettoForm" >

				<form:hidden path="idSoggetto" />
				<form:hidden path="tipoSoggetto"/>

				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />

				<br/>	
				<br/>
				
				
					
				<display:table id="row" name="${soggettoDaCorregere}" class="tb_lista" uid="amm" >
					<display:column property="descrizioneSoggetto" title="Generalità - Soggetto da correggere" sortable="false"/>
				</display:table>	
				<br/>					
				<table class="tb_form">				
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="errore.materiale.decisione" /></td>
						</tr>
						
						<logic:notEqual scope="session" name="atto" property="categoriaAtto.id" value="39" >
						<tr>
							<td><form:label path="decisioneErroreMaterialeIGrado.id" cssClass="required">
								<spring:message code="errore.materiale.decisione.Igrado" />
								</form:label>
							</td>
							<td colspan="3">
							<logic:notEqual value="3" name="atto" property="statoAtto.id" scope="session">
								<form:select path="decisioneErroreMaterialeIGrado.id" size="1" disabled="${readOnlyDecisioneIGrado}" onchange="this.form.submit()">
									<form:option value="-1" label="------Seleziona Decisione -------" />
									<form:options items="${esitiDecisione}" itemValue="id" itemLabel="descrizione"/> 	
								</form:select>
								<form:errors path="decisioneErroreMaterialeIGrado.id" class="testoRosso"/>
							</logic:notEqual>	
							<logic:equal value="3" name="atto" property="statoAtto.id" scope="session">
								<form:select path="decisioneErroreMaterialeIGrado.id" size="1" disabled="${readOnly}">
									<form:option value="-1" label="------Seleziona Decisione -------" />
									<form:options items="${esitiDecisione}" itemValue="id" itemLabel="descrizione"/> 	
								</form:select>
								<form:errors path="decisioneErroreMaterialeIGrado.id" class="testoRosso"/>
							</logic:equal>							
							</td>
						</tr>
						
							<tr>
								<td><form:label path="descAltro1"><spring:message code="errore.materiale.motivazione" /></form:label></td>
								<td colspan="3"><form:textarea path="descAltro1" disabled="${readOnlyMotivazione}" cssStyle="width:98%"  rows="2"/></td>					
							</tr>
						</logic:notEqual>
						
					   <logic:equal scope="session" name="atto" property="categoriaAtto.id" value="39" >
					   
						<tr>
							<td><form:label path="decisioneErroreMaterialeIGrado.id">
								<spring:message code="errore.materiale.decisione.Igrado" />
								</form:label>
							</td>
							<td colspan="3">	
								<form:select path="decisioneErroreMaterialeIGrado.id" size="1" disabled="${readOnly}">
									<form:option value="-1" label="------Seleziona Decisione -------" />
									<form:options items="${esitiDecisione}" itemValue="id" itemLabel="descrizione"/> 	
								</form:select>
								<form:errors path="decisioneErroreMaterialeIGrado.id" class="testoRosso"/>
					
							</td>
						</tr>					   
							<tr>
								<td><form:label path="decisioneErroreMaterialeIIIGrado.id" cssClass="required">
									<spring:message code="errore.materiale.decisione.IIIgrado" />
									</form:label>
								</td>
								<td colspan="3">
									<form:select path="decisioneErroreMaterialeIIIGrado.id" size="1" disabled="${readOnlyDecisione3Grado}" >
										<form:option value="-1" label="------Seleziona Decisione -------" />
										<form:options items="${esitiDecisione3}"  itemValue="id" itemLabel="descrizione" /> 	
									</form:select>
									<form:errors path="decisioneErroreMaterialeIIIGrado.id" class="testoRosso"/>
								</td>
							</tr>
							
						    <tr>
							  <td><form:label path="descAltro3"><spring:message code="errore.materiale.motivazione" /></form:label></td>
							  <td colspan="3"><form:textarea disabled="${readOnlyMotivazione}"  path="descAltro3" cssStyle="width:98%"  rows="2"/></td>											
						   </tr>		
						   
					    <tr>

							<td><form:label path="dataRicezioneSentenza" cssClass="required">
									<spring:message code="soggetto.dataRicezioneSentenza" />
								</form:label></td>
							<td  colspan="3"><form:input path="dataRicezioneSentenza" maxlength="10" disabled="${readOnlyDecisione3Grado}" id="id_dataRicezioneSentenza" /> <br> <form:errors path="dataRicezioneSentenza" class="testoRosso" /></td>

					    </tr>
					    <tr>
							<td><form:label path="numeAnnoSentenza" cssClass="required"><spring:message code="soggetto.numeAnnoSentenza" /></form:label></td>
							<td><form:input path="numeAnnoSentenza" id="numeAnnoSentenza" maxlength="4" disabled="${readOnlyDecisione3Grado}" size="5" onkeypress="return isNumberKey(event)" />
								<form:errors path="numeAnnoSentenza" class="testoRosso"/></td>
								
							<td><form:label path="numeSentenza" cssClass="required"><spring:message code="soggetto.numeSentenza" /></form:label></td>
							<td><form:input path="numeSentenza" maxlength="12" disabled="${readOnlyDecisione3Grado}" onkeypress="return isNumberKey(event)" />
								<form:errors path="numeSentenza" class="testoRosso"/></td>
						</tr> 			
										   					
				   </logic:equal>							
					</table>
					<br/>
					<br/>	
				<table class="tb_form">	
				 <logic:equal value="F" name="soggettoForm" property="tipoSoggetto" scope="request">
                
					<tr>
							<td><form:label path="cognome" cssClass="required"><spring:message code="cognome" /></form:label></td>
							<td><form:input path="cognome"  disabled="${readOnly}" maxlength="35" size="30"/>
								<form:errors path="cognome" class="testoRosso"/>
							</td>
							<td><form:label path="nome" cssClass="required"><spring:message code="nome" /></form:label></td>
							<td><form:input path="nome" disabled="${readOnly}" maxlength="35" size="30"/>
								<form:errors path="nome" class="testoRosso"/>
							</td>
					</tr>
					<tr>
							<td><form:label path="dataNascita" cssClass="required"><spring:message code="dataNascita" /></form:label></td>
							<td>
									<form:input path="dataNascita" disabled="${readOnly}" maxlength="10" size="12" id="dataNascita"/>
									<form:errors path="dataNascita" class="testoRosso" />

							</td>
					</tr>
					<tr>
							<td><form:label path="statoNascita.id" cssClass="required"><spring:message code="stato" /></form:label></td>
							<td><form:select id="id_StatoNascita" path="statoNascita.id" size="1"  disabled="${readOnly}" onchange="controllaCampiEAutocomplete(this.value,'ComuneNascita')">
									<form:option value="-1" label="---Seleziona Stato---"/>
									<form:options items="${statiNascita}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>
								<form:errors path="statoNascita.id" class="testoRosso" />
							</td>						
							<td><form:label path="comuneNascita.id" cssClass="required"><spring:message code="comune"/></form:label></td>
				        	<td>
								<div class="ui-widget">		
									<form:input id="id_descrizioneComuneNascita" disabled="${readOnly}" path="comuneNascita.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
									<div id="id_loadingComuneNascita"></div>
									<form:hidden id="id_hiddenComuneNascita" path="comuneNascita.id"/>
									<form:errors path="comuneNascita.descrizione" class="testoRosso" />
								</div>
				        	</td>	
					</tr>
					<tr>
							<td><form:label path="sesso" cssClass="required"><spring:message code="nuovosoggettofisico.sesso" /></form:label></td>
							<td><form:select path="sesso" size="1" disabled="${readOnly}">
									<form:option value="-1">Seleziona Sesso</form:option>
									<form:option value="M" label="Maschio" />
									<form:option value="F" label="Femmina" />
								</form:select> <form:errors path="sesso" class="testoRosso" />
							</td>
							<td><form:label path="codiceFiscale"><spring:message code="codiceFiscale" /></form:label></td>					   
						 	<td>
								<form:input path="codiceFiscale" disabled="${readOnly}" maxlength="16" size="25"/>
							   <c:if test="${readOnly =='false'}" >
								<!-- MEV 22 del PLO sett 2017 -->
								<c:set var="checkCF" value="disabled"/>
								<c:if test="${(sessionScope.profilo.idTipoUfficio==19) || (sessionScope.profilo.idTipoUfficio==6)}">
								</c:if>
								<input id="verificaCF" name="verificaCF" type="submit" value="Verifica CF" title="Verifica del Codice Fiscale" <c:out value="${checkCF}"/>>
							   </c:if>
							 </td>
							
					</tr>
	
				 </logic:equal>
				 <logic:equal value="G" name="soggettoForm" property="tipoSoggetto" scope="request">
					
					<tr>
						<td><form:label path="ragioneSociale" cssClass="required"><spring:message code="soggettogiuridico.ragioneSociale" /></form:label></td>
						<td><form:input path="ragioneSociale" disabled="${readOnly}" maxlength="120" size="40"/> 
							<form:errors path="ragioneSociale" class="testoRosso"/></td>
						<td><form:label path="partitaIva" cssClass="required"><spring:message code="soggettogiuridico.partitaIva" /></form:label></td>
						<td>
							<form:select id="id_StatoSiglaPiva" path="partitaIvaSiglaStato" size="1" disabled="${readOnly}">
								<form:options items="${statiPiva}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							
							<form:input path="partitaIva" maxlength="12" size="18" disabled="${readOnly}"/>
							<input id="verificaPI" name="verificaPI" type="submit" value="Verifica PI" title="Verifica della Partita Iva">
						
							<form:errors path="partitaIva" class="testoRosso"/>
						</td>
					</tr>
					<tr>
						<td><form:label path="tipoSocieta" cssClass="required">
								<spring:message code="soggettogiuridico.tipoSocieta" />
							</form:label></td>
						<td>
							<form:select path="idTipoSocieta" size="1" disabled="${readOnly}">
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${tipiSocieta}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:errors path="idTipoSocieta" class="testoRosso"/>
						</td>
						<td><form:label path="numeroCCIAA">
								<spring:message code="soggettogiuridico.numeroCCIAA" /></form:label></td>
						<td><form:input path="numeroCCIAA"  disabled="${readOnly}"  maxlength="30" size="30"/>
						</td>
					</tr>			
					<tr>
						<td class="td_titolo" colspan="4"><spring:message
								code="nuovosoggettogiuridico.titolo1" /></td>
					</tr>
					<tr>
						<td><form:label path="statoSedeLegale.id" cssClass="required"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoSedeLegale" disabled="${readOnly}" path="statoSedeLegale.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneSedeLegale')" >
								<form:option value="-1" label="---Seleziona Stato---"/>
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:errors path="statoSedeLegale.id" class="testoRosso" />
						</td>		
						<td><form:label path="comuneSedeLegale.id" cssClass="required"><spring:message code="comune"/></form:label></td>
			        	<td>
							<div class="ui-widget">		
								<form:input id="id_descrizioneComuneSedeLegale" path="comuneSedeLegale.descrizione"  disabled="${readOnly}" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComune"></div>
								<form:hidden id="id_hiddenComuneSedeLegale" path="comuneSedeLegale.id"/>
							</div>
							<form:errors path="comuneSedeLegale.id" class="testoRosso" />
			        	</td>	
					</tr>
					<tr>
						<td><form:label path="indirizzoSedeLegale.toponimo" ><spring:message code="indirizzo" /></form:label></td>
						<td>						
							<form:select path="indirizzoSedeLegale.toponimo.id" size="1" disabled="${readOnly}">	
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:input path="indirizzoSedeLegale.denominazione" disabled="${readOnly}" maxlength="50" size="40"/>
						</td>
						<td><form:label path="indirizzoSedeLegale.civico">
								<spring:message code="civico" />
							</form:label></td>
						<td colspan="2"><form:input path="indirizzoSedeLegale.civico" disabled="${readOnly}" maxlength="20" size="20"/></td>
					</tr>			
			</logic:equal>
			</table>							
				
				<br/>
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
					<logic:notEqual value="3" name="atto" property="statoAtto.id" scope="session">
					    <td class="td_tasto">
							<input type="submit" value="Salva" name="SalvaDecisioneErroreMateriale">	
						</td>	
					</logic:notEqual>		

						<td class="td_tasto">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atto/copertinaAtto.htm')">
						</td>
	
					</tr>
				</table>
				
			</form:form>	
					<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
				<smp:back buttonId="id_buttonIndietro"></smp:back>	


		<script type="text/javascript">
					$(document).ready(function() {
						prendiData("#dataNascita",true);
						prendiData("#id_dataRicezioneSentenza",true);
	

						controllaInizializzazioneCampi($( "#id_StatoSedeLegale" ).val(),"ComuneSedeLegale");
						controllaInizializzazioneCampi($( "#id_StatoNascita" ).val(),"ComuneNascita");
			
						});

				if($( "#id_StatoNascita" ).val() == 1){
					doAutocomplete('ComuneNascita');
				}

				if($( "#id_StatoSedeLegale" ).val() == 1)
				{
					doAutocomplete('ComuneSedeLegale');
				}
				
			   function isNumberKey(evt)
		       {
		          var charCode = (evt.which) ? evt.which : evt.keyCode;
		          if (charCode < 48 || charCode > 57)
		             return false;

		          return true;
		       }


				
			</script>	
			
				
		</tiles:putAttribute>
	</tiles:insertDefinition>			