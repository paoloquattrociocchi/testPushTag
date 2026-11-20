<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld" %>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<p class="titolo_page">
			<spring:message code="conservatoria.titolo.modificaTitoloDiritto" />
		</p>
		<spring:url var="azione" value="/conservatorie/titoliDirittoComunicazione.htm" />
		<form:form id="id_formTitoli" method="post" action="${azione}" commandName="comunicazioneBeniForm">
		<form:hidden id="beneSelected" path="beneSelected"/>
		<form:hidden id="numeroComunicazione" path="numeroComunicazione"/>
		<form:hidden id="beneSitMp" path="beneSitMp"/>
		<form:hidden id="soggettoSitMp" path="soggettoSitMp"/>
		<form:hidden id="idTipoInt" path="idTipoInt"/>
		<form:hidden id="soggettoSelected" path="soggettoSelected"/>
		<form:hidden id="titoloDirittoSelected" path="titoloDirittoSelected"/>
		<form:hidden id="descBene" path="descBene"/>
		<form:hidden id="descComuneBene" path="descComuneBene"/>
		<form:hidden id="codiceCatastale1" path="codiceCatastale1"/>
		<form:hidden id="codiceCatastale2" path="codiceCatastale2"/>
		<form:hidden id="tipoSogg" path="tipoSogg"/>
		<form:hidden id="cognome" path="cognome"/>
		<form:hidden id="nome" path="nome"/>
		<form:hidden id="denominazione" path="denominazione"/>
		<form:hidden id="descTipoInt" path="descTipoInt"/>
		
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0" >
					<tr>
						<td class="td_titolo"><form:label path="numeroComunicazione"><spring:message code="conservatoria.numeroComunicazione" /></form:label></td>
						<td style="font-weight: bold" class="td_titolo"><c:out value="${comunicazioneBeniForm.numeroComunicazione}" /></td>
						<td style="font-weight: bold" class="td_titolo" colspan="2">
							<form:label path="descTipoNota"><spring:message code="conservatoria.tipo" /></form:label>
							&nbsp;&nbsp;
							<c:out value="${comunicazioneBeniForm.descTipoNota}" />
						</td>
					</tr>
					<tr>
						<td class="td_titolo"><form:label path="beneSitMp"><spring:message code="conservatoria.beniSIT" /></form:label></td>
						<td style="font-weight: bold" class="td_titolo"><c:out value="${comunicazioneBeniForm.beneSitMp}" /> - <c:out value="${comunicazioneBeniForm.descBene}" /></td>
						<td class="td_titolo">&nbsp;</td>
						<td class="td_titolo">&nbsp;</td>
					</tr>
					<tr>
						<td class="td_titolo"><form:label path="comune.id" ><spring:message code="comune"/></form:label></td>					
						<td style="font-weight: bold" class="td_titolo"><c:out value="${comunicazioneBeniForm.descComuneBene}" /></td>
						<td style="font-weight: bold" class="td_titolo" colspan="2">
							<form:label path="codiceCatastale1"><spring:message code="bene.immobile.codicecatastale" /></form:label>
							&nbsp;&nbsp;
							<c:out value="${comunicazioneBeniForm.codiceCatastale1}" />
							<c:out value="${comunicazioneBeniForm.codiceCatastale2}" />
						</td> 
					</tr>
				</table>
				<br />
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td class="td_titolo">
							<spring:message code="conservatoria.titolo.soggetto" />
						</td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0" >
				<!-- <tr>
						<td><form:label path="soggettoSitMp"><spring:message code="conservatoria.soggettoSIT" /></form:label></td>
						<td><c:out value="${comunicazioneBeniForm.soggettoSitMp}" /></td>
						<td></td><td></td>
					</tr> -->
					<c:if test="${comunicazioneBeniForm.tipoSogg =='F'}">
					<tr>
						<td width="10%"><form:label path="cognome"><spring:message code="conservatoria.cognome" /></form:label></td>
						<td width="20%"><c:out value="${comunicazioneBeniForm.cognome}" /></td>
						<td width="10%"><form:label path="nome"><spring:message code="conservatoria.nome" /></form:label></td>
						<td><c:out value="${comunicazioneBeniForm.nome}" /></td>
					</tr>
					</c:if>
					<c:if test="${comunicazioneBeniForm.tipoSogg =='N'}">
					<tr>
						<td width="10%"><form:label path="denominazione"><spring:message code="conservatoria.denominazione" /></form:label></td>
						<td width="20%"><c:out value="${comunicazioneBeniForm.denominazione}" /></td>
					</tr>
					</c:if>
				</table>
				<br />
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
					<td class="td_titolo">
							<spring:message code="conservatoria.titolo.interesse" />
						</td>
					</tr>
				</table>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0" >
					<tr>
						<td width="10%"><form:label path="descTipoInt"><spring:message code="conservatoria.tipoInteresse" /></form:label></td>
						<td><c:out value="${comunicazioneBeniForm.descTipoInt}" /></td>
					</tr>
					<tr>
						<td><form:label path="numeQuotaInt"><spring:message code="conservatoria.titolo.quota" /></form:label></td>
						<td>
                            <!-- 
                            <form:input path="numeQuotaInt" id="numeQuotaInt" maxlength="6" size="6" />,
                            <form:input path="numeQuotaDec" id="numeQuotaDec" maxlength="3" size="3" />  -->
                            <form:input path="numeQuota" id="numeQuota" maxlength="10" size="12" />/<form:input 
                                    path="numeQuotaDen" id="numeQuotaDen" maxlength="6" size="6" /></td>
						<td></td>
						<td></td>
					</tr>
				</table>
			
				<table class="tb_bottoni" align="center">
	                 	<tr>
						<c:if test="${comunicazioneBeniForm.tipoOperazione =='NotaInviata'}">
							<td class="td_tasto"><input type="submit" name="Indietro" title="Indietro" value="Indietro"/></td>
						</c:if>
						<c:if test="${comunicazioneBeniForm.tipoOperazione =='Modifica'}">
							<td class="td_tasto"><input type="submit" name="ConfermaModifica" title="Conferma" value="Conferma" /></td>
	                  		<td class="td_tasto"><input type="submit" name="Cancella" title="Cancella" value="Cancella" onClick="return popUpConferma()" class="nowait"/></td>	
	                  		<td class="td_tasto"><input type="submit" name="IndietroListaTitoli" title="Indietro" value="Indietro"/></td>	                   		
                        </c:if>
                        <c:if test="${comunicazioneBeniForm.tipoOperazione =='hoCancellato'}">
							<td class="td_tasto"><input type="submit" name="IndietroListaTitoli" title="Indietro" value="Indietro"/></td>	
						</c:if>
                        </tr>
					</table>
					
			<c:if test="${comunicazioneBeniForm.tipoOperazione=='hoCancellato'}">

				<script type="text/javascript">
					$(document).ready(function() {
						disabilitaCampi();
					});
				</script>
			</c:if>
			<script type="text/javascript">
				function popUpConferma() {
					if (confirm("Confermi l'operazione ?"))
						return true;
					else
						return false;
				}
			</script>
			<script type="text/javascript">
				function disabilitaCampi() {

					document.getElementById('numeQuotaInt').disabled = true;
					document.getElementById('numeQuotaDec').disabled = true;
					document.getElementById('numeQuotaDen').disabled = true;
				}
			</script>

		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
