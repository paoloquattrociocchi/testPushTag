<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		
			<p class="titolo_page">
				<spring:message code="amministrazione.catCatastali.ricerca.titolo" />		
			</p>
			<spring:url var="azione" value="/funzioniAmministrazione/visualizzaRicercaCatCatastale.htm" />
			<form:form id="id_formRicerca" method="post" action="${azione}" commandName="categoriaCatastaleForm">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td><form:label path="descCategoriaCat"><spring:message code="amministrazione.catCatastali.descrizione"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="descCategoriaCat" maxlength="80" size="80"/>
			        		<form:errors path="descCategoriaCat" class="testoRosso"/>
			        	</td>
			        </tr>
			        <tr>
			        	<td><form:label path="descBreveCategoriaCat"><spring:message code="amministrazione.catCatastali.descrbreve"/></form:label></td>
			        	<td>
			        		<form:input path="descBreveCategoriaCat" maxlength="4" size="4"/>
			        		<form:errors path="descBreveCategoriaCat" class="testoRosso"/>
						</td>
			    	</tr>
			  </table>
				<br/>
				<table class="tb_bottoni" align="center">
                 	<tr>
                     	<td class="td_tasto">
                     		<input type="submit" name="Ricerca" title="Ricerca categorie catastali" value="Ricerca"/>
   						</td>
                    </tr>
				</table>
				<br/>
				<logic:present name="risultatiRicerca">							 	
					<display:table class="tb_lista" name="${risultatiRicerca}" uid="risultatiRicerca"  pagesize="10" requestURI="${azione}" >
						<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
							<form:radiobutton path="codiKIdCategCat" value="${risultatiRicerca.codiKIdCategCat}" />
						</display:column>
						<display:column property="descCategoriaCat" title="Descrizione Categoria" sortable="false"/>
						<display:column property="descBreveCategoriaCat" title="Descrizione Breve Categoria" sortable="false"/>
						<display:column property="dataFineValidita" title="Validità" format="{0,date,dd/MM/yyyy}" sortable="false"/>  
					</display:table>
						
					<br/><br/>
					<table class="tb_bottoni" align="center">
                 		<tr>
                 	 		<td class="td_tasto"><input type="submit" value="Nuovo" name="NuovaCatCatastale" title="Nuovo"></td>
                 	 		<td class="td_tasto"><input type="submit" value="Modifica" name="ModificaCatCatastale" title="Modifica"></td>
                     	</tr>
					</table>
					<br />
					<br />
					
			</logic:present>
			</form:form>
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>

		</tiles:putAttribute>
	</tiles:insertDefinition>
