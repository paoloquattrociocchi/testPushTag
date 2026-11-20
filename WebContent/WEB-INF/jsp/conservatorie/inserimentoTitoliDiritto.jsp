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
		<spring:message code="conservatoria.titolo.inserimentoTitoliDiritto" />		
	</p>
	<spring:url var="azione" value="/conservatorie/inserimentoTitoliDiritto.htm" />
	<form:form id="id_formInserimentoTitoliDiritto" method="post" action="${azione}" commandName="comunicazioneBeniForm">
	     
            
            
    <form:hidden id="beneSelected" path="beneSelected"/>
	<form:hidden id="numeroComunicazione" path="numeroComunicazione"/>
	<form:hidden id="descTipoNota" path="descTipoNota"/>
	<form:hidden id="beneSitMp" path="beneSitMp"/>
	<form:hidden id="descBene" path="descBene"/>
	<form:hidden id="descComuneBene" path="descComuneBene"/>
	<form:hidden id="codiceCatastale1" path="codiceCatastale1"/>
	<form:hidden id="codiceCatastale2" path="codiceCatastale2"/>
    <form:hidden id="listaTipiInteresse" path="listaTipiInteresse" />   
   
            	     	
	     			
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
						<td class="td_titolo"></td>
						<td class="td_titolo"></td>
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
			
		<%-- INIZIO DETTAGLIO SOGGETTI -------------------------------------------------------------------%>		
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="conservatoria.elencoSoggetti" /></td>
				</tr>
			</table>
			
				<display:table class="tb_lista" name="${comunicazioneBeniForm.listaSoggetti}" 
											uid="listaSoggetti"  pagesize="10" requestURI="${azione}">
																		
          		<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%" >
					<form:radiobutton id="soggettoSelected" path="soggettoSelected" 
						value="${listaSoggetti.progressivo}"/> </display:column>		
                    <display:column class="${listaSoggetti.stileDisplay}" property="progressivo" title="Progressivo" sortable="true" />
                    <display:column class="${listaSoggetti.stileDisplay}" property="descTipologia" title="Tipologia" sortable="true" />
                    <display:column class="${listaSoggetti.stileDisplay}" property="soggetto" title="Soggetto" sortable="true" />
                    <%-- 
                    <display:column class="${listaSoggetti.stileDisplay}" property="soggettoSitMp" title="SoggettoSMP" sortable="true" />
                    --%>
			</display:table>
			<br />
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="conservatoria.titolo.datiTipoInteresse" /></td>
				</tr>
			</table>
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
						<td width="10%"><form:label path="listaTipiInteresse"><spring:message code="conservatoria.titolo.tipoInteresse"/></form:label></td>
			        	<td>		        	   
			        		<form:select path="listaTipiInteresse.id" id="tipoInteresse" size="1">
								<form:option value="-1" label="--- Seleziona Tipo Interesse ---" />
								<form:options items="${listaTipiInteresse}" itemValue="id" itemLabel="descrizione" />
							</form:select>
			        	</td>
			        	
			   </tr>
			   <tr>
						<td><form:label path="numeQuota" cssClass="required" >
                                <spring:message code="conservatoria.titolo.quota" /></form:label></td>
                        <td>
                            <form:input path="numeQuota" id="numeQuota" maxlength="10" size="12" />/<form:input 
                                    path="numeQuotaDen" id="numeQuotaDen" maxlength="6" size="6" /></td>
			 </tr>
			 </table>
			 
			 <table class="tb_bottoni" align="center">
	            	<tr>
	            	  <c:if test="${comunicazioneBeniForm.tipoOperazione!='hoInserito'}">
	            		<td class="td_tasto"><input type="submit" name="ConfermaInserimento" title="Conferma"   value="Conferma"></td>
	            	 </c:if>  
	                  	<td class="td_tasto"><input type="submit" name="IndietroRicerca" title="IndietroRicerca" value="Indietro"/></td>	                   		
                    </tr>
				</table>
				
				<c:if test="${comunicazioneBeniForm.tipoOperazione=='hoInserito'}">
				
				<script type="text/javascript">
							$(document).ready(function() {
								disabilitaCampi();
							});
				</script>
				</c:if>
	</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>
<script type="text/javascript">
	function disabilitaCampi() {

		document.getElementById('numeQuota').disabled = true;
//		document.getElementById('numeratoreQuotaDecimale').disabled = true;
		document.getElementById('numeQuotaDen').disabled = true;
		document.getElementById('tipoInteresse').disabled = true;

	}
</script>