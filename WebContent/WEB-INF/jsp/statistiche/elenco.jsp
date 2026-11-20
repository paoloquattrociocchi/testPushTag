<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/statistiche/elenco.htm" />
		<p class="titolo_page">
			<spring:message code="statistiche.elenco.titolo" />
		</p>
		<form:form id="mainForm" method="post" action="${azione}" commandName="statisticheForm">
			<form:hidden id="hiddenIdStampa"  path="idStampa" />
			<br/>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="2"><spring:message code="statistiche.filtri.sottotitolo"/></td>
				</tr>
				<tr>
					<td width="150px"><spring:message code="statistiche.elenco.filtro1" /></td>
					<td>
						<form:select  path="filtro1.id" size="1" onchange="refreshLista(1)" >
							<form:option value="-1" label="- Seleziona -"/>
							<form:options items="${valoriFiltro1}"  itemValue="id" itemLabel="descrizione" /> 																	
						</form:select>
					</td>	
				</tr>
				<tr>
					<td width="150px"><spring:message code="statistiche.elenco.filtro2" /></td>
					<td>
						<form:select  path="filtro2.id" size="1"  onchange="refreshLista(2)" >
							<form:option value="-1" label="- Seleziona -"/>
							<form:options items="${valoriFiltro2}"  itemValue="id" itemLabel="descrizione" /> 																	
						</form:select>
					</td>	
				</tr>
				<tr>
					<td width="150px"><spring:message code="statistiche.elenco.filtro3" /></td>
					<td>
						<form:select  path="filtro3.id" size="1"  onchange="refreshLista(3)" >
							<form:option value="-1" label="- Seleziona -"/>
							<form:options items="${valoriFiltro3}"  itemValue="id" itemLabel="descrizione" /> 																	
						</form:select>
					</td>	
				</tr>
			</table>
			<br/>
			
									
			<display:table class="tb_lista" name="${listaStampe}" uid="listaStampe" pagesize="10" requestURI="${azione}">
				<display:column title="Seleziona" sortable="false"  style="text-align: center; width: 5%">
					<form:radiobutton path="idStampa" value="${listaStampe.codiKIdStampa}" onclick="impostaHidden('${listaStampe.codiKIdStampa}')" />
				</display:column>
				<display:column property="descStampa" title="Nome Report" sortable="true" style="text-align: left; width: 95%" />
			</display:table>

			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto">
						<input type="submit" value="Visualizza" name="Visualizza">
					</td>
				</tr>
			</table>
		</form:form>
		<script type="text/javascript">
			function impostaHidden(idStampa){
				document.getElementById("hiddenIdStampa").value = idStampa;
			}

			function refreshLista(filtro){
				$("#mainForm").attr("action", contextPath+"/statistiche/elencoFiltrato.htm?filtro="+filtro);
				$("#mainForm").submit();
			}
			
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>