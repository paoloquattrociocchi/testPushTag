<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
			<p class="titolo_page">
				<spring:message code="amministrazione.istitutoDet.ricerca.titolo" />		
			</p>
			<spring:url var="azione" value="/funzioniAmministrazione/visualizzaRicercaIstDetenzione.htm" />
			<form:form id="id_formRicerca" method="post" action="${azione}" commandName="istitutoDetenzioneForm">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
			        <tr>
			        	<td><form:label path="itemTipoIstituto.id"><spring:message code="amministrazione.istitutoDet.tipoIstituto"/></form:label></td>
			        		<td>
								<form:select path="itemTipoIstituto.id" size="1">	
									<form:option value="-1" label="------ Seleziona Tipo Istituto -------" />
									<form:options items="${elencoTipoIstituto}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>	
								<form:errors path="itemTipoIstituto.id" class="testoRosso"/>
							</td>
						</td>
			    	</tr>
			        <tr>
			        	<td><form:label path="itemComune.id"><spring:message code="amministrazione.istitutoDet.comune"/></form:label></td>
			        		<td>
								<form:select path="itemComune.id" size="1">	
									<form:option value="-1" label="------ Seleziona Comune -------" />
									<form:options items="${elencoComuni}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>	
								<form:errors path="itemComune.id" class="testoRosso"/>
							</td>
						</td>
			    	</tr>
			  </table>
				<br/>
				<table class="tb_bottoni" align="center">
                 	<tr>
                     	<td class="td_tasto">
                     		<input type="submit" name="Ricerca" title="Ricerca istituto detenzione" value="Ricerca"/>
   						</td>
                    </tr>
				</table>
				<br/>
				<logic:present name="risultatiRicerca">							 	
					<display:table class="tb_lista" name="${risultatiRicerca}" uid="risultatiRicerca"  pagesize="10" requestURI="${azione}" >
						<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
							<form:radiobutton path="codiKIdIstituto" value="${risultatiRicerca.codiKIdIstituto}" />
						</display:column>
						<display:column property="descIstituto" title="Descrizione Istituto" sortable="false"/>
						<display:column property="descIndirizzo" title="Indirizzo Istituto" sortable="false"/>  
						<display:column property="dataFineValidita" title="Validità" format="{0,date,dd/MM/yyyy}" sortable="false"/>
					</display:table>
						
					<br/><br/>
					<table class="tb_bottoni" align="center">
                 		<tr>
                 	 		<td class="td_tasto"><input type="submit" value="Nuovo" name="NuovoIstitutoDet" title="Nuovo"></td>
                 	 		<td class="td_tasto"><input type="submit" value="Modifica" name="ModificaIstitutoDet" title="Modifica"></td>
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
