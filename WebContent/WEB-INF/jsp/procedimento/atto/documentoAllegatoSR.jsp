<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<c:set var="STATOATTO_REGISTRATO" value="<%=Constants.STATOATTO_REGISTRATO%>"/>
<c:set var="STATOATTO_INOLTRATO" value="<%=Constants.STATOATTO_INOLTRATO%>"/>

<table class="tb_form">
	<tr>
		<td class="td_titolo" colspan="4"><spring:message code="atto.inseriscidocumento" /></td>
	</tr>
	<c:if test="${currentForm.allegato.fileName!=null && currentForm.allegato.fileName!=''}">
		<tr>
			<td colspan="4">
			<form:hidden path="allegato.fileName" /> 
			<form:hidden path="allegato.contentType" />		
			<span><c:out value="${currentForm.allegato.fileName}"></c:out></span> &nbsp;&nbsp; <img src="${pageContext.request.contextPath}/images/download.gif" style="cursor: pointer; border: none" title="Visualizza documento" onclick="gestioneDocumento('${pageContext.request.contextPath}','downloadDocumentale')"></img>&nbsp;&nbsp; 
			<c:if test="${currentForm.statoAtto.id!=STATOATTO_REGISTRATO && currentForm.statoAtto.id!= STATOATTO_INOLTRATO}">
				<img id="cancFile" src="${pageContext.request.contextPath}/images/cancella.png" style="cursor: pointer; border: none" title="Cancella documento" onclick="gestioneDocumento('${pageContext.request.contextPath}','rimuoviAllegatoDaMemoriaDocumentale')"></img>
			</c:if>
			<c:if test="${(currentForm.dataRegistrazione!=null || currentForm.statoAtto.id==STATOATTO_REGISTRATO) && (currentForm.allegato.firmaRemota.firmaImmediata==false)}">
				<img id="idFirmaFile" src="${pageContext.request.contextPath}/images/firma.png" style="cursor: pointer; border: none" title="Firma documento" onclick="gestioneDocumento('${pageContext.request.contextPath}','firmaPosticipataDocumentale')"></img>
			</c:if>
			</td>
		</tr>
		<tr>
			<td class="td_campo">
				<form:checkbox id="id_checkFirmaImmediata" path="allegato.firmaRemota.firmaImmediata" style="cursor:pointer" disabled="${(currentForm.allegato.documentoFirmato=='S') || (fn:toLowerCase(currentForm.allegato.contentType) != 'application/pdf') || (currentForm.statoAtto.id==STATOATTO_REGISTRATO || currentForm.statoAtto.id==STATOATTO_INOLTRATO)}" /> 
					<form:label	path="allegato.firmaRemota.firmaImmediata">
					<spring:message code="documentale.firmaremota.firmaImmediata" />
				</form:label>
			</td>
		</tr>
	</c:if>
	<c:if test="${currentForm.allegato.fileName==null || currentForm.allegato.fileName==''}">
		<c:if test="${currentForm.statoAtto==null || currentForm.statoAtto.id!=STATOATTO_REGISTRATO}">
			<c:if test="${currentForm.dataRegistrazione==null}">
				<tr>
					<td><form:label for="fileData" path="allegato.fileData" >
							<spring:message code="file" />
						</form:label></td>
					<td colspan="3">
						<form:input id="fileData" path="allegato.fileData" type="file" cssStyle="width:500px" />
						<form:errors path="allegato.fileData" class="testoRosso" />
					</td>
				</tr>
				<tr>
					<td class="td_campo" colspan="2">
						<form:checkbox id="id_checkFirmaImmediata" path="allegato.firmaRemota.firmaImmediata" style="cursor:pointer" /> 
							<form:label	path="allegato.firmaRemota.firmaImmediata">
							<spring:message code="documentale.firmaremota.firmaImmediata" />
						</form:label>
					</td>
				</tr>
			</c:if>
		</c:if>
		<c:if test="${currentForm.dataRegistrazione!=null || currentForm.statoAtto.id==STATOATTO_REGISTRATO}">
			<tr>
				<td><spring:message code="file.notPresent" /></td>
			</tr>
			
		</c:if>
	</c:if>
	<form:hidden path="allegato.documentoFirmato"/>
	<form:hidden id="allegatoRimosso" path="allegato.allegatoRimosso" value="${currentForm.allegato.allegatoRimosso}"/> 
	<input id="id_buttonGestioneDocumento" type="submit" name="" value="" style="visibility: hidden" class="nowait" ></td>
</table>

<!-- ID relativi alla gestione della firma remota -->
	<form:hidden id="id_hiddenFirmaRemotaUserName"
		path="allegato.firmaRemota.userName" />
	<form:hidden id="id_hiddenFirmaRemotaPassword"
		path="allegato.firmaRemota.password" />
	<form:hidden id="id_hiddenFirmaRemotaPin"
		path="allegato.firmaRemota.documentalePin" />
	
	<input type="hidden" id="id_dialogForm_expired" value="false"/>				
	<input type="hidden" id="id_dialogForm_timer"  value="0"/>	
	<div id="id_dialogForm" style="display: none">
		<jsp:include page="/WEB-INF/jsp/common/firmaRemotaPIN.jsp" />
	</div>
