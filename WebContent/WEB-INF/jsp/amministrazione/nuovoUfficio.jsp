<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/funzioniAmministrazione/nuovoUfficio.htm" />
			<p class="titolo_page">
				<spring:message code="amministrazione.uffici.inserisci.titolo" />		
			</p>
			<form:form method="post" action="${azione}" commandName="ufficiForm" id="idForm">
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
			        <tr>
			        	<td><form:label path="itemTipoUfficio.id" cssClass="required"><spring:message code="amministrazione.uffici.tipoUfficio"/></form:label></td>
			        		<td>
								<form:select path="itemTipoUfficio.id" size="1">	
									<form:option value="-1" label="------Seleziona Tipologia Ufficio -------" />
									<form:options items="${elencoTipoUfficio}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>	
								<form:errors path="itemTipoUfficio.id" class="testoRosso"/>
							</td>
						</td>
			    	</tr>
			        <tr>
			        	<td><form:label path="itemComuneSede.id" cssClass="required"><spring:message code="amministrazione.uffici.comuneUfficio"/></form:label></td>
			        		<td>
								<form:select path="itemComuneSede.id" size="1">	
									<form:option value="-1" label="------Seleziona Comune Ufficio-------" />
									<form:options items="${elencoComuni}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>	
								<form:errors path="itemComuneSede.id" class="testoRosso"/>
							</td>
						</td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="descIndirizzoSede" cssClass="required"><spring:message code="amministrazione.uffici.indirizzo"/></form:label></td>
			        	<td>
			        		<form:input path="descIndirizzoSede" maxlength="120" size="90"/>
			        		<form:errors path="descIndirizzoSede" class="testoRosso"/>
						</td>
			    	</tr>
			      	<tr>
			        	<td><form:label path="itemDistretto.id"><spring:message code="amministrazione.uffici.distretto"/></form:label></td>
			        		<td>
								<form:select path="itemDistretto.id" size="1">	
									<form:option value="-1" label="------ Seleziona Distretto -------" />
									<form:options items="${elencoDistretti}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>	
								<form:errors path="itemDistretto.id" class="testoRosso"/>
							</td>
						</td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="descUffGiudiz"><spring:message code="amministrazione.uffici.uffGiudiz"/></form:label></td>
			        	<td>
			        		<form:input path="descUffGiudiz" maxlength="11" size="11"/>
			        		<form:errors path="descUffGiudiz" class="testoRosso"/>
						</td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="flgTribMp"><spring:message code="amministrazione.uffici.misurePrevenzione"/></form:label></td>
			        	<td>
							<form:checkbox path="flgTribMp" value="S" /></td>
			        		<form:errors path="flgTribMp" class="testoRosso"/>
						</td>
			    	</tr>
			    	<tr>
			        	<td><form:label path="itemTribunale.id"><spring:message code="amministrazione.uffici.tribunale"/></form:label></td>
			        		<td>
								<form:select path="itemTribunale.id" size="1">	
									<form:option value="-1" label="------ Seleziona Tribunale -------" />
									<form:options items="${elencoUfficiTribunali}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>	
								<form:errors path="itemTribunale.id" class="testoRosso"/>
							</td>
						</td>
			    	</tr>
			    	<tr>
			    		<td><form:label path="flgDda"><spring:message code="amministrazione.uffici.dda"/></form:label></td>
			        	<td>
							<form:checkbox path="flgDda" value="S" /></td>
			        		<form:errors path="flgDda" class="testoRosso"/>
						</td>
			    	</tr>
			    	<tr>
						<td><form:label path="dataFineValidita"><spring:message code="dataFineValidita"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="dataFineValidita" maxlength="10" size="12"/>
			        		<form:errors path="dataFineValidita" class="testoRosso"/>
			        	</td>
			        </tr>
				</table>
				<br />
				<br />
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" name="Inserisci" title="Inserisci nuovo Ufficio" value="Inserisci"></td>
						<td class="td_tasto"><input type="submit" name="Indietro"  title="Torna alla ricerca degli Uffici" value="Indietro"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
			
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>