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

		<spring:url var="azione" value="/procedimento/soggetto/misuraPersonaleSentenzaCassazione.htm" />
		<p class="titolo_page">
		<c:if test="${procedimento.tipoProcedimento.id != 16}"> 
			<spring:message code="soggetto.misurapersonale" />
		</c:if>	
		<c:if test="${procedimento.tipoProcedimento.id == 16}"> 
			<spring:message code="soggetto.decisione.permesso" />		
		</c:if>
		</p>
		<form:form method="post" action="${azione}" commandName="soggettoForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
		<br />
		<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
		<br />

		    <c:if test="${procedimento.tipoProcedimento.id == 16}"> 
			 <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><c:out value="${soggettoForm.descrizioneSoggetto}" /></td>
				</tr>
			 </table>
			 <br/>
			 <table class="tb_form" cellspacing="0" cellpadding="0" border="0">	
				<tr>
					<td><form:label path="descRichiesta">
							<spring:message code="richiesta" />
						</form:label></td>
					<td colspan="3"><c:out value="${soggettoForm.descRichiesta}" /></td>
				</tr>						 	
			 </table>
			 
			 <br/>	
			</c:if>
          
           <c:if test="${procedimento.tipoProcedimento.id=='16'}"> 			
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
													 
													 
														<c:choose>
															<c:when test="${ bene.misura.tipoMisura.id != '25' && bene.misura.tipoMisura.id != '26'}">
												              <display:column title="Misura" sortable="false" style="width:50%">
																	<c:if test="${bene.misura.durata != ''}">
																		<c:out value="${bene.misura.tipoMisura.descrizione} (${bene.misura.durata})"></c:out>
																	</c:if>
																	<c:if test="${bene.misura.durata == ''}">
																		<c:out value="${bene.misura.tipoMisura.descrizione}"></c:out>
																	</c:if>
												            </display:column>
															<c:if test="${bene.misura.descEsito != null}">												
															  <display:column title="" style="width:80%">
																<c:out value="${bene.misura.descEsito} "></c:out>
															  </display:column>
															</c:if> 
															</c:when>
														</c:choose>													 						 

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
			</c:if>	

			<br />			

			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				
			    <form:hidden path="idSoggetto" />
				<form:hidden path="idVersione" />
				<form:hidden path="tipoSoggetto" />
				<form:hidden path="idAtto" />
				<form:hidden path="schermataDiProvenienza" />
				<form:hidden path="tipologiaPrevenzioneDb.id" />
                <form:hidden path="misura.decisioneMisuraPersonale.descrizione" />
 			
 			  <c:if test="${procedimento.tipoProcedimento.id != 16}"> 
				<tr>
					<td class="td_titolo" colspan="4"><c:out value="${soggettoForm.descrizioneSoggetto}" /></td>
				</tr>
			  </c:if>	
			</table>
			<br />
			<table class="tb_form" border="0">	
			
		 <c:if test="${procedimento.tipoProcedimento.id!='15'}">	
		          <c:if test="${soggettoForm.misura.decisioneMisuraPersonale.descrizione!= null}"> 					
				    <tr>
						<td><form:label path="misura.decisioneMisuraPersonale.id">
								<spring:message code="soggetto.misurapersonale.decisioneIGrado" />
							</form:label></td>
						<td colspan="3"><c:out value="${soggettoForm.misura.decisioneMisuraPersonale.descrizione}" /></td>
					</tr>
				</c:if>	
				    <logic:notEqual scope="session" name="atto" property="categoriaAtto.id" value="39" >	
					 <tr>
						<td><form:label path="misura.decisioneMisuraPersonale.id">
								<spring:message code="soggetto.misurapersonale.decisioneIIGrado" />
							</form:label></td>
						<td colspan="3"><c:out value="${soggettoForm.misura.decisioneMisuraPersonaleIIGrado.descrizione}" /></td>
					 </tr>
				    </logic:notEqual>	
					<tr>
						<td><form:label path="misura.decisioneMisuraPersonaleSentenzaCassazione.id">
								<spring:message code="soggetto.misurapersonale.sentenzaCassazione" />
							</form:label></td>	
						<td colspan="3">	
							<logic:equal scope="session" name="atto" property="statoAtto.id" value="3" >
						    <strong><c:out value="${soggettoForm.misura.decisioneMisuraPersonaleSentenzaCassazione.descrizione}" /></strong>			
							</logic:equal>
							<logic:notEqual scope="session" name="atto" property="statoAtto.id" value="3" >
							<form:select path="misura.decisioneMisuraPersonaleSentenzaCassazione.id" size="1">
									<form:option value="-1" label="------Seleziona Decisione -------" />
									<form:options items="${esiti}" itemValue="id" itemLabel="descrizione" />
							 </form:select>
							 </logic:notEqual>	
					   </td>						
					</tr>

				 <c:if test="${procedimento.tipoProcedimento.id=='14' || procedimento.tipoProcedimento.id=='15' || procedimento.tipoProcedimento.id=='16' || procedimento.tipoProcedimento.id=='8'}">
					   <logic:notEqual scope="session" name="atto" property="statoAtto.id" value="3" >
					    <tr>

							<td><form:label path="dataRicezioneSentenza" cssClass="required">
									<spring:message code="soggetto.dataRicezioneSentenza" />
								</form:label></td>
							<td  colspan="3"><form:input path="dataRicezioneSentenza" maxlength="10" id="id_dataRicezioneSentenza" /> <br> <form:errors path="dataRicezioneSentenza" class="testoRosso" /></td>

					    </tr>
					    <tr>
							<td><form:label path="numeAnnoSentenza" cssClass="required"><spring:message code="soggetto.numeAnnoSentenza" /></form:label></td>
							<td><form:input path="numeAnnoSentenza" id="numeAnnoSentenza" maxlength="4" size="5"/>
								<form:errors path="numeAnnoSentenza" class="testoRosso"/></td>
								
							<td><form:label path="numeSentenza" cssClass="required"><spring:message code="soggetto.numeSentenza" /></form:label></td>
							<td><form:input path="numeSentenza" maxlength="12"/>
								<form:errors path="numeSentenza" class="testoRosso"/></td>
						</tr> 			
					   </logic:notEqual>	 					 			 
				</c:if>					
				<c:if test="${procedimento.tipoProcedimento.id!='16'}">  	
				<tr>
					<td><form:label path="tipologiaPrevenzione.id">
							<spring:message code="procedimento.tipologiaPrevenzione" />
						</form:label></td>
					<td><c:out value="${soggettoForm.tipologiaPrevenzione.descrizione}" /></td>			
					
					<td><form:label path="normativa.id">
							<spring:message code="procedimento.normativa" />
						</form:label></td>
					<td><c:out value="${soggettoForm.normativa.descrizione}" /></td>	
				</tr>
				<tr>
					<td><form:label path="misura.tipoMisura">
							<spring:message code="soggetto.misurapersonale" />
						</form:label></td>
					<td><c:out value="${soggettoForm.misura.tipoMisura.descrizione}" /></td>
					<td><form:label path="misura.normativaDiRiferimento">
							<spring:message code="procedimento.normativa" />
						</form:label></td>
					<td><c:out value="${soggettoForm.misura.normativaDiRiferimento.descrizione}" /></td>
				</tr>
				<tr>
					<td><form:label path="misura.localita">
							<spring:message code="soggetto.misurapersonale.localita" />
						</form:label></td>
					<td colspan="3"><c:out value="${soggettoForm.misura.localita}" /></td>

				</tr>
				<tr>
					<td><form:label path="misura.anni">
							<spring:message code="soggetto.misurapersonale.durata" />
						</form:label></td>
					<td colspan="3"><form:input path="misura.anni" maxlength="1" size="2" disabled="true" /> <form:errors path="misura.anni" class="testoRosso" />&nbsp;&nbsp; <form:input path="misura.mesi" maxlength="2" size="4" disabled="true"/> <form:errors path="misura.mesi" class="testoRosso" />&nbsp;&nbsp; <form:input path="misura.giorni" maxlength="2" size="4" disabled="true"/> <form:errors path="misura.giorni" class="testoRosso" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.prescrizioni" /></td>
				</tr>


				<c:forEach var="prescrizione" items="${listaPrescrizioni}">
					<tr>
						<td colspan="4"><form:checkbox path="misura.idSelezionati" value="${prescrizione.id}" label="${prescrizione.descrizione}" disabled="true" /></td>
					</tr>
				</c:forEach>
				<tr>
					<td><form:label path="misura.ulterioriPrescrizioni">
							<spring:message code="soggetto.misurapersonale.ulterioriprescrizioni" />
						</form:label></td>
					<td colspan="3"><form:textarea path="misura.ulterioriPrescrizioni" id="note" readonly="true" rows="2" cssStyle="width:98%" disabled="true"/></td>
				</tr>
               </c:if>
           </c:if>
           
  	      <c:if test="${procedimento.tipoProcedimento.id!='16'}">         

                <logic:notEmpty name="soggettoForm" property="misura.cauzione">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.cauzione" /></td>
					</tr>
	
				 <c:if test="${procedimento.tipoProcedimento.id!='14' && procedimento.tipoProcedimento.id!='15' && procedimento.tipoProcedimento.id!='16'  && procedimento.tipoProcedimento.id!='8'}">
					<tr>
						<td><form:label path="misura.decisioneCauzioneSentenzaCassazione.id">
								<spring:message code="soggetto.misurapersonale.sentenzaCassazione" />
							</form:label></td>						
						<td colspan="3"><strong><c:out value="${soggettoForm.misura.decisioneCauzioneSentenzaCassazione.descrizione}" /></strong></td>		

					</tr>
				 </c:if>
				 
				 <c:if test="${procedimento.tipoProcedimento.id=='15'}">
				 
				    <tr>
						<td><form:label path="misura.decisioneMisuraPersonale.id">
								<spring:message code="soggetto.misurapersonale.decisioneIGrado" />
							</form:label></td>
						<td colspan="3"><c:out value="${soggettoForm.misura.decisioneMisuraPersonale.descrizione}" /></td>
					</tr>
				    <logic:notEqual scope="session" name="atto" property="categoriaAtto.id" value="39" >	
					 <tr>
						<td><form:label path="misura.decisioneMisuraPersonale.id">
								<spring:message code="soggetto.misurapersonale.decisioneIIGrado" />
							</form:label></td>
						<td colspan="3"><c:out value="${soggettoForm.misura.decisioneMisuraPersonaleIIGrado.descrizione}" /></td>
					 </tr>
				    </logic:notEqual>					 
				 
					   <logic:notEqual scope="session" name="atto" property="statoAtto.id" value="3" >
					<tr>
						<td><form:label path="misura.decisioneMisuraPersonaleSentenzaCassazione.id">
								<spring:message code="soggetto.misurapersonale.sentenzaCassazione" />
							</form:label></td>	
						<td colspan="3">	
							<logic:equal scope="session" name="atto" property="statoAtto.id" value="3" >
						    <strong><c:out value="${soggettoForm.misura.decisioneMisuraPersonaleSentenzaCassazione.descrizione}" /></strong>			
							</logic:equal>
							<logic:notEqual scope="session" name="atto" property="statoAtto.id" value="3" >
							<form:select path="misura.decisioneMisuraPersonaleSentenzaCassazione.id" size="1">
									<form:option value="-1" label="------Seleziona Decisione -------" />
									<form:options items="${esiti}" itemValue="id" itemLabel="descrizione" />
							 </form:select>
							 </logic:notEqual>	
					   </td>						
					</tr>					    
					    
					    <tr>

							<td><form:label path="dataRicezioneSentenza" cssClass="required">
									<spring:message code="soggetto.dataRicezioneSentenza" />
								</form:label></td>
							<td  colspan="3"><form:input path="dataRicezioneSentenza" maxlength="10" id="id_dataRicezioneSentenza" /> <br> <form:errors path="dataRicezioneSentenza" class="testoRosso" /></td>

					    </tr>
					    <tr>
							<td><form:label path="numeAnnoSentenza" cssClass="required"><spring:message code="soggetto.numeAnnoSentenza" /></form:label></td>
							<td><form:input path="numeAnnoSentenza" id="numeAnnoSentenza" maxlength="4" size="5"/>
								<form:errors path="numeAnnoSentenza" class="testoRosso"/></td>
								
							<td><form:label path="numeSentenza" cssClass="required"><spring:message code="soggetto.numeSentenza" /></form:label></td>
							<td><form:input path="numeSentenza" maxlength="12"/>
								<form:errors path="numeSentenza" class="testoRosso"/></td>
						</tr> 			
					   </logic:notEqual>	 					 			 
				</c:if>					 		
					<tr>
						<td><form:label path="misura.cauzione">
								<spring:message code="soggetto.misurapersonale.cauzione" />
							</form:label></td>
						<td colspan="3"><form:radiobutton path="misura.cauzione" value="S"  disabled="true" /> <form:label path="misura.cauzione">
								<spring:message code="si" />
							</form:label> <form:radiobutton path="misura.cauzione" value="N"  disabled="true" /> <form:label path="misura.cauzione">			
									<spring:message code="no" />
							</form:label>
					</tr>
	
					<logic:present name="importoCauzione">
						<tr>
							<td><form:label path="misura.importo">
									<spring:message code="soggetto.misurapersonale.cauzioneimporto" />
								</form:label></td>
							<td colspan="3"><form:input path="misura.importo" maxlength="15" size="20" disabled="true" /> <form:errors path="misura.importo" class="testoRosso" />&nbsp;&nbsp;</td>
						</tr>
						<tr>
							<td><form:label path="misura.note">
									<spring:message code="soggetto.misurapersonale.note" />
								</form:label></td>
							<td colspan="3"><form:textarea path="misura.note" rows="2" cssStyle="width:98%" disabled="true" /></td>
						</tr>
					</logic:present>
				</logic:notEmpty>
				<logic:notEmpty name="soggettoForm" property="misura.art67">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.articolo67" /></td>
					</tr>
				
				  <c:if test="${procedimento.tipoProcedimento.id!='14' && procedimento.tipoProcedimento.id!='15' && procedimento.tipoProcedimento.id!='16' && procedimento.tipoProcedimento.id!='8'}">	
					<tr>
						<td><form:label path="misura.decisioneArt67SentenzaCassazione.id">
								<spring:message code="soggetto.misurapersonale.sentenzaCassazione" />
							</form:label></td>						
						<td colspan="3"><strong><c:out value="${soggettoForm.misura.decisioneArt67SentenzaCassazione.descrizione}" /></strong></td>		

					</tr>	
				  </c:if>					
					<tr>
						<td><form:label path="misura.art67">
								<spring:message code="soggetto.misurapersonale.applicazioneart67" />
							</form:label></td>
						<td colspan="3"><form:radiobutton path="misura.art67" value="S" disabled="true" /> <form:label path="misura.art67">
								<spring:message code="si" />
							</form:label> <form:radiobutton path="misura.art67" value="N" disabled="true" /> <form:label path="misura.art67">
							
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
						<td class="td_tasto"><input type="submit" value="Salva" name="SalvaMisuraDecisorioIIIGrado" id="SalvaMisuraDecisorioIIIGrado"></td>
				        </c:if>
				    </logic:notEqual >										
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
				
				</tr>
			</table>
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>



	</tiles:putAttribute>
</tiles:insertDefinition>


<script type="text/javascript">

	$(document).ready(function() {
		prendiData("#id_dataRicezioneSentenza",true);
	});
</script>