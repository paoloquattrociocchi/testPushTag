<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

<%
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">

		<p class="titolo_page">
			<spring:message code="atto.decreto.incompetenza.procura" />
		</p>
		<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
		<br />
		<%-- Specifiche Intervento-006-SpeseProces-NumAtto --%>
		<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
		<br />
		<table class="tb_form">
			<tr>
				<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettidelprocedimento" /></td>
			</tr>
		</table>
			
		<spring:url var="azioneSoggetto" value="/procedimento/atto/visualizzaDirettiInteressati.htm" />
		<form:form id="id_formSoggetti" method="post" action="${azioneSoggetto}" commandName="soggettoForm">
			<logic:present name="soggetti">
				<display:table class="tb_lista" name="${soggetti}" uid="sogg" pagesize="10" requestURI="${azione}">
					<display:column style="text-align: center; width: 5%">
						<input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}" onclick="selectID('${sogg.idSoggetto}', '${sogg.idVersione}', '${sogg.tipoSoggetto}', '${sogg.misura.descrizioneMisura}');" />
					</display:column>
					<display:column property="descrizioneSoggetto" title="Generalità" sortable="false" />
					<display:column property="dataIscrizione" title="Data Iscrizione" format="{0,date,dd/MM/yyyy}" sortable="false" />
					<c:if test="${sogg.misura.descrizioneMisura==null && customMessageList!=null && fn:length(customMessageList)>0}">
						<display:column class="testoRosso" value="MISURE ASSENTI" title="Misure" sortable="false" />
					</c:if>
					<c:if test="${sogg.misura.descrizioneMisura!=null}">
						<display:column property="misura.descrizioneMisura" title="Misure" sortable="false" />
					</c:if>
				</display:table>
				<form:hidden path="idSoggetto" id="idSoggetto" />
				<form:hidden path="idVersione" id="idVersione" />
				<form:hidden path="tipoSoggetto" id="tipoSoggetto" />
				<form:hidden path="tipoInteresse.id" />
				<form:hidden path="misura.descrizioneMisura" id="tipoMisura" />
				<br />
			</logic:present>
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<logic:present name="soggetti">
						<c:if test="${procedimento.tipoProcedimento.id=='1' || procedimento.tipoProcedimento.id=='2' || procedimento.tipoProcedimento.id=='4' || procedimento.tipoProcedimento.id=='8'}">
							<!-- 							il TIPO del procedimento è PERSONALE oppure PERSONALE E PATRIMONIALE -->
							<td class="td_tasto"><input type="submit" value="Misura Personale" name="MisuraPersonale"></td>
						</c:if>
					</logic:present>
					<logic:notEqual name="<%=Constants.ID_TIPO_PROCEDIMENTO%>" value="<%=Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE%>">
						<c:if test="${procedimento.tipoProcedimento.id=='1' || procedimento.tipoProcedimento.id=='2'}">
							<!-- 							Il TIPO del procedimento è PATRIMONIALE oppure PERSONALE E PATRIMONIALE -->
							<td class="td_tasto"><input type="submit" value="Decisione sui beni" name="DecisioneBeni"></td>
						</c:if>
					</logic:notEqual>
				</tr>
			</table>
		</form:form>
		
		<spring:url var="azione" value="/procedimento/atto/nuovoDecretoIncompetenzaProcura.htm" />
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			<form:hidden path="codAtto" />
			<form:hidden path="categoriaAtto.id" />
			<form:hidden path="tipoAtto.id" />

			<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />

			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>
				<tr>
					<c:if test="${attoForm.dataRegistrazione!=null}">
						<td><form:label path="dataEmissione">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" />
							<form:hidden path="dataEmissione" /></td>
						<td><form:label path="dataDeposito">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" />
							<form:hidden path="dataDeposito" /></td>
					</c:if>
					<c:if test="${attoForm.dataRegistrazione==null}">
						<td><form:label path="dataEmissione" cssClass="required">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td><form:input id="id_dataEmissione" path="dataEmissione" maxlength="10" /> 
						<form:errors path="dataEmissione" class="testoRosso" /></td>
						<td><form:label path="dataDeposito" cssClass="required">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><form:input id="id_dataDeposito" path="dataDeposito" maxlength="10" /> 
						<form:errors path="dataDeposito" class="testoRosso" /></td>
					</c:if>
				</tr>
				<tr>
					<td><form:label path="tipologiaPrevenzione">
							<spring:message code="procedimento.tipologiaPrevenzione" />
						</form:label></td>
					<td><input type="checkbox" disabled="disabled" checked="checked" /> <form:label path="antimafia">
							<spring:message code="procedimento.antimafia" />
						</form:label></td>

						<logic:notEmpty name="ufficiListFromFlussiOut">
							<c:if test="${attoForm.dataRegistrazione!=null}">
								<td>
									<form:label path="destinatario.id">
										<spring:message code="atto.destinatari" />
									</form:label>
								</td>
								<td>
									<table>
										<c:forEach var="destinat" items="${ufficiListFromFlussiOut}">
											<tr>
												<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
											</tr>
										</c:forEach>
									</table>
								</td>
							</c:if>
						</logic:notEmpty>
						<c:if test="${attoForm.dataRegistrazione==null}">	
							<td></td>				
							<td></td>
							<td style="display: none">				
								<table>
									<c:forEach var="destinat" items="${ufficiList}">
										<tr style="display:none">
											<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
										</tr>
									</c:forEach>
								</table>
							</td>
						</c:if>

					</tr>
					<c:if test="${attoForm.dataRegistrazione!=null}">
						<tr>
							<td><form:label path="dataRegistrazione">
									<spring:message code="atto.dataregistrazione" />
								</form:label></td>
							<td><fmt:formatDate value="${attoForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" />
								<form:hidden path="dataRegistrazione" /></td>
							<td></td>
							<td></td>
						</tr>								
					</c:if>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<smp:notificheButtons formId="id_formConfermaInvia" codAtto="${attoForm.codAtto}"></smp:notificheButtons>
					<c:if test="${attoForm.dataRegistrazione==null}">
						<%-- Specifiche Intervento-006-SpeseProces-NumAtto --%>
						<jsp:include page="/WEB-INF/jsp/procedimento/atto/includeGeneraNumAtto.jsp" />
					
						<td class="td_tasto"><input type="button" value="Salva" onclick="inserisciAtto('Conferma', '${attoForm.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" /></td> 
						<input	id="Conferma" type="submit" name="Conferma" value="" style="display: none" />

						<%-- Specifiche Intervento-006-SpeseProces-NumAtto: aggiunto messagio di conferma se numeAnnoAtto is null --%>
					    <td class="td_tasto">
						    <c:if test="${attoForm.numeAnnoAtto==null || attoForm.numeAtto==null}">
								<input type="button" value="Conferma e Invia" onclick="return confermaInvio('ConfermaInvia','${attoForm.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" />
						   	</c:if>
						   	<c:if test="${attoForm.numeAnnoAtto!=null && attoForm.numeAtto!=null}">
						   		<input type="button" value="Conferma e invia" onclick="inserisciAtto('ConfermaInvia', '${attoForm.allegato.documentoFirmato}')" title="Conferma e invia il decreto corrente" class="nowait" />
						   	</c:if>
					   	</td>
						<input	id="ConfermaInvia" type="submit" name="ConfermaInvia" value="" style="display: none" />
						
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
			<br />
			<br />
		</form:form>

		<script type="text/javascript">
				$(document).ready(function() {					
					prendiData("#id_dataEmissione",true);
					prendiData("#id_dataDeposito",true);
				});
			
				function apriMisura() {					
					$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/atto/visualizzaDirettiInteressati.htm");
					$("#id_formConfermaInvia").submit();
				}
			</script>
		<script>
			function selectID(idSoggetto, idVersione, tipoSoggetto, tipoMisura) {
				document.getElementById("idSoggetto").value = idSoggetto;
				document.getElementById("idVersione").value = idVersione;
				document.getElementById("tipoSoggetto").value = tipoSoggetto;
				document.getElementById("tipoMisura").value = tipoMisura;
			}
<%-- Specifiche Intervento-006-SpeseProces-NumAtto: commentato if inutile ed aggiunto msg di conferma
se non è stato generato il numero atto --%>
function confermaInvio(submit, documentoFirmato) {
	var check = checkDate();
	if (check && confirm("Si è scelto di voler procedere senza aver generato precedentemente il numero dell'atto e quindi una volta confermata l'operazione non sarà più possibile modificare il file allegato. Si è sicuri di voler continuare?")) {
		$(":disabled").removeAttr('disabled');
		inserisciAtto(submit, documentoFirmato);
		return true;
	}
	return false;
}
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>