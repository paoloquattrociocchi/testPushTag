<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/dettaglioAtto.htm" />
		<form:form method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			<form:hidden path="tipoAtto.id" /> 
			<form:hidden path="categoriaAtto.id" />
			<form:hidden path="codAtto" />
			
			<p class="titolo_page">
				<spring:message code="atto.dettaglio" />
			</p>
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br /><br />
			<!--  <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="tipoAtto.descrizione"><spring:message code="atto.tipo.atto" /></form:label></td>
						<td><c:out value="${attoForm.tipoAtto.descrizione}" /></td>
						<td><form:label path="categoriaAtto.descrizione"><spring:message code="atto.categoria.atto" /></form:label></td>
						<td><c:out value="${attoForm.categoriaAtto.descrizione}" /></td>
					</tr>
					<tr>
						<td><form:label path="statoAtto.descrizione"><spring:message code="atto.stato.atto" /></form:label></td>
						<td><c:out value="${attoForm.statoAtto.descrizione}" /></td>
						<td><form:label path="dataEmissione"><spring:message code="atto.dataemissione" /></form:label></td>
						<td>
						     <fmt:formatDate
								value="${attoForm.dataEmissione}" type="date"
								pattern="dd/MM/yyyy" />
				
					</tr>	-->
           
				
				
				<display:table class="tb_lista" uid="beni" name="${beni}" pagesize="10" requestURI="${azione}" >
										
					<display:column property="idBene" title="Identificativo pre correzione" sortable="false" />
					<display:column property="descrizioneBene" title="Bene pre correzione" sortable="false" />
					<display:column property="idBene2" title="Identificativo post correzione" sortable="false" />
					<display:column property="descrizioneBene2" title="Bene post correzione" sortable="false" />
				</display:table>       
                   
		

			<br />
			<br />
            <jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
            <table class="tb_form">
            <tr>
					<td class="td_titolo" colspan="4">&nbsp;</td>
				</tr>
             <tr>
            <c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
             <td width="16%">
                     <form:label path="dataEmissione" cssClass="required">
					  <spring:message code="atto.dataemissione" />
					 </form:label>
				</td>
				 <td width="25%">
					 <form:input path="dataEmissione" maxlength="10" size="12" id="dataEmissione" /> 
					 
					 <form:errors path="dataEmissione" class="testoRosso" id="dataEmissione" />
					 </td>
					
            
            </c:if>
            <c:if test="${attoForm.dataRegistrazione!=null || attoForm.statoAtto.id==3}">
						<td width="16%"><form:label path="dataEmissione">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td width="25%"><fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataEmissione" /></td>
            </c:if>
             <td colspan="2">&nbsp;</td>
            </tr>
            <tr>
					<td  colspan="4">&nbsp;</td>
				</tr>
            </table>
            <br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<smp:notificheButtons formId="attoForm" codAtto="${attoForm.codAtto}"></smp:notificheButtons>
					<c:if test="${attoForm.dataRegistrazione==null}">
						<c:if test="${attoForm.statoAtto.id!=3}">
					    <c:if test="${profilo.ruolo!='MAGISTRATO'}">
					 	<td class="td_tasto"><input type="button" value="Salva" onclick="inserisciAtto('Conferma', '${attoForm.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" /></td> 
						<input	id="Conferma" type="submit" name="Conferma" value="" style="display: none" />
					    
					   <td class="td_tasto"><input type="button" value="Conferma e Invia" onclick="inserisciAtto('Invia', '${attoForm.allegato.documentoFirmato}')" title="Invia proposta" class="nowait" /></td> 
						<input	id="Invia" type="submit" name="Invia" value="" style="display: none" />
						 <td class="td_tasto"><input type="submit" value="Modifica" name="Modifica"></td>
					</c:if>
					</c:if>
					</c:if>	
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
			<br />
			<br />


		</form:form>

		<smp:back buttonId="backButton"></smp:back>
			<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
			<script type="text/javascript">
				$(document).ready(function() {					
					prendiData("#dataEmissione",true);
				});
			</script>
		</c:if>
	</tiles:putAttribute>
</tiles:insertDefinition>