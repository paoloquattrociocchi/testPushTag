<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione"
			value="/procedimento/bene/inserimentoImmobile.htm" />
		<p class="titolo_page">
			<spring:message code="bene.immobile.inserimento" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm">
			<form:hidden path="tipologia.id"/>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<br/>
				 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br/>
			<table class="tb_form" border=0>
				<tr>
					<td><form:label path="categoria.id" cssClass="required">
							<spring:message code="bene.categoria" />
						</form:label>
					</td>
					<td  colspan="3"><form:select path="categoria.id" size="1" onchange="submit()">
							<form:option value="" label="------Seleziona Categoria-------" />
							<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
						</form:select>
						<form:errors path="categoria.id" class="testoRosso"/>
					</td>
				</tr>				
					<tr>
						<td><form:label path="sottocategoria.id" cssClass="required"><spring:message code="bene.sottocategoria" /></form:label></td>
						<td  colspan="3">
							<form:select path="sottocategoria.id" size="1">
								<form:option value="" label="------Seleziona Sottocategoria-------" />
								<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
							</form:select>
							<form:errors path="sottocategoria.id" class="testoRosso"/>
						</td>
					</tr>

				<tr>
					<td class="td_titolo" colspan="6">
						<form:label path=""><spring:message	code="bene.immobile.ubicazione" /></form:label>
					</td>
				</tr>
				<tr>
					<td><form:label path="stato.id" cssClass="required" ><spring:message code="stato"/></form:label></td>
		        	<td colspan="3">
						<form:select id="id_Stato" path="stato.id" size="1" onchange="controllaInizializzazioneCampi(this.value,'Comune')">	
							<form:option value="-1" label="-------------------------Seleziona Stato-----------------------"/>
							<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																
						</form:select>
						<form:errors path="stato.id" class="testoRosso"/>
		        	</td>
				</tr>
				<tr>
					<td><form:label path="comune.id" cssClass="required"><spring:message code="comune"/></form:label></td>					
					<td colspan="3">
		        		<div class="ui-widget">		
							<form:input id="id_descrizioneComune" path="comune.descrizione" size="50"  title="Immettere almeno 2 lettere per avviare la ricerca"/>
							<div id="id_loadingComune"></div>
							<form:hidden id="id_hiddenComune" path="comune.id"/>
							<form:errors path="comune.descrizione" class="testoRosso"/>
						</div>
		        	</td>
				</tr>		
								
				<tr>
					<td><form:label path="indirizzo"><spring:message code="indirizzo" /></form:label></td>
					<td>
						<form:select path="indirizzo.toponimo.id" size="1">
						 <form:option value="-1" label="------Seleziona Tipo Strada-------"/>
							<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" />
						</form:select> 	
						<form:input path="indirizzo.denominazione" maxlength="50" size="30"/>
					<td><form:label path="indirizzo.civico"><spring:message code="civico" /></form:label></td>
					<td><form:input path="indirizzo.civico" /></td>
				</tr>							
				
				<tr>
					<td><form:label path="scala">
							<spring:message code="bene.immobile.scala" />
						</form:label>
					</td>
					<td><form:input path="scala" maxlength="3" />
					</td>
					<td><form:label path="interno">
							<spring:message code="bene.immobile.interno" />
						</form:label>
					</td>
					<td><form:input path="interno" maxlength="3"/>
					</td>
				</tr>						
				<tr>						
					<td><form:label path="piano">
							<spring:message code="bene.immobile.piano" />
						</form:label>
					</td>
					<td ><form:input path="piano" maxlength="3"/>
					</td>

					<td><form:label path="edificio">
							<spring:message code="bene.immobile.edificio" />
						</form:label>
					</td>
					<td><form:input path="edificio" maxlength="2"/>
					</td>
				</tr>
				<tr>						
					<td><form:label path="lotto">
							<spring:message code="bene.immobile.lotto" />
						</form:label>
					</td>
					<td><form:input path="lotto" maxlength="2" />
					</td>
					<td><form:label path="indirizzo.cap">
							<spring:message code="bene.immobile.cap" />
						</form:label>
					</td>
					<td><form:input path="indirizzo.cap" maxlength="5"/>
					</td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="6"><form:label path="">
							<spring:message code="bene.immobile.daticatastali" />
						</form:label>
					</td>
				</tr>
				<tr>
					<td>
						<form:label path="descComuneCatastale">
							<spring:message code="bene.immobile.comunecatastale" />
						</form:label>
					</td>
					<td>
						<form:input path="descComuneCatastale" id="id_descComuneCatastale" readonly="true" size="50"  maxlength="100"/>
						<form:hidden path="idComuneCatastale" id="id_hiddenIdComuneCatastale" />
	        		</td>
				</tr>
				<tr>
					<td><form:label path="codiceCatastale1">
							<spring:message code="bene.immobile.codicecatastale" />
						</form:label>
					</td>
					<td><form:input path="codiceCatastale1" readonly="true" id="id_codiceCatastale1"/> <form:input path="codiceCatastale2" maxlength="1" onfocus="javascript:controllaCodiceCastasto('id_codiceCatastale1',this)"/>
					</td> 
					<td><form:label path="tipoCatasto" cssClass="required">
							<spring:message code="bene.immobile.tipocatasto" />
						</form:label>
					</td>
					<td><form:select path="tipoCatasto" size="1">
							<form:option value="" label=""/>
							<form:options items="${tipoCatasto}" itemValue="id" itemLabel="descrizione" />
						</form:select>
						<form:errors path="tipoCatasto" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td><form:label path="categoriaCatastale">
							<spring:message code="bene.immobile.categoriacatastale" />
						</form:label>
					</td>
					<td colspan="3">
						<form:select path="categoriaCatastale.id" size="1" >
							<form:option value="" label="------------------------------------Seleziona Categoria Catastale----------------------------------"/>
							<form:options items="${categorieCatastali}" itemValue="id" itemLabel="descrizione"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td><form:label path="dataAccatastamento">
							<spring:message code="bene.immobile.dataaccatastamento" />
						</form:label>
					</td>
					<td colspan="3"><form:input path="dataAccatastamento" />
					</td>
				</tr>
				<tr>
					<td><form:label path="fondo">
							<spring:message code="bene.immobile.fondo" />
						</form:label>
					</td>
					<td>
						<form:select path="fondo">
							<form:option value="" label="-------Seleziona Fondo -------" />
							<form:options items="${tipoFondo}" itemValue="id" itemLabel="descrizione" />
						</form:select>
					</td>
					<!-- <td colspan="4">
						<table class="tb_bottoni" align="left">
							<tr>
								<td class="td_tasto"><input type="button" value="Graffato con" name="Graffato" class="nowait"></td>
							</tr>
						</table></td>-->
				</tr>
				
				<tr>
					<td class="td_titolo" colspan="4">
						<form:radiobutton path="switchDenunciaIdentifImmobile" value="I" onclick="openCloseDiv('switchDenunciaIdentifImmobile','divIdentificativoImmobile','divDenunciaImmobile')"/>
						<spring:message code="bene.immobile.identificativoimmobile" />
					</td>
				</tr>
			</table>
		<div id="divIdentificativoImmobile" >
			<table class="tb_form" border=0>
				<tr>
					<td><form:label path="sezione">
							<spring:message code="bene.immobile.sezione" />
						</form:label>
					</td>
					<td><form:input path="sezione" maxlength="4"/>
					</td>
					<td><form:label path="foglio" cssClass="required">
							<spring:message code="bene.immobile.foglio" />
						</form:label>
					</td>
					<td><form:input path="foglio" maxlength="4" /><form:errors path="foglio" class="testoRosso"/></td>
				</tr>
				<tr>
					<td><form:label path="particella1" cssClass="required">
							<spring:message code="bene.immobile.particella" />
						</form:label>
					</td>
					<td><form:input path="particella1" maxlength="5"/>/ <form:input path="particella2" maxlength="4"/><form:errors path="particella*" class="testoRosso"/></td>
					<td><form:label path="sub1">
							<spring:message code="bene.immobile.sub" />
						</form:label>
					</td>
					<td><form:input path="sub1" maxlength="4"/>/ <form:input path="sub2" maxlength="3"/><form:errors path="sub*" class="testoRosso"/></td>
				</tr>
			</table>
		</div>
		<table class="tb_form" border=0>
			<tr>
				<td class="td_titolo" colspan="4">
					<form:radiobutton path="switchDenunciaIdentifImmobile" value="D" onclick="openCloseDiv('switchDenunciaIdentifImmobile','divIdentificativoImmobile','divDenunciaImmobile')"/>
					<spring:message code="bene.immobile.denunciaaccatastamento" />
				</td>
			</tr>
		</table>
		<div id="divDenunciaImmobile">
			<table class="tb_form" border=0>
				<tr>
					<td>
					   <form:label path="tipoDenuncia">
							<spring:message code="bene.immobile.tipoDenuncia" />
						</form:label>
					</td>
					<td>
					      <form:select path="tipoDenuncia" size="1">
					       <form:option value="" label=""/>
							<form:options items="${tipoDenuncia}" itemValue="id" itemLabel="descrizione" />
						</form:select>
					</td>
					<td>
					   <form:label path="numeroDenuncia1">
							<spring:message code="procedimento.numero" />
						</form:label>
					</td>
					<td>
					   <form:input path="numeroDenuncia1" maxlength="9"/>/<form:input path="numeroDenuncia2" maxlength="4"/>
					   <form:errors path="numeroDenuncia1" class="testoRosso"/>
					   <form:errors path="numeroDenuncia2" class="testoRosso"/>
					 </td>
				</tr>
				<tr>					
					<td>
						<form:label path="annoDenuncia">
							<spring:message code="bene.immobile.del" />
						</form:label>
					</td>
					<td colspan="3">
						<form:input path="annoDenuncia" maxlength="4"/>
						<form:errors path="annoDenuncia" class="testoRosso"/>
					</td>
				</tr>
				
			</table>
		</div>
			<table class="tb_form" border=0>
				<tr>
					<td class="td_titolo" colspan="4" />
				</tr>
				<tr>
					<td><form:label path="schedaAccatastamento">
							<spring:message code="bene.immobile.schedaaccatastamento" />
						</form:label>
					</td>
					<td><form:input path="schedaAccatastamento" maxlength="10"/>
					</td>
					<td><form:label path="zonaCensuaria">
							<spring:message code="bene.immobile.zonacensuaria" />
						</form:label>
					</td>
					<td>
						<form:input path="zonaCensuaria" maxlength="10"/>
						<form:errors path="zonaCensuaria" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td><form:label path="renditaCatastale">
							<spring:message code="bene.immobile.renditacatastale" />
						</form:label>
					</td>
					<td>
						<form:input path="renditaCatastale" maxlength="15"/>
						<form:errors path="renditaCatastale" class="testoRosso"/>
					</td>
					<td><form:label path="renditaDominicale">
							<spring:message code="bene.immobile.renditadominicale" />
						</form:label>
					</td>
					<td>
						<form:input path="renditaDominicale" maxlength="15"/>
						<form:errors path="renditaDominicale" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td><form:label path="ettari">
							<spring:message code="bene.immobile.ettari" />
						</form:label>
					</td>
					<td>
						<form:input path="ettari" maxlength="5"/>
						<form:errors path="ettari" class="testoRosso"/>
					</td>
					<td><form:label path="are">
							<spring:message code="bene.immobile.are" />
						</form:label>
					</td>
					<td>
						<form:input path="are" maxlength="5"/>
						<form:errors path="are" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td><form:label path="centiare">
							<spring:message code="bene.immobile.centiare" />
						</form:label>
					</td>
					<td colspan="3">
						<form:input path="centiare" maxlength="5"/>
						<form:errors path="centiare" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td><form:label path="vaniIntero">
							<spring:message code="bene.immobile.vani" />
						</form:label>
					</td>
					<td>
						<form:input path="vaniIntero" maxlength="3"/>,<form:input path="vaniDecimale" maxlength="1" cssClass="numerico" onblur="javascript:validaLunghezzaCampo(this,1)"/>
						<form:errors path="vaniIntero" class="testoRosso"/>
						<form:errors path="vaniDecimale" class="testoRosso"/>
					</td>
					<td><form:label path="metriCubiIntero">
							<spring:message code="bene.immobile.metricubi" />
						</form:label>
					</td>
					<td>
						<form:input path="metriCubiIntero" maxlength="4" />,<form:input path="metriCubiDecimale" maxlength="2" onblur="javascript:validaLunghezzaCampo(this,2)"/>
						<form:errors path="metriCubiIntero" class="testoRosso"/>
						<form:errors path="metriCubiDecimale" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td><form:label path="metriQuadriIntero">
							<spring:message code="bene.immobile.metriquadri" />
						</form:label>
					</td>						
					<td colspan="3">
						<form:input path="metriQuadriIntero" maxlength="4"/>,<form:input path="metriQuadriDecimale" maxlength="2" onblur="javascript:validaLunghezzaCampo(this,2)"/>
						<form:errors path="metriQuadriIntero" class="testoRosso"/>
						<form:errors path="metriQuadriDecimale" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td><form:label path="appartenenza">
							<spring:message code="bene.immobile.appartenenza" />
						</form:label>
					</td>
					<td colspan="3">
						<form:radiobutton path="appartenenza" value="S" label="SI"/>
				 		<form:radiobutton path="appartenenza" value="N" label="NO"/>
					</td>
				</tr>
			</table>
			<!-- Segnalazione EMS-167 -->
			<!-- <table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto">
						<input type="button" value='<spring:message code="bene.immobile.impostaVisDatiCatastaliOrigine"/>' name="DatiCatastali" onclick="divScomparsa('datiCatastaliOrigine')" id="buttonImpostaDatiCatastaliOrigine"/>
					</td>
				</tr>
			</table> -->
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="40px" class="td_titolo_label" height="5px" title="apre/chiude impostazione e visualizzazione dati catastali"><img id="id_img_datiCatastaliOrigine" src="${pageContext.request.contextPath}/images/right_arrow_2.png"
							height="38" width="38" /></td>
						<td class="td_titolo_label">
								<spring:message code="bene.immobile.imposta.datiCatastaliOrigine" />
						</td>
					</tr>
			</table>
			<div id="datiCatastaliOrigine" style="display: none;">
			<table class="tb_form" border="0">
				<tr>
					<td class="td_titolo" colspan="6">
						<form:label path=""><spring:message code="bene.immobile.datiCatastaliOrigine" /></form:label>
					</td>
				</tr>
				<tr>
					<td><form:label path="comuneCatastaleP.id" ><spring:message code="comune"/></form:label></td>					
					   <td>
		        		<div class="ui-widget">		
							<form:input id="id_descrizioneComuneCatastaleP" path="comuneCatastaleP.descrizione" size="50" onclick="javascript:controllaSelezione('ComuneCatastaleP')" title="Immettere almeno 2 lettere per avviare la ricerca"/>
							<div id="id_loadingComuneCatastaleP"></div>
							<form:hidden id="id_hiddenComuneCatastaleP" path="comuneCatastaleP.id"/>
						</div>
		        	</td>
				</tr>
				<tr>
					<td><form:label path="codiceCatastale1P">
							<spring:message code="bene.immobile.codicecatastale" />
						</form:label>
					</td>
					<td>
						<form:input path="codiceCatastale1P" readonly="true" id="id_codiceCatastale1P"/> <form:input path="codiceCatastale2P" onfocus="javascript:controllaCodiceCastasto('id_codiceCatastale1P',this)" maxlength="1"/>
					</td>
					<td><form:label path="tipoCatastoP">
							<spring:message code="bene.immobile.tipocatasto" />
						</form:label>
					</td>
					<td><form:select path="tipoCatastoP" size="1">
							<form:option value="" label=""/>
							<form:options items="${tipoCatasto}" itemValue="id" itemLabel="descrizione" />
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4">
						<form:radiobutton path="switchDenunciaIdentifImmobileP"  value="I" onclick="openCloseDiv('switchDenunciaIdentifImmobileP','divIdentificativoImmobileP','divDenunciaImmobileP')"/>
						<spring:message code="bene.immobile.identificativoimmobile" />
					</td>
				</tr>
			</table>
		<div id="divIdentificativoImmobileP">
			<table class="tb_form" border="0">
				<tr>
					<td><form:label path="sezioneP">
							<spring:message code="bene.immobile.sezione" />
						</form:label>
					</td>
					<td><form:input path="sezioneP" maxlength="3" />
					</td>
					<td><form:label path="foglioP">
							<spring:message code="bene.immobile.foglio" />
						</form:label>
					</td>
					<td><form:input path="foglioP" maxlength="4"/>
					</td>
				</tr>
				<tr>
					<td><form:label path="particella1P">
							<spring:message code="bene.immobile.particella" />
						</form:label>
					</td>
					<td><form:input path="particella1P" maxlength="5"/>/ <form:input path="particella2P" maxlength="4"/></td>
					<td><form:label path="sub1P">
							<spring:message code="bene.immobile.sub" />
						</form:label>
					</td>
					<td><form:input path="sub1P" maxlength="4"/>/ <form:input path="sub2P" maxlength="3"/></td>
				</tr>
			</table>
		</div>
			<table class="tb_form" border="0">
				<tr>
					<td class="td_titolo" colspan="4">
						<form:radiobutton path="switchDenunciaIdentifImmobileP" value="D" onclick="openCloseDiv('switchDenunciaIdentifImmobileP','divIdentificativoImmobileP','divDenunciaImmobileP')"/>
						<spring:message code="bene.immobile.denunciaaccatastamento" />
					</td>
				</tr>
			</table>
		<div id="divDenunciaImmobileP">
			<table class="tb_form" border="0">
				<tr>
					<td>
					   <form:label path="tipoDenunciaP">
							<spring:message code="bene.immobile.tipoDenuncia" />
						</form:label>
					</td>
					<td>
					      <form:select path="tipoDenunciaP" size="1">
					      	<form:option value="" label=""/>
							<form:options items="${tipoDenuncia}" itemValue="id" itemLabel="descrizione" />>
						</form:select>
					</td>
					<td>
					   <form:label path="numeroDenuncia1P">
							<spring:message code="procedimento.numero" />
						</form:label>
					</td>
					<td>
					   <form:input path="numeroDenuncia1P" maxlength="9"/>/<form:input path="numeroDenuncia2P" maxlength="4" />
					   <form:errors path="numeroDenuncia1P" class="testoRosso"/>
					   <form:errors path="numeroDenuncia2P" class="testoRosso"/>
					 </td>
				</tr>
				<tr>					
					<td>
						<form:label path="annoDenunciaP">
							<spring:message code="bene.immobile.del" />
						</form:label>
					</td>
					<td colspan="3">
						<form:input path="annoDenunciaP" maxlength="4"/>
						<form:errors path="annoDenunciaP" class="testoRosso"/>
					</td>
				</tr>
			</table>
		</div>
			</div>
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4" />
				</tr>
				<tr>
					<td><form:label path="vincolo">
							<spring:message code="bene.immobile.vincolo" />
						</form:label>
					</td>
					<td><form:select path="vincolo.id" size="1">
							<form:option value="0" label="------Seleziona Vincolo-------" />
							<form:options items="${vincoli}" itemValue="id" itemLabel="descrizione"/>
						</form:select>
					</td>
					<td><form:label path="descrizioneVincolo">
							<spring:message code="bene.immobile.descrizionevincolo" />
						</form:label>
					</td>
					<td><form:input path="descrizioneVincolo" maxlength="15"/>
					</td>
				</tr>
				<tr>
					<td><form:label path="statoConservazione.id">
							<spring:message code="bene.statoconservazione" />
						</form:label>
					</td>
					<td><form:select path="statoConservazione.id" size="1">
							<form:option value="0" label="-------Seleziona Stato Conservazione-------" />
							<form:options items="${statiConservazione}" itemValue="id" itemLabel="descrizione"/>
						</form:select>
					</td>
					<td><form:label path="gravame">
							<spring:message code="bene.gravame" />
						</form:label>
					</td>
					<td>
						<form:radiobutton path="gravame" value="S" label="SI"/>
					 	<form:radiobutton path="gravame" value="N" label="NO"/>
					</td>
				</tr>
				<tr>
					<td><form:label path="comboDisponibilita">
							<spring:message code="bene.immobile.disponibilita" />
						</form:label>
					</td>
					<td><form:select path="comboDisponibilita.id" size="1">
							<form:option value="0" label="------Seleziona Disponibilità-------" />
							<form:options items="${disponibilita}" itemValue="id" itemLabel="descrizione" />
						</form:select>
					</td>
					<td><form:label path="tipoOccupanti">
							<spring:message code="bene.immobile.tipooccupanti" />
						</form:label>
					</td>
					<td>
						<form:select path="tipoOccupanti.id">
							<form:option value="" label="--------Seleziona Occupanti--------"></form:option>
							<form:options items="${tipoOccupanti}" itemValue="id" itemLabel="descrizione" />
						</form:select>
					</td>
				</tr>
				<tr>
					<td><form:label path="note">
							<spring:message code="note" />
						</form:label>
					</td>
					<td colspan="3"><form:textarea path="note" rows="5"/>
					</td>
				</tr>
			</table>

			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Inserisci" name="SalvaEAssocia"></td>
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
		</form:form>
		
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
		<smp:back buttonId="backButton"></smp:back>
		
		
		<script type="text/javascript">
			$(document).ready(function() {
				prendiData("#dataAccatastamento",true);
				if($( "#id_Stato" ).val() != undefined){
				  controllaInizializzazioneCampi($( "#id_Stato" ).val(),"Comune");
				  doAutocompleteImmobili("Comune");
				}
				doAutocompleteImmobili("ComuneCatastaleP");
				
				$("#datiCatastaliOrigine").hide();
				$("#divIdentificativoImmobile").hide();
				$("#divDenunciaImmobile").hide();
				$("#divIdentificativoImmobileP").hide();
				$("#divDenunciaImmobileP").hide();

				$(".numerico").keyup(function(){
					//validaCampoNumerico(this);
				});
				
				/**/
				
				/*cbObjI = document.getElementById("switchDenunciaIdentifImmobileI");
        			if (cbObjI.checked){
        				openCloseDiv("divIdentificativoImmobile","divDenunciaImmobile");
        			}	
        			*/
        			openCloseDiv("switchDenunciaIdentifImmobile","divIdentificativoImmobile","divDenunciaImmobile");
				openCloseDiv("switchDenunciaIdentifImmobileP","divIdentificativoImmobileP","divDenunciaImmobileP")
        					
			});


			function openCloseDiv(open, close){
				if(open != '')
					$("#"+open).show(300);
				if(close != '')
					$("#"+close).hide(300);
			}

			function openCloseDiv(check,idDivI, idDivD){
				var radioButton = $("input[name="+check+"]:checked");
				var idCheck = radioButton.val();

				var divDenuncia = $("#"+idDivD);
				var divIdentificativo = $("#"+idDivI);

				
				if(idCheck=='D'){
					divDenuncia.show(300);
					divIdentificativo.hide(300);
					
				}else{
					divIdentificativo.show(300);
					divDenuncia.hide(300);
				}
			}

			function controllaCodiceCastasto(idCodice,elem){
				var value = $("#"+idCodice).val();
				if(value != undefined && value.length>0){
					$(elem).removeAttr('readonly');
				}else{
					$(elem).attr('readonly', 'readonly');
				}
			}
			
			$(document).ready(function() {
				$("#id_img_datiCatastaliOrigine").rotate({     bind:       { 
						mouseover : function() {              
							$(this).css("cursor","pointer"); 								 
						}, 							
						click : function() { 							
							
							if($("#datiCatastaliOrigine").is(":visible")){
								$(this).rotate({animateTo:0});  
								divScomparsa('datiCatastaliOrigine');
							}
							else {
								$(this).rotate({animateTo:90});
								divScomparsa('datiCatastaliOrigine');
							}
						}       
					}      
				});
			});
			
		</script>		

	</tiles:putAttribute>
</tiles:insertDefinition>
