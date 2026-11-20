<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/modificaBeneVeicolo.htm" />
		<p class="titolo_page">
			<spring:message code="bene.mobileRegistrato.modifica" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm" id="idFormModifica">
			<form:hidden path="tipologia.id" />
			<form:hidden path="idBene"/>
			
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene" /> <c:out value="${beneForm.idBene}" /></td>
				</tr>
			</table>
			<br />
			<jsp:include page="inputVeicolo.jsp"></jsp:include>

			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Salva" name="Modifica"></td>
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>


		<script type="text/javascript">
				$(document).ready(function() {
					prendiData("#dataRegistrazione",true);


					if(!<c:out value="${modificabile}" default="false"/>){
						//disabilito i campi che non possono essere modificati
						if($('[name="targa"]').val().length>0){
							$('[name="targa"]').attr('disabled','disabled');
						}else{
							$('[name="telaio"]').attr('disabled','disabled');
						}
						//$('[name="modelloVeicolo"]').attr('disabled','disabled');
					}

					//elimino tutti i disable prima di fare submit
					$('#idFormModifica').bind('submit', function() {
				        $(this).find(':disabled').removeAttr('disabled');

				        //imposto la descrizione della sotto categoria
				        var desc = $("#id_sottocategoria :selected").text();
				        $('#desc_sottocategoria').val(desc);
				    });
				});
				function initCampo(){
				  //document.getElementById('desc_tipostrada').value = document.getElementById('id_tipostrada').options[document.getElementById('id_tipostrada').selectedIndex].text;    
			      //document.getElementById('desc_sottocategoria').value = document.getElementById('id_sottocategoria').options[document.getElementById('id_sottocategoria').selectedIndex].text;    

				}
			</script>

	</tiles:putAttribute>
</tiles:insertDefinition>
