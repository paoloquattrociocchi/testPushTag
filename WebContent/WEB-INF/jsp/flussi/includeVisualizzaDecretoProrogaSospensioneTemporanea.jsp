<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>

	<c:set var="PROROGA_SOSPENSIONE_MSG" value="<%=Constants.PROROGA_SOSPENSIONE_MSG%>"/>

	<table class="tb_form">	
		<tr>
			<td class="td_titolo" colspan="4">
				<c:out value="${flussiForm.descTipoMessaggio }"></c:out>
			</td>
		</tr>
		<tr>
			<td><form:label path="descSedeUffMittenteUnita" ><spring:message code="flussi.ufficiomittente"/></form:label></td>
        	<td colspan="3">
				<c:out escapeXml="false" value="${flussiForm.descSedeUffMittenteUnita}"/>
        	</td>
		</tr>
		<tr>
			<td><form:label path="" ><spring:message code="procedimento.dataEmissione"/></form:label></td>
        	<td>
				<c:out value="${ flussiForm.attoDto.DATA_EMISSIONE}"/>
        	</td>
        	<td><form:label path="" ><spring:message code="procedimento.dataRicezione"/></form:label></td>
        	<td>
				<c:out value="${flussiForm.attoDto.DATA_RICEZIONE }"/>
        	</td>
		</tr>
		<tr>
			<td>
				<form:label path="procedimentoDto.NUME_ANNO_PROC">
					<spring:message code="procedimento" />
				</form:label>
			</td>
			<td colspan="3">
				<c:out value="${flussiForm.procedimentoDto.NUME_ANNO_PROC}"/><c:if test="${flussiForm.procedimentoDto.NUME_PROC != null }"><c:out value="/${flussiForm.procedimentoDto.NUME_PROC}"/></c:if> 
			</td>
		</tr>	
		
		<tr>
			<td><form:label path="" ><spring:message code="procedimento.tipo"/></form:label></td>
        	<td colspan="3">
				<c:out value="${flussiForm.procedimentoDto.DESC_TIPO_PROC}"/>
        	</td>
		</tr>	
		<tr>
			<td><form:label path="decretoDto.NUME_ANNO_ATTO" ><spring:message code="atto.numero"/></form:label></td>
        	<td colspan="3">
				<c:if test="${flussiForm.impugnazioneDto!=null && flussiForm.impugnazioneDto.NUME_ATTO!=null}">
					<c:out value="${flussiForm.impugnazioneDto.NUME_ANNO_ATTO}"/>
					<c:out value="/${flussiForm.impugnazioneDto.NUME_ATTO}"></c:out>
				</c:if>
				<c:if test="${flussiForm.decretoDto!=null && flussiForm.decretoDto.NUME_ATTO!=null}">
					<c:out value="${flussiForm.decretoDto.NUME_ANNO_ATTO}"/>
					<c:out value="/${flussiForm.decretoDto.NUME_ATTO}"></c:out>
				</c:if>
        	</td>
		</tr>

	</table>

	<table class="tb_form">			
		<tr>
			<td><form:label path="" cssClass="required"><spring:message code="atto.dataricezione" /></form:label></td>
			<td colspan="3">
				<form:input path="attoDto.DATA_RICEZIONE_ASSOCIA" id="dataDepositoProposta" maxlength="10" size="12"/>
				<form:errors path="attoDto.DATA_RICEZIONE_ASSOCIA" class="testoRosso" />
			</td>
		</tr>
	</table>
	<br />
<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td class="td_titolo" colspan="4"><spring:message
				code="soggetto.soggettidelprocedimentoflusso" />
		</td>
	</tr>
</table>
<table class="tb_lista">
	<tr>
		<th>Generalità</th>
	</tr>

	<c:set var="indexLista" value="${0}" />
	<c:forEach var="soggetti" items="${flussiForm.listaSoggettoForm}"
		varStatus="status">
		<c:if test="${soggetti.idRuolo == 1}">
			<c:set var="indexLista" value="${indexLista + 1}" />
			<c:if test="${((indexLista % 2) == 0)}">
				<c:set var="oddEven" value="even" />
			</c:if>
			<c:if test="${((indexLista % 2) == 1)}">
				<c:set var="oddEven" value="odd" />
			</c:if>

			<tr class="${oddEven}">
				<td class=""><c:out value="${soggetti.descrizioneSoggetto}" />
				</td>
			</tr>
		</c:if>
	</c:forEach>
</table>
	<br />
	<br />
	
		<script
			src="${pageContext.request.contextPath}/js/jquery/ui/jquery.ui.datepicker.js"></script>
		<script
			src="${pageContext.request.contextPath}/js/jquery/ui/jquery.ui.datepicker-it.js"></script>