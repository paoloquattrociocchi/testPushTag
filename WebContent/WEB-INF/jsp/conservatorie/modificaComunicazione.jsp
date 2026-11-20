<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<p class="titolo_page">
			<spring:message code="conservatoria.titolo.modifica.comunicazione" />
		</p>
		<spring:url var="azione" value="/conservatorie/modificaComunicazione.htm" />
		<form:form id="id_formRicerca" method="post" action="${azione}" commandName="comunicazioneModificaForm">
			
				<form:hidden path="rettificaSelez" />
				<form:hidden path="conservatoriaSelez" />
				<form:hidden path="numeroComunicazione" />
				<form:hidden path="tipoNotaId" />
				<form:hidden path="descTipoNota" />
				<form:hidden path="repertorioAnno" />
				<form:hidden path="numero" />
				<form:hidden path="progressivo" />
				<form:hidden path="categorieAtti" />
				<form:hidden path="categoria" />
				<form:hidden path="attoSpecie" />
				<form:hidden path="stato" />
				<form:hidden path="provincia" />
				<form:hidden path="unitaNegoziali" />
				<form:hidden path="immobili" />
				<form:hidden path="soggettiFavore" />
				<form:hidden path="soggettiContro" />
				
				<form:hidden id="hiddenDescrAtto" path="descAtto" />

			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="15%" style="font-weight: bold">
						<form:label path="numeroComunicazione">
							<spring:message code="conservatoria.numeroComunicazione" />
						</form:label> 
					</td>
					<td colspan="2"><c:out value="${comunicazioneModificaForm.numeroComunicazione}" /></td>
				</tr>
				<tr>
					<td>
						<form:label path="descTipoNota">
							<spring:message code="conservatoria.tipoNota" />
						</form:label>
					</td>
					<td colspan="2"><c:out value="${comunicazioneModificaForm.descTipoNota}" /></td>
				</tr>
				<tr>
					<td>
						<form:label path="categorieAtti">
							<spring:message code="conservatoria.tipoAtto" />
						</form:label>
					</td>
					<td colspan="2"><c:out value="${comunicazioneModificaForm.categorieAtti}" /></td>
				</tr>
				<tr>
					<td>
						<form:label path="repertorioAnno">
							<spring:message code="conservatoria.repertorioAnno" />
						</form:label>
					</td>
					<td><c:out value="${comunicazioneModificaForm.repertorioAnno}" /></td>
					<td>
						<form:label path="numero">
							<spring:message code="conservatoria.numero" />
						</form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneModificaForm.numero}" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<form:label path="progressivo">
							<spring:message code="conservatoria.progressivo" />
						</form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneModificaForm.progressivo}" />
					</td>
				</tr>
			</table>
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="1">
				<tr>
					<td class="td_titolo"><spring:message code="conservatoria.datiGenerali" /></td>
				</tr>
			</table>
			
			<fmt:formatDate pattern="dd/MM/yyyy" value="${comunicazioneModificaForm.dataTitolo}" var="myDateVar" />
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="10%"><form:label path="titoloDescrizione" cssClass="required"><spring:message code="conservatoria.titoloDescrizione" /></form:label></td>
					<td width="21%">
						<form:input path="titoloDescrizione" id="titoloDescrizione" maxlength="61" size="50" />
					</td>
					<td colspan="2">
						<form:label path="dataTitolo" cssClass="required"><spring:message code="conservatoria.data" /></form:label>
						&nbsp;&nbsp;
						<form:input path="dataTitolo" id="dataTitolo" maxlength="10" size="10" value="${myDateVar}" />
					</td>
				</tr>
				<tr>
					<td>
						<form:label path="pubblicoUfficiale" cssClass="required"><spring:message code="conservatoria.pubblicoUfficiale" /></form:label>
					</td>
					<td><form:input path="pubblicoUfficiale" id="pubblicoUfficiale" maxlength="50" size="50" /></td>
					<td>
						<form:label path="categoria"><spring:message code="conservatoria.categoria" /></form:label>
						&nbsp;&nbsp;
						<c:out value="Altro Pubblico Ufficiale" />
					</td>
					<td>
						<form:label path="codiceFiscale"  cssClass="required"><spring:message code="conservatoria.codiceFiscalePU" /></form:label>
						&nbsp;&nbsp;
						<form:input path="codiceFiscale" id="codiceFiscale"	maxlength="16" size="24" />
					</td>
				</tr>
				<tr>
					<td>
						<form:label path="comune" cssClass="required">
								<spring:message code="conservatoria.sede" />
						</form:label> 
					</td>
					<td colspan="3" align="left">
						<div class="ui-widget">
							<form:input path="comune.descrizione"
								id="id_descrizioneComune" size="70"
								title="Immettere almeno 2 lettere per avviare la ricerca"
								value="${comunicazioneModificaForm.sede}" />
							<div id="id_loadingComune"></div> 
								<form:hidden id="id_hiddenComune" path="comune.id" />
						</div>
					</td>
				</tr>
			</table>	
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
			<c:if test="${comunicazioneModificaForm.tipoNotaId=='T'}">
				<tr>
					<td width="10%">
					<form:label path="attoSpecie" cssClass="required">
							<spring:message code="conservatoria.attoSpecie" />
						</form:label>
					</td>
					<td><c:out value="${comunicazioneModificaForm.attoSpecie}" /></td>
					<td align="centre">
						<form:label path="rettifica">
							<spring:message code="conservatoria.rettifica" />
						</form:label>
					</td>
					<td>	
						<form:select path="rettifica.id" id="rettifica.id">
							<form:option value="-1" label="- Selezionare Rettifica -" />
							<c:choose>
								<c:when
									test="${comunicazioneModificaForm.rettificaSelez == '7'}">
									<form:option value="7" label="D'Ufficio" selected="true" />
								</c:when>
								<c:otherwise>
									<form:option value="7" label="D'Ufficio" />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when
									test="${comunicazioneModificaForm.rettificaSelez == '8'}">
									<form:option value="8" label="Alla nota" selected="true" />
								</c:when>
								<c:otherwise>
									<form:option value="8" label="Alla nota" />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when
									test="${comunicazioneModificaForm.rettificaSelez == '9'}">
									<form:option value="9" label="Al titolo" selected="true" />
								</c:when>
								<c:otherwise>
									<form:option value="9" label="Al titolo" />
								</c:otherwise>
							</c:choose>
						</form:select> <form:errors path="rettifica" class="testoRosso" /></td>
					<td align="centre">
						<form:label path="tipoComunic"  cssClass="required">
							<spring:message code="conservatoria.descrizione" />
						</form:label> 
					</td>
					<td>
					<td><form:input path="tipoComunicSelez" id="tipoComunic.id" maxlength="61" size="50"/></td>
						<form:errors path="tipoComunic" class="testoRosso" />
					</td>
				</tr>
			</c:if>
			<c:if test="${comunicazioneModificaForm.tipoNotaId=='A' }">
				<tr>
					<td width="10%">
						<form:label path="attoSpecie" cssClass="required">
							<spring:message code="conservatoria.attoSpecie" />
						</form:label>
					</td>	
					<td width="35%"><c:out value="${comunicazioneModificaForm.attoSpecie}" /></td>
					<td width="10%">
						<form:label path="tipoComunic" cssClass="required">
							<spring:message code="conservatoria.descrizione" />
						</form:label> 
					</td>
					<td>	
					<%-- COMBO DESCRIZIONE ATTO --%>
					<c:if test="${comunicazioneModificaForm.attoSpecie =='ANNOTAZIONE AD ANNOTAZIONE'}">
						<form:select path="tipoComunicSelez" id="tipoComunicAnn" value="${comunicazioneModificaForm.tipoComunicSelez}" onchange="settaDescrizioneAnn(this)">
							<form:options items="${tipoComunicTrasc}" itemValue="id" itemLabel="descrizione" />
						</form:select>
						<form:errors path="tipoComunicSelez" class="testoRosso" />
					</c:if>
					<c:if test="${comunicazioneModificaForm.attoSpecie =='ANNOTAZIONE A TRASCRIZIONE'}">
						<form:select path="tipoComunicSelez" id="tipoComunicTrasc" value="${comunicazioneModificaForm.tipoComunicSelez}" onchange="settaDescrizioneTrasc(this)">
							<form:options items="${tipoComunicAnn}" itemValue="id" itemLabel="descrizione" />
						</form:select> 
						<form:errors path="tipoComunicSelez" class="testoRosso" />
					</c:if>
					<%--Attributo passa il valore della descrizione --%>
					</td>
				</tr>
			</c:if>
		</table>
		<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td width="10%"><form:label path="conservatoria" cssClass="required">
						<spring:message code="conservatoria.conservatorie" />
					</form:label>
				</td>	
				<td>
					<c:set var="consSelezionata" value="${comunicazioneModificaForm.conservatoriaSelez}" /> 
			
					<form:select path="conservatoria.id" id="conservatoriaCombo" name="conservatoriaCombo"
                           onchange="setDescrizione( this.children[this.selectedIndex].innerText, 'hiddenDescrConservatoria' )">
						<%-- <form:option value="-1" label="- Selezionare Conservatoria -" /> --%>
						<c:forEach items="${conservatoria}" var="conserv" varStatus="status">
							<c:choose>
								<c:when test="${conserv.descrizione eq consSelezionata}">
									<option value="${conserv.id}" selected="true">${conserv.descrizione}</option>
								</c:when>
								<c:otherwise>
									<option value="${conserv.id}">${conserv.descrizione}</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</form:select>
                       <form:errors path="conservatoria" class="testoRosso" /> 
                       <form:hidden id="hiddenDescrConservatoria" path="descrConservatoria" /></td>
				</td>
			</tr>
			</table>
			</br>
			<table class="tb_form" cellspacing="1" cellpadding="1" border="0">
				<tr>
					<td><form:label path="unitaNegoziali">
							<spring:message code="conservatoria.unitaNegoziali" />
						</form:label> &nbsp;&nbsp; <c:out value="${comunicazioneModificaForm.unitaNegoziali}" /></td>
					<td><form:label path="immobili">
							<spring:message code="conservatoria.immobili" />
						</form:label> &nbsp;&nbsp; <c:out value="${comunicazioneModificaForm.immobili}" /></td>
					<td><form:label path="soggettiFavore">
							<spring:message code="conservatoria.soggettiFavore" />
						</form:label> &nbsp;&nbsp; <c:out value="${comunicazioneModificaForm.soggettiFavore}" /></td>
					<td><form:label path="soggettiContro">
							<spring:message code="conservatoria.soggettiContro" />
						</form:label> &nbsp;&nbsp; <c:out value="${comunicazioneModificaForm.soggettiContro}" /></td>
				</tr>
			</table>
			<c:if test="${comunicazioneModificaForm.tipoNotaId=='A' }">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="1">
					<tr>
						<td class="td_titolo"><spring:message
								code="conservatoria.datiRelAnnotazione" /></td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td colspan="2"><form:label path="formalita">
								<spring:message code="conservatoria.formalita" />
							</form:label></td>
					</tr>
					<tr>
						<td><form:label path="trascrizioneDel" id="trascrizioneDel" cssClass="required">
								<spring:message code="conservatoria.trascrizioneDel" />
							</form:label> &nbsp;&nbsp; <form:input path="trascrizioneDel"
								id="trascrizioneDelInput" maxlength="10" size="11" 
								onchange="abilitaNumeri()" /></td>
						<td><form:label path="numeroTrascrizione1"  cssClass="required">
								<spring:message code="conservatoria.numero" />
							</form:label> &nbsp;&nbsp; <form:input path="numeroTrascrizione1"
								id="numeroTrascrizione1" maxlength="6" size="6"
								onchange="abilitaNumeri()" />
							&nbsp;&nbsp; <form:input path="numeroTrascrizione2"
								id="numeroTrascrizione2" maxlength="3" size="3" /></td>
					</tr>
					<tr>
						<td><form:label path="valoreImmobLib">
								<spring:message code="conservatoria.valoreImmobLib" />
							</form:label> &nbsp;&nbsp; 
							<form:input path="valoreImmobLib" id="valoreImmobLib" cssClass="numerico" maxlength="14" size="20" /> 
						</td>
						<td><form:checkbox path="volturaCatastale" id="volturaCatastale" /> 
							<spring:message code="conservatoria.volturaCatastale" />
						</td>
					<tr>
				</table>
			</c:if>
			<c:if test="${comunicazioneModificaForm.tipoNotaId=='T' }">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="1">
					<tr>
						<td class="td_titolo"><spring:message
								code="conservatoria.datiRelTrascrizione" /></td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:checkbox path="attoSoggVoltura"
								id="attoSoggVoltura" /> <spring:message
								code="conservatoria.attoSoggVoltura" /></td>
						<td colspan="2"><form:checkbox path="differita" id="differita" /> <spring:message
								code="conservatoria.differita" /> &nbsp;&nbsp; <form:input
								path="dataDal" id="dataDal" /></td>
					</tr>
					<tr>
						<td><form:checkbox path="presenzaCondizione"
								id="presenzaCondizione" /> <spring:message
								code="conservatoria.presenzaCondizione" /></td>
						<td colspan="2"><form:checkbox path="presenzaTermini"
								id="presenzaTermini" /> <spring:message
								code="conservatoria.presenzaTermini" /></td>
					</tr>
					<tr>
						<td><form:label path="dataFormalita">
								<spring:message code="conservatoria.dataFormalita" />
							</form:label> &nbsp;&nbsp; <form:input path="dataFormalita" id="dataFormalita"
								onchange="abilitaNumeri()" /></td>
						<td colspan="2"><form:label path="numeroFormalita1">
								<spring:message code="conservatoria.numero" />
							</form:label> &nbsp;&nbsp; <form:input path="numeroFormalita1"
								id="numeroFormalita1" maxlength="6" size="6"
								onchange="abilitaNumeri()" />
							&nbsp;&nbsp; <form:input path="numeroFormalita2"
								id="numeroFormalita2" maxlength="3" size="3" /></td>
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
			</c:if>
			<c:if test="${comunicazioneModificaForm.stato=='0' || comunicazioneModificaForm.stato=='1' }">
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" value="Unità Negoziali" name="UnitaNegoziali" title="UnitaNegoziali"></td>
						<td class="td_tasto"><input type="submit" value="Conferma"	name="Conferma" title="Conferma" ></td>
						<td class="td_tasto"><input type="submit" value="Cancella"	name="Cancella" title="Cancella" onClick="return popUpConferma()" class="nowait"></td>
					</tr>
				</table>
			</c:if>
		</form:form>
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
	</tiles:putAttribute>
