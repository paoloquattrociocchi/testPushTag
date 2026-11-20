<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione" value="/procedimento/atto/soggettiCondannatiSentenzaCassazione.htm" />
			<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="atto" enctype="multipart/form-data">

			<p class="titolo_page">
				<spring:message code="soggetticondannati.cassazione.titolo" />
			</p>

			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />

			<br />	


			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="spese.cassazione.titolo" /></td>
				</tr>
			</table>


			<display:table class="tb_lista" uid="listaCondannatiSpese" name="${listaCondannatiSpese}"  pagesize="10" requestURI="${azione}" > 				 
					<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" style="width: 50%" />
			</display:table>			
			 <br />
			  
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="ammende.cassazione.titolo" /></td>
				</tr>
			</table>	
			
			
			<display:table class="tb_lista" uid="listaCondannatiAmmenda" name="${listaCondannatiAmmenda}" pagesize="10" requestURI="${azione}" > 				 
					<display:column property="descrizioneSoggetto" title="Generalità" sortable="true" style="width: 50%" />
					<display:column property="impoCassaAmmenda" title="Importo Ammenda" />
			</display:table>			
			 <br />					  
	
			<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto">
							 <input type="button" id="backButton" value="Indietro" title="Indietro">
						</td>
					</tr>
			</table>
				<br />
			</form:form>


			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
			<smp:back buttonId="backButton"></smp:back>

			
		</tiles:putAttribute>
	</tiles:insertDefinition>