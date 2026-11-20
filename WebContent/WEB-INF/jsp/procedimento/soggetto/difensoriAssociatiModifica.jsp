<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>



	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione" value="/procedimento/soggetto/difensoriAssociatiModifica.htm" />
			<p class="titolo_page"><spring:message code="soggetto.difensori.modifica" /></p>
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
					
					<logic:notEmpty name="difensoreSoggettoForm" property="difensoreForm.idReginde">
					<tr>
						<td><form:label path="difensoreForm.cognome" cssClass="required"><spring:message code="cognome" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.cognome}" /></td>
							<form:hidden path="difensoreForm.cognome"/>
						<td><form:label path="difensoreForm.nome" cssClass="required"><spring:message code="nome" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.nome}" /></td>
							<form:hidden path="difensoreForm.nome"/>
					</tr>
					</logic:notEmpty>
					<logic:empty name="difensoreSoggettoForm" property="difensoreForm.idReginde">
					<tr>
						<td><form:label path="difensoreForm.cognome" cssClass="required"><spring:message code="cognome" /></form:label></td>
						<td><form:input path="difensoreForm.cognome" maxlength="35" size="30"/>
							<form:errors path="difensoreForm.cognome" class="testoRosso"/></td>
						<td><form:label path="difensoreForm.nome" cssClass="required"><spring:message code="nome" /></form:label></td>
						<td><form:input path="difensoreForm.nome" maxlength="35" size="30"/>
							<form:errors path="difensoreForm.nome" class="testoRosso"/></td>
					</tr>
					</logic:empty>
					<tr>
						<td><form:label path="dataNomina" cssClass="required"><spring:message code="soggetto.difensore.datanomina" /></form:label></td>
						<td><form:input path="dataNomina" maxlength="10" size="12" id="dataNomina"/>
							<form:errors path="dataNomina" class="testoRosso" /></td>
						<td><form:label path="tipoNomina" cssClass="required"><spring:message code="soggetto.difensore.tiponomina" /></form:label></td>
						<td><form:select path="tipoNomina.id" size="1">
								<form:option value="U" label="D'ufficio"/>
								<form:option value="F" label="Di fiducia"/> 																	
							</form:select>
						<form:errors path="tipoNomina.id" class="testoRosso"/>
						</td>
						
					</tr>
					<tr>
						<td><form:label path="dataRevoca"><spring:message code="soggetto.difensore.datarevoca" /></form:label></td>
						<td colspan="3"><form:input path="dataRevoca" maxlength="10" size="12" id="dataRevoca"/>
								<form:errors path="dataRevoca" class="testoRosso" /></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo1" /></td>
					</tr>
					
					<logic:empty name="difensoreSoggettoForm" property="difensoreForm.idReginde">
					<tr>
						<td><form:label path="difensoreForm.dataNascita"><spring:message code="dataNascita" /></form:label></td>
						<td colspan="3">
								<form:input path="difensoreForm.dataNascita" maxlength="10" size="12" id="dataNascita"/>
								<form:errors path="difensoreForm.dataNascita" class="testoRosso" />
						</td>								
					</tr>
					<tr>
						<td><form:label path="difensoreForm.luogoNascita"><spring:message code="soggetto.difensore.luogo" /></form:label></td>
						<td><form:input path="difensoreForm.luogoNascita" maxlength="256" size="60"/></td>
						<td><form:label path="difensoreForm.provinciaNascita"><spring:message code="provincia" /></form:label></td>
						<td><form:input path="difensoreForm.provinciaNascita" maxlength="4" size="6"/></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo2" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.codiceFiscale"><spring:message code="codiceFiscale" /></form:label></td>
						<td><form:input path="difensoreForm.codiceFiscale" maxlength="16" size="25"/>
							<form:errors path="difensoreForm.codiceFiscale" class="testoRosso" /></td>
						<td><form:label path="difensoreForm.pec"><spring:message code="pec" /></form:label></td>
						<td><form:input path="difensoreForm.pec" maxlength="255" size="30"/>
							<form:errors path="difensoreForm.pec" class="testoRosso" /></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo3" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.comuneResidenza"><spring:message code="comune" /></form:label></td>
						<td><form:input path="difensoreForm.comuneResidenza" maxlength="256" size="50"/></td>
						<td><form:label path="difensoreForm.provinciaResidenza" ><spring:message code="provincia"/></form:label></td>
			        	<td><form:input path="difensoreForm.provinciaResidenza" maxlength="4" size="6"/></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.indirizzoResidenza"><spring:message code="indirizzo" /></form:label></td>
						<td colspan="2"><form:input path="difensoreForm.indirizzoResidenza" maxlength="256" size="60"/></td>
						<td><form:label path="difensoreForm.capResidenza"><spring:message code="cap" /></form:label>
							<form:input path="difensoreForm.capResidenza" maxlength="5" size="6"/>
							<form:errors path="difensoreForm.capResidenza" class="testoRosso" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.telefonoResidenza"><spring:message code="soggetto.difensore.telefono" /></form:label></td>
						<td><form:input path="difensoreForm.telefonoResidenza" maxlength="15" size="17"/>
							<form:errors path="difensoreForm.telefonoResidenza" class="testoRosso" /></td>
						<td><form:label path="difensoreForm.faxResidenza"><spring:message code="soggetto.difensore.fax" /></form:label></td>
						<td><form:input path="difensoreForm.faxResidenza" maxlength="15" size="17"/>
							<form:errors path="difensoreForm.faxResidenza" class="testoRosso" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.emailResidenza"><spring:message code="soggetto.difensore.email" /></form:label></td>
						<td colspan="3"><form:input path="difensoreForm.emailResidenza" maxlength="256" size="40"/>
							<form:errors path="difensoreForm.emailResidenza" class="testoRosso" /></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.difensore.domiciliolegale" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.comuneDomicilioLegale"><spring:message code="comune" /></form:label></td>
						<td><form:input path="difensoreForm.comuneDomicilioLegale" maxlength="256" size="50"/></td>
						<td><form:label path="difensoreForm.provinciaDomicilioLegale" ><spring:message code="provincia"/></form:label></td>
			        	<td><form:input path="difensoreForm.provinciaDomicilioLegale" maxlength="4" size="6"/></td>
					</tr>
					<tr>
						<tr>
						<td><form:label path="difensoreForm.indirizzoDomicilioLegale"><spring:message code="indirizzo" /></form:label></td>
						<td colspan="2"><form:input path="difensoreForm.indirizzoDomicilioLegale" maxlength="256" size="60"/></td>
						<td><form:label path="difensoreForm.capDomicilioLegale"><spring:message code="cap" /></form:label>
							<form:input path="difensoreForm.capDomicilioLegale" maxlength="5" size="6"/>
							<form:errors path="difensoreForm.capDomicilioLegale" class="testoRosso" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.telefonoDomicilioLegale"><spring:message code="soggetto.difensore.telefono" /></form:label></td>
						<td><form:input path="difensoreForm.telefonoDomicilioLegale" maxlength="15" size="17"/>
							<form:errors path="difensoreForm.telefonoDomicilioLegale" class="testoRosso" /></td>
						<td><form:label path="difensoreForm.faxDomicilioLegale"><spring:message code="soggetto.difensore.fax" /></form:label></td>
						<td><form:input path="difensoreForm.faxDomicilioLegale" maxlength="15" size="17"/>
							<form:errors path="difensoreForm.faxDomicilioLegale" class="testoRosso" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.emailDomicilioLegale"><spring:message code="soggetto.difensore.email" /></form:label></td>
						<td colspan="3"><form:input path="difensoreForm.emailDomicilioLegale" maxlength="256" size="40"/>
							<form:errors path="difensoreForm.emailDomicilioLegale" class="testoRosso" /></td>
					</tr>
					</logic:empty>
					
					
					<logic:notEmpty name="difensoreSoggettoForm" property="difensoreForm.idReginde">
					<tr>
						<td><form:label path="difensoreForm.dataNascita"><spring:message code="dataNascita" /></form:label></td>
						<td colspan="3"><fmt:formatDate value="${difensoreSoggettoForm.difensoreForm.dataNascita}" type="date" pattern="dd/MM/yyyy" /></td>
						<form:hidden path="difensoreForm.dataNascita"/> 							
					</tr>
					<tr>
						<td><form:label path="difensoreForm.luogoNascita"><spring:message code="soggetto.difensore.luogo" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.luogoNascita}" /></td>
							<form:hidden path="difensoreForm.luogoNascita"/> 
						<td><form:label path="difensoreForm.provinciaNascita"><spring:message code="provincia" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.provinciaNascita}" /></td>
							<form:hidden path="difensoreForm.provinciaNascita"/> 
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo2" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.codiceFiscale"><spring:message code="codiceFiscale" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.codiceFiscale}" /></td>
							<form:hidden path="difensoreForm.codiceFiscale"/> 
						<td><form:label path="difensoreForm.pec"><spring:message code="pec" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.pec}" /></td>
							<form:hidden path="difensoreForm.pec"/> 
					</tr>
					<tr>
						<td><form:label path="difensoreForm.status"><spring:message code="soggetto.difensore.status" /></form:label></td>
						<td colspan="3"><c:out value="${difensoreSoggettoForm.difensoreForm.status}" />
							<form:hidden path="difensoreForm.status"/> </td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo3" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.comuneResidenza"><spring:message code="comune" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.comuneResidenza}" /></td>
							<form:hidden path="difensoreForm.comuneResidenza"/> 
						<td><form:label path="difensoreForm.provinciaResidenza" ><spring:message code="provincia"/></form:label></td>
			        	<td><c:out value="${difensoreSoggettoForm.difensoreForm.provinciaResidenza}" /></td>
			        		<form:hidden path="difensoreForm.provinciaResidenza"/> 
					</tr>
					<tr>
						<td><form:label path="difensoreForm.indirizzoResidenza"><spring:message code="indirizzo" /></form:label></td>
						<td colspan="2"><c:out value="${difensoreSoggettoForm.difensoreForm.indirizzoResidenza}" /></td>
							<form:hidden path="difensoreForm.indirizzoResidenza"/> 
						<td><form:label path="difensoreForm.capResidenza"><spring:message code="cap" /></form:label>
							<c:out value="${difensoreSoggettoForm.difensoreForm.capResidenza}" /></td>
							<form:hidden path="difensoreForm.capResidenza"/> 
					</tr>
					<tr>
						<td><form:label path="difensoreForm.telefonoResidenza"><spring:message code="soggetto.difensore.telefono" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.telefonoResidenza}" /></td>
							<form:hidden path="difensoreForm.telefonoResidenza"/> 
						<td><form:label path="difensoreForm.faxResidenza"><spring:message code="soggetto.difensore.fax" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.faxResidenza}" /></td>
							<form:hidden path="difensoreForm.faxResidenza"/> 
					</tr>
					<tr>
						<td><form:label path="difensoreForm.emailResidenza"><spring:message code="soggetto.difensore.email" /></form:label></td>
						<td colspan="3"><c:out value="${difensoreSoggettoForm.difensoreForm.emailResidenza}" /></td>
							<form:hidden path="difensoreForm.emailResidenza"/> 
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.difensore.domiciliolegale" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.comuneDomicilioLegale"><spring:message code="comune" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.comuneDomicilioLegale}" /></td>
							<form:hidden path="difensoreForm.comuneDomicilioLegale"/> 
						<td><form:label path="difensoreForm.provinciaDomicilioLegale" ><spring:message code="provincia"/></form:label></td>
			        	<td><c:out value="${difensoreSoggettoForm.difensoreForm.provinciaDomicilioLegale}" /></td>
			        		<form:hidden path="difensoreForm.provinciaDomicilioLegale"/> 
					</tr>
					<tr>
						<tr>
						<td><form:label path="difensoreForm.indirizzoDomicilioLegale"><spring:message code="indirizzo" /></form:label></td>
						<td colspan="2"><c:out value="${difensoreSoggettoForm.difensoreForm.indirizzoDomicilioLegale}" /></td>
							<form:hidden path="difensoreForm.indirizzoDomicilioLegale"/> 
						<td><form:label path="difensoreForm.capDomicilioLegale"><spring:message code="cap" /></form:label>
							<c:out value="${difensoreSoggettoForm.difensoreForm.capDomicilioLegale}" /></td>
							<form:hidden path="difensoreForm.capDomicilioLegale"/> 
					</tr>
					<tr>
						<td><form:label path="difensoreForm.telefonoDomicilioLegale"><spring:message code="soggetto.difensore.telefono" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.telefonoDomicilioLegale}" /></td>
							<form:hidden path="difensoreForm.telefonoDomicilioLegale"/> 
						<td><form:label path="difensoreForm.faxDomicilioLegale"><spring:message code="soggetto.difensore.fax" /></form:label></td>
						<td><c:out value="${difensoreSoggettoForm.difensoreForm.faxDomicilioLegale}" /></td>
							<form:hidden path="difensoreForm.faxDomicilioLegale"/> 
					</tr>
					<tr>
						<td><form:label path="difensoreForm.emailDomicilioLegale"><spring:message code="soggetto.difensore.email" /></form:label></td>
						<td colspan="3"><c:out value="${difensoreSoggettoForm.difensoreForm.emailDomicilioLegale}" /></td>
							<form:hidden path="difensoreForm.emailDomicilioLegale"/> 
					</tr>
					
					</logic:notEmpty>
					
					
				</table>
				
				<form:hidden path="soggettoForm.idSoggetto"/>
				<form:hidden path="soggettoForm.idVersione"/>
				<form:hidden path="soggettoForm.tipoSoggetto"/>
				<form:hidden path="difensoreForm.idDifensore"/>
				<form:hidden path="difensoreForm.idReginde"/>
					
				<table class="tb_bottoni" align="center">
					<tr>
						<c:if test="${(difensoreSoggettoForm.difensoreForm.idDifensore != null && difensoreSoggettoForm.difensoreForm.idReginde != null)}">
						<td class="td_tasto"><input type="submit" value="Visualizza da Reginde" name="VisualizzaDaReginde"></td>
						</c:if>
						<td class="td_tasto"><input type="submit" value="Salva" name="SalvaDifensore"></td>
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
