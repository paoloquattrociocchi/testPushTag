<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="it.giustizia.smp.utility.Constants"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
		<spring:url var="azione" value="/procedimento/bene/startAnnullaBene.htm" />
		<p class="titolo_page"><spring:message code="bene.annullabile.titolo" /></p>
		<form:form method="post" action="${azione}" commandName="beneForm" id="idFormAnnullaBene">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<form:hidden path="idBene" id="idBene" />
			<form:hidden path="idUffOwner" id="idUffOwner" />
			<form:hidden path="idTipologia" id="idTipologia" />
			<form:hidden path="idCategoria" id="idCategoria" />
			<form:hidden path="idSottoCategoria" id="idSottoCategoria" />
			
			<form:hidden path="idBeneDuplicato" id="idBeneDuplicato" />
			<form:hidden path="idUffOwnerDuplicato" id="idUffOwnerDuplicato" />
			<form:hidden path="idTipologiaDuplicato" id="idTipologiaDuplicato" />
			<form:hidden path="idCategoriaDuplicato" id="idCategoriaDuplicato" />
			<form:hidden path="idSottoCategoriaDuplicato" id="idSottoCategoriaDuplicato" />

			<br />
			<display:table class="tb_lista" name="${risultatiRicerca}" uid="bene" pagesize="10" requestURI="${azione}" >
				<display:column style="text-align: center; width: 3%" title="Valido">
					<input type="radio" name="radioValido" id="idRadioValido" value="${bene_rowNum}"
						onclick="selectIDValido('${bene.idBene}', '${bene.idUffOwner}', '${bene.idTipologia}', '${bene.idCategoria}', '${bene.idSottoCategoria}' );" />
				</display:column>
				<display:column style="text-align: center; width: 5%" title="Duplicato">
					<input type="radio" name="radioDuplicato" id="idRadioDuplicato" value="${bene_rowNum}"
						onclick="selectIDDuplicato('${bene.idBene}', '${bene.idUffOwner}', '${bene.idTipologia}', '${bene.idCategoria}', '${bene.idSottoCategoria}');" />
				</display:column>
				<display:column property="tipologiaCategoriaSottoCategoria" title="Tipologia/Categoria/SottoCategoria bene" sortable="false" />
				<display:column property="descrizioneBene" title="Descrizione bene" sortable="false" />
			</display:table>
			
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="button" value="Annulla Bene" name="" title="Inizia Annullamento Bene" class="nowait" onclick="return validaCheckBoxAnnullamento()"/>
					<input id="idStartAnnullaBene" type="submit" name="startAnnullaBene" value="" style="display: none" /> </td>
				</tr>
			</table>
			
		</form:form>
		
			<script type="text/javascript">
		
			function selectIDValido(idBene, idUffOwner, idTipologia, idCategoria, idSottoCategoria) {
				document.getElementById("idBene").value = idBene;
				document.getElementById("idUffOwner").value = idUffOwner;
				document.getElementById("idTipologia").value = idTipologia;
				document.getElementById("idCategoria").value = idCategoria;
				document.getElementById("idSottoCategoria").value = idSottoCategoria;
			}
			function selectIDDuplicato(idBene, idUffOwner, idTipologia, idCategoria, idSottoCategoria) {
				document.getElementById("idBeneDuplicato").value = idBene;
				document.getElementById("idUffOwnerDuplicato").value = idUffOwner;
				document.getElementById("idTipologiaDuplicato").value = idTipologia;
				document.getElementById("idCategoriaDuplicato").value = idCategoria;
				document.getElementById("idSottoCategoriaDuplicato").value = idSottoCategoria;
			}

			function validaCheckBoxAnnullamento(){
				$('#idStartAnnullaBene').click();
				return true; 
				
				var idBeneValido = $('input[name=radioValido]:checked', '#idFormAnnullaBene').val();
				var idBeneDuplicato = $('input[name=radioDuplicato]:checked', '#idFormAnnullaBene').val();
	
				var idTipologiaValido= $('#idTipologia').val();
				var idTipologiaDuplicato = $('#idTipologiaDuplicato').val();
	
				var idCategoriaValido= $('#idCategoria').val();
				var idCategoriaDuplicato = $('#idCategoriaDuplicato').val();
	
				var idSottoCategoriaValido= $('#idSottoCategoria').val();
				var idSottoCategoriaDuplicato = $('#idSottoCategoriaDuplicato').val();
	
				if(typeof idBeneValido==='undefined'){
					alert("E' obbligatorio selezionare un bene valido");
					return false;
				}
	
				if(typeof idBeneDuplicato==='undefined'){
					alert("E' obbligatorio selezionare un bene duplicato");
					return false;
				}
				
				
				if(idBeneValido==idBeneDuplicato){
					alert ("Operazione non possibile. Non può essere lo stesso bene.");
					return false;
				}
	
				if(idTipologiaValido>0 && idTipologiaDuplicato>0 & idTipologiaValido!= idTipologiaDuplicato){
					alert ("Operazione non possibile. Non sono della stessa tipologia");
					return false;
				}
	
				if(idCategoriaValido>0 && idCategoriaDuplicato>0 && idCategoriaValido!= idCategoriaDuplicato){
					alert ("Operazione non possibile. Non sono della stessa categoria");
					return false;
				}
	
				if(idSottoCategoriaValido>0 && idSottoCategoriaDuplicato>0 && idSottoCategoriaValido!= idSottoCategoriaDuplicato){
					alert ("Operazione non possibile. Non sono della stessa sotto categoria");
					return false;
				}
	
				$('#idStartAnnullaBene').click();
					return true;
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
