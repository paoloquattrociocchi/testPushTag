<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione"
			value="/procedimento/bene/inserimentoBeneFinanziario.htm" />
		<p class="titolo_page">
			<spring:message code="bene.polizza.inserimento" />
			
			
		</p>
		<form:form id="id_beneForm" method="post" action="${azione}" commandName="beneForm">
			<form:hidden path="tipologia.id"/>
			<form:hidden path="tipologia.descrizione"/>
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

			<form:hidden id="id_abiCode" path="abi"/>
			<form:hidden path="descrizioneEnte"/>
			<form:hidden path="provinciaSel"/>
			<form:hidden path="disabledProvincia"/>
			<form:hidden path="disabledValutaEst"/>
			<form:hidden path="indirizzoBeneFin"/>
			<form:hidden id="id_provinciaEntePolizza" path="provinciaEntePolizza"/>
			
	
			<br/>
				 <jsp:include page="/WEB-INF/jsp/procedimento/atto/intestazioneAtto.jsp" />
			<br/>
			<table class="tb_form" border=0>
				<tr>
					<td width="15%"><form:label path="tipologia.id" cssClass="required">
							<spring:message code="bene.tipologia" />
						</form:label>
					</td>
					<td colspan="3">
						<form:select path="tipologia.id" size="1" disabled="true" cssStyle="width:20%">
							<form:option value="4" label="${beneForm.tipologia.descrizione}"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="categoria.id" cssClass="required">
							<spring:message code="bene.categoria" />
						</form:label>
					</td>
					<td colspan="3">
						<form:select id="id_cat" path="categoria.id" size="1" onchange="submit()">
							<form:option value="0" label="------------Seleziona Categoria------------" />
							<form:options items="${categorie}" itemValue="id" itemLabel="descrizione" />
						</form:select>
						<form:errors path="categoria.id" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="sottocategoria.id" cssClass="required">
							<spring:message code="bene.sottocategoria" />
						</form:label>
					</td>
					<td colspan="3">
						<form:select path="sottocategoria.id" size="1" onchange="submit()">
							<form:option value="0" label="------Seleziona Sottocategoria-------" />
							<form:options items="${sottocategorie}" itemValue="id" itemLabel="descrizione" />
						</form:select>
						<form:errors path="sottocategoria.id" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td class="td_titolo" colspan="4"><spring:message code="bene.polizza.datirelativi" /></td>
				</tr>
					<tr>
					<td width="15%"><form:label path="descOperFinanz"  cssClass="required">
							<spring:message code="bene.finanziario.descrizioneente" />
						</form:label>
					</td>
					<td colspan="3">
		        		<div class="ui-widget">
							<form:input id="id_descrizioneDescOperFinanz" path="descOperFinanz.descrizione" 
										cssStyle="width:95%" value="${beneForm.descrizioneEnte}"
										title="Immettere almeno 5 lettere per avviare la ricerca" />
							<div id="id_loadingDescOperFinanz"></div>
						</div>
						<form:hidden id="id_hiddenDescOperFinanz" path="descOperFinanz.id"/>
						<form:errors path="descOperFinanz" class="testoRosso"/>
		        	</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="provincia">
							<spring:message code="bene.finanziario.provincia" />
						</form:label>
					</td>
					<td colspan="3">
						<c:out value="${beneForm.provinciaEntePolizza }" />
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="indirizzoBeneFin" >
							<spring:message code="bene.finanziario.indirizzo" />
						</form:label>
					</td>
					<td colspan="3">
						<c:out value="${beneForm.indirizzoBeneFin}"   />
					</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="numeroPolizza" >
							<spring:message code="bene.finanziario.numeropolizza" />
						</form:label>
					</td>
					<td colspan="3">
						<form:input path="numeroPolizza" maxlength="10" size="20" 
						onkeypress="return soloNumeri(event)" />
								    <form:errors path="numeroPolizza" class="testoRosso"/>
								    
					</td>
				</tr>
				<tr>
					<td width="15%"><form:label path="valutaEur" >
							<spring:message code="bene.finanziario.importo.euro" />
						</form:label>
					</td>
					<td colspan="3">
						<form:input path="valutaEur" maxlength="15" size="20"  />
						<form:errors path="valutaEur" class="testoRosso"/>
					</td>
				</tr>
				<tr>
				    <td width="15%"><form:label path="tipoValuta"><spring:message code="bene.finanziario.valuta"/></form:label></td>
					<td width="35%">							
						<form:select path="tipoValuta.id" size="1" onchange="submit()"  >
							<form:option value="-1" label="-----Seleziona Valuta------" />
							<form:options items="${valuta}" itemValue="id" itemLabel="descrizione" /> 							
						</form:select>
					</td>
					<td><form:label path="valutaEst">
							<spring:message code="bene.finanziario.importo.estero"/>
						</form:label>
					</td>
					<td width="35%">
						<form:input path="valutaEst" maxlength="15" size="20" />
						<form:errors path="valutaEst" class="testoRosso"/>
					</td>
				</tr>
				<tr>
					<td><form:label path="note">
							<spring:message code="note" />
						</form:label>
					</td>
					<td width="35%" colspan="3">
						<form:textarea path="note" rows="5" style="margin: 0px; width: 613px; height: 76px;" />
					</td>
				</tr>
			</table>

			<br />
			<br />

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input type="submit" value="Inserisci" name="SalvaEAssocia"></td>
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro" title="Indietro"></td>
				</tr>
			</table>
			
			</form:form>
			
			
			<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
			<smp:back buttonId="backButton"></smp:back>
			
			
			<script src="${pageContext.request.contextPath}/js/suggestlist/doAutocompleteOpFinanziariPolizza.js"></script>
			
			<script type="text/javascript">
				function soloNumeri(evt){   
					var charCode = (evt.which) ? evt.which : event.keyCode   
					if (charCode > 31 && (charCode < 48 || charCode > 57))
						return false;
					return true;
				}
				$(document).ready(function(){
					doAutocompleteOpFinanzPolizza("DescOperFinanz");
				});
 			</script>									
			
	</tiles:putAttribute>
</tiles:insertDefinition>
