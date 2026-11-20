<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="it.giustizia.smp.form.RiunioneForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
		<spring:url var="azione" value="/procedimento/riunione/gestioneRiunione" />
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="riunioneForm" enctype="multipart/form-data">
			<form:hidden path="idProcedimento" id="idProcedimento"/>
			<form:hidden path="idUfficio" id="idUffOwner"/>
			
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<%--
			<jsp:include page="/WEB-INF/jsp/procedimento/apri/intestazioneProcedimento.jsp" />
			 --%>
			 <br />
			<logic:notPresent name="messaggioProcedura">			
			<jsp:include page="/WEB-INF/jsp/procedimento/apri/soggettiInteressatiEStralciati.jsp" />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="6"><spring:message code="riunione.gestioneRiunione" /></td>
					</tr>
					<tr>
						<td>
							<form:label path="riunioneAnno">
								<spring:message code="riunione.riunioneAlNumero" />
							</form:label>
						</td>
						<td>
							<form:label path="riunioneAnno" cssClass="required">
								<spring:message code="riunione.riunioneAnno" />
							</form:label>
						</td>
						<td><form:input path="riunioneAnno" maxlength="4" size="5" />
							<form:errors path="riunioneAnno" class="testoRosso" />
						</td>
						<td>
							<form:label path="riunioneNumero" cssClass="required">
								<spring:message code="riunione.riunioneNumero" />
							</form:label>
						</td>
						<td><form:input path="riunioneNumero" maxlength="12" size="13" />
							<form:errors path="riunioneNumero" class="testoRosso" />
						</td>
					</tr>
				</table>
				<br/>
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="button" value="Salva" onclick="submitRiunione('ricercaSoggettiRiunente');" title="Salva" class="nowait" /></td> 
						
					</tr>
				</table>
				<br/>
			</logic:notPresent>
			<logic:notEmpty name="direttiInteressatiRiunente">
					<form:hidden path="idProcedimentoRiunente" id="idProcedimentoRiunente"/>
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettidelprocedimento" /></td>
						</tr>
					</table>
					<display:table class="tb_lista" uid="direttiInteressati" name="${direttiInteressatiRiunente}" pagesize="10" requestURI="${azione}">
						<display:column style="text-align: center;">
								<input type="radio" name="radioIDS" id="radioID_${direttiInteressatiRiunente_rowNum}" onclick="selectID('${direttiInteressati.idSoggetto}', '${direttiInteressati.idVersione}', '${direttiInteressati.tipoSoggetto}');" />
							</display:column>
						<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" />
						<display:column property="descrRuolo" title="Ruolo" sortable="true" />
						<display:column property="esisteInProcedeimentoRiunito" title="Presente in riunito" sortable="true" />
					</display:table>
					
					<form:hidden path="idSoggetto" id="idSoggetto"/>
					<form:hidden path="idVersioneSoggetto" id="idVersioneSoggetto"/>
					<form:hidden path="tipoSoggetto" id="tipoSoggetto" />	
					
					<br /><br />
									
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="dataRiunione" cssClass="required" ><spring:message code="riunione.dataRiunione" /></form:label></td>
			        	<td colspan="3">		        		
			        		<form:input path="dataRiunione" maxlength="10" id="id_dataRiunione"/>
			        		<form:errors path="dataRiunione" class="testoRosso"/>			        		
			        	</td>
					</tr>
					<tr>
						<td><form:label path="magistrato.id" >
								<spring:message code="stralcio.magistrato" />
							</form:label>
						</td>
						
						<td colspan="3"><form:select id="magistrato" path="magistrato.id" size="1" cssStyle="width: 200px;">
								<form:option value="-1" label="------Seleziona magistrato--------------" />
								<form:options items="${listaMagistrati}" itemValue="id" itemLabel="descrizione" />
							</form:select>
							<form:errors path="magistrato" class="testoRosso" />
						</td>
					</tr>
	 
				</table>
				<br />	
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegatoRiunione.jsp" />			
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
					
					    <td class="td_tasto"><input type="button"  value="Salva" class="nowait" onclick="inserisciAtto('SalvaRiunione', '${riunioneForm.allegato.documentoFirmato}');"></td>	
						<input id="SalvaRiunione" type="submit" name="SalvaRiunione" value="" style="display: none" />
					
					    <td class="td_tasto"><input type="button" name="EsecuzioneRiunione" title="Conferma" value="Conferma" class="nowait" onclick="submitEsecuzioneRiunione('ConfermaEsecuzioneRiunione');inserisciAtto('idSubmitRiunione', '${riunioneForm.allegato.documentoFirmato}');"/></td>
						<td class="td_tasto"><input type="button" value="Dettaglio" name="Dettaglio" onclick="submitRiunione('dettaglioSoggetto');" title="Visualizza Dettaglio Soggetto" class="nowait" /></td> 
						
					</tr>
					
				</table>
				<br />
			</logic:notEmpty>
			<input	id="idSubmitRiunione" type="submit" name="" value="" style="display: none" />
				
		</form:form>

		
		<script type="text/javascript">

			function submitRiunione(name){
				$('#idSubmitRiunione').attr('name', name);
				$('#idSubmitRiunione').click();
			}

			function submitEsecuzioneRiunione(name){
				$('#idSubmitRiunione').attr('name', name);
				//il click lo fa in inserisciAtto
			}

			$(document).ready(function() {
				prendiData("#id_dataRiunione",true);
				selectID('', '', '');
			});

			function selectID(idSoggetto, idVersione, tipoSoggetto) {
				$("#idSoggetto").val(idSoggetto);
				$("#idVersioneSoggetto").val(idVersione);
				$("#tipoSoggetto").val(tipoSoggetto);
			}
		</script>

	</tiles:putAttribute>
</tiles:insertDefinition>