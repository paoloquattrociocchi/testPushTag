<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">

		<p class="titolo_page"><spring:message code="conservatoria.titolo.modificaSoggetto" /></p>

		<spring:url var="azione" value="/conservatorie/soggettiComunicazione.htm" />
			<form:form id="id_formSoggetti" method="post" action="${azione}" commandName="comunicazioneSoggettiForm">
			
            <form:hidden path="numeroComunicazione" />
            <form:hidden path="stato" />
            <form:hidden path="descTipoNota" />
            <form:hidden path="flgTipoSoggetto" />
            
            <form:hidden path="soggettoDto.progressivo" />                     
            <form:hidden path="soggettoDto.tipologia" />
            <form:hidden path="soggettoDto.soggettoSitMp" />                     
            <form:hidden path="soggettoDto.flagErrore" />
            <form:hidden path="soggettoDto.codiceComuneNasc" />                     
            <form:hidden path="soggettoDto.codiceComuneSede" />                     
            <form:hidden path="soggettoDto.provNascita" />                     
            <form:hidden path="soggettoDto.provSede" />                     
            <form:hidden path="soggettoDto.regime" />    
            <form:hidden path="soggettoDto.codProgrSoggC" />    
            
            <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="15%" class="td_titolo"><form:label path="numeroComunicazione">
							<spring:message code="conservatoria.numeroComunicazione" />
					</form:label></td>
					<td width="15%" style="font-weight: bold" class="td_titolo"><c:out
							value="${comunicazioneSoggettiForm.numeroComunicazione}" /></td>
					<td align="right"  class="td_titolo"><form:label path="descTipoNota" >
							<spring:message code="conservatoria.tipo" />
						</form:label></td>
					<td align="left" style="font-weight: bold" class="td_titolo"><c:out
							value="${comunicazioneSoggettiForm.descTipoNota}" /></td>
				</tr>
			</table>
			<br />
			<br />
			<c:if test="${comunicazioneSoggettiForm.tipoOperazione=='Cancella'}">
                <script type="text/javascript"> $(document).ready(function() { disabilitaCampi(); });
			</script>
			</c:if>
    <%-- INIZIO MODIFICA PERSONA FISICA ------------------------------------------------     --%>
    <c:if test="${comunicazioneSoggettiForm.flgTipoSoggetto == 'F'}">
          <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
                 <tr>
                    <td class="td_titolo"><spring:message code="conservatoria.personaFisica" /></td>
                 </tr>
          </table>
          <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
              <tr>
                  <td><form:label path="soggettoDto.cognome" cssClass="required"> 
                      <spring:message code="conservatoria.cognome" /> </form:label></td>
                  <td>
                  <form:input path="soggettoDto.cognome" id="cognome" maxlength="50" size="52" 
                        value="${comunicazioneSoggettiForm.soggettoDto.cognome}"/></td>
                              
                  <td><form:label path="soggettoDto.nome" cssClass="required"> 
                      <spring:message code="conservatoria.nome" /> </form:label></td>
                  <td><form:input path="soggettoDto.nome" id="nome" maxlength="50" size="52" 
                        value="${comunicazioneSoggettiForm.soggettoDto.nome}"/></td>
              </tr>
              <tr>
                  <td><form:label path="soggettoDto.flgSesso" cssClass="required"> 
                      <spring:message code="conservatoria.sesso" /> </form:label></td>
                  <td>
	                  <form:select path="soggettoDto.flgSesso" id="flgSessoCombo">
                      <c:choose>
                          <c:when
                              test="${comunicazioneSoggettiForm.soggettoDto.flgSesso == 'M'}">
                              <form:option value="M" label="Maschio" selected="true" /> </c:when>
                          <c:otherwise>
                              <form:option value="M" label="Maschio" /> </c:otherwise>
                      </c:choose>
                      <c:choose>
                          <c:when
                              test="${comunicazioneSoggettiForm.soggettoDto.flgSesso == 'F'}">
                              <form:option value="F" label="Femmina" selected="true" /> </c:when>
                          <c:otherwise>
                              <form:option value="F" label="Femmina" /> </c:otherwise>
                      </c:choose>
	                  </form:select>
                  </td>
                  <fmt:formatDate pattern="dd/MM/yyyy" value="${comunicazioneSoggettiForm.soggettoDto.dataNascita}" var="myDateVar" />
                  <td><form:label path="soggettoDto.dataNascita" cssClass="required"> 
                      <spring:message code="dataNascita" /> </form:label></td>
                  <td><form:input path="soggettoDto.dataNascita" id="dataNascita" maxlength="10" size="10" value="${myDateVar}" /></td>
              </tr>
                
				<tr>  <%-- Stato e comune nascita ispirati a /procedimento/nuovo/ricercaSoggettoFisico.jsp    INIZIO --%>
				
				    <td><form:label path="comboStatiBelfiore.id" cssClass="required">
                            <spring:message code="conservatoria.statoNascita" />
				        </form:label></td>
				    <td>
					    <c:set var="statoSelez" value="${comunicazioneSoggettiForm.soggettoDto.statoNascita}" /> 
								    
					    <form:select  path="comboStatiBelfiore.id" id="comboStatiBelfiore" size="1" onchange=
