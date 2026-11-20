<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/elencoAlboAmministratoriAtto.htm" />
		<p class="titolo_page">
			<spring:message code="albo.elenco.titolo" />
		</p>
		<form:form method="post" action="${azione}" commandName="amministratoreForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<display:table class="tb_lista" name="${risultatiRicerca}" uid="amministratore" pagesize="10" requestURI="${azione}">
				<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
					<form:radiobutton path="idAmministratore" value="${amministratore.idAmministratore}" />
				</display:column>
				<display:column property="cognome" title="Cognome" sortable="false" />
				<display:column property="nome" title="Nome" sortable="false" />
				<display:column property="dataNascita" title="Data di Nascita" sortable="false" />
				<display:column property="luogoNascita" title="Luogo di Nascita" sortable="false" />
				<display:column property="codiceFiscale" title="Codice Fiscale" sortable="false" />
				<display:column property="alboProfessionale.descrizione" title="Albo Professionale" sortable="false" />
				<display:column property="dataIscrizione" title="Data Iscrizione" sortable="false" />
				<display:column property="descrSezione" title="Sezione Iscrizione" sortable="false" />
				<display:column property="provinciaDomProf.descrizione" title="Provincia Domicilio" sortable="false" />
				<display:column property="sospeso" title="Sospeso" sortable="false" />
			</display:table>

			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="button" name="Associa" title="Associa" value="Associa" /></td>
					<td class="td_tasto"><input type="button" value="Indietro" onClick="history.back()"></td>
				</tr>
			</table>

		</form:form>

	</tiles:putAttribute>
</tiles:insertDefinition>