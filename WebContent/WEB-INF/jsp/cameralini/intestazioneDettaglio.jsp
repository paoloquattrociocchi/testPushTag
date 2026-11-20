<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
					<tr>
						<td><form:label path="procedPadre.annoNumeroPadre"><spring:message code="cameralini.prevenzione.procedimento"/></form:label></td>
						<td><c:out value="${form.procedPadre.anno}" />/<c:out value="${form.procedPadre.numero}" /></td>
						<td><form:label path="procedPadre.dataApertura"><spring:message code="cameralini.prevenzione.dataIscrizione"/></form:label></td>
						<td> <fmt:formatDate value="${form.procedPadre.dataApertura}" type="date" pattern="dd/MM/yyyy" /> </td>
						<td><form:label path="ufficioSelezionatoDesc"><spring:message code="cameralini.prevenzione.ufficioSelezionato"/></form:label></td>
						<td><c:out value="${ufficioSelezionatoDesc}" /></td>
			        </tr>
			        <tr>
						<td><form:label path="procedPadre.tipoProcedimento.descrizione"><spring:message code="cameralini.prevenzione.tipoProcedimento"/></form:label></td>
						<td><c:out value="${form.procedPadre.tipoProcedimento.descrizione}" /></td>
						<td><form:label path="procedPadre.statoProcedimento.descrizione"><spring:message code="cameralini.prevenzione.stato"/></form:label></td>
						<td><c:out value="${form.procedPadre.statoProcedimento.descrizione}" /></td>
						<td><form:label path="procedPadre.dataApertura"><spring:message code="cameralini.prevenzione.dal"/></form:label></td>
						<td><fmt:formatDate value="${form.procedPadre.dataApertura}" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>
			        <tr>
						<td ><form:label path="tipologiaPrevenzione"><spring:message code="cameralini.prevenzione.tipoPrev"/></form:label></td>
						<td colspan="5">
							<c:if test="${form.procedPadre.antimafia==true}">
									<form:hidden path="procedPadre.antimafia" />
									<c:out value="ANTIMAFIA" />
							</c:if> 
							<c:if test="${form.procedPadre.antimafia==false}">
									<c:if test="${form.procedPadre.antiterrorismo==true}">
										<form:hidden path="procedPadre.antiterrorismo" />
										<c:out value="ANTITERRORISMO" />
									</c:if>
									<c:if test="${form.procedPadre.antiterrorismo==false}">
										<c:if test="${form.procedPadre.ordinaria==true}">
											<form:hidden path="procedPadre.ordinaria" />
											<c:out value="ORDINARIA" />
										</c:if>
									</c:if>
							</c:if>
						</td>
			        </tr>	       
			     
			        
			        
			<form:hidden id="idProcedimento" path="procedPadre.idProcedimento" />
			<form:hidden id="dataApertura" path="procedPadre.dataApertura" />
			<form:hidden id="anno" path="procedPadre.anno" />
			<form:hidden id="numero" path="procedPadre.numero" />
			<form:hidden id="ufficioSelezionatoDesc" path="ufficioSelezionatoDesc" />
			<form:hidden id="tipoProcedimento" path="procedPadre.tipoProcedimento.descrizione" />
			<form:hidden id="statoProcedimento" path="procedPadre.statoProcedimento.descrizione" />
			<form:hidden id="dataAttivazioneStato" path="procedPadre.dataAttivazioneStato" />
			<form:hidden id="tipologiaPrevenzione" path="tipologiaPrevenzione" />
			<form:hidden id="hiddenTrasmissione" path="trasmissioneDesc" />