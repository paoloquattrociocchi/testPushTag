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
		<spring:message code="conservatoria.titolo.ricercaTitoliDiritto" />		
	</p>
	<spring:url var="azione" value="/conservatorie/titoliDirittoComunicazione.htm" />
	<form:form id="id_formTitoliDiritto" method="post" action="${azione}" commandName="comunicazioneBeniForm">
	<form:hidden id="beneSelected" path="beneSelected"/>
	<form:hidden id="numeroComunicazione" path="numeroComunicazione"/>
	<form:hidden id="descTipoNota" path="descTipoNota"/>
	<form:hidden id="beneSitMp" path="beneSitMp"/>
	<form:hidden id="descBene" path="descBene"/>
	<form:hidden id="descComuneBene" path="descComuneBene"/>
	<form:hidden id="codiceCatastale1" path="codiceCatastale1"/>
	<form:hidden id="codiceCatastale2" path="codiceCatastale2"/>
	
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
			<br />
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="conservatoria.elencoTitoliDiritto" /></td>
				</tr>
			</table>
				
			<display:table class="tb_lista" uid="listaTitoliDiritto" name="${comunicazioneBeniForm.listaTitoliDiritto}" pagesize="10" requestURI="${azione}">
          		<display:column sortable="false" style="text-align: center; width: 5%" titleKey="seleziona">
					<form:radiobutton id="titoloDirittoSelected" path="titoloDirittoSelected" value="${listaTitoliDiritto.PROG}'^'${listaTitoliDiritto.IDTIPOINT}" />
				</display:column>		
					<display:column property="PROG" title="Progressivo" sortable="true" />
					<display:column property="SOGGETTO" title="Soggetto" sortable="true" />
					<display:column property="PROGRIGA" title="Prog.Riga" sortable="true" />
					<display:column property="DESCTIPOINT" title="Tipo interesse" sortable="true" />
					<display:column property="NUMEQUOTA" title="Quota" sortable="true" />					


			</display:table>
				
				
			
				<table class="tb_bottoni" align="center">
	            	<tr>
	            	<c:if test="${comunicazioneBeniForm.stato!='2'}">
	            		<td class="td_tasto"><input type="submit" value="Nuovo" name="Nuovo" title="Nuovo"></td>
						<logic:present name="listaTitoliDiritto">	
							<td class="td_tasto"><input type="submit" value="Modifica" name="Modifica" title="Modifica"></td>
	                	</logic:present>  
	                </c:if>	
	                  	<td class="td_tasto"><input type="submit" name="IndietroListaBeni" title="Indietro" value="Indietro"/></td>	                   		
                    </tr>
				</table>
			
			
	</form:form>
	</tiles:putAttribute>
</tiles:insertDefinition>