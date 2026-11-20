<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="it.giustizia.smp.utility.Constants"%>

<c:set var="TIPO_UFFICIO_CORTE_D_APPELLO" value="<%=Constants.TIPO_UFFICIO_CORTE_D_APPELLO%>"/>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
	
		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
	
		<spring:url var="azione" value="/procedimento/soggettiInteressati/dettaglio.htm" />
		<p class="titolo_page"><spring:message code="soggetto.versioni" /></p>
		<form:form method="post" action="${azione}" commandName="soggettoForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<br />
			<display:table class="tb_lista" name="${risultatiRicerca}" uid="sogg" pagesize="10" requestURI="${azione}" >
				<display:column style="text-align: center; width: 5%">
					<input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}"
						onclick="selectID('${sogg.idSoggetto}', '${sogg.idVersione}', '${sogg.tipoSoggetto}', '${sogg.misura.descrizioneMisura}');" />
				</display:column>
				<display:column property="descrizioneSoggettoConVersione" title="Generalità" sortable="false" />
				<c:if test="${procedimento.tipoProcedimento == null || procedimento.tipoProcedimento.id != 9 }">							
					<display:column property="dataIscrizione" title="Data Iscrizione" format="{0,date,dd/MM/yyyy}" sortable="false" />
					<c:if test="${procedimento.tipoProcedimento == null || procedimento.tipoProcedimento.id != '8' }">
						<display:column property="misura.descrizioneMisura" title="Misure" sortable="false" />
						<display:column property="misura.cauzione" title="Cauzione" sortable="false" />
						<display:column property="misura.art67" title="Art.67" sortable="false" />
					</c:if>
				</c:if>					
			</display:table>

			<form:hidden path="idSoggetto" id="idSoggetto" />
			<form:hidden path="idVersione" id="idVersione" />
			<form:hidden path="tipoSoggetto" id="tipoSoggetto" />
			<form:hidden path="tipoInteresse.id" />
			<form:hidden path="misura.descrizioneMisura" id="tipoMisura" />
			<form:hidden path="descrRuolo" id="descrRuolo" />
			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Dettaglio" name="DettaglioVersione" ></td>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>					
				</tr>
			</table>
		</form:form>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
		<smp:back buttonId="backButton"></smp:back>		
		
		<script type="text/javascript">
			function selectID(idSoggetto, idVersione, tipoSoggetto, tipoMisura) {
				document.getElementById("idSoggetto").value = idSoggetto;
				document.getElementById("idVersione").value = idVersione;
				document.getElementById("tipoSoggetto").value = tipoSoggetto;
				document.getElementById("tipoMisura").value = tipoMisura;
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
