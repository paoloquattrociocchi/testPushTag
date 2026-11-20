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

<c:set var="TIPO_UFFICIO_PROCURA_GENERALE" value="<%=Constants.TIPO_UFFICIO_PROCURA_GENERALE%>"/>

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
		<td>
				<form:label path="dataFlusso">
					<spring:message code="flussi.proposta.dataProposta" />
				</form:label>
			</td>
			<td colspan="3">
				<c:out value="${flussiForm.attoDto.DATA_EMISSIONE}"/>
			</td>
		</tr>
		
		<tr>
				<td><form:label path="" ><spring:message code="procedimento.dataEmissione"/></form:label></td>
	        	<td>
					<c:out value="${ flussiForm.attoDto.DATA_EMISSIONE}"/>
	        	</td>
        	<td><form:label path="" ><spring:message code="procedimento.dataRicezione"/></form:label></td>
        	<td colspan="3">
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
			<c:if test="${profilo.idTipoUfficio==TIPO_UFFICIO_PROCURA_GENERALE}">		
					<td><form:label path="" cssClass="required">
							<spring:message code="atto.dataricezione" />
						</form:label>
					</td>
					<td>
						<form:input path="attoDto.DATA_RICEZIONE_ASSOCIA" id="dataDepositoProposta" maxlength="10" size="12"/>
						<form:errors path="attoDto.DATA_RICEZIONE_ASSOCIA" class="testoRosso" />
					</td>		
							
			</c:if>
			<c:if test="${profilo.idTipoUfficio!=TIPO_UFFICIO_PROCURA_GENERALE}">
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
			</c:if>
				
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
			<td><form:label path="procedimentoDto.CODI_FK_ID_PROCED"><spring:message code="procedimento.proponente" /></form:label></td>
			<td  colspan="3">
				<c:out value="${flussiForm.procedimentoDto.DESC_PROCED}"/>
			</td>
		</tr>
		
		<tr>
			<td><form:label path="decretoDto.NUME_ANNO_ATTO" ><spring:message code="atto.numero"/></form:label></td>
        	<td colspan="3">
				<c:out value="${flussiForm.decretoDto.NUME_ANNO_ATTO}"/><c:if test="${flussiForm.decretoDto.NUME_ATTO != null }"><c:out value="/${flussiForm.decretoDto.NUME_ATTO}"></c:out></c:if>
        	</td>
		</tr> 
		
		
		<tr>
			<td><form:label path="procedimentoDto.FLG_ANTIMAFIA" ><spring:message code="procedimento.tipologiaPrevenzione"/></form:label></td>
        	<td colspan="3">
				<spring:message code="procedimento.antimafia"/>
				<c:if test="${flussiForm.procedimentoDto.FLG_ANTIMAFIA != null}">
					<input type="checkbox" disabled="disabled" checked="checked" />
				</c:if>
				<c:if test="${flussiForm.procedimentoDto.FLG_ANTIMAFIA == null}">
					<input type="checkbox" disabled="disabled" />
				</c:if>
				<spring:message code="procedimento.antiterrorismo"/>
				<c:if test="${flussiForm.procedimentoDto.FLG_ANTITERRORISMO != null}">
					<input type="checkbox" disabled="disabled" checked="checked" />
				</c:if>
				<c:if test="${flussiForm.procedimentoDto.FLG_ANTITERRORISMO == null}">
					<input type="checkbox" disabled="disabled" />
				</c:if>
				<spring:message code="procedimento.ordinaria"/>
				<c:if test="${flussiForm.procedimentoDto.FLG_ORDINARIA != null}">
					<input type="checkbox" disabled="disabled" checked="checked" />
				</c:if>
				<c:if test="${flussiForm.procedimentoDto.FLG_ORDINARIA == null}">
					<input type="checkbox" disabled="disabled" />
				</c:if>
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
	</table>

    <c:if test="${profilo.idTipoUfficio != TIPO_UFFICIO_PROCURA_GENERALE }">
		<jsp:include page="/WEB-INF/jsp/flussi/includeDocumentoAllegato.jsp" />
	</c:if>
	

	<table class="tb_form">			
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
		
			<c:set var="indexLista" value="${indexLista + 1}" />
			<c:if test="${((indexLista % 2) == 0)}">
				<c:set var="oddEven" value="even" />
			</c:if>
			<c:if test="${((indexLista % 2) == 1)}">
				<c:set var="oddEven" value="odd" />
			</c:if>

           <c:if test="${soggetti.idRuolo != 3 }">
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