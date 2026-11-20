<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:set var="codiKIdBene" />
<c:set var="codiKIdAtto" />
<c:set var="codiKIdTipoInt" />
<c:set var="codiKIdSoggInt" />

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/soggettiInteressati/dettaglio.htm" />
		<form:form method="post" action="${azione}" commandName="soggettoForm">
			<p class="titolo_page">
				<spring:message code="soggettogiuridico.dettaglio" />
			</p>
			<logic:present name="procedimento">
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				<br />
			</logic:present>
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4">
						<c:out value="${soggettoForm.descrizioneSoggetto}" />
					</td>
				</tr>
			</table>
						
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">

				<c:if test="${provenienzaVersione==null}">
					<logic:notPresent name="titoloDiInteresse">
						<tr>
							<td><form:label path="ipotesiMisura">
									<spring:message code="procedimento.ipotesiMisura" />
								</form:label></td>
							<td><c:out value="${soggettoForm.ipotesiMisura.descrizione }" /></td>
							<td><form:label path="tipologiaPrevenzione">
									<spring:message code="procedimento.tipologiaPrevenzione" />
								</form:label></td>
							<td><c:out value="${soggettoForm.tipologiaPrevenzione.descrizione }" /></td>
						</tr>
						<tr>
							<td><form:label path="normativa">
									<spring:message code="procedimento.normativa" />
								</form:label></td>
							<td colspan="3"><c:out value="${soggettoForm.normativa.descrizione }" /></td>
						</tr>
					</logic:notPresent>
				</c:if>					
				
				<tr>
					<td><form:label path="ragioneSociale">
							<spring:message code="soggettogiuridico.ragioneSociale" />
						</form:label></td>
					<td><c:out value="${soggettoForm.ragioneSociale}" /></td>

					<td><form:label path="partitaIva">
							<spring:message code="soggettogiuridico.partitaIva" />
						</form:label></td>
					<td><c:out value="${soggettoForm.partitaIvaCompleta}" /></td>
				</tr>
				<tr>
					<td><form:label path="tipoSocieta">
							<spring:message code="soggettogiuridico.tipoSocieta" />
						</form:label></td>
					<td><c:out value="${soggettoForm.tipoSocieta}" /></td>
					<td><form:label path="numeroCCIAA">
							<spring:message code="soggettogiuridico.numeroCCIAA" />
						</form:label></td>
					<td><c:out value="${soggettoForm.numeroCCIAA}" /></td>
				</tr>
				
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettogiuridico.titolo1" /></td>
				</tr>
				<tr>
					<td><form:label path="statoSedeLegale.descrizione">
							<spring:message code="stato" />
						</form:label></td>
					<td><c:out value="${soggettoForm.statoSedeLegale.descrizione}" /></td>

					<td><form:label path="comuneSedeLegale.descrizione">
							<spring:message code="comune" />
						</form:label></td>
					<td><c:out value="${soggettoForm.descrizioneComuneSedeLegaleItalianoEstero}" /></td>
				</tr>
				<tr>
					<td><form:label path="indirizzoSedeLegale.toponimo">
							<spring:message code="indirizzo" />
						</form:label></td>
					<td><c:out value="${soggettoForm.indirizzoSedeLegale.toponimo.descrizione}" /> <c:out value="${soggettoForm.indirizzoSedeLegale.denominazione}" /></td>
					<td><form:label path="indirizzoSedeLegale.civico">
							<spring:message code="civico" />
						</form:label></td>
					<td colspan="3"><c:out value="${soggettoForm.indirizzoSedeLegale.civico}" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo6" /></td>
				</tr>
				<tr>
					<td><form:label path="statoDomicilioEletto.descrizione">
							<spring:message code="stato" />
						</form:label></td>
					<td><c:out value="${soggettoForm.statoDomicilioEletto.descrizione}" /></td>
					<td><form:label path="descrizioneComuneDomicilioElettoItalianoEstero">
							<spring:message code="comune" />
						</form:label></td>
					<td><c:out value="${soggettoForm.descrizioneComuneDomicilioElettoItalianoEstero}" /></td>
				</tr>
				<tr>
					<td><form:label path="indirizzoDomicilioEletto">
							<spring:message code="nuovosoggettofisico.indirizzo" />
						</form:label></td>
					<td><c:out value="${soggettoForm.indirizzoDomicilioEletto.toponimo.descrizione}" /> <c:out value="${soggettoForm.indirizzoDomicilioEletto.denominazione}" /></td>
					<td><form:label path="indirizzoDomicilioEletto.civico">
							<spring:message code="civico" />
						</form:label></td>
					<td><c:out value="${soggettoForm.indirizzoDomicilioEletto.civico}" /></td>
				</tr>
				<tr>
					<td><form:label path="pressoIndirizzoDomicilioEletto">
							<spring:message code="presso" />
						</form:label></td>
					<td><c:out value="${soggettoForm.pressoIndirizzoDomicilioEletto}" /></td>
					<td colspan="2"><c:out value="${soggettoForm.pressoIndirizzoDomicilioElettoSpecifica}" /></td>
				</tr>

				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>
				<tr>
					<td><form:label path="rappresentanteSociale">
							<spring:message code="soggettogiuridico.rappresentanteSociale" />
						</form:label></td>
					<td><c:out value="${soggettoForm.rappresentanteSociale}" /></td>

					<td><form:label path="amministratore">
							<spring:message code="soggettogiuridico.amministratore" />
						</form:label></td>
					<td><c:out value="${soggettoForm.amministratore}" /></td>
				</tr>
				<tr>
					<td><form:label path="capitaleSociale">
							<spring:message code="soggettogiuridico.capitaleSociale" />
						</form:label></td>
					<td><c:if test="${soggettoForm.capSocDescr=='int'}">
							<fmt:formatNumber type="number" maxFractionDigits="2" value="${soggettoForm.capitaleSociale}" />
						</c:if> <c:if test="${soggettoForm.capSocDescr=='noInt'}">
							<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${soggettoForm.capitaleSociale}" />
						</c:if></td>
					<td><form:label path="settoreAttivita">
							<spring:message code="soggettogiuridico.settoreAttivita" />
						</form:label></td>
					<td><c:out value="${soggettoForm.settoreAttivita.descrizione}" /></td>
				</tr>
				<tr>
					<td><form:label path="annotazioni">
							<spring:message code="annotazioni" />
						</form:label></td>
					<td colspan="3"><c:out value="${soggettoForm.annotazioni}" /></td>
				</tr>
			</table>

		<br />
		<br />	
		
		<!-- GRATUITO PATROCINIO -->
		<c:if test="${difensoreForm.gratuitoPatrocinio == 'true'}">
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4">
						<spring:message code="soggetto.difensore.gratuitopatrocinio" />
					</td>
				</tr>			
				<tr>
					<td>
						<form:label path="">
							<spring:message code="soggetto.difensore.registroanno"/>
						</form:label>
					</td>
					<td>
						<c:out value="${difensoreForm.annoRegistroGratuitoPatrocinio}" />
					</td>
					<td>
						<form:label path="">
							<spring:message code="procedimento.numero" />
						</form:label>
					</td>
					<td>
						<c:out value="${difensoreForm.numeroRegistroGratuitoPatrocinio}" />
					</td>
				</tr>
				<tr>
					<td>
						<form:label path="">
							<spring:message code="soggetto.difensore.dataistanza" />
						</form:label>
					</td> 
					<td colspan="3">
						<fmt:formatDate value="${difensoreForm.dataIstanzaGratuitoPatrocinio}" type="date" pattern="dd/MM/yyyy" />		
					</td>
				</tr>
				<tr>
					<td>
						<form:label path="">
							<spring:message code="soggetto.difensore.dataprovvedimento" />
						</form:label>
					</td> 
					<td>
						<fmt:formatDate value="${difensoreForm.dataProvvedimentoGratuitoPatrocinio}" type="date" pattern="dd/MM/yyyy" />						
					</td>
					<td>
						<form:label path="">
							<spring:message code="soggetto.difensore.tenoreprovvedimento"/>
						</form:label>
					</td>
		        	<td>
						<c:out value="${difensoreForm.tenoreProvvedimentoGratuitoPatrocinio.id}" />	
		        	</td>
				</tr>
				
				<tr>
					<td>
						<form:label path="">
							<spring:message code="soggetto.difensore.datarevoca" />
						</form:label>
					</td> 
					<td colspan="3">
						<fmt:formatDate value="${difensoreForm.dataRevocaGratuitoPatrocinio}" type="date" pattern="dd/MM/yyyy" />	
					</td>
				</tr>

			</table>	
			
		<br />
		<br />	
									
		</c:if>
		
		<!-- DIFENSORI -->
		<logic:notEmpty name="difensoriAssociati">

			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4">
						<spring:message code="soggetto.difensore.listaDifensoriAssociati"/>
					</td>
				</tr>
			</table>

			<display:table class="tb_lista" name="${difensoriAssociati}" uid="difensoriAssociati" requestURI="${azione}" >
				<display:column property="difensoreForm.descrizioneDifensore" title="Generalità" sortable="false" />
				<display:column property="difensoreForm.descrizioneDomicilio" title="Domicilio Legale" sortable="false" />
				<display:column property="dataNomina" title="Data Nomina" format="{0,date,dd/MM/yyyy}" sortable="false" />
				<display:column property="dataRevoca" title="Data Revoca" format="{0,date,dd/MM/yyyy}" sortable="false" />
				<display:column property="descTipoNomina" title="Tipo Nomina" sortable="false" />
			</display:table>
					
		<br />
		<br />	
							
		</logic:notEmpty>
		
		<table width="100%" class="tb_lista">
			<tr>
				<td class="td_titolo">
					
					<c:if test="${dettaglioSoggetto.TTpTipoPrevProc.descTipoNorm!=null}">
						<b>Tipologia prevenzione: </b><c:out value="${dettaglioSoggetto.TTpTipoPrevProc.TTpTipoPrevenzione.descTipoPrev}" /><b> Normativa: </b><c:out value="${dettaglioSoggetto.TTpTipoPrevProc.descTipoNorm}" />
					</c:if>
				
				</td>
				<tr>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				</tr>
			</tr>
		</table>

		<c:if test="${sessionScope.procedimento.tipoProcedimento != null && 
			(sessionScope.procedimento.tipoProcedimento.id == '1' || sessionScope.procedimento.tipoProcedimento.id == '2' || sessionScope.procedimento.tipoProcedimento.id == '5'
			|| sessionScope.procedimento.tipoProcedimento.id == '6' || sessionScope.procedimento.tipoProcedimento.id == '9' || sessionScope.procedimento.tipoProcedimento.id == '10'
			|| sessionScope.procedimento.tipoProcedimento.id == '11' || sessionScope.procedimento.tipoProcedimento.id == '12' || sessionScope.procedimento.tipoProcedimento.id == '13'
			|| sessionScope.procedimento.tipoProcedimento.id == '16' || sessionScope.procedimento.tipoProcedimento.id == '18'
		)}" >

			<c:set var="index" value="1"/>
	
			<table id="misTable" class="tb_lista">
				<c:if test="${fn:length(misurePatrimoniali) == 0}">
					<thead>
						<tr>
							<th >Atto</th>
							<th>Esito/Tipo misura</th>
							<th>Normativa</th>
							<th>Titolare</th>
							<th>Tipo interesse</th>
							<th>Quota</th>
							<th>Stato del Bene</th>
							<th>Definitività</th>
						</tr>
					</thead>
					<tr>
						<td>Nessun risultato trovato.</td>
					</tr>		
				</c:if>
				<c:forEach items="${misurePatrimoniali}" varStatus="loopStatus" var="mis">
					<c:if test="${codiKIdBene!=mis.CODI_K_ID_BENE}" >
						<thead>
							<tr>
								<th colspan="8" style="font-weight: bolder; background-color: lightgrey; color: #1C548C;">Bene ${mis.CODI_K_ID_BENE}: ${mis.DESCR_BENE}</th>
							</tr>
							<tr>
								<th>Atto</th>
								<th>Esito/Tipo misura
								<c:if test="${sessionScope.procedimento.tipoProcedimento.id==8 || 
									sessionScope.procedimento.tipoProcedimento.id==13 ||
									sessionScope.procedimento.tipoProcedimento.id==14 ||
									sessionScope.procedimento.tipoProcedimento.id==15 ||
									sessionScope.procedimento.tipoProcedimento.id==16 ||
									sessionScope.procedimento.tipoProcedimento.id==17 ||
									sessionScope.procedimento.tipoProcedimento.id==18}" >
									/Richiesta
								</c:if>
								<th>Normativa</th>
								<th>Titolare</th>
								<th>Tipo interesse</th>
								<th>Quota</th>
								<th>Stato del Bene</th>
								<th>Definitività</th>
							</tr>
						</thead>
					</c:if>
					<tr class="${index % 2 == 0 ? 'even' : 'odd'}">
						<td>
							<c:out value="${mis.DESC_CAT_ATTO}" />
							<c:if test="${mis.numeroAtto!=null}">
								<c:out value="${mis.annoAtto}/${mis.numeroAtto}"/>
							</c:if>
							<c:if test="${mis.DATA_DEPOSITO!=null}">
								del <fmt:formatDate value="${mis.DATA_DEPOSITO}" type="date" pattern="dd/MM/yyyy" />		
							</c:if>
						</td>
						<td>
							<c:if test="${mis.DESC_TIPO_MISURA != null}">
								<c:out value="${mis.DESC_TIPO_MISURA}" />
							</c:if> 
							<c:if test="${mis.DESC_ESITO3 != null}">
								<c:out value="${mis.DESC_ESITO3}" />
							</c:if>
							<c:if test="${mis.DESC_ESITO3 == null && mis.DESC_ESITO2 != null }">
								<c:out value="${mis.DESC_ESITO2}" />
							</c:if>
							<c:if test="${mis.DESC_ESITO3 == null && mis.DESC_ESITO2 == null && mis.DESC_ESITO1 != null }">
								<c:out value="${mis.DESC_ESITO1}" />
							</c:if>		
							<c:if test="${mis.richiesta != null}">
								- <c:out value="${mis.richiesta}" />
							</c:if>
						</td>
						<td><c:out value="${mis.DESC_NORMATIVA}" /></td>
						<td><c:out value="${mis.descrizioneSogTit}" /></td>
						<td><c:out value="${mis.DESC_TIPO_INT}" /></td>
						<td>
							<c:if test="${mis.NUME_QUOTA!=null}">
								<c:out value="${mis.NUME_QUOTA}" />%
							</c:if>
							<c:if test="${mis.NUME_FRAZIONE_NUM!=null}">
								<c:out value="${mis.NUME_FRAZIONE_NUM}" />/<c:out value="${mis.NUME_FRAZIONE_DEN}" />
							</c:if>
						</td>
						<td><c:out value="${mis.DESC_TIPO_STATO_BENE}" /></td>
						<td><fmt:formatDate value="${mis.DATA_DEFINITIVITA}" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>
					<c:set var="index" value="${index+1}"/>	
					<c:set var="codiKIdBene" value="${mis.CODI_K_ID_BENE}" />
	
				</c:forEach>	
			</table>	
			
		</c:if>			
		
		<c:if test="${procedimenti!=null}">
		
			<p class="titolo_page">
				<spring:message code="procedimento.riepilogoSoggettoTribunale" />
			</p>
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4">
						<c:out value="${soggettoForm.descrizioneSoggetto}" />
					</td>
				</tr>
			</table>
				
			<!-- INTESTAZIONE PROCEDIMENTO -->		
			<table class="tb_lista">
				
				<tr>
					<th></th>
					<th>Procedimento</th>
					<th>Tipo Fascicolo</th>
					<th>Data Iscrizione</th>
					<th>Stato</th>
					<c:if test="${sessionScope.profilo.idTipoUfficio==17}">
							<th>Proc.Tribunale</th>
							<th>Proc.CdA</th>
							<th>Proc.PG</th>
					</c:if>
					<c:if test="${sessionScope.profilo.idTipoUfficio==8}">					
						<th>Proc.Procura</th>
						<th>Proc.Tribunale</th>
						<th>Proc.CdA</th>						
					</c:if>
					<c:if test="${sessionScope.profilo.idTipoUfficio==19}">
						<th>Proc.Procura</th>
						<th>Proc.CdA</th>										
						<th>Proc.PG</th>
					</c:if>
					<c:if test="${sessionScope.profilo.idTipoUfficio==6}">
						<th>Proc.Procura</th>
						<th>Proc.Tribunale</th>										
						<th>Proc.PG</th>
					</c:if>
					<th>Ruolo</th>
				</tr>			
				<tbody>			
					<c:forEach items="${procedimenti}" var="proc" varStatus="status">
	
						<tr class="even">
							<c:if test="${proc.procedimentoMerito.procedimento.tipoFascicolo.id!=2}">
								<td onclick="apriDettaglio(${status.index})">
									<img id="id_img_dettaglio${status.index}" src="${pageContext.request.contextPath}/images/right_arrow_2.png" height="38" width="38" />
								</td>
							</c:if>	
							<c:if test="${proc.procedimentoMerito.procedimento.tipoFascicolo.id==2}">
								<td>
								</td>
							</c:if>										
							<td>
								<c:out value="${proc.procedimentoMerito.procedimento.annoNumeroProcedimento}" />
							</td>
							<td>
								<c:out value="${proc.procedimentoMerito.procedimento.tipoFascicolo.descrizione}" />
							</td>
							<td>
								<fmt:formatDate value="${proc.procedimentoMerito.procedimento.dataApertura}" type="date" pattern="dd/MM/yyyy" />
							</td>
							<td>
								<c:out value="${proc.procedimentoMerito.procedimento.statoProcedimento.descrizione}" />
							</td>	
							<c:if test="${sessionScope.profilo.idTipoUfficio==17}">
								<td>
									<c:out value="${proc.procedimentoMerito.procedimento.NUM_TRIBUNALE}" />
								</td>
								<td>
									<c:out value="${proc.procedimentoMerito.procedimento.NUM_CDA}" />
								</td>
								<td>
									<c:out value="${proc.procedimentoMerito.procedimento.NUM_PG}" />
								</td>
							</c:if>
							<c:if test="${sessionScope.profilo.idTipoUfficio==8}">					
								<td>
									<c:out value="${proc.procedimentoMerito.procedimento.NUM_PROCURA}" />
								</td>
								<td>
									<c:out value="${proc.procedimentoMerito.procedimento.NUM_TRIBUNALE}" />
								</td>
								<td>
									<c:out value="${proc.procedimentoMerito.procedimento.NUM_CDA}" />
								</td>						
							</c:if>
							<c:if test="${sessionScope.profilo.idTipoUfficio==19}">
								<td>
									<c:out value="${proc.procedimentoMerito.procedimento.NUM_PROCURA}"  />
								</td>
								<td>
									<c:out value="${proc.procedimentoMerito.procedimento.NUM_CDA}" />
								</td>											
								<td>
									<c:out value="${proc.procedimentoMerito.procedimento.NUM_PG}" />
								</td>
							</c:if>
							<c:if test="${sessionScope.profilo.idTipoUfficio==6}">
								<td>		
									<c:out value="${proc.procedimentoMerito.procedimento.NUM_PROCURA}" />
								</td>
								<td>		
									<c:out value="${proc.procedimentoMerito.procedimento.NUM_TRIBUNALE}" />											
								</td>
								<td>		
									<c:out value="${proc.procedimentoMerito.procedimento.NUM_PG}" />
								</td>
							</c:if>
							<td>		
								<c:out value="${proc.dettaglioSoggetto.TTpTipoRuolo.descRuolo}" />
							</td>							
						</tr>
					
						<!-- DETTAGLIO DEL SOGGETTO NEL PROCEDIMENTO -->
						<tr id="dettaglio${status.index}" style="display: none" >
						
							<td colspan="9">
								
								<table width="100%">
									<!-- INIZIO DETTAGLIO RIGA DETTAGLIO PROCEDIMENTO -->
									<tr>
										<td colspan="3">
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</td>
									</tr>
									<tr>
										<td>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</td>									
										<td class="td_titolo" colspan="2">
											
											<c:if test="${proc.dettaglioSoggetto.TTpTipoPrevProc.descTipoNorm!=null}">
												<b>Tipologia prevenzione:</b> <c:out value="${proc.dettaglioSoggetto.TTpTipoPrevProc.TTpTipoPrevenzione.descTipoPrev}" /><b> Normativa: </b><c:out value="${proc.dettaglioSoggetto.TTpTipoPrevProc.descTipoNorm}" />
											</c:if>
										
										</td>
									</tr>
									
									<tr>
										<!-- RIENTRO -->
										<td>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</td>
										<td colspan="2">
											
											<c:if test="${soggettoForm.tipoSoggetto=='F'}">
												</br>
													
												<!-- MISURE PERSONALI -->
												<table class="tb_form">
													<tr>
														<td class="td_titolo" colspan="4">
															<spring:message code="atti.datadefinitivita.misurePersonali"/>
														</td>
													</tr>
												</table>
												
												<display:table class="tb_sotto_lista" name="${proc.procedimentoMerito.misurePersonali}" uid="mis" requestURI="${azione}" >
													<display:column title="Atto" sortable="false" style="width:30%;" >
														${mis.relAttoMisPers.TAtto.TTpCatAtto.descCatAtto}
														<c:if test="${mis.numeroAtto!=null}">
															<c:out value="${mis.annoAtto}/${mis.numeroAtto}"/>
														</c:if>
														<c:if test="${mis.relAttoMisPers.TAtto.dataDeposito!=null}">
															del <fmt:formatDate value="${mis.relAttoMisPers.TAtto.dataDeposito}" type="date" pattern="dd/MM/yyyy" />		
														</c:if>
													</display:column>
													
													<!-- 
														<display:column property="relAttoMisPers.TTpTipoMisura.descTipoMisura" title="Tipo misura" sortable="false" />
														<display:column property="relAttoMisPers.TAnNormative.descNormativa" title="Normativa" sortable="false" /> 
													-->
													<display:column title="Durata" sortable="false">
														<c:if test="${mis.relAttoMisPers.numeDurataAnni>0}">
															${mis.relAttoMisPers.numeDurataAnni} anni
														</c:if>
														<c:if test="${mis.relAttoMisPers.numeDurataMesi>0}">
															${mis.relAttoMisPers.numeDurataMesi} mesi
														</c:if>  
														<c:if test="${mis.relAttoMisPers.numeDurataGiorni>0}">
															${mis.relAttoMisPers.numeDurataGiorni} giorni
														</c:if>  					
													</display:column>
													<display:column title="Esito" sortable="false">
														<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito!=null}">
															${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito}
														</c:if>
														<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito!=null}">
															${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito}
														</c:if>
														<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito1.descEsito!=null}">
															${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito1.descEsito}
														</c:if>
													</display:column>
													<display:column property="relAttoMisPers.TDefPers.dataDefinitivita" title="Definitività" sortable="false" format="{0,date,dd/MM/yyyy}" />
												</display:table>
												
	
												<br />	
										
												<!-- CAUZIONE -->
												<table class="tb_form">
													<tr>
														<td class="td_titolo" colspan="4">
															<spring:message code="soggetto.misurapersonale.cauzione"/>
														</td>
													</tr>
												</table>
										
												<display:table class="tb_sotto_lista" name="${proc.procedimentoMerito.cauzioniSoggetto}" uid="mis" requestURI="${azione}" >
													<display:column title="Atto" sortable="false" style="width:30%;" >
														${mis.relAttoMisPers.TAtto.TTpCatAtto.descCatAtto}
														<c:if test="${mis.numeroAtto!=null}">
															<c:out value="${mis.annoAtto}/${mis.numeroAtto}"/>
														</c:if>
														<c:if test="${mis.relAttoMisPers.TAtto.dataDeposito!=null}">
															del <fmt:formatDate value="${mis.relAttoMisPers.TAtto.dataDeposito}" type="date" pattern="dd/MM/yyyy" />		
														</c:if>
													</display:column>
													<display:column property="relAttoMisPers.TCauzione.impoCauzione" title="Importo" sortable="false" />
													<display:column title="Esito" sortable="false">
														<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito!=null}">
															${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito}
														</c:if>
														<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito!=null}">
															${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito}
														</c:if>
														<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito1.descEsito!=null}">
															${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito1.descEsito}
														</c:if>
													</display:column>
													<display:column property="relAttoMisPers.TDefPers.dataDefinitivita" title="Definitività" sortable="false" format="{0,date,dd/MM/yyyy}" />
												</display:table>
											
	
												<br />
											
												<!-- ART.67 -->
												<table class="tb_form">
													<tr>
														<td class="td_titolo" colspan="4">
															<spring:message code="soggetto.misurapersonale.applicazioneart67"/>
														</td>
													</tr>
												</table>
										
												<display:table class="tb_sotto_lista" name="${proc.procedimentoMerito.art67Soggetto}" uid="mis" requestURI="${azione}" >
													<display:column title="Atto" sortable="false" style="width:30%;" >
														${mis.relAttoMisPers.TAtto.TTpCatAtto.descCatAtto}
														<c:if test="${mis.numeroAtto!=null}">
															<c:out value="${mis.annoAtto}/${mis.numeroAtto}"/>
														</c:if>
														<c:if test="${mis.relAttoMisPers.TAtto.dataDeposito!=null}">
															del <fmt:formatDate value="${mis.relAttoMisPers.TAtto.dataDeposito}" type="date" pattern="dd/MM/yyyy" />		
														</c:if>
													</display:column>
													<display:column title="Esito" sortable="false">
														<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito!=null}">
															${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito}
														</c:if>
														<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito!=null}">
															${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito}
														</c:if>
														<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito1.descEsito!=null}">
															${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito1.descEsito}
														</c:if>
													</display:column>
													<display:column property="relAttoMisPers.TDefPers.dataDefinitivita" title="Definitività" sortable="false" format="{0,date,dd/MM/yyyy}" />
												</display:table>
									
											</c:if>
											
											<br />		
											
											<table class="tb_form">
												<tr>
													<td class="td_titolo" colspan="4">
														<spring:message code="atti.datadefinitivita.misurePatrimoniali"/>
													</td>
												</tr>
											</table>
									
											<!-- MISURE PATRIMONIALI -->
											<c:set var="index" value="1"/>	
											<c:set var="codiKIdBene" value="0" />
									
											<table id="misTable" class="tb_sotto_lista">
												<c:if test="${fn:length(proc.procedimentoMerito.misurePatrimoniali) == 0}">
													<thead>
														<tr>
															<th style="width:30%;">Atto</th>
															<th>Esito/Tipo misura</th>
															<th>Normativa</th>
															<th>Titolare</th>
															<th>Tipo interesse</th>
															<th>Quota</th>
															<th>Stato del Bene</th>
															<th>Definitività</th>
														</tr>
													</thead>
													<tr>
														<td colspan="8">Nessun risultato trovato.</td>
													</tr>		
												</c:if>
												
												<c:forEach items="${proc.procedimentoMerito.misurePatrimoniali}" varStatus="loopStatus" var="mis">
													<c:if test="${codiKIdBene!=mis.CODI_K_ID_BENE}" >
														<thead>
															<tr>
																<th colspan="8" style="font-weight: bolder; background-color: lightgrey; color: #1C548C;">Bene ${mis.CODI_K_ID_BENE}: ${mis.DESCR_BENE}</th>
															</tr>
															<tr>
																<th style="width:30%;">Atto</th>
																<th>Esito/Tipo misura</th>
																<th>Normativa</th>
																<th>Titolare</th>
																<th>Tipo interesse</th>
																<th>Quota</th>
																<th>Stato del Bene</th>
																<th>Definitività</th>
															</tr>
														</thead>
													</c:if>
													<tr class="${index % 2 == 0 ? 'even' : 'odd'}">
														<td>
															<c:out value="${mis.DESC_CAT_ATTO}" />
															<c:if test="${mis.numeroAtto!=null}">
																<c:out value="${mis.annoAtto}/${mis.numeroAtto}"/>
															</c:if>
															<c:if test="${mis.DATA_DEPOSITO!=null}">
																del <fmt:formatDate value="${mis.DATA_DEPOSITO}" type="date" pattern="dd/MM/yyyy" />		
															</c:if>
														</td>
														<td>
															<c:if test="${mis.DESC_TIPO_MISURA != null}">
																<c:out value="${mis.DESC_TIPO_MISURA}" />
															</c:if> 
															<c:if test="${mis.DESC_ESITO3 != null}">
																<c:out value="${mis.DESC_ESITO3}" />
															</c:if>
															<c:if test="${mis.DESC_ESITO3 == null && mis.DESC_ESITO2 != null }">
																<c:out value="${mis.DESC_ESITO2}" />
															</c:if>
															<c:if test="${mis.DESC_ESITO3 == null && mis.DESC_ESITO2 == null && mis.DESC_ESITO1 != null }">
																<c:out value="${mis.DESC_ESITO1}" />
															</c:if>
														</td>
														<td><c:out value="${mis.DESC_NORMATIVA}" /></td>
														<td><c:out value="${mis.descrizioneSogTit}" /></td>
														<td><c:out value="${mis.DESC_TIPO_INT}" /></td>
														<td>
															<c:if test="${mis.NUME_QUOTA!=null}">
																<c:out value="${mis.NUME_QUOTA}" />%
															</c:if>
															<c:if test="${mis.NUME_FRAZIONE_NUM!=null}">
																<c:out value="${mis.NUME_FRAZIONE_NUM}" />/<c:out value="${mis.NUME_FRAZIONE_DEN}" />
	    													</c:if>
														</td>
														<td><c:out value="${mis.DESC_TIPO_STATO_BENE}" /></td>
														<td><fmt:formatDate value="${mis.DATA_DEFINITIVITA}" type="date" pattern="dd/MM/yyyy" /></td>
													</tr>
													<c:set var="index" value="${index+1}"/>	
													<c:set var="codiKIdBene" value="${mis.CODI_K_ID_BENE}" />
												</c:forEach>	
											</table>		
										</td>
									</tr>
									<!-- FINE RIGA DETTAGLIO MERITO -->
									
									<!-- INIZIO DETTAGLIO RIGA AGGRAVAMENTO -->	
									<c:forEach items="${proc.procedimentiAggravamento}" var="procAggr" varStatus="status">			
										<tr>
											<!--  RIENTRO -->
											<td>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>		
											<td>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											<td>
												<!-- INTESTAZIONE PROCEDIMENTO AGGRAVAMENTO -->		
												<table class="tb_lista">
													<tr>
														<th class="titolo" colspan="6">Procedimento di Aggravamento</th>
													</tr>						
													<tr>
														<th>Procedimento</th>
														<th>Data Iscrizione</th>
														<th>Stato</th>
														<c:if test="${sessionScope.profilo.idTipoUfficio==17}">
																<th>Proc.Tribunale</th>
																<th>Proc.CdA</th>
																<th>Proc.PG</th>
														</c:if>
														<c:if test="${sessionScope.profilo.idTipoUfficio==8}">					
															<th>Proc.Procura</th>
															<th>Proc.Tribunale</th>
															<th>Proc.CdA</th>						
														</c:if>
														<c:if test="${sessionScope.profilo.idTipoUfficio==19}">
															<th>Proc.Procura</th>
															<th>Proc.CdA</th>										
															<th>Proc.PG</th>
														</c:if>
														<c:if test="${sessionScope.profilo.idTipoUfficio==6}">
															<th>Proc.Procura</th>
															<th>Proc.Tribunale</th>										
															<th>Proc.PG</th>
														</c:if>
													</tr>			
													<tbody>			
														<tr>
															<td>
																<c:out value="${procAggr.procedimento.annoNumeroProcedimento}" />
															</td>
															<td>
																<fmt:formatDate value="${procAggr.procedimento.dataApertura}" type="date" pattern="dd/MM/yyyy" />
															</td>
															<td>
																<c:out value="${procAggr.procedimento.statoProcedimento.descrizione}" />
															</td>	
															<c:if test="${sessionScope.profilo.idTipoUfficio==17}">
																<td>
																	<c:out value="${procAggr.procedimento.NUM_TRIBUNALE}" />
																</td>
																<td>
																	<c:out value="${procAggr.procedimento.NUM_CDA}" />
																</td>
																<td>
																	<c:out value="${procAggr.procedimento.NUM_PG}" />
																</td>
															</c:if>
															<c:if test="${sessionScope.profilo.idTipoUfficio==8}">					
																<td>
																	<c:out value="${procAggr.procedimento.NUM_PROCURA}" />
																</td>
																<td>
																	<c:out value="${procAggr.procedimento.NUM_TRIBUNALE}" />
																</td>
																<td>
																	<c:out value="${procAggr.procedimento.NUM_CDA}" />
																</td>						
															</c:if>
															<c:if test="${sessionScope.profilo.idTipoUfficio==19}">
																<td>
																	<c:out value="${procAggr.procedimento.NUM_PROCURA}"  />
																</td>
																<td>
																	<c:out value="${procAggr.procedimento.NUM_CDA}" />
																</td>											
																<td>
																	<c:out value="${procAggr.procedimento.NUM_PG}" />
																</td>
															</c:if>
															<c:if test="${sessionScope.profilo.idTipoUfficio==6}">
																<td>		
																	<c:out value="${procAggr.procedimento.NUM_PROCURA}" />
																</td>
																<td>		
																	<c:out value="${procAggr.procedimento.NUM_TRIBUNALE}" />											
																</td>
																<td>		
																	<c:out value="${procAggr.procedimento.NUM_PG}" />
																</td>
															</c:if>
														</tr>
													
														<!-- MISURE SOGGETTO NEL PROCEDIMENTO -->
														<tr>
															<td colspan="6">
																</br>
																<table width="100%">
																	<tr>
																		<td>
																			<!-- MISURE PERSONALI -->
																			<table class="tb_form">
																				<tr>
																					<td class="td_titolo" colspan="4">
																						<spring:message code="atti.datadefinitivita.misurePersonali"/>
																					</td>
																				</tr>
																			</table>
																			
																			<display:table class="tb_sotto_lista" name="${procAggr.misurePersonali}" uid="mis" requestURI="${azione}" >
																				<display:column title="Atto" sortable="false" style="width:30%;" >
																					${mis.relAttoMisPers.TAtto.TTpCatAtto.descCatAtto}
																					<c:if test="${mis.numeroAtto!=null}">
																						<c:out value="${mis.annoAtto}/${mis.numeroAtto}"/>
																					</c:if>
																					<c:if test="${mis.relAttoMisPers.TAtto.dataDeposito!=null}">
																						del <fmt:formatDate value="${mis.relAttoMisPers.TAtto.dataDeposito}" type="date" pattern="dd/MM/yyyy" />		
																					</c:if>
																					<c:if test="${mis.richiesta!=null}">
																					- Misura applicata
																					</c:if>
																				</display:column>
																				<display:column property="relAttoMisPers.TTpTipoMisura.descTipoMisura" title="Tipo misura" sortable="false" />
																				<display:column property="relAttoMisPers.TAnNormative.descNormativa" title="Normativa" sortable="false" />
																				<display:column title="Durata" sortable="false">
																					<c:if test="${mis.relAttoMisPers.numeDurataAnni>0}">
																						${mis.relAttoMisPers.numeDurataAnni} anni
																					</c:if>
																					<c:if test="${mis.relAttoMisPers.numeDurataMesi>0}">
																						${mis.relAttoMisPers.numeDurataMesi} mesi
																					</c:if>  
																					<c:if test="${mis.relAttoMisPers.numeDurataGiorni>0}">
																						${mis.relAttoMisPers.numeDurataGiorni} giorni
																					</c:if>  					
																				</display:column>
																				<display:column title="Esito/Richiesta" sortable="false">
																					<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito!=null}">
																						${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito}
																					</c:if>
																					<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito!=null}">
																						${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito}
																					</c:if>
																					<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito1.descEsito!=null}">
																						${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito1.descEsito}
																					</c:if>
																					<c:if test="${mis.richiesta!=null}">
																						- ${mis.richiesta}
																					</c:if>
																				</display:column>
																				<display:column property="relAttoMisPers.TDefPers.dataDefinitivita" title="Definitività" sortable="false" format="{0,date,dd/MM/yyyy}" />
																			</display:table>
																			
																			<br />	
																		</td>
																	</tr>	
																</table>	
															</td>							
														</tr>
													</tbody>
												</table>	
											</td>
										</tr>
									</c:forEach>	
									<!-- FINE RIGA DETTAGLIO AGGRAVAMENTO -->
									
									<!-- INIZIO DETTAGLIO RIGA ISTANZE CAMERALI  -->	
									<c:forEach items="${proc.procedimentiCamera}" var="procCamere" varStatus="status">			
										<tr>
											<!--  RIENTRO -->
											<td>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>		
											<td>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</td>
											<td>
												<!-- INTESTAZIONE PROCEDIMENTO ISTANZE CAMERALI -->		
												<table class="tb_lista">
													<tr>
														<th class="titolo" colspan="6">
															<c:if test="${procCamere.procedimento.tipoProcedimento != null && procCamere.procedimento.tipoProcedimento.id == '13' }">
																<spring:message code="procedimento.titoloModificaPatrimoniali" /> 
															</c:if>
															<c:if test="${procCamere.procedimento.tipoProcedimento != null && procCamere.procedimento.tipoProcedimento.id == '14' }">
																<spring:message code="procedimento.titoloModificaPersonali" />
															</c:if>
															<c:if test="${procCamere.procedimento.tipoProcedimento != null && procCamere.procedimento.tipoProcedimento.id == '16' }">
																<spring:message code="procedimento.titoloModificaPermessi" /> 
															</c:if>
															<c:if test="${procCamere.procedimento.tipoProcedimento != null && procCamere.procedimento.tipoProcedimento.id == '17' }">
																<p class="titolo_page"><spring:message code="procedimento.titoloAmministratore" /></p>
															</c:if>
															<c:if test="${procCamere.procedimento.tipoProcedimento != null && procCamere.procedimento.tipoProcedimento.id == '18' }">
																<p class="titolo_page"><spring:message code="procedimento.terzoInterveniente" /></p>
															</c:if>					
															<c:if test="${procCamere.procedimento.tipoProcedimento != null && procCamere.procedimento.tipoProcedimento.id == '15' }">
																<spring:message code="procedimento.titoloModificaCauzioni" /> 
															</c:if>
															<c:if test="${procCamere.procedimento.tipoProcedimento != null && procCamere.procedimento.tipoProcedimento.id == '19' }">
																<p class="titolo_page"><spring:message code="procedimento.titoloCorrezione" /></p>
															</c:if>	
														</th>
													</tr>						
													<tr>
														<th>Procedimento</th>
														<th>Data Iscrizione</th>
														<th>Stato</th>
														<c:if test="${sessionScope.profilo.idTipoUfficio==17}">
																<th>Proc.Tribunale</th>
																<th>Proc.CdA</th>
																<th>Proc.PG</th>
														</c:if>
														<c:if test="${sessionScope.profilo.idTipoUfficio==8}">					
															<th>Proc.Procura</th>
															<th>Proc.Tribunale</th>
															<th>Proc.CdA</th>						
														</c:if>
														<c:if test="${sessionScope.profilo.idTipoUfficio==19}">
															<th>Proc.Procura</th>
															<th>Proc.CdA</th>										
															<th>Proc.PG</th>
														</c:if>
														<c:if test="${sessionScope.profilo.idTipoUfficio==6}">
															<th>Proc.Procura</th>
															<th>Proc.Tribunale</th>										
															<th>Proc.PG</th>
														</c:if>
													</tr>			
													<tbody>			
														<tr>
															<td>
																<c:out value="${procCamere.procedimento.annoNumeroProcedimento}" />
															</td>
															<td>
																<fmt:formatDate value="${procCamere.procedimento.dataApertura}" type="date" pattern="dd/MM/yyyy" />
															</td>
															<td>
																<c:out value="${procCamere.procedimento.statoProcedimento.descrizione}" />
															</td>	
															<c:if test="${sessionScope.profilo.idTipoUfficio==17}">
																<td>
																	<c:out value="${procCamere.procedimento.NUM_TRIBUNALE}" />
																</td>
																<td>
																	<c:out value="${procCamere.procedimento.NUM_CDA}" />
																</td>
																<td>
																	<c:out value="${procCamere.procedimento.NUM_PG}" />
																</td>
															</c:if>
															<c:if test="${sessionScope.profilo.idTipoUfficio==8}">					
																<td>
																	<c:out value="${procCamere.procedimento.NUM_PROCURA}" />
																</td>
																<td>
																	<c:out value="${procCamere.procedimento.NUM_TRIBUNALE}" />
																</td>
																<td>
																	<c:out value="${procCamere.procedimento.NUM_CDA}" />
																</td>						
															</c:if>
															<c:if test="${sessionScope.profilo.idTipoUfficio==19}">
																<td>
																	<c:out value="${procCamere.procedimento.NUM_PROCURA}"  />
																</td>
																<td>
																	<c:out value="${procCamere.procedimento.NUM_CDA}" />
																</td>											
																<td>
																	<c:out value="${procCamere.procedimento.NUM_PG}" />
																</td>
															</c:if>
															<c:if test="${sessionScope.profilo.idTipoUfficio==6}">
																<td>		
																	<c:out value="${procCamere.procedimento.NUM_PROCURA}" />
																</td>
																<td>		
																	<c:out value="${procCamere.procedimento.NUM_TRIBUNALE}" />											
																</td>
																<td>		
																	<c:out value="${procCamere.procedimento.NUM_PG}" />
																</td>
															</c:if>
														</tr>
													
														<!-- MISURE SOGGETTO NEL PROCEDIMENTO -->
														<tr>
															<td colspan="6">
																</br>
																<table width="100%">
																	<tr>
																		<td>
																			<!-- MISURE PERSONALI -->
																			<table class="tb_form">
																				<tr>
																					<td class="td_titolo" colspan="4">
																						<spring:message code="atti.datadefinitivita.misurePersonali"/>
																					</td>
																				</tr>
																			</table>
	
																		<c:if test="${procCamere.procedimento.tipoProcedimento != null && 
																			(procCamere.procedimento.tipoProcedimento.id == '14' || 
																				procCamere.procedimento.tipoProcedimento.id == '15' ||
																					procCamere.procedimento.tipoProcedimento.id == '16')}" >
																																
																				<display:table class="tb_sotto_lista" name="${procCamere.misurePersonali}" uid="mis" requestURI="${azione}" >
																					<display:column title="Atto" sortable="false" style="width:30%;" >
																						${mis.relAttoMisPers.TAtto.TTpCatAtto.descCatAtto}
																						<c:if test="${mis.numeroAtto!=null}">
																							<c:out value="${mis.annoAtto}/${mis.numeroAtto}"/>
																						</c:if>
																						<c:if test="${mis.relAttoMisPers.TAtto.dataDeposito!=null}">
																							del <fmt:formatDate value="${mis.relAttoMisPers.TAtto.dataDeposito}" type="date" pattern="dd/MM/yyyy" />		
																						</c:if>
																						<c:if test="${mis.richiesta!=null}">
																						- Misura applicata
																						</c:if>
																					</display:column>
																					<display:column property="relAttoMisPers.TTpTipoMisura.descTipoMisura" title="Tipo misura" sortable="false" />
																					<display:column property="relAttoMisPers.TAnNormative.descNormativa" title="Normativa" sortable="false" />
																					<display:column title="Durata" sortable="false">
																						<c:if test="${mis.relAttoMisPers.numeDurataAnni>0}">
																							${mis.relAttoMisPers.numeDurataAnni} anni
																						</c:if>
																						<c:if test="${mis.relAttoMisPers.numeDurataMesi>0}">
																							${mis.relAttoMisPers.numeDurataMesi} mesi
																						</c:if>  
																						<c:if test="${mis.relAttoMisPers.numeDurataGiorni>0}">
																							${mis.relAttoMisPers.numeDurataGiorni} giorni
																						</c:if>  					
																					</display:column>
																					<display:column title="Esito/Richiesta" sortable="false">
																						<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito!=null}">
																							${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito}
																						</c:if>
																						<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito!=null}">
																							${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito}
																						</c:if>
																						<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito1.descEsito!=null}">
																							${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito1.descEsito}
																						</c:if>
																						<c:if test="${mis.richiesta!=null}">
																							- ${mis.richiesta}
																						</c:if>
																					</display:column>
																					<display:column property="relAttoMisPers.TDefPers.dataDefinitivita" title="Definitività" sortable="false" format="{0,date,dd/MM/yyyy}" />
																				</display:table>
																				
																				<br />	
																		
																				<!-- CAUZIONE -->
																				<table class="tb_form">
																					<tr>
																						<td class="td_titolo" colspan="4">
																							<spring:message code="soggetto.misurapersonale.cauzione"/>
																						</td>
																					</tr>
																				</table>
																		
																				<display:table class="tb_sotto_lista" name="${procCamere.cauzioniSoggetto}" uid="mis" requestURI="${azione}" >
																					<display:column title="Atto" sortable="false" style="width:30%;" >
																						${mis.relAttoMisPers.TAtto.TTpCatAtto.descCatAtto}
																						<c:if test="${mis.numeroAtto!=null}">
																							<c:out value="${mis.annoAtto}/${mis.numeroAtto}"/>
																						</c:if>
																						<c:if test="${mis.relAttoMisPers.TAtto.dataDeposito!=null}">
																							del <fmt:formatDate value="${mis.relAttoMisPers.TAtto.dataDeposito}" type="date" pattern="dd/MM/yyyy" />		
																						</c:if>
																					</display:column>
																					<display:column property="relAttoMisPers.TCauzione.impoCauzione" title="Importo" sortable="false" />
																					<display:column title="Esito" sortable="false">
																						<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito!=null}">
																							${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito}
																						</c:if>
																						<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito!=null}">
																							${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito}
																						</c:if>
																						<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito1.descEsito!=null}">
																							${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito1.descEsito}
																						</c:if>
																					</display:column>
																					<display:column property="relAttoMisPers.TDefPers.dataDefinitivita" title="Definitività" sortable="false" format="{0,date,dd/MM/yyyy}" />
																				</display:table>
																			
									
																				<br />
																			
																				<!-- ART.67 -->
																				<table class="tb_form">
																					<tr>
																						<td class="td_titolo" colspan="4">
																							<spring:message code="soggetto.misurapersonale.applicazioneart67"/>
																						</td>
																					</tr>
																				</table>
																		
																				<display:table class="tb_sotto_lista" name="${procCamere.art67Soggetto}" uid="mis" requestURI="${azione}" >
																					<display:column title="Atto" sortable="false" style="width:30%;" >
																						${mis.relAttoMisPers.TAtto.TTpCatAtto.descCatAtto}
																						<c:if test="${mis.numeroAtto!=null}">
																							<c:out value="${mis.annoAtto}/${mis.numeroAtto}"/>
																						</c:if>
																						<c:if test="${mis.relAttoMisPers.TAtto.dataDeposito!=null}">
																							del <fmt:formatDate value="${mis.relAttoMisPers.TAtto.dataDeposito}" type="date" pattern="dd/MM/yyyy" />		
																						</c:if>
																					</display:column>
																					<display:column title="Esito" sortable="false">
																						<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito!=null}">
																							${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito}
																						</c:if>
																						<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito!=null}">
																							${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito}
																						</c:if>
																						<c:if test="${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito3.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito2.descEsito==null && mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito1.descEsito!=null}">
																							${mis.relAttoMisPers.TTpEsitoByCodiFkIdEsito1.descEsito}
																						</c:if>
																					</display:column>
																					<display:column property="relAttoMisPers.TDefPers.dataDefinitivita" title="Definitività" sortable="false" format="{0,date,dd/MM/yyyy}" />
																				</display:table>
																				
																			</c:if>																				
																		
																			<br />		
	
																			<c:if test="${procCamere.procedimento.tipoProcedimento != null && 
																			(procCamere.procedimento.tipoProcedimento.id == '13' || 
																				procCamere.procedimento.tipoProcedimento.id == '18')}" >
																																								
																				<table class="tb_form">
																					<tr>
																						<td class="td_titolo" colspan="4">
																							<spring:message code="atti.datadefinitivita.misurePatrimoniali"/>
																						</td>
																					</tr>
																				</table>
																		
																				<!-- MISURE PATRIMONIALI -->
																				<c:set var="index" value="1"/>	
																				<c:set var="codiKIdBene" value="0" />
																		
																				<table id="misTable" class="tb_sotto_lista">
																					<c:if test="${fn:length(procCamere.misurePatrimoniali) == 0}">
																						<thead>
																							<tr>
																								<th style="width:30%;">Atto</th>
																								<th>Esito/Tipo misura</th>
																								<th>Normativa</th>
																								<th>Titolare</th>
																								<th>Tipo interesse</th>
																								<th>Quota</th>
																								<th>Stato del Bene</th>
																								<th>Definitività</th>
																							</tr>
																						</thead>
																						<tr>
																							<td colspan="8">Nessun risultato trovato.</td>
																						</tr>		
																					</c:if>
																					
																					<c:forEach items="${procCamere.misurePatrimoniali}" varStatus="loopStatus" var="mis">
																						<c:if test="${codiKIdBene!=mis.CODI_K_ID_BENE}" >
																							<thead>
																								<tr>
																									<th colspan="8" style="font-weight: bolder; background-color: lightgrey; color: #1C548C;">Bene ${mis.CODI_K_ID_BENE}: ${mis.DESCR_BENE}</th>
																								</tr>
																								<tr>
																									<th style="width:30%;">Atto</th>
																									<th>Esito/Tipo misura/Richiesta</th>
																									<th>Normativa</th>
																									<th>Titolare</th>
																									<th>Tipo interesse</th>
																									<th>Quota</th>
																									<th>Stato del Bene</th>
																									<th>Definitività</th>
																								</tr>
																							</thead>
																						</c:if>
																						<tr class="${index % 2 == 0 ? 'even' : 'odd'}">
																							<td>
																								<c:out value="${mis.DESC_CAT_ATTO}" />
																								<c:if test="${mis.numeroAtto!=null}">
																									<c:out value="${mis.annoAtto}/${mis.numeroAtto}"/>
																								</c:if>
																								<c:if test="${mis.DATA_DEPOSITO!=null}">
																									del <fmt:formatDate value="${mis.DATA_DEPOSITO}" type="date" pattern="dd/MM/yyyy" />		
																								</c:if>
																								<c:if test="${mis.richiesta!=null}">
																								- Misura applicata
																								</c:if>
																							</td>
																							<td>
																								<c:if test="${mis.DESC_TIPO_MISURA != null}">
																									<c:out value="${mis.DESC_TIPO_MISURA}" />
																								</c:if> 
																								<c:if test="${mis.DESC_ESITO3 != null}">
																									<c:out value="${mis.DESC_ESITO3}" />
																								</c:if>
																								<c:if test="${mis.DESC_ESITO3 == null && mis.DESC_ESITO2 != null }">
																									<c:out value="${mis.DESC_ESITO2}" />
																								</c:if>
																								<c:if test="${mis.DESC_ESITO3 == null && mis.DESC_ESITO2 == null && mis.DESC_ESITO1 != null }">
																									<c:out value="${mis.DESC_ESITO1}" />
																								</c:if>
																								<c:if test="${mis.richiesta != null}">
																									- <c:out value="${mis.richiesta}" />
																								</c:if>
																							</td>
																							<td><c:out value="${mis.DESC_NORMATIVA}" /></td>
																							<td><c:out value="${mis.descrizioneSogTit}" /></td>
																							<td><c:out value="${mis.DESC_TIPO_INT}" /></td>
																							<td>
																								<c:if test="${mis.NUME_QUOTA!=null}">
																									<c:out value="${mis.NUME_QUOTA}" />%
																								</c:if>
																								<c:if test="${mis.NUME_FRAZIONE_NUM!=null}">
																									<c:out value="${mis.NUME_FRAZIONE_NUM}" />/<c:out value="${mis.NUME_FRAZIONE_DEN}" />
										    													</c:if>
																							</td>
																							<td><c:out value="${mis.DESC_TIPO_STATO_BENE}" /></td>
																							<td><fmt:formatDate value="${mis.DATA_DEFINITIVITA}" type="date" pattern="dd/MM/yyyy" /></td>
																						</tr>
																						<c:set var="index" value="${index+1}"/>	
																						<c:set var="codiKIdBene" value="${mis.CODI_K_ID_BENE}" />
																					</c:forEach>	
																				</table>	
																				
																			</c:if>
		
																		</td>
																	</tr>	
																</table>	
															</td>							
														</tr>
													</tbody>
												</table>	
											</td>
										</tr>
									</c:forEach>	
									<!-- FINE RIGA DETTAGLIO ISTANZE CAMERALI -->									
								</table>
									
							</td>							
						</tr>
					</c:forEach>	
				</tbody>
			</table>
			
		</c:if>					
		
		<br />		
		<br />
		<br />		
		
		<table class="tb_bottoni" align="center">
			<tr>
				<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
			</tr>
		</table>
		
		<br />
		<br />
		
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
		<smp:back buttonId="backButton"></smp:back>
		
	</tiles:putAttribute>
</tiles:insertDefinition>

<script type="text/javascript">

	function apriDettaglio(index) {
		var idS = 'dettaglio'.concat(index);
		var row = document.getElementById(idS);
		var display = row.style.display;

		if(display == 'none') {
			
			$("#id_img_dettaglio"+index).rotate(90);

			row.style.display = '';
			
		} else {
			
			$("#id_img_dettaglio"+index).rotate(0);

			row.style.display = 'none';			
		}
	
	}

</script>