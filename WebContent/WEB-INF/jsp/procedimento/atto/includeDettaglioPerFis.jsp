<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>	
	
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">	
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo1" /></td>
				</tr>
				<tr>
					<td width="15%"><form:label path="soggetto.cognome">
							<spring:message code="cognome" />
						</form:label></td>
					<td width="32%"><c:out value="${attoForm.soggetto.cognome}" /></td>
					<td width="17%"><form:label path="soggetto.nome">
							<spring:message code="nome" />
						</form:label></td>
					<td width="32%"><c:out value="${attoForm.soggetto.nome}" /></td>
				</tr>
				<tr>
					<td><form:label path="soggetto.dataNascita">
							<spring:message code="dataNascita" />
						</form:label></td>
					<td>
						<div>
							<fmt:formatDate value="${attoForm.soggetto.dataNascita}" type="date" pattern="dd/MM/yyyy" />
						</div>
					</td>
				</tr>
				<tr>
					<td><form:label path="soggetto.statoNascita.descrizione">
							<spring:message code="stato" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.statoNascita.descrizione}" /></td>
					<td><form:label path="soggetto.comuneNascita.descrizione">
							<spring:message code="comune" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.descrizioneComuneNascitaItalianoEstero}" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo2" /></td>
				</tr>
				<tr>
					<td><form:label path="soggetto.sesso">
							<spring:message code="nuovosoggettofisico.sesso" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.descrSesso}" /></td>
					<td><form:label path="soggetto.codiceFiscale">
							<spring:message code="codiceFiscale" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.codiceFiscale}" /></td>
				</tr>
				<tr>
					<td><form:label path="soggetto.professione.descrizione">
							<spring:message code="nuovosoggettofisico.professione" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.professione.descrizione}" /></td>
					<td><form:label path="soggetto.cui">
							<spring:message code="soggettofisico.cui" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.cui}" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo7" /></td>
				</tr>
				<tr>
					<td><form:label path="soggetto.statoMorte.descrizione">
							<spring:message code="stato" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.statoMorte.descrizione}" /></td>
					<td><form:label path="soggetto.descrizioneComuneMorteItalianoEstero">
							<spring:message code="comune" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.descrizioneComuneMorteItalianoEstero}" /></td>
				</tr>
				<tr>
					<td><form:label path="soggetto.dataMorte">
							<spring:message code="nuovosoggettofisico.datadecesso" />
						</form:label></td>
					<td colspan="3"><fmt:formatDate value="${attoForm.soggetto.dataMorte}" type="date" pattern="dd/MM/yyyy" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo8" /></td>
				</tr>
				<tr>
					<td><form:label path="soggetto.cognomePaternita">
							<spring:message code="cognome" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.cognomePaternita}" /></td>
					<td><form:label path="soggetto.nomePaternita">
							<spring:message code="nome" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.nomePaternita}" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo9" /></td>
				</tr>
				<tr>
					<td><form:label path="soggetto.cognomeMaternita">
							<spring:message code="cognome" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.cognomeMaternita}" /></td>
					<td><form:label path="soggetto.nomeMaternita">
							<spring:message code="nome" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.nomeMaternita}" /></td>
				</tr>	
				<tr>
					<td>&nbsp;</td>	
				</tr>	
			    <tr>
					<td><form:label path="soggetto.magistrato">
							<spring:message code="atto.versionamento.magistrato" />
						</form:label></td>
					<td><c:out value="${attoForm.soggetto.magistrato.descrizione}" /></td>
			    </tr>						
		</table>

