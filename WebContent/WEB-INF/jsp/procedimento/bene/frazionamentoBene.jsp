<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>



<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<p class="titolo_page">
			<spring:message code="bene.frazionamento" />
		</p>
		<spring:url var="azione" value="/procedimento/bene/frazionamentoBene.htm" />
		<form:form method="post" action="${azione}" commandName="beneForm">
			<input type="hidden" id="id_selectAll_listaIdCheckbox" value="true" />
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />

			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.dafrazionare" /> <c:out value="${bene.idBene}" />&nbsp;&nbsp; - &nbsp;&nbsp; <c:out value="${bene.descrizioneBene}" /></td>
				</tr>
				<form:hidden path="idBene" />



			</table>
			<br />
			<display:table class="tb_lista" uid="beni" name="${beni}" pagesize="10" requestURI="${azione}">

		   <display:column paramProperty="listaIdCheckbox" style="width:7%" title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('beni','listaIdCheckbox');\" title=\"Seleziona/Deseleziona tutti i beni\"></img>&nbsp;Sel.bene" >															
						<form:checkbox path="listaIdCheckbox" value="${beni.idBene}" />
				</display:column>
				<display:column property="idBene" title="Identificativo del Bene" sortable="false" />
				<display:column property="descrizioneBene" title="Descrizione del Bene" sortable="false" />
			</display:table>


			<br />
			<br />
			<table class="tb_bottoni" align="center">
			<logic:notEmpty name="beni"> 
				<tr>
					<td class="td_tasto"><input type="submit" value="Conferma" title="Conferma" name="ConfermaFrazionamentoBene"></td>
					<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/bene/gestioneBeniDelAtto.htm')"></td>
				</tr>
			</logic:notEmpty>
			<logic:empty name="beni">
				<tr> 
					<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/bene/gestioneBeniDelAtto.htm')"></td>
				</tr>
			</logic:empty>
			</table>
		</form:form>
		<script type="text/javascript">
				$(document).ready(function() {
					caricaIdBene();
				});
				caricaIdBene(){			
			  		var temp = document.getElementById('id_bene_2').value;
			  		document.getElementById('id_bene').value= temp;
                    }
			</script>


	</tiles:putAttribute>
</tiles:insertDefinition>
