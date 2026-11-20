<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>
<%@ page import="it.giustizia.smp.form.ProcedimentoForm"%>

<%
	ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
	String statoProc = procedimento.getStatoProcedimento().getId()+"";
%>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
		<spring:url var="azione" value="/procedimento/giudiceDelegato/elenco.htm" />
		<p class="titolo_page">
			<spring:message code="magistrato.titolo.giudiceDelegato" />
		</p>
		<form:form method="post" action="${azione}" commandName="procedimentoForm">
			<div>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<logic:equal name="Associazione" value="ATTO">
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				<br />
			</logic:equal>
			</div>
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="magistrato.titolo.giudiceDelegato" /></td>
				</tr>
			</table>
			
			<display:table class="tb_lista" uid="magistratiAssociati" name="${magistratiAssociati}" pagesize="10" requestURI="${azione}">
				<logic:equal name="Associazione" value="ATTO">
					<logic:equal name="statoAtto" value="<%=Constants.STATOATTO_IN_LAVORAZIONE_S%>">
						<logic:present name="Bottoni">
							<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
								<form:radiobutton path="magistratoForm.id" value="${magistratiAssociati.id}" />
							</display:column>
						</logic:present>	
					</logic:equal>
				</logic:equal>
				
				<logic:equal name="Associazione" value="PROCEDIMENTO">
				<c:if test="${procedimento.statoProcedimento.id != 20}">
					<c:if test="${magistratiAssociati.tipoSostituzione==null}">
						<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%" >
							<form:radiobutton path="magistratoForm.id" value="${magistratiAssociati.id}" checked="checked" />
						</display:column>
					</c:if>
				</c:if>
				<c:if test="${magistratiAssociati.tipoSostituzione!=null}">
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%" >
					</display:column>
				</c:if>
				</logic:equal>
				
				<c:if test="${magistratiAssociati.tipoSostituzione==null}">
					<display:column property="generalita" title="Cognome Nome" sortable="true" />
					<logic:equal name="Associazione" value="PROCEDIMENTO">
						<display:column property="dataAssegnazioneFascicolo" title="Data assegnazione fascicolo" format="{0,date,dd/MM/yyyy}" sortable="true" />
						<display:column property="dataFineAssegnazione" title="Data fine assegnazione" format="{0,date,dd/MM/yyyy}" sortable="true" />
						<display:column property="tipoSostituzione" title="Tipo sostituzione" sortable="true" />
					</logic:equal>
					<display:column value="Giudice delegato" title="Ruolo" sortable="true" />
				</c:if>
				<c:if test="${magistratiAssociati.tipoSostituzione!=null}">
					<display:column property="generalita" title="Cognome Nome" sortable="true"  style="color: #6E6E6E"/>
					<logic:equal name="Associazione" value="PROCEDIMENTO">
						<display:column property="dataAssegnazioneFascicolo" title="Data assegnazione fascicolo" format="{0,date,dd/MM/yyyy}" sortable="true" style="color: #6E6E6E"/>
						<display:column property="dataFineAssegnazione" title="Data fine assegnazione" format="{0,date,dd/MM/yyyy}" sortable="true" style="color: #6E6E6E"/>
						<display:column property="tipoSostituzione" title="Tipo sostituzione" sortable="true" style="color: #6E6E6E"/>
					</logic:equal>
					<display:column value="Giudice delegato" title="Ruolo" sortable="true" style="color: #6E6E6E"/>
				</c:if>	
				
			</display:table>

			<br />
			
			<table class="tb_bottoni" align="center">
				<tr>
				<logic:equal name="Associazione" value="PROCEDIMENTO">
					<c:if test="${procedimento.statoProcedimento.id != 20}">
						<logic:notEmpty name="magistratiAssociati">
							<logic:equal name="UffProprietario" value="SI">
								<td class="td_tasto"><input type="submit" value="Modifica" name="Sostituisci"></td>
							</logic:equal>
		 				</logic:notEmpty>
		 			</c:if>		
				</logic:equal>
				<logic:equal name="Associazione" value="ATTO">
					<logic:present name="Bottoni">
						<logic:equal name="statoAtto" value="<%=Constants.STATOATTO_IN_LAVORAZIONE_S%>">
							<logic:empty name="magistratiAssociati">
								<td class="td_tasto"><input type="button" value="Nuovo" name="Nuovo" onClick="indietro('/procedimento/giudiceDelegato/nuovo.htm')"></td>
							</logic:empty>
							<logic:notEmpty name="magistratiAssociati">
								<td class="td_tasto"><input type="submit" value="Elimina" name="Elimina"></td>
							</logic:notEmpty>
						</logic:equal>
					</logic:present>	
				</logic:equal>
				</tr>
			</table>
		</form:form>
		<script type="text/javascript">
				$(document).ready(function() {
					disabilitaSubmitCollegioElenco('${disabledViewProc}', '${procedimento.statoProcedimento.id }', '${sessionScope.TIPO_FASCICOLO}');
				});
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
