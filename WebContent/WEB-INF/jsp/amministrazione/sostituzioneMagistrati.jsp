<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
			<p class="titolo_page">
				<spring:message code="magistrato.titolo.sostituzione" />		
			</p>
			<spring:url var="azione" value="/funzioniAmministrazione/sostituzioneMagistrati.htm" />
			<spring:url var="azione2" value="/funzioniAmministrazione/sostituzioneMagistratiDettaglio.htm" />
			
			<form:form id="id_formRicerca" method="post" action="${azione}" commandName="magistratoForm">
				<br />
				<form:hidden path="tipoMagistrato" />
				
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="20%"><form:label path="dataFineAssegnazione"><spring:message code="magistrato.dataFineAssegnazioneMagistrato"/></form:label></td>
			        	<td>
				        	<c:if test="${magistratoForm.tipoMagistrato==null}" >
			        			<form:input path="dataFineAssegnazione" maxlength="10" id="dataFineAssegnazione" />				        	
				        	</c:if>		        	   
				        	<c:if test="${magistratoForm.tipoMagistrato!=null}" >
				        		<form:input path="dataFineAssegnazione" maxlength="10" id="dataFineAssegnazione" readonly="true" />
				        	</c:if>
			        		<form:errors path="dataFineAssegnazione" class="testoRosso"/>
			        	</td>
			    	</tr>
			  	</table>
               	<c:if test="${magistratoForm.tipoMagistrato==null}" >
               		<br/>
                	<table class="tb_bottoni" align="center">
               			<tr>
	                  		<c:if test="${sessionScope.tipoUtente=='SMP'}">
	                  			<td class="td_tasto">
	                   			<input type="submit" name="MAGISTRATIPM" title="Magistrati PM" value="Magistrati PM"/>
	                   		</td>
	                  		</c:if>
	                  		<c:if test="${sessionScope.tipoUtente=='SMP_GIU'}">
	                  			<td class="td_tasto">
	                  				<input type="submit" name="MAGISTRATICOLLEGIO" title="Magistrati del Collegio" value="Magistrati del Collegio"/>
	               			</td>
	               			<td class="td_tasto">
	                  				<input type="submit" name="GIUDICEDELEGATO" title="Giudice Delegato" value="Giudice Delegato"/>
	              				</td>
	                  		</c:if>
                  		</tr>
                  	</table>						
				</c:if>  
				<c:if test="${magistratoForm.tipoMagistrato!=null}" >
					<table class="tb_form">
						<tr>
							<td class="td_titolo" colspan="4">
								<c:if test="${magistratoForm.tipoMagistrato=='MAGISTRATIPM'}" >
									<spring:message text="Magistrati PM"/>
								</c:if>
								<c:if test="${magistratoForm.tipoMagistrato=='MAGISTRATICOLLEGIO'}" >
									<spring:message text="Magistrati Collegio"/>
								</c:if>
								<c:if test="${magistratoForm.tipoMagistrato=='GIUDICEDELEGATO'}" >
									<spring:message text="Guidice Delegato"/>
								</c:if>
							</td>
						</tr>
				</c:if>                 		
                    </tr>
				</table>

				<c:if test="${magistratoForm.tipoMagistrato!=null}" >
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td><form:label path="magistratoDaSostituire.id">
									<spring:message code="magistrato.sostituzione.magistratoDaSostituire" />
								</form:label>
							</td>
							<td>
								<form:select path="magistratoDaSostituire.id" size="1" onchange="document.forms[0].submit();">	
									<form:option value="-1" label="------Seleziona Magistrato-------" />
									<form:options items="${magistratiDaSostituire}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>	
								<form:errors path="magistratoDaSostituire.id" class="testoRosso"/>
							</td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	
						<logic:notEmpty name="procedimentiMagistratoSostituito">
							<tr>
								<td colspan="7">								
									<display:table class="tb_lista" name="${procedimentiMagistratoSostituito}" uid="magProc" pagesize="10" requestURI="${azione2}"  > 
										<display:column property="TProcedimento.numeAnnoProc" title="Anno" sortable="true" style="width: 5%;" />
										<display:column property="TProcedimento.numeProc" title="Numero" sortable="true" style="width: 10%;"/>  
										<display:column property="TProcedimento.dataIscrizione" title="Iscrizione Procedimento" format="{0,date,dd/MM/yyyy}" sortable="true" style="width: 10%;"/>
										<display:column property="TTpTipoRuoloMag.descRuolo" title="Ruolo nel procedimento" sortable="true" style="width: 75%;"/>
									</display:table>
								</td>
							</tr>	
						</logic:notEmpty>
				    	
						<tr>
							<td><form:label path="magistratoSubentrante.id">
									<spring:message code="magistrato.sostituzione.magistratoSubentrante" />
								</form:label>
							</td>						
							<td>
								<form:select path="magistratoSubentrante.id" size="1">	
									<form:option value="-1" label="------Seleziona Magistrato-------" />
									<form:options items="${magistratiSubentranti}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>	
								<form:errors path="magistratoSubentrante.id" class="testoRosso"/>
							</td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>						
				    	</tr>
						<tr>
							<td><form:label path="motivazioneSostituzione.id">
									<spring:message code="magistrato.sostituzione.motivazioneSostituzione" />
								</form:label>
							</td>						
							<td>
								<form:select path="motivazioneSostituzione.id" size="1">	
									<form:options items="${motivazioniSostituzione}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>	
							</td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    	</tr>		
				    	<tr>
				    		<td>
				    			<form:radiobutton path="checkProcedimenti" value="T" onclick="selezioneCheck(this.value)" />
				    			<spring:message code="magistrato.sostituzione.tuttiIProcedimenti" />
				    		</td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    		<td></td>
				    	</tr>
				    	<tr>	
							<td>
				    			<form:radiobutton path="checkProcedimenti" value="I" onclick="selezioneCheck(this.value)" />
				    			<spring:message code="magistrato.sostituzione.intervalloProcedimenti" />
				    		</td>
				    		
				    		<td colspan="6">
				    			<table>
				    				<tr>
										<td>
											<form:label path="procedimentoDaNum">
												<spring:message code="magistrato.sostituzione.dalProcedimento"/>
											</form:label>&nbsp
										</td>
										<td>
											<form:label path="procedimentoDaNum">
												<spring:message code="procedimento.numero"/>
											</form:label>
										</td>	
							        	<td>		        	   
							        		<form:input path="procedimentoDaNum" id="procedimentoDaNum" />
							        		<form:errors path="procedimentoDaNum" class="testoRosso"/>
							        	</td>
										<td>
											<form:label path="procedimentoDaAnno">
												<spring:message code="procedimento.anno"/>
											</form:label>
										</td>	
							        	<td>		        	   
							        		<form:input path="procedimentoDaAnno" id="procedimentoDaAnno" size="4"/>
							        		<form:errors path="procedimentoDaAnno" class="testoRosso"/>
							        	</td>
										<td>&nbsp&nbsp&nbsp
											<form:label path="procedimentoANum">
												<spring:message code="magistrato.sostituzione.alProcedimento"/>
											</form:label>&nbsp
										</td>
							        	<td>
											<form:label path="procedimentoANum">
												<spring:message code="procedimento.numero"/>
											</form:label>
										</td>	
							        	<td>		        	   
							        		<form:input path="procedimentoANum" id="procedimentoANum" />
							        		<form:errors path="procedimentoANum" class="testoRosso"/>
							        	</td>
										<td>
											<form:label path="procedimentoAAnno">
												<spring:message code="procedimento.anno"/>
											</form:label>
										</td>	
							        	<td>		        	   
							        		<form:input path="procedimentoAAnno" id="procedimentoAAnno" size="4" />
							        		<form:errors path="procedimentoAAnno" class="testoRosso"/>
							        	</td>	
						        	</tr>
				        		</table>
							</td>				        				        				        	
				    	</tr>		    				    	
				  	</table>		
			  	
					<logic:notEmpty name="procedimentiNonAggiornati">
						</br>
						<table class="tb_form">
							<tr>
								<td class="td_titolo" colspan="4">
									<spring:message code="magistrato.elencoProcedimentiNonAggiornati"/>
								</td>
							</tr>
						</table>
									 	
						<display:table class="tb_lista" name="${procedimentiNonAggiornati}" uid="proc" > 
							<display:column property="numeAnnoProc" title="Anno" sortable="true" style="width: 10%;" />
							<display:column property="numeProc" title="Numero" sortable="true" style="width: 90%;"/>  
						</display:table>
			  		
					</logic:notEmpty>
								  	    
			  	    </br>
			  	    
					<table class="tb_bottoni" align="center">
	                 	<tr>
	                  		<td class="td_tasto">
	                   			<input type="submit" name="Conferma" title="Conferma" value="Conferma"/>
	                   		</td>
	                   		
	                  		<td class="td_tasto">
	                   			<input type="submit" name="Annulla" title="Annulla" value="Annulla"/>
	                   		</td>	                   		
                        </tr>
					</table>
				
			  	</c:if>		
			  	
				</form:form>
			
			<script type="text/javascript">

				<c:if test="${magistratoForm.tipoMagistrato==null}" >
				
					$(document).ready(function() {
						prendiData("#dataFineAssegnazione",true);
					});
					
				</c:if>
				
				function selezioneCheck(val) {
					if(val == 'T') {
						document.getElementById("procedimentoDaNum").value = '';
						document.getElementById("procedimentoDaAnno").value = '';
						document.getElementById("procedimentoANum").value = '';
						document.getElementById("procedimentoAAnno").value = '';
						
						document.getElementById("procedimentoDaNum").disabled = true;
						document.getElementById("procedimentoDaAnno").disabled = true;
						document.getElementById("procedimentoANum").disabled = true;
						document.getElementById("procedimentoAAnno").disabled = true;
					} else if(val == 'I' || val == null) {
						document.getElementById("procedimentoDaNum").disabled = false;
						document.getElementById("procedimentoDaAnno").disabled = false;
						document.getElementById("procedimentoANum").disabled = false;
						document.getElementById("procedimentoAAnno").disabled = false;
					}
				}

				selezioneCheck('${magistratoForm.checkProcedimenti}');
				
			</script>

		</tiles:putAttribute>
	</tiles:insertDefinition>
