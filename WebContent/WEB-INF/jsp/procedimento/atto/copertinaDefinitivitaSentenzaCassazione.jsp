<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="it.giustizia.smp.utility.Constants"%>


<body>
	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione" value="/procedimento/atto/definitivitaSentenzaCassazione.htm" />
			<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
			
				<form:form method="post" action="${azione}" commandName="beneSoggettoForm">
				<p class="titolo_page">
					<spring:message code="atti.definitivita" />
				</p>
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
				<br />

				
					<logic:notEmpty name="risultatiRicercaSogg">
						<table class="tb_form">
							<tr>
								<td class="td_titolo" colspan="4"><spring:message code="atti.datadefinitivita.misurePersonali" /></td>
							</tr>
						</table>
						
				  <c:if test="${procedimento.tipoProcedimento.id != '16' && procedimento.tipoProcedimento.id != '15'}">			
						<display:table class="tb_lista" uid="elemRisRicerca" name="${risultatiRicercaSogg}" pagesize="10" requestURI="${azione}">
							<display:column property="soggetto.descrizioneSoggetto" title="Soggetto Interessato" sortable="false" />
							<display:column title="">
								<display:table class="tb_sotto_lista" uid="misura" name="${elemRisRicerca.misure}">
									
									<display:column property="descrizioneMisura" title="Misura" />	
									
									<c:if test="${misura.decisioneMisuraPersonale!=null && misura.decisioneMisuraPersonale.descrizione!=null}">
										<display:column property="decisioneMisuraPersonale.descrizione" title="Decisione I Grado" />
									</c:if>
									<c:if test="${misura.decisioneMisuraPersonaleIIGrado!=null && misura.decisioneMisuraPersonaleIIGrado.descrizione!=null }">
										<display:column property="decisioneMisuraPersonaleIIGrado.descrizione" title="Decisione II Grado" />
									</c:if>								
									<c:if test="${misura.decisioneMisuraPersonaleSentenzaCassazione!=null && misura.decisioneMisuraPersonaleSentenzaCassazione.descrizione!=null}">
										<display:column property="decisioneMisuraPersonaleSentenzaCassazione.descrizione" title="Sentenza Cassazione" />
									</c:if>								
									<display:column property="dataDefinitivita" title="Definitivita'" format="{0,date,dd/MM/yyyy}" />
								</display:table>
								
								
								
							</display:column>
						</display:table>
											
				 </c:if>
			
					 

				  <c:if test="${procedimento.tipoProcedimento.id  == 16 || procedimento.tipoProcedimento.id == 15 }">		
			
        			<display:table class="tb_lista" uid="elemRisRicerca" name="${risultatiRicercaSogg}" pagesize="10" requestURI="${azione}">
							<display:column property="soggetto.descrizioneSoggetto" title="Soggetto Interessato" sortable="false" />
							<display:column title="">
								<display:table class="tb_sotto_lista" uid="misura" name="${elemRisRicerca.misure}">
									
									<display:column title="Misura" >																	
											<c:choose>
												<c:when test="${(procedimento.tipoProcedimento.id == '16'  && (misura.tipoMisura.id == '1' || misura.tipoMisura.id == '2'|| misura.tipoMisura.id == '3'))
												               || (procedimento.tipoProcedimento.id == '15' && misura.tipoMisura.id == '25')}">
													<c:out value="${misura.descrizioneMisura}"></c:out>
												</c:when>
											</c:choose>
									</display:column>
									<c:if test="${misura.decisioneMisuraPersonale!=null && misura.decisioneMisuraPersonale.descrizione!=null}">
										<display:column title="Decisione I Grado" >
										 	<c:choose>
												<c:when test="${(procedimento.tipoProcedimento.id == '16'  && (misura.tipoMisura.id == '1' || misura.tipoMisura.id == '2'|| misura.tipoMisura.id == '3'))
												               || (procedimento.tipoProcedimento.id == '15' && misura.tipoMisura.id == '25')}">
												               
													<c:out value="${misura.decisioneMisuraPersonale.descrizione}"></c:out>
												</c:when>
											</c:choose>
										</display:column>	
										
									</c:if>
									<c:if test="${misura.decisioneMisuraPersonaleIIGrado!=null && misura.decisioneMisuraPersonaleIIGrado.descrizione!=null }">
										<display:column title="Decisione II Grado" >
											<c:choose>
												<c:when test="${(procedimento.tipoProcedimento.id == '16'  && (misura.tipoMisura.id == '1' || misura.tipoMisura.id == '2'|| misura.tipoMisura.id == '3'))
												               || (procedimento.tipoProcedimento.id == '15' && misura.tipoMisura.id == '25')}">
													<c:out value="${misura.decisioneMisuraPersonaleIIGrado.descrizione}"></c:out>
												</c:when>
											</c:choose>
											</display:column>
										
									</c:if>								
									<c:if test="${misura.decisioneMisuraPersonaleSentenzaCassazione!=null && misura.decisioneMisuraPersonaleSentenzaCassazione.descrizione!=null}">
										<display:column title="Sentenza Cassazione" >
										
											<c:choose>
												<c:when test="${(procedimento.tipoProcedimento.id == '16'  && (misura.tipoMisura.id == '1' || misura.tipoMisura.id == '2'|| misura.tipoMisura.id == '3'))
												               || (procedimento.tipoProcedimento.id == '15' && misura.tipoMisura.id == '25')}">
													<c:out value="${misura.decisioneMisuraPersonaleSentenzaCassazione.descrizione}"></c:out>
												</c:when>
											</c:choose>
										</display:column>	
									</c:if>								
									<display:column title="Definitivita'">
									
											<c:choose>
												<c:when test="${(procedimento.tipoProcedimento.id == '16'  && (misura.tipoMisura.id == '1' || misura.tipoMisura.id == '2'|| misura.tipoMisura.id == '3'))
												               || (procedimento.tipoProcedimento.id == '15' && misura.tipoMisura.id == '25')}">
													<fmt:formatDate value="${misura.dataDefinitivita}" type="date" pattern="dd/MM/yyyy" />
												</c:when>
											</c:choose>
									</display:column>		
								</display:table>
								
								
								
							</display:column>
						</display:table>				    	
				  </c:if>				 
				 
			
					</logic:notEmpty>

					<logic:notEmpty name="risultatiRicerca">
						<br />
						<br />
						<table class="tb_form">
							<tr>
								<td class="td_titolo" colspan="4"><spring:message code="atti.datadefinitivita.misurePatrimoniali" /></td>
							</tr>
						</table>
						<display:table class="tb_lista" uid="elemRisultatiRicerca" name="${risultatiRicerca}" pagesize="10" requestURI="${azione}">
							<display:column property="bene.idBene" title="Identificativo" sortable="false" />
							<display:column property="bene.descrizioneBene" title="Descrizione" sortable="false" />
							<display:column title="Misura - Titolari di diritto">
								<display:table class="tb_sotto_lista" uid="soggetto" name="${elemRisultatiRicerca.soggetti}">
									<display:column property="descrizioneSoggetto" title="" />
									<display:column property="tipoInteresse.descrizione" title="" />
									<display:column property="descrizioneFrazioneOQuota" title="" />
									<c:if test="${soggetto.misura.decisioneMisuraPersonale!=null && soggetto.misura.decisioneMisuraPersonale.descrizione!=null}">
										<display:column property="misura.decisioneMisuraPersonale.descrizione" title="Decisione I Grado" />
									</c:if>
									<c:if test="${soggetto.misura.decisioneMisuraPersonaleIIGrado!=null && soggetto.misura.decisioneMisuraPersonaleIIGrado.descrizione!=null  }">
										<display:column property="misura.decisioneMisuraPersonaleIIGrado.descrizione" title="Decisione II Grado" />
									</c:if>								
									<c:if test="${soggetto.misura.decisioneMisuraPersonaleSentenzaCassazione!=null && soggetto.misura.decisioneMisuraPersonaleSentenzaCassazione.descrizione!=null}">
										<display:column property="misura.decisioneMisuraPersonaleSentenzaCassazione.descrizione" title="Sentenza Cassazione" />
									</c:if>	
									<display:column property="misura.dataDefinitivita" title="Definitivita'" format="{0,date,dd/MM/yyyy}" />
								</display:table>
							</display:column>
						</display:table>
					</logic:notEmpty>
					
				 <c:if test="${procedimento.tipoProcedimento.id == 19}" >

	
					<display:table id="row" name="${soggettoDaCorregere}" class="tb_lista" uid="amm" >
				        <display:column property="descrizioneSoggetto" title="Generalità - Soggetto da correggere" sortable="false"/>
			        </display:table>
			        <br/>
					<display:table id="row" name="${listaIntestatari}" class="tb_lista" uid="amm" >
						<display:column property="descrizioneAmministratore" title="Generalità - Soggetto corretto" sortable="false"/>
					</display:table>	
					
					<br/>
						<table class="tb_form" border="0">
							<tr>
								<td class="td_titolo" colspan="4"><spring:message code="atti.definitivita" /></td>
							</tr>
							<tr>
								<td><form:label path="dataDefinitivitaAtto">
										<spring:message code="atti.datadefinitivita" />
									</form:label> &nbsp;&nbsp;&nbsp;
									<fmt:formatDate value="${attoForm.dataDefinitivita}" type="date" pattern="dd/MM/yyyy" />
								</td>
							</tr>
						</table>
					
				
				 </c:if>					

					<br />
		
					<table class="tb_bottoni" align="center">
						<tr>
							<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/atto/copertinaAtto.htm')"></td>
						</tr>
					</table>
				</form:form>
		
		</tiles:putAttribute>
	</tiles:insertDefinition>

</body>