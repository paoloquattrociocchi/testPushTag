<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atto/ordinanzaVendita.htm" />
		<p class="titolo_page">
			<spring:message code="bene.vendita" />
		</p>
		
		<form:form id="id_formConfermaInvia" method="post" action="${azione}" commandName="attoForm" enctype="multipart/form-data">
			
			<form:hidden path="beneForm.idBene"/>
			
			<form:hidden path="beneForm.tipoTrasformazione.id"/>
			<form:hidden path="beneForm.tipologia.id"/>
			<form:hidden path="beneForm.tipologia.descrizione"/>
			<form:hidden path="beneForm.categoria.id"/>
			<form:hidden path="beneForm.categoria.descrizione"/>
			
			<form:hidden path="beneForm.sottocategoria.id"/>
			<form:hidden path="beneForm.sottocategoria.descrizione"/>
			
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<form:hidden id="id_abiCode" path="beneForm.abi"/>
			<form:hidden path="beneForm.descrizioneEnte"/>
			<form:hidden path="beneForm.provinciaSel"/>
			<form:hidden path="beneForm.disabledProvincia"/>
			<form:hidden path="beneForm.disabledValutaEst"/>
			<form:hidden path="beneForm.indirizzoBeneFin"/>
	
			<br/>
				 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br/>
			
			<table class="tb_form" border=0>

				<tr>
					<td class="td_titolo" colspan="4">Bene da vendere: <c:out value="${dettaglioBene.descrizioneBene}"></c:out></td>
				</tr>			
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>			

				<tr>
					<td width="15%"><form:label path="beneForm.tipologia.id">
							<spring:message code="bene.tipologia" />
						</form:label>
					</td>
					<td colspan="3">
						<form:select path="beneForm.tipologia.id" size="1" disabled="true" cssStyle="width:20%">
							<form:option value="4" label="${attoForm.beneForm.tipologia.descrizione}"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="beneForm.categoria.id">
							<spring:message code="bene.categoria" />
						</form:label>
					</td>
					<td colspan="3">
						<form:select id="id_cat" path="beneForm.categoria.id" size="1" disabled="true" cssStyle="width:20%">
							<form:option value="4" label="${attoForm.beneForm.categoria.descrizione}"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="beneForm.sottocategoria.id" >
							<spring:message code="bene.sottocategoria" />
						</form:label>
					</td>
					<td colspan="3">
						<form:select path="beneForm.sottocategoria.id" size="1" disabled="true">
							<form:option value="4" label="${attoForm.beneForm.sottocategoria.descrizione}"/>
						</form:select>
						<form:errors path="beneForm.sottocategoria.id" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.denaro.datirelativi" /></td>
				</tr>
				<tr>
					<td width="15%"><form:label path="beneForm.descOperFinanz">
							<spring:message code="bene.finanziario.descrizioneente" />
						</form:label>
					</td>
					<td colspan="3">
		        		<div class="ui-widget">
							<form:input id="id_descrizioneDescOperFinanz" path="beneForm.descOperFinanz.descrizione" onBlur="svuota()"
										cssStyle="width:95%" value="${attoForm.beneForm.descrizioneEnte}"
										title="Immettere almeno 5 lettere per avviare la ricerca" disabled="true"/>
							<div id="id_loadingDescOperFinanz"></div>
						</div>
						<form:hidden id="id_hiddenDescOperFinanz" path="beneForm.descOperFinanz.id"/>
						<form:errors path="beneForm.descOperFinanz.id" class="testoRosso"/>
		        	</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="beneForm.codiceAbi">
							<spring:message code="bene.finanziario.abi" />
						</form:label>
					</td>
					<td colspan="3">
						<form:input type="text" id="id_codiceAbi" path="beneForm.codiceAbi" 
									title="Immettere le 5 cifre del codice ABI" cssStyle="width:10%" maxlength="5" 
									onkeypress="return soloNumeri(event)" onchange="submit()"  disabled="true"/>
						<form:errors path="beneForm.codiceAbi" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="beneForm.codiceCab">
							<spring:message code="bene.finanziario.cab" />
						</form:label>
					</td>
					<td colspan="3">
						<c:if test="${attoForm.beneForm.provincia.id==null || attoForm.beneForm.provincia.id == -1}">
			        		<div class="ui-widget">
								<form:input id="id_descrizioneCodiceCab" path="beneForm.codiceCab.descrizione" cssStyle="width:20%" 
											title="Immettere almeno 2 lettere per avviare la ricerca"
											maxlength="5" onkeypress="return soloNumeri(event)"  disabled="true"/>
								<div id="id_loadingCodiceCab"></div>
							</div>
								<form:errors path="beneForm.codiceCab.id" class="testoRosso"/>
								<form:hidden id="id_hiddenCodiceCab" path="beneForm.codiceCab.id"/>
						</c:if>
						<c:if test="${attoForm.beneForm.provincia.id!=null && attoForm.beneForm.provincia.id != -1}">
							<form:select path="beneForm.codiceCab.id" size="1" onchange="submit()"  disabled="true">
								<form:option value="0" label="-----Seleziona Codice CAB-----" />
								<form:options items="${codiciCab}" itemValue="id" itemLabel="descrizione" />
							</form:select>
							<form:errors path="beneForm.codiceCab.id" class="testoRosso"/>
						</c:if>
		        	</td>
				</tr>

				<c:if test="${attoForm.beneForm.sottocategoria.id != null && attoForm.beneForm.sottocategoria.id != 35}">
					<tr>
						<td width="15%"><form:label path="beneForm.indirizzoBeneFin">
								<spring:message code="bene.finanziario.indirizzo" />
							</form:label>
						</td>
						<td colspan="3">
							<form:input path="beneForm.indirizzoBeneFin" value="${attoForm.beneForm.indirizzoBeneFin}" cssStyle="width:95%" disabled="true"/>
						</td>
					</tr>
				</c:if>
				<tr>
					<td width="15%"><form:label path="beneForm.codiceIban">
							<spring:message code="bene.finanziario.iban" />
						</form:label>
					</td>
					<td colspan="1">
						<form:input path="beneForm.codiceIban" title="Immettere il codice IBAN" 
									maxlength="34" size="50"/>
						<form:errors path="beneForm.codiceIban" class="testoRosso"/>
					</td>
					
					<td width="15%"><form:label path="beneForm.codiceSwift">
							<spring:message code="bene.finanziario.swift" />
						</form:label>
					</td>
					<td colspan="1">
						<form:input path="beneForm.codiceSwift" title="Immettere il codice BIC/SWIFT" 
									maxlength="11" size="15"  disabled="true"/>
						<form:errors path="beneForm.codiceSwift" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="beneForm.numeroRegistrazione" >
							<spring:message code="bene.denaro.conto" />
						</form:label>
					</td>
					<td >
						<form:input path="beneForm.numeroRegistrazione" maxlength="12" size="20"
								    onkeypress="return soloNumeri(event)" disabled="true"/>
					</td>
					<td><form:label path="beneForm.flgVersamento">
								<spring:message code="bene.denaro.contante.flgversamento" />
							</form:label>
						</td>
						<td>
							<form:select path="beneForm.flgVersamento.id" size="1" disabled="true">
								<form:option value="1" label="C/C" />
								<form:options items="${listaVersato}" itemValue="id" itemLabel="descrizione" />
							</form:select>
						</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="beneForm.valutaEur">
							<spring:message code="bene.finanziario.importo.euro" />
						</form:label>
					</td>
					<td colspan="3">
						<form:input path="beneForm.valutaEur" maxlength="15" size="20" cssClass="required"/>
						<form:errors path="beneForm.valutaEur" class="testoRosso"/>
					</td>
				</tr>
				<tr>
				    <td width="15%"><form:label path="beneForm.tipoValuta"><spring:message code="bene.finanziario.valuta"/></form:label></td>
					<td width="35%">							
						<form:select path="beneForm.tipoValuta.id" size="1">
							<form:option value="-1" label="-----Seleziona Valuta------" />
							<form:options items="${valuta}" itemValue="id" itemLabel="descrizione" /> 							
						</form:select>
					</td>
					<td><form:label path="beneForm.valutaEst">
							<spring:message code="bene.finanziario.importo.estero"/>
						</form:label>
					</td>
					<td width="35%">
						<form:input path="beneForm.valutaEst" maxlength="15" size="20" />
						<form:errors path="beneForm.valutaEst" class="testoRosso"/>
					</td>
				</tr>
					<tr>
					    <td width="15%"><form:label path="beneForm.intestatario">
					    		<spring:message code="bene.denaro.contante.intestatario"/>
					    	</form:label>
					    </td>
						<td width="35%">							
							<form:input path="beneForm.intestatario"/>
						</td>
						<td><form:label path="beneForm.numModelloTribunale">
								<spring:message code="bene.denaro.contante.nummodellotribunale"/>
							</form:label>
						</td>
						<td width="35%">
							<form:input path="beneForm.numModelloTribunale" maxlength="10"
									    onkeypress="return soloNumeri(event)"/>
						</td>
					</tr>
					<tr>
						<td width="15%"><form:label path="beneForm.numBollettino">
								<spring:message code="bene.denaro.contante.numerobollettino" />
							</form:label>
						</td>
						<td colspan="3">
							<form:input path="beneForm.numBollettino" title="Immettere le 27 cifre del Bollettino" 
										maxlength="27" size="50" />
						</td>
					</tr>				
				

				<tr>
					<td width="15%"><form:label path="beneForm.gravame">
							<spring:message code="bene.gravame" />
						</form:label>
					</td>
					<td width="35%">
					  <form:radiobutton path="beneForm.gravame" value="S" label="SI"/>
					  <form:radiobutton path="beneForm.gravame" value="N" label="NO"/>
				 	</td>
					<td><form:label path="beneForm.note">
							<spring:message code="note" />
						</form:label>
					</td>
					<td width="35%">
						<form:textarea path="beneForm.note" rows="5" />
					</td>
				</tr>
			</table>

			<form:hidden path="codAtto" />
			<form:hidden path="warning" />
			<jsp:include page="/WEB-INF/jsp/procedimento/atto/documentoAllegato.jsp" />
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"></td>
				</tr>

				<c:if test="${attoForm.dataRegistrazione==null}">
					<tr>
						<td><form:label path="dataEmissione" cssClass="required">
								<spring:message code="atto.dataemissione" />
							</form:label></td>
						<td><form:input path="dataEmissione" maxlength="10" id="id_dataEmissione" /> <form:errors path="dataEmissione" class="testoRosso" /></td>
						<td><form:label path="dataDeposito" cssClass="required">
								<spring:message code="atto.datadeposito" />
							</form:label></td>
						<td><form:input path="dataDeposito" maxlength="10" id="id_dataDeposito" /> <form:errors path="dataDeposito" class="testoRosso" /></td>
					</tr>
					<tr style="display:none">
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
					</tr>
				</c:if>
				<c:if test="${attoForm.dataRegistrazione!=null}">
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
				</c:if>
			</table>			

			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Vendita Bene" name="VenditaBene"></td>
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
			
			</form:form>
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>
			
		    <script src="${pageContext.request.contextPath}/js/suggestlist/doAutocompleteOpFinanziari.js"></script>
			<script src="${pageContext.request.contextPath}/js/suggestlist/doAutocompleteCodiciCab.js"></script>
		
			<c:if test="${attoForm.dataRegistrazione==null}">
			
				<script type="text/javascript">

					$(document).ready(function() {
						prendiData("#id_dataDeposito",true);
						prendiData("#id_dataEmissione",true);
					});

				</script>		
						
			</c:if>
			
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