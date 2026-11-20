<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/proposta.htm" />
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			<form:hidden path="codAtto" />
			<form:hidden path="statoAtto.id" />
			<form:hidden path="statoAtto.descrizione" />
			<p class="titolo_page">
				<spring:message code="atto.nuovaproposta" />
			</p>

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
		
			<logic:notEmpty name="atto" property="numeroProcedimentoOrigine" scope="session">
				<table class="tb_form">
					<tr>
					<td class="tb_form td_titolo" >
						<c:out value="${attoForm.numeroProcedimentoOrigine}" />
					</td>
					</tr>
				</table>
			</logic:notEmpty>

			<spring:url var="paginazione" value="/procedimento/atto/proposta.htm?pag=true" />
			<logic:notEmpty name="listaBeniNoTitDir">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="40px" class="td_titolo_label" height="5px" title="visualizza/nasconde l'elenco dei beni senza titoli di diritto"><img id="id_img_listaBeniNoTitDir" src="${pageContext.request.contextPath}/images/right_arrow_2.png" height="38" width="38" /></td>
						<td class="td_titolo_label"><p class="testoRosso">
								<spring:message code="bene.no.titolodiritto.lista" />
							</p></td>
					</tr>
				</table>
				<div id="id_div_listaBeniNoTitDir" style="display: none;">
					<display:table class="tb_lista" uid="bene" name="${listaBeniNoTitDir}" pagesize="10" requestURI="${paginazione}">
						<display:column property="idBene" title="Identificativo" sortable="false" />
						<display:column property="descrizioneBene" title="Descrizione" sortable="false" />
					</display:table>
				</div>
			</logic:notEmpty>

			<logic:notEmpty name="listaBeniNoMisPatr">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="40px" class="td_titolo_label" height="5px" title="visualizza/nasconde l'elenco dei beni senza misure patrimoniale"><img id="id_img_listaBeniNoMisPatr" src="${pageContext.request.contextPath}/images/right_arrow_2.png" height="38" width="38" /></td>
						<td class="td_titolo_label"><p class="testoRosso">
								<spring:message code="bene.no.misuarapatrimoniale.lista" />
							</p></td>
					</tr>
				</table>
				<div id="id_div_listaBeniNoMisPatr" style="display: none;">
					<display:table class="tb_lista" uid="listaBeniNoMisPatr" name="${listaBeniNoMisPatr}" pagesize="10" requestURI="${paginazione}">
						<display:column property="idBene" title="Identificativo" sortable="false" />
						<display:column property="descrizioneBene" title="Descrizione" sortable="false" />
					</display:table>
				</div>
			</logic:notEmpty>
			
			<logic:notEmpty name="listaTerziNoTitDir">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="40px" class="td_titolo_label" height="5px" title="visualizza/nasconde l'elenco dei terzi interessati senza titoli di diritto"><img id="id_img_listaTerziNoTitDir" src="${pageContext.request.contextPath}/images/right_arrow_2.png" height="38" width="38" /></td>
						<td class="td_titolo_label"><p class="testoRosso">
								<spring:message code="soggetto.no.titolididiritto.lista" />
							</p></td>
					</tr>
				</table>
				<div id="id_div_listaTerziNoTitDir" style="display: none;">
					<display:table class="tb_lista" uid="listaTerziNoTitDir" name="${listaTerziNoTitDir}" pagesize="10" requestURI="${paginazione}">
						<display:column property="descrizioneSoggettoConVersione" title="Generalità" sortable="false" />					
					</display:table>
				</div>
			</logic:notEmpty>			

			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettidelprocedimento" /></td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="sogg" name="${soggettiInteressati}" pagesize="10" requestURI="${azione2}">
				<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
					<input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}" onclick="selectID('${sogg.idSoggetto}', '${sogg.idVersione}', '${sogg.tipoSoggetto}', '${sogg.misura.descrizioneMisura}');" />
				</display:column>
				<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" />
				<c:if test="${sogg.misura.descrizioneMisura==null && customMessageList!=null && fn:length(customMessageList)>0}">
					<display:column class="testoRosso" value="MISURE ASSENTI" title="Misure" sortable="false" />
				</c:if>
				<c:if test="${sogg.misura.descrizioneMisura!=null}">
					<display:column property="misura.descrizioneMisura" title="Misure" sortable="false" />
				</c:if>
				<c:if test="${attoForm.attoPerAggravamento==false && sessionScope.tipoUtente=='SMP' && sogg.idFonte==null && customMessageList!=null && fn:length(customMessageList)>0}">
					<display:column class="testoRosso" value="NON ASSOCIATO A FONTE" title="" sortable="false" />
				</c:if>
				<c:if test="${sogg.idFonte!=null}">
					<display:column value="" title="" sortable="false" />
				</c:if>
			</display:table>

			<form:hidden path="identificativoSoggetto" id="idSoggetto" />
			<form:hidden path="versioneSoggetto" id="idVersione" />
			<form:hidden path="tipoSoggetto" id="tipoSoggetto" />
			<form:hidden path="descMisura" id="tipoMisura" />

			<table class="tb_bottoni" align="center">
				<tr>
					<logic:equal name="attoForm" property="attoPerAggravamento" value="true">
						<td class="td_tasto"><input type="submit" value="Misura Personale" name="MisuraPersonaleAggravamento"></td>
					</logic:equal>
					<logic:notEqual name="attoForm" property="attoPerAggravamento" value="true">
						<c:if test="${procedimento.tipoProcedimento.id!='12'}">
							<td class="td_tasto"><input type="submit" value="Misura Personale" name="MisuraPersonale"></td>
						</c:if>
						<td class="td_tasto"><input type="submit" value="Misura Patrimoniale" name="MisuraPatrimoniale"></td>
					</logic:notEqual>
				</tr>
			</table>
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />

			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>

				<logic:equal name="attoForm" property="attoPerAggravamento" value="true">
					<tr>
						<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
							<td><form:label path="note" cssClass="required">
									<spring:message code="richiesta" />
								</form:label></td>
							<td colspan="3"><form:textarea path="note" rows="2" cssStyle="width:98%" /><br> <form:errors path="note" class="testoRosso" /></td>
						</c:if>
						<c:if test="${attoForm.dataRegistrazione!=null || attoForm.statoAtto.id==3}">
							<td><form:label path="note">
									<spring:message code="richiesta" />
								</form:label></td>
								<td><c:out value="${attoForm.note}" /> <form:hidden path="note" /></td>
						</c:if>
					</tr>
				</logic:equal>
				<c:if test="${sessionScope.tipoUtente=='SMP_GIU'}">
					<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
						<td><form:label path="numeroRiferimento">
								<spring:message code="atto.numeroProposta" />
							</form:label></td>
						<td><form:input path="numeroRiferimento" maxlength="20" id="numeroRiferimento" /> <br> <form:errors path="numeroRiferimento" class="testoRosso" /></td>
						<td></td>
						<td></td>
					</c:if>
					<c:if test="${attoForm.dataRegistrazione!=null || attoForm.statoAtto.id==3}">
						<td><form:label path="numeroRiferimento">
								<spring:message code="atto.numeroProposta" />
							</form:label></td>
						<td><c:out value="${attoForm.numeroRiferimento}" /> <form:hidden path="numeroRiferimento" /></td>
						<td></td>
						<td></td>
					</c:if>
				</c:if>
				<tr>
					<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
						<td><form:label path="dataEmissione" cssClass="required">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td><form:input path="dataEmissione" maxlength="10" size="12" id="dataEmissione" />  <form:errors path="dataEmissione" class="testoRosso" /></td>
						<td><form:label path="dataDeposito" cssClass="required">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><form:input path="dataDeposito" maxlength="10" size="12" id="dataDeposito" />  <form:errors path="dataDeposito" class="testoRosso" /></td>
					</c:if>
					<c:if test="${attoForm.dataRegistrazione!=null || attoForm.statoAtto.id==3}">
						<td><form:label path="dataEmissione">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataEmissione" /></td>
						<td><form:label path="dataDeposito">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataDeposito" /></td>
					</c:if>
				</tr>
				<c:choose>
					<c:when test="${ufficiDestinatario2==null}">
						<tr>
						
						<c:if test="${!isAggravamento}">
							<td><form:label path="destinatario.id">
									<spring:message code="atto.destinazione" />
								</form:label></td>
							<td>							
							<c:if test="${attoForm.dataRegistrazione==null}">
							<!-- P.Q. 18/06/2021 MEV 9 Se non sono definiti i destinatari su DB , mostro messaggio -->
								    <c:if test="${fn:length(ufficiDestinatario) == 0}">
								    	<span class="testoRosso">
								    		Non sono previsti uffici di destinazione per la tipologia di prevenzione scelta
								    	</span>
								    </c:if>																			
									<c:if test="${fn:length(ufficiDestinatario) > 0}">
										<form:select path="destinatario.id" size="1" id="listaDestinatari">
											<form:options items="${ufficiDestinatario}" itemValue="id" itemLabel="descrizione" />
										</form:select>
									</c:if>
							</c:if> 							
							<c:if test="${attoForm.dataRegistrazione!=null}">
									<table>
										<c:forEach var="destinat" items="${ufficiListFromFlussiOut}">
											<tr>
												<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
											</tr>
										</c:forEach>
									</table>
								</c:if>
							</td>
							<c:if test="${attoForm.dataRegistrazione!=null}">
								<td><form:label path="dataRegistrazione">
										<spring:message code="atto.dataregistrazione" />
									</form:label>
								</td>
								<td><fmt:formatDate value="${attoForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataRegistrazione" /></td>
							</c:if>
							<c:if test="${attoForm.dataRegistrazione==null}">
								<td></td>
								<td></td>
							</c:if>
						</c:if>	
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
					
							<td><form:label path="destinatario.id">
									<spring:message code="atto.destinazione" />
								</form:label></td>
							<td><c:if test="${attoForm.dataRegistrazione==null}">
							
							<!-- P.Q. 18/06/2021 MEV 9 Se non sono definiti i destinatari su DB , mostro messaggio -->
								    <c:if test="${fn:length(ufficiDestinatario) == 0}">
								    	<span class="testoRosso">
								    		Non sono previsti uffici di destinazione per la tipologia di prevenzione scelta
								    	</span>
								    </c:if>												
								    <c:if test="${fn:length(ufficiDestinatario) > 0}">		
										<form:select path="destinatario.id" size="1" id="listaDestinatari">
											<form:options items="${ufficiDestinatario}" itemValue="id" itemLabel="descrizione" />
										</form:select>
									</c:if>
								</c:if> <c:if test="${attoForm.dataRegistrazione!=null}">
									<table>
										<c:forEach var="destinat" items="${ufficiListFromFlussiOut}">
											<tr>
												<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
											</tr>
										</c:forEach>
									</table>
								</c:if></td>
							<c:if test="${attoForm.dataRegistrazione!=null}">
								<td><form:label path="dataRegistrazione">
										<spring:message code="atto.dataregistrazione" />
									</form:label></td>
								<td><fmt:formatDate value="${attoForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataRegistrazione" /></td>
							</c:if>
							<c:if test="${attoForm.dataRegistrazione==null}">
								<td></td>
								<td></td>
							</c:if>
						</tr>
						<c:if test="${attoForm.attoPerAggravamento==false}">
							<tr>
								<td><c:if test="${attoForm.dataRegistrazione==null}">
										<form:label path="destinatario2.id">
											<spring:message code="atto.altraDestinazione" />
										</form:label>
									</c:if></td>
								<td><c:if test="${attoForm.dataRegistrazione==null}">
										<form:select path="destinatario2.id" size="1">
											<form:option value="-1" label="------Seleziona Altro Destinatario-------" />
											<form:options items="${ufficiDestinatario2}" itemValue="id" itemLabel="descrizione" />
										</form:select>
									</c:if></td>
								<td></td>
								<td></td>
							</tr>
						</c:if>
					</c:otherwise>
				</c:choose>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<c:if test="${attoForm.dataRegistrazione==null}">
						<c:if test="${attoForm.statoAtto.id!=3}">
						
							<td class="td_tasto"><input type="button" value="Salva" onclick="inserisciAtto('Conferma', '${attoForm.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" /></td> 
							<input	id="Conferma" type="submit" name="Conferma" value="" style="display: none" />
						
						</c:if>
						
						<!-- P.Q. 18/06/2021 MEV 9 Se ci sono piu' destinatari mostro il popup con il messaggio di avviso del destinatario -->						
						<c:if test="${fn:length(ufficiDestinatario) > 1}">
							<td class="td_tasto"><input type="button" ${ fn:length(ufficiDestinatario) == 0 ? 'disabled="disabled"' : ''} value="Conferma e Invia" onclick="return confermaInvia()" title="Invia proposta" class="nowait" /></td> 
							<input	id="Invia" type="submit" name="Invia" value="" style="display: none" />															
						</c:if>

						<c:if test="${fn:length(ufficiDestinatario) <= 1}">
						<!--  P.Q. 31/12/2021 20211227015-->
							<c:if test="${isAggravamento}"> <!-- per aggravamento e' abilitato anche senza  ufficiDestinatario -->
								<td class="td_tasto"><input type="button" value="Conferma e Invia" onclick="inserisciAtto('Invia', '${attoForm.allegato.documentoFirmato}')" title="Invia proposta" class="nowait" /></td>						
							</c:if>
						<!--  P.Q. 31/12/2021 20211227015-->							
							<c:if test="${!isAggravamento}">
								<td class="td_tasto"><input type="button" ${ fn:length(ufficiDestinatario) == 0 ? 'disabled="disabled"' : ''} value="Conferma e Invia" onclick="inserisciAtto('Invia', '${attoForm.allegato.documentoFirmato}')" title="Invia proposta" class="nowait" /></td>							
							</c:if>													
							<input	id="Invia" type="submit" name="Invia" value="" style="display: none" />						
						</c:if>
						<!-- P.Q. 18/06/2021 MEV 9 Se ci sono piu' destinatari mostro il popup con il messaggio di avviso del destinatario -->
										
						
					</c:if>
					<smp:notificheButtons formId="id_formConfermaInvia" codAtto="${attoForm.codAtto}"></smp:notificheButtons>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
			<br />
			<br />
			 <form:hidden path="idProcedimentoCda" />
			 <form:hidden path="idUfficio" id="idUfficio" />
		</form:form>
		<smp:back buttonId="backButton"></smp:back>

		<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
			<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataRicezione",true);
					prendiData("#dataEmissione",true);
					prendiData("#dataDeposito",true);
				});
			</script>
		</c:if>
		<script>
			function selectID(idSoggetto, idVersione, tipoSoggetto, tipoMisura) {
				document.getElementById("idSoggetto").value = idSoggetto;
				document.getElementById("idVersione").value = idVersione;
				document.getElementById("tipoSoggetto").value = tipoSoggetto;
				document.getElementById("tipoMisura").value = tipoMisura;
			}
		</script>

		<script type="text/javascript">
			$(document).ready(function() {
				$("#id_img_listaBeniNoTitDir").rotate({
					bind : {
						mouseover : function() {
							$(this).css("cursor", "pointer");
						},
						click : function() {

							if ($("#id_div_listaBeniNoTitDir").is(":visible")) {
								$(this).rotate({
									animateTo : 0
								});
								divScomparsa('id_div_listaBeniNoTitDir');
							} else {
								$(this).rotate({
									animateTo : 90
								});
								divScomparsa('id_div_listaBeniNoTitDir');
							}
						}

					}
				});//fine rotate
				$("#id_img_listaBeniNoMisPatr").rotate({
					bind : {
						mouseover : function() {
							$(this).css("cursor", "pointer");
						},
						click : function() {

							if ($("#id_div_listaBeniNoMisPatr").is(":visible")) {
								$(this).rotate({
									animateTo : 0
								});
								divScomparsa('id_div_listaBeniNoMisPatr');
							} else {
								$(this).rotate({
									animateTo : 90
								});
								divScomparsa('id_div_listaBeniNoMisPatr');
							}
						}

					}
				});//fine rotate
				$("#id_img_listaTerziNoTitDir").rotate({
					bind : {
						mouseover : function() {
							$(this).css("cursor", "pointer");
						},
						click : function() {

							if ($("#id_div_listaTerziNoTitDir").is(":visible")) {
								$(this).rotate({
									animateTo : 0
								});
								divScomparsa('id_div_listaTerziNoTitDir');
							} else {
								$(this).rotate({
									animateTo : 90
								});
								divScomparsa('id_div_listaTerziNoTitDir');
							}
						}

					}
				});//fine rotate				


				//disabilitaCampiProposta('${disabledViewProc}', '${procedimento.statoProcedimento.id }')
			});

		</script>
		
	<script type="text/javascript">
	function confermaInvia() {
	    var destinatarioSelezionato = document.getElementById("listaDestinatari");
	    var valoreDestinatarioSelezionato = destinatarioSelezionato.options[destinatarioSelezionato.selectedIndex].text;		
		if (confirm("La proposta sarà inviata all'ufficio di destinazione:\n " + valoreDestinatarioSelezionato + "\n\n Si è sicuri di voler procedere con l'invio ?")) {			
			inserisciAtto('Invia', '${attoForm.allegato.documentoFirmato}');		
			return true;
		}
		return false;
	}
	</script>		
		
	</tiles:putAttribute>
</tiles:insertDefinition>