<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%
	String idTipoProc = (String)(request.getSession().getAttribute("idTipoProc"));
%>

<!--Roberto 20-04-2012 
<body onload="disabilitaCampiDifensoriAssociati('${disabledViewProc}')">
 -->
<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			
			<script type='text/javascript' src="${pageContext.request.contextPath}/js/disabilita.js"></script>
		
			<spring:url var="azione" value="/procedimento/soggetto/gestioneDifensori.htm" />
			<p class="titolo_page"><spring:message code="soggetto.difensori" /></p>
			<form:form method="post" action="${azione}" commandName="difensoreSoggettoForm">
				<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
				<br/>
				<form:hidden path="soggettoForm.idSoggetto"/>
				<form:hidden path="soggettoForm.idVersione"/>
				<form:hidden path="soggettoForm.tipoSoggetto"/>
				<c:if test="${(sessionScope.procedimento.tipoProcedimento.id != 17) && (sessionScope.procedimento.tipoProcedimento.id != 18) && (sessionScope.procedimento.tipoProcedimento.id != 19)}">
					<table class="tb_form">
						
						<form:hidden path="gratuitoPatrocinioCaricato" id="gratuitoPatrocinioCaricato"/>
						<tr>
							<td class="td_titolo" colspan="4"><c:out value="${difensoreSoggettoForm.soggettoForm.descrizioneSoggetto}" /></td>
						</tr>
						<tr>
							<td class="td_titolo" colspan="4">
							<form:checkbox path="soggettoForm.gratuitoPatrocinio" onclick="this.form.submit()" id="gratuitoPatrocinio"/>
							<spring:message code="soggetto.difensore.gratuitopatrocinio" /></td>
						</tr>
						
						<c:if test="${(difensoreSoggettoForm.soggettoForm.gratuitoPatrocinio == 'true') }">
						<tr>
							<td><form:label path="soggettoForm.annoRegistroGratuitoPatrocinio" cssClass="required"><spring:message code="soggetto.difensore.registroanno"/></form:label></td>
							<td><form:input path="soggettoForm.annoRegistroGratuitoPatrocinio" maxlength="4" size="5" id="annoRegistro" onkeydown="attivaSalva()" onkeypress="attivaSalva()" disabled="false"/>
							<form:errors path="soggettoForm.annoRegistroGratuitoPatrocinio" class="testoRosso" /></td>
							<td><form:label path="soggettoForm.numeroRegistroGratuitoPatrocinio" cssClass="required"><spring:message code="procedimento.numero" /></form:label></td>
							<td><form:input path="soggettoForm.numeroRegistroGratuitoPatrocinio" maxlength="12" id="numeroRegistro" onkeydown="attivaSalva()" onkeypress="attivaSalva()" disabled="false"/>
							<form:errors path="soggettoForm.numeroRegistroGratuitoPatrocinio" class="testoRosso" /></td>
						</tr>
						<tr>
							<td><form:label path="soggettoForm.dataIstanzaGratuitoPatrocinio" cssClass="required"><spring:message code="soggetto.difensore.dataistanza" /></form:label></td> 
							<td colspan="3"><form:input path="soggettoForm.dataIstanzaGratuitoPatrocinio" id="dataIstanza" maxlength="10" size="12" onkeydown="attivaSalva()" onkeypress="attivaSalva()" onchange="attivaSalva()" disabled="false"/>
								<form:errors path="soggettoForm.dataIstanzaGratuitoPatrocinio" class="testoRosso" /></td>
						</tr>
						<tr>
							<td><form:label path="soggettoForm.dataProvvedimentoGratuitoPatrocinio"><spring:message code="soggetto.difensore.dataprovvedimento" /></form:label></td> 
							<td><form:input path="soggettoForm.dataProvvedimentoGratuitoPatrocinio" id="dataProvvedimento" maxlength="10" size="12" onkeydown="attivaSalva()" onkeypress="attivaSalva()" onchange="attivaSalva()" disabled="false"/>
								<form:errors path="soggettoForm.dataProvvedimentoGratuitoPatrocinio" class="testoRosso" /></td>
							<td><form:label path="soggettoForm.tenoreProvvedimentoGratuitoPatrocinio.id" ><spring:message code="soggetto.difensore.tenoreprovvedimento"/></form:label></td>
				        	<td>
								<form:select path="soggettoForm.tenoreProvvedimentoGratuitoPatrocinio.id" size="1" onchange="attivaSalva()" id="tenore" disabled="false">	
									<form:option value="-1" label="-----------Seleziona Tenore----------"/>
									<form:option value="A" label="Accoglie"/>
									<form:option value="R" label="Rigetta"/>
									<!-- 
									<form:options items="${tipiTenore}"  itemValue="id" itemLabel="descrizione" />
									 --> 																
								</form:select>
								<form:errors path="soggettoForm.tenoreProvvedimentoGratuitoPatrocinio.id" class="testoRosso" />
				        	</td>
						</tr>
						
							<tr>
					
							<td><form:label path="soggettoForm.descNoteGp">
									<spring:message code="note" />
								</form:label></td>
							<td colspan="3"><form:textarea path="soggettoForm.descNoteGp" rows="2" cssStyle="width:98%" onkeydown="attivaSalva()" onkeypress="attivaSalva()" onchange="attivaSalva()" disabled="false" /><br></td>
						
					     </tr>
					     
						<logic:equal name="difensoreSoggettoForm" property="gratuitoPatrocinioCaricato" value="true">
							<tr>
								<td><form:label path="soggettoForm.dataRevocaGratuitoPatrocinio"><spring:message code="soggetto.difensore.datarevoca" /></form:label></td> 
								<td colspan="3"><form:input path="soggettoForm.dataRevocaGratuitoPatrocinio" id="dataRevoca" maxlength="10" size="12" onkeydown="attivaSalva()" onkeypress="attivaSalva()" onchange="attivaSalva()" disabled="false"/>
								<form:errors path="soggettoForm.dataRevocaGratuitoPatrocinio" class="testoRosso" /></td>
							</tr>
						</logic:equal>
					</table>
					<table class="tb_bottoni" align="center">
						<tr>
							<td class="td_tasto"><input type="submit" value="Salva" name="SalvaGratuitoPatrocinio" id="salva" disabled="disabled"></td>
						</tr>
					
					</c:if>
					</table>
					</br>
				</c:if>
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.difensore.listaDifensoriAssociati"/></td>
					</tr>
				</table>
				
				<display:table class="tb_lista" name="${difensoriAssociati}" uid="difensoriAssociati" pagesize="10" requestURI="${azione}" >
					<display:column style="text-align: center; width: 5%" title="Seleziona">
						<!-- Roberto 20-04-2012
						<c:if test="${disabledViewProc==true}">
							<input type="radio" name="radioIDS" id="radioID_${difensoriAssociati_rowNum}" onclick="selectID('${difensoriAssociati.difensoreForm.idDifensore}', '${difensoriAssociati.difensoreForm.idReginde}');" disabled="${disabledViewProc}" />
						</c:if>
						<c:if test="${disabledViewProc!=true}">
							<input type="radio" name="radioIDS" id="radioID_${difensoriAssociati_rowNum}" onclick="selectID('${difensoriAssociati.difensoreForm.idDifensore}', '${difensoriAssociati.difensoreForm.idReginde}');" />
						</c:if>
						-->
						<input type="radio" name="radioIDS" id="radioID_${difensoriAssociati_rowNum}" onclick="selectID('${difensoriAssociati.difensoreForm.idDifensore}', '${difensoriAssociati.difensoreForm.idReginde}');" />
						</display:column>
					<display:column property="difensoreForm.descrizioneDifensore" title="Generalità" sortable="false" />
					<display:column property="difensoreForm.descrizioneDomicilio" title="Domicilio Legale" sortable="false" />
					<display:column property="dataNomina" title="Data Nomina" format="{0,date,dd/MM/yyyy}" sortable="false" />
					<display:column property="dataRevoca" title="Data Revoca" format="{0,date,dd/MM/yyyy}" sortable="false" />
					<display:column property="descTipoNomina" title="Tipo Nomina" sortable="false" />
				</display:table>
					
					<form:hidden path="difensoreForm.idDifensore" id="idDifensore"/>
					<form:hidden path="difensoreForm.idReginde" id="idReginde"/>
					<form:hidden path="soggettoForm.descrRuolo"/>
					<form:hidden path="soggettoForm.idRuolo" />	
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<logic:notEmpty name="difensoriAssociati">
						<td class="td_tasto"><input type="submit" value="Dettaglio" name="DettaglioDifensore"></td>
						<c:if test="${profilo.ruolo!='MAGISTRATO'}">
						<c:if test="${procedimento.statoProcedimento.id!=20}">
							<c:if test="${sessionScope.profilo.idTipoUfficio==17}">
									<td class="td_tasto"><input type="submit" value="Modifica" name="ModificaDifensore" disabled="disabled"></td>
									<td class="td_tasto"><input type="submit" value="Cancella" name="CancellaDifensore" disabled="disabled"></td>
							</c:if>
							<c:if test="${sessionScope.profilo.idTipoUfficio!=17}">
								<c:choose>
									<c:when test="${procedimento.statoProcedimento.id==22 || procedimento.statoProcedimento.id==33}">
										<td class="td_tasto"><input type="submit" value="Modifica" name="ModificaDifensore" disabled="disabled"></td>
										<td class="td_tasto"><input type="submit" value="Cancella" name="CancellaDifensore" disabled="disabled" ></td>
									</c:when>
									<c:otherwise>
										<td class="td_tasto"><input type="submit" value="Modifica" name="ModificaDifensore"></td>
										<td class="td_tasto"><input type="submit" value="Cancella" name="CancellaDifensore"></td>														
									</c:otherwise>
								</c:choose>
							</c:if>
						</c:if>	
						<c:if test="${procedimento.statoProcedimento.id==20}">
							<td class="td_tasto"><input type="submit" value="Modifica" name="ModificaDifensore" disabled="disabled"></td>
							<td class="td_tasto"><input type="submit" value="Cancella" name="CancellaDifensore" disabled="disabled"></td>
						</c:if>	
						</c:if>
						</logic:notEmpty>
						<logic:notPresent name="abilitaRicerca">
						<c:if test="${profilo.ruolo!='MAGISTRATO'}">
						<c:if test="${procedimento.statoProcedimento.id!=20}">
							<c:choose>
								<c:when test="${((sessionScope.profilo.idTipoUfficio==17) || (sessionScope.procedimento.statoProcedimento.id== 22 || sessionScope.procedimento.statoProcedimento.id== 33))}">
									<td class="td_tasto"><input type="submit" value="Nuovo" name="AbilitaRicerca" disabled="disabled"></td>
								</c:when>
								<c:otherwise>
									<td class="td_tasto"><input type="submit" value="Nuovo" name="AbilitaRicerca"></td>
								</c:otherwise>
							</c:choose>						
							
							
							
							
							
						
						</c:if>
						<c:if test="${procedimento.statoProcedimento.id==20}">
						
						
						<td class="td_tasto"><input type="submit" value="Nuovo" name="AbilitaRicerca" disabled="true"></td>
						
						
						</c:if>
						</c:if>
						</logic:notPresent>
						<%--
							<logic:notEqual name="difensoreSoggettoForm" property="soggettoForm.descrRuolo" value="TERZO" >
								<c:if test="${difensoreSoggettoForm.soggettoForm.descrRuolo != 'TERZO'}">
									
									<c:if test="${idTipoProc==null}">
										<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/soggetto/visualizzaDirettiInteressati.htm')"></td>
									</c:if>
									<c:if test="${idTipoProc!=null}">
										<c:if test="${idTipoProc==1 || idTipoProc==2 || idTipoProc==4 }">
											<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/soggetto/visualizzaDirettiInteressati.htm')"></td>
										</c:if>
										<c:if test="${idTipoProc==10}">
											<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/soggetto/visualizzaDirettiInteressatiRiesame.htm')"></td>
										</c:if>
									</c:if>
								
								</c:if>
								
								<c:if test="${difensoreSoggettoForm.soggettoForm.descrRuolo == 'TERZO'}">
									
									<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/soggetto/visualizzaDirettiInteressatiTerzi.htm')" /></td>
								
								</c:if>
							
							</logic:notEqual>	
							<logic:equal name="difensoreSoggettoForm" property="soggettoForm.descrRuolo" value="TERZO" >
									<td class="td_tasto"><input type="button" value="Indietro" onClick="indietro('/procedimento/soggetto/visualizzaDirettiInteressatiTerzi.htm')" /></td>
							</logic:equal>		
						
						 --%>
						 <td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
									
					</tr>
				</table>
				
			<logic:present name="abilitaRicerca">
				
				<table class="tb_form">
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.difensori.ricerca" /></td>
					</tr>
					<tr>
						<td><form:label path="difensoreForm.cognome"><spring:message code="cognome" /></form:label></td> 
						<td><form:input path="difensoreForm.cognome"  maxlength="35" size="30"/>
							<form:errors path="difensoreForm.cognome" class="testoRosso" />
						</td>
				</table>
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" value="Ricerca in ReGIndE" name="RicercaDifensoreReginde" onclick="correggiAccenti()"></td>
						<c:if test="${visualizzaPulsanteRicerca=='Ricerca'}">
							<td class="td_tasto"><input type="submit" value="Ricerca" name="RicercaDifensore" onclick="correggiAccenti()"></td>
						</c:if>
					</tr>
				</table>
				
				<logic:present name="difensoriWebService">
					<br/>
					<table class="tb_form">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="soggetto.difensore.listaDifensoriReginde"/></td>
						</tr>
					</table>
					<display:table class="tb_lista" uid="difensoriWebService" name="${difensoriWebService}" pagesize="10" requestURI="${azione}" >
						<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
							<input type="radio" name="radioIDS" id="radioID_${difensoriWebService_rowNum}" onclick="selectID('${difensoriWebService.difensoreForm.idDifensore}', '${difensoriWebService.difensoreForm.idReginde}');" />
							<!--<form:radiobutton path="difensoreForm.idReginde" value="${difensoriWebService.difensoreForm.idReginde}"/>-->
						</display:column>
						<display:column property="difensoreForm.cognome" title="Cognome" sortable="false" />
						<display:column property="difensoreForm.nome" title="Nome" sortable="false" />
						<display:column property="difensoreForm.dataNascita" title="Data di Nascita" format="{0,date,dd/MM/yyyy}" sortable="false" />
						<display:column property="difensoreForm.luogoNascita" title="Luogo di Nascita" sortable="false" />
					</display:table>
	
					<table class="tb_bottoni" align="center">
						<tr>
							<logic:notEmpty name="difensoriWebService">
								<td class="td_tasto"><input type="submit" value="Associa" name="AssociaDifensoreReginde"></td>
							</logic:notEmpty>
						</tr>
					</table>
				</logic:present>
				
				<logic:present name="difensoriBaseDati">
					<br/>
					<table class="tb_form">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="soggetto.difensore.listaDifensori"/></td>
						</tr>
					</table>
					<display:table class="tb_lista" uid="difensoriBaseDati" name="${difensoriBaseDati}" pagesize="10" requestURI="${azione}" >
						<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
							<!--<form:radiobutton path="difensoreForm.idDifensore" value="${difensoriBaseDati.difensoreForm.idDifensore}"/>-->
							<input type="radio" name="radioIDS" id="radioID_${difensoriBaseDati_rowNum}" onclick="selectID('${difensoriBaseDati.difensoreForm.idDifensore}', '${difensoriBaseDati.difensoreForm.idReginde}');" />
						</display:column>
						<display:column property="difensoreForm.cognome" title="Cognome" sortable="false" />
						<display:column property="difensoreForm.nome" title="Nome" sortable="false" />
						<display:column property="difensoreForm.dataNascita" title="Data di Nascita" format="{0,date,dd/MM/yyyy}" sortable="false" />
						<display:column property="difensoreForm.luogoNascita" title="Luogo di Nascita" sortable="false" />
					</display:table>
	
					<table class="tb_bottoni" align="center">
						<tr>
							<logic:notEmpty name="difensoriBaseDati">
								<td class="td_tasto"><input type="submit" value="Associa" name="AssociaDifensoreBaseDati"></td>
							</logic:notEmpty>
						</tr>
					</table>
				</logic:present>
				<table class="tb_bottoni" align="center">
						<tr>
							<c:if test="${visualizzaPulsanteNuovo=='NuovoDifensore'}">
							<c:if test="${profilo.ruolo!='MAGISTRATO'}">
								<td class="td_tasto"><input type="submit" value="Nuovo" name="NuovoDifensore"></td>
							</c:if>
							</c:if>
						</tr>
				</table>
			</logic:present>
			</form:form>
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>
			
			<script type="text/javascript">
				$(document).ready(function() {

					if ((document.getElementById("gratuitoPatrocinioCaricato").value =='true') && (!document.getElementById("gratuitoPatrocinio").checked)){
						attivaSalva();
						disabilita();
						} else {
						prendiData("#dataIstanza",true);
						prendiData("#dataProvvedimento",true);
						prendiData("#dataRevoca",true);
						}
				});				

				function correggiAccenti() {
					//console.log("correggiAccenti");
					var cognomeInput = document.getElementById("difensoreForm.cognome");
					var testo = cognomeInput.value;
					//console.log("pre="+testo);
					testo = testo.toLowerCase()
					testo = testo.replace(/à/g, "a'");
					testo = testo.replace(/è/g, "e'");
					testo = testo.replace(/é/g, "e'");
					testo = testo.replace(/ì/g, "i'");
					testo = testo.replace(/ò/g, "o'");
					testo = testo.replace(/ù/g, "u'");
					testo = testo.toUpperCase()
					//console.log("post="+testo);
					cognomeInput.value = testo; 
				}

				function attivaSalva() {
					var ifg = isFascicoloGestioneBeni();
					if(ifg == 0) {
						document.getElementById("salva").disabled = false;
					}
				}
				
				function disabilita(){
					document.getElementById("annoRegistro").disabled = true;
					document.getElementById("numeroRegistro").disabled = true;
					document.getElementById("dataIstanza").disabled = true;
					document.getElementById("dataProvvedimento").disabled = true;
					document.getElementById("tenore").disabled = true;
					document.getElementById("dataRevoca").disabled = true;
				}
	
				function selectID(idDifensore, idReginde) {
					document.getElementById("idDifensore").value = idDifensore;
					document.getElementById("idReginde").value = idReginde;
				}

				function isFascicoloGestioneBeni() {
					var ret = 0;
					var idTipoFasc = ${sessionScope.TIPO_FASCICOLO}; 
					if(idTipoFasc == 8) ret = 1;
					else ret = 0;
					return ret;
				}

				var ifg = isFascicoloGestioneBeni();
				if(ifg == 1) {
					if(document.getElementsByName("AbilitaRicerca").length != 0) {
						document.getElementsByName("AbilitaRicerca")[0].disabled=true;
					}
					if(document.getElementsByName("ModificaDifensore").length != 0) {
						document.getElementsByName("ModificaDifensore")[0].disabled=true;
					}
					if(document.getElementsByName("CancellaDifensore").length != 0) {
						document.getElementsByName("CancellaDifensore")[0].disabled=true;
					}
					if(document.getElementsByName("gratuitoPatrocinio").length != 0) {
						document.getElementsByName("gratuitoPatrocinio")[0].disabled=true;
					}			
				}

				$(document).keypress(
						function(event){
							if (event.which == '13') {
								event.preventDefault();
							}
					});
			</script>	

		</tiles:putAttribute>
	</tiles:insertDefinition>