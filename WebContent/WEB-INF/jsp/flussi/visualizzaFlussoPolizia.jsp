<%@page import="it.giustizia.smp.form.AttoForm"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld"%>

<%@page import="it.giustizia.smp.utility.Constants"%>
  
<tiles:insertDefinition name="base.definition"> 
	<tiles:putAttribute name="body-content">
		
		<p class="titolo_page">
			<spring:message code="flussi.dettaglio" />
			<c:if test="${flussiForm.descCatAtto==null }">
				<c:out value="${flussiForm.descTipoFlusso}"></c:out>
			</c:if>
			<c:if test="${flussiForm.descCatAtto!=null }">
				<c:out value="${flussiForm.descCatAtto}"></c:out>
			</c:if>
		</p>
		
		<form:form method="post" action="${azione}" commandName="flussiForm">
			<span><form:hidden path="idFlusso"/><form:hidden path="idFlusso"/></span>
			<form:hidden path="atto.codAtto"/>
			<form:hidden path="idUffMittente"/>
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="15%">
						<form:label path="numProc">
							<spring:message code="procedimento" />
						</form:label>
					</td>
					<td width="35%">
						<font size="4"><b><c:out value="${procedimento.annoNumeroProcedimento}" /></b></font>
					</td>
					<td width="15%">
						<form:label path="procedimento.dataIscrizione">
							<spring:message code="procedimento.dataIscrizione" />
						</form:label>
					</td>
					<td width="35%">
						<fmt:formatDate value="${procedimento.dataApertura}" type="date" pattern="dd/MM/yyyy" />
					</td>
				</tr>
				<c:if test="${procedimento.numeroPadre!=null && procedimento.annoPadre!=null}">
					<tr>
						<td><form:label path="procedimento.annoNumeroPadre">
								<spring:message code="procedimento.appellativoProcedimentoPadre" />
							</form:label></td>
						<td colspan="3">
							<c:out value="${procedimento.annoNumeroPadre}" />
							<c:if test="${procedimento.ufficioPadre!=null}">
								&nbsp;<c:out value="${procedimento.ufficioPadre}" />
							</c:if>
						</td>
					</tr>
				</c:if>
				<c:if test="${procedimento.tipoProcedimento != null && (procedimento.tipoProcedimento.id != 13 && procedimento.tipoProcedimento.id != 14 && procedimento.tipoProcedimento.id != 15 && procedimento.tipoProcedimento.id != 16 && procedimento.tipoProcedimento.id != 17) }">
				<tr>
					<td><form:label path="procedimento.procedente">
							<spring:message code="procedimento.procedente" />
						</form:label></td>
					<td colspan="3"><c:out value="${procedimento.procedente.descrizione}" /></td>
				</tr>
				</c:if>
				<tr>
					<td>
						<form:label path="procedimento.tipoProcedimento">
							<spring:message code="procedimento.tipo" />
						</form:label>
					</td>
					<td>
						<c:out value="${procedimento.tipoProcedimento.descrizione}" />
					</td>
					<td>
						<form:label path="procedimento.statoProcedimento">
							<spring:message code="procedimento.stato" />
						</form:label>
					</td>
					<td>
						<c:out value="${procedimento.statoProcedimento.descrizione}" />
					</td>
				</tr>
			</table>
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<c:if test="${procedimento.tipoProcedimento == null || (procedimento.tipoProcedimento.id != 9 && procedimento.tipoProcedimento.id != 13 && procedimento.tipoProcedimento.id != 14 && procedimento.tipoProcedimento.id != 15 && procedimento.tipoProcedimento.id != 16 && procedimento.tipoProcedimento.id != 17) }">				
						<td width="15%"><form:label path="procedimento.tipologiaPrevenzione">
								<spring:message code="procedimento.tipologiaPrevenzione" />
							</form:label></td>
						<td width="85%"><c:if test="${procedimento.antimafia==true}">
								<form:hidden path="procedimento.antimafia" />
								<c:out value="ANTIMAFIA" />
							</c:if> <c:if test="${procedimento.antimafia==false}">
								<c:if test="${procedimento.antiterrorismo==true}">
									<form:hidden path="procedimento.antiterrorismo" />
									<c:out value="ANTITERRORISMO" />
								</c:if>
								<c:if test="${procedimento.antiterrorismo==false}">
									<c:if test="${procedimento.ordinaria==true}">
										<form:hidden path="procedimento.ordinaria" />
										<c:out value="ORDINARIA" />
									</c:if>
								</c:if>
							</c:if></td>
					</c:if>						
				</tr>						
			</table>
			
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="15%">
						<form:label path="atto.tipoAtto">
							<spring:message code="atto.tipo.atto" />
						</form:label>
					</td>
					<td width="35%">
						<c:out value="${atto.tipoAtto.descrizione}" ></c:out>
					</td>
					<td width="15%">
						<form:label path="atto.categoriaAtto">
							<spring:message code="atto.categoria.atto" />
						</form:label>
					</td>
					<td width="35%">
						<c:out value="${atto.categoriaAtto.descrizione}" ></c:out>
					</td>					
				</tr>		
				<tr>
					<td width="15%">
						<form:label path="atto.numeroAtto">
							<spring:message code="atto.numero" />
						</form:label>
					</td>
					<td width="35%">
						<c:out value="${atto.numeroAtto}" ></c:out>
					</td>
					<td width="15%">
						<form:label path="atto.statoAtto">
							<spring:message code="atto.stato.atto" />
						</form:label>
					</td>
					<td width="35%">
						<c:out value="${atto.statoAtto.descrizione}" ></c:out>
					</td>
				</tr>
				<c:if test="${atto.numeroAttoOrigine!=null}">
					<tr>
						<td width="15%">
							<c:if test="${atto.tipoAtto!=null && atto.tipoAtto.id == 21}">
								<form:label path="atto.numeroAttoOrigine"><spring:message code="atto.decreto.cda" /></form:label>
							</c:if>
							<c:if test="${atto.tipoAtto!=null && atto.tipoAtto.id != 21}">
								<form:label path="atto.numeroAttoOrigine"><spring:message code="atto.numero.origine" /></form:label>								
							</c:if>
						</td>
						<td width="35%">
							<c:out value="${atto.numeroAttoOrigine}" ></c:out>
						</td>
						<td width="15%"></td>
						<td width="35%"></td>
					</tr>
				</c:if>
				
				<c:if test="${atto.tipoAtto!=null && atto.tipoAtto.id == 28 }">
					<c:if test="${atto.dataRicezioneAttoOrigine!=null }">
						<tr>
							<td width="15%">
								<form:label path="atto.descrizioneCategoriaAttoOrigine"><spring:message code="atto.istanza.origine" /></form:label>								
							</td>
							<td width="35%">
								<c:out value="${atto.descrizioneCategoriaAttoOrigine}" ></c:out>
							</td>
							<td width="15%">
								<form:label path="atto.dataRicezioneAttoOrigine">ricevuto il&nbsp;</form:label>								
							</td>
							<td width="35%">
								<fmt:formatDate value="${atto.dataRicezioneAttoOrigine}" type="date" pattern="dd/MM/yyyy" />
							</td>
						</tr>
					</c:if>					
				</c:if>
				
				<c:if test="${atto.tipoAtto!=null && atto.tipoAtto.id == 21 }">
					<c:if test="${atto.note!=null}">
						<tr>
							<td width="15%">
								<form:label path="atto.note">
									<spring:message code="atto.numero.sezionale" />
								</form:label>
							</td>
							<td colspan="4">
								<c:out value="${atto.note }"></c:out>
							</td>
						</tr>
					</c:if>					
				</c:if>
				<c:if test="${atto.numeroProcedimentoOrigine!=null}">
					<tr>
						<td width="15%">
							<form:label path="atto.numeroProcedimentoOrigine">
								&nbsp;
							</form:label>
						</td>
						<td width="35%">
							<c:out value="${atto.numeroProcedimentoOrigine}" />
						</td>
						<td width="15%">&nbsp;</td>
						<td width="35%">&nbsp;</td>
					</tr>
				</c:if>
			</table>

			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<c:if test="${sessionScope.procedimento.tipoFascicolo.id!=6}" >
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettidelprocedimento" /></td>
					</c:if>
					<c:if test="${sessionScope.procedimento.tipoFascicolo.id==6}" >
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.soggettoIntestatario" /></td>
					</c:if>
				</tr>
			</table>
			<c:if test="${(procedimento.tipoProcedimento.id == 17 ) && listaIntestatari!=null}"> 
				<display:table id="row" name="${listaIntestatari}" class="tb_lista" uid="amm" >
					<display:column property="descrizioneAmministratore" title="Generalità" sortable="false"/>
				</display:table>	
			</c:if>
			<c:if test="${(procedimento.tipoProcedimento.id == 19) && listaIntestatari!=null}">
				<display:table id="row" name="${soggettoDaCorregere}" class="tb_lista" uid="amm" >
					<display:column property="descrizioneSoggetto" title="Generalità - Soggetto da correggere" sortable="false"/>
				</display:table>	
			</c:if>
			<c:if test="${(procedimento.tipoProcedimento.id == 19) && listaIntestatari!=null}"> 
				<display:table id="row" name="${listaIntestatari}" class="tb_lista" uid="amm" >
					<display:column property="descrizioneAmministratore" title="Generalità - Soggetto corretto" sortable="false"/>
				</display:table>	
			</c:if>
			<c:if test="${!((procedimento.tipoProcedimento.id == 17 || procedimento.tipoProcedimento.id == 18 || procedimento.tipoProcedimento.id == 19) && listaIntestatari!=null)}">
				<display:table class="tb_lista" uid="direttiInteressati" name="${direttiInteressati}" pagesize="10" requestURI="${azione}">
	            <display:column property="descrizioneSoggettoConVersione" title="Generalità" sortable="false" />
					<c:if test="${procedimento.tipoProcedimento == null || procedimento.tipoProcedimento.id != 9 }">							
						<display:column property="dataIscrizione" title="Data iscrizione" format="{0,date,dd/MM/yyyy}" sortable="false" />
						<display:column property="misura.descrizioneMisura" title="Misure" sortable="false" />
						<display:column property="misura.cauzione" title="Cauzione" sortable="false" />
						<display:column property="misura.art67" title="Art.67" sortable="false" />
					</c:if>
				</display:table>
			</c:if>
			
			<br /><br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro"></td>
				</tr>
			</table>
		</form:form>
		
		<smp:back buttonId="backButton"></smp:back>
	</tiles:putAttribute>
</tiles:insertDefinition>