</tiles:insertDefinition>


<script type="text/javascript">
	$(document).ready(function() {
		abilitaNumeri();
	});

	function abilitaNumeri() 
	{
		if( document.getElementById('trascrizioneDelInput') != null )
		{
			var dataTraDel = document.getElementById('trascrizioneDelInput').value;
			if (dataTraDel == '') {
				document.getElementById('numeroTrascrizione1').value = '';
				document.getElementById('numeroTrascrizione2').value = '';
				document.getElementById('numeroTrascrizione1').disabled = true;
				document.getElementById('numeroTrascrizione2').disabled = true;
			} else {
				document.getElementById('numeroTrascrizione1').disabled = false;
			}
			var numero = document.getElementById('numeroTrascrizione1').value;
			if (numero == '') {
				document.getElementById('numeroTrascrizione2').value = '';
				document.getElementById('numeroTrascrizione2').disabled = true;
			} else {
				document.getElementById('numeroTrascrizione2').disabled = false;
			}
		}

		if( document.getElementById('dataFormalita') != null )
		{
			var dataFormal = document.getElementById('dataFormalita').value;
			if (dataFormal == '') {
				document.getElementById('numeroFormalita1').value = '';
				document.getElementById('numeroFormalita2').value = '';
				document.getElementById('numeroFormalita1').disabled = true;
				document.getElementById('numeroFormalita2').disabled = true;
			} else {
				document.getElementById('numeroFormalita1').disabled = false;
			}
			var numeroFor = document.getElementById('numeroFormalita1').value;
			if (numeroFor == '') {
				document.getElementById('numeroFormalita2').value = '';
				document.getElementById('numeroFormalita2').disabled = true;
			} else {
				document.getElementById('numeroFormalita2').disabled = false;
			}
		}
	}
	
	function settaDescrizioneAnn (valore) {
		var e = document.getElementById("tipoComunicAnn");
		var strUser = e.options[e.selectedIndex].text;
		document.getElementById('hiddenDescrAtto').value = strUser;
	}
	
	function settaDescrizioneTrasc (valore) {
		var e = document.getElementById("tipoComunicTrasc");
		var strUser = e.options[e.selectedIndex].text;
		document.getElementById('hiddenDescrAtto').value = strUser;
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

    function setDescrizione( valore, target ) 
    {
        if (valore != '-1')
            document.getElementById( target ).value = valore;
    }
	
	function popUpConferma() 
	{
		if (confirm("Confermi l'operazione ?")) 
			return true;
		else 
			return false;
	}
</script>