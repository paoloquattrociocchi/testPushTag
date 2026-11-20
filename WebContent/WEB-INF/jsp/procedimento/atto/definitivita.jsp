<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/nuovoDecretoIncompetenza.htm" />
		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>		
		<form:form id="id_formAtto" method="post" action="${azione}" modelAttribute="attoForm">
			<form:hidden path="codAtto" />
			<form:hidden path="categoriaAtto.id" />
			<form:hidden path="tipoAtto.id" />
			<form:hidden path="destinatario.id" />
			<p class="titolo_page">
				<spring:message code="atti.definitivita" />
			</p>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<table class="tb_form">
				<tr>
					<td><form:label path="dataDefinitivita" cssClass="required">
							<spring:message code="atto.datadefinitivita" />
						</form:label></td>
					<td>
					<c:if test="${disableDefinitivita!=true}">
						<form:input id="id_dataDefinitivita" path="dataDefinitivita" maxlength="10" /> <form:errors path="dataDefinitivita" class="testoRosso" />
					</c:if>	
					<c:if test="${disableDefinitivita==true}">
						<fmt:formatDate value="${attoForm.dataDefinitivita}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataDefinitivita" />
					</c:if>
					</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
				<c:if test="${disableDefinitivita!=true}">
					<td>
						<form:label path="definitivitaToAll">
								<spring:message code="atti.datadefinitivita.all" />
						</form:label>
					</td>
					<td>
						<form:checkbox path="definitivitaToAll" />
					</td>
					<td></td>
					<td></td>
				</c:if>	
				<c:if test="${disableDefinitivita==true}">
					<td></td>
					<td></td>
					<td></td>
					<td></td>
				</c:if>					
								
				</tr>
				<c:if test="${ufficiDestinatari!=null}">
					<tr>
						<td><form:label path="destinatario.id">
								<spring:message code="atto.destinazione" />
							</form:label></td>
						<td><c:if test="${sessionScope.procedimento.statoProcedimento.id!=22}">
								<form:select path="destinatario2.id" size="1">
									<form:options items="${ufficiDestinatari}" itemValue="id" itemLabel="descrizione" />
								</form:select>
							</c:if> <c:if test="${sessionScope.procedimento.statoProcedimento.id==22}">
								<c:forEach var="destinat" items="${ufficiListFromFlussiOut}">
									<c:out value="${destinat.descrizione}">
									</c:out>
								</c:forEach>
							</c:if></td>
					</tr>
				</c:if>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto">
						<input type="submit" name="ConfermaDefinitivita" title="Conferma la definitività per l'atto corrente" value="Conferma e Invia" id="ConfermaDefinitivita"> 
					</td>
					<td class="td_tasto">
						<input type="button" value="Indietro" onClick="indietro('/procedimento/atto/copertinaAtto.htm')">
					</td>
				</tr>
			</table>
			<br />
			<br />
		</form:form>

		<script type="text/javascript">
				$(document).ready(function() {					
					prendiData("#id_dataDefinitivita",true);					
				});		
				
				disabilitaDefinitivitaIncompetenza('${disableDefinitivita}');				
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>