<%@page import="java.util.List"%>
<%@page import="it.giustizia.smp.utility.Constants"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
<%
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>

<script type="text/javascript">
	var arrayIdBene = new Array();
	var i = -1;

	function messaggio(form){
		 var data = new Date();
		 var anno = data.getFullYear();
	     var campoDataDeposito = form.dataDeposito.value;
	     if(campoDataDeposito!=null && campoDataDeposito.length>0){

	 			var dataDeposito = campoDataDeposito.substring(6);
			    if(dataDeposito==anno){
					return true;
				}else{
					if (confirm("La data di deposito selezionata è relativa ad anni precedenti. Confermando l'operazione la numerazione dell'atto verrà determinata nell'anno di deposito e non sarà possibile rettificare.")){
						return true;
						}else{
							form.dataDeposito.focus();
							return false;
							}
				}
				return true;
	    }else{
	    	form.dataDeposito.focus();
	    	alert('La data di deposito deve essere valorizzata');
		     return false;
			}
	}
</script>

<c:if test="${fn:length(soggetti) == 1}">
	<body onload="checkSeUnico(${attoForm.statoAtto.id})">
</c:if>

<c:if test="${fn:length(soggetti) > 1}">
	<body onload="disabilitaDefinitivita(${attoForm.statoAtto.id})">
