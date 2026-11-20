<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>



<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/soggetto/visualizzaDirettiInteressatiAtto.htm" />
		<p class="titolo_page"><spring:message code="soggetto.soggettidelprocedimento" /></p>
		<form:form method="post" action="${azione}" commandName="soggettoForm">

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br/>
				 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br/>
			
			<c:if test="${(procedimento.tipoProcedimento.id == 17 || procedimento.tipoProcedimento.id == 19) && listaIntestatari!=null}"> 
				<display:table id="row" name="${listaIntestatari}" class="tb_lista" uid="amm" >				
					<display:column property="descrizioneAmministratore" title="Generalità" sortable="false"/>
				</display:table>	
			</c:if>
	
			<c:if test="${!((procedimento.tipoProcedimento.id == 17 || procedimento.tipoProcedimento.id == 19) && listaIntestatari!=null)}">
						
				<display:table class="tb_lista" name="${risultatiRicerca}" uid="sogg" pagesize="10" requestURI="${azione}" >
					<display:column style="text-align: center; width: 5%">
						<input type="radio" name="radioIDS" id="radioID_${sogg_rowNum}"
							onclick="selectID('${sogg.idSoggetto}', '${sogg.idVersione}', '${sogg.tipoSoggetto}', '${sogg.misura.descrizioneMisura}');" />
					</display:column>
					<display:column property="descrizioneSoggetto" title="Generalità" sortable="false" />
					<c:if test="${procedimento.tipoProcedimento == null || procedimento.tipoProcedimento.id != 9 }">				
						<display:column property="dataIscrizione" title="Data Iscrizione" format="{0,date,dd/MM/yyyy}" sortable="false" />
						<display:column property="misura.descrizioneMisura" title="Misure" sortable="false" />
						<display:column property="misura.cauzione" title="Cauzione" sortable="false" />
						<display:column property="misura.art67" title="Art.67" sortable="false" />
					</c:if>
				</display:table>
			</c:if>

			<form:hidden path="idSoggetto" id="idSoggetto" />
			<form:hidden path="idVersione" id="idVersione" />
			<form:hidden path="tipoSoggetto" id="tipoSoggetto" />
			<form:hidden path="tipoInteresse.id" />
			<form:hidden path="misura.descrizioneMisura" id="tipoMisura" />
			<br />
			<br />
			
			
		   <c:if test="${(procedimento.tipoProcedimento.id == 16 || procedimento.tipoProcedimento.id == 14)}">

			      <table class="tb_bottoni" align="center">
					<tr>
	
					  <td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio"></td>
						<td class="td_tasto">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atto/copertinaAtto.htm')">
						</td>
					</tr>
				</table>		   
		   </c:if>
		     		   
		   <c:if test="${(procedimento.tipoProcedimento.id != 16 && procedimento.tipoProcedimento.id != 14)}">
			

				<c:if test="${!((procedimento.tipoProcedimento.id == 17 || procedimento.tipoProcedimento.id == 19) && listaIntestatari!=null)}">
	
				<table class="tb_bottoni" align="center">			
					<tr>
						<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio"></td>
						<c:if test="${procedimento.tipoProcedimento.id != '8' && procedimento.tipoFascicolo.id != '2' && procedimento.tipoFascicolo.id != '14' }">
							<td class="td_tasto"><input type="submit" value=" Misura Patrimoniale " name="MisuraPatrimonialeAtto"></td>
						</c:if>
				</table>
				</c:if>
				<c:if test="${( procedimento.tipoProcedimento.id == 17 || procedimento.tipoProcedimento.id == 19) && listaIntestatari!=null}"> 
				
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
		<script>
			function selectID(idSoggetto, idVersione, tipoSoggetto, tipoMisura) {
				document.getElementById("idSoggetto").value = idSoggetto;
				document.getElementById("idVersione").value = idVersione;
				document.getElementById("tipoSoggetto").value = tipoSoggetto;
				document.getElementById("tipoMisura").value = tipoMisura;
			}
		</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
