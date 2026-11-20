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
		<p class="titolo_page">
			<c:if test="${procedimento.tipoProcedimento.id != 17}"> 
				<spring:message code="soggetto.soggettidelprocedimento" />
			</c:if>
			<c:if test="${procedimento.tipoProcedimento.id == 17}"> 
				<spring:message code="atto.amministratore" />
			</c:if>
		</p>
		<form:form method="post" action="${azione}" commandName="soggettoForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<br />
			
			<c:if test="${(procedimento.tipoProcedimento.id == 17 || procedimento.tipoProcedimento.id == 19) && listaIntestatari!=null}"> 
				<display:table id="row" name="${listaIntestatari}" class="tb_lista" uid="amm" >
					<display:column style="text-align: center; width: 5%">
						<input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}"
							onclick="selectID('${amm.CODI_K_ID_INTESTATARIO}', null, null, null);" />
					</display:column>				
					<display:column property="descrizioneAmministratore" title="Generalità" sortable="false"/>
				</display:table>	
			</c:if>
			
			<c:if test="${!((procedimento.tipoProcedimento.id == 17 || procedimento.tipoProcedimento.id == 18 || procedimento.tipoProcedimento.id == 19) && listaIntestatari!=null)}">
			
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
				
			</c:if>				

			<form:hidden path="idSoggetto" id="idSoggetto" />
			<form:hidden path="idVersione" id="idVersione" />
			<form:hidden path="tipoSoggetto" id="tipoSoggetto" />
			<form:hidden path="tipoInteresse.id" />
			<form:hidden path="misura.descrizioneMisura" id="tipoMisura" />
			<br />
			<br />
			
			<%-- modifiche cs eliminati bottoni per 19 --%>
			
			<c:if test="${(procedimento.tipoProcedimento.id != null && procedimento.tipoProcedimento.id != 19 && procedimento.tipoProcedimento.id != 17) || procedimento.tipoProcedimento == null}">
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio"></td>
					<!-- Segnalazione EMS-123 -->
						<c:choose>
							<c:when test="${procedimento.tipoFascicolo.id == 2 && (procedimento.statoProcedimento.id == 19 || procedimento.statoProcedimento.id == 41)}">
								<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica" disabled="true"></td>
							</c:when>
							<c:otherwise>
								<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica"></td>
							</c:otherwise>
						 </c:choose>
						<td class="td_tasto"><input type="submit" value="Riepilogo Soggetto" name="RiepilogoSoggetto"></td>
					 
					<c:if test="${profilo.idTipoUfficio != TIPO_UFFICIO_CORTE_D_APPELLO }">
						<c:if test="${procedimento.tipoProcedimento.id != null && procedimento.tipoProcedimento.id != 13 && procedimento.tipoProcedimento.id != 14  }">
							<c:choose>
								<c:when test="${procedimento.tipoFascicolo.id == 2 && (procedimento.statoProcedimento.id == 19 || procedimento.statoProcedimento.id == 41)}">
									<td class="td_tasto"><input type="submit" value="Elimina" name="CANCELLA" disabled="true"></td>
								</c:when>
								<c:otherwise>
									<td class="td_tasto"><input type="submit" value="Elimina" name="CANCELLA"></td>
								</c:otherwise>
							</c:choose>
					     </c:if>
					</c:if>
			
			<logic:notEqual scope="session" name="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE%>" value="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE_SMP_GIU%>" >
				<c:if test="${procedimento.tipoProcedimento.id != '8' }">
					<c:if test="${(procedimento.tipoProcedimento==null ) || (procedimento.tipoProcedimento.id != null && procedimento.tipoProcedimento.id != 13 && procedimento.tipoProcedimento.id != 14)  }">
						<logic:present name="permessoMisura">
							<td class="td_tasto"><input type="submit" value="Misura Personale" name="MisuraPersonale"></td>
							<td class="td_tasto"><input type="submit" value="Misura Patrimoniale" name="MisuraPatrimoniale"></td>
						</logic:present>
					
						<logic:notPresent name="permessoMisura">
							<td class="td_tasto"><input type="submit" value="Misura Personale" name="MisuraPersonale" disabled="true"></td>
							<td class="td_tasto"><input type="submit" value="Misura Patrimoniale" name="MisuraPatrimoniale" disabled="true"></td>
						</logic:notPresent>
					</c:if>	
				</c:if>
			</logic:notEqual>
			<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE%>" value="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE_SMP_GIU%>" >
					<c:if test="${profilo.idTipoUfficio != TIPO_UFFICIO_CORTE_D_APPELLO }">
						<c:if test="${(procedimento.tipoProcedimento==null ) || (procedimento.tipoProcedimento.id != null && procedimento.tipoProcedimento.id != 13 && procedimento.tipoProcedimento.id != 14)  }">	
							<c:choose>
								<c:when test="${procedimento.statoProcedimento.id == 12 || procedimento.statoProcedimento.id == 42 || procedimento.statoProcedimento.id == 24 || procedimento.statoProcedimento.id == 23}">
									<td class="td_tasto"><input type="submit" value="Misura Personale" name="MisuraPersonale" disabled="disabled"></td>
									<td class="td_tasto"><input type="submit" value="Misura Patrimoniale" name="MisuraPatrimoniale" disabled="disabled"> </td>
								</c:when>
								<c:otherwise>		
									<td class="td_tasto"><input type="submit" value="Misura Personale" name="MisuraPersonale" ></td>
									<c:if test="${(procedimento.tipoProcedimento.id != 14 && procedimento.tipoProcedimento.id != 15) }">
										<td class="td_tasto"><input type="submit" value="Misura Patrimoniale" name="MisuraPatrimoniale"></td>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:if>
					<c:choose>
						<c:when test="${(procedimento.tipoProcedimento.id == 8 &&  procedimento.statoProcedimento.id == 19) ||procedimento.statoProcedimento.id == 5 || procedimento.statoProcedimento.id == 24}">
							<td class="td_tasto"><input type="submit" value="Difensori" name="Difensori" disabled="disabled"></td>
						</c:when>
						<c:otherwise>
							<td class="td_tasto"><input type="submit" value="Difensori" name="Difensori" ></td>
						</c:otherwise>
					</c:choose>
			</logic:equal>

				</tr>
			
			
				
			</table>
			
			
			</c:if>
			<c:if test="${procedimento.tipoProcedimento.id != null && (procedimento.tipoProcedimento.id == 19 || procedimento.tipoProcedimento.id == 17)}">
			<table class="tb_bottoni" align="center">
				<tr>
					<c:if test="${procedimento.tipoProcedimento.id == 17}">
						<td class="td_tasto"><input type="submit" value="Dettaglio" name="DettaglioAmministratore" ></td>
					</c:if>
					<c:if test="${procedimento.tipoProcedimento.id == 19}">
						<td class="td_tasto"><input type="submit" value="Dettaglio" name="DettaglioCorrezioneErrMat"></td>
					</c:if>
					<td class="td_tasto"><input type="submit" value="Difensori" name="Difensori"></td>
				</tr>
				</table>
			
			</c:if>
			<%-- fine modifiche cs eliminati bottoni per 19 --%>
			
			
			
		</form:form>
		
		<script type="text/javascript">
			$(document).ready(function() {
				disabilitaSubmitDirettiInteressati('${disabledViewProc}', '${profilo.idTipoUfficio}', '${procedimento.statoProcedimento.id }', '${procedimento.tipoProcedimento.id}' );
				disabilitaSubmitDirettiInteressatiAccertamento('${procedimento.tipoProcedimento.id }', '${procedimento.statoProcedimento.id }');
			});

		
			function selectID(idSoggetto, idVersione, tipoSoggetto, tipoMisura) {
				document.getElementById("idSoggetto").value = idSoggetto;
				document.getElementById("idVersione").value = idVersione;
				document.getElementById("tipoSoggetto").value = tipoSoggetto;
				document.getElementById("tipoMisura").value = tipoMisura;
			}
			
		</script>
		
		 <c:if test="${profilo.ruolo=='MAGISTRATO'}">
		 <script type="text/javascript">
				$(document).ready(function() {
					disabilitaSubmitDirettiInteressati('true', '6', '2', '${procedimento.tipoProcedimento.id}' );
				
				});	
		</script>
		  </c:if>
	</tiles:putAttribute>
</tiles:insertDefinition>
