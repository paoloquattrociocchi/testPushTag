<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/dettaglioAtto.htm" />
		<form:form method="post" action="${azione}" commandName="attoForm">
			<p class="titolo_page">
				<spring:message code="atto.dettaglio" />
			</p>
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<!--  <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="tipoAtto.descrizione"><spring:message code="atto.tipo.atto" /></form:label></td>
						<td><c:out value="${attoForm.tipoAtto.descrizione}" /></td>
						<td><form:label path="categoriaAtto.descrizione"><spring:message code="atto.categoria.atto" /></form:label></td>
						<td><c:out value="${attoForm.categoriaAtto.descrizione}" /></td>
					</tr>
					<tr>
						<td><form:label path="statoAtto.descrizione"><spring:message code="atto.stato.atto" /></form:label></td>
						<td><c:out value="${attoForm.statoAtto.descrizione}" /></td>
						<td><form:label path="dataEmissione"><spring:message code="atto.dataemissione" /></form:label></td>
						<td>
						     <fmt:formatDate
								value="${attoForm.dataEmissione}" type="date"
								pattern="dd/MM/yyyy" />
				
					</tr>	-->

			</table>

			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<smp:notificheButtons formId="id_formConfermaInvia" codAtto="${attoForm.codAtto}"></smp:notificheButtons>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
			<br />
			<br />


		</form:form>
		
		<smp:back buttonId="backButton"></smp:back>
	</tiles:putAttribute>
</tiles:insertDefinition>