<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione" value="/procedimento/bene/startAnnullaBene.htm" />
			<form:form method="post" action="${azione}" commandName="beneForm" id="idFormAnnullaBene">
				<p class="titolo_page"><spring:message code="bene.annullabile.titolo"/></p>
				<logic:present name="procedimento">
					<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				</logic:present>
				<br/>
			    <form:hidden path="idBene" id="idbene" />
				<form:hidden path="idUffOwner" id="idUffOwner" />
				
				<form:hidden path="idBeneDuplicato" id="idBeneDuplicato" />
				<form:hidden path="idUffOwnerDuplicato" id="idUffOwnerDuplicato" />
					
			<!-- DETTAGLIO BENE VALIDO -->
			<table class="tb_form">
				<colgroup>
			       <col id="tb_form_col_label" span="1">
			       <col id="tb_form_col_value" span="1">
			       <col id="tb_form_col_label" span="1">
			       <col id="tb_form_col_value" span="1">
			    </colgroup>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="bene.annullabile.valido" /></td>
					</tr>
				<tr>
					<td><form:label path="tipologia.descrizione">
							<spring:message code="bene.tipologia" />
						</form:label></td>
					<td colspan="3"><c:out value="${beneFormValido.tipologia.descrizione}" /></td>
				</tr>
				<tr>
					<td width="15%"><form:label path="categoria.descrizione">
							<spring:message code="bene.categoria" />
						</form:label></td>
					<td width="35%"><c:out value="${beneFormValido.categoria.descrizione}" /></td>
					<td><form:label path="sottocategoria.descrizione">
							<spring:message code="bene.sottocategoria" />
						</form:label></td>
					<td width="35%"><c:out value="${beneFormValido.sottocategoria.descrizione}" /></td>
				</tr>

				<logic:equal name="beneFormValido" property="tipologia.id" value="1">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/annullaIncludeDettaglioImmobile.jsp"/>
				</logic:equal>
				<logic:equal name="beneFormValido" property="tipologia.id" value="2">
					<c:if test="${beneFormValido.categoria.id=='6'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/annullaIncludeDettaglioDenaro.jsp"/>
					</c:if>
					<c:if test="${beneFormValido.categoria.id=='7'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/annullaIncludeDettaglioCollezione.jsp"/>
					</c:if>
					<c:if test="${beneFormValido.categoria.id=='8'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/annullaIncludeDettaglioAnimali.jsp"/>
					</c:if>
					<c:if test="${beneFormValido.categoria.id=='9'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/annullaIncludeDettaglioAltriOggetti.jsp"/>
					</c:if>
				</logic:equal>
				<logic:equal name="beneFormValido" property="tipologia.id" value="3">
					<c:if test="${beneFormValido.categoria.id=='10'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/annullaIncludeDettaglioVeicolo.jsp"/>
					</c:if>
					<c:if test="${beneFormValido.categoria.id=='11'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/annullaIncludeDettaglioBeneImmateriale.jsp"/>
					</c:if>
				</logic:equal>
				<logic:equal name="beneFormValido" property="tipologia.id" value="4">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/annullaIncludeDettaglioBeneFinanziario.jsp"/>
				</logic:equal>
				<logic:equal name="beneFormValido" property="tipologia.id" value="5">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/annullaIncludeDettaglioAzienda.jsp"/>
				</logic:equal>
			</table>
			<br><br>
			<!-- DETTAGLIO BENE DUPLICATO -->
			<table class="tb_form">
				<colgroup>
			       <col id="tb_form_col_label" span="1">
			       <col id="tb_form_col_value" span="1">
			       <col id="tb_form_col_label" span="1">
			       <col id="tb_form_col_value" span="1">
			    </colgroup>
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="bene.annullabile.duplicato" /></td>
					</tr>
				<tr>
					<td><form:label path="tipologia.descrizione">
							<spring:message code="bene.tipologia" />
						</form:label></td>
					<td colspan="3"><c:out value="${beneFormDuplicato.tipologia.descrizione}" /></td>
				</tr>
				<tr>
					<td width="15%"><form:label path="categoria.descrizione">
							<spring:message code="bene.categoria" />
						</form:label></td>
					<td width="35%"><c:out value="${beneFormDuplicato.categoria.descrizione}" /></td>
					<td><form:label path="sottocategoria.descrizione">
							<spring:message code="bene.sottocategoria" />
						</form:label></td>
					<td width="35%"><c:out value="${beneFormDuplicato.sottocategoria.descrizione}" /></td>
				</tr>

				<logic:equal name="beneFormDuplicato" property="tipologia.id" value="1">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/annullaIncludeDettaglioImmobile.jsp"/>
				</logic:equal>
				<logic:equal name="beneFormDuplicato" property="tipologia.id" value="2">
					<c:if test="${beneFormDuplicato.categoria.id=='6'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/duplicatoAnnullaIncludeDettaglioDenaro.jsp"/>
					</c:if>
					<c:if test="${beneFormDuplicato.categoria.id=='7'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/duplicatoAnnullaIncludeDettaglioCollezione.jsp"/>
					</c:if>
					<c:if test="${beneFormDuplicato.categoria.id=='8'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/duplicatoAnnullaIncludeDettaglioAnimali.jsp"/>
					</c:if>
					<c:if test="${beneFormDuplicato.categoria.id=='9'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/duplicatoAnnullaIncludeDettaglioAltriOggetti.jsp"/>
					</c:if>
				</logic:equal>
				<logic:equal name="beneFormDuplicato" property="tipologia.id" value="3">
					<c:if test="${beneFormDuplicato.categoria.id=='10'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/duplicatoAnnullaIncludeDettaglioVeicolo.jsp"/>
					</c:if>
					<c:if test="${beneFormDuplicato.categoria.id=='11'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/duplicatoAnnullaIncludeDettaglioBeneImmateriale.jsp"/>
					</c:if>
				</logic:equal>
				<logic:equal name="beneFormDuplicato" property="tipologia.id" value="4">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/duplicatoAnnullaIncludeDettaglioBeneFinanziario.jsp"/>
				</logic:equal>
				<logic:equal name="beneFormDuplicato" property="tipologia.id" value="5">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/duplicatoAnnullaIncludeDettaglioAzienda.jsp"/>
				</logic:equal>
			</table>

		<br />
		<br />
		<table class="tb_bottoni" align="center">
			<tr>	
		<logic:present name="abilitaConferma"> 					
				<td class="td_tasto"><input type="submit" value="Conferma" name="Conferma" title="Conferma annullamento Soggetto"/></td>					
		</logic:present>								
				<td class="td_tasto"><input id="id_buttonIndietro" type="button" value="Indietro"/></td>					
			</tr>
		</table>
	</form:form>
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
				<smp:back buttonId="id_buttonIndietro"></smp:back>

		</tiles:putAttribute>
	</tiles:insertDefinition>
