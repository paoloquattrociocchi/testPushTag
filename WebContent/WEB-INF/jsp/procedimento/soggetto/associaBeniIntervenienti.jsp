<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>
<%@page import="it.giustizia.smp.form.AttoForm"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">

		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>

		<spring:url var="azione"
			value="/procedimento/soggetto/visualizzaDirettiInteressati.htm" />
		<p class="titolo_page">
			<spring:message code="bene.benidelprocedimento" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneSoggettoForm">
	     
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br/>

			<form:hidden path="soggetto.idSoggettoInterveniente" />

			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.beni.beniAssociati" /></td>
				</tr>
			</table>
						
			<display:table class="tb_lista" uid="beniAssociati" name="${beniAssociati}" pagesize="10" requestURI="${azione}" >
				<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">						
					<form:checkbox id="listaIdCheckati" path="listaIdCheckati" value="${beniAssociati.idBene}" />
				</display:column>					
				<display:column property="idBene" title="Identificativo" sortable="false" />
				<display:column property="descrizioneBene" title="Descrizione" sortable="false" />
				<c:if test="${procedimento.tipoProcedimento == null || (procedimento.tipoProcedimento.id != 9 && procedimento.tipoProcedimento.id != 10)}">						
					<display:column property="titoliDiDiritto" title="Titoli di Diritto" sortable="false" />
				</c:if>
			</display:table>
			
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>					
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
				</tr>
			</table>

		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>
			
		<script type="text/javascript">
			$(document).ready(function() {
				disabilitaSubmitAssociaBeniIntervenienti('${disabledViewProc}', '${profilo.idTipoUfficio}', '${procedimento.statoProcedimento.id }', '${procedimento.tipoProcedimento.id}' );
			});

		
			function selectID(idSoggetto) {
				document.getElementById("idSoggetto").value = idSoggetto;
			}
		</script>
		
		 <c:if test="${profilo.ruolo=='MAGISTRATO'}">
			 <script type="text/javascript">
					$(document).ready(function() {
						disabilitaSubmitAssociaBeniIntervenienti('true', '6', '2', '${procedimento.tipoProcedimento.id}' );
					
					});	
			</script>
		  </c:if>
		  		
	</tiles:putAttribute>
</tiles:insertDefinition>
