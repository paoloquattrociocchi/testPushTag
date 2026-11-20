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
			<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.correzione" /></p>
			<spring:url var="azione" value="/procedimento/nuovo/correzione/ricerca.htm" />
			
			<form:form id="id_Correzioni" method="post" action="${azione}" commandName="form">
			<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td colspan="6" align="left"><spring:message code="cameralini.dettaglioProcedimento" /></td>
					</tr>
		  			<jsp:include page="/WEB-INF/jsp/cameralini/intestazioneDettaglio.jsp" />
			     </table>
	 			 			   			     
	<br /><br />
	<form:hidden id="idTipoSoggetto" path="tipoSoggetto" />
	<form:hidden id="idTipoUfficio" path="tipoUfficio" />
	<form:hidden id="idFunzioneCameralini" path="funzioneCameralini" />
	<form:hidden id="id_StatoSedeLegaleDesc" path="statoSedeLegale.descrizione" />
	<form:hidden id="id_StatoNascitaDesc" path="statoNascita.descrizione" />
	
		<%-- INIZIO MODIFICA PERSONA FISICA -----------------------------------     --%>		
	<c:if test="${form.tipoSoggetto == 'F'}">
		<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
						<td class="td_titolo" colspan="6"><spring:message code="cameralini.correzionePersonaFisica" /></td>
					</tr>
				<tr>
				</tr>
					<tr>
					<td><form:label path="cognome" cssClass="required"> <spring:message code="cameralini.prevenzione.correzione.cognome" /> </form:label></td>
					<td><form:input path="cognome" id="cognome" maxlength="35" size="30" value="${form.cognome}" />
					<form:errors path="cognome" class="testoRosso" /></td>

					<td><form:label path="nome" cssClass="required"> <spring:message code="cameralini.prevenzione.correzione.nome" /> </form:label></td>
					<td><form:input path="nome" id="nome" maxlength="35" size="30" 	value="${form.nome}" />
					<form:errors path="nome" class="testoRosso" /></td>
				</tr>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
							<td colspan="6"><i> Dati relativi alla nascita </i></td>
					</tr>
					<tr>
						 <fmt:formatDate pattern="dd/MM/yyyy" value="${form.dataNascita}" var="myDateVar"/> 
						 <td><form:label path="dataNascita" cssClass="required"> <spring:message code="dataNascita" /> </form:label></td>
							<td><form:input path="dataNascita" maxlength="10" size="12" id="dataNascita" value="${myDateVar}" />
							<form:errors path="dataNascita" class="testoRosso" /> </td>
							
							<td><form:label path="statoNascita.id" cssClass="required"> <spring:message code="stato" />
								</form:label></td> 

							<c:set var="statoSelez" value="${form.statoNascita.id}" />
							<td><form:select id="id_StatoNascita" path="statoNascita.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneNascita'); settaDescrizioneTrasc(this);">
									<form:option value="-1" label="---Seleziona Stato---" />
									<c:forEach items="${statiNascita}" var="statBelf" varStatus="status">
										<c:choose>
											<c:when test="${statBelf.id eq statoSelez}">
												<option value="${statBelf.id}" selected="true">${statBelf.descrizione}</option>
											</c:when>
											<c:otherwise>
												<option value="${statBelf.id}">${statBelf.descrizione}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select> <form:errors path="statoNascita.id" class="testoRosso" /></td>
							<td><form:label path="comuneNascita.id" cssClass="required"> <spring:message code="cameralini.prevenzione.correzione.comune" /> </form:label></td>
							<td>
							<div class="ui-widget">
									<form:input id="id_descrizioneComuneNascita" path="comuneNascita.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca" />
									<div id="id_loadingComuneNascita"></div> <form:hidden id="id_hiddenComuneNascita"
										path="comuneNascita.id" /> <form:errors path="comuneNascita.descrizione" class="testoRosso" />
							</div>
					    </td>	
						  </tr>
					</table>
				    <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
					<td><form:label path="sesso" cssClass="required"> <spring:message code="cameralini.prevenzione.correzione.sesso" /> </form:label></td>
					<td><form:select path="sesso" id="sessoCombo">
						<form:option value="-1" label="---Seleziona Sesso---"/>
							<c:choose>
								<c:when test="${form.sesso == 'M'}">
									<form:option value="M" label="Maschio" selected="true" />
								</c:when>
								<c:otherwise>
									<form:option value="M" label="Maschio" />
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${form.sesso == 'F'}">
									<form:option value="F" label="Femmina" selected="true" />
								</c:when>
								<c:otherwise>
									<form:option value="F" label="Femmina" />
								</c:otherwise>
							</c:choose>
						</form:select> 
						<form:errors path="sesso" class="testoRosso" />
					</td>
					<td><form:label path="codiceFiscale"> <spring:message code="cameralini.prevenzione.correzione.codiceFiscale" /> </form:label></td>
					<td><form:input path="codiceFiscale" id="codiceFiscale" maxlength="16" size="30" value="${form.codiceFiscale}" />
					</td>
				</tr>
				
				
				</table>	
				</table>
				</c:if>
			
		<%-- INIZIO MODIFICA PERSONA GIURIDICA -----------------------------------     --%>
		<c:if test="${form.tipoSoggetto == 'G'}">
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="cameralini.correzionePersonaGiuridica" /></td>
					</tr>
						<br/>		
							<tr>
								<td><form:label path="partitaIva" cssClass="required"> <spring:message code="cameralini.prevenzione.correzione.partitaIva" /> </form:label></td>
								
								<td  colspan="3">
									<form:select id="id_StatoSiglaPiva" path="partitaIvaSiglaStato" size="1">
										<form:options items="${statiPiva}" itemValue="id" itemLabel="descrizione" />
									</form:select> 
									<form:input path="partitaIva" maxlength="12" size="18" value="${form.partitaIva}"/> 
									<form:errors path="partitaIva" class="testoRosso" />
								</td>
		
							</tr>

							<tr>
								<td><form:label path="ragioneSociale" cssClass="required"> <spring:message code="cameralini.prevenzione.correzione.ragioneSociale" /></form:label></td>
								<td colspan="3"><form:input path="ragioneSociale" id="ragioneSociale" maxlength="50" size="51" value="${form.ragioneSociale}" />
								<form:errors path="ragioneSociale" class="testoRosso" />
								</td>
							</tr>
							<tr>
								<td colspan="4"><i> Dati relativi alla sede legale </i></td>
							</tr>
							<tr>
							<td><form:label path="statoSedeLegale.id" cssClass="required">
									<spring:message code="stato" />
								</form:label></td>
							<c:set var="statoSedeLegaleSel" value="${form.statoSedeLegale.descrizione}" />
							<td><form:select id="id_StatoSedeLegale" path="statoSedeLegale.id" size="1"	onchange="controllaCampiEAutocomplete(this.value,'ComuneSedeLegale'); settaDescrizioneSedeTrasc(this);"> 
									<form:option value="-1" label="---Seleziona Stato---" />
								<c:forEach items="${stati}" var="statBelf" varStatus="status">
										<c:choose>
											<c:when test="${statBelf.descrizione eq statoSedeLegaleSel}">
												<option value="${statBelf.id}" selected="true">${statBelf.descrizione}</option>
											</c:when>
											<c:otherwise>
												<option value="${statBelf.id}">${statBelf.descrizione}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select> <form:errors path="statoSedeLegale.id" class="testoRosso" /></td>
								<td><form:label path="comuneSedeLegale.id" cssClass="required"> <spring:message code="comune" />
								</form:label></td> 							
								<td>
								<div class="ui-widget"> 
								<form:input  id="id_descrizioneComuneSedeLegale" path="comuneSedeLegale.descrizione" size="50" 
										title="Immettere almeno 2 lettere per avviare la ricerca" /> 
									<div id="id_loadingComuneSedeLegale"></div>
									<form:hidden id="id_hiddenComuneSedeLegale" path="comuneSedeLegale.id" /> 
									<form:errors path="comuneSedeLegale.descrizione" class="testoRosso" />
								</div>

					<tr>
						<td><form:label path="indirizzoSedeLegale.toponimo" ><spring:message code="indirizzo" /></form:label></td>
						<td>						
							<form:select path="indirizzoSedeLegale.toponimo.id" size="1">	
								<form:option value="-1" label="---Seleziona---"/>
								<form:options items="${tipiStrada}"  itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
							<form:input path="indirizzoSedeLegale.denominazione" maxlength="50" size="40"/>
						</td>
						<td><form:label path="indirizzoSedeLegale.civico">
								<spring:message code="civico" />
							</form:label></td>
						<td colspan="2"><form:input path="indirizzoSedeLegale.civico" maxlength="20" size="20"/></td>
					</tr>
							</tr>	
						</table> 
				</c:if>
			
			<%-- FINE MODIFICA PERSONA GIURIDICA -----------------------------------     --%>
		 <br />  
				<table class="tb_bottoni" align="center">
					<tr>
					    <td class="td_tasto"><input type="submit" value="Registra" name="Registra" title="Registra"></td>
                       	<td class="td_tasto"><input type="submit" name="IndietroDettaglio" title="Indietro" value="Indietro"/></td>
					</tr>
				</table>
				<br/>
			     
			     
			</form:form>

		<c:if test="${form.tipoSoggetto=='F'}">
			<script type="text/javascript">
					$(document).ready(function() {
						prendiData("#dataNascita",true);
					});
			</script>
			<script type="text/javascript">
				if ($("#id_StatoNascitaDesc").val() == 'ITALIA') {
					doAutocomplete('ComuneNascita');
				}
			</script>
		</c:if>

		<c:if test="${form.tipoSoggetto=='G'}">
			<script type="text/javascript">
				if ($("#id_StatoSedeLegale").val() == 'ITALIA') {
					doAutocomplete('ComuneSedeLegale');
				}
			</script>
		</c:if>
		
		<script type="text/javascript">
			function settaDescrizioneTrasc (valore) {
				var e = document.getElementById("id_StatoNascita");
				var statoSelez = e.options[e.selectedIndex].text;
				document.getElementById("id_StatoNascitaDesc").value=statoSelez;
			}
		</script>

		<script type="text/javascript">
			function settaDescrizioneSedeTrasc (valore) {
				var e = document.getElementById("id_StatoSedeLegale");
				var statoSedeLegaleSel = e.options[e.selectedIndex].text;
				document.getElementById("id_StatoSedeLegaleDesc").value=statoSedeLegaleSel;
			}
		</script>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
<smp:back buttonId="backButton"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>