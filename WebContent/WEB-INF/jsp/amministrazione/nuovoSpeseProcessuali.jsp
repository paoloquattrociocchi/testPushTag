<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/funzioniAmministrazione/nuovoSpeseProcessuali.htm" />
			<p class="titolo_page">
				<spring:message code="spese.processuali.inserimento" />		
			</p>
			<form:form method="post" action="${azione}" commandName="speseProcessualiForm" id="idForm">
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="importoSpese" cssClass="required"><spring:message code="importo.spese.processuali"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="importoSpese" maxlength="15" size="20"/>
			        		<form:errors path="importoSpese" class="testoRosso"/>
			        		
			        	</td>
			        </tr>
			        <tr>
						<td><form:label path="descImportoSpese" ><spring:message code="descrizione.importo.spese.processuali"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="descImportoSpese" maxlength="120" size="60"/>
			        	</td>
			        </tr>
				    <tr>
						<td><form:label path="dataInizioValidita" cssClass="required"><spring:message code="dataInizioValidita"/></form:label></td>
				        <td>
				       		 <div>
								<form:input path="dataInizioValidita" maxlength="10" size="12" id="dataInizioValidita"/>
								<form:errors path="dataInizioValidita" class="testoRosso" />
									
							</div>		        	   
				        	
				        <%-- <td>
				        	<fmt:formatDate value="${speseProcessualiForm.dataInizioValidita}" type="date" pattern="dd/MM/yyyy" />
							<form:hidden path="dataInizioValidita" />
						</td> --%>
						
				    </tr>
				   
				</table>
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" name="Inserisci" title="Inserisci nuovo importo spese" value="Inserisci"></td>
						<td class="td_tasto"><input type="button" value="Indietro"  title="Torna alla ricerca importo spese" onclick="indietro('/funzioniAmministrazione/visualizzaRicercaSpeseProcessuali.htm');"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
			
			<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataInizioValidita",false);
				});
				
			</script>
			
			
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>