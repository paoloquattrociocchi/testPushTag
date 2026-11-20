<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/copertinaAttoCda.htm" />
		<spring:url var="paginazione" value="/procedimento/atto/paginazioneOrdineCronologicoAttiCda.htm" />
		<style type="text/css">
			.spanflgNascondiRicevute label {
				font-weight: normal !important;
				color: black;
			}
		</style>
		<form:form method="post" action="${azione}" commandName="attoForm" id="idAttoForm">
        
       
			
			<p class="titolo_page">
				<spring:message code="atto.elencoCronologico.cda" />
				
			</p>
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />


			<table class="tb_form" cellspacing="0" cellpadding="0" border="0"-->
		       <tr>
		      	<td class="td_titolo_label">
				<p>
					Fascicolo Cda: <c:out value="${attoForm.descTribCda}" />

				</p></td>
		        </tr>


			<logic:notEmpty name="listaAtti">
			<display:table class="tb_lista" uid="listaAtti" name="${listaAtti}" pagesize="10" requestURI="${paginazione}">
				<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
					<form:radiobutton path="codAtto" value="${listaAtti.codAtto}" />
				</display:column>
				<display:column title="Atto" sortable="false" >
					<table><tr><td valign="middle">${listaAtti.categoriaAtto.descrizione} ${listaAtti.numeroProcedimentoOrigine} ${listaAtti.descVersionamento}</td><td>${listaAtti.descInfoScheda}</td></tr></table> 
				</display:column>
				<display:column title="Numero atto" sortable="false">
					<c:out value="${listaAtti.numeroAtto}" />

				</display:column>
				<display:column property="statoAtto.descrizione" title="Stato" sortable="false" />
				<display:column property="dataDeposito" title="Data di deposito" format="{0,date,dd/MM/yyyy}" sortable="false" />
				
			</display:table>
			 <form:hidden path="idProcedimentoCdatrib" />
     
     		<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" name="Dettaglio" title="Dettaglio" value="Dettaglio" /></td>

				</tr>
			</table>
			<br />
			<br />
			</logic:notEmpty>
			
			<logic:empty   name="listaAtti">
			 <table class="tb_form" cellspacing="0" cellpadding="0" border="0"-->
					       <tr>
					      	<td class="td_titolo_label">
							<p>
								Non ci sono dati disponibili in CdA
							</p></td>
					        </tr>
				        </table>
			
			</logic:empty>				
		</form:form>




	</tiles:putAttribute>
</tiles:insertDefinition>