<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/fug/elencoSoggettiCBis.htm" />
		<spring:url var="paginazione" value="/procedimento/bene/fug/paginazioneelencoSoggettiCBis.htm" />
		<p class="titolo_page">
			<c:out value="${beneForm.modelloFugSelected.descrizione}" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm">
		<form:hidden path="idInvio" id="idInvio" />
		<form:hidden path="modelloFugSelected.id" />
		<form:hidden path="idBene" id="idBene" />
		<form:hidden path="codAtto" id="codAtto" />
		<form:hidden path="protocolloRif" id="protocolloRif" />
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene" /> <c:out value="${beneForm.idBene}" /> <wbr> <wbr> - <wbr> <wbr> <c:out value="${beneForm.descrizioneBene}" /></td>
				</tr>
			</table>
	<br />
	<table class="tb_form">
		<tr>
			<td class="td_titolo" colspan="4"><spring:message code="elenco.aventiDiritto" /></td>
		</tr>
	</table>
		
		<display:table class="tb_lista" uid="listaSoggetti" name="${beneForm.listaSoggetti}" pagesize="10"	requestURI="${paginazione}" >
			<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
				<form:radiobutton id="idSelezionato" path="idSelezionato" value="${listaSoggetti.idSoggetto}" />
			</display:column>
			<display:column property="descrizioneSoggetto" title="Generalità" sortable="true"/>
			<display:column property="codiceFiscale" title="Codice Fiscale" sortable="true"/>
			<display:column property="importoQuota" title="Importo quota" sortable="true"/>
		</display:table>
		
			<br />
		<table class="tb_form" cellspacing="0" cellpadding="0" border="0" style= "width: 50%" >	
		<tr>
			<td><form:label path="dataNotifica">
					<spring:message code="dataNotifica" />
				</form:label></td>
			<td>
				<div>
					<form:input path="dataNotifica" maxlength="10" size="12" id="dataNotifica" />
				</div>
			</td>
		
			<td><form:label path="dataIrrevocabileRevoca">
					<spring:message code="dataIrrevocabileRevoca" />
				</form:label></td>
			<td >
				<div>
					<form:input path="dataIrrevocabileRevoca" maxlength="10" size="12" id="dataIrrevocabileRevoca" />
				</div>
			</td>
		</tr>	
		</table>
			<br />
		<table class="tb_bottoni" align="center">
			<tr>
			<logic:notEmpty name="listaSoggetti">
				<td class="td_tasto"><input type="submit" name="InviaModello" value="Invia"></td>
			</logic:notEmpty>	
				<td class="td_tasto"><input type="button" id="backButton" value="Indietro"></td>
			</tr>
		</table>
		</form:form>
			
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
		
		<logic:notEmpty name="listaSoggetti">
			<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataNotifica",true);
				});
			</script>
			<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataIrrevocabileRevoca",true);
				});
			</script>
		</logic:notEmpty>

	</tiles:putAttribute>
</tiles:insertDefinition>