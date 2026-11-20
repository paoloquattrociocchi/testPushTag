<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/provvedimentoNominaAmministratori.htm" />
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			
			<form:hidden path="codAtto" />
			<form:hidden path="statoAtto.id" />
			<form:hidden path="statoAtto.descrizione" />
			<form:hidden path="tipoAtto.id" />
			<form:hidden path="categoriaAtto.id" />
					
			<p class="titolo_page">
				<spring:message code="atto.provvedimentoNominaAmministratori" />
			</p>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />

			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<form:hidden path="codAtto" />
			
			<table class="tb_form">
				<tr>
					<td class="td_titolo">
						<spring:message code="atto.amministratoriNominati" />
					</td>
				</tr>
				<tr>
					<td>		
						<display:table id="row" name="${amministratoriAssociati}" class="tb_lista" uid="amministratoriAssociati">
							<display:column property="cognome" title="Cognome" sortable="true" />
							<display:column property="nome" title="Nome" sortable="true" />
							<display:column property="codiceFiscale" title="Codice fiscale" sortable="true" />
							<display:column property="descrIscrizioneAlbo" title="Ordine professionale" sortable="true" />
							<display:column property="dataIscrizioneAlbo" title="Data iscrizione Albo" format="{0,date,dd/MM/yyyy}" sortable="true" />
							<display:column property="comuneDomicilioLegale" title="Domicilio legale" sortable="true" />
						</display:table>
					</td>
				</tr>
			</table>
						
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>

				<c:if test="${attoForm.statoAtto.id != 3}">
					<tr>
						<td>
							<form:label path="dataEmissione" cssClass="required">
								<spring:message code="atto.dataemissione" />
							</form:label>
						</td>
						<td>
							<form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" /> <form:errors path="dataEmissione" class="testoRosso" /></td>
						<td>
							<form:label path="dataDeposito" cssClass="required">
								<spring:message code="atto.datadeposito" />
							</form:label>
						</td>
						<td>
							<form:input path="dataDeposito" maxlength="10" id="id_dataDeposito" /> <form:errors path="dataDeposito" class="testoRosso" />
						</td>
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
				</c:if>
				
			</table>
			
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />			
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<c:if test="${attoForm.dataRegistrazione==null}">
						<c:if test="${attoForm.statoAtto.id!=3}">

							<td class="td_tasto"><input type="button" value="Salva" onclick="inserisciAtto('Salva', '${attoForm.allegato.documentoFirmato}')" title="Salva inserimento atto" class="nowait" /></td> 
							<input	id="Salva" type="submit" name="Salva" value="" style="display: none" />
													
							<td class="td_tasto"><input type="button" value="Conferma" onclick="inserisciAtto('Conferma', '${attoForm.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" /></td> 
							<input	id="Conferma" type="submit" name="Conferma" value="" style="display: none" />
						
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
                            <input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
                        </c:if>
                    </td>					
				</tr>
			</table>			
		</form:form>

	</tiles:putAttribute>
</tiles:insertDefinition>

<c:if test="${attoForm.statoAtto.id != 3}">
	<script type="text/javascript">
	
		$(document).ready(function() {
			prendiData("#id_dataDeposito",true);
			prendiData("#id_dataEmissione",true);
		});		
	
	</script>
</c:if>	
