<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/copertinaAtto.htm" />
		<form:form method="post" id="id_formConfermaInvia"  action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			<form:hidden path="codAtto" />
			<p class="titolo_page">
				<spring:message code="atto.dettaglio" />
			</p>
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br/>
            <logic:equal name="attoForm" property="soggetto.tipoSoggetto" value="F">
               <jsp:include page="/WEB-INF/jsp/procedimento/atto/includeDettaglioPerFis.jsp" />
            </logic:equal>
            <logic:equal name="attoForm" property="soggetto.tipoSoggetto" value="G">
               <jsp:include page="/WEB-INF/jsp/procedimento/atto/includeDettaglioPerGiu.jsp" />
            </logic:equal>
		
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />	

			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
			<br />
			<br />


		</form:form>
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
	</tiles:putAttribute>
</tiles:insertDefinition>