<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/apri/dettaglioProcedimento.htm" />
		<form:form method="post" action="${azione}" commandName="procedimento">
			<p class="titolo_page">
				<spring:message code="procedimento.titoloAccertamento" />
			</p>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="15%"><form:label path="annoNumeroProcedimento">
							<spring:message code="procedimento" />
						</form:label></td>
					<td width="35%"><font size="4"><b><c:out value="${procedimento.annoNumeroProcedimento}" /></b></font></td>
					<td width="15%"><form:label path="dataIscrizione">
							<spring:message code="procedimento.dataIscrizione" />
						</form:label></td>
					<td width="35%"><fmt:formatDate value="${procedimento.dataApertura}" type="date" pattern="dd/MM/yyyy" /></td>
				</tr>
				<tr>
					<td><form:label path="procedente">
							<spring:message code="procedimento.procedente" />
						</form:label></td>
					<td colspan="3"><c:out value="${procedimento.procedente.descrizione}" /></td>
				</tr>
				<tr>
					<td><form:label path="tipoProcedimento">
							<spring:message code="procedimento.tipo" />
						</form:label></td>
					<td><c:out value="${procedimento.tipoProcedimento.descrizione}" /></td>
					<td><form:label path="statoProcedimento">
							<spring:message code="procedimento.stato" />
						</form:label></td>
					<td><c:out value="${procedimento.statoProcedimento.descrizione}" /></td>
					<!--<td colspan="3"><c:out value="${procedimento.procedente.id}" /></td>-->
				</tr>
				<tr>
					<td><form:label path="tipologiaPrevenzione">
							<spring:message code="procedimento.tipologiaPrevenzione" />
						</form:label>
					</td>
					<td>
						<c:if test="${procedimento.antimafia==true}">
								<form:hidden path="antimafia" />
								<c:out value="ANTIMAFIA" />
						</c:if> 
						<c:if test="${procedimento.antimafia==false}">
							<c:if test="${procedimento.antiterrorismo==true}">
								<form:hidden path="antiterrorismo" />
								<c:out value="ANTITERRORISMO" />
							</c:if>
							<c:if test="${procedimento.antiterrorismo==false}">
								<c:if test="${procedimento.ordinaria==true}">
									<form:hidden path="ordinaria" />
									<c:out value="ORDINARIA" />
								</c:if>
							</c:if>
						</c:if>
					</td>
					<td></td>
					<td></td>
				</tr>				
				<logic:notEmpty name="procedimentiFigliStralcio">
					<tr>
						<td width="15%">
						<form:label path="tipologiaPrevenzione">
							<spring:message code="stralcio.procedimentiFigliStralcio" />
							</form:label>
						</td>
						<td colspan="3" width="85%">
							${procedimentiFigliStralcio}
						</td>
					</tr>
				</logic:notEmpty>
				<logic:notEmpty name="procedimentiPadreStralcio">
					<tr>
						<td width="15%">
						<form:label path="tipologiaPrevenzione">
							<spring:message code="stralcio.procedimentiPadreStralcio" />
							</form:label>
						</td>
						<td colspan="3" width="85%">
							${procedimentiPadreStralcio.numeAnnoProc} / ${procedimentiPadreStralcio.numeProc}
						</td>
					</tr>
				</logic:notEmpty>
				<logic:notEmpty name="riunitoAlNumero">
					<tr>
						<td width="15%"><form:label path="tipologiaPrevenzione">
								<spring:message code="riunione.riunitoAlNumero" />
							</form:label></td>
						<td colspan="3" width="85%">${riunitoAlNumero.numeAnnoProc} / ${riunitoAlNumero.numeProc}</td>
					</tr>
				</logic:notEmpty>
				<logic:notEmpty name="riunisceIlNumero">
					<tr>
						<td width="15%"><form:label path="tipologiaPrevenzione">
								<spring:message code="riunione.riunisceIlNumero" />
							</form:label></td>
						<td colspan="3" width="85%">${riunisceIlNumero}</td>
					</tr>
				</logic:notEmpty>
			</table>

			<br />
			<br />

			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettidelprocedimento" /></td>
				</tr>
			</table>
			<display:table class="tb_lista" uid="direttiInteressati" name="${direttiInteressati}" pagesize="10" requestURI="${azione}">
				<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" />
				<display:column property="dataIscrizione" title="Data di iscrizione" format="{0,date,dd/MM/yyyy}" sortable="true" />
			</display:table>
			<br />
			<br />
			<logic:notEmpty name="numeriRegistroAccertamento">
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<c:forEach var="numReg" items="${numeriRegistroAccertamento}">
						<tr>
							<td class="td_titolo"><form:label path="ordinaria">
									<spring:message code="procedimento.accertamento.numeriRegistroAccertamento" arguments="${numReg.annoNumeroProcedimento }" /></td>
							</form:label>
						</tr>
					</c:forEach>
				</table>
			</logic:notEmpty>
			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
				<logic:equal scope="session" name="idTipoUfficio" value="17">
				<c:if test="${procedimento.statoProcedimento.id != null && (procedimento.statoProcedimento.id == 34 || procedimento.statoProcedimento.id == 2) }">
				<logic:notPresent scope="request" name="pulsanteAnnulla">
					<td class="td_tasto"><input type="submit" name="annullaProcedimento" value="Annulla Procedimento"></td>
				</logic:notPresent>
				<logic:present scope="request" name="pulsanteAnnulla">
				<td class="td_tasto"><input type="submit" name="confermaAnnullaProcedimento" value="Conferma Annullamento"></td>
				</logic:present>
				</c:if>
				</logic:equal>
				
					<td class="td_tasto"><input type="button" id="id_buttonIndietro" value="Indietro"></td>
				</tr>
			</table>

		</form:form>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="id_buttonIndietro"></smp:back>
		
	</tiles:putAttribute>
</tiles:insertDefinition>