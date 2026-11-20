<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/udienza/dettaglioUdienza.htm" />
		<p class="titolo_page">
		<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_CORTE_D_APPELLO)%>">
			<spring:message code="verbaleUdienza.titolo" />
		</logic:equal>
		<logic:notEqual scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_CORTE_D_APPELLO)%>">
			<spring:message code="udienza.titolo" />
		</logic:notEqual>	
		</p>
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="udienzaForm">
			<form:hidden path="codAtto" />
			<form:hidden path="tipoAtto" />
			<form:hidden path="statoAtto" />
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<table class="tb_form" border=0>
				<jsp:include page="/WEB-INF/jsp/procedimento/udienza/includeDettaglioUdienza.jsp" />
			</table>
			<c:if test="${profilo.idTipoUfficio!=17}">
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegatoUdi.jsp" />
			</c:if>
			
			<br />
			
			<table class="tb_bottoni" align="center">
				<tr>
					<smp:notificheButtons formId="id_formConfermaInvia" codAtto="${attoForm.codAtto}"></smp:notificheButtons>
			        <c:if test="${attoForm.idProcedimentoCdatrib==null || attoForm.idProcedimentoCdatrib==0}">
						<!-- nuova gestione verbale udienza -->
						<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_PROCURA)%>">
							<td class="td_tasto"><input type="submit" value="Indietro" name="Indietro"></td>
						</logic:equal>
						<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_PROCURA_GENERALE)%>">
							<td class="td_tasto"><input type="submit" value="Indietro" name="Indietro"></td>
						</logic:equal>
						<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_CORTE_D_APPELLO)%>">
							<td class="td_tasto"><input type="submit" value="Indietro" name="IndietroListaAtti"></td>
						</logic:equal>
						<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.ID_TIPO_UFFICIO%>" value="<%= String.valueOf(it.giustizia.smp.utility.Constants.TIPO_UFFICIO_TRIBUNALE)%>">
							<td class="td_tasto"><input type="submit" value="Indietro" name="IndietroListaAtti"></td>
						</logic:equal>
					</c:if>	
				</tr>
			</table>
		</form:form>
		
	
		<smp:back buttonId="id_buttonIndietro"></smp:back>
	</tiles:putAttribute>
</tiles:insertDefinition>