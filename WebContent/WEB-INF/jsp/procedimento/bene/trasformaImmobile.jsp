<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione"
			value="/procedimento/bene/trasformaBeneImmobile.htm" />
		<p class="titolo_page">
			<spring:message code="bene.immobile.trasformazione" />
		</p>
		<form:form method="post" action="${azione}" commandName="beneForm" id="idFormModifica">
			<form:hidden path="tipologia.id"/>
			<form:hidden path="idBene"/>
			<form:hidden path="tipoTrasformazione.id"/>
			
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<br/>
				 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br/>
			
			<jsp:include page="inputImmobile.jsp"></jsp:include>

			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Salva" name="Salva"></td>
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
		</form:form>
		
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
		<smp:back buttonId="backButton"></smp:back>
		
		
		<script type="text/javascript">
			$(document).ready(function() {
				prendiData("#dataAccatastamento",true);
				if($( "#id_Stato" ).val() != undefined){
				  controllaInizializzazioneCampi($( "#id_Stato" ).val(),"Comune");
				  doAutocompleteImmobili("Comune");
				}
				doAutocompleteImmobili("ComuneCatastaleP");
				
				$("#datiCatastaliOrigine").hide();
				$("#divIdentificativoImmobile").hide();
				$("#divDenunciaImmobile").hide();
				$("#divIdentificativoImmobileP").hide();
				$("#divDenunciaImmobileP").hide();

        		openCloseDiv("switchDenunciaIdentifImmobile","divIdentificativoImmobile","divDenunciaImmobile");
				openCloseDiv("switchDenunciaIdentifImmobileP","divIdentificativoImmobileP","divDenunciaImmobileP");

				

				
				$('#idFormModifica').bind('submit', function() {
					//elimino tutti i disable prima di fare submit
			        //$(this).find(':disabled').removeAttr('disabled');
			    });
        					
			});


			function openCloseDiv(open, close){
				if(open != '')
					$("#"+open).show(300);
				if(close != '')
					$("#"+close).hide(300);
			}

			function openCloseDiv(check,idDivI, idDivD){
				var radioButton = $("input[name="+check+"]:checked");
				var idCheck = radioButton.val();

				var divDenuncia = $("#"+idDivD);
				var divIdentificativo = $("#"+idDivI);

				
				if(idCheck=='D'){
					divDenuncia.show(300);
					divIdentificativo.hide(300);
					
				}else{
					divIdentificativo.show(300);
					divDenuncia.hide(300);
				}
			}

			function controllaCodiceCastasto(idCodice,elem){
				var value = $("#"+idCodice).val();
				if(value != undefined && value.length>0){
					$(elem).removeAttr('readonly');
				}else{
					$(elem).attr('readonly', 'readonly');
				}
			}
			
		</script>		

	</tiles:putAttribute>
</tiles:insertDefinition>
