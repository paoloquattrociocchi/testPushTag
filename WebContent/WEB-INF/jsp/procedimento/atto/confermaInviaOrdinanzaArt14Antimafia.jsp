<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/confermaInviaOrdinanzaArt14Antimafia.htm" />
		<form:form method="post" id="id_formConfermaInvia" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
		
			<form:hidden path="codAtto" />
			<form:hidden path="categoriaAtto.id" />
			<form:hidden path="tipoAtto.id" />
			<form:hidden path="idProcedimentoDestinatario" /> 
			
			<p class="titolo_page">
				<spring:message code="antimafia.art14" /> 
			</p>

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
		
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><c:out value="${attoForm.soggettiAntimafia.descrizione}" /></td>
				</tr>
				<tr>
						<td >&nbsp;</td>
				</tr>		
				<tr>
						<td><form:label path="dataEmissione" cssClass="required">
									<spring:message code="atto.dataemissione" />
								</form:label></td>
						<td><form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" /> <form:errors path="dataEmissione" class="testoRosso" />
						</td>

						<td><form:label  path="dataDeposito" cssClass="required">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><form:input id="id_dataDeposito" path="dataDeposito" maxlength="10" /> <form:errors path="dataDeposito" class="testoRosso" /></td>
					</tr>

					<tr>
						<td><form:label path="magistrato.id" cssClass="required"><spring:message code="antimafia.magistrato" /></form:label></td>
						<td colspan="3"><form:select  path="magistrato.id" size="1" >
								<form:option value="-1" label="---Seleziona Magistrato---"/>
								<form:options items="${magistrati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:errors path="magistrato.id" class="testoRosso" />
						</td>	
					</tr>					
					
					<tr>
						<td><form:label path="dataCessazioneMisura">
									<spring:message code="atto.datacessazionemisura" />
								</form:label></td>
						<td><form:input path="dataCessazioneMisura" maxlength="10" id="id_dataCessazioneMisura" /> <form:errors path="dataCessazioneMisura" class="testoRosso" />
						</td>

						<td><form:label  path="dataRipresaMisura">
								<spring:message code="atto.dataripresamisura" />
							</form:label></td>
						<td><form:input id="id_dataRipresaMisura" path="dataRipresaMisura" maxlength="10" /> <form:errors path="dataRipresaMisura" class="testoRosso" /></td>
					</tr>
         			<tr>
						<td><form:label path="note">
								<spring:message code="note" />
							</form:label>
						</td>
						<td colspan="3"><form:textarea id="id_note" path="note" rows="3" cssStyle="width:98%" /></td>
					</tr>
	

			</table>
			<br/>
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
			<br/>
			
			<table class="tb_bottoni" align="center">
				<tr>
						<td class="td_tasto"><input type="button" value="Salva" onclick="inserisciAtto('Salva', '${attoForm.allegato.documentoFirmato}')" title="Salva" class="nowait" /></td>
						<input id="Salva" type="submit" name="Salva" value="" style="display: none" />
						<td class="td_tasto"><input type="button" value="Registra" onclick="inserisciAtto('Registra', '${attoForm.allegato.documentoFirmato}')" title="Registra" class="nowait" /></td> 
						<input id="Registra" type="submit" name="Registra" value="" style="display: none" />			
						<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')"></td>

				</tr>
			</table>
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>


		<script type="text/javascript">			
			$(document).ready(function() {
				prendiData("#id_dataDeposito",true);
				prendiData("#id_dataEmissione",true);
				prendiData("#id_dataRipresaMisura",true);
				prendiData("#id_dataCessazioneMisura",true);
			});
			
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>