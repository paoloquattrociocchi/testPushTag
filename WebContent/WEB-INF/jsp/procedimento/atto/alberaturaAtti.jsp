<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
	
		<style>
			span {
				font-weight: bold;
			}
			
			/* 	a[href]:hover {text-decoration: none; color: black; font-style:italic}  */
		</style>
		<script type="text/javascript">
		
			function caricaFonte(idFonte, idTipoFonte, idOrgano, idProvinciaOrgano, idComuneOrgano) {	
				
				document.getElementById('id_hidden_fonte').value=idFonte;
				document.getElementById('id_hidden_tipo_fonte').value=idTipoFonte;
				document.getElementById('id_hidden_organo').value=idOrgano;	
				document.getElementById('id_hidden_provincia_organo').value=idProvinciaOrgano;	
				document.getElementById('id_hidden_comune_organo').value=idComuneOrgano;	
				document.getElementById('id_button_carica_fonte').click();
			}
			
			
			function caricaAttoInclude(idAtto,idTipoAtto) {	
				
				document.getElementById('id_hidden_atto').value=idAtto;
				document.getElementById('id_hidden_tipo_atto').value=idTipoAtto;
			}
			
			
			function caricaAtto(idAtto,idTipoAtto) {
				
				caricaAttoInclude(idAtto,idTipoAtto);
				document.getElementById('id_button_carica_atto').click();
			}
			
			function caricaDecretoFissazioneUdienza(idAtto, idTipoAtto) {
				caricaAttoInclude(idAtto,idTipoAtto);
				
				$("#id_formCaricaAtto").attr("action", contextPath+"/procedimento/atto/inserimentoUdienza.htm"); 
				$("#id_button_carica_atto").attr("name", "CaricaUdienza"); 
				$("#id_button_carica_atto").click();
			}

			function caricaAttoRedazioneScheda(idAtto, idTipoAtto) {
				caricaAttoInclude(idAtto,idTipoAtto);
				$("#id_formCaricaAtto").attr("action", contextPath+"/procedimento/atto/redazioneScheda.htm"); 
				$("#id_button_carica_atto").attr("name", "CaricaRedazioneScheda"); 
				$("#id_button_carica_atto").click();
			}

			/*
			function caricaAttoRicevuta(idAtto, idTipoAtto) {
				caricaAttoInclude(idAtto,idTipoAtto);
				$("#id_formCaricaAtto").attr("action", contextPath+"/procedimento/atto/redazioneScheda.htm"); 
				$("#id_button_carica_atto").attr("name", "CaricaAttoRicevuta"); 
				$("#id_button_carica_atto").click();
			}*/
			
			function caricaVerbaleUdienza(codUdienza) {
				document.getElementById('id_hidden_udienza').value=codUdienza;
				document.getElementById('id_button_carica_udienza').click();
			}
			
			var Tree = new Array;
			var i=-1;	
		
		</script>
	
		<spring:url var="azione" value="/procedimento/apriFascicolo.htm" />
		<form:form method="post" action="${azione}" commandName="atto" id="idAttoForm">
			<p class="titolo_page">
				<spring:message code="atti.alberatura" />
			</p>
			<form:hidden path="codAtto" id="idCodAtto"/>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<div class="tree">
					<c:forEach items="${albero}" var="node">
						<script type="text/javascript">									
								Tree[++i]= ${node.nodeId}+'|'+${node.fatherId}+'|'+'${node.label}'+'|'+'${node.url}'+'|'+'${node.leafIcon}'+'|'+${node.attachment} +'|'+${node.deletable} +'|'+${node.idAtto};							
							</script>
					</c:forEach>
					<script type="text/javascript">						
							createTree(Tree);
							oc(1,1);
						</script>
				</div>
			</table>
			<input type="submit" id="idEliminaAtto" name="EliminaAtto" title="EliminaAtto"  value="" style="display: none" />
		</form:form>

		<spring:url var="azioneCaricaFonte" value="/procedimento/atto/inserimentoFonte.htm" />
		<form:form id="id_form" method="post" action="${azioneCaricaFonte}" commandName="atto">
			<form:hidden id="id_hidden_fonte" path="codAtto" />
			<form:hidden id="id_hidden_tipo_fonte" path="fonte.id" />
			<form:hidden id="id_hidden_organo" path="organo.id" />
			<form:hidden id="id_hidden_provincia_organo" path="provinciaOrgano.id" />
			<form:hidden id="id_hidden_comune_organo" path="comuneOrgano.id" />
			<form:hidden id="id_hidden_tipo_fonte" path="tipoAtto.id" value="12" />
			<input id="id_button_carica_fonte" type="submit" name="CaricaFonte" title="" value="" style="visibility: hidden">
		</form:form>


		<spring:url var="azioneCaricaAtto" value="/procedimento/atto/copertinaAtto.htm" />
		<form:form id="id_formCaricaAtto" method="post" action="${azioneCaricaAtto}" commandName="atto">
			<form:hidden id="id_hidden_atto" path="codAtto" />
			<form:hidden id="id_hidden_tipo_atto" path="tipoAtto.id" />
			<input id="id_button_carica_atto" type="submit" name="CaricaAtto" style="visibility: hidden">
		</form:form>

		<spring:url var="azioneCaricaVerbaleUdienza" value="/procedimento/udienza/visualizzaUdienze.htm" />
		<form:form id="id_formCaricaAtto" method="post" action="${azioneCaricaVerbaleUdienza}" commandName="udienza">
			<form:hidden id="id_hidden_udienza" path="codUdienza" />
			<input id="id_button_carica_udienza" type="submit" name="DettaglioUdi" style="visibility: hidden">
		</form:form>

		<br>
		<br>
		<br>
<script type="text/javascript">
function confermaEliminaAtto(idAtto) {
	 if(confirm('Sei sicuro di voler eliminare l\'\atto selezionato?')){
		 $('#idCodAtto').val(idAtto);
		 $('#idAttoForm').attr('action', '${pageContext.request.contextPath}/procedimento/atti/elenco.htm');
		 $('#idEliminaAtto').click();
		return true;
	}
		return false;
}
</script>
	</tiles:putAttribute>
</tiles:insertDefinition>