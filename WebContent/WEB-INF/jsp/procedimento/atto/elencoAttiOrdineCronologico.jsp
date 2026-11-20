<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>


<c:set var="TIPO_UFFICIO_PROCURA" value="<%=Constants.TIPO_UFFICIO_PROCURA%>"/>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/copertinaAtto.htm" />
		<spring:url var="paginazione" value="/procedimento/atto/paginazioneOrdineCronologicoAtti.htm" />
		<style type="text/css">
			.spanflgNascondiRicevute label {
				font-weight: normal !important;
				color: black;
			}
		</style>
		<form:form method="post" action="${azione}" commandName="attoForm" id="idAttoForm">
        
       
			
			<p class="titolo_page">
			<!--<logic:notPresent name="TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM">
				<c:if test="${profilo.idTipoUfficio != TIPO_UFFICIO_PROCURA }">
						<spring:message code="atto.elencoCronologico.cda" />
				</c:if>
				<c:if test="${profilo.idTipoUfficio == TIPO_UFFICIO_PROCURA }">
						<spring:message code="atto.elencoCronologico" />
				</c:if>
			</logic:notPresent>
			<logic:present name="TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM">
				<spring:message code="atto.elencoCronologico" />
			</logic:present>-->
			<spring:message code="atto.elencoCronologico" />
				
			</p>
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<logic:notEmpty scope="request" name="attoForm" property="descTribCda">
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0"-->
		       <tr>
		      	<td class="td_titolo_label">
				<p>
				   <logic:notEmpty scope="request" name="attoForm" property="idProcedimentoCda">
					Fascicolo Tribunale: <c:out value="${attoForm.descTribCda}" />
					</logic:notEmpty>
				
					
					

				</p></td>
		        </tr>
	        </logic:notEmpty>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<!--<logic:notPresent name="TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM">
					<c:if test="${profilo.idTipoUfficio != TIPO_UFFICIO_PROCURA }">
						<td class="td_titolo" colspan="3"><spring:message code="atto.elencoCronologico.cda" /></td>
					</c:if>
					<c:if test="${profilo.idTipoUfficio == TIPO_UFFICIO_PROCURA }">
						<td class="td_titolo" colspan="3"><spring:message code="atto.elencoCronologico" /></td>
					</c:if>
					</logic:notPresent>
					<logic:present name="TIPOPROC_LIQUIDAZIONE_COMPENSI_AMM_CAM">
						<td class="td_titolo" colspan="3"><spring:message code="atto.elencoCronologico" /></td>
					</logic:present>-->
					<td class="td_titolo" colspan="3"><spring:message code="atto.elencoCronologico" /></td>
					<td class="td_titolo" align="right">
						<span class="spanflgNascondiRicevute"><form:checkbox id="flgNascondiRicevute" onclick="modificaFiltroNotifiche()" path="flgNascondiRicevute" label="Nascondi le ricevute" /></span> 
					</td>
				</tr>
			</table>
			<logic:notEmpty name="listaAtti">
			<display:table class="tb_lista" uid="listaAtti" name="${listaAtti}" pagesize="10" requestURI="${paginazione}">
				<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
					<form:radiobutton path="codAtto" value="${listaAtti.codAtto}" />
				</display:column>
				<display:column title="Atto" sortable="false" >
					<table><tr><td valign="middle">${listaAtti.categoriaAtto.descrizione} ${listaAtti.numeroProcedimentoOrigine} ${listaAtti.descVersionamento}</td><td>${listaAtti.descInfoScheda}</td></tr></table> 
				</display:column>
				<display:column title="Numero atto" sortable="false">
					<c:out value="${listaAtti.numeroAtto}" />
				</display:column>
				<display:column property="statoAtto.descrizione" title="Stato" sortable="false" />
				<display:column property="dataDeposito" title="Data di deposito" format="{0,date,dd/MM/yyyy}" sortable="false" />
				
			</display:table>
			 <form:hidden path="idProcedimentoCda" />
     
     		<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" name="Dettaglio" title="Dettaglio" value="Dettaglio" /></td>
					<c:if test="${profilo.ruolo!='MAGISTRATO'}">
					<logic:empty scope="request" name="attoForm" property="idProcedimentoCda">
			
					 <td class="td_tasto"><input type="submit" value="Elimina" name="EliminaAtto" onclick="return confermaEliminaAtto()" ></td>
	                
                    </logic:empty>
                    
                    
                    </c:if>
				</tr>
			</table>
			<br />
			<br />
			</logic:notEmpty>
		</form:form>


<script type="text/javascript">
	function modificaFiltroNotifiche() {
		 $('#idAttoForm').attr('action', '${pageContext.request.contextPath}/procedimento/atto/filtroOrdineCronologicoAtti.htm');
		 $("#idAttoForm").submit();
	}
	function confermaEliminaAtto() {
	 	 $('#idAttoForm').attr('action', '${pageContext.request.contextPath}/procedimento/atti/elencoAttiOrdineCronologico.htm');
		 $('#idEliminaAtto').click();
		return true;
	}
</script>

	</tiles:putAttribute>
</tiles:insertDefinition>