<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>
<%@page import="it.giustizia.smp.form.ProfiloForm"%>

<% 		ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute(Constants.PROFILO);%>


	<table class="tb_form">	
		<tr>
			<td class="td_titolo" colspan="4">
				<c:out value="Sentenza di Cassazione su ricorso di II grado"></c:out>
			</td>
		</tr>
		
		<tr>
			<td><form:label path="descSedeUffMittenteUnita" ><spring:message code="flussi.ufficiomittente"/></form:label></td>
        	<td colspan="3">
				<c:out escapeXml="false" value="${flussiForm.descSedeUffMittenteUnita}"/>
        	</td>
		</tr>
			
			
		<tr>
			<td><form:label path="numeroSezionale" >Numero Sezionale</form:label></td>
        	<td colspan="3">
				<c:if test="${flussiForm.numeroSezionale != null }"><c:out value="${flussiForm.numeroSezionale}"></c:out></c:if>
        	</td>
		</tr>
				
		<tr>
				<c:choose>
				<c:when test="${flussiForm.accettazioneDeposito}">			
						<td><form:label path=""><spring:message code="atto.datadeposito" /></form:label></td>
						<td>
						<form:hidden path="attoDto.DATA_DEPOSITO_ASSOCIA" />
							<c:out value="${flussiForm.attoDto.DATA_DEPOSITO_ASSOCIA}" />
	        			</td>
				</c:when>
				<c:otherwise>
						<td><form:label path="" cssClass="required">
							<spring:message code="atto.datadeposito" />
							</form:label>
						</td>
						<td >
							<form:input path="attoDto.DATA_DEPOSITO_ASSOCIA" id="dataDepositoProposta" maxlength="10" size="12"/>
							<form:errors path="attoDto.DATA_DEPOSITO_ASSOCIA" class="testoRosso" />
						</td>		
				</c:otherwise>
			</c:choose>
			<!--
			<td><form:label path="" cssClass="required">
					<spring:message code="atto.datadeposito" />
				</form:label>
			</td>
			<td>
				<form:input path="attoDto.DATA_DEPOSITO_ASSOCIA" id="dataDepositoProposta" maxlength="10" size="12"/>
				<form:errors path="attoDto.DATA_DEPOSITO_ASSOCIA" class="testoRosso" />
			</td>
			-->
			
			<td><form:label path="dataRegistrazione"><spring:message code="atto.dataregistrazione" /></form:label></td>
			<td>
				<fmt:formatDate value="${flussiForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy"/>
			</td>
		</tr>
		<tr>
			<td><form:label path="" ><spring:message code="procedimento.tipo"/></form:label></td>
        	<td colspan="3">
				<c:out value="${flussiForm.procedimentoDto.DESC_TIPO_PROC}"/>
        	</td>
		</tr>

		
		<tr>
			<td><form:label path="impugnazioneDto.NUME_ANNO_ATTO" ><spring:message code="atto.numero"/></form:label></td>
        	<td colspan="3">
				<c:out value="${flussiForm.impugnazioneDto.NUME_ANNO_ATTO}"/><c:if test="${flussiForm.impugnazioneDto.NUME_ATTO != null }"><c:out value="/${flussiForm.impugnazioneDto.NUME_ATTO}"></c:out></c:if>
        	</td>
		</tr> 
		
 		
		
	<c:if test="${flussiForm.numProcDest != null }">
		<tr>
			<td>
				<form:label path="numProcDest">
					<spring:message code="procedimentoDest" />
				</form:label>
			</td>
			<td colspan="3"><font size="4"><b>
				<c:out value="${flussiForm.numProcDest}"/></b></font>
			</td>
			</tr>	
		</c:if> 
		
		<form:hidden path="procedimentoDto.CODI_K_ID_PROC"/>
		<form:hidden path="attoDto.CODI_K_ID_ATTO"/>			
	</table>

		<jsp:include page="/WEB-INF/jsp/flussi/includeDocumentoAllegato.jsp" />

	<table class="tb_form">			
	</table>
	
	<br />

	
		<script
			src="${pageContext.request.contextPath}/js/jquery/ui/jquery.ui.datepicker.js"></script>
		<script
			src="${pageContext.request.contextPath}/js/jquery/ui/jquery.ui.datepicker-it.js"></script>