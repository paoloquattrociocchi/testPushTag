<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	String fromMenuAtti = (String) request.getSession().getAttribute(
			"fromMenuAtti");
%>

<head>
<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>

</head>

<body onload="disabilitaCampiAttoAccertamento('${disabledViewProc}', ${attoForm.statoAtto.id})">
	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione" value="/procedimento/atto/accertamento/aggiorna.htm" />
			<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
				<form:hidden path="codAtto" />
				<form:hidden path="statoAtto.id" />
				<form:hidden path="statoAtto.descrizione" />
				<p class="titolo_page">
					<spring:message code="atto.accertamentoQuestura" />
				</p>

				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				<br />

				<spring:url var="paginazione" value="/procedimento/atto/proposta.htm?pag=true" />
				<br />
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettidelprocedimento" /></td>
					</tr>
				</table>
				<display:table class="tb_lista" uid="sogg" name="${soggettiInteressati}" pagesize="10" requestURI="${azione}">
					<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" />
				</display:table>

				<form:hidden path="identificativoSoggetto" id="idSoggetto" />
				<form:hidden path="versioneSoggetto" id="idVersione" />
				<form:hidden path="tipoSoggetto" id="tipoSoggetto" />

				<br />
				<br />
				<br />

				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="5"></td>
					</tr>
					<tr>
						<td width="15%" nowrap><form:label path="procedente.id">
								<spring:message code="procedimento.procedente" />
							</form:label></td>
						<td width="20%" align="left" nowrap><c:out value="${procedimento.procedente.descrizione }"></c:out></td>
						<td width="20%"></td>
						<td width="40%"></td>
						<td></td>
					</tr>
					<tr>
						<td nowrap><form:label path="numeroRiferimento">
								<spring:message code="atto.numeroriferimento" />
							</form:label></td>
						<td align="left"><c:if test="${attoForm.statoAtto.id!=3}">
								<form:input path="numeroRiferimento" maxlength="20" size="25" />
							</c:if> <c:if test="${attoForm.statoAtto.id==3}">
								<c:out value="${attoForm.numeroRiferimento}" />
							</c:if></td>
						<td align="center"><form:label path="dataInformativa" cssClass="required">
								<spring:message code="atto.datainformativa" />
							</form:label></td>
						<td align="left"><c:if test="${attoForm.statoAtto.id!=3}">
								<form:input id="id_dataInformativa" path="dataInformativa" maxlength="10" />
								<form:errors path="dataInformativa" class="testoRosso" />
							</c:if> <c:if test="${attoForm.statoAtto.id==3}">
								<fmt:formatDate value="${attoForm.dataInformativa}" type="date" pattern="dd/MM/yyyy" />
							</c:if></td>
						<td></td>
					</tr>
					<tr>
						<td><form:label path="note">
								<spring:message code="note" />
							</form:label></td>
						<td colspan="3"><form:textarea id="id_note" path="note" rows="2" cssStyle="width:98%" /></td>
						<td></td>
					</tr>		
				</table>
				<br />
				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />

				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="2"></td>
					</tr>
					<tr>
						<td width="15%" nowrap><form:label path="dataRicezione" cssClass="required">
								<spring:message code="atto.dataricezione" />
							</form:label></td>
						<td align="left"><c:if test="${attoForm.statoAtto.id!=3}">
								<form:input id="id_dataRicezione" path="dataRicezione" maxlength="10" />
								<br>
								<form:errors path="dataRicezione" class="testoRosso" />
							</c:if> <c:if test="${attoForm.statoAtto.id==3}">
								<fmt:formatDate value="${attoForm.dataRicezione}" type="date" pattern="dd/MM/yyyy" />
							</c:if></td>
					</tr>
				</table>

				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
					     <c:if test="${profilo.ruolo!='MAGISTRATO'}">
					     	<c:if test="${attoForm.statoAtto.id!=3}"> <!-- Segnalazione EMS-33 -->
								<td class="td_tasto"><input type="submit" name="ConfermaModifica" title="Conferma inserimento atto" value="Conferma"></td>
							</c:if>
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

			<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
				<script type="text/javascript">
					$(document).ready(function() {
						prendiData("#id_dataInformativa",true);
						prendiData("#id_dataRicezione",true);
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

			<script type="text/javascript">
				$(document)
						.ready(
								function() {
									$("#id_img_listaBeniNoTitDir")
											.rotate(
													{
														bind : {
															mouseover : function() {
																$(this)
																		.css(
																				"cursor",
																				"pointer");
															},
															click : function() {
																if ($(
																		"#id_div_listaBeniNoTitDir")
																		.is(
																				":visible")) {
																	$(this)
																			.rotate(
																					{
																						animateTo : 0
																					});
																	divScomparsa('id_div_listaBeniNoTitDir');
																} else {
																	$(this)
																			.rotate(
																					{
																						animateTo : 90
																					});
																	divScomparsa('id_div_listaBeniNoTitDir');
																}
															}
														}
													});
								});

				$(document)
						.ready(
								function() {
									$("#id_img_listaBeniNoMisPatr")
											.rotate(
													{
														bind : {
															mouseover : function() {
																$(this)
																		.css(
																				"cursor",
																				"pointer");
															},
															click : function() {
																if ($(
																		"#id_div_listaBeniNoMisPatr")
																		.is(
																				":visible")) {
																	$(this)
																			.rotate(
																					{
																						animateTo : 0
																					});
																	divScomparsa('id_div_listaBeniNoMisPatr');
																} else {
																	$(this)
																			.rotate(
																					{
																						animateTo : 90
																					});
																	divScomparsa('id_div_listaBeniNoMisPatr');
																}
															}
														}
													});
								});
			</script>
		</tiles:putAttribute>
	</tiles:insertDefinition>