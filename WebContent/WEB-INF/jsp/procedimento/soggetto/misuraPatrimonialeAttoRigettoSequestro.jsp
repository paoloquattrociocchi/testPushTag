<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<%
	AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
%>



<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
	
		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>	
	
		<spring:url var="azioneAssociaPrevNorm" value="/procedimento/soggetto/soggettiDirettiInteressatiAttoDelProcedimentoAssocia.htm" />
		<p class="titolo_page">
			<spring:message code="soggetto.misurapatrimoniale" />
		</p>
		<br />
		<form:form method="post" action="${azioneAssociaPrevNorm}" commandName="beneSoggetto">
			<input type="hidden" id="id_selectAll_beniProcedimento.idBene" value="true"/>
			<input type="hidden" id="id_selectAll_listaIdCheckati" value="true"/>
		
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
               <br/>
               <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />

			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4">
						<c:out value="${beneSoggetto.soggetto.descrizioneSoggetto}" />
					</td>
				</tr>
			</table>
			
			<br />
			<table class="tb_form">
				<tr>
					<td><form:label path="soggetto.tipologiaPrevenzione">
							<spring:message code="procedimento.tipologiaPrevenzione" />
						</form:label></td>
					<td><form:select path="soggetto.tipologiaPrevenzione.id"
							size="1" onchange="submit()" disabled="true">
							<form:option value="-1"
								label="------Seleziona Tipo Prevenzione-------" />
							<form:options items="${tipiPrevenzione}" itemValue="id"
								itemLabel="descrizione" />
						</form:select></td>
				    <td><form:label path="soggetto.normativa">
							<spring:message code="procedimento.normativa" />
						</form:label></td>
					<td><form:select path="soggetto.normativa.id" 
							size="1" onchange="submit()" disabled="true">
							<form:option value="-1" label="------Seleziona Normativa-------" />
							<form:options items="${normativeMisPatr}" itemValue="id"
								itemLabel="descrizione" />
						</form:select></td>
				</tr>
			</table>
			<form:hidden path="soggetto.idSoggetto" />
			<form:hidden path="soggetto.idVersione" />
			<form:hidden path="soggetto.tipoSoggetto" />
			<form:hidden path="soggetto.tipoInteresse.id" />
			<form:hidden path="soggetto.tipologiaPrevenzioneDb.id" />
		</form:form>
			
		<spring:url var="azioneElimina" value="/procedimento/soggetto/soggettiDirettiInteressatiAttoDelProcedimentoElimina.htm" />
		<form:form method="post" action="${azioneElimina}" commandName="beneSoggetto">

			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.benidelprocedimento.soggetto" /></td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="elemRisultatoRicerca" name="${risultatoRicerca}" pagesize="10" requestURI="${azioneElimina}" >
				
				<display:column property="bene.idBene" title="Identificativo" sortable="false" />
				<display:column property="bene.descrizioneBene" title="Descrizione" sortable="false" />
				<display:column title="Misura - Titolari di diritto">
					<display:table class="tb_sotto_lista" uid="soggettoAssociato" name="${elemRisultatoRicerca.soggetti}" requestURI="${azione}">
						<display:column property="misura.tipoMisura.descrizione" title="" />
						<display:column property="normativa.descrizione" title="" />
						<display:column property="descrizioneSoggetto" title="" />
						<display:column property="tipoInteresse.descrizione" title="" />
						<display:column property="descrizioneFrazioneOQuota" title="" />
					</display:table>
				</display:column>
			</display:table>

			<form:hidden path="soggetto.idSoggetto" />
			<form:hidden path="soggetto.idVersione" />
			<form:hidden path="soggetto.tipoSoggetto" />
			<form:hidden path="soggetto.tipoInteresse.id" />

			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/soggetto/visualizzaDirettiInteressatiAtto.htm')"></td>
				</tr>
			</table>

		</form:form>
		
		<script type="text/javascript">	

			$(document).ready(function() {
				if(${atto!=null}){
				disabilitaSubmitMisuraPatrimonialeWithAtto('${disabledViewProc}',<c:out value="${atto.statoAtto.id}" default="0"/>)
				}else{
				disabilitaSubmitMisuraPatrimoniale('${disabledViewProc}');
				}
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
		
	</tiles:putAttribute>
</tiles:insertDefinition>

