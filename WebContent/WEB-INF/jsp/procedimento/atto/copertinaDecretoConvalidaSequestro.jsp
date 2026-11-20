<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

<%
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/dettaglioAtto.htm" />
		<form:form method="post" id="id_formConfermaInvia" action="${azione}" commandName="attoForm">
			<p class="titolo_page">
				<spring:message code="atto.dettaglio" />
			</p>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<logic:notPresent name="listaDecretiSequestri">
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				<br />

				<c:if test="${attoForm.statoAtto.id==3}">
					<br />
					<form:hidden path="codAtto" />
					<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
					<table class="tb_form">
						<tr>
							<td class="td_titolo" colspan="4"></td>
						</tr>

						<c:if test="${attoForm.statoAtto.id != 3}">
							<tr>
								<td><form:label path="dataEmissione" cssClass="required">
										<spring:message code="atto.dataemissione" />
									</form:label></td>
								<td><form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" /> <form:errors path="dataEmissione" class="testoRosso" /></td>
								<td><form:label path="dataDeposito" cssClass="required">
										<spring:message code="atto.datadeposito" />
									</form:label></td>
								<td><form:input path="dataDeposito" maxlength="10" id="id_dataDeposito" /> <form:errors path="dataDeposito" class="testoRosso" /></td>
							</tr>
							<tr style="display:none">
								<td><logic:notEmpty name="ufficiListFromFlussiOut">
										<form:label path="destinatario.id">
											<spring:message code="atto.destinazione" />
										</form:label>
									</logic:notEmpty></td>
								<td><logic:notEmpty name="ufficiListFromFlussiOut">
										<table>
											<c:forEach var="destinat" items="${ufficiListFromFlussiOut}">
												<tr>
													<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
												</tr>
											</c:forEach>
										</table>
									</logic:notEmpty></td>
								<td><form:label path="dataRegistrazione">
										<spring:message code="atto.dataregistrazione" />
									</form:label></td>
								<td><fmt:formatDate value="<%=new java.util.Date() %>" type="date" pattern="dd/MM/yyyy" /></td>
							</tr>
						</c:if>
						<c:if test="${attoForm.statoAtto.id == 3}">
							<tr>
								<td><form:label path="dataEmissione">
										<spring:message code="atto.dataemissione" />
									</form:label></td>
								<td><fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataEmissione" /></td>
								<td><form:label path="dataDeposito">
										<spring:message code="atto.datadeposito" />
									</form:label></td>
								<td><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /></td>
							</tr>
							<tr>
								<td><logic:notEmpty name="ufficiListFromFlussiOut">
										<form:label path="destinatario.id">
											<spring:message code="atto.destinazione" />
										</form:label>
									</logic:notEmpty></td>
								<td><logic:notEmpty name="ufficiListFromFlussiOut">
										<table>
											<c:forEach var="destinat" items="${ufficiListFromFlussiOut}">
												<tr>
													<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
												</tr>
											</c:forEach>
										</table>
									</logic:notEmpty></td>
								<td><form:label path="dataRegistrazione">
										<spring:message code="atto.dataregistrazione" />
									</form:label></td>
								<td><fmt:formatDate value="${attoForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /></td>
							</tr>
						</c:if>
					</table>
				</c:if>
			</logic:notPresent>
		</form:form>
		<logic:notPresent name="listaDecretiSequestri">
			<c:if test="${attoForm.statoAtto.id!='3'}">

				<spring:url var="azioneConvalida" value="/procedimento/atto/decretoConvalidaSequestro.htm" />
				<form:form method="post" action="${azioneConvalida}" commandName="beneSoggettoForm" onsubmit="checkParentsIfChildChecked()">
					<input type="hidden" id="id_selectAll_beniProcedimento.idBene" value="true" />
					<br />
					<table class="tb_form">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="bene.benidelatto" /></td>
						</tr>
					</table>
					<display:table class="tb_lista" uid="elemRisultatiRicerca" name="${risultatiRicerca}" pagesize="10" requestURI="${azioneR}">
						<display:column paramId="bene.idBene" paramProperty="beniProcedimento[${elemRisultatiRicerca_rowNum-1}].idBene" style="width:7%" title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('elemRisultatiRicerca','beniProcedimento.idBene');\" title=\"Seleziona/Deseleziona tutti i beni\"></img>&nbsp;Sel.bene" >										
						<form:checkbox path="beniProcedimento[${elemRisultatiRicerca_rowNum-1}].idBene" value="${elemRisultatiRicerca.bene.idBene}" onclick="checkSoggetti(this, ${elemRisultatiRicerca_rowNum-1})" />
						</display:column>

						<display:column property="bene.idBene" title="Identificativo" sortable="false" />
						<display:column property="bene.descrizioneBene" title="Descrizione" sortable="false" />
						<display:column title="Misura - Titolari di diritto">
							<display:table class="tb_sotto_lista" uid="soggetto" name="${elemRisultatiRicerca.soggetti}" requestURI="${azioneR}">
								<display:column property="misura.tipoMisura.descrizione" title="" />
								<display:column property="descrizioneSoggetto" title="" />
								<display:column property="tipoInteresse.descrizione" title="" />
								<display:column property="descrizioneFrazioneOQuota" title="" />
								<display:column property="dataEsecuzione" title="Esecuzione" format="{0,date,dd/MM/yyyy}" />
								<c:if test="${soggetto.misura.tipoMisura.id == '5'}">
									<display:column title="Convalida" value="CONVALIDATO" />
								</c:if>
								<c:if test="${soggetto.misura.tipoMisura.id == '6' || soggetto.misura.tipoMisura.id == '16'}">
									<display:column title="Convalida" value="" />
								</c:if>
								<c:if test="${soggetto.misura.tipoMisura.id == '22'}">
									<display:column title="Convalida" value="NON CONVALIDATO" />
								</c:if>
								<display:column sortable="false" style="text-align: center; width: 5%">
									<form:checkbox path="beniProcedimento[${elemRisultatiRicerca_rowNum-1}].listaIdCheckboxString" value="${soggetto.idSoggetto};${soggetto.tipoInteresse.id}" name="soggettoTitolareDi${elemRisultatiRicerca.bene.idBene}"
										onclick="uncheckParentIfNotAllChildren(${elemRisultatiRicerca_rowNum-1})" />
								</display:column>
							</display:table>
						</display:column>
					</display:table>
					<br />
					<br />
					<table class="tb_bottoni" align="center">
						<tr>
						 <c:if test="${profilo.ruolo!='MAGISTRATO'}">
							<td class="td_tasto"><input type="submit" value="Convalida" name="Convalida" /></td>
							<td class="td_tasto"><input type="submit" value="Non Convalida" name="NonConvalida" /></td>
							<td class="td_tasto"><input type="submit" value="Conferma" name="ConfermaInvia"></td>
						</c:if>
							<td class="td_tasto"><c:if test="${fromMenuAtti=='elenco'}">
									<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
								</c:if> <c:if test="${fromMenuAtti=='ordine'}">
									<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
								</c:if> <c:if test="${fromMenuAtti=='nuovo'}">
									<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
								</c:if></td>
						</tr>
					</table>
					<br />
					<br />
				</form:form>
			</c:if>

			<!-- se l'atto è registrato -->
			<c:if test="${attoForm.statoAtto.id=='3'}">

				<spring:url var="azioneDR" value="/procedimento/atto/dettaglioAttoRegistrato.htm" />

				<br />

				<spring:url var="azioneR" value="/procedimento/atto/attoRegistrato.htm" />
				<form:form method="post" action="${azioneR}" commandName="beneSoggettoForm" onsubmit="checkParentsIfChildChecked()">
					<input type="hidden" id="id_selectAll_beniProcedimento.idBene" value="true" />

					<table class="tb_form">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="bene.benidelatto" /></td>
						</tr>
					</table>

					<display:table class="tb_lista" uid="elemRisultatiRicerca" name="${risultatiRicerca}" pagesize="10" requestURI="${azioneR}">
						<display:column property="bene.idBene" title="Identificativo" sortable="false" />
						<display:column property="bene.descrizioneBene" title="Descrizione" sortable="false" />
						<display:column title="Misura - Titolari di diritto">
							<display:table class="tb_sotto_lista" uid="soggetto" name="${elemRisultatiRicerca.soggetti}" requestURI="${azioneR}">
								<display:column property="tipologiaPrevenzione.descrizione" title="" />
								<display:column property="misura.tipoMisura.descrizione" title="" />
								<display:column property="descrizioneSoggetto" title="" />
								<display:column property="tipoInteresse.descrizione" title="" />
								<display:column property="descrizioneFrazioneOQuota" title="" />
								<display:column property="dataEsecuzione" title="Esecuzione" format="{0,date,dd/MM/yyyy}" />
								<c:if test="${soggetto.misura.tipoMisura.id == '5'}">
									<display:column title="Convalida" value="CONVALIDATO" />
								</c:if>
								<c:if test="${soggetto.misura.tipoMisura.id == '6' || soggetto.misura.tipoMisura.id == '16'}">
									<display:column title="Convalida" value="" />
								</c:if>
								<c:if test="${soggetto.misura.tipoMisura.id == '22'}">
									<display:column title="Convalida" value="NON CONVALIDATO" />
								</c:if>
							</display:table>
						</display:column>
					</display:table>
					<br />
					<br />

					<table class="tb_bottoni" align="center">
						<tr>
							<smp:notificheButtons formId="id_formConfermaInvia" codAtto="${attoForm.codAtto}"></smp:notificheButtons>
							<td class="td_tasto"><c:if test="${fromMenuAtti=='elenco'}">
									<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
								</c:if> <c:if test="${fromMenuAtti=='ordine'}">
									<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
								</c:if> <c:if test="${fromMenuAtti=='nuovo'}">
									<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
								</c:if></td>
						</tr>
					</table>
				</form:form>
			</c:if>
		</logic:notPresent>

		<logic:present name="listaDecretiSequestri">
			<spring:url var="azioneSelSequestro" value="/procedimento/atto/decretoConvalidaSequestro.htm" />
			<form:form method="post" action="${azioneSelSequestro}" commandName="attoForm">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="codAtto">
								<spring:message code="atto.decreto.sequestro" />
							</form:label></td>
						<td>
						<form:select path="codAtto" size="1">
								<form:option value="-1" label="------Seleziona Sequestro-------" />
								<form:options items="${listaDecretiSequestri}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:hidden path="tipoAtto.id" /> <form:hidden path="categoriaAtto.id" /></td>
					</tr>
				</table>
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<smp:notificheButtons formId="id_formConfermaInvia" codAtto="${attoForm.codAtto}"></smp:notificheButtons>
						<td class="td_tasto"><input type="submit" value="Conferma" name="SelezionaSequestro"></td>
						<td class="td_tasto"><c:if test="${fromMenuAtti=='elenco'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
							</c:if> <c:if test="${fromMenuAtti=='ordine'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
							</c:if> <c:if test="${fromMenuAtti=='nuovo'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/nuovo.htm')">
							</c:if></td>
					</tr>
				</table>
			</form:form>
		</logic:present>

	</tiles:putAttribute>
