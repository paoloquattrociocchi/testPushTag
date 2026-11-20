<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="it.giustizia.smp.utility.Constants"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<!--  spring:url var="azione"
			value="/flussi/visualizzaFlussoDaElaborare.htm" /-->
		<p class="titolo_page">
			<spring:message code="flussi.dettaglio" />
			<c:out value="${ include}"></c:out>
		</p>
		<form:form method="post" action="${azione}" commandName="flussiForm" enctype="multipart/form-data">
			<span><form:hidden path="idFlusso"/><form:hidden path="include"/></span>
			<form:hidden path="attoDto.CODI_FK_ID_CAT_ATTO"/>
			<form:hidden path="idUffMittente"/>
			<form:hidden path="accettazioneDeposito"/>
			
			<br/>
			<c:set var="PROPOSTA_MSG" value="<%=Constants.PROPOSTA_MSG%>"/>
			<c:set var="PROPOSTA_DA_TRIBUNALE_MSG" value="<%=Constants.PROPOSTA_DA_TRIBUNALE_MSG%>"/>
			<c:set var="ISTANZA_DA_TRIBUNALE_MSG" value="<%=Constants.ISTANZA_DA_TRIBUNALE_MSG%>"/>
			<c:set var="PROPOSTA_AGGRAVAMENTO_MSG" value="<%=Constants.PROPOSTA_AGGRAVAMENTO_MSG%>"/>
			<c:set var="PROPOSTA_AGGRAVAMENTO_DA_TRIBUNALE_MSG" value="<%=Constants.PROPOSTA_AGGRAVAMENTO_DA_TRIBUNALE_MSG%>"/>
		
			<c:set var="SEQUESTRO_MSG_PU" value="<%=Constants.SEQUESTRO_MSG_PU%>"/>
			<c:set var="SEQUESTRO_MSG_PG" value="<%=Constants.SEQUESTRO_MSG_PG%>"/>
			<c:set var="SEQUESTRO_MSG_CDA" value="<%=Constants.SEQUESTRO_MSG_CDA%>"/>
			
			<c:set var="SEQUESTRO_ANT_MSG_PU" value="<%=Constants.SEQUESTRO_ANT_MSG_PU%>"/>
			<c:set var="SEQUESTRO_ANT_MSG_PG" value="<%=Constants.SEQUESTRO_ANT_MSG_PG%>"/>
				
			<c:set var="SEQUESTRO_URG_MSG_PU" value="<%=Constants.SEQUESTRO_URG_MSG_PU%>"/>
			<c:set var="SEQUESTRO_URG_MSG_PG" value="<%=Constants.SEQUESTRO_URG_MSG_PG%>"/>
			
			<c:set var="DECRETO_DECISORIO_I_GRADO_PU_MSG" value="<%=Constants.DECRETO_DECISORIO_I_GRADO_PU_MSG%>"/>
			<c:set var="DECRETO_DECISORIO_I_GRADO_PP_MSG" value="<%=Constants.DECRETO_DECISORIO_I_GRADO_PP_MSG%>"/>
			<c:set var="DECRETO_DECISORIO_I_GRADO_PG_MSG" value="<%=Constants.DECRETO_DECISORIO_I_GRADO_PG_MSG%>"/>
			
			<c:set var="DECRETO_DECISORIO_I_GRADO_PU_CAM_CON_FASCICOLO" value="<%=Constants.DECRETO_DECISORIO_I_GRADO_PU_CAM_CON_FASCICOLO%>"/>
			
			<c:set var="DECRETO_DECISORIO_I_GRADO_PG_CAM_CON_FASCICOLO" value="<%=Constants.DECRETO_DECISORIO_I_GRADO_PG_CAM_CON_FASCICOLO%>"/>
			<c:set var="DECRETO_DECISORIO_I_GRADO_AGGR_CON_FASCICOLO" value="<%=Constants.DECRETO_DECISORIO_I_GRADO_AGGR_CON_FASCICOLO%>"/>
				
			<c:set var="DEFINITIVITA_PU_MSG" value="<%=Constants.DEFINITIVITA_PU_MSG%>"/>
			<c:set var="DEFINITIVITA_PG_MSG" value="<%=Constants.DEFINITIVITA_PG_MSG%>"/>
			<c:set var="DEFINITIVITA_PP_MSG" value="<%=Constants.DEFINITIVITA_PP_MSG%>"/>
			<c:set var="DEFINITIVITA_REVOCAZIONE_CONFISCA_MSG" value="<%=Constants.DEFINITIVITA_REVOCAZIONE_CONFISCA_MSG%>"/>
			<c:set var="DEFINITIVITA_CDA_MSG" value="<%=Constants.DEFINITIVITA_CDA_MSG%>"/>
			<c:set var="DEFINITIVITA_II_GRADO_MSG" value="<%=Constants.DEFINITIVITA_II_GRADO_MSG%>"/>
			<c:set var="DECRETO_INCOMPETENZA_PROCURA_MSG" value="<%=Constants.DECRETO_INCOMPETENZA_PROCURA_MSG%>"/>
			<c:set var="DECRETO_FISSAZIONE_UDIENZA_MSG" value="<%=Constants.DECRETO_FISSAZIONE_UDIENZA_MSG%>"/>
			<c:set var="DECRETO_UDIENZA_CON_FASCICOLO" value="<%=Constants.DECRETO_UDIENZA_CON_FASCICOLO%>"/>
               <c:set var="DECRETO_ANTICIPAZIONE_UDIENZA_MSG" value="<%=Constants.DECRETO_ANTICIPAZIONE_UDIENZA_MSG%>"/>
			<c:set var="DECRETO_RINVIO_UDIENZA_MSG" value="<%=Constants.DECRETO_RINVIO_UDIENZA_MSG%>"/>
			<c:set var="DECRETO_CONVALIDA_SEQ_MSG" value="<%=Constants.DECRETO_CONVALIDA_SEQ_MSG%>"/>
			<c:set var="DECRETO_REVOCA_SEQUESTRO_MSG" value="<%=Constants.DECRETO_REVOCA_SEQUESTRO_MSG%>"/>
			<c:set var="DECRETO_RIGETTO_TOTALE_SEQUESTRO_MSG" value="<%=Constants.DECRETO_RIGETTO_TOTALE_MSG%>"/>
			<c:set var="VERBALE_RINVIO_UDIENZA_MSG" value="<%=Constants.VERBALE_RINVIO_UDIENZA_MSG%>"/>
			<c:set var="DECRETO_REMISSIONE_SU_RUOLO_MSG" value="<%=Constants.DECRETO_REMISSIONE_SU_RUOLO_MSG%>"/>
			<c:set var="DECRETO_ASSEGNAZIONE_NUOVO_RUOLO_MSG" value="<%=Constants.DECRETO_ASSEGNAZIONE_NUOVO_RUOLO_MSG%>"/>
			<c:set var="DECRETO_INCOMPETENZA_RESTITUZIONE_ATTI_MSG" value="<%=Constants.DECRETO_INCOMPETENZA_RESTITUZIONE_ATTI_MSG%>"/>
			<c:set var="DECRETO_INCOMPETENZA_TRASF_ALTRO_TRIBUNALE_MSG" value="<%=Constants.DECRETO_INCOMPETENZA_TRASF_ALTRO_TRIBUNALE_MSG%>"/>			
			<c:set var="ISTANZA_DIFFIDA_SOGGETTO_MSG" value="<%=Constants.ISTANZA_DIFFIDA_SOGGETTO_MSG%>"/>			
			<c:set var="OPPOSIZIONE_AL_DISSEQUESTRO_MSG" value="<%=Constants.OPPOSIZIONE_AL_DISSEQUESTRO_MSG%>"/>
			<c:set var="REVOCA_OPPOSIZIONE_AL_DISSEQUESTRO_MSG" value="<%=Constants.REVOCA_OPPOSIZIONE_AL_DISSEQUESTRO_MSG%>"/>
			<c:set var="REVOCAZIONE_CONFISCA_MSG" value="<%=Constants.REVOCAZIONE_CONFISCA_MSG%>"/>
			<c:set var="REVOCAZIONE_CONFISCA_ATTO_MSG" value="<%=Constants.REVOCAZIONE_CONFISCA_ATTO_MSG%>"/>
			<c:set var="OPPOSIZIONE_AL_DISSEQUESTRO_ATTO_MSG" value="<%=Constants.OPPOSIZIONE_AL_DISSEQUESTRO_ATTO_MSG%>"/>
			<c:set var="SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO_MSG" value="<%=Constants.SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO_MSG%>"/>
			<c:set var="SENTENZA_REVOCAZIONE_CONFISCA_MSG" value="<%=Constants.SENTENZA_REVOCAZIONE_CONFISCA_MSG%>"/>
			<c:set var="RICORSO_IN_APPELLO_CDA_MSG" value="<%=Constants.RICORSO_IN_APPELLO_CDA_MSG%>"/>				
			<c:set var="RICORSO_IN_APPELLO_PU_A_TRIB_MSG" value="<%=Constants.RICORSO_IN_APPELLO_PU_A_TRIB_MSG%>"/>
			<c:set var="RICORSO_IN_APPELLO_PG_A_TRIB_MSG" value="<%=Constants.RICORSO_IN_APPELLO_PG_A_TRIB_MSG%>"/>				
			<c:set var="RICORSO_IN_APPELLO_TRIB_A_PU_MSG" value="<%=Constants.RICORSO_IN_APPELLO_TRIB_A_PU_MSG%>"/>
			<c:set var="RICORSO_IN_APPELLO_TRIB_A_PG_MSG" value="<%=Constants.RICORSO_IN_APPELLO_TRIB_A_PG_MSG%>"/>								
			<c:set var="TIPO_MSG_RIC_AP_SUCCESSIVO" value="<%=Constants.TIPO_MSG_RIC_AP_SUCCESSIVO%>"/>
			<c:set var="TIPO_MSG_RIC_AP_TARDIVO" value="<%=Constants.TIPO_MSG_RIC_AP_TARDIVO%>"/>		
			<c:set var="TIPO_MSG_ATTO_RIFIUTATO" value="<%=Constants.TIPO_MSG_ATTO_RIFIUTATO%>"/>	
			<c:set var="TIPO_MSG_DECRETO_DECISORIO_II_GRADO" value="<%=Constants.TIPO_MSG_DECRETO_DECISORIO_II_GRADO%>"/>
			<c:set var="TIPO_MSG_PROVV_ISTANZA_CDA_CC" value="<%=Constants.TIPO_MSG_PROVV_ISTANZA_CDA_CC%>"/>
			<c:set var="TIPO_MSG_DECRETO_DECISORIO_II_GRADO_CAM" value="<%=Constants.TIPO_MSG_DECRETO_DECISORIO_II_GRADO_CAM%>"/>
			<c:set var="TIPO_MSG_DECRETO_DECISORIO_II_GRADO_AGGRAVAMENTO" value="<%=Constants.TIPO_MSG_DECRETO_DECISORIO_II_GRADO_AGGRAVAMENTO%>"/>
			
			<c:set var="TIPO_MSG_PROVV_ISTANZA_FASCICOLO" value="<%=Constants.TIPO_MSG_PROVV_ISTANZA_FASCICOLO%>"/>
					
			<c:set var="TIPO_MSG_RICHIESTA_PARERE" value="<%=Constants.TIPO_MSG_RICHIESTA_PARERE%>"/>
			<c:set var="TIPO_MSG_PARERE" value="<%=Constants.TIPO_MSG_PARERE%>"/>
			<c:set var="TIPO_MSG_PARERE_CON_FASCICOLO" value="<%=Constants.TIPO_MSG_PARERE_CON_FASCICOLO%>"/>
			<c:set var="TIPO_MSG_VERSIONAMENTO" value="<%=Constants.TIPO_MSG_VERSIONAMENTO%>"/>
			<c:set var="RICORSO_IN_CASSAZIONE_PG_A_CDA_MSG" value="<%=Constants.RICORSO_IN_CASSAZIONE_PG_A_CDA_MSG%>"/>
			<c:set var="RICORSO_IN_CASSAZIONE_CDA_A_PG_MSG" value="<%=Constants.RICORSO_IN_CASSAZIONE_CDA_A_PG_MSG%>"/>
			<c:set var="PROROGA_SEQUESTRO_MSG" value="<%=Constants.PROROGA_SEQUESTRO_MSG%>"/>
			<c:set var="SOSPENSIONE_TEMP_MSG" value="<%=Constants.SOSPENSIONE_TEMP_MSG%>"/>
			<c:set var="PROROGA_SOSPENSIONE_MSG" value="<%=Constants.PROROGA_SOSPENSIONE_MSG%>"/>
			<c:set var="SEQUESTRO_SOSPENSIONE_MSG" value="<%=Constants.SEQUESTRO_SOSPENSIONE_MSG%>"/>
			<c:set var="DECRETO_APPLICAZIONE_PROVV_MSG" value="<%=Constants.DECRETO_APPLICAZIONE_PROVV_MSG%>"/>
			<c:set var="REVOCA_APPLICAZIONE_PROVV_MSG" value="<%=Constants.REVOCA_APPLICAZIONE_PROVV_MSG%>"/>
			<c:set var="SENTENZA_CASSAZIONE_DA_CDA_MSG" value="<%=Constants.SENTENZA_CASSAZIONE_DA_CDA_MSG%>"/>
			<c:set var="SENTENZA_CASSAZIONE_DA_CASS_A_CDA_MSG" value="<%=Constants.SENTENZA_CASSAZIONE_DA_CASS_A_CDA_MSG%>"/>
			<c:set var="SENTENZA_CASSAZIONE_DA_CASS_A_TRIB_MSG" value="<%=Constants.SENTENZA_CASSAZIONE_DA_CASS_A_TRIB_MSG%>"/>
			<c:set var="SENTENZA_CASSAZIONE_TRIB_CC" value="<%=Constants.SENTENZA_CASSAZIONE_TRIB_CC%>"/>
			<c:set var="SENTENZA_CASSAZIONE_CDA_CC" value="<%=Constants.SENTENZA_CASSAZIONE_CDA_CC%>"/>
			<c:set var="SENTENZA_CASSAZIONE_REVOCAZIONE_CONFISCA" value="<%=Constants.SENTENZA_CASSAZIONE_REVOCAZIONE_CONFISCA%>"/>
			
			<c:set var="TIPO_MSG_RIC_TARD_CASS_DA_CDA" value="<%=Constants.TIPO_MSG_RIC_TARD_CASS_DA_CDA%>"/>
			<c:set var="TIPO_MSG_RIC_TARD_CASS_DA_PG" value="<%=Constants.TIPO_MSG_RIC_TARD_CASS_DA_PG%>"/>	
			<c:set var="TIPO_MSG_PER_EFFI_SEQ_A_PU_PG" value="<%=Constants.TIPO_MSG_PER_EFFI_SEQ_A_PU_PG%>"/>
			<c:set var="RICORSO_IN_CASSAZIONE_DA_TRIB_MSG" value="<%=Constants.RICORSO_IN_CASSAZIONE_DA_TRIB_MSG%>"/>
			<c:set var="NRG_DELLA_CASSAZIONE_MSG" value="<%=Constants.NRG_DELLA_CASSAZIONE_MSG%>"/>

			<%-- MEV_15b: aggiunta costante per gestire i verbali di udienza di riserva --%>
			<c:set var="VERBALE_UDIENZA_RISERVA_MSG" value="<%=Constants.VERBALE_UDIENZA_RISERVA_MSG%>"/>
			
			<c:if test="${flussiForm.include == PROPOSTA_MSG || 
						  flussiForm.include == PROPOSTA_DA_TRIBUNALE_MSG || 
						  flussiForm.include == PROPOSTA_AGGRAVAMENTO_MSG ||
						  flussiForm.include == PROPOSTA_AGGRAVAMENTO_DA_TRIBUNALE_MSG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaProposta.jsp" />
			</c:if>
			<c:if test="${flussiForm.include == ISTANZA_DA_TRIBUNALE_MSG }">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaIstanza.jsp" />
			</c:if>

			 <%-- MEV_15b: aggiunta or condition per gestire i verbali di udienza di riserva --%>
			<c:if test="${flussiForm.include == DECRETO_FISSAZIONE_UDIENZA_MSG ||
						  flussiForm.include == DECRETO_ANTICIPAZIONE_UDIENZA_MSG ||
						  flussiForm.include == DECRETO_RINVIO_UDIENZA_MSG ||
						  flussiForm.include == VERBALE_RINVIO_UDIENZA_MSG ||
						  flussiForm.include == DECRETO_REMISSIONE_SU_RUOLO_MSG ||
						  flussiForm.include == DECRETO_ASSEGNAZIONE_NUOVO_RUOLO_MSG ||
						  flussiForm.include == DECRETO_UDIENZA_CON_FASCICOLO ||
						  flussiForm.include == VERBALE_UDIENZA_RISERVA_MSG 
						  }">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaDecretoUdienza.jsp" />
			</c:if>
			<c:if test="${flussiForm.include == DEFINITIVITA_PU_MSG || 
						  flussiForm.include == DEFINITIVITA_PG_MSG || 
						  flussiForm.include == DEFINITIVITA_PP_MSG ||
						  flussiForm.include == DEFINITIVITA_REVOCAZIONE_CONFISCA_MSG ||
						  flussiForm.include == DEFINITIVITA_CDA_MSG ||
						  flussiForm.include == DEFINITIVITA_II_GRADO_MSG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaDefinitivitaMisure.jsp" />
			</c:if>
			<c:if test="${flussiForm.include == DECRETO_INCOMPETENZA_RESTITUZIONE_ATTI_MSG ||
						 flussiForm.include == DECRETO_INCOMPETENZA_TRASF_ALTRO_TRIBUNALE_MSG || 
					     flussiForm.include == DECRETO_INCOMPETENZA_PROCURA_MSG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaDecretoIncompRestAtti.jsp" />
			</c:if>
			<c:if test="${flussiForm.include == ISTANZA_DIFFIDA_SOGGETTO_MSG }">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaIstanzaSoggDiffidato.jsp" />
			</c:if>
			<c:if test="${flussiForm.include == DECRETO_DECISORIO_I_GRADO_PU_MSG || 
					      flussiForm.include == DECRETO_DECISORIO_I_GRADO_PP_MSG || 
					      flussiForm.include == DECRETO_DECISORIO_I_GRADO_PG_MSG ||
					      flussiForm.include == DECRETO_DECISORIO_I_GRADO_PG_CAM_CON_FASCICOLO ||
					      flussiForm.include == DECRETO_DECISORIO_I_GRADO_PU_CAM_CON_FASCICOLO ||
					      flussiForm.include == DECRETO_DECISORIO_I_GRADO_AGGR_CON_FASCICOLO}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaDecretoDecisorioIGrado.jsp" />
			</c:if>
			<!-- Segnalazione 001 -->
			<c:if test="${flussiForm.include == NRG_DELLA_CASSAZIONE_MSG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaNRGCassazione.jsp" />
			</c:if>
			<c:set var="SEQUESTRO_MSG_PU" value="<%=Constants.SEQUESTRO_MSG_PU%>"/>
			<c:set var="SEQUESTRO_MSG_PG" value="<%=Constants.SEQUESTRO_MSG_PG%>"/>
			<c:set var="SEQUESTRO_MSG_CDA" value="<%=Constants.SEQUESTRO_MSG_CDA%>"/>
			
			<c:set var="SEQUESTRO_ANT_MSG_PU" value="<%=Constants.SEQUESTRO_ANT_MSG_PU%>"/>
			<c:set var="SEQUESTRO_ANT_MSG_PG" value="<%=Constants.SEQUESTRO_ANT_MSG_PG%>"/>
				
			<c:set var="SEQUESTRO_URG_MSG_PU" value="<%=Constants.SEQUESTRO_URG_MSG_PU%>"/>
			<c:set var="SEQUESTRO_URG_MSG_PG" value="<%=Constants.SEQUESTRO_URG_MSG_PG%>"/>
			
			<c:if test="${flussiForm.include == SEQUESTRO_MSG_PU ||
			              flussiForm.include == SEQUESTRO_MSG_PG ||
			              flussiForm.include == SEQUESTRO_MSG_CDA ||
			              flussiForm.include == SEQUESTRO_ANT_MSG_PU ||
			              flussiForm.include == SEQUESTRO_ANT_MSG_PG ||
			              flussiForm.include == SEQUESTRO_URG_MSG_PU ||
			              flussiForm.include == SEQUESTRO_URG_MSG_PG ||
						  flussiForm.include == DECRETO_RIGETTO_TOTALE_SEQUESTRO_MSG ||
						  flussiForm.include == SOSPENSIONE_TEMP_MSG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaDecreto.jsp" />
			</c:if>
			<c:if test="${flussiForm.include == DECRETO_CONVALIDA_SEQ_MSG || 
					      flussiForm.include == DECRETO_REVOCA_SEQUESTRO_MSG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaDecretoConvalidaSequestro.jsp" />
			</c:if>
			
			<c:if test="${flussiForm.include == TIPO_MSG_PER_EFFI_SEQ_A_PU_PG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaPerditaEfficaciaSequestro.jsp" />
			</c:if>				
			
			<c:if test="${flussiForm.include == OPPOSIZIONE_AL_DISSEQUESTRO_MSG ||
							flussiForm.include == OPPOSIZIONE_AL_DISSEQUESTRO_ATTO_MSG ||
								flussiForm.include == SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO_MSG ||
									flussiForm.include == REVOCA_OPPOSIZIONE_AL_DISSEQUESTRO_MSG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaOpposizioneDissequestro.jsp" />
			</c:if>

			<c:if test="${flussiForm.include == REVOCAZIONE_CONFISCA_MSG ||
							flussiForm.include == REVOCAZIONE_CONFISCA_ATTO_MSG ||
								flussiForm.include == SENTENZA_REVOCAZIONE_CONFISCA_MSG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaRevocazioneConfisca.jsp" />
			</c:if>
			
			<c:if test="${flussiForm.include == RICORSO_IN_APPELLO_CDA_MSG || flussiForm.include == RICORSO_IN_APPELLO_PU_A_TRIB_MSG ||
							flussiForm.include == RICORSO_IN_APPELLO_PG_A_TRIB_MSG ||flussiForm.include == RICORSO_IN_APPELLO_TRIB_A_PU_MSG ||
							flussiForm.include == RICORSO_IN_APPELLO_TRIB_A_PG_MSG ||flussiForm.include == TIPO_MSG_RIC_AP_TARDIVO
							||flussiForm.include == TIPO_MSG_RIC_AP_SUCCESSIVO || flussiForm.include == RICORSO_IN_CASSAZIONE_DA_TRIB_MSG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaRicorso.jsp" />
			</c:if>		
			
			<c:if test="${flussiForm.include == TIPO_MSG_ATTO_RIFIUTATO }">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaAttoRifiutato.jsp" />
			</c:if>						
			
			<c:if test="${flussiForm.include == TIPO_MSG_DECRETO_DECISORIO_II_GRADO ||
							flussiForm.include == TIPO_MSG_PROVV_ISTANZA_CDA_CC||
							flussiForm.include == TIPO_MSG_DECRETO_DECISORIO_II_GRADO_CAM ||
							flussiForm.include == TIPO_MSG_DECRETO_DECISORIO_II_GRADO_AGGRAVAMENTO ||
							flussiForm.include == TIPO_MSG_PROVV_ISTANZA_FASCICOLO}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaDecretoDecisorioIIGrado.jsp" />
			</c:if>	

			<c:if test="${flussiForm.include == TIPO_MSG_RICHIESTA_PARERE ||
							flussiForm.include == TIPO_MSG_PARERE ||
							flussiForm.include == TIPO_MSG_PARERE_CON_FASCICOLO }">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaParere.jsp" />
			</c:if>		

			<c:if test="${flussiForm.include == TIPO_MSG_VERSIONAMENTO}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaVersionamento.jsp" />
			</c:if>	

			<c:if test="${flussiForm.include == RICORSO_IN_CASSAZIONE_PG_A_CDA_MSG || flussiForm.include == RICORSO_IN_CASSAZIONE_CDA_A_PG_MSG
			             || flussiForm.include == TIPO_MSG_RIC_TARD_CASS_DA_CDA || flussiForm.include == TIPO_MSG_RIC_TARD_CASS_DA_PG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaRicorsoCassazione.jsp" />
			</c:if>		
										
			<c:if test="${flussiForm.include == PROROGA_SEQUESTRO_MSG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaDecretoProrogaSequestro.jsp" />
			</c:if>		

			<c:if test="${flussiForm.include == PROROGA_SOSPENSIONE_MSG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaDecretoProrogaSospensioneTemporanea.jsp" />
			</c:if>		
			
			<c:if test="${flussiForm.include == SEQUESTRO_SOSPENSIONE_MSG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaDecretoSequestroSospensioneTemporanea.jsp" />
			</c:if>	
			
			<c:if test="${flussiForm.include == DECRETO_APPLICAZIONE_PROVV_MSG ||
			              flussiForm.include == REVOCA_APPLICAZIONE_PROVV_MSG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaApplicazioneProvv.jsp" />
			</c:if>		
			
			<c:if test="${flussiForm.include == SENTENZA_CASSAZIONE_DA_CDA_MSG ||
					      flussiForm.include == SENTENZA_CASSAZIONE_TRIB_CC ||
						  flussiForm.include == SENTENZA_CASSAZIONE_CDA_CC ||
						  flussiForm.include == SENTENZA_CASSAZIONE_REVOCAZIONE_CONFISCA}">

				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaSentenzaCassazione.jsp" />
			</c:if>	
			
			<c:if test="${flussiForm.include == SENTENZA_CASSAZIONE_DA_CASS_A_CDA_MSG ||
			              flussiForm.include == SENTENZA_CASSAZIONE_DA_CASS_A_TRIB_MSG}">
				<jsp:include page="/WEB-INF/jsp/flussi/includeVisualizzaSentenzaDaCassazioneACda.jsp" />
			</c:if>					
			
					
					
																								
			<table class="tb_bottoni" align="center">
				<tr>
					<logic:notEmpty name="listaAccertamenti">
						<td class="td_tasto"><input type="button" class="nowait" value="Riconcilia" onclick="return confermaRiconcilia()"></td>
						<input type="submit" value="Riconcilia" name="Riconcilia" id="idRiconciliaAccertamento" style="visibility: hidden" >
						<td class="td_tasto"><input type="button" class="nowait" value="Registra" onclick="return confermaRegistra()"></td>
						<input type="submit" value="Registra" name="Registra" id="idRegistraAccertamento" style="visibility: hidden" >
					</logic:notEmpty>
					
					<logic:empty name="listaAccertamenti">
					<!-- 	
							<td class="td_tasto"><input type="submit" value="Registra" name="Registra"></td>
						-->								
						<td class="td_tasto"><input type="button" value="Registra" onclick="inserisciAtto('Registra', '${attoForm.allegato.documentoFirmato}')" title="Conferma registrazione flusso" class="nowait" /></td> 
						<input	id="Registra" type="submit" name="Registra" value="" style="display: none" />													
					</logic:empty>
					<!-- da implementare dopo aver fatto lo stralcio -->
					<!-- logic:empty name="listaAccertamenti"-->	
						<!-- logic:notEmpty name="listaProcedimenti"-->
						<!--td class="td_tasto"><input type="submit" value="Registra" name="RegistraRicorsoSuccessivo"></td-->
						<!--/logic:notEmpty-->
						<!--logic:empty name="listaProcedimenti"-->
						<!--td class="td_tasto"><input type="submit" value="Registra" name="Registra"></td-->
						<!--/logic:empty-->
				    <!--/logic:empty-->
				    
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro"></td>
				</tr>
			</table>
		</form:form>
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
	<smp:back buttonId="backButton"></smp:back>
		
		<script type="text/javascript">
		$(document).ready(function() {
			if($( "#dataDepositoProposta" ).val() != undefined){
				prendiData("#dataDepositoProposta", true);
			}
		});

		function confermaRegistra() {
		 if(confirm('Stai effettuando la registrazione della proposta senza effettuare la riconciliazione con un accertamento. Sei sicuro di voler procedere?')){
			 $('#idRegistraAccertamento').click();
			return true;
		}
			return false;
		}

		function confermaRiconcilia() {
		if (confirm('Stai effettuando la riconciliazione della proposta con un accertamento. Sei sicuro di voler procedere?')){
			 $('#idRiconciliaAccertamento').click();
			return true;
		}
			return false;
		}

	</script>
	</tiles:putAttribute>	
</tiles:insertDefinition>