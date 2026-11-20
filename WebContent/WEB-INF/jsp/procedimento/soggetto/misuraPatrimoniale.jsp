<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>
<%@ page import="it.giustizia.smp.display.CustomCheckboxTableDecorator"%>

<%
	AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
	String disabledViewProc = (String) request.getSession().getAttribute("disabledViewProc");
	String store = (String)request.getAttribute("storeCheck") ; 
	
	CustomCheckboxTableDecorator decoratorSogg = new CustomCheckboxTableDecorator();
	decoratorSogg.setId("bene.idBene");
	decoratorSogg.setFieldName("listaIdCheckati");
	decoratorSogg.setDisabled(disabledViewProc);
	decoratorSogg.setStore(store);
	pageContext.setAttribute("checkboxDecoratorSogg", decoratorSogg);
	
	CustomCheckboxTableDecorator decoratorProc = new CustomCheckboxTableDecorator();
	decoratorProc.setId("bene.idBene");
	decoratorProc.setFieldName("proc_chk");
	decoratorProc.setDisabled(disabledViewProc);
	decoratorProc.setOnClick("checkProcClick(this)");
	decoratorProc.setStore(store);
	pageContext.setAttribute("checkboxDecoratorProc", decoratorProc);
	
	CustomCheckboxTableDecorator decoratorSubProc = new CustomCheckboxTableDecorator();
	decoratorSubProc.setId("subCheck");
	decoratorSubProc.setFieldName("subproc_chk");
	decoratorSubProc.setDisabled(disabledViewProc);
	decoratorSubProc.setOnClick("checkSubProcClick(this)");
	decoratorSubProc.setStore(store);
	pageContext.setAttribute("checkboxDecoratorSubProc", decoratorSubProc);
