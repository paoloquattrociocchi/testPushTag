<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atti/nuovo.htm" />
		<form:form method="post" action="${azione}" commandName="atto">
			<p class="titolo_page"><spring:message code="atti.nuovo"/></p>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<logic:notEmpty name="listaBeniNonDefiniti">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="40px" class="td_titolo_label" height="5px" title="visualizza/nasconde l'elenco dei beni che sono NON DEFINITI"><img id="id_img_listaBeniNoDefiniti" src="${pageContext.request.contextPath}/images/right_arrow_2.png" height="38"
							width="38" /></td>
						<td class="td_titolo_label"><p class="testoRosso">
								<spring:message code="bene.no.definiti" />
							</p></td>
					</tr>
				</table>
				<div id="id_div_listaBeniNoDefiniti" style="display: none;">
					<display:table class="tb_lista" uid="bene" name="${listaBeniNonDefiniti}" pagesize="10" requestURI="${paginazione}">
						<display:column property="idBene" title="Identificativo" sortable="false" />
						<display:column property="descrizioneBene" title="Descrizione" sortable="false" />
					</display:table>
				</div>
			</logic:notEmpty>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td><form:label path="tipoAtto.id">
							<spring:message code="atto.tipi" />
						</form:label></td>
					<td><form:select path="tipoAtto.id" size="1" onchange="this.form.submit()">
							<form:option value="-1" label="------Seleziona Tipo Atto-------" />
							<form:options items="${tipiAtto}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>
				<logic:notEmpty name="categoria">
					<tr>
						<td><form:label path="categoriaAtto.id">
								<spring:message code="atto.categoria" />
							</form:label></td>
						<td>
						 <form:select path="categoriaAtto.id" size="1" onchange="selezionaCategoriaAtto(this.value)">
								<form:option value="-1" label="------Seleziona Categoria Atto-------" />
								<form:options items="${categoria}" itemValue="id" itemLabel="descrizione" />
							</form:select>
						</td>
					</tr>
				</logic:notEmpty>
				<logic:notEmpty name="listaDecisorioIGradoCombo">
					<tr>
						<td><form:label path="decisorioIGrado.id">
								<spring:message code="ricorsoAppello.decisori" />
							</form:label></td>
						<td><form:select path="decisorioIGrado.id" onchange="selezionaDecisorio(this.value)">
								<form:option value="-1" label="------Seleziona Decisorio-------" />
								<form:options items="${listaDecisorioIGradoCombo}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>						
					</tr>
				</logic:notEmpty>		
				<logic:notEmpty name="listaDecisorioIIGradoCombo">
					<tr>
						<td><form:label path="decisorioIIGrado.id">
								<spring:message code="ricorsoCassazione.decisori" />
							</form:label></td>
						<td><form:select path="decisorioIIGrado.id" onchange="selezionaDecisorioIIGrado(this.value)">
								<form:option value="-1" label="------Seleziona Decisorio-------" />
								<form:options items="${listaDecisorioIIGradoCombo}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>						
					</tr>
				</logic:notEmpty>
				
				<logic:notEmpty name="listaprovvIstCombo">
					<tr>
						<td><form:label path="decisorioIIGrado.id">
								<spring:message code="ricorsoCassazione.provvedimento" />
							</form:label></td>
						<td><form:select path="decisorioIIGrado.id" onchange="selezionaDecisorioIIGrado(this.value)">
								<form:option value="-1" label="------Seleziona Provvedimento-------" />
								<form:options items="${listaprovvIstCombo}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>						
					</tr>
				</logic:notEmpty>	
				<logic:notEmpty name="listaRevocazioneCombo">
					<tr>
						<td><form:label path="decisorioIIGrado.id">
								<spring:message code="ricorsoCassazione.revocazione" />
							</form:label></td>
						<td><form:select path="decisorioIIGrado.id" onchange="selezionaDecisorioIIGrado(this.value)">
								<form:option value="-1" label="------Seleziona Revocazione Confisca-------" />
								<form:options items="${listaRevocazioneCombo}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>						
					</tr>
				</logic:notEmpty>			
				
				
				
				<logic:notEmpty name="listaSoggettiOrdAntimafia">
					<tr>
						<td><form:label path="soggettiAntimafia.id">
								<spring:message code="antimafia.soggetti.lista" />
							</form:label></td>
						<td><form:select path="soggettiAntimafia.id" onchange="selezionatoSoggettoOrdinanza(this.value)">
								<form:option value="-1" label="------Seleziona Soggetto-------" />
								<form:options items="${listaSoggettiOrdAntimafia}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>						
					</tr>
				</logic:notEmpty>	
				<logic:notEmpty name="listaSequestriPerditaEfficaciaCombo">
					<tr>
						<td><form:label path="seqPerditaEfficacia.id">
								<spring:message code="atto.decreto.sequestro" />
							</form:label></td>
						<td><form:select path="seqPerditaEfficacia.id" onchange="selezionaSequestroPerditaEfficacia(this.value)">
								<form:option value="-1" label="------Seleziona Sequestro-------" />
								<form:options items="${listaSequestriPerditaEfficaciaCombo}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>						
					</tr>
				</logic:notEmpty>					
									
			</table>
			<br />
			<br />
			<logic:present name="direttiInteressatiSenzaDifensore">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="40px" class="td_titolo_label" height="5px" title="apre/chiude l'elenco dei diretti interessati senza difensore"><img id="id_img_direttiInteressatiSenzaDifensore" src="${pageContext.request.contextPath}/images/right_arrow_2.png"
							height="38" width="38" /></td>
						<td class="td_titolo_label"><p class="testoRosso">
								<spring:message code="atto.udienza.sogint.nodifensore" />
							</p></td>
					</tr>
				</table>
				<div id="id_div_direttiInteressatiSenzaDifensore" style="display: none;">
					<display:table class="tb_lista" uid="direttiInteressatiSenzaDifensore" name="${direttiInteressatiSenzaDifensore}" pagesize="10" requestURI="${azione}">
						<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" style="width: 50%" />
					</display:table>
				</div>
			</logic:present>
			<br />
			<div id="id_div_nuovo_atto" style="visibility: hidden">
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input id="id_button_nuovo_atto" type="submit" name="SelezionaCategoria" title="Conferma" value="Conferma"></td>
					</tr>
				</table>
			</div>
			
			<div id="id_div_nuovo_ricorso" style="visibility: hidden">
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input id="id_button_nuovo_ricorso" type="submit" name="SelezionatoDecisorio" title="Conferma" value="Conferma"></td>
					</tr>
				</table>
			</div>	
			<div id="id_div_nuovo_ordinanza_antimafia" style="visibility: hidden">
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input id="id_button_nuovo_ordinanza_antimafia" type="submit" name="SelezionatoSoggettoOrdinanza" title="Conferma" value="Conferma"></td>
					</tr>
				</table>
			</div>				
			<div id="id_div_nuovo_ricorso_cassazione" style="visibility: hidden">
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input id="id_button_nuovo_ricorso_cassazione" type="submit" name="SelezionatoDecisorioIIGrado" title="Conferma" value="Conferma"></td>
					</tr>
				</table>
			</div>	
			
			<div id="id_div_nuovo_perdita_efficacia" style="visibility: hidden">
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input id="id_button_nuovo_perdita_efficacia" type="submit" name="SelezionatoSequestroPerditaEfficacia" title="Conferma" value="Conferma"></td>
					</tr>
				</table>
			</div>			
			
			
			
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
<script type="text/javascript">
			function selezionaCategoriaAtto(idCategoria) {
				
				if(idCategoria == '6' || idCategoria == '8' || idCategoria == '25' || idCategoria == '308' || idCategoria == '310' || idCategoria == '309' || idCategoria == '63' || idCategoria == '64' || idCategoria == '65' || idCategoria == '66' || idCategoria == '5' || idCategoria == '7' || idCategoria == '337' || idCategoria == '338'|| idCategoria == '24'|| idCategoria == '335'|| idCategoria == '358' || idCategoria == '363'|| idCategoria == '369'){
					// 6 Opposizione al dissequestro
					// 8 - 25 = decreto di fissazione udienza I e II grado
					// 308 = decreto di incompetenza e restituzione atti
					// 309 = Decreto di incompetenza e trasferimento ad altro tribunale
					// 310 = Decreto di incompetenza della procura
					// 63 - 65 = Decreto di assegnazione nuovo ruolo I e II grado
					// 64 - 66 = Decreto di remissione su ruolo I e II grado
					// 5 = Ricorso in Appello
					// 337= Ricorso tardivo in appello
					// 338= Ricorso successivo al primo invio in Corte
					// 24= Ricorso Avverso Decreto II Grado
					// 335= Ricorso tardivo cassazione
					
				   $("#id_button_nuovo_atto").click();
				} 
				else if (idCategoria == '-1'){	
					$("#id_div_nuovo_atto").css('visibility','hidden');							
				}else{
									
					$("#id_div_nuovo_atto").css('visibility','visible');
					
				}
			}

			function selezionaDecisorio(idDecisorio) {	
				
				if (idDecisorio == '-1'){	
					$("#id_div_nuovo_ricorso").css('visibility','hidden');						
				}else{
									
					$("#id_div_nuovo_ricorso").css('visibility','visible');
					
				}

			}			
			
			function selezionaDecisorioIIGrado(idDecisorio) {	
				
				if (idDecisorio == '-1'){	
					$("#id_div_nuovo_ricorso_cassazione").css('visibility','hidden');						
				}else{
									
					$("#id_div_nuovo_ricorso_cassazione").css('visibility','visible');
					
				}

			}

			function selezionaSequestroPerditaEfficacia(idPerditaEfficacia) {	
				
				if (idPerditaEfficacia == '-1'){	
					$("#id_div_nuovo_perdita_efficacia").css('visibility','hidden');						
				}else{
									
					$("#id_div_nuovo_perdita_efficacia").css('visibility','visible');
					
				}

			}

						

			function selezionatoSoggettoOrdinanza(idSoggetto) {	
				
				if (idSoggetto == '-1'){	
					$("#id_div_nuovo_ordinanza_antimafia").css('visibility','hidden');						
				}else{
									
					$("#id_div_nuovo_ordinanza_antimafia").css('visibility','visible');
					
				}

			}
			
		
			
			$(document).ready(function() {
				$("#id_img_direttiInteressatiSenzaDifensore").rotate({     bind:       { 
						mouseover : function() {              
							$(this).css("cursor","pointer"); 								 
						}, 							
						click : function() { 							
							
							if($("#id_div_direttiInteressatiSenzaDifensore").is(":visible")){
								$(this).rotate({animateTo:0});  
								divScomparsa('id_div_direttiInteressatiSenzaDifensore');
							}
							else {
								$(this).rotate({animateTo:90});
								divScomparsa('id_div_direttiInteressatiSenzaDifensore');
							}
						}       
						
					}      
				});
			});	

			$(document).ready(function() {
				$("#id_img_listaBeniNoDefiniti").rotate({     bind:       { 
						mouseover : function() {              
							$(this).css("cursor","pointer"); 								 
						}, 							
						click : function() { 							
							
							if($("#id_div_listaBeniNoDefiniti").is(":visible")){
								$(this).rotate({animateTo:0});  
								divScomparsa('id_div_listaBeniNoDefiniti');
							}
							else {
								$(this).rotate({animateTo:90});
								divScomparsa('id_div_listaBeniNoDefiniti');
							}
						}       
						
					}      
				});
			});	
		</script>
