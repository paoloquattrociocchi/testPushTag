<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"prefix="fn" %>
<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

<%
	
	AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
	
%>

<c:set var="contatore" value="1"/>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">

		<logic:notPresent name="listaDecreti">
			<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
	
			<spring:url var="azione" value="/procedimento/atto/associaDecretoRevocaApplicazioneProvvisoria.htm" />
			<p class="titolo_page">
				<spring:message code="atto.decreto.revocaApplicazioneProvvisoria" />
			</p>
			<form:form method="post" id="id_formConfermaInvia" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br/>			
			    <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				<br/>
	
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<form:hidden path="soggetto.idSoggetto" />
					<form:hidden path="soggetto.idVersione" />
					<form:hidden path="soggetto.tipoSoggetto" />
					<form:hidden path="soggetto.idProcedimento" />
					<form:hidden path="soggetto.idUfficio" />
					<form:hidden path="soggetto.misura.idMisureCaricate" />
					<form:hidden path="idAtto" />
					<form:hidden path="codAtto" />
					<form:hidden path="soggetto.misura.idVecchiePrescrizioni" />
					<form:hidden path="soggetto.schermataDiProvenienza" />
					<form:hidden path="soggetto.misura.noteApp" />
					<form:hidden path="soggetto.tipologiaPrevenzioneDb.id" />
	
					<tr>
						<td class="td_titolo" colspan="4"><c:out value="${attoForm.soggetto.descrizioneSoggetto}" /></td>
					</tr>
				</table>
				
				<br/>
	
				<table class="tb_form" border="0">
					<tr>
						
						<td><form:label path="soggetto.misura.tipoMisura" >
								<spring:message code="soggetto.misurapersonale" />
							</form:label>
						</td>
						<td><form:select path="soggetto.misura.tipoMisura.id" size="1" onchange="this.form.submit()">
								<form:option value="-1" label="---Nessun Tipo Misura Personale---" />
								<c:forEach var="tipoMisura" items="${tipiMisuraTipoAP}">
									<c:if test="${tipoMisura.id == soggettoOrigine.misura.tipoMisura.id}">
										<form:option value="${tipoMisura.id}" label="${tipoMisura.descrizione}" />
									</c:if>
								</c:forEach>
								
								<form:errors path="soggetto.misura.tipoMisura.id" class="testoRosso" />
							</form:select>

					    </td>
						<td><form:label path="soggetto.misura.normativaDiRiferimento" >
								<spring:message code="procedimento.normativa" />
							</form:label>
						</td>
						<td><form:select path="soggetto.misura.normativaDiRiferimento.id" size="1">
								<form:option value="-1" label="------Nessuna Normativa-------" />
								<c:forEach var="normative" items="${normativeMisPers}">
									<c:if test="${normative.id == soggettoOrigine.misura.normativaDiRiferimento.id}">
										<form:option value="${normative.id}" label="${normative.descrizione}" />
									</c:if>
								</c:forEach>								
								<form:errors path="soggetto.misura.normativaDiRiferimento.id" class="testoRosso" />
							</form:select>
						</td>
					</tr>
	
					<tr>
						<td><form:label path="soggetto.misura.localita">
								<spring:message code="soggetto.misurapersonale.localita" />
							</form:label></td>
						<td colspan="3">
							<form:textarea path="soggetto.misura.localita" rows="2" cssStyle="width:98%" readonly="true" />
						</td>
					</tr>
					
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.prescrizioni" /></td>
					</tr>
	
					<c:forEach var="prescrizione" items="${listaPrescrizioni}" varStatus="loop">
						<tr>
							<td colspan="4"><form:checkbox path="soggetto.misura.idSelezionati" value="${prescrizione.id}" label="${prescrizione.descrizione}" onClick="abilitaUlterioriPrescrizioni2(this,'${soggettoOrigine.misura.ulterioriPrescrizioni}');" disabled="true"/></td>
						</tr>
					</c:forEach>
					<tr>
						<td><form:label path="soggetto.misura.ulterioriPrescrizioni">
								<spring:message code="soggetto.misurapersonale.ulterioriprescrizioni" />
							</form:label></td>
						<td colspan="3"><form:textarea path="soggetto.misura.ulterioriPrescrizioni" id="note" rows="2" cssStyle="width:98%" readonly="true" /></td>
					</tr>
	
	
	
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="atto.art9c1" /></td>
					</tr>
					<tr>
						<td><form:checkbox path="soggetto.misura.art9c1" value="S" disabled="true"/> 
						    <form:label path="soggetto.misura.art9c1">
						    </form:label>
					   </td>				
						<td  colspan="3"><form:label path="soggetto.misura.art9c1">
								<spring:message code="atto.decreto.sospensionePassaporto" />
							</form:label> <form:errors path="soggetto.misura.art9c1" class="testoRosso" />
					    </td>
	
					</tr>
				</table>
				
				</br>
				</br>
			   <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="dataEmissione">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td>
							<c:if test="${attoForm.statoAtto.id != 3}">	
								<form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" /> 
								<form:errors path="dataEmissione" class="testoRosso" />
							</c:if>
							<c:if test="${attoForm.statoAtto.id == 3}">	
								<fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" />
							</c:if>								
						</td>
						<td><form:label path="dataDeposito">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td>
							<c:if test="${attoForm.statoAtto.id != 3}">	
								<form:input path="dataDeposito" maxlength="10" id="id_dataDeposito" />
								<form:errors path="dataDeposito" class="testoRosso" />
							</c:if>
							<c:if test="${attoForm.statoAtto.id == 3}">	
								<fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" />
							</c:if>								
						</td>
						</tr>
					<tr>
			
			</table>			
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />	

			<c:if test="${attoForm.statoAtto.id==3}">
			    <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="destinatario.id">
								<spring:message code="atto.destinazione" />
							</form:label></td>
						<td>
							<table>
								<c:forEach var="destinat" items="${ufficiListFromFlussiOut}">
									<tr>
										<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
									</tr>
								</c:forEach>
							</table>
						</td>
						<td><form:label path="dataRegistrazione">
								<spring:message code="atto.dataregistrazione" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>
	    		</table>					
			</c:if>
				
			<br />
			
				<table class="tb_bottoni" align="center">
					<tr>
						<smp:notificheButtons formId="id_formConfermaInvia" codAtto="${attoForm.codAtto}"></smp:notificheButtons>
						<%-- Specifiche Intervento-006-SpeseProces-NumAtto --%>
						<c:if test="${profilo.ruolo!='MAGISTRATO' && attoForm.statoAtto.id != 3 && attoForm.dataRegistrazione==null}">
							<jsp:include page="/WEB-INF/jsp/procedimento/atto/includeGeneraNumAtto.jsp" />
						</c:if>
				   		<td class="td_tasto">		
						<c:if test="${profilo.ruolo!='MAGISTRATO'}">	
							<c:if test="${attoForm.statoAtto.id != 3}">			
								<input type="submit" value="Salva" name="SalvaApplicazioneProvvisoria">						
								<%-- Specifiche Intervento-006-SpeseProces-NumAtto: aggiunto messagio di conferma se numeAnnoAtto is null --%>
								<c:if test="${attoForm.numeAnnoAtto==null || attoForm.numeAtto==null}">
									<input type="button" value="Conferma e Invia" onclick="return confermaInvio('ConfermaInviaApplicazioneProvvisoria');" title="Conferma e Invia Decreto Revoca Applicazione Provvisoria" class="nowait" />
				            </c:if>	
							   	<c:if test="${attoForm.numeAnnoAtto!=null && attoForm.numeAtto!=null}">
							   		<input type="button" value="Conferma e Invia" onclick="$('#ConfermaInviaApplicazioneProvvisoria').click();" title="Conferma e Invia Decreto Revoca Applicazione Provvisoria" class="nowait" />
				          </c:if>	   					
							   	<input type="submit" name="ConfermaInviaApplicazioneProvvisoria" id="ConfermaInviaApplicazioneProvvisoria" value="" style="display: none" />
								<!-- <input type="submit" value="Conferma e Invia" name="ConfermaInviaApplicazioneProvvisoria" id="ConfermaInviaApplicazioneProvvisoria"> -->
							</c:if>	
						</c:if>	   					
					        <input type="button" id="id_buttonIndietro" value="Indietro" title="Indietro">
						</td>								
					</tr>
				</table>
			</form:form>
		
		</logic:notPresent>
		<logic:present name="listaDecreti">
			<p class="titolo_page">
				<spring:message code="atto.decreto.revocaApplicazioneProvvisoria" />
			</p>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br/>			
		    <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br/>		
			<spring:url var="azioneSelDecreto" value="/procedimento/atto/decretoRevocaApplicazioneProvvisoria.htm" />
			<form:form method="post" action="${azioneSelDecreto}" commandName="attoForm">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="codAtto">
								<spring:message code="atto.decreto.applicazioneProvvisoria" />
							</form:label></td>
						<td>
						<form:select path="codAtto" size="1">
								<form:option value="-1" label="------Seleziona Decreto-------" />
								<form:options items="${listaDecreti}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:hidden path="tipoAtto.id" /> <form:hidden path="categoriaAtto.id" />
						</td>
					</tr>
				</table>
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto">
							<input type="submit" value="Conferma" name="SelezionaDecreto">
						</td>
						<td class="td_tasto">
							<c:if test="${fromMenuAtti=='elenco'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
							</c:if> 
							<c:if test="${fromMenuAtti=='ordine'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
							</c:if> 
							<c:if test="${fromMenuAtti=='nuovo'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
							</c:if>
						</td>
					</tr>
				</table>
			</form:form>
		</logic:present>		

		<smp:back buttonId="id_buttonIndietro"></smp:back>
		<script type="text/javascript">
		
			$(document).ready(function() {
				prendiData("#id_dataEmissione",true);
				prendiData("#id_dataDeposito",true);
		
				$("#idForm").bind('submit', function() {
					return messaggio(this);
				});
			});				

			<c:if test="${attoForm.statoAtto.id != 3}">	
			
				//controllo selezione campi misura
				var size = ${fn:length(listaPrescrizioni)};
				var sizeOrigine = ${fn:length(soggettoOrigine.misura.idSelezionati)};
				for(var i=0;i<size;i++) {
					var el = document.getElementsByName("soggetto.misura.idSelezionati")[i];
					var elOrigine;
					<c:forEach var="elOrigine" items='${soggettoOrigine.misura.idSelezionati}' varStatus="count">
						var appoggio = ${elOrigine};
						if(el.value == appoggio) el.disabled=false;
					</c:forEach>
				}
	
				var misura = document.getElementsByName("soggetto.misura.tipoMisura.id");
				if(misura.value<1) {
					misura.readonly=false;
				}
				var normativa = document.getElementsByName("soggetto.misura.normativaDiRiferimento.id");
				if(normativa.value<1) {
					normativa.readonly=false;
				}			
	
				var art9c1 = document.getElementsByName("soggetto.misura.art9c1")[0];
				if(${soggettoOrigine.misura.art9c1 == 'S'}) {
					art9c1.disabled=false;
				}


				<%-- Specifiche Intervento-006-SpeseProces-NumAtto --%>
				<c:if test="${attoForm.numeAnnoAtto==null || attoForm.numeAtto==null}">
				function confermaInvio(idSubmitButton) {
					var check = checkDate();
					if (check && confirm("Si è scelto di voler procedere senza aver generato precedentemente il numero dell'atto e quindi una volta confermata l'operazione non sarà più possibile modificare il file allegato. Si è sicuri di voler continuare?")) {
						$('#' + idSubmitButton).click();
						return true;
					}
					return false;
				}
				</c:if>

			</c:if>
			
			<c:if test="${attoForm.statoAtto.id == 3}">	
			    
				var misura = document.getElementsByName("soggetto.misura.tipoMisura.id")[0];
				misura.disabled=true;
				var normativa = document.getElementsByName("soggetto.misura.normativaDiRiferimento.id")[0];
				normativa.disabled=true;
				var art9c1 = document.getElementsByName("soggetto.misura.art9c1")[0];
				art9c1.disabled=true;
				var localita = document.getElementsByName("soggetto.misura.localita")[0];
				localita.disabled=true;
				var ulterioriPrescrizioni = document.getElementsByName("soggetto.misura.ulterioriPrescrizioni")[0];
				ulterioriPrescrizioni.disabled=true;
			</c:if>			
			
		</script>
			
				
	</tiles:putAttribute>
</tiles:insertDefinition>