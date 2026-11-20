<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/smp.tld" prefix="smp" %>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<c:set var="ISTANZA_AUTORIZZAZIONE_UTILIZZO_BENI" value="<%=Constants.ISTANZA_AUTORIZZAZIONE_UTILIZZO_BENI%>"/>
<c:set var="ISTANZA_AUTORIZZAZIONE_AMMINISTRARE_BENI_IN_COMUNIONE" value="<%=Constants.ISTANZA_AUTORIZZAZIONE_AMMINISTRARE_BENI_IN_COMUNIONE%>"/>
<c:set var="ISTANZA_AMMINISTRAZIONE_ORDINARIA" value="<%=Constants.ISTANZA_AMMINISTRAZIONE_ORDINARIA%>"/>
<c:set var="ISTANZA_AMMINISTRAZIONE_STRAORDINARIA" value="<%=Constants.ISTANZA_AMMINISTRAZIONE_STRAORDINARIA%>"/>
<c:set var="ISTANZA_CONTO_GESTIONE" value="<%=Constants.ISTANZA_CONTO_GESTIONE%>"/>
<c:set var="RICHIESTA_RINUNCIA_AMMINISTRATORI" value="<%=Constants.RICHIESTA_RINUNCIA_AMMINISTRATORI%>"/>
<c:set var="RELAZIONE_AMMINISTRATORE" value="<%=Constants.RELAZIONE_AMMINISTRATORE%>"/>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/attiIstanzaFascicoloGestione.htm" />
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			<form:hidden path="codAtto" />
			<form:hidden path="statoAtto.id" />
			<form:hidden path="statoAtto.descrizione" />
			<form:hidden path="tipoAtto.id" />
			<form:hidden path="categoriaAtto.id" />
			
			<p class="titolo_page">
				<c:if test="${attoForm.categoriaAtto.id == ISTANZA_AUTORIZZAZIONE_UTILIZZO_BENI}" >
					<spring:message code="atto.istanzaAutorizzazioneUtilizzoBeni" />				
				</c:if>
				<c:if test="${attoForm.categoriaAtto.id == ISTANZA_AUTORIZZAZIONE_AMMINISTRARE_BENI_IN_COMUNIONE}" >
					<spring:message code="atto.istanzaAutorizzazioneAmministrareBeni" />				
				</c:if>
				<c:if test="${attoForm.categoriaAtto.id == ISTANZA_AMMINISTRAZIONE_ORDINARIA}" >
					<spring:message code="atto.istanzaAutorizzazioneOrdinaria" />				
				</c:if>
				<c:if test="${attoForm.categoriaAtto.id == ISTANZA_AMMINISTRAZIONE_STRAORDINARIA}" >
					<spring:message code="atto.istanzaAutorizzazioneStraordinaria" />				
				</c:if>		
				<c:if test="${attoForm.categoriaAtto.id == ISTANZA_CONTO_GESTIONE}" >
					<spring:message code="atto.istanzaContoGestione" />				
				</c:if>			
				<c:if test="${attoForm.categoriaAtto.id == RICHIESTA_RINUNCIA_AMMINISTRATORI}" >
					<spring:message code="atto.istanzaRinunciaAmministratori" />				
				</c:if>							
						
			</p>
			
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />

			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
						
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />

			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>

				<c:if test="${attoForm.categoriaAtto.id == ISTANZA_CONTO_GESTIONE || attoForm.categoriaAtto.id == RELAZIONE_AMMINISTRATORE}" >
					<tr>
						<td><form:label path="periodoRifDal">
							<spring:message code="atto.periodoRiferimento" />
							</form:label>
						</td>		
						<td colspan="3">
							<table>
								<tr>				
									<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
										<td><form:label path="periodoRifDal" cssClass="required">
												<spring:message code="atto.dal" />
											</form:label></td>
										<td><form:input path="periodoRifDal" maxlength="10" size="12" id="periodoRifDal" /> <br> <form:errors path="periodoRifDal" class="testoRosso" /></td>
									</c:if>
									<c:if test="${attoForm.dataRegistrazione!=null || attoForm.statoAtto.id==3}">
										<td><form:label path="periodoRifDal">
												<spring:message code="atto.dal" />
											</form:label></td>
										<td><fmt:formatDate value="${attoForm.periodoRifDal}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="periodoRifDal" /></td>
									</c:if>
									<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
										<td><form:label path="periodoRifAl" cssClass="required">
												<spring:message code="atto.al" />
											</form:label></td>
										<td><form:input path="periodoRifAl" maxlength="10" size="12" id="periodoRifAl" /> <br> <form:errors path="periodoRifAl" class="testoRosso" /></td>
									</c:if>
									<c:if test="${attoForm.dataRegistrazione!=null || attoForm.statoAtto.id==3}">
										<td><form:label path="periodoRifAl">
												<spring:message code="atto.al" />
											</form:label></td>
										<td><fmt:formatDate value="${attoForm.periodoRifAl}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="periodoRifAl" /></td>
									</c:if>
								</tr>
							</table>
						</td>
					</tr>
				</c:if>					
				
				<tr>
					<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
						<td><form:label path="dataRicezione" cssClass="required">
								<c:if test="${attoForm.categoriaAtto.id != RICHIESTA_RINUNCIA_AMMINISTRATORI && attoForm.categoriaAtto.id != RELAZIONE_AMMINISTRATORE}" >
									<spring:message code="atto.dataricezione" />
								</c:if>
								<c:if test="${attoForm.categoriaAtto.id == RICHIESTA_RINUNCIA_AMMINISTRATORI || attoForm.categoriaAtto.id == RELAZIONE_AMMINISTRATORE}" >
									<spring:message code="atto.datapervenuto" />
								</c:if>
							</form:label></td>
						<td><form:input path="dataRicezione" maxlength="10" size="12" id="dataRicezione" /> <br> <form:errors path="dataRicezione" class="testoRosso" /></td>
					</c:if>
					<c:if test="${attoForm.dataRegistrazione!=null || attoForm.statoAtto.id==3}">
						<td><form:label path="dataRicezione">
								<spring:message code="atto.dataricezione" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataRicezione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataRicezione" /></td>
					</c:if>
				</tr>
				
				<tr>
					<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
						<td><form:label path="note" cssClass="required">
								<spring:message code="richiesta" />
							</form:label></td>
						<td colspan="3"><form:textarea path="note" rows="2" cssStyle="width:98%" /><br> <form:errors path="note" class="testoRosso" /></td>
					</c:if>
					<c:if test="${attoForm.dataRegistrazione!=null || attoForm.statoAtto.id==3}">
						<td><form:label path="note">
								<spring:message code="richiesta" />
							</form:label></td>
							<td><c:out value="${attoForm.note}" /> <form:hidden path="note" /></td>
					</c:if>
				</tr>
				
				<tr>	
					<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
						<td><form:label path="numeroRiferimento">
								<spring:message code="atto.numeroriferimento" />
							</form:label></td>
						<td><form:input path="numeroRiferimento" maxlength="20" id="numeroRiferimento" /> <br> <form:errors path="numeroRiferimento" class="testoRosso" /></td>
						<td></td>
						<td></td>
					</c:if>
					<c:if test="${attoForm.dataRegistrazione!=null || attoForm.statoAtto.id==3}">
						<td><form:label path="numeroRiferimento">
								<spring:message code="atto.numeroriferimento" />
							</form:label></td>
						<td><c:out value="${attoForm.numeroRiferimento}" /> <form:hidden path="numeroRiferimento" /></td>
						<td></td>
						<td></td>
					</c:if>
				</tr>					

				<c:if test="${attoForm.categoriaAtto.id != ISTANZA_CONTO_GESTIONE && attoForm.categoriaAtto.id != RICHIESTA_RINUNCIA_AMMINISTRATORI && attoForm.categoriaAtto.id != RELAZIONE_AMMINISTRATORE}" >
					<tr>
						<td>
							<form:label path="tipoProponente.id" cssClass="required">
								<spring:message code="atto.tipoProponente" />
							</form:label>
						</td>
						<td>
							<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
								<form:select path="tipoProponente.id" size="1" onchange="changeTipoProponente(this.value)" id="tipoProponente">
									<form:option value="-1" label="------Seleziona Proponente-------" />
									<form:options items="${tipiProponenti}" itemValue="id" itemLabel="descrizione" />
								</form:select>
								<form:errors path="tipoProponente.id" class="testoRosso" />
							</c:if>
							<c:if test="${attoForm.dataRegistrazione!=null || attoForm.statoAtto.id==3}">		
								<form:select path="tipoProponente.id" size="1" id="tipoProponente" disabled="true">
									<form:option value="-1" label="------Seleziona Proponente-------" />
									<form:options items="${tipiProponenti}" itemValue="id" itemLabel="descrizione" />
								</form:select>
								<form:errors path="tipoProponente.id" class="testoRosso" />
							</c:if>										
						</td>
						<td></td>
						<td></td>					
					</tr>
				</c:if>
				<c:if test="${attoForm.categoriaAtto.id == ISTANZA_CONTO_GESTIONE || attoForm.categoriaAtto.id == RICHIESTA_RINUNCIA_AMMINISTRATORI || attoForm.categoriaAtto.id == RELAZIONE_AMMINISTRATORE}" >
					<form:hidden path="tipoProponente.id" id="tipoProponente"/>
				</c:if>
					
				<tr>
					<td>
						<c:if test="${attoForm.categoriaAtto.id != ISTANZA_CONTO_GESTIONE && attoForm.categoriaAtto.id != RELAZIONE_AMMINISTRATORE}" >
							<form:label path="amministratore.id">
								<spring:message code="atto.amministratore" />
							</form:label>
						</c:if>
		
						<c:if test="${attoForm.categoriaAtto.id == ISTANZA_CONTO_GESTIONE || attoForm.categoriaAtto.id == RELAZIONE_AMMINISTRATORE}" >
							<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}"> 
							<form:label path="amministratore.id" cssClass="required">
								<spring:message code="atto.amministratore" />
							</form:label>
							</c:if>
							<c:if test="${attoForm.dataRegistrazione!=null || attoForm.statoAtto.id==3}">
							<form:label path="amministratore.id">
								<spring:message code="atto.amministratore" />
							</form:label>
							</c:if>
						</c:if>

					</td>				
					<td>
						<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">					
							<form:select path="amministratore.id" size="1" id="amministratore" disabled="true">
								<form:option value="-1" label="------Seleziona Amministratore-------" />
								<form:options items="${amministratori}" itemValue="id" itemLabel="descrizione" />
							</form:select>
						<form:errors path="amministratore.id" class="testoRosso" />		
						</c:if>
						<c:if test="${attoForm.dataRegistrazione!=null || attoForm.statoAtto.id==3}">
							<form:select path="amministratore.id" size="1" id="amministratore" disabled="true">
								<form:option value="-1" label="------Seleziona Amministratore-------" />
								<form:options items="${amministratori}" itemValue="id" itemLabel="descrizione" />
							</form:select>
							<form:errors path="amministratore.id" class="testoRosso" />		
						</c:if>						
					</td>	
					<c:if test="${attoForm.categoriaAtto.id != ISTANZA_CONTO_GESTIONE && attoForm.categoriaAtto.id != RICHIESTA_RINUNCIA_AMMINISTRATORI && attoForm.categoriaAtto.id != RELAZIONE_AMMINISTRATORE}" >
						<td>
							<form:label path="altroOrgano">
								<spring:message code="atto.descrEnteAltro" />
							</form:label>
						</td>					
						<td>
							<form:input path="altroOrgano" maxlength="60" size="60" id="altroOrgano" disabled="true"/>
							<form:errors path="altroOrgano" class="testoRosso" />						
						</td>
					</c:if>
					<c:if test="${attoForm.categoriaAtto.id == ISTANZA_CONTO_GESTIONE || attoForm.categoriaAtto.id == RICHIESTA_RINUNCIA_AMMINISTRATORI || attoForm.categoriaAtto.id == RELAZIONE_AMMINISTRATORE}" >
						<td></td>
						<td></td>
					</c:if>
				</tr>
				<c:if test="${attoForm.categoriaAtto.id != ISTANZA_CONTO_GESTIONE && attoForm.categoriaAtto.id != RICHIESTA_RINUNCIA_AMMINISTRATORI && attoForm.categoriaAtto.id != RELAZIONE_AMMINISTRATORE}" >
					<tr>
						<td>
							<form:label path="cognProponente">
								<spring:message code="atto.cognProponente" />
							</form:label>
						</td>						
						<td>
							<form:input path="cognProponente" maxlength="60" size="60" id="cognProponente" disabled="true"/>
							<form:errors path="cognProponente" class="testoRosso" />						
						</td>
						<td>
							<form:label path="nomeProponente">
								<spring:message code="atto.nomeProponente" />
							</form:label>
						</td>							
						<td>
							<form:input path="nomeProponente" maxlength="60" size="60" id="nomeProponente" disabled="true"/>
							<form:errors path="nomeProponente" class="testoRosso" />						
						</td>									
					</tr>
				</c:if>				
			</table>
			<br />
			<br />
 
