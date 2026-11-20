<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/procedimento/atti/elencoAttiTribunale.htm" />
		<form:form method="post" action="${azione}" commandName="attoForm">
		
		<input type="hidden" name="idProcedimentoCda" id="id_idProcedimentoCda" value="" />
		
			<p class="titolo_page">
				<spring:message code="atto.dettaglio" />
			</p>
			<br />
			<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />
			<br />
			

			<br />
			<br />
            <logic:notEmpty name="listaProcedimenti">
	          <table class="tb_form" cellspacing="0" cellpadding="0" border="0"-->
		       <tr>
		      	<td class="td_titolo_label">
				<p>
					Selezionare il procedimento di tribunale di cui visualizzare gli atti
				</p></td>
		        </tr>
	        </table>
	        <display:table class="tb_lista" uid="procedimento" name="${listaProcedimenti}" pagesize="10" requestURI="${paginazione}">
		     <display:column style="text-align: center; width: 5%" title="Sel.">
			<input type="radio" name="radioIDS" id="radioID_${procedimento_rowNum}"  onclick="selectID('${procedimento.idProcedimento}');" />
		</display:column>
		<display:column property="descTribCda" title="Numero Procedimento e Ufficio" sortable="false" />
	</display:table>

			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto" ><input type="submit" value="Seleziona" title="Seleziona" name="Seleziona" class="nowait"  /></td>
					
				</tr>
			</table>
</logic:notEmpty>
<logic:empty   name="listaProcedimenti">
 <table class="tb_form" cellspacing="0" cellpadding="0" border="0"-->
		       <tr>
		      	<td class="td_titolo_label">
				<p>
					Non ci sono dati disponibili in tribunale
				</p></td>
		        </tr>
	        </table>

</logic:empty>		
			
			<br />
			<br />


		</form:form>
		<script>
	    function selectID(idProcedimentoCda) {
		document.getElementById("id_idProcedimentoCda").value = idProcedimentoCda;
        document.forms[0].idProcedimentoCda.value= idProcedimentoCda;
	     }
     </script>	
	
		<script
			src="${pageContext.request.contextPath}/js/jquery/ui/jquery.ui.datepicker.js"></script>
		<script
			src="${pageContext.request.contextPath}/js/jquery/ui/jquery.ui.datepicker-it.js"></script>
		
	</tiles:putAttribute>
</tiles:insertDefinition>