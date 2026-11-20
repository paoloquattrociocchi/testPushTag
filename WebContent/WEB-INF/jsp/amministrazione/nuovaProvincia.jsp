<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/funzioniAmministrazione/nuovaProvincia.htm" />
			<p class="titolo_page">
				<spring:message code="amministrazione.province.inserisci.titolo"/>		
			</p>
			<form:form method="post" action="${azione}" commandName="provinciaForm" id="idForm">
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
			    		<td><form:label path="codiKIdIstatProv" cssClass="required"><spring:message code="amministrazione.province.codiKIdIstatProv"/></form:label></td>
			        	<td>
			        		<form:input path="codiKIdIstatProv" maxlength="3" size="3"/>
			        		<form:errors path="codiKIdIstatProv" class="testoRosso"/>
						</td>
			    	</tr>
					<tr>
						<td><form:label path="descProvincia" cssClass="required"><spring:message code="amministrazione.province.descrizioneProvincia"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="descProvincia" maxlength="120" size="120"/>
			        		<form:errors path="descProvincia" class="testoRosso"/>
			        	</td>
			        </tr>
			        <tr>
			        	<td><form:label path="descSiglaProvincia" cssClass="required"><spring:message code="amministrazione.province.siglaProvincia"/></form:label></td>
			        	<td>
			        		<form:input path="descSiglaProvincia" maxlength="2" size="2"/>
			        		<form:errors path="descSiglaProvincia" class="testoRosso"/>
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
						<td class="td_tasto"><input type="submit" name="Inserisci" title="Inserisci nuova provincia" value="Inserisci"></td>
						<td class="td_tasto"><input type="submit" name="Indietro"  title="Torna alla ricerca province" value="Indietro"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
			
				
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>