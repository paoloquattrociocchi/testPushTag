<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione" value="/procedimento/atto/versionamentoSoggettoFisico.htm" />
			<form:form method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			
				<form:hidden path="soggetto.idSoggetto" />
				<form:hidden path="soggetto.idVersione" />
				<form:hidden path="soggetto.tipoSoggetto" value="<%= it.giustizia.smp.utility.Constants.SOGGETTO_FISICO%>"/>
			
				<p class="titolo_page"><spring:message code="soggettoFisico.versionamento.dettaglio"/></p>
				<logic:present name="procedimento">
					<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				</logic:present>
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo1" /></td>
					</tr>
					<tr>
							<td><form:label path="soggetto.cognome" cssClass="required"><spring:message code="cognome" /></form:label></td>
							<td><form:input path="soggetto.cognome" maxlength="35" size="30"/>
								<form:errors path="soggetto.cognome" class="testoRosso"/>
							</td>
							<td><form:label path="soggetto.nome" cssClass="required"><spring:message code="nome" /></form:label></td>
							<td><form:input path="soggetto.nome" maxlength="35" size="30"/>
								<form:errors path="soggetto.nome" class="testoRosso"/>
							</td>
					</tr>
					<tr>
							<td><form:label path="soggetto.dataNascita" cssClass="required"><spring:message code="dataNascita" /></form:label></td>
							<td>
									<form:input path="soggetto.dataNascita" maxlength="10" size="12" id="dataNascita"/>
									<form:errors path="soggetto.dataNascita" class="testoRosso" />

							</td>
					</tr>
					<tr>
							<td><form:label path="soggetto.statoNascita.id" cssClass="required"><spring:message code="stato" /></form:label></td>
							<td><form:select id="id_StatoNascita" path="soggetto.statoNascita.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneNascita')">
									<form:option value="-1" label="---Seleziona Stato---"/>
									<form:options items="${statiNascita}"  itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>
								<form:errors path="soggetto.statoNascita.id" class="testoRosso" />
							</td>						
							<td><form:label path="soggetto.comuneNascita.id" cssClass="required"><spring:message code="comune"/></form:label></td>
				        	<td>
								<div class="ui-widget">		
									<form:input id="id_descrizioneComuneNascita" path="soggetto.comuneNascita.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
									<div id="id_loadingComuneNascita"></div>
									<form:hidden id="id_hiddenComuneNascita" path="soggetto.comuneNascita.id"/>
									<form:errors path="soggetto.comuneNascita.descrizione" class="testoRosso" />
								</div>
				        	</td>	

					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo2" /></td>
					</tr>
					<tr>
							<td><form:label path="soggetto.sesso" cssClass="required"><spring:message code="nuovosoggettofisico.sesso" /></form:label></td>
							<td><form:select path="soggetto.sesso" size="1">
									<form:option value="-1">Seleziona Sesso</form:option>
									<form:option value="M" label="Maschio" />
									<form:option value="F" label="Femmina" />
								</form:select> <form:errors path="soggetto.sesso" class="testoRosso" />
							</td>
							<td><form:label path="soggetto.codiceFiscale"><spring:message code="codiceFiscale" /></form:label></td>
							<td>
								<form:input path="soggetto.codiceFiscale" maxlength="16" size="25"/>
								<!-- MEV 22 del PLO sett 2017 -->
								<c:set var="checkCF" value="disabled"/>
								<c:if test="${(sessionScope.profilo.idTipoUfficio==19) || (sessionScope.profilo.idTipoUfficio==6)}">
								</c:if>
								<input id="verificaCF" name="verificaCF" type="submit" value="Verifica CF" title="Verifica del Codice Fiscale" <c:out value="${checkCF}"/>>
							</td>
					</tr>
					<tr>
						<td><form:label path="soggetto.cui"><spring:message code="soggettofisico.cui" /></form:label></td>
						<td><form:input path="soggetto.cui" maxlength="7" size="7" /></td>	
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo7" /></td>
					</tr>
					<tr>
						<td><form:label path="soggetto.statoMorte.id"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoMorte" path="soggetto.statoMorte.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneMorte')" >
								<form:option value="-1" label="---Seleziona Stato---"/>	
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>										
						<td><form:label path="soggetto.comuneMorte.id" ><spring:message code="comune"/></form:label></td>
			        	<td>
							<div class="ui-widget">		
								<form:input id="id_descrizioneComuneMorte" path="soggetto.comuneMorte.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComuneMorte"></div>
								<form:hidden id="id_hiddenComuneMorte" path="soggetto.comuneMorte.id"/>
								<form:errors path="soggetto.comuneMorte.descrizione" class="testoRosso" />
							</div>
			        	</td>
					</tr>
					<tr>
						<td><form:label path="soggetto.dataMorte"><spring:message code="nuovosoggettofisico.datadecesso" /></form:label></td>
						<td colspan="3"><form:input path="soggetto.dataMorte" maxlength="10" size="12" id="dataMorte"/>
									<form:errors path="soggetto.dataMorte" class="testoRosso" /></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo8"/></td>
					</tr>
					<tr>
						<td><form:label path="soggetto.cognomePaternita"><spring:message code="cognome" /></form:label></td>
						<td><form:input path="soggetto.cognomePaternita" maxlength="30" size="30"/>
										<form:errors path="soggetto.cognomePaternita" class="testoRosso"/></td>
						<td><form:label path="soggetto.nomePaternita"><spring:message code="nome" /></form:label></td>
						<td><form:input path="soggetto.nomePaternita" maxlength="30" size="30"/>
										<form:errors path="soggetto.nomePaternita" class="testoRosso"/></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo9" /></td>
					</tr>
					<tr>
						<td><form:label path="soggetto.cognomeMaternita"><spring:message code="cognome" /></form:label></td>
						<td><form:input path="soggetto.cognomeMaternita" maxlength="30" size="30"/>
							<form:errors path="soggetto.cognomeMaternita" class="testoRosso"/></td>
						<td><form:label path="soggetto.nomeMaternita"><spring:message code="nome" /></form:label></td>
						<td><form:input path="soggetto.nomeMaternita" maxlength="30" size="30"/>
							<form:errors path="soggetto.nomeMaternita" class="testoRosso"/></td>
					</tr>
					<tr>
						<td>&nbsp;</td>	
					</tr>				
					<tr>
						<td><form:label path="soggetto.magistrato.id"><spring:message code="atto.versionamento.magistrato" /></form:label></td>
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
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />			
			
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" name="Conferma" title="Conferma Versionamento" value="Conferma"></td>
											
						<td class="td_tasto"><input id="id_buttonIndietro" type="button" value="Indietro"/></td>					
					</tr>
				</table>

			</form:form>				
				<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
				<smp:back buttonId="id_buttonIndietro"></smp:back>


			<script type="text/javascript">
					$(document).ready(function() {
						prendiData("#dataNascita",true);
						prendiData("#dataMorte",true);
						prendiData("#id_dataEmissione",true);
						prendiData("#id_dataDeposito",true);

						controllaInizializzazioneCampi($( "#id_StatoNascita" ).val(),"ComuneNascita");
						controllaInizializzazioneCampi($( "#id_StatoMorte" ).val(),"ComuneMorte");
						});

				if($( "#id_StatoNascita" ).val() == 1){
					doAutocomplete('ComuneNascita');
				}
				
				if($( "#id_StatoMorte" ).val() == 1){
					doAutocomplete('ComuneMorte');
				}
				
			</script>			

		</tiles:putAttribute>
	</tiles:insertDefinition>
