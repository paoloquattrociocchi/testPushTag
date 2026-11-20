<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="atto" enctype="multipart/form-data">
				<form:hidden id="id_hidden_Atto" path="codAtto" />
				<form:hidden path="tipoAtto.id" />
				<form:hidden path="statoAtto.id" />
					<p class="titolo_page">
						<spring:message code="atto.archiviazione" />
					</p>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				<br />
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="atto.archiviazione" /></td>
					</tr>
					<tr>					
						<td><form:label path="categoriaAtto.id" cssClass="required">
								<spring:message code="atto.archiviazione.categoria" />
							</form:label></td>	
					<td>
					<form:select class="nonModificabile" id="id_fonte" path="categoriaAtto.id" size="1"   disabled="true" >
							<form:option value="-1" label="-------------------------Seleziona Motivazione-----------------------" />
							<form:options items="${categoria}" itemValue="id" itemLabel="descrizione" />
					</form:select>
					<form:errors path="categoriaAtto.id" class="testoRosso" />					
					</td>							
					<!-- 							
						<td>
							<form:input class="nonModificabile" id="categoriaAtto" path="categoriaAtto.descrizione" maxlength="10" size="20"/> 
						</td>
 					-->						
					</tr>
					
					<tr>
						<td><form:label  path="dataInformativa" cssClass="required">
								<spring:message code="atto.dataarchiviazione" />
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

				<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegatoNuovo.jsp" />				

				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
					 <smp:notificheButtons formId="id_formConfermaInvia" codAtto="${attoForm.codAtto}"></smp:notificheButtons>
						
					 <c:if test="${profilo.ruolo!='MAGISTRATO'}">
						<logic:equal name="atto" property="codAtto" value="0">
								<td class="td_tasto"><input type="button" value="Conferma" onclick="inserisciAtto('ConfermaInserimento', '${atto.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" /></td> 
								<input	id="ConfermaInserimento" type="submit" name="ConfermaInserimento" value="" style="display: none" />
						</logic:equal>
						<logic:greaterThan name="atto" property="codAtto" value="0">
							
							
							<td class="td_tasto"><input type="button" value="Conferma" onclick="inserisciAtto('ConfermaModifica', '${atto.allegato.documentoFirmato}')" title="Conferma modifica atto" class="nowait" /></td> 
							<input	id="ConfermaModifica" type="submit" name="ConfermaModifica" value="" style="display: none" />
							
						</logic:greaterThan>
						</c:if>
						<td class="td_tasto">
							
							<%--
							<c:if test="${fromMenuAtti=='elenco'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
							</c:if>
							<c:if test="${fromMenuAtti=='ordine'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
							</c:if>
							<c:if test="${fromMenuAtti=='nuovo'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/nuovo.htm')">
							</c:if>
							 --%> 
							 <input type="button" id="backButton" value="Indietro" title="Indietro">
						</td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>


			
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