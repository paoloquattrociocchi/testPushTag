<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/amministrazione/dettaglioProcedimento.htm" />
		<form:form method="post" action="${azione}" commandName="procedimento">
			<p class="titolo_page">
				<spring:message code="procedimento.riesameProvvedimentiQuestore.dettaglio" />
			</p>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="15%"><form:label path="annoNumeroProcedimento">
							<spring:message code="procedimento" />
						</form:label></td>
					<td width="35%"><font size="4"><b><c:out value="${procedimento.annoNumeroProcedimento}" /></b></font></td>
					<td width="15%"><form:label path="dataIscrizione">
							<spring:message code="procedimento.dataIscrizione" />
						</form:label></td>
					<td width="35%"><fmt:formatDate value="${procedimento.dataApertura}" type="date" pattern="dd/MM/yyyy" /></td>
				</tr>
				<tr>
					<td><form:label path="tipoProcedimento">
							<spring:message code="procedimento.tipo" />
						</form:label></td>
					<td><c:out value="${procedimento.tipoProcedimento.descrizione}" /></td>
					<td><form:label path="statoProcedimento">
							<spring:message code="procedimento.stato" />
						</form:label></td>
					<td><c:out value="${procedimento.statoProcedimento.descrizione}" /></td>
					<!--<td colspan="3"><c:out value="${procedimento.procedente.id}" /></td>-->
				</tr>
			</table>

			<br />
			<br />

			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="procedimento.riesameProvvedimentiQuestore.soggetto.richiedente" /></td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="direttiInteressati" name="${direttiInteressati}" pagesize="10" requestURI="${azione}">

				<display:column property="descrizioneSoggetto" title="Generalità soggetto richiedente" sortable="true" />
				<display:column property="dataIscrizione" title="Data di iscrizione" format="{0,date,dd/MM/yyyy}" sortable="true" />
			</display:table>
			<br />
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="procedimento.numeriregistro" /></td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="numeriRegistro" name="${numeriRegistro}" pagesize="10" requestURI="${azione}">
				<display:column property="ufficio" title="Ufficio" sortable="true" />
				<display:column property="procedimento" title="Procedimento" sortable="true" />
				<display:column property="dataIscrizione" title="Data Iscrizione" format="{0,date,dd/MM/yyyy}" sortable="true" />
				<display:column property="iter" title="Iter" sortable="true" />
			</display:table>
			<br />
			<br />
			<br />


				<table class="tb_bottoni" align="center">
					<tr>			
						<td class="td_tasto"><input type="button" id="id_buttonIndietro" value="Indietro"></td>
					</tr>
				</table>


		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="id_buttonIndietro"></smp:back>
				
	</tiles:putAttribute>
</tiles:insertDefinition>