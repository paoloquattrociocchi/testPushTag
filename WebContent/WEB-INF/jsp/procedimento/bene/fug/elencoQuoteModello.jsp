<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>

<script>
	function selectID(codAtto, importo, dataDep, dataDef, numeAtto) {
		document.getElementById("selCodAtto").value = codAtto;
		document.getElementById("selImporto").value = importo;
		document.getElementById("selNumeAtto").value = numeAtto;
		
		var app=dataDep.substring(8)+'/'+dataDep.substring(5,7)+'/'+dataDep.substring(0,4);
		document.forms[0].dataDeposito.value=app;
		
		var appDataDef=dataDef.substring(8)+'/'+dataDef.substring(5,7)+'/'+dataDef.substring(0,4);
		document.forms[0].dataDefinitivita.value=appDataDef;
		
	}
</script>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/fug/elencoQuoteModello.htm" />
		<spring:url var="paginazione" value="/procedimento/bene/fug/paginazioneelencoQuoteModello.htm" />
		<p class="titolo_page">
			<c:out value="${beneForm.modelloFugSelected.descrizione}" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm">
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene" /> <c:out value="${beneForm.idBene}" /> <wbr> <wbr> - <wbr> <wbr> <c:out value="${beneForm.descrizioneBene}" /></td>
				</tr>
			</table>
		<form:hidden path="idBene" />
		<form:hidden path="modelloFugSelected.id" />
		<form:hidden path="codAtto" id="selCodAtto" />
		<form:hidden path="importo" id="selImporto" />
		<form:hidden path="numeAtto" id="selNumeAtto" />
		<form:hidden path="dataDeposito" id="selDataDeposito" />
		<form:hidden path="dataDefinitivita" id="selDataDefinitivita" />
	<br />
	<table class="tb_form">
		<tr>
			<td class="td_titolo" colspan="4"><spring:message code="elenco.quote.sottotitolo" /></td>
		</tr>
	</table>
		<logic:notEmpty name="listaQuote">
			<display:table class="tb_lista" uid="listaQuote" name="${listaQuote}" pagesize="10" requestURI="${paginazione}">
				<c:if test="${beneForm.modelloFugSelected.id != 'C2'}">
					<display:column style="text-align: center; width: 5%" title="Seleziona">
					<logic:present name="ceckAutomatico">
						<script> selectID('${listaQuote.codAtto}','${listaQuote.importo}','${listaQuote.dataDeposito}','${listaQuote.dataDefinitivita}','${listaQuote.numeAtto}');</script>
						<input type="radio" name="radioIDS" id="radioID_${listaQuote_rowNum}" checked="checked" onclick="selectID('${listaQuote.codAtto}','${listaQuote.importo}','${listaQuote.dataDeposito}','${listaQuote.dataDefinitivita}','${listaQuote.numeAtto}');" />
					</logic:present>
					<logic:notPresent name="ceckAutomatico">
						<input type="radio" name="radioIDS" id="radioID_${listaQuote_rowNum}" onclick="selectID('${listaQuote.codAtto}','${listaQuote.importo}','${listaQuote.dataDeposito}','${listaQuote.dataDefinitivita}','${listaQuote.numeAtto}');" />
					</logic:notPresent>
					</display:column>
				</c:if>
				<display:column title="Atto di riferimento" sortable="false" value="${listaQuote.categoriaAtto.descrizione} ${listaQuote.numeroProcedimentoOrigine}"/>
				<display:column title="Numero atto" sortable="false" value="${listaQuote.numeAtto}"/>
				<display:column property="dataDeposito" title="Data deposito" format="{0,date,dd/MM/yyyy}" sortable="false" />
				<c:if test="${beneForm.modelloFugSelected.id == 'D'}">
					<display:column property="dataDefinitivita" title="Data definitività" format="{0,date,dd/MM/yyyy}" sortable="false" />
				</c:if>
				<display:column title="Importo" sortable="false" value="${listaQuote.importo}"/>
				
				<c:if test="${beneForm.modelloFugSelected.id == 'C'}">
					<display:column title="Titolari di diritto">
					<display:table class="tb_sotto_lista" uid="soggetto" name="${listaQuote.soggetti}" requestURI="${azioneR}">
						<display:column property="descrizioneSoggetto" title="Soggetto" />
						<display:column property="descrizioneFrazioneOQuota" title="Quota" />
						<display:column property="importoQuota" title="Importo" />
					</display:table>
			</display:column>
				</c:if>
				
			</display:table>
		</logic:notEmpty>
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" name="InviaModello" value="Invia"></td>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro"></td>
				</tr>
			</table>
		</form:form>
			
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>