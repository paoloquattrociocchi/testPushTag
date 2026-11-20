<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

				
				<form:hidden path="codUdienza" />
						
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					  <tr>
						<td width="15%"><form:label path="dataUdienza"><spring:message code="udienza.data"/></form:label></td>
						<td width="30%"><fmt:formatDate value="${udienzaForm.dataUdienza}" type="date" pattern="dd/MM/yyyy" /></td>
						<td width="15%"><form:label path=""><spring:message code="udienza.ora.inizio"/></form:label></td>
						<td width="15%"><c:out value="${udienzaForm.oraInizioUdienzaStr}"/></td>
						<td width="15%"><form:label path=""><spring:message code="udienza.ora.fine"/></form:label></td>
						<td width="10%"><c:out value="${udienzaForm.oraFineUdienzaStr}" /></td>
					  </tr>
					  <tr>
					    <td><form:label path="indirizzoAulaUdienza"><spring:message code="indirizzo"/></form:label></td>
						<td><c:out value="${udienzaForm.indirizzoAulaUdienza}" /></td>
						<td><form:label path="aulaUdienza"><spring:message code="atto.udienza.aula"/></form:label></td>
						<td colspan="3"><c:out value="${udienzaForm.aulaUdienza}"/></td>
					 </tr>
				</table>
				
				<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE%>" value="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE_SMP_GIU%>" >
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr><td class="td_titolo"><spring:message code="udienza.collegio"/></td></tr>
					<tr>	
						<td>
							<display:table class="tb_lista" name="${collegioUdienza}">
								<display:column property="generalita" title="Nominativo" sortable="false" />
								<display:column property="ruolo.descrizione" title="Ruolo" sortable="false" />
							</display:table>
						</td>
					</tr>
				</table>
				</logic:equal>

				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr><td class="td_titolo"><spring:message code="udienza.pubblicomin"/></td></tr>
				<tr>
					<td>
						<display:table class="tb_lista" name="${listaPmUdienza}">
							<display:column property="generalita" title="PM in Udienza" sortable="false" />
						</display:table>
					</td>
				</tr>
				</table>
				
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="15%"><form:label path=""><spring:message code="atto.udienza.cancelliere"/></form:label></td>
					<td width="10%"><c:out value="${udienzaForm.cognomeCancelliere}" /></td>
					<td width="75%"><c:out value="${udienzaForm.nomeCancelliere}" /></td>
				</tr>
				<tr>
				    <td><form:label path=""><spring:message code="note"/></form:label></td>
					<td colspan="2"><c:out value="${udienzaForm.note}" /></td>
				</tr>
				</table>
				
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr><td class="td_titolo"><spring:message code="udienza.soggetticonv"/></td></tr>
				<tr>			
					<td>
						<display:table class="tb_lista" name="${soggConvUdienza}">
							<display:column property="descSoggetto" title="Generalità" sortable="false" />
							<display:column property="descrRuolo" title="Ruolo" sortable="false" />
							<display:column property="flgPresenza" title="Presente" sortable="false" />
						</display:table>
					</td>
				</tr>
				</table>
				
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">				
				<tr><td class="td_titolo" colspan="6"><spring:message code="udienza.difensori"/></td></tr>
				<tr>			
					<td colspan="6">
						<display:table class="tb_lista" name="${difUdienza}">
							<display:column property="descSoggetto" title="Generalità" sortable="false" />
							<display:column property="flgPresenza" title="Presente" sortable="false" />
						</display:table>
					</td>
				</tr>
				</table>
				
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="15%"><form:label path="altriConv"><spring:message code="udienza.altriconv"/></form:label></td>
						<td width="85%"><c:out value="${udienzaForm.altriConv}" /></td>
					</tr>
				</table>
				
				<c:if test="${udienzaForm.esito.id != -1}">
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
					    	<td width="15%"><form:label path="esito"><spring:message code="udienza.esito"/></form:label></td>
							<td width="85%"><c:out value="${udienzaForm.esito.descrizione}" /></td>
						</tr>
					</table>
					<br/>
					<c:if test="${udienzaForm.esito.id != 10}">
						<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
							<c:if test="${udienzaForm.dataRinvio != null}">
							  <tr><td class="td_titolo" colspan="5"><spring:message code="udienza.rinvio"/></td></tr>
							  <tr>
								<td width="15%"><form:label path="dataRinvio"><spring:message code="udienza.rinvio.data"/></form:label></td>
								<td width="30%"><fmt:formatDate value="${udienzaForm.dataRinvio}" type="date" pattern="dd/MM/yyyy" /></td>
								<td width="15%"><form:label path=""><spring:message code="udienza.rinvio.ora"/></form:label></td>
								<td width="40%"><c:out value="${udienzaForm.oraUdienzaRinvioStr}" /></td>
							  </tr>
							  <tr>
							    <td><form:label path="indirizzoAulaUdienzaRinvio"><spring:message code="indirizzo"/></form:label></td>
								<td><c:out value="${udienzaForm.indirizzoAulaUdienzaRinvio}" /></td>
								<td><form:label path="aulaUdienzaRinvio"><spring:message code="atto.udienza.aula"/></form:label></td>
								<td><c:out value="${udienzaForm.aulaUdienzaRinvio}"/></td>
							 </tr>
							</c:if>
						</table>
					</c:if>
				</c:if>