<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>


<c:set var="ISTANZA_LIQUIDAZIONE_AMM" value="<%=Constants.ISTANZA_LIQUIDAZIONE_AMM%>"/>
<c:set var="ISTANZA_TERZO_INTERVENIENTE" value="<%=Constants.ISTANZA_TERZO_INTERVENIENTE%>"/>
<c:set var="ISTANZA_CORREZIONE_ERRORI_MATERIALI" value="<%=Constants.ISTANZA_CORREZIONE_ERRORI_MATERIALI%>"/>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/istanzaModificaPersonali.htm" />
		
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
		    <form:hidden path="idProcedimento" id="idProcedimento"/>
			<form:hidden path="idUfficio" id="idUfficio"/>
			<form:hidden path="codAtto" id="codAtto"/>
		    <form:hidden path="decisorioIGrado.id" id="decisorioIGrado.id"/>
			<form:hidden path="categoriaAtto.id" id="categoriaAtto.id"/>
			<form:hidden path="statoAtto.id" id="statoAtto.id"/>

			<input type="hidden" id="id_selectAll_impugnazione.idsDirettiInteressati" value="true"/>
			<input type="hidden" id="id_selectAll_impugnazione.idsSoggettiTitolari" value="true"/>
			<input type="hidden" id="id_selectAll_impugnazione.idsSoggettiDifensori" value="true"/>	
			
			
			<c:set var="PATRIMONIALE_ID" value="<%=Constants.TipoMisura.PATRIMONIALE_ID%>"/>
					<c:set var="PERSONALE_ID" value="<%=Constants.TipoMisura.PERSONALE_ID%>"/>
					
					<c:set var="PATRIMONIALE_DESCR" value="<%=Constants.TipoMisura.PATRIMONIALE_DESCR%>"/>
					<c:set var="PERSONALE_DESCR" value="<%=Constants.TipoMisura.PERSONALE_DESCR%>"/>
					
			<p class="titolo_page">
				<spring:message code="atto.registrazioneistanza" />
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

			<c:if test="${attoForm.categoriaAtto.id == ISTANZA_TERZO_INTERVENIENTE}">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.interveniente" /></td>
					</tr>
				</table>

				<display:table class="tb_lista" name="${intervenienti}" uid="sogg" requestURI="${azione}" >
					<display:column property="descrizioneSoggettoConVersione" title="Generalità" sortable="false" />
				</display:table>
				<br />
			</c:if>
			
			<c:if test="${attoForm.categoriaAtto.id != ISTANZA_LIQUIDAZIONE_AMM && attoForm.categoriaAtto.id != ISTANZA_CORREZIONE_ERRORI_MATERIALI }" >

				<spring:url var="paginazione" value="/procedimento/atto/istanzaModificaPersonali.htm?pag=true" />
					<logic:present name="listaRicorso">
					<div style="max-height: 400px; overflow-y: scroll; float: left; width: 100%;">
						<display:table class="tb_lista" uid="soggettoRicorsoForm" name="${listaRicorso}">
								<c:if test="${attoForm.categoriaAtto.id == '340'}">
								
								<display:column paramId="bene.idBene" paramProperty="beniProcedimento[${soggettoRicorsoForm_rowNum-1}].idBene" style="width:1%" title="Sel." >	
									<input type="checkbox" onclick="impostaIstanza('Soggetto',this, 0, ${soggettoRicorsoForm.idSoggetto}, 0,0);"
									${soggettoRicorsoForm.flgSoggettoValidoRicorso == 'R' ? 'checked' : ''}  ${riepilogoRicorso}>
								</display:column>
								
								</c:if>
								<display:column title="Diretto interessato" style="width:10%">
									<c:out value="${soggettoRicorsoForm.descrizioneSoggetto}"></c:out>
								</display:column>
								<c:choose>
								<c:when test="${soggettoRicorsoForm.misura.descrizioneMisura == 'NO_MIS'}">
									<display:column title="">
										<spring:message code="stralcio.misureAssenti" />
									</display:column>
								</c:when>
								<c:otherwise>
									<display:column title="Misure">
										<display:table class="tb_sotto_lista" uid="bene" name="${soggettoRicorsoForm.listaBeneStralcioForm}">
											<display:column title="Sel." style="width:1%">
												<c:choose>
													<c:when test="${soggettoRicorsoForm.misura.descrizioneMisura == 'NO_MIS'}">
															---
													</c:when>
													<c:when test="${bene.tipoMisura == 'PATR'}">
													    
														<input type="checkbox" onclick="impostaIstanza('${PATRIMONIALE_DESCR}',this, ${bene.idBene}, ${soggettoRicorsoForm.idSoggetto}, 0,0);" ${bene.flgValidoBeneRicorso == 'R' ? 'checked' : ''} ${riepilogoRicorso}>
													   
													</c:when>
													<c:otherwise>
														&nbsp;
													</c:otherwise>
												</c:choose>
	
											</display:column>
											<c:choose>
												<c:when test="${soggettoRicorsoForm.misura.descrizioneMisura == 'NO_MIS'}">
													<display:column title="" sortable="false" style="width:10%">
															--- 
															</display:column>
													<display:column title="Misura" style="width:80%">
															--- 
															</display:column>
												</c:when>
												<c:when test="${bene.tipoMisura == 'PERS'}">
													<display:column title="Misura" sortable="false" style="width:50%">
													 <c:if test="${procedimento.tipoProcedimento.id!='16'}"> 
													 
														<c:if test="${bene.misura.durata != ''}">
															<c:out value="${bene.misura.tipoMisura.descrizione} (${bene.misura.durata})"></c:out>
														</c:if>
														<c:if test="${bene.misura.durata == ''}">
															<c:out value="${bene.misura.tipoMisura.descrizione}"></c:out>
														</c:if>
														
														<c:if test="${bene.misura.importoCauzione != null}">
															Importo: <c:out value="${bene.misura.importoCauzione}"></c:out>
														</c:if>														
													</c:if>
													 <c:if test="${procedimento.tipoProcedimento.id=='16'}"> 
													 
														<c:choose>
															<c:when test="${ bene.misura.tipoMisura.id != '21'}">
																	<c:if test="${bene.misura.durata != ''}">
																		<c:out value="${bene.misura.tipoMisura.descrizione} (${bene.misura.durata})"></c:out>
																	</c:if>
																	<c:if test="${bene.misura.durata == ''}">
																		<c:out value="${bene.misura.tipoMisura.descrizione}"></c:out>
																		<!-- Segnalazione EMS-77 -->
																		<c:if test="${bene.misura.carattereMisura == 'C' && bene.misura.importoCauzione != null}">
																		Importo: <c:out value="${bene.misura.importoCauzione}"></c:out>
																		</c:if>
																	</c:if>
															</c:when>
														</c:choose>													 						 
													 
													 </c:if>
													
													
													</display:column>
													<c:if test="${bene.misura.descEsito != null}">
													<display:column title="" style="width:80%">
														<c:out value="${bene.misura.descEsito} "></c:out>
													</display:column>
													</c:if>
												</c:when>
												<c:otherwise>
													<display:column title="Misura" sortable="false" style="width:50%">
															<div title="${bene.descrizioneBene}">
																<c:out value="${bene.idBene} - ${bene.descrizioneBene}"></c:out>
															</div>
													</display:column>
													<display:column title="" style="width:80%">
														<display:table class="tb_sotto_lista" uid="soggetto" name="${bene.listaSoggettoTitolareForm}" >
															<display:column property="misura.descEsito" title="" style="width:20%" />
															<display:column property="descrizioneSoggetto" title="" style="width:25%"/>
															<display:column property="tipoInteresse.descrizione" title="" style="width:8%"/>
															<display:column property="descrizioneFrazioneOQuota" title="" style="width:3%"/>
															<display:column sortable="false" style="text-align: center; width: 1%">
															   
																<input type="checkbox" onclick="impostaIstanza('${PATRIMONIALE_DESCR}',this, ${bene.idBene}, ${soggettoRicorsoForm.idSoggetto},${soggetto.idSoggetto},0);"
																	${soggetto.misura.flagMisuraValido == 'R' ? 'checked' : ''} ${riepilogoRicorso}>
															  
															
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
					</logic:present>		

				</c:if> 
				
				<c:if test="${attoForm.categoriaAtto.id == ISTANZA_LIQUIDAZIONE_AMM}" >
				<input type="hidden" id="id_selectAll_listaIdCheckbox_associati" value="true" />
						<display:table class="tb_lista" uid="bene" name="${beniInAmministrazione}" >
								<display:column paramId="idBene" paramProperty="listaIdCheckbox_associati" style="width:7%" title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('bene','listaIdCheckbox_associati');\" title=\"Seleziona/Deseleziona tutti i beni\"></img>&nbsp;Sel.bene" >						
									<form:checkbox path="listaIdCheckbox_associati" value="${bene.idBene}" checked="true"/>
								</display:column>
								<display:column property="idBene" title="Identificativo" sortable="false"/>
								<display:column property="descrizioneBene" title="Descrizione" sortable="false"/>
						</display:table>
				</c:if>
				
			<br />
			
			<%-- CS -----------------------------------     --%>
			<form:hidden path="soggetto.tipoSoggetto" id="tipoSoggetto.id"/>
			<c:if test="${attoForm.categoriaAtto.id == ISTANZA_CORREZIONE_ERRORI_MATERIALI}" >
				<fmt:formatDate pattern="dd/MM/yyyy" value="${soggetto.dataNascita}" var="myDateVar"/> 
				<%-- INIZIO MODIFICA PERSONA FISICA -----------------------------------     --%>		
				<logic:equal name="attoForm" property="soggetto.tipoSoggetto" value="F">
				
				<display:table id="row" name="${soggettoDaCorregere}" class="tb_lista" uid="amm" >
					<display:column property="descrizioneSoggetto" title="Generalità - Soggetto da correggere" sortable="false"/>
				</display:table>	
				<br/>	
								
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
		
						<tr>
								<td class="td_titolo" colspan="6">Correzione Persona Fisica </td>
							</tr>
						<tr>
						</tr>
							<tr>
								<td><form:label path="soggetto.cognome" cssClass="required"><spring:message code="cameralini.prevenzione.correzione.cognome" /></form:label></td>
								<td><form:input path="soggetto.cognome" id="cognomeCorr" maxlength="35" size="30" />
								<form:errors path="soggetto.cognome" class="testoRosso" /></td>
		
								<td><form:label path="soggetto.nome" cssClass="required"><spring:message code="cameralini.prevenzione.correzione.nome" /></form:label></td>
								<td><form:input path="soggetto.nome" id="nomeCorr" maxlength="35" size="30" />
								<form:errors path="soggetto.nome" class="testoRosso" /></td>
						</tr>
						<tr>
							<td class="td_titolo" colspan="6">Dati relativi alla nascita </td>
						</tr>
						<tr>
							<td><form:label path="soggetto.dataNascita" cssClass="required"> <spring:message code="dataNascita" /> </form:label></td>
							<td><form:input path="soggetto.dataNascita" maxlength="10" size="12" id="dataNascitaCorr" value="${myDateVar}" />
							<form:errors path="soggetto.dataNascita" class="testoRosso" /></td>
							
							<td><form:label path="soggetto.statoNascita.id" cssClass="required"><spring:message code="stato" /></form:label></td> 
							<c:set var="statoSelez" value="${soggetto.statoNascita.id}" />
							
							<td>
								<form:select id="id_StatoNascita" path="soggetto.statoNascita.id" size="1" value="${soggetto.statoNascita.id}" onchange="controllaCampiEAutocomplete(this.value,'ComuneNascita');">
									<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 
								</form:select> 
								<form:errors path="soggetto.statoNascita.id" class="testoRosso" />
							</td>
						</tr>
						<tr>
							<td><form:label path="soggetto.comuneNascita.id" cssClass="required"> <spring:message code="cameralini.prevenzione.correzione.comune" /> </form:label></td>
							<td colspan="3">
								<div class="ui-widget">
										<form:input id="id_descrizioneComuneNascita" path="soggetto.comuneNascita.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca" />
										<div id="id_loadingComuneNascita"></div> 
										<form:hidden id="id_hiddenComuneNascita" path="soggetto.comuneNascita.id" /> 
										<form:errors path="soggetto.comuneNascita.descrizione" class="testoRosso" />
								</div>
							</td>
						</tr>
						<tr>
						<td><form:label path="soggetto.sesso" cssClass="required"> <spring:message code="cameralini.prevenzione.correzione.sesso" /> </form:label></td>
						<td><form:select path="soggetto.sesso" id="sessoCombo">
							<form:option value="-1" label="---Seleziona Sesso---"/>
								<c:choose>
									<c:when test="${soggetto.sesso == 'M'}">
										<form:option value="M" label="Maschio" selected="true" />
									</c:when>
									<c:otherwise>
										<form:option value="M" label="Maschio" />
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${soggetto.sesso == 'F'}">
										<form:option value="F" label="Femmina" selected="true" />
									</c:when>
									<c:otherwise>
										<form:option value="F" label="Femmina" />
									</c:otherwise>
								</c:choose>
							</form:select> 
							<form:errors path="soggetto.sesso" class="testoRosso" />
						</td>
						<td><form:label path="soggetto.codiceFiscale" > <spring:message code="cameralini.prevenzione.correzione.codiceFiscale" /> </form:label></td>
						<td><form:input path="soggetto.codiceFiscale" id="codiceFiscale" maxlength="16" size="30"  />
							<form:errors path="soggetto.codiceFiscale" class="testoRosso" />
						</td>
					</tr>
					</table>
					
					</logic:equal>
					
					
		<logic:equal name="attoForm" property="soggetto.tipoSoggetto" value="G">
		
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4">Correzione Persona Giuridica</td>
					</tr>		
					<tr>
						<td><form:label path="soggetto.partitaIva" cssClass="required"> <spring:message code="cameralini.prevenzione.correzione.partitaIva" /> </form:label></td>
						<td  colspan="3">
	
							<form:input path="soggetto.partitaIva" maxlength="12" size="18" value="${soggetto.partitaIva}"/> 
							<form:errors path="soggetto.partitaIva" class="testoRosso" />
						</td>
					</tr>
					<tr>
						<td><form:label path="soggetto.ragioneSociale" cssClass="required"> <spring:message code="cameralini.prevenzione.correzione.ragioneSociale" /></form:label></td>
						<td colspan="3"><form:input path="soggetto.ragioneSociale" id="ragioneSociale" maxlength="50" size="51"  />
						<form:errors path="soggetto.ragioneSociale" class="testoRosso" />
						</td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"> Dati relativi alla sede legale </td>
					</tr>
					<tr>
						<td><form:label path="soggetto.statoSedeLegale.id" cssClass="required"><spring:message code="stato" /></form:label></td>
							<c:set var="statoSedeLegaleSel" value="${soggetto.statoSedeLegale.descrizione}" />
							<td>
								<form:select id="id_StatoSedeLegale" path="soggetto.statoSedeLegale.id" size="1" value="${soggetto.statoSedeLegale.id}" onchange="controllaCampiEAutocomplete(this.value,'ComuneSedeLegale');"> 
									<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 
								</form:select> <form:errors path="soggetto.statoSedeLegale.id" class="testoRosso" /></td>
								<td><form:label path="soggetto.comuneSedeLegale.id" cssClass="required"><spring:message code="comune" /></form:label></td> 							
								<td>
								
								<form:input  id="id_descrizioneComuneSedeLegale" path="soggetto.comuneSedeLegale.descrizione" size="50" /> 
									<div id="id_loadingComuneSedeLegale"></div>
									<form:hidden id="id_hiddenComuneSedeLegale" path="soggetto.comuneSedeLegale.id" /> 
									<form:errors path="soggetto.comuneSedeLegale.descrizione" class="testoRosso" />
								</td>
					<tr>
				</table>
		
		</logic:equal>
			
			<%-- FINE MODIFICA PERSONA GIURIDICA -----------------------------------     --%>
			
					
				</c:if>
			    
						 
			<br />
			
			
			<table class="tb_form">
			
			
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="ricorsoAppello.datiDeposito" /></td>
				   </tr>
				    <c:if test="${profilo.idTipoUfficio!=17}">
						<tr>
							<td style="width:15%"><form:label path="impugnazione.tipoDeposito.id" cssClass="required">
									<spring:message code="ricorsoAppello.deposito" />
								</form:label>
							</td>
							<td style="width:30%"><form:select id="impugnazione.tipoDeposito" path="impugnazione.tipoDeposito.id" size="1" cssStyle="width:200px;"  onchange="this.form.submit()">
									<form:option value="-1" label="------Seleziona tipo deposito--------------" />
									<form:option value="C" label="Cancelleria" />
									<form:option value="A" label="Altro ufficio giudiziario" />
									<form:option value="I" label="Istituto di detenzione" />
								</form:select>
								<form:errors path="impugnazione.tipoDeposito" class="testoRosso" />
							</td>
							<logic:equal name="attoForm" property="impugnazione.tipoDeposito.id" value="A">
							
								<td style="width:15%"><form:label path="impugnazione.altriUffici.id" cssClass="required">
										<spring:message code="ricorsoAppello.altriUffici" />
									</form:label>
								</td>						
								<td style="width:30%">
									<form:select path="impugnazione.altriUffici.id" size="1" >	
										<form:option value="-1" label="------Seleziona Istituto-------"/>
										<form:options items="${altriUffici}" itemValue="id" itemLabel="descrizione" /> 																	
									</form:select>	
									<form:errors path="impugnazione.altriUffici" class="testoRosso" />					
								</td>
							</logic:equal>
							
							<logic:equal name="attoForm" property="impugnazione.tipoDeposito.id" value="I">
								<td style="width:15%"><form:label path="impugnazione.istitutoDiDetenzione.id">
										<spring:message code="nuovosoggettofisico.istitutodidetenzione" />
									</form:label>
								</td>						
								<td style="width:30%">
									<form:select path="impugnazione.istitutoDiDetenzione.id" size="1" cssStyle="width:300px;" >	
										<form:option value="-1" label="------Seleziona Istituto-------"/>
										<form:options items="${istitutiDetenzione}" itemValue="id" itemLabel="descrizione" /> 																	
									</form:select>	
									<form:errors path="impugnazione.istitutoDiDetenzione" class="testoRosso" />					
								</td>	
							</logic:equal>
							
							<logic:equal name="attoForm" property="impugnazione.tipoDeposito.id" value="1">
								<td style="width:15%">&nbsp;
								</td>						
								<td style="width:30%">
									&nbsp;					
								</td>	
							</logic:equal>
						</tr>
						</c:if>
						<tr>
					<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
						<td><form:label path="dataDeposito" cssClass="required">
								<spring:message code="atto.datadeposito" />
							</form:label>
						</td>
						<td><form:input path="dataDeposito" maxlength="10" size="12" id="dataDeposito" />
							<form:errors path="dataDeposito" class="testoRosso" /></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						
					</c:if>
					<c:if test="${attoForm.dataRegistrazione!=null || attoForm.statoAtto.id==3}">
							<td><form:label path="dataDeposito">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataDeposito" /></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					
					</c:if>
				</tr>	  
                            
					</table>
					
				  <c:if test="${profilo.idTipoUfficio!=17}">
					<table class="tb_form" >
					<tr>
					<td  class="td_titolo" colspan="4"><spring:message code="ricorsoAppello.pervenimento" /></td>
				   </tr>
					<tr>
							<td style="width:15%"><form:label path="impugnazione.tipoModalitaPervenimento.id" cssClass="required">
									<spring:message code="ricorsoAppello.modalitaPervenimento" />
								</form:label>
							</td>
							<td style="width:30%"><form:select id="impugnazione.tipoModalitaPervenimento" path="impugnazione.tipoModalitaPervenimento.id" size="1" cssStyle="width: 200px;" onchange="this.form.submit()">
									<form:option value="-1" label="------Seleziona modalità pervenimento--------------" />
									<form:options items="${modalitaPervenimento}" itemValue="id" itemLabel="descrizione" />
								</form:select>
								<form:errors path="impugnazione.tipoModalitaPervenimento" class="testoRosso" />
								
							</td>
							
							
							<logic:equal name="attoForm" property="impugnazione.tipoModalitaPervenimento.id" value="4">
							<td style="width:15%"><form:label path="impugnazione.modalitaPervenimentoDesc" cssClass="required">
									<spring:message code="ricorsoAppello.altro" />
								</form:label>
							</td>
							<td style="width:30%">
                              <form:input path="impugnazione.modalitaPervenimentoDesc" maxlength="59" size="50" id="impugnazione.modalitaPervenimentoDesc"  />
                              <form:errors path="impugnazione.modalitaPervenimentoDesc" class="testoRosso" />
                             </td>
							</logic:equal>
							
							
							<logic:equal name="attoForm" property="impugnazione.tipoModalitaPervenimento.id" value="2">
							<td style="width:15%"><form:label path="impugnazione.descProvPosta">
									<spring:message code="ricorsoAppello.descProvPosta" />
								</form:label>
							</td>
							<td style="width:30%">
                              <form:input path="impugnazione.descProvPosta" maxlength="99" size="50" id="impugnazione.descProvPosta"  />
                             </td>
							</logic:equal>
							
							<logic:equal name="attoForm" property="impugnazione.tipoModalitaPervenimento.id" value="3">
							<td style="width:15%"><form:label path="impugnazione.modalitaPervenimentoDesc">
									<spring:message code="ricorsoAppello.nomeCognome" />
								</form:label>
							</td>
							<td style="width:30%">
                              <form:input path="impugnazione.modalitaPervenimentoDesc" maxlength="59" size="50" id="impugnazione.modalitaPervenimentoDesc"  />
                             
                             </td>
							</logic:equal>
							
							<logic:notEqual name="attoForm" property="impugnazione.tipoModalitaPervenimento.id" value="4">
							<logic:notEqual name="attoForm" property="impugnazione.tipoModalitaPervenimento.id" value="3">
							<logic:notEqual name="attoForm" property="impugnazione.tipoModalitaPervenimento.id" value="2">
							<td style="width:15%">&nbsp;</td>
							<td style="width:30%">&nbsp;</td>
							</logic:notEqual>
							</logic:notEqual>
							</logic:notEqual>
						</tr> 
							<tr>
							<td><form:label path="impugnazione.dataSpedizione">
									<spring:message code="ricorsoAppello.dataSpedizione" />
								</form:label>
							</td>
							<td>
                              <form:input path="impugnazione.dataSpedizione" maxlength="10" size="12" id="dataSpedizione"/>
                               <form:errors path="impugnazione.dataSpedizione" class="testoRosso" />
                             </td>
                             <td>&nbsp;
							</td>
							<td>
                              &nbsp;
                             </td>
                        </tr> 
                        <tr>
							<td><form:label path="impugnazione.dataPervenimento" cssClass="required">
									<spring:message code="ricorsoAppello.dataPervenimento" />
								</form:label>
							</td>
							<td>
                              <form:input path="impugnazione.dataPervenimento" maxlength="10" size="12" id="dataPervenimento"  />
                              <form:errors path="impugnazione.dataPervenimento" class="testoRosso" />
                             </td>
                             <td><form:label path="impugnazione.orarioPervenimento">
									<spring:message code="ricorsoAppello.oraPervenimento" />
								</form:label>
							</td>
							<td>
                              <form:input path="impugnazione.orarioPervenimento" maxlength="5" size="12" id="impugnazione.orarioPervenimento"  />
                              <form:errors path="impugnazione.orarioPervenimento" class="testoRosso" />
                             </td>
                        </tr> 
                            
					</table>
					
					<table class="tb_form">
					<tr>
					  <td class="td_titolo" colspan="4"><spring:message code="ricorsoAppello.depositante" /></td>
				    </tr>
					    <tr>
						
						   <logic:notEqual name="attoForm" property="impugnazione.descTipoProp" value="">
								<td style="width:15%"><form:label path="impugnazione.descTipoProp">
										<spring:message code="ricorsoAppello.parte" />
									</form:label>
								</td>
						     	<td style="width:30%">
	                              <c:out value="${attoForm.impugnazione.descTipoProp}"></c:out>
							   </td>
							   
							  <form:hidden path="impugnazione.descTipoProp" id="impugnazione.descTipoProp"/>
                           </logic:notEqual>
			
						   <logic:equal name="attoForm" property="impugnazione.descTipoProp" value="">
								<td style="width:15%"><form:label path="impugnazione.parte.id" cssClass="required">
										<spring:message code="ricorsoAppello.parte" />
									</form:label>
								</td>

								<c:if test="${attoForm.categoriaAtto.id == ISTANZA_LIQUIDAZIONE_AMM	||
													attoForm.categoriaAtto.id == ISTANZA_CORREZIONE_ERRORI_MATERIALI}" >
									
									<td style="width:30%"><form:select id="impugnazione.parte" path="impugnazione.parte.id" size="1" cssStyle="width: 200px;" onchange="this.form.submit()">
											<form:option value="-1" label="------Seleziona tipo parte--------------" />
											<c:if test="${attoForm.categoriaAtto.id == ISTANZA_LIQUIDAZIONE_AMM}" >
												<form:option value="I" label="Amministratore" />
											</c:if>
											<c:if test="${attoForm.categoriaAtto.id != ISTANZA_LIQUIDAZIONE_AMM}" >
												<form:option value="I" label="Intestatario" />
											</c:if>
											<form:option value="A" label="Difensore" />
											<form:option value="E" label="Delegato" />
										</form:select>
										<form:errors path="impugnazione.parte" class="testoRosso" />
										</td>
								</c:if>
								
								<c:if test="${!(attoForm.categoriaAtto.id == ISTANZA_LIQUIDAZIONE_AMM ||
													attoForm.categoriaAtto.id == ISTANZA_CORREZIONE_ERRORI_MATERIALI)}" >
													
									<td style="width:30%"><form:select id="impugnazione.parte" path="impugnazione.parte.id" size="1" cssStyle="width: 200px;" onchange="this.form.submit()">
											<form:option value="-1" label="------Seleziona tipo parte--------------" />
											<c:if test="${procedimento.tipoProcedimento.id!='18'}">
												<form:option value="D" label="Proposto" />
											</c:if>
											<c:if test="${procedimento.tipoProcedimento.id=='18'}">
												<form:option value="I" label="Interveniente" />
											</c:if>
											<!-- Segnalazione EMS-118 -->
											<c:if test="${procedimento.tipoProcedimento.id!='14'}">
												<form:option value="T" label="Terzo interessato" />
											</c:if>
											<form:option value="A" label="Difensore" />
											<form:option value="E" label="Delegato" />
										</form:select>
										<form:errors path="impugnazione.parte" class="testoRosso" />
										</td>
								</c:if>
										
								<logic:equal name="attoForm" property="impugnazione.parte.id" value="D">
								
							</tr>
							<tr>					
									<td colspan = "4">																		
							
									 <display:table class="tb_sotto_lista" uid="soggettiDiretti" name="${soggettiDiretti}">										
										<display:column paramId="impugnazione.idsDirettiInteressati" paramProperty="impugnazione.idsDirettiInteressati" sortable="false" style="text-align: center; width: 10%" title="Seleziona">
											  <form:radiobutton path="impugnazione.idsDirettiInteressati" value="${soggettiDiretti.idSoggetto}" />
										</display:column>										
										<display:column title="Diretti Interessati" style="width:80%">
											<c:out value="${soggettiDiretti.descrizioneSoggetto} "></c:out>
										</display:column>										
									</display:table>
									<form:errors path="impugnazione.direttiInteressati" class="testoRosso" />	
									</td>													
								</logic:equal>
								<logic:equal name="attoForm" property="impugnazione.parte.id" value="T">								
						   </tr>
								<tr>					
									<td colspan = "4">										
									 <display:table class="tb_sotto_lista" uid="soggettiTitolari" name="${soggettiTitolari}">										
										
								       <display:column paramId="impugnazione.idsSoggettiTitolari" paramProperty="impugnazione.idsSoggettiTitolari" sortable="false" style="text-align: center; width: 10%" title="Seleziona">
										  <form:radiobutton path="impugnazione.idsSoggettiTitolari" value="${soggettiTitolari.idSoggetto}" />
										</display:column>	
																													
										<display:column title="Soggetti Titolari" style="width:80%">
											<c:out value="${soggettiTitolari.descrizioneSoggetto} "></c:out>
										</display:column>										
									</display:table>	
								  <form:errors path="impugnazione.soggettiTitolari" class="testoRosso" />
								  </td>									
								</logic:equal>	
								
								<c:if test="${attoForm.impugnazione.parte.id == 'A' || attoForm.impugnazione.parte.id == 'E' }">
								
									<td style="width:1%">&nbsp;
									</td>						
									<td style="width:30%">
										<form:select path="impugnazione.avvocatiDifensori.id" size="1" onchange="this.form.submit()" >	
											<form:option value="-1" label="------Avvocati Difensori-------"/>
											<form:options items="${avvocatiDifensori}" itemValue="id" itemLabel="descrizione" /> 																	
										</form:select>
										<form:errors path="impugnazione.avvocatiDifensori" class="testoRosso" />
										
										
									</td>	
								</tr>
								<tr>					
									<td colspan = "4">										
									<display:table class="tb_sotto_lista" uid="soggettiDifensori" name="${soggettiDifensori}">											
										
								       <display:column paramId="impugnazione.idsSoggettiDifensori" paramProperty="impugnazione.idsSoggettiDifensori" sortable="false" style="text-align: center; width: 10%" title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('soggettiDifensori','impugnazione.idsSoggettiDifensori');\" title=\"Seleziona/Deseleziona tutti i soggetti\"></img>&nbsp;Seleziona">
										  <form:checkbox  path="impugnazione.idsSoggettiDifensori" value="${soggettiDifensori.id}" />
										</display:column>																			
										<display:column title="Soggetti" style="width:80%">
											<c:out value="${soggettiDifensori.descrizione} "></c:out>
										</display:column>										
									</display:table>	
									<form:errors path="impugnazione.soggettiDifensori" class="testoRosso" />
													
									</td>
								</c:if>
								
								
						  	
						  
						<logic:equal name="attoForm" property="impugnazione.parte.id" value="I">
						
							<tr>					
								<td colspan = "4">		
									<c:if test="${procedimento.tipoProcedimento.id!='18'}">																
										<display:table id="row" name="${listaIntestatari}" class="tb_lista" uid="amm" >
											<display:column property="descrizioneAmministratore" title="Generalità" sortable="false"/>
										</display:table>
									</c:if>	
									<c:if test="${procedimento.tipoProcedimento.id=='18'}">	  		
										<display:table class="tb_lista" name="${intervenienti}" uid="sogg" requestURI="${azione}" >
											<display:column property="descrizioneSoggettoConVersione" title="Generalità" sortable="false" />
										</display:table>
									</c:if>										
								</td>			
																		
						</logic:equal>	
											  
					</logic:equal>	 
				
					</tr> 	
						      
					</table>
				    
				    <table class="tb_form">
					  <tr>
				     	<td class="td_titolo" colspan="4"><spring:message code="ricorsoAppello.ricevente" /></td>
				      </tr>
						
						
						<tr >
						   <td style="width:15%"><form:label path="impugnazione.cognome" cssClass="required">
									<spring:message code="ricorsoAppello.cognome" />
								</form:label>
							</td>
							<td style="width:30%">
                              <form:input path="impugnazione.cognome" maxlength="25" size="25" id="impugnazione.cognome" />
                              <form:errors path="impugnazione.cognome" class="testoRosso" />
                             </td>
							<td style="width:15%"><form:label path="impugnazione.nome" cssClass="required">
									<spring:message code="ricorsoAppello.nome" />
								</form:label>
							</td>
							<td style="width:30%">
                              <form:input path="impugnazione.nome" maxlength="25" size="25" id="impugnazione.nome"/>
                              <form:errors path="impugnazione.nome" class="testoRosso" />
                             </td>
                          
                        </tr> 
		
              </table>
              </c:if>
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>

				
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
				
				
			
				
					
						
							
							<c:if test="${attoForm.dataRegistrazione!=null}">
							<tr>
								<td><form:label path="dataRegistrazione">
										<spring:message code="atto.dataregistrazione" />
									</form:label></td>
								<td><fmt:formatDate value="${attoForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataRegistrazione" /></td>
							    <td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							</c:if>
							
							
							
							<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />	
						
					
						
				
			
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<c:if test="${attoForm.dataRegistrazione==null}">
						<c:if test="${attoForm.statoAtto.id!=3}">
						
							<td class="td_tasto"><input type="button" value="Salva" onclick="inserisciAtto('Conferma', '${attoForm.allegato.documentoFirmato}')" title="Salva" class="nowait" /></td> 
							<input	id="Conferma" type="submit" name="Conferma" value="" style="display: none" />
						
						</c:if>
						<logic:present name="altroNomeTastoRegistra">
							<td class="td_tasto"><input type="button" value="${altroNomeTastoRegistra}" onclick="inserisciAtto('Invia', '${attoForm.allegato.documentoFirmato}')" title="Registra" class="nowait" /></td>
						</logic:present>
						<logic:notPresent name="altroNomeTastoRegistra">
							<td class="td_tasto"><input type="button" value="Registra" onclick="inserisciAtto('Invia', '${attoForm.allegato.documentoFirmato}')" title="Registra" class="nowait" /></td>
						</logic:notPresent>
						<input	id="Invia" type="submit" name="Invia" value="" style="display: none" />
						
					</c:if>
					
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
			<br />
			<br />
		</form:form>
		<smp:back buttonId="backButton"></smp:back>

		<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
			<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataRicezione",true);
   					prendiData("#dataDeposito",true);
   					prendiData("#dataImpugnazione",true);
					prendiData("#dataSpedizione",true);
					prendiData("#dataPervenimento",true);

					
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
			
			function impostaIstanza(tipoRicorso, obj, idBene, idSoggInt, idSoggTit,tipoMisura){

			  
			     
				var params = '?tipoRicorso='+tipoRicorso+'&checked='+$(obj).is(":checked")+ '&idProcedimento='+$('#idProcedimento').val()+ '&idUfficio='+$('#idUfficio').val()+ '&idBene='+idBene+ '&idSoggInt='+idSoggInt+ 
				'&idSoggTit='+normalizzaValore(idSoggTit)+'&codAtto='+$('#codAtto').val()+'&carattereMisura='+tipoMisura;
				
				
				$('#id_formConfermaInvia').attr('action', '${pageContext.request.contextPath}/procedimento/atti/impostaIstanza'+params );
				 
					$('#id_formConfermaInvia').submit();
					

			}

            function normalizzaValore(valore){
				
				if(valore == -1){
					return '';
				}else{
					return valore;
				}
			}
            
			
			if($( "#id_StatoNascita" ).val() == 1)
			{
				doAutocomplete('ComuneNascita');
			}
			if($( "#id_StatoSedeLegale" ).val() == 1)
			{
				doAutocomplete('ComuneSedeLegale');
			}
			
		</script>

	

		
	</tiles:putAttribute>
</tiles:insertDefinition>