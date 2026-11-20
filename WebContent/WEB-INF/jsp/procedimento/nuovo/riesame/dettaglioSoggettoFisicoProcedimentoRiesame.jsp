<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/procedimento/nuovo/nuovoSoggettoFisico.htm" />
			<form:form method="post" action="${azione}" commandName="soggettoForm">
				<p class="titolo_page">
				<spring:message code="procedimento.riesameProvvedimentiQuestore.dettaglio" />&nbsp;-&nbsp;
				<spring:message code="soggettoFisico.dettaglio"/></p>
				<logic:present name="procedimento">
					<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br/>
					 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />					
				</logic:present>
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo1" /></td>
					</tr>
					<tr>
						<td width="15%"><form:label path="cognome"><spring:message code="cognome" /></form:label></td>
						<td width="32%"><c:out value="${soggettoForm.cognome}"/></td>
						<td width="17%"><form:label path="nome"><spring:message code="nome" /></form:label></td>
						<td width="32%"><c:out value="${soggettoForm.nome}" /></td>
					</tr>
					<tr>
						<td><form:label path="dataNascita"><spring:message code="dataNascita" /></form:label></td>
						<td>
							<div>
								<fmt:formatDate value="${soggettoForm.dataNascita}" type="date" pattern="dd/MM/yyyy" /> 
								<!-- 
								<form:checkbox path="persDaIdent" disabled="true" />
								<spring:message code="nuovosoggettofisico.persDaIdent" />
								 -->
								 <form:hidden path="persDaIdent"/>
							</div>
						</td>	
						<td><form:label path="statoNascita.descrizione"><spring:message code="stato" /></form:label></td>
						<td><c:out value="${soggettoForm.statoNascita.descrizione}" />
						</td>					
					</tr>
					<tr>	
						<td></td>
						<td></td>					
						<td><form:label path="descrizioneComuneNascitaItalianoEstero" ><spring:message code="comune"/></form:label></td>
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
					
					<logic:notEmpty name="soggettoForm" property="descIstitutoDetenzioneEstero">
					<tr>
						<td><form:label path="descIstitutoDetenzioneEstero"><spring:message code="nuovosoggettofisico.istitutodidetenzioneEstero" /></form:label></td>		
						<td colspan="3"><c:out value="${soggettoForm.descIstitutoDetenzioneEstero}"/></td>	
					</tr>		
					</logic:notEmpty>
					
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo3" /></td>
					</tr>
					<tr>
						<td><form:label path="statoResidenza.descrizione"><spring:message code="stato" /></form:label></td>
						<td><c:out value="${soggettoForm.statoResidenza.descrizione}" /></td>			
						<td><form:label path="descrizioneComuneResidenzaItalianoEstero" ><spring:message code="comune"/></form:label></td>
			        	<td><c:out value="${soggettoForm.descrizioneComuneResidenzaItalianoEstero}" /></td>	
					</tr>
					<tr>
						<td><form:label path="indirizzoResidenza"><spring:message code="indirizzo" /></form:label></td>
						<td><c:out value="${soggettoForm.indirizzoResidenza.toponimo.descrizione}" />
							<c:out value="${soggettoForm.indirizzoResidenza.denominazione}" /></td>
						<td><form:label path="indirizzoResidenza.civico"><spring:message code="civico" /></form:label></td>
						<td><c:out value="${soggettoForm.indirizzoResidenza.civico}" /></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo4" /></td>
					</tr>
					<tr>
						<td><form:label path="statoDomicilio"><spring:message code="stato" /></form:label></td>
						<td><c:out value="${soggettoForm.statoDomicilio.descrizione}"/></td>					
						<td><form:label path="descrizioneComuneDomicilioItalianoEstero" ><spring:message code="comune"/></form:label></td>
			        	<td><c:out value="${soggettoForm.descrizioneComuneDomicilioItalianoEstero}"/></td>	
					</tr>
					<tr>
						<td><form:label path="indirizzoDomicilio"><spring:message code="nuovosoggettofisico.indirizzo" /></form:label></td>
						<td><c:out value="${soggettoForm.indirizzoDomicilio.toponimo.descrizione}"/>
							<c:out value="${soggettoForm.indirizzoDomicilio.denominazione}"/></td>
						<td><form:label path="indirizzoDomicilio.civico"><spring:message code="civico" /></form:label></td>
						<td><c:out value="${soggettoForm.indirizzoDomicilio.civico}"/></td>
					</tr>
				</table>

				
			</form:form>
			
			<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<!--<td class="td_tasto"><input type="button" value="Indietro" onclick="indietro('${indietro}')"/></td>-->
						<td class="td_tasto"><input id="id_buttonIndietro" type="button" value="Indietro"/></td>	
					</tr>
				</table>
				
				<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
				<smp:back buttonId="id_buttonIndietro"></smp:back>
				
				<br />
				<br />

		</tiles:putAttribute>
	</tiles:insertDefinition>