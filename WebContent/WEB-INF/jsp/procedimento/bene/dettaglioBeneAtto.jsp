<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/dettaglioBeneAtto.htm" />
		<p class="titolo_page">
			<spring:message code="bene.azienda.dettaglio" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm">
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene" /> <c:out value="${beneForm.idBene}" /></td>
				</tr>

			</table>
			<table class="tb_form" border=0>
				<tr>
					<td><form:label path="tipologia.descrizione">
							<spring:message code="bene.tipologia" />
						</form:label></td>
					<td colspan="3"><c:out value="${beneForm.tipologia.descrizione}" /></td>
				</tr>
				<tr>
					<td><form:label path="categoria.descrizione">
							<spring:message code="bene.categoria" />
						</form:label></td>
					<td><c:out value="${beneForm.categoria.descrizione}" /></td>
					<td><form:label path="sottocategoria.descrizione">
							<spring:message code="bene.sottocategoria" />
						</form:label></td>
					<td><c:out value="${beneForm.sottocategoria.descrizione}" /></td>
				</tr>

				<logic:equal name="beneForm" property="tipologia.id" value="1">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioImmobile.jsp" />
				</logic:equal>
				<logic:equal name="beneForm" property="tipologia.id" value="2">
					<c:if test="${beneForm.categoria.id=='6'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioDenaro.jsp" />
					</c:if>
					<c:if test="${beneForm.categoria.id=='7'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioCollezione.jsp" />
					</c:if>
					<c:if test="${beneForm.categoria.id=='8'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioAnimali.jsp" />
					</c:if>
				</logic:equal>
				<logic:equal name="beneForm" property="tipologia.id" value="3">
					<c:if test="${beneForm.categoria.id=='10'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioVeicolo.jsp" />
					</c:if>
					<c:if test="${beneForm.categoria.id=='11'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioBeneImmateriale.jsp" />
					</c:if>
				</logic:equal>
				<logic:equal name="beneForm" property="tipologia.id" value="4">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioBeneFinanziario.jsp" />
				</logic:equal>
				<logic:equal name="beneForm" property="tipologia.id" value="5">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioAzienda.jsp" />
				</logic:equal>
			</table>

			<form:hidden path="idBene" />

			<br />
			<br />

			<c:if test="${beneForm.dettAssBene=='Dettaglio'}">
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto">
							<!-- <input type="button" value="Indietro" onClick="indietro('/procedimento/bene/beniCollegatiAtto.htm')"> -->
							<input type="button" id="id_buttonIndietro" value="Indietro">
						</td>
					</tr>
				</table>
			</c:if>
			<c:if test="${beneForm.dettAssBene=='Associa'}">
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" name="ConfermaAssociaBene" value="Conferma Associazione"></td>
						<td class="td_tasto">
							 <!-- <input type="button" value="Indietro" onClick="indietro('/procedimento/bene/beniCollegati.htm')"> -->
							 <input type="button" id="id_buttonIndietro" value="Indietro">
						</td>
					</tr>
				</table>
			</c:if>
			<c:if test="${beneForm.dettAssBene=='Elimina'}">
				<table class="tb_bottoni" align="center">
					<tr>
						<td align="right" class="td_tasto"><input type="submit" name="ConfermaEliminaBeneAtto" value="Conferma eliminazione"></td>
						<td align="left" class="td_tasto">
							<!-- <input type="button" value="Annulla" onClick="indietro('/procedimento/bene/beniCollegati.htm')"> -->
							<input type="button" id="id_buttonIndietro" value="Annulla">
						</td>
					</tr>
				</table>
			</c:if>
		</form:form>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="id_buttonIndietro"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>