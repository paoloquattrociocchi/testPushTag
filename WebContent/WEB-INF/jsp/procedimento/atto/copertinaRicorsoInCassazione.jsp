<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

<%
String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
<%-- 		<spring:url var="azione" value="/procedimento/atto/copertinaAtto.htm" /> --%>
<%-- 	<form:form method="post" id="id_formConfermaInvia" action="${azione}" commandName="attoForm" enctype="multipart/form-data"> --%>
		<p class="titolo_page">
			<logic:equal name="attoForm" property="categoriaAtto.id" value="24">
				<spring:message code="ricorsoCassazione.titolo" />
			</logic:equal>
		</p>
		<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
		<br />
		<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
		<br />
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
<%-- 	</form:form> --%>

<!-- 20180919 [SG]: problematica = verifica documento associato a ricorso per cassazione procedimento 2017/11 CDA Salerno -->
<!-- eliminato form in alto e modificato questo in basso aggiungendo id ed enctype -->
<!-- spostato da sopra a sotto il campo nascosto codAtto -->
		<spring:url var="filtro" value="/procedimento/atto/copertinaRicorsoInCassazione.htm" />
		<form:form method="post" id="id_formConfermaInvia" action="${filtro}" commandName="attoForm" enctype="multipart/form-data">
			<form:hidden path="codAtto" />
			<form:hidden path="warning" />
			<logic:present name="listaRicorso">
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><form:checkbox
								path="visualizzaFiltro" onclick="this.form.submit()" /> <spring:message
								code="ricorsoAppello.filtrosumisure" />
						</td>
					</tr>
					<logic:equal name="attoForm" property="visualizzaFiltro" value="true">
						<tr><td>
						<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
							<tr>
								<td><form:label path="tipoMisure.id">
										<spring:message code="ricorsoAppello.tipoMisura" />
								</form:label></td>
								<td>
									<form:label path="soggettiDirettiFiltro.id">
										<spring:message code="ricorsoAppello.soggettiDiretti" />
									</form:label>
								</td>
								<td>
									<form:label path="soggettiTitolariFiltro.id">
										<spring:message code="ricorsoAppello.soggettiTitolari" />
									</form:label>
								</td>
								<td><form:label path="tipologiaBene.id">
										<spring:message code="ricorsoAppello.beni" />
								</form:label></td>
							</tr>
							<tr>&nbsp;</tr>
							<c:set var="PATRIMONIALE_ID" value="<%=Constants.TipoMisura.PATRIMONIALE_ID%>" />
							<c:set var="PERSONALE_ID" value="<%=Constants.TipoMisura.PERSONALE_ID%>" />
							<c:set var="PATRIMONIALE_DESCR" value="<%=Constants.TipoMisura.PATRIMONIALE_DESCR%>" />
							<c:set var="PERSONALE_DESCR" value="<%=Constants.TipoMisura.PERSONALE_DESCR%>" />
							<tr>
								<td>
									<form:select id="tipoMisure" path="tipoMisure.id" onchange="disabilitaTitolariEBeniSeTipMisPers(); this.form.submit();">
										<form:option value="-1" label="---- Seleziona Tipo Misura ----" />
										<form:options items="${tipoMisure}" itemValue="id" itemLabel="descrizione" />
								</form:select></td>
								<td><form:select path="soggettiDirettiFiltro.id" size="1" onchange="this.form.submit()">
										<form:option value="-1" label="---- Seleziona Soggetto Diretto ----" />
										<form:options items="${soggettiDirettiFiltro}" itemValue="id" itemLabel="descrizione" />
								</form:select></td>
									<form:hidden path="soggettiDirettiFiltro.descrizione" />
								<td><form:select path="soggettiTitolariFiltro.id" size="1" onchange="this.form.submit()" disabled="false">
										<form:option value="-1" label="---- Seleziona Soggetto Titolare ----" />
										<form:options items="${soggettiTitolariFiltro}" itemValue="id" itemLabel="descrizione" />
								</form:select></td>
									<form:hidden path="soggettiTitolariFiltro.descrizione" />
								<td>
									<table>
										<tr>
											<td><form:label path="tipologiaBene.id">
													<spring:message code="bene.tipologia" />
											</form:label></td>
											<td colspan="3"><form:select id="tipologiaBene" path="tipologiaBene.id" size="1" cssStyle="width: 350px;" onchange="this.form.submit()" disabled="false">
													<form:option value="-1" label="------Seleziona Tipologia-------" />
													<form:options items="${tipologie}" itemValue="id" itemLabel="descrizione" />
											</form:select></td>
												<form:hidden path="tipologiaBene.descrizione" />
										</tr>
										<tr>
											<td><form:label path="categoriaBene.id">
													<spring:message code="bene.categoria" />
												</form:label></td>
											<td colspan="3"><form:select id="categoriaBene" path="categoriaBene.id" size="1" cssStyle="width: 350px;" onchange="this.form.submit()" disabled="false">
													<form:option value="-1" label="------Seleziona Categoria-------" />
													<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
											</form:select> </td>
												<form:hidden path="categoriaBene.descrizione" />
										</tr>
										<tr>
											<td><form:label path="sottoCategoriaBene.id">
													<spring:message code="bene.sottocategoria" />
												</form:label></td>
											<td colspan="3"><form:select id="sottoCategoriaBene" path="sottoCategoriaBene.id" size="1" cssStyle="width: 350px;" onchange="this.form.submit()" disabled="false">
													<form:option value="-1" label="------Seleziona Sottocategoria-------" />
													<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
											</form:select></td>
												<form:hidden path="sottoCategoriaBene.descrizione" />
										</tr>
									</table>
								</td>
							</tr>
						</table>
						</td></tr>
						<tr><td>
						<table class="tb_bottoni" align="center">
							<tr>
								<td class="td_tasto"><input type="button" title="Pulisci filtri di ricerca" value="Pulisci"
									onclick="pulisciFiltri(); submit();" class="nowait" /></td>
							</tr>
						</table>
						</td></tr>
						<br />
					</logic:equal>
				</table>
				<div style="max-height: 400px; overflow-y: scroll; float: left; width: 100%;">
					<display:table class="tb_lista" uid="soggettoRicorsoForm" name="${listaRicorso}">
							
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
												<display:column title="">																			 
													<table class="tb_sotto_lista" >
													<tr>
													<c:if test="${ bene.misura.descEsito != null && bene.misura.descEsito != ''}">
													  <th width="100px">Decisione I Grado</th>
													</c:if>  
													  <th width="100px">Decisione II Grado</th>
													  <th width="50%">&nbsp;</th>	
													</tr>
													<tr>
													<c:if test="${ bene.misura.descEsito != null && bene.misura.descEsito != ''}">
													  <td><c:out value="${bene.misura.descEsito} "></c:out></td>
													 </c:if>
													  <td><c:out value="${bene.misura.descEsito2} "></c:out></td>
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
														<display:column  title="Titoli di Diritto" style="width:50%">
														    <c:out value="${soggetto.descrizioneSoggetto}" />
															<c:out value="${soggetto.tipoInteresse.descrizione}" />
															<c:out value="${soggetto.descrizioneFrazioneOQuota}" />
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
			</logic:present>
			<table class="tb_form">
				 <c:if test="${((profilo.idTipoUfficio!=8) || (attoForm.statoAtto.id == 3))}">
					<tr>
					<td class="td_titolo" colspan="4"><spring:message code="ricorsoAppello.datiDeposito" /></td>
				   </tr>
						<tr>
							<td style="width:15%"><form:label path="impugnazione.tipoDeposito.id">
									<spring:message code="ricorsoAppello.deposito" />
								</form:label>
							</td>
							<td style="width:30%"><form:select id="impugnazione.tipoDeposito" path="impugnazione.tipoDeposito.id" size="1" cssStyle="width:200px;"   disabled="true" >
									<form:option value="-1" label="------Seleziona tipo deposito--------------" />
									<form:option value="C" label="Cancelleria" />
									<form:option value="A" label="Altro ufficio giudiziario" />
									<form:option value="I" label="Istituto di detenzione" />
								</form:select>
							</td>
							<logic:equal name="attoForm" property="impugnazione.tipoDeposito.id" value="A">
							<td style="width:15%"><form:label path="impugnazione.altriUffici.id">
									<spring:message code="ricorsoAppello.altriUffici" />
								</form:label>
							</td>						
							<td style="width:30%">
								<form:select path="impugnazione.altriUffici.id" size="1" disabled="true">	
									<form:option value="-1" label="------Seleziona Istituto-------"/>
									<form:options items="${altriUffici}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>	
							</td>
							</logic:equal>
							<logic:equal name="attoForm" property="impugnazione.tipoDeposito.id" value="I">
							<td style="width:15%"><form:label path="impugnazione.istitutoDiDetenzione.id">
									<spring:message code="nuovosoggettofisico.istitutodidetenzione" />
								</form:label>
							</td>						
							<td style="width:30%">
								<form:select path="impugnazione.istitutoDiDetenzione.id" size="1" cssStyle="width:300px;" disabled="true">	
									<form:option value="-1" label="------Seleziona Istituto-------"/>
									<form:options items="${istitutiDetenzione}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>	
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
						<tr>
							<td ><form:label path="impugnazione.dataImpugnazione" >
									<spring:message code="ricorsoAppello.dataImpugnazione" />
								</form:label>
							</td>
							<td>
                              <form:input path="impugnazione.dataImpugnazione" maxlength="10" size="12" id="impugnazione.dataImpugnazione" disabled="true" />
                             </td>
                             <td><form:label path="impugnazione.orarioImpugnazione" >
									<spring:message code="ricorsoAppello.oraImpugnazione" />
								</form:label>
							</td>
							<td>
                              <form:input path="impugnazione.orarioImpugnazione" maxlength="5" size="12" id="impugnazione.orarioImpugnazione" disabled="true"/>
                             </td>
                        </tr> 
                        <tr>
							<td><form:label path="impugnazione.dataRegistrazione">
									<spring:message code="ricorsoAppello.dataRegistrazione" />
								</form:label>
							</td>
							<td>
                              <form:input path="impugnazione.dataRegistrazione" maxlength="10" size="12" id="impugnazione.dataRegistrazione" disabled="true"/>
                             </td>
                             <td><form:label path="impugnazione.orarioRegistrazione" >
									<spring:message code="ricorsoAppello.oraRegistrazione" />
								</form:label>
							</td>
							<td>
                              <form:input path="impugnazione.orarioRegistrazione" maxlength="5" size="12" id="impugnazione.orarioRegistrazione" disabled="true" />
                             </td>
                        </tr>     
					</table>
					<table class="tb_form" >
					<tr>
					<td  class="td_titolo" colspan="4"><spring:message code="ricorsoAppello.pervenimento" /></td>
				   </tr>
					<tr>
							<td style="width:15%"><form:label path="impugnazione.tipoModalitaPervenimento.id"  >
									<spring:message code="ricorsoAppello.modalitaPervenimento" />
								</form:label>
							</td>
							<td style="width:30%"><form:select id="impugnazione.tipoModalitaPervenimento" path="impugnazione.tipoModalitaPervenimento.id" size="1" cssStyle="width: 200px;" disabled="true">
									<form:option value="-1" label="------Seleziona modalità pervenimento--------------" />
									<form:options items="${modalitaPervenimento}" itemValue="id" itemLabel="descrizione" />
								</form:select>
							</td>
							<logic:equal name="attoForm" property="impugnazione.tipoModalitaPervenimento.id" value="4">
							<td style="width:15%"><form:label path="impugnazione.modalitaPervenimentoDesc"  >
									<spring:message code="ricorsoAppello.altro" />
								</form:label>
							</td>
							<td style="width:30%">
                              <form:input path="impugnazione.modalitaPervenimentoDesc" maxlength="255" size="50" id="impugnazione.modalitaPervenimentoDesc" disabled="true"  />
                             </td>
							</logic:equal>
							<logic:equal name="attoForm" property="impugnazione.tipoModalitaPervenimento.id" value="2">
							<td style="width:15%"><form:label path="impugnazione.descProvPosta">
									<spring:message code="ricorsoAppello.descProvPosta" />
								</form:label>
							</td>
							<td style="width:30%">
                              <form:input path="impugnazione.descProvPosta" maxlength="255" size="50" id="impugnazione.descProvPosta"  disabled="true"/>
                             </td>
							</logic:equal>
							<logic:equal name="attoForm" property="impugnazione.tipoModalitaPervenimento.id" value="3">
							<td style="width:15%"><form:label path="impugnazione.modalitaPervenimentoDesc">
									<spring:message code="ricorsoAppello.nomeCognome" />
								</form:label>
							</td>
							<td style="width:30%">
                              <form:input path="impugnazione.modalitaPervenimentoDesc" maxlength="255" size="50" id="impugnazione.modalitaPervenimentoDesc" disabled="true" />
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
							<td><form:label path="impugnazione.dataSpedizione" >
									<spring:message code="ricorsoAppello.dataSpedizione" />
								</form:label>
							</td>
							<td>
                              <form:input path="impugnazione.dataSpedizione" maxlength="10" size="12" id="impugnazione.dataSpedizione" disabled="true"/>
                             </td>
                             <td>&nbsp;
							</td>
							<td>
                             &nbsp;
                             
                             </td>
                        </tr> 
                        <tr>
							<td><form:label path="impugnazione.dataPervenimento" >
									<spring:message code="ricorsoAppello.dataPervenimento" />
								</form:label>
							</td>
							<td>
                              <form:input path="impugnazione.dataPervenimento" maxlength="10" size="12" id="impugnazione.dataPervenimento" disabled="true" />
                             </td>
                             <td><form:label path="impugnazione.orarioPervenimento" >
									<spring:message code="ricorsoAppello.oraPervenimento" />
								</form:label>
							</td>
							<td>
                              <form:input path="impugnazione.orarioPervenimento" maxlength="5" size="12" id="impugnazione.orarioPervenimento" disabled="true" />
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
                           </logic:notEqual>
                           <logic:equal name="attoForm" property="impugnazione.descTipoProp" value="">						
								<td style="width:15%"><form:label path="impugnazione.parte.id" >
										<spring:message code="ricorsoAppello.parte" />
									</form:label>
								</td>
								<c:if test="${sessionScope.procedimento.tipoProcedimento.id==17}" >
									<td style="width:30%"><form:select id="impugnazione.parte" path="impugnazione.parte.id" size="1" cssStyle="width: 200px;" disabled="true">
											<form:option value="-1" label="------Seleziona tipo parte--------------" />
											<form:option value="I" label="Amministratore" />
											<form:option value="A" label="Difensore" />
											<form:option value="E" label="Delegato" />
										</form:select>
										</td>								
								</c:if>
								<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=17}" >
									<td style="width:30%"><form:select id="impugnazione.parte" path="impugnazione.parte.id" size="1" cssStyle="width: 200px;" disabled="true">
											<form:option value="-1" label="------Seleziona tipo parte--------------" />
											<form:option value="D" label="Proposto" />
											<form:option value="T" label="Terzo interessato" />
											<form:option value="A" label="Difensore" />
											<form:option value="E" label="Delegato" />
											<c:if test="${intervenienti==true}" >
												<form:option value="N" label="Interveniente" />
											</c:if>												
										</form:select>
										</td>
								</c:if>
								<logic:equal name="attoForm" property="impugnazione.parte.id" value="D">
								</tr>
								<tr>					
									<td colspan = "4">																					
									 <display:table class="tb_sotto_lista" uid="soggettiDiretti" name="${soggettiDiretti}">																			
										<display:column title="Diretti Interessati" style="width:80%">
											<c:out value="${soggettiDiretti.descrizioneSoggetto} "></c:out>
										</display:column>										
									</display:table>
									</td>	
								</logic:equal>
      							<logic:equal name="attoForm" property="impugnazione.parte.id" value="N">
						       </tr>
								<tr>								
								<td colspan = "4">																					
									 <display:table class="tb_sotto_lista" uid="soggettiDiretti" name="${soggettiDiretti}">																			
										<display:column title="Intervenienti" style="width:80%">
											<c:out value="${soggettiDiretti.descrizioneSoggetto} "></c:out>
										</display:column>										
									</display:table>
									</td>	
								</logic:equal>									
								<logic:equal name="attoForm" property="impugnazione.parte.id" value="T">
								</tr>
								<tr>					
									<td colspan = "4">										
									 <display:table class="tb_sotto_lista" uid="soggettiTitolari" name="${soggettiTitolari}">																			
										<display:column title="Soggetti Titolari" style="width:80%">
											<c:out value="${soggettiTitolari.descrizioneSoggetto} "></c:out>
										</display:column>										
									</display:table>	
								 </td>
								</logic:equal>	
								<logic:equal name="attoForm" property="impugnazione.parte.id" value="A">
								<td style="width:1%">&nbsp;
								</td>						
								<td style="width:30%">
									<logic:notEmpty name="attoForm" property="impugnazione.descCognAvv">
									<c:out value="${attoForm.impugnazione.descCognAvv}"></c:out>&nbsp;
									<c:out value="${attoForm.impugnazione.descNomeAvv}"></c:out>&nbsp;
									</logic:notEmpty>
									<logic:empty name="attoForm" property="impugnazione.descCognAvv">
									<form:select path="impugnazione.avvocatiDifensori.id" size="1" disabled="true" >	
										<form:option value="-1" label="------Avvocati Difensori-------"/>
										<form:options items="${avvocatiDifensori}" itemValue="id" itemLabel="descrizione" /> 																	
									</form:select>
									</logic:empty>
								</td>	
							   </tr>
								<tr>					
									<td colspan = "4">										
									<display:table class="tb_sotto_lista" uid="soggettiDifensori" name="${soggettiDifensori}">																				
										<display:column title="Nell'interesse di" style="width:80%;align:left">
											<c:out value="${soggettiDifensori.descrizione} "></c:out>
										</display:column>										
									</display:table>									
								   </td>
								</logic:equal>
								<logic:equal name="attoForm" property="impugnazione.parte.id" value="E">
								<td style="width:1%">&nbsp;
								</td>						
								<td style="width:30%">
									<logic:notEmpty name="attoForm" property="impugnazione.descCognAvv">
									<c:out value="${attoForm.impugnazione.descCognAvv}"></c:out>&nbsp;
									<c:out value="${attoForm.impugnazione.descNomeAvv}"></c:out>&nbsp;
									</logic:notEmpty>
									<logic:empty name="attoForm" property="impugnazione.descCognAvv">
									<form:select path="impugnazione.avvocatiDifensori.id" size="1" disabled="true" >	
										<form:option value="-1" label="------Avvocati Difensori-------"/>
										<form:options items="${avvocatiDifensori}" itemValue="id" itemLabel="descrizione" /> 																	
									</form:select>
									</logic:empty>
								</td>	
							   </tr>
								<tr>					
									<td colspan = "4">										
									<display:table class="tb_sotto_lista" uid="soggettiDifensori" name="${soggettiDifensori}">																				
										<display:column title="Soggetti" style="width:80%">
											<c:out value="${soggettiDifensori.descrizione} "></c:out>
										</display:column>										
									</display:table>									
								   </td>
								</logic:equal>
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
                             <td style="width:15%"><form:label path="impugnazione.cognome" >
									<spring:message code="ricorsoAppello.cognome" />
								</form:label>
							</td>
							<td style="width:30%">
                              <form:input path="impugnazione.cognome" maxlength="25" size="25" id="impugnazione.cognome" disabled="true" />
                             </td>
							<td style="width:15%"><form:label path="impugnazione.nome" >
									<spring:message code="ricorsoAppello.nome" />
								</form:label>
							</td>
							<td style="width:30%">
                              <form:input path="impugnazione.nome" maxlength="25" size="25" id="impugnazione.nome" disabled="true"/>
                             </td>
                        </tr> 
				</c:if>
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
			</table>
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>
				<c:if test="${attoForm.statoAtto.id != 3 && attoForm.statoAtto.id != 4}">
					<tr style="display:none">
						<td><form:label path="destinatario.id">
								<spring:message code="atto.destinazione" />
							</form:label></td>
						<td><c:if test="${attoForm.statoAtto.id != 3 && attoForm.statoAtto.id != 4}">
								<table>
									<c:forEach var="destinat" items="${ufficiList}">
										<tr>
											<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
										</tr>
									</c:forEach>
								</table>
							</c:if></td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</c:if>
				<c:if test="${attoForm.statoAtto.id == 3 || attoForm.statoAtto.id == 4}">
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
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
				</c:if>
			</table>
			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<smp:notificheButtons formId="id_formConfermaInvia" codAtto="${attoForm.codAtto}"></smp:notificheButtons>
					<td class="td_tasto">
					  <c:if test="${attoForm.statoAtto.id == 3}">
					  <%-- F.C. MEV ID 2022 - 5 - Sitmp Cassazione--%>
			            <c:if test="${profilo.idTipoUfficio==6 && procedimento.tipoProcedimento.id != 8 && procedimento.tipoProcedimento.id != 13 && procedimento.tipoProcedimento.id != 14 && procedimento.tipoProcedimento.id != 15}">					                
						 <c:if test="${attoForm.pulsanteInviaRicorsoInCassazione == 'S'}">
							<input id="InviaRicorsoInCassazione" type="button" value=" Invia in Cassazione " onClick="inviaRicorsoInCassazione()"/>
						 </c:if>
						</c:if>	
					 </c:if>						
						<c:if test="${fromMenuAtti=='elenco'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')"/>
							&nbsp;			        							
						</c:if> 
						<c:if test="${fromMenuAtti=='ordine'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')"/>
							&nbsp;							
						</c:if> 
						<c:if test="${fromMenuAtti=='nuovo'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')"/>
						</c:if>															
					</td>				
				</tr>
			</table>
		</form:form>
		
		<script  type="text/javascript">
			function inviaRicorsoInCassazione() {
				$("#id_formConfermaInvia").attr("action", contextPath+"/procedimento/atto/inviaRicorsoInCassazione.htm");
				$("#id_formConfermaInvia").submit();
			}
		</script>		
		
				<script type="text/javascript">

			$(document).ready(function() {
	
				disabilitaTitolariEBeniSeTipMisPers();
	
			});

			function pulisciFiltri() {

				if(document.getElementsByName("tipoMisure.id").length != 0)
					document.getElementsByName("tipoMisure.id")[0].selectedIndex = -1;
				if(document.getElementsByName("soggettiDirettiFiltro.id").length != 0)
					document.getElementsByName("soggettiDirettiFiltro.id")[0].selectedIndex = -1;
				if(document.getElementsByName("soggettiTitolariFiltro.id").length != 0)
					document.getElementsByName("soggettiTitolariFiltro.id")[0].selectedIndex = -1;
				if(document.getElementsByName("tipologiaBene.id").length != 0)
					document.getElementsByName("tipologiaBene.id")[0].selectedIndex = -1;
				if(document.getElementsByName("categoriaBene.id").length != 0)
					document.getElementsByName("categoriaBene.id")[0].selectedIndex = -1;
				if(document.getElementsByName("sottoCategoriaBene.id").length != 0)
					document.getElementsByName("sottoCategoriaBene.id")[0].selectedIndex = -1;
			}

			function abilitaFiltri() {
				$('#soggettiTitolariFiltro').removeAttr('disabled');
				$('#tipologiaBene').removeAttr('disabled');
				$('#categoriaBene').removeAttr('disabled');
				$('#sottoCategoriaBene').removeAttr('disabled');
			}

			function disabilitaTitolariEBeniSeTipMisPers() {

				var misPersSel = false;
				$('#tipoMisure :selected').each(function(i, selected) {
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

			function disabilitaFiltri() {
				if(document.getElementsByName("soggettiTitolariFiltro.id").length != 0)
					document.getElementsByName("soggettiTitolariFiltro.id")[0].selectedIndex = -1;
				    document.getElementsByName("soggettiTitolariFiltro.id")[0].disabled = true;
				if(document.getElementsByName("tipologiaBene.id").length != 0)
					document.getElementsByName("tipologiaBene.id")[0].selectedIndex = -1;
				if(document.getElementsByName("categoriaBene.id").length != 0)
					document.getElementsByName("categoriaBene.id")[0].selectedIndex = -1;
				if(document.getElementsByName("sottoCategoriaBene.id").length != 0)
					document.getElementsByName("sottoCategoriaBene.id")[0].selectedIndex = -1;

				$('#soggettiTitolariFiltro').attr('disabled', true);
				$('#tipologiaBene').attr('disabled', true);
				$('#categoriaBene').attr('disabled', true);
				$('#sottoCategoriaBene').attr('disabled', true);
			}
		</script>
		
	</tiles:putAttribute>
</tiles:insertDefinition>