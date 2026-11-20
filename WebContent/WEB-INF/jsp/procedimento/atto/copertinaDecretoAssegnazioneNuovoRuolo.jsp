<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
<%
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/assegnazioneNuovoRuolo.htm" />
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="udienzaForm" enctype="multipart/form-data">	
			<form:hidden path="codUdienza"/>
			<form:hidden path="codAtto"/>
			<form:hidden path="dataRegistrazione"/>
		
			<p class="titolo_page"><spring:message code="atto.udienza.decretoAssegnazioneNuovoRuolo"/></p>
				
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />			
			<br/>					
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br/>			
			<br/>			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4" >
						<spring:message code="atto.udienza.decretoAsseNuovoRuoloRemSuRuolo.datiUdienza"/>
						<form:hidden path="udienzaPrecedente.codUdienza"/>
					</td>
				</tr>	
				<tr>
					<td><form:label path=""><spring:message code="atto.udienza.data" /></form:label></td>
					<td><fmt:formatDate value="${udienzaForm.udienzaPrecedente.dataUdienza}" type="date" pattern="dd/MM/yyyy" /></td>
					<td><form:label path=""><spring:message code="atto.udienza.ora" /></form:label></td>
					<td><c:out value="${udienzaForm.udienzaPrecedente.oraInizioUdienzaStr}" /></td>
				</tr>
				<tr>
					<td><form:label path=""><spring:message code="udienza.esito" /></form:label></td>
					<td colspan="3"><c:out value="${udienzaForm.udienzaPrecedente.esito.descrizione}" /></td>
				</tr>
				<tr>
					<td><form:label path=""><spring:message code="indirizzo" /></form:label></td>
					<td><c:out value="${udienzaForm.udienzaPrecedente.indirizzoAulaUdienza}" /></td>
					<td><form:label path=""><spring:message code="atto.udienza.aula"/></form:label></td>	
					<td><c:out value="${udienzaForm.udienzaPrecedente.aulaUdienza}" /></td>
				</tr>
				<tr>
					<td><form:label path=""><spring:message code="atto.udienza.cancelliere"/></form:label></td>
					<td colspan="3">
						<c:out value="${udienzaForm.udienzaPrecedente.cognomeCancelliere}" />&nbsp;<c:out value="${udienzaForm.udienzaPrecedente.nomeCancelliere}" />
					</td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4" ></td>
				</tr>
			</table>
			<br/>
			<br/>
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegatoUdi.jsp" />
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<c:if test="${udienzaForm.dataRegistrazione==null}">
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>
					<tr>
						<td><form:label path="dataEmissione" cssClass="required"><spring:message code="atto.dataemissione"/></form:label></td>	
						<td><form:input id="id_dataEmissione" path="dataEmissione" maxlength="10"/>
							<form:errors path="dataEmissione" class="testoRosso"/>
						</td>
						<td><form:label path="dataDeposito" cssClass="required"><spring:message code="atto.datadeposito"/></form:label></td>	
						<td><form:input id="id_dataDeposito" path="dataDeposito" maxlength="10" />
							<form:errors path="dataDeposito" class="testoRosso"/>
						</td>
					</tr>
					<tr style="display:none">
						<td><form:label path="destinatario.id"><spring:message code="atto.destinazione" /></form:label></td>
						<td colspan="3">
							<form:select path="destinatario.id">							
								<form:options items="${ufficiList}" itemValue="id" itemLabel="descrizione" /> 	
							</form:select>
						</td>
					</tr>
				</c:if>
				<c:if test="${udienzaForm.dataRegistrazione!=null}">
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>
					<tr>
						<td><form:label path="dataEmissione"><spring:message code="atto.dataemissione"/></form:label></td>	
						<td><fmt:formatDate value="${udienzaForm.dataEmissione}" type="date" pattern="dd/MM/yyyy"/></td>
						<td><form:label path="dataDeposito"><spring:message code="atto.datadeposito"/></form:label></td>	
						<td><fmt:formatDate value="${udienzaForm.dataDeposito}" type="date" pattern="dd/MM/yyyy"/></td>
					</tr>
					<tr>
						<td><form:label path="dataRegistrazione"><spring:message code="atto.dataregistrazione"/></form:label></td>	
						<td>
							<fmt:formatDate value="${udienzaForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" />
							<form:hidden path="dataRegistrazione" />
						</td>
						<td><form:label path="destinatario.id"><spring:message code="atto.destinazione" /></form:label></td>
						<td>
						<table>
							<c:forEach var="destinat" items="${ufficiListFromFlussiOut}">
								<tr>
									<td style="border: none"><c:out value="${destinat.descrizione}"/></td>
								</tr>
							</c:forEach>
						</table>
						</td>
					</tr>
				</c:if>           		
          	</table>
          	<br/>
          	<br/>
			<table class="tb_bottoni" align="center">
          		<tr>
          			<smp:notificheButtons formId="id_formConfermaInvia" codAtto="${udienzaForm.codAtto}"></smp:notificheButtons>
					<c:if test="${udienzaForm.dataRegistrazione==null}">	
          			 <c:if test="${profilo.ruolo!='MAGISTRATO'}">
						<td class="td_tasto"><input type="button" value="Salva" onclick="inserisciAtto('Conferma', '${attoForm.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" /></td> 
						<input	id="Conferma" type="submit" name="Conferma" value="" style="display: none" />
	          			<td class="td_tasto"><input type="submit" name="ConfermaInvia" value="Conferma e Invia"></td>
	          		</c:if>
	          		</c:if>
          			<td class="td_tasto">
						<c:if test="${fromMenuAtti=='elenco'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
						</c:if>
						<c:if test="${fromMenuAtti=='ordine'}">
						<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
						</c:if>
						<c:if test="${fromMenuAtti=='nuovo'}">
						<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/nuovo.htm')">
						</c:if>	          			          			
          			</td>
          		</tr>
          	</table>
		</form:form>
			
			<script	src="${pageContext.request.contextPath}/js/rotator/jQueryRotate.2.1.js"></script>
			<script type="text/javascript">
			
				$(document).ready(function() {
					prendiData("#id_dataUdienza",false);
					prendiData("#id_dataEmissione",true);
					prendiData("#id_dataDeposito",true);
					$("#id_img_direttiInteressatiSenzaDifensore").rotate({     bind:       { 
							mouseover : function() {              
								$(this).css("cursor","pointer"); 								 
							}, 							
							click : function() { 							
								
								if($("#id_div_direttiInteressatiSenzaDifensore").is(":visible")){
									$(this).rotate({animateTo:0});  
									divScomparsa('id_div_direttiInteressatiSenzaDifensore');
								}
								else {
									$(this).rotate({animateTo:90});
									divScomparsa('id_div_direttiInteressatiSenzaDifensore');
								}
							}       
							
						}      
					});
				});
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>