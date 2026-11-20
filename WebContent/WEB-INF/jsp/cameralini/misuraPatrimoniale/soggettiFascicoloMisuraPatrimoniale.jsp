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
			<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.misurapatrimoniale" /></p>
			<spring:url var="azione" value="/procedimento/nuovo/misuraPatrimoniale/ricerca.htm" />
			<form:form id="id_formElencoSoggetti" method="post" action="${azione}" commandName="form">
			<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td colspan="6" align="left"><spring:message code="cameralini.dettaglioProcedimento" /></td>
					</tr>
		  			<jsp:include page="/WEB-INF/jsp/cameralini/intestazioneDettaglio.jsp" />
			     </table>
			    
			   			     
			     <br /><br />
					<display:table class="tb_lista" uid="elemRisRicerca" name="${listaSoggetti}" pagesize="10" requestURI="${azione}" >
								
						<display:column paramId="soggetto.idSoggetto" paramProperty="soggettiMisPers[${elemRisRicerca_rowNum-1}].idSoggetto" style="width:10%" 
										title="Sel." >										
							<form:checkbox path="soggettiMisPers[${elemRisRicerca_rowNum-1}].idSoggetto" value="${elemRisRicerca.idSoggetto}" />
						</display:column>

						<display:column property="descrizioneSoggetto" title="Generalità" sortable="false"/>
					</display:table>
					<br/><br/>
					
				<table class="tb_bottoni" align="center">
					<tr>
					    <td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio" title="Dettaglio"></td>
                      	<td class="td_tasto"><input type="submit" name="Registra" title="Registra" value="Registra"/></td>
                      	<td class="td_tasto"><input type="submit" name="Indietro" title="Indietro" value="Indietro"/></td>
					</tr>
				</table>
				<br/>
			     
			     
			</form:form>
			
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
<smp:back buttonId="backButton"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>