<%@page import="it.giustizia.smp.form.AttoForm"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page import="it.giustizia.smp.form.FlussiPoliziaForm"%>
<%@page import="it.giustizia.smp.utility.Constants"%>
  
<tiles:insertDefinition name="base.definition"> 
	<tiles:putAttribute name="body-content">
		
		<p class="titolo_page">
			<spring:message code="flussi.dettaglio" />
			<c:if test="${flussiForm.descCatAtto==null }">
				<c:out value="${flussiForm.descTipoFlusso}"></c:out>
			</c:if>
			<c:if test="${flussiForm.descCatAtto!=null }">
				<c:out value="${flussiForm.descCatAtto}"></c:out>
			</c:if>
		</p>
		
		<form:form method="post" action="${azione}" commandName="flussiForm" enctype="multipart/form-data">
			
			<c:if test="${(flussiForm.idServizio==17 || flussiForm.idServizio==19) }"><!-- visibile per 17 e 19 -->
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="flussi.ufficio.destinatario" /></td>
					</tr>
					<tr>
						<td width="15%">
							<form:label path="ufficioMittente.descUfficio">
								<spring:message code="flussi.ufficio.tipoufficio" />
							</form:label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.ufficioDestinatario.descUfficio}" ></c:out>
						</td>
						<td width="15%">
							<form:label path="ufficioMittente.descIndirizzoSede">
								<spring:message code="flussi.ufficio.descrsede" />
							</form:label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.ufficioDestinatario.descIndirizzoSede}" ></c:out>
						</td>
					</tr>
				</table>
			</c:if>

			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="flussi.ufficio.mittente" /></td>
				</tr>
				<tr>
					<td width="15%">
						<form:label path="ufficioMittente.descUfficio">
							<spring:message code="flussi.ufficio.tipoufficio" />
						</form:label>
					</td>
					<td width="35%">
						<c:out value="${ufficioMittente.descUfficio}" ></c:out>
					</td>
					<td width="15%">
						<form:label path="ufficioMittente.descIndirizzoSede">
							<spring:message code="flussi.ufficio.descrsede" />
						</form:label>
					</td>
					<td width="35%">
						<c:out value="${ufficioMittente.descIndirizzoSede}" ></c:out>
					</td>
				</tr>
				<c:if test="${(flussiForm.idServizio==17 || flussiForm.idServizio==19)}"><!-- visibile per 17 e 19 -->
					<tr>
						<td width="15%">
							<form:label path="descrNumProposta">
								<spring:message code="flussi.ufficio.numproposta" />
							</form:label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.descrNumProposta}" ></c:out>
						</td>
						<td width="15%">
							<form:label path="dataEmissione">
								<spring:message code="flussi.ufficio.dataEmissione" />
							</form:label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.dataEmissione}" ></c:out>
						</td>
					</tr>
					<tr>
						<td width="15%">
							<form:label path="descrNumAccert">
								<spring:message code="flussi.ufficio.numaccert" />
							</form:label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.descrNumAccert}" ></c:out>
						</td>
						<td width="15%">&nbsp;</td>
						<td width="35%">&nbsp;</td>
					</tr>
					<c:if test="${(flussiForm.idServizio==17 || flussiForm.idServizio==19)}">
						<tr>
							<td width="15%">
								<form:label path="descrNote">
									<spring:message code="flussi.ufficio.descrNote" />
								</form:label>
							</td>
							<td width="85%" colspan="3">
								<c:out value="${flussiForm.descrNote}" ></c:out>
							</td>
						</tr>
					</c:if>
				</c:if>
			</table>
			<!-- dettaglio procedimento per tutti i servizi tranne per 17 e 19 -->
			<logic:notEmpty name="flussiForm" property="procedimento">
			<c:if test="${(flussiForm.idServizio==18 || flussiForm.idServizio==20 || flussiForm.idServizio==21 || flussiForm.idServizio==22 || flussiForm.idServizio==23 || flussiForm.idServizio==24 || flussiForm.idServizio==25 )}">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="procedimento" /></td>
					</tr>
					<tr>
						<td width="15%">
							<form:label path="numProc">
								<spring:message code="procedimento" />
							</form:label>
						</td>
						<td width="35%">
							<font size="4"><b><c:out value="${procedimento.annoNumeroProcedimento}" /></b></font>
						</td>
						<td width="15%">
							<form:label path="procedimento.dataIscrizione">
								<spring:message code="procedimento.dataIscrizione" />
							</form:label>
						</td>
						<td width="35%">
							<fmt:formatDate value="${procedimento.dataApertura}" type="date" pattern="dd/MM/yyyy" />
						</td>
					</tr>
					<c:if test="${procedimento.numeroPadre!=null && procedimento.annoPadre!=null}">
						<tr>
							<td><form:label path="procedimento.annoNumeroPadre">
									<spring:message code="procedimento.appellativoProcedimentoPadre" />
								</form:label></td>
							<td colspan="3">
								<c:out value="${procedimento.annoNumeroPadre}" />
								<c:if test="${procedimento.ufficioPadre!=null}">
									&nbsp;<c:out value="${procedimento.ufficioPadre}" />
								</c:if>
							</td>
						</tr>
					</c:if>
					<c:if test="${procedimento.tipoProcedimento != null && (procedimento.tipoProcedimento.id != 13 && procedimento.tipoProcedimento.id != 14 && procedimento.tipoProcedimento.id != 15 && procedimento.tipoProcedimento.id != 16 && procedimento.tipoProcedimento.id != 17) }">
					<tr>
						<td><form:label path="procedimento.procedente">
								<spring:message code="procedimento.procedente" />
							</form:label></td>
						<td colspan="3"><c:out value="${procedimento.procedente.descrizione}" /></td>
					</tr>
					</c:if>
					<tr>
						<td>
							<form:label path="procedimento.tipoProcedimento">
								<spring:message code="procedimento.tipo" />
							</form:label>
						</td>
						<td>
							<c:out value="${procedimento.tipoProcedimento.descrizione}" />
						</td>
						<td>
							<form:label path="procedimento.statoProcedimento">
								<spring:message code="procedimento.stato" />
							</form:label>
						</td>
						<td>
							<c:out value="${procedimento.statoProcedimento.descrizione}" />
						</td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<c:if test="${procedimento.tipoProcedimento == null || (procedimento.tipoProcedimento.id != 9 && procedimento.tipoProcedimento.id != 13 && procedimento.tipoProcedimento.id != 14 && procedimento.tipoProcedimento.id != 15 && procedimento.tipoProcedimento.id != 16 && procedimento.tipoProcedimento.id != 17) }">				
							<td width="15%"><form:label path="procedimento.tipologiaPrevenzione">
									<spring:message code="procedimento.tipologiaPrevenzione" />
								</form:label></td>
							<td width="85%"><c:if test="${procedimento.antimafia==true}">
									<form:hidden path="procedimento.antimafia" />
									<c:out value="ANTIMAFIA" />
								</c:if> <c:if test="${procedimento.antimafia==false}">
									<c:if test="${procedimento.antiterrorismo==true}">
										<form:hidden path="procedimento.antiterrorismo" />
										<c:out value="ANTITERRORISMO" />
									</c:if>
									<c:if test="${procedimento.antiterrorismo==false}">
										<c:if test="${procedimento.ordinaria==true}">
											<form:hidden path="procedimento.ordinaria" />
											<c:out value="ORDINARIA" />
										</c:if>
									</c:if>
								</c:if></td>
						</c:if>						
					</tr>						
				</table>
			</c:if>
			</logic:notEmpty>
			
			<logic:notEmpty name="flussiForm" property="numProc">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="procedimento" /></td>
					</tr>
					<tr>
						<td width="15%">
							<label>Procedimento</label>
						</td>
						<td width="35%">
							<font size="4"><b><c:out value="${flussiForm.numProc}" /></b></font>
						</td>
						<td width="15%"></td>
						<td width="35%"></td>
					</tr>
				</table>
			</logic:notEmpty>
			
			<c:if test=""></c:if>
			<!-- visualizza daettaglio dell'atto per servizio 18 e 24 -->
			<logic:notEmpty property="atto" name="flussiForm">
			<c:if test="${(flussiForm.idServizio==18 || flussiForm.idServizio==24)}">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4">Atto di riferimento</td>
					</tr>
					<tr>
						<td width="15%">
							<form:label path="atto.tipoAtto">
								<spring:message code="atto.tipo.atto" />
							</form:label>
						</td>
						<td width="35%">
							<c:out value="${atto.tipoAtto.descrizione}" ></c:out>
						</td>
						<td width="15%">
							<form:label path="atto.categoriaAtto">
								<spring:message code="atto.categoria.atto" />
							</form:label>
						</td>
						<td width="35%">
							<c:out value="${atto.categoriaAtto.descrizione}" ></c:out>
						</td>					
					</tr>		
					<tr>
						<td width="15%">
							<form:label path="atto.numeroAtto">
								<spring:message code="atto.numero" />
							</form:label>
						</td>
						<td width="35%">
							<c:out value="${atto.numeroAtto}" ></c:out>
						</td>
						<td width="15%">
							<form:label path="atto.statoAtto">
								<spring:message code="atto.stato.atto" />
							</form:label>
						</td>
						<td width="35%">
							<c:out value="${atto.statoAtto.descrizione}" ></c:out>
						</td>
					</tr>
					<c:if test="${atto.numeroAttoOrigine!=null}">
						<tr>
							<td width="15%">
								<c:if test="${atto.tipoAtto!=null && atto.tipoAtto.id == 21}">
									<form:label path="atto.numeroAttoOrigine"><spring:message code="atto.decreto.cda" /></form:label>
								</c:if>
								<c:if test="${atto.tipoAtto!=null && atto.tipoAtto.id != 21}">
									<form:label path="atto.numeroAttoOrigine"><spring:message code="atto.numero.origine" /></form:label>								
								</c:if>
							</td>
							<td width="35%">
								<c:out value="${atto.numeroAttoOrigine}" ></c:out>
							</td>
							<td width="15%"></td>
							<td width="35%"></td>
						</tr>
					</c:if>
					
					<c:if test="${atto.tipoAtto!=null && atto.tipoAtto.id == 28 }">
						<c:if test="${atto.dataRicezioneAttoOrigine!=null }">
							<tr>
								<td width="15%">
									<form:label path="atto.descrizioneCategoriaAttoOrigine"><spring:message code="atto.istanza.origine" /></form:label>								
								</td>
								<td width="35%">
									<c:out value="${atto.descrizioneCategoriaAttoOrigine}" ></c:out>
								</td>
								<td width="15%">
									<form:label path="atto.dataRicezioneAttoOrigine">ricevuto il&nbsp;</form:label>								
								</td>
								<td width="35%">
									<fmt:formatDate value="${atto.dataRicezioneAttoOrigine}" type="date" pattern="dd/MM/yyyy" />
								</td>
							</tr>
						</c:if>					
					</c:if>
					
					<c:if test="${atto.tipoAtto!=null && atto.tipoAtto.id == 21 }">
						<c:if test="${atto.note!=null}">
							<tr>
								<td width="15%">
									<form:label path="atto.note">
										<spring:message code="atto.numero.sezionale" />
									</form:label>
								</td>
								<td colspan="4">
									<c:out value="${atto.note }"></c:out>
								</td>
							</tr>
						</c:if>					
					</c:if>
					<c:if test="${atto.numeroProcedimentoOrigine!=null}">
						<tr>
							<td width="15%">
								<form:label path="atto.numeroProcedimentoOrigine">
									&nbsp;
								</form:label>
							</td>
							<td width="35%">
								<c:out value="${atto.numeroProcedimentoOrigine}" />
							</td>
							<td width="15%">&nbsp;</td>
							<td width="35%">&nbsp;</td>
						</tr>
					</c:if>
				</table>
			</c:if>
			</logic:notEmpty>
			<!-- dettaglio delle richieste di aggravamento - idservizio 23 -->
			<c:if test="${flussiForm.idServizio == 23 }">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4">Dati richiesta aggravamento</td>
					</tr>
					<tr>
						<td width="15%">
							<label>Numero protocollo aggravamento</label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.descrNumAggr}" ></c:out>
						</td>
						<td width="15%">
							<label>Data emissione</label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.dataEmissioneAggr}" ></c:out>
						</td>					
					</tr>
					<tr>
						<td width="15%">
							<label>Proposta aggravamento</label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.descrPropostaAggr}" ></c:out>
						</td>
						<td width="15%">&nbsp;</td>
						<td width="35%">&nbsp;</td>					
					</tr>
					<tr>
						<td width="15%">
							<label>Note</label>
						</td>
						<td colspan="3">
							<c:out value="${flussiForm.descrNoteAggr}" ></c:out>
						</td>
					</tr>
				</table>
			</c:if>
			<!-- dettaglio delle richieste di revoca/modifica - idservizio 24 -->
			<c:if test="${flussiForm.idServizio == 24 }">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4">Dati parere</td>
					</tr>
					<tr>
						<td width="15%">
							<label>Data emissione</label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.dataEmissioneParere}" ></c:out>
						</td>
						<td width="15%">
							<label>Parere</label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.descrFlgParere}" ></c:out>
						</td>
					</tr>
					<tr>
						<td width="15%">
							<label>Numero parere</label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.descrNumParere}" ></c:out>
						</td>
						<td width="15%">&nbsp;</td>
						<td width="35%">&nbsp;</td>
					</tr>
					<tr>		
						<td width="15%">
							<label>Note</label>
						</td>
						<td colspan="3">
							<c:out value="${flussiForm.descrNoteParere}" ></c:out>
						</td>
					</tr>
				</table>
			</c:if>
			<!-- dettaglio del servizio esito indagine - idservizio 18 -->
			<c:if test="${flussiForm.idServizio == 18 }">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4">Dati esito indagine</td>
					</tr>
					<tr>
						<td width="15%">
							<label>Data emissione</label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.dataEmissioneEsito}" ></c:out>
						</td>
						<td width="15%">
							<label>Numero protocollo</label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.descrEsito}" ></c:out>
						</td>
					</tr>
					<tr>		
						<td width="15%">
							<label>Note</label>
						</td>
						<td colspan="3">
							<c:out value="${flussiForm.descrNoteEsito}" ></c:out>
						</td>
					</tr>
				</table>
			</c:if>
			<!-- dettaglio del servizio termini cessazione - idservizio 25 -->
			<c:if test="${(flussiForm.idServizio==20 || flussiForm.idServizio==21 || flussiForm.idServizio==22 || flussiForm.idServizio==25)}">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4">Dati comunicazione</td>
					</tr>
					<tr>
						<td width="15%">
							<label>Data emissione</label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.dataEmissioneComunic}" ></c:out>
						</td>
						<td width="15%">
							<label>Data evento</label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.dataEventoComunic}" ></c:out>
						</td>
					</tr>
					<tr>		
						<td width="15%">
							<label>Comunicazione</label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.descrTipoComunic}" ></c:out>
						</td>
						<td width="15%">
							<label>Numero protocollo comunicazione</label>
						</td>
						<td width="35%">
							<c:out value="${flussiForm.descrNumCominic}" ></c:out>
						</td>
					</tr>
					<tr>		
						<td width="15%">
							<label>Note</label>
						</td>
						<td colspan="3">
							<c:out value="${flussiForm.descrNoteComunic}" ></c:out>
						</td>
					</tr>
				</table>
			</c:if>
			
			<logic:equal value="S" property="enableRegistra" name="flussiForm">
			 	<br />
			 	<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="atto.inseriscidocumento" /></td>
					</tr>
					<tr>
						 <c:if test="${allegatoObbligatorio == 'S'}">
							<td><form:label for="fileData" path="allegato.fileData" cssClass="required">
								<spring:message code="file" />
							</form:label></td>
						</c:if>
						<c:if test="${allegatoObbligatorio != 'S'}">
							<td><form:label for="fileData" path="allegato.fileData">
								<spring:message code="file" />
							</form:label></td>
						</c:if>				
						<td colspan="3">
							<form:input id="fileData" path="allegato.fileData" type="file" cssStyle="width:500px" />
							<form:errors path="allegato.fileData" class="testoRosso" />
						</td>
					</tr>
					<tr>
						<td class="td_campo" colspan="3">
							<form:checkbox id="id_checkFirmaImmediata" path="allegato.firmaRemota.firmaImmediata" style="cursor:pointer" /> 
								<form:label	path="allegato.firmaRemota.firmaImmediata">
								<spring:message code="documentale.firmaremota.firmaImmediata" />
							</form:label>
						</td>
					</tr>
					<form:hidden path="allegato.documentoFirmato"/>
					<input id="id_buttonGestioneDocumento" type="submit" name="" value="" style="visibility: hidden" class="nowait"></td>
				</table>
				<!-- ID relativi alla gestione della firma remota -->
				<form:hidden id="id_hiddenFirmaRemotaUserName" path="allegato.firmaRemota.userName" />
				<form:hidden id="id_hiddenFirmaRemotaPassword" path="allegato.firmaRemota.password" />
				<form:hidden id="id_hiddenFirmaRemotaPin" path="allegato.firmaRemota.documentalePin" />
				<input type="hidden" id="id_dialogForm_expired" value="false"/>				
				<input type="hidden" id="id_dialogForm_timer"  value="0"/>	
				<div id="id_dialogForm" style="display: none">
					<jsp:include page="/WEB-INF/jsp/common/firmaRemotaPIN.jsp" />
				</div>
				<table class="tb_form">			
					<tr>
						<td><form:label path="" cssClass="required"><spring:message code="atto.dataricezione" /></form:label></td>
						<td colspan="3">
							<form:input path="dataRicezione" id="dataRicezione" maxlength="10" size="12"/>
							<form:errors path="dataRicezione" class="testoRosso" />
						</td>
					</tr>
				</table>
			</logic:equal>
			
			<!-- dettaglio dei soggetti -->
			<logic:notEmpty property="listSoggetto" name="flussiForm">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettoIntestatario" /></td>
					</tr>
				</table>
				<display:table class="tb_lista" uid="direttiInteressati" name="${direttiInteressati}" pagesize="10" requestURI="${azione}">
		            <display:column property="descrizioneSoggettoConVersione" title="Generalità" sortable="false" />
					<%--<display:column property="dataIscrizione" title="Data iscrizione" format="{0,date,dd/MM/yyyy}" sortable="false" />--%>
					<display:column property="misura.descrizioneMisura" title="Misure" sortable="false" />
					<%--<display:column property="misura.cauzione" title="Cauzione" sortable="false" />--%>
					<%--<display:column property="misura.art67" title="Art.67" sortable="false" />--%>
				</display:table>
			</logic:notEmpty>
			
			<!-- dettaglio misure personali solo per il servizio 19 -->
			<logic:notEmpty name="flussiForm" property="listMisurePersonali">
				<br /><br /><br />
			 	<c:if test="${flussiForm.idServizio==19}">
			 		<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale" /></td>
						</tr>
					</table>
			 		<display:table class="tb_lista" uid="misureSoggetto" name="${misureSoggetto}" pagesize="10" requestURI="${azione}">
						<display:column property="soggetto.descrizioneSoggetto" title="Soggetto Interessato" sortable="false" />
						<display:column title="">
							<display:table class="tb_sotto_lista" uid="misurePersonali" name="${misureSoggetto.misurePersonali}" requestURI="${azione}">
								<display:column property="tipoPrevenzione" title="Tipologia prevenzione" sortable="false" />
								<display:column property="normativaPrevenzione" title="Normativa prevenzione" sortable="false" />
								<display:column property="tipoMisura" title="Misura personale" sortable="false" />
								<display:column property="normativaMisura" title="Normativa misura personale" sortable="false" />
								<display:column property="descrMisPers" title="Anno-Mesi-Giorni-Localit&agrave;" sortable="false" />
							</display:table>
						</display:column>
					</display:table>
			 	</c:if>
		 	</logic:notEmpty>
		 	
			<!-- dettaglio misure patrimoniali solo per il servizio 19 -->
			<logic:notEmpty name="flussiForm" property="listMisurePatrimoniali">
			<br /><br /><br />
		 	<c:if test="${flussiForm.idServizio==19}">
		 		<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapatrimoniale" /></td>
					</tr>
				</table>
		 		<display:table class="tb_lista" uid="beneSoggetto" name="${beneSoggetto}" pagesize="10" requestURI="${azione}">
					<display:column property="soggetto.descrizioneSoggetto" title="Soggetto Interessato" sortable="false" />
					<display:column title="">
						<display:table class="tb_sotto_lista" uid="misurePatrimoniali" name="${beneSoggetto.misurePatrimoniali}" requestURI="${azione}">
							<display:column property="descrBene" title="Bene" sortable="false" />
							<display:column property="descrCategoriaBene" title="Categoria" sortable="false" />
							<display:column property="descrSubCatBene" title="Sottocategoria" sortable="false" />
						</display:table>
					</display:column>
				</display:table>
		 	</c:if>
		 	</logic:notEmpty>
		 	
			<br /><br />
			<table class="tb_bottoni" align="center">
				<tr>
					<logic:equal value="S" property="enableRegistra" name="flussiForm">
						<input type="submit" value="Registra" name="Registra" id="Registra" style="visibility: hidden"  >
						<td class="td_tasto"><input type="button" class="nowait" value="Registra" onclick="inserisciAtto('Registra', '${allegato.documentoFirmato}')"></td>
					</logic:equal>
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro"></td>
				</tr>
			</table>
			
		</form:form>
		
		<smp:back buttonId="backButton"></smp:back>
		<script type="text/javascript">
			$(document).ready(function() {
				if($( "#dataRicezione" ).val() != undefined){
					prendiData("#dataRicezione", true);
				}
			});
		
			function confermaRegistra() {
				if(confirm('Stai effettuando la registrazione del flusso. Sei sicuro di voler procedere?')){
					$('#Registra').click();
					return true;
				}
				return false;
			}
		</script>
		<script src="${pageContext.request.contextPath}/js/jquery/ui/jquery.ui.datepicker.js"></script>
		<script src="${pageContext.request.contextPath}/js/jquery/ui/jquery.ui.datepicker-it.js"></script>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