<c:if test="${attoForm.categoriaAtto.id == RELAZIONE_AMMINISTRATORE}" >	
        <logic:present name="listaBeni">				
			 <logic:notEmpty name="listaBeni">	             
				
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="depositi.listaBeniRelazione" /></td>
						</tr>
					</table>
		            <display:table class="tb_lista" uid="bene" name="${listaBeni}" >				
						<display:column property="idBene" title="Identificativo" sortable="false" />
						<display:column property="descrizioneBene" title="Descrizione" sortable="false" />					
					</display:table>
					
					<br />
			        <br />		
			        
			 </logic:notEmpty>  
		</logic:present>	       			
</c:if>			
			
			<table class="tb_bottoni" align="center">
				<tr>
					<c:if test="${attoForm.dataRegistrazione==null}">
						<c:if test="${attoForm.statoAtto.id!=3}">

							<td class="td_tasto"><input type="button" value="Salva" onclick="inserisciAtto('Salva', '${attoForm.allegato.documentoFirmato}')" title="Salva inserimento atto" class="nowait" /></td> 
							<input	id="Salva" type="submit" name="Salva" value="" style="display: none" />
													
							<td class="td_tasto"><input type="button" value="Conferma" onclick="inserisciAtto('Conferma', '${attoForm.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" /></td> 
							<input	id="Conferma" type="submit" name="Conferma" value="" style="display: none" />
						
						</c:if>
					</c:if>

                    <td class="td_tasto">
                    	<c:if test="${fromMenuAtti=='elenco'}">
                            <input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
                        </c:if> 
                        <c:if test="${fromMenuAtti=='ordine'}">
                            <input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
                        </c:if> 
                        <c:if test="${fromMenuAtti=='nuovo'}">
                            <input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
                        </c:if>
                    </td>					
				</tr>
			</table>
			<br />
			<br />
		</form:form>

		<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
			<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataRicezione",true);
				});

				changeTipoProponente(document.getElementById("tipoProponente").value);
				
				function changeTipoProponente(value) {

					if(value=="AMMINISTRATORE") {
						document.getElementById("amministratore").disabled = false;
						document.getElementById("cognProponente").disabled = true;
						document.getElementById("cognProponente").value = "";
						document.getElementById("nomeProponente").disabled = true;
						document.getElementById("nomeProponente").value = "";						
						document.getElementById("altroOrgano").disabled = true;
						document.getElementById("altroOrgano").value="";
					} else if(value=="ENTE") {
						document.getElementById("altroOrgano").disabled = false;
						document.getElementById("cognProponente").disabled = false;
						document.getElementById("nomeProponente").disabled = false;
						document.getElementById("amministratore").value = "-1";
						document.getElementById("amministratore").disabled = true;
					} else if(value=="ALTRO") {
						document.getElementById("altroOrgano").disabled = false;
						document.getElementById("cognProponente").disabled = false;
						document.getElementById("nomeProponente").disabled = false;
						document.getElementById("amministratore").value = "-1";						
						document.getElementById("amministratore").disabled = true;
					}
					
				}				
			</script>
		</c:if>
		
		<c:if test="${attoForm.dataRegistrazione==null && attoForm.statoAtto.id!=3}">
			<c:if test="${attoForm.categoriaAtto.id == ISTANZA_CONTO_GESTIONE || attoForm.categoriaAtto.id == RELAZIONE_AMMINISTRATORE}" >
				<script type="text/javascript">
					$(document).ready(function() {
						prendiData("#periodoRifDal",true);
						prendiData("#periodoRifAl",true);
					});
				</script>	
			</c:if>
		</c:if>
		
	</tiles:putAttribute>
</tiles:insertDefinition>