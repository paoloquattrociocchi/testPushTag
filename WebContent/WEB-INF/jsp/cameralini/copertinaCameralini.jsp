<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/nuovo/cameralini/copertinaAmministratore.htm" />
		<form:form method="post" action="${azione}" commandName="form">
		<form:hidden id="ufficioSelezionatoDesc" path="ufficioSelezionatoDesc" />
			
			<c:if test="${form.funzioneCameralini =='terzi'}">
				<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.ricerca.terzi" /></p>
			</c:if>
			<c:if test="${form.funzioneCameralini =='amministratore'}">
				<p class="titolo_page"><spring:message code="procedimento.titoloAmministratore" /></p>
			</c:if>
			<c:if test="${form.funzioneCameralini =='misuraPatrimoniale'}">
				<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.misurapatrimoniale" /></p>
			</c:if>
			<c:if test="${form.funzioneCameralini =='correzione'}">
				<p class="titolo_page"><spring:message code="cameralini.prevenzione.titolo.correzione" /></p>
			</c:if>
			
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="15%"><form:label path="procedPadre.anno"><spring:message code="cameralini.prevenzione.procedimento"/></form:label></td>
						<td width="35%"><font size="4"><b><c:out value="${form.procedCameralini.anno}" />/<c:out value="${form.procedCameralini.numero}" /></b></font></td>
						<td width="15%"><form:label path="procedPadre.dataIscrizione"><spring:message code="procedimento.dataIscrizione" /></form:label></td>
						<td width="35%"><fmt:formatDate value="${form.procedCameralini.dataApertura}" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>
					
					<c:if test="${form.procedPadre.numero !=null && form.procedPadre.anno !=null}">
						<tr>
							<td><form:label path="procedPadre.anno">
									<spring:message code="procedimento.appellativoProcedimentoPadre" />
								</form:label></td>
							<td colspan="3">
								<c:out value="${form.procedPadre.anno}" />/<c:out value="${form.procedPadre.numero}" />
								<c:if test="${form.procedPadre.ufficioPadre!=null}">
									<c:out value="${form.procedPadre.ufficioPadre}" />
								</c:if>
							</td>
						</tr>
					</c:if>
					<tr>
						<td><form:label path="procedPadre.tipoProcedimento"><spring:message code="procedimento.tipo" /></form:label></td>
						<td><c:out value="${form.procedCameralini.tipoProcedimento.descrizione}" /></td>
						<td><form:label path="procedPadre.statoProcedimento"><spring:message code="procedimento.stato" /></form:label></td>
						<td><c:out value="${form.procedCameralini.statoProcedimento.descrizione}" /></td>
					</tr>
				</table>
				<br />
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettoIntestatario" /></td>
						</tr>
					</table>
					
					<c:if test="${form.funzioneCameralini =='amministratore'}">

						<display:table class="tb_lista" uid="listaAmministratori" name="${listaAmministratori}" pagesize="10" requestURI="${azione}" >
								<display:column property="descrizioneAmministratore" title="Generalità" sortable="false"/>
						</display:table>
					
					</c:if>
					
					<c:if test="${form.funzioneCameralini !='amministratore'}">
					
						<display:table class="tb_lista" uid="listaIntestatari" name="${listaIntestatari}" pagesize="10" requestURI="${azione}" >
								<display:column property="descrizioneAmministratore" title="Generalità" sortable="false"/>
						</display:table>
						
					</c:if>
					
				<br />
				<logic:notEmpty name="listaSoggetti">	
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettidelprocedimento" /></td>
						</tr>
					</table>
					<display:table class="tb_lista" uid="listaSoggetti" name="${listaSoggetti}" pagesize="10" requestURI="${azione}" >
							<display:column property="descrizioneSoggetto" title="Generalità" sortable="false"/>
					</display:table>
				</logic:notEmpty>	
					<br />
					<br />
					<br />
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="procedimento.numeriregistro" /></td>
						</tr>
					</table>
					<display:table class="tb_lista" uid="numeriRegistro" name="${numeriRegistro}" pagesize="10" requestURI="${azione}">
						<display:column property="ufficio" title="Ufficio" sortable="true" />
						<display:column property="procedimento" title="Procedimento" sortable="true" />
						<display:column property="dataIscrizione" title="Data iscrizione" format="{0,date,dd/MM/yyyy}" sortable="true" />
						<display:column property="iter" title="Stato" sortable="true" />
					</display:table>
					<br />
					<br />
					<logic:notEmpty name="numeriRegistroAccertamento">
						<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
							<c:forEach var="numReg" items="${numeriRegistroAccertamento}">
								<tr>
									<td class="td_titolo"><form:label path="ordinaria">
											<spring:message code="procedimento.accertamento.numeriRegistroPrevenzione" arguments="${numReg.annoNumeroProcedimento }" /></td>
									</form:label>
								</tr>
							</c:forEach>
						</table>
					</logic:notEmpty>
	 		
			
				<table class="tb_bottoni" align="center">
					<tr>
					<logic:equal scope="session" name="idTipoUfficio" value="19">
						<c:if test="${form.procedCameralini.statoProcedimento.id != null && (form.procedCameralini.statoProcedimento.id == 5 || form.procedCameralini.statoProcedimento.id == 47)   }">
							<logic:notPresent scope="request" name="pulsanteAnnulla">
								<td class="td_tasto"><input type="submit" name="annullaProcedimento" value="Annulla Procedimento" disabled="disabled"></td>
							</logic:notPresent>
							<logic:present scope="request" name="pulsanteAnnulla">
								<td class="td_tasto"><input type="submit" name="confermaAnnullaProcedimento" value="Conferma Annullamento"></td>
								<td class="td_tasto"><input type="button" id="id_buttonIndietro" value="Indietro" name="indietro"></td>
							</logic:present>
						</c:if>
					</logic:equal>
					
					<logic:equal scope="session" name="idTipoUfficio" value="6">
						<c:if test="${form.procedCameralini.statoProcedimento.id != null && form.procedCameralini.statoProcedimento.id == 5  }">
							<logic:notPresent scope="request" name="pulsanteAnnulla">
								<td class="td_tasto"><input type="submit" name="annullaProcedimento" value="Annulla Procedimento" disabled="disabled"></td>
							</logic:notPresent>
							<logic:present scope="request" name="pulsanteAnnulla">
								<td class="td_tasto"><input type="submit" name="confermaAnnullaProcedimento" value="Conferma Annullamento"></td>
								<td class="td_tasto"><input type="button" id="id_buttonIndietro" value="Indietro" name="indietro"></td>
							</logic:present>
						</c:if>
					</logic:equal>
						
					</tr>
				</table>		
	 	
		<br />
		<br />
		<br />
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>