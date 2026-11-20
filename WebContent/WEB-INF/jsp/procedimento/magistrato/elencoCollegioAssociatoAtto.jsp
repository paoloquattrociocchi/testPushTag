<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
	
		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
		<c:set var="STATOATTO_REGISTRATO" value="<%=Constants.STATOATTO_REGISTRATO%>"/>		
		<c:set var="STATOATTO_INOLTRATO" value="<%=Constants.STATOATTO_INOLTRATO%>"/>
		<c:set var="STATOATTO_IN_LAVORAZIONE" value="<%=Constants.STATOATTO_IN_LAVORAZIONE%>"/>
		
		
		<spring:url var="azione" value="/procedimento/atto/collegio/associati/gestione.htm" />
		<p class="titolo_page"><spring:message code="magistrato.titolo.associati.atto" /></p>
		<form:form method="post" action="${azione}" commandName="magistratoForm">

			<div>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br/>
				 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />					
			</div>
			<br/>
			
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="magistrato.lista.associati" /></td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="magistratiAssociati" name="${magistratiAssociati}" pagesize="10"
				requestURI="${azione}" >
				<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
					<form:radiobutton path="idMagString" value="${magistratiAssociati.id};${magistratiAssociati.ruolo.id}" />
				</display:column>
				<display:column property="generalita" title="Cognome Nome" sortable="true" />
				<display:column property="ruolo.descrizione" title="Ruolo" sortable="true" />
				<display:column property="descrizioneCortaRelatore" title="Relatore" sortable="true" />
			</display:table>

			<br />
			<br />
			<table class="tb_bottoni" align="center">
			<tr>
			
				<logic:notEmpty name="magistratiAssociati">
					
						<c:if test="${sessionScope.atto.statoAtto.id==STATOATTO_IN_LAVORAZIONE}">
						  	<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica"></td>
							<td class="td_tasto"><input type="submit" value="Elimina" name="Elimina"></td>
							<td class="td_tasto"><input type="submit" value="Nuovo" name="Nuovo"></td>
						</c:if>							
					
				</logic:notEmpty>
				
				<logic:empty name="magistratiAssociati">
					
						<c:if test="${sessionScope.atto.statoAtto.id==STATOATTO_IN_LAVORAZIONE}">
							<td class="td_tasto"><input type="submit" value="Nuovo" name="Nuovo"></td>
						</c:if>
				
				</logic:empty>
				
				
				
				<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/atto/copertinaAtto.htm')"></td>
				</tr>
			</table>
		</form:form>
		
		<script type="text/javascript">
			$(document).ready(function() {
				//disabilitaSubmitCollegioElencoAtto('${atto.statoAtto.id}','${atto.categoriaAtto.id}');
			});
		</script>
	
	</tiles:putAttribute>
</tiles:insertDefinition>
