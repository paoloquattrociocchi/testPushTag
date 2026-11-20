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
							size="1" onchange="submit()">
							<form:option value="-1"
								label="------Seleziona Tipo Prevenzione-------" />
							<form:options items="${tipiPrevenzione}" itemValue="id"
								itemLabel="descrizione" />
						</form:select></td>
				    <td><form:label path="soggetto.normativa">
							<spring:message code="procedimento.normativa" />
						</form:label></td>
					<td><form:select path="soggetto.normativa.id" 
							size="1" onchange="submit()">
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
				<display:column paramId="bene.idBene" paramProperty="listaIdCheckati" style="width:7%" title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('elemRisultatoRicerca','listaIdCheckati');\" title=\"Seleziona/Deseleziona tutti i beni\"></img>&nbsp;Sel.bene" >						
					<form:checkbox path="listaIdCheckati" value="${elemRisultatoRicerca.bene.idBene}" />
				</display:column>
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
			<c:if test="${profilo.ruolo!='MAGISTRATO'}">
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Elimina" name="Elimina"></td>
				</tr>
			</table>
			</c:if>

		</form:form>



		<spring:url var="azioneFiltro" value="/procedimento/soggetto/soggettiTitolariInteressatiDelProcedimentoFiltro.htm" />
		<form:form method="post" action="${azioneFiltro}" commandName="beneSoggetto" onsubmit="this.form.submit()">
			<c:if test="${atto.statoAtto.id == 1}">
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4">
						    <form:checkbox path="visualizzaFiltro" onclick="this.form.submit()"/>
							<spring:message code="bene.filtrosuibeni" />
	                	</td>
	                </tr>
					<logic:equal name="beneSoggetto" property="visualizzaFiltro" value="true">
						<tr><td>
						<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
							<tr>					
								<td>
									<form:label path="soggettiTitolari.id">
										<spring:message code="ricorsoAppello.soggettiTitolari" />
									</form:label>
								</td>
								<td>
									<form:label path="tipologia.id">
										<spring:message code="ricorsoAppello.beni" />
									</form:label>
								</td>
							</tr>
							<tr>&nbsp;</tr>
											
							<tr>											
								<td>
									<form:select path="soggettiTitolari.id" size="1" onchange="this.form.submit()"> 
										<form:option value="-1" label="---- Seleziona Soggetto Titolare ----" />
										<form:options items="${soggettiTitolari}" itemValue="id" itemLabel="descrizione" />
									</form:select>
								</td>
								<form:hidden path="soggettiTitolari.descrizione"/>						
								<td>
									<table>
										<tr>
											<td><form:label path="tipologia.id"><spring:message code="bene.tipologia" /></form:label></td>
											<td  colspan="3"><form:select id="tipologia" path="tipologia.id" size="1" cssStyle="width: 350px;" onchange="this.form.submit()">
													<form:option value="-1" label="------Seleziona Tipologia-------" />
													<form:options items="${tipologie}" itemValue="id" itemLabel="descrizione" />
												</form:select></td>
											<form:hidden path="tipologia.descrizione"/>
										</tr>
										<tr>
											<td><form:label path="categoria.id">
													<spring:message code="bene.categoria" />
												</form:label>
											</td>
											<td colspan="3"><form:select id="categoria" path="categoria.id" size="1" cssStyle="width: 350px;" onchange="this.form.submit()">
													<form:option value="-1" label="------Seleziona Categoria-------" />
													<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
												</form:select>
												<form:hidden path="categoria.descrizione"/>
											</td>
										</tr>
										<tr>
											<td><form:label path="sottocategoria.id"><spring:message code="bene.sottocategoria" /></form:label></td>
											<td colspan="3"><form:select id="sottocategoria" path="sottocategoria.id" size="1" cssStyle="width: 350px;" onchange="this.form.submit()">
													<form:option value="-1" label="------Seleziona Sottocategoria-------" />
													<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
												</form:select>
											</td>
											<form:hidden path="sottocategoria.descrizione"/>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<table class="tb_bottoni" align="center">
							<tr>
								<td class="td_tasto"><input type="button" title="Pulisci filtri di ricerca" value="Pulisci" onclick="pulisciFiltri(); submit();" class="nowait" /></td>
							</tr>
						</table>
						</td></tr>
					<br/>
					</logic:equal>
					<br/>
				</table>
			</c:if>
		</form:form>


		<spring:url var="azioneAssocia" value="/procedimento/soggetto/soggettiDirettiInteressatiAttoDelProcedimentoAssocia.htm" />
		<form:form method="post" action="${azioneAssocia}" commandName="beneSoggetto" onsubmit="checkParentsIfChildChecked()">
			
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.benidelprocedimento" /></td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="elemRisultatiRicerca" name="${risultatiRicerca}" pagesize="10" requestURI="${azioneAssocia}" >
				<display:column paramId="bene.idBene" paramProperty="beniProcedimento[${elemRisultatiRicerca_rowNum-1}].idBene" style="width:7%" title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('elemRisultatiRicerca','beniProcedimento.idBene');\" title=\"Seleziona/Deseleziona tutti i beni\"></img>&nbsp;Sel.bene" >										
					<form:checkbox
						path="beniProcedimento[${elemRisultatiRicerca_rowNum-1}].idBene"
						value="${elemRisultatiRicerca.bene.idBene}"
						onclick="checkSoggetti(this, ${elemRisultatiRicerca_rowNum-1})" />
				</display:column>
									
				<display:column property="bene.idBene" title="Identificativo" sortable="false" />
				<display:column property="bene.descrizioneBene" title="Descrizione" sortable="false" />
				<display:column title="Misura - Titolari di diritto">
					<display:table class="tb_sotto_lista" uid="soggetto"name="${elemRisultatiRicerca.soggetti}" requestURI="${azioneAssocia}">
						<display:column property="misura.tipoMisura.descrizione" title="" />
						<display:column property="normativa.descrizione" title="" />
						<display:column property="descrizioneSoggetto" title="" />
						<display:column property="tipoInteresse.descrizione" title="" />
						<display:column property="descrizioneFrazioneOQuota" title="" />
						<display:column sortable="false" style="text-align: center; width: 5%">
							<form:checkbox
								path="beniProcedimento[${elemRisultatiRicerca_rowNum-1}].listaIdCheckboxString"
								value="${soggetto.idSoggetto};${soggetto.tipoInteresse.id}"
								name="soggettoTitolareDi${elemRisultatiRicerca.bene.idBene}"
								onclick="uncheckParentIfNotAllChildren(${elemRisultatiRicerca_rowNum-1})" />
						</display:column>
					</display:table>
				</display:column>
			</display:table>

			<form:hidden path="soggetto.idSoggetto" />
			<form:hidden path="soggetto.idVersione" />
			<form:hidden path="soggetto.tipoSoggetto" />
			<form:hidden path="soggetto.tipoInteresse.id" />
			<form:hidden path="soggetto.tipologiaPrevenzioneDb.id" />
			<form:hidden path="soggetto.tipologiaPrevenzione.id" />
			<form:hidden path="soggetto.normativa.id" />
			
			<br/>
			<table class="tb_bottoni" align="center">
				<tr>
				<c:if test="${profilo.ruolo!='MAGISTRATO'}">
					<td class="td_tasto"><input type="submit" value="Associa" name="Associa"></td>
				</c:if>
					<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/soggetto/visualizzaDirettiInteressatiAtto.htm')"></td>
				</tr>
			</table>

		</form:form>
		
		<script type="text/javascript">	

			$(document).ready(function() {
               if(${atto!=null}){
				disabilitaSubmitMisuraPatrimonialeWithAtto('${disabledViewProc}',<c:out value="${atto.statoAtto.id}" default="0"/>);
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

			function pulisciFiltri() {
				if(document.getElementsByName("soggettiTitolari.id").length != 0)
					document.getElementsByName("soggettiTitolari.id")[0].selectedIndex = -1;
				if(document.getElementsByName("tipologia.id").length != 0)
					document.getElementsByName("tipologia.id")[0].selectedIndex = -1;
				if(document.getElementsByName("categoria.id").length != 0)
					document.getElementsByName("categoria.id")[0].selectedIndex = -1;
				if(document.getElementsByName("sottocategoria.id").length != 0)
					document.getElementsByName("sottocategoria.id")[0].selectedIndex = -1;
			}
			
		</script>	
 	</tiles:putAttribute>
</tiles:insertDefinition>
		

