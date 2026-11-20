<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 					<fmt:setLocale value="it_IT"/>
 					<tr>
						<td><label><spring:message code="bene.gest.stima"/></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneFinanzGest.stima}" type="number" pattern="###,###,##0.00" /></td>
			        	<td><label><spring:message code="bene.gest.dataStima"/></label></td>
			        	<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneFinanzGest.dataStima}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>
					<tr>
					  	<td><label><spring:message code="bene.gest.dirittiTerzi" /></label></td>
					 	<td>
					 		<c:if test="${!empty beneForm.datiBeneFinanzGest.dirittiTerzi}">
						    	<c:out value='${(beneForm.datiBeneFinanzGest.dirittiTerzi?"Si":"No")}' />
							</c:if>
						</td>
					 	
					    <td><label><spring:message code="bene.gest.descDirittiTerzi" /></label></td>
						<td><c:out value="${beneForm.datiBeneFinanzGest.descDirittiTerzi}" /></td>
					</tr>	
					<tr>	
						<td><label><spring:message code="bene.gest.beneRinvenuto" /></label></td>
					 	
					 	<td colspan="3" align="left">
					 		<c:if test="${!empty beneForm.datiBeneFinanzGest.beneRinvenuto}">
						    	<c:out value='${(beneForm.datiBeneFinanzGest.beneRinvenuto?"Si":"No")}' />
							</c:if>
						</td>
					 	
					</tr>	
					<tr>						    
 						<td><label><spring:message code="bene.gest.dataPossesso" /></label></td>
			        	<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneFinanzGest.dataPossesso}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					    <td><label><spring:message code="bene.gest.dataPerditaPossesso" /></label></td>
					    <td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneFinanzGest.dataPerditaPossesso}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>	
					<tr>						    
					    <td><label><spring:message code="bene.gest.descAltriVincoli" /></label></td>
						<td colspan="3" align="left"><c:out value="${beneForm.datiBeneFinanzGest.descAltriVincoli}" /></td>
					</tr>
<!--  finanziario -->
				<c:if test="${!empty beneForm.datiBeneFinanzGest.finanziario}">
					<tr>						    
					    <td><label><spring:message code="bene.gest.descIntestatarioOriginale" /></label></td>
						<td><c:out value="${beneForm.datiBeneFinanzGest.finanziario.descIntestatarioOriginale}" /></td>
					    <td><label><spring:message code="bene.gest.descIntestatarioAttuale" /></label></td>
						<td><c:out value="${beneForm.datiBeneFinanzGest.finanziario.descIntestatarioAttuale}" /></td>
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.comunicazioneFUG" /></label></td>
						<td>
					 		<c:if test="${!empty beneForm.datiBeneFinanzGest.finanziario.comunicazioneFUG}">
						    	<c:out value='${(beneForm.datiBeneFinanzGest.finanziario.comunicazioneFUG?"Si":"No")}' />
							</c:if>
						</td>
					 	
					    <td><label><spring:message code="bene.gest.dataComunicazioneFug" /></label></td>
			        	<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneFinanzGest.finanziario.dataComunicazioneFug}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>
					<tr>						    
					    <td><label><spring:message code="bene.gest.saldoPrimaComunicazione" /></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneFinanzGest.finanziario.saldoPrimaComunicazione}" type="number" pattern="###,###,##0.00" /></td>
					    <td><label><spring:message code="bene.gest.saldoPrimaRelazione" /></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneFinanzGest.finanziario.saldoPrimaRelazione}" type="number" pattern="###,###,##0.00" /></td>
					</tr>
				</c:if>
<!--  mobile -->
				<c:if test="${!empty beneForm.datiBeneFinanzGest.mobile}">
					<tr>						    
					    <td><label><spring:message code="bene.gest.oneriCustodia" /></label></td>
					 	<td>
					 		<c:if test="${!empty beneForm.datiBeneFinanzGest.mobile.oneriCustodia}">
						    	<c:out value='${(beneForm.datiBeneFinanzGest.mobile.oneriCustodia?"Si":"No")}' />
							</c:if>
						</td>
					 	
					</tr>	
					<tr>						    
					    <td><label><spring:message code="bene.gest.valoreOneriCustodia" /></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneFinanzGest.mobile.valoreOneriCustodia}" type="number" pattern="###,###,##0.00" /></td>						
					    <td><label><spring:message code="bene.gest.dataOneriCustodia" /></label></td>
					    <td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneFinanzGest.mobile.dataOneriCustodia}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
 					</tr>
 				</c:if>
