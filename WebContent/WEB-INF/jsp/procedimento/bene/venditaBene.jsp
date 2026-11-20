<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/bene/trasformaBene.htm" />
		<p class="titolo_page">
			<spring:message code="bene.vendita" />
		</p>
		
		<form:form id="id_beneForm" method="post" action="${azione}" commandName="beneForm">
			
			<form:hidden path="idBene"/>
			
			<form:hidden path="tipoTrasformazione.id"/>
			<form:hidden path="tipologia.id"/>
			<form:hidden path="tipologia.descrizione"/>
			<form:hidden path="categoria.id"/>
			<form:hidden path="categoria.descrizione"/>
			
			<form:hidden path="sottocategoria.id"/>
			<form:hidden path="sottocategoria.descrizione"/>
			
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<form:hidden id="id_abiCode" path="abi"/>
			<form:hidden path="descrizioneEnte"/>
			<form:hidden path="provinciaSel"/>
			<form:hidden path="disabledProvincia"/>
			<form:hidden path="disabledValutaEst"/>
			<form:hidden path="indirizzoBeneFin"/>
	
			<br/>
				 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br/>
			<table class="tb_form" border=0>
				<tr>
					<td width="15%"><form:label path="tipologia.id">
							<spring:message code="bene.tipologia" />
						</form:label>
					</td>
					<td colspan="3">
						<form:select path="tipologia.id" size="1" disabled="true" cssStyle="width:20%">
							<form:option value="4" label="${beneForm.tipologia.descrizione}"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="categoria.id">
							<spring:message code="bene.categoria" />
						</form:label>
					</td>
					<td colspan="3">
						<form:select id="id_cat" path="categoria.id" size="1" disabled="true" cssStyle="width:20%">
							<form:option value="4" label="${beneForm.categoria.descrizione}"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="sottocategoria.id" >
							<spring:message code="bene.sottocategoria" />
						</form:label>
					</td>
					<td colspan="3">
						<form:select path="sottocategoria.id" size="1" disabled="true">
							<form:option value="4" label="${beneForm.sottocategoria.descrizione}"/>
						</form:select>
						<form:errors path="sottocategoria.id" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.denaro.datirelativi" /></td>
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
										title="Immettere almeno 5 lettere per avviare la ricerca" disabled="true"/>
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
						<form:input type="text" id="id_codiceAbi" path="codiceAbi" 
									title="Immettere le 5 cifre del codice ABI" cssStyle="width:10%" maxlength="5" 
									onkeypress="return soloNumeri(event)" onchange="submit()"  disabled="true"/>
						<form:errors path="codiceAbi" class="testoRosso"/>
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
											maxlength="5" onkeypress="return soloNumeri(event)"  disabled="true"/>
								<div id="id_loadingCodiceCab"></div>
							</div>
								<form:errors path="codiceCab.id" class="testoRosso"/>
								<form:hidden id="id_hiddenCodiceCab" path="codiceCab.id"/>
						</c:if>
						<c:if test="${beneForm.provincia.id!=null && beneForm.provincia.id != -1}">
							<form:select path="codiceCab.id" size="1" onchange="submit()"  disabled="true">
								<form:option value="0" label="-----Seleziona Codice CAB-----" />
								<form:options items="${codiciCab}" itemValue="id" itemLabel="descrizione" />
							</form:select>
							<form:errors path="codiceCab.id" class="testoRosso"/>
						</c:if>
		        	</td>
				</tr>
				
				
				
