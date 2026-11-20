<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/soggettiInteressati/dettaglio.htm" />
 
		<logic:equal name="contesto" value="atto">
			<c:set var="urlIndietro" value="/procedimento/soggetto/visualizzaDirettiInteressatiAtto.htm" />
		</logic:equal>
		<logic:notEqual name="contesto" value="atto">
			<c:set var="urlIndietro" value="/procedimento/soggetto/visualizzaDirettiInteressati.htm" />
			<logic:equal name="soggettoForm" property="descrRuolo" value="TERZO">
				<c:set var="urlIndietro" value="/procedimento/soggetto/visualizzaDirettiInteressatiTerzi.htm" />
			</logic:equal>
			<c:if test="${soggettoForm.idRuolo == 3}">
				<c:set var="urlIndietro" value="/procedimento/soggetto/visualizzaIntervenienti.htm" />
			</c:if>
		</logic:notEqual>

		<form:form method="post" action="${azione}" commandName="soggettoForm">
			<p class="titolo_page">
				<spring:message code="soggettogiuridico.dettaglio" />
			</p>
			<logic:present name="procedimento">
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				<br />
			</logic:present>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">

				<c:if test="${provenienzaVersione==null}">
					<logic:notPresent name="titoloDiInteresse">
						<tr>
							<td><form:label path="ipotesiMisura">
									<spring:message code="procedimento.ipotesiMisura" />
								</form:label></td>
							<td><c:out value="${soggettoForm.ipotesiMisura.descrizione }" /></td>
							<td><form:label path="tipologiaPrevenzione">
									<spring:message code="procedimento.tipologiaPrevenzione" />
								</form:label></td>
							<td><c:out value="${soggettoForm.tipologiaPrevenzione.descrizione }" /></td>
						</tr>
						<tr>
							<td><form:label path="normativa">
									<spring:message code="procedimento.normativa" />
								</form:label></td>
							<td colspan="3"><c:out value="${soggettoForm.normativa.descrizione }" /></td>
						</tr>
					</logic:notPresent>
				</c:if>					
				
				<tr>
					<td><form:label path="ragioneSociale">
							<spring:message code="soggettogiuridico.ragioneSociale" />
						</form:label></td>
					<td><c:out value="${soggettoForm.ragioneSociale}" /></td>

					<td><form:label path="partitaIva">
							<spring:message code="soggettogiuridico.partitaIva" />
						</form:label></td>
					<td><c:out value="${soggettoForm.partitaIvaCompleta}" /></td>
				</tr>
				<tr>
					<td><form:label path="tipoSocieta">
							<spring:message code="soggettogiuridico.tipoSocieta" />
						</form:label></td>
					<td><c:out value="${soggettoForm.tipoSocieta}" /></td>
					<td><form:label path="numeroCCIAA">
							<spring:message code="soggettogiuridico.numeroCCIAA" />
						</form:label></td>
					<td><c:out value="${soggettoForm.numeroCCIAA}" /></td>
				</tr>
				
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettogiuridico.titolo1" /></td>
				</tr>
				<tr>
					<td><form:label path="statoSedeLegale.descrizione">
							<spring:message code="stato" />
						</form:label></td>
					<td><c:out value="${soggettoForm.statoSedeLegale.descrizione}" /></td>

					<td><form:label path="comuneSedeLegale.descrizione">
							<spring:message code="comune" />
						</form:label></td>
					<td><c:out value="${soggettoForm.descrizioneComuneSedeLegaleItalianoEstero}" /></td>
				</tr>
				<tr>
					<td><form:label path="indirizzoSedeLegale.toponimo">
							<spring:message code="indirizzo" />
						</form:label></td>
					<td><c:out value="${soggettoForm.indirizzoSedeLegale.toponimo.descrizione}" /> <c:out value="${soggettoForm.indirizzoSedeLegale.denominazione}" /></td>
					<td><form:label path="indirizzoSedeLegale.civico">
							<spring:message code="civico" />
						</form:label></td>
					<td colspan="3"><c:out value="${soggettoForm.indirizzoSedeLegale.civico}" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo6" /></td>
				</tr>
				<tr>
					<td><form:label path="statoDomicilioEletto.descrizione">
							<spring:message code="stato" />
						</form:label></td>
					<td><c:out value="${soggettoForm.statoDomicilioEletto.descrizione}" /></td>
					<td><form:label path="descrizioneComuneDomicilioElettoItalianoEstero">
							<spring:message code="comune" />
						</form:label></td>
					<td><c:out value="${soggettoForm.descrizioneComuneDomicilioElettoItalianoEstero}" /></td>
				</tr>
				<tr>
					<td><form:label path="indirizzoDomicilioEletto">
							<spring:message code="nuovosoggettofisico.indirizzo" />
						</form:label></td>
					<td><c:out value="${soggettoForm.indirizzoDomicilioEletto.toponimo.descrizione}" /> <c:out value="${soggettoForm.indirizzoDomicilioEletto.denominazione}" /></td>
					<td><form:label path="indirizzoDomicilioEletto.civico">
							<spring:message code="civico" />
						</form:label></td>
					<td><c:out value="${soggettoForm.indirizzoDomicilioEletto.civico}" /></td>
				</tr>
				<tr>
					<td><form:label path="pressoIndirizzoDomicilioEletto">
							<spring:message code="presso" />
						</form:label></td>
					<td><c:out value="${soggettoForm.pressoIndirizzoDomicilioEletto}" /></td>
					<td colspan="2"><c:out value="${soggettoForm.pressoIndirizzoDomicilioElettoSpecifica}" /></td>
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
					<td><c:if test="${soggettoForm.capSocDescr=='int'}">
							<fmt:formatNumber type="number" maxFractionDigits="2" value="${soggettoForm.capitaleSociale}" />
						</c:if> <c:if test="${soggettoForm.capSocDescr=='noInt'}">
							<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${soggettoForm.capitaleSociale}" />
						</c:if></td>
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
<%--
			<logic:equal name="soggettoForm" property="descrRuolo" value="TERZO">
				<c:set var="urlIndietro" value="/procedimento/soggetto/visualizzaDirettiInteressatiTerzi.htm" />
			</logic:equal>
			 
				<logic:notEqual name="provenienza" value="cancellazione">
					<table class="tb_bottoni" align="center">
						<tr>
							<td class="td_tasto"><input type="button" value="Indietro"
								onClick="indietro('${urlIndietro}')"></td>
						</tr>
					</table>
				</logic:notEqual>
			--%>
			<table class="tb_bottoni" align="center">
				<tr>
					<logic:equal name="provenienza" value="cancellazione">
						<td align="right" class="td_tasto"><input type="submit" name="CONFERMA" value="Conferma eliminazione"></td>
					</logic:equal>

					<c:if test="${provenienzaVersione==null && versioniSoggetto!=null && fn:length(versioniSoggetto)>1}">
						<td class="td_tasto">
							<input type="submit" name="Versioni" value="Versioni">
						</td>
					</c:if>
							
					<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('${urlIndietro}')"></td>
<%--					
					<td class="td_tasto"><input type="button" id="id_buttonIndietro" value="Indietro"></td>
--%>			
				</tr>
			</table>

			
			<br />
			<form:hidden path="idSoggetto" />
			<form:hidden path="idVersione" />
			<form:hidden path="tipoSoggetto" />
			<form:hidden path="descrRuolo" />
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="id_buttonIndietro"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>