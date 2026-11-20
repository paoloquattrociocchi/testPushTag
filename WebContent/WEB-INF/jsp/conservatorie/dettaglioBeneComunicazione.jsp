<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<p class="titolo_page">
			<c:if test="${comunicazioneBeniForm.tipoOperazione =='Dettaglio'}"><spring:message code="conservatoria.titolo.dettaglioBene" /></c:if>
			<c:if test="${comunicazioneBeniForm.tipoOperazione =='Modifica'}"><spring:message code="conservatoria.titolo.modificaBene" /></c:if>
		</p>
		<spring:url var="azione" value="/conservatorie/beniComunicazione.htm" />
		<form:form id="id_formBeni" method="post" action="${azione}" commandName="comunicazioneBeniForm">
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0" >
				<tr>
					<td class="td_titolo" style="font-weight:bold" width="30%">
						<form:label path="numeroComunicazione"><spring:message code="conservatoria.numeroComunicazione" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneBeniForm.numeroComunicazione}" />
					</td>
					<td class="td_titolo" style="font-weight:bold" align="left">
						<form:label path="descTipoNota"><spring:message code="conservatoria.tipo" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneBeniForm.descTipoNota}" />
					</td>
				</tr>
			</table>
			<br />
			<br />
			<%-- INIZIO DETTAGLIO -----------------------------------------------------------------------------------------------------------------%>
			<c:if test="${comunicazioneBeniForm.tipoOperazione =='Dettaglio'}">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo">
							<spring:message code="conservatoria.beniSIT" />&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${comunicazioneBeniForm.beneSitMp}" />
						</td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="12%">
							<form:label path="descBene" ><spring:message code="bene.mobile.altro.descrizione"/></form:label>
						</td>
						<td colspan="2">
							<c:out value="${comunicazioneBeniForm.descBene}" />
						</td>
					</tr>
					<tr>
						<td width="12%"><form:label path="comune.id" ><spring:message code="comune"/></form:label></td>					
						<td width="20%">
							<c:out value="${comunicazioneBeniForm.descComuneBene}" />
	       				</td>
						<td colspan="2">
							<form:label path="codiceCatastale1"><spring:message code="bene.immobile.codicecatastale" /></form:label>
							&nbsp;&nbsp;
							<c:out value="${comunicazioneBeniForm.codiceCatastale1}" />
							<c:out value="${comunicazioneBeniForm.codiceCatastale2}" />
						</td> 
					</tr>
					<tr>
						<td><form:label path="tipoCatasto"><spring:message code="conservatoria.catasto" /></form:label></td>
						<td>
							<c:out value="${comunicazioneBeniForm.descTipoCatasto}" />
						</td>
						<td>
							<form:label path="fondo"><spring:message code="bene.immobile.fondo" /></form:label>
							&nbsp;&nbsp;
							<c:out value="${comunicazioneBeniForm.descrFondo}" /></td>
						<td>
							<form:label path="numeGraffati"><spring:message code="conservatoria.graffato" /></form:label>
							&nbsp;&nbsp;
							<c:out value="${comunicazioneBeniForm.numeGraffati}" /></td>
					</tr>	
					<tr>
						<td><form:label path="descrNaturaImmobile"><spring:message code="conservatoria.naturaImmobile" /></form:label></td>
						<td colspan="3">
							<c:out value="${comunicazioneBeniForm.descrNaturaImmobile}" />
						</td>
					</tr>
				</table>
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo"><spring:message code="bene.immobile.ubicazione" /></td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="3%"><form:label path="numeSupEttari"><spring:message code="bene.immobile.ettari" /></form:label></td>
						<td width="10%"><c:out value="${comunicazioneBeniForm.numeSupEttari}" /></td>
						<td width="3%"><form:label path="numeSupAre"><spring:message code="bene.immobile.are" /></form:label></td>
						<td width="10%"><c:out value="${comunicazioneBeniForm.numeSupAre}" /></td>
						<td width="3%"><form:label path="numeSupCentiare"><spring:message code="bene.immobile.centiare" /></form:label></td>
						<td width="10%"><c:out value="${comunicazioneBeniForm.numeSupCentiare}" /></td>
					</tr>
					<tr>
						<td><form:label path="numeSupMqInt"><spring:message code="bene.immobile.metriquadri" /></form:label></td>						
						<td><c:out value="${comunicazioneBeniForm.numeSupMqInt}" /></td>
						<td><form:label path="numeVaniInt"><spring:message code="bene.immobile.vani" /></form:label></td>
						<td><c:out value="${comunicazioneBeniForm.numeVaniInt}" /></td>
						<td><form:label path="numeMcInt"><spring:message code="bene.immobile.metricubi" /></form:label></td>
						<td><c:out value="${comunicazioneBeniForm.numeMcInt}" /></td>
					</tr>
					<tr>
					 <!--  06-03-2015
						<td><form:label path="indirizzo"><spring:message code="indirizzo" /></form:label></td>
						<td>
							<c:out value="${comunicazioneBeniForm.indirizzo.denominazione}" />
							<c:out value="${comunicazioneBeniForm.descCivico}" /> 
						</td>	
					-->
						<td><form:label path="descIndirizzo"><spring:message code="indirizzo" /></form:label></td>
						<td>
							<c:out value="${comunicazioneBeniForm.descIndirizzo}" />
						</td>	

						<td><form:label path="descCivico"><spring:message code="civico" /></form:label></td>
						<td><c:out value="${comunicazioneBeniForm.descCivico}" /></td>
						<td>&nbsp;</td><td>&nbsp;</td>
					</tr>
					<tr>
						<td><form:label path="descEdificio"><spring:message code="bene.immobile.edificio" /></form:label></td>
						<td><c:out value="${comunicazioneBeniForm.descEdificio}" /></td>
						<td><form:label path="descScala"><spring:message code="bene.immobile.scala" /></form:label></td>
						<td><c:out value="${comunicazioneBeniForm.descScala}" /></td>
						<td><form:label path="descInterno"><spring:message code="bene.immobile.interno" /></form:label></td>
						<td><c:out value="${comunicazioneBeniForm.descInterno}" /></td>
					</tr>
					<tr>
						<td><form:label path="descPiano"><spring:message code="bene.immobile.piano" /></form:label></td>
						<td ><c:out value="${comunicazioneBeniForm.descPiano}" /></td>
						<td><form:label path="descLotto"><spring:message code="bene.immobile.lotto" /></form:label></td>
						<td><c:out value="${comunicazioneBeniForm.descLotto}" /></td>
						<td>&nbsp;</td><td>&nbsp;</td>
					</tr>
				</table>
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo"><spring:message code="bene.immobile.daticatastali" /></td>
					</tr>
				</table>
				</br>
				
				
				<logic:present scope="request" name="visualizzaIdentificazione">
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="td_titolo"><spring:message code="conservatoria.IdentificazioneCatasto" /></td>
						</tr>
					</table>
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td width="3%"><form:label path="descSezione"><spring:message code="bene.immobile.sezione" /></form:label></td>
							<td width="10%"><c:out value="${comunicazioneBeniForm.descSezione}" /></td>
							<td width="3%"><form:label path="descFoglio"><spring:message code="bene.immobile.foglio" /></form:label></td>
							<td width="10%"><c:out value="${comunicazioneBeniForm.descFoglio}" /></td>
							<td width="3%">&nbsp;</td>
							<td width="10%">&nbsp;</td>
						</tr>
						<tr>
							<td><form:label path="descParticella1"><spring:message code="bene.immobile.particella" /></form:label></td>
							<td><c:out value="${comunicazioneBeniForm.descParticella1}" />/ <c:out value="${comunicazioneBeniForm.descParticella2}" /></td>
							<td><form:label path="descSubalterno1"><spring:message code="bene.immobile.sub" /></form:label></td>
							<td><c:out value="${comunicazioneBeniForm.descSubalterno1}" />/ <c:out value="${comunicazioneBeniForm.descSubalterno2}" /></td>
							<td width="3%">&nbsp;</td>
							<td width="10%">&nbsp;</td>
						</tr>
					</table>
					</br>
				</logic:present>
				
				<logic:present scope="request" name="visualizzaDenuncia">
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="td_titolo"><spring:message code="bene.immobile.denunciaaccatastamento" /></td>
						</tr>
					</table>
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td width="3%"><form:label path="descTipoDenuncia"><spring:message code="bene.immobile.tipoDenuncia" /></form:label></td>
							<td width="10%"><c:out value="${comunicazioneBeniForm.descTipoDenuncia}" /></td>
							<td width="3%"><form:label path="numeDenuncia1"><spring:message code="procedimento.numero" /></form:label></td>
							<td width="10%">
								<c:out value="${comunicazioneBeniForm.numeDenuncia1}" />/<c:out value="${comunicazioneBeniForm.numeDenuncia2}" />
							</td>
							<td width="3%">&nbsp;</td>
							<td width="10%">&nbsp;</td>
						</tr>
						<tr>
							<td><form:label path="numeAnnoDenuncia"><spring:message code="bene.immobile.del" /></form:label></td>
							<td colspan="5">
								<c:out value="${comunicazioneBeniForm.numeAnnoDenuncia}" />
							</td>
						</tr>
					</table>
					</br>
				</logic:present>
				
				<div id="datiCatastaliOrigineVisualizza" style="display: none; width: 100%;">
					<jsp:include page="/WEB-INF/jsp/conservatorie/includeDatiCatastoOrigine.jsp" />
					<table class="tb_bottoni" align="center" id="chiudiVis" border="0">
							<tr>
								<td class="td_tasto">
									<input type="button" id="chiudiOrigineVis" value="Chiudi origine" name="chiudiOrigineVis" title="Chiudi origine"
										onclick="chiudiDatiCatastaliOrigineVis()" class="nowait" />
								</td>
								<td class="td_tasto">
									<input type="submit" id="indietro" name="Indietro" title="Indietro" value="Indietro" />
								</td>
							</tr>
					</table>
				</div>	
				<%-- BOTTONI -----------------%>
				<table class="tb_bottoni" align="center" id="bottoniApriVis" border="0">
					<tr>
						<td class="td_tasto">
							<input type="button" id="origineVis" value="Visualizza origine" name="origineVis" title="Visualizza origine" onclick="visDatiCatastaliOrigineVis()" class="nowait" />
						</td>
						<td class="td_tasto">
							<input type="submit" id="indietroVis" name="Indietro" title="Indietro" value="Indietro" />
						</td>
					</tr>
				</table>
			</c:if>
			<%-- FINE DETTAGLIO ------------------------------------------------------------------------------------------------------------------%>
			
			<%-- INIZIO MODIFICA -----------------------------------------------------------------------------------------------------------------%>
			<c:if test="${comunicazioneBeniForm.tipoOperazione =='Modifica'}">
			<%-- 
			<form:hidden id="id_numeVaniInt" 	path="numeVaniInt"/>
			<form:hidden id="id_numeMcInt" 		path="numeMcInt"/>
			<form:hidden id="id_descEdificio" 	path="descEdificio"/>
			<form:hidden id="id_descScala" 		path="descScala"/>
			<form:hidden id="id_descInterno" 	path="descInterno"/>
			<form:hidden id="id_descPiano" 		path="descPiano"/>
			<form:hidden id="id_descSezione" 	path="descSezione"/>
			<form:hidden id="id_descSubalterno1" path="descSubalterno1"/>
			--%>
			<form:hidden id="beneSelected" path="beneSelected"/>
			<form:hidden id="numeroComunicazione" path="numeroComunicazione"/>
			<form:hidden id="descTipoNota" path="descTipoNota"/>
			<form:hidden id="beneSitMp" path="beneSitMp"/>
			<form:hidden id="stato" path="stato"/>
			<form:hidden id="tipoOperazione" path="tipoOperazione"/>
			<form:hidden id="conservatoriaId" path="conservatoriaId"/>
			
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo"><spring:message code="conservatoria.beniSIT" />&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${comunicazioneBeniForm.beneSitMp}" /></td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="12%">
							<form:label path="descBene" ><spring:message code="bene.mobile.altro.descrizione"/></form:label>
						</td>
						<td colspan="5">
							<c:out value="${comunicazioneBeniForm.descBene}" />
						</td>
					</tr>
					<tr>
						<td width="12%"><form:label path="comune.id" cssClass="required"><spring:message code="comune"/></form:label></td>					
						<td>
							<form:select id="id_comuneConservatoria" path="comuneConservatoria"  onchange="componiCodCatasto()">
								<form:option value="" label="------Seleziona Comune-------" />
								<form:options items="${listaComuniConservatoria}" itemValue="id" itemLabel="descrizione" />
							</form:select>
	       				</td>
						<td width="10%"><form:label path="codiceCatastale1" cssClass="required"><spring:message code="bene.immobile.codicecatastale" /></form:label></td>
						<td colspan="3">
							<form:input path="codiceCatastale1" readonly="true" id="id_codiceCatastale1" maxlength="1"/>&nbsp; 
							<form:input path="codiceCatastale2" maxlength="1" onfocus="javascript:controllaCodiceCastasto('id_codiceCatastale1',this)"/>
						</td> 
					</tr>
					<tr>
						<td><form:label path="tipoCatasto" cssClass="required"><spring:message code="conservatoria.catasto" /></form:label></td>
						<td>
							<form:select id="id_tipoCatasto" path="tipoCatasto" size="1" value="${comunicazioneBeniForm.tipoCatasto}" onchange="abilitaInput()">
								<form:option value="" label="------Seleziona Catasto-------" />
								<form:options items="${tipoCatasto}" itemValue="id" itemLabel="descrizione" />
							</form:select>
						</td>
						<td><form:label path="fondo" ><spring:message code="bene.immobile.fondo" /></form:label></td>
						<td>
							<form:select path="fondo">
								<form:option value="" label="------Seleziona Fondo-------" />
								<form:options items="${tipoFondo}" itemValue="id" itemLabel="descrizione" />
							</form:select>
						</td>
						<td colspan="2"><form:label path="numeGraffati" ><spring:message code="conservatoria.graffato" /></form:label>
							&nbsp;&nbsp;<form:input path="numeGraffati" id="numeGraffati" maxlength="15" />
						</td>
					</tr>	
					<tr>
						<td><form:label path="idNaturaImmobile" cssClass="required"><spring:message code="conservatoria.naturaImmobile" /></form:label></td>
						<td colspan="5">
							<form:select path="idNaturaImmobile" value="${comunicazioneBeniForm.idNaturaImmobile}">
								<form:option value="" label="------Seleziona Natura dell'immobile-------" />
								<form:options items="${categoriaCatastale}" itemValue="id" itemLabel="descrizione" />
							</form:select>
						</td>
					</tr>
				</table>
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo"><spring:message code="bene.immobile.ubicazione" /></td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="10%"><form:label path="numeSupEttari" ><spring:message code="bene.immobile.ettari" /></form:label></td>
						<td width="22%"><form:input path="numeSupEttari" id="numeSupEttari" maxlength="5" disabled="${comunicazioneBeniForm.disabledCampiT}" /></td>
						
						<td width="5%"><form:label path="numeSupAre" ><spring:message code="bene.immobile.are" /></form:label></td>
						<td width="20%"><form:input path="numeSupAre" id="numeSupAre" maxlength="5" disabled="${comunicazioneBeniForm.disabledCampiT}" /></td>

						<td width="8%"><form:label path="numeSupCentiare" ><spring:message code="bene.immobile.centiare" /></form:label></td>
						<td width="35%"><form:input path="numeSupCentiare" id="numeSupCentiare" maxlength="5" disabled="${comunicazioneBeniForm.disabledCampiT}" /></td>
					</tr>
					<tr>
						<td><form:label path="numeSupMqInt" ><spring:message code="bene.immobile.metriquadri" /></form:label></td>						
						<td><form:input path="numeSupMqInt" id="numeSupMqInt" maxlength="4" size="4" />,<form:input path="numeSupMqDec" id="numeSupMqDec" maxlength="2" size="4" /></td>
						
						<td><form:label path="numeVaniInt" ><spring:message code="bene.immobile.vani" /></form:label></td>
						<td><form:input path="numeVaniInt" id="numeVaniInt" name="numeVaniInt" maxlength="3" size="4" disabled="${comunicazioneBeniForm.disabledCampi}" />,<form:input path="numeVaniDec" id="numeVaniDec" maxlength="1" size="4" /></td>
						
						<td><form:label path="numeMcInt" ><spring:message code="bene.immobile.metricubi" /></form:label></td>
						<td><form:input path="numeMcInt" id="numeMcInt" maxlength="4" size="4" disabled="${comunicazioneBeniForm.disabledCampi}" />,<form:input path="numeMcDec" id="numeMcDec" maxlength="2" size="4" disabled="${comunicazioneBeniForm.disabledCampi}" /></td>
					</tr>
					<tr>
						<td><form:label path="descIndirizzo" ><spring:message code="indirizzo" /></form:label></td>
						<td><form:input path="descIndirizzo" maxlength="34" size="34"/></td>
						<td>&nbsp;</td><td>&nbsp;</td>
						<td><form:label path="descCivico" ><spring:message code="civico" /></form:label></td>
						<td><form:input path="descCivico" maxlength="6" /></td>
					</tr>
					<tr>
						<td><form:label path="descEdificio" ><spring:message code="bene.immobile.edificio" /></form:label></td>
						<td><form:input path="descEdificio" disabled="${comunicazioneBeniForm.disabledCampi}" maxlength="2" /></td>
						
						<td><form:label path="descScala" ><spring:message code="bene.immobile.scala" /></form:label></td>
						<td><form:input path="descScala" disabled="${comunicazioneBeniForm.disabledCampi}" maxlength="2" /></td>
						
						<td><form:label path="descInterno" ><spring:message code="bene.immobile.interno" /></form:label></td>
						<td><form:input path="descInterno" disabled="${comunicazioneBeniForm.disabledCampi}" maxlength="3"/></td>
					</tr>
					<tr>
						<td><form:label path="descPiano" ><spring:message code="bene.immobile.piano" /></form:label></td>
						<td ><form:input path="descPiano" disabled="${comunicazioneBeniForm.disabledCampi}" maxlength="4"/></td>
						<td><form:label path="descLotto" ><spring:message code="bene.immobile.lotto" /></form:label></td>
						<td colspan="3"><form:input path="descLotto" maxlength="2" /></td>
				</table>
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo"><spring:message code="bene.immobile.daticatastali" /></td>
					</tr>
				</table>
				</br>
				<form:hidden id="identificazioneCatastaleChecked" path="identificazioneCatastaleChecked"/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" width="3%">
							<c:if test="${comunicazioneBeniForm.identificazioneCatastale!=true}" >
								<form:checkbox path="identificazioneCatastale" id="identificazioneCatastale" onclick="abilitaIdenCatastale()"/>
							</c:if>
							<c:if test="${comunicazioneBeniForm.identificazioneCatastale==true}" >
								<form:checkbox path="identificazioneCatastale" id="identificazioneCatastale" disabled="true" onclick="abilitaIdenCatastale()"/>
							</c:if>
						</td>
						<td class="td_titolo"><spring:message code="conservatoria.IdentificazioneCatasto" /></td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="10%"><form:label path="descSezione" ><spring:message code="bene.immobile.sezione" /></form:label></td>
						<td width="28%">
							<form:input path="descSezione" id="descSezione" disabled="${comunicazioneBeniForm.disabledCampi}" maxlength="3" size="8" />	
						</td>
						<td colspan="2">
							<form:label path="descFoglio" cssClass="required"><spring:message code="bene.immobile.foglio" /></form:label>
							&nbsp;&nbsp;
							<form:input path="descFoglio" id="descFoglio" maxlength="4" disabled="${comunicazioneBeniForm.disabledCampiIdentificazione}" />
						</td>
					</tr>
					<tr>
						<td><form:label path="descParticella1" cssClass="required"><spring:message code="bene.immobile.particella" /></form:label></td>
						<td><form:input path="descParticella1" id="descParticella1" maxlength="5" disabled="${comunicazioneBeniForm.disabledCampiIdentificazione}" />/ <form:input path="descParticella2" id="descParticella2" maxlength="4" disabled="${comunicazioneBeniForm.disabledCampiIdentificazione}" /></td>
						<td>
							<form:label path="descSubalterno1" ><spring:message code="bene.immobile.sub" /></form:label>
							&nbsp;
							<form:input path="descSubalterno1" id="descSubalterno1" disabled="${comunicazioneBeniForm.disabledCampi}" maxlength="4" />/ <form:input path="descSubalterno2" maxlength="3" />
						</td>
					</tr>
				</table>
				</br>
				<form:hidden id="denunciaAccatastamentoChecked" path="denunciaAccatastamentoChecked"/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" width="3%">
							<c:if test="${comunicazioneBeniForm.denunciaAccatastamento!=true}" >
								<form:checkbox path="denunciaAccatastamento" id="denunciaAccatastamento" onclick="abilitaDenunciaCatastale()"/>
							</c:if>
							<c:if test="${comunicazioneBeniForm.denunciaAccatastamento==true}" >
								<form:checkbox path="denunciaAccatastamento" id="denunciaAccatastamento" disabled="true" onclick="abilitaDenunciaCatastale()"/>
							</c:if>
						</td>
						<td class="td_titolo"><spring:message code="bene.immobile.denunciaaccatastamento" /></td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="5%"><form:label path="descTipoDenuncia" cssClass="required"><spring:message code="bene.immobile.tipoDenuncia" /></form:label></td>
						<td>
							<form:select path="tipoDenuncia" id="tipoDenuncia" size="1" value="${comunicazioneBeniForm.tipoDenuncia}" disabled="${comunicazioneBeniForm.disabledCampiDenuncia}" >
								<form:option value="" label="" />
								<form:options items="${tipoDenuncia}" itemValue="id" itemLabel="descrizione" />
							</form:select>
						</td>
						<td colspan="2"><form:label path="numeDenuncia1" cssClass="required"><spring:message code="procedimento.numero" /></form:label>
							&nbsp;&nbsp;
							<form:input path="numeDenuncia1" id="numeDenuncia1" maxlength="9" disabled="${comunicazioneBeniForm.disabledCampiDenuncia}"  />/
							<form:input path="numeDenuncia2" id="numeDenuncia2" maxlength="4" disabled="${comunicazioneBeniForm.disabledCampiDenuncia}"  /> 
							<form:errors path="numeDenuncia1" class="testoRosso" /> 
							<form:errors path="numeDenuncia2" class="testoRosso" />
						</td>
					</tr>
					<tr>
						<td><form:label path="numeAnnoDenuncia" cssClass="required"><spring:message code="bene.immobile.del" /></form:label></td>
						<td colspan="3">
							<form:input path="numeAnnoDenuncia" id="numeAnnoDenuncia" maxlength="4" disabled="${comunicazioneBeniForm.disabledCampiDenuncia}" />
							<form:errors path="numeAnnoDenuncia" class="testoRosso" />
						</td>
					</tr>
				</table>
				</br>
				<table class="tb_bottoni" align="center" id="bottoniApriMod">
					<tr>
						<td class="td_tasto">
							<input type="button" id="origine" value="Visualizza origine" name="origine" title="Visualizza origine" onclick="visDatiCatastaliOrigineMod()" class="nowait" />
						</td>
					</tr>
				</table>
				<div id="datiCatastaliOrigineModifica" style="display: none; width: 100%;"> 
					<jsp:include page="/WEB-INF/jsp/conservatorie/includeDatiCatastoOrigine.jsp" />
					</br>
					<table class="tb_bottoni" align="center" id="chiudiVisMod" border="0">
							<tr>
								<td class="td_tasto">
									<input type="button" id="chiudiOrigine" value="Chiudi origine" name="chiudiOrigine" title="Chiudi origine"
										onclick="chiudiDatiCatastaliOrigineMod()" class="nowait" />
								</td>
							</tr>
					</table>
				</div>
				</br>
				</br>
					<table class="tb_bottoni" align="center">
	                 	<tr>
						<c:if test="${comunicazioneBeniForm.stato=='0' || comunicazioneBeniForm.stato=='1' }">
							<td class="td_tasto"><input type="submit" name="Conferma" title="Conferma" value="Conferma" /></td>
	                  		<td class="td_tasto"><input type="submit" name="Cancella" title="Cancella" value="Cancella" onClick="return popUpConferma()" class="nowait"/></td>
						</c:if>
	                  		<td class="td_tasto"><input type="submit" name="Indietro" title="Indietro" value="Indietro"/></td>	                   		
                        </tr>
					</table>
			</c:if>
			<%-- FINE MODIFICA 
            
