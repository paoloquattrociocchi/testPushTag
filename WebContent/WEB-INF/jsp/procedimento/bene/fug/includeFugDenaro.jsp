<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

			<tr>
				<td class="td_titolo" colspan="4"><spring:message code="bene.denaro.datirelativi" /></td>
			</tr>

			<tr>
				<td width="15%"><form:label path=""><spring:message code="bene.finanziario.descrizioneente" /></form:label></td>
				<td colspan="3">
					<c:if test="${beneForm.descOperFinanz.descrizione!=null}">
						<c:out value="${beneForm.descOperFinanz.descrizione}"/>
					</c:if>
					<c:if test="${beneForm.descrizioneEnte!=null}">
						<c:out value="${beneForm.descrizioneEnte}"/>
					</c:if>
				</td>
				<form:hidden path="descOperFinanz.id"/>
				<form:hidden path="descOperFinanz.descrizione"/>
				<form:hidden path="descrizioneEnte"/>
			</tr>
						
			<tr>
				<td width="15%"><form:label path="codiceAbi"><spring:message code="bene.finanziario.abi" /></form:label></td>
				<td colspan="3"><c:out value="${beneForm.codiceAbi}"/></td>
				<form:hidden path="codiceAbi"/>
				<form:hidden path="abi"/>
			</tr>
			
			<tr>
				<td width="15%"><form:label path="codiceCab"><spring:message code="bene.finanziario.cab" /></form:label></td>
				<td colspan="3"><c:out value="${beneForm.codiceCab.id}"/></td>
				<form:hidden path="codiceCab.id"/>
			</tr>
			
			<tr>
				<td width="15%"><form:label path="indirizzoBeneFin"><spring:message code="bene.finanziario.indirizzo" /></form:label></td>
				<td colspan="3"><c:out value="${beneForm.indirizzoBeneFin}"/></td>
				<form:hidden path="indirizzoBeneFin"/>
				<form:hidden path="indirizzo.toponimo.id"/>
				<form:hidden path="indirizzo.denominazione"/>
				<form:hidden path="indirizzo.civico"/>
				<form:hidden path="indirizzo.cap"/>
			</tr>

			<tr>
				<td><form:label path="codiceIban"><spring:message code="bene.finanziario.iban" /></form:label></td>
				<td colspan="1"><c:out value="${beneForm.codiceIban}" /></td>
				<td><form:label path="codiceSwift"><spring:message code="bene.finanziario.swift" /></form:label></td>
				<td colspan="1"><c:out value="${beneForm.codiceSwift}" /></td>
				<form:hidden path="codiceIban"/>
				<form:hidden path="codiceSwift"/>
			</tr>	

			<tr>
				<td width="15%"><form:label path="numeroRegistrazione"><spring:message code="bene.denaro.conto" /></form:label></td>
				<td><c:out value="${beneForm.numeroRegistrazione}" /></td>
				<form:hidden path="numeroRegistrazione"/>
				<td colspan="2"></td>
			</tr>
	
			<tr>
				<td width="15%"><form:label path="valutaEur"><spring:message code="bene.finanziario.importo.euro"/></form:label></td>
				<td colspan="3">
				<form:input path="valutaEur" maxlength="15" size="20"/>
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
					<form:input path="valutaEst" maxlength="15" size="20" 
								disabled="${beneForm.disabledValutaEst}" />
					<form:errors path="valutaEst" class="testoRosso"/>			
				</td>
			</tr>
			<c:if test="${beneForm.sottocategoria.id != null && beneForm.sottocategoria.id != 35 && beneForm.sottocategoria.id != 206}">
			<tr>
				<td width="15%"><form:label path="direttoreProtempore">
						<spring:message code="bene.denaro.direttore" />
					</form:label>
				</td>
				<td colspan="3">
					<form:input path="direttoreProtempore" size="50"/>
				</td>
			</tr>
		</c:if>
		<c:if test="${beneForm.sottocategoria.id != null && (beneForm.sottocategoria.id == 35 || beneForm.sottocategoria.id == 206)}">
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
				<td><form:label path="flgVersamento">
					<c:if test="${isModelloA == true}">
							<spring:message code="bene.denaro.contante.flgversamento" />
					</c:if>
					<c:if test="${isModelloA == false}">
							<spring:message code="bene.denaro.contante.flgversamento.modelloH" />
					</c:if>					
					</form:label>
				</td>
				<td>
				<form:select path="flgVersamento.id" size="1" onchange="formattaCampiNumeroBollettino(this)">
				
					<c:if test="${isModelloA == true}">
						<form:option value="-1" label="----Seleziona tipo versamento---" />
					</c:if>
					<c:if test="${isModelloA == false}">
						<form:option value="-1" label="----Seleziona tipo pagamento---" />
					</c:if>									
				
					<form:options items="${listaVersato}" itemValue="id" itemLabel="descrizione" />
				</form:select>
				<form:errors path="flgVersamento.id" class="testoRosso"/>
				</td>
				<td><form:label path="numBollettino">
						<spring:message code="bene.denaro.contante.numerobollettino" />
					</form:label>
				</td>
				<td>
					<input type="text" id="numBollettinoSx" name="numBollettinoSx" value="${numBollettinoSx}" size="${sizeBollettinoSx}" maxlength="${maxBollettinoSx}" ${maxBollettinoSx=="0"?"style='display: none'":""}/>
					<span id="numBollettinoCx" > ${numBollettinoCx} </span>
					<input type="text" id="numBollettinoDx" name="numBollettinoDx" value="${numBollettinoDx}" size="${sizeBollettinoDx}" maxlength="${maxBollettinoDx}" />
					<form:hidden path="numBollettino" />
					<form:errors path="numBollettino" class="testoRosso"/>
				</td>
			</tr>
		</c:if>
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