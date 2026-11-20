<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione"
			value="/procedimento/bene/modificaBeneFinanziario.htm" />
		<p class="titolo_page">
			<spring:message code="bene.finanziario.modifica" />
		</p>
		<form:form id="id_beneForm" method="post" action="${azione}" commandName="beneForm">
			<form:hidden path="idBene"/>
			
			<form:hidden path="tipologia.descrizione"/>
			
			<form:hidden path="tipologia.id"/>
			<form:hidden path="categoria.id"/>
			<form:hidden path="categoria.descrizione"/>
			
			<%-- P.Q. 21/10/2022 Ticket 20221018014 Abilito la sottocategoria per finanziari 
			<form:hidden path="sottocategoria.id"/>
			<form:hidden path="sottocategoria.descrizione"/>
			--%>
			<%-- 
			
			
			<form:hidden path="indirizzoBeneFin"/>
			--%>
			
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<form:hidden id="id_abiCode" path="abi"/>
			<form:hidden path="descrizioneEnte"/>
			<form:hidden path="provinciaSel"/>
			<form:hidden path="disabledProvincia"/>
			<form:hidden path="disabledValutaEst"/>
			
	
			<br/>
				 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br/>
			<table class="tb_form">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene" /> <c:out value="${beneForm.idBene}" /></td>
				</tr>
			</table>
			<br />
			<table class="tb_form" border=0>
				<%-- --%>
				<tr>
					<td width="15%"><form:label path="tipologia.id">
							<spring:message code="bene.tipologia" />
						</form:label>
					</td>
					<td colspan="3">
						<select style="width:20%" disabled="disabled">
							<option value="${beneForm.tipologia.descrizione}">${beneForm.tipologia.descrizione}</option>
						</select>
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="categoria.id">
							<spring:message code="bene.categoria" />
						</form:label>
					</td>
					<td colspan="3">
						<select style="width:20%" disabled="disabled">
							<option value="${beneForm.categoria.id}">${beneForm.categoria.descrizione}</option>
						</select>
						<%-- 
						<form:select id="id_cat" path="categoria.id" size="1" disabled="true">
							<form:option value="0" label="------------Seleziona Categoria------------" />
							<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
						</form:select>
						--%>
						<form:errors path="categoria.id" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="sottocategoria.id">
							<spring:message code="bene.sottocategoria" />
						</form:label>
					</td>
					<td colspan="3">
					<%-- P.Q. 21/10/2022 Ticket 20221018014 Abilito la sottocategoria per finanziari --%>
					<%--
						<select style="width:20%" disabled="disabled">
							<option value="${beneForm.sottocategoria.id}">${beneForm.sottocategoria.descrizione}</option>
						</select>
		            --%>
						 
						<form:select path="sottocategoria.id" size="1">
							<form:option value="0" label="------Seleziona Sottocategoria-------" />
							<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
						</form:select>
												
						<form:errors path="sottocategoria.id" class="testoRosso"/>
					</td>
				</tr>
				
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.finanziario.datirelativi" /></td>
				</tr>
					<tr>
					<td width="15%"><form:label path="descOperFinanz">
							<spring:message code="bene.finanziario.descrizioneente" />
						</form:label>
					</td>
					<td colspan="3">
		        		<div class="ui-widget">
							<form:input id="id_descrizioneDescOperFinanz" path="descOperFinanz.descrizione" onBlur="svuota()"
										cssStyle="width:95%" value="${beneForm.descrizioneEnte}"
										title="Immettere almeno 5 lettere per avviare la ricerca"/>
							<div id="id_loadingDescOperFinanz"></div>
						</div>
						<form:hidden id="id_hiddenDescOperFinanz" path="descOperFinanz.id"/>
						<form:errors path="descOperFinanz.id" class="testoRosso"/>
		        	</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="codiceAbi">
							<spring:message code="bene.finanziario.abi" />
						</form:label>
					</td>
					<td colspan="3">
					<!-- 
						// F.C. 06/04/2022 SITMP-386 nel caso di modifica bene finanziario il campo abi deve
						// essere disabilitato in presenza dell'operatore economico estero -->
						<form:input type="text" id="id_codiceAbi" path="codiceAbi" 
									title="Immettere le 5 cifre del codice ABI" cssStyle="width:10%" maxlength="5" 
									onkeypress="return soloNumeri(event)" onchange="submitForm()"
									readonly="${isOperatoreEconomicoEstero == true}"/>
						<form:errors path="codiceAbi" class="testoRosso"/>
					</td>
				</tr>
			
				<tr>
					<td width="15%"><form:label path="provincia">
							<spring:message code="bene.finanziario.provincia" />
						</form:label>
					</td>
					<td colspan="3">
						<form:select path="provincia.id" size="1" disabled="${beneForm.disabledProvincia  || isOperatoreEconomicoEstero == true}" onchange="submitForm()">
							<form:option value="-1" label="------Seleziona Provincia------------" />
							<form:options items="${provinceAbi}" itemValue="id" itemLabel="descrizione" />
						</form:select>
						<form:errors path="provincia.id" class="testoRosso"/>
					</td>
				</tr>	
				<tr>
					<td width="15%"><form:label path="codiceCab">
							<spring:message code="bene.finanziario.cab" />
						</form:label>
					</td>
					<td colspan="3">
						<c:if test="${beneForm.provincia.id==null || beneForm.provincia.id == -1}">
			        		<div class="ui-widget">
								<form:input id="id_descrizioneCodiceCab" path="codiceCab.descrizione" cssStyle="width:20%" 
											title="Immettere almeno 2 lettere per avviare la ricerca"
											readonly="${isOperatoreEconomicoEstero == true}"
											maxlength="5" onkeypress="return soloNumeri(event)"/>
								<div id="id_loadingCodiceCab"></div>
							</div>
								<form:errors path="codiceCab.id" class="testoRosso"/>
								<form:hidden id="id_hiddenCodiceCab" path="codiceCab.id"/>
						</c:if>
						<c:if test="${beneForm.provincia.id!=null && beneForm.provincia.id != -1}">
							<form:select path="codiceCab.id" size="1" onchange="submitForm()" disabled="${isOperatoreEconomicoEstero == true}">
								<form:option value="0" label="-----Seleziona Codice CAB-----" />
								<form:options items="${codiciCab}" itemValue="id" itemLabel="descrizione" />
							</form:select>
							<form:errors path="codiceCab.id" class="testoRosso"/>
						</c:if>
		        	</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="indirizzoBeneFin">
							<spring:message code="bene.finanziario.indirizzo" />
						</form:label>
					</td>
					<td colspan="3">
						<form:input path="indirizzoBeneFin" value="${beneForm.indirizzoBeneFin}" cssStyle="width:95%" disabled="true"/>
					</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="codiceIban">
							<spring:message code="bene.finanziario.iban" />
						</form:label>
					</td>
					<td colspan="1" id="td_iban">
						<form:input path="codiceIban" title="Immettere il codice IBAN" 
									readonly="${isOperatoreEconomicoEstero == true}" 
									maxlength="34" size="50"/>
						<form:errors path="codiceIban" class="testoRosso"/>
					</td>
					
					<td width="15%"><form:label path="codiceSwift">
							<spring:message code="bene.finanziario.swift" />
						</form:label>
					</td>
					<td colspan="1">
						<form:input path="codiceSwift" title="Immettere il codice BIC/SWIFT" 
									maxlength="11" size="15"/>
						<form:errors path="codiceSwift" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="numeroRegistrazione" >
							<spring:message code="bene.finanziario.numeroregistrazione" />
						</form:label>
					</td>
					<td colspan="3">
					<!-- GC/PQ 28/08/2024: TT#202407020117 sostituita la funzione che verifica solo numeri con una che verifica lettere e numeri 
					 	onkeypress="return soloNumeri(event)
					-->
						<form:input path="numeroRegistrazione" maxlength="20" size="30"
							readonly="${isOperatoreEconomicoEstero == true}"
							onkeypress="return chkLettereNumeri()" /> <form:errors path="numeroRegistrazione" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="valutaEur">
							<spring:message code="bene.finanziario.importo.euro" />
						</form:label>
					</td>
					<td colspan="3">
						<form:input path="valutaEur" maxlength="15" size="20" />
						<form:errors path="valutaEur" class="testoRosso"/>
					</td>
				</tr>
				<tr>
				    <td width="15%"><form:label path="tipoValuta"><spring:message code="bene.finanziario.valuta"/></form:label></td>
					<td width="35%">							
						<form:select path="tipoValuta.id" size="1" onchange="submitForm()">
							<form:option value="-1" label="-----Seleziona Valuta------" />
							<form:options items="${valuta}" itemValue="id" itemLabel="descrizione" /> 							
						</form:select>
					</td>
					<td><form:label path="valutaEst">
							<spring:message code="bene.finanziario.importo.estero"/>
						</form:label>
					</td>
					<td width="35%">
						<form:input path="valutaEst" maxlength="15" size="20" 
									disabled="${beneForm.disabledValutaEst}" />
						<form:errors path="valutaEst" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="direttoreProtempore">
							<spring:message code="bene.finanziario.direttoreprotempore" />
						</form:label>
					</td>
					<td colspan="3">
						<form:input path="direttoreProtempore" size="50"/>
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="gravame">
							<spring:message code="bene.gravame" />
						</form:label>
					</td>
					<td width="35%">
					  <form:radiobutton path="gravame" value="S" label="SI"/>
					  <form:radiobutton path="gravame" value="N" label="NO"/>
				 	</td>
					<td><form:label path="note">
							<spring:message code="note" />
						</form:label>
					</td>
					<td width="35%">
						<form:textarea path="note" rows="5" />
					</td>
				</tr>
			</table>

			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Salva" name="Modifica"></td>
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro" title="Indietro"></td>
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
						document.getElementById('id_beneForm').submitForm();
					}
				}
				function soloNumeri(evt){   
					var charCode = (evt.which) ? evt.which : event.keyCode   
					if (charCode > 31 && (charCode < 48 || charCode > 57))
						return false;
					return true;
				}
				function chkLettereNumeri() {
					  var charCode = event.which ? event.which : event.keyCode;
					  
					  var isCapitalAlphabet = charCode >= 65 && charCode <= 90;
					  var isSmallAlphabet = charCode >= 97 && charCode <= 122;
					  var isNumber = charCode >= 48 && charCode <= 57;

					  if (isCapitalAlphabet || isSmallAlphabet || isNumber) {
					    return true;
					  } else {
					    return false;
					  }
					}
				function submitForm(){
					$('#id_beneForm').find(':disabled').removeAttr('disabled');
					$('#id_beneForm').submit();
				}
				
				$(document).ready(function(){
					doAutocompleteOpFinanz("DescOperFinanz");
					doAutocompleteC("CodiceCab","codiceAbi");

					if(!<c:out value="${modificabile}" default="false"/>){

						if($('#codiceIban').val().length>0 && $('#td_iban span').length == 0) {
							// P.Q. 15/11/2021 Inizio Ticket 20211022016
							$('[name="codiceIban"]').attr('readonly',true);
							// P.Q. 15/11/2021 Fine Ticket 20211022016							
						}else{
							// F.C. 06/04/2022 Inserita if per poter modificare il bene nel caso di ente fittizio
							if($('#descOperFinanz.descrizione').val().length>0 ) {
								$('[name="codiceAbi"]').attr('disabled','disabled');
								$('[name="descOperFinanz.descrizione"]').attr('disabled','disabled');
								$('[name="provincia.id"]').attr('disabled','disabled');
								$('[name="codiceCab.id"]').attr('disabled','disabled');
								$('[name="numeroRegistrazione"]').attr('disabled','disabled');
								// P.Q. 15/11/2021 Inizio Ticket 20211022016							
								$('[name="codiceCab.descrizione"]').attr('disabled','disabled');
								// P.Q. 15/11/2021 Fine Ticket 20211022016
							}							
						}
					}

					$('#id_beneForm').bind('submit', function() {
				        $(this).find(':disabled').removeAttr('disabled');
				    });

				    
				});
			</script>
	</tiles:putAttribute>
</tiles:insertDefinition>
