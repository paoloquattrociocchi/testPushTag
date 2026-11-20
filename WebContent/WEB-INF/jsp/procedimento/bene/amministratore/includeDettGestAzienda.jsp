<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 					<fmt:setLocale value="it_IT"/>
 					<tr>
						<td><label><spring:message code="bene.gest.stima"/></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneAziendaGest.stima}" type="number" pattern="###,###,##0.00" /></td>
			        	<td><label><spring:message code="bene.gest.dataStima"/></label></td>
			        	<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneAziendaGest.dataStima}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>
					<tr>
					  	<td><label><spring:message code="bene.gest.dirittiTerzi" /></label></td>
					 	<td>
					 		<c:if test="${!empty beneForm.datiBeneAziendaGest.dirittiTerzi}">
						    	<c:out value='${(beneForm.datiBeneAziendaGest.dirittiTerzi?"Si":"No")}' />
							</c:if>
						</td>
					 	
					    <td><label><spring:message code="bene.gest.descDirittiTerzi" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.descDirittiTerzi}" /></td>
					</tr>	
					<tr>						    
					    <td><label><spring:message code="bene.gest.trascrizioneSequestro" /></label></td>
					 	<td>
					 		<c:if test="${!empty beneForm.datiBeneAziendaGest.trascrizioneSequestro}">
						    	<c:out value='${(beneForm.datiBeneAziendaGest.trascrizioneSequestro?"Si":"No")}' />
							</c:if>
						</td>

					 	<td><label><spring:message code="bene.gest.dataTrascrizioneSequestro" /></label></td>
					    <td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneAziendaGest.dataTrascrizioneSequestro}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>
					<tr>
					    <td><label><spring:message code="bene.gest.descStatoAttivitaAziendale" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.descStatoAttivitaAziendale}" /></td>
					    <td><label><spring:message code="bene.gest.prospettivaProsecuzioneAttivita" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.prospettivaProsecuzioneAttivita}" /></td>
					</tr>
					<tr>
					    <td><label><spring:message code="bene.gest.descDestinazioneImpresa" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.descDestinazioneImpresa}" /></td>
					    <td><label><spring:message code="bene.gest.tipoOrganoAmministrativo" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.tipoOrganoAmministrativo}" /></td>
					</tr>
					<tr>
					    <td><label><spring:message code="bene.gest.nominativoAmministratore" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.nominativoAmministratore}" /></td>
					    <td><label><spring:message code="bene.gest.dataNominaAmministratore" /></label></td>
					    <td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneAziendaGest.dataNominaAmministratore}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>
					<tr>
					    <td><label><spring:message code="bene.gest.nominativoRappresentanteLegale" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.nominativoRappresentanteLegale}" /></td>
					    <td><label><spring:message code="bene.gest.dataNominaRappresentante" /></label></td>
						<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneAziendaGest.dataNominaRappresentante}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>
					<tr>
					    <td><label><spring:message code="bene.gest.liquidazioneSociale" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.liquidazioneSociale}" /></td>
					    <td><label><spring:message code="bene.gest.dataLiquidazione" /></label></td>
						<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneAziendaGest.dataLiquidazione}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>
					<tr>
					    <td><label><spring:message code="bene.gest.nominativoLiquidatore" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.nominativoLiquidatore}" /></td>
					    <td><label><spring:message code="bene.gest.tipoOrganoControllo" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.tipoOrganoControllo}" /></td>
					</tr>
					<tr>
					    <td><label><spring:message code="bene.gest.revisioneLegaleConti" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.revisioneLegaleConti}" /></td>
					    <td><label><spring:message code="bene.gest.nominativoRevisore" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.nominativoRevisore}" /></td>
					</tr>
					<tr>
					    <td><label><spring:message code="bene.gest.dataUltimoBilancioApprovato" /></label></td>
						<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneAziendaGest.dataUltimoBilancioApprovato}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					    <td><label><spring:message code="bene.gest.saldoUltimoBilancio" /></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneAziendaGest.saldoUltimoBilancio}" type="number" pattern="###,###,##0.00" /></td>
					</tr>
					<tr>
					    <td><label><spring:message code="bene.gest.luogoScrittureContabili" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.luogoScrittureContabili}" /></td>
					    <td><label><spring:message code="bene.gest.nominativaDetentoreScritture" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.nominativaDetentoreScritture}" /></td>
					</tr>
					<tr>
					    <td><label><spring:message code="bene.gest.numeroDipendenti" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.numeroDipendenti}" /></td>
					    <td><label><spring:message code="bene.gest.ccnl" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.ccnl}" /></td>
					</tr>
					<tr>
					    <td><label><spring:message code="bene.gest.tipoContrattoLavoro" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.tipoContrattoLavoro}" /></td>
					    <td><label><spring:message code="bene.gest.ammortizzatoreSociale" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.ammortizzatoreSociale}" /></td>
					</tr>
					<tr>
					    <td><label><spring:message code="bene.gest.contenziosi" /></label></td>
					    <td>
					 		<c:if test="${!empty beneForm.datiBeneAziendaGest.contenziosi}">
						    	<c:out value='${(beneForm.datiBeneAziendaGest.contenziosi?"Si":"No")}' />
							</c:if>
						</td>
					 	
					    <td><label><spring:message code="bene.gest.rispettoNormeSicurezza" /></label></td>
						<td>
					 		<c:if test="${!empty beneForm.datiBeneAziendaGest.rispettoNormeSicurezza}">
						    	<c:out value='${(beneForm.datiBeneAziendaGest.rispettoNormeSicurezza?"Si":"No")}' />
							</c:if>
						</td>
					 	
					</tr>
					<tr>
					    <td><label><spring:message code="bene.gest.lavoratoriStranieri" /></label></td>
						<td>
					 		<c:if test="${!empty beneForm.datiBeneAziendaGest.lavoratoriStranieri}">
						    	<c:out value='${(beneForm.datiBeneAziendaGest.lavoratoriStranieri?"Si":"No")}' />
							</c:if>
						</td>
					 						 	
					    <td><label><spring:message code="bene.gest.addettiIrregolari" /></label></td>
						<td>
					 		<c:if test="${!empty beneForm.datiBeneAziendaGest.addettiIrregolari}">
						    	<c:out value='${(beneForm.datiBeneAziendaGest.addettiIrregolari?"Si":"No")}' />
							</c:if>
						</td>
					 	
				 	</tr>
					<tr>
					    <td><label><spring:message code="bene.gest.lavoratoriFormazione" /></label></td>
						<td>
					 		<c:if test="${!empty beneForm.datiBeneAziendaGest.lavoratoriFormazione}">
						    	<c:out value='${(beneForm.datiBeneAziendaGest.lavoratoriFormazione?"Si":"No")}' />
							</c:if>
						</td>
					 	
					 	<td><label><spring:message code="bene.gest.posizioneSocio" /></label></td>
						<td><c:out value="${beneForm.datiBeneAziendaGest.posizioneSocio}" /></td>
					</tr>
