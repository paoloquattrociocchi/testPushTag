<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>

<c:set var="AGENZIA_BENI_CONFISCATI" value="<%=Constants.AGENZIA_BENI_CONFISCATI%>"/>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/amministratore/elencoAssociatoProcedimento.htm" />

		<p class="titolo_page">
			<spring:message code="coadiutori.dettaglio.titolo" />
		</p>

		<form:form method="post" action="${azione}" commandName="coadiutoreForm" >

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			
			<form:hidden path="idProcedimento" />
			<form:hidden path="idUfficio" />
			<form:hidden path="idAmministratore" />
			<form:hidden path="idProgrCoad" />				
	
			<br />
			
			<c:if test="${disabled==1}" >

				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td>
							<form:label path="cognome" >
								<spring:message code="cognome" />
							</form:label>
						</td>
						<td>
							<c:out value="${coadiutoreForm.cognome}" />
						</td>			
						<td>
							<form:label path="nome" >
								<spring:message code="nome" />
							</form:label>
						</td>
						<td>
							<c:out value="${coadiutoreForm.nome}" />
						</td>	
					</tr>	
					<tr>
						<td>
							<form:label path="dataNascita" >
								<spring:message code="dataNascita" />
							</form:label>
						</td>
						<td>
							<fmt:formatDate value="${coadiutoreForm.dataNascita}" type="date" pattern="dd/MM/yyyy" />
						</td>			
						<td>
							<form:label path="codiceFiscale">
								<spring:message code="codiceFiscale" />
							</form:label>
						</td>
						<td>
							<c:out value="${coadiutoreForm.codiceFiscale}" />
						</td>	
					</tr>		
					<tr>
						<td>
							<form:label path="luogoNascita" >
								<spring:message code="luogoNascita" />
							</form:label>
						</td>
						<td>
							<c:out value="${coadiutoreForm.luogoNascita}" />
						</td>			
						<td>
							<form:label path="recapito">
								<spring:message code="coadiutori.recapito" />
							</form:label>
						</td>
						<td>
							<c:out value="${coadiutoreForm.recapito}" />
						</td>	
					</tr>			
					<tr>
						<td>
							<form:label path="inizioIncarico" >
								<spring:message code="coadiutori.dataInizioIncarico" />
							</form:label>
						</td>
						<td>
							<fmt:formatDate value="${coadiutoreForm.inizioIncarico}" type="date" pattern="dd/MM/yyyy" />
						</td>			
						<td>
							<form:label path="fineIncarico">
								<spring:message code="coadiutori.dataFineIncarico" />
							</form:label>
						</td>
						<td>
							<fmt:formatDate value="${coadiutoreForm.fineIncarico}" type="date" pattern="dd/MM/yyyy" />
						</td>	
					</tr>			
				
				</table>
				<br />
				<br />			
						
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="button" id="backButton" value="Indietro"></td>					
					</tr>
				</table>			
			
			</c:if>
			
			<c:if test="${disabled!=1}" >
			
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td>
							<form:label path="cognome" cssClass="required">
								<spring:message code="cognome" />
							</form:label>
						</td>
						<td>
							<form:input path="cognome" maxlength="35" />
							<form:errors path="cognome" class="testoRosso" />
						</td>			
						<td>
							<form:label path="nome" cssClass="required">
								<spring:message code="nome" />
							</form:label>
						</td>
						<td>
							<form:input path="nome" maxlength="35"/>
							<form:errors path="nome" class="testoRosso" />
						</td>	
					</tr>	
					<tr>
						<td>
							<form:label path="dataNascita">
								<spring:message code="dataNascita" />
							</form:label>
						</td>
						<td>
							<form:input path="dataNascita" />
							<form:errors path="dataNascita" class="testoRosso" />
						</td>			
						<td>
							<form:label path="codiceFiscale">
								<spring:message code="codiceFiscale" />
							</form:label>
						</td>
						<td>
							<form:input path="codiceFiscale" maxlength="16" />
						</td>	
					</tr>		
					<tr>
						<td>
							<form:label path="luogoNascita" >
								<spring:message code="luogoNascita" />
							</form:label>
						</td>
						<td>
							<form:input path="luogoNascita" maxlength="300" />
						</td>			
						<td>
							<form:label path="recapito">
								<spring:message code="coadiutori.recapito" />
							</form:label>
						</td>
						<td>
							<form:input path="recapito" maxlength="300" />
						</td>	
					</tr>			
					<tr>
						<td>
							<form:label path="inizioIncarico" cssClass="required">
								<spring:message code="coadiutori.dataInizioIncarico" />
							</form:label>
						</td>
						<td>
							<form:input path="inizioIncarico" />
							<form:errors path="inizioIncarico" class="testoRosso" />
						</td>			
						<td>
							<form:label path="fineIncarico">
								<spring:message code="coadiutori.dataFineIncarico" />
							</form:label>
						</td>
						<td>
							<form:input path="fineIncarico" />
							<form:errors path="fineIncarico" class="testoRosso" />
						</td>	
					</tr>			
				
				</table>
				<br />
				<br />			
						
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" value="Salva" name="SalvaCoadiutore" ></td>
						<td class="td_tasto"><input type="button" id="backButton" value="Indietro"></td>					
					</tr>
				</table>
				
			</c:if>				
	
		</form:form>

			<c:if test="${disabled!=1}" >
			
				<script type="text/javascript">
					$(document).ready(
							function() {
								prendiData("#dataNascita", true);
								prendiData("#inizioIncarico", true);
								prendiData("#fineIncarico", true);
							});
				</script>
				
			</c:if>
			
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
									
	</tiles:putAttribute>
</tiles:insertDefinition>