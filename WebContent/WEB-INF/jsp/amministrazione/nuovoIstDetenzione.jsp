<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

	<tiles:insertDefinition name="base.definition">
		<tiles:putAttribute name="body-content">
			<spring:url var="azione"value="/funzioniAmministrazione/nuovoIstDetenzione.htm" />
			<p class="titolo_page">
				<spring:message code="amministrazione.istitutoDet.inserisci.titolo"/>		
			</p>
			<form:form method="post" action="${azione}" commandName="istitutoDetenzioneForm" id="idForm">
				<br/>
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
			        <tr>
			        	<td><form:label path="itemTipoIstituto.id" cssClass="required"><spring:message code="amministrazione.istitutoDet.tipoIstituto"/></form:label></td>
			        		<td>
								<form:select path="itemTipoIstituto.id" size="1">	
									<form:option value="-1" label="------ Seleziona Tipo Istituto -------" />
									<form:options items="${elencoTipoIstituto}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>	
								<form:errors path="itemTipoIstituto.id" class="testoRosso"/>
							</td>
						</td>
			    	</tr>
			        <tr>
			        	<td><form:label path="itemComune.id" cssClass="required"><spring:message code="amministrazione.istitutoDet.comune"/></form:label></td>
			        		<td>
								<form:select path="itemComune.id" size="1">	
									<form:option value="-1" label="------ Seleziona Comune -------" />
									<form:options items="${elencoComuni}" itemValue="id" itemLabel="descrizione" /> 																	
								</form:select>	
								<form:errors path="itemComune.id" class="testoRosso"/>
							</td>
						</td>
			    	</tr>
					<tr>
						<td><form:label path="descIndirizzo"><spring:message code="amministrazione.istitutoDet.indirizzo"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="descIndirizzo" maxlength="120" size="120"/>
			        		<form:errors path="descIndirizzo" class="testoRosso"/>
			        	</td>
			        </tr>
					<tr>
						<td><form:label path="descUffGiudiz" ><spring:message code="amministrazione.istitutoDet.descrizioneUffGiud"/></form:label></td>
			        	<td>		        	   
			        		<form:input path="descUffGiudiz" maxlength="11" size="11"/>
			        		<form:errors path="descUffGiudiz" class="testoRosso"/>
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
				<br/>
				<br/>
				<table class="tb_bottoni" align="center">
					<tr>
						<td class="td_tasto"><input type="submit" name="Inserisci" title="Inserisci nuovo istituto detenzione" value="Inserisci"></td>
						<td class="td_tasto"><input type="submit" name="Indietro"  title="Torna alla ricerca istituto detenzione" value="Indietro"></td>
					</tr>
				</table>
				<br />
				<br />
			</form:form>
		</tiles:putAttribute>
	</tiles:insertDefinition>
</body>
</html>