<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<p class="titolo_page"> <spring:message code="conservatoria.titolo.nuovo.inserimento" /></p>
		<spring:url var="azione" value="/conservatorie/inserimentoComunicazione.htm" />
		<form:form id="id_formRicerca" method="post" action="${azione}" commandName="comunicazioneInsertForm">

			<logic:notEmpty name="comunicazioneInsertForm" property="numeroComunicazione">
				<table class="tb_form" cellspacing="1" cellpadding="1" border="0">
					<tr>
						<td width="10%"><form:label path="numeroComunicazione">
								<spring:message code="conservatoria.numeroComunicazione" />
							</form:label> &nbsp;&nbsp; <c:out value="${comunicazioneInsertForm.numeroComunicazione}" /></td>
					</tr>
					<script type="text/javascript">
						$(document).ready(function() { disabilitaCampi(); });
					</script>
				</table>
			</logic:notEmpty>

			<table class="tb_form" cellspacing="1" cellpadding="1" border="0">
				<tr>
					<td width="10%"><form:label path="tipoNota" cssClass="required"> <spring:message code="conservatoria.tipoNota" />
						</form:label></td>
					<td align="left">
                        <form:select path="tipoNota.id" id="tipoNotaCombo" name="tipoNotaCombo" onchange="abilitaSezione(this.value)">
                        	<form:option value="-1" label="-- Selezionare Tipo --" />
							<form:options items="${tipoNota}" itemValue="id" itemLabel="descrizione" />
						</form:select> <form:errors path="tipoNota" class="testoRosso" />
                    </td>
				</tr>
			</table>
				
			<table class="tb_form" cellspacing="1" cellpadding="1" border="0">
				<tr>
					<td width="10%"><form:label path="categorieAttiT" cssClass="required"> <spring:message code="conservatoria.categorieAtti" /></form:label></td>
                    <td align="left">
                        <div id="id_AttiPerTrascriz" style="display: none; width: 100%;">
                                <form:select path="categorieAttiT.id" id="categorieAttiComboT"
                                    onchange="setDescrCategoriaAtto( this.children[this.selectedIndex] )">
                                    <form:option value="-1" label="--- Selezionare Tipo Atto ---" />
                                    <form:options items="${categorieAtti}" itemValue="id" itemLabel="descrizione" />
                                </form:select> 
                                <form:errors path="categorieAttiT" class="testoRosso" />
                        </div>
                        <div id="id_AttiPerAnnotaz" style="display: none; width: 100%;">
                            
                                <form:select path="categorieAttiA.id" id="categorieAttiComboA"
                                    onchange="setDescrCategoriaAtto( this.children[this.selectedIndex] )">
                                    <form:option value="-1" label="--- Selezionare Tipo Atto ---" />
                                    <form:options items="${categorieAttiNonSequestri}" itemValue="id" itemLabel="descrizione" />
                                </form:select>
                                <form:errors path="categorieAttiA" class="testoRosso" />
                
                        </div>
                        <form:hidden id="hiddenDescrCategoriaAtto" path="descrCategoriaAtto" />
                        <form:hidden id="categoriaAtto" path="categoriaAtto" />
                    </td>
				</tr>
			</table>	
				
			<table class="tb_form" cellspacing="1" cellpadding="1" border="0">	
				<tr>
					<td width="10%">
						<form:label path="repertorioAnno" cssClass="required"><spring:message code="conservatoria.repertorioAnno" /></form:label>
					</td>
					<td width="10%" align="left">
						<form:input path="repertorioAnno" 	id="repertorioAnno" maxlength="4" size="8" /> 
						<form:errors path="repertorioAnno" class="testoRosso" />
					</td>
					<td width="20%" align="left">
						<form:label path="numero" cssClass="required"><spring:message code="conservatoria.numero" /></form:label>
						&nbsp;&nbsp;
						<form:input path="numero" id="numero" maxlength="7" size="8" /><form:errors path="numero" class="testoRosso" />
					</td>
					<td  align="left">
						<form:label path="progressivo"><spring:message code="conservatoria.progressivo" /></form:label>
						&nbsp;&nbsp;
						<form:input path="progressivo" id="progressivo" maxlength="3" size="8" /><form:errors path="progressivo" class="testoRosso" />
					</td>
				</tr>
			</table>
			<br />
			<table class="tb_form" cellspacing="1" cellpadding="1" border="0">
				<tr>
					<td class="td_titolo"><spring:message code="conservatoria.datiGenerali" /></td>
				</tr>
			</table>
			
			<fmt:formatDate pattern="dd/MM/yyyy" value="${comunicazioneModificaForm.dataTitolo}" var="myDateVar" />
			
			<table class="tb_form" cellspacing="2" cellpadding="2" border="0">
				<tr>
					<td width="10%"><form:label path="titoloDescrizione" cssClass="required">
							<spring:message code="conservatoria.titoloDescrizione" />
						</form:label>
					</td>
					<td width="10%" align="left"><form:input path="titoloDescrizione" id="titoloDescrizione" maxlength="61" size="65" /></td>
					<td colspan="2">
						<form:label path="dataTitolo" cssClass="required"><spring:message code="conservatoria.data" /></form:label>
						&nbsp;&nbsp;
						<form:input path="dataTitolo" id="dataTitolo" maxlength="10" size="11" value="${myDateVar}" />
					</td>
				</tr>
				<tr>
					<td><form:label path="pubblicoUfficiale" cssClass="required">
							<spring:message code="conservatoria.pubblicoUfficiale" />
						</form:label>
					</td>
					<td align="left">
						<form:input path="pubblicoUfficiale" id="pubblicoUfficiale" maxlength="50" size="65" />
					</td>
					<td>
						<form:label path="categoria"><spring:message code="conservatoria.categoria" /></form:label>
						<spring:message code="conservatoria.altroPubbUff" />
					</td>
					<td>
						<form:label path="codiceFiscale" cssClass="required"><spring:message code="conservatoria.codiceFiscalePU" /></form:label>
						&nbsp;&nbsp;
						<form:input path="codiceFiscale" id="codiceFiscale" maxlength="16" size="24" />
					</td>
				</tr>
				<tr>
					<td><form:label path="comune" cssClass="required">
							<spring:message code="conservatoria.sede" />
						</form:label>
					</td>
					<td align="left" colspan="3">
						<div class="ui-widget">
							<form:input id="id_descrizioneComune" path="comune.descrizione"
								size="65" title="Immettere almeno 2 lettere per avviare la ricerca" />
							<div id="id_loadingComune"></div>
							<form:hidden id="id_hiddenComune" path="comune.id" />
						</div>
					</td>
				</tr>
			</table>
			
			<%-- SCRIPT PER LA GESTIONE DELLE SEZIONI TRANSIZIONE E ANNOTAZIONE --%>

			<%-- INIZIO COMBO T --%>
				<table class="tb_form" id="attoSpecie.T" cellspacing="2" cellpadding="2" border="0">
					<tr>
						<td width="10%">
							<form:label path="attoSpecieT" cssClass="required"><spring:message code="conservatoria.attoSpecie" /></form:label>
						</td>
						<td>	
							<form:select path="attoSpecieT.id" id="attoSpecieT.id">
								<form:option value="ATTO GIUDIZIARIO" label="ATTO GIUDIZIARIO" />
							</form:select> <form:errors path="attoSpecieT" class="testoRosso" />
						    &nbsp;&nbsp; &nbsp;&nbsp; 
							<form:label path="rettifica"><spring:message code="conservatoria.rettifica" /></form:label> 
							&nbsp;&nbsp; 
							<form:select path="rettifica.id" id="rettifica.id">
								<form:option value="-1" label="- Selezionare Rettifica -" />
								<form:option value="7" label="D'Ufficio" />
								<form:option value="8" label="Alla nota" />
								<form:option value="9" label="Al titolo" />
							</form:select> <form:errors path="rettifica" class="testoRosso" />
							&nbsp;&nbsp; &nbsp;&nbsp; 
							<form:label path="tipoComunicT" cssClass="required"><spring:message code="conservatoria.descrizione" /></form:label> 
							&nbsp;&nbsp; 
							<form:input path="tipoComunicT"	id="tipoComunicT.id" maxlength="100" size="60" />
							<form:errors path="tipoComunicT" class="testoRosso" />
							<form:hidden id="hiddenDescrAtto" path="descAtto" />
						</td>
					</tr>
				</table>

			<%-- INIZIO COMBO A --%>
				<table class="tb_form" id="attoSpecie.A" cellspacing="2" cellpadding="2" border="0">
					<tr>
						<td width="10%">
							<form:label path="attoSpecieA" cssClass="required"><spring:message code="conservatoria.attoSpecie" /></form:label>
						</td>
						<td>		
							<form:select path="attoSpecieA.id" id="attoSpecieA.id" onchange="cambiaDel( this.value )">
								<form:option value="-1" label="-- Selezionare Atto specie --" />
									<form:option value="Annotazione a trascrizione"  label="Annotazione a trascrizione" />
									<form:option value="Annotazione ad annotazione"  label="Annotazione ad annotazione" />
							</form:select> <form:errors path="attoSpecieA" class="testoRosso" />
							&nbsp;&nbsp;
							<div id="Ann" >
								<form:label path="tipoComunicA" cssClass="required">
									<spring:message code="conservatoria.descrizione" />
								</form:label> &nbsp;&nbsp;
								<form:select path="tipoComunicAnn.id" id="tipoComunicAnn" onchange="settaDescrizioneA(this)">
										<form:option value="-1" label="- Selezionare Tipo Comunicazione -" />
										<form:options items="${tipoComunicAnn}" itemValue="id" itemLabel="descrizione" />
								</form:select> 
							</div>
							<div id="Trasc">
								<form:label path="tipoComunicA" cssClass="required">
									<spring:message code="conservatoria.descrizione" />
								</form:label> &nbsp;&nbsp;
								<form:select path="tipoComunicTrasc.id" id="tipoComunicTrasc" onchange="settaDescrizioneT(this)">
										<form:option value="-1" label="- Selezionare Tipo Comunicazione -" />
										<form:options items="${tipoComunicTrasc}" itemValue="id" itemLabel="descrizione" />
								</form:select> 
							</div>
						</td>
					</tr>
				</table>

			<table class="tb_form" cellspacing="2" cellpadding="2" border="0">
				<tr>
					<td width="10%"><form:label path="conservatoria" cssClass="required">
							<spring:message code="conservatoria.conservatorie" />
						</form:label>
					</td>
					<td align="left">
						<form:select path="conservatoria.id"
							id="conservatoriaCombo" name="conservatoriaCombo" onchange="setDescrConservatoria( this.children[this.selectedIndex].innerText )">
							<form:option value="-1" label="- Selezionare Conservatoria -" />
							<form:options items="${conservatoria}" itemValue="id" itemLabel="descrizione" />
						</form:select> <form:errors path="conservatoria" class="testoRosso" /> 
                        <form:hidden id="hiddenDescrConservatoria" path="descrConservatoria" /></td>
				</tr>
			</table>
			<br />
			<table class="tb_form" cellspacing="1" cellpadding="1" border="0">
				<tr>
					<td><form:label path="unitaNegoziali">
							<spring:message code="conservatoria.unitaNegoziali" />
						</form:label> &nbsp;&nbsp; <c:out
							value="${comunicazioneInsertForm.unitaNegoziali}" /></td>
					<td><form:label path="immobili">
							<spring:message code="conservatoria.immobili" />
						</form:label> &nbsp;&nbsp; <c:out value="${comunicazioneInsertForm.immobili}" /></td>
					<td><form:label path="soggettiFavore">
							<spring:message code="conservatoria.soggettiFavore" />
						</form:label> &nbsp;&nbsp; <c:out
							value="${comunicazioneInsertForm.soggettiFavore}" /></td>
					<td><form:label path="soggettiContro">
							<spring:message code="conservatoria.soggettiContro" />
						</form:label> &nbsp;&nbsp; <c:out
							value="${comunicazioneInsertForm.soggettiContro}" /></td>
				</tr>
			</table>
			<br />
			<br />

			<%-- INIZIO DATI TRASCRIZIONE --%>
			<div id="divTrascrizione" style="width: 100%;">
				<table class="tb_form" cellspacing="1" cellpadding="1" border="0">
					<tr>
						<td class="td_titolo"><spring:message
								code="conservatoria.datiRelTrascrizione" /></td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="1" cellpadding="1" border="0">
					<tr>
						<td><form:checkbox path="attoSoggVoltura"
								id="attoSoggVoltura" /> <spring:message
								code="conservatoria.attoSoggVoltura" /></td>
						<td><form:checkbox path="differita" id="differita" /> <spring:message
								code="conservatoria.differita" /> &nbsp;&nbsp; <form:input
								path="dataDal" id="dataDal" /></td>
					</tr>
					<tr>
						<td><form:checkbox path="presenzaCondizione"
								id="presenzaCondizione" /> <spring:message
								code="conservatoria.presenzaCondizione" /></td>
						<td><form:checkbox path="presenzaTermini"
								id="presenzaTermini" /> <spring:message
								code="conservatoria.presenzaTermini" /></td>
					</tr>
					<tr>
						<td><form:label path="dataFormalita">
								<spring:message code="conservatoria.dataFormalita" />
							</form:label> &nbsp;&nbsp; <form:input path="dataFormalita" id="dataFormalita"
								onchange="abilitaNumeri()" /></td>
						<td><form:label path="numeFormalita1">
								<spring:message code="conservatoria.numero" />
							</form:label> &nbsp;&nbsp; <form:input path="numeFormalita1"
								id="numeFormalita1" maxlength="6" size="6"
								onchange="abilitaNumeri()" />
							&nbsp;&nbsp; <form:input path="numeFormalita2"
								id="numeFormalita2" maxlength="3" size="3" /></td>
					</tr>
					<tr>
						<td><form:checkbox path="quadroA" id="quadroA" /> <spring:message
								code="conservatoria.quadroA" /></td>
						<td><form:checkbox path="quadroB" id="quadroB" /> <spring:message
								code="conservatoria.quadroB" /></td>
						<td><form:checkbox path="quadroC" id="quadroC" /> <spring:message
								code="conservatoria.quadroC" /></td>
					</tr>
				</table>
			</div>

			<%-- INIZIO DATI ANNOTAZIONE --%>
			<div id="divAnnotazione" style="width: 100%;">
				<table class="tb_form" cellspacing="1" cellpadding="1" border="0">
					<tr>
						<td class="td_titolo"><spring:message
								code="conservatoria.datiRelAnnotazione" /></td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="1" cellpadding="1" border="0">
					<tr>
						<td><form:label path="formalita">
								<spring:message code="conservatoria.formalita" />
							</form:label></td>
					</tr>
					<tr>
						<td>
							<form:label path="trascrizioneDel" id="trascrizioneDel" cssClass="required">
								<spring:message code="conservatoria.trascrizioneDel" />
							</form:label> &nbsp;&nbsp; 
							<form:input path="trascrizioneDel" id="trascrizioneDelInput" onchange="abilitaNumeri()" />
							&nbsp;&nbsp; 
							<form:label path="numeroTrascrizione1" id="numeroTrascrizione" cssClass="required">
								<spring:message code="conservatoria.numero" />
							</form:label> &nbsp;&nbsp; 
							<form:input path="numeroTrascrizione1" id="numeroTrascrizione1" maxlength="6" size="6" onchange="abilitaNumeri()" />
							&nbsp;&nbsp; 
							<form:input path="numeroTrascrizione2" id="numeroTrascrizione2" maxlength="3" size="3" />
						</td>
					</tr>
					<tr>
						<td><form:label path="valoreImmobLib">
								<spring:message code="conservatoria.valoreImmobLib" />
							</form:label> &nbsp;&nbsp; <form:input path="valoreImmobLib"
								id="valoreImmobLib" cssClass="numerico" /> &nbsp;</td>
					</tr>
					<tr>
						<td><form:checkbox path="volturaCatastale"
								id="volturaCatastale" /> <spring:message
								code="conservatoria.volturaCatastale" /></td>
					<tr>
				</table>
			</div>
			<br /><br />
			<logic:empty name="comunicazioneInsertForm" property="numeroComunicazione">
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" value="Conferma" name="Conferma" title="Conferma"></td>
					</tr>
				</table>
			</logic:empty>
		</form:form>
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
	</tiles:putAttribute>
