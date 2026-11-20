 <%@page import="it.giustizia.smp.form.ProfiloForm"%>
 <% 
   ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
 %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="smp" uri="/WEB-INF/smp.tld"%>

<%@page import="it.giustizia.smp.utility.Constants"%>

  
<tiles:insertDefinition name="base.definition"> 
	<tiles:putAttribute name="body-content">
	        
		<spring:url var="azione" value="/flussi/accettaDeposito.htm" />
	
 		<p class="titolo_page">
			<spring:message code="depositi.dettaglio" />
		</p>
		
		<form:form id="id_formInvia" method="post" action="${azione}" commandName="flussiToolAmministratoriForm" enctype="multipart/form-data">

			<span>
			   <form:hidden path="idDeposito"/>
			   <form:hidden path="idUffOwner"/>			   
			   <form:hidden path="nomeAllegato"/>
			   <form:hidden path="tipoAllegato"/>
			   <form:hidden path="IdProcFG"/>
			   <form:hidden path="IdProc"/>
			</span>
			
			<table class="tb_form" cellspacing="0" cellpadding="0" border="0">

   		      <tr>
			     <td class="td_titolo" colspan="4">
				    <spring:message code="procedimento.titolo" />
			     </td>
		      </tr>		

      		  <tr>
			    <td><form:label path="" ><spring:message code="procedimento"/></form:label></td>
	        	<td>
					<c:out value="${flussoForm.formProcedimentoFM.annoNumeroProcedimento}"/>
	        	</td>
        	    <td><form:label path="" ><spring:message code="procedimento.dataIscrizione"/></form:label></td>
        	    <td colspan="3">
        	        <fmt:formatDate value="${flussoForm.formProcedimentoFM.dataIscrizione}" type="date" pattern="dd/MM/yyyy" /> 
        	    </td>
		      </tr>
      		  <tr>
			    <td><form:label path="" ><spring:message code="procedimento.tipo"/></form:label></td>
        	    <td colspan="3">
				    <c:out value="${flussoForm.formProcedimentoFM.tipoProcedimento.descrizione}"/>
        	    </td>
		      </tr>
      		  <tr>
			    <td><form:label path="" ><spring:message code="procedimento.statoProcedimento"/></form:label></td>
        	    <td colspan="3">
				    <c:out value="${flussoForm.formProcedimentoFM.statoProcedimento.descrizione}"/>
        	    </td>
		      </tr>
   		      <tr>
			     <td class="td_titolo" colspan="4">
				    <spring:message code="procedimento.titoloGestione" />
			     </td>
		      </tr>		

      		  <tr>
			    <td><form:label path="" ><spring:message code="procedimento"/></form:label></td>
	        	<td>
					<c:out value="${flussoForm.formProcedimentoFG.annoNumeroProcedimento}"/>
	        	</td>
        	    <td><form:label path="" ><spring:message code="procedimento.dataIscrizione"/></form:label></td>
        	    <td colspan="3">
       	           <fmt:formatDate value="${flussoForm.formProcedimentoFG.dataIscrizione}" type="date" pattern="dd/MM/yyyy" /> 
        	    </td>
		      </tr>
      		  <tr>
			    <td><form:label path="" ><spring:message code="procedimento.tipo"/></form:label></td>
        	    <td colspan="3">
				    <c:out value="${flussoForm.formProcedimentoFG.tipoProcedimento.descrizione}"/>
        	    </td>
		      </tr>
      		  <tr>
			    <td><form:label path="" ><spring:message code="procedimento.statoProcedimento"/></form:label></td>
        	    <td colspan="3">
				    <c:out value="${flussoForm.formProcedimentoFG.statoProcedimento.descrizione}"/>
        	    </td>
		      </tr>
              <tr><td colspan="4">&nbsp;</td></tr>
     		  <tr>
			    <td><form:label path="" ><spring:message code="depositi.dataRicezione"/></form:label></td>
        	    <td colspan="3">
         	        <fmt:formatDate value="${flussoForm.dataRicezione}" type="date" pattern="dd/MM/yyyy" />       	 
         	        <form:hidden path="dataRicezione"/>   
         	        <form:hidden path="numeroRiferimento"/>    	           
        	    </td>
		      </tr>
              <tr><td colspan="4">&nbsp;</td></tr>
     		  <tr>
			    <td><form:label path="" ><spring:message code="depositi.dataInizioPeriodo"/></form:label></td>
        	    <td>
         	        <fmt:formatDate value="${flussoForm.dataInizioPeriodo}" type="date" pattern="dd/MM/yyyy" />  
 		            <form:hidden path="dataInizioPeriodo"/>         	             	    
        	    </td>     		  
			    <td><form:label path="" ><spring:message code="depositi.dataFinePeriodo"/></form:label></td>
        	    <td colspan="3">
         	        <fmt:formatDate value="${flussoForm.dataFinePeriodo}" type="date" pattern="dd/MM/yyyy" />  
		            <form:hidden path="dataFinePeriodo"/>         	             	    
        	    </td>
		      </tr>

   		      <tr>
			     <td class="td_titolo" colspan="4">
				    <spring:message code="depositi.amministratore" />
			     </td>
		      </tr>		
       		  <tr>
			    <td><form:label path="" ><spring:message code="depositi.amministratore.cognome"/></form:label></td>
	        	<form:hidden path="formAmministratore.id"/>
	        	<td>
					<c:out value="${flussoForm.formAmministratore.cognome}"/>
					<form:hidden path="formAmministratore.cognome"/>         	             	    					
	        	</td>
        	    <td><form:label path="" ><spring:message code="depositi.amministratore.nome"/></form:label></td>
        	    <td colspan="3">
        	        <c:out value="${flussoForm.formAmministratore.nome}" /> 
    		        <form:hidden path="formAmministratore.nome"/>         	             	            	        
        	    </td>
		      </tr>
     		  <tr>
			    <td><form:label path="" ><spring:message code="depositi.amministratore.dataNascita"/></form:label></td>
	        	<td>
					<fmt:formatDate value="${flussoForm.formAmministratore.dataNascita}" type="date" pattern="dd/MM/yyyy" />
	        	</td>
        	    <td><form:label path="" ><spring:message code="depositi.amministratore.luogoNascita"/></form:label></td>
        	    <td colspan="3">
       	           <c:out value="${flussoForm.formAmministratore.luogoNascita}" /> 
        	    </td>
		      </tr>
             <tr><td colspan="4">&nbsp;</td></tr>
		     <tr>
	     	    <td><form:label path="" >Documento allegato</form:label></td>  		     
			    <td colspan="3">
			     <c:if test="${flussoForm.nomeAllegato != ''}">
			      <span><c:out value="${flussoForm.nomeAllegato}"></c:out></span> &nbsp;&nbsp; <img src="${pageContext.request.contextPath}/images/download.gif" style="cursor: pointer; border: none" title="Visualizza Relazione" onclick="gestioneDocumento('${pageContext.request.contextPath}')"></img>&nbsp;&nbsp; 
                 </c:if>
               </td>
             </tr>
            			       
		    </table>
		    
				<br/>
					<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
						<tr>
							<td class="td_titolo" colspan="4"><spring:message code="depositi.listaBeni" /></td>
						</tr>
					</table>
		            <display:table class="tb_lista" uid="bene" name="${listaBeni}" >				
						<display:column property="idBene" title="Identificativo" sortable="false" />
						<display:column property="descrizioneBene" title="Descrizione" sortable="false" />					
					</display:table>


		    
			<br/><br/>
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto">	
						 <c:if test="${flussoForm.flag == 'N'}">			
						     <input	id="Conferma" type="submit" name="Conferma" value="Accetta Deposito" title="Accetta Deposito" />
	                     </c:if>
						 <c:if test="${flussoForm.flag == 'S'}">			
						     <input	id="Conferma" type="submit" name="Conferma" value="Accetta Deposito" title="Accetta Deposito" disabled="disabled" />
	                     </c:if>	                     
					</td>
					<td class="td_tasto">					
						<input type="button" value="Indietro" onClick="indietro('/flussi/flussiToolAmministratori.htm')">
					</td>					
				</tr>
			</table>
			<br/><br/>
			
		</form:form>
		
		<!-- <smp:back buttonId="backButton"></smp:back> -->
		
<script type="text/javascript">

function gestioneDocumento(contextPath) {

		$('#id_formInvia').attr('action', contextPath+'/flussi/downloadAllegato.htm');	
		$('#id_formInvia').submit();
		$('#id_formInvia').attr('action', contextPath+'/flussi/accettaDeposito.htm');
}
</script>		    
			
			
		
	
	
	</tiles:putAttribute>
</tiles:insertDefinition>


