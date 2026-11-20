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

<style type="text/css">
			.spanPaginazione label {
				
				color: color: #1c5791;
				font-weight: bold;
				font-family: Calibri, tahoma;
			}
		</style>
	<c:set var="SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO" value="<%=Constants.SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO%>"/>
	<c:set var="SENTENZA_REVOCAZIONE_DELLA_CONFISCA" value="<%=Constants.SENTENZA_REVOCAZIONE_DELLA_CONFISCA%>"/>
	<c:set var="DECRETO_DECISORIO_PROVV_ISTANZA" value="<%=Constants.DECRETO_DECISORIO_PROVV_ISTANZA%>"/>

     <logic:equal value="Y" name="beneSoggetto" property="paginazione" scope="request">
    <c:set var="PAGINAZIONE" value="10" />
	</logic:equal>
	 <logic:equal value="N" name="beneSoggetto" property="paginazione" scope="request">
    <c:set var="PAGINAZIONE" value="" />
	</logic:equal>


	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content" >
			<spring:url var="azioneElimina"
				value="/procedimento/atto/decisorioBeniIIGrado.htm" />
			<p class="titolo_page">
				<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=17}" >
					<spring:message code="bene.decisione" />
				</c:if>
				<c:if test="${sessionScope.procedimento.tipoProcedimento.id==17}" >
					<spring:message code="bene.impugnazione.decisione" />
				</c:if>			
			</p>
			<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>

				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

				<br />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />

				<br />	
				<br />
				
			<spring:url var="filtro" value="/procedimento/atto/decisorioBeniIIGrado.htm" />


			<form:form method="post" action="${filtro}" commandName="attoForm">
				<form:hidden path="warning" />

				<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=17}" >

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
				</c:if>				
			</form:form>
				
			<form:form method="post" action="${azioneElimina}" commandName="beneSoggetto" id="idbeneSoggettoForm" onsubmit="checkParentsIfChildChecked();">
			<form:hidden path="paginazione" />
			<input type="hidden" id="id_selectAll_beniProcedimento.idBene" value="true"/>
			<input type="hidden" id="id_selectAll_listaIdCheckati" value="true"/>
               
               <br/>
             <c:if test="${sessionScope.procedimento.tipoFascicolo.id==6}" >  
             
			 <table class="tb_form" cellspacing="0" cellpadding="0" border="0">	
				<tr>
					<td width="10%;"><form:label path="descRichiesta">
							<spring:message code="richiesta" />
						</form:label></td>
					<td width="80%;" align="left"><strong><c:out value="${beneSoggetto.descRichiesta}" /></strong></td>
				    <td  width="10%;" >&nbsp;</td>
				</tr>						 	
			 </table> 
			</c:if>
               
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

<!-- 					
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
 -->
					<table class="tb_form">
						<tr>
<!--  						
							<td class="td_titolo" colspan="3"><spring:message code="bene.benidelprocedimento" /></td>
-->							

 				  	        <c:if test="${risultatoRicerca!=null && fn:length(risultatoRicerca)>10}">
			                <td class="td_titolo" align="right">
							<span class="spanPaginazione"><form:checkbox id="paginazione" onclick="modificaPaginazione()"  path="paginazione" value="N" label="Lista completa beni" /></span> 
						    </td>
						    </c:if>