</tiles:insertDefinition>

<script type="text/javascript">

$(document).ready( function() { abilitaSezioneInAvvio(); } );

    function abilitaSezioneInAvvio()
    {
        document.getElementById( "divTrascrizione" ).style.display = 'none';
        document.getElementById( "divAnnotazione" ).style.display = 'none';
        document.getElementById( "attoSpecie.A" ).style.display = 'none';
        document.getElementById( "attoSpecie.T" ).style.display = 'none';
        document.getElementById( "Ann" ).style.display = 'none';
        document.getElementById( "Trasc" ).style.display = 'none';

        var id = document.getElementById( 'tipoNotaCombo' ).value;
        var idann = document.getElementById( 'tipoComunicAnn' ).value;
        var idtrasc = document.getElementById( 'tipoComunicTrasc' ).value;
        var idAttoSpe = document.getElementById( 'attoSpecieA.id' ).value;
        if( id == "A" )
        {
            document.getElementById( "id_AttiPerAnnotaz" ).style.display = 'inline-block';
            document.getElementById( "divAnnotazione" ).style.display = 'inline-block';
            document.getElementById( "attoSpecie.A" ).style.display = 'inline-block';
            if( idAttoSpe != "-1" )
            {
                if( idAttoSpe != "Annotazione a trascrizione" )
                    document.getElementById( "Trasc" ).style.display = 'inline-block';

                if( idAttoSpe != "Annotazione ad annotazione" )
                    document.getElementById( "Ann" ).style.display = 'inline-block';
            }
            if( idann != "-1" )
                document.getElementById( "Ann" ).style.display = 'inline-block';
        
            if( idtrasc != "-1" )
                document.getElementById( "Trasc" ).style.display = 'inline-block';

        }else if( id == "T" )
        {
            document.getElementById( "id_AttiPerTrascriz" ).style.display = 'inline-block';
            document.getElementById( "divTrascrizione" ).style.display = 'inline-block';
            document.getElementById( "attoSpecie.T" ).style.display = 'inline-block';
        }
        abilitaNumeri();
    }

    function abilitaSezione( valore )
    {
        document.getElementById( "divTrascrizione" ).style.display = 'none';
        document.getElementById( "divAnnotazione" ).style.display = 'none';
        document.getElementById( "attoSpecie.A" ).style.display = 'none';
        document.getElementById( "attoSpecie.T" ).style.display = 'none';
        document.getElementById( "id_AttiPerAnnotaz" ).style.display = 'none';
        document.getElementById( "id_AttiPerTrascriz" ).style.display = 'none';
        document.getElementById( "categorieAttiComboA" ).selectedIndex = 0;
        document.getElementById( "categorieAttiComboT" ).selectedIndex = 0;
        if( valore == "A" )
        {
            document.getElementById( "id_AttiPerAnnotaz" ).style.display = 'inline-block';
            document.getElementById( "divAnnotazione" ).style.display = 'inline-block';
            document.getElementById( "attoSpecie.A" ).style.display = 'inline-block';
            document.getElementById( "dataFormalita" ).value = '';
        }else if( valore == "T" )
        {
            document.getElementById( "id_AttiPerTrascriz" ).style.display = 'inline-block';
            document.getElementById( "divTrascrizione" ).style.display = 'inline-block';
            document.getElementById( "attoSpecie.T" ).style.display = 'inline-block';
            document.getElementById( "trascrizioneDelInput" ).value = '';
            document.getElementById( "numeroTrascrizione1" ).value = '';
            document.getElementById( "numeroTrascrizione2" ).value = '';
        }
        abilitaNumeri();
    }

    function cambiaDel( valore )
    {
        if( valore == '-1' )
        {
            document.getElementById( "Ann" ).style.display = 'none';
            document.getElementById( "Trasc" ).style.display = 'none';
            document.getElementById( 'trascrizioneDel' ).innerHTML = 'Trascrizione del';
        }
        if( valore == 'Annotazione a trascrizione' )
        {
            document.getElementById( "Ann" ).style.display = 'inline-block';
            document.getElementById( 'tipoComunicAnn' ).value = '-1';
            document.getElementById( "Trasc" ).style.display = 'none';
            document.getElementById( 'trascrizioneDel' ).innerHTML = 'Trascrizione del';
        }
        if( valore == 'Annotazione ad annotazione' )
        {
            document.getElementById( "Ann" ).style.display = 'none';
            document.getElementById( "Trasc" ).style.display = 'inline-block'
            document.getElementById( 'tipoComunicTrasc' ).value = '-1';
            document.getElementById( 'trascrizioneDel' ).innerHTML = 'Annotazione del';
        }
    }
    function setDescrConservatoria( valore )
    {
        if( valore != '-1' )
            document.getElementById( 'hiddenDescrConservatoria' ).value = valore;
    }
    function setDescrCategoriaAtto( valore )
    {
        document.getElementById( 'categoriaAtto' ).value = valore.value; 
        document.getElementById( 'hiddenDescrCategoriaAtto' ).value = valore.innerText;
//   if( valore != '-1' ) document.getElementById( 'hiddenDescrCategoriaAtto' ).value = valore.innerText;
    }
    function disabilitaCampi()
    {
        document.getElementById( 'tipoNotaCombo' ).disabled = true;
        document.getElementById( 'categorieAttiComboA' ).disabled = true;
        document.getElementById( 'categorieAttiComboT' ).disabled = true;
        document.getElementById( 'repertorioAnno' ).disabled = true;
        document.getElementById( 'numero' ).disabled = true;
        document.getElementById( 'progressivo' ).disabled = true;
        document.getElementById( 'conservatoriaCombo' ).disabled = true;
        document.getElementById( 'codiceFiscale' ).disabled = true;
        document.getElementById( 'titoloDescrizione' ).disabled = true;
        document.getElementById( 'dataTitolo' ).disabled = true;
        document.getElementById( 'pubblicoUfficiale' ).disabled = true;
        document.getElementById( 'id_descrizioneComune' ).disabled = true;
        document.getElementById( 'attoSpecieT.id' ).disabled = true;
        document.getElementById( 'rettifica.id' ).disabled = true;
        document.getElementById( 'tipoComunicT.id' ).disabled = true;
        document.getElementById( 'attoSpecieA.id' ).disabled = true;

        document.getElementById( 'tipoComunicAnn' ).disabled = true;
        document.getElementById( 'tipoComunicTrasc' ).disabled = true;

        document.getElementById( 'attoSoggVoltura' ).disabled = true;
        document.getElementById( 'differita' ).disabled = true;
        document.getElementById( 'dataDal' ).disabled = true;
        document.getElementById( 'presenzaCondizione' ).disabled = true;
        document.getElementById( 'presenzaTermini' ).disabled = true;
        document.getElementById( 'dataFormalita' ).disabled = true;
        document.getElementById( 'numeFormalita1' ).disabled = true;
        document.getElementById( 'numeFormalita2' ).disabled = true;

        document.getElementById( 'quadroA' ).disabled = true;
        document.getElementById( 'quadroB' ).disabled = true;
        document.getElementById( 'quadroC' ).disabled = true;
        document.getElementById( 'trascrizioneDelInput' ).disabled = true;
        document.getElementById( 'numeroTrascrizione1' ).disabled = true;
        document.getElementById( 'numeroTrascrizione2' ).disabled = true;
        document.getElementById( 'valoreImmobLib' ).disabled = true;
        document.getElementById( 'volturaCatastale' ).disabled = true;

        var id = document.getElementById( 'tipoNotaCombo' ).value;
        var idann = document.getElementById( 'tipoComunicAnn' ).value;
        var idtrasc = document.getElementById( 'tipoComunicTrasc' ).value;

        if( id == "A" )
        {

            document.getElementById( "divTrascrizione" ).style.display = 'none';
            document.getElementById( "attoSpecie.T" ).style.display = 'none';
            if( idann != -1 )
            {
                document.getElementById( "Trasc" ).style.display = 'none';
            }else
            {
                document.getElementById( "Ann" ).style.display = 'none';
            }
        }else if( id == "T" )
        {
            document.getElementById( "divTrascrizione" ).style.display = 'inline-block';
            document.getElementById( "attoSpecie.T" ).style.display = 'inline-block';
            document.getElementById( "divAnnotazione" ).style.display = 'none';
            document.getElementById( "attoSpecie.A" ).style.display = 'none';
        }
    }

    function abilitaNumeri()
    {
        if( document.getElementById( 'trascrizioneDelInput' ) != null )
        {
            var dataTraDel = document.getElementById( 'trascrizioneDelInput' ).value;
            if( dataTraDel == '' )
            {
                document.getElementById( 'numeroTrascrizione1' ).value = '';
                document.getElementById( 'numeroTrascrizione2' ).value = '';
                document.getElementById( 'numeroTrascrizione1' ).disabled = true;
                document.getElementById( 'numeroTrascrizione2' ).disabled = true;
            }else
            {
                document.getElementById( 'numeroTrascrizione1' ).disabled = false;
            }
            var numero = document.getElementById( 'numeroTrascrizione1' ).value;
            if( numero == '' )
            {
                document.getElementById( 'numeroTrascrizione2' ).value = '';
                document.getElementById( 'numeroTrascrizione2' ).disabled = true;
            }else
            {
                document.getElementById( 'numeroTrascrizione2' ).disabled = false;
            }

            if( document.getElementById( 'trascrizioneDelInput' ).disabled == true )
            {
                document.getElementById( 'numeroTrascrizione1' ).disabled = true;
                document.getElementById( 'numeroTrascrizione2' ).disabled = true;
            }
        }

        if( document.getElementById( 'dataFormalita' ) != null )
        {
            var dataFormal = document.getElementById( 'dataFormalita' ).value;
            if( dataFormal == '' )
            {
                document.getElementById( 'numeFormalita1' ).value = '';
                document.getElementById( 'numeFormalita2' ).value = '';
                document.getElementById( 'numeFormalita1' ).disabled = true;
                document.getElementById( 'numeFormalita2' ).disabled = true;
            }else
            {
                document.getElementById( 'numeFormalita1' ).disabled = false;
            }
            var numeroFor = document.getElementById( 'numeFormalita1' ).value;
            if( numeroFor == '' )
            {
                document.getElementById( 'numeFormalita2' ).value = '';
                document.getElementById( 'numeFormalita2' ).disabled = true;
            }else
            {
                document.getElementById( 'numeFormalita2' ).disabled = false;
            }
        }
    }

    function settaDescrizioneT( valore )
    {
        var e = document.getElementById( "tipoComunicTrasc" );
        var strUser = e.options[e.selectedIndex].text;
        document.getElementById( 'hiddenDescrAtto' ).value = strUser;
    }

    function settaDescrizioneA( valore )
    {
        var e = document.getElementById( "tipoComunicAnn" );
        var strUser = e.options[e.selectedIndex].text;
        document.getElementById( 'hiddenDescrAtto' ).value = strUser;
    }
</script>

<script type="text/javascript">
	doAutocompleteConservat('Comune');
</script>

<script type="text/javascript">
	$(document).ready(function() {
		prendiData("#dataTitolo", true);
		prendiData("#dataFormalita", true);
		prendiData("#dataDal", true);
		prendiData("#trascrizioneDelInput", true);
	});
</script>