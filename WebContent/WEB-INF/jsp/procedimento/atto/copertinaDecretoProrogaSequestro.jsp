<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
<%
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/decretoProrogaSequestro.htm" />
		<form:form method="post" id="id_formConfermaInvia" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			<p class="titolo_page">
				<spring:message code="atto.dettaglio" />
			</p>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<logic:notPresent name="listaDecreti">
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				<br />
				<form:hidden path="codAtto" />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />

				<c:if test="${attoForm.statoAtto.id != 3}">
					<table class="tb_form">
						<tr>
							<td class="td_titolo" colspan="4"></td>
						</tr>		
						<tr>
							<td><form:label path="dataEmissione" cssClass="required">
									<spring:message code="atto.dataemissione" />
								</form:label></td>
							<td><form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" /> <form:errors path="dataEmissione" class="testoRosso" /></td>
							<td><form:label path="dataDeposito" cssClass="required">
									<spring:message code="atto.datadeposito" />
								</form:label></td>
							<td><form:input path="dataDeposito" maxlength="10" id="id_dataDeposito" /> <form:errors path="dataDeposito" class="testoRosso" /></td>
						</tr>
					</table>
					<br/>
					<br/>	
					<table class="tb_bottoni" align="center">
						<tr>
					    <c:if test="${profilo.ruolo!='MAGISTRATO'}">
					    	<%-- Specifiche Intervento-006-SpeseProces-NumAtto --%>
							<jsp:include page="/WEB-INF/jsp/procedimento/atto/includeGeneraNumAtto.jsp" />
							<td class="td_tasto">
								<input type="button" value="Salva" onclick="inserisciAtto('Conferma', '${attoForm.allegato.documentoFirmato}')" title="Salva" class="nowait" />
							</td> 
							<input	id="Conferma" type="submit" name="Conferma" value="" style="display: none" />
					    
							<td class="td_tasto">
								<%-- Specifiche Intervento-006-SpeseProces-NumAtto: aggiunto messagio di conferma se numeAnnoAtto is null --%>
								<c:if test="${attoForm.numeAnnoAtto==null || attoForm.numeAtto==null}">
									<input type="button" value="Conferma e Invia" onclick="return confermaInvio('Invia', '${attoForm.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" />
							   	</c:if>
							   	<c:if test="${attoForm.numeAnnoAtto!=null && attoForm.numeAtto!=null}">
							   		<input type="button" value="Conferma e Invia" onclick="inserisciAtto('Invia', '${attoForm.allegato.documentoFirmato}')" title="Conferma e Invia" class="nowait" />
							   	</c:if>
							</td> 
							<input id="Invia" type="submit" name="Invia" value="" style="display: none" />
						</c:if>
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
					<br/>
					<br/>															
				</c:if>
				<c:if test="${attoForm.statoAtto.id == 3}">
					<table class="tb_form">
						<tr>
							<td class="td_titolo" colspan="4"></td>
						</tr>					
						<tr>
							<td>
								<form:label path="dataEmissione">
									<spring:message code="atto.dataemissione" />
								</form:label>
							</td>
							<td>
								<fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /> 
								<form:hidden path="dataEmissione" />
							</td>
							<td>
								<form:label path="dataDeposito">
									<spring:message code="atto.datadeposito" />
								</form:label>
							</td>
							<td>
								<fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /> 
								<form:hidden path="dataDeposito" />
							</td>							
						</tr>
						<tr>
							<td><logic:notEmpty name="ufficiListFromFlussiOut">
									<form:label path="destinatario.id">
										<spring:message code="atto.destinazione" />
									</form:label>
								</logic:notEmpty></td>
							<td><logic:notEmpty name="ufficiListFromFlussiOut">
									<table>
										<c:forEach var="destinat" items="${ufficiListFromFlussiOut}">
											<tr>
												<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
											</tr>
										</c:forEach>
									</table>
								</logic:notEmpty></td>
							<td><form:label path="dataRegistrazione">
									<spring:message code="atto.dataregistrazione" />
								</form:label></td>
							<td><fmt:formatDate value="${attoForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /></td>
						</tr>						
					</table>
					<c:if test="${attoForm.idProcedimentoCdatrib==null || attoForm.idProcedimentoCdatrib==0}" >
					<br/>
					<br/>	
					<table class="tb_bottoni" align="center">
						<tr>
							<smp:notificheButtons formId="id_formConfermaInvia" codAtto="${attoForm.codAtto}"></smp:notificheButtons>
							
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
					</c:if>
					<br/>
					<br/>	
				</c:if>
			</logic:notPresent>
		</form:form>
		
		<logic:notPresent name="listaDecreti">
	
				<spring:url var="azione1" value="/procedimento/atto/beneAssociatoADecretoProrogaSequestro.htm" />
				<form:form method="post" action="${azione1}" commandName="beneForm">
	
					<br />
					<table class="tb_form">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="bene.benidelatto" /></td>
						</tr>
					</table>
					<display:table class="tb_lista" uid="elemRisultatiRicerca" name="${risultatiRicerca}" pagesize="10" requestURI="${azione1}">
						<display:column property="bene.idBene" title="Identificativo" sortable="false" />
						<display:column property="bene.descrizioneBene" title="Descrizione" sortable="false" />
						<display:column title="Misura - Titolari di diritto">
							<display:table class="tb_sotto_lista" uid="soggetto" name="${elemRisultatiRicerca.soggetti}" requestURI="${azione1}">
								<display:column property="misura.tipoMisura.descrizione" title="" />
								<display:column property="normativa.descrizione" title="" />
								<display:column property="descrizioneSoggetto" title="" />
								<display:column property="tipoInteresse.descrizione" title="" />
								<display:column property="descrizioneFrazioneOQuota" title="" />
							</display:table>
						</display:column>
					</display:table>
	
					<br />
	
				</form:form>
				<br />
	
		</logic:notPresent>
		<logic:present name="listaDecreti">
			<spring:url var="azioneSelDecreto" value="/procedimento/atto/decretoProrogaSequestro.htm" />
			<form:form method="post" action="${azioneSelDecreto}" commandName="attoForm">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="codAtto">
								<spring:message code="atto.decreto.sequestro" />
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
				<form:hidden path="idUfficio" id="idUfficio" />
			</form:form>
		</logic:present>
	</tiles:putAttribute>
</tiles:insertDefinition>

<script type="text/javascript">

	$(document).ready(function() {
		prendiData("#dataEsecuzione",true);
		prendiData("#id_dataDeposito",true);
		prendiData("#id_dataEmissione",true);
	});		

<%-- Specifiche Intervento-006-SpeseProces-NumAtto --%>
if (${attoForm.numeAnnoAtto==null || attoForm.numeAtto==null}) {
	function confermaInvio(submit, documentoFirmato) {
		var check = checkDate();
		if (check && confirm("Si è scelto di voler procedere senza aver generato precedentemente il numero dell'atto e quindi una volta confermata l'operazione non sarà più possibile modificare il file allegato. Si è sicuri di voler continuare?")) {
			inserisciAtto(submit, documentoFirmato);
			return true;
		}
		return false;
	}
}
</script>
