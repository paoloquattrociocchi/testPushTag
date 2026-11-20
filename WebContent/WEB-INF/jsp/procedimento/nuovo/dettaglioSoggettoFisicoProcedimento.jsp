<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page import="it.giustizia.smp.utility.Constants"%>


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
		<!-- Segnalazione EMS-70 -->
		<logic:equal name="contesto" value="sentenza">
			<c:set var="urlIndietro" value="/procedimento/atto/copertinaAtto.htm" />
		</logic:equal>

		<c:set var="ID_RUOLO" value="<%=Constants.ID_RUOLO_INTERVENIENTE%>" />

		<form:form method="post" action="${azione}" commandName="soggettoForm">
			<p class="titolo_page">
				<c:choose>
					<c:when test="${soggettoForm.idRuolo == ID_RUOLO}">
						<spring:message code="soggettoFisico.interveniente.dettaglio" />
					</c:when>
					<c:otherwise>
						<spring:message code="soggettoFisico.dettaglio" />
					</c:otherwise>
				</c:choose>
				
			</p>
			<logic:present name="procedimento">
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			</logic:present>
			<br />
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
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo1" /></td>
				</tr>
				<tr>
					<td width="15%"><form:label path="cognome">
							<spring:message code="cognome" />
						</form:label></td>
					<td width="32%"><c:out value="${soggettoForm.cognome}" /></td>
					<td width="17%"><form:label path="nome">
							<spring:message code="nome" />
						</form:label></td>
					<td width="32%"><c:out value="${soggettoForm.nome}" /></td>
				</tr>
				<tr>
					<td><form:label path="dataNascita">
							<spring:message code="dataNascita" />
						</form:label></td>
					<td>
						<div>
							<fmt:formatDate value="${soggettoForm.dataNascita}" type="date" pattern="dd/MM/yyyy" />
							<!-- 
								<form:checkbox path="persDaIdent" disabled="true" />
								<spring:message code="nuovosoggettofisico.persDaIdent" />
								 -->
							<form:hidden path="persDaIdent" />
						</div>
					</td>
					<td><form:label path="presunta">
							<spring:message code="dataNascitaPresunta" />
						</form:label></td>
					<td><c:if test="${soggettoForm.presunta==true}">
							<input type="checkbox" disabled="disabled" checked="checked" />
						</c:if> <c:if test="${soggettoForm.presunta==false || soggettoForm.presunta==null}">
							<input type="checkbox" disabled="disabled" />
						</c:if></td>
				</tr>
				<tr>
					<td><form:label path="statoNascita.descrizione">
							<spring:message code="stato" />
						</form:label></td>
					<td><c:out value="${soggettoForm.statoNascita.descrizione}" /></td>
					<td><form:label path="comuneNascita.descrizione">
							<spring:message code="comune" />
						</form:label></td>
					<td><c:out value="${soggettoForm.descrizioneComuneNascitaItalianoEstero}" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo2" /></td>
				</tr>
				<tr>
					<td><form:label path="sesso">
							<spring:message code="nuovosoggettofisico.sesso" />
						</form:label></td>
					<td><c:out value="${soggettoForm.descrSesso}" /></td>
					<td><form:label path="codiceFiscale">
							<spring:message code="codiceFiscale" />
						</form:label></td>
					<td><c:out value="${soggettoForm.codiceFiscale}" /></td>
				</tr>
				<tr>
					<td><form:label path="professione.descrizione">
							<spring:message code="nuovosoggettofisico.professione" />
						</form:label></td>
					<td><c:out value="${soggettoForm.professione.descrizione}" /></td>
					<td><form:label path="cui">
							<spring:message code="soggettofisico.cui" />
						</form:label></td>
					<td><c:out value="${soggettoForm.cui}" /></td>
				</tr>
				<tr>
					<td><form:label path="condizioneGiuridica.descrizione">
							<spring:message code="nuovosoggettofisico.condizionegiuridica" />
						</form:label></td>
					<td><c:out value="${soggettoForm.condizioneGiuridica.descrizione}" /></td>
					<td><form:label path="istitutoDiDetenzione.descrizione">
							<spring:message code="nuovosoggettofisico.istitutodidetenzione" />
						</form:label></td>
					<td><c:out value="${soggettoForm.istitutoDiDetenzione.descrizione}" /></td>
				</tr>

				<logic:notEmpty name="soggettoForm" property="descIstitutoDetenzioneEstero">
					<tr>
						<td><form:label path="descIstitutoDetenzioneEstero">
								<spring:message code="nuovosoggettofisico.istitutodidetenzioneEstero" />
							</form:label></td>
						<td colspan="3"><c:out value="${soggettoForm.descIstitutoDetenzioneEstero}" /></td>
					</tr>
				</logic:notEmpty>

				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo3" /></td>
				</tr>
				<tr>
					<td><form:label path="statoResidenza.descrizione">
							<spring:message code="stato" />
						</form:label></td>
					<td><c:out value="${soggettoForm.statoResidenza.descrizione}" /></td>
					<td><form:label path="descrizioneComuneResidenzaItalianoEstero">
							<spring:message code="comune" />
						</form:label></td>
					<td><c:out value="${soggettoForm.descrizioneComuneResidenzaItalianoEstero}" /></td>
				</tr>
				<tr>
					<td><form:label path="indirizzoResidenza">
							<spring:message code="indirizzo" />
						</form:label></td>
					<td><c:out value="${soggettoForm.indirizzoResidenza.toponimo.descrizione}" /> <c:out value="${soggettoForm.indirizzoResidenza.denominazione}" /></td>
					<td><form:label path="indirizzoResidenza.civico">
							<spring:message code="civico" />
						</form:label></td>
					<td><c:out value="${soggettoForm.indirizzoResidenza.civico}" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo4" /></td>
				</tr>
				<tr>
					<td><form:label path="statoDomicilio">
							<spring:message code="stato" />
						</form:label></td>
					<td><c:out value="${soggettoForm.statoDomicilio.descrizione}" /></td>
					<td><form:label path="descrizioneComuneDomicilioItalianoEstero">
							<spring:message code="comune" />
						</form:label></td>
					<td><c:out value="${soggettoForm.descrizioneComuneDomicilioItalianoEstero}" /></td>
				</tr>
				<tr>
					<td><form:label path="indirizzoDomicilio">
							<spring:message code="nuovosoggettofisico.indirizzo" />
						</form:label></td>
					<td><c:out value="${soggettoForm.indirizzoDomicilio.toponimo.descrizione}" /> <c:out value="${soggettoForm.indirizzoDomicilio.denominazione}" /></td>
					<td><form:label path="indirizzoDomicilio.civico">
							<spring:message code="civico" />
						</form:label></td>
					<td><c:out value="${soggettoForm.indirizzoDomicilio.civico}" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo5" /></td>
				</tr>
				<tr>
					<td><form:label path="statoDimora.descrizione">
							<spring:message code="stato" />
						</form:label></td>
					<td><c:out value="${soggettoForm.statoDimora.descrizione}" /></td>
					<td><form:label path="descrizioneComuneDimoraItalianoEstero">
							<spring:message code="comune" />
						</form:label></td>
					<td><c:out value="${soggettoForm.descrizioneComuneDimoraItalianoEstero}" /></td>
				</tr>
				<tr>
					<td><form:label path="indirizzoDimora">
							<spring:message code="nuovosoggettofisico.indirizzo" />
						</form:label></td>
					<td><c:out value="${soggettoForm.indirizzoDimora.toponimo.descrizione}" /> <c:out value="${soggettoForm.indirizzoDimora.denominazione}" /></td>
					<td><form:label path="indirizzoDimora.civico">
							<spring:message code="civico" />
						</form:label></td>
					<td><c:out value="${soggettoForm.indirizzoDimora.civico}" /></td>
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
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo7" /></td>
				</tr>
				<tr>
					<td><form:label path="statoMorte.descrizione">
							<spring:message code="stato" />
						</form:label></td>
					<td><c:out value="${soggettoForm.statoMorte.descrizione}" /></td>
					<td><form:label path="descrizioneComuneMorteItalianoEstero">
							<spring:message code="comune" />
						</form:label></td>
					<td><c:out value="${soggettoForm.descrizioneComuneMorteItalianoEstero}" /></td>
				</tr>
				<tr>
					<td><form:label path="dataMorte">
							<spring:message code="nuovosoggettofisico.datadecesso" />
						</form:label></td>
					<td colspan="3"><fmt:formatDate value="${soggettoForm.dataMorte}" type="date" pattern="dd/MM/yyyy" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo8" /></td>
				</tr>
				<tr>
					<td><form:label path="cognomePaternita">
							<spring:message code="cognome" />
						</form:label></td>
					<td><c:out value="${soggettoForm.cognomePaternita}" /></td>
					<td><form:label path="nomePaternita">
							<spring:message code="nome" />
						</form:label></td>
					<td><c:out value="${soggettoForm.nomePaternita}" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo9" /></td>
				</tr>
				<tr>
					<td><form:label path="cognomeMaternita">
							<spring:message code="cognome" />
						</form:label></td>
					<td><c:out value="${soggettoForm.cognomeMaternita}" /></td>
					<td><form:label path="nomeMaternita">
							<spring:message code="nome" />
						</form:label></td>
					<td><c:out value="${soggettoForm.nomeMaternita}" /></td>
				</tr>
			</table>

			<br />
			<br />
