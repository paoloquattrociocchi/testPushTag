<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="it.giustizia.smp.utility.Constants"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/confermaModificaTitoloDiInteresse.htm" />
		<p class="titolo_page">
			<spring:message code="bene.titolidiinteresse" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneSoggetto">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />

			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene" /> <c:out value="${beneSoggetto.bene.idBene}" />&nbsp;&nbsp; - &nbsp;&nbsp; <c:out value="${beneSoggetto.bene.descrizioneBene}" /> <form:hidden path="bene.idBene" /></td>
				</tr>
			</table>
			<display:table class="tb_lista" name="${soggettiDelBene}" pagesize="10" requestURI="${azione}">
				<display:column property="soggetto.descrizioneSoggetto" title="Nome Cognome - Denominazione/Ragione Sociale" sortable="true" style="width: 50%" />
				<display:column property="descrizioneTipoInteresse" title="Tipo interesse" sortable="true" style="width: 25%" />
				<display:column property="descrizioneFrazioneOQuota" title="Quota" sortable="true" style="width: 15%" />
			</display:table>
			<br />
			<br />

			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.titolidiinteresse.titolare" /></td>
				</tr>
				<tr>
					<td><spring:message code="soggetto.generalita" /></td>
					<td><c:out value="${beneSoggetto.soggetto.descrizioneSoggetto}" /></td>
				</tr>
				<tr>
					<td><form:label path="descrizioneTipoInteresse" cssClass="required">
							<spring:message code="bene.titolidiinteresse.titolodidiritto" />
						</form:label></td>
					<td><c:out value="${beneSoggetto.descrizioneTipoInteresse}" /> <form:hidden path="idTipoInteresse" /></td>
				</tr>

				<c:if test="${beneSoggetto.quotaInteresse!=null}">
					<tr>
						<td><form:label path="quotaInteresse">
								<spring:message code="bene.titolidiinteresse.quotainteresse" />
							</form:label></td>
						<td><form:input path="quotaInteresse" maxlength="6" /></td>
					</tr>					
				</c:if>
				<c:if test="${beneSoggetto.quotaInteresse==null}">
					<tr>
						<td><form:label path="numeratoreFrazioneQuota">
								<spring:message code="bene.titolidiinteresse.frazione" />
							</form:label></td>
						<td><form:input path="numeratoreFrazioneQuota"  maxlength="3"/>/<form:input path="denominatoreFrazioneQuota" maxlength="3"/></td>
					</tr>				
				</c:if>



				<c:if test="${procedimento.tipoProcedimento != null && procedimento.tipoProcedimento.id != 13 }">
					<logic:notPresent name="<%=Constants.ATTO%>" scope="session">
						<tr>
							<td><form:label path="soggetto.misura.tipoMisura" cssClass="required">
									<spring:message code="soggetto.misura.tipo" />
								</form:label></td>
							<td><form:select path="soggetto.misura.tipoMisura.id" size="1" onchange="submit()">
									<form:option value="-1" label="------Seleziona Tipo Misura-------" />
									<form:options items="${tipiMisuraTipoT}" itemValue="id" itemLabel="descrizione" />

								</form:select></td>
						</tr>
						<tr>
							<td><form:label path="soggetto.normativa" cssClass="required">
									<spring:message code="procedimento.normativa" />
								</form:label></td>


							<td><form:select path="soggetto.normativa.id" size="1">

									<logic:notEmpty name="normativeRefresh">
										<form:options items="${normativeRefresh}" itemValue="id" itemLabel="descrizione" />
									</logic:notEmpty>
									<logic:empty name="normativeRefresh">
										<form:options items="${normativenew}" itemValue="id" itemLabel="descrizione" />
									</logic:empty>
								</form:select></td>

						</tr>
					</logic:notPresent>
					<logic:present name="<%=Constants.ATTO%>" scope="session">
						<logic:notEqual value="<%=Constants.DECRETO_DECISORIO_I_GRADO%>" name="atto" property="categoriaAtto.id">
							<logic:notEqual value="<%=Constants.DECRETO_DECISORIO_II_GRADO%>" name="atto" property="categoriaAtto.id">
								<tr>
									<td><form:label path="soggetto.misura.tipoMisura" cssClass="required">
											<spring:message code="soggetto.misura.tipo" />
										</form:label></td>
									<td><form:select path="soggetto.misura.tipoMisura.id" size="1" onchange="submit()">
											<form:option value="-1" label="------Seleziona Tipo Misura-------" />
											<logic:equal value="<%=Constants.TIPO_ATTO_PROPOSTA%>" name="atto" property="tipoAtto.id">
												<form:options items="${tipiMisuraTipoT}" itemValue="id" itemLabel="descrizione" />
											</logic:equal>
											<logic:notEqual value="<%=Constants.TIPO_ATTO_PROPOSTA%>" name="atto" property="tipoAtto.id">
												<form:options items="${tipiMisuraTipoSequestro}" itemValue="id" itemLabel="descrizione" />
											</logic:notEqual>
										</form:select></td>
								</tr>
								<tr>
									<td><form:label path="soggetto.normativa" cssClass="required">
											<spring:message code="procedimento.normativa" />
										</form:label></td>


									<td><form:select path="soggetto.normativa.id" size="1">

											<logic:notEmpty name="normativeRefresh">
												<form:options items="${normativeRefresh}" itemValue="id" itemLabel="descrizione" />
											</logic:notEmpty>
											<logic:empty name="normativeRefresh">
												<form:options items="${normativenew}" itemValue="id" itemLabel="descrizione" />
											</logic:empty>
										</form:select></td>
								</tr>
							</logic:notEqual>
						</logic:notEqual>
					</logic:present>
				</c:if>
				
				
				<c:if test="${procedimento.tipoProcedimento != null && procedimento.tipoProcedimento.id == 13 }">
					<input type="hidden" name="isIstanzaModificaMisurePatrimoniali" value="true" />
				</c:if>

			</table>

			<form:hidden path="bene.identificativoSoggettoProcedimento" />
			<form:hidden path="bene.descrizioneBene" />

			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Conferma" name="Conferma"></td>
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
		</form:form>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
