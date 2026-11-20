<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/procedimento/nuovo/nuovoSoggettoGiuridico.htm" />
			<form:form method="post" action="${azione}" commandName="soggettoForm">
				<p class="titolo_page"><spring:message code="soggettogiuridico.dettaglio"/></p>
				<logic:present name="procedimento">
					<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
					 <br/>
					 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				</logic:present>
				<br/>				
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">		
					<tr>
						<td><form:label path="ragioneSociale">
								<spring:message code="soggettogiuridico.ragioneSociale" /></form:label></td>
						<td><c:out value="${soggettoForm.ragioneSociale}" /></td>

						<td><form:label path="partitaIva">
								<spring:message code="soggettogiuridico.partitaIva" /></form:label></td>
						<td><c:out value="${soggettoForm.partitaIvaCompleta}" /></td>
					</tr>
					<tr>
						<td width="17%"><form:label path="tipoSocieta">
								<spring:message code="soggettogiuridico.tipoSocieta" />
							</form:label></td>
						<td><c:out value="${soggettoForm.tipoSocieta}" /></td>
						<td><form:label path="numeroCCIAA">
								<spring:message code="soggettogiuridico.numeroCCIAA" /></form:label></td>
						<td><c:out value="${soggettoForm.numeroCCIAA}" /></td>
					</tr>			
					<tr>
						<td class="td_titolo" colspan="4"><spring:message
								code="nuovosoggettogiuridico.titolo1" /></td>
					</tr>
					<tr>
						<td><form:label path="statoSedeLegale.descrizione">
								<spring:message code="stato" />
							</form:label></td>
						<td><c:out value="${soggettoForm.statoSedeLegale.descrizione}" /></td>

						<td><form:label path="comuneSedeLegale.descrizione">
							<spring:message code="comune" /></form:label></td>
						<td>							
							<c:out value="${soggettoForm.descrizioneComuneSedeLegaleItalianoEstero}" />							
						</td>
					</tr>
					<tr>
						<td><form:label path="indirizzoSedeLegale.toponimo">
								<spring:message code="indirizzo" />
							</form:label></td>
						<td><c:out value="${soggettoForm.indirizzoSedeLegale.toponimo.descrizione}" />
								<c:out value="${soggettoForm.indirizzoSedeLegale.denominazione}" />						
						</td>
						<td><form:label path="indirizzoSedeLegale.civico">
								<spring:message code="civico" />
							</form:label></td>
						<td colspan="3"><c:out value="${soggettoForm.indirizzoSedeLegale.civico}" /></td>
					</tr>
				
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>
					<tr>
						<td><form:label path="rappresentanteSociale">
								<spring:message code="soggettogiuridico.rappresentanteSociale" />
							</form:label></td>
						<td><c:out value="${soggettoForm.rappresentanteSociale}" /></td>

						<td><form:label path="amministratore">
								<spring:message code="soggettogiuridico.amministratore" />
							</form:label></td>
						<td><c:out value="${soggettoForm.amministratore}" /></td>
					</tr>
					<tr>
						<td><form:label path="capitaleSociale">
								<spring:message code="soggettogiuridico.capitaleSociale" />
							</form:label></td>
						<td>
							<c:if test="${soggettoForm.capSocDescr=='int'}">
								<fmt:formatNumber type="number" maxFractionDigits="2" value="${soggettoForm.capitaleSociale}" />
							</c:if>
							<c:if test="${soggettoForm.capSocDescr=='noInt'}">
								<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${soggettoForm.capitaleSociale}" />
							</c:if>
						</td>
						<td><form:label path="settoreAttivita">
								<spring:message code="soggettogiuridico.settoreAttivita" />
							</form:label></td>
						<td><c:out value="${soggettoForm.settoreAttivita.descrizione}" /></td>
					</tr>
					<tr>
						<td><form:label path="annotazioni">
								<spring:message code="annotazioni" />
							</form:label></td>
						<td colspan="3"><c:out value="${soggettoForm.annotazioni}" /></td>
					</tr>
				</table>

				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="button" id="backButton" value="Indietro" name="Indietro"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>
		</tiles:putAttribute>
	</tiles:insertDefinition>
