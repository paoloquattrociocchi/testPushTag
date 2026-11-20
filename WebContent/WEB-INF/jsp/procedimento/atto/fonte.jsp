<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<%
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/inserimentoFonte.htm" />
		<form:form method="post" action="${azione}" commandName="atto" enctype="multipart/form-data">
			<p class="titolo_page">
				<spring:message code="atto.nuovafonte" />
			</p>

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />

			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="atto.fonte" /></td>
				</tr>
				<tr>
					<td><form:label path="fonte">
							<spring:message code="atto.fonte" />
						</form:label></td>
					<logic:equal name="atto" property="fonte.id" value="12">
						<td colspan="3"><c:out value="${atto.fonte}" /></td>
					</logic:equal>
					<logic:notEqual name="atto" property="fonte.id" value="12">
						<td><c:out value="${atto.fonte}" /></td>
						<td><form:label path="organo">
								<spring:message code="atto.organo" />
							</form:label></td>
						<td><form:select path="organo" size="1" cssStyle="width:300px">
								<form:option value="-1" label="Guardia di Finanza" />
							</form:select></td>
				</tr>
				<tr>
					<td><form:label path="provinciaOrgano">
							<spring:message code="provincia" />
						</form:label></td>
					<td><form:select path="provinciaOrgano" size="1">
							<form:option value="-1" label="AGRIGENTO" />
						</form:select></td>
					<td><form:label path="comuneOrgano">
							<spring:message code="comune" />
						</form:label></td>
					<td><form:select path="comuneOrgano" size="1">
							<form:option value="-1" label="AGRIGENTO" />
						</form:select></td>
				</tr>
				<tr>
					<td><form:label path="indirizzoOrgano">
							<spring:message code="indirizzo" />
						</form:label></td>
					<td colspan="3"><form:select path="indirizzoOrgano" size="1" cssStyle="width:300px">
							<form:option value="1" label="CO PROV AGRIGENTO" />
						</form:select></td>
					</logic:notEqual>
				</tr>
				<tr>
					<td><form:label path="altroOrgano">
							<spring:message code="atto.altroorgano" />
						</form:label></td>
					<td colspan="3"><form:input path="altroOrgano" maxlength="50" cssStyle="width:300px" /></td>
				</tr>
				<tr>
					<td><form:label path="numeroRiferimento">
							<spring:message code="atto.numeroriferimento" />
						</form:label></td>
					<td><form:input path="numeroRiferimento" /></td>
					<td><form:label path="dataInformativa" cssClass="required">
							<spring:message code="atto.datainformativa" />
						</form:label></td>
					<td><form:input id="dataInformativa" path="dataInformativa" maxlength="10" size="12" /> <br> <form:errors path="dataInformativa" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="note">
							<spring:message code="note" />
						</form:label></td>
					<td colspan="3"><form:textarea path="note" rows="2" cssStyle="width:98%" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.direttiinteressatinonassociati" /></td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="soggettiDelProcedimento" name="${soggettiDelProcedimento}" pagesize="10" requestURI="${azione2}">
				<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
					<form:radiobutton path="identificativoSoggetto" value="${soggettiDelProcedimento.idSoggetto}" />
				</display:column>
				<display:column property="descrizioneSoggetto" title="Nome Cognome - Denominazione/Ragione Sociale" sortable="true" style="width: 50%" />
			</display:table>
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="atto.inseriscidocumento" /></td>
				</tr>
				<tr>
					<td colspan="4"><input type="file" name="file"></td>
				</tr>
				<tr>
					<td width="15%"><form:label path="dataRicezione">
							<spring:message code="atto.dataricezione" />
						</form:label></td>
					<td colspan="3"><form:input id="dataRicezione" path="dataRicezione" maxlength="10" size="12" /> <br> <form:errors path="dataRicezione" class="testoRosso" /></td>
				</tr>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" name="Conferma" title="Conferma inserimento atto" value="Conferma"></td>
					<td class="td_tasto"><c:if test="${fromMenuAtti=='elenco'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
						</c:if> <c:if test="${fromMenuAtti=='ordine'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
						</c:if> <c:if test="${fromMenuAtti=='nuovo'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/nuovo.htm')">
						</c:if></td>
				</tr>
			</table>
			<br />
			<br />
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>