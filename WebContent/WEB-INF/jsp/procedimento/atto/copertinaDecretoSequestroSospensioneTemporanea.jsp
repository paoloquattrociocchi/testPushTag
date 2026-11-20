<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

<%
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/dettaglioAtto.htm" />
		<form:form method="post" id="id_formConfermaInvia" action="${azione}" commandName="attoForm">
			<p class="titolo_page">
				<spring:message code="atto.dettaglio" />
			</p>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<logic:notPresent name="listaDecreti">
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />

			<c:if test="${attoForm.statoAtto.id==3}">
				<br />
				<form:hidden path="codAtto" />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>

					<c:if test="${attoForm.statoAtto.id != 3}">
						<tr>
							<td><form:label path="dataEmissione" cssClass="required">
									<spring:message code="atto.dataemissione" />
								</form:label></td>
							<td><form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" /> <form:errors path="dataEmissione" class="testoRosso" /></td>
							<td><form:label path="dataDeposito" cssClass="required">
									<spring:message code="atto.datadeposito" />
								</form:label></td>
							<td><form:input path="dataDeposito" maxlength="10" id="id_dataDeposito" /> <form:errors path="dataDeposito" class="testoRosso" /></td>
						</tr>
						<tr style="display:none">
							<td><logic:notEmpty name="ufficiListFromFlussiOut">
									<form:label path="destinatario.id">
										<spring:message code="atto.destinazione" />
									</form:label>
								</logic:notEmpty></td>
							<td><logic:notEmpty name="ufficiListFromFlussiOut">
									<table>
										<c:forEach var="destinat" items="${ufficiListFromFlussiOut}">
											<tr>
												<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
											</tr>
										</c:forEach>
									</table>
								</logic:notEmpty></td>
							<td><form:label path="dataRegistrazione">
									<spring:message code="atto.dataregistrazione" />
								</form:label></td>
							<td><fmt:formatDate value="<%=new java.util.Date() %>" type="date" pattern="dd/MM/yyyy" /></td>
						</tr>
					</c:if>
					<c:if test="${attoForm.statoAtto.id == 3}">
						<tr>
							<td><form:label path="dataEmissione">
									<spring:message code="atto.dataemissione" />
								</form:label></td>
							<td><fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataEmissione" /></td>
							<td><form:label path="dataDeposito">
									<spring:message code="atto.datadeposito" />
								</form:label></td>
							<td><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /></td>
						</tr>
						<tr>
							<td><logic:notEmpty name="ufficiListFromFlussiOut">
									<form:label path="destinatario.id">
										<spring:message code="atto.destinazione" />
									</form:label>
								</logic:notEmpty></td>
							<td><logic:notEmpty name="ufficiListFromFlussiOut">
									<table>
										<c:forEach var="destinat" items="${ufficiListFromFlussiOut}">
											<tr>
												<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
											</tr>
										</c:forEach>
									</table>
								</logic:notEmpty></td>
							<td><form:label path="dataRegistrazione">
									<spring:message code="atto.dataregistrazione" />
								</form:label></td>
							<td><fmt:formatDate value="${attoForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /></td>
						</tr>
					</c:if>
				</table>
			</c:if>
			</logic:notPresent>
		</form:form>
		
		<logic:notPresent name="listaDecreti">
		<c:if test="${attoForm.statoAtto.id!='3'}">

			<spring:url var="azioneRotate" value="/procedimento/atto/rotateListaBeniNoTitDir.htm" />
			<form:form method="post" action="${azioneRotate}" commandName="beneForm">
				<logic:notEmpty name="listaBeneQuotaZero">
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td width="40px" class="td_titolo_label" height="5px" title="visualizza/nasconde l'elenco dei beni che superano il 100%"><img id="id_img_listaBeniQuoteZero" src="${pageContext.request.contextPath}/images/right_arrow_2.png" height="38"
								width="38" /></td>
							<td class="td_titolo_label"><p class="testoRosso">
									<spring:message code="bene.titolodiritto.quote.zero" />
								</p></td>
						</tr>
					</table>
					<div id="id_div_listaBeniQuoteZero" style="display: none;">
						<display:table class="tb_lista" uid="bene" name="${listaBeneQuotaZero}" pagesize="10" requestURI="${paginazione}">
							<display:column property="idBene" title="Identificativo" sortable="false" />
							<display:column property="descrizioneBene" title="Descrizione" sortable="false" />
						</display:table>
					</div>
				</logic:notEmpty>
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
						<display:table class="tb_lista" uid="listaBeniNoTitDir" name="${listaBeniNoTitDir}" pagesize="10" requestURI="${azioneRotate}">
							<display:column property="idBene" title="Identificativo" sortable="false" />
							<display:column property="descrizioneBene" title="Descrizione" sortable="false" />
						</display:table>
					</div>
				</logic:notEmpty>

				<logic:notEmpty name="listaBeniNoMisPatr">
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td width="40px" class="td_titolo_label" height="5px" title="visualizza/nasconde l'elenco dei beni senza misure patrimoniale"><img id="id_img_listaBeniNoMisPatr" src="${pageContext.request.contextPath}/images/right_arrow_2.png"
								height="38" width="38" /></td>
							<td class="td_titolo_label"><p class="testoRosso">
									<spring:message code="bene.no.misuarapatrimoniale.lista" />
								</p></td>
						</tr>
					</table>
					<div id="id_div_listaBeniNoMisPatr" style="display: none;">
						<display:table class="tb_lista" uid="listaBeniNoMisPatr" name="${listaBeniNoMisPatr}" pagesize="10" requestURI="${azioneRotate}">
							<display:column property="idBene" title="Identificativo" sortable="false" />
							<display:column property="descrizioneBene" title="Descrizione" sortable="false" />
						</display:table>
					</div>
				</logic:notEmpty>
				<br />
			</form:form>

			<logic:present name="direttiInteressatiSenzaDifensore">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="40px" class="td_titolo_label" height="5px" title="apre/chiude l'elenco dei diretti interessati senza difensore"><img id="id_img_direttiInteressatiSenzaDifensore" src="${pageContext.request.contextPath}/images/right_arrow_2.png"
							height="38" width="38" /></td>
						<td class="td_titolo_label"><p class="testoRosso">
								<spring:message code="atto.udienza.sogint.nodifensore" />
							</p></td>
					</tr>
				</table>
				<div id="id_div_direttiInteressatiSenzaDifensore" style="display: none;">
					<display:table class="tb_lista" uid="direttiInteressatiSenzaDifensore" name="${direttiInteressatiSenzaDifensore}" pagesize="10" requestURI="${azione}">
						<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" style="width: 50%" />
					</display:table>
				</div>
			</logic:present>

			<spring:url var="azione1" value="/procedimento/atto/beneAssociatoADecretoSequestroSospensioneTemporanea.htm" />
			<form:form method="post" action="${azione1}" commandName="beneForm">

				<br />
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="bene.benidelatto" /></td>
					</tr>
				</table>
				<display:table class="tb_lista" uid="elemRisultatiRicerca" name="${risultatiRicerca}" pagesize="10" requestURI="${azione1}">
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:radiobutton path="idBene" value="${elemRisultatiRicerca.bene.idBene}" />
					</display:column>
					<display:column property="bene.idBene" title="Identificativo" sortable="false" />
					<display:column property="bene.descrizioneBene" title="Descrizione" sortable="false" />
					<display:column title="Misura - Titolari di diritto">
						<display:table class="tb_sotto_lista" uid="soggetto" name="${elemRisultatiRicerca.soggetti}" requestURI="${azione1}">
							<display:column property="misura.tipoMisura.descrizione" title="" />
							<display:column property="normativa.descrizione" title="" />
							<display:column property="descrizioneSoggetto" title="" />
							<display:column property="tipoInteresse.descrizione" title="" />
							<display:column property="descrizioneFrazioneOQuota" title="" />
						</display:table>
					</display:column>
				</display:table>

				<br />

				<table class="tb_bottoni" align="center">
					<logic:notEmpty name="risultatiRicerca">
						<tr>
						<c:if test="${profilo.ruolo!='MAGISTRATO'}">
							<td class="td_tasto"><input type="submit" value="Elimina" name="Elimina"></td>
						</c:if>
						</tr>
					</logic:notEmpty>
				</table>
			</form:form>
			<br />

			<spring:url var="azione2" value="/procedimento/atto/associaBeneADecretoSequestroSospensioneTemporanea.htm" />
			<form:form method="post" action="${azione2}" commandName="attoForm">

				<input type="hidden" id="id_selectAll_listaIdCheckbox" value="true" />
				<br />
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4">
						    <form:checkbox path="visualizzaFiltro" onclick="this.form.submit()"/>
							<spring:message code="bene.filtrosuibeni" />
	                	</td>
	                </tr>
					<logic:equal name="attoForm" property="visualizzaFiltro" value="true">
						<tr><td>
						<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
							<tr>					
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
									<form:label path="tipologiaBene.id"><spring:message code="ricorsoAppello.beni" /></form:label>
								</td>
							</tr>
							<tr>&nbsp;</tr>
											
							<tr>											
								<td>
									<form:select path="soggettiDiretti.id" size="1" onchange="this.form.submit()"> 
										<form:option value="-1" label="---- Seleziona Soggetto Diretto ----" />
										<form:options items="${soggettiDiretti}" itemValue="id" itemLabel="descrizione" />
									</form:select>
								</td>
								<form:hidden path="soggettiDiretti.descrizione"/>						
								<td>
									<form:select path="soggettiTitolari.id" size="1" onchange="this.form.submit()"> 
										<form:option value="-1" label="---- Seleziona Soggetto Titolare ----" />
										<form:options items="${soggettiTitolari}" itemValue="id" itemLabel="descrizione" />
									</form:select>
								</td>
								<form:hidden path="soggettiTitolari.descrizione"/>						
								<td>
									<table>
										<tr>
											<td><form:label path="tipologiaBene.id"><spring:message code="bene.tipologia" /></form:label></td>
											<td  colspan="3"><form:select id="tipologiaBene" path="tipologiaBene.id" size="1" cssStyle="width: 350px;" onchange="this.form.submit()">
													<form:option value="-1" label="------Seleziona Tipologia-------" />
													<form:options items="${tipologie}" itemValue="id" itemLabel="descrizione" />
												</form:select></td>
											<form:hidden path="tipologiaBene.descrizione"/>
										</tr>
										<tr>
											<td><form:label path="categoriaBene.id">
													<spring:message code="bene.categoria" />
												</form:label>
											</td>
											<td colspan="3"><form:select id="categoriaBene" path="categoriaBene.id" size="1" cssStyle="width: 350px;" onchange="this.form.submit()">
													<form:option value="-1" label="------Seleziona Categoria-------" />
													<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
												</form:select>
												<form:hidden path="categoriaBene.descrizione"/>
											</td>
										</tr>
										<tr>
											<td><form:label path="sottoCategoriaBene.id"><spring:message code="bene.sottocategoria" /></form:label></td>
											<td colspan="3"><form:select id="sottoCategoriaBene" path="sottoCategoriaBene.id" size="1" cssStyle="width: 350px;" onchange="this.form.submit()">
													<form:option value="-1" label="------Seleziona Sottocategoria-------" />
													<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
												</form:select>
											</td>
											<form:hidden path="sottoCategoriaBene.descrizione"/>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						</td></tr>
						<tr><td>
						<table class="tb_bottoni" align="center">
							<tr>
								<td class="td_tasto"><input type="button" title="Pulisci filtri di ricerca" value="Pulisci" onclick="pulisciFiltri(); submit();" class="nowait" /></td>
							</tr>
						</table>
						</td></tr>
					<br/>
					</logic:equal>
					<br/>
				</table>
				<table class="tb_form">
					<logic:notEmpty name="attoForm" property="criteriFiltro">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="bene.benidelprocedimentofiltrati.associabili" /></td>
						</tr>
					</logic:notEmpty>
					<logic:empty name="attoForm" property="criteriFiltro">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="bene.benidelprocedimento.associabili" /></td>
						</tr>
					</logic:empty>
				</table>

				<display:table class="tb_lista" uid="beni" name="${beni}" pagesize="10" requestURI="${azione2}">

					<display:column paramId="idBene" paramProperty="listaIdCheckbox" style="width:7%" title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('beni','listaIdCheckbox');\" title=\"Seleziona/Deseleziona tutti i beni\"></img>&nbsp;Sel.bene" >						
						<form:checkbox path="listaIdCheckbox" value="${beni.idBene}" />
					</display:column>

					<display:column property="idBene" title="Identificativo del Bene" sortable="false" />
					<display:column property="descrizioneBene" title="Descrizione del Bene" sortable="false" />
				</display:table>

				<br />
				<br />

				<table class="tb_bottoni" align="center">
					<tr>
					<c:if test="${profilo.ruolo!='MAGISTRATO'}">
					<logic:notEmpty name="beni"> 
						<td class="td_tasto"><input type="submit" value="Associa" name="Associa"></td>
					</logic:notEmpty>
					</c:if>
					
						<td class="td_tasto"><c:if test="${fromMenuAtti=='elenco'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
							</c:if> <c:if test="${fromMenuAtti=='ordine'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
							</c:if> <c:if test="${fromMenuAtti=='nuovo'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
							</c:if></td>
					</tr>
				</table>
				<br />
				<c:if test="${attoForm.statoAtto.id != 3 }">
				<c:if test="${profilo.ruolo!='MAGISTRATO'}">
					<table class="tb_bottoni" align="center">
						<tr>
							<td class="td_tasto"><input type="submit" value="Conferma" name="ConfermaInvia"></td>
						</tr>
					</table>
				</c:if>
				</c:if>
				<br />
			</form:form>
		</c:if>

		<!-- se l'atto è registrato -->
		<c:if test="${attoForm.statoAtto.id=='3'}">

			<spring:url var="azioneDR" value="/procedimento/atto/dettaglioAttoRegistrato.htm" />

			<br />

			<spring:url var="azioneR" value="/procedimento/atto/attoRegistrato.htm" />
			<form:form method="post" action="${azioneR}" commandName="beneSoggettoForm" onsubmit="checkParentsIfChildChecked()">
				<input type="hidden" id="id_selectAll_beniProcedimento.idBene" value="true" />

				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="bene.benidelatto" /></td>
					</tr>
				</table>

				<display:table class="tb_lista" uid="elemRisultatiRicerca" name="${risultatiRicerca}" pagesize="10" requestURI="${azioneR}">
				<c:if test="${attoForm.idProcedimentoCdatrib==null || attoForm.idProcedimentoCdatrib==0}" >
					<c:if test="${convalidato == null}">
						<display:column paramId="bene.idBene" paramProperty="beniProcedimento[${elemRisultatiRicerca_rowNum-1}].idBene" style="width:7%" title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('elemRisultatiRicerca','beniProcedimento.idBene');\" title=\"Seleziona/Deseleziona tutti i beni\"></img>&nbsp;Sel.bene" >
							<form:checkbox path="beniProcedimento[${elemRisultatiRicerca_rowNum-1}].idBene" value="${elemRisultatiRicerca.bene.idBene}" onclick="checkSoggetti(this, ${elemRisultatiRicerca_rowNum-1})" />
						</display:column>
					</c:if>
				</c:if>	

					<display:column property="bene.idBene" title="Identificativo" sortable="false" />
					<display:column property="bene.descrizioneBene" title="Descrizione" sortable="false" />
					<display:column title="Misura - Titolari di diritto">
						<display:table class="tb_sotto_lista" uid="soggetto" name="${elemRisultatiRicerca.soggetti}" requestURI="${azioneR}">
							<display:column property="tipologiaPrevenzione.descrizione" title="" />
							<display:column property="misura.tipoMisura.descrizione" title="" />
							<display:column property="descrizioneSoggetto" title="" />
							<display:column property="tipoInteresse.descrizione" title="" />
							<display:column property="descrizioneFrazioneOQuota" title="" />
							<display:column property="dataEsecuzione" title="Esecuzione" format="{0,date,dd/MM/yyyy}" />
							<c:if test="${attoForm.idProcedimentoCdatrib==null || attoForm.idProcedimentoCdatrib==0}" >
							<c:if test="${convalidato == null}">
								<display:column sortable="false" style="text-align: center; width: 5%">
									<form:checkbox path="beniProcedimento[${elemRisultatiRicerca_rowNum-1}].listaIdCheckboxString" value="${soggetto.idSoggetto};${soggetto.tipoInteresse.id}" name="soggettoTitolareDi${elemRisultatiRicerca.bene.idBene}"
										onclick="uncheckParentIfNotAllChildren(${elemRisultatiRicerca_rowNum-1})" />
								</display:column>
							</c:if>
							</c:if>
						</display:table>
					</display:column>
				</display:table>

				<br />
				<br />
				<!-- 
				<c:if test="${profilo.idTipoUfficio!=17 && convalidato == null}">
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="bene.esecuzione" /></td>
						</tr>
						<tr>
							<td colspan="1"><form:label path="soggetto.dataEsecuzione" cssClass="required">
									<spring:message code="bene.dataesecuzione" />
								</form:label></td>
							<td colspan="3"><form:input id="dataEsecuzione" path="soggetto.dataEsecuzione" maxlength="10" size="12" /> <form:errors path="soggetto.dataEsecuzione" class="testoRosso" /></td>
						</tr>
					</table>
				</c:if>

				<br />
				<br />
				 -->
				 <c:if test="${attoForm.idProcedimentoCdatrib==null || attoForm.idProcedimentoCdatrib==0}" >
				<table class="tb_bottoni" align="center">
					<tr>
					<!-- 
						<c:if test="${profilo.idTipoUfficio!=17 && convalidato == null}">
							<td class="td_tasto"><input type="submit" value="Salva" name="Esecuzione"></td>
						</c:if>
 					-->			
 					<c:if test="${profilo.idTipoUfficio==19 || profilo.idTipoUfficio==6}">
                    <c:if test="${profilo.ruolo!='MAGISTRATO' && attoForm.numeAnnoAtto != null && attoForm.numeAtto != null }">
                        <td class="td_tasto">   
                           <input type="button" value="Conservatoria"  name="Conservatoria" onClick="indietro('/conservatorie/predispComunicazioni.htm')">
                        </td>
				    </c:if>
				    </c:if>
						<td class="td_tasto"><c:if test="${fromMenuAtti=='elenco'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
							</c:if> <c:if test="${fromMenuAtti=='ordine'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
							</c:if> <c:if test="${fromMenuAtti=='nuovo'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
							</c:if></td>
					</tr>
				</table>
			</c:if>	
			</form:form>
		</c:if>
		</logic:notPresent>
		
		<logic:present name="listaDecreti">
					<spring:url var="azioneSelDecreto" value="/procedimento/atto/decretoSequestroSospensioneTemporanea.htm" />
					<form:form method="post" action="${azioneSelDecreto}" commandName="attoForm">
						<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
							<tr>
								<td><form:label path="codAttoOrigine">
										<spring:message code="atto.decreto.sequestro" />
									</form:label></td>
								<td>
								<form:select path="codAttoOrigine" size="1">
										<form:option value="-1" label="------Seleziona Decreto-------" />
										<form:options items="${listaDecreti}" itemValue="id" itemLabel="descrizione" />
									</form:select> <form:hidden path="tipoAtto.id" /> <form:hidden path="categoriaAtto.id" />
								</td>
							</tr>
						</table>
						<br />
						<br />
						<table class="tb_bottoni" align="center">
							<tr>
							<smp:notificheButtons formId="id_formConfermaInvia" codAtto="${attoForm.codAtto}"></smp:notificheButtons>
							<c:if test="${profilo.ruolo!='MAGISTRATO'}">
								<td class="td_tasto"><input type="submit" value="Conferma" name="SelezionaDecreto"></td>
							</c:if>
								<td class="td_tasto"><c:if test="${fromMenuAtti=='elenco'}">
								
										<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
									</c:if> <c:if test="${fromMenuAtti=='ordine'}">
										<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
									</c:if> <c:if test="${fromMenuAtti=='nuovo'}">
										<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/nuovo.htm')">
									</c:if></td>
							</tr>
						</table>
					</form:form>
		</logic:present>
	</tiles:putAttribute>
