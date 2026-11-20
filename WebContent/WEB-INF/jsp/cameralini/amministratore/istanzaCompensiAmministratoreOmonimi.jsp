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
			<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.ricerca.amministratore" /></p>
			<spring:url var="azione" value="/procedimento/nuovo/amministratore/ricerca.htm" />
			<form:form id="id_formElencoAmministratori" method="post" action="${azione}" commandName="form">
			<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td colspan="6" align="left"><spring:message code="cameralini.dettaglioProcedimento" /></td>
					</tr>
		  			<jsp:include page="/WEB-INF/jsp/cameralini/intestazioneDettaglio.jsp" />
			     </table>
			    
			   			     
			     <br /><br />
			     
					<display:table class="tb_lista" uid="listaOmonimi" name="${listaOmonimi}"  pagesize="10" requestURI="${azione}" >
						<display:column sortable="false" style="text-align: center; width: 5%" titleKey="seleziona">
							<form:radiobutton id="amministratoreOmonimoSel" path="amministratoreOmonimoSel" value="${listaOmonimi.CODI_K_ID_INTESTATARIO}'^'${listaOmonimi.CODI_K_ID_UFF_OWNER}" />
						</display:column>			
						<display:column property="descrizioneAmministratore" title="Amministratori già presenti" sortable="false"/>
					</display:table>
					<br/>
					<table class="tb_bottoni" align="center">
					<tr>
					    <td class="td_tasto"><input type="submit" value="Associa" name="Associa" title="Associa"></td>
                      	<td class="td_tasto"><input type="submit" name="Nuovo" title="Nuovo" value="Nuovo"/></td>
                      	<td class="td_tasto"><input type="submit" name="Annulla" title="Annulla" value="Indietro"/></td>
					</tr>
				</table>
			 <form:hidden id="amministratoreSelected" path="amministratoreSelected" />    
			</form:form>
			
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
<smp:back buttonId="backButton"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>