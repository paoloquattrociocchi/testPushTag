<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
			
				<tr>
					<td><form:label path="soggetto.ragioneSociale">
							<spring:message code="soggettogiuridico.ragioneSociale" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.ragioneSociale}" /></td>

					<td><form:label path="soggetto.partitaIva">
							<spring:message code="soggettogiuridico.partitaIva" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.partitaIvaCompleta}" /></td>
				</tr>
				<tr>
					<td><form:label path="soggetto.tipoSocieta">
							<spring:message code="soggettogiuridico.tipoSocieta" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.tipoSocieta}" /></td>
					<td><form:label path="soggetto.numeroCCIAA">
							<spring:message code="soggettogiuridico.numeroCCIAA" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.numeroCCIAA}" /></td>
				</tr>
				
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettogiuridico.titolo1" /></td>
				</tr>
				<tr>
					<td><form:label path="soggetto.statoSedeLegale.descrizione">
							<spring:message code="stato" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.statoSedeLegale.descrizione}" /></td>

					<td><form:label path="soggetto.comuneSedeLegale.descrizione">
							<spring:message code="comune" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.descrizioneComuneSedeLegaleItalianoEstero}" /></td>
				</tr>
				<tr>
					<td><form:label path="soggetto.indirizzoSedeLegale.toponimo">
							<spring:message code="indirizzo" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.indirizzoSedeLegale.toponimo.descrizione}" /> <c:out value="${attoForm.soggetto.indirizzoSedeLegale.denominazione}" /></td>
					<td><form:label path="soggetto.indirizzoSedeLegale.civico">
							<spring:message code="civico" />
						</form:label></td>
					<td colspan="3"><c:out value="${attoForm.soggetto.indirizzoSedeLegale.civico}" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>
				<tr>
					<td><form:label path="soggetto.rappresentanteSociale">
							<spring:message code="soggettogiuridico.rappresentanteSociale" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.rappresentanteSociale}" /></td>

					<td><form:label path="soggetto.amministratore">
							<spring:message code="soggettogiuridico.amministratore" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.amministratore}" /></td>
				</tr>
				<tr>
					<td><form:label path="soggetto.capitaleSociale">
							<spring:message code="soggettogiuridico.capitaleSociale" />
						</form:label></td>
					<td><c:if test="${attoForm.soggetto.capSocDescr=='int'}">
							<fmt:formatNumber type="number" maxFractionDigits="2" value="${attoForm.soggetto.capitaleSociale}" />
						</c:if> <c:if test="${attoForm.soggetto.capSocDescr=='noInt'}">
							<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${attoForm.soggetto.capitaleSociale}" />
						</c:if></td>
					<td><form:label path="soggetto.settoreAttivita">
							<spring:message code="soggettogiuridico.settoreAttivita" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.settoreAttivita.descrizione}" /></td>
				</tr>
				<tr>
					<td><form:label path="soggetto.annotazioni">
							<spring:message code="annotazioni" />
						</form:label></td>
					<td colspan="3"><c:out value="${attoForm.soggetto.annotazioni}" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>	
				</tr>				
				<tr>
						<td><form:label path="soggetto.magistrato">
								<spring:message code="atto.versionamento.magistrato" />
							</form:label></td>
						<td colspan="3"><c:out value="${attoForm.soggetto.magistrato.descrizione}" /></td>
				</tr>				
			</table>

