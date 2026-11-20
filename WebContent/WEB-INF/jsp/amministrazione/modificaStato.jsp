<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/funzioniAmministrazione/visualizzaRicercaStato.htm" />
			<p class="titolo_page">
				<spring:message code="amministrazione.stati.modifica.titolo" />		
			</p>
			<form:form method="post" action="${azione}" commandName="statoForm" id="idForm">
				<br/>
				<form:hidden path="codiKIdStato" />
				
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="descStato"><spring:message code="descrizioneStato"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="descStato" maxlength="50" size="50"/>
			        		<form:errors path="descStato" class="testoRosso"/>
			        	</td>
			        </tr>
			        <tr>
			        	<td><form:label path="descSiglaStato"><spring:message code="siglaStato"/></form:label></td>
			        	<td>
			        		<form:input path="descSiglaStato" maxlength="3" size="3"/>
			        		<form:errors path="descSiglaStato" class="testoRosso"/>
						</td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="descIstatStato"><spring:message code="istatStato"/></form:label></td>
			        	<td>
			        		<form:input path="descIstatStato" maxlength="3" size="3"/>
			        		<form:errors path="descIstatStato" class="testoRosso"/>
						</td>
			    	</tr>
					<tr>
						<td><form:label path="dataFineValidita"><spring:message code="dataFineValidita"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="dataFineValidita" maxlength="10" size="12"/>
			        		<form:errors path="dataFineValidita" class="testoRosso"/>
			        	</td>
			        </tr>			    	
				</table>

				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" name="SalvaModifica" title="Modifica stato" value="Salva"></td>
						<td class="td_tasto"><input type="submit" name="Ricerca" title="Torna alla ricerca degli stati" value="Indietro"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
				
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>