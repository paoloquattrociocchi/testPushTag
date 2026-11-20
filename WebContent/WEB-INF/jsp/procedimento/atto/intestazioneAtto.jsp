<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="it.giustizia.smp.form.AttoForm"%>
<%@ page import="it.giustizia.smp.utility.Constants"%>

<%
	AttoForm atto = (AttoForm) request.getSession().getAttribute(Constants.ATTO);
%>

<%if(atto!=null){ %>
	<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td class="td_titolo_label" width="15%"><spring:message code="atto.tipo.atto" />
			</td>
			<td class="td_titolo" width="35%">
			<%if(atto.getTipoAtto()!=null){ %>
				<c:out value="<%=atto.getTipoAtto().getDescrizione()%>" />
			<%} %>						
			</td>
			<td class="td_titolo_label"><spring:message code="atto.categoria.atto" />
			</td>
			<td class="td_titolo" width="35%">
			<%if(atto.getCategoriaAtto()!=null){ %>
				<c:out value="<%=atto.getCategoriaAtto().getDescrizione()%>" />
			<%} %>
			</td>
		</tr>		
		<tr>
			<td class="td_titolo_label" width="15%"><spring:message code="atto.numero" />
			</td>
			<td class="td_titolo">
				<%if(atto.getNumeroAtto() != null){ %>
					<c:out value="<%=atto.getNumeroAtto()%>" />
				<%} %>
				&nbsp;
			</td>
			<td class="td_titolo_label"><spring:message code="atto.stato.atto" />
			</td>
			<td class="td_titolo">
				<%if(atto.getStatoAtto()!=null){ %>
					<c:out value="<%=atto.getStatoAtto().getDescrizione()%>" >
					</c:out>
				<%} %>
			</td>
		</tr>
		
		<logic:notEmpty name="atto" property="numeroAttoOrigine" scope="session">
		<tr>
			<%if(atto.getTipoAtto()!=null && "21".equalsIgnoreCase(atto.getTipoAtto().getId())){ %>
			   <td class="td_titolo_label" width="15%"><spring:message code="atto.decreto.cda" /></td>
			<%}else{ %>
			   <td class="td_titolo_label" width="15%"><spring:message code="atto.numero.origine" /></td>
			<%} %>
			
			
		
			<td class="td_titolo">
				<%if(atto.getNumeroAttoOrigine() != null){ %>
					<c:out value="<%=atto.getNumeroAttoOrigine()%>" />
				<%} %>
				&nbsp;
			</td>
			
			<td class="td_titolo_label">&nbsp;</td>
			<td class="td_titolo">&nbsp;</td>
		</tr>
		</logic:notEmpty>
		
		<%if(atto.getTipoAtto()!=null && "28".equalsIgnoreCase(atto.getTipoAtto().getId())){ %>		
			<logic:notEmpty name="atto" property="dataRicezioneAttoOrigine" scope="session">
			<tr>
			   <td class="td_titolo_label" width="15%"><spring:message code="atto.istanza.origine" /></td>
			
				<td class="td_titolo" colspan="3">
					<%if(atto.getDescrizioneCategoriaAttoOrigine() != null){ %>
						<c:out value="<%=atto.getDescrizioneCategoriaAttoOrigine()%>" ></c:out>
					<%} %>				
					<%if(atto.getDataRicezioneAttoOrigine() != null){ %>
						ricevuto il <fmt:formatDate value="<%=atto.getDataRicezioneAttoOrigine()%>" type="date" pattern="dd/MM/yyyy" />
					<%} %>
					&nbsp;
				</td>

			</tr>
			</logic:notEmpty>		
		<%} %>		
		
<%if(atto.getTipoAtto()!=null && "21".equalsIgnoreCase(atto.getTipoAtto().getId())){ %>		
		<logic:notEmpty name="atto" property="note" scope="session">
		 <tr>		  
            <td class="td_titolo_label" width="15%"><spring:message code="atto.numero.sezionale" /></td>	
			<td class="td_titolo" colspan="4">
				<%if(atto.getNote() != null){ %>
					<c:out value="<%=atto.getNote()%>" />
				<%} %>
				&nbsp;
			</td>	
		 </tr>
		</logic:notEmpty>		
<%} %>		
		<logic:notEmpty name="atto" property="numeroProcedimentoOrigine" scope="session">
		<tr>
			
			<td class="td_titolo" colspan="2">
				<c:out value="<%=atto.getNumeroProcedimentoOrigine()%>" />
				&nbsp;
			</td>
			
			<td class="td_titolo_label">&nbsp;</td>
			<td class="td_titolo">&nbsp;</td>
		</tr>
		</logic:notEmpty>
	</table>
<%}%>