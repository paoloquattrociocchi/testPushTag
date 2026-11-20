<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>

<c:set var="AGENZIA_BENI_CONFISCATI" value="<%=Constants.AGENZIA_BENI_CONFISCATI%>"/>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
		<spring:url var="azione" value="/procedimento/amministratore/elencoAssociatoAtto.htm" />
		<spring:url var="paginazione" value="/procedimento/amministratore/paginazioneElencoAssociatoAtto.htm" />
		<p class="titolo_page">
			<spring:message code="amministratore.titolo.Amministratori" />
		</p>
		<form:form method="post" action="${azione}" commandName="amministratoreForm" >
		<form:hidden path="jspProvenienza" />
		<form:hidden path="dettaglio" />
			<div>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				 <br/>
    			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			</div>
			<br />
	<display:table id="row" name="${amministratoriAssociati}" class="tb_lista" uid="amministratoriAssociati" pagesize="10" requestURI="${paginazione}">
				<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
					<form:radiobutton path="id" value="${amministratoriAssociati.id}" onclick="disableButtons('${amministratoriAssociati.id}')" />
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

			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
				<logic:notEmpty name="amministratoriAssociati">
					<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio" id="btnDettaglio"></td>
						<c:if test="${statoAtto == 1}">
							<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica"></td>
							<td class="td_tasto"><input type="submit" value="Elimina" name="Elimina"></td>
							<td class="td_tasto"><input type="submit" value="Associa Beni" name="Associa"></td>
						</c:if>
						<td class="td_tasto"><input type="submit" value="Beni in amministrazione" name="BeniInAmministrazione"></td>
					</logic:notEmpty>
				</tr>
			</table>
			
		<script type="text/javascript">

			function disableButtons(idAmm){
				var idAgenzia = ${AGENZIA_BENI_CONFISCATI};
				if(idAmm == idAgenzia) {
					//J.C. SITMP 296
					if(document.getElementsByName("btnDettaglio").length != 0) {
						document.getElementsByName("btnDettaglio")[0].disabled=true;
					}
					if(document.getElementsByName("Modifica").length != 0) {
						document.getElementsByName("Modifica")[0].disabled=true;
					}

					if(document.getElementsByName("Elimina").length != 0) {
						document.getElementsByName("Elimina")[0].disabled=false;
					}		

					if(document.getElementsByName("Associa").length != 0) {
						document.getElementsByName("Associa")[0].disabled=false;
					}		
						
					if(document.getElementsByName("BeniInAmministrazione").length != 0) {
						document.getElementsByName("BeniInAmministrazione")[0].disabled=false;
					}		
			
				} else {

					//J.C. SITMP 296
					if(document.getElementsByName("btnDettaglio").length != 0) {
					document.getElementsByName("btnDettaglio")[0].disabled=false;
					}
					
					if(document.getElementsByName("Modifica").length != 0) {
					document.getElementsByName("Modifica")[0].disabled=false;
					}

					if(document.getElementsByName("Elimina").length != 0) {
						document.getElementsByName("Elimina")[0].disabled=false;
					}		

					if(document.getElementsByName("Associa").length != 0) {
						document.getElementsByName("Associa")[0].disabled=false;
					}		
					
					if(document.getElementsByName("BeniInAmministrazione").length != 0) {
						document.getElementsByName("BeniInAmministrazione")[0].disabled=false;
					}								
				}

			}
			
		</script>
					
		</form:form>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
