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
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/notifiche/listaNotifiche.htm" />
		<style type="text/css">
			.pagebanner {visibility: hidden;)
		</style>
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			<form:hidden id="id_hidden_Atto" path="codAtto" />
			<form:hidden path="codAtto" />
			<form:hidden path="tipoAtto.id" />
			<form:hidden id="idAttoRicevuta" path="idAttoRicevuta" />
			<form:hidden id="idNotifAmm" path="idNotifAmm" />
			<form:hidden id="idUffRicevuta" path="idUffRicevuta" />
			<p class="titolo_page">
				<spring:message code="notifiche.listanotifiche.titolo" />
			</p>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<c:forEach var="notifica" items="${attoForm.listaNotifiche}" varStatus="dep">
			 	<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="notifiche.titolo.datinotifica" /></td>
					</tr>
					<tr>				
						<td width="20%"><form:label path="oggetto">
								<spring:message code="notifiche.invianotifiche.oggetto" />
							</form:label></td>
						<td colspan="3"><c:out value="${notifica.oggetto}"/></td>
					</tr>
					<tr>				
						<td width="20%"><form:label path="oggetto">
								<spring:message code="notifiche.invianotifiche.evento" />
							</form:label></td>
						<td colspan="3"><c:out value="${notifica.evento}"/></td>
					</tr>
					<tr>
						<td><form:label path="dataInvio" >
							<spring:message code="notifiche.invianotifiche.dataInvio" />
						</form:label></td>
						<td  width="40%"><fmt:formatDate value="${notifica.dataInvio}" type="date" pattern="dd/MM/yyyy"/> </td>
						<td><form:label path="flgDatiSensibili" >
							<spring:message code="notifiche.invianotifiche.datiSensibili" />
						</form:label></td>
						<td ><form:checkbox id="datiSensibili" disabled="true"  path="listaNotifiche[${dep.index}].datiSensibili" /> </td>
					</tr>
					<tr>
						<td colspan="4">
							<display:table class="tb_lista" uid="destinatario"  name="${attoForm.listaNotifiche[dep.index].dettaglioDestinatari}" pagesize="999">
								<display:column title="Tipo Destinatario" sortable="false" style="width:30%" >
									<table >
										<tr><td valign="middle" style="border-bottom:0px;">${destinatario.tipoDestinatario}</td></tr>
										<c:if test="${destinatario.hasAmministratori}">
											<tr><td valign="middle" style="border-bottom:0px;"><i>Contiene anche i dati per il Tool Amministratori&nbsp;&nbsp;</i></td><td valign="middle" style="border-bottom:0px;"><img src="${pageContext.request.contextPath}/images/download.gif" style="cursor: pointer; border: none" title="Visualizza documento xml inviato agli amministratori" onclick="downloadXMLToolAmm('${pageContext.request.contextPath}','${destinatario.codiKIdNotifDest}');"></img>&nbsp;&nbsp;</td></tr>
										</c:if>
									</table>
								</display:column>
								<display:column property="destinatario" title="Descrizione Destinatario"  style="width:20%" sortable="false" />
								<display:column property="statoComunicazione" title="Stato comunicazione" style="width:20%" sortable="false" />
								<display:column title="Ricevute	" sortable="false"  style="width:20%">
									<table >
									<c:forEach var="ricevuta" items="${destinatario.mappaRicevute}"  varStatus="dep">
										<tr><td valign="middle" style="border-bottom:0px;">
										${ricevuta.key}&nbsp;&nbsp;</td><td valign="middle" style="border-bottom:0px;"><img src="${pageContext.request.contextPath}/images/download.gif" style="cursor: pointer; border: none" title="Visualizza documento" onclick="downloadRicevuta('${pageContext.request.contextPath}','${ricevuta.value.id.codiKIdAtto}','${ricevuta.value.id.codiKIdUffOwner}');"></img>&nbsp;&nbsp;</td>
										</td></tr>
									</c:forEach>
									</table>
								</display:column>
							</display:table>
						</td>
					</tr>
				</table>
				<br/>			
			</c:forEach>
			<br/><br/>
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto">
						 <input type="button" id="backButton" value="Indietro" title="Indietro">
					</td>
				</tr>
			</table>
			<br />
			<br />
		</form:form>
		
		<smp:back buttonId="backButton"></smp:back>
 		<script  type="text/javascript">
 			function downloadRicevuta(contextPath,idAtto,idUfficio) {
 				document.getElementById("idAttoRicevuta").value = idAtto;
 				document.getElementById("idUffRicevuta").value = idUfficio;
 				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/notifiche/scaricaRicevuta.htm");
				$("#id_formConfermaInvia").submit();
				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/notifiche/listaNotifiche.htm");
	 	 	}
 			function downloadXMLToolAmm(contextPath,idNotifAmm) {
 	 			document.getElementById("idNotifAmm").value = idNotifAmm;
 				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/notifiche/scaricaXmlToolAmm.htm");
				$("#id_formConfermaInvia").submit();
				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/notifiche/listaNotifiche.htm");
				
	 	 	}
 		</script>
	 	<img src="${pageContext.request.contextPath}/images/arrow_d.png" width="0" height="0"/>
	</tiles:putAttribute>
</tiles:insertDefinition>



