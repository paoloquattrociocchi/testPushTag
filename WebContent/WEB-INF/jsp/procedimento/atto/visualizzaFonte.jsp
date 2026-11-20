<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/inserimentoFonte.htm" />
		<form:form method="post" action="${azione}" commandName="atto"  enctype="multipart/form-data">	
			<p class="titolo_page"><spring:message code="atto.fonte"/></p>
			
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br/>
				
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4" >
							<spring:message code="atto.fonte"/>
						</td>
					</tr>
					<tr>
						<td><form:label path="fonte"><spring:message code="atto.fonte"/></form:label></td>
						<td><c:out value="${atto.fonte}"/></td>
						<td><form:label path="organo"><spring:message code="atto.organo"/></form:label></td>
						<td><c:out value="${atto.organo}"/></td>
					</tr>
					<tr>
						<td><form:label path="provinciaOrgano"><spring:message code="provincia"/></form:label></td>
						<td><c:out value="${atto.provinciaOrgano}"/></td>
						<td><form:label path="comuneOrgano"><spring:message code="comune"/></form:label></td>
						<td><c:out value="${atto.comuneOrgano}"/></td>
					</tr>
					<tr>
						<td><form:label path="indirizzoOrgano"><spring:message code="indirizzo"/></form:label></td>
						<td colspan="3"><c:out value="${atto.indirizzoOrgano}"/></td>
					</tr>
					<tr>
						<td><form:label path="altroOrgano"><spring:message code="atto.altroorgano"/></form:label></td>
						<td colspan="3"><c:out value="${atto.altroOrgano}"/></td>
					</tr>
					<tr>
						<td><form:label path="numeroRiferimento"><spring:message code="atto.numeroriferimento"/></form:label></td>
						<td><c:out value="${atto.numeroRiferimento}"/></td>
						<td><form:label path="dataInformativa"><spring:message code="atto.datainformativa"/></form:label></td>
						<td><c:out value="${atto.dataInformativa}"/></td>
					</tr>		
					<tr>
						<td><form:label path="note"><spring:message code="note"/></form:label></td>
						<td colspan="3">
							<form:textarea path="note" rows="2" cssStyle="width:98%" readonly="true"/>
						</td>
					</tr>
				</table>
						
				<br/><br/>
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="atto.fonte.soggettidellafonte"/></td>
					</tr>
				</table>
				<display:table class="tb_lista" uid="soggettiDellaFonte" name="${soggettiDellaFonte}" pagesize="10" requestURI="${azione2}" > 
					<display:column property="descrizioneSoggetto" title="Nome Cognome - Denominazione/Ragione Sociale" sortable="true" style="width: 50%" />
				</display:table>
				
				<br/><br/>
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.direttiinteressatinonassociati"/></td>
					</tr>
				</table>
				<display:table class="tb_lista" uid="soggettiDelProcedimentoNonAssociati" name="${soggettiDelProcedimentoNonAssociati}" pagesize="10" requestURI="${azione2}" > 
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:checkbox path="listaIdCheckbox" value="${soggettiDelProcedimentoNonAssociati.idSoggetto}" />
					</display:column>
					<display:column property="descrizioneSoggetto" title="Nome Cognome - Denominazione/Ragione Sociale" sortable="true" style="width: 50%" />
				</display:table>			
				<br/>
				<table class="tb_bottoni" align="center">
          			<tr>
          				<td class="td_tasto"><input type="submit" name="Associa" title ="Associa il soggetto alla fonte" value="Associa"></td>
              			<td class="td_tasto"><input type="button" value="Indietro" onClick="history.back()"></td>
              		</tr>
				</table>
				<br/><br/>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>