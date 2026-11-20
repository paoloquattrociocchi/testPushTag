<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/stralcio/gestioneStralcioAttoGenerici" />
		<form:form method="post" action="${azione}" commandName="stralcioForm" id="idAttoForm">
			
			<form:hidden path="idProcedimentoDestinatario" /> 
			
			<p class="titolo_page">
				<spring:message code="stralcio.elencoSpostaCopiaAtti" />
			</p>
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo_label" width="15%"><spring:message code="procedimentoDiDestinazione" />
					</td>
					<td colspan="3" class="td_titolo">
						<c:out value="${stralcioForm.idProcedimentoDestinatario}" />			
					</td>
				</tr>
			</table>
			
			
			
			
			<logic:notEmpty name="listaAtti">
			<display:table class="tb_lista" uid="listaAtti" name="${listaAtti}">
				<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
					<form:radiobutton path="codAtto" value="${listaAtti.codAtto}" />
				</display:column>
				<display:column title="Atto" sortable="false" value="${listaAtti.categoriaAtto.descrizione} ${listaAtti.numeroProcedimentoOrigine}"/>
			</display:table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="button" value="Dettaglio" name="Dettaglio" onclick="submitDettaglio('Dettaglio');" title="Visualizza Dettaglio Atto" class="nowait" /></td> 
				</tr>
			</table>
			<br />
			</logic:notEmpty>
			<input	id="idSubmitGestioneAttiGenerici" type="submit" name="" value="" style="display: none" />
		</form:form>


<script type="text/javascript">

function submitDettaglio(name){

	$('#idSubmitGestioneAttiGenerici').attr('name', name);
	$('#idAttoForm').attr('action', '${pageContext.request.contextPath}/procedimento/atto/copertinaAtto.htm');

	$('#idSubmitGestioneAttiGenerici').click();
	
}

</script>

	</tiles:putAttribute>
</tiles:insertDefinition>