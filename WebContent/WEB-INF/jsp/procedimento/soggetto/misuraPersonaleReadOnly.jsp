<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/soggetto/misuraPersonale.htm" />
		<p class="titolo_page">
			<c:if test="${procedimento.tipoProcedimento.id != 16}"> 
				<spring:message code="soggetto.misurapersonale" />
			</c:if>	
			<c:if test="${procedimento.tipoProcedimento.id == 16}"> 
				<spring:message code="soggetto.decisione.permesso" />		
			</c:if>	
		</p>
		<form:form method="post" action="${azione}" commandName="soggettoForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			
			<logic:present name="listaRicorso">
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

											    <c:if test="${procedimento.tipoProcedimento.id!='16'}">												
													<c:if test="${bene.misura.durata != ''}">
														<c:out value="${bene.misura.tipoMisura.descrizione} (${bene.misura.durata})"></c:out>
													</c:if>
													<c:if test="${bene.misura.durata == ''}">
														<c:out value="${bene.misura.tipoMisura.descrizione}"></c:out>
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

			<br />
		
			<br />
			<table class="tb_form" border="0">
				<logic:present name="esiti">
					<tr>
						<td><form:label path="misura.decisioneMisuraPersonale.id">
								<spring:message code="soggetto.misurapersonale.esito" />
							</form:label></td>
						<td colspan="3"><c:out value="${soggettoForm.misura.decisioneMisuraPersonale.descrizione}" /></td>
					</tr>
				</logic:present>
				<tr>
					<td><form:label path="tipologiaPrevenzione.id">
							<spring:message code="procedimento.tipologiaPrevenzione" />
						</form:label></td>
					<td><c:out value="${soggettoForm.tipologiaPrevenzione.descrizione}" /></td>
					<td><form:label path="normativa.id">
							<spring:message code="procedimento.normativa" />
						</form:label></td>
					<td><c:out value="${soggettoForm.normativa.descrizione}" /></td>
				</tr>
				<tr>
					<td><form:label path="misura.tipoMisura">
							<spring:message code="soggetto.misurapersonale" />
						</form:label></td>
					<td><c:out value="${soggettoForm.misura.tipoMisura.descrizione}" /></td>
					<td><form:label path="misura.normativaDiRiferimento">
							<spring:message code="procedimento.normativa" />
						</form:label></td>
					<td><c:out value="${soggettoForm.misura.normativaDiRiferimento.descrizione}" /></td>
				</tr>
				<tr>
					<td><form:label path="misura.localita">
							<spring:message code="soggetto.misurapersonale.localita" />
						</form:label></td>
					<td colspan="3"><form:textarea path="misura.localita" rows="2" cssStyle="width:98%" readonly="true" /></td>
				</tr>
				<tr>
					<td><form:label path="misura.anni">
							<spring:message code="soggetto.misurapersonale.durata.generica" />
						</form:label></td>
					<td colspan="3"><c:if test="${soggettoForm.misura.anni>0}">
							<c:if test="${soggettoForm.misura.anni==1}">
								<c:out value="${soggettoForm.misura.anni}" /> anno
								</c:if>
							<c:if test="${soggettoForm.misura.anni>1}">
								<c:out value="${soggettoForm.misura.anni}" /> anni
								</c:if>
						</c:if> <c:if test="${soggettoForm.misura.mesi>0}">
							<c:if test="${soggettoForm.misura.mesi==1}">
								<c:out value="${soggettoForm.misura.mesi}" /> mese
								</c:if>
							<c:if test="${soggettoForm.misura.mesi>1}">
								<c:out value="${soggettoForm.misura.mesi}" /> mesi
								</c:if>
						</c:if> <c:if test="${soggettoForm.misura.giorni>0}">
							<c:if test="${soggettoForm.misura.giorni==1}">
								<c:out value="${soggettoForm.misura.giorni}" /> giorno
								</c:if>
							<c:if test="${soggettoForm.misura.giorni>1}">
								<c:out value="${soggettoForm.misura.giorni}" /> giorni
								</c:if>
						</c:if></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.prescrizioni" /></td>
				</tr>


				<c:forEach var="prescrizione" items="${listaPrescrizioni}">
					<tr>
						<td colspan="4"><form:checkbox path="misura.idSelezionati" value="${prescrizione.id}" label="${prescrizione.descrizione}" disabled="true" /></td>
					</tr>
				</c:forEach>
				<tr>
					<td><form:label path="misura.ulterioriPrescrizioni">
							<spring:message code="soggetto.misurapersonale.ulterioriprescrizioni" />
						</form:label></td>
					<td colspan="3"><form:textarea path="misura.ulterioriPrescrizioni" id="note" rows="2" cssStyle="width:98%" readonly="true" /></td>
				</tr>

				<logic:present name="listaVecchiePrescrizioni">
					<c:forEach var="vecchiePrescrizioni" items="${listaVecchiePrescrizioni}">
						<tr>
							<td colspan="4"><form:checkbox path="misura.idVecchiePrescrizioni" value="${vecchiePrescrizioni.id}" label="${vecchiePrescrizioni.descrizione}" checked="checked" disabled="true" /></td>
						</tr>
						<c:if test="${(vecchiePrescrizioni.id == '13')}">
							<tr>
								<td><form:label path="misura.ulterioriPrescrizioniVecchie">
										<spring:message code="soggetto.misurapersonale.ulterioriprescrizioni" />
									</form:label></td>
								<td colspan="3"><form:textarea path="misura.ulterioriPrescrizioniVecchie" id="note" disabled="true" rows="2" cssStyle="width:98%" readonly="readonly" /></td>
							</tr>
						</c:if>
					</c:forEach>
				</logic:present>

				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.cauzione" /></td>
				</tr>
				<tr>
					<td><form:label path="misura.cauzione">
							<spring:message code="soggetto.misurapersonale.cauzione" />
						</form:label></td>
					<td colspan="3"><form:radiobutton path="misura.cauzione" value="S" disabled="true" /> <form:label path="misura.cauzione">
							<spring:message code="si" />
						</form:label> <form:radiobutton path="misura.cauzione" value="N" disabled="true" /> <form:label path="misura.cauzione">
							<spring:message code="no" />
						</form:label></td>
				</tr>
				<logic:present name="importoCauzione">
					<tr>
						<td><form:label path="misura.importo">
								<spring:message code="soggetto.misurapersonale.cauzioneimporto" />
							</form:label></td>
						<td colspan="3"><c:out value="${soggettoForm.misura.importo}" /></td>
					</tr>
					<tr>
						<td><form:label path="misura.note">
								<spring:message code="soggetto.misurapersonale.note" />
							</form:label></td>
						<td colspan="3"><c:out value="${soggettoForm.misura.note}" /></td>
					</tr>
				</logic:present>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.misurapersonale.articolo67" /></td>
				</tr>
				<tr>
					<td><form:label path="misura.art67">
							<spring:message code="soggetto.misurapersonale.applicazioneart67" />
						</form:label></td>
					<td colspan="3"><form:radiobutton path="misura.art67" value="S" disabled="true" /> <form:label path="misura.art67">
							<spring:message code="si" />
						</form:label> <form:radiobutton path="misura.art67" value="N" disabled="true" /> <form:label path="misura.art67">
							<spring:message code="no" />
						</form:label></td>
				</tr>
			</table>
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<logic:notPresent name="indietroSogInt">
						<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/atto/copertinaAtto.htm')"></td>
					</logic:notPresent>
					<logic:present name="indietroSogInt">
						<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/soggetto/visualizzaDirettiInteressati.htm')"></td>
					</logic:present>
				</tr>
			</table>
		</form:form>

	</tiles:putAttribute>
</tiles:insertDefinition>
