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
			<form:form id="id_formElencoAmministratori" name="id_formElencoAmministratori" method="post" action="${azione}" commandName="form">
			<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td colspan="6" align="left"><spring:message code="cameralini.dettaglioProcedimento" /></td>
					</tr>
		  			<jsp:include page="/WEB-INF/jsp/cameralini/intestazioneDettaglio.jsp" />
			     </table>
			    
			   			     
			     <br /><br />

					<display:table class="tb_lista" uid="listaAmministratori" name="${listaAmministratori}"  pagesize="10" requestURI="${azione}" >
						<display:column sortable="false" style="text-align: center; width: 5%" titleKey="seleziona">
							<form:radiobutton id="amministratoreSelected" path="amministratoreSelected" value="${listaAmministratori.id}" onclick="abilitaRegistra()"/>
						</display:column>			
						<display:column property="cognome" title="Cognome" sortable="true" />
						<display:column property="nome" title="Nome" sortable="true" />
						<display:column property="codiceFiscale" title="Codice fiscale" sortable="true" />
						<display:column property="descrIscrizioneAlbo" title="Ordine professionale" sortable="true" />
						<display:column property="dataIscrizioneAlbo" title="Data iscrizione Albo" format="{0,date,dd/MM/yyyy}" sortable="true" />
						<display:column property="comuneDomicilioLegale" title="Domicilio legale" sortable="true" />
						<display:column title="Sospeso" sortable="true">
							<c:if test="${amministratoriAssociati.dataFineIncarico!=null}">
								<c:out value="SI" />				
							</c:if>
							<c:if test="${amministratoriAssociati.dataFineIncarico==null}">
								<c:out value="NO" />
							</c:if>				
						</display:column>
					</display:table>
					<br/><br/>
				
				<div id="pulsanteRegistra" style="visibility: visible;">
					<table class="tb_bottoni" align="center">
						<tr>
						    <td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio" title="Dettaglio"></td>
						    
						    <logic:empty name="listaOmonimi">
						    	<td class="td_tasto"><input type="submit" name="Registra" title="Registra" value="Registra"></td>
						    </logic:empty>
						    
						    <logic:notEmpty name="listaOmonimi">
						    	<td class="td_tasto"><input type="submit" name="Registra" title="Registra" value="Registra" disabled="disabled"></td>
						    </logic:notEmpty>
						    
						    <td class="td_tasto"><input type="submit" name="Indietro" title="Indietro" value="Indietro"/></td>
						    
						</tr>
					</table>
				</div>
				
				
				<br/>
				<logic:notEmpty name="listaOmonimi">
				<div id="listaOmonimi_id" style="visibility: visible;">
				<display:table class="tb_lista" uid="listaOmonimi" name="${listaOmonimi}"  pagesize="10" requestURI="${azione}" >
						<display:column sortable="false" style="text-align: center; width: 5%" titleKey="seleziona">
							<form:radiobutton id="amministratoreOmonimoSel" path="amministratoreOmonimoSel" value="${listaOmonimi.CODI_K_ID_INTESTATARIO}'^'${listaOmonimi.CODI_K_ID_UFF_OWNER}"/>
						</display:column>			
						<display:column property="descrizioneAmministratore" title="Amministratori già presenti" sortable="false"/>
					</display:table>
					<br/>
					<table class="tb_bottoni" align="center">
						<tr>
						    <td class="td_tasto"><input type="submit" value="Associa" name="Associa" title="Associa"></td>
	                      	<td class="td_tasto"><input type="submit" name="Nuovo" title="Nuovo" value="Nuovo"/></td>
						</tr>
					</table>
					</div>
			   	</logic:notEmpty>
			   
		   

	    	 
			</form:form>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
<smp:back buttonId="backButton"></smp:back>
			
			<script type="text/javascript">
function abilitaRegistra() {
		document.getElementsByName("Registra")[0].disabled=false;
 		document.getElementById("listaOmonimi_id").style.visibility='hidden';
	}
</script>	
			

	</tiles:putAttribute>
</tiles:insertDefinition>