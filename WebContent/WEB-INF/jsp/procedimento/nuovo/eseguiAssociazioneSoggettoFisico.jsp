<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/nuovo/gestioneAssociaSoggetto.htm" />
		<form:form method="post" action="${azione}" commandName="procedimentoForm">
		
			<p class="titolo_page"><spring:message code="soggetto.soggettidelprocedimento" /></p>
			<logic:present name="procedimentoForm.idProcedimento">
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			</logic:present>
			<br />
			<display:table class="tb_lista" uid="sogg" name="${soggettiTemp}" pagesize="10" 
					requestURI="${azione}?ord=true" >
				<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<input type="radio" name="id" value="<c:out value="${sogg.idSoggetto}" />" onclick="document.getElementById('idChk').value='${sogg_rowNum}'">
				</display:column>
				<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" />
			</display:table>
			<input type="hidden" id="idChk" name="idChk"/>
			<br/><br/>
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="button" value="Elimina" onclick="eliminaSoggetto();"></td>
					<td class="td_tasto"><input type="submit" value="Aggiungi soggetto Fisico" name="AggiungiSoggettoFisico"></td>
					<td class="td_tasto"><input type="submit" value="Aggiungi soggetto Giuridico"name="AggiungiSoggettoGiuridico" /></td>
				</tr>
			</table>
			<br />
			<table class="tb_form">
				<tr>
					<td width="15%"><form:label path="procedente" cssClass="required"><spring:message code="procedimento.procedente" /></form:label></td>
					<td>
						<form:select path="procedente.id" size="1">
							<c:if test="${procedenti!=null && fn:length(procedenti) gt 1}">
								<form:option value="-1" label="------Seleziona Procedente-------" />
							</c:if>								
							<form:options items="${procedenti}" itemValue="id" itemLabel="descrizione" /> 
						</form:select>
						<form:errors path="procedente.id" class="testoRosso"/>
					</td>
				</tr>
				<logic:equal name="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE%>" scope="session" value="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE_SMP%>" >
				<tr>
					<td><form:label path="tipoProcedimento.id"><spring:message code="procedimento.ipotesiMisura" /></form:label></td>
					<td>
						<c:forEach var="tipoProc" items="${tipiProcedimento}">
							<c:if test="${tipoProc.id == procedimentoForm.tipoProcedimento.id}">
								<c:out value="${tipoProc.descrizione}"> </c:out>
							</c:if>
						</c:forEach>
					</td>
				</tr>
				<form:hidden path="tipoProcedimento.id" />
				<tr>
					<td><form:label path="tipologiaPrevenzione"><spring:message code="procedimento.tipologiaPrevenzione" /></form:label></td>
					<td colspan="3">
						<c:if test="${procedimentoForm.antimafia==true}">
							<input type="checkbox" disabled="disabled" checked="checked" />
						</c:if>
						<c:if test="${procedimentoForm.antimafia==false}">
							<input type="checkbox" disabled="disabled" />
						</c:if>
						<form:hidden path="antimafia" />
						<form:label path="antimafia"><spring:message code="procedimento.antimafia"/></form:label>
					
						<c:if test="${procedimentoForm.antiterrorismo==true}">
							<input type="checkbox" disabled="disabled" checked="checked" />
						</c:if>
						<c:if test="${procedimentoForm.antiterrorismo==false}">
							<input type="checkbox" disabled="disabled" />
						</c:if>							
						<form:hidden path="antiterrorismo" />
						<form:label path="antiterrorismo"><spring:message code="procedimento.antiterrorismo" /></form:label>
					
						<c:if test="${procedimentoForm.ordinaria==true}">
							<input type="checkbox" disabled="disabled" checked="checked" />
						</c:if>
						<c:if test="${procedimentoForm.ordinaria==false}">
							<input type="checkbox" disabled="disabled" />
						</c:if>		
						<form:hidden path="ordinaria" />
						<form:label path="ordinaria"><spring:message code="procedimento.ordinaria" /></form:label>
					</td>
				</tr>
				</logic:equal> 
			</table>
			<br /><br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" name="RegistraProcedimento" title="Registra il procedimento" value="Registra Procedimento"></td>
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
			<br /><br />
		</form:form>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
		<smp:back buttonId="backButton"></smp:back>
		
		
		<script type="text/javascript">
			$(document).ready(function() {
				prendiData("#dataEmissioneAccertamento",true);
				prendiData("#dataRicezioneProposta",true);
				prendiData("#dataRicezioneAccertamento",true);
			});
		</script>
		<script type="text/javascript">
			function eliminaSoggetto() {
				if(document.getElementById("idChk").value != null) {
					location.href='${azione}?elimID='+document.getElementById("idChk").value;
					document.getElementById("idChk").value = null;
				}
			}				
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
