<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/dettaglioAtto.htm" />
		<form:form method="post" id="id_formConfermaInvia" action="${azione}" commandName="attoForm">
			<p class="titolo_page">
				<spring:message code="atto.dettaglio" />
			</p>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />

			<c:if test="${attoForm.statoAtto.id==6}">
				<br />
				<form:hidden path="codAtto" />
				
				<table class="tb_form">
					<tr>
						<td width="10%"><form:label path="dataEmissione">
								<spring:message code="atto.dataemissione" />
							</form:label>
						</td>
						<td width="15%"><fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataEmissione" /></td>
						<td width="25%">&nbsp;</td>
						<td width="25%">&nbsp;</td>
					</tr>
					<tr>
						<td width="10%"><form:label path="dataDeposito">
								<spring:message code="stralcio.magistrato" />
							</form:label>
						</td>
						<td width="15%"><c:out value="${descrizioneMagistrato}" /></td>
						<td width="25%">&nbsp;</td>
						<td width="25%">&nbsp;</td>
					</tr>
				</table>
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
			</c:if>
			<br/>
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="button" id="id_buttonIndietro" value="Indietro"></td>
				</tr>
			</table>
		</form:form>
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="id_buttonIndietro"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>