<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
			<p class="titolo_page"><spring:message code="conservatoria.titolo.ricerca" /></p>
			<spring:url var="azione" value="/conservatorie/visualizzaComunicazioniConservatoria.htm" />
			<form:form id="id_formRicerca" method="post" action="${azione}" commandName="comunicazioneRicercaForm">
				<br />
				<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td width="8%"><form:label path="tipoNota"><spring:message code="conservatoria.tipoNota"/></form:label></td>
			        	<td >		        	   
			        		<form:select path="tipoNota.id" size="1">
								<form:option value="-1" label="--- Seleziona Tipo ---" />
								<form:options items="${tipoNota}" itemValue="id" itemLabel="descrizione" />
							</form:select>
			        	<form:errors path="tipoNota" class="testoRosso"/>
			        	</td>  
			        </tr>
			        <tr>
						<td><form:label path="categorieAtti"><spring:message code="conservatoria.categorieAtti"/></form:label></td>
			        	<td>		        	   
			        		<form:select path="categorieAtti.id" size="1">
								<form:option value="-1" label="--- Seleziona Tipo Atto ---" />
								<form:options items="${categorieAtti}" itemValue="id" itemLabel="descrizione" />
							</form:select>
			        	<form:errors path="categorieAtti" class="testoRosso"/>
			        	</td>
			        </tr>
                    <tr>
	                    <td>
	                    	<form:label path="anno"><spring:message code="conservatoria.anno" /></form:label>
	                    </td>
	                    <td>
	                    	<form:input path="anno" id="anno" maxlength="4" size="5" /><form:errors path="anno" class="testoRosso" />
	                    	&nbsp;&nbsp;&nbsp;&nbsp;
	                    	
	                    	<form:label path="dalNumero"><spring:message code="conservatoria.dalNumero" /></form:label>
	                    	&nbsp;<form:input path="dalNumero" id="dalNumero" maxlength="7" size="8" /> <form:errors path="dalNumero"  class="testoRosso" />
	                    	&nbsp;&nbsp;&nbsp;&nbsp;
	                    	
	                    	<form:label path="alNumero"><spring:message code="conservatoria.alNumero" /></form:label>
	                    	&nbsp;<form:input path="alNumero" id="alNumero" maxlength="7" size="8" /> <form:errors path="alNumero"  class="testoRosso" />
	                    </td>					
                    </tr>
			        <tr>
						<td><form:label path="conservatoria"><spring:message code="conservatoria.conservatorie"/></form:label></td>
			        	<td  >		        	   
			        		<form:select path="conservatoria.id" size="1">
								<form:option value="-1" label="--- Seleziona Conservatoria ---" />
								<form:options items="${conservatoria}" itemValue="id" itemLabel="descrizione" />
							</form:select>
				        	<form:errors path="conservatoria"/>
			        	</td>
			        </tr>
			    </table>
			    <br/><br/>
			   <div id="id_tipoPersona" style="display: inline-block; width: 100%;">
				   <c:if test="${comunicazioneRicercaForm.denominazione == null}">
						<table class="tb_form" cellspacing="0" cellpadding="0" border="0" >
					      	<tr>
						    	<td>
						    		<form:radiobutton path="tipoPersona"  id="personaFisica"  checked="checked"   label = " Persona Fisica" style="cursor:pointer"  onclick="abilitaPersonaFisica()" />
						    		&nbsp;&nbsp;
						    		<form:radiobutton path="tipoPersona"  id="personaGiuridica" label = " Persona Giuridica" style="cursor:pointer"  onclick="abilitaPersonaGiuridica()"/>
						    	</td>
			     			</tr>
			     		</table>
			     	</c:if>
					<c:if test="${comunicazioneRicercaForm.denominazione != null}">
						<table class="tb_form" cellspacing="0" cellpadding="0" border="0" >
					      	<tr>
						    	<td>
						    		<form:radiobutton path="tipoPersona"  id="personaFisica"    label = " Persona Fisica" style="cursor:pointer"  onclick="abilitaPersonaFisica()" />
						    		&nbsp;&nbsp;
						    		<form:radiobutton path="tipoPersona"  id="personaGiuridica" checked="checked" label = " Persona Giuridica" style="cursor:pointer"  onclick="abilitaPersonaGiuridica()"/>
						    	</td>
			     			</tr>
			     		</table>
					</c:if>
	     		</div>
	     		<br/>
	     		
	     		
	     		<c:if test="${comunicazioneRicercaForm.denominazione == null}">
	     			<div id="nomeCognome" style="display: inline-block; width: 100%;">
	     		</c:if>
	     		<c:if test="${comunicazioneRicercaForm.denominazione != null}">
	     		 	<div id="nomeCognome" style="display: none; width: 100%;">
	     		</c:if>
			     		<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
		                    <tr>
			                    <td>
			                    	<form:label path="cognome"><spring:message code="conservatoria.cognome" /></form:label>
			                    	&nbsp;&nbsp;
			                    	<form:input path="cognome" id="cognome" maxlength="50" size="50" /> <form:errors path="cognome" />
			                    	&nbsp;&nbsp;&nbsp;&nbsp;
			                    	<form:label path="nome"><spring:message code="conservatoria.nome" /></form:label>
			                    	&nbsp;&nbsp;
			                    	<form:input path="nome" id="nome" maxlength="50" size="50" /> <form:errors path="nome" />
			                    </td>
		                    </tr>
					 	</table>
				 	</div>
	
				 <c:if test="${comunicazioneRicercaForm.denominazione == null}">
				 	<div id="denom" style="display: none; width: 100%;">
				 </c:if>
				 
				 <c:if test="${comunicazioneRicercaForm.denominazione != null}">
					 <div id="denom" style="display: inline-block; width: 100%;">
				 </c:if> 
						 <table class="tb_form" cellspacing="0" cellpadding="0" border="0">
		                    <tr>
			                    <td>
			                    	<form:label path="denominazione"><spring:message code="conservatoria.denominazione" /></form:label>
			                    	&nbsp;&nbsp;
			                    	<form:input path="denominazione" id="denominazione" maxlength="150" size="50" /> <form:errors path="denominazione" />
			                    </td>
		                    </tr>
						 </table>
					 </div>

				 
				<br/><br/>
				<table class="tb_bottoni" align="center">
                 	<tr>
                     	<td class="td_tasto"><input type="submit" name="Ricerca" title="Ricerca conservatoria" value="Ricerca"/></td>
   					</tr>                    
				</table>
				<br/>
				<logic:present name="listaComunicazioniConservatoriaForm">	
					<display:table class="tb_lista" name="${listaComunicazioniConservatoriaForm}" uid="listaComunicazioniConservatoriaForm" pagesize="10" requestURI="${azione}" >
						<display:column title="Seleziona" sortable="false" style="text-align: center; width: 5%">
						<form:radiobutton path="notaSelezionata" value="${listaComunicazioniConservatoriaForm.idNota}'^'${listaComunicazioniConservatoriaForm.idUffOwner}" />
						</display:column> 
						<display:column property="descTipoCatAtto" title="Tipo Atto" sortable="false"/>
						<display:column property="repertorio" title="Anno/Numero-progressivo" sortable="false"/>  
						<display:column property="statoComunicazione" title="Stato" sortable="false"/>
						<display:column property="tipoComunicazione" title="Tipo" sortable="false"/>
						<display:column property="dataComunicazione" title="Data" format="{0,date,dd/MM/yyyy}" sortable="false"/>
					</display:table>
					<br/><br/>
				</logic:present>
				<logic:notEmpty name="listaComunicazioniConservatoriaForm">
					<table class="tb_bottoni" align="center">
                 		<tr>
                 	 		<td class="td_tasto"><input type="submit" value="Dettaglio" name="Dettaglio" title="Dettaglio"></td>
                       	</tr>
					</table>
					<br />
					<br />
				</logic:notEmpty>
			</form:form>
			
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
<smp:back buttonId="backButton"></smp:back>

<script type="text/javascript">
	function abilitaPersonaGiuridica() {
					
           document.getElementById("denom").style.display='inline-block' ;
           document.getElementById("nomeCognome").style.display='none' ;
          
		document.getElementById("denominazione").disabled=false;
		document.getElementById("nome").disabled=true;
		document.getElementById("cognome").disabled=true;
	}
	
	function abilitaPersonaFisica() {

		   document.getElementById("denom").style.display='none' ;
           document.getElementById("nomeCognome").style.display='inline-block' ;
           
		document.getElementById("denominazione").disabled=true;
		document.getElementById("nome").disabled=false;
		document.getElementById("cognome").disabled=false;
	}
</script>

		</tiles:putAttribute>
	</tiles:insertDefinition>