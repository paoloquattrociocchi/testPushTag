<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
		<spring:url var="azione" value="/procedimento/bene/dettaglioBene.htm" />
		
		
		
		<p class="titolo_page">
		<c:choose>
			<c:when test="${isCancellazione }">
				<spring:message code="bene.polizza.elimina" />
			</c:when>
			<c:otherwise>
				<spring:message code="bene.polizza.dettaglio" />
			</c:otherwise>
		</c:choose>
			
		</p>
		<form:form id="id_beneForm" method="post" action="${azione}" commandName="beneForm">
			<form:hidden path="tipologia.id" />
			<form:hidden path="tipologia.descrizione" />
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<form:hidden id="id_abiCode" path="abi" />
			<form:hidden path="descrizioneEnte" />
			<form:hidden path="provinciaSel" />
			<form:hidden path="disabledProvincia" />
			<form:hidden path="disabledValutaEst" />
			<form:hidden path="indirizzoBeneFin" />
			<form:hidden id="id_provinciaEntePolizza" path="provinciaEntePolizza" />
			<form:hidden path="idBene" />

			<form:hidden path="categoria.descrizione" />
			<form:hidden path="sottocategoria.descrizione" />
			
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<table class="tb_form" border=0>
				<tr>
					<td width="15%"><form:label path="tipologia.id" >
							<spring:message code="bene.tipologia" />
						</form:label></td>
					<td colspan="3">
						<c:out value="${beneForm.tipologia.descrizione}" />
						</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="categoria.id" >
							<spring:message code="bene.categoria" />
						</form:label></td>
					<td colspan="3">
						<c:out value="${beneForm.categoria.descrizione}" />
						
				</tr>
				<tr>
					<td width="15%"><form:label path="sottocategoria.id" >
							<spring:message code="bene.sottocategoria" />
						</form:label></td>
					<td colspan="3">
						<c:out value="${beneForm.sottocategoria.descrizione}" />
						</td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.polizza.datirelativi" /></td>
				</tr>
				<tr>
					<td width="15%"><form:label path="descOperFinanz" >
							<spring:message code="bene.finanziario.descrizioneente" />
						</form:label></td>
					<td colspan="3">
						<c:out value="${beneForm.descOperFinanz.descrizione}" />
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="provincia">
							<spring:message code="bene.finanziario.provincia" />
						</form:label></td>
					<td colspan="3"><c:out value="${beneForm.provinciaEntePolizza }" /></td>
				</tr>
				<tr>
					<td width="15%"><form:label path="indirizzoBeneFin">
							<spring:message code="bene.finanziario.indirizzo" />
						</form:label></td>
					<td colspan="3"><c:out value="${beneForm.indirizzoBeneFin}" /></td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="numeroPolizza">
							<spring:message code="bene.finanziario.numeropolizza" />
						</form:label></td>
					<td colspan="3"><c:out value="${beneForm.numeroPolizza}" /></td>
				</tr>
				<tr>
					<td width="15%"><form:label path="valutaEur">
							<spring:message code="bene.finanziario.importo.euro" />
						</form:label></td>
					<td colspan="3"><c:out value="${beneForm.valutaEurFormattata}" /></td>
				</tr>
				<tr>
					<td width="15%"><form:label path="tipoValuta">
							<spring:message code="bene.finanziario.valuta" />
						</form:label></td>
					<td width="35%">
						<c:out value="${beneForm.tipoValuta.descrizione}" /></td>
					<td><form:label path="valutaEst">
							<spring:message code="bene.finanziario.importo.estero" />
						</form:label></td>
					<td width="35%">
						<c:out value="${beneForm.valutaEstFormattata}" />
					</td>
				</tr>
				<tr>
					<td><form:label path="note">
							<spring:message code="note" />
						</form:label></td>
					<td width="35%" colspan="3"><form:textarea path="note" rows="5" style="margin: 0px; width: 613px; height: 76px;" disabled="true" /></td>
				</tr>
			</table>

			<br />
			
			<form:hidden path="versioneBene" />
		
			<logic:notEmpty name="risultatiAtti">	
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="bene.listaatti" /></td>
					</tr>
				</table>
				<display:table class="tb_lista" uid="risultatiAtti" name="${risultatiAtti}" requestURI="${azione}" >
	 				<display:column title="Seleziona" sortable="false"  style="text-align: center; width: 5%">
						<form:radiobutton path="idAtto" value="${risultatiAtti.id.codiKIdAtto}" onclick="impostaHidden('${risultatiAtti.id.codiKIdAtto}',${risultatiAtti.id.codiKIdAmmin},${risultatiAtti.id.codiKIdProc})" />
					</display:column>
  
					<display:column property="descRichiesta" title="Atto" sortable="false" />
					<display:column property="descCognome" title="Amministratore" sortable="true" />
					<display:column property="descNome" title="" sortable="false" />
					<display:column property="descCodiceFiscale" title="Codice Fiscale" sortable="true" />
					<display:column property="dataSpedizione" title="Inizio Periodo" format="{0,date,dd/MM/yyyy}" sortable="true" />
					<display:column property="dataPervIst" title="Fine Periodo" format="{0,date,dd/MM/yyyy}" sortable="true" />
					<display:column property="dataDeposito" title="Data Relazione" format="{0,date,dd/MM/yyyy}" sortable="true" />
				</display:table>
			</logic:notEmpty>
			<br />
		
			<table class="tb_bottoni" align="center">
				<tr>
					<c:if test="${isCancellazione }">
						<td class="td_tasto"><input type="submit" name="ConfermaEliminaBene" value="Conferma eliminazione"></td>
													<c:if test="${controlloEffettuato != null }">
								<input type="hidden" name="controlloEffettuato" value="true" />
							</c:if>
					</c:if>
					<logic:notEmpty name="risultatiAtti">
						<td class="td_tasto"><input type="submit" name="DettaglioDatiAmministratore" value="Dettaglio Dati Amministratore"></td>
					</logic:notEmpty>
					<c:if test="${beneForm.idBene == null}">				
					    <input type="hidden" name="idDeposito" value="${beneForm.idDeposito}" />
					    <input type="hidden" name="descrizioneBene" value="${beneForm.descrizioneBene}" />
						<td class="td_tasto"><input type="submit" name="DettaglioDatiDellAmministratore" value="Dettaglio Dati Amministratore"></td>						
						<c:if test="${beneForm.warning != null && beneForm.warning == 'ok'}">		
						    <td class="td_tasto"><input type="submit" name="ImportaInPrevenzione" value="Importa nel Fascicolo di Prevenzione" disabled="disabled"></td>														
						</c:if>
						<c:if test="${beneForm.warning == null || (beneForm.warning != null && beneForm.warning == 'ko')}">		
						    <td class="td_tasto"><input type="submit" name="ImportaInPrevenzione" value="Importa nel Fascicolo di Prevenzione"></td>														
						</c:if>
					</c:if>
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro" title="Indietro"></td>
				</tr>
			</table>

		</form:form>


		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>


		<script src="${pageContext.request.contextPath}/js/suggestlist/doAutocompleteOpFinanziariPolizza.js"></script>

		<script type="text/javascript">
 				$(document).ready(function(){
					doAutocompleteOpFinanzPolizza("DescOperFinanz");
				});
		</script>


	</tiles:putAttribute>
</tiles:insertDefinition>
