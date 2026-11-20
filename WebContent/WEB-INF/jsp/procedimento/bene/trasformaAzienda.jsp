<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/trasformaBeneAzienda.htm" />
		<p class="titolo_page">
			<spring:message code="bene.azienda.trasformazione" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm" id="idFormModifica">
			
			<form:hidden path="idBene"/>
			<form:hidden path="tipologia.id" />
			
			<form:hidden path="tipoTrasformazione.id"/>
			
			
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include
				page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			
			<jsp:include page="inputAzienda.jsp"></jsp:include>
			
			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Salva" name="Salva"></td>
					<td class="td_tasto"><input id="backButton" type="button"value="Indietro" title="Indietro"></td>
				</tr>
			</table>
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>


		<script type="text/javascript">
			$(document).ready(function() {
				prendiData("#dataRegistrazioneMin",false);
				prendiData("#dataRegistrazioneMax",false);
				
				inizializzaSubmitAutocomplete('Salva');
				var stato = $("#id_Stato").val();
				if ($("#id_Stato").val() != undefined) {
					controllaInizializzazioneCampi($("#id_Stato").val(), "Comune");
				}

				$('#idFormModifica').bind('submit', function() {
			        $(this).find(':disabled').removeAttr('disabled');

			        //imposto la descrizione della sotto categoria
			        var desc = $("#id_tipostrada :selected").text();
			        $('#desc_tipostrada').val(desc);

			        submitWithAutocompleteComuni(['Stato'],['Comune'],'write');
			        
			    });
			    
			});
			
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
