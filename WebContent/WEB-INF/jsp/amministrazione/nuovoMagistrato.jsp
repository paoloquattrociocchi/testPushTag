<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/funzioniAmministrazione/nuovoMagistrato.htm" />
			<p class="titolo_page">
				<spring:message code="magistrato.titolo.inserisci" />		
			</p>
			<form:form method="post" action="${azione}" commandName="magistratoForm" id="idForm">
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="cognome" cssClass="required"><spring:message code="cognome" /></form:label></td>
						<td><form:input path="cognome" maxlength="35" size="30"/>
							<form:errors path="cognome" class="testoRosso"/>
						</td>
						<td><form:label path="nome" cssClass="required"><spring:message code="nome" /></form:label></td>
						<td><form:input path="nome" maxlength="35" size="30"/>
							<form:errors path="nome" class="testoRosso"/>
						</td>
					</tr>
					<tr>
						<td><form:label path="dataNascita"><spring:message code="dataNascita" /></form:label></td>
						<td>
							<div>
								<form:input path="dataNascita" maxlength="10" size="12" id="dataNascita"/>
								<form:errors path="dataNascita" class="testoRosso" />
							</div>
						</td>
					</tr>
					<tr>
			    		<td><form:label path="numeMatricola"><spring:message code="magistrato.matricola"/></form:label></td>
			        	<td>
			        		<form:input path="numeMatricola" maxlength="6" size="6"/>
			        		<form:errors path="numeMatricola" class="testoRosso"/>
						</td>
			    	</tr>
				</table>

				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" name="Inserisci" title="Inserisci nuovo magistrato" value="Inserisci"></td>
						<td class="td_tasto"><input type="submit" value="Indietro" name="Indietro" title="Torna alla ricerca magistrati"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
			
			
			<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataNascita",true);
				});
			</script>	
							
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>