<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/apri/dettaglioProcedimento.htm" />
		<form:form method="post" action="${azione}" commandName="procedimento">
			<p class="titolo_page">
				<spring:message code="procedimento.ricercaPuntuale" />
			</p>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td><form:label path="anno">
							<spring:message code="procedimento.titoloDiv" />
						</form:label></td>
					<td><form:label path="anno" cssClass="required">
							<spring:message code="procedimento.anno" />
						</form:label></td>
					<td><form:input path="anno" id="anno" maxlength="4" size="5" /> <form:errors path="anno" class="testoRosso" /></td>
					<td><form:label path="numero" cssClass="required">
							<spring:message code="procedimento.numero" />
						</form:label></td>
					<td><form:input path="numero" maxlength="12" /> <form:errors path="numero" class="testoRosso" /></td>
				</tr>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" name="RicercaPuntuale" title="Ricerca procedimento" value="Ricerca" /></td>

				</tr>
			</table>
			<br />
			<br />
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
