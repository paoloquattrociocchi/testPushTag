<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%@ page import="it.giustizia.smp.utility.Constants"%>

<c:set var="STATOATTO_REGISTRATO" value="<%=Constants.STATOATTO_REGISTRATO%>"/>

	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="paginazione" value="/procedimento/atto/paginazioneNuovaFonte.htm" />
			<spring:url var="azione" value="/procedimento/atto/inserimentoFonte.htm" />
			<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="atto" enctype="multipart/form-data">
				<form:hidden id="id_hidden_Atto" path="codAtto" />
				<form:hidden path="tipoAtto.id" />
				<form:hidden path="statoAtto.id" />
				<input type="hidden" id="id_selectAll_listaIdCheckbox_associati" value="true" />
				<input type="hidden" id="id_selectAll_listaIdCheckbox" value="true" />
				<logic:equal name="atto" property="codAtto" value="0">
					<p class="titolo_page">
						<spring:message code="atto.nuovafonte" />
					</p>
				</logic:equal>
				<logic:greaterThan name="atto" property="codAtto" value="0">
					<p class="titolo_page">
						<spring:message code="atto.modificafonte" />
					</p>
				</logic:greaterThan>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br />
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="atto.fonte" /></td>
					</tr>
					<tr>
						<td><form:label path="fonte.id" cssClass="required">
								<spring:message code="atto.fonte" />
							</form:label></td>
						<logic:equal name="atto" property="fonte.id" value="12">
							<td colspan="3">
						</logic:equal>
						<logic:notEqual name="atto" property="fonte.id" value="12">
							<td>
						</logic:notEqual>
						<form:select class="nonModificabile" id="id_fonte" path="fonte.id" size="1" onchange="pulisciCombo(); this.form.submit();">
							<form:option value="-1" label="-------------------------Seleziona Fonte-----------------------" />
							<form:options items="${fonti}" itemValue="id" itemLabel="descrizione" />
						</form:select>
						<form:errors path="fonte.id" class="testoRosso" />
						</td>
						<logic:notEqual name="atto" property="fonte.id" value="12">
							<td><form:label path="organo" cssClass="required">
									<spring:message code="atto.organo" />
								</form:label></td>
							<td><form:select class="nonModificabile" path="organo.id" size="1" onchange="pulisciCombo(); this.form.submit()">
									<form:option value="-1" label="-------------------------Seleziona Organo-----------------------" />
									<form:options items="${organi}" itemValue="id" itemLabel="descrizione" />
								</form:select> <form:errors path="organo.id" class="testoRosso" /></td>
					</tr>
					<tr>
						<td><form:label path="provinciaOrgano" cssClass="required">
								<spring:message code="provincia" />
							</form:label></td>
						<td><form:select class="nonModificabile" path="provinciaOrgano.id" size="1" onchange="pulisciComboIndirizzo(); this.form.submit()">
								<form:option value="-1" label="-------------------------Seleziona Provincia-----------------------" />
								<form:options items="${province}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:errors path="provinciaOrgano.id" class="testoRosso" /></td>
						<td><form:label path="comuneOrgano" cssClass="required">
								<spring:message code="comune" />
							</form:label></td>
						<td><form:select class="nonModificabile" path="comuneOrgano.id" size="1" onchange="this.form.submit()">
								<form:option value="-1" label="-------------------------Seleziona Comune-----------------------" />
								<form:options items="${comuni}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:errors path="comuneOrgano.id" class="testoRosso" /></td>
					</tr>
					<tr>
						<td><form:label path="indirizzoOrgano" >
								<spring:message code="indirizzo" />
							</form:label></td>
						<td colspan="3"><form:select class="nonModificabile" path="indirizzoOrgano.id" size="1" onchange="this.form.submit()">
						<!-- 	<form:option value="-1" label="-------------------------Seleziona Indirizzo-----------------------" />    -->	
								<form:options items="${indirizziSede}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:errors path="indirizzoOrgano.id" class="testoRosso" /></td>
						</logic:notEqual>
					</tr>
					<logic:equal name="atto" property="fonte.id" value="12">
						<tr>
							<td><form:label path="altroOrgano" cssClass="required"> 
									<spring:message code="atto.altroorgano" />
								</form:label></td>
							<td colspan="3"><form:input class="nonModificabile" id="id_altroOrgano" path="altroOrgano" maxlength="50" size="50" /> <form:errors path="altroOrgano" class="testoRosso" /></td>
						</tr>
					</logic:equal>
					<tr>
						<td><form:label path="numeroRiferimento">
								<spring:message code="atto.numeroriferimento" />
							</form:label></td>
						<td><form:input class="nonModificabile" path="numeroRiferimento" maxlength="20" size="25" /></td>
						<td><form:label  path="dataInformativa" cssClass="required">
								<spring:message code="atto.datainformativa" />
							</form:label></td>
						<td><form:input class="nonModificabile" id="dataInformativa" path="dataInformativa" maxlength="10" size="12"/> <form:errors path="dataInformativa" class="testoRosso" /> <form:hidden path="dataInformativa" /></td>
					</tr>
					<tr>
						<td><form:label path="note">
								<spring:message code="note" />
							</form:label></td>
						<td colspan="3"><form:textarea class="nonModificabile" id="id_note" path="note" rows="2" cssStyle="width:98%" /></td>
					</tr>
				</table>
				<br>
				<logic:greaterThan name="atto" property="codAtto" value="0">
					<table class="tb_form">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="soggetto.direttiinteressatiassociati" /></td>
						</tr>
					</table>
					<display:table class="tb_lista" uid="soggettiDelProcedimentoAssociati" name="${soggettiDelProcedimentoAssociati}"  requestURI="${azione}" > 				
							<display:column paramId="idSoggetto" 
										paramProperty="listaIdCheckbox_associati" style="width:5%"  title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('soggettiDelProcedimentoAssociati','listaIdCheckbox_associati');\" title=\"Seleziona/Deseleziona tutti i soggetti per rimuoverli dalla fonte corrente\"></img>&nbsp;Rimuovi" >					
							<form:checkbox id="id_checkbox_listaIdCheckbox_associati" path="listaIdCheckbox_associati" value="${soggettiDelProcedimentoAssociati.idSoggetto}" disabled="${disabledViewProc}"/>
						</display:column>
						<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" style="width: 50%"/>
					</display:table>
					<br></br>
				</logic:greaterThan>
				<br>
				
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.direttiinteressatinonassociati" /></td>
					</tr>
				</table>

				<display:table class="tb_lista" uid="soggettiDelProcedimentoNonAssociati" name="${soggettiDelProcedimentoNonAssociati}"  requestURI="${paginazione}" > 				 
						<display:column paramId="idSoggetto" 
									paramProperty="listaIdCheckbox" style="width:5%" title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('soggettiDelProcedimentoNonAssociati','listaIdCheckbox');\" title=\"Seleziona/Deseleziona tutti i soggetti per associarli alla fonte corrente\"></img>&nbsp;Associa" >					
							<form:checkbox path="listaIdCheckbox" value="${soggettiDelProcedimentoNonAssociati.idSoggetto}" disabled="${disabledViewProc}"/>
						</display:column>
						<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" style="width: 50%" />
				</display:table>		
			<br>

				<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegatoNuovo.jsp" />
				
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>
					<tr>
						<td width="15%"><form:label path="dataRicezione">
								<spring:message code="atto.dataricezione" />
							</form:label></td>
						<td colspan="3"><form:input id="dataRicezione" path="dataRicezione" maxlength="10" size="12" class="nonModificabile"/> <br>
						<form:errors path="dataRicezione" class="testoRosso" /></td>
					</tr>
				</table>

				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<c:if test="${atto.statoAtto.id != STATOATTO_REGISTRATO }">
								<td class="td_tasto"><input type="button" value="Conferma" onclick="inserisciAtto('ConfermaInserimento', '${atto.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" /></td> 
								<input	id="ConfermaInserimento" type="submit" name="ConfermaInserimento" value="" style="display: none" />
						</c:if>
						
						<td class="td_tasto">
							 <input type="button" id="backButton" value="Indietro" title="Indietro">
						</td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>


			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
			<smp:back buttonId="backButton"></smp:back>

			<script type="text/javascript">
			
			/*	
				function controllaSelezioneFonte(idFonte) {					
					if(idFonte == 12 ) {
						// selezionato 'Altro' come fonte
						document.getElementById('id_altroOrgano').disabled=false;
					}
					else {
						document.getElementById('id_altroOrgano').disabled=true;
						document.getElementById('id_altroOrgano').value='';
					}					
				}	
				*/	

				function pulisciCombo(){
					if(document.getElementsByName("provinciaOrgano.id").length != 0)
						document.getElementsByName("provinciaOrgano.id")[0].selectedIndex = -1;
					}

				function pulisciComboIndirizzo(){
					if(document.getElementsByName("indirizzoOrgano.id").length != 0)
						document.getElementsByName("indirizzoOrgano.id")[0].selectedIndex = -1;
					}
			

				$(document).ready(function() {
					if(${disabledViewProc!=true})
						if(${atto.statoAtto.id!=3})
							prendiData("#dataInformativa",true);
					if(${disabledViewProc!=true})
						prendiData("#dataRicezione",true);						
					//controllaSelezioneFonte($( "#id_fonte" ).val()) ;


				//elimino tutti i disable prima di fare submit
				
				$('#ConfermaModifica').bind('submit', function() {
			       // $(this).find(':disabled').removeAttr('disabled');
			        $(":disabled").removeAttr('disabled');
			    });					


				//gestione momentanea ...
				if(${atto.statoAtto.id==3}){
					 $(".nonModificabile").attr("disabled", true);
					}
				
			    //disabilitaCampiNuovaFonte('${disabledViewProc}', <c:out value="${atto.statoAtto.id}" default="0"/>);

				});

				
			</script>
			
		</tiles:putAttribute>
	</tiles:insertDefinition>