<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/fug/elencoQuoteCBis.htm" />
		<p class="titolo_page">
			<spring:message code="elenco.modelliC" />
		</p>
		<form:form method="post" action="${azione}" commandName="fugForm">
		<form:hidden path="idBene" />
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
	<br />
		<table class="tb_form">
			<tr>
				<td class="td_titolo" colspan="4"><spring:message code="bene" /> <c:out value="${fugForm.idBene}" /> <wbr> <wbr> - <wbr> <wbr> <c:out value="${fugForm.descBene}" /></td>
			</tr>
		</table>	
			
		
	<br />
	<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td class="td_titolo" colspan="4"><spring:message code="elenco.modelli.sottotitolo" /></td>
		</tr>
	</table>
		<logic:notEmpty name="modelliInviati">
			<display:table class="tb_lista" uid="modelliInviati" name="${modelliInviati}" pagesize="10" requestURI="${azione}">
				<logic:present name="ceckAutomatico">
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:radiobutton path="idInvio" value="${modelliInviati.idInvio}" checked="checked" />
					</display:column>
				</logic:present>
				<logic:notPresent name="ceckAutomatico">
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:radiobutton path="idInvio" value="${modelliInviati.idInvio}" />
					</display:column>
				</logic:notPresent>
				<display:column title="Protocollo invio" sortable="false" value="${modelliInviati.descProtocollo}"/>
				<display:column property="dataInvio" title="Data invio" format="{0,date,dd/MM/yyyy}" sortable="false" />
				<display:column title="Atto di riferimento" sortable="false" value="${modelliInviati.categoriaAtto.descrizione} "/>
				<display:column title="Numero atto" sortable="false" value="${modelliInviati.numeAtto}"/>
			</display:table>
		</logic:notEmpty>
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<logic:notEmpty name="modelliInviati">
						<td class="td_tasto"><input type="submit" value="Seleziona" name="Seleziona"></td>
						<td class="td_tasto"><input type="button" id="backButton" value="Indietro"></td>
					</logic:notEmpty>
				</tr>
			</table>
		</form:form>
			
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
		
	</tiles:putAttribute>
</tiles:insertDefinition>

