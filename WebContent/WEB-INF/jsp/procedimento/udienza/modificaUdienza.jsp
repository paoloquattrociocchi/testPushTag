<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>



<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/udienza/modificaUdienza.htm" />
		<p class="titolo_page">
		<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_CORTE_D_APPELLO)%>">
			<spring:message code="verbaleUdienza.titolo" />
		</logic:equal>
		<logic:notEqual scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_CORTE_D_APPELLO)%>">
			<spring:message code="udienza.titolo" />
		</logic:notEqual>	
		</p>
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="udienzaForm" enctype="multipart/form-data">
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
		<br />
		<!--nuova gestione verbale udienza -- andrà messa l'intestazione atto anche per gli altri atti -->
		<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_CORTE_D_APPELLO)%>">
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
		</logic:equal>
			<form:hidden path="codUdienza" />
			<form:hidden path="dataUdienzaStr" />
			<form:hidden path="dataUdienza" />
		
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">

				<tr>
					<td width="15%"><form:label path="dataUdienzaStr">
							<spring:message code="udienza.data" />
						</form:label></td>
					<td width="30%"><c:out value="${udienzaForm.dataUdienzaStr}" /></td>

					<td width="10%"><form:label path="">
							<spring:message code="udienza.ora.inizio" />
						</form:label></td>
					<td width="15%"><form:select path="oraInizioUdienza.id" size="1">
							<form:options items="${ore}" itemValue="id" itemLabel="descrizione" />
						</form:select> <form:errors path="oraInizioUdienza.id" class="testoRosso" /> : <form:select path="minutiInizioUdienza.id" size="1">
							<form:options items="${minuti}" itemValue="id" itemLabel="descrizione" />
						</form:select> <form:errors path="minutiInizioUdienza.id" class="testoRosso" /></td>

					<td width="10%"><form:label path="">
							<spring:message code="udienza.ora.fine" />
						</form:label></td>
					<td width="20%"><form:select path="oraFineUdienza.id" size="1">
							<form:option value="-1" label="--hh--" />
							<form:options items="${ore}" itemValue="id" itemLabel="descrizione" />
						</form:select> <form:errors path="oraFineUdienza.id" class="testoRosso" /> : <form:select path="minutiFineUdienza.id" size="1">
							<!--<form:option value="-1" label="--mm--" />-->
							<form:options items="${minuti}" itemValue="id" itemLabel="descrizione" />
						</form:select> <form:errors path="minutiFineUdienza.id" class="testoRosso" /></td>
				</tr>
				<tr>
					<td><form:label path="indirizzoAulaUdienza">
							<spring:message code="indirizzo" />
						</form:label></td>
					<td><form:input path="indirizzoAulaUdienza" value="${udienzaForm.indirizzoAulaUdienza}" maxlength="60" size="55" /> <form:errors path="indirizzoAulaUdienza" class="testoRosso" /></td>
					<td><form:label path="aulaUdienza">
							<spring:message code="atto.udienza.aula" />
						</form:label></td>
					<td colspan="3"><form:input path="aulaUdienza" value="${udienzaForm.aulaUdienza}" maxlength="20" size="20" /> <form:errors path="aulaUdienza" class="testoRosso" /></td>
				</tr>
			</table>

			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo"><spring:message code="udienza.collegio" /></td>
				</tr>
				<tr>
					<td><display:table class="tb_lista" uid="riga" name="${collegioUdienza}">
							<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
								<form:radiobutton path="magistratoForm.id" value="${riga_rowNum-1}" />
							</display:column>
							<display:column property="generalita" title="Nominativo" sortable="false" />
							<display:column property="ruolo.descrizione" title="Ruolo" sortable="false" />
						</display:table></td>
				</tr>
			</table>

			<br />
			<table class="tb_bottoni" align="center">
				<logic:notEmpty name="collegioUdienza">
					<tr>
						<td class="td_tasto"><input type="submit" value="Modifica" name="ModificaMag"></td>
						<td class="td_tasto"><input type="submit" value="Elimina" name="EliminaMag"></td>
						<td class="td_tasto"><input type="submit" value="Nuovo" name="NuovoMag"></td>
					</tr>
				</logic:notEmpty>
				<logic:empty name="collegioUdienza">
					<tr>
						<td class="td_tasto"><input type="submit" value="Nuovo" name="NuovoMag"></td>
					</tr>
				</logic:empty>
			</table>

			<c:if test="${udienzaForm.flagModMag == true}">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="magistrato.modifica.titolo.magistratoScelto" /></td>
					</tr>
					<tr>
						<td><form:label path="">
								<spring:message code="cognome" />
							</form:label></td>
						<td><c:out value="${udienzaForm.magistratoForm.cognome}" /></td>
						<td><form:label path="">
								<spring:message code="nome" />
							</form:label></td>
						<td><c:out value="${udienzaForm.magistratoForm.nome}" /></td>
						<form:hidden path="magistratoForm.id" />
					</tr>
					<tr>
						<td><form:label path="magistratoForm.ruolo.id"><spring:message code="magistrato.ruolo" /></form:label></td>
						<td>
							<form:select path="magistratoForm.ruolo.id" size="1" >
							<form:option value="-1" label="------ Seleziona Ruolo Magistrato--------" />
							<form:options items="${tipoRuoliMagistrati}" itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>
					
						<!-- 
						<td><form:label path="magistratoForm.ruolo">
								<spring:message code="magistrato.ruolo" />
							</form:label></td>
						<td colspan="3">
							<table>
								<tr>
									<td style="border-bottom: none;"><form:radiobutton path="magistratoForm.ruolo" value="PRESIDENTE" /></td>
									<td style="border-bottom: none;"><spring:message code="magistrato.ruolo.presidente" /></td>
									<td style="border-bottom: none;"><form:radiobutton path="magistratoForm.ruolo" value="GIUDICE A LATERE" /></td>
									<td style="border-bottom: none;"><spring:message code="magistrato.ruolo.giudiceALatere" /></td>
								</tr>
							</table>
						</td>
						 -->
					</tr>
				</table>
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" value="Conferma" name="ConfModificaMag"></td>
					</tr>
				</table>
			</c:if>

			<br>
			<c:if test="${udienzaForm.flagNuovoMag == true}">
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="magistrato.titolo.ricerca.udienza" /></td>
					</tr>
					<tr>
						<td><form:label path="magistratoForm.cognome">
								<spring:message code="cognome" />
							</form:label></td>
						<td><form:input path="magistratoForm.cognome" maxlength="30" /> <form:errors path="magistratoForm.cognome" class="testoRosso" /></td>
						<td><form:label path="magistratoForm.nome">
								<spring:message code="nome" />
							</form:label></td>
						<td><form:input path="magistratoForm.nome" maxlength="30" /> <form:errors path="magistratoForm.nome" class="testoRosso" /></td>
					</tr>
				</table>
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" value="Ricerca" name="Ricerca"></td>
					</tr>
				</table>
			</c:if>

			<c:if test="${udienzaForm.flagAssociaMag == true}">
				<display:table class="tb_lista" uid="rigaEl" name="${risultati}" pagesize="10" requestURI="${azione}">
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:radiobutton path="magistratoForm.id" value="${rigaEl.id}" />
					</display:column>
					<display:column property="cognome" title="Cognome" sortable="false" />
					<display:column property="nome" title="Nome" sortable="false" />
				</display:table>

				<br />
				<logic:notEmpty name="risultati">
					<table class="tb_form" align="center">

						<tr>
						
						<td><form:label path="magistratoForm.ruolo.id"><spring:message code="magistrato.ruolo" /></form:label></td>
						<td>
							<form:select path="magistratoForm.ruolo.id" size="1" >
							<form:option value="-1" label="------ Seleziona Ruolo Magistrato--------" />
							<form:options items="${tipoRuoliMagistrati}" itemValue="id" itemLabel="descrizione" /> 																	
							</form:select>
						</td>
						<!-- 
							<td><form:label path="magistratoForm.ruolo">
									<spring:message code="magistrato.ruolo" />
								</form:label></td>
							<td colspan="3">
								<table>
									<tr>
										<td style="border-bottom: none;"><form:radiobutton path="magistratoForm.ruolo" value="PRESIDENTE" /></td>
										<td style="border-bottom: none;"><spring:message code="magistrato.ruolo.presidente" /></td>
										<td style="border-bottom: none;"><form:radiobutton path="magistratoForm.ruolo" value="GIUDICE A LATERE" /></td>
										<td style="border-bottom: none;"><spring:message code="magistrato.ruolo.giudiceALatere" /></td>
									</tr>
								</table>
							</td>
							-->
						</tr>

					</table>
					<table class="tb_bottoni" align="center">
						<tr>
							<td class="td_tasto"><input type="submit" value="Associa" name="Associa"></td>
						</tr>
					</table>
				</logic:notEmpty>
			</c:if>

			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td colspan="4" class="td_titolo"><spring:message code="udienza.pubblicomin" /></td>
				</tr>
				<tr>
					<td><display:table class="tb_lista" uid="rigaPm" name="${listaPmUdienza}">
							<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
								<form:radiobutton path="pmForm.id" value="${rigaPm_rowNum-1}" />
							</display:column>
							<display:column property="generalita" title="PM in Udienza" sortable="false" />
						</display:table></td>
				</tr>
			</table>

			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="15%"><form:label path="listaPm">
							<spring:message code="udienza.nominativo" />
						</form:label></td>
					<td width="85%"><form:select path="listaPm.id" size="1">
							<form:option value="-1" label="--- Seleziona PM ---"></form:option>
							<form:options items="${elMagPmUdienza}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>
			</table>

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Aggiungi" name="AggiungiPM"></td>
					<td class="td_tasto"><input type="submit" value="Elimina" name="EliminaPM"></td>
				</tr>
			</table>

			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="15%"><form:label path="">
							<spring:message code="atto.udienza.cancelliere" />
						</form:label></td>
					<td width="20%"><form:input path="cognomeCancelliere" value="${udienzaForm.cognomeCancelliere}" maxlength="30" size="30" /></td>
					<td width="65%"><form:input path="nomeCancelliere" value="${udienzaForm.nomeCancelliere}" maxlength="30" size="30" /></td>
				</tr>
				<tr>
					<td><form:label path="">
							<spring:message code="note" />
						</form:label></td>
					<td colspan="2"><form:textarea  path="note" cssStyle="width:98%" rows="5"  /></td>
				</tr>
			</table>

			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo"><spring:message code="udienza.soggetticonv" /></td>
				</tr>
				<tr>
					<td><display:table class="tb_lista" uid="elem" name="${soggConvUdienza}">
							<display:column property="descSoggetto" title="Generalità" sortable="false" />
							<display:column property="descrRuolo" title="Ruolo" sortable="false" />
							<display:column title="Presente" sortable="false">
								<form:select path="allFlgPresSogConv[${elem_rowNum-1}]" size="1">
									<form:option value="" label=""></form:option>
									<form:option value="S" label="Si"></form:option>
									<form:option value="N" label="No"></form:option>
								</form:select>
							</display:column>
						</display:table></td>
				</tr>
			</table>

			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="6"><spring:message code="udienza.difensori" /></td>
				</tr>
				<tr>
						<td colspan="6"><display:table class="tb_lista" uid="elem" name="${difUdienza}">
							<display:column property="descSoggetto" title="Generalità" sortable="false" />
							<display:column title="Presente" sortable="false">
								<form:select path="allFlgPresDif[${elem_rowNum-1}]" size="1">
									<form:option value="" label=""></form:option>
									<form:option value="S" label="Si"></form:option>
									<form:option value="N" label="No"></form:option>
									<form:options items="${allFlgPresDif}" itemValue="id" itemLabel="descrizione" />
								</form:select>
							</display:column>
						</display:table></td>
				</tr>
			</table>

			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="15%"><form:label path="altriConv">
							<spring:message code="udienza.altriconv" />
						</form:label></td>
					<td width="85%"><form:input path="altriConv" value="${udienzaForm.altriConv}" maxlength="30" size="40" /></td>
				</tr>
			</table>
		<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegatoUdi.jsp" />
			

			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td><form:label path="esito">
							<spring:message code="udienza.esito" />
						</form:label></td>
					<td><form:select path="esito.id" size="1" onchange="submit()">
							<form:options items="${esiti}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
				</tr>
			</table>

			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<c:if test="${udienzaForm.esito.id != null && udienzaForm.esito.id != -1 && udienzaForm.esito.id != 10}">
					<tr>
						<td class="td_titolo" colspan="5"><spring:message code="udienza.rinvio" /></td>
					</tr>
					<tr>
						<td width="15%"><form:label path="dataRinvio">
								<spring:message code="udienza.rinvio.data" />
							</form:label></td>
						<td width="35%"><form:input path="dataRinvio" id="dataRinvio" maxlength="10" size="12"/> <form:errors path="dataRinvio" class="testoRosso" /></td>
						<td width="10%"><form:label path="oraUdienzaRinvio">
								<spring:message code="udienza.rinvio.ora" />
							</form:label></td>
						<td width="40%"><form:select path="oraUdienzaRinvio.id" size="1">
								<form:option value="-1" label="--hh--" />
								<form:options items="${ore}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:errors path="oraUdienzaRinvio.id" class="testoRosso" /> : <form:select path="minutiUdienzaRinvio.id" size="1">
								<!--<form:option value="-1" label="--mm--" />-->
								<form:options items="${minuti}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:errors path="minutiUdienzaRinvio.id" class="testoRosso" /></td>
					</tr>
					<tr>
						<td><form:label path="indirizzoAulaUdienzaRinvio">
								<spring:message code="indirizzo" />
							</form:label></td>
						<td><form:input path="indirizzoAulaUdienzaRinvio" value="${udienzaForm.indirizzoAulaUdienzaRinvio}" maxlength="60" size="55" /> <form:errors path="indirizzoAulaUdienzaRinvio" class="testoRosso" /></td>
						<td><form:label path="aulaUdienzaRinvio">
								<spring:message code="atto.udienza.aula" />
							</form:label></td>
						<td colspan="3"><form:input path="aulaUdienzaRinvio" value="${udienzaForm.aulaUdienzaRinvio}" maxlength="20" size="20" /> <form:errors path="aulaUdienzaRinvio" class="testoRosso" /></td>
					</tr>
				</c:if>
			</table>
		<br />
		
			<table class="tb_bottoni" align="center">
				<tr>
					<c:if test="${udienzaForm.esito.id == null || udienzaForm.esito.id == -1}">
						<td class="td_tasto"><input type="submit" value="Salva" name="Salva" onclick="inserisciAtto('SalvaModifica', '${udienzaForm.allegato.documentoFirmato}')" title="Salva" class="nowait" ></td>
					</c:if>
					<c:if test="${udienzaForm.esito.id != null && udienzaForm.esito.id != -1}">
						<td class="td_tasto"><input type="submit" value="Salva" name="Salva" disabled="disabled" onclick="inserisciAtto('SalvaModifica', '${udienzaForm.allegato.documentoFirmato}')" title="Salva" class="nowait" ></td>
					</c:if>
					<c:if test="${udienzaForm.esito.id == null || udienzaForm.esito.id == -1}">
						<td class="td_tasto"><input type="submit" value="Conferma e Invia" name="ConfInvia" disabled="disabled" onclick="inserisciAtto('ConfermaInvia', '${udienzaForm.allegato.documentoFirmato}')" title="Conferma e invia" class="nowait" ></td>
					</c:if>
					<c:if test="${udienzaForm.esito.id != null && udienzaForm.esito.id != -1}">
						<td class="td_tasto"><input type="submit" value="Conferma e Invia" name="ConfInvia" onclick="inserisciAtto('ConfermaInvia', '${udienzaForm.allegato.documentoFirmato}')" title="Conferma e invia" class="nowait" ></td>
					</c:if>
					<!-- nuova gestione verbale udienza -->
