<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/funzioniAmministrazione/visualizzaRicercaSpeseProcessuali.htm" />
			<p class="titolo_page">
				<spring:message code="spese.processuali.ricerca" />		
			</p>
			
			<form:form id="id_formRicerca" method="post" action="${azione}" commandName="speseProcessualiForm">
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0" >
					<tr>
						<td><form:label path="importoSpese"><spring:message code="importo.spese.processuali.euro"/></form:label>       		        	   
			        		<form:input path="importoSpese" maxlength="15" size="20"/>
			        		<form:errors path="importoSpese" class="testoRosso"/>
			        	</td>
			    	</tr>
			    	
			 	</table>
				<br/>
				<table class="tb_bottoni" align="center">
                 	<tr>
                     	<td class="td_tasto">
                     		<input type="submit" name="Ricerca" title="Ricerca" value="Ricerca"/>
   						</td>
                    </tr>
				</table>
				<br/>
				<logic:present name="risultatiRicerca">							 	
					<display:table class="tb_lista" name="${risultatiRicerca}" uid="risultatiRicerca"  pagesize="10" requestURI="${azione}" >
						<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
							<form:radiobutton path="codiKIdSpeseProcessuali" value="${risultatiRicerca.codiKIdSpeseProcessuali}" 
										      disabled="${risultatiRicerca.dataFineValidita != null }" />
						</display:column> 
						<display:column property="importoSpese" title="Importo Spese Processuali" sortable="false"/>
						<display:column property="descImportoSpese" title="Descrizione importo Spese Processuali" sortable="false"/>  
						<display:column property="dataInizioValidita" title="Data Inizio Validità" format="{0,date,dd/MM/yyyy}" sortable="false"/>
						<display:column property="dataFineValidita" title="Data Fine Validità" format="{0,date,dd/MM/yyyy}" sortable="false"/>
					</display:table>
						
					<br/><br/>
					<table class="tb_bottoni" align="center">
                 		<tr>
                 	 		<td class="td_tasto"><input type="submit" value="Nuovo" name="NuovoImportoSpese" title="Nuovo"></td>
                 	 		<td class="td_tasto"><input type="submit" value="Modifica" name="ModificaImportoSpese" title="Modifica"></td>
                     	</tr>
					</table>
					<br />
					<br />
					
			</logic:present> 
			</form:form>
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>


		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>
