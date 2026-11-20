<%-- <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<table class="tb_form">
	<tr>
		<td class="td_titolo" colspan="4"><c:out
				value="${flussiForm.descTipoMessaggio }"></c:out></td>
	</tr>
	<tr>
		<td><form:label path="descSedeUffMittenteUnita">
				<spring:message code="flussi.ufficiomittente" />
			</form:label></td>
		<td colspan="3"><c:out
				value="${flussiForm.descSedeUffMittenteUnita }" />
			<c:if test="${flussiForm.procedimentoDto.NUME_PROC != null }">
				<c:out value="/${flussiForm.procedimentoDto.NUME_PROC}" />
			</c:if></td>
	</tr>
	<tr>
		<td><form:label path="procedimentoDto.NUME_ANNO_PROC">
				<spring:message code="flussi.decreto.numeroProcedimento" />
			</form:label></td>
		<td><c:out value="${flussiForm.attoProcDto.ANNO_PROCEDIMENTO}" />
			<c:if test="${flussiForm.attoProcDto.NUM_PROCEDIMENTO != null }">
				<c:out value="/${flussiForm.attoProcDto.NUM_PROCEDIMENTO}" />
			</c:if></td>
		<td><form:label path="attoDto.CODI_ANNO_ATTO">
				<spring:message code="flussi.decreto.numeroDecreto" />
			</form:label></td>
		<td><c:out value="${flussiForm.attoDto.CODI_ANNO_ATTO}" />
			<c:if test="${flussiForm.attoDto.NUME_ATTO != null }">
				<c:out value="/${flussiForm.attoDto.NUME_ATTO}"></c:out>
			</c:if></td>
	</tr>
	<tr>
		<td><form:label path="attoDto.DATA_EMISSIONE">
				<spring:message code="atto.dataemissione" />
			</form:label></td>
		<td><c:out value="${ flussiForm.attoDto.DATA_EMISSIONE}" /></td>
		<td><form:label path="attoDto.DATA_DEPOSITO">
				<spring:message code="atto.datadeposito" />
			</form:label></td>
		<td><c:out value="${flussiForm.attoDto.DATA_DEPOSITO}" /></td>
	</tr>
	<tr>
		<td><form:label path="dataRegistrazione">
				<spring:message code="atto.dataregistrazione" />
			</form:label></td>
		<td colspan="3"><fmt:formatDate
				value="${flussiForm.dataRegistrazione}" type="date"
				pattern="dd/MM/yyyy" /></td>
	</tr>
</table>
<br />
<br />
<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td class="td_titolo" colspan="4"><spring:message
				code="soggetto.soggettidelprocedimento" /></td>
	</tr>
</table>
<display:table class="tb_lista" uid="direttiInteressati"
	name="${flussiForm.listaSoggettoForm}" pagesize="10"
	requestURI="${azione}">

	<display:column property="descrizioneSoggetto" title="Generalità"
		sortable="true" />
</display:table>

<br />
<br /> --%>