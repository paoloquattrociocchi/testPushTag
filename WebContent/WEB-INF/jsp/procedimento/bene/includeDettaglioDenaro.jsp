<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="it.giustizia.smp.utility.Constants"%>
<c:set var="DESC_ENTE_FITTIZIO" value="<%=Constants.EnteFittizio.DESCRIZIONE%>"/>

					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="bene.denaro.datirelativi" /></td>
					</tr>
					
					<tr>
						<td width="15%"><form:label path=""><spring:message code="bene.finanziario.descrizioneente" /></form:label></td>
						<td colspan="3">
							<c:if test="${beneForm.descOperFinanz.descrizione != null && beneForm.descOperFinanz.descrizione != DESC_ENTE_FITTIZIO}">
								<c:out value="${beneForm.descOperFinanz.descrizione}"/>
							</c:if>
							<c:if test="${beneForm.descrizioneEnte!=null}">
								<c:out value="${beneForm.descrizioneEnte}"/>
							</c:if>
						</td>
					</tr>
					
					<tr>
						<td width="15%"><form:label path="codiceAbi"><spring:message code="bene.finanziario.abi" /></form:label></td>
						<td colspan="3">
							<c:if test="${beneForm.descOperFinanz.descrizione != null && beneForm.descOperFinanz.descrizione != DESC_ENTE_FITTIZIO}">
								<c:out value="${beneForm.codiceAbi}"/>
							</c:if>
						</td>
					</tr>
					
					<tr>
						<td width="15%"><form:label path="codiceCab"><spring:message code="bene.finanziario.cab" /></form:label></td>
						<td colspan="3">
							<c:if test="${beneForm.descOperFinanz.descrizione != null && beneForm.descOperFinanz.descrizione != DESC_ENTE_FITTIZIO}">						
								<c:out value="${beneForm.codiceCab.id}"/>
							</c:if>
						</td>
					</tr>
					
					<tr>
						<td width="15%"><form:label path="indirizzoBeneFin"><spring:message code="bene.finanziario.indirizzo" /></form:label></td>
						<td colspan="3">
							<c:if test="${beneForm.descOperFinanz.descrizione != null && beneForm.descOperFinanz.descrizione != DESC_ENTE_FITTIZIO}">
								<c:out value="${beneForm.indirizzoBeneFin}"/>
							</c:if>								
						</td>
					</tr>
					
					<tr>
						<td width="15%"><form:label path="codiceIban"><spring:message code="bene.finanziario.iban" /></form:label></td>
						<td colspan="1"><c:out value="${beneForm.codiceIban}" /></td>
						<td width="15%"><form:label path="codiceSwift"><spring:message code="bene.finanziario.swift" /></form:label></td>
						<td colspan="1"><c:out value="${beneForm.codiceSwift}" /></td>
					</tr>	
					
					<tr>
						<td width="15%"><form:label path="numeroRegistrazione"><spring:message code="bene.denaro.conto" /></form:label></td>
						<td>
							<c:if test="${beneForm.descOperFinanz.descrizione != null && beneForm.descOperFinanz.descrizione != DESC_ENTE_FITTIZIO}">
								<c:out value="${beneForm.numeroRegistrazione}" />
							</c:if>
						</td>
						<c:if test="${beneForm.sottocategoria.id != 35 && beneForm.sottocategoria.id != 206}">
							<td colspan="2"></td>
						</c:if>
						<c:if test="${beneForm.sottocategoria.id != null && (beneForm.sottocategoria.id == 35 || beneForm.sottocategoria.id == 206)}">
							<td><form:label path="flgVersamento">
									<spring:message code="bene.denaro.contante.flgversamento" />
								</form:label>
							</td>
							<td><c:out value="${beneForm.flgVersamento.descrizione}" /></td>
						</c:if>
					</tr>
					
					<tr>
						<td width="15%"><form:label path="valutaEur"><spring:message code="bene.finanziario.importo.euro" /></form:label></td>
						<td colspan="3"><c:out value="${beneForm.valutaEur}" /></td>
					</tr>
					
					<tr>
						<td width="15%"><form:label path="tipoValuta"><spring:message code="bene.finanziario.valuta"/></form:label></td>
						<td width="35%"><c:out value="${beneForm.tipoValuta.descrizione}" /></td>
						<td><form:label path="valutaEst"><spring:message code="bene.finanziario.importo.estero" /></form:label></td>
						<td width="35%"><c:out value="${beneForm.valutaEst}" /></td>
					</tr>
					
					<c:if test="${beneForm.sottocategoria.id != null && (beneForm.sottocategoria.id != 35 || beneForm.sottocategoria.id != 206 )}">
						<tr>
							<td width="15%"><form:label path="direttoreProtempore"><spring:message code="bene.denaro.direttore" /></form:label></td>
							<td colspan="3"><c:out value="${beneForm.direttoreProtempore}" /></td>
						</tr>
					</c:if>
					<c:if test="${beneForm.sottocategoria.id != null && (beneForm.sottocategoria.id == 35 || beneForm.sottocategoria.id == 206)}">
						<tr>
						    <td width="15%"><form:label path="intestatario"><spring:message code="bene.denaro.contante.intestatario"/></form:label></td>
							<td width="35%"><c:out value="${beneForm.intestatario}" /></td>
							<td><form:label path="numModelloTribunale"><spring:message code="bene.denaro.contante.nummodellotribunale"/></form:label></td>
							<td width="35%"><c:out value="${beneForm.numModelloTribunale}" /></td>
						</tr>
						<tr>
							<td width="15%"><form:label path="numBollettino"><spring:message code="bene.denaro.contante.numerobollettino" /></form:label></td>
							<td width="35%"><c:out value="${beneForm.numBollettino}" /></td>
							<td colspan="2">&nbsp;</td>
						</tr>
					</c:if>
					
					<tr>
						<td width="15%"><form:label path="gravame"><spring:message code="bene.gravame" /></form:label></td>
						<td width="35%">
						  <form:radiobutton disabled="true" path="gravame" value="S" label="SI"/>
						  <form:radiobutton disabled="true" path="gravame" value="N" label="NO"/>
					    </td>
					    <td><form:label path="note"><spring:message code="note" /></form:label></td>
						<td width="15%"><c:out value="${beneForm.note}" /></td>
					</tr>