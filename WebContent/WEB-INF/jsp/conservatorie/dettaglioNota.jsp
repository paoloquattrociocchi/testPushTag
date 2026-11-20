<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
	<p class="titolo_page">
		<spring:message code="conservatoria.titolo.ricerca.dettaglio" />		
	</p>
    <spring:url var="dettaglio" value="/conservatorie/dettaglioNota.htm" />
    <spring:url var="azione" value="/conservatorie/visualizzaComunicazioniConservatoria.htm" />
	<form:form id="id_formRicerca" method="post" action="${azione}" commandName="conservatoriaDettaglioNotaForm">
	
	<br />

		<table class="tb_form" cellspacing="0" cellpadding="0" border="0" >
			<tr>
				<td width="15%"><form:label path="numero"><spring:message code="conservatoria.numeroComunicazione" /></form:label></td>
				<td width="20%" style="font-weight:bold" ><c:out value="${conservatoriaDettaglioNotaForm.numero}" /></td>
				<td width="15%">
					<form:label path="stato"><spring:message code="conservatoria.stato" /></form:label>
					&nbsp;&nbsp;
					<c:out value="${conservatoriaDettaglioNotaForm.descStato}" /></td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><form:label path="descTipoNota"><spring:message code="conservatoria.tipo" /></form:label></td>
				<td colspan="3" ><c:out value="${conservatoriaDettaglioNotaForm.descTipoNota}" /></td>
			</tr>
			<tr>
				<td><form:label path="descCatAtto"><spring:message code="conservatoria.tipoAtto" /></form:label></td>
				<td colspan="3"><c:out value="${conservatoriaDettaglioNotaForm.descCatAtto}" /></td>
			</tr>
			<tr>
				<td><form:label path="repertorioAnno"><spring:message code="conservatoria.repertorioAnno" /></form:label></td>
				<td><c:out value="${conservatoriaDettaglioNotaForm.repertorioAnno}" /></td>
				<td>
					<form:label path="numeroProcedimento"><spring:message code="conservatoria.numero" /></form:label>
					&nbsp;&nbsp;
					<c:out value="${conservatoriaDettaglioNotaForm.numeroRepertorio1}" />
				</td>	
				<td>
					<form:label path="progressivo"><spring:message code="conservatoria.progressivo" /></form:label>
					&nbsp;&nbsp;
					<c:out value="${conservatoriaDettaglioNotaForm.progressivo}" />
				</td>		
			</tr>
		</table>
	<br />
         <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
           <tr>
              <td class="td_titolo"><spring:message code="conservatoria.datiGenerali" /></td>
           </tr>
        </table>
		<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<td width="15%"><form:label path="titoloDescrizione"> 
						<spring:message code="conservatoria.titoloDescrizione" />
					</form:label>
				</td>
				<td width="20%"><c:out value="${conservatoriaDettaglioNotaForm.titoloDescrizione}" /></td>
				<td width="22%">
					<form:label path="data"><spring:message code="conservatoria.data" /></form:label>
					&nbsp;&nbsp;
					<fmt:formatDate value="${conservatoriaDettaglioNotaForm.data}" type="date" pattern="dd/MM/yyyy" />
				</td> 		
				<td colspan="2">&nbsp;</td>				
			</tr>
			<tr>
				<td ><form:label path="pubblicoUfficiale"> 
						<spring:message code="conservatoria.pubblicoUfficiale" />
					</form:label>
				</td>
				<td><c:out value="${conservatoriaDettaglioNotaForm.pubblicoUfficiale}" /></td>
				<td>
					<form:label path="categoria"><spring:message code="conservatoria.categoria" /></form:label>
					&nbsp;&nbsp;
					<c:out value="${conservatoriaDettaglioNotaForm.descTipoPubblUff}" />
				</td> 
				<td colspan="2"><form:label path="codiceFiscale">
						<spring:message code="conservatoria.codiceFiscalePU" />
					</form:label>
					&nbsp;&nbsp;
					<c:out value="${conservatoriaDettaglioNotaForm.codiceFiscale}" /></td> 							
			</tr>
			<tr>
				<td ><form:label path="sede"><spring:message code="conservatoria.sede" /></form:label></td>
				<td ><c:out value="${conservatoriaDettaglioNotaForm.sede}" /></td>
				<td colspan="3">
					<form:label path="provincia"><spring:message code="conservatoria.provincia" /></form:label>
					&nbsp;&nbsp;
					<c:out value="${conservatoriaDettaglioNotaForm.provincia}" />
				</td> 						
			</tr>
			<tr>
				<td><form:label path="attoSpecie"><spring:message code="conservatoria.attoSpecie" /></form:label></td>
				<td ><c:out value="${conservatoriaDettaglioNotaForm.attoSpecie}" /></td>
				<td colspan="3">
					<form:label path="tipoComunic"><spring:message code="conservatoria.descrizione" /></form:label>
					&nbsp;&nbsp;
					<c:out value="${conservatoriaDettaglioNotaForm.descAtto}" />
				</td>
			</tr>
			<tr>
				<td ><form:label path="conservatoria"><spring:message code="conservatoria.conservatorie" /></form:label></td>
				<td colspan="4"><c:out value="${conservatoriaDettaglioNotaForm.conservatoria}" /></td>
			</tr>
			<tr>
				 <td><form:label path="unitaNegoziali"><spring:message code="conservatoria.unitaNegoziali" /></form:label></td>
				 <td><c:out value="${conservatoriaDettaglioNotaForm.unitaNegoziali}" /></td>
				 <td >
				 	<form:label path="immobili"><spring:message code="conservatoria.immobili" /></form:label>
				 	&nbsp;&nbsp;
				 	<c:out value="${conservatoriaDettaglioNotaForm.immobili}" />
				 </td>
				 <td >
				 	<form:label path="soggettiFavore"><spring:message code="conservatoria.soggettiFavore" /></form:label>
				 	&nbsp;&nbsp;
				 	<c:out value="${conservatoriaDettaglioNotaForm.soggettiFavore}" />
				 </td>
				 <td >
				 	<form:label path="soggettiContro"><spring:message code="conservatoria.soggettiContro" /></form:label>
				 	<c:out value="${conservatoriaDettaglioNotaForm.soggettiContro}" />
				 </td>
			</tr>
		</table>	
	<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="conservatoria.elencoBeni" /></td>
				</tr>
			</table>
			
			<display:table class="tb_lista" uid="listaBeni" name="${conservatoriaDettaglioNotaForm.listaBeni}" pagesize="10" requestURI="${dettaglio}">
					<display:column class="${listaBeni.stileDisplay}" property="PROGRESSIVO" title="Progressivo" sortable="true" />
					<display:column class="${listaBeni.stileDisplay}" property="PROGUNEG" title="Progr Uneg" sortable="true" />
					<display:column class="${listaBeni.stileDisplay}" property="progUnegBene" title="Progr Uneg Bene" sortable="true" />
					<display:column class="${listaBeni.stileDisplay}" property="descrizione" title="Descrizione" sortable="true" />
					<display:column class="${listaBeni.stileDisplay}" property="catasto" title="Catasto" sortable="true" />					
					<display:column class="${listaBeni.stileDisplay}" property="comune" title="Comune" sortable="true" />	
					<display:column class="${listaBeni.stileDisplay}" property="beneSMP" title="Identificativo bene" sortable="true" />	
			</display:table>
	<br />
	<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="conservatoria.elencoSoggetti" /></td>
				</tr>
			</table>
			
			<display:table class="tb_lista" uid="listaSoggetti" 
									name="${conservatoriaDettaglioNotaForm.listaSoggetti}" pagesize="10" requestURI="${dettaglio}">
					<display:column class="${listaSoggetti.stileDisplay}" property="progressivo" title="Progressivo" sortable="true" />
					<display:column class="${listaSoggetti.stileDisplay}" property="descTipologia" title="Tipologia" sortable="true" />
					<display:column class="${listaSoggetti.stileDisplay}" property="soggetto" title="Soggetto" sortable="true" />
					<%-- 
					<display:column class="${listaSoggetti.stileDisplay}" property="soggettoSitMp" title="SoggettoSMP" sortable="true" />
					--%>
			</display:table>
			<br />
	     		
     		<table class="tb_bottoni" align="center">
         		<tr>
         	 		<td class="td_tasto"><input type="submit" value="Indietro" name="Indietro" title="Indietro"></td>
               	</tr>
			</table>
	     		
	</form:form>
			
	<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
	<smp:back buttonId="backButton"></smp:back>
		
	</tiles:putAttribute>
</tiles:insertDefinition>