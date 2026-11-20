<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<%
	AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>



	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content" >
			<spring:url var="azione"
				value="/procedimento/atto/decisorioBeniSentenzaCassazione.htm" />
			<p class="titolo_page">
				<spring:message code="bene.decisione" />
			</p>
			<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
			<form:form method="post" action="${azione}" commandName="beneSoggetto">


				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />

				<br />
				<br />	

				<c:if test="${sessionScope.procedimento.tipoProcedimento.id==17}" >
	
					<table class="tb_form">
						<tr>
							<td class="td_titolo" colspan="3"><spring:message code="bene.benidelprocedimento" /></td>
						</tr>
					</table>
					
					<display:table class="tb_lista" uid="bene" name="${beniInAmministrazione}" >
						<display:column property="idBene" title="Identificativo" sortable="false"/>
						<display:column property="descrizioneBene" title="Descrizione" sortable="false"/>
					</display:table>
				
				</c:if>				
				
				<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=17}" >	

					<form:form method="post" action="${azione2}" commandName="attoForm">
						<form:hidden path="warning" />
		
						<table class="tb_form">
							<c:if test="${attoForm.statoAtto.id != 3}">
								<tr>
									<td class="td_titolo" colspan="4">
									    <form:checkbox path="visualizzaFiltro" onclick="this.form.submit()"/>
										<spring:message code="bene.filtrosuibeni" />
				                	</td>
				                </tr>
				            </c:if>
							<logic:equal name="attoForm" property="visualizzaFiltro" value="true">
								<tr><td>
								<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
									<tr>					
										<td>
											<form:label path="soggettiDiretti.id">
												<spring:message code="ricorsoAppello.soggettiDiretti" />
											</form:label>
										</td>
										<td>
											<form:label path="soggettiTitolari.id">
												<spring:message code="ricorsoAppello.soggettiTitolari" />
											</form:label>
										</td>
										<td>
											<form:label path="tipologiaBene.id"><spring:message code="ricorsoAppello.beni" /></form:label>
										</td>
									</tr>
									<tr>&nbsp;</tr>
													
									<tr>											
										<td>
											<form:select path="soggettiDiretti.id" size="1" onchange="this.form.submit()"> 
												<form:option value="-1" label="---- Seleziona Soggetto Diretto ----" />
												<form:options items="${soggettiDiretti}" itemValue="id" itemLabel="descrizione" />
											</form:select>
										</td>
										<form:hidden path="soggettiDiretti.descrizione"/>						
										<td>
											<form:select path="soggettiTitolari.id" size="1" onchange="this.form.submit()"> 
												<form:option value="-1" label="---- Seleziona Soggetto Titolare ----" />
												<form:options items="${soggettiTitolari}" itemValue="id" itemLabel="descrizione" />
											</form:select>
										</td>
										<form:hidden path="soggettiTitolari.descrizione"/>						
										<td>
											<table>
												<tr>
													<td><form:label path="tipologiaBene.id"><spring:message code="bene.tipologia" /></form:label></td>
													<td  colspan="3"><form:select id="tipologiaBene" path="tipologiaBene.id" size="1" cssStyle="width: 350px;" onchange="this.form.submit()">
															<form:option value="-1" label="------Seleziona Tipologia-------" />
															<form:options items="${tipologie}" itemValue="id" itemLabel="descrizione" />
														</form:select></td>
													<form:hidden path="tipologiaBene.descrizione"/>
												</tr>
												<tr>
													<td><form:label path="categoriaBene.id">
															<spring:message code="bene.categoria" />
														</form:label>
													</td>
													<td colspan="3"><form:select id="categoriaBene" path="categoriaBene.id" size="1" cssStyle="width: 350px;" onchange="this.form.submit()">
															<form:option value="-1" label="------Seleziona Categoria-------" />
															<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
														</form:select>
														<form:hidden path="categoriaBene.descrizione"/>
													</td>
												</tr>
												<tr>
													<td><form:label path="sottoCategoriaBene.id"><spring:message code="bene.sottocategoria" /></form:label></td>
													<td colspan="3"><form:select id="sottoCategoriaBene" path="sottoCategoriaBene.id" size="1" cssStyle="width: 350px;" onchange="this.form.submit()">
															<form:option value="-1" label="------Seleziona Sottocategoria-------" />
															<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
														</form:select>
													</td>
													<form:hidden path="sottoCategoriaBene.descrizione"/>
												</tr>
											</table>
										</td>
									</tr>
								</table>
								</td></tr>
								<tr><td>
								<table class="tb_bottoni" align="center">
									<tr>
										<td class="td_tasto"><input type="button" title="Pulisci filtri di ricerca" value="Pulisci" onclick="pulisciFiltri(); submit();" class="nowait" /></td>
									</tr>
								</table>
								</td></tr>
								<br/>
							</logic:equal>
						</table>
						
						<table class="tb_form">
							<logic:notEmpty name="attoForm" property="criteriFiltro">
								<tr>
									<td class="td_titolo" colspan="3"><spring:message code="bene.benidelprocedimentofiltrati" /></td>
								</tr>
							</logic:notEmpty>
							<logic:empty name="attoForm" property="criteriFiltro">
								<tr>
									<td class="td_titolo" colspan="3"><spring:message code="bene.benidelprocedimento" /></td>
								</tr>
							</logic:empty>
						</table>
					</form:form>
				
