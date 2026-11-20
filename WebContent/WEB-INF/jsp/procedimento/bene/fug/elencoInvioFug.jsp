<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script>
	function selectID(idInvio, statoInvio) {
		document.getElementById("selIdInvio").value = idInvio;
		document.forms[0].statoInvio.value=statoInvio;
	}
</script>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/fug/elencoInvioFug.htm" />
		<spring:url var="paginazione" value="/procedimento/bene/fug/paginazioneElencoInvioFug.htm" />
		<p class="titolo_page">
			<spring:message code="elenco.modelli" />
		</p>
		<form:form method="post" action="${azione}" commandName="fugForm">
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			
 	<br>	
		<table class="tb_form">
			<tr>
				<td class="td_titolo" colspan="4">
				<form:checkbox path="visualizzaFiltro" onclick="this.form.submit()"/>
				<spring:message code="fug.filtroinvii" /></td>
			</tr>
		<logic:equal name="fugForm" property="visualizzaFiltro" value="true">
			<tr>
				<td><form:label path="modelloFugSelected.id">
					<spring:message code="fug.modello.label"/></form:label></td>
					<td colspan="4"><form:select path="modelloFugSelected.id" onchange="this.form.submit()">
						<form:option value="-1" label="-------Seleziona Modello -------"></form:option>
						<form:option value="A" label="Modello A"></form:option>
						<form:option value="C" label="Modello C"></form:option>
						<form:option value="C2" label="Modello C-Bis"></form:option>
						<form:option value="D" label="Modello D"></form:option>
						<form:option value="H" label="Modello H"></form:option>
					</form:select>
					<form:hidden path="modelloFugSelected.descrizione"/>
				</td>
			</tr>
			<tr>
				<td><form:label path="dataInvioDa">
							<spring:message code="fug.dataInvioDa" />
						</form:label></td>
				<td colspan="4"><form:input path="dataInvioDa" maxlength="10" id="id_dataInvioDa"  onchange="this.form.submit()" />
				<form:hidden path="dataInvioDa"/>
				</td>
			</tr>
			<tr>
				<td><form:label path="dataInvioA">
							<spring:message code="fug.dataInvioA" />
						</form:label></td>
				<td colspan="4"><form:input path="dataInvioA" maxlength="10" id="id_dataInvioA"  onchange="this.form.submit()" />
				<form:hidden path="dataInvioA"/>
				</td>
			</tr>
		</logic:equal>
		</table>
		
		<form:hidden path="idInvio" id="selIdInvio" />
		<form:hidden path="statoInvio" id="selstatoInvio" />
		
	<br />
	<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td class="td_titolo" colspan="4"><spring:message code="elenco.modelli.sottotitolo" /></td>
		</tr>
	</table>
		<logic:notEmpty name="modelliInviati">
		   <display:table  id="row" class="tb_lista" uid="modelliInviati" name="${modelliInviati}" pagesize="10" requestURI="${paginazione}">
				<logic:equal name="checkSi" value="${modelliInviati.descProtocollo}">
					<display:column style="text-align: center; width: 5%" title="Seleziona" sortable="false">
						<input type="radio" name="radioIDS"  id="radioID_${modelliInviati_rowNum}" onclick="selectID('${modelliInviati.idInvio}','${modelliInviati.statoInvio}');" />
					</display:column>
				</logic:equal>
				<logic:notEqual name="checkSi" value="${modelliInviati.descProtocollo}">
					<display:column style="text-align: center; width: 5%" title="Seleziona" sortable="false">
						<input type="radio" name="radioIDS" id="radioID_${modelliInviati_rowNum}" onclick="selectID('${modelliInviati.idInvio}','${modelliInviati.statoInvio}');" />
					</display:column>
				</logic:notEqual>
				<display:column title="Bene" sortable="true" value="${modelliInviati.idBene}"/>
				<display:column style="width: 30%" title="" sortable="true" value="${modelliInviati.descBene}"/>
				<display:column title="Numero atto" sortable="true" value="${modelliInviati.numeAtto}"/>
				<display:column title="Modello" sortable="true" value="${modelliInviati.descModello}"/>
				<display:column property="dataInvio" title="Data invio" format="{0,date,dd/MM/yyyy}" sortable="true" />
				<display:column title="Protocollo invio" sortable="true" value="${modelliInviati.descProtocollo}"/>
				<display:column title="Esito" sortable="true" value="${modelliInviati.statoInvio}"/>
				<display:column property="dataDefinitaPosizione" title="Data definita posizione" format="{0,date,dd/MM/yyyy}" sortable="true" />
				<display:column title="Importo"  property="importoEuro" sortable="true" />
			</display:table>
		</logic:notEmpty>
		
		<logic:empty name="modelliInviati">
		   <display:table  id="row" class="tb_lista" uid="modelliInviati" name="${modelliInviati}" pagesize="10" requestURI="${paginazione}">
				<display:column style="text-align: center; width: 5%" title="Seleziona" sortable="false">
					<form:radiobutton path="id" value="${modelliInviati.idInvio}" />
				</display:column>
				<display:column title="Bene"  property="idBene"/>
				<display:column style="width: 30%" title=""  property="descBene"/>
				<display:column title="Numero atto"  property="numeAtto"/>
				<display:column title="Modello"  property="descModello"/>
				<display:column title="Data invio"  property="dataInvio"/>
				<display:column title="Protocollo invio"  property="descProtocollo"/>
				<display:column title="Esito"  property="statoInvio"/>
				<display:column title="Data definita posizione"  property="dataDefinitaPosizione"/>
			</display:table>
		</logic:empty>
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<logic:notEmpty name="modelliInviati">
						<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio"></td>
						<td class="td_tasto"><input type="submit" name="AnnullaModello" value="Annulla modello"></td>
					</logic:notEmpty>
				</tr>
			</table>
		</form:form>
			
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
		<script type="text/javascript">
			
			$(document).ready(function() {
				prendiData("#id_dataInvioDa",true);
				prendiData("#id_dataInvioA",true);
			});
			
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>

