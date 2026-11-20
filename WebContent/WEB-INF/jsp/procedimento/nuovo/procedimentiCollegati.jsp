<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"
				value="/procedimento/nuovo/dettaglioProcedimento.htm" />
			<form:form method="post" action="${azione}" commandName="soggettoForm">
				<p class="titolo_page"><spring:message code="procedimenticollegati.titolo" /></p>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<form:hidden path="idSoggetto"/>
					<form:hidden path="idVersione"/>
					<form:hidden path="tipoSoggetto"/>
					<form:hidden path="tipoFascicolo.id" id="tipoFascicoloSel"/>
					<tr>
						<td class="td_titolo" colspan="4">
							<c:out value="${soggettoForm.descrizioneSoggetto}" />
						</td>
					</tr>
				</table>
				<br />
				<display:table class="tb_lista" uid="procedimenti" name="${procedimenti}" pagesize="10"
					requestURI="${azione}?tipoSoggetto=${soggettoForm.tipoSoggetto}" >
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:radiobutton path="idProcedimento" value="${procedimenti.idProcedimento}" onclick="document.getElementById('tipoFascicoloSel').value = '${procedimenti.tipoFascicolo.id}'" />
					</display:column>
					<display:column property="annoNumeroProcedimento" title="Procedimento" sortable="true"/>
					<display:column property="tipoFascicolo.descrizione" title="Tipo fascicolo" sortable="true"/>
					<display:column property="dataApertura" title="Data apertura" sortable="true" format="{0,date,dd/MM/yyyy}"/>
					<display:column property="statoProcedimento.descrizione" title="Stato" sortable="true"/>
					<display:column property="descrRuolo" title="Ruolo nel Procedimento" sortable="true"/>
				</display:table>


				
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" name="DettaglioProcedimento" value="Dettaglio Procedimento"></td>
						<td class="td_tasto"><input type="button" id="backButton" name="Indietro" value="Indietro"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>

			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>
			
		</tiles:putAttribute>
	</tiles:insertDefinition>
	