"controllaCampiEAutocomplete(this.value,'ComboComuniBelfiore'); setDescrizione(this.children[this.selectedIndex].innerText,'hiddenStatoNascita')"> 
                                    <%-- onchange="controllaCampiEAutocomplete(this.value,'ComboComuniBelfiore')" >  --%>

				            <form:option value="-1" label="------------------ Seleziona uno Stato -------------------" />

<%--		                <form:options items="${statiNascita}" itemValue="id" itemLabel="descrizione" />    --%>
				            <c:forEach items="${comboStatiBelfiore}" var="statBelf" varStatus="status">
                                <c:choose>
                                    <c:when test="${statBelf.descrizione eq statoSelez}">
                                        <option value="${statBelf.id}" selected="true">${statBelf.descrizione}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${statBelf.id}">${statBelf.descrizione}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </form:select> 
                        <form:hidden id="hiddenStatoNascita"  path="soggettoDto.statoNascita" />
                    </td>
                    <td><form:label path="comboComuniBelfiore.id" cssClass="required"> 
	                      <spring:message code="conservatoria.comuneNascita" /> </form:label></td>
	                <td>
				        <div class="ui-widget">
				            <form:input id="id_descrizioneComboComuniBelfiore"
				                path="comboComuniBelfiore.descrizione" size="50"
				                title="Immettere almeno 2 lettere per avviare la ricerca" />
				            <div id="id_loadingComboComuniBelfiore"></div>
				            <form:hidden id="id_hiddenComboComuniBelfiore" path="comboComuniBelfiore.id" />
				        </div>
				    </td>
				</tr> <%-- Stato e comune nascita come in /procedimento/nuovo/ricercaSoggettoFisico.jsp    FINE --%>

              <tr>
                  <td><form:label path="soggettoDto.codiceFiscalePF" cssClass="required"> 
                      <spring:message code="conservatoria.codiceFiscale" /> </form:label></td>
                  <td colspan="3">
                  	<form:input path="soggettoDto.codiceFiscalePF" id="codiceFiscale" maxlength="16" size="24" value="${comunicazioneSoggettiForm.soggettoDto.codiceFiscalePF}"/></td>
              </tr>
             <tr>
                  <td><form:label path="soggettoDto.regime" > 
                      <spring:message code="conservatoria.regime" /> </form:label></td>
                  <td>
                      <c:set var="regimeSelez" value="${comunicazioneSoggettiForm.soggettoDto.regime}" /> 
                      <form:select id="id_comboRegime" path="comboRegime.id" name="comboRegime" onchange="abilitaComboCon()">
                          <form:option value="-1" label="- Selezionare un regime  -" />
                          <c:forEach items="${comboRegime}" var="regim" varStatus="status">
                              <c:choose>
                                  <c:when test="${regim.id eq regimeSelez}">
                                      <option value="${regim.id}" selected="true">${regim.descrizione}</option>
                                  </c:when>
                                  <c:otherwise>
                                      <option value="${regim.id}">${regim.descrizione}</option>
                                  </c:otherwise>
                              </c:choose>
                          </c:forEach>
                      </form:select>
                  </td>
                  <td>
                      <div id="id_listaPFinRegime">
                          <c:set var="soggCon" value="${comunicazioneSoggettiForm.soggettoDto.codProgrSoggC}" /> 
                      	  <form:label path="comboPF.id"><spring:message code="conservatoria.con" /> </form:label>
                          &nbsp;&nbsp; <form:select id="id_comboPFinRegime" path="comboPF.id" >
                            <form:option value="-1" label="--- Selezionare ---" />
                              <c:forEach items="${listaPFinRegime}" var="pFinRegime" varStatus="status">
                                  <c:choose>
                                      <c:when test="${pFinRegime.id eq soggCon}">
                                          <option value="${pFinRegime.id}" selected="true">${pFinRegime.descrizione}</option>
                                      </c:when>
                                      <c:otherwise>
                                          <option value="${pFinRegime.id}">${pFinRegime.descrizione}</option>
                                      </c:otherwise>
                                  </c:choose>
                              </c:forEach>
    						</form:select> 
                      </div>
                  </td>
                  <td>
                  	  <div style="visibility: hidden;"><select name="listapersonale" id="listapersonale"></select></div>
                   </td>
               </tr>
          </table>
    </c:if>  
    <%-- FINE MODIFICA PERSONA FISICA ------------------------------------------------     --%>
    
    <%-- INIZIO MODIFICA PERSONA GIURIDICA--------------------------------------------     --%>            
    <c:if test="${comunicazioneSoggettiForm.flgTipoSoggetto == 'N'}">
            <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
                   <tr>
                      <td class="td_titolo"><spring:message code="conservatoria.personaGiuridica" /></td>
                   </tr>
            </table>
            <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td width="10%" ><form:label path="soggettoDto.denominazione" cssClass="required"> 
                        <spring:message code="conservatoria.denominazione" /> </form:label></td>
                    <td colspan="3" width="90%" ><form:input path="soggettoDto.denominazione" id="denominazione" maxlength="150"  size="150"
                        value="${comunicazioneSoggettiForm.soggettoDto.denominazione}"/></td>
                </tr>   
                
                <tr>  <%-- Stato e comune sede ispirati a /procedimento/nuovo/ricercaSoggettoFisico.jsp    INIZIO --%>

                    <td><form:label path="comboStatiIstat.id" cssClass="required">
                            <spring:message code="conservatoria.statoSede" />
                        </form:label></td>
                    <td>
                        <c:set var="statoSelez" value="${comunicazioneSoggettiForm.soggettoDto.statoSede}" /> 
                        <form:select  path="comboStatiIstat.id" id="comboStatiIstat" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComboComuniIstat'); setDescrizione(this.children[this.selectedIndex].innerText,'hiddenStatoSede')"> 
                        <%-- onchange="controllaCampiEAutocomplete(this.value,'ComboComuniIstat')" >  --%>
                            <form:option value="-1" label="------------------ Seleziona uno Stato -------------------" />
							<%--  <form:options items="${statiSede}" itemValue="id" itemLabel="descrizione" />    --%>
                            <c:forEach items="${comboStatiIstat}" var="statBelf" varStatus="status">
                                <c:choose>
                                    <c:when test="${statBelf.descrizione eq statoSelez}">
                                        <option value="${statBelf.id}" selected="true">${statBelf.descrizione}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${statBelf.id}">${statBelf.descrizione}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </form:select> 
                        <form:hidden id="hiddenStatoSede"  path="soggettoDto.statoSede" />
                    </td>
                    <td>
                    	<form:label path="comboComuniIstat.id" cssClass="required"><spring:message code="conservatoria.comuneSede" /> </form:label></td>
                    <td>
                        <div class="ui-widget">
                            <form:input id="id_descrizioneComboComuniIstat" 
                                path="comboComuniIstat.descrizione" size="50"
                                title="Immettere almeno 2 lettere per avviare la ricerca" />
                            <div id="id_loadingComboComuniIstat"></div>
                            <form:hidden id="id_hiddenComboComuniIstat" path="comboComuniIstat.id" />
                        </div>
                    </td>
                </tr> <%-- Stato e comune sede come in /procedimento/nuovo/ricercaSoggettoFisico.jsp    FINE --%>
                <tr>
                    <td><form:label path="soggettoDto.partitaIva" cssClass="required"> 
                        <spring:message code="partitaIva" /> </form:label></td>
                    <td colspan="3"><form:input path="soggettoDto.partitaIva" id="partitaIva" maxlength="11" size="13" 
                        value="${comunicazioneSoggettiForm.soggettoDto.partitaIva}"/></td>
                </tr> 
            </table>        
                <br />  
    </c:if>  
    <%-- FINE MODIFICA PERSONA GIURIDICA---------------------------------------     --%>
               <br /> <br />  
                <table class="tb_bottoni" align="center">
                    <tr>
                    
                    <c:if test="${comunicazioneSoggettiForm.tipoOperazione!='Cancella'}">
                         <c:if test="${comunicazioneSoggettiForm.stato=='0' || comunicazioneSoggettiForm.stato=='1' }">
	                        <td class="td_tasto"><input type="submit" value="Cancella" name="Cancella" title="Cancella" onClick="return popUpConferma()" class="nowait"/>
	                        <td class="td_tasto">
                            <c:if test="${comunicazioneSoggettiForm.flgTipoSoggetto == 'F'}">
	                            <input type="submit" value="Conferma" name="Conferma" title="Conferma" onclick=
	                                "return submitWithAutocompleteComuni(['ComboStatiBelfiore'],['ComboComuniBelfiore'],'search')" />
                            </c:if>
                            <c:if test="${comunicazioneSoggettiForm.flgTipoSoggetto == 'N'}">
                                <input type="submit" value="Conferma" name="Conferma" title="Conferma" onclick=
                                    "return submitWithAutocompleteComuni(['ComboStatiIstat'],['ComboStatiIstat'],'search')" />
                            </c:if>
                        </c:if>  
                       </c:if>           
                        <td class="td_tasto"><input type="submit" value="Indietro" name="Indietro" title="Indietro" />
                    </tr>
                </table>
		</form:form>

