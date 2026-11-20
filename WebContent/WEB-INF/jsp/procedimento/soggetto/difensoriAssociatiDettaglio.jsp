<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>



	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione" value="/procedimento/soggetto/difensoriAssociatiDettaglio.htm" />
			<p class="titolo_page"><spring:message code="soggetto.difensori.dettaglio" /></p>
			<form:form method="post" action="${azione}" commandName="difensoreSoggettoForm">
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br/>
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><c:out value="${difensoreSoggettoForm.soggettoForm.descrizioneSoggetto}" /></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetti.difensore.difensore" /></td>
					</tr>
					<tr>
						<td width="15%"><form:label path="difensoreForm.cognome"><spring:message code="cognome" /></form:label></td>
						<td width="32%"><c:out value="${difensoreSoggettoForm.difensoreForm.cognome}" /></td>
						<td width="17%"><form:label path="difensoreForm.nome"><spring:message code="nome" /></form:label></td>
						<td width="32%"><c:out value="${difensoreSoggettoForm.difensoreForm.nome}" /></td>
					</tr>
					<tr>
						<td><form:label path="dataNomina"><spring:message code="soggetto.difensore.datanomina" /></form:label></td>
						<td><div>
						<fmt:formatDate value="${difensoreSoggettoForm.dataNomina}" type="date" pattern="dd/MM/yyyy"/>
						</div></td>
						<td><form:label path="tipoNomina"><spring:message code="soggetto.difensore.tiponomina" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.tipoNomina.descrizione}" /></td>
					</tr>
					<tr>
						<td><form:label path="dataRevoca"><spring:message code="soggetto.difensore.datarevoca" /></form:label></td>
						<td colspan="3"><div>
						<fmt:formatDate value="${difensoreSoggettoForm.dataRevoca}" type="date" pattern="dd/MM/yyyy"/>
						</div></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo1" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.dataNascita"><spring:message code="dataNascita" /></form:label></td>
						<td colspan="3"><fmt:formatDate value="${difensoreSoggettoForm.difensoreForm.dataNascita}" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.luogoNascita"><spring:message code="soggetto.difensore.luogo" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.luogoNascita}" /></td>
						<td><form:label path="difensoreForm.provinciaNascita"><spring:message code="provincia" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.provinciaNascita}" /></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo2" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.codiceFiscale"><spring:message code="codiceFiscale" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.codiceFiscale}" /></td>
						<td><form:label path="difensoreForm.pec"><spring:message code="pec" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.pec}" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.status"><spring:message code="soggetto.difensore.status" /></form:label></td>
						<td colspan="3"><c:out value="${difensoreSoggettoForm.difensoreForm.status}" />
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo3" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.comuneResidenza"><spring:message code="comune" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.comuneResidenza}" /></td>
						<td><form:label path="difensoreForm.provinciaResidenza" ><spring:message code="provincia"/></form:label></td>
			        	<td><c:out value="${difensoreSoggettoForm.difensoreForm.provinciaResidenza}" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.indirizzoResidenza"><spring:message code="indirizzo" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.indirizzoResidenza}" /></td>
						<td><form:label path="difensoreForm.capResidenza"><spring:message code="cap" /></form:label>
						<c:out value="${difensoreSoggettoForm.difensoreForm.capResidenza}" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.telefonoResidenza"><spring:message code="soggetto.difensore.telefono" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.telefonoResidenza}" /></td>
						<td><form:label path="difensoreForm.faxResidenza"><spring:message code="soggetto.difensore.fax" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.faxResidenza}" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.emailResidenza"><spring:message code="soggetto.difensore.email" /></form:label></td>
						<td colspan="3"><c:out value="${difensoreSoggettoForm.difensoreForm.emailResidenza}" /></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.difensore.domiciliolegale" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.comuneDomicilioLegale"><spring:message code="comune" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.comuneDomicilioLegale}" /></td>
						<td><form:label path="difensoreForm.provinciaDomicilioLegale" ><spring:message code="provincia"/></form:label></td>
			        	<td><c:out value="${difensoreSoggettoForm.difensoreForm.provinciaDomicilioLegale}" /></td>
			    	</tr>
					<tr>
						<tr>
						<td><form:label path="difensoreForm.indirizzoDomicilioLegale"><spring:message code="indirizzo" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.indirizzoDomicilioLegale}" /></td>
						<td><form:label path="difensoreForm.capDomicilioLegale"><spring:message code="cap" /></form:label>
						<c:out value="${difensoreSoggettoForm.difensoreForm.capDomicilioLegale}" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.telefonoDomicilioLegale"><spring:message code="soggetto.difensore.telefono" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.telefonoDomicilioLegale}" /></td>
						<td><form:label path="difensoreForm.faxDomicilioLegale"><spring:message code="soggetto.difensore.fax" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.faxDomicilioLegale}" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.emailDomicilioLegale"><spring:message code="soggetto.difensore.email" /></form:label></td>
						<td colspan="3"><c:out value="${difensoreSoggettoForm.difensoreForm.emailDomicilioLegale}" /></td>
					</tr>
				</table>
				
				<form:hidden path="soggettoForm.idSoggetto"/>
				<form:hidden path="soggettoForm.idVersione"/>
				<form:hidden path="soggettoForm.tipoSoggetto"/>
				<form:hidden path="difensoreForm.idDifensore"/>
				<form:hidden path="difensoreForm.idReginde"/>
					
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
					</tr>
				</table>
			</form:form>
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>
			
			
			<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataNascita",true);
					prendiData("#dataNomina",true);
					prendiData("#dataRevoca",true);
					});
			</script>
		</tiles:putAttribute>
	</tiles:insertDefinition>
