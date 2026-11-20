<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="it.giustizia.smp.form.ProcedimentoForm"%>
<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/redazioneScheda.htm" />
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			<form:hidden path="idProcedimento" id="idProcedimento"/>
			<form:hidden path="idMisura" id="idMisura"/>
			<form:hidden path="soggetti[0].idSoggetto" id="idSoggetto" />
			<form:hidden path="soggetti[0].idVersione" id="idVersione" />
			<form:hidden path="idProcedimento" id="idProcedimento"/>
			<form:hidden path="dataDefinitivita" id="dataDefinitivita"/>
			<form:hidden path="idAtto" id="idAttoMisura"/>
			<form:hidden path="descNormaProc" id="descNormaProc"/>
			<input type="hidden" name="hasOmonimi" id="hasOmonimi" value="<c:out value="${hasOmonimi}" />"/>
			<input type="hidden" name="idNSC" id="idNSC"/>
			<input type="hidden" name="idNSCDownload" id="idNSCDownload"/>
			<logic:notPresent name="isReadOnly">
				<p class="titolo_page">
					<spring:message code="redazioneScheda.nuovaRedazioneSchedaCasellario"/>
				</p>
				<logic:present name="listaMisure">
					<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				</logic:present>
			</logic:notPresent>
			<logic:present name="isReadOnly">
				<p class="titolo_page">
					<spring:message code="redazioneScheda.dettaglioRedazioneSchedaCasellario"/>
				</p>
			</logic:present>
			<logic:present name="listaMisure">
				<!-- INZIO - Lista delle Misure -->
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="6"><spring:message code="redazioneScheda.elencoSoggettiMisure"/></td>
				</tr>
				</table>
				<div style="max-height: 400px; min-height: 100px; overflow-y: scroll; float: left; width: 100%;">
					<display:table class="tb_lista" uid="listaMisure" name="${listaMisure}">
						<display:column style="width:2%" title="Sel."  >
							<c:if test="${listaMisure.showOption}">
								<input type="radio" name="misura"  onclick="impostaMisura(${listaMisure.CODI_K_ID_MISURA},${listaMisure.CODI_K_ID_PERSONA_FIS},${listaMisure.CODI_FK_ID_VER},${listaMisure.CODI_K_ID_ATTO},'<fmt:formatDate value="${listaMisure.DATA_DEFINITIVITA}" type="date" pattern="dd/MM/yyyy"/>');">
							</c:if>  
						</display:column>
						<display:column title="Diretto interessato" style="width:30%">
							<c:out value="${listaMisure.DESC_COGNOME} ${listaMisure.DESC_NOME}"></c:out>
							&nbsp;nato il&nbsp;
							<fmt:formatDate value="${listaMisure.DATA_NASCITA}" type="date" pattern="dd/MM/yyyy"/>
						</display:column>
						<display:column title="Misura" style="width:40%">
							<c:out value="${listaMisure.DESC_TIPO_MISURA}"></c:out>
						</display:column>
						<display:column title="Data Redazione" style="width:20%">
							<fmt:formatDate value="${listaMisure.DATA_EMISSIONE}" type="date" pattern="dd/MM/yyyy" />
						</display:column>
						<display:column title="Scheda" style="width:20%">
							<c:out value="${listaMisure.STATO}"></c:out>  
						</display:column>
					</display:table>
				</div>
				<div style="clear: both;" ></div>
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto" id="id_div_nuova_scheda" ><input type="submit" value="Nuova Scheda" title="Nuova Scheda" name="nuovaRedazioneSchedaDettaglio" onClick="handleSubmit(this)" class="nowait" /></td>
						<td class="td_tasto"><input type="button" id="id_buttonIndietro" value="Indietro" onClick="indietro('/procedimento/atti/nuovo.htm')"></td>
					</tr>
				</table>
				<!-- FINE - Lista delle Misure -->
			</logic:present>
			<logic:notPresent name="listaMisure">
				<!-- INZIO - DETTAGLIO -->
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" width="18%"><spring:message code="procedimento" /></td>
						<td class="td_titolo" width="32%"><c:out value="${procedimento.annoNumeroProcedimento}" /></td>
						<td class="td_titolo" width="18%"><spring:message code="procedimento.dataIscrizione" /></td>
						<td class="td_titolo" width="32%"><fmt:formatDate value="${procedimento.dataApertura}" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>
					<tr>
						<td class="td_titolo"><spring:message code="procedimento.tipo" /></td>
						<td class="td_titolo">
							<c:if test="${procedimento.tipoProcedimento!=null}">
								<c:out value="${procedimento.tipoProcedimento.descrizione}" />
							</c:if>
						</td>
						<td class="td_titolo"><spring:message code="procedimento.statoProcedimento" /></td>
						<td class="td_titolo">
							<c:if test="${procedimento.statoProcedimento!=null}">
								<c:out value="${procedimento.statoProcedimento.descrizione}" />
							</c:if>
						</td>
					</tr>
					<tr>
						<td><spring:message code="atto.tipo.atto" /></td>
						<td><c:out value="${sessionScope.atto.tipoAtto.descrizione}" /></td>
						<td><spring:message code="atto.categoria.atto" /></td>
						<td><c:out value="${sessionScope.atto.categoriaAtto.descrizione}" /></td>
					</tr>		
					<tr>
						<td><spring:message code="atto.numero" /></td>
						<td><c:out value="${sessionScope.atto.numeroAtto}" /></td>
						<td><spring:message code="redazioneScheda.ufficioProvvedimento" /></td>
						<td><c:out value="${attoForm.descTipoUfficio}"/> di <c:out value="${attoForm.descSedeUfficio}"/></td> 
					</tr>
					<tr>
						<td><spring:message code="atto.dataemissione" /></td>
						<td><fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /></td> 
						<td><spring:message code="atto.datadeposito" /></td>
						<td><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /></td> 
					</tr>
					<tr>
						<td><spring:message code="atto.dataregistrazione" /></td>
						<td><fmt:formatDate value="${attoForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /></td> 
						<td><spring:message code="atti.datadefinitivita" /></td>
						<td><fmt:formatDate value="${attoForm.dataDefinitivita}" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>
					<logic:present name="hasImpugnazioniPrimoGrado">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="redazioneScheda.impugnazionePrimoGrado"/></td>
						</tr>
						<tr>
							<td><spring:message code="atto.categoria.atto" /></td>
							<td><c:out value="${attoForm.impugnazioniPrimoGrado[0].DESC_CAT_ATTO}"/></td> 
							<td><spring:message code="redazioneScheda.autorita" /></td>
							<td><c:out value="${attoForm.impugnazioniPrimoGrado[0].DESC_UFFICIO_OWNER}"/></td> 
						</tr>
						<tr>
							<td><spring:message code="atto.numero" /></td>
							<td><c:out value="${attoForm.impugnazioniPrimoGrado[0].ANNO_ATTO}"/> / <c:out value="${attoForm.impugnazioniPrimoGrado[0].NUMERO_ATTO}"/></td> 
							<td><spring:message code="atto.dataemissione" /></td>
							<td><fmt:formatDate value="${attoForm.impugnazioniPrimoGrado[0].DATA_EMISSIONE_DECISORIO}" type="date" pattern="dd/MM/yyyy" /></td> 
						</tr>
					</logic:present>
					<logic:present name="hasSentenzaCassazione">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="redazioneScheda.sentenzaCassazione"/></td>
						</tr>
						<tr>
							<td><spring:message code="atto.categoria.atto" /></td>
							<td><c:out value="${attoForm.sentenzaCassazione.DESC_CAT_ATTO}"/></td> 
							<td><spring:message code="redazioneScheda.autorita" /></td>
							<td><c:out value="${attoForm.sentenzaCassazione.DESC_UFFICIO_OWNER}"/></td> 
						</tr>
						<tr>
							<td><spring:message code="atto.numero" /></td>
							<td><c:out value="${attoForm.sentenzaCassazione.ANNO_ATTO}"/> / <c:out value="${attoForm.sentenzaCassazione.NUMERO_ATTO}"/></td> 
							<td><spring:message code="atto.dataemissione" /></td>
							<td><fmt:formatDate value="${attoForm.sentenzaCassazione.DATA_EMISSIONE_DECISORIO}" type="date" pattern="dd/MM/yyyy" /></td> 
						</tr>
					</logic:present>
					<tr>
						<td class="td_titolo" style="cursor:pointer;" colspan="4" onClick="showHideSoggetto()" title="Fai click sulla riga per visualizzare o nascondere gli altri dati del soggetto"><img id="altriDatiSoggettoImg" src="${pageContext.request.contextPath}/images/arrow_r.png" />&nbsp;&nbsp;<spring:message code="redazioneScheda.soggetto"/>:&nbsp;&nbsp;<c:out value="${attoForm.soggetti[0].descrizioneSoggetto}" /></td>
					</tr>
				</table>
				<div id="altriDatiSoggetto"  style="display:none" >
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr id="trsog1">
						<td width="18%"><spring:message code="nuovosoggettofisico.sesso" /></td>
						<td width="32%"><c:out value="${attoForm.soggetti[0].descrSesso}" /></td>
						<td width="18%"><spring:message code="codiceFiscale" /></td>
						<td width="32%"><c:out value="${attoForm.soggetti[0].codiceFiscale}" /></td>
					</tr>
					<tr id="trsog2">
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo7" /></td>
					</tr>
					<tr id="trsog3">
						<td><spring:message code="stato" /></td>
						<td><c:out value="${attoForm.soggetti[0].statoMorte.descrizione}"/></td>					
						<td><spring:message code="comune"/></td>
			        	<td><c:out value="${attoForm.soggetti[0].descrizioneComuneMorteItalianoEstero}"/> </td>
					</tr>
					<tr id="trsog4">
						<td><spring:message code="nuovosoggettofisico.datadecesso" /></td>
						<td colspan="3"><fmt:formatDate value="${attoForm.soggetti[0].dataMorte}" type="date" pattern="dd/MM/yyyy" /></td> 
					</tr>
					<!-- Paternità e Maternità -->
					<tr id="trsog5">
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo8"/></td>
					</tr>
					<tr id="trsog6">
						<td><spring:message code="cognome" /></td>
						<td><c:out value="${attoForm.soggetti[0].cognomePaternita}"/></td>
						<td><spring:message code="nome" /></td>
						<td><c:out value="${attoForm.soggetti[0].nomePaternita}"/></td>
					</tr>
						<tr id="trsog5">
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo9"/></td>
					</tr>
					<tr id="trsog7">
						<td><spring:message code="cognome" /></td>
						<td><c:out value="${attoForm.soggetti[0].cognomeMaternita}"/></td>
						<td><spring:message code="nome" /></td>
						<td><c:out value="${attoForm.soggetti[0].nomeMaternita}"/></td>
					</tr>
				</table>
				</div>
				<br/>
				<!--  Informazione decisorio -->
				<table class="tb_form" border="0">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="redazioneScheda.titoloMisura" /></td>
					</tr>
					<!-- 
					<tr>
						<td><spring:message code="soggetto.misurapersonale.decisione" /></td>
						<td colspan="3"><c:out value="${attoForm.soggetti[0].misura.decisioneMisuraPersonale.descrizione}" /></td>
					</tr>
					-->
					<tr>
						<td width="18%"><spring:message code="procedimento.tipologiaPrevenzione" /></td>
						<td width="32%"><c:out value="${attoForm.soggetti[0].tipologiaPrevenzione.descrizione}" /></td>
						<td width="18%"><spring:message code="procedimento.normativa" /></td>
						<td width="32%"><c:out value="${attoForm.descNormaProc}"/></td>
					</tr>
					<tr>
						<td><spring:message code="soggetto.misurapersonale" /></td>
						<td><c:out value="${attoForm.soggetti[0].misura.tipoMisura.descrizione}" /></td>
						<td><spring:message code="procedimento.normativa" /></td>
						<td><c:out value="${attoForm.soggetti[0].misura.normativaDiRiferimento.descrizione}" /></td>
					</tr>
					<tr>
						<td><spring:message code="soggetto.misurapersonale.localita" /></td>
						<td colspan="3"><form:textarea path="soggetti[0].misura.localita" rows="2" cssStyle="width:98%" readonly="true" /></td>
					</tr>
					<tr>
						<td><spring:message code="soggetto.misurapersonale.esito" /></td>
						<td><c:out value="${attoForm.soggetti[0].misura.decisioneMisuraPersonale.descrizione}" /></td>
						<td><spring:message code="soggetto.misurapersonale.durata.generica" /></td>
						<td><c:if test="${attoForm.soggetti[0].misura.anni>0}">
								<c:if test="${attoForm.soggetti[0].misura.anni==1}">
									<c:out value="${attoForm.soggetti[0].misura.anni}" /> anno
									</c:if>
								<c:if test="${attoForm.soggetti[0].misura.anni>1}">
									<c:out value="${attoForm.soggetti[0].misura.anni}" /> anni
									</c:if>
							</c:if> <c:if test="${attoForm.soggetti[0].misura.mesi>0}">
								<c:if test="${attoForm.soggetti[0].misura.mesi==1}">
									<c:out value="${attoForm.soggetti[0].misura.mesi}" /> mese
									</c:if>
								<c:if test="${attoForm.soggetti[0].misura.mesi>1}">
									<c:out value="${attoForm.soggetti[0].misura.mesi}" /> mesi
									</c:if>
							</c:if> <c:if test="${attoForm.soggetti[0].misura.giorni>0}">
								<c:if test="${attoForm.soggetti[0].misura.giorni==1}">
									<c:out value="${attoForm.soggetti[0].misura.giorni}" /> giorno
									</c:if>
								<c:if test="${attoForm.soggetti[0].misura.giorni>1}">
									<c:out value="${attoForm.soggetti[0].misura.giorni}" /> giorni
									</c:if>
							</c:if>
						</td>
						
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.cauzione" /></td>
					</tr>
					<tr>
						<td><spring:message code="soggetto.misurapersonale.cauzione" /></td>
						<td><form:radiobutton path="soggetti[0].misura.cauzione" value="S" disabled="true" /> <form:label path="soggetti[0].misura.cauzione">
								<spring:message code="si" />
							</form:label> <form:radiobutton path="soggetti[0].misura.cauzione" value="N" disabled="true" /> <form:label path="soggetti[0].misura.cauzione">
								<spring:message code="no" />
							</form:label></td>
						<logic:present name="importoCauzione">
							<td><spring:message code="soggetto.misurapersonale.cauzioneimporto" /></td>
							<td><c:out value="${attoForm.soggetti[0].misura.importo}" /></td>
						</logic:present>
						<logic:notPresent name="importoCauzione">
							<td colspan="2"></td>
						</logic:notPresent>
					</tr>
					<logic:present name="importoCauzione">
						<tr>
							<td><spring:message code="soggetto.misurapersonale.note" /></td>
							<td colspan="3"><c:out value="${attoForm.soggetti[0].misura.note}" /></td>
						</tr>
					</logic:present>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.articolo67" /></td>
					</tr>
					<tr>
						<td colspan="1"><spring:message code="soggetto.misurapersonale.applicazioneart67" /></td>
						<td colspan="3">
							<form:radiobutton path="soggetti[0].misura.art67" value="S" disabled="true" /> <form:label path="soggetti[0].misura.art67">
								<spring:message code="si" />
							</form:label> <form:radiobutton path="soggetti[0].misura.art67" value="N" disabled="true" /> <form:label path="soggetti[0].misura.art67">
								<spring:message code="no" />
							</form:label>
						</td>
					</tr>
				
					
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="redazioneScheda.schedaCasellario" /></td>
					</tr>
					<tr>
						<td width="18%"><spring:message code="redazioneScheda.stato" /></td>
						<td width="32%">
							<c:if test="${attoForm.comunicazioneForm==null}">
								Assente        		
							</c:if>
							<c:if test="${attoForm.comunicazioneForm!=null}">
								<c:out value="${attoForm.comunicazioneForm.descInvioComunicazione}"></c:out>
								<c:if test="${attoForm.comunicazioneForm.flagStatoInvioComunicazione=='2' || attoForm.comunicazioneForm.flagStatoInvioComunicazione=='4'}"> il <fmt:formatDate value="${attoForm.comunicazioneForm.dataInvio}" type="date" pattern="dd/MM/yyyy" /> </c:if>
							</c:if>
						</td>
			        		<logic:notPresent name="isReadOnly">		        		
						        <td  width="18%"><form:label path="dataRedazioneScheda"  cssClass="required"><spring:message code="redazioneScheda.dataRedazione"/></form:label></td>
					        	<td  width="32%">
						        	<form:input path="dataRedazioneScheda" maxlength="10" size="12" id="dataRedazioneScheda"/>
					        		<form:errors path="dataRedazioneScheda" class="testoRosso"/>			        		
					    		</td>	    	
				        	</logic:notPresent>
				        	<logic:present name="isReadOnly">
				        		<td  width="18%"><c:if test="${attoForm.dataRedazioneScheda!=null}"><spring:message code="redazioneScheda.dataRedazione"/></c:if></td>
				        		<td  width="32%"><fmt:formatDate value="${attoForm.dataRedazioneScheda}" type="date" pattern="dd/MM/yyyy" /></td>
				        	</logic:present>
					</tr>
					
					<logic:present name="hasOmonimi">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="redazioneScheda.selezionaOmonimi"/></td>
						</tr>
						<tr>
							<td width="100%" colspan="4">
								<div style="max-height: 400px; min-height: 100px; overflow-y: scroll; float: left; width: 100%;">
									<display:table class="tb_lista" uid="listaOmonimi" name="${listaOmonimi}">
										<display:column style="width:2%" title="Sel."  >
											<c:if test="${listaOmonimi.deceduto=='No'}">
												<input type="radio" name="omonimo" onclick="impostaOmonimo(${listaOmonimi.idNSC});">
											</c:if>
										</display:column>
										<display:column title="Anagrafica Soggetto" style="width:60%">
											<c:out value="${listaOmonimi.datiSoggetto}"></c:out>
										</display:column>
										<display:column title="Codice Fiscale" style="width:23%">
											<c:out value="${listaOmonimi.codiceFiscale}"></c:out>  
										</display:column>
										<display:column title="Deceduto" style="width:13%">
											<c:out value="${listaOmonimi.deceduto}"></c:out>  
										</display:column>
										<display:column title="Pdf" style="width:5%">
											<img onclick="scaricaPDFOmonimo(${listaOmonimi.idNSC})" src="${pageContext.request.contextPath}/images/download.gif" style="cursor: pointer; border: none" title="Visualizza documento" ></img>
										</display:column>
									</display:table>
								</div>
							</td>
						</tr>
					</logic:present>		        		
			        <logic:present name="isInviata">		        		
			        	<logic:notPresent name="isCancellataFisicamente">
						    <tr>
								<td colspan="2" onclick="scaricaPDFCasellario()"><span class="required" style="cursor:pointer;"><spring:message code="redazioneScheda.pdfCertificatoCasellario"/>&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/images/download.gif" style="cursor: pointer; border: none" title="Visualizza documento" ></img></span></td>
								<logic:present name="hasModuloElettorale">
									<td colspan="2" onclick="scaricaPDFSchedaElettorale()"><span class="required" style="cursor:pointer;"><spring:message code="redazioneScheda.pdsSchedaElettorale"/>&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/images/download.gif" style="cursor: pointer; border: none" title="Visualizza documento" ></img></span></td>
								</logic:present>
								<logic:notPresent name="hasModuloElettorale">
									<td colspan="2" ><spring:message code="redazioneScheda.pdsSchedaElettorale"/><b>&nbsp;Non Disponibile&nbsp;</b></span></td>
								</logic:notPresent>
					        </tr>
				        </logic:notPresent>
			        </logic:present>
					<tr>
				</table>
				<br/><br/>
				<table class="tb_bottoni" align="center">
					<tr>
						<logic:notPresent name="isReadOnly">
							<td class="td_tasto" ><input type="submit" value="Salva" title="Salva" name="nuovaRedazioneSchedaDettaglioSalva" onClick="handleSubmit(this)"  class="nowait" /></td>
						</logic:notPresent>
						
						<logic:present name="isReadOnly">
							<logic:notPresent name="isInviata"><!-- commentato per debug style="${hideOnLoad}"  -->
								<td class="td_tasto" id="td_invia" ><input type="submit" value="Invia" title="Invia" id="Invia"  name="inviaSchedaACasellario"  onClick="handleSubmit(this)"   class="nowait" /></td>
							</logic:notPresent> 
							<logic:notPresent name="isCancellata">
								<td class="td_tasto" ><input type="submit" value="Cancella" title="Cancella" name="cancellaSchedaACasellario"  onClick="return confermaCancellazione(this)"   class="nowait"  /></td>
							</logic:notPresent>
						</logic:present>
					
						<td class="td_tasto">
							<input type="button" id="id_buttonIndietro" value="Indietro" onClick="vaiIndietro()">
						</td>
					</tr>
				</table>
			</logic:notPresent>
		</form:form>
		 <script  type="text/javascript">
		 	$(document).ready(function() {
				prendiData("#dataRedazioneScheda",true);
		 	});

			function impostaOmonimo(idNSC) {
				document.getElementById("idNSC").value = idNSC;
				$("#td_invia").css('visibility','visible');
			}

			function confermaCancellazione(button) {
				 if(confirm('Sei sicuro di voler cancellare?')){
					handleSubmit(button);
					return true;
				}
					return false;
			}


		 	
			function impostaMisura(idMisura,idPersona,idVersione,idAttoMisura,dataDefinitivita) {
				document.getElementById("idMisura").value = idMisura;
				document.getElementById("idSoggetto").value = idPersona;
				document.getElementById("idVersione").value = idVersione;
				document.getElementById("idAttoMisura").value = idAttoMisura;
				document.getElementById("dataDefinitivita").value = dataDefinitivita;
								
				// Abilito il tasto "Nuova Scheda" solo se è stato selezinata una misura
				$("#id_div_nuova_scheda").css('visibility','visible');
			}

			function scaricaPDFOmonimo(idNSC) {
				document.getElementById("idNSCDownload").value = idNSC;
				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/atto/scaricaPDFOmonimo.htm");
				$("#id_formConfermaInvia").submit();
				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/atto/redazioneScheda.htm");
				
			}
			
			function scaricaPDFCasellario() {
				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/atto/scaricaPDFCasellario.htm");
				$("#id_formConfermaInvia").submit();
				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/atto/redazioneScheda.htm");
				
			}

			function scaricaPDFSchedaElettorale() {
				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/atto/scaricaPDFSchedaElettorale.htm");
				$("#id_formConfermaInvia").submit();
				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/atto/redazioneScheda.htm");
			}

			function vaiIndietro() {
				$("#id_formConfermaInvia").attr("action", contextPath+"${sessionScope.returnTo}");
				$("#id_formConfermaInvia").submit();
			}

			function showHideSoggetto() {
				visibilitySoggetto = (visibilitySoggetto=='none'?'block':'none');
				$("#altriDatiSoggetto").attr("style", "display:" + visibilitySoggetto );
				if (visibilitySoggetto=='none')   {
					$("#altriDatiSoggettoImg").attr("src", "${pageContext.request.contextPath}/images/arrow_r.png" );
				} else {
					$("#altriDatiSoggettoImg").attr("src", "${pageContext.request.contextPath}/images/arrow_d.png" );
				}    
			}
			var visibilitySoggetto="none";
		 </script>
		 <img src="${pageContext.request.contextPath}/images/arrow_d.png" width="0" height="0"/>
	</tiles:putAttribute>
</tiles:insertDefinition>



