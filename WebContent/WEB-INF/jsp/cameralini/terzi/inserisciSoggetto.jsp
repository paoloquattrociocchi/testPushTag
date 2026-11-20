<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
			<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.ricerca.terzi" /></p>
			<spring:url var="azione" value="/procedimento/nuovo/terzi/ricerca.htm" />
			
			<form:form id="id_terzi" method="post" action="${azione}" commandName="form">
			<%-- CAMPI DI HIDDEN --%>
			<form:hidden id="tipoSoggetto" path="tipoSoggetto" />
			<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td colspan="6" align="left"><spring:message code="cameralini.dettaglioProcedimento" /></td>
					</tr>
		  			<jsp:include page="/WEB-INF/jsp/cameralini/intestazioneDettaglio.jsp" />
			     </table>
			 <br /><br />
			 <%-- INIZIO PERSONA FISICA --%>
			 <c:if test="${form.tipoSoggetto=='F'}" >
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="cameralini.terzoInterv.personaFisica" /></td>
					</tr>
					<tr>
						<td><form:label path="cognome" cssClass="required"><spring:message code="cognome" /></form:label></td>
						<td><form:input path="cognome" maxlength="35" size="30" /><form:errors path="cognome" class="testoRosso" /></td>
						<td><form:label path="nome" cssClass="required"><spring:message code="nome" /></form:label></td>
						<td><form:input path="nome" maxlength="35" size="30" /><form:errors path="nome" class="testoRosso" /></td>
					</tr>
					<tr>
						<td><form:label path="dataNascita" cssClass="required"><spring:message code="dataNascita" /></form:label></td>
						<td>
							<form:input path="dataNascita" maxlength="10" size="12" id="dataNascita"/>
							<form:errors path="dataNascita" class="testoRosso" />
						</td>
						<td>&nbsp;</td><td>&nbsp;</td>
					</tr>
					<tr>
						<td><form:label path="statoNascita.id" cssClass="required"><spring:message code="stato" /></form:label></td>
						<td><form:select id="id_StatoNascita" path="statoNascita.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneNascita')">
								<form:option value="-1" label="---Seleziona Stato---"/>
								<form:options items="${statiNascita}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:errors path="statoNascita.id" class="testoRosso" />
						</td>						
						<td><form:label path="comuneNascita.id" cssClass="required"><spring:message code="comune"/></form:label></td>
					    <td>
							<div class="ui-widget">		
								<form:input id="id_descrizioneComuneNascita" path="comuneNascita.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComuneNascita"></div>
								<form:hidden id="id_hiddenComuneNascita" path="comuneNascita.id"/>
								<form:errors path="comuneNascita.descrizione" class="testoRosso" />
							</div>
					    </td>	
					</tr>
					<tr>
						<td><form:label path="sesso" cssClass="required"><spring:message code="nuovosoggettofisico.sesso" /></form:label></td>
						<td><form:select path="sesso" size="1">
								<form:option value="-1" label="---Seleziona Sesso---"/>
								<form:option value="M" label="Maschio" />
								<form:option value="F" label="Femmina" />
							</form:select> <form:errors path="sesso" class="testoRosso" />
						</td>
						<td><form:label path="codiceFiscale" cssClass="required"><spring:message code="codiceFiscale" /></form:label></td>
						<td><form:input path="codiceFiscale" maxlength="16" size="25"/>
							<form:errors path="codiceFiscale" class="testoRosso" />
						</td>
					</tr>
					
				</table>
			</c:if>
			<%-- FINE PERSONA FISICA --%>
			
			<%-- INIZIO PERSONA GIURIDICA --%>
			 <c:if test="${form.tipoSoggetto=='G'}" >
			 	<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="cameralini.terzoInterv.personaGiuridica" /></td>
					</tr>
					<tr>
						<td><form:label path="ragioneSociale" cssClass="required"><spring:message code="soggettogiuridico.ragioneSociale" /></form:label></td>
						<td><form:input path="ragioneSociale"  maxlength="120" size="40"/> 
							<form:errors path="ragioneSociale" class="testoRosso"/></td>
						<td><form:label path="partitaIva" cssClass="required"><spring:message code="soggettogiuridico.partitaIva" /></form:label></td>
						<td>
							<form:select id="id_StatoSiglaPiva" path="partitaIvaSiglaStato" size="1" >
								<form:options items="${statiPiva}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							
							<form:input path="partitaIva" maxlength="12" size="18"/>
							<form:errors path="partitaIva" class="testoRosso"/>
						</td>
					</tr>

					<tr>
						<td><form:label path="tipoSocieta" cssClass="required">
								<spring:message code="soggettogiuridico.tipoSocieta" />
							</form:label></td>
						<td>
							<form:select path="idTipoSocieta" size="1">
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${tipiSocieta}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:errors path="idTipoSocieta" class="testoRosso"/>
						</td>
						<td><form:label path="numeroCCIAA">
								<spring:message code="soggettogiuridico.numeroCCIAA" /></form:label></td>
						<td><form:input path="numeroCCIAA"  maxlength="30" size="30"/>
						</td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettogiuridico.titolo1" /></td>
					</tr>
					<tr>
						<td><form:label path="statoSedeLegale.id" cssClass="required"><spring:message code="stato" /></form:label></td>
						<td>
							<form:select id="id_StatoSedeLegale"  path="statoSedeLegale.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneSedeLegale')" >
								<form:option value="-1" label="---Seleziona Stato---"/>
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							<form:errors path="statoSedeLegale.id" class="testoRosso"/>
							</form:select>
						</td>		
						<td><form:label path="comuneSedeLegale.id" cssClass="required"><spring:message code="comune"/></form:label></td>
			        	<td>
							<div class="ui-widget">		
								<form:input id="id_descrizioneComuneSedeLegale" path="comuneSedeLegale.descrizione" size="40" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComune"></div>
							    <form:errors path="comuneSedeLegale.descrizione" class="testoRosso"/>
								<form:hidden id="id_hiddenComuneSedeLegale" path="comuneSedeLegale.id"/>
							</div>
			        	</td>	
					</tr>

					<tr>
						<td><form:label path="indirizzoSedeLegale.toponimo" ><spring:message code="indirizzo" /></form:label></td>
						<td>						
							<form:select path="indirizzoSedeLegale.toponimo.id" size="1">	
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:input path="indirizzoSedeLegale.denominazione" maxlength="50" size="40"/>
						</td>
						<td><form:label path="indirizzoSedeLegale.civico">
								<spring:message code="civico" />
							</form:label></td>
						<td colspan="2"><form:input path="indirizzoSedeLegale.civico" maxlength="20" size="20"/></td>
					</tr>
					
				</table>
			 </c:if>
			 <%-- FINE PERSONA GIURIDICA --%>
			 
			<br /><br />
				<table class="tb_bottoni" align="center">
					<tr>
                      	<td class="td_tasto"><input type="submit" value="Registra" name="Registra" title="Registra" /></td>
                      	<td class="td_tasto"><input type="submit" value="Indietro" name="IndietroNuovo" title="Indietro" /></td>
					</tr>
				</table>
			<br/>

<c:if test="${form.tipoSoggetto=='F'}" >
	<script type="text/javascript">
			$(document).ready(function() {
				prendiData("#dataNascita",true);
			});
	</script>
	
	<script type="text/javascript">
		if($( "#id_StatoNascita" ).val() == 1)
		{
			doAutocomplete('ComuneNascita');
		}
	</script>
</c:if>

<c:if test="${form.tipoSoggetto=='G'}" >
	<script type="text/javascript">
		if($( "#id_StatoSedeLegale" ).val() == 1)
		{
			doAutocomplete('ComuneSedeLegale');
		}
	</script>
</c:if>
			</form:form>
			
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
<smp:back buttonId="backButton"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>