<script type="text/javascript">


     $(document).ready(function() { abilitaComboCon(); });
        
     function abilitaComboCon() 
     {
         var id = document.getElementById('id_comboRegime').value;
         if( id == "C" || id == "D" )
             document.getElementById("id_listaPFinRegime").style.display = 'inline-block';
         else
         {
             document.getElementById("id_listaPFinRegime").style.display = 'none';
             document.getElementById("id_comboPFinRegime").value = '-1';
         }
     }
	    
	    
   function popUpConferma() 
   {
       if (confirm("Confermi l'operazione ?"))
           return true;
        else
           return false;
   }
   
   function disabilitaCampi() 
   {
       if( document.getElementById('flgTipoSoggetto').value == 'F' )
       {
            document.getElementById('cognome').disabled = true;
            document.getElementById('nome').disabled = true;
            document.getElementById('flgSessoCombo').disabled = true;
            document.getElementById('dataNascita').disabled = true;
            document.getElementById('comboStatiBelfiore').disabled = true;
            document.getElementById('id_descrizioneComboComuniBelfiore').disabled = true;
            document.getElementById('codiceFiscale').disabled = true;
            document.getElementById('id_comboRegime').disabled = true;
            document.getElementById('id_listaPFinRegime').disabled = true;
       }else
       {
           document.getElementById('id_descrizioneComboComuniIstat').disabled = true;
           document.getElementById('partitaIva').disabled = true;
           document.getElementById('denominazione').disabled = true;
           document.getElementById('id_ComboStatiIstat').disabled = true;
       }
    }
   
   function setDescrizione( valore, target ) 
   {
       document.getElementById( target ).value = valore;
   }
   
   /*      
   function modificaOption(val){
		var listaAppoggio = document.getElementById("listapersonale");
		var num_optionListaApp= document.getElementById("listapersonale").options.length; 
		var indice_eliminare = 0;
		var indice_selezionato = 0;
		var mySelect = document.getElementById("codProgrSoggC");
		var cognome = document.getElementById("cognome").value;
		var nome = document.getElementById("nome").value;
		var element = cognome + " " + nome;
		if(val == 'C') {
			if (mySelect.length > 0) {
    			for (var i = 0; i < mySelect.length; i++) {
    				if (mySelect.options[i].text == element) {
    					indice_selezionato = i;
    				}
    			}
    			if(indice_selezionato>=0){
    				value_selezionato = document.getElementById("codProgrSoggC").options[indice_selezionato].value;
    				testo_selezionato = document.getElementById("codProgrSoggC").options[indice_selezionato].innerHTML;
    				duplicato=0;
    				for(a=0;a<num_optionListaApp;a++){
    					if(document.getElementById('listapersonale').options[a].value==value_selezionato){
    						duplicato=1;
    					}
    				}
    				if(duplicato==0){
    					document.getElementById('listapersonale').options[num_optionListaApp]=new Option('',escape(value_selezionato),false,false);
    					document.getElementById('listapersonale').options[num_optionListaApp].innerHTML = testo_selezionato;
    				}
    				mySelect.removeChild(mySelect.options[indice_selezionato]);
    			}
			}
		} else {
			if(num_optionListaApp>=0){
				for (var i = 0; i < listaAppoggio.length; i++) {
					if (listaAppoggio.options[i].text == element) {
						indice_selezionato = i;
					}
				}
				if(indice_selezionato>=0){
					value_selezionato = document.getElementById("listapersonale").options[indice_selezionato].value;
					testo_selezionato = document.getElementById("listapersonale").options[indice_selezionato].innerHTML;
					var num_optionMyList= document.getElementById("codProgrSoggC").options.length;
					document.getElementById('codProgrSoggC').options[num_optionMyList]=new Option('',escape(value_selezionato),false,false);
					document.getElementById('codProgrSoggC').options[num_optionMyList].innerHTML = testo_selezionato;
					listaAppoggio.removeChild(listaAppoggio.options[indice_selezionato]);
				}
			}
		}
	}
*/
</script>

