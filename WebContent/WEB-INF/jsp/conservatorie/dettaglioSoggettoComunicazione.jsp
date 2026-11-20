<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">

		<p class="titolo_page"> <spring:message code="conservatoria.titolo.dettaglioSoggetto" /> </p>

		<spring:url var="azione" value="/conservatorie/soggettiComunicazione.htm" />
			<form:form id="id_formSoggetti" method="post" action="${azione}" commandName="comunicazioneSoggettiForm">
			
            <form:hidden path="numeroComunicazione" />
            <form:hidden path="stato" />
            <form:hidden path="descTipoNota" />
            <form:hidden path="flgTipoSoggetto" />
            
            <form:hidden path="soggettoDto.progressivo" />                     
            <form:hidden path="soggettoDto.tipologia" />                     
            <form:hidden path="soggettoDto.soggettoSitMp" />                     
            <form:hidden path="soggettoDto.flagErrore" />                     

            <form:hidden path="soggettoDto.cognome" />
            <form:hidden path="soggettoDto.nome" />
            <form:hidden path="soggettoDto.flgSesso" />
            <form:hidden path="soggettoDto.dataNascita" />
            <form:hidden path="soggettoDto.comuneNascita" />
            <form:hidden path="soggettoDto.statoNascita" />
            <form:hidden path="soggettoDto.codiceFiscalePF" />
            <form:hidden path="soggettoDto.regime" />
            <form:hidden path="soggettoDto.descRegime" />
            <form:hidden path="soggettoDto.codProgrSoggC" />
            <form:hidden path="soggettoDto.codiceComuneNasc" />

            <form:hidden path="soggettoDto.denominazione" />
            <form:hidden path="soggettoDto.statoSede" />
            <form:hidden path="soggettoDto.comuneSede" />
            <form:hidden path="soggettoDto.partitaIva" />
            <form:hidden path="soggettoDto.codiceComuneSede" />
            
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td style="font-weight: bold" class="td_titolo">
						<form:label path="numeroComunicazione"><spring:message code="conservatoria.numeroComunicazione" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneSoggettiForm.numeroComunicazione}" />
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<form:label path="descTipoNota" ><spring:message code="conservatoria.tipo" /></form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneSoggettiForm.descTipoNota}" />
					</td>
				</tr>
				<%-- 
				<tr>
					<td class="td_titolo">
   						<form:label path="soggettoDto.soggettoSitMp"><spring:message code="conservatoria.soggettoSIT" /> </form:label>
   					</td>
                    <td colspan="3" style="font-weight: bold" class="td_titolo">
                    	<c:out value="${comunicazioneSoggettiForm.soggettoDto.soggettoSitMp}" />
                    </td>
				</tr>
				--%>
			</table>
			</br>
			</br>
			
    <%-- INIZIO DETTAGLIO PERSONA FISICA ------------------------------------------------     --%>
    <c:if test="${comunicazioneSoggettiForm.flgTipoSoggetto == 'F'}">
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
		           <tr>
		              <td class="td_titolo"><spring:message code="conservatoria.personaFisica" /></td>
		           </tr>
       		</table>
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td width="10%"><form:label path="soggettoDto.cognome"> 
						<spring:message code="conservatoria.cognome" /> </form:label></td>
					<td width="20%"><c:out value="${comunicazioneSoggettiForm.soggettoDto.cognome}" /></td>
					<td width="10%"><form:label path="soggettoDto.nome"> 
						<spring:message code="conservatoria.nome" /> </form:label></td>
					<td><c:out value="${comunicazioneSoggettiForm.soggettoDto.nome}" /></td>
				</tr>
				<tr>
					<td><form:label path="soggettoDto.flgSesso"> 
						<spring:message code="conservatoria.sesso" /> </form:label></td>
					<td><c:out value="${comunicazioneSoggettiForm.soggettoDto.flgSesso}" /></td>
					<td><form:label path="soggettoDto.dataNascita"> 
						<spring:message code="dataNascita" /> </form:label></td>
					<td>				
						<fmt:formatDate value="${comunicazioneSoggettiForm.soggettoDto.dataNascita}" type="date" pattern="dd/MM/yyyy" />
					</td>
				</tr>
				<tr>
					<td><form:label path="soggettoDto.statoNascita"> 
						<spring:message code="conservatoria.statoNascita" /> </form:label></td>
					<td>${comunicazioneSoggettiForm.soggettoDto.statoNascita}</td>
								
					<td><form:label path="soggettoDto.comuneNascita"> 
						<spring:message code="conservatoria.comuneNascita" /> </form:label></td>

                    <c:if test="${comunicazioneSoggettiForm.soggettoDto.provNascita != null}">
        <td>${comunicazioneSoggettiForm.soggettoDto.comuneNascita} (${comunicazioneSoggettiForm.soggettoDto.provNascita})</td>
                    </c:if>                    
                    <c:if test="${comunicazioneSoggettiForm.soggettoDto.provNascita == null}">
        <td>${comunicazioneSoggettiForm.soggettoDto.comuneNascita}</td>
                    </c:if>                    
				</tr>
				<tr>
					<td><form:label path="soggettoDto.codiceFiscalePF"> 
						<spring:message code="conservatoria.codiceFiscale" /> </form:label></td>
					<td colspan="3" >${comunicazioneSoggettiForm.soggettoDto.codiceFiscalePF}</td>
				</tr>
   				<tr>
					<td><form:label path="soggettoDto.descRegime"> 
						<spring:message code="conservatoria.regime" /> </form:label></td>
					<td>${comunicazioneSoggettiForm.soggettoDto.descRegime}</td>
								
					<td><form:label path="soggettoDto.codProgrSoggC"> 
						<spring:message code="conservatoria.con" /> </form:label></td>
					<td>${comunicazioneSoggettiForm.soggettoDto.descSoggCon}</td>
				</tr>
			</table>
    </c:if>  <%-- FINE DETTAGLIO PERSONA FISICA -----------------------------------     --%>
	
	<%-- INIZIO DETTAGLIO PERSONA GIURIDICA--------------------------------------------  	--%>			
    <c:if test="${comunicazioneSoggettiForm.flgTipoSoggetto == 'N'}">
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
		           <tr>
		              <td class="td_titolo"><spring:message code="conservatoria.personaGiuridica" /></td>
		           </tr>
       		</table>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
                <tr>
                    <td width="10%"><form:label path="soggettoDto.denominazione"> 
                        <spring:message code="conservatoria.denominazione" /> </form:label></td>
                    <td colspan="3">${comunicazioneSoggettiForm.soggettoDto.denominazione}</td>
                </tr>   
                <tr>
                    <td><form:label path="soggettoDto.statoSede"> 
                        <spring:message code="conservatoria.statoSede" /> </form:label></td>
                    <td width="20%">${comunicazioneSoggettiForm.soggettoDto.statoSede}</td>
                    <td width="10%"><form:label path="soggettoDto.comuneSede"> 
                        <spring:message code="conservatoria.comuneSede" /> </form:label></td>
                    <c:if test="${comunicazioneSoggettiForm.soggettoDto.provSede != null}">
                        <td>${comunicazioneSoggettiForm.soggettoDto.comuneSede} (${comunicazioneSoggettiForm.soggettoDto.provSede})</td>
                    </c:if>
                    <c:if test="${comunicazioneSoggettiForm.soggettoDto.provSede == null}">
                        <td>${comunicazioneSoggettiForm.soggettoDto.comuneSede}</td>
                    </c:if>
                </tr>   
                <tr>
                    <td><form:label path="soggettoDto.partitaIva"> 
                        <spring:message code="partitaIva" /> </form:label></td>
                    <td colspan="3">${comunicazioneSoggettiForm.soggettoDto.partitaIva}</td>
                </tr>   
			</table>		
	</c:if>  <%-- FINE DETTAGLIO PERSONA GIURIDICA---------------------------------------     --%>
				
			</br>
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Indietro"
						name="Indietro" title="Indietro"></td>
				</tr>
			</table>
			
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>