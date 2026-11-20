<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/aggiornaIstanzaSoggettoDiffidato.htm" />
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			<form:hidden path="codAtto" />
			<form:hidden path="statoAtto.id" />
			<form:hidden path="statoAtto.descrizione" />
			<p class="titolo_page">
				<spring:message code="atto.istanzaSoggettoDiffidato" />
			</p>

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>

				<c:if test="${attoForm.statoAtto.id!=3}">
					<tr>
						<td><form:label path="dataEmissione" cssClass="required">
								<spring:message code="atto.istanzaSoggettoDiffidato.dataPresentazioneIstanza" />
							</form:label></td>
						<td><form:input path="dataEmissione" maxlength="10" size="12" id="dataPresentazioneIstanza" /> <br> <form:errors path="dataEmissione" class="testoRosso" /></td>
						
					<!--<td><c:out value="${attoForm.modalitaTrasmissione.id}" /> </td>-->
					 	<td><form:label path="modalitaTrasmissione" cssClass="required">
								<spring:message code="atto.istanzaSoggettoDiffidato.modalitaTrasmissione" />
							</form:label>
						</td>
					 	<td>
					 		<form:select path="modalitaTrasmissione.id" size="1" onchange="this.form.submit()">
								<form:option value="-1" label="------ Seleziona Modalita di trasmissione --------" />
								<form:options items="${tipoModalitaPervenimento}" itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
					 		<form:errors path="modalitaTrasmissione.id" class="testoRosso" />
					 	</td>
					
					</tr>
					
					<c:if test="${attoForm.modalitaTrasmissione.id==4}">
					<tr>
						<td colspan="2"></td>
						<td><form:label path="descAltroMod"  cssClass="required">
								<spring:message code="atto.istanzaSoggettoDiffidato.modalitaTrasmissioneTesto" />
							</form:label></td>
						<td><form:input path="descAltroMod" maxlength="60" size="80" /> <form:errors path="descAltroMod" class="testoRosso" /></td>
					</tr>
					</c:if> 
					
					<tr>
						<td><form:label path="dataDeposito">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><form:input path="dataDeposito" maxlength="10" size="12" id="dataDeposito" /> <br> <form:errors path="dataDeposito" class="testoRosso" /></td>
						<td><c:if test="${attoForm.dataRegistrazione==null}">
								<form:label path="destinatario.id">
									<spring:message code="atto.destinazione" />
								</form:label>
							</c:if></td>
						<td><c:if test="${attoForm.dataRegistrazione==null}">
								<form:select path="destinatario.id" size="1">
									<form:options items="${ufficiList}" itemValue="id" itemLabel="descrizione" />
								</form:select>
							</c:if></td>
					</tr>
				</c:if>
				<c:if test="${attoForm.statoAtto.id==3}">
					<tr>
						<td><form:label path="dataEmissione">
								<spring:message code="atto.istanzaSoggettoDiffidato.dataPresentazioneIstanza" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataEmissione" /></td>
						<td><form:label path="modalitaTrasmissione">
								<spring:message code="atto.istanzaSoggettoDiffidato.modalitaTrasmissione" />
							</form:label></td>
						<td><c:out value="${attoForm.modalitaTrasmissione.descrizione}" /> <form:hidden path="modalitaTrasmissione" /></td>
					</tr>
					<c:if test="${attoForm.modalitaTrasmissione.id==4}">
					<tr>
						<td colspan="2"></td>
						<td><form:label path="descAltroMod">
								<spring:message code="atto.istanzaSoggettoDiffidato.modalitaTrasmissioneTesto" />
							</form:label></td>
						<td><c:out value="${attoForm.descAltroMod}" /> <form:hidden path="descAltroMod" /></td>
					</tr>
					</c:if> 
					<tr>
						<td><form:label path="dataDeposito">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /></td>
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
					</tr>
				</c:if>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<c:if test="${attoForm.statoAtto.id!=3}">
					
						<td class="td_tasto"><input type="button" value="Salva" onclick="inserisciAtto('Conferma', '${attoForm.allegato.documentoFirmato}')" title="Conferma inserimento/modifica atto" class="nowait" /></td> 
						<input	id="Conferma" type="submit" name="Conferma" value="" style="display: none" />
						
						<td class="td_tasto"><input type="button" value="Conferma e invia" onclick="inserisciAtto('ConfermaInvia', '${attoForm.allegato.documentoFirmato}')" title="Conferma e invia" class="nowait" /></td> 
						<input	id="ConfermaInvia" type="submit" name="ConfermaInvia" value="" style="display: none" />
						
					</c:if>
					<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')"></td>
				</tr>
			</table>
			<br />
			<br />
		</form:form>
		<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
			<script type="text/javascript">
					$(document).ready(function() {
						prendiData("#dataRicezione",true);
						prendiData("#dataPresentazioneIstanza",true);
						prendiData("#dataDeposito",true);
					});
				</script>
		</c:if>
		<script>
				function selectID(idSoggetto, idVersione, tipoSoggetto) {
					document.getElementById("idSoggetto").value = idSoggetto;
					document.getElementById("idVersione").value = idVersione;
					document.getElementById("tipoSoggetto").value = tipoSoggetto;
				}
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>