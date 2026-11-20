<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="it.giustizia.smp.utility.Constants"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<script type="text/javascript">
			var arrayIdBene = new Array();
			var i=-1;
		</script>

		<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>

		<spring:url var="azione"
			value="/procedimento/bene/fug/beniFug.htm" />			
		<p class="titolo_page">
			<spring:message code="fug.beni" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm">
		
	     
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br/>
			
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="fug.beni.beni" /></td>
				</tr>
			</table>
			
			<logic:present name="risultatiRicerca">	
				<c:forEach items="${risultatiRicerca}" var="bene">  
					<script type="text/javascript">	
						arrayIdBene[++i] = <c:out value="${bene.idBene}"/>;
					</script>
				</c:forEach>
				
				<display:table class="tb_lista" uid="risultatiRicerca" name="${risultatiRicerca}" pagesize="10" requestURI="${azione}" >
					<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:radiobutton id="listaIdCheckbox" path="listaIdCheckbox" value="${risultatiRicerca.idBene}" />
					</display:column>					
					<display:column property="idBene" title="Identificativo" sortable="false" />
					<display:column property="descrizioneBene" title="Descrizione" sortable="false" />
				</display:table>
				
				<br />
				<br />

				<table class="tb_bottoni" align="center">
					<tr>
					<logic:notEmpty name="risultatiRicerca">
						<td class="td_tasto"><input type="submit" value="Seleziona" name="Dettaglio"></td>
						 
					</logic:notEmpty>
						
					</tr>
				</table>
			</logic:present>
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
		
		
	</tiles:putAttribute>
</tiles:insertDefinition>
