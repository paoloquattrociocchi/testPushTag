<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/copertinaRicevuta.htm" />
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm">
			<form:hidden path="codAtto" />
			<form:hidden path="idUfficio" />
			<p class="titolo_page">
				<spring:message code="notifiche.rapporto.titolo" />
			</p>
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />

			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="notifiche.ricevuta.infoatto" /></td>
				</tr>
				<tr>
					<td  width="15%"><spring:message code="notifiche.ricevuta.destinatario" /></td>
					<td  width="85%" colspan="3"><c:out value="${attoNotificato.listaDestinatari[0].DESC_DEST}"/></td> 
				</tr>
				<tr>
					<td  width="15%"><spring:message code="notifiche.ricevuta.tipo.atto" /></td>
					<td  width="35%"><c:out value="${attoNotificato.tipoAtto.descrizione}"/></td> 
					<td  width="15%"><spring:message code="atto.categoria.atto" /></td>
					<td  width="35%"><c:out value="${attoNotificato.categoriaAtto.descrizione}"/></td> 
				</tr>
				<tr>
					<td><spring:message code="atto.numero" /></td>
					<td><c:out value="${attoNotificato.numeroAtto}"/></td> 
					<td><spring:message code="atto.dataemissione" /></td>
					<td><fmt:formatDate value="${attoNotificato.dataEmissione}" type="date" pattern="dd/MM/yyyy" /></td> 
				</tr>
			
				<c:if test="${attoForm.allegato.fileName!=null && attoForm.allegato.fileName!=''}">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="notifiche.rapporto.documento.rapporto" /></td>
					</tr>
					<tr>
						<td>
							<form:hidden path="allegato.fileName" /> 
							<form:hidden path="allegato.contentType" />		
							<span><c:out value="${attoForm.allegato.fileName}"></c:out></span> &nbsp;&nbsp;  
						</td>
						<td>
				 			<img src="${pageContext.request.contextPath}/images/download.gif" style="cursor: pointer; border: none" title="Visualizza documento" onclick="downloadRicevuta('${pageContext.request.contextPath}','${attoForm.codAtto}','${attoForm.idUfficio}');"></img>&nbsp;&nbsp;
						</td>
						<td colspan="2" style="width:80%">
						</td>
					</tr>
				</c:if>
				
			</table>

			<!-- jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" / -->

			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
			<br />
			<br />


		</form:form>
		<script  type="text/javascript">
 			function downloadRicevuta(contextPath,idAtto,idUfficio) {
 				//document.getElementById("idAttoRicevuta").value = idAtto;
 				//document.getElementById("idUffRicevuta").value = idUfficio;
 				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/notifiche/scaricaRicevutaDaAtto.htm");
				$("#id_formConfermaInvia").submit();
				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/atto/copertinaRicevuta.htm");
	 	 	}
 		</script>
		<smp:back buttonId="backButton"></smp:back>
	</tiles:putAttribute>
</tiles:insertDefinition>