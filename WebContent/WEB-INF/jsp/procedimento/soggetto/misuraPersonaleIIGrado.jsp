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

		<spring:url var="azione" value="/procedimento/soggetto/misuraPersonaleIIGrado.htm" />
		<p class="titolo_page">
			<spring:message code="soggetto.misurapersonale" />
		</p>
		<form:form method="post" action="${azione}" commandName="soggettoForm">

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
			
			 
			<c:if test="${soggettoForm.descRichiesta != '' && soggettoForm.descRichiesta != null}">
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
			</c:if>
			
		
			<logic:present name="listaRicorso">
				<div style="max-height: 400px; overflow-y: scroll; float: left; width: 100%;">
					<display:table class="tb_lista" uid="soggettoRicorsoForm" name="${listaRicorso}">
							
							<display:column title="Diretto interessato" style="width:10%">
								<c:out value="${soggettoRicorsoForm.descrizioneSoggetto}"></c:out>
							</display:column>
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
												
											    <c:if test="${procedimento.tipoProcedimento.id!='14' && procedimento.tipoProcedimento.id!='15' && procedimento.tipoProcedimento.id!='16'}">
													<c:if test="${bene.misura.durata != ''}">
														<c:out value="${bene.misura.tipoMisura.descrizione} (${bene.misura.durata})"></c:out>
													</c:if>
													<c:if test="${bene.misura.durata == ''}">
														<c:out value="${bene.misura.tipoMisura.descrizione}"></c:out>
													</c:if>
												</c:if>
											    <c:if test="${procedimento.tipoProcedimento.id=='14' || procedimento.tipoProcedimento.id=='15' || procedimento.tipoProcedimento.id=='16'}"> 
													 
														<c:choose>
															<c:when test="${ bene.misura.tipoMisura.id != '21'}">
																	<c:if test="${bene.misura.durata != ''}">
																		<c:out value="${bene.misura.tipoMisura.descrizione} (${bene.misura.durata})"></c:out>
																	</c:if>
																	<c:if test="${bene.misura.durata == ''}">
																		<c:out value="${bene.misura.tipoMisura.descrizione}"></c:out>
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
                <form:hidden path="misura.decisioneMisuraPersonale.descrizione" />
                <form:hidden path="visibilitaIIGrado" />
                
                <c:if test="${ procedimento.tipoProcedimento.id=='15' || procedimento.tipoProcedimento.id=='16'  }">
	               	<form:hidden path="misura.tipoMisura.id" />
	                <form:hidden path="tipologiaPrevenzione.id" />
					<form:hidden path="normativa.id" />
					<form:hidden path="misura.normativaDiRiferimento.id" />
             	</c:if>
				
			</table>
			<br />
			<br />
			
			<table class="tb_form" border="0">
				<logic:notEqual name="atto" property="categoriaAtto.id" value="373" scope="session">
					<c:if test="${procedimento.tipoProcedimento.id=='15'  }">
						<td>
							<form:label path="misura.cauzione">
								<spring:message code="soggetto.misurapersonale.cauzione" />
							</form:label>
						</td>
					</c:if>
					<c:if test="${procedimento.tipoProcedimento.id!='15'  }">
						<tr>
							<c:if test="${soggettoForm.misura.decisioneMisuraPersonale.descrizione!=''  }">
								<td>
									<form:label path="misura.decisioneMisuraPersonale.id">
										<spring:message code="soggetto.misurapersonale.decisioneIGrado" />
									</form:label>
								</td>
							</c:if>
							<td colspan="3">
								<c:out value="${soggettoForm.misura.decisioneMisuraPersonale.descrizione}" />
							</td>
						</tr>
					</c:if>

					<logic:present name="esiti">
						<tr>
							<logic:notEmpty name="soggettoForm" property="misura.decisioneMisuraPersonale.descrizione">
								<td>
									<form:label path="misura.decisioneMisuraPersonaleIIGrado.id">
										<spring:message code="soggetto.misurapersonale.decisioneIIGrado" />
									</form:label>
								</td>
							</logic:notEmpty>

							<td colspan="3">
								<logic:equal scope="session" name="atto" property="statoAtto.id" value="3">
									<c:out value="${soggettoForm.misura.decisioneMisuraPersonaleIIGrado.descrizione}" />
								</logic:equal> 
								<logic:notEqual scope="session" name="atto" property="statoAtto.id" value="3">
									<c:if test="${ procedimento.tipoProcedimento.id!='16' }">
										<form:select path="misura.decisioneMisuraPersonaleIIGrado.id" size="1" onchange="disableAndSubmit(this)" disabled="${disabledObject}">
											<form:option value="-1" label="------Seleziona Decisione -------" />
											<form:options items="${esiti}" itemValue="id" itemLabel="descrizione" />
										</form:select>
									</c:if>
									<c:if test="${ procedimento.tipoProcedimento.id=='16' }">
										<form:select path="misura.decisioneMisuraPersonaleIIGrado.id" size="1" disabled="${disabledObject}">
											<form:option value="-1" label="------Seleziona Decisione -------" />
											<form:options items="${esiti}" itemValue="id" itemLabel="descrizione" />
										</form:select>
									</c:if>
								</logic:notEqual>
							</td>
						</tr>
					</logic:present>
				</logic:notEqual>
				<logic:equal name="atto" property="categoriaAtto.id" value="373" scope="session">
				    <tr>
						<td>
							<form:label path="misura.decisioneMisuraPersonaleIIGrado.id">
								<spring:message code="soggetto.misurapersonale.decisioneIIGrado" />
							</form:label>
						</td>
						
						<logic:present name="esiti">
						
							<td colspan="3">
								<logic:equal scope="session" name="atto" property="statoAtto.id" value="3" >
									<c:out value="${soggettoForm.misura.decisioneMisuraPersonaleIIGrado.descrizione}" />
								</logic:equal>
								<logic:notEqual scope="session" name="atto" property="statoAtto.id" value="3" >
									<form:select path="misura.decisioneMisuraPersonaleIIGrado.id" size="1" onchange="disableAndSubmit(this)" disabled="${disabledObject}">
										<form:option value="-1" label="------Seleziona Decisione -------" />
										<form:options items="${esiti}" itemValue="id" itemLabel="descrizione" />
								 	</form:select>
							 	</logic:notEqual>
						 	</td>
						</logic:present>
					</tr>
				</logic:equal>
				<c:if test="${procedimento.tipoProcedimento.id!='14' && procedimento.tipoProcedimento.id!='15' && procedimento.tipoProcedimento.id!='16'  }">
					<tr>
						<td>
							<form:label path="tipologiaPrevenzione.id" cssClass="required">
								<spring:message code="procedimento.tipologiaPrevenzione" />
							</form:label></td>
						<td>
							<form:select path="tipologiaPrevenzione.id" size="1" onchange="disableAndSubmit(this)" disabled="${disabledObject}">
								<form:option value="-1" label="------Seleziona Tipo Prevenzione-------" />
								<form:options items="${tipiPrevenzione}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:errors path="tipologiaPrevenzione.id" class="testoRosso" /></td>
						<td>
							<form:label path="normativa.id" cssClass="required">
								<spring:message code="procedimento.normativa" />
							</form:label></td>
						<td>
							<form:select path="normativa.id" size="1" disabled="${disabledObject}">
								<form:option value="-1" label="------Seleziona Normativa-------" />
								<form:options items="${normativeTipoPrev}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:errors path="normativa.id" class="testoRosso" />
						</td>
					</tr>
				</c:if>
			
                
                <c:if test="${ procedimento.tipoProcedimento.id!='15' && procedimento.tipoProcedimento.id!='16'  }">
                
					<tr>
						<td>
							<form:label path="misura.tipoMisura" cssClass="required">
								<spring:message code="soggetto.misurapersonale" />
							</form:label>
						</td>
						<td>
							<form:select path="misura.tipoMisura.id" size="1" onchange="disableAndSubmit(this)" disabled="${disabledObject}">
								<form:option value="-1" label="---Seleziona Tipo Misura Personale---" />
								<form:options items="${tipiMisuraTipoP}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:errors path="misura.tipoMisura.id" class="testoRosso" />
						</td>
						<td>
							<form:label path="misura.normativaDiRiferimento" cssClass="required">
								<spring:message code="procedimento.normativa" />
							</form:label>
						</td>
						<td>
							<form:select path="misura.normativaDiRiferimento.id" size="1" disabled="${disabledObject}">
								<form:option value="-1" label="------Seleziona Normativa-------" />
								<form:options items="${normativeMisPers}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:errors path="misura.normativaDiRiferimento.id" class="testoRosso" />
						</td>
					</tr>

					<tr>
						<td>
							<form:label path="misura.localita">
								<spring:message code="soggetto.misurapersonale.localita" />
							</form:label>
						</td>
						<td colspan="3">
							<form:textarea path="misura.localita" rows="2" cssStyle="width:98%" disabled="${disabledObject}"/>
						</td>
					</tr>
					<tr>
						<td>
							<form:label path="misura.anni" cssClass="required">
								<spring:message code="soggetto.misurapersonale.durata" />
							</form:label>
						</td>
						<td colspan="3">
							<form:input path="misura.anni" maxlength="1" size="2" disabled="${disabledObject}" onkeypress="return isNumeric(event)"/> <form:errors path="misura.anni" class="testoRosso" />&nbsp;&nbsp; <form:input path="misura.mesi" maxlength="2" size="4" disabled="${disabledObject}" onkeypress="return isNumeric(event)"/> <form:errors path="misura.mesi" class="testoRosso" />&nbsp;&nbsp; <form:input path="misura.giorni" maxlength="2" size="4" disabled="${disabledObject}" onkeypress="return isNumeric(event)"/> <form:errors path="misura.giorni" class="testoRosso" />
						</td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4">
							<spring:message code="soggetto.misurapersonale.prescrizioni" />
						</td>
					</tr>


					<c:forEach var="prescrizione" items="${listaPrescrizioni}">
						<tr>
							<td colspan="4"><form:checkbox path="misura.idSelezionati" value="${prescrizione.id}" label="${prescrizione.descrizione}" onClick="abilitaUlterioriPrescrizioni(this);" disabled="${disabledObject}" /></td>
						</tr>
					</c:forEach>
					<tr>
						<td>
							<form:label path="misura.ulterioriPrescrizioni">
								<spring:message code="soggetto.misurapersonale.ulterioriprescrizioni" />
							</form:label>
						</td>
						<td colspan="3">
							<form:textarea path="misura.ulterioriPrescrizioni" id="note" readonly="true" rows="2" cssStyle="width:98%" disabled="${disabledObject}"/>
						</td>
					</tr>

					<logic:present name="listaVecchiePrescrizioni">
						<c:forEach var="vecchiePrescrizioni" items="${listaVecchiePrescrizioni}">
							<tr>
								<td colspan="4">
									<form:checkbox path="misura.idVecchiePrescrizioni" value="${vecchiePrescrizioni.id}" label="${vecchiePrescrizioni.descrizione}" checked="checked" disabled="true" />
								</td>
							</tr>
							<c:if test="${(vecchiePrescrizioni.id == '13')}">
								<tr>
									<td>
										<form:label path="misura.ulterioriPrescrizioniVecchie">
											<spring:message code="soggetto.misurapersonale.ulterioriprescrizioni" />
										</form:label>
									</td>
									<td colspan="3">
										<form:textarea path="misura.ulterioriPrescrizioniVecchie" id="note" disabled="true" rows="2" cssStyle="width:98%" />
									</td>
								</tr>
							</c:if>
						</c:forEach>
				</logic:present>
				</c:if>
              
               
               	<c:if test="${ procedimento.tipoProcedimento.id!='16'  }">
                <c:if test="${ procedimento.tipoProcedimento.id!='15'}">
                
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.cauzione" /></td>
				</tr>
			<!--  SITMP-91 DG  casistica con cauzione proveniente da tribunale -->
				<logic:present name="importoCauzione">
				<tr>
					<td><form:label path="misura.cauzione">
							<spring:message code="soggetto.misurapersonale.cauzione" />
						</form:label></td>
					<td colspan="3">
						<!--  SITMP-91 DG  -->
						<!-- <form:radiobutton path="misura.cauzione" value="S" onclick="this.form.submit()" disabled="${disabledObject}" />  -->
						<form:radiobutton path="misura.cauzione" value="S" disabled="true" /> 
						<form:label path="misura.cauzione">
							<spring:message code="si" />
						</form:label> 
						<!--  SITMP-91 DG  -->
						<!-- <form:radiobutton path="misura.cauzione" value="N" onclick="this.form.submit()" disabled="${disabledObject}" /> --> 
						<form:radiobutton path="misura.cauzione" value="N" disabled="true" />
						<form:label path="misura.cauzione">			
								<spring:message code="no" />
						</form:label>
				</tr>
				</logic:present>
				</c:if>
				<!--  SITMP-91 DG  casistica con cauzione proveniente da tribunale -->
				<logic:present name="importoCauzione">
					<tr>
						<td><form:label path="misura.importo">
								<spring:message code="soggetto.misurapersonale.cauzioneimporto" />
							</form:label></td>
						<td colspan="3">
							<!--  SITMP-91 DG  -->
							<!-- <form:input path="misura.importo" cssClass="numerico" maxlength="10" size="20" disabled="${disabledImporto}" />  -->
							<form:input path="misura.importo" cssClass="numerico" maxlength="10" size="20" disabled="true" /> 
							<form:errors path="misura.importo" class="testoRosso" />&nbsp;&nbsp;
						</td>
					</tr>
					<tr>
						<td><form:label path="misura.note">
								<spring:message code="soggetto.misurapersonale.note" />
							</form:label></td>
						<!-- Segnalazione EMS-97 -->
						<td colspan="3"><form:textarea path="misura.note" rows="2" cssStyle="width:98%" /></td>
					</tr>
				</logic:present>
				<!--  SITMP-91 DG  casistica senza cauzione proveniente da tribunale -->
				<logic:notPresent name="importoCauzione">
				<tr>
					<td><form:label path="misura.cauzione">
							<spring:message code="soggetto.misurapersonale.cauzione" />
						</form:label></td>
					<td colspan="3">
						<!--  SITMP-91 DG  -->
						<!-- <form:radiobutton path="misura.cauzione" value="S" onclick="this.form.submit()" disabled="${disabledObject}" />  -->
						<form:radiobutton path="misura.cauzione" value="S" disabled="true" /> 
						<form:label path="misura.cauzione">
							<spring:message code="si" />
						</form:label> 
						<!--  SITMP-91 DG  -->
						<!-- <form:radiobutton path="misura.cauzione" value="N" onclick="this.form.submit()" disabled="${disabledObject}" /> --> 
						<form:radiobutton path="misura.cauzione" value="N" disabled="true" />
						<form:label path="misura.cauzione">			
								<spring:message code="no" />
						</form:label>
				</tr>
							<form:hidden path="misura.importo" />
							<form:hidden path="misura.note" />
				</logic:notPresent>
				
				</c:if>
				<c:if test="${ procedimento.tipoProcedimento.id!='15' && procedimento.tipoProcedimento.id!='16'  }">
				  <logic:notEmpty name="soggettoForm" property="misura.art67">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.articolo67" /></td>
				</tr>
				<tr>
					<td><form:label path="misura.art67" cssClass="required">
							<spring:message code="soggetto.misurapersonale.applicazioneart67" />
						</form:label></td>
					<!--  SITMP-91 DG  casistica  cauzione proveniente da tribunale -->
					<td colspan="3">
						<!-- <form:radiobutton path="misura.art67" value="S" disabled="${disabledObject}" /> -->
						<form:radiobutton path="misura.art67" value="S" disabled="true" /> 
						<form:label path="misura.art67">
							<spring:message code="si" />
						</form:label> 
						<!-- <form:radiobutton path="misura.art67" value="N" disabled="${disabledObject}" />  -->
						
						<form:radiobutton path="misura.art67" value="N" disabled="true" /> 
						
						<form:label path="misura.art67">
						
								<spring:message code="no" />
							
						</form:label> <form:errors path="misura.art67" class="testoRosso" /></td>
				</tr>
				</logic:notEmpty>
				</c:if>
			</table>
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<logic:notEqual scope="session" name="atto" property="statoAtto.id" value="3" >
						<c:if test="${profilo.ruolo!='MAGISTRATO'}">
						<td class="td_tasto">
						<c:choose>
						<c:when test="${esitoReadOnly!=null && esitoReadOnly=='true'}">
							<input type="submit" value="Salva" name="SalvaMisuraDecisorioIIGrado" id="SalvaMisuraDecisorioIIGrado" disabled="disabled">
						</c:when>
						<c:otherwise>
							<input type="submit" value="Salva" name="SalvaMisuraDecisorioIIGrado" id="SalvaMisuraDecisorioIIGrado">
						</c:otherwise>
						</c:choose>
						
						
						</td>
				        </c:if>
				    </logic:notEqual >
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>

				
				</tr>
			</table>
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>

        	
        	
        	<c:if test="${procedimento.tipoProcedimento.id=='15'  }">
			<script type="text/javascript">	
              if(document.getElementsByName("misura.decisioneMisuraPersonaleIIGrado.id")[0].value != null){
					
					if(document.getElementsByName("misura.decisioneMisuraPersonaleIIGrado.id")[0].value != '-1'
						&& document.getElementsByName("misura.decisioneMisuraPersonaleIIGrado.id")[0].value != '235'
							&& document.getElementsByName("misura.decisioneMisuraPersonaleIIGrado.id")[0].value != '236'
								&& document.getElementsByName("misura.decisioneMisuraPersonaleIIGrado.id")[0].value != '237'
									&& document.getElementsByName("misura.decisioneMisuraPersonaleIIGrado.id")[0].value != '238'
										&& document.getElementsByName("misura.decisioneMisuraPersonaleIIGrado.id")[0].value != '264'){	
                  	if(document.getElementsByName("misura.importo")!= null && document.getElementsByName("misura.importo").length != 0) 
						document.getElementsByName("misura.importo")[0].disabled=true;
				    //Segnalazione EMS-97
                  	//if(document.getElementsByName("misura.note")!= null &&  document.getElementsByName("misura.note").length != 0)
					//	document.getElementsByName("misura.note")[0].disabled=true;	
					}
				}
			
			</script>
			</c:if>
			
			<c:if test="${procedimento.tipoProcedimento.id!='15'  }">

		<script type="text/javascript">	
				$(document).ready(function() {
					
					disabilitaBottoneSalvaDecisorioIIGrado('${disabled}');
					
					$('#SalvaMisuraDecisorioIIGrado').bind('submit', function() {
				        $(":disabled").removeAttr('disabled');
				    });
					
				});					
				
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