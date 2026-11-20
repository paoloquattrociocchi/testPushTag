<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">

		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>

		<spring:url var="azione" value="/procedimento/udienza/visualizzaUdienze.htm" />
		<p class="titolo_page">
			<spring:message code="udienza.titolo" />
		</p>
		<form:form method="post" action="${azione}" commandName="udienzaForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<br />
			<display:table class="tb_lista" name="${risultatiRicerca}" uid="udi" pagesize="10" requestURI="${azione}">
				<display:column style="text-align: center; width: 5%">
					<input type="radio" name="radioIDS" id="radioID_${udi_rowNum}" onclick="selectID('${udi.codUdienza}', '${udi.stato.descrizione}');" />
				</display:column>
				<display:column property="dataOraUdienza" title="Data/Ora udienza" sortable="false" />
				<display:column property="stato.descrizione" title="Stato" sortable="false" />
				<display:column property="esito.descrizione" title="Esito" sortable="false" />
			</display:table>

			<form:hidden path="codUdienza" id="codUdienza" />
			<form:hidden path="stato.descrizione" id="stato.descrizione" />
			
			<br />
			<br />
			<logic:notEmpty name="risultatiRicerca">
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Dettaglio" name="DettaglioUdi"></td>
					<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE%>" value="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE_SMP_GIU%>">
						<td class="td_tasto"><input type="submit" value="Modifica" name="ModificaUdi"></td>
					</logic:equal>
					<logic:equal scope="session" name="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE%>" value="<%= it.giustizia.smp.utility.Constants.TIPO_UTENTE_SMP%>">
						<td class="td_tasto"><input type="submit" value="Modifica" name="ModificaUdi" disabled="true"></td>
					</logic:equal>
				</tr>
			</table>
		  </logic:notEmpty>
		</form:form>
		<script>
				function selectID(codUdienza, stato) {
					document.getElementById("codUdienza").value = codUdienza;
					document.getElementById("stato.descrizione").value = stato;
				}
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>