<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="it.giustizia.smp.form.ProcedimentoForm"%>
<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
	  									
		<spring:url var="azione" value="/procedimento/notifiche/inviaNotifica.htm" />
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			<form:hidden id="id_hidden_Atto" path="codAtto" />
			<form:hidden path="codAtto" />
			<form:hidden path="tipoAtto.id" />
			<form:hidden path="statoAtto.id" />
			<input type="hidden" id="id_selectAll_listaIdCheckboxDestinatari" value="true" />
			<p class="titolo_page">
				<spring:message code="notifiche.invianotifiche.titolo" />
			</p>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<logic:notPresent name="inSelezionaAltroDestinatario">
				<!-- ************************************  DATI DELLA NOTIFICA ************************************  -->
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="notifiche.titolo.datinotifica" /></td>
					</tr>
					<tr>				
						<td><form:label path="oggetto" cssClass="required">
								<spring:message code="notifiche.invianotifiche.oggetto" />
							</form:label></td>
						<td colspan="3"><form:input path="oggetto" maxlength="1000"  cssStyle="width:98%" id="oggetto" /><br/><form:errors path="oggetto" class="testoRosso" /></td>
					</tr>
					<tr>
						<td><form:label path="testo" cssClass="required">
								<spring:message code="notifiche.invianotifiche.evento" />
							</form:label></td>
						<td colspan="3">
								<form:textarea id="testo" path="testo" rows="2" cssStyle="width:98%" /><br/> <form:errors path="testo" class="testoRosso" />
						</td>
					</tr>
					<tr>
						<td><form:label path="flgDatiSensibili" >
								<spring:message code="notifiche.invianotifiche.datiSensibili" />
							</form:label></td>
						<td colspan="3"><form:checkbox id="flgDatiSensibili"  path="flgDatiSensibili" />  <form:errors path="flgDatiSensibili" class="testoRosso" /></td>
					</tr>
					<c:if test="${attoForm.hasAmministratori}">
						<tr>
							<td><form:label path="flgAllegaDatiToolAdmin" >
									<spring:message code="notifiche.invianotifiche.flgAllegaDatiToolAdmin" />
								</form:label></td>
							<td colspan="3"><form:checkbox id="flgAllegaDatiToolAdmin"  path="flgAllegaDatiToolAdmin" />
							<form:errors path="flgAllegaDatiToolAdmin" class="testoRosso" /></td>
					</tr>		
					</c:if>			
					<tr>
						<!-- copiato da proposta -->
						<td colspan="4"><jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegatoReadOnly.jsp" /></td>
					</tr>
					 <tr>
						<td colspan="4">
							<!-- copiato da copertinaDecretoOpposizioneDissequestro -->				 		
							<display:table class="tb_lista" uid="destinatario" name="${attoForm.listaDestinatari}" pagesize="10">
								
								<display:column paramId="CODI_DEST" paramProperty="listaIdCheckboxDestinatari" style="width:7%" title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTuttoSkipDisable('destinatario','listaIdCheckboxDestinatari');\" title=\"Seleziona/Deseleziona tutti i destinatari\"></img>&nbsp;Sel.Dest." >
									<form:checkbox path="listaIdCheckboxDestinatari" id="CHK_${destinatario.CODI_DEST}" value="CHK_${destinatario.CODI_DEST}" disabled="${destinatario.FLG_SELEZIONABILE!='S'}" title="${(destinatario.FLG_SELEZIONABILE!='S'?'Non abilitato perchè manca il codice per identificazione da passare al sistema GLMP':'')}" />
								</display:column>
								<display:column property="DESC_RUOLO_DEST_SITMP" title="Tipo Destinatario" sortable="false" style="width:20%"  />
								<display:column property="DESC_DEST" title="Descrizione Destinatario" sortable="false" />
							</display:table>
						</td>
					</tr>
				</table>				
				<br/><br/>
				<table class="tb_bottoni" align="center">
					<tr>
						<logic:notPresent name="isReadOnly">
							<td class="td_tasto">
								 <input type="submit" id="altroDestinatario" name="altroDestinatario" value="Aggiungi altro Destinatario" title="Altro Destinatario">
							</td>
							<td class="td_tasto">
								 <input type="submit" id="inviaButton" name="inviaButton"  value="Invia Notifica" title="Invia">
							</td>
						</logic:notPresent>
						<td class="td_tasto">
							 <input type="button" id="backButton" value="Indietro" title="Indietro">
						</td>
						
					</tr>
				</table>
			</logic:notPresent>
			<logic:present name="inSelezionaAltroDestinatario">
				<!-- ************************************  SELEZIONE ALTRO DESTINATARIO ************************************  -->
				<form:hidden path="oggetto" />
				<form:hidden path="testo" />
				<form:hidden path="flgDatiSensibili" />
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="notifiche.titolo.altrodestinatario" /></td>
					</tr>
						<td><form:label path="tipoDestinatario.id">
									<spring:message code="notifiche.invio.tipodestinatario" />
							</form:label>
						</td>
						<td><form:select path="tipoDestinatario.id" size="1" cssStyle="width:300px" onchange="changeComboManager()">
								<form:option value="-1" label="--- Tipologia Ufficio ---"/>
								<form:options  items="${tipiDestinatario}"   itemValue="id" itemLabel="descrizione"  />
							</form:select>
						</td>
					</tr>
					</tr>
						<td><form:label path="provinciaDestinatario.id">
									<spring:message code="notifiche.invio.provincia" />
							</form:label>
						</td>
						<td><form:select path="provinciaDestinatario.id" size="1" cssStyle="width:300px" onchange="changeComboManager()">
								<form:option value="-1" label="--- Provincia ---"/>
								<form:options  items="${province}"   itemValue="id" itemLabel="descrizione"  />
							</form:select>
						</td>
					</tr>
					</tr>
						<td><form:label path="comuneDestinatario.id">
									<spring:message code="notifiche.invio.comune" />
							</form:label>
						</td>
						<td><form:select path="comuneDestinatario.id" size="1" cssStyle="width:300px" onchange="changeComboManager()">
								<form:option value="-1" label="--- Comune ---"/>
								<form:options  items="${comuni}"   itemValue="id" itemLabel="descrizione"  />
							</form:select>
						</td>
					</tr>
					</tr>
					<td><form:label path="ufficioDestinatario.id">
									<spring:message code="notifiche.invio.destinatario" />
							</form:label>
						</td>
						<td><form:select path="ufficioDestinatario.id" size="1" cssStyle="width:300px" onchange="changeComboManager()">
								<form:option value="-1" label="--- Ufficio ---"/>
								<form:options  items="${uffici}"   itemValue="id" itemLabel="descrizione"  />
							</form:select>
						</td>
					</tr>
				</table>
				<br/><br/>
				<table class="tb_bottoni" align="center">
				<tr>
						<td class="td_tasto">
						 	<input type="submit" id="confermaAltroDestinatario" name="confermaAltroDestinatario" value="Conferma Altro Destinatario" title="Conferma inserimento altro destinatario">
						</td>
						<td class="td_tasto">
						 	<input type="submit" id="annullaAltroDestinatario" name="annullaAltroDestinatario" value="Annulla" title="Annulla inserimento altro destinatario">
						</td>
					</tr>
				</table>
			</logic:present>
			<br />
			<br />
		</form:form>
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
 		<script  type="text/javascript">
 			function changeComboManager() {
 				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/notifiche/gestisciCambioCombo.htm");
				$("#id_formConfermaInvia").submit();
				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/notifiche/inviaNotifica.htm");
			}
 		</script>
	 	<img src="${pageContext.request.contextPath}/images/arrow_d.png" width="0" height="0"/>
	</tiles:putAttribute>
</tiles:insertDefinition>



