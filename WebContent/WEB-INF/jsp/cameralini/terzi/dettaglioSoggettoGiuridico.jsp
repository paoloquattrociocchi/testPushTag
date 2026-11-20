<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione" value="/procedimento/nuovo/nuovoSoggettoGiuridico.htm" />
			<p class="titolo_page">
				<spring:message code="cameralini.prevenzione.titolo.ricerca.terzi" />
			</p>
			<form:form method="post" action="${azione}" commandName="form" id="idForm">
			<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td colspan="6" align="left"><spring:message code="cameralini.dettaglioProcedimento" /></td>
					</tr>
		  			<jsp:include page="/WEB-INF/jsp/cameralini/intestazioneDettaglio.jsp" />
			     </table>
			 	<br /><br />
				<p class="titolo_page"><spring:message code="soggettogiuridico.dettaglio"/></p>
				
				<br/>

			 	<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="cameralini.terzoInterv.personaGiuridica" /></td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">		
					<tr>
						<td><form:label path="soggettoForm.ragioneSociale">
								<spring:message code="soggettogiuridico.ragioneSociale" /></form:label></td>
						<td><c:out value="${form.soggettoForm.ragioneSociale}" /></td>

						<td><form:label path="soggettoForm.partitaIva">
								<spring:message code="soggettogiuridico.partitaIva" /></form:label></td>
						<td><c:out value="${form.soggettoForm.partitaIvaCompleta}" /></td>
					</tr>
					<tr>
						<td width="17%"><form:label path="soggettoForm.tipoSocieta">
								<spring:message code="soggettogiuridico.tipoSocieta" />
							</form:label></td>
						<td><c:out value="${form.soggettoForm.tipoSocieta}" /></td>
						<td><form:label path="soggettoForm.numeroCCIAA">
								<spring:message code="soggettogiuridico.numeroCCIAA" /></form:label></td>
						<td><c:out value="${form.soggettoForm.numeroCCIAA}" /></td>
					</tr>			
					<tr>
						<td class="td_titolo" colspan="4"><spring:message
								code="nuovosoggettogiuridico.titolo1" /></td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.statoSedeLegale.descrizione">
								<spring:message code="stato" />
							</form:label></td>
						<td><c:out value="${form.soggettoForm.statoSedeLegale.descrizione}" /></td>

						<td><form:label path="soggettoForm.comuneSedeLegale.descrizione">
							<spring:message code="comune" /></form:label></td>
						<td>							
							<c:out value="${form.soggettoForm.descrizioneComuneSedeLegaleItalianoEstero}" />							
						</td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.indirizzoSedeLegale.toponimo">
								<spring:message code="indirizzo" />
							</form:label></td>
						<td><c:out value="${form.soggettoForm.indirizzoSedeLegale.toponimo.descrizione}" />
								<c:out value="${form.soggettoForm.indirizzoSedeLegale.denominazione}" />						
						</td>
						<td><form:label path="soggettoForm.indirizzoSedeLegale.civico">
								<spring:message code="civico" />
							</form:label></td>
						<td colspan="3"><c:out value="${form.soggettoForm.indirizzoSedeLegale.civico}" /></td>
					</tr>
				
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.rappresentanteSociale">
								<spring:message code="soggettogiuridico.rappresentanteSociale" />
							</form:label></td>
						<td><c:out value="${form.soggettoForm.rappresentanteSociale}" /></td>

						<td><form:label path="soggettoForm.amministratore">
								<spring:message code="soggettogiuridico.amministratore" />
							</form:label></td>
						<td><c:out value="${form.soggettoForm.amministratore}" /></td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.capitaleSociale">
								<spring:message code="soggettogiuridico.capitaleSociale" />
							</form:label></td>
						<td>
							<c:if test="${form.soggettoForm.capSocDescr=='int'}">
								<fmt:formatNumber type="number" maxFractionDigits="2" value="${form.soggettoForm.capitaleSociale}" />
							</c:if>
							<c:if test="${form.soggettoForm.capSocDescr=='noInt'}">
								<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${form.soggettoForm.capitaleSociale}" />
							</c:if>
						</td>
						<td><form:label path="soggettoForm.settoreAttivita">
								<spring:message code="soggettogiuridico.settoreAttivita" />
							</form:label></td>
						<td><c:out value="${form.soggettoForm.settoreAttivita.descrizione}" /></td>
					</tr>
					<tr>
						<td><form:label path="soggettoForm.annotazioni">
								<spring:message code="annotazioni" />
							</form:label></td>
						<td colspan="3"><c:out value="${form.soggettoForm.annotazioni}" /></td>
					</tr>
				</table>
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>										
						<td class="td_tasto"><input type="submit" value="Indietro" name="IndietroDettaglioGiuridico" title="Indietro" /></td>
					</tr>
				</table>
	</form:form>			
				<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
				<smp:back buttonId="backButton"></smp:back>

				<br />
				<br />

		</tiles:putAttribute>
	</tiles:insertDefinition>
