<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
<%
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/inserimentoUdienza.htm" />
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="udienzaForm" enctype="multipart/form-data">
			<form:hidden path="codUdienza" />
			<form:hidden path="codAtto" />
			<form:hidden path="dataRegistrazione" />

			<p class="titolo_page">
				<spring:message code="atto.udienza.titolo" />
			</p>

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<br />
			<logic:present name="direttiInteressatiSenzaDifensore">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="40px" class="td_titolo_label" height="5px" title="apre/chiude l'elenco dei diretti interessati senza difensore"><img id="id_img_direttiInteressatiSenzaDifensore" src="${pageContext.request.contextPath}/images/right_arrow_2.png"
							height="38" width="38" /></td>
						<td class="td_titolo_label"><p class="testoRosso">
								<spring:message code="atto.udienza.sogint.nodifensore" />
							</p></td>
					</tr>
				</table>
				<div id="id_div_direttiInteressatiSenzaDifensore" style="display: none;">
					<display:table class="tb_lista" uid="direttiInteressatiSenzaDifensore" name="${direttiInteressatiSenzaDifensore}" pagesize="10" requestURI="${azione}">
						<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" style="width: 50%" />
					</display:table>
				</div>
			</logic:present>
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="atto.udienza.nuovaUdienza" /></td>
				</tr>
				<tr>
					<c:if test="${udienzaForm.dataRegistrazione==null}">
						<td><form:label path="dataUdienza" cssClass="required">
								<spring:message code="atto.udienza.data" />
							</form:label></td>
						<td><form:input id="id_dataUdienza" path="dataUdienza" maxlength="10" /> <form:errors path="dataUdienza" class="testoRosso" /></td>
						<td><form:label path="oraInizioUdienza.id" cssClass="required">
								<spring:message code="atto.udienza.ora" />
							</form:label></td>
						<td><form:select path="oraInizioUdienza.id" size="1">
								<form:option value="-1" label="--hh--" />
								<form:options items="${ore}" itemValue="id" itemLabel="descrizione" />
							</form:select> : <form:select path="minutiInizioUdienza.id" size="1">
								<form:options items="${minuti}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:errors path="oraInizioUdienza.id" class="testoRosso" /></td>
					</c:if>
					<c:if test="${udienzaForm.dataRegistrazione!=null}">
						<td><form:label path="dataUdienza">
								<spring:message code="atto.udienza.data" />
							</form:label></td>
						<td><fmt:formatDate value="${udienzaForm.dataUdienza}" type="date" pattern="dd/MM/yyyy" />
							<form:hidden path="dataUdienza" /></td>
						<td><form:label path="oraInizioUdienzaStr">
								<spring:message code="atto.udienza.ora" />
							</form:label></td>
						<td><c:out value="${udienzaForm.oraInizioUdienzaStr}" /></td>
					</c:if>
				</tr>
				<tr>
					<c:if test="${udienzaForm.dataRegistrazione==null}">
						<td><form:label path="indirizzoAulaUdienza" cssClass="required">
								<spring:message code="indirizzo" />
							</form:label></td>
						<td colspan="3"><form:input path="indirizzoAulaUdienza" maxlength="115" size="115" /> <form:errors path="indirizzoAulaUdienza" class="testoRosso" /></td>
					</c:if>
					<c:if test="${udienzaForm.dataRegistrazione!=null}">
						<td><form:label path="indirizzoAulaUdienza">
								<spring:message code="indirizzo" />
							</form:label></td>
						<td colspan="3"><c:out value="${udienzaForm.indirizzoAulaUdienza}" /></td>
					</c:if>
				</tr>
				<tr>
					<c:if test="${udienzaForm.dataRegistrazione==null}">
						<td><form:label path="aulaUdienza" cssClass="required">
								<spring:message code="atto.udienza.aula" />
							</form:label></td>
						<td colspan="3"><form:input path="aulaUdienza" maxlength="20" size="20" /> <form:errors path="aulaUdienza" class="testoRosso" /></td>
					</c:if>
					<c:if test="${udienzaForm.dataRegistrazione!=null}">
						<td><form:label path="aulaUdienza">
								<spring:message code="atto.udienza.aula" />
							</form:label></td>
						<td colspan="3"><c:out value="${udienzaForm.aulaUdienza}" /></td>
					</c:if>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="atto.udienza.cancelliere" /></td>
				</tr>
				<tr>
					<c:if test="${udienzaForm.dataRegistrazione==null}">
						<td><form:label path="cognomeCancelliere">
								<spring:message code="cognome" />
							</form:label></td>
						<td><form:input path="cognomeCancelliere" maxlength="50" size="50" />
						<td><form:label path="nomeCancelliere">
								<spring:message code="nome" />
							</form:label></td>
						<td><form:input path="nomeCancelliere" maxlength="50" size="50" /></td>
					</c:if>
					<c:if test="${udienzaForm.dataRegistrazione!=null}">
						<td><form:label path="cognomeCancelliere">
								<spring:message code="cognome" />
							</form:label></td>
						<td><c:out value="${udienzaForm.cognomeCancelliere}" /></td>
						<td><form:label path="nomeCancelliere">
								<spring:message code="nome" />
							</form:label></td>
						<td><c:out value="${udienzaForm.nomeCancelliere}" /></td>
					</c:if>
				</tr>
			</table>
			<br />
			<br />

			<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegatoUdi.jsp" />

			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<c:if test="${udienzaForm.dataRegistrazione==null}">
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>
					<tr>
						<td><form:label path="dataEmissione" >
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td><form:input id="id_dataEmissione" path="dataEmissione" maxlength="10" /> <form:errors path="dataEmissione" class="testoRosso" /></td>
						<td><form:label path="dataDeposito" >
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><form:input id="id_dataDeposito" path="dataDeposito" maxlength="10" /> <form:errors path="dataDeposito" class="testoRosso" /></td>
					</tr>
					<tr style="display:none">
						<td><form:label path="destinatario.id">
								<spring:message code="atto.destinazione" />
							</form:label></td>
						<td colspan="3"><form:select path="destinatario.id">
								<form:options items="${ufficiList}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>
					</tr>
				</c:if>
				<c:if test="${udienzaForm.dataRegistrazione!=null}">
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>
					<tr>
						<td><form:label path="dataEmissione">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td><fmt:formatDate value="${udienzaForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /></td>
						<td><form:label path="dataDeposito">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><fmt:formatDate value="${udienzaForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>
					<tr>
						<td><form:label path="dataRegistrazione">
								<spring:message code="atto.dataregistrazione" />
							</form:label></td>
						<td><fmt:formatDate value="${udienzaForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataRegistrazione" /></td>
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
					</tr>
				</c:if>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<smp:notificheButtons formId="id_formConfermaInvia" codAtto="${udienzaForm.codAtto}"></smp:notificheButtons>
					<c:if test="${udienzaForm.dataRegistrazione==null}">
						
						<td class="td_tasto"><input type="button" value="Salva" onclick="inserisciAtto('SalvaModifica', '${udienzaForm.allegato.documentoFirmato}')" title="Salva l'udienza corrente" class="nowait" /></td> 
						<input	id="SalvaModifica" type="submit" name="SalvaModifica" value="" style="display: none" />
							
						
						<td class="td_tasto"><input type="button" value="Conferma e Invia" onclick="inserisciAtto('ConfermaInvia', '${udienzaForm.allegato.documentoFirmato}')" title="Conferma e invia l'udienza corrente" class="nowait" /></td> 
						<input	id="ConfermaInvia" type="submit" name="ConfermaInvia" value="" style="display: none" />
						
						
					</c:if>
					<!-- nuova gestione verbale udienza - bisognerà fare come per Corte d'Appello -->
					<c:if test="${attoForm.idProcedimentoCdatrib==null || attoForm.idProcedimentoCdatrib==0}">
						<td class="td_tasto">
							<c:if test="${fromMenuAtti=='elenco'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
							</c:if> <c:if test="${fromMenuAtti=='ordine'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
							</c:if> <c:if test="${fromMenuAtti=='nuovo'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/nuovo.htm')">
							</c:if>
						</td>
					</c:if>
				</tr>
			</table>
		</form:form>
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