<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/funzioniAmministrazione/nuovoComune.htm" />
			<p class="titolo_page">
				<spring:message code="amministrazione.comuni.inserisci.titolo" />		
			</p>
			<form:form method="post" action="${azione}" commandName="comuneForm" id="idForm">
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="descComune" cssClass="required"><spring:message code="amministrazione.comuni.descrizioneComune"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="descComune" maxlength="120" size="60"/>
			        		<form:errors path="descComune" class="testoRosso"/>
			        	</td>
			        </tr>
			        <tr>
			        	<td><form:label path="itemProvincia.id" cssClass="required"><spring:message code="amministrazione.comuni.fkIdProvincia"/></form:label></td>
			        		<td>
								<form:select path="itemProvincia.id" size="1">	
									<form:option value="-1" label="------Seleziona Provincia-------" />
									<form:options items="${elencoProvince}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>	
								<form:errors path="itemProvincia.id" class="testoRosso"/>
							</td>
						</td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="descCapComune" cssClass="required"><spring:message code="amministrazione.comuni.CAPComune"/></form:label></td>
			        	<td>
			        		<form:input path="descCapComune" maxlength="5" size="5"/>
			        		<form:errors path="descCapComune" class="testoRosso"/>
						</td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="descIstatComune" cssClass="required"><spring:message code="amministrazione.comuni.codiceIstatComune"/></form:label></td>
			        	<td>
			        		<form:input path="descIstatComune" maxlength="6" size="6"/>
			        		<form:errors path="descIstatComune" class="testoRosso"/>
						</td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="descBelfiore" cssClass="required"><spring:message code="amministrazione.comuni.codiceBelfioreComune"/></form:label></td>
			        	<td>
			        		<form:input path="descBelfiore" maxlength="4" size="4"/>
			        		<form:errors path="descBelfiore" class="testoRosso"/>
						</td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="descCatasto" cssClass="required"><spring:message code="amministrazione.comuni.codiceCatastoComune"/></form:label></td>
			        	<td>
			        		<form:input path="descCatasto" maxlength="4" size="4"/>
			        		<form:errors path="descCatasto" class="testoRosso"/>
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
						<td class="td_tasto"><input type="submit" name="Inserisci" title="Inserisci nuovo Comune" value="Inserisci"></td>
						<td class="td_tasto"><input type="submit" name="Indietro"  title="Torna alla ricerca dei Comuni" value="Indietro"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
			
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>