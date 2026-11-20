<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%
	String fromMenuAtti = (String) request.getSession().getAttribute(
			"fromMenuAtti");
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/richiestaParere.htm" />
		<form:form method="post" id="id_formConfermaInvia" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			<form:hidden path="codAtto" />
			<p class="titolo_page">
				<c:choose>
					<c:when test="${attoForm.categoriaAtto.id == 330}">
						<spring:message code="atto.invioProcura" />
					</c:when>
					<c:otherwise>
						<spring:message code="atto.invio" />
					</c:otherwise>
				</c:choose>
			</p>

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
   		    <jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />


			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>
				<c:if test="${attoForm.statoAtto.id != 3 && attoForm.statoAtto.id != 4 }">
					<tr>
					    <c:if test="${attoForm.categoriaAtto.id == 330}">					
						<td><form:label path="dataEmissione">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td><form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" /> 
                             <form:errors path="dataEmissione" class="testoRosso" />					
						</td>
						<td><form:label path="dataDeposito">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><form:input path="dataDeposito" maxlength="10" id="id_dataDeposito" /> 
						    <form:errors path="dataDeposito" class="testoRosso" />
						</td>
	                    </c:if>
					    <c:if test="${attoForm.categoriaAtto.id != 330}">					
						<td><form:label path="dataEmissione">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td><form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" /> 
						<form:errors path="dataEmissione" class="testoRosso" /></td>
						<td><form:label path="dataDeposito">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><form:input path="dataDeposito" maxlength="10" id="id_dataDeposito" /> 
						<form:errors path="dataDeposito" class="testoRosso" /></td>
	                    </c:if>
	                    
					</tr>
					<c:if test="${attoForm.categoriaAtto.id != 330}">
					<tr style="display: none">
						<td><form:label path="destinatario.id">
								<spring:message code="atto.destinazione" />
							</form:label></td>
						<td><c:if test="${attoForm.statoAtto.id != 3 && attoForm.statoAtto.id != 4}">
								<table>
									<c:forEach var="destinat" items="${ufficiList}">
										<tr>
											<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
										</tr>
									</c:forEach>
								</table>
							</c:if></td>
						<td><form:label path="dataRegistrazione">
								<spring:message code="atto.dataregistrazione" />
							</form:label></td>
						<td><fmt:formatDate value="<%=new java.util.Date()%>" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>
					</c:if>
				</c:if>
				<c:if test="${attoForm.statoAtto.id == 3 || attoForm.statoAtto.id == 4 }">
					<tr>
						<td><form:label path="dataEmissione">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden 

path="dataEmissione" /></td>
						<td><form:label path="dataDeposito">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>
					<c:if test="${attoForm.categoriaAtto.id != 330}">
					<tr>
						<c:if test="${ufficiListFromFlussiOut!=null && fn:length(ufficiListFromFlussiOut)>0}">
							<td><form:label path="destinatario.id">
									<spring:message code="atto.destinazione" />
								</form:label></td>
							<td>
								<table>
									<c:forEach var="destinat" items="${ufficiListFromFlussiOut}">
										<tr>
											<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
										</tr>
									</c:forEach>
								</table>
							</td>
						</c:if>
						<c:if test="${!(ufficiListFromFlussiOut!=null && fn:length(ufficiListFromFlussiOut)>0)}">
							<td></td>
							<td></td>
						</c:if>
						<td><form:label path="dataRegistrazione">
								<spring:message code="atto.dataregistrazione" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" />
					</tr>
					</c:if>
				</c:if>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<c:if test="${attoForm.statoAtto.id != 3 && attoForm.statoAtto.id != 4 }">

						<c:if test="${profilo.ruolo!='MAGISTRATO'}">
						
  						    <c:if test="${attoForm.categoriaAtto.id == 330}">
						       <td class="td_tasto"><input type="button" value="Salva" onclick="inserisciAtto('Conferma', 
'${attoForm.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" /></td> 
						       <input id="Conferma" type="submit" name="Conferma" value="" style="display: none" />
						    </c:if>
												
							<td class="td_tasto"><input type="button" value="Conferma e Invia" onclick="inserisciAtto('invia', 

'${attoForm.allegato.documentoFirmato}')" title="Invia" class="nowait" /></td>
							<input id="invia" type="submit" name="Invia" value="" style="display: none" />
			
						</c:if>
					</c:if>
					<td class="td_tasto"><c:if test="${fromMenuAtti=='elenco'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
						</c:if> <c:if test="${fromMenuAtti=='ordine'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
						</c:if> <c:if test="${fromMenuAtti=='nuovo'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/nuovo.htm')">
						</c:if></td>
				</tr>
			</table>
		</form:form>

		<script type="text/javascript">
			$(document).ready(function() {
				prendiData("#id_dataDeposito", true);
				prendiData("#id_dataEmissione", true);
			});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>