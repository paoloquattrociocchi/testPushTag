<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
		<spring:url var="azione" value="/procedimento/magistrato/associati/gestione.htm" />
		<spring:url var="ordinamento" value="/procedimento/magistrato/associati/visualizza.htm" />
		<p class="titolo_page">
			<spring:message code="magistrato.titolo.associati" />
		</p>
		<form:form method="post" action="${azione}" commandName="procedimentoForm">

			<div>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			</div>
			<br />

			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="magistrato.lista.associati" /></td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="magistratiAssociati" name="${magistratiAssociati}" pagesize="10" requestURI="${ordinamento}">
				<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
					<form:radiobutton path="magistratoForm.id" value="${magistratiAssociati.id}" />
				</display:column>
				<display:column property="generalita" title="Cognome Nome" sortable="true" />
				<display:column property="dataAssegnazioneFascicolo" title="Data assegnazione fascicolo" format="{0,date,dd/MM/yyyy}" sortable="true" />
				<display:column property="dataFineAssegnazione" title="Data fine assegnazione" format="{0,date,dd/MM/yyyy}" sortable="true" />
				<display:column property="tipoSostituzione" title="Tipo sostituzione" sortable="true" />
			</display:table>

			<br />
			<br />

			<table class="tb_bottoni" align="center">
			<c:if test="${procedimento.statoProcedimento.id != null && !(procedimento.statoProcedimento.id == 19 || procedimento.statoProcedimento.id == 24 || procedimento.statoProcedimento.id == 14  )   }">
				<logic:notEmpty name="magistratiAssociati" > 
					<tr>
						<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica"></td>
						<td class="td_tasto"><input type="submit" value="Elimina" name="Elimina"></td>
						<td class="td_tasto"><input type="button" value="Nuovo" name="Nuovo" onClick="indietro('/procedimento/magistrato/associa/ricerca.htm')"></td>
					</tr>
				</logic:notEmpty>
				<c:if test="${profilo.ruolo!='MAGISTRATO'}">
				<logic:empty name="magistratiAssociati"> 
					<tr>
						<c:if test="${procedimento.statoProcedimento.id == 22}">
							<td class="td_tasto"><input type="button" value="Nuovo" name="Nuovo" onClick="indietro('/procedimento/magistrato/associa/ricerca.htm')" disabled="true"></td>
						</c:if>
						<c:if test="${procedimento.statoProcedimento.id != 22}">
							<td class="td_tasto"><input type="button" value="Nuovo" name="Nuovo" onClick="indietro('/procedimento/magistrato/associa/ricerca.htm')"></td>
						</c:if>
					</tr>
				</logic:empty>
				</c:if>
			</c:if>
			</table>
		</form:form>
		<c:if test="${sessionScope.profilo.idTipoUfficio!=17 && procedimento.statoProcedimento.id!=12}">
			<script type="text/javascript">
				$(document).ready(function() {
					disabilitaSubmitElencoAssociati('${disabledViewProc}', '${procedimento.statoProcedimento.id }', '${sessionScope.profilo.idTipoUfficio}');
				});
			</script>		
		</c:if>
	</tiles:putAttribute>
</tiles:insertDefinition>
