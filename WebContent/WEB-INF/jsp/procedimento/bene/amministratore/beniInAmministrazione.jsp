<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>
<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%
	AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<script type="text/javascript">
			var arrayIdBene = new Array();
			var i=-1;
		</script>

		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>

		<spring:url var="azione" value="/procedimento/bene/amministratore/beniInAmministrazione.htm" />
		<spring:url var="paginazione" value="/procedimento/bene/amministratore/beniInAmministrazione.htm" />
		<p class="titolo_page">
			<spring:message code="amministratore.beniAmministrati" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm">
			<form:hidden path="idAmministratore" />
	     
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br/>
			<c:if test="${atto!=null}">
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				<br />		
			</c:if>
			<table class="tb_form">
				<tr>
					<td class="td_titolo_label" width="15%"><spring:message code="soggettogiuridico.amministratore" />
					</td>
					<td class="td_titolo">
						<c:out value="${beneForm.nominativoAmministratore}" />
					</td>
				</tr>
			</table>
			<br/>
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="amministratore.beniAmministrati" /></td>
				</tr>
			</table>
			
			<logic:present name="risultatiRicerca">	
				<c:forEach items="${risultatiRicerca}" var="bene">  
					<script type="text/javascript">	
						arrayIdBene[++i] = <c:out value="${bene.idBene}"/>;
					</script>
				</c:forEach>
				
				<display:table class="tb_lista" uid="risultatiRicerca" name="${risultatiRicerca}" pagesize="10" requestURI="${paginazione}" >
								
					<display:column property="idBene" title="Identificativo" sortable="false" />
					<display:column property="descrizioneBene" title="Descrizione" sortable="false" />
					<display:column property="dataInizioIncarico" title="Data inizio incarico" format="{0,date,dd/MM/yyyy}" sortable="true" />
					<display:column property="dataFineIncarico" title="Data fine incarico" format="{0,date,dd/MM/yyyy}" sortable="true" />
				</display:table>
				
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro"></td>
				</table>
			</logic:present>
		</form:form>

<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
<smp:back buttonId="backButton"></smp:back>
		
	</tiles:putAttribute>
</tiles:insertDefinition>