<!-- 				
					<table class="tb_form">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="bene.benidelprocedimento" /></td>
						</tr>
					</table>
 -->					
					<display:table class="tb_lista" uid="elemRisultatoRicerca" name="${risultatoRicerca}" pagesize="10" requestURI="${azione}" >
	
						<display:column property="bene.idBene" title="Identificativo" sortable="false" />
						<display:column property="bene.descrizioneBene" title="Descrizione" sortable="false" />
						<display:column title="Titolari di diritto">
							<display:table class="tb_sotto_lista" uid="soggettoAssociato" name="${elemRisultatoRicerca.soggetti}" requestURI="${azione}">
								<display:column property="descrizioneSoggetto" title=""  style="width:35%;"/>
								<display:column property="tipoInteresse.descrizione" title=""   style="width:15%;"/>
								<display:column property="descrizioneFrazioneOQuota" title=""  style="width:5%;"/>
								<display:column title="Decisione I Grado"   style="width:20%;">
								<c:out value="${soggettoAssociato.misura.decisioneMisuraPersonale.descrizione}"/>	
								</display:column>
								<display:column  title="Decisione II Grado"   style="width:20%;">
								<c:out value="${soggettoAssociato.misura.decisioneMisuraPersonaleIIGrado.descrizione}"/>	
								</display:column>
								<display:column  title="Sentenza Cassazione"   style="width:20%;">
								<c:out value="${soggettoAssociato.misura.decisioneMisuraPersonaleSentenzaCassazione.descrizione}"/>	
								</display:column>											
							</display:table>
						</display:column>
						
					</display:table>

				</c:if>					
				<br/>
				<br/>

				<c:if test="${sessionScope.procedimento.tipoProcedimento.id==17}" >

					<logic:notEqual value="3" name="atto" property="statoAtto.id" scope="session">
					
						<table class="tb_form">
						
							<tr>
								<td class="td_titolo" colspan="4"><spring:message code="bene.decisione" /></td>
							</tr>
							<tr>
								<td><form:label path="decisioneBeni.id">
									<spring:message code="soggetto.misurapersonale.decisioneIGrado" />
									</form:label>
								</td>
								<td colspan="3">
									<c:out value="${esitoIGrado.descrizione}" />
								</td>
							</tr>

							<c:if test="${esitoIIGrado.descrizione!=null}" > 
								<tr>
									<td><form:label path="decisioneBeni.id">
										<spring:message code="soggetto.misurapersonale.decisioneIIGrado" />
										</form:label>
									</td>
									<td colspan="3">
										<c:out value="${esitoIIGrado.descrizione}" />
									</td>
								</tr>							
							</c:if>
																	
							<tr>
								<td><form:label path="decisioneBeni.id">
									<spring:message code="soggetto.misurapersonale.sentenzaCassazione" />
									</form:label>
								</td>
								<td colspan="3">
									<form:select path="decisioneBeni.id" size="1">
										<form:option value="-1" label="------Seleziona Decisione -------" />
										<form:options items="${esitiDecisioneBeni}" itemValue="id" itemLabel="descrizione" /> 	
									</form:select>
								</td>
							</tr>
							
						</table>
	
					</logic:notEqual>
									
				</c:if>
				
			</br>
				
			<table class="tb_bottoni" align="center">
				<tr>
					<c:if test="${sessionScope.procedimento.tipoProcedimento.id==17}" >
						<td class="td_tasto">
							<input type="submit" value="Salva" name="SalvaDecisioneBeniDecisorioIIIGrado">
						</td>
					</c:if>
											
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
				</tr>
			</table>


			</form:form>	
		
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
				
		</tiles:putAttribute>
	</tiles:insertDefinition>

<script type="text/javascript">

$(document).ready(function() {

	$("#idForm").bind('submit', function() {
		return messaggio(this);
	});
});

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
</script>
