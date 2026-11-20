	<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
	<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
	<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	
	<%@ page import="it.giustizia.smp.form.AttoForm"%>
	<%@ page import="it.giustizia.smp.form.ComboForm"%>
	<%@ page import="it.giustizia.smp.form.SpeseSoggettoForm"%> 
	<%@ page import="it.giustizia.smp.form.SoggettoForm"%> 
	<%@ page import="it.giustizia.smp.utility.Constants"%>
	<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
	<%
		AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
		String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
		
	%>
	    <logic:equal value="Y" name="speseSoggetto" property="paginazione" scope="request">
	    <c:set var="PAGINAZIONE" value="3" />
		</logic:equal>
		<logic:equal value="N" name="speseSoggetto" property="paginazione" scope="request">
	    <c:set var="PAGINAZIONE" value="" />
		</logic:equal> 
		<tiles:insertDefinition name="base.definition">
			<tiles:putAttribute name="body-content" >
				<spring:url var="azione" value="/procedimento/atto/visualizzaDirettiInteressati.htm" />
				<spring:url var="filtro" value="/procedimento/atto/visualizzaDirettiInteressati.htm" />
				<spring:url var="filtroTutti" value="/procedimento/atto/visualizzaDirettiInteressati.htm" />
				
				<p class="titolo_page"> 	
						<spring:message code="condanna.spese.processuali" />
				</p>
				<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
	
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
	
				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
	
				<br />	
				
				<br />
				
				<form:form method="post" name="${soggettiSpeseP}" action="${azione}" commandName="speseSoggettoForm" id="idspeseSoggettoForm" onsubmit="checkParentsIfChildChecked();"> 
					<%--  <form:hidden path="paginazione" /> --%>
					
				<c:if test="${attoForm.statoAtto.id != 3 && (esitoReadOnly==null || esitoReadOnly=='false')}">
								
					<table class="tb_form" border="0">
		
						<tr>
						 
							<td class="td_titolo" colspan="3">
							 
								<form:checkbox  id="checkTutti" path="checkTutti" onclick="validaCheckTutti(this);" />
								<spring:message code="soggetto.filtro.applica.tutti" />	
							    <form:select id="speseProcessuali" path="speseProcessuali.id" size="1" > 	  						
								    <form:option value="-1" label="---Non Condannato---" />
									<form:options items="${listaSpeseProcessuali}" itemValue="id" itemLabel="descrizione" />
							    </form:select>
							 </td> 
							
						</tr>
						
					</table>
					
					<input type="hidden" id="id_selectAll_beniProcedimento.idBene" value="true"/>
					<input type="hidden" id="id_selectAll_listaIdCheckati" value="true"/>
					<!--  Inizio lista  -->
					
					<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=17}" >
						<%--  <display:table class="tb_lista" uid="elemRisultatoRicerca" name="${elemRisultatoRicerca.soggetti}" pagesize="${PAGINAZIONE}" requestURI="${azioneElimina}" > --%>
						<display:table class="tb_lista" name="${soggettiSpese}" id="soggetto" pagesize="${PAGINAZIONE}" requestURI="${azione}" >
							<display:column property="descrizioneSoggetto" title="Soggetti"   sortable="false" />
							<display:column property="descrRuolo" title="Ruolo" sortable="false" />
							<display:column property="speseProcessuali.descrizione" title="Importo" sortable="false" />
							<form:hidden path="soggettiSpeseP[${soggetto_rowNum-1}].idSoggetto" id="idSoggetto" value="${idSoggetto}" size="1" />
							<display:column title="Importo Spese Processuali" sortable="false" >
								<form:select path="soggettiSpeseP[${soggetto_rowNum-1}].speseProcessuali.id" size="1" >
									<form:option value="-1" label="---Non Condannato---" />
								    <c:forEach items="${listaSpeseProcessuali}" var="item">
								    <c:choose>
									    <c:when test="${soggetto.speseProcessuali.id==item.id }">
									    <form:option value="${item.id }" selected="true">
									    	<c:out value="${item.descrizione }"/>
									    </form:option>
									    </c:when>
									    <c:otherwise>
									    <form:option value="${item.id }">
									    	<c:out value="${item.descrizione }"/>
									    </form:option>
									    </c:otherwise>
									    </c:choose>
								    </c:forEach>
								 </form:select>	 
							</display:column>	
							
