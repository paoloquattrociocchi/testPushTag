<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="it.giustizia.smp.utility.Constants"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
		<spring:url var="azione" value="/procedimento/soggetto/startAnnullaSoggetto.htm" />
		<spring:url var="paginazione" value="/procedimento/soggetto/visualizzaSoggettiPerAnnullamento.htm" />
		<p class="titolo_page"><spring:message code="soggetto.annullabile.titolo" /></p>
		<form:form method="post" action="${azione}" commandName="soggettoForm" id="idFormAnnullaSoggetto">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<form:hidden path="idSoggetto" id="idSoggetto" />
			<form:hidden path="idVersione" id="idVersione" />
			<form:hidden path="tipoSoggetto" id="tipoSoggetto" />
			<form:hidden path="descrRuolo" id="descrRuolo" />
			
			<form:hidden path="idSoggettoDuplicato" id="idSoggettoDuplicato" />
			<form:hidden path="idVersioneDuplicato" id="idVersioneDuplicato" />
			<form:hidden path="tipoSoggettoDuplicato" id="tipoSoggettoDuplicato" />
			<form:hidden path="descrRuoloDuplicato" id="descrRuoloDuplicato" />

			<br />
			<display:table class="tb_lista" name="${risultatiRicerca}" uid="sogg" pagesize="10" requestURI="${paginazione}" >
				<display:column style="text-align: center; width: 3%" title="Valido">
					<input type="radio" name="radioValido" id="idRadioValido" value="${sogg_rowNum}"
						onclick="selectIDValido('${sogg.idSoggetto}', '${sogg.idVersione}', '${sogg.tipoSoggetto}', '${sogg.descrRuolo}');" />
				</display:column>
				<display:column style="text-align: center; width: 5%" title="Duplicato">
					<input type="radio" name="radioDuplicato" id="idRadioDuplicato" value="${sogg_rowNum}"
						onclick="selectIDDuplicato('${sogg.idSoggetto}', '${sogg.idVersione}', '${sogg.tipoSoggetto}' ,'${sogg.descrRuolo}');" />
				</display:column>
				<display:column property="descrizioneSoggetto" title="Generalità" sortable="false" />
				<display:column property="descrRuolo" title="Ruolo" sortable="false" />
			</display:table>
			
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="button" value="Annulla Soggetto" name="" title="Inizia Annullamento Soggetto" class="nowait" onclick="return validaCheckBoxAnnullamento()"/>
					<input id="idStartAnnullaSoggetto" type="submit" name="startAnnullaSoggetto" value="" style="display: none" /> </td>
				</tr>
			</table>
			
			<c:set var="SOGGETTO_DIRETTO" value="<%=Constants.RUOLO_DIRETTO %>"/>
			<c:set var="SOGGETTO_TERZO" value="<%=Constants.RUOLO_TERZO%>"/>
		</form:form>
		
			<script type="text/javascript">
		
			function selectIDValido(idSoggetto, idVersione, tipoSoggetto, descrRuolo) {
				document.getElementById("idSoggetto").value = idSoggetto;
				document.getElementById("idVersione").value = idVersione;
				document.getElementById("tipoSoggetto").value = tipoSoggetto;
				document.getElementById("descrRuolo").value = descrRuolo;
			}
			function selectIDDuplicato(idSoggetto, idVersione, tipoSoggetto, descrRuolo) {
				document.getElementById("idSoggettoDuplicato").value = idSoggetto;
				document.getElementById("idVersioneDuplicato").value = idVersione;
				document.getElementById("tipoSoggettoDuplicato").value = tipoSoggetto;
				document.getElementById("descrRuoloDuplicato").value = descrRuolo;
			}

			function validaCheckBoxAnnullamento(){
			

			var idSoggValido = $('input[name=radioValido]:checked', '#idFormAnnullaSoggetto').val();
			var idSoggDuplicato = $('input[name=radioDuplicato]:checked', '#idFormAnnullaSoggetto').val();

			var ruoloValido= $('#descrRuolo').val();
			var ruoloDuplicato = $('#descrRuoloDuplicato').val();


			if(typeof idSoggValido==='undefined'){
				alert("E' obbligatorio selezionare un soggetto valido");
				return false;
			}

			if(typeof idSoggDuplicato==='undefined'){
				alert("E' obbligatorio selezionare un soggetto duplicato");
				return false;
			}
			
			
			if(idSoggValido==idSoggDuplicato){
				alert ("Operazione non possibile. Non può essere lo stesso soggetto.");
				return false;
			}

			if(ruoloValido=="<c:out value='${SOGGETTO_TERZO}'/>" && ruoloDuplicato.indexOf("<c:out value='${SOGGETTO_DIRETTO}'/>") != -1 ){
				alert ("Operazione non possibile. Impossibile annullare un soggetto con ruolo Diretto se il valido è Terzo");
				return false;
			}

			$('#idStartAnnullaSoggetto').click();
				return true;
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
