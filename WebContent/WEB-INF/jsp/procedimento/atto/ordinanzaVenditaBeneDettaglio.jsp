<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	String fromMenuAtti = (String) request.getSession().getAttribute("fromMenuAtti");
%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
		<spring:url var="azione" value="/procedimento/atto/ordinanzaVendita.htm" />
		<p class="titolo_page">
			<spring:message code="bene.vendita" />
		</p>
		
		<form:form method="post" action="${azione}" commandName="beneForm">
			<br/>
				 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br/>

			<table class="tb_form">
			
				<tr>
					<td class="td_titolo" colspan="4">Bene venduto: <c:out value="${dettaglioBene.descrizioneBene}"></c:out></td>
				</tr>	
						
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>	
							
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene" /> <c:out value="${beneForm.idBene}" /></td>
				</tr>

			</table>
			<table class="tb_form" border=0>
				<tr>
					<td><form:label path="tipologia.descrizione">
							<spring:message code="bene.tipologia" />
						</form:label></td>
					<td colspan="3"><c:out value="${beneForm.tipologia.descrizione}" /></td>
				</tr>
				<tr>
					<td width="15%"><form:label path="categoria.descrizione">
							<spring:message code="bene.categoria" />
						</form:label></td>
					<td width="35%"><c:out value="${beneForm.categoria.descrizione}" /></td>
					<td><form:label path="sottocategoria.descrizione">
							<spring:message code="bene.sottocategoria" />
						</form:label></td>
					<td width="35%"><c:out value="${beneForm.sottocategoria.descrizione}" /></td>
				</tr>

				<logic:equal name="beneForm" property="tipologia.id" value="1">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioImmobile.jsp" />
				</logic:equal>
				<logic:equal name="beneForm" property="tipologia.id" value="2">
					<c:if test="${beneForm.categoria.id=='6'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioDenaro.jsp" />
					</c:if>
					<c:if test="${beneForm.categoria.id=='7'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioCollezione.jsp" />
					</c:if>
					<c:if test="${beneForm.categoria.id=='8'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioAnimali.jsp" />
					</c:if>
					<c:if test="${beneForm.categoria.id=='9'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioAltriOggetti.jsp" />
					</c:if>
				</logic:equal>
				<logic:equal name="beneForm" property="tipologia.id" value="3">
					<c:if test="${beneForm.categoria.id=='10'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioVeicolo.jsp" />
					</c:if>
					<c:if test="${beneForm.categoria.id=='11'}">
						<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioBeneImmateriale.jsp" />
					</c:if>
				</logic:equal>
				<logic:equal name="beneForm" property="tipologia.id" value="4">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioBeneFinanziario.jsp" />
				</logic:equal>
				<logic:equal name="beneForm" property="tipologia.id" value="5">
					<jsp:include page="/WEB-INF/jsp/procedimento/bene/includeDettaglioAzienda.jsp" />
				</logic:equal>
			</table>

			<form:hidden path="idBene" />
			<form:hidden path="versioneBene" />
			<br />
			<br />
		
		</form:form>
		
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			
			<form:hidden path="codAtto" />
			<form:hidden path="warning" />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>

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
						<td><logic:notEmpty name="ufficiListFromFlussiOut">
								<form:label path="destinatario.id">
									<spring:message code="atto.destinazione" />
								</form:label>
							</logic:notEmpty></td>
						<td><logic:notEmpty name="ufficiListFromFlussiOut">
								<table>
									<c:forEach var="destinat" items="${ufficiListFromFlussiOut}">
										<tr>
											<td style="border: none"><c:out value="${destinat.descrizione}" /></td>
										</tr>
									</c:forEach>
								</table>
							</logic:notEmpty></td>
						<td><form:label path="dataRegistrazione">
								<spring:message code="atto.dataregistrazione" />
							</form:label></td>
						<td><fmt:formatDate value="${attoForm.dataRegistrazione}" type="date" pattern="dd/MM/yyyy" /></td>
					</tr>

			</table>			

			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto">
						<c:if test="${fromMenuAtti=='elenco'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elenco.htm')">
						</c:if> 
						<c:if test="${fromMenuAtti=='ordine'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
						</c:if> 
						<c:if test="${fromMenuAtti=='nuovo'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atti/elencoAttiOrdineCronologico.htm')">
						</c:if>
						<c:if test="${fromMenuAtti=='invioRicorsi'}">
							<input type="button" value="Indietro" onClick="indietro('/procedimento/atto/ricercaRicorsoCda.htm')">
						</c:if>			
					</td>	
				</tr>
			</table>
			
			</form:form>
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>
			
		    <script src="${pageContext.request.contextPath}/js/suggestlist/doAutocompleteOpFinanziari.js"></script>
			<script src="${pageContext.request.contextPath}/js/suggestlist/doAutocompleteCodiciCab.js"></script>
		
			<script type="text/javascript">

				function svuota() {
					var descEnte = document.getElementById('id_descrizioneDescOperFinanz').value;
					var abi = document.getElementById('id_codiceAbi').value;
					if(descEnte == '' && abi != ''){
						document.getElementById('id_beneForm').submit();
					}
				}
				function soloNumeri(evt){   
					var charCode = (evt.which) ? evt.which : event.keyCode   
					if (charCode > 31 && (charCode < 48 || charCode > 57))
						return false;
					return true;
				}
				$(document).ready(function(){
					doAutocompleteOpFinanz("DescOperFinanz");
					doAutocompleteC("CodiceCab","codiceAbi");
				});
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>