<!-- 				<tr> -->
<%-- 					<td width="15%"><form:label path="provincia"> --%>
<%-- 							<spring:message code="bene.finanziario.provincia" /> --%>
<%-- 						</form:label> --%>
<!-- 					</td> -->
<!-- 					<td colspan="3"> -->
<%-- 						<form:select path="provincia.id" size="1" disabled="${beneForm.disabledProvincia}" onchange="submit()"> --%>
<%-- 							<form:option value="-1" label="------Seleziona Provincia------------" /> --%>
<%-- 							<form:options items="${provinceAbi}" itemValue="id" itemLabel="descrizione" /> --%>
<%-- 						</form:select> --%>
<%-- 						<form:errors path="provincia.id" class="testoRosso"/> --%>
<!-- 					</td> -->
<!-- 				</tr>	 -->



				<c:if test="${beneForm.sottocategoria.id != null && beneForm.sottocategoria.id != 35}">
					<tr>
						<td width="15%"><form:label path="indirizzoBeneFin">
								<spring:message code="bene.finanziario.indirizzo" />
							</form:label>
						</td>
						<td colspan="3">
							<form:input path="indirizzoBeneFin" value="${beneForm.indirizzoBeneFin}" cssStyle="width:95%" disabled="true"/>
						</td>
					</tr>
				</c:if>
				<tr>
					<td width="15%"><form:label path="codiceIban">
							<spring:message code="bene.finanziario.iban" />
						</form:label>
					</td>
					<td colspan="1">
						<form:input path="codiceIban" title="Immettere il codice IBAN" 
									maxlength="34" size="50"/>
						<form:errors path="codiceIban" class="testoRosso"/>
					</td>
					
					<td width="15%"><form:label path="codiceSwift">
							<spring:message code="bene.finanziario.swift" />
						</form:label>
					</td>
					<td colspan="1">
						<form:input path="codiceSwift" title="Immettere il codice BIC/SWIFT" 
									maxlength="11" size="15"  disabled="true"/>
						<form:errors path="codiceSwift" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="numeroRegistrazione" >
							<spring:message code="bene.denaro.conto" />
						</form:label>
					</td>
					<td >
						<form:input path="numeroRegistrazione" maxlength="12" size="20"
								    onkeypress="return soloNumeri(event)" disabled="true"/>
					</td>
					<td><form:label path="flgVersamento">
								<spring:message code="bene.denaro.contante.flgversamento" />
							</form:label>
						</td>
						<td>
							<form:select path="flgVersamento.id" size="1" disabled="true">
								<form:option value="1" label="C/C" />
								<form:options items="${listaVersato}" itemValue="id" itemLabel="descrizione" />
							</form:select>
						</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="valutaEur">
							<spring:message code="bene.finanziario.importo.euro" />
						</form:label>
					</td>
					<td colspan="3">
						<form:input path="valutaEur" maxlength="15" size="20" cssClass="required"/>
						<form:errors path="valutaEur" class="testoRosso"/>
					</td>
				</tr>
				<tr>
				    <td width="15%"><form:label path="tipoValuta"><spring:message code="bene.finanziario.valuta"/></form:label></td>
					<td width="35%">							
						<form:select path="tipoValuta.id" size="1" onchange="submit()">
							<form:option value="-1" label="-----Seleziona Valuta------" />
							<form:options items="${valuta}" itemValue="id" itemLabel="descrizione" /> 							
						</form:select>
					</td>
					<td><form:label path="valutaEst">
							<spring:message code="bene.finanziario.importo.estero"/>
						</form:label>
					</td>
					<td width="35%">
						<form:input path="valutaEst" maxlength="15" size="20" />
						<form:errors path="valutaEst" class="testoRosso"/>
					</td>
				</tr>
					<tr>
					    <td width="15%"><form:label path="intestatario">
					    		<spring:message code="bene.denaro.contante.intestatario"/>
					    	</form:label>
					    </td>
						<td width="35%">							
							<form:input path="intestatario"/>
						</td>
						<td><form:label path="numModelloTribunale">
								<spring:message code="bene.denaro.contante.nummodellotribunale"/>
							</form:label>
						</td>
						<td width="35%">
							<form:input path="numModelloTribunale" maxlength="10"
									    onkeypress="return soloNumeri(event)"/>
						</td>
					</tr>
					<tr>
						<td width="15%"><form:label path="numBollettino">
								<spring:message code="bene.denaro.contante.numerobollettino" />
							</form:label>
						</td>
						<td colspan="3">
							<form:input path="numBollettino" title="Immettere le 27 cifre del Bollettino" 
										maxlength="27" size="50" />
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
					<td class="td_tasto"><input type="submit" value="Vendita Bene" name="VenditaBene"></td>
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