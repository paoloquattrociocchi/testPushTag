<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
			<p class="titolo_page">
				<c:if test="${idTipoProc == 10}">
					<spring:message code="procedimento.riesameProvvedimentiQuestore.titoloRicercaSoggetto" />
				</c:if>
				<logic:present name="procedimento">
					<spring:message code="soggettofisico.titolo" />			
				</logic:present>
				<logic:notPresent name="procedimento">
						<c:if test="${sessionScope.TIPO_FASCICOLO==4}">
							<spring:message code="procedimento.riesameProvvedimentiQuestore.dettaglio" />&nbsp;-&nbsp;
						</c:if>
						<c:if test="${sessionScope.TIPO_FASCICOLO==2}">
							<spring:message code="procedimento.titoloAccertamento" />&nbsp;-&nbsp;
						</c:if>
						<c:if test="${sessionScope.TIPO_FASCICOLO==1}">
							<spring:message code="procedimento.titolo" />&nbsp;-&nbsp;
						</c:if>
					 <spring:message code="soggettofisico.titolo" />				
				</logic:notPresent>		
			</p>
			
  			<c:if test="${ricerche=='true'}">
	  			<spring:url var="azione" value="/ricerche/soggetto/ricercaSoggettoFisico.htm" />
			</c:if> 	
            <c:if test="${ricerche!='true'}">
				<spring:url var="azione" value="/procedimento/nuovo/ricercaSoggettoFisico.htm" />            	
            </c:if>
			
			<form:form id="id_formRicerca" method="post" action="${azione}" commandName="soggettoForm">
				<form:hidden path="descrRuolo" />
				<form:hidden path="idRuolo" />
				<logic:present name="procedimento">
					<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
					 <br/>
					    <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
					 <br/>					
				</logic:present>
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="cognome"><spring:message code="cognome"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="cognome" maxlength="35" size="30"/>
			        		<form:errors path="cognome" class="testoRosso"/>
			        	</td>
			        	<td><form:label path="nome"><spring:message code="nome"/></form:label></td>
			        	<td>
			        		<form:input path="nome" maxlength="35" size="30"/>
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
						<td><form:label path="statoNascita.id" ><spring:message code="stato"/></form:label></td>
			        	<td>
							<form:select id="id_StatoNascita" path="statoNascita.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneNascita')">	
								<form:option value="-1" label="-------------------------Seleziona Stato-----------------------"/>
								<form:options items="${statiNascita}"  itemValue="id" itemLabel="descrizione" /> 																
							</form:select>
			        	</td>
						<td><form:label path="comuneNascita.id" ><spring:message code="comune"/></form:label></td>
			        	<td>
			        		<div class="ui-widget">		
								<form:input id="id_descrizioneComuneNascita" path="comuneNascita.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComuneNascita"></div>
								<form:hidden id="id_hiddenComuneNascita" path="comuneNascita.id"/>
							</div>
			        	</td>								
					</tr>
					<tr>	
			        	<td><form:label path="codiceFiscale" ><spring:message code="codiceFiscale"/></form:label></td>
			        	<td>
			        		<form:input path="codiceFiscale" maxlength="16" size="25"/>
			        		<form:errors path="codiceFiscale" class="testoRosso"/>
			        	</td>
			        	<td><form:label path="cui"><spring:message code="soggettofisico.cui"/></form:label></td>
			        	<td><form:input path="cui" maxlength="7" size="10"/>
					</tr>
				</table>
				<br/>
				<table class="tb_bottoni" align="center">
                 	<tr>
                     	<td class="td_tasto">
                     		<input type="submit" name="Ricerca" title="Ricerca soggetto fisico" value="Ricerca" 
                     				onclick="return submitWithAutocompleteComuni(['StatoNascita'],['ComuneNascita'],'search')"/>
   						</td>
                     	<logic:present name="indietroTerzoSoggetto" scope="session">
                     		<td class="td_tasto"><input type="button" name="Indietro" id="backButton" title="Indietro" value="Indietro"/></td>
                     	</logic:present>
					</tr>
				</table>
				<br/>
				<logic:present name="risultatiRicerca">							 	
					<display:table class="tb_lista" name="${risultatiRicerca}" uid="sogg"  pagesize="10" requestURI="${azione}" > 
							<display:column style="text-align: center;">
								<input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}" onclick="selectID('${sogg.idSoggetto}', '${sogg.idVersione}', 'F');" />
							</display:column>
							<display:column property="cognome" title="Cognome" sortable="false"/>
							<display:column property="nome" title="Nome" sortable="false"/>  
							<display:column property="dataNascita" title="Data di nascita" format="{0,date,dd/MM/yyyy}" sortable="false"/>
							<display:column property="statoNascita.descrizione" title="Stato" sortable="false"/>
							<display:column property="descrizioneComuneNascitaItalianoEstero" title="Comune di nascita" sortable="false"/> 
							<display:column property="codiceFiscale" title="Codice Fiscale" sortable="false"/>
							<display:column property="cui" title="CUI" sortable="false"/>
						
						</display:table>	 
						
						<form:hidden path="idSoggetto" id="idSoggetto"/>
						<form:hidden path="idVersione" id="idVersione"/>
						<form:hidden path="tipoSoggetto" id="tipoSoggetto" value="F" />
						
					<br/><br/>
					<table class="tb_bottoni" align="center">
                 		<tr>
                 			<logic:notEmpty name="risultatiRicerca">
	                 			<c:if test="${ricerche=='true'}">
									<td class="td_tasto"><input type="submit" value="Dettaglio" name="DettaglioSoggetto"></td>
	                 			</c:if> 	
	                 			<c:if test="${ricerche!='true'}">
		                     		<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio"></td>
		                     		<td class="td_tasto"><input type="submit" value="Procedimenti collegati" name="ProcedimentiCollegati"></td>
		                     		<td class="td_tasto"><input type="submit" value="Associa" name="Associa"></td>
	                     		</c:if>
                     		</logic:notEmpty>
                     		<c:if test="${ricerche!='true'}">
                     			<td class="td_tasto"><input type="submit" value="Nuovo soggetto" name="NuovoSoggetto" title="Nuovo soggetto fisico"></td>
                   			</c:if>
                     		
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
					// focus on the first text input field in the first field on the page
        			$("input[type='text']:first", document.forms[0]).focus();
        			inizializzaSubmitAutocomplete('Ricerca');
					controllaInizializzazioneCampi($( "#id_StatoNascita" ).val(),"ComuneNascita");
				});

				function selectID(idSoggetto, idVersione, tipoSoggetto) {
					document.getElementById("idSoggetto").value = idSoggetto;
					document.getElementById("idVersione").value = idVersione;
					document.getElementById("tipoSoggetto").value= tipoSoggetto;
				}

				if($( "#id_StatoNascita" ).val() == 1)
				{
					doAutocomplete('ComuneNascita');
				}
			</script>

		</tiles:putAttribute>
	</tiles:insertDefinition>