<%-- 							<display:column title="Importo Spese Processuali" sortable="false" > --%>
<%-- 								<form:select path="soggettiSpeseP[${soggettoAssociato_rowNum-1}].speseProcessuali.id" size="1" > 	  						 --%>
<%-- 								    <form:option value="-1" label="---Seleziona Importo---" /> --%>
<%-- 									<form:options items="${listaSpeseProcessuali}" itemValue="id" itemLabel="descrizione" /> --%>
<%-- 								 </form:select>	  --%>
<%-- 							</display:column> --%>
	
						</display:table>
						<br />	
				
						<br />
						<table class="tb_bottoni" align="center">
						<tr>
							<td class="td_tasto">
								<c:choose>
								<c:when test="${esitoReadOnly!=null && esitoReadOnly=='true'}">
									<input type="submit" value="Annulla" name="AnnullaSpese" disabled="disabled">
								</c:when>
								<c:otherwise>
									<input type="submit" value="Annulla" name="AnnullaSpese">
								</c:otherwise>
								</c:choose>
							</td>
					
							<td class="td_tasto">
								<c:choose>
								<c:when test="${esitoReadOnly!=null && esitoReadOnly=='true'}">
									<input type="submit" value="Salva Spese" name="SalvaSpese" id="SalvaSpese" disabled="disabled">
								</c:when>
								<c:otherwise>
									<input type="submit" value="Salva Spese" name="SalvaSpese" id="SalvaSpese">
								</c:otherwise>
								</c:choose>
							</td>
							<td class="td_tasto">
								<c:choose>
								<c:when test="${esitoReadOnly!=null && esitoReadOnly=='true'}">
									<input type="submit" value="Elimina Spese" title="Cancella" name="CancellaSpese"  disabled="disabled"  class="nowait"  />
								</c:when>
								<c:otherwise>
									<input type="submit" value="Elimina Spese" title="Cancella" name="CancellaSpese"  onClick="return confermaCancellazione(this)"   class="nowait"  />
								</c:otherwise>
								</c:choose>
							</td>
											
							<td class="td_tasto">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atto/copertinaAtto.htm')">
							</td>						
						</tr>
						</table>
					
					</c:if>
				</c:if>
				
				<c:if test="${attoForm.statoAtto.id == 3 || (esitoReadOnly!=null && esitoReadOnly=='true')}">	
					<table class="tb_form" border="0">
		
						<tr>
							<td class="td_titolo" colspan="3">	
							 </td> 
						</tr>	
					</table>
					
					<input type="hidden" id="id_selectAll_beniProcedimento.idBene" value="true"/>
					<input type="hidden" id="id_selectAll_listaIdCheckati" value="true"/>
					<!--  Inizio lista  -->
					
					<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=17}" >
						<%--  <display:table class="tb_lista" uid="elemRisultatoRicerca" name="${elemRisultatoRicerca.soggetti}" pagesize="${PAGINAZIONE}" requestURI="${azioneElimina}" > --%>
						<display:table class="tb_lista" uid="soggettoAssociato" name="${soggettiSpese}" pagesize="${PAGINAZIONE}" requestURI="${azione}" >
							<display:column property="descrizioneSoggetto" title="Soggetti"   sortable="false" />
							<display:column property="descrRuolo" title="Ruolo" sortable="false" />
							<display:column property="speseProcessuali.descrizione" title="Importo" sortable="false" />
									
						</display:table>
						<br />	
				
						<br />
						<table class="tb_bottoni" align="center">
						<tr>			
								<td class="td_tasto">
									<input type="button" value="Indietro" onClick="indietro('/procedimento/atto/copertinaAtto.htm')">
								</td>						
						</tr>
						</table>
					
					</c:if>
				</c:if>
				</form:form>	
					
			</tiles:putAttribute>
		</tiles:insertDefinition>
	
	<script type="text/javascript">	
	
	function modificaPaginazione() {
		 var noPaginazione=document.getElementsByName("paginazione")[1];
		 if (noPaginazione.checked)
		 $('#idspeseSoggettoForm').attr('action', '${pageContext.request.contextPath}/procedimento/atto/noPaginazioneSpeseBeni.htm');
	     else 
	     $('#idspeseSoggettoForm').attr('action', '${pageContext.request.contextPath}/procedimento/atto/yesPaginazioneSpeseBeni.htm');
	     $("#idspeseSoggettoForm").submit();
	}
	function disabilitaDefinitivita(statoAtto){
			if(statoAtto!=3){
				if(document.getElementsByName("Definitivita")[0] != null) {
					document.getElementsByName("Definitivita")[0].disabled=true;
				}
			}
		}	
	
		disabilitaDecisorioBeni('${disabledViewProc}', <%=atto.getStatoAtto().getId()%>);
		
		disabilitaDefinitivita('${attoForm.statoAtto.id}');
	
		function enableAll(array) {
	
			for (var i = 0; i < array.length; i++)
				array[i].disabled = false;
		}
	
		function disableAll(array) {
	
			for (var i = 0; i < array.length; i++)
				array[i].disabled = true;
		}
	
		function uncheckAll(array) {
	
			for (var i = 0; i < array.length; i++)
				array[i].checked = false;
		}
	
		function checkAll(array) {
	
			for (var i = 0; i < array.length; i++)
				array[i].checked = true;
		}
	
		function checkSoggetti(checkbox, indice) {
			
			var nome = "beniProcedimento[" + indice + "].listaIdCheckboxString";
	
			var soggettiTitolari = document.getElementsByName(nome);
	
			if (checkbox.checked) {
	
				checkAll(soggettiTitolari);
			
			} else {
	
				uncheckAll(soggettiTitolari);
			}
		}
	
		function uncheckParentIfNotAllChildren(indice) {
			
			var nomeLista = "beniProcedimento[" + indice + "].listaIdCheckboxString";
	
			var soggettiTitolari = document.getElementsByName(nomeLista);
	
			var almenoUnoDisattivato = false;
	
			for (var i = 0; i < soggettiTitolari.length; i++){
				if (!soggettiTitolari[i].checked){
					almenoUnoDisattivato = true;
					break;
					}
			}
	
			if (almenoUnoDisattivato){
	
				var nomePadre = nomeLista.replace("listaIdCheckboxString", "idBene");
				var padre = document.getElementsByName(nomePadre);
				for (var i = 0; i < padre.length; i++){
					padre[i].checked = false;
				}
			}
		}
	
		function checkParent(checkbox){
	
			var nomeFiglio = checkbox.name;
			var nomePadre = nomeFiglio.replace("listaIdCheckboxString", "idBene");
			var padre = document.getElementsByName(nomePadre);
			for (var i = 0; i < padre.length; i++){
				padre[i].checked = true;
			}
		}
	
		function checkParentsIfChildChecked() {
	
			for(var i=0; i<document.getElementsByTagName("input").length; i++){
				if(document.getElementsByTagName("input")[i].type == "checkbox"){
					if(document.getElementsByTagName("input")[i].checked == true){
						checkParent(document.getElementsByTagName("input")[i]);
					}
				}
			}
		}
	
		$(document).ready(function() {
			prendiData("#dataDeposito",true);
			prendiData("#dataEmissione",true);
	
			$("#idForm").bind('submit', function() {
				return messaggio(this);
			});
		});
	
		function inserisci(submit, documentoFirmato) {
			if($('#dataDeposito').val()==''){
					alert('La data di deposito deve essere valorizzata');
			     	return false;
			}
			 $(":disabled").removeAttr('disabled');
			inserisciAtto(submit, documentoFirmato);
		}

		
		function ConfermaOperazione() {
			var richiesta = window.confirm("Reimpostare il modulo o continuare?");
			return richiesta;
		}

		function confermaCancellazione(button) {
			 if(confirm("Si vuole procedere con l'eliminazione di tutte le condanne alle spese processuali per tutti i soggetti?")){
				handleSubmit(button);
				return true;
			}
				return false;
		}
	
	
		function pulisciFiltri() {
			if(document.getElementsByName("soggettiDiretti.id").length != 0)
				document.getElementsByName("soggettiDiretti.id")[0].selectedIndex = -1;
			if(document.getElementsByName("soggettiTitolari.id").length != 0)
				document.getElementsByName("soggettiTitolari.id")[0].selectedIndex = -1;
			if(document.getElementsByName("tipologiaBene.id").length != 0)
				document.getElementsByName("tipologiaBene.id")[0].selectedIndex = -1;
			if(document.getElementsByName("categoriaBene.id").length != 0)
				document.getElementsByName("categoriaBene.id")[0].selectedIndex = -1;
			if(document.getElementsByName("sottocategoriaBene.id").length != 0)
				document.getElementsByName("sottocategoriaBene.id")[0].selectedIndex = -1;
		}
	
		function validaCheckTutti(checkbox) {

			if(document.getElementsByName("speseProcessuali.id").length != 0){
				var e = document.getElementByName("speseProcessuali.id") ;
			} else {
				var e = "-1";
			}	
			
			var checkbox_val = checkbox.value;
		    if (checkbox.checked == true)
		    {
		    	document.getElementsByName("checkTutti").value = true;
		        if (e =='-1' ){
			       
		    		alert('Selezionare un importo e premere Salva Spese' );
				}
		    }	
		    else
		    {
		    	if (e !='-1' ){
		       	 	alert('Importo selezionato, spunta Applica a tutti i soggetti e Salva Spese' );
		        }
		    }

		}
	
		
	</script>