</c:if>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/dettaglioAtto.htm" />
		<spring:url var="azioneAtto" value="/procedimento/atto/copertinaAtto.htm" />
		
		<spring:url var="azioneDecisorio" value="/procedimento/atto/decisorioIIGrado.htm" />
		<p class="titolo_page">
		<c:if test="${attoForm.categoriaAtto.id==373}">
		<spring:message code="atto.decreto.provvIstanza.titolo" />		
        </c:if>
        <c:if test="${attoForm.categoriaAtto.id!=373}">
			<spring:message code="atto.decreto.decisorioIIGrado.titolo" />
		</c:if>	
		</p>
		<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
		<br />
		<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
		<br />
		<spring:url var="azioneSoggetto" value="/procedimento/atto/visualizzaDirettiInteressatiIIGrado.htm" />
		<form:form id="id_formSoggetti" method="post" action="${azioneSoggetto}" commandName="soggettoForm">
			<logic:present name="soggetti">
				<display:table class="tb_lista" name="${soggetti}" uid="sogg" pagesize="10" requestURI="${azioneAtto}">
					<display:column style="text-align: center; width: 5%">
						<input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}" onclick="selectID('${sogg.idSoggetto}', '${sogg.idVersione}', '${sogg.tipoSoggetto}', '${sogg.misura.descrizioneMisura}');" />
					</display:column>
					<display:column property="descrizioneSoggetto" title="Generalità" sortable="false" />
					<display:column property="dataIscrizione" title="Data Iscrizione" format="{0,date,dd/MM/yyyy}" sortable="false" />
					<c:if test="${sogg.misura.descrizioneMisura==null && customMessageList!=null && fn:length(customMessageList)>0}">
						<display:column class="testoRosso" value="MISURE ASSENTI" title="Misure" sortable="false" />
					</c:if>
					<c:if test="${sogg.misura.descrizioneMisura!=null}">
						<display:column property="misura.descrizioneMisura" title="Misure" sortable="false" />
					</c:if>
				</display:table>
				<form:hidden path="idSoggetto" id="idSoggetto" />
				<form:hidden path="idVersione" id="idVersione" />
				<form:hidden path="tipoSoggetto" id="tipoSoggetto" />
				<form:hidden path="tipoInteresse.id" />
				<form:hidden path="misura.descrizioneMisura" id="tipoMisura" />
				<br />
			</logic:present>
			
			<c:if test="${(procedimento.tipoProcedimento.id == 17 || procedimento.tipoProcedimento.id == 19) && listaIntestatari!=null}"> 
				<display:table id="row" name="${listaIntestatari}" class="tb_lista" uid="amm" >
					<display:column property="descrizioneAmministratore" title="Generalità" sortable="false"/>
				</display:table>	
			</c:if>		
						
			<br />
			<table class="tb_bottoni" align="center">
			 	 <tr>
					<logic:present name="soggetti">
						<!--  <td class="td_tasto"><input type="submit" value="Dettaglio" name="DettaglioSoggettoDecisorio"></td>-->
						<c:if test="${procedimento.tipoProcedimento.id=='1' || procedimento.tipoProcedimento.id=='4' || procedimento.tipoProcedimento.id=='9' || procedimento.tipoProcedimento.id=='8' 
						              || procedimento.tipoProcedimento.id=='14'  || procedimento.tipoProcedimento.id=='15'  || procedimento.tipoProcedimento.id=='16'}">
							<!-- 							il TIPO del procedimento è PERSONALE oppure PERSONALE E PATRIMONIALE -->
							<c:if test="${procedimento.tipoProcedimento.id != 15 && procedimento.tipoProcedimento.id != 16}"> 
							        <td class="td_tasto"><input type="submit" value="Misura Personale" name="MisuraPersonale"></td>
							   </c:if>
					          	<c:if test="${procedimento.tipoProcedimento.id == 16}"> 
							        <td class="td_tasto"><input type="submit" value="Decisione su Permesso" name="MisuraPersonale"></td>
							   </c:if>
							   <c:if test="${procedimento.tipoProcedimento.id == 15}"> 
							        <td class="td_tasto"><input type="submit" value="Decisione su Cauzione" name="MisuraPersonale"></td>
							   </c:if>	
						</c:if>
					</logic:present>
					<logic:notEqual name="<%=Constants.ID_TIPO_PROCEDIMENTO%>" value="<%=Constants.TIPOPROC_AGGRAVAMENTO_PERSONALE%>">
						<c:if test="${procedimento.tipoProcedimento.id=='1' || procedimento.tipoProcedimento.id=='2' || procedimento.tipoProcedimento.id=='13'}">
							<!-- 							Il TIPO del procedimento è PATRIMONIALE oppure PERSONALE E PATRIMONIALE -->
							<td class="td_tasto"><input type="submit" value="Decisione sui beni" name="DecisioneBeni"></td>
						</c:if>
						<c:if test="${procedimento.tipoProcedimento.id=='17' || procedimento.tipoProcedimento.id=='19'}">
							<td class="td_tasto"><input type="submit" value="Decisione" name="DecisioneBeni"></td>
						</c:if>		
						<c:if test="${procedimento.tipoProcedimento.id=='17'}">
							 <c:if test="${sessionScope.atto.statoAtto.id==3}">
								
										<td class="td_tasto">
											<input type="submit" value="Impugnazione" name="SubmitImpugnazione">
										</td>
								
							</c:if>
						</c:if>
					</logic:notEqual>
					<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=17}" >
						<td class="td_tasto"><input type="submit" value="Definitività" name="Definitivita"></td>
					</c:if>
					<c:if test="${profilo.idTipoUfficio==6}">
						<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=17}" >
							<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=12}" >
								<td class="td_tasto"><input type="submit" value="Revoca Definitività" name="RevocaDefinitivita"></td>
							</c:if>
						</c:if>
					</c:if>

						<c:if test="${attoForm.pulsanteAnnullaConRinvio =='S'}">
						  <td class="td_tasto">
					        	<input type="submit" value="Rinvio" name="Rinvio">
					      </td>  	
					   </c:if>
					
					
					<c:if test="${profilo.idTipoUfficio==19 || profilo.idTipoUfficio==6}">
                    <c:if test="${profilo.ruolo!='MAGISTRATO' && attoForm.numeAnnoAtto != null && attoForm.numeAtto != null }">
						<c:if test="${procedimento.tipoProcedimento.id=='1' || procedimento.tipoProcedimento.id=='2'}">
	                    <td class="td_tasto">   
	                       <input type="button" value="Conservatoria"  name="Conservatoria" onClick="indietro('/conservatorie/predispComunicazioni.htm')">
	                    </td>
				        </c:if>
					</c:if>
					</c:if>
					<!-- Tastp Spese processuali Marzo 2020 mev  -->
				   <logic:present name="soggetti">
						<!-- il tasto Condanna Spese sarà visibile per il TIPO del procedimento è PERSONALE, PATRIMONIALE oppure PERSONALE E PATRIMONIALE -->
						<c:if test="${procedimento.tipoProcedimento.id=='1' || procedimento.tipoProcedimento.id=='2' || procedimento.tipoProcedimento.id=='4' 
									|| (procedimento.tipoProcedimento.id=='13' && (attoForm.categoriaAtto.id=='22' || attoForm.categoriaAtto.id=='359'))
									||  (procedimento.tipoProcedimento.id=='14' && (attoForm.categoriaAtto.id=='22' || attoForm.categoriaAtto.id=='359')) }">
							 <td class="td_tasto">
								<input type="submit" value="Condanna Spese" name="CondannaSpese" onClick="indietro('/procedimento/atto/visualizzaDirettiInteressati.htm')">
				 			 </td>
						</c:if>
					</logic:present>
					
			  	</tr>
			</table>			
		</form:form>
		<logic:notEmpty name="listaBeniNoTitDir">
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="40px" class="td_titolo_label" height="5px" title="visualizza/nasconde l'elenco dei beni senza titoli di diritto"><img id="id_img_listaBeniNoTitDir" src="${pageContext.request.contextPath}/images/right_arrow_2.png" height="38"
						width="38" /></td>
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
					<td width="40px" class="td_titolo_label" height="5px" title="visualizza/nasconde l'elenco dei beni senza misure patrimoniale"><img id="id_img_listaBeniNoMisPatr" src="${pageContext.request.contextPath}/images/right_arrow_2.png" height="38"
						width="38" /></td>
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
		<logic:present name="soggettiSenzaDecisione">
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="40px" class="td_titolo_label" height="5px" title="apre/chiude l'elenco dei soggetti che hanno delle misure senza decisione"><img id="id_img_soggettiSenzaDecisione" src="${pageContext.request.contextPath}/images/right_arrow_2.png"
						height="38" width="38" /></td>
					<td class="td_titolo_label"><p class="testoRosso">
							<spring:message code="atto.decreto.decisorioIGrado.soggSenzaDecisioni" />
						</p></td>
				</tr>
			</table>
			<div id="id_div_soggettiSenzaDecisione" style="display: none;">
				<display:table class="tb_lista" uid="soggettiSenzaDecisione" name="${soggettiSenzaDecisione}" pagesize="10" requestURI="${azione}">
					<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" style="width: 50%" />
				</display:table>
			</div>
		</logic:present>
		<logic:present name="beniSenzaDecisione">
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="40px" class="td_titolo_label" height="5px" title="apre/chiude l'elenco dei beni che sono senza decisione"><img id="id_img_beniSenzaDecisione" src="${pageContext.request.contextPath}/images/right_arrow_2.png" height="38"
						width="38" /></td>
					<td class="td_titolo_label"><p class="testoRosso">
							<spring:message code="atto.decreto.decisorioIGrado.beniSenzaDecisioni" />
						</p></td>
				</tr>
			</table>
			<div id="id_div_beniSenzaDecisione" style="display: none;">
				<display:table class="tb_lista" uid="beniSenzaDecisione" name="${beniSenzaDecisione}" pagesize="10" requestURI="${azione}">
					<display:column property="idBene" title="ID" sortable="true" style="width: 50%" />
					<display:column property="descrizioneBene" title="Descrizione" sortable="true" style="width: 50%" />
				</display:table>
			</div>
		</logic:present>
		<form:form id="id_formConfermaInvia" method="post" action="${azioneDecisorio}" commandName="attoForm" enctype="multipart/form-data">
			<br />
			<br />
			<form:hidden path="codAtto" />
			<form:hidden path="warning" />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>

				<c:if test="${attoForm.statoAtto.id!=3}">
					<tr>
						<td>
							<form:label path="dataEmissione" cssClass="required">
								<spring:message code="atto.dataemissione" />
							</form:label>
						</td>
						<td>
							<form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" />  
							<form:errors path="dataEmissione" class="testoRosso" />
						</td>
						<%-- Specifiche Intervento-006-SpeseProces-NumAtto: data deposito NON modificabile se ho generato
						NUMERO ATTO poichè ANNO NUMERO ATTO è l'anno della data deposito --%>
						<c:if test="${attoForm.numeAnnoAtto==null || attoForm.numeAtto==null}">
							<td>
								<form:label path="dataDeposito" cssClass="required">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
							<td>
								<form:input path="dataDeposito" maxlength="10" id="id_dataDeposito" /> <form:errors path="dataDeposito" class="testoRosso" />
							</td>
						</c:if>
						<c:if test="${attoForm.numeAnnoAtto!=null && attoForm.numeAtto!=null}">
							<td>
								<form:label path="dataDeposito">
									<spring:message code="atto.datadeposito" />
								</form:label>
							</td>
							<td><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /></td>
						</c:if>
					</tr>
					<tr style="display:none">
						<td><form:label path="destinatario.id">
								<spring:message code="atto.destinazione" />
							</form:label></td>
						<td><c:if test="${attoForm.dataRegistrazione==null}">
								<table>
									<form:select path="destinatario.id" size="1">
										<form:options items="${ufficiList}" itemValue="id" itemLabel="descrizione" />
									</form:select>
									<%--								<c:forEach var="destinat" items="${ufficiList}">
										<tr>
											<td style="border: none"><c:out
													value="${destinat.descrizione}" /></td>
										</tr>
									</c:forEach> --%>
								</table>
							</c:if></td>
						<td><form:label path="dataRegistrazione">
								<spring:message code="atto.dataregistrazione" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataRegistrazione" /></td>
					</tr>
				</c:if>
				<c:if test="${attoForm.statoAtto.id==3}">
					<tr>
						<td><form:label path="dataEmissione">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataEmissione" /></td>
						<td><form:label path="dataDeposito">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy"  /></td>
					</tr>
					<tr>
						<td><form:label path="destinatario.id">
								<spring:message code="atto.destinazione" />
							</form:label></td>
						<td>
							<table>
								<c:forEach var="destinat" items="${ufficiListFromFlussiOut}">
									<tr>
										<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
									</tr>
								</c:forEach>
							</table>
						</td>
						<td><form:label path="dataRegistrazione">
								<spring:message code="atto.dataregistrazione" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>
				</c:if>
			</table>
			<br />
			<br />
			
			<table class="tb_bottoni" align="center">
				<tr>
				 <c:if test="${profilo.ruolo!='MAGISTRATO'}">
					<c:if test="${attoForm.dataRegistrazione==null }">
						<%-- Specifiche Intervento-006-SpeseProces-NumAtto --%>
						<%-- escludo le tipologie di procedimento su istanza --%>
						<c:if test="${procedimento.tipoProcedimento.id != 16
									&& procedimento.tipoProcedimento.id != 17 
									&& (procedimento.tipoProcedimento.id!='13' || (attoForm.categoriaAtto.id=='22' || attoForm.categoriaAtto.id=='359'))
									&& (procedimento.tipoProcedimento.id!='14' || (attoForm.categoriaAtto.id=='22' || attoForm.categoriaAtto.id=='359'))
									&& (procedimento.tipoProcedimento.id!='15' || (attoForm.categoriaAtto.id=='22' || attoForm.categoriaAtto.id=='359'))
									&& (procedimento.tipoProcedimento.id!='8' || (attoForm.categoriaAtto.id=='22' || attoForm.categoriaAtto.id=='359')) }">
							<jsp:include page="/WEB-INF/jsp/procedimento/atto/includeGeneraNumAtto.jsp" />
						</c:if>
						<td class="td_tasto"><input type="submit" value="Salva" name="Salva"></td>
						<c:if test="${customMessageList!=null && fn:length(customMessageList)>0}">
							<td class="td_tasto"><input type="submit" value="Conferma e Invia" name="ConfermaInvia" disabled="disabled"></td>
						</c:if>
						<c:if test="${customMessageList==null || fn:length(customMessageList)==0}">
							<%-- Specifiche Intervento-006-SpeseProces-NumAtto: aggiunto messagio di conferma se numeAnnoAtto is null --%>
							<td class="td_tasto">
								<c:if test="${attoForm.numeAnnoAtto==null || attoForm.numeAtto==null}">
									<input type="button" value="Conferma e Invia" onclick="return confermaInvio('ConfermaInvia')" title="Conferma inserimento atto" class="nowait" />
								</c:if>
								<c:if test="${attoForm.numeAnnoAtto!=null && attoForm.numeAtto!=null}">
									<input type="button" value="Conferma e Invia" onclick="inserisci();" title="Conferma inserimento atto" class="nowait" />
								</c:if>
						  	</td>
						  	
							<input id="ConfermaInvia" type="submit" name="ConfermaInvia" value="" style="display: none" />
							<!-- <td class="td_tasto"><input type="submit" value="Conferma e Invia" name="ConfermaInvia"></td> -->
						</c:if>
					</c:if>
					</c:if>
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
	</tiles:putAttribute>
