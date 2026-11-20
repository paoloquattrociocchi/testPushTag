<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
<%
	AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>


<style type="text/css">
			.spanPaginazione label {
				
				color: color: #1c5791;
				font-weight: bold;
				font-family: Calibri, tahoma;
			}
</style>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content" >
			<p class="titolo_page"> <spring:message code="atto.impugnazione" />
			</p>
			<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />

			<br />	
			<br />

			<spring:url var="azione" value="/procedimento/atto/impugnazioneLiquidazioneAmm.htm" />
			<form:form method="post" action="${azione}" commandName="beneSoggetto" id="idbeneSoggettoForm">

				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="3"><spring:message code="bene.benidelprocedimento" /></td>
					</tr>
				</table>
				
				<display:table class="tb_lista" uid="bene" name="${beniInAmministrazione}" >
					<display:column property="idBene" title="Identificativo" sortable="false"/>
					<display:column property="descrizioneBene" title="Descrizione" sortable="false"/>
				</display:table>

				<br/>
				<br/>
						
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="bene.impugnazione.decisione" /></td>
					</tr>
					<tr>
						<td width="20%"><form:label path="decisioneBeni.id">
							<spring:message code="bene.impugnazione.decisione" />
							</form:label>
						</td>
						<td colspan="3">
							<form:select path="decisioneBeni.id" size="1" disabled="true">
								<form:option value="-1" label="------Seleziona Decisione -------" />
								<form:options items="${esitiDecisioneBeni}" itemValue="id" itemLabel="descrizione" /> 	
							</form:select>
						</td>
						
						<form:hidden path="decisioneBeni.id" />
						<form:hidden path="motivazione1"/>
						<form:hidden path="importo1"/>
						<c:if test="${sessionScope.procedimento.tipoProcedimento.id==17}" >
						<tr>
							<td><form:label path="motivazione1">
								<spring:message code="errore.materiale.motivazione" />
								</form:label>
							</td>
							<td colspan="3">
								<form:textarea path="motivazione1" rows="2" cssStyle="width:98%" disabled="true"/>
								<form:errors path="motivazione1" class="testoRosso" />
							</td>
						</tr>		
						
						<tr>
							<td><form:label path="importo1">
								<spring:message code="liquidazioneamm.importo" />
								</form:label>
							</td>
							<td colspan="3">
								<form:input path="importo1" maxlength="11" size="20"  disabled="true"/>
								<form:errors path="importo1" class="testoRosso" />
							</td>
						</tr>							
						</c:if>										
					</tr>
					
				</table>
				
				<table class="tb_form">	
					<tr>
						<td colspan="4"></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="liquidazioneamm.impugnazioneCda" /></td>
					</tr>	
					<tr>
						<td width="20%">
							<form:label path="dataPresentazioneCda" >
								<spring:message code="liquidazioneamm.datapresentazione" />
							</form:label></td>
						<td>
							<form:input path="dataPresentazioneCda" maxlength="10" id="id_dataPresentazioneCda" disabled="${disabilita}"/> <form:errors path="dataPresentazioneCda" class="testoRosso" />
						</td>
						
						<td>
							<form:label path="dataDepositoCda" >
								<spring:message code="atto.datadeposito" />
							</form:label>
						</td>
						
						<td>
							<form:input path="dataDepositoCda" maxlength="10" id="id_dataDepositoCda" disabled="${disabilita}" /> <form:errors path="dataDepositoCda" class="testoRosso" />
						</td>						
					</tr>
					<tr>
						<td width="20%">
							<form:label path="dataDecisione" >
								<spring:message code="liquidazioneamm.datadecisione" />
							</form:label></td>
						<td>
							<form:input path="dataDecisione" maxlength="10" id="id_dataDecisione" disabled="${disabilita}"/>
							<form:errors path="dataDecisione" class="testoRosso" />
						</td>
						
						<td>
							<form:label path="dataDepositoDecisione" >
								<spring:message code="atto.datadepositodecisione" />
							</form:label>
						</td>
						
						<td>
							<form:input path="dataDepositoDecisione" maxlength="10" id="id_dataDepositoDecisione" disabled="${disabilita}"/>
							<form:errors path="dataDepositoDecisione" class="testoRosso" />
						</td>						
					</tr>
					<tr>
						<td><form:label path="motivazione2">
							<spring:message code="liquidazioneamm.esito" />
							</form:label>
						</td>
						<td colspan="3">
							<form:textarea path="motivazione2" disabled="${disabilita}" rows="2" cssStyle="width:98%" /> <form:errors path="motivazione2" class="testoRosso" />
						</td>
					</tr>		
					
					<tr>
						<td>
							<form:label path="importo2">
								<spring:message code="liquidazioneamm.importo" />
							</form:label>
						</td>
						<td colspan="3">
							<form:input path="importo2" maxlength="15" size="20" disabled="${disabilita}" /> <form:errors path="importo2" class="testoRosso" />
						</td>
					</tr>	
					
					<tr>
						<td>
							<form:label path="dataDefinitivitaCda" >
								<spring:message code="atto.datadefinitivita" />
							</form:label>
						</td>
						<td colspan="3">
							<form:input path="dataDefinitivitaCda" maxlength="10" id="id_dataDefinitivitaCda" disabled="${disabilita}" /> <form:errors path="dataDefinitivitaCda" class="testoRosso" />
						</td>
					</tr>

					<tr>
						<td colspan="4"></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="liquidazioneamm.impugnazioneCass" /></td>
					</tr>
										
					<tr>
						<td width="20%">
							<form:label path="dataPresentazioneCass" >
								<spring:message code="liquidazioneamm.datapresentazione" />
							</form:label></td>
						<td>
							<form:input path="dataPresentazioneCass" maxlength="10" id="id_dataPresentazioneCass" disabled="${disabilita}"/> <form:errors path="dataPresentazioneCass" class="testoRosso" />
						</td>
						
						<td>
							<form:label path="dataDepositoCass" >
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td>
							<form:input path="dataDepositoCass" maxlength="10" id="id_dataDepositoCass" disabled="${disabilita}"/> <form:errors path="dataDepositoCass" class="testoRosso" />
						</td>						
					</tr>


					<tr>
						<td width="20%">
							<form:label path="dataSentenza" >
								<spring:message code="liquidazioneamm.datasentenza" />
							</form:label></td>
						<td>
							<form:input path="dataSentenza" maxlength="10" id="id_dataSentenza" disabled="${disabilita}" />
							<form:errors path="dataSentenza" class="testoRosso" />
						</td>
						
						<td>
							<form:label path="dataDepositoSentenza" >
								<spring:message code="atto.datadepositosentenza" />
							</form:label></td>
						<td>
							<form:input path="dataDepositoSentenza" maxlength="10" id="id_dataDepositoSentenza" disabled="${disabilita}" />
							<form:errors path="dataDepositoSentenza" class="testoRosso" />
						</td>						
					</tr>












						
					<tr>
						<td><form:label path="motivazione3">
							<spring:message code="liquidazioneamm.esito" />
							</form:label>
						</td>
						<td colspan="3">
							<form:textarea path="motivazione3" rows="2" cssStyle="width:98%" disabled="${disabilita}" /> <form:errors path="motivazione3" class="testoRosso" />
						</td>
					</tr>		
					
					<tr>
						<td><form:label path="importo3">
							<spring:message code="liquidazioneamm.importo" />
							</form:label>
						</td>
						<td colspan="3">
							<form:input path="importo3" maxlength="15" size="20" disabled="${disabilita}" /> <form:errors path="importo3" class="testoRosso" />
						</td>
					</tr>	
					
					<tr>
						<td>
							<form:label path="dataDefinitivitaCass" >
								<spring:message code="atto.datadefinitivita" />
							</form:label></td>
						<td colspan="3">
							<form:input path="dataDefinitivitaCass" maxlength="10" id="id_dataDefinitivitaCass" disabled="${disabilita}" /> <form:errors path="dataDefinitivitaCass" class="testoRosso" />
						</td>
					</tr>
					
				</table>				
				
				<br/>
				<br />

				<table class="tb_bottoni" align="center">
					<tr>
					<c:if test="${profilo.idTipoUfficio!=17 && profilo.idTipoUfficio!=8}">
						<td class="td_tasto">
						
							<c:if test="${profilo.ruolo!='MAGISTRATO' && sessionScope.procedimento.statoProcedimento.id!=22}">
								<input type="submit" value="Salva" name="SalvaImpugnazione">
							</c:if>	
							<c:if test="${profilo.ruolo=='MAGISTRATO' || sessionScope.procedimento.statoProcedimento.id==22}">
								<input type="submit" value="Salva" name="SalvaImpugnazione" disabled="disabled">
							</c:if>				
						</td>
					 </c:if>
						
						<td class="td_tasto">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atto/copertinaAtto.htm')">
						</td>
													
					</tr>
				</table>
				
			</form:form>	
		
		</tiles:putAttribute>
	</tiles:insertDefinition>

<c:if test="${profilo.idTipoUfficio!=17 && profilo.idTipoUfficio!=8}">
 <script type="text/javascript">	


	$(document).ready(function() {
		prendiData("#id_dataPresentazioneCda",true);
		prendiData("#id_dataDepositoCda",true);
		prendiData("#id_dataDefinitivitaCda",true);
		prendiData("#id_dataPresentazioneCass",true);
		prendiData("#id_dataDepositoCass",true);
		prendiData("#id_dataDefinitivitaCass",true);
		prendiData("#id_dataDecisione",true);
		prendiData("#id_dataDepositoDecisione",true);
		prendiData("#id_dataSentenza",true);
		prendiData("#id_dataDepositoSentenza",true);
	});
 </script>
</c:if> 