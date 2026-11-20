
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/dettaglioBene.htm" />
		<p class="titolo_page">
			<spring:message code="bene.azienda.dettaglio" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm">
			<form:hidden path="beneFromBDC" />

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene" /> <c:out value="${beneForm.idBene}" /></td>
				</tr>

			</table>
			<table class="tb_form" border=0>
				<tr>
					<td><form:label path="tipologia.descrizione">
							<spring:message code="bene.tipologia" />
						</form:label></td>
					<td colspan="3"><c:out value="${beneForm.tipologia.descrizione}" /></td>
					<input type="hidden" name="tipologia.descrizione" value="${beneForm.tipologia.descrizione}" />
				</tr>
				<tr>
					<td width="15%"><form:label path="categoria.descrizione">
							<spring:message code="bene.categoria" />
						</form:label></td>
					<td width="35%"><c:out value="${beneForm.categoria.descrizione}" /></td>
					<input type="hidden" name="categoria.descrizione" value="${beneForm.categoria.descrizione}" />
					
					<td><form:label path="sottocategoria.descrizione">
							<spring:message code="bene.sottocategoria" />
						</form:label></td>
					<td width="35%"><c:out value="${beneForm.sottocategoria.descrizione}" /></td>
					<input type="hidden" name="sottocategoria.descrizione" value="${beneForm.sottocategoria.descrizione}" />
					
				</tr>

				<logic:equal name="beneForm" property="tipologia.id" value="1">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioImmobile.jsp" />
				</logic:equal>
				<logic:equal name="beneForm" property="tipologia.id" value="2">
					<c:if test="${beneForm.categoria.id=='6'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioDenaro.jsp" />
					</c:if>
					<c:if test="${beneForm.categoria.id=='7'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioCollezione.jsp" />
					</c:if>
					<c:if test="${beneForm.categoria.id=='8'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioAnimali.jsp" />
					</c:if>
					<c:if test="${beneForm.categoria.id=='9'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioAltriOggetti.jsp" />
					</c:if>
				</logic:equal>
				<logic:equal name="beneForm" property="tipologia.id" value="3">
					<c:if test="${beneForm.categoria.id=='10'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioVeicolo.jsp" />
					</c:if>
					<c:if test="${beneForm.categoria.id=='11'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioBeneImmateriale.jsp" />
					</c:if>
				</logic:equal>
				<logic:equal name="beneForm" property="tipologia.id" value="4">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioBeneFinanziario.jsp" />
				</logic:equal>
				<logic:equal name="beneForm" property="tipologia.id" value="5">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioAzienda.jsp" />
				</logic:equal>
			</table>
			<br />
			<br />
			
			<form:hidden path="idBene" />
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
					<c:if test="${beneForm.dettAssBene=='Associa'}">
						<td class="td_tasto"><input type="submit" name="ConfermaAssociaBene" value="Conferma Associazione"></td>
					</c:if>
					<c:if test="${beneForm.dettAssBene=='Elimina'}">
						<td align="right" class="td_tasto">
							<input type="submit" name="ConfermaEliminaBene" value="Conferma eliminazione">
							<c:if test="${controlloEffettuato != null }">
								<input type="hidden" name="controlloEffettuato" value="true" />
							</c:if>
						</td>
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
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro"></td>
					
				</tr>
			</table>
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>

		<script type="text/javascript">
			function impostaHidden(idAtto,idAmmin,idProc){
				document.getElementById("hiddenIdAtto").value = idAtto;
				document.getElementById("hiddenIdAmmin").value = idAmmin;
				document.getElementById("hiddenIdProc").value = idProc;
			}
			
		</script>

	</tiles:putAttribute>
</tiles:insertDefinition>
