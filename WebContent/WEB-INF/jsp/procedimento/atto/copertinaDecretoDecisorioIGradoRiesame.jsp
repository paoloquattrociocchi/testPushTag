<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript">
		var arrayIdBene = new Array();
		var i=-1;
</script>

<body>
	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione" value="/procedimento/atto/decisioneRiesame.htm" />
			<form:form method="post" id="id_formConfermaInvia" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
				<form:hidden path="codAtto" />
				<form:hidden path="statoAtto.id" />
				<form:hidden path="statoAtto.descrizione" />
				<p class="titolo_page">
					<spring:message code="atto.dettaglio" />
				</p>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br />
				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				<br />


				<display:table class="tb_lista" name="${soggetti}" uid="sogg" pagesize="10" requestURI="${azione}">
					<display:column style="text-align: center; width: 5%">
						<input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}" checked="checked" />
					</display:column>
					<display:column property="descrizioneSoggetto" title="Generalità soggetto richiedente" sortable="false" />
					<display:column property="dataIscrizione" title="Data Iscrizione" format="{0,date,dd/MM/yyyy}" sortable="false" />
				</display:table>
				<br />
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td align="left"><form:label path="" cssClass="required">
								<spring:message code="atto.istanzaSoggettoDiffidato.decisioneOpposizione" />
							</form:label> <c:if test="${attoForm.dataRegistrazione == null}">
								<form:select path="esitoOpposizione.id" size="1">
									<form:option value="-1" label="------Seleziona Decisione -------" />
									<form:options items="${listaEsiti}" itemValue="id" itemLabel="descrizione" />
								</form:select>
								<form:errors path="esitoOpposizione.id" class="testoRosso" />
							</c:if> <c:if test="${attoForm.dataRegistrazione != null}">
								<c:forEach var="esito" items="${listaEsiti}" varStatus="status">
									<c:if test="${esito.id == attoForm.esitoOpposizione.id}">
										<c:out value="${esito.descrizione}" />
									</c:if>
								</c:forEach>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<form:label path="dataDefinitivita" cssClass="required">
									<spring:message code="atto.datadefinitivita" />
								</form:label>
								<c:if test="${procedimento.statoProcedimento.id == 11 && sessionScope.procedimento.idProcedimento == attoForm.idProcEmiss}">
									<form:input id="id_dataDefinitivita" path="dataDefinitivita" maxlength="10" />
									<form:errors path="dataDefinitivita" class="testoRosso" />
								</c:if>
								<c:if test="${procedimento.statoProcedimento.id == 22}">
									<fmt:formatDate value="${attoForm.dataDefinitivita}" type="date" pattern="dd/MM/yyyy" />
								</c:if>
							</c:if></td>
					</tr>
				</table>
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
									<spring:message code="atto.dataemissione" />
								</form:label></td>
							<td><form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" /> <br> <form:errors path="dataEmissione" class="testoRosso" /></td>
							<%-- Specifiche Intervento-006-SpeseProces-NumAtto: data deposito NON modificabile se ho generato
								NUMERO ATTO poichè ANNO NUMERO ATTO è l'anno della data deposito --%>
							<c:if test="${attoForm.numeAnnoAtto==null || attoForm.numeAtto==null}">
								<td>
									<form:label path="dataDeposito" cssClass="required">
										<spring:message code="atto.datadeposito" />
									</form:label>
								</td>
								<td>
									<form:input path="dataDeposito" maxlength="10" id="id_dataDeposito" /> <br> <form:errors path="dataDeposito" class="testoRosso" />
								</td>
							</c:if>
							<c:if test="${attoForm.numeAnnoAtto!=null && attoForm.numeAtto!=null}">
								<td>
									<form:label path="dataDeposito">
									<spring:message code="atto.datadeposito" />
									</form:label>
								</td>
								<td><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /></td>
							</c:if>
						</tr>
						<tr style="display:none">
							<td><logic:notEmpty name="ufficiList">
									<form:label path="destinatario.id">
										<spring:message code="atto.destinazione" />
									</form:label>
								</logic:notEmpty></td>
							<td><logic:notEmpty name="ufficiList">
									<table>
										<c:forEach var="destinat" items="${ufficiList}">
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
					</c:if>
					<c:if test="${attoForm.statoAtto.id==3}">
						<tr>
							<td><form:label path="dataEmissione">
									<spring:message code="atto.dataemissione" />
								</form:label></td>
							<td><fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataEmissione" /></td>
							<td><form:label path="dataDeposito">
									<spring:message code="atto.datadeposito" />
								</form:label></td>
							<td><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataDeposito" /></td>
						</tr>
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
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
					 <c:if test="${profilo.ruolo!='MAGISTRATO'}">
						<c:if test="${attoForm.dataRegistrazione == null && sessionScope.procedimento.idProcedimento == attoForm.idProcEmiss}">
							<%-- Specifiche Intervento-006-SpeseProces-NumAtto --%>
							<jsp:include page="/WEB-INF/jsp/procedimento/atto/includeGeneraNumAtto.jsp" />
							<td class="td_tasto"><input type="submit" value="Salva" name="DecisioneRiesame"></td>
							<%-- Specifiche Intervento-006-SpeseProces-NumAtto: aggiunto messagio di conferma se numeAnnoAtto is null --%>
						    <td class="td_tasto">
							    <c:if test="${attoForm.numeAnnoAtto==null || attoForm.numeAtto==null}">
									<input type="button" value="Conferma e Invia" onclick="return confermaInvio('ConfermaInvia')" title="Conferma inserimento atto" class="nowait" />
							   	</c:if>
							   	<c:if test="${attoForm.numeAnnoAtto!=null && attoForm.numeAtto!=null}">
							   		<input type="button" value="Conferma e Invia" onclick="return inserisci('ConfermaInvia');" title="Conferma inserimento atto" class="nowait" />
							   	</c:if>
						   	</td>
							<input type="submit" id="ConfermaInvia" name="ConfermaInvia" value="" style="display: none">
<!-- 							<td class="td_tasto"><input type="submit" value="Conferma e Invia" name="ConfermaInvia"></td> -->
						</c:if>
						<c:if test="${attoForm.dataRegistrazione != null && procedimento.statoProcedimento.id == 11 && sessionScope.procedimento.idProcedimento == attoForm.idProcEmiss}">
							<td class="td_tasto"><input type="submit" value="Conferma e Invia" name="DefinitivitaDecisorioIGradoRiesame"></td>
						</c:if>
						</c:if>

						<td class="td_tasto"><c:if test="${fromMenuAtti=='elenco'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
							</c:if> <c:if test="${fromMenuAtti=='ordine'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
							</c:if> <c:if test="${fromMenuAtti=='nuovo'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
							</c:if>
						</td>
					</tr>
				</table>

			</form:form>

		</tiles:putAttribute>
	</tiles:insertDefinition>



	<script type="text/javascript">	
	$(document).ready(function() {
		prendiData("#id_dataEmissione",true);
		prendiData("#id_dataDeposito",true);
		prendiData("#id_dataDefinitivita",true);	
	});
		
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
		
		function inserisci(idSubmitButton) {
			var check = checkDate();
			if (check) {
				$('#' + idSubmitButton).click();
				return true;
			}
			return false;
		}
</script>
</body>
</html>