<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<%
	AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">

		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>

		<spring:url var="azione" value="/procedimento/atto/decisioneIstanzaCauzione.htm" />
		<p class="titolo_page">
			<spring:message code="cameralini.titolo.sentenza.decisione" />		
		</p>
		<form:form method="post" action="${azione}" commandName="soggettoForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
		<br />
		<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
		<br />

		   
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				
			    <form:hidden path="idSoggetto" />
				<form:hidden path="idVersione" />
				<form:hidden path="tipoSoggetto" />
				<form:hidden path="idAtto" />
				<form:hidden path="schermataDiProvenienza" />
				<form:hidden path="tipologiaPrevenzioneDb.id" />
                <form:hidden path="misura.decisioneMisuraPersonale.descrizione" />
 			
 				<tr>
					<td class="td_titolo" colspan="4"><c:out value="${soggettoForm.descrizioneSoggetto}" /></td>
				</tr>
			</table>
			<br />
			<table class="tb_form" border="0">	
			
            <logic:notEmpty name="soggettoForm" property="misura.cauzione">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.cauzione" /></td>
				</tr>
	
				<c:if test="${fn:length(soggettoForm.decisioneSuDecreto) gt 0}">
				    <tr>
						<td><form:label path="decisioneSuDecreto"><spring:message code="errore.materiale.decisione.Igrado" /></form:label></td>
						<td colspan="3"><c:out value="${soggettoForm.decisioneSuDecreto}" /></td>
					</tr>
				</c:if>	
				
			    <logic:equal scope="session" name="atto" property="categoriaAtto.id" value="40" >	
					 <tr>
						<td><form:label path="misura.decisioneMisuraPersonale.descrizione">
								<spring:message code="soggetto.misurapersonale.decisioneIIGrado" />
							</form:label>
						</td>
						<td colspan="3"><c:out value="${soggettoForm.misura.decisioneMisuraPersonaleIIGrado.descrizione}" /></td>
					 </tr>
			    </logic:equal>					 
			 
				
					<tr>
						<td><form:label path="misura.decisioneMisuraPersonaleSentenzaCassazione.id">
								<spring:message code="soggetto.misurapersonale.sentenzaCassazione" />
							</form:label></td>	
						<td colspan="3">	
							<logic:equal scope="session" name="atto" property="statoAtto.id" value="3" >
						    	<c:out value="${soggettoForm.misura.decisioneMisuraPersonaleSentenzaCassazione.descrizione}" />			
							</logic:equal>
							<logic:notEqual scope="session" name="atto" property="statoAtto.id" value="3" >
								<form:select path="misura.decisioneMisuraPersonaleSentenzaCassazione.id" size="1">
										<form:option value="-1" label="------Seleziona Decisione -------" />
										<form:options items="${esiti}" itemValue="id" itemLabel="descrizione" />
								 </form:select>
							 </logic:notEqual>	
					   </td>						
					</tr>					    
						    
				    <tr>
	
						<td><form:label path="dataRicezioneSentenza" cssClass="required">
								<spring:message code="soggetto.dataRicezioneSentenza" />
							</form:label></td>
						<logic:equal scope="session" name="atto" property="statoAtto.id" value="3" >
							<td colspan="3"><fmt:formatDate value="${soggettoForm.dataRicezioneSentenza}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataRicezioneSentenza" /></td>
						</logic:equal>
						<logic:notEqual scope="session" name="atto" property="statoAtto.id" value="3" >
							<td colspan="3"><form:input path="dataRicezioneSentenza" maxlength="10" id="id_dataRicezioneSentenza" /> <br> <form:errors path="dataRicezioneSentenza" class="testoRosso" /></td>
						</logic:notEqual>
				    </tr>
				    <tr>
						<td><form:label path="numeAnnoSentenza" cssClass="required"><spring:message code="soggetto.numeAnnoSentenza" /></form:label></td>
							<td><form:input path="numeAnnoSentenza" id="numeAnnoSentenza"  disabled="${readOnlyDecisione3Grado}" maxlength="4" size="5"  onkeypress="return isNumberKey(event)" />
								<form:errors path="numeAnnoSentenza" class="testoRosso"/></td>
							
						<td><form:label path="numeSentenza" cssClass="required"><spring:message code="soggetto.numeSentenza" /></form:label></td>
							<td><form:input path="numeSentenza" maxlength="12"  disabled="${readOnlyDecisione3Grado}"  onkeypress="return isNumberKey(event)" />
								<form:errors path="numeSentenza" class="testoRosso"/></td>
					</tr> 			
			  					 			 
		
				</logic:notEmpty>
			
			</table>
			<br />
			<table class="tb_bottoni" align="center">
				<tr>	
					<logic:notEqual scope="session" name="atto" property="statoAtto.id" value="3" >
						<c:if test="${profilo.ruolo!='MAGISTRATO'}">
						<td class="td_tasto"><input type="submit" value="Salva" name="SalvaDecisorioIIIGrado" id="SalvaDecisorioIIIGrado"></td>
				        </c:if>
				    </logic:notEqual >										
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
				
				</tr>
			</table>
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>



	</tiles:putAttribute>
</tiles:insertDefinition>


<script type="text/javascript">

	$(document).ready(function() {
		prendiData("#id_dataRicezioneSentenza",true);
	});
	
	 function isNumberKey(evt)
     {
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode < 48 || charCode > 57)
           return false;

        return true;
     }
</script>