</tiles:insertDefinition>

<script type="text/javascript">

	$(document).ready(function() {
		$("#id_img_direttiInteressatiSenzaDifensore").rotate({     bind:       { 
				mouseover : function() {              
					$(this).css("cursor","pointer"); 								 
				}, 							
				click : function() { 							
					
					if($("#id_div_direttiInteressatiSenzaDifensore").is(":visible")){
						$(this).rotate({animateTo:0});  
						divScomparsa('id_div_direttiInteressatiSenzaDifensore');
					}
					else {
						$(this).rotate({animateTo:90});
						divScomparsa('id_div_direttiInteressatiSenzaDifensore');
					}
				}       
				
			}      
		});
	});

	$(document).ready(function() {
		$("#id_img_listaBeniNoTitDir").rotate({     bind:       { 
				mouseover : function() {              
					$(this).css("cursor","pointer"); 								 
				}, 							
				click : function() { 							
					
					if($("#id_div_listaBeniNoTitDir").is(":visible")){
						$(this).rotate({animateTo:0});  
						divScomparsa('id_div_listaBeniNoTitDir');
					}
					else {
						$(this).rotate({animateTo:90});
						divScomparsa('id_div_listaBeniNoTitDir');
					}
				}       
				
			}      
		});
	});	

	$(document).ready(function() {
		$("#id_img_listaBeniNoMisPatr").rotate({     bind:       { 
				mouseover : function() {              
					$(this).css("cursor","pointer"); 								 
				}, 							
				click : function() { 							
					
					if($("#id_div_listaBeniNoMisPatr").is(":visible")){
						$(this).rotate({animateTo:0});  
						divScomparsa('id_div_listaBeniNoMisPatr');
					}
					else {
						$(this).rotate({animateTo:90});
						divScomparsa('id_div_listaBeniNoMisPatr');
					}
				}       
				
			}      
		});
	});


	$(document).ready(function() {
		$("#id_img_listaBeniQuoteZero").rotate({     bind:       { 
				mouseover : function() {              
					$(this).css("cursor","pointer"); 								 
				}, 							
				click : function() { 							
					
					if($("#id_div_listaBeniQuoteZero").is(":visible")){
						$(this).rotate({animateTo:0});  
						divScomparsa('id_div_listaBeniQuoteZero');
					}
					else {
						$(this).rotate({animateTo:90});
						divScomparsa('id_div_listaBeniQuoteZero');
					}
				}       
				
			}      
		});
	});	
	
	$(document).ready(function() {
		prendiData("#dataEsecuzione",true);
		prendiData("#id_dataDeposito",true);
		prendiData("#id_dataEmissione",true);
	});		

	function enableAll(array) {

		for (var i = 0; i < array.length; i++)
			array[i].disabled = false;
	}

	function disableAll(array) {

		for (var i = 0; i < array.length; i++)
			array[i].disabled = true;
	}

	function uncheckAll(array) {

		for (var i = 0; i < array.length; i++)
			array[i].checked = false;
	}

	function checkAll(array) {

		for (var i = 0; i < array.length; i++)
			array[i].checked = true;
	}

	function checkSoggetti(checkbox, indice) {
		
		var nome = "beniProcedimento[" + indice + "].listaIdCheckboxString";

		var soggettiTitolari = document.getElementsByName(nome);

		if (checkbox.checked) {

			checkAll(soggettiTitolari);
		
		} else {

			uncheckAll(soggettiTitolari);
		}
	}

	function uncheckParentIfNotAllChildren(indice) {
		
		var nomeLista = "beniProcedimento[" + indice + "].listaIdCheckboxString";

		var soggettiTitolari = document.getElementsByName(nomeLista);

		var almenoUnoDisattivato = false;

		for (var i = 0; i < soggettiTitolari.length; i++){
			if (!soggettiTitolari[i].checked){
				almenoUnoDisattivato = true;
				break;
				}
		}

		if (almenoUnoDisattivato){

			var nomePadre = nomeLista.replace("listaIdCheckboxString", "idBene");
			var padre = document.getElementsByName(nomePadre);
			for (var i = 0; i < padre.length; i++){
				padre[i].checked = false;
			}
		}
	}

	function checkParent(checkbox){
		
		var nomeFiglio = checkbox.name;
		var nomePadre = nomeFiglio.replace("listaIdCheckboxString", "idBene");
		var padre = document.getElementsByName(nomePadre);
		for (var i = 0; i < padre.length; i++){
			padre[i].checked = true;
		}
	}

	function checkParentsIfChildChecked() {

		for(var i=0; i<document.getElementsByTagName("input").length; i++){
			if(document.getElementsByTagName("input")[i].type == "checkbox"){
				if(document.getElementsByTagName("input")[i].checked == true){
					checkParent(document.getElementsByTagName("input")[i]);
				}
			}
		}
	}
	function pulisciFiltri() {
		if(document.getElementsByName("soggettiDiretti.id").length != 0)
			document.getElementsByName("soggettiDiretti.id")[0].selectedIndex = -1;
		if(document.getElementsByName("soggettiTitolari.id").length != 0)
			document.getElementsByName("soggettiTitolari.id")[0].selectedIndex = -1;
		if(document.getElementsByName("tipologiaBene.id").length != 0)
			document.getElementsByName("tipologiaBene.id")[0].selectedIndex = -1;
		if(document.getElementsByName("categoriaBene.id").length != 0)
			document.getElementsByName("categoriaBene.id")[0].selectedIndex = -1;
		if(document.getElementsByName("sottoCategoriaBene.id").length != 0)
			document.getElementsByName("sottoCategoriaBene.id")[0].selectedIndex = -1;
	}

</script>
