
<%@page import="it.giustizia.smp.form.ComboForm"%>
<%@page import="java.util.List"%>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%!
String comboIdProcedente=null;
String comboDescrizioneProcedente=null;

%>
<% List<ComboForm> listaProcedenti=(List<ComboForm>)request.getAttribute("procedenti");
comboIdProcedente=null;
comboDescrizioneProcedente=null;
if (listaProcedenti != null && listaProcedenti.size()== 1 ){
	comboIdProcedente=listaProcedenti.get(0).getId();
	comboDescrizioneProcedente=listaProcedenti.get(0).getDescrizione();
}

%>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
	    	<c:set var="comboIdProcedente"  scope="page"  value="<%=comboIdProcedente%>" />
	    	<c:set var="comboDescrizioneProcedente"  scope="page"  value="<%=comboDescrizioneProcedente%>" />
	    	
		<spring:url var="azione" value="/procedimento/nuovo/aggravamento/dettaglioPadre.htm" />
		<form:form method="post" action="${azione}" commandName="procedimento">
			<p class="titolo_page">
				<spring:message code="procedimento.aggravamento.dettaglioPadre" />
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
					<td><form:label path="proponente">
							<spring:message code="procedimento.proponente" />
						</form:label></td>
					<td colspan="3"><c:out value="${procedimento.proponente}" /></td>
				</tr>

				<tr>
					<td><form:label path="procedente">
							<spring:message code="procedimento.procedente" />
						</form:label></td>
					<td><c:out value="${procedimento.procedente.descrizione}" /></td>
					<td><form:label path="statoProcedimento">
							<spring:message code="procedimento.stato" />
						</form:label></td>
					<td><c:out value="${procedimento.statoProcedimento.descrizione}" /></td>
				</tr>
			</table>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="15%"><form:label path="tipologiaPrevenzione">
							<spring:message code="procedimento.tipologiaPrevenzione" />
						</form:label></td>
					<td width="28%"><c:if test="${procedimento.antimafia==true}">
							<input type="checkbox" disabled="disabled" checked="checked" />
						</c:if> <c:if test="${procedimento.antimafia==false}">
							<input type="checkbox" disabled="disabled" />
						</c:if> <form:hidden path="antimafia" /> <form:label path="antimafia">
							<spring:message code="procedimento.antimafia" />
						</form:label></td>
					<td width="28%"><c:if test="${procedimento.antiterrorismo==true}">
							<input type="checkbox" disabled="disabled" checked="checked" />
						</c:if> <c:if test="${procedimento.antiterrorismo==false}">
							<input type="checkbox" disabled="disabled" />
						</c:if> <form:hidden path="antiterrorismo" /> <form:label path="antiterrorismo">
							<spring:message code="procedimento.antiterrorismo" />
						</form:label></td>
					<td width="29%"><c:if test="${procedimento.ordinaria==true}">
							<input type="checkbox" disabled="disabled" checked="checked" />
						</c:if> <c:if test="${procedimento.ordinaria==false}">
							<input type="checkbox" disabled="disabled" />
						</c:if> <form:hidden path="ordinaria" /> <form:label path="ordinaria">
							<spring:message code="procedimento.ordinaria" />
						</form:label></td>
				</tr>
			</table>

			<br />
			<br />

			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettidelprocedimento" /></td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="direttiInteressati" name="${direttiInteressati}" pagesize="10" requestURI="${azione}">
				<display:column title="Seleziona">
					<form:radiobutton path="idSelezionato" value="${direttiInteressati.idSoggetto}" />
				</display:column>
				<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" />
				<display:column property="dataIscrizione" title="Data iscrizione" format="{0,date,dd-MM-yyyy}" sortable="true" />
				<display:column property="misura.descrizioneMisura" title="Misure" sortable="true" />
				<display:column property="misura.cauzione" title="Cauzione" sortable="true" />
				<display:column property="misura.art67" title="Art.67" sortable="true" />
			</display:table>

			<br />
			<br />

			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td><form:label path="procedente.id" cssClass="required">
							<spring:message code="procedimento.procedente" />
						</form:label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<c:if test="${comboIdProcedente==null}">
						 <form:select path="procedente.id" size="1">
							<form:option value="-1" label="--- Seleziona Procedente ---" />
							<form:options items="${procedenti}" itemValue="id" itemLabel="descrizione" />
							<form:errors path="procedente.id" class="testoRosso" />
						</form:select>
					  </c:if>
					<c:if test="${comboIdProcedente!=null}">
					<form:hidden path="procedente.id"  value="${comboIdProcedente}" />
					<c:out value="${comboDescrizioneProcedente}"/> 
					 </c:if>
					</td>	
				</tr>
				<form:hidden path="idProcedimento" />
			</table>

			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" name="ISCRIVI" value="Iscrivi"></td>
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro"></td>
				</tr>
			</table>
			<br />
			<br />

		</form:form>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
