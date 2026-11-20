<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/notifiche/notifiche.htm" />
		<p class="titolo_page">
			<spring:message code="notificheEComunicazioni.elencoNotifiche.titolo" />
		</p>
		<form:form id="mainForm" method="post" action="${azione}" commandName="notificheEComunicazioniForm">
			<form:hidden id="hiddenIdNotifica"  path="idNotifica" />
			<form:hidden id="hiddenAnnoProcedimentoSel"  path="annoProcedimentoSel" />
			<form:hidden id="hiddenNumProcedimentoSel"  path="numProcedimentoSel" />
			<form:hidden id="hiddenCodAttoSel"  path="codAttoSel" />
			<br/>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="notificheEComunicazioni.elencoNotifiche.sottotitolo"/></td>
				</tr>
				<tr>
					<td width="18%"><spring:message code="notificheEComunicazioni.elencoNotifiche.dataDal" /></td>
					<td width="32%">
						<form:input path="filtroDataDal" maxlength="10" size="12" id="filtroDataDal"/>
					    <form:errors path="filtroDataDal" class="testoRosso"/>
					</td>
					<td width="18%"><spring:message code="notificheEComunicazioni.elencoNotifiche.dataAl" /></td>
					<td width="32%">
						<form:input path="filtroDataAl" maxlength="10" size="12" id="filtroDataAl"/>
					    <form:errors path="filtroDataAl" class="testoRosso"/>
					</td> 
				</tr>
				<tr>
					<td><spring:message code="notificheEComunicazioni.annoProcedimento" /></td>
						<td>
							<form:input path="annoProcedimento" id="anno" maxlength="4" size="5"/>
							<form:errors path="annoProcedimento" class="testoRosso"/>
						</td>
						<td><spring:message code="notificheEComunicazioni.numeroProcedimento" /></td>
						<td><form:input path="numProcedimento" maxlength="12"/>
							<form:errors path="numProcedimento" class="testoRosso"/>
						</td>
				</tr>
				
				<tr>
					<td><spring:message code="notificheEComunicazioni.elencoNotifiche.tipoDestinatario" /></td>
					<td colspan="3">
						<form:select  path="tipoDestinatario.id" size="1"  onchange="refreshLista(3)" >
							<form:option value="-1" label="- Seleziona -"/>
							<form:options items="${valoriComboTipoDestinatario}"  itemValue="id" itemLabel="descrizione" /> 																	
						</form:select>
					</td>	
				</tr>
			</table>
			<br/>
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto">
						<input type="submit" value="Ricerca" name="Ricerca">
					</td>
				</tr>
			</table>
			<br/>
			<display:table class="tb_lista" name="${listaNotifiche}" uid="listaNotifiche" pagesize="10" requestURI="${azione}">
				<display:column title="Seleziona" sortable="false"  style="text-align: center; width: 5%">
					<form:radiobutton path="idNotifica" value="${listaNotifiche.codiKIdNotifica}" onclick="impostaHidden('${listaNotifiche.codiKIdNotifica}',${listaNotifiche.numeAnnoProc},${listaNotifiche.numeProc},${listaNotifiche.codiFkIdAtto})" />
				</display:column>
				<display:column title="Numero Procedimento" sortable="false" style="text-align: left; width: 10%">
					${listaNotifiche.numeAnnoProc}/${listaNotifiche.numeProc}
				</display:column>
				<display:column property="descCatAtto" title="Tipo Atto" sortable="false" style="text-align: left; width: 30%">
				</display:column>
				
				<display:column title="Numero Atto" sortable="false" style="text-align: left; width: 10%">
					<c:choose>
				        <c:when test="${listaNotifiche.numeAnnoAtto != null}">	${listaNotifiche.numeAnnoAtto}/${listaNotifiche.numeAtto} </c:when>
				        <c:otherwise></c:otherwise>
				    </c:choose>
				</display:column>
				<display:column property="dataInvio" title="Data Invio" sortable="false" style="text-align: left; width: 10%" format="{0,date,dd/MM/yyyy}" />
				<display:column property="descDestinataro" title="Destinatario" sortable="false" style="text-align: left; width: 25%" />
				<display:column property="ricevute" title="Ricevute" sortable="false" style="text-align: left; width: 15%" />
				
			</display:table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto">
						<input type="submit" value="Vai al Procedimento" name="Dettaglio">
					</td>
					<td class="td_tasto">
						<input type="submit" value="Visualizza le notifiche dell'atto" name="ListaNotifiche">
					</td>
				</tr>
			</table>
		</form:form>
		<script type="text/javascript">
			$(document).ready(function() {
				prendiData("#filtroDataDal",true);
				prendiData("#filtroDataAl",true);
		 	});
		</script>
		<script type="text/javascript">
			function impostaHidden(idNotifica,annoProcedimentoSel,numProcedimentoSel,codAtto){
				document.getElementById("hiddenIdNotifica").value = idNotifica;
				document.getElementById("hiddenAnnoProcedimentoSel").value = annoProcedimentoSel;
				document.getElementById("hiddenNumProcedimentoSel").value = numProcedimentoSel;
				document.getElementById("hiddenCodAttoSel").value = codAtto;
				
			}

			function refreshLista(filtro) {
				//$("#mainForm").attr("action", contextPath+"/statistiche/elencoFiltrato.htm?filtro="+filtro);
				//$("#mainForm").submit();
			}
			
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>