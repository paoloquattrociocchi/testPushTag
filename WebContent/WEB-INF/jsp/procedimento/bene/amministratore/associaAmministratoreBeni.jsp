<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
//J.C. - SITMP 271
request.getSession().setAttribute("associaAmministratoreBeniJspVisited","VISITED");
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<script type="text/javascript">
			var arrayIdBene = new Array();
			var i=-1;
	</script>

		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
		<p class="titolo_page">
			<spring:message code="amministratore.beniassociati" />
		</p>
		<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
		<br />
		<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
		<br />
		
	<spring:url var="azione1" value="/procedimento/amministratore/dissociaBeni.htm" />
			<form:form method="post" action="${azione1}" commandName="beneForm" id="formPrincipale">
			    <form:hidden path="idAmministratore" />
				<table class="tb_form">
				<tr>
					<td class="td_titolo_label" width="15%"><spring:message code="soggettogiuridico.amministratore" />
					</td>
					<td class="td_titolo">
						<c:out value="${beneForm.nominativoAmministratore}" />
					</td>
				</tr>
				</table>
				<br />
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="bene.benidelatto" /></td>
					</tr>
				</table>
	
			<display:table class="tb_lista" uid="elemRisultatoRicerca" name="${risultatoRicerca}" pagesize="10" requestURI="${azione1}" >
				<display:column paramId="idBene" paramProperty="listaIdCheckbox" style="width:7%" title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('elemRisultatoRicerca','listaIdCheckbox');\" title=\"Seleziona/Deseleziona tutti i beni\"></img>&nbsp;Sel.bene" >						
					<form:checkbox path="listaIdCheckbox" value="${elemRisultatoRicerca.idBene}" />
				</display:column>
				<display:column property="idBene" title="Identificativo" sortable="false" />
				<display:column property="descrizioneBene" title="Descrizione" sortable="false" />
			</display:table>

				<br />

				<table class="tb_bottoni" align="center">
					<logic:notEmpty name="risultatoRicerca">
						<tr>
							<td class="td_tasto"><input type="submit" value="Elimina" name="Elimina"></td>
						</tr>
					</logic:notEmpty>
				</table>
			</form:form>
			<br />

			<spring:url var="azione2" value="/procedimento/amministratore/associaBeni.htm" />
			<form:form method="post" action="${azione2}" commandName="beneForm">
   			<form:hidden path="idAmministratore" />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4">
					<form:checkbox path="visualizzaFiltro" onclick="this.form.submit()"/>
					<spring:message code="bene.filtrosuibeni" /></td>
				</tr>
				<logic:equal name="beneForm" property="visualizzaFiltro" value="true">
				<tr>
					<td><form:label path="idBene"><spring:message code="bene.identificativobene" /></form:label></td>
					<td colspan="3">
						<div class="ui-widget">
							<form:input id="inputIdBene" path="idBene" maxlength="15" size="17"/>
							<div id="id_loading_inputIdBene"></div>
						</div>
					</td>
				</tr>
				<tr>
					<td><form:label path="tipologia.id"><spring:message code="bene.tipologia" /></form:label></td>
					<td  colspan="3"><form:select path="tipologia.id" size="1" onchange="this.form.submit()">
							<form:option value="-1" label="------Seleziona Tipologia-------" />
							<form:options items="${tipologie}" itemValue="id" itemLabel="descrizione" />
						</form:select></td>
					<form:hidden path="tipologia.descrizione"/>
				</tr>
				<tr>
					<td><form:label path="categoria.id">
							<spring:message code="bene.categoria" />
						</form:label>
					</td>
					<td  colspan="3"><form:select path="categoria.id" size="1" onchange="submit()">
							<form:option value="-1" label="------Seleziona Categoria-------" />
							<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
						</form:select>
						<form:hidden path="categoria.descrizione"/>
					</td>
				</tr>
				<logic:notEqual name="beneForm" property="tipologia.id" value="5">
					<tr>
						<td><form:label path="sottocategoria.id"><spring:message code="bene.sottocategoria" /></form:label></td>
						<td  colspan="3">
							<form:select path="sottocategoria.id" size="1" onchange="submit()">
								<form:option value="-1" label="------Seleziona Sottocategoria-------" />
								<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>
						<form:hidden path="sottocategoria.descrizione"/>
					</tr>
				</logic:notEqual>
			</logic:equal>
			</table>
			
			<table class="tb_form">
				<logic:notEmpty name="beneForm" property="criteriFiltro">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.benidelprocedimentofiltrati" />
					</td>
				</tr>
				</logic:notEmpty>
				<logic:empty name="beneForm" property="criteriFiltro">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.benidelprocedimento" /></td>
				</tr>
				</logic:empty>
			</table>	
		
				<input type="hidden" id="id_selectAll_listaIdCheckbox" value="true" />
				<display:table class="tb_lista" uid="beni" name="${beni}" pagesize="10" requestURI="${azione2}">

					<display:column paramId="idBene" paramProperty="listaIdCheckbox" style="width:7%" title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('beni','listaIdCheckbox');\" title=\"Seleziona/Deseleziona tutti i beni\"></img>&nbsp;Sel.bene" >						
						<form:checkbox path="listaIdCheckbox" value="${beni.idBene}" />
					</display:column>
					<display:column property="idBene" title="Identificativo del Bene" sortable="false" />
					<display:column property="descrizioneBene" title="Descrizione del Bene" sortable="false" />
				</display:table>

				<br />
				<br />

				<table class="tb_bottoni" align="center">
					<tr>
					<logic:notEmpty name="beni"> 
						<td class="td_tasto"><input type="submit" value="Associa bene" name="Associa"></td>
					</logic:notEmpty>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro"></td>
					</tr>
				</table>
			</form:form>
					
		<logic:equal name="beneForm" property="visualizzaFiltro" value="true">
		
			<script src="${pageContext.request.contextPath}/js/suggestlist/doAutocompleteIdBene.js"></script>
		
			<script type="text/javascript">
					$(document).ready(function() {
						doAutocompleteIdBene("inputIdBene",null,null);
					});
			</script>
		</logic:equal>
		
		<script type="text/javascript">
				$(document).ready(function() {
					disabilitaSubmitBeniDelProcedimento('${disabledViewProc}', '${profilo.idTipoUfficio}','${sessionScope.procedimento.tipoProcedimento.id}');
				});	
		</script>
			


<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
<smp:back buttonId="backButton"></smp:back>
</tiles:putAttribute>
</tiles:insertDefinition>