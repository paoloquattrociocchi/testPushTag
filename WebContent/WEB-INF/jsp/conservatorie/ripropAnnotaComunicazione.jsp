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

	<c:if test="${comunicazioneRipropAnnotaForm.operazione=='A'}">
        <spring:url var="azione" value="/conservatorie/annotazioneComunicazione.htm" />
		<p class="titolo_page">
			<spring:message code="conservatoria.titolo.annotazioneComunicazione" />
		</p>
	</c:if>
	<c:if test="${comunicazioneRipropAnnotaForm.operazione=='R'}">
        <spring:url var="azione" value="/conservatorie/riproposizioneComunicazione.htm" />
		<p class="titolo_page">
			<spring:message code="conservatoria.titolo.riproposizioneComunicazione" />
		</p>
	</c:if>
	<c:if test="${comunicazioneRipropAnnotaForm.operazione=='E'}">
        <spring:url var="azione" value="/conservatorie/esportazioneComunicazione.htm" />
		<p class="titolo_page">
			<spring:message code="conservatoria.titolo.esportazioneComunicazione" />
		</p>
	</c:if>
		<form:form id="id_formAnnotazione" method="post" action="${azione}" commandName="comunicazioneRipropAnnotaForm">
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="15%"><form:label path="numero"><spring:message code="conservatoria.numeroComunicazione" /></form:label></td>
					<td width="20%" style="font-weight: bold"><c:out value="${comunicazioneRipropAnnotaForm.numeroComunicazione}" /></td>
					<td width="8%" align="center">
						<form:label path="stato"><spring:message code="conservatoria.stato" /></form:label>
					</td>	
					<td  width="10%">
						<div id="cella_descStato" style="display: inline-block; width: 100%;">
							<c:out value="${comunicazioneRipropAnnotaForm.descStato}" />
						</div>
						<div id="cella_inviata" style="display: none; width: 100%;">
							<c:out value="Inviata" />
						</div>
					</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><form:label path="descTipoNota"><spring:message code="conservatoria.tipo" /></form:label></td>
					<td colspan="4"><c:out value="${comunicazioneRipropAnnotaForm.descTipoNota}" /></td>
				</tr>
				<tr>
					<td><form:label path="descCatAtto"><spring:message code="conservatoria.tipoAtto" /></form:label></td>
					<td colspan="4"><c:out value="${comunicazioneRipropAnnotaForm.descCatAtto}" /></td>
				</tr>
				<tr>
					<td><form:label path="repertorioAnno"><spring:message code="conservatoria.repertorioAnno" /></form:label></td>
					<td><c:out value="${comunicazioneRipropAnnotaForm.repertorioAnno}" /></td>
					<td>
						<form:label path="numeroProcedimento"><spring:message code="conservatoria.numero" /></form:label>
					</td>
					<td align="left"><c:out value="${comunicazioneRipropAnnotaForm.numero}" /></td>
					<td>
						<form:label path="progressivo"><spring:message code="conservatoria.progressivo" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneRipropAnnotaForm.progressivo}" />
					</td>
				</tr>
				<tr>
			</table>
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="1">
				<tr>
					<td class="td_titolo"><spring:message code="conservatoria.datiGenerali" /></td>
				</tr>
			</table>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="15%"><form:label path="titoloDescrizione"><spring:message code="conservatoria.titoloDescrizione" /></form:label></td>
					<td width="20%"><c:out value="${comunicazioneRipropAnnotaForm.titoloDescrizione}" /></td>
					<td colspan="2">
						<form:label path="data"><spring:message code="conservatoria.data" /></form:label>
						&nbsp;&nbsp;
						<fmt:formatDate value="${comunicazioneRipropAnnotaForm.dataTitolo}" type="date" pattern="dd/MM/yyyy" />
					</td>
				</tr>
				<tr>
					<td><form:label path="pubblicoUfficiale">
							<spring:message code="conservatoria.pubblicoUfficiale" />
						</form:label>
					</td>
					<td><c:out value="${comunicazioneRipropAnnotaForm.pubblicoUfficiale}" /></td>
					<td>
						<form:label path="categoria"><spring:message code="conservatoria.categoria" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneRipropAnnotaForm.descTipoPubblUff}" />
					</td>
					<td>
						<form:label path="codiceFiscale"><spring:message code="conservatoria.codiceFiscalePU" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneRipropAnnotaForm.codiceFiscale}" />
					</td>
				</tr>
				<tr>
					<td><form:label path="sede"><spring:message code="conservatoria.sede" /></form:label></td>
					<td><c:out value="${comunicazioneRipropAnnotaForm.sede}" /></td>
					<td colspan="2">
						<form:label path="provincia"><spring:message code="conservatoria.provincia" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneRipropAnnotaForm.provincia}" />
					</td>
				</tr>
				<tr>
					<td><form:label path="attoSpecie">
							<spring:message code="conservatoria.attoSpecie" />
						</form:label></td>
					<td>
						<c:out value="${comunicazioneRipropAnnotaForm.attoSpecie}" />
					</td>
					<td colspan="2">
						<form:label path="descAtto"><spring:message code="conservatoria.descrizione" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneRipropAnnotaForm.descAtto}" />
					</td>
				</tr>
				<tr>
					<td><form:label path="conservatoria"><spring:message code="conservatoria.conservatorie" /></form:label></td>
					<td colspan="3"><c:out value="${comunicazioneRipropAnnotaForm.conservatoria}" /></td>
				</tr>
				<tr>
					<td>
						<form:label path="unitaNegoziali"><spring:message code="conservatoria.unitaNegoziali" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneRipropAnnotaForm.unitaNegoziali}" />
					</td>
					<td>
						<form:label path="immobili"><spring:message code="conservatoria.immobili" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneRipropAnnotaForm.immobili}" />
					</td>
					<td>
						<form:label path="soggettiFavore"><spring:message code="conservatoria.soggettiFavore" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneRipropAnnotaForm.soggettiFavore}" />
					</td>
					<td>
						<form:label path="soggettiContro"><spring:message code="conservatoria.soggettiContro" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneRipropAnnotaForm.soggettiContro}" />
					</td>
				</tr>
			</table>
			<br />
			<%-- INIZIO DATI RELATIVI --------------------------------------------------------------------------------------------- --%>
			
			<%-- TRASCRIZIONE --------------------------------------------------------------------------------------------- --%>

			<c:if test="${comunicazioneRipropAnnotaForm.tipoNotaId=='T'}">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="1">
					<tr>
						<td class="td_titolo"><spring:message
								code="conservatoria.datiRelTrascrizione" /></td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td> 
							<form:checkbox path="attoSoggVoltura" id="attoSoggVoltura" disabled="true"/>
							<spring:message code="conservatoria.attoSoggVoltura" />
						</td>
						<td colspan="2">
							<form:checkbox path="differita" id="differita" disabled="true"/>
							<spring:message code="conservatoria.differita" /> &nbsp;&nbsp; 
							<form:input path="dataDal" id="dataDal" disabled="true"/>
						</td>
					</tr>
					<tr>
						<td>
							<form:checkbox path="presenzaCondizione" id="presenzaCondizione" disabled="true"/>
							<spring:message code="conservatoria.presenzaCondizione" />
						</td>
						<td colspan="2">
							<form:checkbox path="presenzaTermini" id="presenzaTermini" disabled="true" />
							<spring:message code="conservatoria.presenzaTermini" />
						</td>
					</tr>
					<tr>
						<td>
							<form:label path="dataFormalita">
								<spring:message code="conservatoria.dataFormalita" />
							</form:label> &nbsp;&nbsp; 
							<form:input path="dataFormalita" id="dataFormalita" disabled="true"/>
						</td>
						<td colspan="2"><form:label path="numeroFormalita1">
								<spring:message code="conservatoria.numero" />
							</form:label> &nbsp;&nbsp; 
							<form:input path="numeroFormalita1" id="numeroFormalita1"  maxlength="6" size="6" disabled="true"/>
							&nbsp;&nbsp; 
							<form:input path="numeroFormalita2" id="numeroFormalita2"  maxlength="3" size="3" disabled="true"/>
						</td>
					</tr>
					<tr>
						<td>
							<spring:message code="conservatoria.quadroA" />
							<c:if test="${comunicazioneRipropAnnotaForm.quadroATrascrizione=='OK'}">
								<form:checkbox path="quadroA" id="quadroA" checked="true"  disabled="true" />
							</c:if>
							<c:if test="${comunicazioneRipropAnnotaForm.quadroATrascrizione=='NO'}">
								<form:checkbox path="quadroA" id="quadroA"  disabled="true" />
							</c:if>
						</td>
						<td>
							<spring:message code="conservatoria.quadroB" />
							<c:if test="${comunicazioneRipropAnnotaForm.quadroBTrascrizione=='OK'}">
								<form:checkbox path="quadroB" id="quadroB" checked="true" disabled="true" />
							</c:if>
							<c:if test="${comunicazioneRipropAnnotaForm.quadroBTrascrizione=='NO'}">
								<form:checkbox path="quadroB" id="quadroB"  disabled="true" />
							</c:if>
						</td>
						<td>
							<spring:message code="conservatoria.quadroC" />
							<c:if test="${comunicazioneRipropAnnotaForm.quadroCTrascrizione=='OK'}">
								<form:checkbox path="quadroC" id="quadroC" checked="true" disabled="true" />
							</c:if>
							<c:if test="${comunicazioneRipropAnnotaForm.quadroCTrascrizione=='NO'}">
								<form:checkbox path="quadroC" id="quadroC"  disabled="true" />
							</c:if> 
						</td>
					</tr>
				</table>
			</c:if>
			
			<%-- ANNOTAZIONE --------------------------------------------------------------------------------------------- --%>
			<c:if test="${comunicazioneRipropAnnotaForm.tipoNotaId=='A'}">

				<table class="tb_form" cellspacing="0" cellpadding="0" border="1">
					<tr>
						<td class="td_titolo"><spring:message
								code="conservatoria.datiRelAnnotazione" /></td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td colspan="2">
							<form:label path="formalita">
								<spring:message code="conservatoria.formalita" />
							</form:label></td>
					</tr>
					<tr>
						<td width="35%">
							<form:label path="trascrizioneDel" id="trascrizioneDel">
								<spring:message code="conservatoria.trascrizioneDel" />
							</form:label> &nbsp;&nbsp;
							<fmt:formatDate value="${comunicazioneRipropAnnotaForm.trascrizioneDel}" type="date" pattern="dd/MM/yyyy" />
						</td>
						<td>
							<form:label path="numeroTrascrizione1">
								<spring:message code="conservatoria.numero" />
							</form:label> &nbsp;&nbsp; 
							<c:out value="${comunicazioneRipropAnnotaForm.numeroTrascrizione1}" />
							 /
							<c:out value="${comunicazioneRipropAnnotaForm.numeroTrascrizione2}" />
						</td>
					</tr>
					<tr>
						<td><form:label path="valoreImmobLibStr">
								<spring:message code="conservatoria.valoreImmobLib" />
							</form:label> &nbsp;&nbsp; 
							<c:out value="${comunicazioneRipropAnnotaForm.valoreImmobLibStr}" />
						</td>
						<td>
							<c:if test="${comunicazioneRipropAnnotaForm.volturaCatastaleAnnotazione=='OK'}">
								<input type="checkbox" name="volturaCatastale" checked disabled="disabled">
							</c:if>
							<c:if test="${comunicazioneRipropAnnotaForm.volturaCatastaleAnnotazione=='NO'}">
								<input type="checkbox" name="volturaCatastale" disabled="disabled">
							</c:if>
							<spring:message code="conservatoria.volturaCatastale" />
						</td>
					<tr>
				</table>
			</c:if>
			<%-- FINE DATI RELATIVI --------------------------------------------------------------------------------------------- --%>
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4">
						<spring:message code="conservatoria.elencoBeni" />
					</td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="listaBeni" name="${comunicazioneRipropAnnotaForm.listaBeni}"
				pagesize="10" requestURI="${azione}">
				<c:if test="${comunicazioneRipropAnnotaForm.numero != null}">
					<display:column class="${listaBeni.stileDisplay}" property="PROGRESSIVO" title="Progressivo" sortable="true" />
					<display:column class="${listaBeni.stileDisplay}" property="PROGUNEG" title="Progr Uneg" sortable="true" />
					<display:column class="${listaBeni.stileDisplay}" property="progUnegBene" title="Progr Uneg Bene" sortable="true" />
					<display:column class="${listaBeni.stileDisplay}" property="descrizione" title="Descrizione" sortable="true" />
					<display:column class="${listaBeni.stileDisplay}" property="catasto" title="Catasto" sortable="true" />
					<display:column class="${listaBeni.stileDisplay}" property="comune" title="Comune" sortable="true" />
					<display:column class="${listaBeni.stileDisplay}" property="beneSMP" title="Identificativo bene" sortable="true" />
				</c:if>
			</display:table>
			<br />
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message
							code="conservatoria.elencoSoggetti" /></td>
				</tr>
			</table>

			<display:table class="tb_lista" uid="listaSoggetti"
				name="${comunicazioneRipropAnnotaForm.listaSoggetti}" pagesize="10" requestURI="${azione}">
				<c:if test="${comunicazioneRipropAnnotaForm.numero != null}">
                    <display:column class="${listaSoggetti.stileDisplay}" property="progressivo" title="Progressivo" sortable="true" />
                    <display:column class="${listaSoggetti.stileDisplay}" property="descTipologia" title="Tipologia" sortable="true" />
                    <display:column class="${listaSoggetti.stileDisplay}" property="soggetto" title="Soggetto" sortable="true" />
                    <%-- 
                    <display:column class="${listaSoggetti.stileDisplay}" property="soggettoSitMp" title="SoggettoSMP" sortable="true" />
                    --%>
				</c:if>
			</display:table>
			<br />
			<br />
			<c:if test="${comunicazioneRipropAnnotaForm.operazione=='A'}">
				<c:if test="${comunicazioneRipropAnnotaForm.stato=='2'}">
					<table class="tb_bottoni" align="center">
						<tr>
							<td class="td_tasto"><input type="submit" value="Annota" name="Annota" title="Annota"></td>
						</tr>
					</table>
				</c:if>
			</c:if>
			<c:if test="${comunicazioneRipropAnnotaForm.operazione=='R'}">
				<c:if test="${comunicazioneRipropAnnotaForm.stato=='2'}">
					<table class="tb_bottoni" align="center">
						<tr>
							<td class="td_tasto"><input type="submit" value="Riproponi" name="Riproponi" title="Riproponi"></td>
						</tr>
					</table>
				</c:if>
			</c:if>
			<c:if test="${comunicazioneRipropAnnotaForm.operazione=='E'}">
				<c:if test="${comunicazioneRipropAnnotaForm.stato!='0'}">
					<table class="tb_bottoni" align="center">
						<tr>
							<td class="td_tasto"><input type="submit" value="Esporta" name="Esporta" title="Esporta" class="nowait" onclick="cambiaStato()"></td>
						</tr>
					</table>
				</c:if>
			</c:if>
			
<script type="text/javascript">
		function cambiaStato() {
			document.getElementById("cella_descStato").style.display = 'none';
			document.getElementById("cella_inviata").style.display = 'inline-block';
		}
</script>			
			
			
			
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>