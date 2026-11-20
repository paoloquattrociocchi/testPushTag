<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
	
		<spring:url var="azione" value="/procedimento/soggetto/visualizzaDirettiInteressatiTerzi.htm" />
		<p class="titolo_page">
			<spring:message code="soggetto.beniCollegati" />
		</p>
		
		<form:form method="post" action="${azione}" commandName="soggettoForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<br />
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4">
						<c:out value="${soggettoForm.descrizioneSoggetto}" />
					</td>
				</tr>
			</table>

			<br />
			<display:table class="tb_lista" name="${beniCollegati}" uid="bene">
				<display:column title="Bene" sortable="false" >
					<c:out value="${bene.CODI_K_ID_BENE}: ${bene.DESCR_BENE}" />
				</display:column>
				<display:column title="Tipo interesse" property="DESC_TIPO_INT" sortable="false" />
				<display:column title="Quota" sortable="false">
					<c:if test="${bene.NUME_QUOTA!=null}">
						<c:out value="${bene.NUME_QUOTA}" />%
					</c:if>
					<c:if test="${bene.NUME_FRAZIONE_NUM!=null}">
						<c:out value="${bene.NUME_FRAZIONE_NUM}" />/<c:out value="${bene.NUME_FRAZIONE_DEN}" />
					</c:if>				
				</display:column>
				<display:column title="Atto" property="DESC_CAT_ATTO" sortable="false" />
				<display:column title="Esito/Tipo misura" sortable="false">
					<c:if test="${bene.CODI_K_ID_CAT_ATTO==4 || bene.CODI_K_ID_CAT_ATTO==22}">
						<c:if test="${bene.DESC_ESITO3 != null}">
							<c:out value="${bene.DESC_ESITO3}" />
						</c:if>
						<c:if test="${bene.DESC_ESITO3 == null && bene.DESC_ESITO2 != null }">
							<c:out value="${bene.DESC_ESITO2}" />
						</c:if>
						<c:if test="${bene.DESC_ESITO3 == null && bene.DESC_ESITO2 == null && bene.DESC_ESITO1 != null }">
							<c:out value="${bene.DESC_ESITO1}" />
						</c:if>	
					</c:if>
					<c:if test="${bene.CODI_K_ID_CAT_ATTO!=4 && bene.CODI_K_ID_CAT_ATTO!=22}">
						<c:out value="${bene.DESC_TIPO_MISURA}" />
					</c:if>
				</display:column>
	
			</display:table>

			<form:hidden path="idSoggetto" id="idSoggetto" />
			<form:hidden path="idVersione" id="idVersione" />
			<form:hidden path="tipoSoggetto" id="tipoSoggetto" />
			<form:hidden path="tipoInteresse.id" />
			<form:hidden path="descrRuolo" id="descrRuolo" />
			<form:hidden path="idRuolo" id="idRuolo" />
			
			<br />
			<br />

		<table class="tb_bottoni" align="center">
			<tr>
				<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
			</tr>
		</table>

		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
		<smp:back buttonId="backButton"></smp:back>
		
	</tiles:putAttribute>
</tiles:insertDefinition>