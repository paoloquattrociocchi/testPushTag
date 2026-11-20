<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<p class="titolo_page">
	<c:if test="${procedimento.tipoProcedimento != null && procedimento.tipoProcedimento.id == 8 }">
		<spring:message code="procedimento.titoloAggravamento" />
	</c:if>
	<c:if test="${procedimento.tipoProcedimento != null && procedimento.tipoProcedimento.id != 8 }">
		<spring:message code="procedimento.titolo" />
	</c:if>
</p>
<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td width="15%"><form:label path="annoNumeroProcedimento">
				<spring:message code="procedimento" />
			</form:label></td>
		<td width="35%"><font size="4"><b><c:out value="${procedimento.annoNumeroProcedimento}" /></b></font></td>
		<td width="15%"><form:label path="dataIscrizione">
				<spring:message code="procedimento.dataIscrizione" />
			</form:label></td>
		<td width="35%"><fmt:formatDate value="${procedimento.dataApertura}" type="date" pattern="dd/MM/yyyy" /></td>
	</tr>
	<c:if test="${procedimento.numeroPadre!=null && procedimento.annoPadre!=null}">
		<tr>
			<td><form:label path="annoNumeroPadre">
					<spring:message code="procedimento.appellativoProcedimentoPadre" />
				</form:label></td>
			<td colspan="3"><c:out value="${procedimento.annoNumeroPadre}" /></td>
		</tr>
	</c:if>
	<tr>
		<td><form:label path="procedente">
				<spring:message code="procedimento.procedente" />
			</form:label></td>
		<td colspan="3"><c:out value="${procedimento.procedente.descrizione}" /></td>
	</tr>
	<tr>
		<td><form:label path="tipoProcedimento">
				<spring:message code="procedimento.tipo" />
			</form:label></td>
		<td><c:out value="${procedimento.tipoProcedimento.descrizione}" /></td>
		<td><form:label path="statoProcedimento">
				<spring:message code="procedimento.stato" />
			</form:label></td>
		<td><c:out value="${procedimento.statoProcedimento.descrizione}" /></td>
		<!--<td colspan="3"><c:out value="${procedimento.procedente.id}" /></td>-->
	</tr>
</table>
<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td width="15%"><form:label path="tipologiaPrevenzione">
				<spring:message code="procedimento.tipologiaPrevenzione" />
			</form:label></td>
		<td width="85%"><c:if test="${procedimento.antimafia==true}">
				<form:hidden path="antimafia" />
				<c:out value="ANTIMAFIA" />
			</c:if> <c:if test="${procedimento.antimafia==false}">
				<c:if test="${procedimento.antiterrorismo==true}">
					<form:hidden path="antiterrorismo" />
					<c:out value="ANTITERRORISMO" />
				</c:if>
				<c:if test="${procedimento.antiterrorismo==false}">
					<c:if test="${procedimento.ordinaria==true}">
						<form:hidden path="ordinaria" />
						<c:out value="ORDINARIA" />
					</c:if>
				</c:if>
			</c:if></td>
	</tr>
	<logic:notEmpty name="procedimentiFigliStralcio">
		<tr>
			<td width="15%"><form:label path="tipologiaPrevenzione">
					<spring:message code="stralcio.procedimentiFigliStralcio" />
				</form:label></td>
			<td width="85%">${procedimentiFigliStralcio}</td>
		</tr>
	</logic:notEmpty>
	<logic:notEmpty name="procedimentiPadreStralcio">
		<tr>
			<td width="15%"><form:label path="tipologiaPrevenzione">
					<spring:message code="stralcio.procedimentiPadreStralcio" />
				</form:label></td>
			<td width="85%">${procedimentiPadreStralcio.numeAnnoProc} / ${procedimentiPadreStralcio.numeProc}</td>
		</tr>
	</logic:notEmpty>
	
	<logic:notEmpty name="riunitoAlNumero">
		<tr>
			<td width="15%"><form:label path="tipologiaPrevenzione">
					<spring:message code="riunione.riunitoAlNumero" />
				</form:label></td>
			<td width="85%">${riunitoAlNumero.numeAnnoProc} / ${riunitoAlNumero.numeProc}</td>
		</tr>
	</logic:notEmpty>
	<logic:notEmpty name="riunisceIlNumero">
		<tr>
			<td width="15%"><form:label path="tipologiaPrevenzione">
					<spring:message code="riunione.riunisceIlNumero" />
				</form:label></td>
			<td width="85%">${riunisceIlNumero}</td>
		</tr>
	</logic:notEmpty>
</table>