</tiles:insertDefinition>

<script type="text/javascript">

	function inserisci() {
		var check = checkDate();
		if(check) {
			$('#ConfermaInvia').click();
		}
	}

	function checkSeUnico(statoAtto) {
		document.getElementById("radioID_1").checked = true;
		selectID('${sogg.idSoggetto}', '${sogg.idVersione}', '${sogg.tipoSoggetto}');
		if(statoAtto!=3){
			if(document.getElementsByName("Definitivita")[0])document.getElementsByName("Definitivita")[0].disabled=true;
			if(document.getElementsByName("Conservatoria")[0])document.getElementsByName("Conservatoria")[0].disabled=true;

			
		}
			
	}

	function selectID(idSoggetto, idVersione, tipoSoggetto, tipoMisura) {
		document.getElementById("idSoggetto").value = idSoggetto;
		document.getElementById("idVersione").value = idVersione;
		document.getElementById("tipoSoggetto").value = tipoSoggetto;
		document.getElementById("tipoMisura").value = tipoMisura;
	}

	function disabilitaDefinitivita(statoAtto){
		
		if(statoAtto!=3){
			document.getElementsByName("Definitivita")[0].disabled=true;
			
			document.getElementsByName("Conservatoria")[0].disabled=true;
			}
		}	

	$(document).ready(function() {

		$("#id_img_soggettiSenzaDecisione").rotate({
			bind : {
				mouseover : function() {
					$(this).css("cursor", "pointer");
				},
				click : function() {

					if ($("#id_div_soggettiSenzaDecisione").is(":visible")) {
						$(this).rotate({
							animateTo : 0
						});
						divScomparsa('id_div_soggettiSenzaDecisione');
					} else {
						$(this).rotate({
							animateTo : 90
						});
						divScomparsa('id_div_soggettiSenzaDecisione');
					}
				}

			}
		});
	});

	$(document).ready(function() {

		$("#id_img_beniSenzaDecisione").rotate({
			bind : {
				mouseover : function() {
					$(this).css("cursor", "pointer");
				},
				click : function() {

					if ($("#id_div_beniSenzaDecisione").is(":visible")) {
						$(this).rotate({
							animateTo : 0
						});
						divScomparsa('id_div_beniSenzaDecisione');
					} else {
						$(this).rotate({
							animateTo : 90
						});
						divScomparsa('id_div_beniSenzaDecisione');
					}
				}

			}
		});
	});

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
		});
	});

	$(document).ready(function() {
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
		});
	});

	$(document).ready(function() {
		prendiData("#id_dataDeposito",true);
		prendiData("#id_dataEmissione",true);
	});

	<%-- Specifiche Intervento-006-SpeseProces-NumAtto --%>
	<c:if test="${attoForm.numeAnnoAtto==null || attoForm.numeAtto==null}">
	function confermaInvio(idSubmitButton) {
		var check = checkDate();
		<c:if test="${procedimento.tipoProcedimento.id != 16
			&& procedimento.tipoProcedimento.id != 17
			&& (procedimento.tipoProcedimento.id!=13 || (attoForm.categoriaAtto.id==22 || attoForm.categoriaAtto.id==359))
			&& (procedimento.tipoProcedimento.id!=14 || (attoForm.categoriaAtto.id==22 || attoForm.categoriaAtto.id==359))
			&& (procedimento.tipoProcedimento.id!=15 || (attoForm.categoriaAtto.id==22 || attoForm.categoriaAtto.id==359))
			&& (procedimento.tipoProcedimento.id!=8 || (attoForm.categoriaAtto.id==22 || attoForm.categoriaAtto.id==359)) }">
			if (check && confirm("Si è scelto di voler procedere senza aver generato precedentemente il numero dell'atto e quindi una volta confermata l'operazione non sarà più possibile modificare il file allegato. Si è sicuri di voler continuare?")) {
				$('#' + idSubmitButton).click();
				return true;
			}		
			return false;
		</c:if>
		
		$('#' + idSubmitButton).click();
		return true;
<%-- 		
		<c:if test="${procedimento.tipoProcedimento.id == 16
			|| procedimento.tipoProcedimento.id == 17 
			|| (procedimento.tipoProcedimento.id==13 && (attoForm.categoriaAtto.id==22 || attoForm.categoriaAtto.id==359))
			|| (procedimento.tipoProcedimento.id==14 && (attoForm.categoriaAtto.id==22 || attoForm.categoriaAtto.id==359))
			|| (procedimento.tipoProcedimento.id==15 && (attoForm.categoriaAtto.id==22 || attoForm.categoriaAtto.id==359))
			|| (procedimento.tipoProcedimento.id==8 && (attoForm.categoriaAtto.id==22 || attoForm.categoriaAtto.id==359))}">		
			$('#' + idSubmitButton).click();
			return true;
		</c:if>
--%>		
	}
	</c:if>
</script>