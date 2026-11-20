<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
			<p class="titolo_page">
				<spring:message code="amministrazione.stati.ricerca.titolo" />		
			</p>
			<spring:url var="azione" value="/funzioniAmministrazione/visualizzaRicercaStato.htm" />
			<form:form id="id_formRicerca" method="post" action="${azione}" commandName="statoForm">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="descStato"><spring:message code="descrizioneStato"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="descStato" maxlength="50" size="50"/>
			        		<form:errors path="descStato" class="testoRosso"/>
			        	</td>
			        </tr>
			        <tr>
			        	<td><form:label path="descSiglaStato"><spring:message code="siglaStato"/></form:label></td>
			        	<td>
			        		<form:input path="descSiglaStato" maxlength="3" size="3"/>
			        		<form:errors path="descSiglaStato" class="testoRosso"/>
						</td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="descIstatStato"><spring:message code="istatStato"/></form:label></td>
			        	<td>
			        		<form:input path="descIstatStato" maxlength="3" size="3"/>
			        		<form:errors path="descIstatStato" class="testoRosso"/>
						</td>
			    	</tr>
			  </table>
				<br/>
				<table class="tb_bottoni" align="center">
                 	<tr>
                     	<td class="td_tasto">
                     		<input type="submit" name="Ricerca" title="Ricerca stato" value="Ricerca"/>
   						</td>
                    </tr>
				</table>
				<br/>
				<logic:present name="risultatiRicerca">							 	
					<display:table class="tb_lista" name="${risultatiRicerca}" uid="risultatiRicerca"  pagesize="10" requestURI="${azione}" >
						<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
							<form:radiobutton path="codiKIdStato" value="${risultatiRicerca.codiKIdStato}" />
						</display:column>
						<display:column property="descStato" title="Descrizione" sortable="false"/>
						<display:column property="descSiglaStato" title="Sigla" sortable="false"/>  
						<display:column property="descIstatStato" title="Codice Istat" sortable="false"/>
						<display:column property="dataFineValidita" title="Validità" format="{0,date,dd/MM/yyyy}" sortable="false"/>
					</display:table>
						
					<br/><br/>
					<table class="tb_bottoni" align="center">
                 		<tr>
                 	 		<td class="td_tasto"><input type="submit" value="Nuovo" name="NuovoStato" title="Nuovo"></td>
                 	 		<td class="td_tasto"><input type="submit" value="Modifica" name="ModificaStato" title="Modifica"></td>
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