<%-- 					
					<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_PROCURA)%>">
						<td class="td_tasto"><input type="submit" value="Indietro" name="Indietro"></td>
					</logic:equal>
					<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_PROCURA_GENERALE)%>">
						<td class="td_tasto"><input type="submit" value="Indietro" name="Indietro"></td>
					</logic:equal>
					<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_CORTE_D_APPELLO)%>">
						<td class="td_tasto"><input type="submit" value="Indietro" name="IndietroListaAtti"></td>
					</logic:equal>
					<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_TRIBUNALE)%>">
						<td class="td_tasto"><input type="submit" value="Indietro" name="IndietroListaAtti"></td>
					</logic:equal>
--%>					
					<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_PROCURA)%>">
						<td class="td_tasto">
						<c:if test="${fromMenuAtti=='elenco'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
						</c:if> 
						<c:if test="${fromMenuAtti=='ordine'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
						</c:if> 
						<c:if test="${fromMenuAtti=='nuovo'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/nuovo.htm')">
						</c:if>
						</td>
					</logic:equal>
					<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_PROCURA_GENERALE)%>">
						<td class="td_tasto">
						<c:if test="${fromMenuAtti=='elenco'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
						</c:if> 
						<c:if test="${fromMenuAtti=='ordine'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
						</c:if> 
						<c:if test="${fromMenuAtti=='nuovo'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/nuovo.htm')">
						</c:if>
						</td>
					</logic:equal>
					<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_CORTE_D_APPELLO)%>">
						<td class="td_tasto">
						<c:if test="${fromMenuAtti=='elenco'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
						</c:if> 
						<c:if test="${fromMenuAtti=='ordine'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
						</c:if> 
						<c:if test="${fromMenuAtti=='nuovo'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/nuovo.htm')">
						</c:if>
						</td>
					</logic:equal>
					<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_TRIBUNALE)%>">
						<td class="td_tasto">
						<c:if test="${fromMenuAtti=='elenco'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
						</c:if> 
						<c:if test="${fromMenuAtti=='ordine'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
						</c:if> 
						<c:if test="${fromMenuAtti=='nuovo'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/nuovo.htm')">
						</c:if>
						</td>
					</logic:equal>
										
				</tr>
			</table>
		</form:form>
		
		
		<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataRinvio",false);
				});
			</script>
			
	<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="id_buttonIndietro"></smp:back>
	</tiles:putAttribute>
</tiles:insertDefinition>
