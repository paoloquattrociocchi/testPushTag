
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
	
		<spring:url var="azione" value="/procedimento/bene/amministratore/dettaglioDatiAmmBene.htm" />
		<p class="titolo_page">
			<spring:message code="bene.atti.dettaglio" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm">
			<form:hidden path="idBene" />
			
			
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene" /> <c:out value="${beneForm.idBene}" /> <c:out value="${beneForm.descrizioneBene}" /></td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4">
						<spring:message code="bene.gest.atto" /> <c:out value="${beneForm.amministratore}" /> 
					<!-- 	<spring:message code="bene.gest.deposito" /><c:out value="${beneForm.idDeposito}" />  --> 
					</td>
				</tr>

			</table>
			<table class="tb_form" border=0>
				<tr>
					<td><form:label path="tipologia.descrizione">
							<spring:message code="bene.tipologia" />
						</form:label></td>
					<td colspan="3"><c:out value="${beneForm.tipologia.descrizione}" /></td>
					<input type="hidden" name="tipologia.descrizione" value="${beneForm.tipologia.descrizione}" />					
				</tr>
				<tr>
					<td width="15%"><form:label path="categoria.descrizione">
							<spring:message code="bene.categoria" />
						</form:label></td>
					<td width="35%"><c:out value="${beneForm.categoria.descrizione}" /></td>
					<input type="hidden" name="categoria.descrizione" value="${beneForm.categoria.descrizione}" />
					
					<td><form:label path="sottocategoria.descrizione">
							<spring:message code="bene.sottocategoria" />
						</form:label></td>
					<td width="35%"><c:out value="${beneForm.sottocategoria.descrizione}" /></td>
					<input type="hidden" name="sottocategoria.descrizione" value="${beneForm.sottocategoria.descrizione}" />
					
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.atti.titolo" /></td>
				</tr>

				<c:if test="${!empty beneForm.datiSpecificiBene.altriOggetti.datiMobileGest}">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/amministratore/includeDettGestAltriOggetti.jsp" />
				</c:if>		
				<c:if test="${!empty beneForm.datiSpecificiBene.animali.datiMobileGest}">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/amministratore/includeDettGestAnimali.jsp" />
				</c:if>		
				<c:if test="${!empty beneForm.datiSpecificiBene.azienda.datiAziendaGest}">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/amministratore/includeDettGestAzienda.jsp" />
				</c:if>		
				<c:if test="${!empty beneForm.datiSpecificiBene.finanziario.datiFinanzGest}">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/amministratore/includeDettGestFinanziario.jsp" />
				</c:if>		
				<c:if test="${!empty beneForm.datiSpecificiBene.immateriale.datiImmaterialeGest}">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/amministratore/includeDettGestImmateriale.jsp" />
				</c:if>		
				<c:if test="${!empty beneForm.datiSpecificiBene.immobile.datiImmobileGest}">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/amministratore/includeDettGestImmobile.jsp" />
				</c:if>		
				<c:if test="${!empty beneForm.datiSpecificiBene.collezioni.datiMobileGest}">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/amministratore/includeDettGestCollezioni.jsp" />
				</c:if>
				<c:if test="${!empty beneForm.datiSpecificiBene.veicolo.datiVeicoloGest}">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/amministratore/includeDettGestVeicolo.jsp" />
				</c:if>		

			</table>
			<br />
			<br />
			
			<form:hidden path="idBene" />
			<form:hidden path="versioneBene" />
		    <c:if test="${beneForm.idBene == null}">
			    <input type="hidden" name="idDeposito" value="${beneForm.idDeposito}" />
		    <input type="hidden" name="descrizioneBene" value="${beneForm.descrizioneBene}" />		    
		    </c:if>
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro">
				</tr>
			</table>
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>
