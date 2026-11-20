<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<body onload="abilita()">
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/decretoRestituzioneBeni.htm" />
		<form:form method="post" id="id_formConfermaInvia" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			<form:hidden path="codAtto" />
			<p class="titolo_page">
				<spring:message code="atto.invio" />
			</p>

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>			
				<tr>
				
				  <c:if test="${attoForm.statoAtto.id != 3}">				
						<td width="20%">
						    <form:checkbox path="restituzionePerEquivalente" id="id_restituzionePerEquivalente" onclick="abilita()"/>  
						    <form:label path="restituzionePerEquivalente"><spring:message code="bene.restituzione.equivalente" /></form:label>
						</td>
						<td>    
						   <form:textarea path="note" id="id_note" cssStyle="width:98%" rows="3" />
						   <form:errors path="note" class="testoRosso" />
					    </td>
				  </c:if>	
				  <c:if test="${attoForm.statoAtto.id == 3}">				
						<td width="20%">
						    <form:checkbox path="restituzionePerEquivalente" id="id_restituzionePerEquivalente" disabled="true"/>  
						    <form:label path="restituzionePerEquivalente"><spring:message code="bene.restituzione.equivalente" /></form:label>
						</td>
						<td>  
						   <c:out value="${attoForm.note}" />  
					    </td>
				  </c:if>				  
				      	
				</tr>		                
	        </table>      
			<br/>
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
		
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>

				<c:if test="${attoForm.statoAtto.id != 3}">
					<tr>
						<td><form:label path="dataEmissione" cssClass="required">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td><form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" /> <form:errors path="dataEmissione" class="testoRosso" /></td>
						<td><form:label path="dataDeposito" cssClass="required">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><form:input path="dataDeposito" maxlength="10" id="id_dataDeposito" /> <form:errors path="dataDeposito" class="testoRosso" /></td>
					</tr>
					<tr style="display:none">
						<td><form:label path="destinatario.id">
								<spring:message code="atto.destinazione" />
							</form:label></td>
						<td>
								<table>
									<c:forEach var="destinat" items="${ufficiList}">
										<tr>
											<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
										</tr>
									</c:forEach>
								</table>
							</td>
						<td><form:label path="dataRegistrazione">
								<spring:message code="atto.dataregistrazione" />
							</form:label></td>
						<td><fmt:formatDate value="<%=new java.util.Date() %>" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>
				</c:if>
				<c:if test="${attoForm.statoAtto.id == 3}">
					<tr>
						<td><form:label path="dataEmissione">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataEmissione" /></td>
						<td><form:label path="dataDeposito">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>
					<tr>
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
						<td><form:label path="dataRegistrazione">
								<spring:message code="atto.dataregistrazione" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>
				</c:if>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>

					<c:if test="${attoForm.statoAtto.id != 3 }">

						<td class="td_tasto"><input type="button" value="Salva" onclick="inserisciAtto('Conferma', '${attoForm.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" /></td> 
						<input	id="Conferma" type="submit" name="Conferma" value="" style="display: none" />

						<td class="td_tasto"><input type="button" value="Conferma e Invia" onclick="inserisciAtto('invia', '${attoForm.allegato.documentoFirmato}')" title="Invia" class="nowait" /></td> 
						<input	id="invia" type="submit" name="Invia" value="" style="display: none" />
						
						
					</c:if>
					<td class="td_tasto"><input type="button" value="Indietro" onClick="javascript:indietro('/procedimento/atto/copertinaAtto.htm')"></td>
				</tr>
			</table>
		</form:form>

		<script type="text/javascript">
			
			$(document).ready(function() {
				prendiData("#id_dataDeposito",true);
				prendiData("#id_dataEmissione",true);
			});
			
			
			function abilita() 
			{
				 if(document.getElementById("id_restituzionePerEquivalente").checked)
				 {
				     document.getElementById("id_note").disabled = false;
				 }else{
					 document.getElementById("id_note").disabled = true;
					 document.getElementById("id_note").value = "";
				 }
				
			}			
			
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
