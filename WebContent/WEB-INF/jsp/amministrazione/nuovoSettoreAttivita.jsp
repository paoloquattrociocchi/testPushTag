<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/funzioniAmministrazione/nuovoSettoreAttivita.htm" />
			<p class="titolo_page">
				<spring:message code="amministrazione.settore.inserisci.titolo"/>		
			</p>
			<form:form method="post" action="${azione}" commandName="settoreAttivitaForm" id="idForm">
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="descSettoreAtt" cssClass="required"><spring:message code="amministrazione.settore.descrizione"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="descSettoreAtt" maxlength="80" size="80"/>
			        		<form:errors path="descSettoreAtt" class="testoRosso"/>
			        	</td>
			        </tr>
			        <tr>
			        	<td><form:label path="descSezioneAteco"><spring:message code="amministrazione.settore.codice.ateco"/></form:label></td>
			        	<td>
			        		<form:input path="descSezioneAteco" maxlength="2" size="2"/>
			        		<form:errors path="descSezioneAteco" class="testoRosso"/>
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
						<td class="td_tasto"><input type="submit" name="Inserisci" title="Inserisci nuovo settore attivita" value="Inserisci"></td>
						<td class="td_tasto"><input type="submit" name="Indietro"  title="Torna alla ricerca settore attivita" value="Indietro"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
			
				
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>