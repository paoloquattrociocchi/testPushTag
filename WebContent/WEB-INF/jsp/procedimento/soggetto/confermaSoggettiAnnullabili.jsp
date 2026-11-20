<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione" value="/procedimento/soggetto/startAnnullaSoggetto.htm" />
			<form:form method="post" action="${azione}" commandName="soggettoForm" id="idFormAnnullaSoggetto">
				<p class="titolo_page"><spring:message code="soggetto.annullabile.titolo"/></p>
				<logic:present name="procedimento">
					<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				</logic:present>
				<br/>
			      <form:hidden path="idSoggetto" id="idSoggetto" />
					<form:hidden path="idVersione" id="idVersione" />
					<form:hidden path="tipoSoggetto" id="tipoSoggetto" />
					<form:hidden path="descrRuolo" id="descrRuolo" />
					
					<form:hidden path="idSoggettoDuplicato" id="idSoggettoDuplicato" />
					<form:hidden path="idVersioneDuplicato" id="idVersioneDuplicato" />
					<form:hidden path="tipoSoggettoDuplicato" id="tipoSoggettoDuplicato" />
					<form:hidden path="descrRuoloDuplicato" id="descrRuoloDuplicato" />
					
			<!------------------ PERSONA FISICA-------------------->
			<c:if test="${soggettoForm.tipoSoggetto=='F'}">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<colgroup>
			       <col id="tb_form_col_label" span="1">
			       <col id="tb_form_col_value" span="1">
			       <col id="tb_form_col_label" span="1">
			       <col id="tb_form_col_value" span="1">
			    </colgroup>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.annullabile.valido" /></td>
					</tr>
					<tr>
						<td><form:label path="cognome"><spring:message code="cognome" /></form:label></td>
						<td><c:out value="${soggettoFormValido.cognome}"/> <form:errors path="cognome" class="testoRosso"/></td>
						<td><form:label path="nome"><spring:message code="nome" /></form:label></td>
						<td><c:out value="${soggettoFormValido.nome}" /> <form:errors path="nome" class="testoRosso"/></td>
					</tr>
					<tr>
						<td><form:label path="dataNascita"><spring:message code="dataNascita" /></form:label></td>
						<td>
							<div>
								<fmt:formatDate value="${soggettoFormValido.dataNascita}" type="date" pattern="dd/MM/yyyy" />  <form:errors path="dataNascita" class="testoRosso"/>
								<form:hidden path="persDaIdent"/>
							</div>
						</td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td><form:label path="statoNascita.descrizione"><spring:message code="stato" /></form:label></td>
						<td><c:out value="${soggettoFormValido.statoNascita.descrizione}" /> <form:errors path="statoNascita.descrizione" class="testoRosso"/></td>	
						<td><form:label path="comuneNascita.descrizione" ><spring:message code="comune"/></form:label></td>
			        	<td><c:out value="${soggettoFormValido.descrizioneComuneNascitaItalianoEstero}"/> <form:errors path="descrizioneComuneNascitaItalianoEstero" class="testoRosso"/></td>	
					</tr>
				</table>
			</c:if>
			<c:if test="${soggettoForm.tipoSoggettoDuplicato=='F'}">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<colgroup>
			       <col id="tb_form_col_label" span="1">
			       <col id="tb_form_col_value" span="1">
			       <col id="tb_form_col_label" span="1">
			       <col id="tb_form_col_value" span="1">
			    </colgroup>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.annullabile.duplicato" /></td>
					</tr>
					<tr>
						<td><form:label path="cognome"><spring:message code="cognome" /></form:label></td>
						<td><c:out value="${soggettoFormDuplicato.cognome}"/> <form:errors path="cognome" class="testoRosso"/></td>
						<td><form:label path="nome"><spring:message code="nome" /></form:label></td>
						<td><c:out value="${soggettoFormDuplicato.nome}" /> <form:errors path="nome" class="testoRosso"/></td>
					</tr>
					<tr>
						<td><form:label path="dataNascita"><spring:message code="dataNascita" /></form:label></td>
						<td>
							<div>
								<fmt:formatDate value="${soggettoFormDuplicato.dataNascita}" type="date" pattern="dd/MM/yyyy" /> <form:errors path="dataNascita" class="testoRosso"/> 
								<form:hidden path="persDaIdent"/>
							</div>
						</td>
						<td colspan="2"></td>
					</tr>
					<tr>
						<td><form:label path="statoNascita.descrizione"><spring:message code="stato" /></form:label></td>
						<td><c:out value="${soggettoFormDuplicato.statoNascita.descrizione}" /> <form:errors path="statoNascita.descrizione" class="testoRosso"/></td>	
						<td><form:label path="comuneNascita.descrizione" ><spring:message code="comune"/></form:label></td>
			        	<td><c:out value="${soggettoFormDuplicato.descrizioneComuneNascitaItalianoEstero}" /> <form:errors path="descrizioneComuneNascitaItalianoEstero" class="testoRosso"/></td>	
					</tr>
				</table>
			</c:if>
				
				<!------------------ PERSONA GIURIDICA-------------------->
			<c:if test="${soggettoForm.tipoSoggetto=='G'}">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">	
				<colgroup>
			       <col id="tb_form_col_label" span="1">
			       <col id="tb_form_col_value" span="1">
			       <col id="tb_form_col_label" span="1">
			       <col id="tb_form_col_value" span="1">
			    </colgroup>	
			    	<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.annullabile.valido" /></td>
					</tr>
					<tr>
						<td><form:label path="ragioneSociale">
								<spring:message code="soggettogiuridico.ragioneSociale" /></form:label></td>
						<td><c:out value="${soggettoFormValido.ragioneSociale}" /> <form:errors path="ragioneSociale" class="testoRosso"/></td>

						<td><form:label path="partitaIva">
								<spring:message code="soggettogiuridico.partitaIva" /></form:label></td>
						<td><c:out value="${soggettoFormValido.partitaIvaCompleta}" /></td>
					</tr>
					<tr>
						<td><form:label path="tipoSocieta">
								<spring:message code="soggettogiuridico.tipoSocieta" />
							</form:label></td>
						<td><c:out value="${soggettoFormValido.tipoSocieta}" /> <form:errors path="tipoSocieta" class="testoRosso"/></td>
						<td colspan="2"> </td>
					</tr>	
				</table>
			</c:if>
			<c:if test="${soggettoForm.tipoSoggettoDuplicato=='G'}">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">	
				<colgroup>
			       <col id="tb_form_col_label" span="1">
			       <col id="tb_form_col_value" span="1">
			       <col id="tb_form_col_label" span="1">
			       <col id="tb_form_col_value" span="1">
			    </colgroup>	
			    	<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.annullabile.duplicato" /></td>
					</tr>
					<tr>
						<td><form:label path="ragioneSociale">
								<spring:message code="soggettogiuridico.ragioneSociale" /></form:label></td>
						<td><c:out value="${soggettoFormDuplicato.ragioneSociale}" /> <form:errors path="ragioneSociale" class="testoRosso"/></td>

						<td><form:label path="partitaIva">
								<spring:message code="soggettogiuridico.partitaIva" /></form:label></td>
						<td><c:out value="${soggettoFormDuplicato.partitaIvaCompleta}" /></td>
					</tr>
					<tr>
						<td><form:label path="tipoSocieta">
								<spring:message code="soggettogiuridico.tipoSocieta" />
							</form:label></td>
						<td><c:out value="${soggettoFormDuplicato.tipoSocieta}" /> <form:errors path="tipoSocieta" class="testoRosso"/></td>
						<td colspan="2"> </td>
					</tr>	
				</table>
			</c:if>
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>	
				<logic:present name="abilitaConferma"> 					
						<td class="td_tasto"><input type="submit" value="Conferma" name="Conferma" title="Conferma annullamento Soggetto"/></td>					
				</logic:present>								
						<td class="td_tasto"><input id="id_buttonIndietro" type="button" value="Indietro"/></td>					
					</tr>
				</table>
			</form:form>
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
				<smp:back buttonId="id_buttonIndietro"></smp:back>

		</tiles:putAttribute>
	</tiles:insertDefinition>
