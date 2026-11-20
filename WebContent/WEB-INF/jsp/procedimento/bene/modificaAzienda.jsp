<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/modificaBene.htm" />
		<p class="titolo_page">
			<spring:message code="bene.azienda.modifica" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm" id="idFormModifica">
			
			<form:hidden path="idBene"/>
			<form:hidden path="tipologia.id" />
			
			
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include
				page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
					<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene" /> <c:out value="${beneForm.idBene}" /></td>
				</tr>
			</table>
<br />
			<jsp:include page="inputAzienda.jsp"></jsp:include>

			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Salva" name="Modifica"
						onclick="javascritp:initCampo(); return submitWithAutocompleteComuni(['Stato'],['Comune'],'write')">
					</td>
					<td class="td_tasto"><input id="backButton" type="button"
						value="Indietro" title="Indietro"></td>
				</tr>
			</table>
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>


		<script type="text/javascript">
			$(document).ready(function() {
				inizializzaSubmitAutocomplete('Modifica');
				if ($("#id_Stato").val() != undefined) {
					controllaInizializzazioneCampi($("#id_Stato").val(), "Comune");
				}

				if(!<c:out value="${modificabile}" default="false"/>){
					$('[name="ragioneSociale"]').attr('disabled','disabled');
					$('[name="stato.id"]').attr('disabled','disabled');
					
					if($('[name="codiceFiscale"]').val().length!=0){
						$('[name="codiceFiscale"]').attr('disabled','disabled');
					}
					if($('[name="partitaIva"]').val().length!=0){
						$('[name="partitaIva"]').attr('disabled','disabled');
						$('[name="partitaIvaSiglaStato"]').attr('disabled','disabled');
					}


					$('#idFormModifica').bind('submit', function() {
				        $(this).find(':disabled').removeAttr('disabled');
				    });
									
					
				}
			});
			function initCampo() {
				document.getElementById('desc_tipostrada').value = document.getElementById('id_tipostrada').options[document.getElementById('id_tipostrada').selectedIndex].text;
				document.getElementById('desc_categoria').value = document.getElementById('id_categoria').options[document.getElementById('id_categoria').selectedIndex].text;

			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
