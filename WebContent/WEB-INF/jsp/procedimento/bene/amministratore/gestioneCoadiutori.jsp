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
		<spring:url var="azione" value="/procedimento/amministratore/elencoAssociatoProcedimento.htm" />

		<p class="titolo_page">
			<spring:message code="coadiutori.titolo" />
		</p>

		<form:form method="post" action="${azione}" commandName="coadiutoreForm" >

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			
			<form:hidden path="idProcedimento" />
			<form:hidden path="idUfficio" />
			<form:hidden path="idAmministratore" />
	
			<br />

			<display:table id="row" name="${listaCoadiutori}" class="tb_lista" uid="coad">
	    		<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
					<form:radiobutton path="idProgrCoad" value="${coad.idProgrCoad}" />
		    	</display:column>
				<display:column property="cognome" title="Cognome" />
				<display:column property="nome" title="Nome" />
				<display:column property="dataNascita" title="Data nascita" format="{0,date,dd/MM/yyyy}" />
				<display:column property="codiceFiscale" title="Codice Fiscale" />
				<display:column property="luogoNascita" title="Luogo di nascita" />
				<display:column property="recapito" title="Recapito" />
				<display:column property="inizioIncarico" title="Data inizio incarico" format="{0,date,dd/MM/yyyy}" />
				<display:column property="fineIncarico" title="Data fine incarico" format="{0,date,dd/MM/yyyy}"  />
			</display:table>
			
			<br />
			<br />			
					
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Nuovo" name="NuovoCoadiutore" ></td>
					<td class="td_tasto"><input type="submit" value="Modifica" name="ModificaCoadiutore"></td>
					<td class="td_tasto"><input type="submit" value="Dettaglio" name="DettaglioCoadiutore"></td>
					<td class="td_tasto"><input type="submit" value="Elimina" name="EliminaCoadiutore"></td>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro"></td>					
				</tr>
			</table>
	
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
		
		<script type="text/javascript">

			var idTipoProc = ${sessionScope.TIPO_FASCICOLO};
			if(idTipoProc == 8) {  
				if(document.getElementsByName("NuovoCoadiutore").length != 0) {
					document.getElementsByName("NuovoCoadiutore")[0].disabled=true;
				}
				if(document.getElementsByName("ModificaCoadiutore").length != 0) {
					document.getElementsByName("ModificaCoadiutore")[0].disabled=true;
				}
				if(document.getElementsByName("NuovoCoadiutore").length != 0) {
					document.getElementsByName("NuovoCoadiutore")[0].disabled=true;
				}
				if(document.getElementsByName("EliminaCoadiutore").length != 0) {
					document.getElementsByName("EliminaCoadiutore")[0].disabled=true;
				}				
			}

		</script>
									
	</tiles:putAttribute>
</tiles:insertDefinition>