<!-- 						    
						     <c:if test="${risultatoRicerca==null || fn:length(risultatoRicerca)<=10}">
						      <td class="td_titolo" align="right">
							<span class="spanPaginazione">&nbsp;</span> 
						    </td>
						    </c:if>
 -->						    
						</tr>
					</table>
					<display:table class="tb_lista" uid="elemRisultatoRicerca" name="${risultatoRicerca}" pagesize="${PAGINAZIONE}" requestURI="${azioneElimina}" >
					<logic:notEqual value="3" name="atto" property="statoAtto.id" scope="session">
						<display:column paramId="bene.idBene" paramProperty="beniProcedimento[${elemRisultatoRicerca_rowNum-1}].idBene" style="width:7%" title="<img src=\"${pageContext.request.contextPath}/images/check-all.gif\" height=\"12\" onmouseover=\"this.style.cursor='pointer'\" onclick=\"selezionaTutto('elemRisultatoRicerca','beniProcedimento.idBene');\" title=\"Seleziona/Deseleziona tutti i beni\"></img>&nbsp;Sel." >	
							<form:checkbox
								path="beniProcedimento[${elemRisultatoRicerca_rowNum-1}].idBene"
								value="${elemRisultatoRicerca.bene.idBene}"
								onclick="checkSoggetti(this, ${elemRisultatoRicerca_rowNum-1})" disabled="${esitoReadOnly }" />
						</display:column>
					</logic:notEqual>
						<display:column property="bene.idBene" title="Identificativo" sortable="false" />
						<display:column property="bene.descrizioneBene" title="Descrizione" sortable="false" />
						<display:column title="Titolari di diritto">
							<display:table class="tb_sotto_lista" uid="soggettoAssociato" name="${elemRisultatoRicerca.soggetti}" requestURI="${azione}">
								<display:column property="descrizioneSoggetto" title=""  style="width:35%;"/>
								<display:column property="tipoInteresse.descrizione" title=""   style="width:15%;"/>
								<display:column property="descrizioneFrazioneOQuota" title=""  style="width:5%;"/>
								 <c:if test="${soggettoAssociato.misura.decisioneMisuraPersonale.descrizione !=null && soggettoAssociato.misura.decisioneMisuraPersonale.descrizione!=''}">
								<display:column title="Decisione I Grado"   style="width:20%;">
								<c:out value="${soggettoAssociato.misura.decisioneMisuraPersonale.descrizione}"/>	
								</display:column>
								</c:if>
								<display:column  title="Decisione II Grado"   style="width:20%;">
								<c:out value="${soggettoAssociato.misura.decisioneMisuraPersonaleIIGrado.descrizione}"/>	
								</display:column>
								<logic:notEqual value="3" name="atto" property="statoAtto.id" scope="session">
								<display:column sortable="false" style="text-align: center; width: 5%">
									<form:checkbox
										path="beniProcedimento[${elemRisultatoRicerca_rowNum-1}].listaIdCheckboxString"
										value="${soggettoAssociato.idSoggetto};${soggettoAssociato.tipoInteresse.id}"
										name="soggettoTitolareDi${elemRisultatoRicerca.bene.idBene}"
										onclick="uncheckParentIfNotAllChildren(${elemRisultatoRicerca_rowNum-1})" disabled="${esitoReadOnly }"/>
								</display:column>	
								</logic:notEqual>											
							</display:table>
						</display:column>
						
					</display:table>
					
				</c:if>	
				<logic:equal value="N" name="beneSoggetto" property="paginazione" scope="request">
             	   <c:if test="${(elemRisultatoRicerca_rowNum >10 ) }"> 				
						<span class="pagebanner">${elemRisultatoRicerca_rowNum} risultati trovati, visualizzati tutti i risultati.</span>
				   </c:if>	
	      		</logic:equal>			
				<br/>
				<br/>
				
				<logic:notEqual value="3" name="atto" property="statoAtto.id" scope="session">
				
				<table class="tb_form">
				
					<c:if test="${sessionScope.procedimento.tipoProcedimento.id==17 && esitoIGrado.descrizione !='' && !sessionScope.atto.categoriaAtto.id==DECRETO_DECISORIO_PROVV_ISTANZA }" >
					
						<tr>
							<td class="td_titolo" colspan="4">
								<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=17}" >
									<spring:message code="bene.decisione" />
								</c:if>
								<c:if test="${sessionScope.procedimento.tipoProcedimento.id==17}" >
									<spring:message code="bene.impugnazione.decisione" />
								</c:if>
							</td>
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
								
					</c:if>
									
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="bene.decisione" /></td>
					</tr>
					<c:if test="${esitoReadOnly != 'true'}">
					<tr>
						<td><form:label path="decisioneBeni.id">
							<spring:message code="bene.decisione" />
							</form:label>
						</td>
						<td colspan="3">
							<form:select path="decisioneBeni.id" size="1">
								<form:option value="-1" label="------Seleziona Decisione -------" />
								<form:options items="${esitiDecisioneBeni}" itemValue="id" itemLabel="descrizione" /> 	
							</form:select>
						</td>
					</tr>
					</c:if>
					<c:if test="${sessionScope.procedimento.tipoProcedimento.id==17}" >
					<tr>
						<td><form:label path="motivazione1">
							<spring:message code="errore.materiale.motivazione" />
							</form:label>
						</td>
						<td colspan="3">
							<form:textarea path="motivazione1" rows="2" cssStyle="width:98%" /> <form:errors path="motivazione1" class="testoRosso" />
						</td>
					</tr>		
					
					<tr>
						<td><form:label path="importo1">
							<spring:message code="liquidazioneamm.importo" />
							</form:label>
						</td>
						<td colspan="3">
							<form:input path="importo1" maxlength="11" size="20" /> <form:errors path="importo1" class="testoRosso" />
						</td>
					</tr>							
					</c:if>					
					
				</table>
				
				<br/>
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto">
						<c:if test="${profilo.ruolo!='MAGISTRATO'}">
							<c:choose>
							<c:when test="${esitoReadOnly!=null && esitoReadOnly=='true'}">
								<input type="submit" value="Salva" name="SalvaDecisioneBeniDecisorioIGrado" disabled="disabled">
							</c:when>
							<c:otherwise>
								<input type="submit" value="Salva" name="SalvaDecisioneBeniDecisorioIGrado">
							</c:otherwise>
							</c:choose>
						</c:if>
						</td>
						<c:if test="${!(sessionScope.atto!=null && sessionScope.atto.tipoAtto!=null && (
								(sessionScope.atto.categoriaAtto.id==SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO) || 
								(sessionScope.atto.categoriaAtto.id==SENTENZA_REVOCAZIONE_DELLA_CONFISCA)
							)
						) }">
							<td class="td_tasto">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atto/copertinaAtto.htm')">
							</td>
						</c:if>
						<%-- <c:if test="${risultatoRicerca!=null && fn:length(risultatoRicerca)>10}">
						<td class="td_tasto">
						<logic:equal value="Y" name="beneSoggetto" property="paginazione" scope="request">
						<input type="submit" value="Lista completa beni" name="ListaCompleta">
						
						</logic:equal>
						<logic:equal value="N" name="beneSoggetto" property="paginazione" scope="request">					
						<input type="submit" value="Paginazione beni" name="Paginazione">		
						</logic:equal>		
							</td>
						</c:if>	 --%>
					</tr>
				</table>
				</logic:notEqual>
				<logic:equal value="3" name="atto" property="statoAtto.id" scope="session">
				
						<table class="tb_form">
						
							<c:if test="${sessionScope.procedimento.tipoProcedimento.id==17}" >
							
								<tr>
									<td class="td_titolo" colspan="4">
										<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=17}" >
											<spring:message code="bene.decisione" />
										</c:if>
										<c:if test="${sessionScope.procedimento.tipoProcedimento.id==17}" >
											<spring:message code="bene.impugnazione.decisione" />
										</c:if>
									</td>
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
								
								<tr>
									<td class="td_titolo" colspan="4">
										<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=17}" >
											<spring:message code="bene.decisione" />
										</c:if>
										<c:if test="${sessionScope.procedimento.tipoProcedimento.id==17}" >
											<spring:message code="bene.impugnazione.decisione" />
										</c:if>
									</td>
								</tr>
								<tr>
									<td><form:label path="decisioneBeni.id">
											<c:if test="${sessionScope.procedimento.tipoProcedimento.id!=17}" >
												<spring:message code="bene.decisione" />
											</c:if>
											<c:if test="${sessionScope.procedimento.tipoProcedimento.id==17}" >
												<spring:message code="bene.impugnazione.decisione" />
											</c:if>
										</form:label>
									</td>
									<td colspan="3">
										<form:select path="decisioneBeni.id" size="1" disabled="${esitoReadOnly }">
											<form:option value="-1" label="------Seleziona Decisione -------" />
											<form:options items="${esitiDecisioneBeni}" itemValue="id" itemLabel="descrizione" /> 	
										</form:select>
									</td>
								</tr>		
								
																					<tr>
								<td><form:label path="motivazione1">
									<spring:message code="errore.materiale.motivazione" />
									</form:label>
								</td>
								<td colspan="3">
									<form:textarea path="motivazione1" rows="2" disabled="true"/> <form:errors path="motivazione1" class="testoRosso" />
								</td>
							</tr>		
							
							<tr>
								<td><form:label path="importo1">
									<spring:message code="liquidazioneamm.importo" />
									</form:label>
								</td>
								<td colspan="3">
									<form:input path="importo1" maxlength="15" size="20" disabled="true"/> <form:errors path="importo1" class="testoRosso" />
								</td>
							</tr>		
										
							</c:if>		
													
						</table>

					<br/>
					<table class="tb_bottoni" align="center">
						<tr>
							
							<c:if test="${!(sessionScope.atto!=null && sessionScope.atto.tipoAtto!=null && (
									(sessionScope.atto.categoriaAtto.id==SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO) || 
									(sessionScope.atto.categoriaAtto.id==SENTENZA_REVOCAZIONE_DELLA_CONFISCA)
								)
							) }">
	
								<td class="td_tasto">
									<input type="button" value="Indietro" onClick="indietro('/procedimento/atto/copertinaAtto.htm')">
								</td>
							</c:if>
							<%-- <c:if test="${risultatoRicerca!=null && fn:length(risultatoRicerca)>10}">
							<td class="td_tasto">
							<logic:equal value="Y" name="beneSoggetto" property="paginazione" scope="request">
							<input type="submit" value="Lista completa beni" name="ListaCompleta">
							
							</logic:equal>
							<logic:equal value="N" name="beneSoggetto" property="paginazione" scope="request">					
							<input type="submit" value="Paginazione beni" name="Paginazione">		
							</logic:equal>		
								</td>
							</c:if>	 --%>
						</tr>
					</table>
				</logic:equal>
			</form:form>	
		
			<c:if test="${(sessionScope.atto!=null && sessionScope.atto.tipoAtto!=null && (
					(sessionScope.atto.categoriaAtto.id==SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO) || 
					(sessionScope.atto.categoriaAtto.id==SENTENZA_REVOCAZIONE_DELLA_CONFISCA)
				)
			) }">

			<c:if test="${(sessionScope.atto!=null && sessionScope.atto.tipoAtto!=null && (
					(sessionScope.atto.categoriaAtto.id==SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO))
			) }">
				<spring:url var="azioneDecisorio" value="/procedimento/atto/sentenzaOpposizioneDissequestro.htm" />
			</c:if>

			<c:if test="${(sessionScope.atto!=null && sessionScope.atto.tipoAtto!=null && (
					(sessionScope.atto.categoriaAtto.id==SENTENZA_REVOCAZIONE_DELLA_CONFISCA))
			) }">
				<spring:url var="azioneDecisorio" value="/procedimento/atto/sentenzaRevocazioneConfisca.htm" />
			</c:if>
						
			<form:form method="post" id="id_formConfermaInvia" action="${azioneDecisorio}" commandName="attoForm" enctype="multipart/form-data">
				<form:hidden path="codAtto" />
				<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"></td>
					</tr>
	
					<c:if test="${attoForm.statoAtto.id != 3}">
						<tr>
						
		       	       <c:if test="${(sessionScope.atto!=null && sessionScope.atto.tipoAtto!=null && (
					                 (sessionScope.atto.categoriaAtto.id==SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO) || 
					                 (sessionScope.atto.categoriaAtto.id==SENTENZA_REVOCAZIONE_DELLA_CONFISCA) ) ) }">

							<td><form:label path="dataEmissione" cssClass="required">
									<spring:message code="atto.dataemissione" />
								</form:label></td>
							<td><form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" /> <form:errors path="dataEmissione" class="testoRosso" /></td>
							<td><form:label path="dataDeposito" cssClass="required">
									<spring:message code="atto.datadeposito" />
								</form:label></td>
							<td><form:input path="dataDeposito" maxlength="10" id="id_dataDeposito" /> <form:errors path="dataDeposito" class="testoRosso" /></td>
			           </c:if>	
		       	       <c:if test="${!(sessionScope.atto!=null && sessionScope.atto.tipoAtto!=null && (
					                 (sessionScope.atto.categoriaAtto.id==SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO) || 
					                 (sessionScope.atto.categoriaAtto.id==SENTENZA_REVOCAZIONE_DELLA_CONFISCA) ) ) }">
							<td><form:label path="dataEmissione">
									<spring:message code="atto.dataemissione" />
								</form:label></td>
							<td><form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" /> <form:errors path="dataEmissione" class="testoRosso" /></td>
							<td><form:label path="dataDeposito">
									<spring:message code="atto.datadeposito" />
								</form:label></td>
							<td><form:input path="dataDeposito" maxlength="10" id="id_dataDeposito" /> <form:errors path="dataDeposito" class="testoRosso" /></td>
                        </c:if>
                        
						</tr>
						<tr style="display:none">
							<td><form:label path="destinatario.id">
									<spring:message code="atto.destinazione" />
								</form:label></td>
							<td><c:if test="${attoForm.statoAtto.id != 3}">
									<table>
										<c:forEach var="destinat" items="${ufficiList}">
											<tr>
												<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
											</tr>
										</c:forEach>
									</table>
								</c:if></td>
							<td><form:label path="dataRegistrazione">
									<spring:message code="atto.dataregistrazione" />
								</form:label></td>
							<td><fmt:formatDate value="<%=new java.util.Date() %>" type="date" pattern="dd/MM/yyyy" /></td>
						</tr>
					</c:if>
					<c:if test="${attoForm.statoAtto.id == 3}">
						<tr>
							<td><form:label path="dataEmissione">
									<spring:message code="atto.dataemissione" />
								</form:label></td>
							<td><fmt:formatDate value="${attoForm.dataEmissione}" type="date" pattern="dd/MM/yyyy" /> <form:hidden path="dataEmissione" /></td>
							<td><form:label path="dataDeposito">
									<spring:message code="atto.datadeposito" />
								</form:label></td>
							<td><fmt:formatDate value="${attoForm.dataDeposito}" type="date" pattern="dd/MM/yyyy" /></td>
						</tr>
						<tr>
							<td><form:label path="destinatario.id">
									<spring:message code="atto.destinazione" />
								</form:label></td>
							<td>
								<table>
									<c:forEach var="destinat" items="${ufficiListFromFlussiOut}">
										<tr>
											<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
										</tr>
									</c:forEach>
								</table>
							</td>
							<td><form:label path="dataRegistrazione">
									<spring:message code="atto.dataregistrazione" />
								</form:label></td>
							<td><fmt:formatDate value="${attoForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /></td>
						</tr>
					</c:if>
				</table>
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
	
						<c:if test="${attoForm.statoAtto.id != 3 }">
	 					    <c:if test="${profilo.ruolo!='MAGISTRATO'}">
	 					    
		       	                <c:if test="${(sessionScope.atto!=null && sessionScope.atto.tipoAtto!=null && (
					                           (sessionScope.atto.categoriaAtto.id==SENTENZA_OPPOSIZIONE_AL_DISSEQUESTRO) || 
					                           (sessionScope.atto.categoriaAtto.id==SENTENZA_REVOCAZIONE_DELLA_CONFISCA) ) ) }">
							          
						            <td class="td_tasto"><input type="button" value="Salva" onclick="inserisciAtto('Conferma', '${attoForm.allegato.documentoFirmato}')" title="Conferma inserimento atto" class="nowait" /></td> 
						            <input	id="Conferma" type="submit" name="Conferma" value="" style="display: none" />

			                    </c:if>

	 					    
							<td class="td_tasto">
								<input type="button" value="Conferma e Invia" onclick="inserisciAtto('Invia', '${attoForm.allegato.documentoFirmato}')" title="Invia" class="nowait" />
							</td> 
		
							<input	id="Invia" type="submit" name="Invia" value="" style="display: none" />
                             </c:if>   
							<!-- 
							 <input	id="Invia" type="submit" name="Invia" value="Prova" />
							-->
						</c:if>
							
						<c:if test="${attoForm.statoAtto.id == 3 && (sessionScope.atto!=null && sessionScope.atto.tipoAtto!=null && (
								(sessionScope.atto.categoriaAtto.id==SENTENZA_REVOCAZIONE_DELLA_CONFISCA)
							)
						) }">
							<td class="td_tasto">					
								<input type="submit" value="Definitività" name="Definitivita">
							</td>
						</c:if>		
							
						<td class="td_tasto">
						
							<c:if test="${fromMenuAtti=='elenco'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
							</c:if> <c:if test="${fromMenuAtti=='ordine'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
							</c:if> <c:if test="${fromMenuAtti=='nuovo'}">
								<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
							</c:if>
						</td>						
					</tr>
				</table>
			</form:form>	
		</c:if>
				
		</tiles:putAttribute>
	</tiles:insertDefinition>

<script type="text/javascript">	
function modificaPaginazione() {
	 var noPaginazione=document.getElementsByName("paginazione")[1];
	 if (noPaginazione.checked)
	 $('#idbeneSoggettoForm').attr('action', '${pageContext.request.contextPath}/procedimento/atto/noPaginazioneDecisorioBeniIIGrado.htm');
    else 
    $('#idbeneSoggettoForm').attr('action', '${pageContext.request.contextPath}/procedimento/atto/yesPaginazioneDecisorioBeniIIGrado.htm');
    $("#idbeneSoggettoForm").submit();
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
		prendiData("#id_dataDeposito",true);
		prendiData("#id_dataEmissione",true);

		$("#idForm").bind('submit', function() {
			return messaggio(this);
		});
	});

	function inserisci(submit, documentoFirmato) {
		var check = checkDate();
		if($('#id_dataDeposito').val()==''){
				alert('La data di deposito deve essere valorizzata');
		     	return false;
		}
		if(check) {
			return false;
		}
		 $(":disabled").removeAttr('disabled');
		inserisciAtto(submit, documentoFirmato);
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
	
</script>