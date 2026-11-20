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
		<spring:url var="azione" value="/procedimento/atto/decretoPerditaEfficaciaSequestro.htm" />
		<form:form method="post" id="id_formConfermaInvia" action="${azione}"  commandName="attoForm" enctype="multipart/form-data">
			<p class="titolo_page">
				<spring:message code="antimafia.perdita.efficacia" />
			</p>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />

				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				<br />
			
					<br />
					<table class="tb_form">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="bene.benidelatto" /></td>
						</tr>
					</table>
					<display:table class="tb_lista" uid="elemRisultatiRicerca" name="${risultatiRicerca}" pagesize="10" requestURI="${azione}">
						<display:column property="bene.idBene" title="Identificativo" sortable="false" />
						<display:column property="bene.descrizioneBene" title="Descrizione" sortable="false" />
						<display:column title="Misura - Titolari di diritto">
							<display:table class="tb_sotto_lista" uid="soggetto" name="${elemRisultatiRicerca.soggetti}" requestURI="${azione}">
								<display:column property="misura.tipoMisura.descrizione" title="" />
								<display:column property="descrizioneSoggetto" title="" />
								<display:column property="tipoInteresse.descrizione" title="" />
								<display:column property="descrizioneFrazioneOQuota" title="" />
							</display:table>
						</display:column>
					</display:table>

					<br />
	

					<br />
					<form:hidden path="codAtto" />
					<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
					<table class="tb_form">
						<tr>
							<td class="td_titolo" colspan="4"></td>
						</tr>

						<c:if test="${attoForm.statoAtto.id != 3}">
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
							<tr style="display:none">
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
								<td><fmt:formatDate value="<%=new java.util.Date() %>" type="date" pattern="dd/MM/yyyy" /></td>
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
						</c:if>
					</table>

					<br />
					<br />
					<table class="tb_bottoni" align="center">
						<tr>
						 <c:if test="${attoForm.statoAtto.id!='3'}">
						  <c:if test="${profilo.ruolo!='MAGISTRATO'}">
					<%-- Specifiche Intervento-006-SpeseProces-NumAtto --%>
					<jsp:include page="/WEB-INF/jsp/procedimento/atto/includeGeneraNumAtto.jsp" />
					<td class="td_tasto">
						<%-- Specifiche Intervento-006-SpeseProces-NumAtto: aggiunto messagio di conferma se numeAnnoAtto is null --%>
						<c:if test="${attoForm.numeAnnoAtto==null || attoForm.numeAtto==null}">
							<input type="button" value="Conferma e Invia" onclick="return confermaInvio('ConfermaInvia');" title="Conferma e Invia Decreto Perdita Efficacia Sequestro" class="nowait" />
					   	</c:if>
					   	<c:if test="${attoForm.numeAnnoAtto!=null && attoForm.numeAtto!=null}">
					   		<input type="button" value="Conferma e Invia" onclick="$('#ConfermaInvia').click();" title="Conferma e Invia Decreto Perdita Efficacia Sequestro" class="nowait" />
					   	</c:if>
					   	<input type="submit" name="ConfermaInvia" id="ConfermaInvia" value="" style="display: none" />
<!-- 						<input type="submit" value="Conferma e Invia" name="ConfermaInvia"> -->
					</td>
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
				<form:hidden path="idUfficio" id="idUfficio" />
			</form:form>				

	</tiles:putAttribute>
</tiles:insertDefinition>


<script type="text/javascript">

	$(document).ready(function() {
		prendiData("#dataEsecuzione",true);
		prendiData("#id_dataDeposito",true);
		prendiData("#id_dataEmissione",true);
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

</script>
