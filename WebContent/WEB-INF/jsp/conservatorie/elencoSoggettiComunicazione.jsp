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
		<p class="titolo_page"><spring:message code="conservatoria.titolo.soggetti" /></p>
		<spring:url var="azione" value="/conservatorie/soggettiComunicazione.htm" />
		<form:form id="id_formSoggetti" method="post" action="${azione}" commandName="comunicazioneSoggettiForm">
			<br />
            <form:hidden path="stato" />
            <form:hidden path="catAttoId" />
            <form:hidden path="tipoAttoId" />
          	<form:hidden path="numeroProcedimento" />
            <table class="tb_form" cellspacing="0" cellpadding="0" border="0" >
				<tr>
					<td width="15%"><form:label path="numeroComunicazione"> 
							<spring:message code="conservatoria.numeroComunicazione" /></form:label>
					</td>
					<td  width="20%" style="font-weight:bold">
						<c:out value="${comunicazioneSoggettiForm.numeroComunicazione}" />
						<form:hidden path="numeroComunicazione" />
					</td>
					<td colspan="2" style="font-weight:bold"><form:label path="descTipoNota"> 
							<spring:message code="conservatoria.tipo" />
						</form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneSoggettiForm.descTipoNota}" />
						<form:hidden path="descTipoNota" />
					</td>
				</tr>
				<tr>
					<td ><form:label path="descCatAtto"><spring:message code="conservatoria.tipoAtto" /></form:label>
					</td>
					<td colspan="3" >
						<c:out value="${comunicazioneSoggettiForm.descCatAtto}" />
						<form:hidden path="descCatAtto" />
					</td>
				</tr>
                <tr>
                    <td><form:label path="repertorioAnno">
                            <spring:message code="conservatoria.repertorioAnno" />
                        </form:label></td>
                    <td><c:out value="${comunicazioneSoggettiForm.repertorioAnno}" /></td>
                    <form:hidden path="repertorioAnno" />
                    <td><form:label path="numeroRepertorio1">
                            <spring:message code="conservatoria.numero" />
                        </form:label>
                        &nbsp;&nbsp;
                        <c:out value="${comunicazioneSoggettiForm.numeroRepertorio1}" /></td>
                    <form:hidden path="numeroRepertorio1" />
                    <td><form:label path="progressivoNota">
                            <spring:message code="conservatoria.progressivo" />
                        </form:label>
                        &nbsp;&nbsp;
                        <c:out value="${comunicazioneSoggettiForm.progressivoNota}" /></td>
                    <form:hidden path="progressivoNota" />
                </tr>
                <tr>
					<td ><form:label path="titoloDescrizione"> 
							<spring:message code="conservatoria.titoloDescrizione" />
						</form:label></td>
					<td><c:out value="${comunicazioneSoggettiForm.titoloDescrizione}" /></td>
						<form:hidden path="titoloDescrizione" />
					
					<td colspan="2"><form:label path="data">
							<spring:message code="conservatoria.data" />
						</form:label>
						&nbsp;&nbsp;
						<fmt:formatDate value="${comunicazioneSoggettiForm.data}" type="date" pattern="dd/MM/yyyy" /></td> 						
						<form:hidden path="data" />
				</tr>
                <tr>
					<td ><form:label path="conservatoria"> 
							<spring:message code="conservatoria.conservatorie" />
						</form:label>
					</td>
					<td><c:out value="${comunicazioneSoggettiForm.conservatoria}" /></td>
						<form:hidden path="conservatoria" />
					
					<td colspan="2"><form:label path="codiceFiscale">
							<spring:message code="conservatoria.codiceFiscalePU" />
						</form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneSoggettiForm.codiceFiscale}" /></td> 						
						<form:hidden path="codiceFiscale" />
				</tr>
			</table>
			<br />
			<br />
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="conservatoria.elencoSoggetti" /></td>
				</tr>
			</table>
			
			<display:table class="tb_lista" name="${comunicazioneSoggettiForm.listaSoggetti}" 
											uid="listaSoggetti"  pagesize="10" requestURI="${azione}">
																		
          		<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%" >
					<form:radiobutton id="soggettoSelected" path="soggettoSelected" 
						value="${listaSoggetti.progressivo}'^'${listaSoggetti.tipologia}"/></display:column>		
					<display:column class="${listaSoggetti.stileDisplay}" property="progressivo" title="Progressivo" sortable="true" />
					<display:column class="${listaSoggetti.stileDisplay}" property="descTipologia" title="Tipologia" sortable="true" />
					<display:column class="${listaSoggetti.stileDisplay}" property="soggetto" title="Soggetto" sortable="true" />
					<%-- 
					<display:column class="${listaSoggetti.stileDisplay}" property="soggettoSitMp" title="SoggettoSMP" sortable="true" />
					--%>
			</display:table>
		
			<br />
		<logic:notEmpty name="listaSoggetti">
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio" title="Dettaglio"></td>
						<c:if test="${comunicazioneSoggettiForm.stato!='2'}">
							<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica" title="Modifica"></td>
						</c:if>
					</tr>
				</table>
		</logic:notEmpty>
		
		
			<br />
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="conservatoria.listaSoggettiAssociabili" /></td>
				</tr>
			</table>
			
				<display:table class="tb_lista" name="${comunicazioneSoggettiForm.listaSoggettiAssociabili}" 
											uid="listaSoggettiAssociabili"  pagesize="10" requestURI="${azione}">
																		
          		<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%" >
					<form:radiobutton id="soggettoAssociabileSelected" path="soggettoAssociabileSelected" 
				value="${listaSoggettiAssociabili.idSoggetto}'^'${listaSoggettiAssociabili.tipologia}'^'${listaSoggettiAssociabili.versione}"/>
				 </display:column>		
                 <%-- <display:column property="idSoggetto" title="Identificativo" sortable="true" /> --%>
                 <display:column property="descTipologia" title="Tipologia" sortable="true" />
                 <display:column property="soggetto" title="Generalità" sortable="true" />
                 <%-- <display:column property="soggettoSitMp" title="Procedimento" sortable="true" /> --%>
                            
			</display:table>
		
			<br />
		<logic:notEmpty name="listaSoggettiAssociabili">
				<table class="tb_bottoni" align="center">
					<tr>
                        <c:if test="${comunicazioneSoggettiForm.stato != '2'}">
					       	<td class="td_tasto"><input type="submit" value="Associa" name="Associa" title="Associa"></td>
						</c:if>
						<td class="td_tasto"><input type="submit" value="SoggettoSitMp" name="SoggettoSitMp" title="Soggetto SitMp"></td>
					</tr>
				</table>
		</logic:notEmpty>
		
		
		
		</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>