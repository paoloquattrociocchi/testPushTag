<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<c:set var="SENTENZA_REVOCAZIONE_DELLA_CONFISCA" value="<%=Constants.SENTENZA_REVOCAZIONE_DELLA_CONFISCA%>"/>

<body>
	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione" value="/procedimento/atto/dettaglioAtto.htm" />
			<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
			<script type='text/javascript' src="${pageContext.request.contextPath}/js/formUtils.js"></script>
			
			<form:form method="post" action="${azione}" commandName="attoForm">
				<p class="titolo_page">
					<spring:message code="atti.definitivita" />
				</p>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				<br />
			</form:form>
			<!-- L'Atto è registrato -->
			<c:if test="${attoForm.statoAtto.id=='3'}">
				<br />
				
				<c:if test="${(sessionScope.atto!=null && sessionScope.atto.tipoAtto!=null && (
						(sessionScope.atto.categoriaAtto.id==SENTENZA_REVOCAZIONE_DELLA_CONFISCA)
					)
				) }">
					<spring:url var="azioneR" value="/procedimento/atto/definitivitaRevocazioneConfisca.htm" />
				</c:if>
				
				<c:if test="${!(sessionScope.atto!=null && sessionScope.atto.tipoAtto!=null && (
						(sessionScope.atto.categoriaAtto.id==SENTENZA_REVOCAZIONE_DELLA_CONFISCA)
					)
				) }">
					<spring:url var="azioneR" value="/procedimento/atto/definitivita.htm" />
				</c:if>
					
				
			
				      <c:if test="${sessionScope.procedimento.tipoProcedimento.id!=8 && sessionScope.procedimento.tipoProcedimento.id!=13
								    &&  sessionScope.procedimento.tipoProcedimento.id!=14 && sessionScope.procedimento.tipoProcedimento.id!=15
								    &&  sessionScope.procedimento.tipoProcedimento.id!=16 && sessionScope.procedimento.tipoProcedimento.id!=17
								    &&  sessionScope.procedimento.tipoProcedimento.id!=18 && sessionScope.procedimento.tipoProcedimento.id!=19}">	
						<spring:url var="azione1" value="/procedimento/atto/postDefinitivitaDecisorio.htm" />
							<form:form id="id_filtro" method="post" action="${azione1}" commandName="attoForm">
								<form:hidden path="warning" />
								
									
						<table class="tb_form">
							<tr>
								<td class="td_titolo" colspan="4">
								    <form:checkbox path="visualizzaFiltroPers" onclick="this.form.submit()"/>
									<spring:message code="ricorsoAppello.filtrosumisurePers" />
			                	</td>
			                </tr>
							<logic:equal name="attoForm" property="visualizzaFiltroPers" value="true">
			                    <tr><td>
								<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
									<tr>					
										<td>
											<form:label path="soggettiDirettiPers.id">
												<spring:message code="ricorsoAppello.soggettiDiretti" />
											</form:label>
										</td>
									</tr>
									<tr>&nbsp;</tr>												
									<tr>											
										<td>
											<form:select path="soggettiDirettiPers.id" size="1" onchange="this.form.submit()"> 
												<form:option value="-1" label="---- Seleziona Soggetto Diretto ----" />
												<form:options items="${soggettiDirettiPers}" itemValue="id" itemLabel="descrizione" />
											</form:select>
										</td>
										<form:hidden path="soggettiDirettiPers.descrizione"/>			
									</tr>
								</table>
								</td></tr>

			                    <tr><td>
							
								<table class="tb_bottoni" align="center">
									<tr>
										<td class="td_tasto"><input type="button" title="Pulisci filtri di ricerca" value="Pulisci" onclick="pulisciFiltriPers(); submit();" class="nowait" /></td>
									</tr>
								</table>

                               </td></tr>

							</logic:equal>
						</table>
						
						<table class="tb_form">
							<tr>
								<td class="td_titolo" colspan="4">
								    <form:checkbox path="visualizzaFiltro" onclick="this.form.submit()"/>
									<spring:message code="bene.filtrosuibeni" />
			                	</td>
			                </tr>
							<logic:equal name="attoForm" property="visualizzaFiltro" value="true">
							  <tr><td>
								<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
									<tr>					
										<td>
											<form:label path="soggettiDiretti.id">
												<spring:message code="ricorsoAppello.soggettiDiretti" />
											</form:label>
										</td>
										<td>
											<form:label path="soggettiTitolari.id">
												<spring:message code="ricorsoAppello.soggettiTitolari" />
											</form:label>
										</td>
										<td>
											<form:label path="tipologiaBene.id"><spring:message code="ricorsoAppello.beni" /></form:label>
										</td>
									</tr>
									<tr>&nbsp;</tr>
													
									<tr>											
										<td>
											<form:select path="soggettiDiretti.id" size="1" onchange="this.form.submit()"> 
												<form:option value="-1" label="---- Seleziona Soggetto Diretto ----" />
												<form:options items="${soggettiDiretti}" itemValue="id" itemLabel="descrizione" />
											</form:select>
										</td>
										<form:hidden path="soggettiDiretti.descrizione"/>						
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
													<td><form:label path="tipologiaBene.id"><spring:message code="bene.tipologia" /></form:label></td>
													<td  colspan="3"><form:select id="tipologiaBene" path="tipologiaBene.id" size="1" cssStyle="width: 350px;" onchange="this.form.submit()">
															<form:option value="-1" label="------Seleziona Tipologia-------" />
															<form:options items="${tipologie}" itemValue="id" itemLabel="descrizione" />
														</form:select></td>
													<form:hidden path="tipologiaBene.descrizione"/>
												</tr>
												<tr>
													<td><form:label path="categoriaBene.id">
															<spring:message code="bene.categoria" />
														</form:label>
													</td>
													<td colspan="3"><form:select id="categoriaBene" path="categoriaBene.id" size="1" cssStyle="width: 350px;" onchange="this.form.submit()">
															<form:option value="-1" label="------Seleziona Categoria-------" />
															<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
														</form:select>
														<form:hidden path="categoriaBene.descrizione"/>
													</td>
												</tr>
												<tr>
													<td><form:label path="sottoCategoriaBene.id"><spring:message code="bene.sottocategoria" /></form:label></td>
													<td colspan="3"><form:select id="sottoCategoriaBene" path="sottoCategoriaBene.id" size="1" cssStyle="width: 350px;" onchange="this.form.submit()">
															<form:option value="-1" label="------Seleziona Sottocategoria-------" />
															<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
														</form:select>
													</td>
													<form:hidden path="sottoCategoriaBene.descrizione"/>
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

							</logic:equal>

						</table>
				   </form:form>
				</c:if>


				<form:form id="id_listaMisure" method="post" action="${azioneR}" commandName="beneSoggettoForm" onsubmit="checkParentsIfChildChecked()">
					<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=17 && sessionScope.procedimento.tipoProcedimento.id!=19}" >
					
						<input type="hidden" id="id_selectAll_soggettiMisPers.idSoggetto" value="true" />
						<table class="tb_form">
							<tr>
								<td class="td_titolo" colspan="4"><spring:message code="atti.datadefinitivita.misurePersonali" /></td>
							</tr>
						</table>
						<display:table class="tb_lista" uid="elemRisRicerca" name="${risultatiRicercaSogg}" pagesize="10" requestURI="${azioneR}">
							<logic:notEqual scope="session" name="procedimento" property="statoProcedimento.id" value="22">
								<display:column paramId="soggetto.idSoggetto" paramProperty="soggettiMisPers[${elemRisRicerca_rowNum-1}].idSoggetto" style="width:3%" 
												title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('elemRisRicerca','soggettiMisPers.idSoggetto');\" title=\"Seleziona/Deseleziona tutti i soggetti\"></img>&nbsp;Sel." >										
									<form:checkbox path="soggettiMisPers[${elemRisRicerca_rowNum-1}].idSoggetto" value="${elemRisRicerca.soggetto.idSoggetto}" disabled="${elemRisRicerca.soggetto.disabledCheckDefinitivita}" onclick="checkMisure(this, ${elemRisRicerca_rowNum-1})" />
								</display:column>
							</logic:notEqual>	
							<display:column property="soggetto.descrizioneSoggetto" title="Soggetto Interessato" sortable="false" />
							<display:column title="">
								<display:table class="tb_sotto_lista" uid="misura" name="${elemRisRicerca.misure}" requestURI="${azioneR}">
									<display:column property="descrizioneMisura" title="Misura" />
									<c:if test="${misura.decisioneMisuraPersonaleIIGrado!=null && misura.decisioneMisuraPersonaleIIGrado.descrizione!=null}">
										<display:column property="decisioneMisuraPersonaleIIGrado.descrizione" title="Esito" />
									</c:if>
									<c:if test="${misura.decisioneMisuraPersonaleIIGrado==null || misura.decisioneMisuraPersonaleIIGrado.descrizione==null}">
										<display:column property="decisioneMisuraPersonale.descrizione" title="Esito" />
									</c:if>									
									<display:column property="dataDefinitivita" title="Definitivita'" format="{0,date,dd/MM/yyyy}" />
							
							        <logic:notEqual scope="session" name="procedimento" property="statoProcedimento.id" value="22">									
										<display:column sortable="false" style="text-align: center; width: 5%">   
											<form:checkbox path="soggettiMisPers[${elemRisRicerca_rowNum-1}].misuraInfo" value="${misura.idMisura};${misura.tipoMisura.id}" disabled="${misura.disabledCheckDefinitivita}" />
									  	</display:column>
									</logic:notEqual>  
								</display:table>
							</display:column>
						</display:table>

						<input type="hidden" id="id_selectAll_beniProcedimento.idBene" value="true"/>
						<input type="hidden" id="id_selectAll_listaIdCheckati" value="true"/>
						
						<table class="tb_form">
							<tr>
								<td class="td_titolo" colspan="4"><spring:message code="atti.datadefinitivita.misurePatrimoniali" /></td>
							</tr>
						</table>
						<display:table class="tb_lista" uid="elemRisultatiRicerca" name="${risultatiRicerca}" pagesize="10" requestURI="${azioneR}">
						   <logic:notEqual scope="session" name="procedimento" property="statoProcedimento.id" value="22">
						
								<display:column paramId="bene.idBene" paramProperty="beniProcedimento[${elemRisultatiRicerca_rowNum-1}].idBene" style="width:3%" 
												title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('elemRisultatiRicerca','beniProcedimento.idBene');\" title=\"Seleziona/Deseleziona tutti i beni\"></img>&nbsp;Sel." >										
									<form:checkbox path="beniProcedimento[${elemRisultatiRicerca_rowNum-1}].idBene" value="${elemRisultatiRicerca.bene.idBene}" 
									    disabled="${elemRisultatiRicerca.bene.disabledCheckDefinitivita}"
										onclick="checkSoggetti(this, ${elemRisultatiRicerca_rowNum-1})" />
	
									
								</display:column>
							</logic:notEqual>
							<display:column property="bene.idBene" title="Identificativo" sortable="false" />
							<display:column property="bene.descrizioneBene" title="Descrizione" sortable="false" />
							<display:column title="Misura - Titolari di diritto">
								<display:table class="tb_sotto_lista" uid="soggetto" name="${elemRisultatiRicerca.soggetti}" requestURI="${azioneR}">
									<display:column property="misura.tipoMisura.descrizione" title="" />
									<display:column property="normativa.descrizione" title="" />
									<display:column property="descrizioneSoggetto" title="" />
									<display:column property="tipoInteresse.descrizione" title="" />
									<display:column property="descrizioneFrazioneOQuota" title="" />
									<c:if test="${soggetto.misura.decisioneMisuraPersonaleIIGrado!=null && soggetto.misura.decisioneMisuraPersonaleIIGrado.descrizione!=null}">
										<display:column property="misura.decisioneMisuraPersonaleIIGrado.descrizione" title="Decisione" />
									</c:if>
									<c:if test="${soggetto.misura.decisioneMisuraPersonaleIIGrado==null || soggetto.misura.decisioneMisuraPersonaleIIGrado.descrizione==null}">
										<display:column property="misura.decisioneMisuraPersonale.descrizione" title="Decisione" />
									</c:if>
									<display:column property="misura.dataDefinitivita" title="Definitivita'" format="{0,date,dd/MM/yyyy}" />
									<logic:notEqual scope="session" name="procedimento" property="statoProcedimento.id" value="22">
									
										<display:column sortable="false" style="text-align: center; width: 5%">
										
											<form:checkbox path="beniProcedimento[${elemRisultatiRicerca_rowNum-1}].listaIdCheckboxString" 
														   value="${soggetto.idSoggetto};${soggetto.tipoInteresse.id};${soggetto.misura.decisioneMisuraPersonale.id};${soggetto.misura.tipoMisura.id}"
														   name="soggettoTitolareDi${elemRisultatiRicerca.bene.idBene}" 
	 													   disabled="${soggetto.disabledCheckDefinitivita}" 													    
														   onclick="uncheckParentIfNotAllChildren(${elemRisultatiRicerca_rowNum-1})" />
	
										</display:column>
								     </logic:notEqual>
								</display:table>
							</display:column>
						</display:table>
					 </c:if>	
						
					<c:if test="${sessionScope.procedimento.tipoProcedimento.id==17}" >
		
						<table class="tb_form">
							<tr>
								<td class="td_titolo" colspan="3"><spring:message code="bene.benidelprocedimento" /></td>
							</tr>
						</table>
						
						<display:table class="tb_lista" uid="bene" name="${beniInAmministrazione}" >
							<display:column property="idBene" title="Identificativo" sortable="false"/>
							<display:column property="descrizioneBene" title="Descrizione" sortable="false"/>
						</display:table>
					
					</c:if>
					
					<c:if test="${sessionScope.procedimento.tipoProcedimento.id==19}" >
		
		
						<display:table id="row" name="${soggettoDaCorregere}" class="tb_lista" uid="amm" >
					        <display:column property="descrizioneSoggetto" title="Generalità - Soggetto da correggere" sortable="false"/>
				        </display:table>
				        <br/>
						<display:table id="row" name="${listaIntestatari}" class="tb_lista" uid="amm" >
							<display:column property="descrizioneAmministratore" title="Generalità - Soggetto corretto" sortable="false"/>
						</display:table>	
						
					
					</c:if>					
					<br />
					<br />
					<br />
					<!-- definitività: no in procura -->
					
					<c:if test="${profilo.idTipoUfficio!=17}">
						<logic:notEqual scope="session" name="procedimento" property="statoProcedimento.id" value="22">
							<table class="tb_form" border="0">
								<tr>
									<td class="td_titolo" colspan="4"><spring:message code="atti.definitivita" /></td>
								</tr>
								<tr>
									<td><form:label path="dataDefinitivitaAtto" cssClass="required">
											<spring:message code="atti.datadefinitivita" />
										</form:label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
										<c:if test="${disableDefinitività eq false}"> 
											<form:input id="id_dataDefinitivitaAtto" path="dataDefinitivitaAtto" maxlength="10" />
										</c:if>
										<c:if test="${disableDefinitività eq true}">
											<fmt:formatDate value="${attoForm.dataDefinitivita}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataDefinitivitaAtto" />
										</c:if> 
										
									</td>
									<form:errors path="dataDefinitivitaAtto" class="testoRosso" />
								</tr>
								
								<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=17 && sessionScope.procedimento.tipoProcedimento.id!=19}" >
									<tr>
										<td colspan="4"><form:label path="">
												<spring:message code="atti.datadefinitivita.all" />
											</form:label> <form:checkbox value="true" path="" id="all" 
											onclick="selezionaTutteMisure('elemRisultatiRicerca','beniProcedimento.idBene','elemRisRicerca','soggettiMisPers.idSoggetto')" /></td>
									</tr>
								</c:if>
								
							</table>
						</logic:notEqual>
					</c:if>
			
					<c:if test="${(sessionScope.procedimento.tipoProcedimento.id==17 || sessionScope.procedimento.tipoProcedimento.id==19 )&& sessionScope.procedimento.statoProcedimento.id==22}" >
						<table class="tb_form" border="0">
							<tr>
								<td class="td_titolo" colspan="4"><spring:message code="atti.definitivita" /></td>
							</tr>
							<tr>
								<td><form:label path="dataDefinitivitaAtto">
										<spring:message code="atti.datadefinitivita" />
									</form:label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
									<fmt:formatDate value="${attoForm.dataDefinitivita}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataDefinitivitaAtto" />
								</td>
								<form:errors path="dataDefinitivitaAtto" class="testoRosso" />
							</tr>
						</table>
					</c:if>
				
					<br />
					<br />

					<table class="tb_bottoni" align="center">
						<tr>
							<c:if test="${profilo.idTipoUfficio!=17 && profilo.idTipoUfficio!=8}">
							<!-- logic:notEqual scope="request" name="tip_proc" value="22" -->
								<logic:notEqual scope="session" name="procedimento" property="statoProcedimento.id" value="22">
									 <c:if test="${sessionScope.TIPO_FASCICOLO!=8}">
										 <c:if test="${profilo.ruolo!='MAGISTRATO'}">
										 	<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=15 && sessionScope.procedimento.tipoProcedimento.id!=17 && sessionScope.procedimento.tipoProcedimento.id!=19}" >
												<td class="td_tasto"><input type="submit" value="Salva" name="Salva" id="Salva"></td>
												<td class="td_tasto"><input type="submit" value="Cancella" name="Cancella" id="Cancella"></td>
												
											</c:if>
											<td class="td_tasto"><input type="submit" value="Conferma e Invia" name="ConfermaInviaDefinitivita" id="ConfermaInviaDefinitivita"></td>
										</c:if>
									</c:if>	
								</logic:notEqual>	
							</c:if>
							<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/atto/copertinaAtto.htm')"></td>
						</tr>
					</table>
				</form:form>
			</c:if>
		</tiles:putAttribute>
	</tiles:insertDefinition>

	<c:if test="${disableDefinitività eq false}">
	
		<script type="text/javascript">
			$(document).ready(function() {
				prendiData("#id_dataDefinitivitaAtto",true);
			});		
		</script>
		
	</c:if>
	
	<script type="text/javascript">

	$(document).ready(function() {
		$("#id_img_listaBeniNoTitDir").rotate({     bind:       { 
				mouseover : function() {              
					$(this).css("cursor","pointer"); 								 
				}, 							
				click : function() { 							
					
					if($("#id_div_listaBeniNoTitDir").is(":visible")){
						$(this).rotate({animateTo:0});  
						divScomparsa('id_div_listaBeniNoTitDir');
					}
					else {
						$(this).rotate({animateTo:90});
						divScomparsa('id_div_listaBeniNoTitDir');
					}
				}       
				
			}      
		});
	});	

	$(document).ready(function() {
		$("#id_img_listaBeniNoMisPatr").rotate({     bind:       { 
				mouseover : function() {              
					$(this).css("cursor","pointer"); 								 
				}, 							
				click : function() { 							
					
					if($("#id_div_listaBeniNoMisPatr").is(":visible")){
						$(this).rotate({animateTo:0});  
						divScomparsa('id_div_listaBeniNoMisPatr');
					}
					else {
						$(this).rotate({animateTo:90});
						divScomparsa('id_div_listaBeniNoMisPatr');
					}
				}       
				
			}      
		});
	});		

	function gestioneDocumento(azione) {					
		$('#id_downloadButton').attr('name',azione);
		$('#id_downloadButton').click();
		
	}

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

	function checkMisure(checkbox, indice) {
		
		var nome = "soggettiMisPers[" + indice + "].misuraInfo";

		var misure = document.getElementsByName(nome);

		if (checkbox.checked) {

			checkAll(misure);
		
		} else {

			uncheckAll(misure);
		}
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
		var misPatr = /listaIdCheckboxString/g;
		var misPers = /misuraInfo/g;
		
		if(nomeFiglio.match(misPers)){
			var nomePadre = nomeFiglio.replace("misuraInfo", "idSoggetto");
			var padre = document.getElementsByName(nomePadre);
			for (var i = 0; i < padre.length; i++){
				padre[i].checked = true;
			}
		}
		if(nomeFiglio.match(misPatr)){
			var nomePadre = nomeFiglio.replace("listaIdCheckboxString", "idBene");
			var padre = document.getElementsByName(nomePadre);
			for (var i = 0; i < padre.length; i++){
				padre[i].checked = true;
			}
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

	disabilitaCopertinaDefinitivita('${procedimento.statoProcedimento.id}', '${disableDefinitività}');

	function pulisciFiltri() {
		if(document.getElementsByName("soggettiDiretti.id").length != 0)
			document.getElementsByName("soggettiDiretti.id")[0].selectedIndex = -1;
		if(document.getElementsByName("soggettiTitolari.id").length != 0)
			document.getElementsByName("soggettiTitolari.id")[0].selectedIndex = -1;
		if(document.getElementsByName("tipologiaBene.id").length != 0)
			document.getElementsByName("tipologiaBene.id")[0].selectedIndex = -1;
		if(document.getElementsByName("categoriaBene.id").length != 0)
			document.getElementsByName("categoriaBene.id")[0].selectedIndex = -1;
		if(document.getElementsByName("sottocategoriaBene.id").length != 0)
			document.getElementsByName("sottocategoriaBene.id")[0].selectedIndex = -1;
	}	
		
	function pulisciFiltriPers() {
		if(document.getElementsByName("soggettiDirettiPers.id").length != 0)
			document.getElementsByName("soggettiDirettiPers.id")[0].selectedIndex = -1;
	}	
		
</script>
</body>