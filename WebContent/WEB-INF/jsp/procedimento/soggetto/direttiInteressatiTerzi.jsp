<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="it.giustizia.smp.utility.Constants"%>

<c:set var="TIPO_UFFICIO_PROCURA_GENERALE" value="<%=Constants.TIPO_UFFICIO_PROCURA_GENERALE%>"/>
<c:set var="TIPO_UFFICIO_TRIBUNALE" value="<%=Constants.TIPO_UFFICIO_TRIBUNALE_L%>"/>
<c:set var="TIPO_UFFICIO_CORTE_D_APPELLO" value="<%=Constants.TIPO_UFFICIO_CORTE_D_APPELLO%>"/>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
	
		<spring:url var="azione" value="/procedimento/soggetto/visualizzaDirettiInteressatiTerzi.htm" />
		<p class="titolo_page"><spring:message code="soggetto.soggettidelprocedimentoTerzi" /></p>
		<form:form method="post" action="${azione}" commandName="soggettoForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<br />
			<display:table class="tb_lista" name="${risultatiRicerca}" uid="sogg" pagesize="10" requestURI="${azione}" >
				<display:column style="text-align: center; width: 5%">
					<input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}"
						onclick="selectID('${sogg.idSoggetto}', '${sogg.idVersione}', '${sogg.tipoSoggetto}', '${sogg.descrRuolo }');" />
				</display:column>
				<display:column property="descrizioneSoggettoConVersione" title="Generalità" sortable="false" />
			</display:table>

			<form:hidden path="idSoggetto" id="idSoggetto" />
			<form:hidden path="idVersione" id="idVersione" />
			<form:hidden path="tipoSoggetto" id="tipoSoggetto" />
			<form:hidden path="tipoInteresse.id" />
			<form:hidden path="descrRuolo" id="descrRuolo" />
			<form:hidden path="idRuolo" id="idRuolo" />
			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
				<c:if test="${procedimento.statoProcedimento.id != null && procedimento.statoProcedimento.id != 19  }">
					<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio"></td>
					<c:if test="${(procedimento.tipoProcedimento==null ) || (procedimento.tipoProcedimento.id != null && procedimento.tipoProcedimento.id != 13 && procedimento.tipoProcedimento.id != 14)  }">
					    <c:if test="${procedimento.tipoProcedimento!=null && procedimento.tipoProcedimento.id == 18}">
					      <td class="td_tasto"><input type="submit" value="Modifica" name="Modifica" disabled="disabled"></td>
					      <c:if test="${(profilo.idTipoUfficio != TIPO_UFFICIO_CORTE_D_APPELLO)}">
					      	<td class="td_tasto"><input type="submit" value="Elimina" name="CANCELLA" disabled="disabled"></td>					    
					      </c:if>
					    </c:if>
					    <c:if test="${procedimento.tipoProcedimento!=null && procedimento.tipoProcedimento.id != 18}">
					      <td class="td_tasto"><input type="submit" value="Modifica" name="Modifica" ></td>
						     <c:choose>
								<c:when test="${(profilo.idTipoUfficio == TIPO_UFFICIO_PROCURA_GENERALE) || (profilo.idTipoUfficio == TIPO_UFFICIO_TRIBUNALE && procedimento.tipoFascicolo.id == 1 && procedimento.statoProcedimento.id == 2)}">
									<c:if test="${(profilo.idTipoUfficio != TIPO_UFFICIO_CORTE_D_APPELLO)}">
										<td class="td_tasto"><input type="submit" value="Elimina" name="CANCELLA" disabled="disabled"></td>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:if test="${(profilo.idTipoUfficio != TIPO_UFFICIO_CORTE_D_APPELLO)}">
										<td class="td_tasto"><input type="submit" value="Elimina" name="CANCELLA" ></td>
									</c:if>
								</c:otherwise>
							</c:choose>
					    </c:if>
					</c:if>
					   <c:choose>
						<c:when test="${procedimento.statoProcedimento.id == 24 }">
				        	<td class="td_tasto"><input type="submit" value="Beni Collegati" name="BeniCollegati"></td>
					    </c:when>						
						<c:otherwise>
						
						    <c:if test="${procedimento.tipoProcedimento!=null && procedimento.tipoProcedimento.id == 18}">
								<td class="td_tasto"><input type="submit" value="Beni Collegati" name="BeniCollegati" disabled="disabled"></td>				    
						    
						    </c:if>
						    <c:if test="${procedimento.tipoProcedimento!=null && procedimento.tipoProcedimento.id != 18}">
								<td class="td_tasto"><input type="submit" value="Beni Collegati" name="BeniCollegati"></td>				    
						    
						    </c:if>						
					       
					
						</c:otherwise>
					   </c:choose>						
					
			     	<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE%>" value="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE_SMP_GIU%>" >
						<logic:notPresent name="disattivaDifensore">
						   <c:choose>
							<c:when test="${procedimento.statoProcedimento.id == 24 }">
					        	<td class="td_tasto"><input type="submit" value="Difensori" name="Difensori" disabled="disabled"></td>
						    </c:when>						
							<c:otherwise>
						      <td class="td_tasto"><input type="submit" value="Difensori" name="Difensori"></td>
						
							</c:otherwise>
						   </c:choose>									
		
						</logic:notPresent>
						<logic:present name="disattivaDifensore">
							<td class="td_tasto"><input type="submit" value="Difensori" name="Difensori" disabled="true"></td>
						</logic:present>
				   </logic:equal>
				</c:if>	
				
				<c:if test="${procedimento.statoProcedimento.id != null && procedimento.statoProcedimento.id == 19  }">
					<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio"></td>
					<c:if test="${(procedimento.tipoProcedimento==null ) || (procedimento.tipoProcedimento.id != null && procedimento.tipoProcedimento.id != 13 && procedimento.tipoProcedimento.id != 14)  }">
						<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica" disabled="disabled"></td>
						<c:if test="${(profilo.idTipoUfficio != TIPO_UFFICIO_CORTE_D_APPELLO)}">
							<td class="td_tasto"><input type="submit" value="Elimina" name="CANCELLA" disabled="disabled"></td>
						</c:if>
					</c:if>
					<td class="td_tasto"><input type="submit" value="Beni Collegati" name="BeniCollegati" disabled="disabled"></td>
									
					<td class="td_tasto"><input type="submit" value="Difensori" name="Difensori" disabled="disabled"></td>
				
				</c:if>	
				
				</tr>
				
			</table>
		</form:form>
		<script type="text/javascript">
			$(document).ready(function() {
				disabilitaSubmitDirettiInteressatiTerzi('${disabledViewProc}', '${profilo.idTipoUfficio}', '${procedimento.statoProcedimento.id }' );
			});

		
			function selectID(idSoggetto, idVersione, tipoSoggetto, descrRuolo) {
				document.getElementById("idSoggetto").value = idSoggetto;
				document.getElementById("idVersione").value = idVersione;
				document.getElementById("tipoSoggetto").value = tipoSoggetto;
				document.getElementById("descrRuolo").value = descrRuolo;
			}
		</script>
		
		 <c:if test="${profilo.ruolo=='MAGISTRATO'}">
		 <script type="text/javascript">
				$(document).ready(function() {
					disabilitaSubmitDirettiInteressatiTerzi('true', '6', '2' );
				
				});	
		</script>
		  </c:if>
	</tiles:putAttribute>
</tiles:insertDefinition>