<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="it.giustizia.smp.utility.Constants"%>

<c:set var="ID_RUOLO" value="<%=Constants.ID_RUOLO_INTERVENIENTE%>" />
<c:set var="TIPO_UFFICIO_PROCURA_GENERALE" value="<%=Constants.TIPO_UFFICIO_PROCURA_GENERALE%>"/>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/soggetto/visualizzaDirettiInteressati.htm" />
		<p class="titolo_page">
			<c:choose>
				<c:when test="${soggettoForm.idRuolo == ID_RUOLO}">
					<spring:message code="soggetto.intervenienti.associa" />
				</c:when>
				<c:otherwise>
					<spring:message code="soggetto.soggettidelprocedimento" />
				</c:otherwise>
			</c:choose>
		</p>
		<form:form method="post" action="${azione}" commandName="soggettoForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<br />
			
			<form:hidden path="tipoSoggetto" id="tipoSoggetto" />
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo"><c:out value="${soggettoForm.descrizioneSoggetto}" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettiAssociati" /></td>
				</tr>
			</table>
						
			<display:table class="tb_lista" name="${soggettiAssociati}" uid="sogg" pagesize="10" requestURI="${azione}" >
				<display:column style="text-align: center; width: 5%">
					<input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}"
						onclick="selectID('${sogg.idSoggetto}');" />
				</display:column>
				<display:column property="descrizioneSoggettoConVersione" title="Generalità" sortable="false" />
				<display:column property="note" title="Note" sortable="false" />
			</display:table>
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettiNonAssociati" /></td>
				</tr>
			</table>
						
			<display:table class="tb_lista" name="${soggettiDaAssociare}" uid="sogg" pagesize="10" requestURI="${azione}" >
				<display:column style="text-align: center; width: 5%">
					<input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}"
						onclick="selectID('${sogg.idSoggetto}');" />
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
			
			<form:hidden path="idSoggettoInterveniente" id="idSoggettoInterveniente" />	
			<form:hidden path="idSoggetto" id="idSoggetto" />
			<form:hidden path="idVersione" id="idVersione" />
			<form:hidden path="idRuolo" id="idRuolo" />

			<br />
			<br />
			
			<c:if test="${ !empty soggettiDaAssociare}">
				<table class="tb_form">
					<tr>
						<td>
							<form:label path="note"><spring:message code="note" /></form:label>
						</td>
						<td>
							<form:textarea path="note" cols="250" rows="4" cssStyle="width:98%" />
						</td>
					</tr>
				</table>
				
				<br />
				<br />
			</c:if>
				
			<table class="tb_bottoni" align="center">
				<tr>
				<!-- Segnalazione SMP-06 -->
					<c:if test="${sessionScope.profilo.idTipoUfficio != TIPO_UFFICIO_PROCURA_GENERALE}">
						<logic:notEmpty name="soggettiDaAssociare">
							<td class="td_tasto"><input type="submit" value="Associa" name="AssociaSoggetto"></td>
						</logic:notEmpty>
						<logic:notEmpty name="soggettiAssociati">
							<c:if test="${procedimento.tipoProcedimento.id != 12}">
								<td class="td_tasto"><input type="submit" value="Elimina" name="EliminaAssociazioneSoggetto"></td>
							</c:if>
							
						</logic:notEmpty>					
					</c:if>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
			
		</form:form>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
		<smp:back buttonId="backButton"></smp:back>

		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
		<c:choose>
			<c:when test="${profilo.ruolo=='MAGISTRATO'}">
				 <script type="text/javascript">
					$(document).ready(function() {
						disabilitaSubmitAssociaIntervenienti('true', '6', '2', '${procedimento.tipoProcedimento.id}' );
					
					});

				</script>
			</c:when>
			<c:otherwise>
				<script type="text/javascript">
					$(document).ready(function() {
						disabilitaSubmitAssociaIntervenienti('${disabledViewProc}', '${profilo.idTipoUfficio}', '${procedimento.statoProcedimento.id }', '${procedimento.tipoProcedimento.id}' );
					});
				
					function selectID(idSoggetto) {
						document.getElementById("idSoggetto").value = idSoggetto;
					}
					function disabilitaSubmitAssociaIntervenienti(disabilita,profilo,idStatoProc,tipoProcedimento){
						if(disabilita){
							if(profilo==19 && (idStatoProc!=2 && idStatoProc!=6 && idStatoProc!=11)){
								document.getElementsByName("AssociaSoggetto")[0].disabled=true;
							}
							if(profilo==17 && (idStatoProc!=11)){
								document.getElementsByName("AssociaSoggetto")[0].disabled=true;
							}
							if(profilo==6){
								document.getElementsByName("AssociaSoggetto")[0].disabled=true;
							}		
							//document.getElementsByName("EliminaAssociazioneSoggetto")[0].disabled=true;
						}	
					}
					
				</script>			
			</c:otherwise>
		</c:choose>
	</tiles:putAttribute>
</tiles:insertDefinition>
