<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>
<%@ page import="it.giustizia.smp.form.AttoForm"%>

<%
//J.C. SITMP 271
request.getSession().setAttribute("amministratoreAnagraficaJspVisited","VISITED");
//jaya
%>
<!-- J.C - SITMP 281: Creo variabile di appoggio -->
<c:set var="tipoUfficio" value="<%=(Integer) request.getSession().getAttribute("idTipoUfficio")%>"/>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione"  value="/procedimento/amministratore/gestioneAnagrafica.htm" />
		<form:form method="post" action="${azione}" commandName="amministratoreForm" id="idForm">
			
			<form:hidden path="id" />
			<form:hidden path="jspProvenienza" />
			<form:hidden path="dettaglio" />
			
			<c:if test="${amministratoreForm.id == null}">
				<p class="titolo_page">
					<spring:message code="amministratore.titolo.nuovo.Amministratore" />
				</p>
			</c:if>
			<c:if test="${amministratoreForm.id != null}">
				<c:if test="${amministratoreForm.dettaglio != 'DETTAGLIO'}">
					<p class="titolo_page">
						<spring:message code="amministratore.titolo.aggiorna.Amministratore" />
					</p>
				</c:if>
				<c:if test="${amministratoreForm.dettaglio == 'DETTAGLIO'}">
					<p class="titolo_page">
						<spring:message code="amministratore.titolo.dettaglio.Amministratore" />
					</p>
				</c:if>
			</c:if>
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
				<td class="td_titolo" colspan="4"><spring:message code="nuovosoggettofisico.titolo1" /></td>
				</tr>
			   
		       	<tr>
		       	<c:if test="${amministratoreForm.dettaglio != 'DETTAGLIO'}">
		       	<tr>
					<td><form:label path="cognome" cssClass="required">
							<spring:message code="cognome" />
						</form:label></td>
					<td><form:input path="cognome" maxlength="35" size="30" />
					 <form:errors path="cognome" class="testoRosso" /></td>
					<td><form:label path="nome" cssClass="required">
							<spring:message code="nome" />
						</form:label></td>
					<td><form:input path="nome" maxlength="35" size="30" /> 
					<form:errors path="nome" class="testoRosso" /></td>
				</tr>
				
				<tr>
					<td><form:label path="dataNascita"> <!--  cssClass="required"> -->
							<spring:message code="dataNascita" />
						</form:label></td>
					<td>
						<div>
							<form:input path="dataNascita" maxlength="10" size="12"
								id="dataNascita" />
							<!--<form:errors path="dataNascita" class="testoRosso" /> -->
						</div>
					</td>
					<td><form:label path="luogoNascita">
						<spring:message code="luogoNascita" />
						</form:label></td>
					<td><form:input path="luogoNascita" size="50" /></td>
				</tr>
				
				<tr>
				<td class="td_titolo" colspan="4"><spring:message
						code="nuovoamministratore.titolo3" /></td>
				</tr>
				<tr>
				
				<tr>
					<td><form:label path="statoDomProf.id" cssClass="required">
							<spring:message code="stato" />
						</form:label></td>
					<td><form:select id="id_StatoDomProf" path="statoDomProf.id"
							size="1"
							onchange="controllaCampiEAutocomplete(this.value,'ComuneDomProf')">
							<form:option value="-1" label="---Seleziona Stato---" />
							<form:options items="${stati}" itemValue="id"
								itemLabel="descrizione" />
						</form:select> 
						<form:errors path="statoDomProf.id" class="testoRosso" />
					</td>
					<td><form:label path="comuneDomProf.id" cssClass="required">
							<spring:message code="comune" />
						</form:label></td>
					<td>
						<div class="ui-widget">
							<form:input id="id_descrizioneComuneDomProf"
								path="comuneDomProf.descrizione" size="50"
								title="Immettere almeno 2 lettere per avviare la ricerca" />
							<div id="id_loadingComuneDomProf"></div>
							<form:hidden id="id_hiddenComuneDomProf" path="comuneDomProf.id" />
							<form:errors path="comuneDomProf.descrizione" class="testoRosso" />
						</div>
					</td>
				</tr>
				<tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message
						code="nuovosoggettofisico.titolo2" /></td>
				</tr>
				<tr>
					<td><form:label path="codiceFiscale"> <!--  cssClass="required"> -->
						<spring:message code="codiceFiscale" />
						</form:label></td>
					<td><form:input path="codiceFiscale" maxlength="16" size="25" />
					<!-- <form:errors path="codiceFiscale" class="testoRosso" /> -->
					</td>
					<td><form:label path="partitaIva">
						<spring:message code="partitaIva" />
						</form:label></td>
					<td><form:input path="partitaIva" maxlength="11" size="25" /></td>
				</tr>
				<tr>
					<td><form:label path="descStudio">
						<spring:message code="descStudio" />
						</form:label></td>
					<td><form:input path="descStudio"  size="50" /></td>
					<td><form:label path="pec">
						<spring:message code="pec" />
						</form:label></td>
					<td><form:input path="pec"  size="50" /></td>
				</tr>
				<tr>
					<td><form:label path="tel">
						<spring:message code="tel" />
						</form:label></td>
					<td><form:input path="tel"  size="25" /></td>
					<td><form:label path="fax">
						<spring:message code="fax" />
						</form:label></td>
					<td><form:input path="fax" size="25" /></td>
				</tr>
			
			</c:if>
			
			
			<c:if test="${amministratoreForm.dettaglio == 'DETTAGLIO'}">
				<tr>
				<td><form:label path="cognome">
						<spring:message code="cognome" />
					</form:label></td>
				<td><c:out value="${amministratoreForm.cognome}" />
					<form:hidden path="cognome" /></td>
				<td><form:label path="nome">
						<spring:message code="nome" />
					</form:label></td>
				<td colspan="2"><c:out value="${amministratoreForm.nome}" />
					<form:hidden path="nome" /></td>
				</tr>
				<tr>
					<td><form:label path="dataNascita">
							<spring:message code="dataNascita" />
						</form:label></td>
					<td>
						<div>
							<fmt:formatDate value="${amministratoreForm.dataNascita}" type="date"
								pattern="dd/MM/yyyy" />
							<form:hidden path="dataNascita" />
						</div>
					</td>
					
					<td><form:label path="luogoNascita">
							<spring:message code="luogoNascita" />
						</form:label></td>
					<td colspan="2"><c:out value="${amministratoreForm.luogoNascita}" />
						<form:hidden path="luogoNascita" /></td>
				</tr>
				<tr>
				<td class="td_titolo" colspan="5"><spring:message
						code="nuovoamministratore.titolo3" /></td>
				</tr>
				<tr>
					<td><form:label path="statoDomProf.id">
							<spring:message code="stato" />
						</form:label></td>
					<td><c:out value="${amministratoreForm.statoDomProf.descrizione}" />
						<form:hidden path="statoDomProf.id" />
						<form:hidden path="statoDomProf.descrizione" /></td>
					<td><form:label path="comuneDomProf.id">
							<spring:message code="comune" />
						</form:label></td>
					<td colspan="2"><c:out value="${amministratoreForm.comuneDomProf.descrizione}" />
						<form:hidden path="comuneDomProf.id" />
						<form:hidden path="comuneDomProf.descrizione" />
				</tr>
				<tr>
					<td class="td_titolo" colspan="5"><spring:message
							code="nuovosoggettofisico.titolo2" /></td>
				</tr>
				<tr>
					<td><form:label path="codiceFiscale">
							<spring:message code="codiceFiscale" />
						</form:label></td>
					<td><c:out value="${amministratoreForm.codiceFiscale}" />
						<form:hidden path="codiceFiscale" /></td>
				
					<td><form:label path="partitaIva">
							<spring:message code="partitaIva" />
						</form:label></td>
					<td colspan="2"><c:out value="${amministratoreForm.partitaIva}" />
						<form:hidden path="partitaIva" /></td>
				</tr>
				<tr>
					<td><form:label path="descStudio">
							<spring:message code="descStudio" />
						</form:label></td>
					<td><c:out value="${amministratoreForm.descStudio}" />
						<form:hidden path="descStudio" /></td>
						<td><form:label path="pec">
							<spring:message code="pec" />
						</form:label></td>
					<td colspan="2"><c:out value="${amministratoreForm.pec}" />
						<form:hidden path="pec" /></td>
				</tr>
				<tr>
					<td><form:label path="tel">
							<spring:message code="tel" />
						</form:label></td>
					<td><c:out value="${amministratoreForm.tel}" />
						<form:hidden path="tel" /></td>
						<td><form:label path="fax">
							<spring:message code="fax" />
						</form:label></td>
					<td colspan="2"><c:out value="${amministratoreForm.fax}" />
						<form:hidden path="fax" /></td>
				</tr>
			</c:if>
			</table>
			<br />
			<br />
			
			<table class="tb_bottoni" align="center">
				<tr>
					<c:if test="${amministratoreForm.id == null}">
						<td class="td_tasto"><input type="submit"
							name="Inserisci" title="Inserisci nuovo amministratore" value="Inserisci"></td>
					</c:if>
					<c:if test="${amministratoreForm.id != null}">
						<c:if test="${amministratoreForm.dettaglio != 'DETTAGLIO'}">
							<td class="td_tasto"><input type="submit" name="Salva" title="Modifica dati anagrafici amministratore" value="Salva"></td>
							<c:if test="${amministratoreForm.jspProvenienza == 'ATTO' && tipoUfficio != 19}">  <!-- J.C - SITMP 281: Aggiunto && tipoUfficio != 19 ( tribunale ) -->
								<td class="td_tasto"><input type="submit" value="Associa Beni" name="Associa"></td>				
							</c:if>
						</c:if>
					</c:if>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro"></td>				
				</tr>
				
			</table>
			
			<br />
			<br />
		</form:form>

    	<c:if test="${amministratoreForm.id == null}">
			<script type="text/javascript">
				$(document).ready(
						function() {
							prendiData("#dataNascita", true);

							controllaInizializzazioneCampi(
									$("#id_StatoDomProf").val(),
									"ComuneDomProf");
							
						});
			</script>
		</c:if>

		<script type="text/javascript">
			if ($("#id_StatoDomProf").val() == 1) {
				doAutocomplete('ComuneDomProf');
			}
			
		</script>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>
</body>
</html>