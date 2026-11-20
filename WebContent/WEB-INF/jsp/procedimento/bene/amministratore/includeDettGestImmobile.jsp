<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 					<fmt:setLocale value="it_IT"/>
 					<tr>
						<td><label><spring:message code="bene.gest.stima"/></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneImmobileGest.stima}" type="number" pattern="###,###,##0.00" /></td>
			        	<td><label><spring:message code="bene.gest.dataStima"/></label></td>
			        	<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneImmobileGest.dataStima}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>
					<tr>
						<td><label><spring:message code="bene.gest.dirittiTerzi" /></label></td>
					  	<td>
					 		<c:if test="${!empty beneForm.datiBeneImmobileGest.dirittiTerzi}">
						    	<c:out value='${(beneForm.datiBeneImmobileGest.dirittiTerzi?"Si":"No")}' />
							</c:if>
						</td>

					    <td><label><spring:message code="bene.gest.descDirittiTerzi" /></label></td>
						<td><c:out value="${beneForm.datiBeneImmobileGest.descDirittiTerzi}" /></td>
					</tr>	
					<tr>						    
					    <td><label><spring:message code="bene.gest.stimaOneriRistrutturazione" /></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneImmobileGest.stimaOneriRistrutturazione}" type="number" pattern="###,###,##0.00" /></td>
					    <td><label><spring:message code="bene.gest.confiscaParziale" /></label></td>
					    <td>
					 		<c:if test="${!empty beneForm.datiBeneImmobileGest.confiscaParziale}">
						    	<c:out value='${(beneForm.datiBeneImmobileGest.confiscaParziale?"Si":"No")}' />
							</c:if>
						</td>
					    
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.beneDivisibile" /></label></td>
					    <td>
					 		<c:if test="${!empty beneForm.datiBeneImmobileGest.beneDivisibile}">
						    	<c:out value='${(beneForm.datiBeneImmobileGest.beneDivisibile?"Si":"No")}' />
							</c:if>
						</td>
					    
					    <td><label><spring:message code="bene.gest.utilizzoAutonomoQuota" /></label></td>
					    <td>
					 		<c:if test="${!empty beneForm.datiBeneImmobileGest.utilizzoAutonomoQuota}">
						    	<c:out value='${(beneForm.datiBeneImmobileGest.utilizzoAutonomoQuota?"Si":"No")}' />
							</c:if>
						</td>
					    
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.vocazioneTuristica" /></label></td>
					    <td>
					 		<c:if test="${!empty beneForm.datiBeneImmobileGest.vocazioneTuristica}">
						    	<c:out value='${(beneForm.datiBeneImmobileGest.vocazioneTuristica?"Si":"No")}' />
							</c:if>
						</td>
					    
					    <td><label><spring:message code="bene.gest.fabbricatoInteramenteConfiscato" /></label></td>
					    <td>
					 		<c:if test="${!empty beneForm.datiBeneImmobileGest.fabbricatoInteramenteConfiscato}">
						    	<c:out value='${(beneForm.datiBeneImmobileGest.fabbricatoInteramenteConfiscato?"Si":"No")}' />
							</c:if>
						</td>
					    
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.immobileOccupato" /></label></td>
					    <td>
					 		<c:if test="${!empty beneForm.datiBeneImmobileGest.immobileOccupato}">
						    	<c:out value='${(beneForm.datiBeneImmobileGest.immobileOccupato?"Si":"No")}' />
							</c:if>
						</td>
					    
					   	<td><label><spring:message code="bene.gest.descTipoOccupazione" /></label></td>
						<td><c:out value="${beneForm.datiBeneImmobileGest.descTipoOccupazione}" /></td>
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.descAltroTipoOccupazione" /></label></td>
						<td colspan="3" align="left"><c:out value="${beneForm.datiBeneImmobileGest.descAltroTipoOccupazione}" /></td>
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.dataStipulaContratto" /></label></td>
						<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneImmobileGest.dataStipulaContratto}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					    <td><label><spring:message code="bene.gest.dataScadenzaContratto" /></label></td>
						<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneImmobileGest.dataScadenzaContratto}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.canoneAnnuoLocazione" /></label></td>
						<td colspan="3" align="left"><fmt:formatNumber value="${beneForm.datiBeneImmobileGest.canoneAnnuoLocazione}" type="number" pattern="###,###,##0.00" /></td>
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.mensilePattuito" /></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneImmobileGest.mensilePattuito}" type="number" pattern="###,###,##0.00" /></td>
					    <td><label><spring:message code="bene.gest.mensileVersato" /></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneImmobileGest.mensileVersato}" type="number" pattern="###,###,##0.00" /></td>
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.regolaritaPagamenti" /></label></td>
					    <td>
					 		<c:if test="${!empty beneForm.datiBeneImmobileGest.regolaritaPagamenti}">
						    	<c:out value='${(beneForm.datiBeneImmobileGest.regolaritaPagamenti?"Si":"No")}' />
							</c:if>
						</td>
					    
					    <td><label><spring:message code="bene.gest.descrizionePagamenti" /></label></td>
						<td><c:out value="${beneForm.datiBeneImmobileGest.descrizionePagamenti}" /></td>
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.importoDovuto" /></label></td>
						<td colspan="3" align="left"><fmt:formatNumber value="${beneForm.datiBeneImmobileGest.importoDovuto}" type="number" pattern="###,###,##0.00" /></td>
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.importoDovutoDal" /></label></td>
						<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneImmobileGest.importoDovutoDal}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
 			        	<td><label><spring:message code="bene.gest.importoDovutoAl" /></label></td>
 			        	<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneImmobileGest.importoDovutoAl}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.condominioArretrato" /></label></td>
						<td colspan="3" align="left"><fmt:formatNumber value="${beneForm.datiBeneImmobileGest.condominioArretrato}" type="number" pattern="###,###,##0.00" /></td>
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.condominioArretratoDal" /></label></td>
						<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneImmobileGest.condominioArretratoDal}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
 			        	<td><label><spring:message code="bene.gest.condominioArretratoAl" /></label></td>
	 			        <td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneImmobileGest.condominioArretratoAl}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.condominioInScadenza" /></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneImmobileGest.condominioInScadenza}" type="number" pattern="###,###,##0.00" /></td>
					    <td><label><spring:message code="bene.gest.descTipoOccupanti" /></label></td>
						<td><c:out value="${beneForm.datiBeneImmobileGest.descTipoOccupanti}" /></td>
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.presenzaServitu" /></label></td>
					    <td>
					 		<c:if test="${!empty beneForm.datiBeneImmobileGest.presenzaServitu}">
						    	<c:out value='${(beneForm.datiBeneImmobileGest.presenzaServitu?"Si":"No")}' />
							</c:if>
						</td>
					    
						<td><label><spring:message code="bene.gest.usiCivici" /></label></td>
					    <td>
					 		<c:if test="${!empty beneForm.datiBeneImmobileGest.usiCivici}">
						    	<c:out value='${(beneForm.datiBeneImmobileGest.usiCivici?"Si":"No")}' />
							</c:if>
						</td>
					    
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.altriVincoli" /></label></td>
					    <td colspan="3" align="left">
					 		<c:if test="${!empty beneForm.datiBeneImmobileGest.altriVincoli}">
						    	<c:out value='${(beneForm.datiBeneImmobileGest.altriVincoli?"Si":"No")}' />
							</c:if>
						</td>
					    
					</tr>