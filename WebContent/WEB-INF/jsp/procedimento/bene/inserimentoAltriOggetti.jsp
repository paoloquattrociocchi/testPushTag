<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/inserimentoAltriOggetti.htm" />
		<p class="titolo_page">
			<spring:message code="bene.mobile.inserimento" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm" id="idFormInserimento">
		<form:hidden path="tipologia.id"/>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br/>
				 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br/>
			<table class="tb_form" border=0>
				<tr>
					<td colspan="4" class="td_titolo">
						<form:label path="">
							<spring:message code="bene.mobile.datirelativi" />
						</form:label>
					</td>
				</tr>
				<tr>
					<td><form:label path="categoria.id" cssClass="required">
							<spring:message code="bene.categoria" />
						</form:label>
					</td>
					<td  colspan="4">
						<form:select path="categoria.id" size="1" disabled="true">
							<form:option value="" label="------Seleziona Categoria-------" />
							<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
						</form:select>
						<form:errors path="categoria.id" class="testoRosso"/>
					</td>
				</tr>				
				<tr>
					<td><form:label path="sottocategoria.id" cssClass="required"><spring:message code="bene.sottocategoria" /></form:label></td>
					<td  colspan="4">
						<form:select id="id_sottocategoria" path="sottocategoria.id" size="1"> <!-- da fare obbligatoria -->
							<form:option value="" label="------Seleziona Sottocategoria-------" />
							<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
						</form:select>
						<form:hidden id="desc_sottocategoria" path="sottocategoria.descrizione" />
						<form:errors path="sottocategoria.id" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td><form:label path="stato.id" ><spring:message code="stato"/></form:label></td>
		        	<td colspan="3">
						<form:select id="id_Stato" path="stato.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'Comune')">	
							<form:option value="-1" label="-------------------------Seleziona Stato-----------------------"/>
							<form:options items="${stati}"  itemValue="id" itemLabel="descrizione" /> 																
						</form:select>
		        	</td>
				</tr>	
				<tr>
					<td><form:label path="comune.id" ><spring:message code="comune"/></form:label></td>					
				   	<td colspan="3">
		        		<div class="ui-widget">		
							<form:input id="id_descrizioneComune" path="comune.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
							<div id="id_loadingComune"></div>
							<form:hidden id="id_hiddenComune" path="comune.id"/>
						</div>
		        	</td>
				</tr>	
				<tr>
					<td><form:label path="indirizzo"><spring:message code="indirizzo" /></form:label></td>
					<td>
						<form:select id="id_tipostrada" path="indirizzo.toponimo.id" size="1">
						 	<form:option value="-1" label="------Seleziona Tipo Strada-------"/>
							<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" />
						</form:select> 
						<form:hidden id="desc_tipostrada" path="indirizzo.toponimo.descrizione" />
						<form:input path="indirizzo.denominazione" maxlength="50" size="30"/>
					</td>
					<td><form:label path="indirizzo.civico"><spring:message code="civico" /></form:label></td>
					<td><form:input path="indirizzo.civico" /></td>
				</tr>						
				<tr>
					<td><form:label path="descrizioneBene" cssClass="required">
							<spring:message code="bene.mobile.altro.descrizione" />
						</form:label>
					</td>
					<td colspan="3"><form:input path="descrizioneBene" maxlength="250" size="70" /><form:errors path="descrizioneBene" class="testoRosso"/></td>
					
				</tr>
				<tr>
					<td><form:label path="gravame">
							<spring:message code="bene.gravame" />
						</form:label>
					</td>
					<td>
					 <form:radiobutton path="gravame" value="S" label="SI"/>
					 <form:radiobutton path="gravame" value="N" label="NO"/>
				   </td>
					<td><form:label path="note">
							<spring:message code="note" />
						</form:label>
					</td>
					<td><form:textarea path="note" rows="5" />
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
				prendiData("#dataRegistrazioneMin",false);
				prendiData("#dataRegistrazioneMax",false);
				inizializzaSubmitAutocomplete('SalvaEAssocia');
				if($( "#id_Stato" ).val() != undefined){
				  	controllaInizializzazioneCampi($( "#id_Stato" ).val(),"Comune");
				}

				//elimino tutti i disable prima di fare submit
				$('#idFormInserimento').bind('submit', function() {
			        $(this).find(':disabled').removeAttr('disabled');

			        //imposto la descrizione della sotto categoria
			        var desc = $("#id_sottocategoria :selected").text();
			        $('#desc_sottocategoria').val(desc);

			        submitWithAutocompleteComuni(['Stato'],['Comune'],'write');
			    });
			});
			
		</script>			
		
		<script type="text/javascript">
			if($( "#id_Stato" ).val() == 1)
			{
				doAutocomplete('Comune');
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
