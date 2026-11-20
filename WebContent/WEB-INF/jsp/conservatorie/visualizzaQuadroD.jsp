<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">

		<p class="titolo_page">
			<spring:message code="conservatoria.titolo.quadroD" />
		</p>
		<c:if test="${comunicazioneQuadroDForm.tipoInf=='0' }">
			<spring:url var="azione"
				value="/conservatorie/quadroDConservatorie.htm" />
		</c:if>
		<c:if test="${comunicazioneQuadroDForm.tipoInf=='1' }">
			<spring:url var="azione" value="/conservatorie/quadroDCatasto.htm" />
		</c:if>
		<form:form id="id_formRicerca" method="post" action="${azione}"
			commandName="comunicazioneQuadroDForm">

			<form:hidden path="tipoInf" />
			<form:hidden path="numero" />
			<form:hidden path="stato" />
			<form:hidden path="quadroDPresente" />
			<form:hidden path="oldQuadroD" />

			<form:hidden path="descTipoNota" />
			<form:hidden path="descCatAtto" />
			<form:hidden path="repertorioAnno" />
			<form:hidden path="numeroRepertorio1" />
			<form:hidden path="progressivo" />
			<form:hidden path="conservatoria" />
			<form:hidden path="codiceFiscale" />
			<form:hidden path="titoloDescrizione" />
			<form:hidden path="data" />
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="13%">
						<form:label path="numero"><spring:message code="conservatoria.numeroComunicazione" /></form:label>
					</td>
					<td style="font-weight: bold">
						<c:out value="${comunicazioneQuadroDForm.numero}" />
					</td>
					<td colspan="2">
						<form:label path="descTipoNota"><spring:message code="conservatoria.tipo" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneQuadroDForm.descTipoNota}" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<form:label path="descCatAtto"><spring:message code="conservatoria.tipoAtto" /></form:label>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<c:out value="${comunicazioneQuadroDForm.descCatAtto}" />
					</td>
				</tr>
				<tr>
					<td>
						<form:label path="repertorioAnno"><spring:message code="conservatoria.repertorioAnno" /></form:label>
					</td>
					<td><c:out value="${comunicazioneQuadroDForm.repertorioAnno}" /></td>
					<td>
						<form:label path="numeroRepertorio1"><spring:message code="conservatoria.numero" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneQuadroDForm.numeroRepertorio1}" /></td>
					<td>
						<form:label path="progressivo"><spring:message code="conservatoria.progressivo" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneQuadroDForm.progressivo}" /></td>
				</tr>
				<tr>
					<td><form:label path="titoloDescrizione"><spring:message code="conservatoria.titoloDescrizione" /></form:label></td>
					<td><c:out value="${comunicazioneQuadroDForm.titoloDescrizione}" /></td>
					<td colspan="2"><form:label path="data">
							<spring:message code="conservatoria.data" />
						</form:label>
						&nbsp;&nbsp;
						<fmt:formatDate value="${comunicazioneQuadroDForm.data}" type="date" pattern="dd/MM/yyyy" />
					</td>
				</tr>
				<tr>
					<td><form:label path="conservatoria"><spring:message code="conservatoria.conservatorie" /></form:label></td>
					<td><c:out value="${comunicazioneQuadroDForm.conservatoria}" /></td>
					<td colspan="2">
						<form:label path="codiceFiscale"><spring:message code="conservatoria.codiceFiscalePU" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneQuadroDForm.codiceFiscale}" />
					</td>
				</tr>
				<tr>
					<td><form:label path="quadroD">
							<c:if test="${comunicazioneQuadroDForm.tipoInf=='0' }">
								<spring:message code="conservatoria.quadroDConservatorie" />
							</c:if>
							<c:if test="${comunicazioneQuadroDForm.tipoInf=='1' }">
								<spring:message code="conservatoria.quadroDCatasto" />
							</c:if>
						</form:label></td>
					<td colspan="5"><form:textarea path="quadroD" rows="8"
							cssStyle="width:98%" /></td>
				</tr>
			</table>

			<c:if
				test="${comunicazioneQuadroDForm.stato=='0' || comunicazioneQuadroDForm.stato=='1' }">
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" value="Conferma"
							name="Conferma" title="Conferma"></td>
						<c:if test="${ comunicazioneQuadroDForm.quadroDPresente }">
							<td class="td_tasto"><input type="submit" value="Cancella"
								title="Cancella" id="Cancella" name="Cancella"
								onClick="return popUpConferma()" class="nowait" />

							</td>
						</c:if>
					</tr>
				</table>
			</c:if>
			<script type="text/javascript">
				//	$("#id_formRicerca").attr("action", contextPath+"/conservatorie/quadroDCatasto.htm");
				//	$("#id_formRicerca").submit();

				function popUpConferma() 
				{
					if (confirm("Confermi l'operazione ?")) 
						return true;
					else 
						return false;
				}
			</script>
		</form:form>

		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>

	</tiles:putAttribute>
</tiles:insertDefinition>

