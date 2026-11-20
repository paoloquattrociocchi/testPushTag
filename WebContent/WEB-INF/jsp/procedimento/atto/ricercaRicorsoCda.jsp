<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="it.giustizia.smp.form.ProcedimentoForm"%>



<%
	ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");

%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/copertinaAtto.htm" />
		<spring:url var="paginazione" value="/procedimento/atto/paginazioneRicercaRicorsoCda.htm" />
		<form:form method="post" action="${azione}" commandName="attoForm" id="idAttoForm">
			<p class="titolo_page">
				<spring:message code="atto.elenco.ricorsi" />
			</p>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="atto.elenco.ricorsi" /></td>
				</tr>
			</table>
			<logic:notEmpty name="listaAtti">
			<display:table class="tb_lista" uid="listaAtti" name="${listaAtti}" pagesize="10" requestURI="${paginazione}">
				<display:column title="Atto" sortable="false" value="${listaAtti.categoriaAtto.descrizione} ${listaAtti.numeroProcedimentoOrigine}"/>
				<display:column title="Numero atto" sortable="false">
					<c:out value="${listaAtti.numeroAtto}" />			 
				</display:column>
				<display:column property="statoAtto.descrizione" title="Stato" sortable="false" />
				<display:column property="dataDeposito" title="Data di deposito" format="{0,date,dd/MM/yyyy}" sortable="false" />
				
			</display:table>
			
			<br />
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">	
	
				<tr>
				   <c:if test="${procedimento.dataTrasmCda==null}">	
						<td width="20%"><form:label path="dataTrasmCda" cssClass="required"> 
									<spring:message code="atto.dataTrasmCda" />
								</form:label></td>
						<td><form:input path="dataTrasmCda" maxlength="10" id="id_dataTrasmCda" /> <form:errors path="dataTrasmCda" class="testoRosso" />
						</td>
					</c:if>	
					<c:if test="${procedimento.dataTrasmCda!=null}">
						<td width="20%"><form:label path="dataTrasmCda">
									<spring:message code="atto.dataTrasmCda" />
								</form:label></td>
					     <td>
					      <fmt:formatDate value="${procedimento.dataTrasmCda}" type="date" pattern="dd/MM/yyyy" />
						 </td>					
					</c:if>
				</tr>			
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					
				<c:if test="${procedimento.dataTrasmCda==null}">				
					<td class="td_tasto"><input type="submit" name="InviaRicorsiInCdA" title="Invia in CdA" value="Invia in CdA" /></td>
				</c:if>
				</tr>
			</table>
			<br />
			<br />
			</logic:notEmpty>
		</form:form>


<script type="text/javascript">
		
$(document).ready(function() {
	prendiData("#id_dataTrasmCda",true);

});

</script>
</script>

	</tiles:putAttribute>
</tiles:insertDefinition>