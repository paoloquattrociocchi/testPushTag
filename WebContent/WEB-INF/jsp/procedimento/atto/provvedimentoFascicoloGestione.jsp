<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>


<c:set var="PROVVEDIMENTO_SU_ISTANZA_DELLE_PARTI_E_AMMINISTRATORE" value="<%=Constants.PROVVEDIMENTO_SU_ISTANZA_DELLE_PARTI_E_AMMINISTRATORE%>"/>
<c:set var="PROVVEDIMENTO_CONTO_GESTIONE" value="<%=Constants.PROVVEDIMENTO_CONTO_GESTIONE%>"/>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/provvedimentoFascicoloGestione.htm" />
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			
			<form:hidden path="codAtto" />
			<form:hidden path="statoAtto.id" />
			<form:hidden path="statoAtto.descrizione" />
			<form:hidden path="tipoAtto.id" />
			<form:hidden path="categoriaAtto.id" />
					
			<p class="titolo_page">
				<c:if test="${attoForm.categoriaAtto.id == PROVVEDIMENTO_SU_ISTANZA_DELLE_PARTI_E_AMMINISTRATORE}" >
					<spring:message code="atto.provvedimentoIstanzaPartiAmministratore" />
				</c:if>
				<c:if test="${attoForm.categoriaAtto.id == PROVVEDIMENTO_CONTO_GESTIONE}" >
					<spring:message code="atto.provvedimentoContoGestione" />
				</c:if>
			</p>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<logic:notPresent name="listaIstanze">
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				<br />
				<form:hidden path="codAtto" />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>

					<c:if test="${attoForm.statoAtto.id != 3}">
						<tr>
							<td>
								<form:label path="dataEmissione" cssClass="required">
									<spring:message code="atto.dataemissione" />
								</form:label>
							</td>
							<td>
								<form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" /> <form:errors path="dataEmissione" class="testoRosso" /></td>
							<td>
								<form:label path="dataDeposito" cssClass="required">
									<spring:message code="atto.datadeposito" />
								</form:label>
							</td>
							<td>
								<form:input path="dataDeposito" maxlength="10" id="id_dataDeposito" /> <form:errors path="dataDeposito" class="testoRosso" />
							</td>
						</tr>
						<tr>
							<td>
								<form:label path="dataUdienza">
									<spring:message code="udienza.data" />
								</form:label>
							</td>
							<td>
								<form:input path="dataUdienza" maxlength="10" id="id_dataUdienza" /> <form:errors path="dataUdienza" class="testoRosso" /></td>
						
							<c:if test="${attoForm.categoriaAtto.id == PROVVEDIMENTO_SU_ISTANZA_DELLE_PARTI_E_AMMINISTRATORE}" >
								<td>
									<form:label path="descEsitoProvv" cssClass="required">
										<spring:message code="atto.descEsitoProvv" />
									</form:label>
								</td>
								<td>
									<form:textarea path="descEsitoProvv" rows="2" cols="100" cssStyle="width:98%" /><br> <form:errors path="descEsitoProvv" class="testoRosso" />
								</td>
							</c:if>
							<c:if test="${attoForm.categoriaAtto.id == PROVVEDIMENTO_CONTO_GESTIONE}" >
								<td></td>
								<td></td>
							</c:if>
						</tr>						
					</c:if>
					<c:if test="${attoForm.statoAtto.id == 3}">
						<tr>
							<td><form:label path="dataEmissione">
									<spring:message code="atto.dataemissione" />
								</form:label></td>
							<td><fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataEmissione" /></td>
							<td><form:label path="dataDeposito">
									<spring:message code="atto.datadeposito" />
								</form:label></td>
							<td><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /></td>
						</tr>
						<tr>
							<td><form:label path="dataUdienza">
									<spring:message code="udienza.data" />
								</form:label></td>
							<td><fmt:formatDate value="${attoForm.dataUdienza}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataUdienza" /></td>	

							<c:if test="${attoForm.categoriaAtto.id == PROVVEDIMENTO_SU_ISTANZA_DELLE_PARTI_E_AMMINISTRATORE}" >					
								<td>
									<form:label path="descEsitoProvv">
										<spring:message code="atto.descEsitoProvv" />
									</form:label>
								</td>
								<td colspan="2">
									<c:out value="${attoForm.descEsitoProvv}" /> <form:hidden path="descEsitoProvv" />
								</td>	
							</c:if>
							<c:if test="${attoForm.categoriaAtto.id == PROVVEDIMENTO_CONTO_GESTIONE}" >
								<td></td>
								<td></td>
							</c:if>							
						</tr>					
					</c:if>
					
					<c:if test="${attoForm.categoriaAtto.id == PROVVEDIMENTO_CONTO_GESTIONE}" >
						<c:if test="${attoForm.statoAtto.id != 3}">
							<tr>
								<td>
									<form:label path="esitoProvv" cssClass="required">
										<spring:message code="atto.approvazione" />
									</form:label>
								</td>
								<td>
									<form:select path="esitoProvv" size="1" id="esitoProvv">
										<form:option value="-1" label="------Seleziona Approvazione-------" />
										<form:option value="S" label="SI" />
										<form:option value="N" label="NO" />
									</form:select>
									<form:errors path="esitoProvv" class="testoRosso" />
								</td>
								<td>
									<form:label path="descEsitoProvv">
										<spring:message code="atto.descEsitoProvv" />
									</form:label>
								</td>
								<td>
									<form:textarea path="descEsitoProvv" rows="2" cssStyle="width:98%" /><br> <form:errors path="descEsitoProvv" class="testoRosso" />
								</td>
							</tr>
							<tr>
								<td>
									<form:label path="flgImp">
										<spring:message code="atto.impugnazione" />
									</form:label>
								</td>
								<td>
									<form:select path="flgImp" size="1" onchange="changeFlgImp(this.value)" id="flgImp">
										<form:option value="-1" label="------Seleziona Impugnazione-------" />
										<form:option value="S" label="SI" />
										<form:option value="N" label="NO" />
									</form:select>
									<form:errors path="flgImp" class="testoRosso" />
								</td>
								<td>
									<form:label path="descEsitoImp">
										<spring:message code="atto.descEsitoImp" />
									</form:label>
								</td>
								<td>
									<form:textarea path="descEsitoImp" rows="2" cssStyle="width:98%" /><br> <form:errors path="descEsitoImp" class="testoRosso" />
								</td>
							</tr>

							<tr>
								<td>
									<form:label path="flgCas">
										<spring:message code="atto.ricorsoCassazione" />
									</form:label>
								</td>
								<td>
									<form:select path="flgCas" size="1" onchange="changeFlgCas(this.value)" id="flgCas">
										<form:option value="-1" label="------Seleziona Ricorso Cassazione-------" />
										<form:option value="S" label="SI" />
										<form:option value="N" label="NO" />
									</form:select>
									<form:errors path="flgCas" class="testoRosso" />
								</td>
								<td>
									<form:label path="descEsitoCas">
										<spring:message code="atto.descEsitoCas" />
									</form:label>
								</td>
								<td>
									<form:textarea path="descEsitoCas" rows="2" cssStyle="width:98%" /><br> <form:errors path="descEsitoCas" class="testoRosso" />
								</td>
							</tr>
							<tr>
								<td>
									<form:label path="descNote" >
										<spring:message code="note" />
									</form:label>
								</td>
								<td colspan="3">
									<form:textarea path="descNote" rows="2" cssStyle="width:98%" /><br> <form:errors path="descNote" class="testoRosso" />
								</td>							
							</tr>
						</c:if>
								
						<c:if test="${attoForm.statoAtto.id == 3}">
							<tr>
								<td>
									<form:label path="esitoProvv" >
										<spring:message code="atto.approvazione" />
									</form:label>
								</td>
								<td>
									<form:select path="esitoProvv" size="1" id="esitoProvv" disabled="true">
										<form:option value="-1" label="" />
										<form:option value="S" label="SI" />
										<form:option value="N" label="NO" />
									</form:select>
									<form:errors path="esitoProvv" class="testoRosso" />
								</td>
								<td>
									<form:label path="descEsitoProvv">
										<spring:message code="atto.descEsitoProvv" />
									</form:label>
								</td>
								<td>
									<c:out value="${attoForm.descEsitoProvv}" /> <form:hidden path="descEsitoProvv" />
								</td>
							</tr>
							<tr>
								<td>
									<form:label path="flgImp" >
										<spring:message code="atto.impugnazione" />
									</form:label>
								</td>
								<td>
									<form:select path="flgImp" size="1" onchange="changeFlgImp(this.value)" id="flgImp" disabled="true">
										<form:option value="-1" label="" />
										<form:option value="S" label="SI" />
										<form:option value="N" label="NO" />
									</form:select>
									<form:errors path="flgImp" class="testoRosso" />
								</td>
								<td>
									<form:label path="descEsitoImp">
										<spring:message code="atto.descEsitoImp" />
									</form:label>
								</td>
								<td>
									<c:out value="${attoForm.descEsitoImp}" /> <form:hidden path="descEsitoProvv" />								
								</td>
							</tr>

							<tr>
								<td>
									<form:label path="flgCas" >
										<spring:message code="atto.ricorsoCassazione" />
									</form:label>
								</td>
								<td>
									<form:select path="flgCas" size="1" onchange="changeFlgCas(this.value)" id="flgCas" disabled="true">
										<form:option value="-1" label="" />
										<form:option value="S" label="SI" />
										<form:option value="N" label="NO" />
									</form:select>
									<form:errors path="flgCas" class="testoRosso" />
								</td>
								<td>
									<form:label path="descEsitoCas">
										<spring:message code="atto.descEsitoCas" />
									</form:label>
								</td>
								<td>
									<c:out value="${attoForm.descEsitoCas}" /> <form:hidden path="descEsitoCas" />
								</td>
							</tr>
							<tr>
								<td>
									<form:label path="descNote" >
										<spring:message code="note" />
									</form:label>
								</td>
								<td colspan="3">
									<c:out value="${attoForm.descNote}" /> <form:hidden path="descNote" />
								</td>							
							</tr>
						</c:if>				
					</c:if>
					
				</table>
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<c:if test="${attoForm.dataRegistrazione==null}">
							<c:if test="${attoForm.statoAtto.id!=3}">
	
								<td class="td_tasto"><input type="button" value="Salva" onclick="inserisciAtto('Salva', '${attoForm.allegato.documentoFirmato}')" title="Salva inserimento atto" class="nowait" /></td> 
								<input	id="Salva" type="submit" name="Salva" value="" style="display: none" />
														
								<td class="td_tasto"><input type="button" value="Conferma" onclick="inserisciAtto('Conferma', '${attoForm.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" /></td> 
								<input	id="Conferma" type="submit" name="Conferma" value="" style="display: none" />
							
							</c:if>
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
			</logic:notPresent>
		</form:form>
		
		<logic:present name="listaIstanze">
			<spring:url var="azioneSelDecreto" value="/procedimento/atto/provvedimentoFascicoloGestione.htm" />
			<form:form method="post" action="${azioneSelDecreto}" commandName="attoForm">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td>
							<form:label path="codAtto">
								<c:if test="${attoForm.categoriaAtto.id == PROVVEDIMENTO_SU_ISTANZA_DELLE_PARTI_E_AMMINISTRATORE}" >
									<spring:message code="atto.istanzaDellePartieAmministratore" />
								</c:if>
								<c:if test="${attoForm.categoriaAtto.id == PROVVEDIMENTO_CONTO_GESTIONE}" >
									<spring:message code="atto.istanzaContoGestione" />
								</c:if>								
							</form:label>
						</td>
						<td>
						<form:select path="codAtto" size="1">
								<form:option value="-1" label="------Seleziona Istanza-------" />
								<form:options items="${listaIstanze}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:hidden path="tipoAtto.id" /> <form:hidden path="categoriaAtto.id" />
						</td>
					</tr>
				</table>
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
					<c:if test="${profilo.ruolo!='MAGISTRATO'}">
						<td class="td_tasto"><input type="submit" value="Conferma" name="SelezionaDecreto"></td>
					</c:if>
						<td class="td_tasto"><c:if test="${fromMenuAtti=='elenco'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
							</c:if> <c:if test="${fromMenuAtti=='ordine'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
							</c:if> <c:if test="${fromMenuAtti=='nuovo'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/nuovo.htm')">
							</c:if></td>
					</tr>
				</table>
			</form:form>
		</logic:present>
	</tiles:putAttribute>
</tiles:insertDefinition>


<script type="text/javascript">

	//changeFlgImp(document.getElementById("flgImp").value);
	//changeFlgCas(document.getElementById("flgCas").value);

	function changeFlgImp(value) {
		if(value=="S") {
			document.getElementById("descEsitoImp").disabled = false;
		} else {
			document.getElementById("descEsitoImp").value = "";
			document.getElementById("descEsitoImp").disabled = true;
		}
		
	}		

	function changeFlgCas(value) {
		if(value=="S") {
			document.getElementById("descEsitoCas").disabled = false;
		} else {
			document.getElementById("descEsitoCas").value = "";
			document.getElementById("descEsitoCas").disabled = true;
		}
		
	}						
</script>

<c:if test="${attoForm.statoAtto.id != 3}">
	<script type="text/javascript">
	
		$(document).ready(function() {
			prendiData("#id_dataDeposito",true);
			prendiData("#id_dataEmissione",true);
			prendiData("#id_dataUdienza",true);			
		});		
	
	</script>
</c:if>	
