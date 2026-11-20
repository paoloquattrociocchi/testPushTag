<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="it.giustizia.smp.form.ComboForm"%>
<%@page import="java.util.List"%>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
	
		<spring:url var="azione" value="/amministrazione/ricercaProcedimento.htm" />
		<form:form method="post" action="${azione}" commandName="procedimento">
			<p class="titolo_page">
				<spring:message code="procedimento.ricerca" />							
			</p>
			
				<c:if test="${profilo.ruolo=='MAGISTRATO'}">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4">
					<spring:message code="procedimento.tipoFascicolo" />

					</td>
				</tr>
				<tr>
					<td ><form:label path="tipoFascicolo.id">
							<spring:message code="procedimento.tipoFascicolo" />
						</form:label></td>
					<td><form:select path="tipoFascicolo.id" size="1" onchange="submit()">
							
							<form:options items="${tipoDiFascicolo}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				
					<td ><form:label path="tipoProcedimento.id">
							<spring:message code="procedimento.tipo" />
						</form:label></td>
					<td><form:select path="tipoProcedimento.id" size="1">
							<form:option value="-1" label="--- Seleziona Tipo ---" />
							<form:options items="${tipiProcedimento}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				
					
					
				</tr>
				</table>
				</c:if>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				
				<tr>
					<td class="td_titolo" colspan="6">
					<spring:message code="procedimento.puntuale" />

					</td>
				</tr>	
				<!-- 
			    <tr>
			    	<td>
					    <form:label path="tipoUffRic">
					    	<spring:message code="procedimento.tipoUfficio" />
				    	</form:label>
					</td>	
					<td colspan="3">
						<form:select path="tipoUffRic.id" size="1">
							<form:options items="${tipiUfficioRicerca}" itemValue="id" itemLabel="descrizione" />
						</form:select>
					</td>
				</tr>	
				 -->
					<tr>
						<td><form:label path="anno" cssClass="required"><spring:message code="procedimento.anno" /></form:label></td>
						<td><form:input path="anno" id="anno" maxlength="4" size="5"/>
							<form:errors path="anno" class="testoRosso"/></td>
							
						<td><form:label path="numero" cssClass="required"><spring:message code="procedimento.numero" /></form:label></td>
						<td><form:input path="numero" maxlength="12"/>
							<form:errors path="numero" class="testoRosso"/></td>
							
						<td><form:label path="idUffRic" cssClass="required"><spring:message code="procedimento.lista.uffici" /></form:label></td>
						<td>
						
							<c:if test="${sessionScope.TIPO_FASCICOLO==8}">
								<form:select path="idUffRic.id" size="1" disabled="true">
									<form:options items="${listaUffici}" itemValue="id" itemLabel="descrizione" />
								</form:select>
							</c:if>
							<c:if test="${sessionScope.TIPO_FASCICOLO!=8}">
								<form:select path="idUffRic.id" size="1">
									<form:options items="${listaUffici}" itemValue="id" itemLabel="descrizione" />
								</form:select>
							</c:if>

						</td>	
			    </tr>
			    
			    
			    
	        </table>

			<br />
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">		
				<tr>
					<td class="td_titolo" colspan="6">
					   <spring:message code="procedimento.criteri" />
			   
					</td>
				</tr>
				<tr>
					<td style="border-bottom: none;">&nbsp;</td>
				</tr>
				<tr>
				
					
					<td><form:label path="annoCriterio">
							<spring:message code="procedimento.anno" />
						</form:label></td>
					<td><form:input path="annoCriterio" id="annoCriterio" maxlength="4" size="5" /> <form:errors path="annoCriterio" class="testoRosso" /></td>
					<td><form:label path="numeroMin">
							<spring:message code="procedimento.numeroMin" />
						</form:label> <form:input path="numeroMin" maxlength="12" /> <form:errors path="numeroMin" class="testoRosso" /></td>
					<td><form:label path="numeroMax">
							<spring:message code="procedimento.numeroMax" />
						</form:label> <form:input path="numeroMax" maxlength="12" /> <form:errors path="numeroMax" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="dataIscrizioneMin">
							<spring:message code="procedimento.dataIscrizione" />
						</form:label></td>
					<td><form:label path="dataIscrizioneMin">
							<spring:message code="procedimento.dataIscrizioneMin" />
						</form:label> <form:input path="dataIscrizioneMin" id="dataIscrizioneMin" maxlength="10" size="12" /> <form:errors path="dataIscrizioneMin" class="testoRosso" /></td>
					<td colspan="2"><form:label path="dataIscrizioneMax">
							<spring:message code="procedimento.dataIscrizioneMax" />
						</form:label> <form:input path="dataIscrizioneMax" id="dataIscrizioneMax" maxlength="10" size="12" /> <form:errors path="dataIscrizioneMax" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="statoProcedimento.id">
							<spring:message code="procedimento.statoProcedimento" />
						</form:label></td>
					<td colspan="3"><form:select path="statoProcedimento.id" size="1">
							<form:option value="-1" label="--- Seleziona Stato ---" />
							<form:options items="${statiProcedimento}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>
				<tr>
					<td rowspan="2"><form:label path="cognomeSoggetto">
							<spring:message code="procedimento.soggetto" />
						</form:label></td>
					<td><form:label path="cognomeSoggetto">
							<spring:message code="procedimento.cognomeSoggetto" />
						</form:label> <form:input path="cognomeSoggetto" maxlength="35" /> <form:errors path="cognomeSoggetto" class="testoRosso" /></td>
					<td colspan="2"><form:label path="nomeSoggetto" >
							<spring:message code="procedimento.nomeSoggetto" />
						</form:label> <form:input path="nomeSoggetto" maxlength="35"/> <form:errors path="nomeSoggetto" class="testoRosso" /></td>
				</tr>
				<tr>
					<td colspan="3"><form:label path="ragioneSociale">
							<spring:message code="soggettogiuridico.ragioneSociale" />
						</form:label> <form:input path="ragioneSociale" /> <form:errors path="ragioneSociale" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="pmAssegnatario.id">
							<spring:message code="procedimento.pmAssegnatario" />
						</form:label></td>
					<td colspan="3"><form:select path="pmAssegnatario.id" size="1">
							<form:option value="-1" label="--- Seleziona Magistrato ---" />
							<form:options items="${pmAssegnatari}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>
				<c:if test="${profilo.ruolo!='MAGISTRATO'}">
				<c:if test="${fn:length(tipoDiFascicolo) > 1}">
				
				<tr>
					<td><form:label path="tipoFascicolo.id">
							<spring:message code="procedimento.tipoFascicolo" />
						</form:label></td>
					<td colspan="3"><form:select path="tipoFascicolo.id" size="1" onchange="submit()">
							<form:option value="-1" label="--- Seleziona Tipo ---" />
							<form:options items="${tipoDiFascicolo}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>
				</c:if>
				<tr>
					<td><form:label path="tipoProcedimento.id">
							<spring:message code="procedimento.tipo" />
						</form:label></td>
					<td colspan="3"><form:select path="tipoProcedimento.id" size="1">
							<form:option value="-1" label="--- Seleziona Tipo ---" />
							<form:options items="${tipiProcedimento}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>
				</c:if>
				
				
			
				<tr>
					<td><form:label path="annoDecretoDecisorio">
							<spring:message code="procedimento.decretoDecisorio" />
						</form:label></td>
					<td><form:label path="annoDecretoDecisorio">
							<spring:message code="procedimento.anno" />
						</form:label> <form:input path="annoDecretoDecisorio" /> <form:errors path="annoDecretoDecisorio" class="testoRosso" /></td>
					<td colspan="2"><form:label path="numeroDecretoDecisorio">
							<spring:message code="procedimento.numero" />
						</form:label> <form:input path="numeroDecretoDecisorio" /> <form:errors path="numeroDecretoDecisorio" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="tipoProcedente.id">
							<spring:message code="procedimento.tipoProcedente" />
						</form:label></td>
					<td colspan="3"><form:select path="tipoProcedente.id" size="1" onchange="submit()">
							<form:option value="-1" label="--- Seleziona Tipo ---" />
							<form:options items="${tipiProcedente}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>
			
				<tr>
					<td><form:label path="procedente.id">
							<spring:message code="procedimento.procedente" />
						</form:label></td>
					<td colspan="3"><form:select path="procedente.id" size="1">
							<c:if test="${procedimento.tipoProcedente.id!=0}">
								<form:option value="-1" label="--- Seleziona Procedente ---" />
							</c:if>
							<form:options items="${procedenti}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>

				<tr>
					<td rowspan="4"><form:label path="procedente">
							<spring:message code="procedimento.ipotesiMisura" />
						</form:label></td>
				<tr>
					<td colspan="3"><form:checkbox path="antimafia" /> <form:label path="antimafia">
							<spring:message code="procedimento.antimafia" />
						</form:label></td>
				</tr>
				<tr>
					<td colspan="3"><form:checkbox path="antiterrorismo" /> <form:label path="antiterrorismo">
							<spring:message code="procedimento.antiterrorismo" />
						</form:label></td>
				</tr>
				<tr>
					<td colspan="3"><form:checkbox path="ordinaria" /> <form:label path="ordinaria">
							<spring:message code="procedimento.ordinaria" />
						</form:label></td>
				</tr>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" name="Ricerca" title="Ricerca procedimento" value="Ricerca" /></td>

				</tr>
			</table>
			<br />
			<br />

			<logic:present name="risultati">

				<display:table id="row" name="${risultati}" class="tb_lista" uid="risultati" pagesize="10" requestURI="${azione}">
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:radiobutton path="idProcedimento" value="${risultati.idProcedimento}" />
					</display:column>
					<display:column property="annoNumeroProcedimento" sortable="false" title="Procedimento" />
					<display:column property="dataApertura" sortable="false" title="Data Iscrizione" format="{0,date,dd/MM/yyyy}" />
					<display:column property="statoProcedimento.descrizione" sortable="false" title="Stato" />
					
					<c:if test="${sessionScope.profilo.idTipoUfficio==17}">
						<display:column property="NUM_TRIBUNALE" sortable="false" title="Proc.Tribunale" />
						<display:column property="NUM_CDA" sortable="false" title="Proc.CdA" />
						<display:column property="NUM_PG" sortable="false" title="Proc.PG" />
					</c:if>
					<c:if test="${sessionScope.profilo.idTipoUfficio==8}">					
						<display:column property="NUM_PROCURA" sortable="false" title="Proc.Procura" />
						<display:column property="NUM_TRIBUNALE" sortable="false" title="Proc.Tribunale" />
						<display:column property="NUM_CDA" sortable="false" title="Proc.CdA" />						
					</c:if>
					<c:if test="${sessionScope.profilo.idTipoUfficio==19}">
						<display:column property="NUM_PROCURA" sortable="false" title="Proc.Procura" />
						<display:column property="NUM_CDA" sortable="false" title="Proc.CdA" />											
						<display:column property="NUM_PG" sortable="false" title="Proc.PG" />
					</c:if>
					<c:if test="${sessionScope.profilo.idTipoUfficio==6}">
						<display:column property="NUM_PROCURA" sortable="false" title="Proc.Procura" />
						<display:column property="NUM_TRIBUNALE" sortable="false" title="Proc.Tribunale" />											
						<display:column property="NUM_PG" sortable="false" title="Proc.PG" />
					</c:if>
				</display:table>
				<br />
				<br />

			</logic:present>

			<logic:notEmpty name="risultati">
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" name="Dettaglio" title="Dettaglio" value="Dettaglio" /></td>
					</tr>
				</table>
				<br />
				<br />
			</logic:notEmpty>
		</form:form>

		<script type="text/javascript">
			$(document).ready(function() {
				prendiData("#dataIscrizioneMin",false);
				prendiData("#dataIscrizioneMax",false);
			});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>