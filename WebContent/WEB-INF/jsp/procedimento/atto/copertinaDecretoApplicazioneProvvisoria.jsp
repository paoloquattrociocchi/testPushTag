<%@page import="java.util.List"%>
<%@page import="it.giustizia.smp.utility.Constants"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
<%
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<p class="titolo_page">
			<spring:message code="atto.dettaglio" />
		</p>
		<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
		<br />
		<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
		<br/>		
		<spring:url var="azioneSoggetto" value="/procedimento/atto/misuraDecretoApplicazioneProvvisoria.htm" />
		<form:form id="id_formSoggetti" method="post" action="${azioneSoggetto}" commandName="soggettoForm">

  		   <display:table class="tb_lista" name="${soggetti}" uid="sogg" pagesize="10" requestURI="${paginazione}">
			<display:column style="text-align: center; width: 5%">
				<input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}" onclick="selectID('${sogg.idSoggetto}', '${sogg.idVersione}', '${sogg.tipoSoggetto}', '${sogg.misura.descrizioneMisura}');" />
			</display:column>
			<display:column property="descrizioneSoggetto" title="Generalità" sortable="false" />
			<display:column property="dataIscrizione" title="Data Iscrizione" format="{0,date,dd/MM/yyyy}" sortable="false" />
			
			<c:if test="${sogg.misura.descrizioneMisura==null && customMessageList!=null && fn:length(customMessageList)>0}">
				<display:column class="testoRosso" value="MISURE ASSENTI" title="Misure" sortable="false" />
			</c:if>
			<c:if test="${sogg.misura.descrizioneMisura!=null}">
				<display:column property="misura.descrizioneMisura" title="Misure" sortable="false" />
			</c:if>
		</display:table>
				<form:hidden path="idSoggetto" id="idSoggetto" />
				<form:hidden path="idVersione" id="idVersione" />
				<form:hidden path="tipoSoggetto" id="tipoSoggetto" />
				<form:hidden path="tipoInteresse.id" />
				<form:hidden path="misura.descrizioneMisura" id="tipoMisura" />

			<br/>
			<table class="tb_bottoni" align="center">
				<tr>
					
					<c:if test="${profilo.ruolo!='MAGISTRATO'}">
				  	<td class="td_tasto"><input type="submit" value="Associa" name="MisuraApplicazioneProvvisoria"></td>							
				</c:if>
				</tr>
			</table>
		</form:form>	
		<br/>
		<br/>
	

		
	</tiles:putAttribute>
</tiles:insertDefinition>



<script type="text/javascript">
	function selectID(idSoggetto, idVersione, tipoSoggetto, tipoMisura) {
		document.getElementById("idSoggetto").value = idSoggetto;
		document.getElementById("idVersione").value = idVersione;
		document.getElementById("tipoSoggetto").value = tipoSoggetto;
		document.getElementById("tipoMisura").value = tipoMisura;
	}

	
	$(document).ready(function() {


		$("#idForm").bind('submit', function() {
			return messaggio(this);
		});
	});

</script>