<script type="text/javascript">
    $(document).ready(function() {
        prendiData("#dataNascita", true);
        
        inizializzaSubmitAutocomplete('Conferma');
        
        if( document.getElementById('id_ComboStatiBelfiore') != null )
            controllaInizializzazioneCampi($( "#id_ComboStatiBelfiore" ).val(),"ComboComuniBelfiore");
        if( document.getElementById('id_ComboStatiIstat') != null )
            controllaInizializzazioneCampi($( "#id_ComboStatiIstat" ).val(),"ComboComuniIstat");
    });
    
    	    
</script>

	    <c:if test="${comunicazioneSoggettiForm.flgTipoSoggetto == 'F'}">
	        <script type="text/javascript">
		        if( document.getElementById('comboStatiBelfiore').selectedIndex == 1 )
		        {
		            doAutocompleteConservat('ComboComuniBelfiore');
		        }
	        </script>
	    </c:if>
	    <c:if test="${comunicazioneSoggettiForm.flgTipoSoggetto == 'N'}">
	        <script type="text/javascript">
		        if( document.getElementById('comboStatiIstat').selectedIndex == 1)
		        {
		            doAutocompleteConservat('ComboComuniIstat');
		        }
	        </script>
	    </c:if>


	</tiles:putAttribute>
</tiles:insertDefinition>