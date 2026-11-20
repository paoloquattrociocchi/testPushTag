<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/ricercaBene.htm" />
		<p class="titolo_page">
			<spring:message code="bene.aggiungibene" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm">
			<form:hidden id="id_hiddenVersioneBene" path="versioneBene" />
			<input type="hidden" name="visualizzaPulsante" value="${visualizzaPulsante}" />

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />

			<table class="tb_form">

				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.ricercabene" /></td>
				</tr>
				<tr>
					<td><form:label path="tipologia.id">
							<spring:message code="bene.tipologia" />
						</form:label></td>
					<td colspan="3"><form:select path="tipologia.id" size="1" onchange="this.form.submit()">
							<form:option value="-1" label="------Seleziona Tipologia-------" />
							<form:options items="${tipologie}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>
				<tr>
					<td><form:label path="categoria.id">
							<spring:message code="bene.categoria" />
						</form:label></td>
					<td colspan="3"><form:select path="categoria.id" size="1" onchange="submit()">
							<form:option value="-1" label="------Seleziona Categoria-------" />
							<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>
				<logic:notEqual name="beneForm" property="tipologia.id" value="5">
					<tr>
						<td><form:label path="sottocategoria.id">
								<spring:message code="bene.sottocategoria" />
							</form:label></td>
						<td colspan="3"><form:select path="sottocategoria.id" size="1" onchange="submit()">
								<form:option value="-1" label="------Seleziona Sottocategoria-------" />
								<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>
					</tr>
				</logic:notEqual>

				<logic:equal name="beneForm" property="tipologia.id" value="5">
					<tr>
						<td><form:label path="stato.id">
								<spring:message code="stato" />
							</form:label></td>
						<td><form:select id="id_Stato" path="stato.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'Comune')">
								<form:option value="-1" label="-------------------------Seleziona Stato-----------------------" />
								<form:options items="${stati}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>
					</tr>
					<tr>
						<td><form:label path="comune.id">
								<spring:message code="comune" />
							</form:label></td>
						<td>
							<div class="ui-widget">
								<form:input id="id_descrizioneComune" path="comune.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca" />
								<div id="id_loadingComune"></div>
								<form:hidden id="id_hiddenComune" path="comune.id" />
							</div>
						</td>
					</tr>
					<tr>
						<td><form:label path="ragioneSociale">
								<spring:message code="soggettogiuridico.ragioneSociale" />
							</form:label></td>
						<td colspan="3"><form:input path="ragioneSociale" maxlength="200" size="40" /></td>
					</tr>
					<tr>
						<td><form:label path="settoreAttivita.id">
								<spring:message code="soggettogiuridico.settoreAttivita" />
							</form:label></td>
						<td colspan="3"><form:select path="settoreAttivita.id" size="1">
								<option value="-1" label="--------------Seleziona Settore di Attività-----------------------" />
								<form:options items="${settoriAttivita}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>
					</tr>
				</logic:equal>

				<logic:equal name="beneForm" property="tipologia.id" value="4">
					<tr>
						<td><form:label path="stato.id">
								<spring:message code="stato" />
							</form:label></td>
						<td><form:select id="id_Stato" path="stato.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'Comune')">
								<form:option value="-1" label="-------------------------Seleziona Stato-----------------------" />
								<form:options items="${stati}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>
					</tr>
					<tr>
						<td><form:label path="comune.id">
								<spring:message code="comune" />
							</form:label></td>
						<td>
							<div class="ui-widget">
								<form:input id="id_descrizioneComune" path="comune.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca" />
								<div id="id_loadingComune"></div>
								<form:hidden id="id_hiddenComune" path="comune.id" />
							</div>
						</td>
					</tr>
					<tr>
						<td><form:label path="numeroRegistrazione">
								<spring:message code="bene.finanziario.numeroregistrazione"  />
							</form:label></td>
						<td colspan="3"><form:input path="numeroRegistrazione" size="30" maxlength="20" />
						<form:errors path="numeroRegistrazione" class="testoRosso"/></td>
					</tr>
					<tr>
						<td><form:label path="descrizioneEnte">
								<spring:message code="bene.finanziario.descrizioneente" />
							</form:label></td>
						<td colspan="3"><form:input path="descrizioneEnte" size="60" /></td>
					</tr>
				</logic:equal>

				<logic:equal name="beneForm" property="tipologia.id" value="1">
					<tr>
						<td><form:label path="stato.id">
								<spring:message code="stato" />
							</form:label></td>
						<td><form:select id="id_Stato" path="stato.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'Comune')">
								<form:option value="-1" label="-------------------------Seleziona Stato-----------------------" />
								<form:options items="${stati}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>
					</tr>
					<tr>
						<td><form:label path="comune.id">
								<spring:message code="comune" />
							</form:label></td>
						<td>
							<div class="ui-widget">
								<form:input id="id_descrizioneComune" path="comune.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca" />
								<div id="id_loadingComune"></div>
								<form:hidden id="id_hiddenComune" path="comune.id" />
							</div>
						</td>
					</tr>
					<tr>
						<td><form:label path="indirizzo.toponimo">
								<spring:message code="indirizzo" />
							</form:label></td>
						<td><form:select path="indirizzo.toponimo.id" size="1">
								<form:option value="-1" label="---Seleziona---" />
								<form:options items="${tipiStrada}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:input path="indirizzo.denominazione" maxlength="50" size="40" /></td>
						<td><form:label path="indirizzo.civico">
								<spring:message code="civico" />
							</form:label></td>
						<td><form:input path="indirizzo.civico" maxlength="20" size="20" /></td>
					</tr>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="bene.immobile.daticatastali" /></td>
					</tr>
					<tr>
						<td><form:label path="sezione">
								<spring:message code="bene.immobile.sezione" />
							</form:label></td>
						<td><form:input path="sezione" maxlength="4" size="8" /></td>
						<td><form:label path="foglio">
								<spring:message code="bene.immobile.foglio" />
							</form:label></td>
						<td><form:input path="foglio" maxlength="10" /></td>
					</tr>
					<tr>
						<td><form:label path="particella1">
								<spring:message code="bene.immobile.particella" />
							</form:label></td>
						<td><form:input path="particella1" maxlength="5" />/ <form:input path="particella2" maxlength="5" /></td>
						<td><form:label path="sub1">
								<spring:message code="bene.immobile.sub" />
							</form:label></td>
						<td><form:input path="sub1" maxlength="5" />/ <form:input path="sub2" maxlength="5" /></td>
					</tr>
				</logic:equal>

				<logic:equal name="beneForm" property="tipologia.id" value="3">
					<logic:equal name="beneForm" property="categoria.id" value="10">
						<tr>
							<td><form:label path="dataRegistrazione">
									<spring:message code="bene.dataRegistrazione" />
								</form:label>
							<form:label path="dataRegistrazioneMin">
									<spring:message code="procedimento.dataIscrizioneMin" />
								</form:label> </td>
								<td><form:input path="dataRegistrazioneMin" id="dataRegistrazioneMin" maxlength="10" size="12" /> <form:errors path="dataRegistrazioneMin" class="testoRosso" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<b><spring:message code="procedimento.dataIscrizioneMax" /><b>
								<form:input path="dataRegistrazioneMax" id="dataRegistrazioneMax" maxlength="10" size="12" /> <br> <form:errors path="dataRegistrazioneMax" class="testoRosso" /></td>
						</tr>
						<tr>
							<td><form:label path="numRegBeneMobileRegistrato">
									<spring:message code="bene.mobileRegistrato.numeroRegistrazione" />
								</form:label></td>
							<td><form:input path="numRegBeneMobileRegistrato" />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<b><spring:message code="bene.mobileRegistrato.targa" /></b>
								
							<form:input path="targa" /></td>
						</tr>
					</logic:equal>
					<logic:equal name="beneForm" property="categoria.id" value="11">
						<tr>
							<td><form:label path="dataRegistrazione">
									<spring:message code="bene.dataRegistrazione" />
								</form:label></td>
							<td><form:label path="dataRegistrazioneMin">
									<spring:message code="procedimento.dataIscrizioneMin" />
								</form:label> <form:input path="dataRegistrazioneMin" id="dataRegistrazioneMin" maxlength="10" size="12" /> <br> <form:errors path="dataRegistrazioneMin" class="testoRosso" /></td>
							<td><form:label path="dataIscrizioneMax">
									<spring:message code="procedimento.dataIscrizioneMax" />
								</form:label> <form:input path="dataRegistrazioneMax" id="dataRegistrazioneMax" maxlength="10" size="12" /> <br> <form:errors path="dataRegistrazioneMax" class="testoRosso" /></td>
						</tr>
						<tr>
							<td><form:label path="numRegBeneMobileRegistrato">
									<spring:message code="bene.mobileRegistrato.numeroRegistrazione" />
								</form:label></td>
							<td colspan="3"><form:input path="numRegBeneMobileRegistrato" /></td>
						</tr>
					</logic:equal>
				</logic:equal>

				<logic:equal name="beneForm" property="tipologia.id" value="2">
					<tr>
						<td><form:label path="stato.id">
								<spring:message code="stato" />
							</form:label></td>
						<td><form:select id="id_Stato" path="stato.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'Comune')">
								<form:option value="-1" label="-------------------------Seleziona Stato-----------------------" />
								<form:options items="${stati}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>
					</tr>
					<tr>
						<td><form:label path="comune.id">
								<spring:message code="comune" />
							</form:label></td>
						<td>
							<div class="ui-widget">
								<form:input id="id_descrizioneComune" path="comune.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca" />
								<div id="id_loadingComune"></div>
								<form:hidden id="id_hiddenComune" path="comune.id" />
							</div>
						</td>
					</tr>
					<logic:equal name="beneForm" property="categoria.id" value="6">
						<tr>
							<td><form:label path="numeroRegistrazione">
									<spring:message code="bene.finanziario.numeroregistrazione" />
								</form:label></td>
							<td colspan="3"><form:input path="numeroRegistrazione" size="30" maxlength="20" />
							<form:errors path="numeroRegistrazione" class="testoRosso"/></td>
						</tr>
						<tr>
							<td><form:label path="descOperFinanz.descrizione">
									<spring:message code="bene.finanziario.descrizioneente" />
								</form:label></td>
							<td colspan="3"><form:input path="descOperFinanz.descrizione" size="60" /></td>
						</tr>
						<logic:equal name="beneForm" property="sottocategoria.id" value="35">
							<tr>
								<td><form:label path="tipoValuta">
										<spring:message code="bene.finanziario.valuta" />
									</form:label></td>
								<td><form:select path="tipoValuta.id" size="1" onchange="submit()">
										<form:option value="-1" label="-----Seleziona Valuta------" />
										<form:options items="${valuta}" itemValue="id" itemLabel="descrizione" />
									</form:select></td>
							</tr>
						</logic:equal>
					</logic:equal>
				</logic:equal>
			</table>

			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Ricerca" name="Ricerca" onclick="return submitWithAutocompleteComuni(['Stato'],['Comune'],'search')"></td>
					<c:if test="${visualizzaPulsante=='Ricerca' || visualizzaPulsante=='RicercaBDC'}">
						<td class="td_tasto"><input type="submit" value="Ricerca in BDC" name="RicercaBDC" onclick="return submitWithAutocompleteComuni(['Stato'],['Comune'],'search')"></td>
					</c:if>
				</tr>
			</table>
			<br />
			<br />

			<logic:present name="risultatiBaseDati">
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="6"><spring:message code="bene.listabenitrovati" /></td>
					</tr>
				</table>
				<display:table class="tb_lista" uid="risultatiBaseDati" name="${risultatiBaseDati}" pagesize="10" requestURI="${azione}">
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:radiobutton path="idBene" value="${risultatiBaseDati.idBene}" onclick="selezioneBeniBDC(null)" />
					</display:column>
					<display:column property="idBene" title="Identificativo del Bene" sortable="true" />
					<display:column property="descrizioneBene" title="Descrizione del Bene" sortable="true" />
				</display:table>
			</logic:present>
			</br>
			<logic:present name="risultatiBDC">
				<br />
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="6"><spring:message code="bene.listabenitrovatiBDC" /></td>
					</tr>
				</table>
				<display:table class="tb_lista" uid="risultatiBDC" name="${risultatiBDC}" pagesize="10" requestURI="${azione}" >
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:radiobutton path="idBene" value="${risultatiBDC.idBene}" onclick="selezioneBeniBDC(${risultatiBDC.versioneBene})" />
					</display:column>
					<display:column property="idBene" title="Identificativo del Bene" sortable="true" />
					<display:column property="descrizioneBene" title="Descrizione del Bene" sortable="true" />
				</display:table>
			</logic:present>

			<br />
			<br />
			<logic:notEmpty name="risultatiBaseDati">
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" value="Dettaglio bene" name="Dettaglio"></td>
						<td class="td_tasto"><input type="submit" value="Associa bene" name="Associa"></td>
						<c:if test="${visualizzaPulsante=='RicercaBDC'}">
							<td class="td_tasto"><input type="submit" value="Nuovo" name="Nuovo"></td>
						</c:if>
					</tr>
				</table>
			</logic:notEmpty>
			<logic:empty name="risultatiBaseDati">
				<logic:notEmpty name="risultatiBDC">
					<table class="tb_bottoni" align="center">
						<tr>
							<td class="td_tasto"><input type="submit" value="Dettaglio bene" name="Dettaglio"></td>
							<td class="td_tasto"><input type="submit" value="Associa bene" name="Associa"></td>
							<c:if test="${visualizzaPulsante=='RicercaBDC'}">
								<td class="td_tasto"><input type="submit" value="Nuovo" name="Nuovo"></td>
							</c:if>
						</tr>
					</table>
				</logic:notEmpty>
			</logic:empty>
			
			<logic:empty name="risultatiBaseDati">
				<logic:empty name="risultatiBDC">
					<table class="tb_bottoni" align="center">
						<tr>
							<!--  <td class="td_tasto"><input type="submit" value="Dettaglio bene" name="Dettaglio"></td>
							<td class="td_tasto"><input type="submit" value="Associa bene" name="Associa"></td>-->
							<c:if test="${visualizzaPulsante=='RicercaBDC'}">
								<td class="td_tasto"><input type="submit" value="Nuovo" name="Nuovo"></td>
							</c:if>
						</tr>
					</table>
				</logic:empty>
			</logic:empty>

			
		</form:form>
<!--  DG Mev Marzo-Giugno Prog 14 - Errore Compilazione campo Comune dopo ricerca -->
		<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataRegistrazioneMin",false);
					prendiData("#dataRegistrazioneMax",false);
					if($( "#id_Stato" ).val() != undefined){
						inizializzaSubmitAutocomplete('Ricerca');
						inizializzaSubmitAutocomplete('RicercaBDC');
					  	controllaInizializzazioneCampiComune($( "#id_Stato" ).val(),"Comune");
					}
				});
				
				function selezioneBeniBDC(versioneBene) {
					$('#id_hiddenVersioneBene').val(versioneBene);
				}
			</script>

	</tiles:putAttribute>
</tiles:insertDefinition>