</tiles:insertDefinition>


<script type="text/javascript">

	$(document).ready(function() {
		prendiData("#dataEsecuzione",true);
		prendiData("#id_dataDeposito",true);
		prendiData("#id_dataEmissione",true);
	});		

	function enableAll(array) {

		for (var i = 0; i < array.length; i++)
			array[i].disabled = false;
	}

	function disableAll(array) {

		for (var i = 0; i < array.length; i++)
			array[i].disabled = true;
	}

	function uncheckAll(array) {

		for (var i = 0; i < array.length; i++)
			array[i].checked = false;
	}

	function checkAll(array) {

		for (var i = 0; i < array.length; i++)
			array[i].checked = true;
	}

	function checkSoggetti(checkbox, indice) {
		
		var nome = "beniProcedimento[" + indice + "].listaIdCheckboxString";

		var soggettiTitolari = document.getElementsByName(nome);

		if (checkbox.checked) {

			checkAll(soggettiTitolari);
		
		} else {

			uncheckAll(soggettiTitolari);
		}
	}

	function uncheckParentIfNotAllChildren(indice) {
		
		var nomeLista = "beniProcedimento[" + indice + "].listaIdCheckboxString";

		var soggettiTitolari = document.getElementsByName(nomeLista);

		var almenoUnoDisattivato = false;

		for (var i = 0; i < soggettiTitolari.length; i++){
			if (!soggettiTitolari[i].checked){
				almenoUnoDisattivato = true;
				break;
				}
		}

		if (almenoUnoDisattivato){

			var nomePadre = nomeLista.replace("listaIdCheckboxString", "idBene");
			var padre = document.getElementsByName(nomePadre);
			for (var i = 0; i < padre.length; i++){
				padre[i].checked = false;
			}
		}
	}

	function checkParent(checkbox){
		
		var nomeFiglio = checkbox.name;
		var nomePadre = nomeFiglio.replace("listaIdCheckboxString", "idBene");
		var padre = document.getElementsByName(nomePadre);
		for (var i = 0; i < padre.length; i++){
			padre[i].checked = true;
		}
	}

	function checkParentsIfChildChecked() {

		for(var i=0; i<document.getElementsByTagName("input").length; i++){
			if(document.getElementsByTagName("input")[i].type == "checkbox"){
				if(document.getElementsByTagName("input")[i].checked == true){
					checkParent(document.getElementsByTagName("input")[i]);
				}
			}
		}
	}
</script>
