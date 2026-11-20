<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
			<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.correzione" /></p>
			<spring:url var="azione" value="/procedimento/nuovo/correzione/ricerca.htm" />
			
			<form:form id="id_formElencoSoggetti" method="post" action="${azione}" commandName="form">
			<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td colspan="6" align="left"><spring:message code="cameralini.dettaglioProcedimento" /></td>
					</tr>
		  			<jsp:include page="/WEB-INF/jsp/cameralini/intestazioneDettaglio.jsp" />
			     </table>
			 			   			     
			     <br /><br />
				<form:hidden id="idTipoSoggetto" path="tipoSoggetto" />
				<form:hidden id="idTipoUfficio" path="tipoUfficio" />
				<form:hidden id="idFunzioneCameralini" path="funzioneCameralini" />
				
				<display:table class="tb_lista" name="${listaSoggetti}" uid="listaSoggetti" pagesize="10" requestURI="${azione}" >
						<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
							<form:radiobutton id="soggettoSelected" path="soggettoSelected" value="${listaSoggetti.idSoggetto}"/>
						</display:column> 
						<display:column property="descrizioneSoggetto" title="Generalità" sortable="false"/>
				</display:table>
				
					
				<table class="tb_bottoni" align="center">
					<tr>
					    <td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio" title="Dettaglio"></td>
					  <c:if test="${form.tipoUfficio == '19'}">
                      	<td class="td_tasto"><input type="submit" name="Modifica" title="Modifica" value="Modifica"/></td>
                      </c:if>
                      	<td class="td_tasto"><input type="submit" name="Indietro" title="Indietro" value="Indietro"/></td>
					</tr>
				</table>
				<br/>
			     
			     
			</form:form>
			
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
<smp:back buttonId="backButton"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>