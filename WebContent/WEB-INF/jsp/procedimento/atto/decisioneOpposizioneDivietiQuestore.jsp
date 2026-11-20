<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<%
	AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content" >
			<spring:url var="azione" value="/procedimento/soggetto/decisioneErrore.htm" />
			<p class="titolo_page">
				<spring:message code="atto.istanzaSoggettoDiffidato.decisioneOpposizione" />
			</p>
			<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
			<form:form method="post" action="${azione}" commandName="soggettoForm" >

				<form:hidden path="idSoggetto" />
				<form:hidden path="tipoSoggetto"/>

				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />

				<br/>	
				<br/>
				
				<table class="tb_form">				
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="atto.istanzaSoggettoDiffidato.decisioneOpposizione" /></td>
						</tr>
						
						<tr>
							<td><form:label path="decisioneSuDecreto"><spring:message code="errore.materiale.decisione.Igrado" /></form:label></td>
							<td colspan="3"><form:input path="decisioneSuDecreto" maxlength="30" disabled="true" cssStyle="width:50%"/></td>
						</tr>
						<tr>
							<td><form:label path="decisioneErroreMaterialeIIIGrado.id" cssClass="required">
								<spring:message code="errore.materiale.decisione.IIIgrado" />
								</form:label>
							</td>
							<td colspan="3">
								<form:select path="decisioneErroreMaterialeIIIGrado.id" size="1" disabled="${readOnlyDecisione3Grado}" >
									<form:option value="-1" label="------Seleziona Decisione -------" />
									<form:options items="${esitiDecisione3}"  itemValue="id" itemLabel="descrizione" /> 	
								</form:select>
								<form:errors path="decisioneErroreMaterialeIIIGrado.id" class="testoRosso"/>
							</td>
						</tr>
						
					    <tr>
							<td>
								<form:label path="dataRicezioneSentenza" cssClass="required">
									<spring:message code="soggetto.dataRicezioneSentenza" />
								</form:label>
							</td>
							<td>
									<form:input path="dataRicezioneSentenza" maxlength="10" id="id_dataRicezioneSentenza" />
							 	<br> <form:errors path="dataRicezioneSentenza" class="testoRosso" />
							</td>
					    </tr>
					    
					    <tr>
							<td><form:label path="numeAnnoSentenza" cssClass="required"><spring:message code="soggetto.numeAnnoSentenza" /></form:label></td>
							<td><form:input path="numeAnnoSentenza" id="numeAnnoSentenza" maxlength="4" disabled="${readOnlyDecisione3Grado}" size="5" onkeypress="return isNumberKey(event)" />
								<form:errors path="numeAnnoSentenza" class="testoRosso"/></td>
								
							<td><form:label path="numeSentenza" cssClass="required"><spring:message code="soggetto.numeSentenza" /></form:label></td>
							<td><form:input path="numeSentenza" maxlength="12" disabled="${readOnlyDecisione3Grado}" onkeypress="return isNumberKey(event)" />
								<form:errors path="numeSentenza" class="testoRosso"/></td>
						</tr> 			
										   										
					</table>
					<br/>
					<br/>	
				<table class="tb_form">	
				
			</table>							
				
				<br/>
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
					<logic:notEqual value="3" name="atto" property="statoAtto.id" scope="session">
					    <td class="td_tasto">
							<input type="submit" value="Salva" name="SalvaDecisioneDivietiQuestore">	
						</td>	
					</logic:notEqual>		

						<td class="td_tasto">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atto/copertinaAtto.htm')">
						</td>
	
					</tr>
				</table>
				
			</form:form>	
					<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
				<smp:back buttonId="id_buttonIndietro"></smp:back>	


		<script type="text/javascript">
			   function isNumberKey(evt)
		       {
		          var charCode = (evt.which) ? evt.which : evt.keyCode;
		          if (charCode < 48 || charCode > 57)
		             return false;

		          return true;
		       }
			   
			   $(document).ready(function() {
					prendiData("#id_dataRicezioneSentenza",true);
			   });
				
		</script>	
			
				
		</tiles:putAttribute>
	</tiles:insertDefinition>			