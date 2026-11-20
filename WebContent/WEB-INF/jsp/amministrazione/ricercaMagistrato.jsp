<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
			<p class="titolo_page">
				<spring:message code="magistrato.titolo.ricerca.inserimento" />		
			</p>
			<spring:url var="azione" value="/funzioniAmministrazione/visualizzaRicercaMagistrato.htm" />
			<form:form id="id_formRicerca" method="post" action="${azione}" commandName="magistratoForm">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="cognome"><spring:message code="cognome"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="cognome" maxlength="35" size="35"/>
			        		<form:errors path="cognome" class="testoRosso"/>
			        	</td>
			        	<td><form:label path="nome"><spring:message code="nome"/></form:label></td>
			        	<td>
			        		<form:input path="nome" maxlength="35" size="35"/>
			        		<form:errors path="nome" class="testoRosso"/>
						</td>
			    	</tr>
			    	<tr>
			        	<td><form:label path="dataNascita" ><spring:message code="dataNascita"/></form:label></td>
			        	<td colspan="3">		        		
			        		<form:input path="dataNascita" maxlength="10" size="12" id="dataNascita"/>
			        		<form:errors path="dataNascita" class="testoRosso"/>			        		
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
				<br/>
				<table class="tb_bottoni" align="center">
                 	<tr>
                     	<td class="td_tasto">
                     		<input type="submit" name="Ricerca" title="Ricerca magistrato" value="Ricerca"/>
   						</td>
                    </tr>
				</table>
				<br/>
				<logic:present name="risultatiRicerca">							 	
					<display:table class="tb_lista" name="${risultatiRicerca}" uid="risultatiRicerca"  pagesize="10" requestURI="${azione}" >
						<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
							<form:radiobutton path="id" value="${risultatiRicerca.id}" />
						</display:column> 
						<display:column property="cognome" title="Cognome" sortable="false"/>
						<display:column property="nome" title="Nome" sortable="false"/>  
						<display:column property="dataNascita" title="Data di nascita" format="{0,date,dd/MM/yyyy}" sortable="false"/>
						<display:column property="numeMatricola" title="Matricola" sortable="false"/>
					</display:table>
						
					<br/><br/>
					<table class="tb_bottoni" align="center">
                 		<tr>
                 	 		<td class="td_tasto"><input type="submit" value="Nuovo" name="NuovoMagistrato" title="Nuovo"></td>
                 	 		<td class="td_tasto"><input type="submit" value="Modifica" name="ModificaMagistrato" title="Modifica"></td>
                     	</tr>
					</table>
					<br />
					<br />
					
			</logic:present>
			</form:form>
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>


			<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataNascita",true);
				});
			</script>

		</tiles:putAttribute>
	</tiles:insertDefinition>
