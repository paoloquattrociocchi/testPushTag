<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
		<spring:url var="azione" value="/procedimento/stralcio/gestioneStralcio" />
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="stralcioForm" enctype="multipart/form-data">
			<form:hidden path="idProcedimento" id="idProcedimento"/>
			<form:hidden path="idUfficio" id="idUffOwner"/>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<logic:notPresent name="riepilogoStralcio">			
				<logic:notPresent name="messaggioProcedura">	
					<c:set var="PATRIMONIALE_ID" value="<%=Constants.TipoMisura.PATRIMONIALE_ID%>"/>
					<c:set var="PERSONALE_ID" value="<%=Constants.TipoMisura.PERSONALE_ID%>"/>
					<c:set var="NO_MIS_ID" value="<%=Constants.TipoMisura.NO_MIS_ID%>"/>
					
					<c:set var="PATRIMONIALE_DESCR" value="<%=Constants.TipoMisura.PATRIMONIALE_DESCR%>"/>
					<c:set var="PERSONALE_DESCR" value="<%=Constants.TipoMisura.PERSONALE_DESCR%>"/>
					
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="td_titolo" colspan="6"><spring:message code="stralcio.gestioneStralcio" /></td>
						</tr>
						<tr>
							<td>
								<form:label path="tipoMisure.id">
									<spring:message code="stralcio.tipoMisura" />
								</form:label>
							</td>
							<td>
								<form:label path="soggettiDiretti.id">
									<spring:message code="stralcio.soggettiDiretti" />
								</form:label>
							</td>
							<td>
								<form:label path="soggettiTitolari.id">
									<spring:message code="stralcio.soggettiTitolari" />
								</form:label>
							</td>
							<td>
								<form:label path="tipologiaBene.id">
									<spring:message code="stralcio.beni" />
								</form:label>
							</td>
						</tr>
						<tr>&nbsp;
						</tr>
						<tr>
							<td>
								<form:select id="tipoMisure" path="tipoMisure.id" class="ricercaChange" onclick="disabilitaTitolariEBeniSeTipMisPers();">
									<form:option value="-1" label="---- Nessun filtro ----" />
									<form:options items="${tipoMisure}" itemValue="id" itemLabel="descrizione" />
								</form:select>
							</td>
							<td>
								<form:select id="soggettiDiretti" path="soggettiDiretti.id"  class="ricercaChange">
									<form:option value="-1" label="---- Nessun filtro ----" />
									<form:options items="${soggettiDiretti}" itemValue="id" itemLabel="descrizione" />
								</form:select>
							</td>
							<td>
								<form:select id="soggettiTitolari" path="soggettiTitolari.id" class="ricercaChange">
									<form:option value="-1" label="---- Nessun filtro ----" />
									<form:options items="${soggettiTitolari}" itemValue="id" itemLabel="descrizione" />
								</form:select>
							</td>
							<td>
								<table>
									<tr>
										<td><form:label path="tipologiaBene.id">
												<spring:message code="bene.tipologia" />
											</form:label>
										</td>
										<td colspan="3"><form:select id="tipologiaBene" path="tipologiaBene.id" size="1" cssStyle="width: 200px;" class="ricercaChange" onchange="caricaCombo('categoriaBene', $(this).val())">
												<form:option value="-1" label="---- Nessun filtro ----" />
												<form:options items="${tipologie}" itemValue="id" itemLabel="descrizione" />
											</form:select>
										</td>
									</tr>
									<tr>
										<td><form:label path="categoriaBene.id">
												<spring:message code="bene.categoria" />
											</form:label></td>
										<td colspan="3"><form:select id="categoriaBene" path="categoriaBene.id" size="1" cssStyle="width: 200px;" class="ricercaChange" onchange="caricaCombo('sottoCategoriaBene', $(this).val())">
												<form:option value="-1" label="---- Nessun filtro ----" />
												<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
											</form:select>
										</td>
									</tr>
									<tr>
										<td><form:label path="sottoCategoriaBene.id">
												<spring:message code="bene.sottocategoria" />
											</form:label>
										</td>
										<td colspan="3">
											<form:select id="sottoCategoriaBene" path="sottoCategoriaBene.id" size="1" cssStyle="width: 200px;" class="ricercaChange" >
												<form:option value="-1" label="---- Nessun filtro ----" />
												<form:options items="${sottoCategorieBene}" itemValue="id" itemLabel="descrizione" />
											</form:select>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<br />
						
					<table class="tb_bottoni" align="center">
						<tr>
							<td class="td_tasto"><input type="button" title="Pulisci filtri di ricerca" value="Pulisci" onclick="pulisciFiltri(); submitStralcio('ricercaStralcioOnSelect');" class="nowait" /></td>
							<!-- 
							<td class="td_tasto"><input type="button" name="RicercaStralcio" title="Ricerca stralcio" value="Ricerca" onclick="ricercaStralcioOnSelect()" />
							</td>
							 -->
						</tr>
					</table>
				</logic:notPresent>
			</logic:notPresent>
			
			<logic:present name="riepilogoStralcio">
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="6"><spring:message code="stralcio.riepilogoStralcio" /></td>
				</tr>
				</table>
			</logic:present>
			
			<logic:present name="listaStralcio">
				<div style="max-height: 400px; overflow-y: scroll; float: left; width: 100%;">
					<display:table class="tb_lista" uid="soggettoStralcioForm" name="${listaStralcio}">
							<display:column paramId="bene.idBene" paramProperty="beniProcedimento[${soggettoStralcioForm_rowNum-1}].idBene" style="width:1%" title="Sel." >	
								<input type="checkbox" onclick="impostaStralcio('Soggetto',this, 0, ${soggettoStralcioForm.idSoggetto}, 0);"
								${soggettoStralcioForm.flgSoggettoValido == 'O' ? 'checked' : ''}  ${riepilogoStralcio}>
							</display:column>
							<display:column title="Diretto interessato" style="width:10%">
								<c:out value="${soggettoStralcioForm.descrizioneSoggetto}"></c:out>
							</display:column>
							<c:choose>
							<c:when test="${soggettoStralcioForm.misura.descrizioneMisura == 'NO_MIS'}">
								<display:column title="">
									<spring:message code="stralcio.misureAssenti" />
								</display:column>
							</c:when>
							<c:otherwise>
								<display:column title="Misure">
									<display:table class="tb_sotto_lista" uid="bene" name="${soggettoStralcioForm.listaBeneStralcioForm}">
										<display:column title="Sel." style="width:1%">
											<c:choose>
												<c:when test="${soggettoStralcioForm.misura.descrizioneMisura == 'NO_MIS'}">
														---
												</c:when>
												<c:when test="${bene.tipoMisura == 'PATR'}">
													<input type="checkbox" onclick="impostaStralcio('${PATRIMONIALE_DESCR}',this, ${bene.idBene}, ${soggettoStralcioForm.idSoggetto}, 0);" ${bene.flgValidoBene == 'O' ? 'checked' : ''} ${riepilogoStralcio}>
												</c:when>
												<c:otherwise>
													<input type="checkbox" onclick="impostaStralcio('${PERSONALE_DESCR}',this, 0, ${soggettoStralcioForm.idSoggetto}, 0);" ${soggettoStralcioForm.flgValidoMisPers == 'O' ? 'checked' : ''} ${riepilogoStralcio}>
												</c:otherwise>
											</c:choose>

										</display:column>
										<c:choose>
											<c:when test="${soggettoStralcioForm.misura.descrizioneMisura == 'NO_MIS'}">
												<display:column title="" sortable="false" style="width:10%">
														--- 
														</display:column>
												<display:column title="Misura" style="width:80%">
														--- 
														</display:column>
											</c:when>
											<c:when test="${bene.tipoMisura == 'PERS'}">
												<display:column title="Misura" sortable="false" style="width:50%">
													<c:out value="${soggettoStralcioForm.misura.tipoMisura.descrizione} (${soggettoStralcioForm.misura.durata})"></c:out>
												</display:column>
												<display:column title="" style="width:80%">
													<c:out value="ART 67: ${soggettoStralcioForm.misura.art67}           CAUZIONE: ${soggettoStralcioForm.misura.cauzione}"></c:out>
												</display:column>
											</c:when>
											<c:otherwise>
												<display:column title="Misura" sortable="false" style="width:50%">
														<div title="${bene.descrizioneBene}">
															<c:out value="${bene.idBene} - ${bene.descrizioneBene}"></c:out>
														</div>
												</display:column>
												<display:column title="" style="width:80%">
													<display:table class="tb_sotto_lista" uid="soggetto" name="${bene.listaSoggettoTitolareForm}" >
														<display:column property="misura.tipoMisura.descrizione" title="" style="width:20%" />
														<display:column property="descrizioneSoggetto" title="" style="width:25%"/>
														<display:column property="tipoInteresse.descrizione" title="" style="width:8%"/>
														<display:column property="descrizioneFrazioneOQuota" title="" style="width:3%"/>
														<display:column sortable="false" style="text-align: center; width: 1%">
															<input type="checkbox" onclick="impostaStralcio('${PATRIMONIALE_DESCR}',this, ${bene.idBene}, ${soggettoStralcioForm.idSoggetto},${soggetto.idSoggetto});"
																${soggetto.misura.flagMisuraValido == 'O' ? 'checked' : ''} ${riepilogoStralcio}>
														</display:column>
													</display:table>
												</display:column>

											</c:otherwise>
										</c:choose>
									</display:table>
								</display:column>
							</c:otherwise>
						</c:choose>
					</display:table>
				</div>
				<div style="clear: both;" ></div>
				<br />
				<logic:empty name="riepilogoStralcio">
				<table class="tb_bottoni" align="center">
					<tr>
						<logic:notPresent name="messaggioProcedura">
							<td class="td_tasto"><input type="button" name="riepilogoStralcioButton" title="Salva" value="Salva" class="nowait" onclick="submitStralcio('riepilogoStralcio');"/></td>
						</logic:notPresent>
					</tr>
				</table>
				</logic:empty>
			</logic:present>

			<logic:notEmpty name="riepilogoStralcio">
				<logic:notEmpty name="listaStralcio">
					<table class="tb_form">
						<tr>
							<td><form:label path="dataStralcio" cssClass="required" ><spring:message code="stralcio.dataStralcio"/></form:label></td>
				        	<td colspan="3">		        		
				        		<form:input path="dataStralcio" maxlength="10" size="12" id="dataStralcio"/>
				        		<form:errors path="dataStralcio" class="testoRosso" />
				        				        		
				        	</td>
						</tr>
						<c:if test="${procedimento.tipoFascicolo.id != '2'}">
							<tr>
								<td><form:label path="magistrato.id" >
										<spring:message code="stralcio.magistrato" />
									</form:label>
								</td>
								<td colspan="3"><form:select id="magistrato" path="magistrato.id" size="1" cssStyle="width: 200px;">
										<form:option value="-1" label="------Seleziona magistrato--------------" />
										<form:options items="${listaMagistrati}" itemValue="id" itemLabel="descrizione" />
									</form:select>
									<form:errors path="magistrato.id" class="testoRosso" />
								</td>
							</tr>
						</c:if>													

					</table>
					<br />
					<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegatoStralcio.jsp" />
					<br />
					<table class="tb_bottoni" align="center">
						<tr>	
							  <td class="td_tasto"><input type="button"  value="Salva" class="nowait" onclick="inserisciAtto('SalvaStralcio', '${stralcioForm.allegato.documentoFirmato}');"></td>	
							  <input id="SalvaStralcio" type="submit" name="SalvaStralcio" value="" style="display: none" />

									
							<td class="td_tasto"><input type="button" name="EsecuzioneStralcio" title="Conferma" value="Conferma" class="nowait" onclick="inserisciAtto('ConfermaEsecuzioneStralcio', '${stralcioForm.allegato.documentoFirmato}');"/></td>
							<input	id="ConfermaEsecuzioneStralcio" type="submit" name="ConfermaEsecuzioneStralcio" value="" style="display: none" />
						
							<td class="td_tasto"><input type="button" id="id_buttonIndietro" value="Indietro"></td>
						</tr>
					</table>
					<br />
					<br />
				</logic:notEmpty>
			</logic:notEmpty>
				<td class="td_tasto">
				<input type="submit" name="" title="" value="" id="idSubmitStralcio" style="display: none"/></td>
				</td>
		</form:form>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="id_buttonIndietro"></smp:back>

		<script type="text/javascript">

			$( ".ricercaChange" ).change(function() {
				submitStralcio('ricercaStralcioOnSelect');
				//ricercaStralcioOnSelect();
			});


			function submitStralcio(name){
				$('#idSubmitStralcio').attr('name', name);
				$('#idSubmitStralcio').click();
			}

			/*
			function ricercaStralcioOnSelect() {

				$('#submitGestioneStralcio').attr('name', 'ricercaStralcioOnSelect');
				
				$('#id_formConfermaInvia').attr('action', '${pageContext.request.contextPath}/procedimento/stralcio/ricercaStralcioOnSelect' );
				$('#submitGestioneStralcio').click();
								
			}

			function riepilogo() {

				$('#submitGestioneStralcio').attr('name', 'riepilogoStralcio');
				$('#id_formConfermaInvia').attr('action', '${pageContext.request.contextPath}/procedimento/stralcio/riepilogoStralcio.htm' );
				$('#submitGestioneStralcio').click();
								
			}
			*/
					
			function pulisciFiltri() {

				abilitaFiltri();				
				$('#tipoMisure').val(-1);
				$('#soggettiDiretti').val(-1);
				$('#soggettiTitolari').val(-1);
				$('#tipologiaBene').val(-1);
				$('#categoriaBene').val(-1);
				$('#sottoCategoriaBene').val(-1);

				/*
				$('#id_formConfermaInvia').attr('method', 'get');
				$('#id_formConfermaInvia').attr('action', '${pageContext.request.contextPath}/procedimento/stralcio/gestioneStralcio.htm' );
				$('#submitGestioneStralcio').click();
				*/
				
			}

			
			function disabilitaTitolariEBeniSeTipMisPers() {
				
				var misPersSel = false;
				$('#tipoMisure :selected').each(
					function(i, selected) {
						if ($(selected).val() == 'PERS') {
							misPersSel = true;
							return;
						}
					});
				
				if (misPersSel) {
					disabilitaFiltri();
				} else {
					abilitaFiltri();
				}
			}

			function caricaCombo(comboDaCaricare, valoreSel){
				 $.ajax({  
					    type: "POST",  
					    url: "${pageContext.request.contextPath}/procedimento/stralcio/caricaCombo/"+comboDaCaricare+"/"+valoreSel,  
					    success: function(cbo){
							if(cbo){
								$('#'+comboDaCaricare).empty();
								jQuery('#'+comboDaCaricare).append(jQuery('<option></option').val('-1').html('------Seleziona categoria-------------'));

								$('#sottoCategoriaBene').empty();
								jQuery('#sottoCategoriaBene').append(jQuery('<option></option').val('-1').html('------Seleziona sottocategoria------'));
								 
					    		$.each(cbo, function(i, field){
						    	  jQuery('#'+comboDaCaricare).append(jQuery('<option></option').val(field.id).html(field.descrizione)); 
					    	    });
							}
					    },
					      error: function (xhr, ajaxOptions, thrownError) {}
					  }); 
			}
	
			function impostaStralcio(tipoStralcio, obj, idBene, idSoggInt, idSoggTit){

				var params = '?tipoStralcio='+tipoStralcio+'&checked='+$(obj).is(":checked")+ '&idProcedimento='+$('#idProcedimento').val()+ '&idUffOwner='+$('#idUffOwner').val()+ '&idBene='+idBene+ '&idSoggInt='+idSoggInt+ 
				'&idSoggTit='+normalizzaValore(idSoggTit)+'&filtroIdsSoggTit='+normalizzaValore($('#soggettiTitolari').val())+'&filtroIdsMisura='+normalizzaValore($('#tipoMisure').val())+'&filtroTipoBene='+$('#tipologiaBene').val()+'&filtroCatBene='+$('#categoriaBene').val()+'&filtroSottoCatBene='+$('#sottoCategoriaBene').val();

				//alert(params);
				$('#id_formConfermaInvia').attr('action', '${pageContext.request.contextPath}/procedimento/stralcio/impostaStralcio'+params );
				$('#id_formConfermaInvia').submit();
			}

			//toglie da valore il contenuto del null
			function normalizzaValore(valore){
				
				if(valore == -1){
					return '';
				}else{
					return valore;
				}
			}

				/*
			function esecuzioneStralcio(){
				var errMagistrato = false;
				var errDataStralcio = false;
				
				if(($("input:checkbox:checked").length) <= 0){
					alert('Non esiste alcuna informazione da stralciare!');
					return false;
				}
				if($('#magistrato').val() == '-1'){
						$('#magistratoError').html('Inserire il magistrato');
						errMagistrato = true;
				}
				
				if($('#dataStralcio').val() == ''){
					$('#dataStralcioError').html('Inserire la data stralcio');
					errDataStralcio = true;
				}

				if(errMagistrato || errDataStralcio){
						return false;
				}
				$('#id_formConfermaInvia').attr('action', '${pageContext.request.contextPath}/procedimento/stralcio/esecuzioneStralcio.htm' );
				//$('#id_formConfermaInvia').submit();
				//il submit lo fa in firmaRemota.js#inserisciAtto()
				
			}
				*/

			$(document).ready(function() {
				prendiData("#dataStralcio",true);
				disabilitaTitolariEBeniSeTipMisPers();

				prepopolaMultiselect('${stralcioForm.tipoMisure.id}', 'tipoMisure');
				prepopolaMultiselect('${stralcioForm.soggettiDiretti.id}', 'soggettiDiretti');
				prepopolaMultiselect('${stralcioForm.soggettiTitolari.id}', 'soggettiTitolari');

			});

			//funzione utilizzata per popolare le multicompbo dopo il refresh della pagina...
			function prepopolaMultiselect(selectedIds, targetSelect){
				var arraySel = selectedIds.split(',');
				$('#'+targetSelect).val(arraySel);
			}
			
			function abilitaFiltri(){
				$('#soggettiTitolari').removeAttr('disabled');
				$('#tipologiaBene').removeAttr('disabled');
				$('#categoriaBene').removeAttr('disabled');
				$('#sottoCategoriaBene').removeAttr('disabled');
			}

			function disabilitaFiltri(){
				$('#soggettiTitolari').attr('disabled', true);

				$('#tipologiaBene').val(-1);
				$('#tipologiaBene').attr('disabled', true );

				initCombo('categoriaBene', '------Seleziona categoria-------------');
				$('#categoriaBene').attr('disabled', true );

				initCombo('sottoCategoriaBene', '------Seleziona sottocategoria------');
				$('#sottoCategoriaBene').attr('disabled', true );
			}
			
			function initCombo (idCombo, primaLinea){
				$('#'+idCombo).empty();
				jQuery('#'+idCombo).append(jQuery('<option></option').val('-1').html(primaLinea));
			}

			
		</script>

	</tiles:putAttribute>
</tiles:insertDefinition>