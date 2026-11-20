<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione" value="/procedimento/nuovo/nuovoSoggettoFisico.htm" />
			<form:form method="post" action="${azione}" commandName="soggettoForm" id="idForm">
				<p class="titolo_page"><spring:message code="soggettoFisico.dettaglio"/></p>
				<logic:present name="procedimento">
					<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				</logic:present>
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo1" /></td>
					</tr>
					<tr>
						<td><form:label path="cognome"><spring:message code="cognome" /></form:label></td>
						<td><c:out value="${soggettoForm.cognome}"/></td>
						<td><form:label path="nome"><spring:message code="nome" /></form:label></td>
						<td><c:out value="${soggettoForm.nome}" /></td>
					</tr>
					<tr>
						<td><form:label path="dataNascita"><spring:message code="dataNascita" /></form:label></td>
						<td>
							<div>
								<fmt:formatDate value="${soggettoForm.dataNascita}" type="date" pattern="dd/MM/yyyy" /> 
								<!--
								<form:checkbox path="persDaIdent" value="1" disabled="true" />
								<spring:message code="nuovosoggettofisico.persDaIdent" /> -->
								<form:hidden path="persDaIdent"/>
							</div>
						</td>
					    <td><form:label path="presunta"><spring:message code="dataNascitaPresunta" /></form:label></td>
						<td>
							<c:if test="${soggettoForm.presunta==true}">
								<input type="checkbox" disabled="disabled" checked="checked" />
							</c:if>
							<c:if test="${soggettoForm.presunta==false || soggettoForm.presunta==null}">
								<input type="checkbox" disabled="disabled"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<td><form:label path="statoNascita.descrizione"><spring:message code="stato" /></form:label></td>
						<td><c:out value="${soggettoForm.statoNascita.descrizione}" /></td>	
						<td><form:label path="comuneNascita.descrizione" ><spring:message code="comune"/></form:label></td>
			        	<td><c:out value="${soggettoForm.descrizioneComuneNascitaItalianoEstero}" /></td>	
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo2" /></td>
					</tr>
					<tr>
						<td><form:label path="sesso"><spring:message code="nuovosoggettofisico.sesso" /></form:label></td>
						<td><c:out value="${soggettoForm.descrSesso}" /></td>
						<td><form:label path="codiceFiscale"><spring:message code="codiceFiscale" /></form:label></td>
						<td><c:out value="${soggettoForm.codiceFiscale}" /></td>
					</tr>
					<tr>
						<td><form:label path="cui"><spring:message code="soggettofisico.cui" /></form:label></td>
						<td colspan="3"><c:out value="${soggettoForm.cui}" /></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo7" /></td>
					</tr>
					<tr>
						<td><form:label path="statoMorte.descrizione"><spring:message code="stato" /></form:label></td>
						<td><c:out value="${soggettoForm.statoMorte.descrizione}"/></td>					
						<td><form:label path="comuneMorte.descrizione" ><spring:message code="comune"/></form:label></td>
			        	<td><c:out value="${soggettoForm.descrizioneComuneMorteItalianoEstero}"/> </td>
					</tr>
					<tr>
						<td><form:label path="dataMorte"><spring:message code="nuovosoggettofisico.datadecesso" /></form:label></td>
						<td colspan="3"><fmt:formatDate value="${soggettoForm.dataMorte}" type="date" pattern="dd/MM/yyyy" /></td> 
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo8"/></td>
					</tr>
					<tr>
						<td><form:label path="cognomePaternita"><spring:message code="cognome" /></form:label></td>
						<td><c:out value="${soggettoForm.cognomePaternita}"/></td>
						<td><form:label path="nomePaternita"><spring:message code="nome" /></form:label></td>
						<td><c:out value="${soggettoForm.nomePaternita}"/></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo9" /></td>
					</tr>
					<tr>
						<td><form:label path="cognomeMaternita"><spring:message code="cognome" /></form:label></td>
						<td><c:out value="${soggettoForm.cognomeMaternita}"/></td>
						<td><form:label path="nomeMaternita"><spring:message code="nome" /></form:label></td>
						<td><c:out value="${soggettoForm.nomeMaternita}"/></td>
					</tr>
				</table>

				
			</form:form>
			
			
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>						
						<td class="td_tasto"><input id="id_buttonIndietro" type="button" value="Indietro"/></td>	
					</tr>
				</table>
				
				<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
				<smp:back buttonId="id_buttonIndietro"></smp:back>

				<br />
				<br />

		</tiles:putAttribute>
	</tiles:insertDefinition>
