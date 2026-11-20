<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<%
	AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
   
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">

		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>

		<spring:url var="azione" value="/procedimento/soggetto/misuraPersonale.htm" />
		<p class="titolo_page">
		
		<c:if test="${procedimento.tipoProcedimento.id != 16 && procedimento.tipoProcedimento.id != 15}"> 
			<spring:message code="soggetto.misurapersonale" />
		</c:if>	
		<c:if test="${procedimento.tipoProcedimento.id == 16}"> 
			<spring:message code="soggetto.decisione.permesso" />		
		</c:if>	
		<c:if test="${procedimento.tipoProcedimento.id == 15}"> 
			Decisione su cauzione		
		</c:if>	
		</p>
		<form:form method="post" action="${azione}" commandName="soggettoForm">
			<input type="hidden" id="esitoReadOnly" value="${esitoReadOnly}"/>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
		    <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
		    <br />
		    
		    <c:if test="${procedimento.tipoProcedimento.id == 14 || procedimento.tipoProcedimento.id == 15 || procedimento.tipoProcedimento.id == 16}"> 
			 
			 <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><c:out value="${soggettoForm.descrizioneSoggetto}" /></td>
				</tr>
			 </table>
			 <br/>
			 <table class="tb_form" cellspacing="0" cellpadding="0" border="0">	
				<tr>
					<td width="10%;"><form:label path="descRichiesta">
							<spring:message code="richiesta" />
						</form:label></td>
					<td width="80%;" align="left"><strong><c:out value="${soggettoForm.descRichiesta}" /></strong></td>
				    <td  width="10%;" >&nbsp;</td>
				</tr>						 	
			 </table>
			 
			 <br/>	
			</c:if>
			
		
			<logic:present name="listaRicorso">
				<div style="max-height: 400px; overflow-y: scroll; float: left; width: 100%;">
					<display:table class="tb_lista" uid="soggettoRicorsoForm" name="${listaRicorso}">
							
							
							<c:choose>
							<c:when test="${soggettoRicorsoForm.misura.descrizioneMisura == 'NO_MIS'}">
								<display:column title="">
									<spring:message code="stralcio.misureAssenti" />
								</display:column>
							</c:when>
							<c:otherwise>
								<display:column title="Misure">
									<display:table class="tb_sotto_lista" uid="bene" name="${soggettoRicorsoForm.listaBeneStralcioForm}">
										
										<c:choose>
											<c:when test="${soggettoRicorsoForm.misura.descrizioneMisura == 'NO_MIS'}">
												<display:column title="" sortable="false" style="width:10%">
														--- 
														</display:column>
												<display:column title="Misura" style="width:80%">
														--- 
														</display:column>
											</c:when>
											<c:when test="${bene.tipoMisura == 'PERS'}">
												<display:column title="Misura" sortable="false" style="width:50%">
												
											    <c:if test="${procedimento.tipoProcedimento.id!='16'}">
													<c:if test="${bene.misura.durata != ''}">
														<c:out value="${bene.misura.tipoMisura.descrizione} (${bene.misura.durata})"></c:out>
													</c:if>
													<c:if test="${bene.misura.durata == ''}">
														<c:out value="${bene.misura.tipoMisura.descrizione}"></c:out>
													</c:if>
													<c:if test="${bene.misura.importoCauzione != null}">
															Importo: <c:out value="${bene.misura.importoCauzione}"></c:out>
													</c:if>		
												</c:if>
											    <c:if test="${procedimento.tipoProcedimento.id=='16'  }"> 
													 	<c:choose>
															<c:when test="${ bene.misura.tipoMisura.id != '21'}">
																	<c:if test="${bene.misura.durata != ''}">
																		<c:out value="${bene.misura.tipoMisura.descrizione} (${bene.misura.durata})"></c:out>
																	</c:if>
																	<c:if test="${bene.misura.durata == ''}">
																		<c:out value="${bene.misura.tipoMisura.descrizione}"></c:out>
																		<!-- Segnalazione EMS-82 -->
																		<c:if test="${bene.misura.carattereMisura == 'C' && bene.misura.importoCauzione != null}">
																		Importo: <c:out value="${bene.misura.importoCauzione}"></c:out>
																		</c:if>
																	</c:if>
															</c:when>
														</c:choose>	
													</c:if>		
												</display:column>
												
												
											    <c:if test="${bene.misura.descEsito != null}">												
												  <display:column title="" style="width:80%">
													<c:out value="${bene.misura.descEsito} "></c:out>
												  </display:column>
												</c:if>  
											</c:when>
											
											
											<c:otherwise>
												<display:column title="Misura" sortable="false" style="width:50%">
														<div title="${bene.descrizioneBene}">
															<c:out value="${bene.idBene} - ${bene.descrizioneBene}"></c:out>
														</div>
												</display:column>
												<display:column title="" style="width:80%">
													<display:table class="tb_sotto_lista" uid="soggetto" name="${bene.listaSoggettoTitolareForm}" >
														<display:column property="misura.descEsito" title="" style="width:20%" />
														<display:column property="descrizioneSoggetto" title="" style="width:25%"/>
														<display:column property="tipoInteresse.descrizione" title="" style="width:8%"/>
														<display:column property="descrizioneFrazioneOQuota" title="" style="width:3%"/>
														
													</display:table>
												</display:column>

											</c:otherwise>
										</c:choose>
									</display:table>
								</display:column>
							</c:otherwise>
						</c:choose>
					</display:table>
				</div>
				<div style="clear: both;" ></div>
				<br />	
				</logic:present>

			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<form:hidden path="idSoggetto" />
				<form:hidden path="idVersione" />
				<form:hidden path="tipoSoggetto" />
				<form:hidden path="misura.idMisureCaricate" />
				<form:hidden path="idAtto" />
				<form:hidden path="misura.idVecchiePrescrizioni" />
				<form:hidden path="schermataDiProvenienza" />
				<form:hidden path="misura.cauzioneProp" />
				<form:hidden path="misura.art67Prop" />
				<form:hidden path="misura.cauzioneApp" />
				<form:hidden path="misura.importoApp" />
				<form:hidden path="misura.noteApp" />
				<form:hidden path="tipologiaPrevenzioneDb.id" />
				
			<c:if test="${ procedimento.tipoProcedimento.id=='15' || procedimento.tipoProcedimento.id=='16'  }">
               <form:hidden path="misura.tipoMisura.id" />
                <form:hidden path="tipologiaPrevenzione.id" />
				<form:hidden path="normativa.id" />
             </c:if>

              <c:if test="${procedimento.tipoProcedimento.id != 15 && procedimento.tipoProcedimento.id != 16}"> 
				<tr>
					<td class="td_titolo" colspan="4"><c:out value="${soggettoForm.descrizioneSoggetto}" /></td>
				</tr>
			 </c:if>
			 
		 	
			</table>
			<br />
			
			<c:if test="${procedimento.tipoProcedimento.id != 16}"> 
			<table class="tb_form" border="0">
				<logic:present name="esiti">
					<tr>
					 
					 <c:if test="${procedimento.tipoProcedimento.id=='15'  }">
					   <td><form:label path="misura.cauzione">
							<spring:message code="soggetto.misurapersonale.cauzione" />
						</form:label></td>
					</c:if>	
						
				    <c:if test="${procedimento.tipoProcedimento.id!='15'  }">
						<td><form:label path="misura.decisioneMisuraPersonale.id">
								<spring:message code="soggetto.misurapersonale.decisioneIGrado" />
							</form:label></td>
					</c:if>	

						<td colspan="3">
							<form:select path="misura.decisioneMisuraPersonale.id" size="1"   onchange="disableAndSubmit(this)" disabled="${esitoReadOnly}"> 	
								<form:option value="-1" label="------Seleziona Decisione -------" />
								<form:options items="${esiti}" itemValue="id" itemLabel="descrizione" />
							</form:select>
						</td>
					</tr>
				</logic:present>
				<c:if test="${procedimento.tipoProcedimento.id!='14' && procedimento.tipoProcedimento.id!='15' && procedimento.tipoProcedimento.id!='16'  }">
				<tr>
					<td><form:label path="tipologiaPrevenzione.id" cssClass="required">
							<spring:message code="procedimento.tipologiaPrevenzione" />
						</form:label></td>
					<td><form:select path="tipologiaPrevenzione.id" size="1" onchange="disableAndSubmit(this)">
							<form:option value="-1" label="------Seleziona Tipo Prevenzione-------" />
							<form:options items="${tipiPrevenzione}" itemValue="id" itemLabel="descrizione" />
						</form:select> <form:errors path="tipologiaPrevenzione.id" class="testoRosso" /></td>
					<td><form:label path="normativa.id" cssClass="required">
							<spring:message code="procedimento.normativa" />
						</form:label></td>
					<td>
					<!-- normative: utilizzo cmb finta per mostrare dettaglio anche per le normative scadute -->
					<select id="normativaValorizzata" name="normativaValorizzata" disabled="disabled" style="display:none"><option value="${soggettoForm.normativa.id}">${soggettoForm.normativa.descrizione}</option></select>
					<form:select path="normativa.id" size="1">
							<form:option value="-1" label="------Seleziona Normativa-------" />
							<form:options items="${normativeTipoPrev}" itemValue="id" itemLabel="descrizione" />
					</form:select> <form:errors path="normativa.id" class="testoRosso" />
					</td>
				</tr>
				</c:if>
					
				
				<c:if test="${ procedimento.tipoProcedimento.id!='15' }">
				
				<tr>
					<td><form:label path="misura.tipoMisura" cssClass="required">
							<spring:message code="soggetto.misurapersonale" />
						</form:label></td>
					<td><form:select path="misura.tipoMisura.id" size="1" onchange="disableAndSubmit(this)">
							<form:option value="-1" label="---Seleziona Tipo Misura Personale---" />
							<c:if test="${ procedimento.tipoProcedimento.id=='16' }">
							  <form:options items="${tipiMisuraTipoAP}" itemValue="id" itemLabel="descrizione" />
							</c:if>
							<c:if test="${ procedimento.tipoProcedimento.id!='16' }">
							  <form:options items="${tipiMisuraTipoP}" itemValue="id" itemLabel="descrizione" />
							</c:if>
							
						</form:select> <form:errors path="misura.tipoMisura.id" class="testoRosso" /></td>
					<td><form:label path="misura.normativaDiRiferimento" cssClass="required">
							<spring:message code="procedimento.normativa" />
						</form:label></td>
					<td>
					<!-- normative: utilizzo cmb finta per mostrare dettaglio anche per le normative scadute -->
					<select id="misuraValorizzata" name="misuraValorizzata" disabled="disabled" style="display:none"><option value="${soggettoForm.misura.normativaDiRiferimento.id}">${soggettoForm.misura.normativaDiRiferimento.descrizione}</option></select>
					<form:select path="misura.normativaDiRiferimento.id" size="1">
							<form:option value="-1" label="------Seleziona Normativa-------" />
							<form:options items="${normativeMisPers}" itemValue="id" itemLabel="descrizione" />
					</form:select> <form:errors path="misura.normativaDiRiferimento.id" class="testoRosso" />
					</td>
				</tr>

				<tr>
					<td><form:label path="misura.localita">
							<spring:message code="soggetto.misurapersonale.localita" />
						</form:label></td>
					<td colspan="3"><form:textarea path="misura.localita" rows="2" cssStyle="width:98%" /></td>
				</tr>
				<tr>
					<td><form:label path="misura.anni" cssClass="required">
							<spring:message code="soggetto.misurapersonale.durata" />
						</form:label></td>
					<td colspan="3"><form:input path="misura.anni" maxlength="1" size="2" onkeypress="return isNumeric(event)"/> <form:errors path="misura.anni" class="testoRosso" />&nbsp;&nbsp; <form:input path="misura.mesi" maxlength="2" size="4" onkeypress="return isNumeric(event)"/> <form:errors path="misura.mesi" class="testoRosso" />&nbsp;&nbsp; <form:input path="misura.giorni" maxlength="2" size="4" onkeypress="return isNumeric(event)"/> <form:errors path="misura.giorni" class="testoRosso" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.prescrizioni" /></td>
				</tr>


				<c:forEach var="prescrizione" items="${listaPrescrizioni}">
					<tr>
						<td colspan="4"><form:checkbox path="misura.idSelezionati" value="${prescrizione.id}" label="${prescrizione.descrizione}" onClick="abilitaUlterioriPrescrizioni(this);" /></td>
					</tr>
				</c:forEach>
				<tr>
					<td><form:label path="misura.ulterioriPrescrizioni">
							<spring:message code="soggetto.misurapersonale.ulterioriprescrizioni" />
						</form:label></td>
					<td colspan="3"><form:textarea path="misura.ulterioriPrescrizioni" id="note" readonly="true" rows="2" cssStyle="width:98%" /></td>
				</tr>

				<logic:present name="listaVecchiePrescrizioni">
					<c:forEach var="vecchiePrescrizioni" items="${listaVecchiePrescrizioni}">
						<tr>
							<td colspan="4"><form:checkbox path="misura.idVecchiePrescrizioni" value="${vecchiePrescrizioni.id}" label="${vecchiePrescrizioni.descrizione}" checked="checked" disabled="true" /></td>
						</tr>
						<c:if test="${(vecchiePrescrizioni.id == '13')}">
							<tr>
								<td><form:label path="misura.ulterioriPrescrizioniVecchie">
										<spring:message code="soggetto.misurapersonale.ulterioriprescrizioni" />
									</form:label></td>
								<td colspan="3"><form:textarea path="misura.ulterioriPrescrizioniVecchie" id="note" disabled="true" rows="2" cssStyle="width:98%" /></td>
							</tr>
						</c:if>
					</c:forEach>
				</logic:present>
               </c:if>
              
               
               <c:if test="${ procedimento.tipoProcedimento.id!='16'}">
				<c:if test="${ procedimento.tipoProcedimento.id!='15'}">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.cauzione" /></td>
				</tr>

				<tr>
					<td><form:label path="misura.cauzione">
							<spring:message code="soggetto.misurapersonale.cauzione" />
						</form:label></td>
					<td colspan="3"><form:radiobutton path="misura.cauzione" value="S" onclick="this.form.submit()" /> <form:label path="misura.cauzione">
							<spring:message code="si" />
						</form:label> <form:radiobutton path="misura.cauzione" value="N" onclick="this.form.submit()" /> <form:label path="misura.cauzione">
							<c:if test="${soggettoForm.misura.cauzioneProp == null}">
								<spring:message code="no" />
							</c:if>
							<c:if test="${soggettoForm.misura.cauzioneProp != null}">
								<c:if test="${soggettoForm.misura.cauzioneProp == 'S' }">
									<spring:message code="no.rigetta" />
								</c:if>
								<c:if test="${soggettoForm.misura.cauzioneProp == 'N'}">
									<spring:message code="no" />
								</c:if>
							</c:if>
						</form:label>
				</tr>
				</c:if>

				<logic:present name="importoCauzione">
					<tr>
						<td><form:label path="misura.importo">
								<spring:message code="soggetto.misurapersonale.cauzioneimporto" />
							</form:label></td>
						<td colspan="3"><form:input path="misura.importo" cssClass="numerico" maxlength="10" size="20" onKeypress="if (!((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode == 44) || (event.keyCode == 46))) event.returnValue = false;"/> <form:errors path="misura.importo" class="testoRosso" />&nbsp;&nbsp;</td>
					</tr>
					<tr>
						<td><form:label path="misura.note">
								<spring:message code="soggetto.misurapersonale.note" />
							</form:label></td>
						<td colspan="3"><form:textarea path="misura.note" rows="2" cssStyle="width:98%" /></td>
					</tr>
				</logic:present>
				</c:if>
				<c:if test="${ procedimento.tipoProcedimento.id!='15' && procedimento.tipoProcedimento.id!='16'  }">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.articolo67" /></td>
				</tr>
				<tr>
					<td><form:label path="misura.art67" cssClass="required">
							<spring:message code="soggetto.misurapersonale.applicazioneart67" />
						</form:label></td>
					<td colspan="3"><form:radiobutton path="misura.art67" value="S" /> <form:label path="misura.art67">
							<spring:message code="si" />
						</form:label> <form:radiobutton path="misura.art67" value="N" /> <form:label path="misura.art67">
							<c:if test="${soggettoForm.misura.art67Prop == null}">
								<spring:message code="no" />
							</c:if>
							<c:if test="${soggettoForm.misura.art67Prop != null}">
								<c:if test="${soggettoForm.misura.art67Prop == 'S' }">
									<spring:message code="no.rigetta" />
								</c:if>
								<c:if test="${soggettoForm.misura.art67Prop == 'N'}">
									<spring:message code="no" />
								</c:if>
							</c:if>
						</form:label> <form:errors path="misura.art67" class="testoRosso" /></td>
				</tr>
				</c:if>
			</table>
			
			</c:if>
			<c:if test="${procedimento.tipoProcedimento.id=='16' }">		
			 <table class="tb_form" border="0">
	
					<tr>
					 

						<td><form:label path="misura.decisioneMisuraPersonale.id">
								<spring:message code="soggetto.misurapersonale.decisioneIGrado" />
							</form:label></td>
			
						<td colspan="3">
						 <logic:present scope="session" name="atto">
					     <logic:notEqual scope="session" name="atto" property="statoAtto.id" value="3" >
						   <form:select path="misura.decisioneMisuraPersonale.id" size="1" disabled="${esitoReadOnly}"> 	  						
							<form:option value="-1" label="------Seleziona Decisione -------" />
								<form:options items="${esiti}" itemValue="id" itemLabel="descrizione" />
							</form:select>
						</logic:notEqual>
					     <logic:equal scope="session" name="atto" property="statoAtto.id" value="3" >
						   <form:select path="misura.decisioneMisuraPersonale.id" size="1" disabled="true"> 	  						
							<form:option value="-1" label="------Seleziona Decisione -------" />
								<form:options items="${esiti}" itemValue="id" itemLabel="descrizione" />
							</form:select>
						</logic:equal>						
						</logic:present>
			
						</td>
					</tr>
				</table>	
				<br>
				<br>
			</c:if>
			
			
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
				<c:if test="${profilo.ruolo!='MAGISTRATO'}">
					<logic:present name="esiti">
					<logic:present scope="session" name="atto">
					<logic:notEqual scope="session" name="atto" property="statoAtto.id" value="3" >
						<td class="td_tasto">
						
							<c:if test="${esitoReadOnly==null || esitoReadOnly!='true'}">
								<input type="submit" value="Salva" name="SalvaMisuraDecisorioIGrado" id="SalvaMisuraDecisorioIGrado">
							</c:if>
							<c:if test="${esitoReadOnly=='true'}">
								<input type="submit" value="Salva" name="SalvaMisuraDecisorioIGrado" id="SalvaMisuraDecisorioIGrado" disabled="disabled">
							</c:if>
							
						</td>
					</logic:notEqual>
				   </logic:present>
					</logic:present>
					
					
					
					<logic:notPresent name="esiti">
						<td class="td_tasto">
						
		
							<c:if test="${esitoReadOnly==null || esitoReadOnly!='true'}">					
								<input type="submit" value="Salva" name="Salva">
							</c:if>
							<c:if test="${esitoReadOnly=='true'}">					
								<input type="submit" value="Salva" name="Salva" disabled="disabled">
							</c:if>	
						
									
						</td>
					</logic:notPresent>
					</c:if>
					


					<logic:present scope="session" name="atto">
					<logic:notEqual scope="session" name="atto" property="statoAtto.id" value="3" >
					<c:if test="${procedimento.tipoProcedimento.id != 16 && procedimento.tipoProcedimento.id != 15}">
					 <td class="td_tasto">
						<c:if test="${esitoReadOnly==null || esitoReadOnly!='true'}">
							<input type="submit" value="Elimina" name="ELIMINA">
						</c:if>
						<c:if test="${esitoReadOnly=='true'}">
							<input type="submit" value="Elimina" name="ELIMINA" disabled="disabled">
						</c:if>	
				    </td>	
			       </c:if>	
						</logic:notEqual>
					</logic:present>				
					
					
					<c:if test="${soggettoForm.schermataDiProvenienza == 'soggetti'}">
							<td class="td_tasto"><input type="button" value="Indietro"
							onClick="indietro('/procedimento/soggetto/visualizzaDirettiInteressati.htm')"></td>
					</c:if>
					<c:if test="${soggettoForm.schermataDiProvenienza == 'proposta'}">
						<logic:notEmpty name="atto" property="categoriaAtto" scope="session">
						<td class="td_tasto"><input type="button" value="Indietro"
							onClick="indietro('/procedimento/atto/copertinaAtto.htm')">
						</td>
						</logic:notEmpty>	
						<logic:empty name="atto" property="categoriaAtto" scope="session">
						<td class="td_tasto"><input type="button" value="Indietro"
							onClick="indietro('/procedimento/atti/elenco.htm')">
						</td>
						</logic:empty>
							
					</c:if>
					<c:if test="${soggettoForm.schermataDiProvenienza == 'decisorio'}">
						<td class="td_tasto"><input type="button" value="Indietro"
							onClick="indietro('/procedimento/atto/copertinaAtto.htm')"></td>
					</c:if>
				</tr>
			</table>
			
				 <form:hidden path="idProcedimentoCda" />
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>


		
			<c:if test="${procedimento.tipoProcedimento.id=='16'  }">
			<script type="text/javascript">	
			
			
			
			
		
			
			
			
			
			
			if(document.getElementsByName("misura.localita")!= null && document.getElementsByName("misura.localita")[0]!=null ) document.getElementsByName("misura.localita")[0].disabled=true; 
			
		
			if(document.getElementsByName("misura.anni")!= null  && document.getElementsByName("misura.anni")[0]!=null ) document.getElementsByName("misura.anni")[0].disabled=true; 
			
			
			if(document.getElementsByName("misura.mesi")!= null  && document.getElementsByName("misura.mesi")[0]!=null) document.getElementsByName("misura.mesi")[0].disabled=true; 
			
			
			if(document.getElementsByName("misura.giorni")!= null && document.getElementsByName("misura.giorni")[0]!=null) document.getElementsByName("misura.giorni")[0].disabled=true; 
		    
			
			if(document.getElementsByName("misura.idSelezionati")!= null) {
			var i = document.getElementsByName("misura.idSelezionati").length;
			for(var j =0;j<i;j++){
				document.getElementsByName("misura.idSelezionati")[j].disabled=true;
			}
			}
			
			if(document.getElementsByName("misura.ulterioriPrescrizioni")!= null && document.getElementsByName("misura.ulterioriPrescrizioni")[0]!= null ) document.getElementsByName("misura.ulterioriPrescrizioni")[0].disabled=true;
			
			
		
			
			</script>
			</c:if>
			<c:if test="${procedimento.tipoProcedimento.id=='15'  }">
			<script>

			if(document.getElementsByName("misura.decisioneMisuraPersonale.id").length != 0){
				
				if(document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '-1'
					&& document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '235'
        				&& document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '236'
                        && document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '237'
                         && document.getElementsByName("misura.decisioneMisuraPersonale.id")[0].value != '238'){
                  if(document.getElementsByName("misura.importo")!= null  && document.getElementsByName("misura.importo")[0]!=null ) document.getElementsByName("misura.importo")[0].disabled=true; 
                  if(document.getElementsByName("misura.note")!= null  && document.getElementsByName("misura.note")[0]!=null) document.getElementsByName("misura.note")[0].disabled=true; 
				}
			}
			
		

			</script>
			
			</c:if>
			<c:if test="${procedimento.tipoProcedimento.id!='16'  && procedimento.tipoProcedimento.id!='15' }">
			<script type="text/javascript">	
				$(document).ready(function() {
					
					if(${atto!=null}){
						startWithAtto('${disabledViewProc}', <c:out value="${sessionScope.atto.statoAtto.id}" default="0"/>, '${procedimento.tipoProcedimento.id}');
					}else{
						start('${disabledViewProc}');
					}

					$('#SalvaMisuraDecisorioIGrado').bind('submit', function() {
				           $(":disabled").removeAttr('disabled');
				       });

					checkComboNormative();
				});

	            function checkComboNormative() {
		            var cmbNrm = $('#normativa.id');
		            var cmbMis = $('#misura.normativaDiRiferimento.id');
		            var cmbNrmVal = $('#normativaValorizzata');
		            var cmbMisVal = $('#misuraValorizzata');
		            if (cmbNrm.is(':disabled')){
		            	cmbNrm.hide();
		            	cmbNrmVal.show();
			        }
		            if (cmbMis.is(':disabled')){
		            	cmbMis.hide();
		            	cmbMisVal.show();
			        }
		        }
					
	            function disableAndSubmit(element) {
					$(":disabled").removeAttr('disabled');
					element.form.submit();
				}
	            
	            function isNumeric(evt){   
					var charCode = (evt.which) ? evt.which : event.keyCode   
					if (charCode > 31 && (charCode < 48 || charCode > 57))
						return false;
					return true;
				}
			
			</script>
			</c:if>

	</tiles:putAttribute>
</tiles:insertDefinition>