<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>



<%
//J.C. SITMP 282 - Amministratori - Disabilitare dettaglio e modifica ANBSC
String disableWiew = (String) request.getSession().getAttribute("disabledViewProc");

if ( disableWiew == null || disableWiew.isEmpty() ){	
	disableWiew = "false";
}
%>
<c:set var="disabilita" value="<%=disableWiew%>"/>
<c:set var="AGENZIA_BENI_CONFISCATI" value="<%=Constants.AGENZIA_BENI_CONFISCATI%>"/>
<%//J.C. SITMP 282 END %>

<%
//J.C. SITMP 306 - J.C. SITMP 307 - Amministratori - Disabilitare dettaglio e modifica
String fascicoloGestione = (String) request.getSession().getAttribute("fascicoloGestione");

if ( fascicoloGestione == null || fascicoloGestione.isEmpty() ){	
	fascicoloGestione = "false";
}
%>
<c:set var="fascicoloGestione" value="<%=fascicoloGestione%>"/>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
		<spring:url var="azione" value="/procedimento/amministratore/elencoAssociatoProcedimento.htm" />
		<spring:url var="paginazione" value="/procedimento/amministratore/paginazioneElencoAssociatoProcedimento.htm" />
		<p class="titolo_page">
			<spring:message code="amministratore.titolo.Amministratori" />
		</p>
		<form:form method="post" action="${azione}" commandName="amministratoreForm" >
		<form:hidden path="jspProvenienza" />
		<form:hidden path="dettaglio" />
			<div>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
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
						<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio" id="DettaglioB"></td>
					</logic:notEmpty>
					<logic:notEmpty name="amministratoriAssociati">
						<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica"></td>
					</logic:notEmpty>
					<logic:notEmpty name="amministratoriAssociati">
						<td class="td_tasto"><input type="submit" value="Beni in amministrazione" name="BeniInAmministrazione"></td>
					</logic:notEmpty>
					<logic:notEmpty name="amministratoriAssociati">
						<td class="td_tasto"><input type="submit" value="Coadiutori" name="Coadiutori"></td>
					</logic:notEmpty>
				</tr>
			</table>
		
		</form:form>

		<script type="text/javascript">

			if(document.getElementsByName("Coadiutori").length != 0) {
				document.getElementsByName("Coadiutori")[0].disabled=true;
			}		

			// J.C. - SITMP 282
			//var disabilita = ${disabledViewProc}; //Precedente SITMP 282
			var disabilita = ${disabilita};
			
			var idTipoProc = ${sessionScope.TIPO_FASCICOLO};
			var idStatoProc = ${sessionScope.procedimento.statoProcedimento.id};

			if(disabilita && idTipoProc == 8) {  
				if(document.getElementsByName("Modifica").length != 0) {
					document.getElementsByName("Modifica")[0].disabled=true;
				}
			}

			//camera di consiglio
			if(idTipoProc == 6) {  
				if(document.getElementsByName("Modifica").length != 0) {
					document.getElementsByName("Modifica")[0].disabled=true;
				}
				//stato fascicolo IN ACQUISIZIONE ISTANZA 
				if(idStatoProc == 47) {
					if(document.getElementsByName("BeniInAmministrazione").length != 0) {
						document.getElementsByName("BeniInAmministrazione")[0].disabled=true;
					}
				}				
			}

			function disableButtons(idAmm){
				
				var idAgenzia = ${AGENZIA_BENI_CONFISCATI};
				var idTipoProc = ${sessionScope.TIPO_FASCICOLO};
				
				if(idTipoProc != 6) {  
					
					if(idAmm == idAgenzia) {
						if(document.getElementsByName("DettaglioB").length != 0) {
							document.getElementsByName("DettaglioB")[0].disabled=true;
						}
						if(document.getElementsByName("Modifica").length != 0) {
							document.getElementsByName("Modifica")[0].disabled=true;
						}				
						//if(document.getElementsByName("BeniInAmministrazione").length != 0) {
						//	document.getElementsByName("BeniInAmministrazione")[0].disabled=true;
						//}		
						if(document.getElementsByName("Coadiutori").length != 0) {
							document.getElementsByName("Coadiutori")[0].disabled=false;
						}		
							
					} else {
						if(document.getElementsByName("DettaglioB").length != 0) {
							document.getElementsByName("DettaglioB")[0].disabled=false;
						}

						// J.C. - SITMP 282
						//var disabilita = ${disabledViewProc}; //Precedente SITMP 282
						var disabilita = ${disabilita};
						var idTipoProc = ${sessionScope.TIPO_FASCICOLO};
						
						if(!(disabilita && idTipoProc == 8)) {  
							if(document.getElementsByName("Modifica").length != 0) {
								document.getElementsByName("Modifica")[0].disabled=false;
							}
						}
						
						if(document.getElementsByName("BeniInAmministrazione").length != 0) {
							document.getElementsByName("BeniInAmministrazione")[0].disabled=false;
						}	
	
						if(document.getElementsByName("Coadiutori").length != 0) {
							document.getElementsByName("Coadiutori")[0].disabled=true;
						}		
													
					}

					var fascicoloGestione = ${fascicoloGestione};
					if(fascicoloGestione == "true") {

						if(document.getElementsByName("DettaglioB").length != 0) {
							document.getElementsByName("DettaglioB")[0].disabled=true;
						}

						if(document.getElementsByName("Modifica").length != 0) {
							document.getElementsByName("Modifica")[0].disabled=false;
						}

						if(document.getElementsByName("BeniInAmministrazione").length != 0) {
							document.getElementsByName("BeniInAmministrazione")[0].disabled=false;
						}

						if(document.getElementsByName("Coadiutori").length != 0) {
							document.getElementsByName("Coadiutori")[0].disabled=true;
						}	

					}

				}

			}
			
		</script>
							
	</tiles:putAttribute>
</tiles:insertDefinition>