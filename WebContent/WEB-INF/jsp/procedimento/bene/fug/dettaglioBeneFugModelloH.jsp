<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/fug/dettaglioBeneFug.htm" />
		<p class="titolo_page">
			<spring:message code="titolo.dettaglio.invio.modelloH" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm">
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="2">
						<spring:message code="bene" />
						<c:out value="${beneForm.idBene}" />
						<form:hidden path="idBene"/>
						<form:hidden path="modelloFugSelected.id"/>
						<c:out value=" - ${beneForm.categoria.descrizione}" />
						<c:out value=" - ${beneForm.sottocategoria.descrizione}" />
						<c:if test="${beneForm.descOperFinanz.descrizione!=null}">
							<c:out value=" - ${beneForm.descOperFinanz.descrizione}"/>
						</c:if>
						<c:if test="${beneForm.descrizioneEnte!=null}">
							<c:out value=" - ${beneForm.descrizioneEnte}"/>
						</c:if>
						<c:out value=" - "/>
						<c:choose>
							<c:when test="${beneForm.codiceAbi == null &&  beneForm.codiceCab.id == null}">
								<c:out value=" IBAN: ${beneForm.codiceIban}"/>
							</c:when>
							<c:otherwise>
								<spring:message code="bene.finanziario.abi" />
								<c:out value=": ${beneForm.codiceAbi} "/>
								<spring:message code="bene.finanziario.cab" />
								<c:out value=": ${beneForm.codiceCab.id}"/>							
							</c:otherwise>
						</c:choose>

					</td>
				</tr>
				<tr>
					<td width="10%">
						<form:label path="valutaEur"><spring:message code="bene.finanziario.importo.euro"/></form:label>
						<form:hidden path="valutaEur"/>
					</td>
					<td align="left">
						<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${beneForm.valutaEur}" />
					</td>
				</tr>
				<tr>
					<td width="10%">
						<form:label path="dataTrasferimento"><spring:message code="bene.finanziario.dataTrasferimento"/></form:label>
						<form:hidden path="dataTrasferimento"/>
					</td>
					<td align="left">
						<fmt:formatDate value="${beneForm.dataTrasferimento}" type="date" pattern="dd/MM/yyyy" />
					</td>
				</tr>
				<tr>
					<td width="10%">
						<form:label path="numBollettino"><spring:message code="bene.denaro.contante.numerobollettino"/></form:label>
						<form:hidden path="numBollettino"/>
					</td>
					<td align="left">
						<c:out value="${beneForm.numBollettino}"/>
					</td>
				</tr>
			</table>

			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Invia" name="InviaModello"></td>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro"></td>
				</tr>
			</table>
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