<script type="text/javascript">
    doAutocompleteComuneConservat('Comune');
</script>

             ------------------------------------------------------------------------------------------------------------------%>
<script type="text/javascript">
 	
 	function visDatiCatastaliOrigineMod() {
		document.getElementById("datiCatastaliOrigineModifica").style.display = 'inline-block';
		document.getElementById("bottoniApriMod").style.visibility = 'hidden';
	}
 	
 	function chiudiDatiCatastaliOrigineMod() {
		document.getElementById("datiCatastaliOrigineModifica").style.display = 'none';
		document.getElementById("bottoniApriMod").style.visibility = 'visible';
	}
 	
 	function visDatiCatastaliOrigineVis() {
		document.getElementById("datiCatastaliOrigineVisualizza").style.display = 'inline-block';
		document.getElementById("bottoniApriVis").style.visibility = 'hidden';
	}
 	
 	function chiudiDatiCatastaliOrigineVis() {
		document.getElementById("datiCatastaliOrigineVisualizza").style.display = 'none';
		document.getElementById("bottoniApriVis").style.visibility = 'visible';
	}
 	
 	function controllaCodiceCastasto(idCodice,elem){
		var value = $("#"+idCodice).val();
		if(value != undefined && value.length>0){
			$(elem).removeAttr('readonly');
		}else{
			$(elem).attr('readonly', 'readonly');
		}
	}
 	 	
	function abilitaInput() 
	{
		if( document.getElementById('id_tipoCatasto') != null )
		{
			var tipo = document.getElementById('id_tipoCatasto').value;
			if (tipo == 'U') {
				document.getElementById('numeVaniInt').disabled = false;
				document.getElementById('numeVaniDec').disabled = false;
				document.getElementById('numeMcInt').disabled = false;
				document.getElementById('numeMcDec').disabled = false;
				document.getElementById('descEdificio').disabled = false;
				document.getElementById('descScala').disabled = false;
				document.getElementById('descInterno').disabled = false;
				document.getElementById('descPiano').disabled = false;
				var cbObj = document.getElementById("identificazioneCatastale");
		    		if (cbObj.checked) {
			        document.getElementById('descSezione').disabled = false;
					document.getElementById('descSubalterno1').disabled = false;
					document.getElementById('descSubalterno2').disabled = false;
		    		}
		    		
	    		document.getElementById("numeSupEttari").value = '';
				document.getElementById('numeSupEttari').disabled = true;
				
				document.getElementById("numeSupAre").value = '';
				document.getElementById('numeSupAre').disabled = true;
				
				document.getElementById("numeSupCentiare").value = '';
				document.getElementById('numeSupCentiare').disabled = true;
				
		    } else {
				document.getElementById("numeVaniInt").value = '';
				document.getElementById('numeVaniInt').disabled = true;
				
				document.getElementById("numeVaniDec").value = '';
				document.getElementById('numeVaniDec').disabled = true;

				document.getElementById("numeMcInt").value = '';
				document.getElementById('numeMcInt').disabled = true;
				
				document.getElementById("numeMcDec").value = '';
				document.getElementById('numeMcDec').disabled = true;
				
				document.getElementById("descEdificio").value = '';
				document.getElementById('descEdificio').disabled = true;
				
				document.getElementById("descScala").value = '';
				document.getElementById('descScala').disabled = true;
				
				document.getElementById("descInterno").value = '';
				document.getElementById('descInterno').disabled = true;
				
				document.getElementById("descPiano").value = '';
				document.getElementById('descPiano').disabled = true;
				
				document.getElementById("descSezione").value = '';
				document.getElementById('descSezione').disabled = true;
				
				document.getElementById("descSubalterno1").value = '';
				document.getElementById('descSubalterno1').disabled = true;
				
				document.getElementById("descSubalterno2").value = '';
				document.getElementById('descSubalterno2').disabled = true;
				
				document.getElementById('numeSupEttari').disabled = false;
				document.getElementById('numeSupAre').disabled = false;
				document.getElementById('numeSupCentiare').disabled = false;
			}
		}
	}
	
	function abilitaIdenCatastale() 
	{
		document.getElementById("denunciaAccatastamento").disabled = false;
		document.getElementById("denunciaAccatastamento").checked = false;
		document.getElementById("identificazioneCatastale").disabled = true;
		
		document.getElementById('descSezione').disabled = false;
		document.getElementById('descFoglio').disabled = false;
		document.getElementById('descParticella1').disabled = false;
		document.getElementById('descParticella2').disabled = false;
		document.getElementById('descSubalterno1').disabled = false;
		document.getElementById('descSubalterno2').disabled = false;
		
		document.getElementById("tipoDenuncia").selectedIndex = 0;
		document.getElementById('tipoDenuncia').disabled = true;
		document.getElementById('numeDenuncia1').value = '';
		document.getElementById('numeDenuncia1').disabled = true;
		document.getElementById('numeDenuncia2').value = '';
		document.getElementById('numeDenuncia2').disabled = true;
		document.getElementById('numeAnnoDenuncia').value = '';
		document.getElementById('numeAnnoDenuncia').disabled = true;
		
		document.getElementById('identificazioneCatastaleChecked').value = true;
		document.getElementById('denunciaAccatastamentoChecked').value = false;
	}
	
	function abilitaDenunciaCatastale() 
	{
		document.getElementById("identificazioneCatastale").checked = false;
		document.getElementById("identificazioneCatastale").disabled = false;
		document.getElementById("denunciaAccatastamento").disabled = true;
		
		document.getElementById('tipoDenuncia').disabled = false;
		document.getElementById('numeDenuncia1').disabled = false;
		document.getElementById('numeDenuncia2').disabled = false;
		document.getElementById('numeAnnoDenuncia').disabled = false;
		
		document.getElementById('descSezione').value = '';
		document.getElementById('descSezione').disabled = true;
		document.getElementById('descFoglio').value = '';
		document.getElementById('descFoglio').disabled = true;
		document.getElementById('descParticella1').value = '';
		document.getElementById('descParticella1').disabled = true;
		document.getElementById('descParticella2').value = '';
		document.getElementById('descParticella2').disabled = true;
		document.getElementById('descSubalterno1').value = '';
		document.getElementById('descSubalterno1').disabled = true;
		document.getElementById('descSubalterno2').value = '';
		document.getElementById('descSubalterno2').disabled = true;
		
		document.getElementById('identificazioneCatastaleChecked').value = false;
		document.getElementById('denunciaAccatastamentoChecked').value = true;
	}
	
	
	function abilitaInputP() 
	{
		if( document.getElementById('id_tipoCatastoP') != null )
		{
			var tipo = document.getElementById('id_tipoCatastoP').value;
			if (tipo == 'U') {
				var cbObjP = document.getElementById("identificazioneCatastaleP");
		        if (cbObjP.checked) {
			        document.getElementById('descSezioneP').disabled = false;
					document.getElementById('descSubalterno1P').disabled = false;
					document.getElementById('descSubalterno2P').disabled = false;
		        }
			} else {
				document.getElementById("descSezioneP").value = '';
				document.getElementById('descSezioneP').disabled = true;
				
				document.getElementById("descSubalterno1P").value = '';
				document.getElementById('descSubalterno1P').disabled = true;
				
				document.getElementById("descSubalterno2P").value = '';
				document.getElementById('descSubalterno2P').disabled = true;
			}
		}
	}
	
	function abilitaIdenCatastaleP() 
	{
		document.getElementById("denunciaAccatastamentoP").disabled = false;
		document.getElementById("denunciaAccatastamentoP").checked = false;
		document.getElementById("identificazioneCatastaleP").disabled = true;
		
		document.getElementById('descSezioneP').disabled = false;
		document.getElementById('descFoglioP').disabled = false;
		document.getElementById('descParticella1P').disabled = false;
		document.getElementById('descParticella2P').disabled = false;
		document.getElementById('descSubalterno1P').disabled = false;
		document.getElementById('descSubalterno2P').disabled = false;
		
		document.getElementById("descTipoDenunciaP").selectedIndex = 0;
		document.getElementById('descTipoDenunciaP').disabled = true;
		document.getElementById('numeDenuncia1P').value = '';
		document.getElementById('numeDenuncia1P').disabled = true;
		document.getElementById('numeDenuncia2P').value = '';
		document.getElementById('numeDenuncia2P').disabled = true;
		document.getElementById('numeAnnoDenunciaP').value = '';
		document.getElementById('numeAnnoDenunciaP').disabled = true;
	}
	
	function abilitaDenunciaCatastaleP() 
	{
		document.getElementById("identificazioneCatastaleP").checked = false;
		document.getElementById("identificazioneCatastaleP").disabled = false;
		document.getElementById("denunciaAccatastamentoP").disabled = true;
		
		document.getElementById('descTipoDenunciaP').disabled = false;
		document.getElementById('numeDenuncia1P').disabled = false;
		document.getElementById('numeDenuncia2P').disabled = false;
		document.getElementById('numeAnnoDenunciaP').disabled = false;
		
		document.getElementById('descSezioneP').value = '';
		document.getElementById('descSezioneP').disabled = true;
		document.getElementById('descFoglioP').value = '';
		document.getElementById('descFoglioP').disabled = true;
		document.getElementById('descParticella1P').value = '';
		document.getElementById('descParticella1P').disabled = true;
		document.getElementById('descParticella2P').value = '';
		document.getElementById('descParticella2P').disabled = true;
		document.getElementById('descSubalterno1P').value = '';
		document.getElementById('descSubalterno1P').disabled = true;
		document.getElementById('descSubalterno2P').value = '';
		document.getElementById('descSubalterno2P').disabled = true;
	}
	
 	function componiCodCatasto() {
 		var catasto = document.getElementById('id_comuneConservatoria').value;
 		document.getElementById('id_codiceCatastale1').value = catasto;
	}
 	
 	function componiCodCatastoP() {
 		document.getElementById('codiceCatastale1P').value = "";
 		document.getElementById('codiceCatastale2P').value = "";
	}
	
	function popUpConferma() 
	{
		if (confirm("Confermi l'operazione ?")) 
			return true;
		else 
			return false;
	}
</script>
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>