<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<%
	AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">

		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>

		<spring:url var="azione" value="/procedimento/atto/associaDecretoApplicazioneProvvisoria.htm" />
		<p class="titolo_page">
			<spring:message code="atto.decreto.applicazioneProvvisoria" />
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
					 <c:if test="${disabledObject!='true'}">
					<td><form:select path="soggetto.misura.tipoMisura.id" size="1" onchange="this.form.submit()" disabled="${disabledObject}">
							<form:option value="-1" label="---Seleziona Tipo Misura Personale---" />
							<form:options items="${tipiMisuraTipoAP}" itemValue="id" itemLabel="descrizione" />
							<form:errors path="soggetto.misura.tipoMisura.id" class="testoRosso" />
						</form:select>
				    </td>
					</c:if>
					 <c:if test="${disabledObject=='true'}">
					<td><form:select path="soggetto.misura.tipoMisura.id" size="1" disabled="${disabledObject}">
							<form:option value="-1" label="---Seleziona Tipo Misura Personale---" />
							<form:options items="${tipiMisuraTipoAP}" itemValue="id" itemLabel="descrizione" />
							<form:errors path="soggetto.misura.tipoMisura.id" class="testoRosso" />
						</form:select>
				    </td>
					</c:if>
					
					<td><form:label path="soggetto.misura.normativaDiRiferimento" >
							<spring:message code="procedimento.normativa" />
						</form:label>
					</td>
					<td><form:select path="soggetto.misura.normativaDiRiferimento.id" size="1" disabled="${disabledObject}">
							<form:option value="-1" label="------Seleziona Normativa-------" />
							<form:options items="${normativeMisPers}" itemValue="id" itemLabel="descrizione" />
							<form:errors path="soggetto.misura.normativaDiRiferimento.id" class="testoRosso" />
						</form:select>
					</td>
				</tr>

				<tr>
					<td><form:label path="soggetto.misura.localita">
							<spring:message code="soggetto.misurapersonale.localita" />
						</form:label></td>
					<td colspan="3"><form:textarea path="soggetto.misura.localita" rows="2" cssStyle="width:98%" disabled="${disabledObject}"/></td>
				</tr>
				
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.prescrizioni" /></td>
				</tr>


				<c:forEach var="prescrizione" items="${listaPrescrizioni}">
					<tr>
						<td colspan="4"><form:checkbox path="soggetto.misura.idSelezionati" value="${prescrizione.id}" label="${prescrizione.descrizione}" onClick="abilitaUlterioriPrescrizioni(this);" disabled="${disabledObject}"/></td>
					</tr>
				</c:forEach>
				<tr>
					<td><form:label path="soggetto.misura.ulterioriPrescrizioni">
							<spring:message code="soggetto.misurapersonale.ulterioriprescrizioni" />
						</form:label></td>
					<td colspan="3"><form:textarea path="soggetto.misura.ulterioriPrescrizioni" id="note" readonly="true" rows="2" cssStyle="width:98%" disabled="${disabledObject}"/></td>
				</tr>

			</table>
			<table class="tb_form" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="atto.art9c1" /></td>
				</tr>
				<tr>
					<td><form:checkbox path="soggetto.misura.art9c1" value="S" disabled="${disabledObject}"/> 
					    <form:label path="soggetto.misura.art9c1">
					    </form:label>
				        <form:label path="soggetto.misura.art9c1">
							<spring:message code="atto.decreto.sospensionePassaporto" />
						</form:label> <form:errors path="soggetto.misura.art9c1" class="testoRosso" />
				    </td>

				</tr>
			</table>
		 <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
			<tr>
					<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
						<td width="16%"><form:label path="dataEmissione" cssClass="required">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td width="34%"><form:input path="dataEmissione" maxlength="10" size="12" id="id_dataEmissione"  /> <br> <form:errors path="dataEmissione" class="testoRosso" /></td>
						<td width="16%"><form:label path="dataDeposito" cssClass="required">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td width="34%"><form:input path="dataDeposito" maxlength="10" size="12" id="id_dataDeposito" /> <br> <form:errors path="dataDeposito" class="testoRosso" /></td>
					</c:if>
					<c:if test="${attoForm.dataRegistrazione!=null || attoForm.statoAtto.id==3}">
						<td width="16%"><form:label path="dataEmissione">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td width="34%"><fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataEmissione" /></td>
						<td width="16%"><form:label path="dataDeposito">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td width="34%"><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataDeposito" /></td>
					</c:if>
				</tr>
			</table>
		  		
		<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />	
		   <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<c:if test="${attoForm.statoAtto.id==3}">
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
					</c:if>
		  </table>
				
			<br />
		
			<table class="tb_bottoni" align="center">
				<tr>
				<%-- Specifiche Intervento-006-SpeseProces-NumAtto --%>
				<c:if test="${disabledObject!='true'}">
					<c:if test="${attoForm.dataRegistrazione==null}">
						<jsp:include page="/WEB-INF/jsp/procedimento/atto/includeGeneraNumAtto.jsp" />
					</c:if>
				</c:if>
					<td class="td_tasto">	
					 <c:if test="${disabledObject!='true'}">					
								<input type="submit" value="Salva" name="SalvaApplicazioneProvvisoria">						
						<%-- Specifiche Intervento-006-SpeseProces-NumAtto: aggiunto messagio di conferma se numeAnnoAtto is null --%>
						<c:if test="${attoForm.numeAnnoAtto==null || attoForm.numeAtto==null}">
							<input type="button" value="Conferma e Invia" onclick="return confermaInvio('ConfermaInviaApplicazioneProvvisoria');" title="Conferma e Invia Decreto Applicazione Provvisoria" class="nowait" />
					   	</c:if>
					   	<c:if test="${attoForm.numeAnnoAtto!=null && attoForm.numeAtto!=null}">
					   		<input type="button" value="Conferma e Invia" onclick="$('#ConfermaInviaApplicazioneProvvisoria').click();" title="Conferma e Invia Decreto Applicazione Provvisoria" class="nowait" />
					   	</c:if>
					   	<input type="submit" name="ConfermaInviaApplicazioneProvvisoria" id="ConfermaInviaApplicazioneProvvisoria" value="" style="display: none" />
				     </c:if>
						        <input type="button" id="id_buttonIndietro" value="Indietro" title="Indietro">
						
					</td>								
				</tr>
			</table>
		</form:form>

			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="id_buttonIndietro"></smp:back>

<c:if test="${attoForm.statoAtto.id!=3}">
<script type="text/javascript">

	$(document).ready(function() {
		prendiData("#id_dataEmissione",true);
		prendiData("#id_dataDeposito",true);

		$("#idForm").bind('submit', function() {
			return messaggio(this);
		});
	});				
			</script>
</c:if>			
				
<%-- Specifiche Intervento-006-SpeseProces-NumAtto: commentato if inutile ed aggiunto msg di conferma
se non è stato generato il numero atto --%>
<c:if test="${disabledObject!='true' && (attoForm.numeAnnoAtto==null || attoForm.numeAtto==null)}">
<script type="text/javascript">
function confermaInvio(idSubmitButton) {
	var check = checkDate();
	if (check && confirm("Si è scelto di voler procedere senza aver generato precedentemente il numero dell'atto e quindi una volta confermata l'operazione non sarà più possibile modificare il file allegato. Si è sicuri di voler continuare?")) {
		$('#' + idSubmitButton).click();
		return true;
	}
	return false;
}
</script>
</c:if>
				
	</tiles:putAttribute>
</tiles:insertDefinition>