<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.ricerca.terzi" /></p>
		<spring:url var="azione" value="/procedimento/nuovo/terzi/ricerca.htm" />
		<spring:url var="azione2" value="/procedimento/nuovo/terzi/paginazioneRicerca.htm" />
					
		<form:form id="id_terzi" method="post" action="${azione}" commandName="form">
			<%-- CAMPI DI HIDDEN --%>
			<form:hidden path="soggettoForm.idSoggetto" id="idSoggetto"/>
			<form:hidden path="soggettoForm.idVersione" id="idVersione"/>
			<form:hidden path="soggettoForm.tipoSoggetto" id="tipoSoggetto" />
			<form:hidden path="tipoSoggetto" />
			
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td colspan="6" align="left"><spring:message code="cameralini.dettaglioProcedimento" /></td>
				</tr>
	  			<jsp:include page="/WEB-INF/jsp/cameralini/intestazioneDettaglio.jsp" />
		     </table>
			
			 <%-- INIZIO PERSONA FISICA --%>
			 <c:if test="${form.tipoSoggetto=='F'}" >
				 <p class="titolo_page">
				 	<spring:message code="soggettofisico.titolo" />
				 </p>
				 	<fmt:formatDate pattern="dd/MM/yyyy" value="${form.dataNascita}" var="myDateVar"/> 
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
							 <td><form:label path="dataNascita"> <spring:message code="dataNascita" /> </form:label></td>
							 <td colspan="3"><form:input path="dataNascita" maxlength="10" size="12" id="dataNascita" value="${myDateVar}" />
								<form:errors path="dataNascita" class="testoRosso" /> 
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
				        	<td colspan="3">
				        		<form:input path="codiceFiscale" maxlength="16" size="25"/>
				        		<form:errors path="codiceFiscale" class="testoRosso"/>
				        	</td>
						</tr>
					</table>
					<br/>
					<table class="tb_bottoni" align="center">
	                 	<tr>
	                     	<td class="td_tasto">
	                     		<input type="submit" name="RicercaSoggettoFisico" title="Ricerca soggetto fisico" value="Ricerca" 
	                     				onclick="return submitWithAutocompleteComuni(['StatoNascita'],['ComuneNascita'],'search')"/>
	   						</td>
	                     	<td class="td_tasto"><input type="submit" value="Indietro" name="IndietroRicerca" title="Indietro" /></td>
						</tr>
					</table>
					<br/>
					<logic:present name="risultatiRicerca">
						<display:table class="tb_lista" name="${risultatiRicerca}" uid="sogg"  pagesize="10" requestURI="${azione2}" > 
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
						<br/><br/>
						<table class="tb_bottoni" align="center">
	                 		<tr>
	                 			<logic:notEmpty name="risultatiRicerca">
			                     		<td class="td_tasto"><input type="submit" value="Dettaglio" name="DettaglioSoggettoFisico" ></td>
			                     		<td class="td_tasto"><input type="submit" value="Associa" name="Associa" ></td>
			                    </logic:notEmpty>
		                     	<td class="td_tasto"><input type="submit" value="Nuovo Soggetto Fisico" name="NuovoSoggettoFisico"></td>
                     		</tr>
						</table>
						
				</logic:present>
			</c:if>
			<%-- FINE PERSONA FISICA --%>
			
			<%-- INIZIO PERSONA GIURIDICA --%>
			 <c:if test="${form.tipoSoggetto=='G'}" >
				<p class="titolo_page">
					<spring:message code="soggettogiuridico.titolo" />
				</p>
			 	<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="ragioneSociale"><spring:message code="soggettogiuridico.ragioneSociale" /></form:label></td>
						<td colspan="3"><form:input path="ragioneSociale" maxlength="120" size="40"/></td>
					</tr>
					<tr>
						<td><form:label path="partitaIva"><spring:message code="soggettogiuridico.partitaIva" /></form:label></td>
						<td colspan="3">
							<form:select id="id_StatoSiglaPiva" path="partitaIvaSiglaStato" size="1" >
								<form:option value="" label="  "/>
								<form:options items="${statiPiva}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							
							<form:input path="partitaIva" maxlength="12" size="18"/>
							<form:errors path="partitaIva" class="testoRosso"/>
						</td>
					</tr>
					<tr><td class="td_titolo" colspan="4"><spring:message code="soggetto.sedeLegale" /></td></tr>
					<tr>
						<td><form:label path="statoSedeLegale.id" ><spring:message code="stato"/></form:label></td>
						<td>
							<form:select id="id_StatoSedeLegale"  path="statoSedeLegale.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneSedeLegale')" >
								<form:option value="-1" label="-------------------------Seleziona Stato-----------------------"/>
								<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>						
						<td width="12%"><form:label path="comuneSedeLegale.id" ><spring:message code="comune"/></form:label></td>
				        <td>
				        	<div class="ui-widget">		
								<form:input id="id_descrizioneComuneSedeLegale" path="comuneSedeLegale.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
								<div id="id_loadingComuneSedeLegale"></div>
								<form:hidden id="id_hiddenComuneSedeLegale" path="comuneSedeLegale.id"/>
							</div>
				        </td>	
				        					
					</tr>
				</table>
				<br/><br/>
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto">
							<input type="submit" name="RicercaSoggettoGiuridico" title="Ricerca soggetto giuridico" value="Ricerca" 
								onclick="return submitWithAutocompleteComuni(['StatoSedeLegale'],['ComuneSedeLegale'],'search')"/>
						</td>
						<td class="td_tasto"><input type="submit" value="Indietro" name="IndietroRicerca" title="Indietro" /></td>
					</tr>
				</table>
				<br/>
				<br/>
				
				<logic:present name="risultatiRicercaSoggettoGiuridico">
					<display:table class="tb_lista" name="${risultatiRicercaSoggettoGiuridico}" uid="sogg"
						pagesize="10" requestURI="${azione}" >
							<display:column style="text-align: center;">
								<input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}" onclick="selectID('${sogg.idSoggetto}', '${sogg.idVersione}', 'G');" />
							</display:column>
						<display:column property="ragioneSociale" sortable="false" paramId="idSoggetto" paramProperty="idSoggetto" title="Denominazione/Ragione sociale" />
						<display:column property="partitaIvaCompleta" sortable="false" paramId="idSoggetto" paramProperty="idSoggetto" title="Partita IVA" />
						<display:column property="statoSedeLegale.descrizione" sortable="false" paramId="idSoggetto" paramProperty="idSoggetto" title="Stato" />
						<display:column property="descrizioneComuneSedeLegaleItalianoEstero" sortable="false" paramId="idSoggetto" paramProperty="idSoggetto" title="Comune Sede Legale" />
					</display:table>
								
					<br />
					<br />
					<table class="tb_bottoni" align="center">
						<tr>
						<logic:notEmpty name="risultatiRicercaSoggettoGiuridico">
							<td class="td_tasto"><input type="submit" value="Dettaglio" name="DettaglioSoggettoGiuridico"></td>
							<td class="td_tasto"><input type="submit" value="Associa" name="Associa"></td>
						</logic:notEmpty>
                     	<td class="td_tasto"><input type="submit" value="Nuovo Soggetto Giuridico" name="NuovoSoggettoGiuridico"></td>
						</tr>
					</table>
					<br />
					<br />
				</logic:present>
			 </c:if>
			 <%-- FINE PERSONA GIURIDICA --%>
			
			<c:if test="${form.tipoSoggetto=='F'}" >
				<script type="text/javascript">
						$(document).ready(function() {
							prendiData("#dataNascita",true);
							$("input[type='text']:first", document.forms[0]).focus();
							inizializzaSubmitAutocomplete('Ricerca');
							controllaInizializzazioneCampi($( "#id_StatoNascita" ).val(),"ComuneNascita");
						});
			
						function selectID(idSoggetto, idVersione, tipoSoggetto) {
							document.getElementById("idSoggetto").value = idSoggetto;
							document.getElementById("idVersione").value = idVersione;
							document.getElementById("tipoSoggetto").value= tipoSoggetto;
						}	
					
				</script>
				
				<script type="text/javascript">
					if($( "#id_StatoNascita" ).val() == 1)
					{
						doAutocomplete('ComuneNascita');
					}
				</script>
			</c:if>
			
			<c:if test="${form.tipoSoggetto=='G'}" >
				<script type="text/javascript">
				$(document).ready(function() {
					// focus on the first text input field in the first field on the page
					$("input[type='text']:first", document.forms[0]).focus();
					inizializzaSubmitAutocomplete('Ricerca');
					controllaInizializzazioneCampi($( "#id_StatoSedeLegale" ).val(),"ComuneSedeLegale");
				});
			
				function selectID(idSoggetto, idVersione, tipoSoggetto) {
					document.getElementById("idSoggetto").value = idSoggetto;
					document.getElementById("idVersione").value = idVersione;
					document.getElementById("tipoSoggetto").value= tipoSoggetto;
				}
				
				</script>
				
				<script type="text/javascript">
				
					if($( "#id_StatoSedeLegale" ).val() == 1)
					{
						doAutocomplete('ComuneSedeLegale');
					}
				</script>
			</c:if>
			</form:form>
						
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>