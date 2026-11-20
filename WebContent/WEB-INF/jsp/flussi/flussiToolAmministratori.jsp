 <%@page import="it.giustizia.smp.form.ProfiloForm"%>
 <% 
   ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
 %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione"
			value="/flussi/dettaglioDeposito.htm" />
		<p class="titolo_page">
			<spring:message code="depositi" />
		</p>
		<form:form method="post" action="${azione}" commandName="flussiToolAmministratoriForm">
			<br/>
			
			<table class="tb_form">	
				<tr>
					<td class="td_titolo" colspan="4">
					   <spring:message code="depositi.elenco" />
					</td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="risultati" name="${risultatiRicerca}" pagesize="10" requestURI="${azione}" >
				<display:column sortable="false" style="text-align: center; width: 5%" titleKey="seleziona">
					<form:radiobutton id="listaIdRadio" path="idDeposito" value="${risultati.idDeposito}" />
				</display:column>					
				<display:column property="descProc" sortable="false" titleKey="depositi.procedimentoPrev"/>
				<display:column property="descProcFG" sortable="false" titleKey="depositi.procedimentoGest"/>
				<display:column property="dataRicezione" sortable="false" format="{0,date,dd/MM/yyyy}" titleKey="depositi.dataRicezione"/>
				<display:column property="amministratore" sortable="false" titleKey="depositi.amministratore" />
			</display:table>
			
			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Acquisisci Deposito" name="Dettaglio"></td>
				</tr>
			</table>
		</form:form>
		
	</tiles:putAttribute>
</tiles:insertDefinition>