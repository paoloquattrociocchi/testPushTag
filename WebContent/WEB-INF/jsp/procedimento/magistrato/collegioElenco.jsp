<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>

<c:set var="TIPO_UFFICIO_CORTE_APPELLO" value="<%=Constants.TIPO_UFFICIO_CORTE_D_APPELLO%>"/>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
		<spring:url var="azione" value="/procedimento/collegio/elenco.htm" />
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
			<display:table class="tb_lista" uid="magistratiAssociati" name="${magistratiAssociati}" pagesize="10" requestURI="${azione}">
				<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
					<form:radiobutton path="magistratoForm.id" value="${magistratiAssociati.id}" />
				</display:column>
				<display:column property="generalita" title="Cognome Nome" sortable="true" />
				<display:column property="dataAssegnazioneFascicolo" title="Data assegnazione fascicolo" format="{0,date,dd/MM/yyyy}" sortable="true" />
				<display:column property="dataFineAssegnazione" title="Data fine assegnazione" format="{0,date,dd/MM/yyyy}" sortable="true" />
				<display:column property="tipoSostituzione" title="Tipo sostituzione" sortable="true" />
				<display:column property="ruolo.descrizione" title="Ruolo" sortable="true" />
				<display:column property="descrizioneCortaRelatore" title="Relatore" sortable="true" />
			</display:table>

			<br />
			<br />

			<table class="tb_bottoni" align="center">
			<!-- Segnalazione EML-14 -->
				<c:choose>
					<c:when test="${profilo.idTipoUfficio==TIPO_UFFICIO_CORTE_APPELLO && procedimento.statoProcedimento.id == 24}">
						<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica" disabled="disabled"></td>
						<td class="td_tasto"><input type="button" value="Nuovo" name="Nuovo" disabled="disabled"></td>
						<td class="td_tasto"><input type="submit" value="Elimina" name="Elimina" disabled="disabled"></td>
					</c:when>
				<c:otherwise>
					<c:if test="${procedimento.statoProcedimento.id != null && procedimento.statoProcedimento.id != 19  }">
						<logic:notEmpty name="magistratiAssociati">
							<tr>
								<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica"></td>
								
								<td class="td_tasto"><input type="button" value="Nuovo" name="Nuovo" onClick="indietro('/procedimento/collegio/nuovo.htm')"></td>
							    <td class="td_tasto"><input type="submit" value="Elimina" name="Elimina"></td>
							</tr>
						</logic:notEmpty>
						<logic:empty name="magistratiAssociati">
							<tr>
								<td class="td_tasto"><input type="button" value="Nuovo" name="Nuovo" onClick="indietro('/procedimento/collegio/nuovo.htm')"></td>
							</tr>
						</logic:empty>
					</c:if>
				</c:otherwise>
				</c:choose>
			</table>
		</form:form>
		<script type="text/javascript">
				$(document).ready(function() {
					disabilitaSubmitCollegioElenco('${disabledViewProc}', '${procedimento.statoProcedimento.id }', '${sessionScope.TIPO_FASCICOLO}');
				});
			</script>
			
	 <c:if test="${profilo.ruolo=='MAGISTRATO'}">
		 <script type="text/javascript">
				$(document).ready(function() {
					disabilitaSubmitCollegioElenco('true', '1', '${sessionScope.TIPO_FASCICOLO}');
				
				});	
		</script>
		</c:if>		
	</tiles:putAttribute>
</tiles:insertDefinition>
