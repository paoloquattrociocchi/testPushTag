<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/funzioniAmministrazione/modificaSpeseProcessuali.htm" />
			<p class="titolo_page">
				<spring:message code="spese.processuali.modifica" />		
			</p>
			<form:form method="post" action="${azione}" commandName="speseProcessualiForm" id="idForm">
				<br/>
				<form:hidden path="codiKIdSpeseProcessuali" />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="importoSpese"><spring:message code="importo.spese.processuali"/></form:label></td>
			        	<td>
			        		<c:out value="${speseProcessualiForm.importoSpese }"></c:out>  
			        	</td>
			        </tr>
			        <tr>
						<td><form:label path="dataInizioValidita"><spring:message code="dataInizioValidita"/></form:label></td>
			        	<td>
							<fmt:formatDate value="${speseProcessualiForm.dataInizioValidita }" var="dateFormatted" pattern="dd/MM/yyyy"/>
							<c:out value="${dateFormatted}" />
			        		
			        	</td>
			        </tr>
			        <tr>
						<td><form:label path="descImportoSpese" ><spring:message code="descrizione.importo.spese.processuali"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="descImportoSpese" maxlength="120" size="60"/>
			        	</td>
				        </tr>
					    <tr>
							<td><form:label path="dataFineValidita" ><spring:message code="dataFineValidita"/></form:label></td>
					        <td>
				       		 <div>
								<form:input path="dataFineValidita" maxlength="10" size="12" id="dataFineValidita"/>
								<form:errors path="dataFineValidita" class="testoRosso" />
							</div>		        	   
						</td>
						
				    	</tr>
				</table>

				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" name="Salva" title="Modifica" value="Salva"></td>
						<td class="td_tasto"><input type="button" value="Indietro" title="Torna alla ricerca" onclick="indietro('/funzioniAmministrazione/visualizzaRicercaSpeseProcessuali.htm?importoSpese=${importoSpeseSearched }');"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
			
			
			<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataFineValidita",false);
				});
			</script>
			
				
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>