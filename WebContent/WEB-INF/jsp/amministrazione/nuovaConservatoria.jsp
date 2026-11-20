<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/funzioniAmministrazione/nuovaConservatoria.htm" />
			<p class="titolo_page">
				<spring:message code="amministrazione.conservatorie.inserisci.titolo"/>		
			</p>
			<form:form method="post" action="${azione}" commandName="conservatoriaForm" id="idForm">
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="descConserv" cssClass="required"><spring:message code="amministrazione.conservatorie.descrizione"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="descConserv" maxlength="100" size="100"/>
			        		<form:errors path="descConserv" class="testoRosso"/>
			        	</td>
			        </tr>
			        <tr>
			        	<td><form:label path="descCodiConserv" cssClass="required"><spring:message code="amministrazione.conservatorie.codice.conservatoria"/></form:label></td>
			        	<td>
			        		<form:input path="descCodiConserv" maxlength="4" size="4"/>
			        		<form:errors path="descCodiConserv" class="testoRosso"/>
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
						<td class="td_tasto"><input type="submit" name="Inserisci" title="Inserisci nuova conservatoria" value="Inserisci"></td>
						<td class="td_tasto"><input type="submit" name="Indietro"  title="Torna alla ricerca conservatoria" value="Indietro"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
			
				
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>