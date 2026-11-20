<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/conservatorie/predisposizioneComunicazioni.htm" />
		<%-- --%>
		<form:form method="post" name="form1" action="${azione}" commandName="comunicazioneInsertForm">
			<p class="titolo_page"> <spring:message code="conservatoria.titolo.predisposizione" /> </p>
			<logic:present name="procedimento">
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			</logic:present>
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo"><spring:message code="conservatoria.titolo.dati" /></td>
				</tr>
            </table>
            <br />
            <form:hidden id="hiddenFlgDecisorio" path="flgDecisorio" />
            <table class="tb_form">
                <tr>
                    <td width="16%"><form:label path="tipoNota" cssClass="required">
                            <spring:message code="conservatoria.tipoComunicazione" />
                        </form:label></td>
                    <td align="left" >
                    	<%-- COMBO TIPO COMUNICAZIONE --%>
                    	<form:select path="tipoNota.id" id="tipoNotaCombo" name="tipoNotaCombo"
                            onchange="abilitaSezioneAttoSpecie(this.value)">
                            <form:option value="-1" label="-- Selezionare Tipo --" />
                            <form:options items="${tipoNota}" itemValue="id" itemLabel="descrizione" />
                        </form:select> <form:errors path="tipoNota" class="testoRosso" />
                    </td>
                </tr>
            </table>
            <br />
            <%-- COMBO ATTO SPECIE TRASCRIZIONE --%>
            <div id="attoSpecie.T" style="width:100%">
                <table class="tb_form">
                    <tr>
                        <td width="16%"><form:label path="attoSpecieT" cssClass="required">
                                <spring:message code="conservatoria.attoSpecie" />
                            </form:label></td>
                        <td align="left"> 
                        <form:select path="attoSpecieT.id" id="attoSpecieT.id">       
                               <form:option value="ATTO GIUDIZIARIO" label="ATTO GIUDIZIARIO" />
                        </form:select> <form:errors path="attoSpecieT" class="testoRosso" /></td>
                    </tr>
                    <tr><td colspan="2"></td></tr>
                    <tr>
                        <td width="16%"><form:label path="tipoComunicT" cssClass="required">
                                <spring:message code="conservatoria.descrizione" />
                            </form:label></td>
                        <td align="left"><form:input path="tipoComunicT"
							id="tipoComunicT.id" maxlength="100" size="65" />
							<form:errors path="tipoComunicT" class="testoRosso" />
							<form:hidden id="hiddenDescrAtto" path="descAtto" />
					   </td>
                    </tr>
                </table>
            </div>
             <%-- COMBO ATTO SPECIE ANNOTAZIONE --%>
            <div id="attoSpecie.A" style="width:100%">
                <table class="tb_form">
                    <tr>
                        <td width="16%"><form:label path="attoSpecieA" cssClass="required">
                                <spring:message code="conservatoria.attoSpecie" />
                            </form:label></td>
                            
                        <td align="left"><form:select path="attoSpecieA.id"
                                id="attoSpecieA.id" onchange="abilitaSezioneDescrizione( this.value )">
                                <form:option value="-1" label="-- Selezionare Atto specie --" />
                                <form:option value="Annotazione a trascrizione" label="Annotazione a trascrizione" />
                                <form:option value="Annotazione ad annotazione" label="Annotazione ad annotazione" />
                            </form:select> <form:errors path="attoSpecieA" class="testoRosso" /></td>
                    </tr>
                </table>
            </div>
           <br /><br />
	            <%-- COMBO DESCRIZIONE ANNOTAZIONE a ANNOTAZIONE --%>
	            <div id="descrizione.A" style="width:100%">
	                <table class="tb_form">
	                    <tr>
	                        <td width="16%"><form:label path="attoSpecieA" cssClass="required">
	                                <spring:message code="conservatoria.descrizione" />
	                            </form:label></td>
	                        <td align="left">
								<form:select path="tipoComunicAnn.id" id="tipoComunicAnn" onchange="settaDescrizioneA(this)">
										<form:option value="-1" label="- Selezionare Tipo Comunicazione -" />
										<form:options items="${tipoComunicTrasc}" itemValue="id" itemLabel="descrizione" />
								</form:select> 
                            </td>
	                    </tr>
	                </table>
	            </div>
            
            	<%-- COMBO DESCRIZIONE ANNOTAZIONE a TRASCRIZIONE --%>
	            <div id="descrizione.T" style="width:100%">
	                <table class="tb_form">
	                    <tr>
	                        <td width="16%"><form:label path="attoSpecieA" cssClass="required">
	                                <spring:message code="conservatoria.descrizione" />
	                            </form:label></td>
	                            
	                        <td align="left">
							<form:select path="tipoComunicTrasc.id" id="tipoComunicTrasc" onchange="settaDescrizioneT(this)">
									<form:option value="-1" label="- Selezionare Tipo Comunicazione -" />
									<form:options items="${tipoComunicAnn}" itemValue="id" itemLabel="descrizione" />
							</form:select> 
	                         </td>
	                    </tr>
	                </table>
	            </div>
            <br /><br />
            
            <%-- GP --%>
            	<div id="id_tenoreMisura" style="width: 100%;">
						<table class="tb_form" cellspacing="0" cellpadding="0" border="0" >
		                    <tr>
	                       		 <td colspan="2">
	                       		 	<form:label path="tenoreMisura"  cssClass="required">
	                       		 		<spring:message code="conservatoria.tenoreMisura" />
	                       		 	</form:label>
	                            </td>
			     			</tr>
					      	<tr>
					      		<td width="3%">&nbsp;</td>
						    	<td><form:radiobutton path="tenoreMisura"  id="confisca"  value="1"  label = "Confisca" style="cursor:pointer"  />
						    	&nbsp;&nbsp;&nbsp;
						    	<form:radiobutton path="tenoreMisura"  id="rigetto"   value="2"  label = "Rigetto"  style="cursor:pointer"  />
						    	</td>
			     			</tr>
			     		</table>
			     		<br/>
	     		</div>
	     		<br/>
	     		
	     		<div id="nuova_si" style="width: 100%; display: inline-block;">
	                <table class="tb_bottoni" align="center">
	    				<tr>
	                        <c:if test="${comunicazioneInsertForm.listaRichiesta and empty comunicazioneInsertForm.listaPredisp }">
	                            <td class="td_tasto">
	                            		<input type="submit" name="Nuova" value="Nuova" title="Nuova">
	                            </td>
	                        </c:if> 
	                        <td class="td_tasto"><input type="submit" name="Procedi" value="Procedi" title="Procedi"></td>
	                        <td class="td_tasto"><input type="button" id="backButton1" value="Indietro" title="Indietro" ></td>
	    				</tr>
	    			</table>
    			</div>
    			<div id="nuova_no" style="width: 100%; display: none;">
	    			<table class="tb_bottoni" align="center">
	    				<tr>
	                        <td class="td_tasto"><input type="submit" name="Procedi" value="Procedi" title="Procedi"></td>
	                        <td class="td_tasto"><input type="button" id="backButton2" value="Indietro" title="Indietro" ></td>
	    				</tr>
	    			</table>
    			</div>
    			
    			<br /><br />
    			
    			<div id="id_tabelle" style="width: 100%;">
					<logic:present name="listaPredisp">
						<display:table class="tb_lista" name="${listaComunicazioniP}" 
	                                uid="listaComunicazioniP" pagesize="10" requestURI="${azione}" >
							<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
								<form:checkbox id="notaSel" path="notaSel[${listaComunicazioniP_rowNum-1}]" value="${listaComunicazioniP.idNota}^${listaComunicazioniP.codConservatoria}^${listaComunicazioniP.statoComunicazione}" />
							</display:column> 
							<display:column property="idNota" title="Nota" sortable="false"/>
                            <display:column property="annoNumero" title="Anno/Numero-progressivo" sortable="false"/>							
                            <display:column property="statoComunicazione" title="Stato" sortable="false"/>
							<display:column property="dataComunicazione" title="Data" format="{0,date,dd/MM/yyyy}" sortable="false"/>
							<display:column property="descConservatoria" title="Comunicazione alla Conservatoria di" sortable="false"/>
						</display:table>
						
						<logic:notEmpty name="listaComunicazioniP">
							<br/><br/>
							<table class="tb_bottoni" align="center">
								<tr>
		                    		<td class="td_tasto"><input type="submit" name="Rigenera" value="Rigenera" title="Rigenera" onClick="return popUpConferma()" class="nowait"></td>
								</tr>
							</table>
						</logic:notEmpty>
					</logic:present>
				</div>
				
