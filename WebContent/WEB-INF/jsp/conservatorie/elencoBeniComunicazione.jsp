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
			<spring:message code="conservatoria.titolo.beniImmobili" />
		</p>

		<spring:url var="azione" value="/conservatorie/beniComunicazione.htm" />

		<form:form id="id_formBeni" method="post" action="${azione}" commandName="comunicazioneBeniForm">
			<br />
			
			<form:hidden id="conservatoriaId" path="conservatoriaId"/>
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0" >
				<tr>
					<td width="15%"><form:label path="numeroComunicazione"> 
							<spring:message code="conservatoria.numeroComunicazione" />
						</form:label>
					</td>
					<td  width="20%" style="font-weight:bold">
						<c:out value="${comunicazioneBeniForm.numeroComunicazione}" />
						<form:hidden path="numeroComunicazione" />
					</td>
					<td colspan="2" style="font-weight:bold"><form:label path="descTipoNota"> 
							<spring:message code="conservatoria.tipo" />
						</form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneBeniForm.descTipoNota}" />
						<form:hidden path="descTipoNota" />
					</td>
				</tr>
				<tr>
					<td ><form:label path="descCatAtto"> 
							<spring:message code="conservatoria.tipoAtto" />
						</form:label>
					</td>
					<td colspan="3" >
						<c:out value="${comunicazioneBeniForm.descCatAtto}" />
					</td>
				</tr>
				<tr>
					<td><form:label path="repertorioAnno"> 
							<spring:message code="conservatoria.repertorioAnno" />
						</form:label>
					</td>
					<td>
						<c:out value="${comunicazioneBeniForm.repertorioAnno}" />
					</td>
					<td><form:label path="numeroRepertorio1"> 
							<spring:message code="conservatoria.numero" />
						</form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneBeniForm.numeroRepertorio1}" />
					</td>
					<td><form:label path="progressivoNota"> 
							<spring:message code="conservatoria.progressivo" />
						</form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneBeniForm.progressivoNota}" />
					</td>
				</tr>
				<tr>
					<td ><form:label path="titoloDescrizione"> 
							<spring:message code="conservatoria.titoloDescrizione" />
						</form:label></td>
					<td><c:out value="${comunicazioneBeniForm.titoloDescrizione}" /></td>
					
					<td colspan="2"><form:label path="data">
							<spring:message code="conservatoria.data" />
						</form:label>
						&nbsp;&nbsp;
						<fmt:formatDate value="${comunicazioneBeniForm.data}" type="date" pattern="dd/MM/yyyy" />
					</td> 						
				</tr>
				<tr>
					<td ><form:label path="conservatoria"> 
							<spring:message code="conservatoria.conservatorie" />
						</form:label>
					</td>
					<td><c:out value="${comunicazioneBeniForm.conservatoria}" /></td>
					
					<td colspan="2"><form:label path="codiceFiscale">
							<spring:message code="conservatoria.codiceFiscalePU" />
						</form:label>
						&nbsp;&nbsp;
						<c:out value="${comunicazioneBeniForm.codiceFiscale}" />
					</td> 						
				</tr>
			</table>
			<br />
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="conservatoria.elencoBeni" /></td>
				</tr>
			</table>
			
			<display:table class="tb_lista" uid="listaBeni" name="${comunicazioneBeniForm.listaBeni}" pagesize="10" requestURI="${azione}">
	          		<display:column sortable="false" style="text-align: center; width: 5%" titleKey="seleziona">
						<form:radiobutton id="beneSelected" path="beneSelected" value="${listaBeni.PROGRESSIVO}" />
					</display:column>		
						<display:column class="${listaBeni.stileDisplay}" property="PROGRESSIVO" title="Progressivo" sortable="true" />
						<display:column class="${listaBeni.stileDisplay}" property="PROGUNEG" title="Progr Uneg" sortable="true" />
						<display:column class="${listaBeni.stileDisplay}" property="progUnegBene" title="Progr Uneg Bene" sortable="true" />
						<display:column class="${listaBeni.stileDisplay}" property="descrizione" title="Descrizione" sortable="true" />
						<display:column class="${listaBeni.stileDisplay}" property="catasto" title="Catasto" sortable="true" />					
						<display:column class="${listaBeni.stileDisplay}" property="comune" title="Comune" sortable="true" />	
						<display:column class="${listaBeni.stileDisplay}" property="beneSMP" title="Identificativo bene" sortable="true" />	
			</display:table>
			<br />
			<logic:present name="listaBeni">	
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio" title="Dettaglio"></td>
						<c:if test="${comunicazioneBeniForm.stato!='2'}">
							<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica" title="Modifica"></td>
						</c:if>
						<td class="td_tasto"><input type="submit" value="Titoli di diritto" name="TitoliDiritto" title="Titoli di diritto"></td>
					</tr>
				</table>
			</logic:present>
				
			<br />	
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="conservatoria.elencoBeniAssociabili" /></td>
				</tr>
			</table>
				
			<display:table class="tb_lista" uid="listaBeniAssociabili" name="${comunicazioneBeniForm.listaBeniAssociabili}" pagesize="10" requestURI="${azione}">
          		<display:column sortable="false" style="text-align: center; width: 5%" titleKey="seleziona">
					<form:radiobutton id="beneAssociabileSelected" path="beneAssociabileSelected" value="${listaBeniAssociabili.CODI_K_ID_BENE}" />
				</display:column>		
					<display:column property="CODI_K_ID_BENE" title="Identificativo" sortable="true" />
					<display:column property="DESCR_BENE" title="Descrizione" sortable="true" />
					<display:column property="DESCR_CATASTO" title="Descrizione catasto" sortable="true" />
					<display:column property="DESC_CATASTO" title="Codice catastale" sortable="true" />
					<display:column property="DESC_COMUNE" title="Comune catasto" sortable="true" />	
					<%-- 				
						<display:column property="CODI_K_ID_PROC" title="Procedimento" sortable="true" />
					--%>	
			</display:table>
			<br />
			<logic:present name="listaBeniAssociabili">
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" value="Associa" name="Associa" title="Associa"></td>
						<td class="td_tasto"><input type="submit" value="Bene SitMP" name="BeneSitMP" title="Bene SitMP"></td>
					</tr>
				</table>
			</logic:present>
			
		</form:form>

	</tiles:putAttribute>
</tiles:insertDefinition>