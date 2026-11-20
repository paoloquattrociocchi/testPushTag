<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

					
				</table>
				<br/>	
				<table class="tb_form">	
					<tr>
						<td class="td_titolo" colspan="4"><spring:message code="soggetto.modifica.listaeredi"/></td>
					</tr>
				</table>
				
				<display:table class="tb_lista" name="${erediAssociati}" uid="erediAssociati" pagesize="10" requestURI="${azione}" >
					<display:column style="text-align: center; width: 5%" title="Seleziona">
						<form:radiobutton path="eredeConviventeForm.id" value="${erediAssociati.id}"/>
						</display:column>
					<display:column property="cognome" title="Cognome" sortable="false" />
					<display:column property="nome" title="Nome" sortable="false" />
					<display:column property="dataNascita" title="Data Nascita" format="{0,date,dd/MM/yyyy}" sortable="false" />
					<display:column property="statoNascitaErCon.descrizione" title="Stato" sortable="false" />
					<display:column property="descrizioneComuneNascitaItalianoEstero" title="Comune" sortable="false" />
					<display:column property="parentela" title="Parentela" sortable="false" />
				</display:table>
				
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<logic:notEmpty name="erediAssociati">
							<td class="td_tasto"><input type="submit" value="Modifica" name="ModificaErede"></td>
							<td class="td_tasto"><input type="submit" value="Cancella" name="CancellaErede"></td>
						</logic:notEmpty>
						<td class="td_tasto"><input type="submit" value="Nuovo" name="NuovoErede"></td>
					</tr>
				</table>
				
				<logic:notEmpty name="soggettoForm" property="gestioneInserimentoModificaErede">
				<table class="tb_form" >
				<form:hidden path="eredeConviventeForm.id"/>
				<tr>
					<td><form:label path="eredeConviventeForm.cognome" cssClass="required"><spring:message code="cognome" /></form:label></td>
					<td><form:input path="eredeConviventeForm.cognome" maxlength="35" size="30"/>
						<form:errors path="eredeConviventeForm.cognome" class="testoRosso"/></td>
					<td><form:label path="eredeConviventeForm.nome" cssClass="required"><spring:message code="nome" /></form:label></td>
					<td><form:input path="eredeConviventeForm.nome" maxlength="35" size="30"/>
						<form:errors path="eredeConviventeForm.nome" class="testoRosso"/></td>
				</tr>
				<tr>
					<td><form:label path="eredeConviventeForm.dataNascita"><spring:message code="dataNascita" /></form:label></td>
					<td colspan="3">
						<form:input path="eredeConviventeForm.dataNascita" maxlength="10" size="12" id="dataNascitaErede"/>
						<form:errors path="eredeConviventeForm.dataNascita" class="testoRosso" /></td>								
				</tr>
				<tr>
					<td><form:label path="eredeConviventeForm.statoNascitaErCon.id"><spring:message code="stato" /></form:label></td>
					<td><form:select id="id_StatoNascitaErCon" path="eredeConviventeForm.statoNascitaErCon.id" size="1" onchange="controllaCampiEAutocomplete(this.value,'ComuneNascitaErCon')">
							<form:option value="-1" label="---Seleziona Stato---"/>
							<form:options items="${statiNascita}"  itemValue="id" itemLabel="descrizione" /> 																	
						</form:select>
					</td>						
					<td><form:label path="eredeConviventeForm.comuneNascitaErCon.id" ><spring:message code="comune"/></form:label></td>
		        	<td>
						<div class="ui-widget">		
							<form:input id="id_descrizioneComuneNascitaErCon" path="eredeConviventeForm.comuneNascitaErCon.descrizione" size="50" title="Immettere almeno 2 lettere per avviare la ricerca"/>
							<div id="id_loadingComuneNascitaErede"></div>
							<form:hidden id="id_hiddenComuneNascitaErCon" path="eredeConviventeForm.comuneNascitaErCon.id"/>
						</div>
		        	</td>
				</tr>
				<tr>
					<td><form:label path="eredeConviventeForm.parentela"><spring:message code="soggetto.modifica.parentela" /></form:label></td>
					<td colspan="3">
						<form:input path="eredeConviventeForm.parentela" /></td>												
				</tr>
				</table>
				
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" value="Salva" name="SalvaErede"></td>
					</tr>
				</table>
				</logic:notEmpty>			
				<table class="tb_form">