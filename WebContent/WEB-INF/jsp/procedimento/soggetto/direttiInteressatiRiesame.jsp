<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>



<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
	
		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
			
		<spring:url var="azione" value="/procedimento/soggetto/visualizzaDirettiInteressatiRiesame.htm" />
		<p class="titolo_page"><spring:message code="procedimento.riesameProvvedimentiQuestore.soggetto.richiedente" /></p>
		<form:form method="post" action="${azione}" commandName="soggettoForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<br />
			<display:table class="tb_lista" name="${risultatiRicerca}" uid="sogg" pagesize="10" requestURI="${azione}" >
				<display:column style="text-align: center; width: 5%">
					<input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}" checked="checked" 
						onclick="selectID('${sogg.idSoggetto}', '${sogg.idVersione}', '${sogg.tipoSoggetto}');" />
				</display:column>
				<display:column property="descrizioneSoggetto" title="Generalità" sortable="false" />
				<display:column property="dataIscrizione" title="Data Iscrizione" format="{0,date,dd/MM/yyyy}" sortable="false" />
			</display:table>

			<form:hidden path="idSoggetto" id="idSoggetto" value="${sogg.idSoggetto}"/>
			<form:hidden path="idVersione" id="idVersione" value="${sogg.idVersione}"/>
			<form:hidden path="tipoSoggetto" id="tipoSoggetto" value="${sogg.tipoSoggetto}"/>
			<form:hidden path="tipoInteresse.id" />
			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio"></td>
					<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica"></td>
					<!-- Segnalazione EMS-68 -->
					<c:choose>
						<c:when test="${procedimento.tipoProcedimento.id == 11}">
							<td class="td_tasto"><input type="button" value="Riepilogo Soggetto" name="RiepilogoSoggetto" disabled="disabled"></td>
						</c:when>
						<c:otherwise>
							<td class="td_tasto"><input type="button" value="Riepilogo Soggetto" name="RiepilogoSoggetto" ></td>
						</c:otherwise>
					</c:choose>
					<td class="td_tasto"><input type="submit" value="Difensori" name="Difensori"></td>
				</tr>
			</table>
		</form:form>
		<script>

			$(document).ready(function() {
				disabilitaSubmitDirettiInteressatiRiesame('${disabledViewProc}', '${profilo.idTipoUfficio}', '${procedimento.statoProcedimento.id }');
			});
					
			function selectID(idSoggetto, idVersione, tipoSoggetto) {
				document.getElementById("idSoggetto").value = idSoggetto;
				document.getElementById("idVersione").value = idVersione;
				document.getElementById("tipoSoggetto").value = tipoSoggetto;
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
