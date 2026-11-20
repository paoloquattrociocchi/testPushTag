<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 					<fmt:setLocale value="it_IT"/>
 					<tr>
						<td><label><spring:message code="bene.gest.stima"/></label></td>
						<td><fmt:formatNumber value="${beneForm.datiBeneImmaterialeGest.stima}" type="number" pattern="###,###,##0.00" /></td>
			        	<td><label><spring:message code="bene.gest.dataStima"/></label></td>
			        	<td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneImmaterialeGest.dataStima}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>
					<tr>
					  	<td><label><spring:message code="bene.gest.dirittiTerzi" /></label></td>
					 	<td>
					 		<c:if test="${!empty beneForm.datiBeneImmaterialeGest.dirittiTerzi}">
						    	<c:out value='${(beneForm.datiBeneImmaterialeGest.dirittiTerzi?"Si":"No")}' />
							</c:if>
						</td>
					 	
					    <td><label><spring:message code="bene.gest.descDirittiTerzi" /></label></td>
						<td><c:out value="${beneForm.datiBeneImmaterialeGest.descDirittiTerzi}" /></td>
					</tr>	
					<tr>						    
					    <td><label><spring:message code="bene.gest.trascrizioneSequestro" /></label></td>
					 	<td>
					 		<c:if test="${!empty beneForm.datiBeneImmaterialeGest.trascrizioneSequestro}">
						    	<c:out value='${(beneForm.datiBeneImmaterialeGest.trascrizioneSequestro?"Si":"No")}' />
							</c:if>
						</td>
					 	
					    <td><label><spring:message code="bene.gest.dataTrascrizioneSequestro" /></label></td>
					    <td>
	 			        	<fmt:parseDate value="${beneForm.datiBeneImmaterialeGest.dataTrascrizioneSequestro}" pattern="yyyy-MM-dd" var="parsedDate" /> 
	 			        	<fmt:formatDate value="${parsedDate}" type="date" pattern="dd/MM/yyyy" />
 			        	</td>
					</tr>	
					<tr>						    
					    <td><label><spring:message code="bene.gest.descCustode" /></label></td>
						<td colspan="3"><c:out value="${beneForm.datiBeneImmaterialeGest.descCustode}" /></td>
					</tr>
