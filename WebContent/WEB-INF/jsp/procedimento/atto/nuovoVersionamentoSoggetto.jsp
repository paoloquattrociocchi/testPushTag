<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/nuovoVersionamentoSoggetto.htm" />
		<form:form method="post" action="${azione}" commandName="attoForm">
			<p class="titolo_page"></p>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					   <td width="10%"><form:label path="tipoAtto.id">
							<spring:message code="atto.tipi" />
						   </form:label></td>					
						<td><c:out value="${atto.tipoAtto.descrizione}" /></td>
						<form:hidden id="tipoAtto.id" path="tipoAtto.id"/>
				</tr>
				<tr>
						<td width="10%"> <form:label path="categoriaAtto.id">
								<spring:message code="atto.categoria" />
							</form:label></td>
						<td><c:out value="${atto.categoriaAtto.descrizione}" /></td>
						<form:hidden id="categoriaAtto.id" path="categoriaAtto.id"/>
				</tr>			
			</table>
			<br/>
			<br/>
			<display:table class="tb_lista" uid="sogg" name="${listaSoggetti}" pagesize="10" requestURI="${azione}">
				  <display:column style="text-align: center; width: 5%">
					 <input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}"
						onclick="selectID('${sogg.idSoggetto}', '${sogg.idVersione}', '${sogg.tipoSoggetto}');" />
				   </display:column>							
		            <display:column property="descrizioneSoggettoNoVersione" title="Generalità" sortable="true" />						
					<display:column property="idVersione" title="Versione" sortable="false" />	
					<display:column property="descrRuolo" title="Ruolo" sortable="false" />
			</display:table>			
			<br/>
			
			
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input id="id_button_nuovo_atto" type="submit" name="Versionamento" title="Versionamento" value="Versionamento"></td>
				</tr>
			</table>
	
	      <form:hidden path="identificativoSoggetto"/>
	      <form:hidden path="versioneSoggetto"/>
	      <form:hidden path="tipoSoggetto"/>
		
		</form:form>
		<script>
			function selectID(idSoggetto, idVersione, tipoSoggetto) {
				document.getElementById("identificativoSoggetto").value = idSoggetto;
				document.getElementById("versioneSoggetto").value = idVersione;
				document.getElementById("tipoSoggetto").value = tipoSoggetto;
			}
		</script>		
		
	</tiles:putAttribute>
</tiles:insertDefinition>