%>
		
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">

		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
	
		<spring:url var="azioneAssociaPrevNorm" value="/procedimento/soggetto/soggettiDirettiInteressatiDelProcedimentoAssocia.htm" />

		<p class="titolo_page">
			<spring:message code="soggetto.misurapatrimoniale" />
		</p>
		<br />
		<form:form method="post" action="${azioneAssociaPrevNorm}" commandName="beneSoggetto">
			<input type="hidden" id="id_selectAll_beniProcedimento.idBene" value="true"/>
			<input type="hidden" id="id_selectAll_listaIdCheckati" value="true"/>

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

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
					<td class="td_titolo" colspan="4"><spring:message code="bene.benidelprocedimento" /></td>
				</tr>
			
				<tr>
					<td><form:label path="soggetto.tipologiaPrevenzione" cssClass="required">
							<spring:message code="procedimento.tipologiaPrevenzione" />
						</form:label>
					</td>
					<td><form:select path="soggetto.tipologiaPrevenzione.id"
							size="1" onchange="submit()">
							<form:option value="-1"
								label="------Seleziona Tipo Prevenzione-------" />
							<form:options items="${tipiPrevenzione}" itemValue="id"
								itemLabel="descrizione" />
						</form:select>
					</td>
				    <td><form:label path="soggetto.normativa" cssClass="required">
							<spring:message code="procedimento.normativa" />
						</form:label>
					</td>
					<td><form:select path="soggetto.normativa.id" 
							size="1" onchange="submit()">
							<form:option value="-1" label="------Seleziona Normativa-------" />
							<form:options items="${normativeMisPatr}" itemValue="id"
								itemLabel="descrizione" />
						</form:select>
					</td>
				</tr>
			</table>
			<form:hidden path="soggetto.idSoggetto" />
			<form:hidden path="soggetto.idVersione" />
			<form:hidden path="soggetto.tipoSoggetto" />
			<form:hidden path="soggetto.tipoInteresse.id" />
			<form:hidden path="soggetto.tipologiaPrevenzioneDb.id" />
		</form:form>

		<spring:url var="azioneElimina" value="/procedimento/soggetto/soggettiDirettiInteressatiDelProcedimentoElimina.htm" />
		<form:form method="post" action="${azioneElimina}" commandName="beneSoggetto" name="formElimina">
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.benidelprocedimento.soggetto" /></td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="elemRisultatoRicerca" name="${risultatoRicerca}" pagesize="10" decorator="checkboxDecoratorSogg" form="formElimina" excludedParams="listaIdCheckati" >
				<display:column property="checkbox" 
						style="width:7%" 
						title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('elemRisultatoRicerca','listaIdCheckati');\" title=\"Seleziona/Deseleziona tutti i beni\"></img>&nbsp;Sel.bene" >	
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
				<logic:notEmpty name="risultatoRicerca">
					<tr>
						<td class="td_tasto"><input type="submit" value="Elimina" name="Elimina"></td>
					</tr>
				 </logic:notEmpty>
			</table>
			</c:if>
		</form:form>


		<spring:url var="azioneFiltro" value="/procedimento/soggetto/soggettiDirettiInteressatiDelProcedimentoFiltro.htm" />
		<form:form method="post" action="${azioneFiltro}" commandName="beneSoggetto" onsubmit="this.form.submit()">

		<c:if test="${atto.statoAtto.id != 3}">
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
						<tr>&nbsp;
						</tr>
										
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
					</td></tr>
					<tr><td>
					<table class="tb_bottoni" align="center">
						<tr>
							<td class="td_tasto"><input type="button" title="Pulisci filtri di ricerca" value="Pulisci" onclick="pulisciFiltri(); submit();" class="nowait" /></td>
						</tr>
					</table>
					</td></tr>
					<br/>
				</logic:equal>
			</table>
		</c:if>
	</form:form>


		<spring:url var="azioneAssocia" value="/procedimento/soggetto/soggettiDirettiInteressatiDelProcedimentoAssocia.htm" />
		<form:form method="post" action="${azioneAssocia}" commandName="beneSoggetto" name="formAssocia">

			<table class="tb_form">
			<logic:notEmpty name="beneSoggetto" property="criteriFiltro">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.benidelprocedimentofiltrati" />
					</td>
				</tr>
			</logic:notEmpty>
			<logic:empty name="beneSoggetto" property="criteriFiltro">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.benidelprocedimento" /></td>
				</tr>
			</logic:empty>
			</table>
			<display:table class="tb_lista" uid="elemRisultatiRicerca" name="${risultatiRicerca}" pagesize="10" decorator="checkboxDecoratorProc" form="formAssocia" excludedParams="proc_chk">
				<display:column 
						property="checkbox"
						style="width:7%" 
						title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" 
													onclick=\"selezionaTutto('elemRisultatiRicerca','beniProcedimento.idBene');\" title=\"Seleziona/Deseleziona tutti i beni\"></img>&nbsp;Sel.bene" >	
				</display:column>
				<display:column property="bene.idBene" title="Identificativo" sortable="false" />
				<display:column property="bene.descrizioneBene" title="Descrizione" sortable="false" />
 		     		<display:column title="Misura - Titolari di diritto">
					<display:table class="tb_sotto_lista" uid="soggetto"name="${elemRisultatiRicerca.soggetti}" decorator="checkboxDecoratorSubProc" form="formAssocia" excludedParams="subproc_chk">
						<display:column property="misura.tipoMisura.descrizione" title="" />
						<display:column property="normativa.descrizione" title="" />
						<display:column property="descrizioneSoggetto" title="" />
						<display:column property="tipoInteresse.descrizione" title="" />
						<display:column property="descrizioneFrazioneOQuota" title="" />
						<display:column 
							property="checkbox"
							title=""
							sortable="false"
							style="text-align: center; width: 5%">
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
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
				</tr>
			</table>

		</form:form>
		
		<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
		<smp:back buttonId="backButton"></smp:back>
		
 
		<script type="text/javascript">

			$(document).ready(function() {
				if(${atto!=null}){
					disabilitaSubmitMisuraPatrimonialeWithAtto('${disabledViewProc}', <c:out value="${atto.statoAtto.id}" default="0"/>);
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

			function checkProcClick(box) {
				//console.log("checkProcClick");
				//console.log(box);
				//console.log(box.value);
				//console.log(box.checked);
				var subProcList = document.getElementsByName("subproc_chk");
				var idx;
				for (idx = 0; idx < subProcList.length; idx++) {
					var subBox = subProcList[idx];
					//console.log(subBox);
					//console.log(subBox.value);
					//console.log(subBox.checked);
					var subVal = subBox.value;
					var subValList = subVal.split(";");
					// se  subVal inizia con boxVal
					if (subValList[0]==box.value){
						subBox.checked = box.checked;
					} 
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
		
			function checkSubProcClick(box) {
				//console.log("checkSubProcClick");
				// info del sub-box cliccato
				var boxVal = box.value;
				var boxValList = boxVal.split(";");
				var boxValId = boxValList[0]; 
				//console.log("boxValId="+boxValId);

				// esamino i sub-box
				var almenoUnoAttivo = false;
				var subProcList = document.getElementsByName("subproc_chk");
				var idx;
				for (idx = 0; idx < subProcList.length; idx++) {
					var subBox = subProcList[idx];
					var subVal = subBox.value;
					var subValList = subVal.split(";");
					var subValId = subValList[0]; 
					if (subValId==boxValId){
						//console.log("find: "+subVal+" - checked="+subBox.checked);
						// gruppo dei sub-box
						if (subBox.checked){
							almenoUnoAttivo = true;
							break;
						}
					} 
				}

				// definisco click sul check della riga
				//console.log("almenoUnoAttivo="+almenoUnoAttivo);
				//if (almenoUnoDisattivato){
				var procList = document.getElementsByName("proc_chk");
				for (idx = 0; idx < procList.length; idx++) {
					var rowBox = procList[idx];
					var rowVal = rowBox.value;
					//console.log("rowVal="+rowVal);
					if (rowVal==boxValId){
						// trovato il check
						rowBox.checked = almenoUnoAttivo;
					} 
				}
				//}
				
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
 
 		<script type="text/javascript">
			// anomalia sul componente displaytag: se gli indici della paginazione si mettono in basso (paging.banner.placement=bottom)
			// non viene aggiuta la funzione js displaytagform e alcuni campi hidden 
			function displaytagform(formname, fields) {
				//console.log("displaytagform - formname: "+formname);
				//console.log("displaytagform - fields.length: "+fields.length);
				var objfrm = document.forms[formname];
				for (j = fields.length - 1; j >= 0; j--) {
					//console.log("fields[j].f: "+fields[j].f);
					//console.log("fields[j].v: "+fields[j].v);
					var f = objfrm.elements[fields[j].f];
					if (f) {
						//console.log("find: "+fields[j].f);
						f.value = fields[j].v;
					} else {
						//console.log("not find: "+fields[j].f);
						var input = document.createElement("input");
						input.setAttribute("type", "hidden");
						input.setAttribute("name", fields[j].f);
						input.setAttribute("value", fields[j].v);
						//append to form element that you want .
						objfrm.appendChild(input);
					}
				}

				//append cambiopagina form element
				var cambio = document.createElement("input");
				cambio.setAttribute("type", "hidden");
				cambio.setAttribute("name", "CambioPagina");
				cambio.setAttribute("value", "true");
				objfrm.appendChild(cambio);

				objfrm.submit();
			}
		</script>
 
	</tiles:putAttribute>
</tiles:insertDefinition>