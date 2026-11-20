<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione"
			value="/procedimento/magistrato/modifica.htm" />
		<form:form method="post" action="${azione}" commandName="magistratoForm">
			<p class="titolo_page">
				<spring:message code="magistrato.modifica.titolo" />
			</p>

			<div>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
								
			</div>
			<form:hidden path="id"/>
			<br>
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="magistrato.modifica.titolo.magistratoScelto" /></td>
				</tr>
				<tr>
					<td><form:label path="cognome"><spring:message code="cognome" /></form:label></td>
					<td><c:out value="${magistratoForm.cognome}" /></td>
						<form:hidden path="cognome"/>
					<td><form:label path="nome"><spring:message code="nome" /></form:label></td>
					<td><c:out value="${magistratoForm.nome}" /></td>
						<form:hidden path="nome"/>
				</tr>
				<tr>
					<td><form:label path="ruolo"><spring:message code="magistrato.ruolo" /></form:label></td>
					<td><c:out value="${magistratoForm.ruolo.descrizione}" /></td>
						<form:hidden path="ruolo.id"/>
						<form:hidden path="ruolo.descrizione"/>
<%--						<td><form:label path="relatore"><spring:message code="magistrato.relatore" /></form:label></td>
						<td><c:out value="${magistratoForm.descrizioneRelatore}" /></td>
							<form:hidden path="descrizioneRelatore"/>
--%>					
					<td></td>
					<td></td>		
				</tr>
				<tr>
					<td><form:label path="dataAssegnazioneFascicolo" cssClass="required"><spring:message code="magistrato.dataAssegnazioneFascicolo" /></form:label></td>
					<td><form:input path="dataAssegnazioneFascicolo" id="dataAssegnazioneFascicolo" maxlength="10" size="12"/> 
						<form:errors path="dataAssegnazioneFascicolo" class="testoRosso" /></td>
					<td><form:label path="dataFineAssegnazione"><spring:message code="magistrato.dataFineAssegnazione" /></form:label></td>
					<td><form:input path="dataFineAssegnazione" id="dataFineAssegnazione" maxlength="10" size="12"/> 
						<form:errors path="dataFineAssegnazione" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="tipoSostituzione"><spring:message code="magistrato.tipoSostituzione" /></form:label>
					</td>
					<td><form:select path="tipoSostituzione" size="1" onchange="submit()" >
							<form:option value="" label="--- Seleziona Tipo di Sostituzione ---" />
								<form:options items="${tipiSostituzioneMagistrato}" itemValue="id" itemLabel="descrizione" />
						</form:select>
					</td>
					<c:if test="${magistratoForm.tipoSostituzione != null}">
						<c:if test="${magistratoForm.tipoSostituzione == 'Altro' || magistratoForm.tipoSostituzione == 'ALTRO'}">
							<td><form:label path="altroMotivo"><spring:message code="magistrato.altroMotivo" /></form:label></td>
							<td><form:input path="altroMotivo"/></td>
						</c:if>
						<c:if test="${magistratoForm.tipoSostituzione != 'Altro' && magistratoForm.tipoSostituzione != 'ALTRO'}">
							<td colspan="2"></td>
						</c:if>
					</c:if>
					<c:if test="${magistratoForm.tipoSostituzione == null}">
						<td colspan="2"></td>
					</c:if>
				</tr>
				<tr>
					<td><form:label path="ordineDiVisualizzazione"><spring:message code="magistrato.ordineDiVisualizzazione" /></form:label></td>
					<td colspan="3"><form:input path="ordineDiVisualizzazione" maxlength="2" size="4"/></td>
				</tr>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Conferma" name="Conferma"></td>
					<td class="td_tasto"><input type="button" id="id_buttonIndietro" value="Indietro"></td>
				</tr>
			</table>
		</form:form>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
		<smp:back buttonId="id_buttonIndietro"></smp:back>
		
		
		<script type="text/javascript">
			$(document).ready(function() {
				prendiData("#dataAssegnazioneFascicolo",true);
				prendiData("#dataFineAssegnazione",false);
			});
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
