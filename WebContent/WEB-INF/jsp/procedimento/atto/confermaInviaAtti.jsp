<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@page import="it.giustizia.smp.utility.Constants"%>

<c:set var="DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO" value="<%=Constants.DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO%>"/>
<c:set var="STATOATTO_IN_LAVORAZIONE_S" value="<%=Constants.STATOATTO_IN_LAVORAZIONE_S%>"/>
<%-- Specifiche Intervento-006-SpeseProces-NumAtto --%>
<c:set var="DECRETO_RIGETTO_TOTALE_SEQUESTRO" 			value="<%=Constants.DECRETO_RIGETTO_TOTALE_SEQUESTRO%>"/>
<c:set var="DECRETO_SEQUESTRO_URGENTE" 					value="<%=Constants.DECRETO_SEQUESTRO_URGENTE%>"/>
<c:set var="DECRETO_SEQUESTRO_ANTICIPATO" 				value="<%=Constants.DECRETO_SEQUESTRO_ANTICIPATO%>"/>
<c:set var="DECRETO_SEQUESTRO_ORDINARIO" 				value="<%=Constants.DECRETO_SEQUESTRO_ORDINARIO%>"/>
<c:set var="DECRETO_SEQUESTRO_ORDINARIO_II_GRADO" 		value="<%=Constants.DECRETO_SEQUESTRO_ORDINARIO_II_GRADO%>"/>
<c:set var="DECRETO_SOSPENSIONE_TEMP_CDA" 				value="<%=Constants.DECRETO_SOSPENSIONE_TEMP_CDA%>"/>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/confermaInviaAtto.htm" />
		<form:form method="post" id="id_formConfermaInvia" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			<form:hidden path="codAtto" />
			<p class="titolo_page">
				<spring:message code="atto.invio" />
			</p>

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<c:if test="${attoForm.categoriaAtto.id == DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO}">
				<table class="tb_form">
					<tr>
						<td class="td_titolo_label" style="width: 40px">
							<form:label path="note">
								<spring:message code="note" />
							</form:label>
						</td>
						<td class="td_titolo">
							<textarea style="width: 100%" rows="2" disabled>${attoForm.note}</textarea>
						</td>
					</tr>
				</table>
			</c:if>
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
						<td><c:if test="${attoForm.statoAtto.id != 3}">
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
						<%-- Specifiche Intervento-006-SpeseProces-NumAtto --%>
 						<c:if test="${attoForm.categoriaAtto.id == DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO
 										|| attoForm.categoriaAtto.id == DECRETO_RIGETTO_TOTALE_SEQUESTRO
 										|| attoForm.categoriaAtto.id == DECRETO_SEQUESTRO_URGENTE
										|| attoForm.categoriaAtto.id == DECRETO_SEQUESTRO_ANTICIPATO
										|| attoForm.categoriaAtto.id == DECRETO_SEQUESTRO_ORDINARIO
										|| attoForm.categoriaAtto.id == DECRETO_SEQUESTRO_ORDINARIO_II_GRADO
										|| attoForm.categoriaAtto.id == DECRETO_SOSPENSIONE_TEMP_CDA}">
							<jsp:include page="/WEB-INF/jsp/procedimento/atto/includeGeneraNumAtto.jsp" />
 						</c:if>
					<!-- 
						<td class="td_tasto"><input type="button" title="Redigi" value="Redigi Atto" onClick="javascript:redirigi('/procedimento/atto/report.htm')"></td>
 					-->
 					
						<td class="td_tasto">
							<input type="button" value="Salva" onclick="inserisciAtto('Conferma', '${attoForm.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" />
						</td> 
						<input	id="Conferma" type="submit" name="Conferma" value="" style="display: none" />
 					 	<%-- Specifiche Intervento-006-SpeseProces-NumAtto: aggiunto messagio di conferma se numeAnnoAtto is null --%>
						<c:if test="${attoForm.categoriaAtto.id == DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO
 										|| attoForm.categoriaAtto.id == DECRETO_RIGETTO_TOTALE_SEQUESTRO
 										|| attoForm.categoriaAtto.id == DECRETO_SEQUESTRO_URGENTE
										|| attoForm.categoriaAtto.id == DECRETO_SEQUESTRO_ANTICIPATO
										|| attoForm.categoriaAtto.id == DECRETO_SEQUESTRO_ORDINARIO
										|| attoForm.categoriaAtto.id == DECRETO_SEQUESTRO_ORDINARIO_II_GRADO
										|| attoForm.categoriaAtto.id == DECRETO_SOSPENSIONE_TEMP_CDA}">
						    <td class="td_tasto">
							    <c:if test="${attoForm.numeAnnoAtto==null || attoForm.numeAtto==null}">
									<input type="button" value="Conferma e Invia" onclick="return confermaInvio('invia','${attoForm.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" />
							   	</c:if>
							   	<c:if test="${attoForm.numeAnnoAtto!=null && attoForm.numeAtto!=null}">
							   		<input type="button" value="Conferma e Invia" onclick="inserisciAtto('invia', '${attoForm.allegato.documentoFirmato}')" title="Invia" class="nowait" />
							   	</c:if>
						   	</td>
							<input id="invia" type="submit" name="Invia" value="" style="display: none" />
						</c:if>
						<%-- Specifiche Intervento-006-SpeseProces-NumAtto --%>
						<c:if test="${attoForm.categoriaAtto.id != DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO
 										&& attoForm.categoriaAtto.id != DECRETO_RIGETTO_TOTALE_SEQUESTRO
 										&& attoForm.categoriaAtto.id != DECRETO_SEQUESTRO_URGENTE
										&& attoForm.categoriaAtto.id != DECRETO_SEQUESTRO_ANTICIPATO
										&& attoForm.categoriaAtto.id != DECRETO_SEQUESTRO_ORDINARIO
										&& attoForm.categoriaAtto.id != DECRETO_SEQUESTRO_ORDINARIO_II_GRADO
										&& attoForm.categoriaAtto.id != DECRETO_SOSPENSIONE_TEMP_CDA}">
							<td class="td_tasto">
								<input type="button" value="Conferma e Invia" onclick="inserisciAtto('invia', '${attoForm.allegato.documentoFirmato}')" title="Invia" class="nowait" />
							</td>
							<input id="invia" type="submit" name="Invia" value="" style="display: none" />
						</c:if>				
						<!-- 
						<td class="td_tasto"><input type="submit" name="Invia" title="Invia" value="Conferma e Invia"></td>
						 -->
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
			<%-- Specifiche Intervento-006-SpeseProces-NumAtto: commentato if inutile ed aggiunto msg di conferma se non è stato generato il numero atto --%>
if (${atto.categoriaAtto.id == DECRETO_RIGETTO_STATO_RICHIESTA_SEQUESTRO
		|| atto.categoriaAtto.id == DECRETO_RIGETTO_TOTALE_SEQUESTRO
		|| attoForm.categoriaAtto.id == DECRETO_SEQUESTRO_URGENTE
		|| attoForm.categoriaAtto.id == DECRETO_SEQUESTRO_ANTICIPATO
		|| attoForm.categoriaAtto.id == DECRETO_SEQUESTRO_ORDINARIO
		|| attoForm.categoriaAtto.id == DECRETO_SEQUESTRO_ORDINARIO_II_GRADO
		|| attoForm.categoriaAtto.id == DECRETO_SOSPENSIONE_TEMP_CDA}) {
	function confermaInvio(submit, documentoFirmato) {
		var check = checkDate();
		if (check && confirm("Si è scelto di voler procedere senza aver generato precedentemente il numero dell'atto e quindi una volta confermata l'operazione non sarà più possibile modificare il file allegato. Si è sicuri di voler continuare?")) {
			inserisciAtto(submit, documentoFirmato);
			return true;
		}
		return false;
	}
}
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>