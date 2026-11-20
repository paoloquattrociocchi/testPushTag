<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld"%>

<tiles:insertDefinition name="base.definition">
<tiles:putAttribute name="body-content">
	
			<c:if test="${ricerca.funzioneCameralini=='misuraPatrimoniale' }">
				<spring:url var="azione" value="/procedimento/nuovo/misuraPatrimoniale/ricerca.htm" />
				<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.ricerca.misurapatrimoniale" /></p>
			</c:if>
			<c:if test="${ricerca.funzioneCameralini=='misuraPersonale'}" >
				<spring:url var="azione" value="/procedimento/nuovo/misuraPersonale/ricerca.htm" />
				<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.ricerca.misurapersonale" /></p>
			</c:if>
			<c:if test="${ricerca.funzioneCameralini=='correzione'}" >
				<spring:url var="azione" value="/procedimento/nuovo/correzione/ricerca.htm" />
				<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.ricerca.correzione" /></p>
			</c:if>
			<c:if test="${ricerca.funzioneCameralini=='cauzioni'}" >
				<spring:url var="azione" value="/procedimento/nuovo/cauzioni/ricerca.htm" />
				<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.ricerca.cauzione" /></p>
			</c:if>
			<c:if test="${ricerca.funzioneCameralini=='permessi'}" >
				<spring:url var="azione" value="/procedimento/nuovo/permessi/ricerca.htm" />
				<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.ricerca.permessi" /></p>
			</c:if>
			<c:if test="${ricerca.funzioneCameralini=='terzi'}" >
				<spring:url var="azione" value="/procedimento/nuovo/terzi/ricerca.htm" />
				<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.ricerca.terzi" /></p>
			</c:if>
			<c:if test="${ricerca.funzioneCameralini=='amministratore'}" >
				<spring:url var="azione" value="/procedimento/nuovo/amministratore/ricerca.htm" />
				<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.ricerca.amministratore" /></p>
			</c:if>
				<br />
				<form:form id="id_formRicerca" method="post" action="${azione}" commandName="ricerca">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><spring:message code="cameralini.appellativoProcedimentoPadre"/></td>
						<td><form:label path="procedPadre.anno" cssClass="required"><spring:message code="cameralini.prevenzione.anno"/></form:label></td>
						<td><form:input path="procedPadre.anno" id="anno" maxlength="4" size="5"/>
						<form:errors path="procedPadre.anno" class="testoRosso"/></td>
						<td><form:label path="procedPadre.numero" cssClass="required"><spring:message code="cameralini.prevenzione.numero"/></form:label></td>
						<td><form:input path="procedPadre.numero" id="numero" maxlength="12" size="14"/>
						<form:errors path="procedPadre.numero" class="testoRosso"/></td>
						<td><form:label path="ufficio"><spring:message code="cameralini.prevenzione.ufficio"/></form:label></td>
		        		<form:hidden path="ufficio.id" id="ufficio" value="${idUffOwner}" size="1" />
			        	<td><form:input path="ufficioSelezionatoDesc" value="${ufficioSelezionatoDesc}" readonly="true" size="40"/></td> 
			        </tr>
				</table>
				<form:hidden id="hiddenUfficio" path="ufficioSelezionatoDesc"/>
				<br/><br/>
				<table class="tb_bottoni" align="center">
                 	<tr>
                     	<td class="td_tasto"><input type="submit" name="Ricerca" title="Ricerca" value="Ricerca" onclick="settaDescrizioneUfficio()"/></td>
   					</tr>
				</table>
				</form:form>

<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
<smp:back buttonId="backButton"></smp:back>

<script type="text/javascript">
	function settaDescrizioneUfficio() {
		var e = document.getElementById("ufficio");
		<%-- MEV_30b: risolto errore js sulla pagina: ufficio non è un array --%>
		var strUser = "";
		if (e.options)
			strUser = e.options[e.selectedIndex].text;
		else
			strUser = document.getElementById("ufficioSelezionatoDesc").value;
		document.getElementById('hiddenUfficio').value = strUser;
	}
</script>

</tiles:putAttribute>
</tiles:insertDefinition>