<script type="text/javascript">

    $(document).ready(function() { abilitaSezioneInAvvio(); });
    
    function abilitaSezioneInAvvio()
    {
        var flgDecisorio = document.getElementById( "hiddenFlgDecisorio" ).value;
        if( flgDecisorio == 'C' )
            document.getElementById( "tipoNotaCombo" ).remove(2) ; 
        
    	var tipoNota = document.getElementById( "tipoNotaCombo" ).value;
    	if( tipoNota == "-1" ) {
	        document.getElementById( "attoSpecie.A" ).style.display = 'none';
	        document.getElementById( "attoSpecie.T" ).style.display = 'none';
	        document.getElementById( "descrizione.A" ).style.display = 'none';
	        document.getElementById( "descrizione.T" ).style.display = 'none';
	        document.getElementById( "id_tenoreMisura" ).style.display = 'none';
    	}    
    	else if( tipoNota == "A" )  {
            document.getElementById( "attoSpecie.T" ).style.display = 'none';
            document.getElementById( "id_tenoreMisura" ).style.display = 'inline-block';
            var attoSpeA = document.getElementById( "attoSpecieA.id" ).value;
            if( attoSpeA == "-1" ) {
                document.getElementById( "descrizione.A" ).style.display = 'none';
    	        document.getElementById( "descrizione.T" ).style.display = 'none';
            }
            if( attoSpeA == "Annotazione a trascrizione" ) {
                document.getElementById( "descrizione.A" ).style.display = 'none';
    	        document.getElementById( "descrizione.T" ).style.display = 'inline-block';
            }
            if( attoSpeA == "Annotazione ad annotazione" ) {
                document.getElementById( "descrizione.A" ).style.display = 'inline-block';
    	        document.getElementById( "descrizione.T" ).style.display = 'none';
            }
        } 
    	else if ( tipoNota == "T" )  {
            document.getElementById( "attoSpecie.A" ).style.display = 'none';
            document.getElementById( "descrizione.A" ).style.display = 'none';
	        document.getElementById( "descrizione.T" ).style.display = 'none';
	        document.getElementById( "id_tenoreMisura" ).style.display = 'none';
        }
    }

    function abilitaSezioneAttoSpecie( valore )
    {
    	document.getElementById( "id_tabelle" ).style.display = 'none';
    	document.getElementById( "nuova_si" ).style.display = 'none';
    	document.getElementById( "nuova_no" ).style.display = 'inline-block';
    	if( valore == "-1" )
        {
	        document.getElementById( "attoSpecie.A" ).style.display = 'none';
	        document.getElementById( "attoSpecie.T" ).style.display = 'none';
	        document.getElementById( "descrizione.A" ).style.display = 'none';
	        document.getElementById( "descrizione.T" ).style.display = 'none';
	        document.getElementById( "attoSpecieA.id" ).value = '-1';
	        document.getElementById( "id_tenoreMisura" ).style.display = 'none';
        }
    	
        if( valore == "A" ) {
        	document.getElementById( "id_tenoreMisura" ).style.display = 'inline-block';
            document.getElementById( "attoSpecie.A" ).style.display = 'inline-block';
            document.getElementById( "attoSpecie.T" ).style.display = 'none';
            var id = document.getElementById( "attoSpecie.A" ).value;
            
            if(id == "Annotazione ad annotazione") {
                document.getElementById( "descrizione.A" ).style.display = 'inline-block';
                document.getElementById( "tipoComunicTrasc.id" ).value = '-1';
                document.getElementById( "descrizione.T" ).style.display = 'none';
                
            }  else if(id == "Annotazione a trascrizione")  {
               document.getElementById( "descrizione.T" ).style.display = 'inline-block';
               document.getElementById( "tipoComunicAnn.id" ).value = '-1';
               document.getElementById( "descrizione.A" ).style.display = 'none';
            }
        } else if( valore == "T" )  {
        	document.getElementById( "id_tenoreMisura" ).style.display = 'none';
            document.getElementById( "attoSpecie.T" ).style.display = 'inline-block';
            document.getElementById( "attoSpecie.A" ).style.display = 'none';
            document.getElementById( "attoSpecieA.id" ).value = '-1';
            document.getElementById( "descrizione.A" ).style.display = 'none';
            document.getElementById( "descrizione.T" ).style.display = 'none';
            document.getElementById( "tipoComunicAnn.id" ).value = '-1';
            document.getElementById( "tipoComunicTrasc.id" ).value = '-1';
        }
    }
    
    
    function abilitaSezioneDescrizione( valore )
    {
        document.getElementById( "descrizione.A" ).style.display = 'none';
        document.getElementById( "descrizione.T" ).style.display = 'none';
        if( valore == "Annotazione ad annotazione" )
        {
            document.getElementById( "descrizione.A" ).style.display = 'inline-block';
        }else if( valore == "Annotazione a trascrizione" )
        {
            document.getElementById( "descrizione.T" ).style.display = 'inline-block';
        }
    }
    

	function popUpConferma() {
		for (var i = 0; i < document.form1.notaSel.length; i++) {
			if (document.form1.notaSel[i].checked) {
				var stato = document.form1.notaSel[i].value;
				var res = stato.substr(stato.length-1, stato.length)
				if (res == 0 || res == 1) {
					if (confirm("Confermi la cancellazione delle note selezionate che si trovano nello stato diverso da 2 (inviato) ?")) 
						return true;
					else 
						return false;	
				}
			}
		}
		return true;
	}
</script>
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
		<smp:back buttonId="backButton1"></smp:back>
		<smp:back buttonId="backButton2"></smp:back>
		
	</tiles:putAttribute>
</tiles:insertDefinition>