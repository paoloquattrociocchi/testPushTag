<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			
  			<c:if test="${ricerche=='true'}">
	  			<spring:url var="azione" value="/ricerche/soggetto/ricercaSoggettoGiuridico.htm" />
			</c:if> 	
            <c:if test="${ricerche!='true'}">
				<spring:url var="azione" value="/procedimento/nuovo/ricercaSoggettoGiuridico.htm" />         	
            </c:if>			
			
			<form:form method="post" action="${azione}" commandName="soggettoForm">
				<form:hidden path="descrRuolo" />
				<form:hidden path="idRuolo" />
				<p class="titolo_page">
					<logic:present name="procedimento">
					<spring:message code="soggettogiuridico.titolo" />				
				</logic:present>
				<logic:notPresent name="procedimento">
						<c:if test="${sessionScope.TIPO_FASCICOLO==2}">
							<spring:message code="procedimento.titoloAccertamento" />&nbsp;-&nbsp;
						</c:if>
						<c:if test="${sessionScope.TIPO_FASCICOLO==1}">
							<spring:message code="procedimento.titolo" />&nbsp;-&nbsp;
						</c:if>
					 <spring:message code="soggettogiuridico.titolo" />				
				</logic:notPresent>	
				</p>
				<logic:present name="procedimento">
					<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
					 <br/>
					 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				</logic:present>
				<br/>				
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
						<td><form:label path="stato.id" ><spring:message code="stato"/></form:label></td>
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
								<div id="id_loadingComune"></div>
								<form:hidden id="id_hiddenComuneSedeLegale" path="comuneSedeLegale.id"/>
							</div>
				        </td>						
					</tr>
				</table>
				<br/><br/>
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto">
							<input type="submit" name="Ricerca" title="Ricerca soggetto giuridico" value="Ricerca" 
								onclick="return submitWithAutocompleteComuni(['StatoSedeLegale'],['ComuneSedeLegale'],'search')"/>
						</td>
						<logic:present name="indietroTerzoSoggetto" scope="session">
                     		<td class="td_tasto">
                     			<input id="backButton" type="button" value="Indietro" title="Indietro">
                     		</td>
                     	</logic:present>
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
					
					<form:hidden path="idSoggetto" id="idSoggetto"/>
					<form:hidden path="idVersione" id="idVersione"/>
					<form:hidden path="tipoSoggetto" id="tipoSoggetto" value="G" />	
								
					<br />
					<br />
					<table class="tb_bottoni" align="center">
						<tr>
						<logic:notEmpty name="risultatiRicercaSoggettoGiuridico">
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
								<td class="td_tasto"><input type="submit" value="Nuovo soggetto" name="NuovoSoggetto" title="Nuovo soggetto giuridico"></td>
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

				if($( "#id_StatoSedeLegale" ).val() == 1)
				{
					doAutocomplete('ComuneSedeLegale');
				}
			</script>				
		</tiles:putAttribute>
	</tiles:insertDefinition>