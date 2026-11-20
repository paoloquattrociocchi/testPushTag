<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<script type="text/javascript">
			var arrayIdBene = new Array();
			var i=-1;
	</script>

		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
		<spring:url var="azione" value="/procedimento/bene/gestioneBeniDelAtto.htm" />
		<p class="titolo_page">
			<spring:message code="bene.benidellattodirigetto" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm">

			<input type="hidden" id="idDecretoSequestroEProposta" value="${idDecretoSequestroEProposta}">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />


			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><form:checkbox path="visualizzaFiltro" onclick="this.form.submit()" /> <spring:message code="bene.filtrosuibeni" /></td>
				</tr>
				<logic:equal name="beneForm" property="visualizzaFiltro" value="true">
					<tr>
						<td><form:label path="idBene">
								<spring:message code="bene.identificativobene" />
							</form:label></td>
						<td colspan="3">
							<div class="ui-widget">
								<form:input id="inputIdBene" path="idBene" maxlength="15" size="17" />
								<div id="id_loading_inputIdBene"></div>
							</div>
						</td>
					</tr>
					<tr>
						<td><form:label path="tipologia.id">
								<spring:message code="bene.tipologia" />
							</form:label></td>
						<td colspan="3"><form:select path="tipologia.id" size="1" onchange="this.form.submit()">
								<form:option value="-1" label="------Seleziona Tipologia-------" />
								<form:options items="${tipologie}" itemValue="id" itemLabel="descrizione" />
							</form:select></td>
						<form:hidden path="tipologia.descrizione" />
					</tr>
					<tr>
						<td><form:label path="categoria.id">
								<spring:message code="bene.categoria" />
							</form:label></td>
						<td colspan="3"><form:select path="categoria.id" size="1" onchange="submit()">
								<form:option value="-1" label="------Seleziona Categoria-------" />
								<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
							</form:select> <form:hidden path="categoria.descrizione" /></td>
					</tr>
					<logic:notEqual name="beneForm" property="tipologia.id" value="5">
						<tr>
							<td><form:label path="sottocategoria.id">
									<spring:message code="bene.sottocategoria" />
								</form:label></td>
							<td colspan="3"><form:select path="sottocategoria.id" size="1" onchange="submit()">
									<form:option value="-1" label="------Seleziona Sottocategoria-------" />
									<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
								</form:select></td>
							<form:hidden path="sottocategoria.descrizione" />
						</tr>
					</logic:notEqual>
				</logic:equal>
			</table>

			<table class="tb_form">

				<logic:notEmpty name="beneForm" property="criteriFiltro">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="bene.benilistafiltrati" /></td>
					</tr>
				</logic:notEmpty>
				<logic:empty name="beneForm" property="criteriFiltro">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="bene.lista.beni" /></td>
					</tr>
				</logic:empty>
			</table>
			<c:forEach items="${risultatiRicerca}" var="bene">
				<script type="text/javascript">	
						arrayIdBene[++i] = <c:out value="${bene.idBene}"/>;
					</script>
			</c:forEach>
			<display:table class="tb_lista" uid="risultatiRicerca" name="${risultatiRicerca}" pagesize="10" requestURI="${azione}">
				<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
					<form:checkbox id="listaIdCheckbox" path="listaIdCheckbox" value="${risultatiRicerca.idBene}" />
				</display:column>
				<display:column property="idBene" title="Identificativo" sortable="false" />
				<display:column property="descrizioneBene" title="Descrizione" sortable="false" />
				<c:if test="${procedimento.tipoProcedimento == null || procedimento.tipoProcedimento.id != 9 }">	
					<display:column property="titoliDiDiritto" title="Titoli di Diritto" sortable="false" />
				</c:if>
			</display:table>

			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<logic:notEmpty name="risultatiRicerca">
						<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio"></td>
					</logic:notEmpty>

				</tr>
			</table>

		</form:form>


		<logic:equal name="beneForm" property="visualizzaFiltro" value="true">

			<script src="${pageContext.request.contextPath}/js/suggestlist/doAutocompleteIdBene.js"></script>

			<script type="text/javascript">
					$(document).ready(function() {
						$("input[type='text']:first", document.forms[0]).focus();
						doAutocompleteIdBene("inputIdBene",null,"idDecretoSequestroEProposta");
					});

			</script>
		</logic:equal>
			
		<script type="text/javascript">
				$(document).ready(function() {
					disabilitaSubmitConvalidaSeq('${disabledViewProc}','${disabledViewMenu}','${profilo.idTipoUfficio}');
				});
			</script>
						
	</tiles:putAttribute>
</tiles:insertDefinition>
