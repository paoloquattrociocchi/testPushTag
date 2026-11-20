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
		<spring:url var="azione" value="/procedimento/atto/certificatoPenale.htm" />
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			<form:hidden path="idProcedimento" id="idProcedimento"/>
			<form:hidden path="soggetti[0].idSoggetto" id="idSoggetto" />
			<form:hidden path="soggetti[0].idVersione" id="idVersione" />
			<form:hidden path="idProcedimento" id="idProcedimento"/>
			
			
			<input type="hidden" name="isRequireAuthentication" id="isRequireAuthentication" value="${isRequireAuthentication}"/>
			<input type="hidden" name="hasOmonimi" id="hasOmonimi" value="<c:out value="${hasOmonimi}" />"/>
			<input type="hidden" name="idNSC" id="idNSC"/>
			<input type="hidden" name="idNSCDownload" id="idNSCDownload"/>
			<input type="hidden" name="authUsername" id="authUsername"/>
			<input type="hidden" name="authPassword" id="authPassword"/>
			
			
			<logic:notPresent name="isInviata">
				<p class="titolo_page">
					<spring:message code="certificatoPenale.nuovaRichiestaCertificato"/>
				</p>
				<logic:present name="listaSoggetti">
					<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				</logic:present>
			</logic:notPresent >
			<logic:present name="isInviata">
				<p class="titolo_page">
					<spring:message code="certificatoPenale.dettaglioCertificatoPenale"/>
				</p>
			</logic:present>
			<logic:present name="listaSoggetti">
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="6"><spring:message code="certificatoPenale.elencoSoggetti"/></td>
				</tr>
				</table>
				<div style="max-height: 400px; min-height: 100px; overflow-y: scroll; float: left; width: 100%;">
					<display:table class="tb_lista" uid="listaSoggetti" name="${listaSoggetti}">
						<display:column style="width:2%" title="Sel."  >
							<input type="radio" name="radio_soggetti"  onclick="impostaSoggetto('${listaSoggetti.idSoggetto}', '${listaSoggetti.idVersione}');">
						</display:column>
						<display:column title="Diretto interessato" style="width:98%">
							<c:out value="${listaSoggetti.descrizioneSoggettoConVersione}"></c:out>
						</display:column>
					</display:table>
				</div>
				<div style="clear: both;" ></div>
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto" id="id_div_nuova_scheda" ><input type="submit" value="Richiedi Certificato" title="Richiedi Certificato" name="richiediCertificatoDettaglio" onClick="handleSubmit(this)" class="nowait" /></td>
						<td class="td_tasto"><input type="button" id="id_buttonIndietro" value="Indietro" onClick="indietro('/procedimento/atti/nuovo.htm')"></td>
					</tr>
				</table>
			</logic:present>
			<logic:notPresent name="listaSoggetti">
				<!-- DETTAGLIO DELLA SCHEDA - PROCEDIMENTO / ATTO -->
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo"  width="15%"><spring:message code="procedimento" /></td>
						<td class="td_titolo"  width="35%"><c:out value="${procedimento.annoNumeroProcedimento}" /></td>
						<td class="td_titolo" ><spring:message code="procedimento.dataIscrizione" /></td>
						<td class="td_titolo" ><fmt:formatDate value="${procedimento.dataApertura}" type="date" pattern="dd/MM/yyyy" /></td>
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
					<logic:present name="isInviata">
						<tr>
							<td><spring:message code="atto.tipo.atto" /></td>
							<td><c:out value="${attoForm.tipoAtto.descrizione}" /></td>
							<td><spring:message code="atto.categoria.atto" /></td>
							<td><c:out value="${attoForm.categoriaAtto.descrizione}" /></td>
						</tr>		
						<tr>
							<td><spring:message code="atto.numero" /></td>
							<td><c:out value="${attoForm.numeroAtto}" /></td>
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
					</logic:present>
					<logic:notPresent name="isInviata">
						<tr><td colspan="4">&nbsp;</td></tr>
					</logic:notPresent>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo1" /></td>
					</tr>
					
					<tr>
						<td width="15%"><spring:message code="cognome" /></td>
						<td width="32%"><c:out value="${attoForm.soggetti[0].cognome}" /></td>
						<td width="17%"><spring:message code="nome" /></td>
						<td width="32%"><c:out value="${attoForm.soggetti[0].nome}" /></td>
					</tr>
					<tr>
						<td><spring:message code="dataNascita" /></td>
						<td>
							<div>
								<fmt:formatDate value="${attoForm.soggetti[0].dataNascita}" type="date" pattern="dd/MM/yyyy" />
							</div>
						</td>
						<td><spring:message code="dataNascitaPresunta" /></td>
						<td><c:if test="${attoForm.soggetti[0].presunta==true}">
								<input type="checkbox" disabled="disabled" checked="checked" />
							</c:if> <c:if test="${attoForm.soggetti[0].presunta==false || attoForm.soggetti[0].presunta==null}">
								<input type="checkbox" disabled="disabled" />
							</c:if></td>
					</tr>
					<tr>
						<td><spring:message code="stato" /></td>
						<td><c:out value="${attoForm.soggetti[0].statoNascita.descrizione}" /></td>
						<td><spring:message code="comune" /></td>
						<td><c:out value="${attoForm.soggetti[0].descrizioneComuneNascitaItalianoEstero}" /></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo2" /></td>
					</tr>
					<tr>
						<td><spring:message code="nuovosoggettofisico.sesso" /></td>
						<td><c:out value="${attoForm.soggetti[0].descrSesso}" /></td>
						<td><spring:message code="codiceFiscale" /></td>
						<td><c:out value="${attoForm.soggetti[0].codiceFiscale}" /></td>
					</tr>
					<tr>
						<td><spring:message code="nuovosoggettofisico.professione" /></td>
						<td><c:out value="${attoForm.soggetti[0].professione.descrizione}" /></td>
						<td><spring:message code="soggettofisico.cui" /></td>
						<td><c:out value="${attoForm.soggetti[0].cui}" /></td>
					</tr>
					<tr>
						<td><spring:message code="nuovosoggettofisico.condizionegiuridica" /></td>
						<td><c:out value="${attoForm.soggetti[0].condizioneGiuridica.descrizione}" /></td>
						<td><spring:message code="nuovosoggettofisico.istitutodidetenzione" /></td>
						<td><c:out value="${attoForm.soggetti[0].istitutoDiDetenzione.descrizione}" /></td>
					</tr>
	
					<logic:notEmpty name="soggettoForm" property="descIstitutoDetenzioneEstero">
						<tr>
							<td><spring:message code="nuovosoggettofisico.istitutodidetenzioneEstero" /></td>
							<td colspan="3"><c:out value="${soggettoForm.descIstitutoDetenzioneEstero}" /></td>
						</tr>
					</logic:notEmpty> 
				</table>
				<logic:present name="isInviata">
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="certificatoPenale.certificatoPenale" /></td>
						</tr>
				        <tr>
							<td colspan="2" onclick="scaricaPDFCertificatoPenaleNew()"><span class="required" style="cursor:pointer;"><spring:message code="certificatoPenale.pdfCertificatoPenale"/>&nbsp;&nbsp;<img src="${pageContext.request.contextPath}/images/download.gif" style="cursor: pointer; border: none" title="Visualizza documento" ></img></span></td>
				        </tr>
			        	<tr>
					</table>
				</logic:present>
				<logic:present name="listaOmonimi">
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="certificatoPenale.selezionaOmonimi"/></td>
						</tr>
						<tr>
							<td width="100%" colspan="4">
								<div style="max-height: 400px; min-height: 100px; overflow-y: scroll; float: left; width: 100%;">
									<display:table class="tb_lista" uid="listaOmonimi" name="${listaOmonimi}">
										<display:column style="width:2%" title="Sel."  >
											<input type="radio" name="omonimo" onclick="impostaOmonimo(${listaOmonimi.idNSC});">
										</display:column>
										<display:column title="Anagrafica Soggetto" style="width:40%">
											<c:out value="${listaOmonimi.datiSoggetto}"></c:out>
										</display:column>
										<display:column title="Codice Fiscale" style="width:12%">
											<c:out value="${listaOmonimi.codiceFiscale}"></c:out>  
										</display:column>
										<display:column title="Paternità" style="width:18%">
											<c:out value="${listaOmonimi.paternita}"></c:out>  
										</display:column>
										<display:column title="Maternità" style="width:18%">
											<c:out value="${listaOmonimi.maternita}"></c:out>  
										</display:column>
										<display:column title="Tipo" style="width:8%">
											<c:out value="${listaOmonimi.tipo}"></c:out>  
										</display:column>
									</display:table>
								</div>
							</td>
						</tr>
					</table>
				</logic:present>		   
				<br/><br/>
				<table class="tb_bottoni" align="center">
					<tr>
						<logic:notPresent name="isInviata">
							<td class="td_tasto" id="td_invia" >
								<input type="submit" value="Invia Richiesta" title="Invia Richiesta" id="inviaRichiestaCertificatoPenale"  name="inviaRichiestaCertificatoPenale"  onClick="return submitInvioRichista(this)"   class="nowait" />
							</td>
						</logic:notPresent> 
						<!-- 
						<logic:present name="isInviata">
							<logic:notPresent name="isCancellata">
								<td class="td_tasto" ><input type="submit" value="Cancella" title="Cancella" name="cancellaSchedaACasellario"  onClick="handleSubmit(this)"   class="nowait"  /></td>
							</logic:notPresent>
						</logic:present>
						 -->
						<td class="td_tasto">
							<input type="button" id="id_buttonIndietro" value="Indietro" onClick="vaiIndietro()">
						</td>
					</tr>
				</table>
			</logic:notPresent>
			<div id="id_dialogForm" style="display: none">
				<table style="width: 100%" align="center" cellspacing="4"
					cellpadding="0" border="0">
					<tr>
						<td colspan="2">
							<div id="id_divEsito"></div>
						</td>
					</tr>
					<tr>
						<td><form:label path="allegato.firmaRemota.userName" cssClass="required"><spring:message code="documentale.firmaremota.usrname" /></form:label></td>
						<td><input id="id_inputUserName" name="id_inputUserName" type="text" maxlength="40" style="width: 200" /><div id="id_divUserName"><span class="testoRosso" style="font-size: 10"></span></div></td>
					</tr>
					<tr>
						<td><form:label path="allegato.firmaRemota.password"cssClass="required"><spring:message code="documentale.firmaremota.pwd" /></form:label></td>
						<td><input id="id_inputPassword" name="id_inputPassword" type="password" maxlength="40" style="width: 200" />
							<div id="id_divPassword">
								<span class="testoRosso" style="font-size: 10"></span>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</form:form>
		 <script  type="text/javascript">

			function submitInvioRichista(button) {
				if (document.getElementById("isRequireAuthentication").value == 'TRUE') {
					apriDialogAuth("inviaRichiestaCertificatoPenale");
					return false;
				} else {
					handleSubmit(button);
					$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/atto/certificatoPenaleInviaRichiesta.htm");
					$("#id_formConfermaInvia").submit();
					return true;
				}
			}
		 
			function apriDialogAuth(idSubmitButton) {
				width_dialog = "300";
				var titolo = "Login - Richiesta Certificati Casellario";
				$("#id_dialogForm").dialog({
					show : {
						effect : 'slide',
						duration : 300,
						complete : function() {
							$('#id_inputUserName').focus();
						}
					},
					hide : {
						effect : "fade",
						duration : 250
					},
					close : function() {
						resetAllFields();
					},
					height : 230,
					width : width_dialog,
					modal : true,
					resizable : false,
					draggable : true,
					open : function(event, ui) {
						$("span.ui-dialog-title").text(titolo);
						// cambia il colore della barra di intestazione
						$(".ui-widget-header").css("background", "#1c548c");
						// cambia il colore relativo al testo sulla barra di intestazione
						$(".ui-widget-header").css("color", "#FFFFFF");
					},
					buttons : {
						Conferma : function() {
							conferma(idSubmitButton);
						},
						Annulla : function() {
							resetAllFields();
							$(this).dialog("close");
						}
					}
				});
			}

			
			function conferma(idSubmitButton) {
				var flg_a = validate('UserName', 'REQUIRED');
				var flg_b = validate('Password', 'REQUIRED');
				if (flg_a && flg_b) {
					//$('#id_hiddenFirmaRemotaUserName').val('' + $('#id_inputUserName').val());
					//$('#id_hiddenFirmaRemotaPassword').val('' + $('#id_inputPassword').val());
					$('#id_dialogForm').dialog("close");
					document.getElementById("isRequireAuthentication").value = "FALSE";
					document.getElementById("authUsername").value = document.getElementById("id_inputUserName").value ;
					document.getElementById("authPassword").value = document.getElementById("id_inputPassword").value ;
					handleSubmit(this);
					
					$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/atto/certificatoPenaleInviaRichiesta.htm");
					$("#id_formConfermaInvia").submit();
				
				}
			}

			/*
			 * Reimposta il form della firma remota
			 */
			function resetAllFields() {
				resetError('UserName');
				resetError('Password');
				resetField('UserName');
				resetField('Password');
			}

			/*
			 * Valida il campo di cui è specificato l'ID in base al criterio di validazione
			 * specificato (al momento, vale solo 'REQUIRED')
			 */
			function validate(idField, validation) {
				var msg = '';
				if (validation == 'REQUIRED')
					msg = 'Obbligatorio';
				var fieldVal = $('#id_input' + idField).val();
				if (fieldVal == '') {
					$("#id_div" + idField + " span").text(msg);
					return false;
				} else {
					$("#id_div" + idField + " span").text("");
					return true;
				}
			}

			
			function resetError(idField) {
				$("#id_div" + idField + " span").text('');
			}

			
			function resetField(idField) {
				$('#id_input' + idField).val('');
			}
						
		 </script>
		 <script  type="text/javascript">
		 	$(document).ready(function() {
				prendiData("#dataRedazioneScheda",true);
		 	});

			function impostaOmonimo(idNSC) {
				document.getElementById("idNSC").value = idNSC;
				$("#td_invia").css('visibility','visible');
			}
		 	
			function impostaSoggetto(idSoggetto,idVersione) {
				document.getElementById("idSoggetto").value = idSoggetto;
				document.getElementById("idVersione").value = idVersione;
			}

			function scaricaPDFCertificatoPenale() {
				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/atto/scaricaPDFCertificatoPenale.htm");
				$("#id_formConfermaInvia").submit();
				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/atto/certificatoPenale.htm");
			}


			function scaricaPDFCertificatoPenaleNew() {
				window.open( 
						contextPath+"/procedimento/atto/viewPDFCetificatoPenale.htm", 
						"_blank", 
						"toolbar=no, scrollbars=no, resizable=yes, top=100, left=100, width=800, height=500");
			}

			

			function vaiIndietro() {
				$("#id_formConfermaInvia").attr("action", contextPath+"${sessionScope.returnTo}");
				$("#id_formConfermaInvia").submit();
			}

			
			
		 </script>
		 <img src="${pageContext.request.contextPath}/images/arrow_d.png" width="0" height="0"/>
	</tiles:putAttribute>
</tiles:insertDefinition>



