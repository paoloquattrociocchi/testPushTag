<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/funzioniAmministrazione/nuovaCatCatastale.htm" />
			<p class="titolo_page">
				<spring:message code="amministrazione.catCatastali.inserisci.titolo"/>		
			</p>
			<form:form method="post" action="${azione}" commandName="categoriaCatastaleForm" id="idForm">
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="descCategoriaCat" cssClass="required"><spring:message code="amministrazione.catCatastali.descrizione"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="descCategoriaCat" maxlength="80" size="80"/>
			        		<form:errors path="descCategoriaCat" class="testoRosso"/>
			        	</td>
			        </tr>
			        <tr>
			        	<td><form:label path="descBreveCategoriaCat" cssClass="required"><spring:message code="amministrazione.catCatastali.descrbreve"/></form:label></td>
			        	<td>
			        		<form:input path="descBreveCategoriaCat" maxlength="4" size="4"/>
			        		<form:errors path="descBreveCategoriaCat" class="testoRosso"/>
						</td>
			    	</tr>
			       	<tr>
						<td><form:label path="dataFineValidita" cssClass="required"><spring:message code="dataFineValidita"/></form:label></td>
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
						<td class="td_tasto"><input type="submit" name="Inserisci" title="Inserisci nuova categoria catastale" value="Inserisci"></td>
						<td class="td_tasto"><input type="submit" name="Indietro"  title="Torna alla ricerca categorie catastali" value="Indietro"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
			
				
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>