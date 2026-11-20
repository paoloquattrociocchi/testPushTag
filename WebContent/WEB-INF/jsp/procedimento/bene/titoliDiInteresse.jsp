<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>

<c:set var="TIPOPROC_TERZO_INTERVENIENTE_CAM" value="<%=Constants.TIPOPROC_TERZO_INTERVENIENTE_CAM%>"/>
<c:set var="STATOPROC_ISCRITTO" value="<%=Constants.STATOPROC_ISCRITTO%>"/>
<c:set var="STATOPROC_IN_ACQUISIZ_PROPOSTA" value="<%=Constants.STATOPROC_IN_ACQUISIZ_PROPOSTA%>"/>
<c:set var="STATOPROC_TRATTAZIONE_II_GRADO" value="<%=Constants.STATOPROC_TRATTAZIONE_II_GRADO%>"/>
<c:set var="TIPO_UFFICIO_CORTE_D_APPELLO" value="<%=Constants.TIPO_UFFICIO_CORTE_D_APPELLO%>"/>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>

		<p class="titolo_page">
			<spring:message code="bene.titolidiinteresse" />
		</p>
		<spring:url var="azione" value="/procedimento/bene/gestioneSoggettiDelBene.htm" />
		<form:form method="post" action="${azione}" commandName="beneForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			
			<table class="tb_form">
				<!-- logic:iterate name="beni" id="beni" -->
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene" /> <c:out value="${beneForm.idBene}" /> <wbr> <wbr> - <wbr> <wbr> <c:out value="${beneForm.descrizioneBene}" /></td>
				</tr>
				<!--/logic:iterate -->
			</table>
			<display:table class="tb_lista" name="${soggettiDelBene}" uid="soggettoDelBene" pagesize="10" requestURI="${azione}">
				<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
					<form:radiobutton path="identificativoSoggettoBeneProcedimentoTitoloDiritto" value="${soggettoDelBene.soggetto.idSoggetto}§${soggettoDelBene.idTipoInteresse}" />
				</display:column>
				<display:column property="soggetto.descrizioneSoggetto" title="Generalità" sortable="true" style="width: 50%" />
				<display:column property="descrizioneTipoInteresse" title="Tipo interesse" sortable="true" style="width: 25%" />
				<display:column property="descrizioneFrazioneOQuota" title="Quota" sortable="true" style="width: 15%" />
			</display:table>

			<form:hidden path="idBene" />
			<br />
			<table class="tb_bottoni" align="center">
				<logic:notEmpty name="soggettiDelBene">
					<tr>
						<c:if test="${ !(
											(sessionScope.atto.tipoAtto.id == 4 && sessionScope.atto.categoriaAtto.id == 6) ||
											(sessionScope.atto.tipoAtto.id == 25 && sessionScope.atto.categoriaAtto.id == 328)
										) }">
							<!-- Segnalazione EMS-131 -->
							<c:choose>
								<c:when test="${procedimento.tipoProcedimento.id == 13}">
									<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica" disabled="disabled"></td>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${procedimento.tipoProcedimento.id == TIPOPROC_TERZO_INTERVENIENTE_CAM}">
											<c:if test="${procedimento.statoProcedimento.id == STATOPROC_ISCRITTO or procedimento.statoProcedimento.id == STATOPROC_IN_ACQUISIZ_PROPOSTA}">
												<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica"></td>
											</c:if>
										</c:when>
										<c:otherwise>
											<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica"></td>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${ (
											(sessionScope.atto.tipoAtto.id == 4 && sessionScope.atto.categoriaAtto.id == 6) ||
											(sessionScope.atto.tipoAtto.id == 25 && sessionScope.atto.categoriaAtto.id == 328)
										) }">
							<td class="td_tasto"><input id="backButton" type="button" value="Indietro" title="Indietro"></td>
						</c:if>		
						<!-- Segnalazione EMS-131 -->
							<c:choose>
								<c:when test="${procedimento.tipoProcedimento.id == 13 || (profilo.idTipoUfficio == TIPO_UFFICIO_CORTE_D_APPELLO && procedimento.statoProcedimento.id == STATOPROC_TRATTAZIONE_II_GRADO)}">
									<td class="td_tasto"><input type="submit" value="Elimina" name="Elimina" disabled="disabled"></td>
								</c:when>
								<c:otherwise>
									<td class="td_tasto"><input type="submit" value="Elimina" name="Elimina"></td>
								</c:otherwise>
							</c:choose>				
					</tr>
				</logic:notEmpty>
			</table>
		</form:form>
		
		<c:if test="${ !(
							(sessionScope.atto.tipoAtto.id == 4 && sessionScope.atto.categoriaAtto.id == 6) ||
							(sessionScope.atto.tipoAtto.id == 25 && sessionScope.atto.categoriaAtto.id == 328)
						) }">
			<spring:url var="azione2" value="/procedimento/bene/associaTitoloDiInteresse.htm" />
			<form:form method="post" action="${azione2}" commandName="beneForm">
				<br />
				<form:hidden path="idBene" />
				<form:hidden path="descrizioneBene" />
	
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="bene.soggettiDelFascicolo" /></td>
					</tr>
				</table>
				<display:table class="tb_lista" uid="soggettiDelProcedimento" name="${soggettiDelProcedimento}" pagesize="10" requestURI="${azione2}">
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:radiobutton path="identificativoSoggettoProcedimento" value="${soggettiDelProcedimento.idSoggetto}" />
					</display:column>
					<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" style="width: 50%" />
					<display:column property="descrRuolo" title="Ruolo" sortable="true" style="width: 35%" />
				</display:table>
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
					<c:if test="${!(profilo.idTipoUfficio == TIPO_UFFICIO_CORTE_D_APPELLO && procedimento.statoProcedimento.id == STATOPROC_TRATTAZIONE_II_GRADO)}">
						<td class="td_tasto"><input type="submit" value="Associa" name="Associa"></td>
						<td class="td_tasto"><input type="submit" value="Terzo Soggetto Fisico" title="Aggiungi Terzo Soggetto Fisico" name="TerzoSoggettoFisico"></td>
						<td class="td_tasto"><input type="submit" value="Terzo Soggetto Giuridico" title="Aggiungi Terzo Soggetto Giuridico" name="TerzoSoggettoGiuridico"></td>
					</c:if>
						<logic:notEmpty name="atto" scope="session">				          
					      <td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/bene/gestioneBeniDelAtto.htm')"></td>				         
			            </logic:notEmpty>
			            <logic:empty name="atto" scope="session">
						<td class="td_tasto"><input id="backButton" type="button" value="Indietro" title="Indietro"></td>
					    </logic:empty>
					</tr>
				</table>
			</form:form>
		</c:if>
	
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>

		<script type="text/javascript">
				$(document).ready(function() {
					disabilitaSubmitTitoliDiInteresse('${disabledViewProc}', '${procedimento.statoProcedimento.id}', '${profilo.idTipoUfficio}', '${atto}');
				});
			</script>

	</tiles:putAttribute>
</tiles:insertDefinition>