<%--
			<logic:equal name="soggettoForm" property="descrRuolo" value="TERZO">
				<c:set var="urlIndietro" value="/procedimento/soggetto/visualizzaDirettiInteressatiTerzi.htm" />
			</logic:equal>

			<table class="tb_bottoni" align="center">
				<tr>
					<logic:present name="decretoDecisorio">
						<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/atto/copertinaAtto.htm')"></td>
					</logic:present>
					<logic:notPresent name="decretoDecisorio">
						<logic:equal name="provenienza" value="cancellazione">
							<td align="right" class="td_tasto"><input type="submit" name="CONFERMA" value="Conferma eliminazione"></td>
							<td class="td_tasto"><input type="button" value="Annulla" onClick="indietro('${urlIndietro}')"></td>
						</logic:equal>
						<logic:notEqual name="provenienza" value="cancellazione">
							<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('${urlIndietro}')"></td>
						</logic:notEqual>
					</logic:notPresent>
				</tr>
			</table>
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
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
			   		<!-- <logic:equal name="contesto" value="versioni">
			   			<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
			   		</logic:equal>
					<logic:notEqual name="contesto" value="versioni">
						<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('${urlIndietro}')"></td>
					</logic:notEqual> -->

				</tr>
			</table>
			<br />
			<br />
			<form:hidden path="idSoggetto" />
			<form:hidden path="idVersione" />
			<form:hidden path="tipoSoggetto" />
			<form:hidden path="descrRuolo" />
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
		<smp:back buttonId="backButton"></smp:back>
		
	</tiles:putAttribute>
</tiles:insertDefinition>