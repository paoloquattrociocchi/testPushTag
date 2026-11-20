<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<table class="tb_form">	
		<tr>
			<td class="td_titolo" colspan="4">
				<c:out value="${flussiForm.descTipoMessaggio }"></c:out>
			</td>
		</tr>
		<tr>
			<td><form:label path="descSedeUffMittenteUnita">
					<spring:message code="flussi.ufficiomittente" />
				</form:label>
			</td>
			<td colspan="3">
				<c:out value="${flussiForm.descSedeUffMittenteUnita }"/><c:if test="${flussiForm.procedimentoDto.NUME_PROC != null }"><c:out value="/${flussiForm.procedimentoDto.NUME_PROC}"/></c:if>
			</td>
		</tr>	
		<tr>
			<td><form:label path="dataRegistrazione" ><spring:message code="atto.dataregistrazione"/></form:label></td>
        	<td colspan="3">
				<fmt:formatDate value="${flussiForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy"/>
        	</td>
		</tr>	
		<tr>
			 <td><form:label path="attoDto.DATA_EMISSIONE" ><spring:message code="atto.dataemissione"/></form:label></td>
        	<td>
				<c:out value="${flussiForm.attoDto.DATA_EMISSIONE}"/>
        	</td> 
        	<td><form:label path="attoDto.DATA_DEPOSITO" ><spring:message code="atto.datadeposito"/></form:label></td>
        	<td>
				<c:out value="${flussiForm.attoDto.DATA_DEPOSITO}"/>
        	</td>
		</tr>
				
	 	 <tr>
			<td><form:label path="procedimentoDto.NUME_ANNO_PROC"><spring:message code="flussi.decreto.numeroProcedimento" /></form:label></td>
			<td> 
				<c:out value="${flussiForm.procedimentoDto.NUME_ANNO_PROC}"/><c:if test="${flussiForm.procedimentoDto.NUME_PROC != null }"><c:out value="/${flussiForm.procedimentoDto.NUME_PROC}"/></c:if>
			</td>
			<td><form:label path="decretoDto.NUME_ANNO_ATTO" ><spring:message code="atto.numero"/></form:label></td>
        	<td colspan="3">
				<c:out value="${flussiForm.decretoDto.NUME_ANNO_ATTO}"/><c:if test="${flussiForm.decretoDto.NUME_ATTO != null }"><c:out value="/${flussiForm.decretoDto.NUME_ATTO}"></c:out></c:if>
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
	<br />
	<br />
	<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettidelprocedimento" />
			</td>
		</tr>
	</table>
	<display:table class="tb_lista" uid="direttiInteressati" name="${flussiForm.listaSoggettoForm}" pagesize="10"
		requestURI="${azione}" >

		<display:column property="descrizioneSoggetto" title="Generalità" sortable="true"/>
	</display:table>
	
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