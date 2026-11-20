<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/udienza/dettaglioUdienza.htm" />
		<p class="titolo_page">
			<spring:message code="udienza.titolo" />	
		</p>
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="udienzaForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
		<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<br />
			<table class="tb_form" border=0>
			
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4">
							Dati ricorso per cassazione
						</td>
					</tr>	
					
					  <tr>
						<td width="15%"><form:label path="dataUdienza"><spring:message code="udienza.data"/></form:label></td>
						<td width="35%"><fmt:formatDate value="${udienzaForm.dataUdienza}" type="date" pattern="dd/MM/yyyy" /></td>
						<td width="15%"><form:label path=""><spring:message code="udienza.ora.inizio"/></form:label></td>
						<td width="35%"><c:out value="${udienzaForm.oraInizioUdienzaStr}"/></td>
						<!-- 
						<td width="15%"><form:label path=""><spring:message code="udienza.ora.fine"/></form:label></td>
						<td width="10%"><c:out value="${udienzaForm.oraFineUdienzaStr}" /></td>
						 -->
					  </tr>
					  <tr>
					  	<!-- 
					    <td><form:label path="indirizzoAulaUdienza"><spring:message code="indirizzo"/></form:label></td>
						<td><c:out value="${udienzaForm.indirizzoAulaUdienza}" /></td>
						 -->
						<td><form:label path="aulaUdienza"><spring:message code="atto.udienza.aula"/></form:label></td>
						<td colspan="3"><c:out value="${udienzaForm.aulaUdienza}"/></td>
					 </tr>			

					 <tr>
					    <!-- <td ><form:label path=""><spring:message code="note"/></form:label></td> -->
						<td colspan="4"><c:out value="${udienzaForm.note}" /></td>
					 </tr>
				</table>
				
	

			<br />
			<table class="tb_bottoni" align="center">
				<tr>
				
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>

				</tr>
			</table>
		</form:form>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
		
	</tiles:putAttribute>
</tiles:insertDefinition>