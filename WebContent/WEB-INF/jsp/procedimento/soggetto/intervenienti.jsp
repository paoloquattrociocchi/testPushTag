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
	
		<spring:url var="azione" value="/procedimento/soggetto/visualizzaDirettiInteressati.htm" />
		<spring:url var="paginazione" value="/procedimento/soggetto/visualizzaIntervenienti.htm" />
		
		
		<p class="titolo_page"><spring:message code="soggetto.intervenienti" /></p>
		<form:form method="post" action="${azione}" commandName="soggettoForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<br />
			
			<display:table class="tb_lista" name="${risultatiRicerca}" uid="sogg" pagesize="10" requestURI="${paginazione}" >
				<display:column style="text-align: center; width: 5%">
					<input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}"
						onclick="selectID('${sogg.idSoggetto}', '${sogg.idVersione}', '${sogg.tipoSoggetto}');" />
				</display:column>
				<display:column property="descrizioneSoggettoConVersione" title="Generalità" sortable="false" />
				<c:if test="${procedimento.tipoProcedimento == null || procedimento.tipoProcedimento.id != 9 }">							
					<display:column property="dataIscrizione" title="Data Iscrizione" format="{0,date,dd/MM/yyyy}" sortable="false" />
<%-- 					<c:if test="${procedimento.tipoProcedimento == null || procedimento.tipoProcedimento.id != '8' }"> --%>
<%-- 						<display:column property="misura.descrizioneMisura" title="Misure" sortable="false" /> --%>
<%-- 						<display:column property="misura.cauzione" title="Cauzione" sortable="false" /> --%>
<%-- 						<display:column property="misura.art67" title="Art.67" sortable="false" /> --%>
<%-- 					</c:if> --%>
					<display:column property="flgIntervSoggAssociati" title="Soggetti Associati" sortable="false" />
				</c:if>					
			</display:table>
				
			<form:hidden path="idSoggettoInterveniente" id="idSoggettoInterveniente" />
			<form:hidden path="idSoggetto" id="idSoggetto" />
			<form:hidden path="idVersione" id="idVersione" />
			<form:hidden path="tipoSoggetto" id="tipoSoggetto" />
			<form:hidden path="idRuolo" />

			<br />
			<br />
			
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio"></td>
					<!-- Segnalazione SMP-06 -->
					<c:choose>
						<c:when test="${sessionScope.profilo.idTipoUfficio == 8 }"> <!-- Procura Generale -->
								<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica" disabled="disabled"></td>
					    </c:when>						
						<c:otherwise>
								<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica"></td>
						</c:otherwise>
					</c:choose>					
				    
				    <c:choose>
						<c:when test="${procedimento.tipoProcedimento.id == 18 }">
							<td class="td_tasto"><input type="submit" value="Soggetti associati" name="VisualizzaAssociaSoggetti" disabled="disabled"></td>
							<td class="td_tasto"><input type="submit" value="Beni associati" name="VisualizzaBeniAssociati" disabled="disabled"></td>
						</c:when>
						<c:when test="${procedimento.statoProcedimento.id == 6 || procedimento.statoProcedimento.id == 11 }">
							<td class="td_tasto"><input type="submit" value="Soggetti associati" name="VisualizzaAssociaSoggetti"></td>
							<td class="td_tasto"><input type="submit" value="Beni associati" name="VisualizzaBeniAssociati" disabled="disabled"></td>
						</c:when>
						<c:when test="${procedimento.statoProcedimento.id == 24}">
							<td class="td_tasto"><input type="submit" value="Soggetti associati" name="VisualizzaAssociaSoggetti" disabled="disabled"></td>
							<td class="td_tasto"><input type="submit" value="Beni associati" name="VisualizzaBeniAssociati"></td>
						</c:when>						
						<c:otherwise>
							<td class="td_tasto"><input type="submit" value="Soggetti associati" name="VisualizzaAssociaSoggetti"></td>
							<td class="td_tasto"><input type="submit" value="Beni associati" name="VisualizzaBeniAssociati"></td>
						</c:otherwise>
					</c:choose>
					<c:if test="${sessionScope.profilo.idTipoUfficio!=17}">
					   <c:choose>
						   <c:when test="${procedimento.statoProcedimento.id == 24 || sessionScope.profilo.idTipoUfficio == 8 }">
							<td class="td_tasto"><input type="submit" value="Difensori" name="Difensori" disabled="disabled"></td>						
							</c:when>						
						<c:otherwise>
						    <td class="td_tasto"><input type="submit" value="Difensori" name="Difensori"></td>						
						</c:otherwise>
					   </c:choose>					
					</c:if>
					
					
					<!-- Tipo Proc 12 Fascicolo di gestione disabilitare il pulsante elimina -->
					<c:if test="${procedimento.tipoProcedimento.id != 12 }">
					
					<!-- Tipo Uff 6 CDA disabilitare il pulsante elimina -->
					  <c:if test="${sessionScope.profilo.idTipoUfficio!=6}">
					   <c:choose>
						<c:when test="${procedimento.statoProcedimento.id == 24  || sessionScope.profilo.idTipoUfficio == 8 }">
								<td class="td_tasto"><input type="submit" value="Elimina" name="EliminaInterveniente" disabled="disabled"></td>
					    </c:when>						
						<c:otherwise>
						<c:choose>
							<c:when test="${procedimento.tipoProcedimento.id == 18}">
								<td class="td_tasto"><input type="submit" value="Elimina" name="EliminaInterveniente" disabled="disabled"></td>
							</c:when>
							<c:otherwise>
								<td class="td_tasto"><input type="submit" value="Elimina" name="EliminaInterveniente"></td>
							</c:otherwise>
						</c:choose>					
						</c:otherwise>
					   </c:choose>						  

					  </c:if>	
					</c:if>
				</tr>
				
			</table>
			
		</form:form>
		
		<script type="text/javascript">
			$(document).ready(function() {
				disabilitaSubmitIntervenienti('${disabledViewProc}', '${profilo.idTipoUfficio}', '${procedimento.statoProcedimento.id }', '${procedimento.tipoProcedimento.id}' );
			});

		
			function selectID(idSoggettoInterveniente, idVersione, tipoSoggetto) {
				document.getElementById("idSoggettoInterveniente").value = idSoggettoInterveniente;
				document.getElementById("idSoggetto").value = idSoggettoInterveniente;
				document.getElementById("idVersione").value = idVersione;
				document.getElementById("tipoSoggetto").value = tipoSoggetto;
			}
		</script>
		
		 <c:if test="${profilo.ruolo=='MAGISTRATO'}">
		 <script type="text/javascript">
				$(document).ready(function() {
					disabilitaSubmitIntervenienti('true', '6', '2', '${procedimento.tipoProcedimento.id}' );
				
				});	
		</script>
		  </c:if>
	</tiles:putAttribute>
</tiles:insertDefinition>
