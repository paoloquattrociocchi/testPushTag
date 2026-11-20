<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 					<fmt:setLocale value="it_IT"/>
 					<tr>
						<td><label><spring:message code="bene.gest.stima"/></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneMobileGest.stima}" type="number" pattern="###,###,##0.00" /></td>
			        	<td><label><spring:message code="bene.gest.dataStima"/></label></td>
			        	<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneMobileGest.dataStima}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>
					<tr>
					  	<td><label><spring:message code="bene.gest.dirittiTerzi" /></label></td>
					  	<td>
					 		<c:if test="${!empty beneForm.datiBeneMobileGest.dirittiTerzi}">
						    	<c:out value='${(beneForm.datiBeneMobileGest.dirittiTerzi?"Si":"No")}' />
							</c:if>
						</td>
						<td><label><spring:message code="bene.gest.descDirittiTerzi" /></label></td>
						<td><c:out value="${beneForm.datiBeneMobileGest.descDirittiTerzi}" /></td>
					</tr>	
					<tr>	
					 	<td><label><spring:message code="bene.gest.beneRinvenuto" /></label></td>
					  	<td colspan="3">
					 		<c:if test="${!empty beneForm.datiBeneMobileGest.beneRinvenuto}">
						    	<c:out value='${(beneForm.datiBeneMobileGest.beneRinvenuto?"Si":"No")}' />
							</c:if>
						</td>
 
					</tr>	
					<tr>						    
 						<td><label><spring:message code="bene.gest.dataPossesso" /></label></td>
			        	<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneMobileGest.dataPossesso}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					    <td><label><spring:message code="bene.gest.dataPerditaPossesso" /></label></td>
					    <td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneMobileGest.dataPerditaPossesso}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>	
					<tr>						    
					    <td><label><spring:message code="bene.gest.oneriCustodia" /></label></td>
					 	<td colspan="3">
					 		<c:if test="${!empty beneForm.datiBeneMobileGest.oneriCustodia}">
						    	<c:out value='${(beneForm.datiBeneMobileGest.oneriCustodia?"Si":"No")}' />
							</c:if>
						</td>
					</tr>	
					<tr>						    
					    <td><label><spring:message code="bene.gest.valoreOneriCustodia" /></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneMobileGest.valoreOneriCustodia}" type="number" pattern="###,###,##0.00" /></td>						
					    <td><label><spring:message code="bene.gest.dataOneriCustodia" /></label></td>
					    <td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneMobileGest.dataOneriCustodia}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
 					</tr>	
					<tr>						    
					    <td><label><spring:message code="bene.gest.descAltriVincoli" /></label></td>
						<td  colspan="3" align="left"><c:out value="${beneForm.datiBeneMobileGest.descAltriVincoli}" /></td>
					</tr>
