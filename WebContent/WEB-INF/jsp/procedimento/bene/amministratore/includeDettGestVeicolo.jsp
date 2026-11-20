<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 					<fmt:setLocale value="it_IT"/>
 					<tr>
						<td><label><spring:message code="bene.gest.stima"/></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneVeicoloGest.stima}" type="number" pattern="###,###,##0.00" /></td>
			        	<td><label><spring:message code="bene.gest.dataStima"/></label></td>
			        	<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneVeicoloGest.dataStima}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>
					<tr>
					  	<td><label><spring:message code="bene.gest.dirittiTerzi" /></label></td>
						<td>
					 		<c:if test="${!empty beneForm.datiBeneVeicoloGest.dirittiTerzi}">
						    	<c:out value='${(beneForm.datiBeneVeicoloGest.dirittiTerzi?"Si":"No")}' />
							</c:if>
						</td>

						<td><label><spring:message code="bene.gest.descDirittiTerzi" /></label></td>
						<td><c:out value="${beneForm.datiBeneVeicoloGest.descDirittiTerzi}" /></td>
					</tr>	
					<tr>	
						<td><label><spring:message code="bene.gest.beneRinvenuto" /></label></td>
					    <td colspan="3" align="left">
					 		<c:if test="${!empty beneForm.datiBeneVeicoloGest.beneRinvenuto}">
						    	<c:out value='${(beneForm.datiBeneVeicoloGest.beneRinvenuto?"Si":"No")}' />
							</c:if>
						</td>

					</tr>	
					<tr>						    
 						<td><label><spring:message code="bene.gest.dataPossesso" /></label></td>
			        	<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneVeicoloGest.dataPossesso}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					    <td><label><spring:message code="bene.gest.dataPerditaPossesso" /></label></td>
					    <td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneVeicoloGest.dataPerditaPossesso}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>	
					<tr>						    
					    <td><label><spring:message code="bene.gest.descStatoManutenzione" /></label></td>
						<td colspan="3" align="left"><c:out value="${beneForm.datiBeneVeicoloGest.descStatoManutenzione}" /></td>
					</tr>	
					<tr>						    
						<td><label><spring:message code="bene.gest.trascrizioneSequestro" /></label></td>
					    <td>
					 		<c:if test="${!empty beneForm.datiBeneVeicoloGest.trascrizioneSequestro}">
						    	<c:out value='${(beneForm.datiBeneVeicoloGest.trascrizioneSequestro?"Si":"No")}' />
							</c:if>
						</td>

						<td><label><spring:message code="bene.gest.dataTrascrizioneSequestro" /></label></td>
					    <td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneVeicoloGest.dataTrascrizioneSequestro}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>	
					<tr>						    
					    <td><label><spring:message code="bene.gest.descCustode" /></label></td>
						<td><c:out value="${beneForm.datiBeneVeicoloGest.descCustode}" /></td>
						<td><label><spring:message code="bene.gest.oneriCustodia" /></label></td>
					    <td>
					 		<c:if test="${!empty beneForm.datiBeneVeicoloGest.oneriCustodia}">
						    	<c:out value='${(beneForm.datiBeneVeicoloGest.oneriCustodia?"Si":"No")}' />
							</c:if>
						</td>

					</tr>	
					<tr>						    
					    <td><label><spring:message code="bene.gest.valoreOneriCustodia" /></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneVeicoloGest.valoreOneriCustodia}" type="number" pattern="###,###,##0.00" /></td>						
					    <td><label><spring:message code="bene.gest.dataOneriCustodia" /></label></td>
					    <td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneVeicoloGest.dataOneriCustodia}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
 					</tr>	
					<tr>						    
					    <td><label><spring:message code="bene.gest.descAltriVincoli" /></label></td>
						<td colspan="3" align="left"><c:out value="${beneForm.datiBeneVeicoloGest.descAltriVincoli}" /></td>
					</tr>