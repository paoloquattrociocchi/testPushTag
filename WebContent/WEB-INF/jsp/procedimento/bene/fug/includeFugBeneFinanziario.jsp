<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

		<tr>
			<td class="td_titolo" colspan="4"><spring:message code="bene.finanziario.datirelativi" /></td>
		</tr>
		
		<tr>
			<td width="15%"><form:label path="codiceAbi"><spring:message code="bene.finanziario.abi" /></form:label></td>
			<td colspan="5"><c:out value="${beneForm.codiceAbi}"/></td>
			<form:hidden path="codiceAbi"/>
			<form:hidden path="abi"/>
		</tr>
		
		<tr>
			<td width="15%"><form:label path=""><spring:message code="bene.finanziario.descrizioneente" /></form:label></td>
			<td colspan="5">
				<c:if test="${beneForm.descOperFinanz.descrizione!=null}">
					<c:out value="${beneForm.descOperFinanz.descrizione}"/>
				</c:if>
				<c:if test="${beneForm.descrizioneEnte!=null}">
					<c:out value="${beneForm.descrizioneEnte}"/>
				</c:if>
				<form:hidden path="descOperFinanz.id"/>
				<form:hidden path="descOperFinanz.descrizione"/>
				<form:hidden path="descrizioneEnte"/>
			</td>
		</tr>
		
		<tr>
			<td><form:label path="codiceCab"><spring:message code="bene.finanziario.cab" /></form:label></td>
			<td colspan="5"><c:out value="${beneForm.codiceCab.id}"/></td>
			<form:hidden path="codiceCab.id"/>
		</tr>
		<c:if test="${beneForm.indirizzoBeneFin!=null}">
			<tr>
				<td><form:label path="indirizzoBeneFin"><spring:message code="bene.finanziario.indirizzo" /></form:label></td>
				<td colspan="5"><c:out value="${beneForm.indirizzoBeneFin}"/></td>
			</tr>
		</c:if>
					
		<c:if test="${beneForm.indirizzoBeneFin==null}">
			<tr>
				<td><form:label path="indirizzo.toponimo.id"><spring:message code="indirizzo" /></form:label></td>
				<td>
					<c:if test="${beneForm.indirizzo.toponimo.id=='1'}"><c:out value="Via" />&nbsp;&nbsp;</c:if>
					<c:if test="${beneForm.indirizzo.toponimo.id=='2'}"><c:out value="C/da" />&nbsp;&nbsp;</c:if>
					<c:if test="${beneForm.indirizzo.toponimo.id=='3'}"><c:out value="Largo" />&nbsp;&nbsp;</c:if>
					<c:if test="${beneForm.indirizzo.toponimo.id=='4'}"><c:out value="Piazza" />&nbsp;&nbsp;</c:if>
					<c:out value="${beneForm.indirizzo.denominazione}"/>
				</td>
				<c:if test="${beneForm.indirizzo.civico!=null}">
					<td><form:label path="indirizzo.civico"><spring:message code="civico" /></form:label></td>
					<td colspan="3"><c:out value="${beneForm.indirizzo.civico}"/></td>
				</c:if>
				<c:if test="${beneForm.indirizzo.civico==null}">
					<td colspan="4"></td>
				</c:if>
			</tr>
		</c:if>
		<form:hidden path="indirizzoBeneFin"/>
		<form:hidden path="indirizzo.toponimo.id"/>
		<form:hidden path="indirizzo.denominazione"/>
		<form:hidden path="indirizzo.civico"/>
		<form:hidden path="indirizzo.cap"/>
				
		<c:if test="${beneForm.descrizioneFiliale!=null}">
			<tr>
				<td><form:label path="descrizioneFiliale"><spring:message code="bene.finanziario.descrizionefiliale" /></form:label></td>
				<td colspan="5"><c:out value="${beneForm.descrizioneFiliale}" /></td>
			</tr>
		</c:if>
		<form:hidden path="descrizioneFiliale"/>
		
		<c:if test="${beneForm.stato.descrizione!=null}">
			<tr>
	        	<td><form:label path="stato.id" ><spring:message code="stato"/></form:label></td>
	        	<td colspan="5"><c:out value="${beneForm.stato.descrizione}" /></td>							
			</tr>
		</c:if>
		<form:hidden path="stato.id"/>
		
		<c:if test="${beneForm.comune.descrizione!=null}">
			<tr>
				<td><form:label path="comune.id" ><spring:message code="comune"/></form:label></td>
	        	<td colspan="5"><c:out value="${beneForm.comune.descrizione}" /></td>								
			</tr>	
		</c:if>
		<form:hidden path="comune.id"/>
		
		<tr>
			<td><form:label path="codiceIban"><spring:message code="bene.finanziario.iban" /></form:label></td>
			<td colspan="1">
				<c:if test="${isPolizzaNonBancaria!=null && isPolizzaNonBancaria == false}">
					<c:out value="${beneForm.codiceIban}" />
				</c:if>
			</td>
			<td><form:label path="codiceSwift"><spring:message code="bene.finanziario.swift" /></form:label></td>
			<td colspan="1"><c:out value="${beneForm.codiceSwift}" /></td>
			<form:hidden path="codiceIban"/>
			<form:hidden path="codiceSwift"/>
		</tr>	
		
		<tr>
			<td><form:label path="numeroRegistrazione"><spring:message code="bene.finanziario.numeroregistrazione" /></form:label></td>
			<td colspan="3">
				<c:if test="${isPolizzaNonBancaria!=null && isPolizzaNonBancaria == false}">
					<c:out value="${beneForm.numeroRegistrazione}" />
				</c:if>
			</td>
			<form:hidden path="numeroRegistrazione"/>
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