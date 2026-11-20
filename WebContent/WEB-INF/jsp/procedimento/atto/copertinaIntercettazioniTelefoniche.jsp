<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>


<c:set var="AUTORIZ_INTERCETTAZIONI_TELEFONICHE" value="<%=Constants.AUTORIZ_INTERCETTAZIONI_TELEFONICHE%>"/>
<c:set var="PROVV_DISTRUZIONE_INTERCETTAZIONI_TEL" value="<%=Constants.PROVV_DISTRUZIONE_INTERCETTAZIONI_TEL%>"/>

<%
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");

%>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/copertinaIntercettazioniTelefoniche.htm" />
		<form:form method="post" id="id_formConfermaInvia" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			
			<form:hidden path="codAtto" />
			<form:hidden path="categoriaAtto.id" />
			<form:hidden path="tipoAtto.id" />
			<form:hidden path="idProcedimentoDestinatario" /> 			
			<form:hidden path="flagSpedRic.id" value="S"/> 
			<form:hidden path="flagSpedRic.id" value="S"/> 

			
			<p class="titolo_page">
			<c:if test="${attoForm.categoriaAtto.id == AUTORIZ_INTERCETTAZIONI_TELEFONICHE}" >
				<spring:message code="antimafia.autorizzazione.intercettazione" /> 
			</c:if>	
			<c:if test="${attoForm.categoriaAtto.id == PROVV_DISTRUZIONE_INTERCETTAZIONI_TEL}" >
				<spring:message code="antimafia.distruzione.intercettazione" /> 
			</c:if>			
			
			</p>

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
		
			<table class="tb_form">
			<colgroup>
		       <col id="tb_form_col_label" span="1">
		       <col id="tb_form_col_value" span="1">
		       <col id="tb_form_col_label" span="1">
		       <col id="tb_form_col_value" span="1">
		    </colgroup>
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>
								

		<c:if test="${attoForm.statoAtto.id != 3}">
						
						<tr>
							<td><form:label path="fonteAttoGenerico.id" cssClass="required">
									<spring:message code="antimafia.destinatario" />
								</form:label></td>
							<td>
							<form:select class="nonModificabile" id="id_fonte" path="fonteAttoGenerico.id" size="1" onchange="pulisciCombo(); this.form.submit();">
								<form:option value="-1" label="-------------------------Seleziona Fonte-----------------------" />
								<form:options items="${fontiAttoGenerico}" itemValue="id" itemLabel="descrizione" />
							</form:select>
							<form:errors path="fonteAttoGenerico.id" class="testoRosso" />
							</td>
								<td><form:label path="organo" cssClass="required">
										<spring:message code="atto.organo" />
									</form:label></td>
								<td><form:select class="nonModificabile" path="organo.id" size="1" onchange="pulisciCombo(); this.form.submit()">
										<form:option value="-1" label="-------------------------Seleziona Organo-----------------------" />
										<form:options items="${organi}" itemValue="id" itemLabel="descrizione" />
									</form:select> <form:errors path="organo.id" class="testoRosso" /></td>
		
						</tr>
				
						<tr>
							<td><form:label path="provinciaOrgano" cssClass="required">
									<spring:message code="provincia" />
								</form:label></td>
							<td><form:select class="nonModificabile" path="provinciaOrgano.id" size="1" onchange="pulisciComboIndirizzo(); this.form.submit()">
									<form:option value="-1" label="-------------------------Seleziona Provincia-----------------------" />
									<form:options items="${province}" itemValue="id" itemLabel="descrizione" />
								</form:select> <form:errors path="provinciaOrgano.id" class="testoRosso" /></td>
							<td><form:label path="comuneOrgano" cssClass="required">
									<spring:message code="comune" />
								</form:label></td>
							<td><form:select class="nonModificabile" path="comuneOrgano.id" size="1" onchange="this.form.submit()">
									<form:option value="-1" label="-------------------------Seleziona Comune-----------------------" />
									<form:options items="${comuni}" itemValue="id" itemLabel="descrizione" />
								</form:select> <form:errors path="comuneOrgano.id" class="testoRosso" /></td>
						</tr>
						<tr>
							<td><form:label path="indirizzoOrgano" cssClass="required">
									<spring:message code="indirizzo" />
								</form:label></td>
							<td colspan="3"><form:select class="nonModificabile" path="indirizzoOrgano.id" size="1" onchange="this.form.submit()">
									<form:option value="-1" label="-------------------------Seleziona Indirizzo-----------------------" />
									<form:options items="${indirizziSede}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:errors path="indirizzoOrgano.id" class="testoRosso" />
							</td>
						</tr> 
					<tr>
						<td><form:label path="magistrato.id" cssClass="required"><spring:message code="antimafia.magistrato" /></form:label></td>
						<td><form:select  path="magistrato.id" size="1" >
								<form:option value="-1" label="---Seleziona Magistrato---"/>
								<form:options items="${magistrati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:errors path="magistrato.id" class="testoRosso" />
						</td>
						
					   <td><form:label  path="dataDeposito" cssClass="required">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><form:input id="id_dataDeposito" path="dataDeposito" maxlength="10" /> <form:errors path="dataDeposito" class="testoRosso" /></td>
							
					</tr>	

					<tr>
						<td><form:label path="note">
								<spring:message code="note" />
							</form:label>
						</td>
						<td colspan="3"><form:textarea id="id_note" path="note" rows="3" cssStyle="width:98%" /></td>
					</tr>
			</c:if>
				<c:if test="${attoForm.statoAtto.id == 3}">
		
					<!--  Organo già predefinito sul DB -->
					<tr>
						<td><form:label path="fonteAttoGenerico.id">
									<spring:message code="antimafia.destinatario" />
								</form:label></td>
						<td>
						<form:select class="nonModificabile" id="id_fonte" path="fonteAttoGenerico.id" size="1" >
							<form:options items="${fontiAttoGenerico}" itemValue="id" itemLabel="descrizione" />
						</form:select>
						</td>

						<td><form:label path="organo">
								<spring:message code="atto.organo" />
							</form:label></td>
						<td><form:select class="nonModificabile" path="organo.id" size="1" >
								<form:options items="${organi}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>
	
					</tr>
						
					<tr>
							<td><form:label path="provinciaOrgano">
									<spring:message code="provincia" />
								</form:label></td>
							<td><form:select class="nonModificabile" path="provinciaOrgano.id" size="1" >
													<form:options items="${province}" itemValue="id" itemLabel="descrizione" />
								</form:select></td>
							<td><form:label path="comuneOrgano">
									<spring:message code="comune" />
								</form:label></td>
							<td><form:select class="nonModificabile" path="comuneOrgano.id" size="1" >
									<form:options items="${comuni}" itemValue="id" itemLabel="descrizione" />
								</form:select></td>
					</tr>
					<tr>
							<td><form:label path="indirizzoOrgano">
									<spring:message code="indirizzo" />
								</form:label></td>
							<td colspan="3"><form:select class="nonModificabile" path="indirizzoOrgano.id" size="1" >
									<form:options items="${indirizziSede}" itemValue="id" itemLabel="descrizione" />
							</form:select>
							</td>
					</tr> 
		
                     <tr>
						<td><form:label path="magistrato.id"><spring:message code="antimafia.magistrato" /></form:label></td>
					    <td><c:out value="${attoForm.magistrato.descrizione}" /></td>	
					    
						<td><form:label  path="dataDeposito" >
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						 <td>
					      <fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" />
						 </td>						    
					    				
					 </tr>	
 

					<tr>
						<td><form:label path="note">
								<spring:message code="note" />
							</form:label>
						</td>
						<td colspan="3"><c:out value="${attoForm.note}" /></td>
					</tr>

				</c:if>
			</table>
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
			<br />
			
			<table class="tb_bottoni" align="center">
				<tr>

					<c:if test="${attoForm.statoAtto.id != 3 }">
		
						<td class="td_tasto"><input type="button" value="Salva" onclick="inserisciAtto('Salva', '${attoForm.allegato.documentoFirmato}')" title="Salva" class="nowait" /></td>
						<input id="Salva" type="submit" name="Salva" value="" style="display: none" />
						<td class="td_tasto"><input type="button" value="Registra" onclick="inserisciAtto('Registra', '${attoForm.allegato.documentoFirmato}')" title="Registra" class="nowait" /></td> 
						<input id="Registra" type="submit" name="Registra" value="" style="display: none" />
					</c:if>
					<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')"></td>
	
				</tr>
			</table>
		</form:form>


		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		
		
	<c:if test="${attoForm.statoAtto.id == 3}">
				
			<script type="text/javascript">
				
				$(document).ready(function() {

					$(".nonModificabile").attr("disabled", true);
	
					
				});		
			</script>	
	</c:if>

		<script type="text/javascript">
			
			$(document).ready(function() {
				prendiData("#id_dataRicezione",true);
				prendiData("#id_dataDeposito",true);
				prendiData("#id_dataCessazioneMisura",true);


				
			});


			function pulisciCombo(){
				if(document.getElementsByName("provinciaOrgano.id").length != 0)
					document.getElementsByName("provinciaOrgano.id")[0].selectedIndex = -1;
				}

			function pulisciComboIndirizzo(){
				if(document.getElementsByName("indirizzoOrgano.id").length != 0)
					document.getElementsByName("indirizzoOrgano.id")[0].selectedIndex = -1;
				}


				
				

			
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>