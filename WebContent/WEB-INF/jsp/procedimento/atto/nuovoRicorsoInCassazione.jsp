<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
		<spring:url var="azione" value="/procedimento/atto/nuovoRicorsoInCassazione.htm" />
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
		
			<form:hidden path="idProcedimento" id="idProcedimento"/>
			<form:hidden path="idUfficio" id="idUfficio"/>
			<form:hidden path="codAtto" id="codAtto"/>
		    <form:hidden path="decisorioIIGrado.id" id="decisorioIIGrado.id"/>
			<form:hidden path="categoriaAtto.id" id="categoriaAtto.id"/>
			<form:hidden path="statoAtto.id" id="statoAtto.id"/>
			<input type="hidden" id="id_selectAll_impugnazione.idsDirettiInteressati" value="true"/>
			<input type="hidden" id="id_selectAll_impugnazione.idsSoggettiTitolari" value="true"/>
			<input type="hidden" id="id_selectAll_impugnazione.idsSoggettiDifensori" value="true"/>			
			

			
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			
			<logic:notPresent name="riepilogoRicorso">			
					<c:set var="PATRIMONIALE_ID" value="<%=Constants.TipoMisura.PATRIMONIALE_ID%>"/>
					<c:set var="PERSONALE_ID" value="<%=Constants.TipoMisura.PERSONALE_ID%>"/>
					
					<c:set var="PATRIMONIALE_DESCR" value="<%=Constants.TipoMisura.PATRIMONIALE_DESCR%>"/>
					<c:set var="PERSONALE_DESCR" value="<%=Constants.TipoMisura.PERSONALE_DESCR%>"/>
					
			
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="td_titolo" colspan="6"><spring:message code="ricorsoCassazione.gestione" /></td>
						</tr>
						<tr>					
							<td>
								<form:label path="tipoMisure.id">
									<spring:message code="ricorsoAppello.tipoMisura" />
								</form:label>
							</td>
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
								<form:label path="tipologiaBene.id">
									<spring:message code="ricorsoAppello.beni" />
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
							<td class="td_tasto"><input type="button" title="Pulisci filtri di ricerca" value="Pulisci" onclick="pulisciFiltri(); submitRicorso('ricercaRicorsoOnSelect');" class="nowait" /></td>
						</tr>
					</table>
				<br/>
			</logic:notPresent>
			
			<logic:present name="riepilogoRicorso">
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="6"><spring:message code="ricorsoCassazione.riepilogoRicorso" /></td>
				</tr>
				</table>
			</logic:present>
			
			
			
			<logic:present name="listaRicorso">
				<c:if test="${(procedimento.tipoProcedimento.id == 17 || procedimento.tipoProcedimento.id == 19) }"> 
					<display:table id="row" name="${listaIntestatari}" class="tb_lista" uid="amm" >
						<display:column property="descrizioneAmministratore" title="Generalità" sortable="false"/>
					</display:table>	
					<display:table class="tb_lista" uid="bene" name="${beniInAmministrazione}" >
							<display:column property="idBene" title="Identificativo" sortable="false"/>
							<display:column property="descrizioneBene" title="Descrizione" sortable="false"/>
						</display:table>
						<table class="tb_form">
							<tr>
								<td class="td_titolo" colspan="4"><spring:message code="bene.decisione" /></td>
							</tr>
							<tr>
								<td><form:label path="modalitaTrasmissione.id">
									<spring:message code="bene.decisione" />
									</form:label>
								</td>
								<td colspan="3">
									<form:select path="modalitaTrasmissione.id" size="1" disabled="true">
										<form:option value="-1" label="------Seleziona Decisione -------" />
										<form:options items="${esitiDecisioneBeni}" itemValue="id" itemLabel="descrizione" /> 	
									</form:select>
								</td>
							</tr>
							
						</table>
				</c:if>	
				<c:if test="${(procedimento.tipoProcedimento.id != 17 && procedimento.tipoProcedimento.id != 19) }"> 			
					<div style="max-height: 400px; overflow-y: scroll; float: left; width: 100%;">
						<display:table class="tb_lista" uid="soggettoRicorsoForm" name="${listaRicorso}">
								<display:column paramId="bene.idBene" paramProperty="beniProcedimento[${soggettoRicorsoForm_rowNum-1}].idBene" style="width:1%" title="Sel." >	
									<input type="checkbox" onclick="impostaRicorso('Soggetto',this, 0, ${soggettoRicorsoForm.idSoggetto}, 0,0);"
									${soggettoRicorsoForm.flgSoggettoValidoRicorso == 'R' ? 'checked' : ''}  ${riepilogoRicorso}>
								</display:column>
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
														<input type="checkbox" onclick="impostaRicorso('${PATRIMONIALE_DESCR}',this, ${bene.idBene}, ${soggettoRicorsoForm.idSoggetto}, 0,0);" ${bene.flgValidoBeneRicorso == 'R' ? 'checked' : ''} ${riepilogoRicorso}>
													</c:when>
													<c:otherwise>
														<input type="checkbox" onclick="impostaRicorso('${PERSONALE_DESCR}',this, ${bene.misura.idMisuraPersonale}, ${soggettoRicorsoForm.idSoggetto}, 0,'${bene.misura.carattereMisura}');" ${bene.misura.flagMisuraValido == 'R' ? 'checked' : ''} ${riepilogoRicorso}>
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
													<c:if test="${bene.misura.durata != ''}">
														<c:out value="${bene.misura.tipoMisura.descrizione} (${bene.misura.durata})"></c:out>
													</c:if>
													<c:if test="${bene.misura.durata == ''}">
														<c:out value="${bene.misura.tipoMisura.descrizione}"></c:out>
													</c:if>
													<c:if test="${bene.misura.importoCauzione != null}">
														Importo: <c:out value="${bene.misura.importoCauzione}"></c:out>
													</c:if>													
													</display:column>
													<display:column>
														<table class="tb_sotto_lista" >
														<tr>
														<c:if test="${ bene.misura.descEsito != null && bene.misura.descEsito != ''}">
														  <th width="100px">Decisione I Grado</th>
														</c:if>  
														  <th width="100px">Decisione II Grado</th>
														  <th width="42%">&nbsp;</th>	
														  <th width="8%"></th>
														</tr>
														<tr>
														<c:if test="${ bene.misura.descEsito != null && bene.misura.descEsito != ''}">
														  <td><c:out value="${bene.misura.descEsito} "></c:out></td>
														</c:if> 
														  <td><c:out value="${bene.misura.descEsito2} "></c:out></td>
														  <td>&nbsp;</td>
														  <td>&nbsp;</td>
														</tr>													  
														</table>
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
															<display:column property="misura.descEsito" title="Decisione I Grado" style="width:100px" />
															<display:column property="misura.descEsito2" title="Decisione II Grado" style="width:100px" />
															
															
															<display:column  title="Titoli di Diritto" style="width:42%">
															    <c:out value="${soggetto.descrizioneSoggetto}" />
																<c:out value="${soggetto.tipoInteresse.descrizione}" />
																<c:out value="${soggetto.descrizioneFrazioneOQuota}" />
											
															</display:column>
					
															<display:column sortable="false" style="text-align: center; width: 8%">
																<input type="checkbox" onclick="impostaRicorso('${PATRIMONIALE_DESCR}',this, ${bene.idBene}, ${soggettoRicorsoForm.idSoggetto},${soggetto.idSoggetto},0);"
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
				</c:if>
				<div style="clear: both;" ></div>
				<br />
				<logic:empty name="riepilogoRicorso">
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="button" name="riepilogoRicorsoButton" title="Salva" value="Salva"  class="nowait" onclick="submitRicorso('riepilogoRicorso');""/></td>
					</tr>
				</table>
				</logic:empty>
			</logic:present>

			<logic:notEmpty name="riepilogoRicorso">
				<logic:notEmpty name="listaRicorso">
				<table class="tb_form">
				 <c:if test="${profilo.idTipoUfficio!=8}">
				  	
					<tr>
					<td class="td_titolo" colspan="4"><spring:message code="ricorsoAppello.datiDeposito" /></td>
				   </tr>
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
						<fmt:formatDate pattern="dd/MM/yyyy" value="<%=new java.util.Date() %>" var="myDateVar"/>
						<fmt:formatDate pattern="HH:mm" value="<%=new java.util.Date() %>" var="myTimeVar"/>
						<tr>
							<td ><form:label path="impugnazione.dataImpugnazione" cssClass="required">
									<spring:message code="ricorsoAppello.dataImpugnazione" />
								</form:label>
							</td>
							<td> 
                              <form:input path="impugnazione.dataImpugnazione" maxlength="10" size="12" id="dataImpugnazione"/>
                               <form:errors path="impugnazione.dataImpugnazione" class="testoRosso" /> 
                             </td>
                             <td><form:label path="impugnazione.orarioImpugnazione">
									<spring:message code="ricorsoAppello.oraImpugnazione" />
								</form:label>
							</td>
							<td>
                              <form:input path="impugnazione.orarioImpugnazione" maxlength="5" size="12" id="impugnazione.orarioImpugnazione" />
                              <form:errors path="impugnazione.orarioImpugnazione" class="testoRosso" />
                             </td>
                        </tr> 
                        <tr>
							<td><form:label path="impugnazione.dataRegistrazione" cssClass="required">
									<spring:message code="ricorsoAppello.dataRegistrazione" />
								</form:label>
							</td>
							<td>
                              <form:input path="impugnazione.dataRegistrazione" maxlength="10" size="12" id="impugnazione.dataRegistrazione" value="${myDateVar}" readonly="true"/>
                             </td>
                             <td><form:label path="impugnazione.orarioRegistrazione">
									<spring:message code="ricorsoAppello.oraRegistrazione" />
								</form:label>
							</td>
							<td>
                              <form:input path="impugnazione.orarioRegistrazione" maxlength="5" size="12" id="impugnazione.orarioRegistrazione" value="${myTimeVar}" readonly="true" />
                             </td>
                        </tr>     
                            
					</table>
					
					
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

								<c:if test="${sessionScope.procedimento.tipoProcedimento.id==17}" >
									
									<td style="width:30%"><form:select id="impugnazione.parte" path="impugnazione.parte.id" size="1" cssStyle="width: 200px;" onchange="this.form.submit()">
											<form:option value="-1" label="------Seleziona tipo parte--------------" />
											<form:option value="I" label="Amministratore" />
											<form:option value="A" label="Difensore" />
											<form:option value="E" label="Delegato" />
										</form:select>
										<form:errors path="impugnazione.parte" class="testoRosso" />
										</td>
								</c:if>
								
								<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=17}" >
													
									<td style="width:30%"><form:select id="impugnazione.parte" path="impugnazione.parte.id" size="1" cssStyle="width: 200px;" onchange="this.form.submit()">
											<form:option value="-1" label="------Seleziona tipo parte--------------" />
											<form:option value="D" label="Proposto" />
											<!-- Segnalazione EMS-128 -->
											<c:if test="${procedimento.tipoProcedimento.id!='14'}">
												<form:option value="T" label="Terzo interessato" />
											</c:if>
											<form:option value="A" label="Difensore" />
											<form:option value="E" label="Delegato" />
											<c:if test="${intervenienti==true}" >
												<form:option value="N" label="Interveniente" />
											</c:if>											
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
										  <form:radiobutton  path="impugnazione.idsDirettiInteressati" value="${soggettiDiretti.idSoggetto}" />
										</display:column>										
										<display:column title="Diretti Interessati" style="width:80%">
											<c:out value="${soggettiDiretti.descrizioneSoggetto} "></c:out>
										</display:column>										
									</display:table>
									<form:errors path="impugnazione.direttiInteressati" class="testoRosso" />	
									</td>													
								</logic:equal>
								<logic:equal name="attoForm" property="impugnazione.parte.id" value="N">
								
								</tr>
								<tr>					
									<td colspan = "4">																		
							
									 <display:table class="tb_sotto_lista" uid="soggettiDiretti" name="${soggettiDiretti}">										
										<display:column paramId="impugnazione.idsDirettiInteressati" paramProperty="impugnazione.idsDirettiInteressati" sortable="false" style="text-align: center; width: 10%" title="Seleziona">
											  <form:radiobutton path="impugnazione.idsDirettiInteressati" value="${soggettiDiretti.idSoggetto}" />
										</display:column>										
										<display:column title="Intervenienti" style="width:80%">
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
										  <form:radiobutton  path="impugnazione.idsSoggettiTitolari" value="${soggettiTitolari.idSoggetto}" />
										</display:column>	
																													
										<display:column title="Soggetti Titolari" style="width:80%">
											<c:out value="${soggettiTitolari.descrizioneSoggetto} "></c:out>
										</display:column>										
									</display:table>	
								  <form:errors path="impugnazione.soggettiTitolari" class="testoRosso" />
								  </td>									
								</logic:equal>	
								
								<c:if test="${attoForm.impugnazione.parte.id == 'A' || attoForm.impugnazione.parte.id == 'E'}">
								
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
										<display:column title="Nell'interesse di" style="width:80%;align:left">
											<c:out value="${soggettiDifensori.descrizione} "></c:out>
										</display:column>										
									</display:table>	
									<form:errors path="impugnazione.soggettiDifensori" class="testoRosso" />
													
									</td>
								</c:if>
								<logic:equal name="attoForm" property="impugnazione.parte.id" value="I">
								
									<tr>					
										<td colspan = "4">																		
											<display:table id="row" name="${listaIntestatari}" class="tb_lista" uid="amm" >
												<display:column property="descrizioneAmministratore" title="Generalità" sortable="false"/>
											</display:table>	
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
                       </c:if>
                       
                       <jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />	
		
                            
					</table>
					
					<br />
					<br />
					<table class="tb_bottoni" align="center">
						<tr>
							 <c:if test="${profilo.idTipoUfficio!=8}">
							<td class="td_tasto"><input type="button" name="EsecuzioneRicorsoCassazione" title="Registra" value="Registra" class="nowait" onclick="inserisciAtto('ConfermaEsecuzioneRicorso', '${atto.allegato.documentoFirmato}');"/></td>
							<input	id="ConfermaEsecuzioneRicorso" type="submit" name="ConfermaEsecuzioneRicorso" value="" style="display: none" />
						    </c:if>
						     <c:if test="${profilo.idTipoUfficio==8}">
							<td class="td_tasto"><input type="button" name="EsecuzioneRicorsoCassazione" title="Conferma e invia" value="Conferma e invia" class="nowait" onclick="inserisciAtto('ConfermaEsecuzioneRicorso', '${atto.allegato.documentoFirmato}');"/></td>
							<input	id="ConfermaEsecuzioneRicorso" type="submit" name="ConfermaEsecuzioneRicorso" value="" style="display: none" />
						    </c:if>
							<td class="td_tasto"><input type="button" id="id_buttonIndietro" value="Indietro" onClick="indietro('/procedimento/atto/copertinaAtto.htm')"></td>						
						</tr>
					</table>
					<br />
					<br />
				</logic:notEmpty>
			  </logic:notEmpty>
				<td class="td_tasto">
				<input type="submit" name="" title="" value="" id="idSubmitRicorso" style="display: none"/></td>
				</td>
		</form:form>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
        <script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataImpugnazione",true);
					prendiData("#dataSpedizione",true);
					prendiData("#dataPervenimento",true);
				});
			</script>
        
		<script type="text/javascript">

		$( ".ricercaChange" ).change(function() {
			submitRicorso('ricercaRicorsoOnSelect');
		});
		


			function submitRicorso(name){
				$('#idSubmitRicorso').attr('name', name);
				$('#idSubmitRicorso').click();
			}

					
			function pulisciFiltri() {

				abilitaFiltri();	
				$('#tipoMisure').val(-1);
				$('#soggettiDiretti').val(-1);
				$('#soggettiTitolari').val(-1);
				$('#tipologiaBene').val(-1);
				$('#categoriaBene').val(-1);
				$('#sottoCategoriaBene').val(-1);
				

			
			}

			function abilitaFiltri(){
				$('#soggettiTitolari').removeAttr('disabled');
				$('#tipologiaBene').removeAttr('disabled');
				$('#categoriaBene').removeAttr('disabled');
				$('#sottoCategoriaBene').removeAttr('disabled');
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
					    url: "${pageContext.request.contextPath}/procedimento/atti/caricaCombo/"+comboDaCaricare+"/"+valoreSel,  
					    success: function(cbo){
							if(cbo){
								$('#'+comboDaCaricare).empty();
								jQuery('#'+comboDaCaricare).append(jQuery('<option></option').val('-1').html('------Seleziona categoria-------------'));

								$('#sottoCategoriaBene').empty();
								jQuery('#sottoCategoriaBene').append(jQuery('<option></option').val('-1').html('------Seleziona sottocategoria------'));
								 if(comboDaCaricare == "tipologiaBene" && valoreSel == -1)
								{
									$('#sottoCategoriaBene').val(-1);
								}
					    		$.each(cbo, function(i, field){
						    	  jQuery('#'+comboDaCaricare).append(jQuery('<option></option').val(field.id).html(field.descrizione)); 
					    	    });
							}
					    },
					      error: function (xhr, ajaxOptions, thrownError) {}
					  }); 
			}
	
			function impostaRicorso(tipoRicorso, obj, idBene, idSoggInt, idSoggTit,tipoMisura){

			    
			     
				var params = '?tipoRicorso='+tipoRicorso+'&checked='+$(obj).is(":checked")+ '&idProcedimento='+$('#idProcedimento').val()+ '&idUfficio='+$('#idUfficio').val()+ '&idBene='+idBene+ '&idSoggInt='+idSoggInt+ 
				'&idSoggTit='+normalizzaValore(idSoggTit)+'&filtroIdsSoggTit='+normalizzaValore($('#soggettiTitolari').val())+'&filtroIdsMisura='+normalizzaValore($('#tipoMisure').val())+'&filtroTipoBene='+$('#tipologiaBene').val()+'&filtroCatBene='+$('#categoriaBene').val()+'&filtroSottoCatBene='+$('#sottoCategoriaBene').val()+'&codAtto='+$('#codAtto').val()+'&carattereMisura='+tipoMisura;
               
				
				$('#id_formConfermaInvia').attr('action', '${pageContext.request.contextPath}/procedimento/atti/impostaRicorsoCassazione'+params );
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



			$(document).ready(function() {

				disabilitaTitolariEBeniSeTipMisPers();

				prepopolaMultiselect('${attoForm.tipoMisure.id}', 'tipoMisure');
				prepopolaMultiselect('${attoForm.soggettiDiretti.id}', 'soggettiDiretti');
				prepopolaMultiselect('${attoForm.soggettiTitolari.id}', 'soggettiTitolari');

			});

			//funzione utilizzata per popolare le multicompbo dopo il refresh della pagina...
			function prepopolaMultiselect(selectedIds, targetSelect){
				var arraySel = selectedIds.split(',');
				$('#'+targetSelect).val(arraySel);
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