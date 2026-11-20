<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

<c:set var="DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO" value="<%=Constants.DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO%>"/>
<c:set var="STATOATTO_IN_LAVORAZIONE_S" value="<%=Constants.STATOATTO_IN_LAVORAZIONE_S%>"/>

<%
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
<%-- 		<spring:url var="azione" value="/procedimento/atto/dettaglioAtto.htm" /> --%>
<%-- 		<form:form method="post" id="id_formConfermaInvia" action="${azione}" commandName="attoForm"> --%>
		<spring:url var="azioneRigetto"  value="/procedimento/atto/decretoRigettoSequestro.htm" />
		<form:form method="post"  id="id_formConfermaInvia"  action="${azioneRigetto}" commandName="attoForm" >
			<p class="titolo_page">
				<spring:message code="atto.dettaglio" />
			</p>

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<c:if test="${attoForm.categoriaAtto.id == DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO}">
				<table class="tb_form">
					<tr>
						<td style="width: 40px">
							<form:label path="note">
								<spring:message code="note" />
							</form:label>
						</td>
						<td>
							<c:choose>
								<c:when test="${attoForm.statoAtto.id == STATOATTO_IN_LAVORAZIONE_S}">
									<form:textarea path="note" style="width: 100%" rows="2"/>
								</c:when>
								<c:otherwise>
									<textarea style="width: 100%" rows="2" readonly>${attoForm.note}</textarea>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</table>
			</c:if>

			<c:if test="${attoForm.statoAtto.id==3}">
				<br />
				<form:hidden path="codAtto" />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>

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
			</c:if>
<%-- 		</form:form> --%>
	
<%-- 		<spring:url var="azioneRigetto" value="/procedimento/atto/decretoRigettoSequestro.htm" /> --%>
<%-- 		<form:form method="post" action="${azioneRigetto}" commandName="beneSoggettoForm" > --%>
			<input type="hidden" id="id_selectAll_beniProcedimento.idBene" value="true" />
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.benidelatto" /></td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="elemRisultatiRicerca" name="${risultatiRicerca}" pagesize="10" requestURI="${azioneR}">

				<display:column property="bene.idBene" title="Identificativo" sortable="false" />
				<display:column property="bene.descrizioneBene" title="Descrizione" sortable="false" />
				<display:column title="Misura - Titolari di diritto">
					<display:table class="tb_sotto_lista" uid="soggetto" name="${elemRisultatiRicerca.soggetti}" requestURI="${azioneR}">
						<display:column property="misura.tipoMisura.descrizione" title="" />
						<display:column property="descrizioneSoggetto" title="" />
						<display:column property="tipoInteresse.descrizione" title="" />
						<display:column property="descrizioneFrazioneOQuota" title="" />

					</display:table>
				</display:column>
			</display:table>
			<br />
			<br />
			<br />
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<smp:notificheButtons formId="id_formConfermaInvia" codAtto="${attoForm.codAtto}"></smp:notificheButtons>
				   	
				  <c:if test="${profilo.ruolo!='MAGISTRATO'}">
					<c:if test="${attoForm.statoAtto.id!=3}">
						<td class="td_tasto"><input type="submit" value="Conferma" name="ConfermaInvia"></td>
					</c:if>
					</c:if>
					<td class="td_tasto">
						<c:if test="${fromMenuAtti=='elenco'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
						</c:if> <c:if test="${fromMenuAtti=='ordine'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
						</c:if> <c:if test="${fromMenuAtti=='nuovo'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
						</c:if>
					</td>
				</tr>
			</table>
			<br />
			<br />
		</form:form>

	</tiles:putAttribute>
</tiles:insertDefinition>


<script type="text/javascript">

	$(document).ready(function() {
		prendiData("#dataEsecuzione",true);
		prendiData("#id_dataDeposito",true);
		prendiData("#id_dataEmissione",true);
	});		

</script>
