<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="it.giustizia.smp.form.ProcedimentoForm"%>
<%@ page import="it.giustizia.smp.form.ProfiloForm"%>

<%
	ProcedimentoForm procedimento = (ProcedimentoForm) request.getSession().getAttribute("procedimento");
	String disabledViewProc = (String) request.getSession().getAttribute("disabledViewProc");
	ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
%>
<c:if test="${procedimento!=null}">
	<table class="tb_form" cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td class="td_titolo_label" width="15%"><spring:message
					code="procedimento" />
			</td>
			<td class="td_titolo" width="35%"><c:out
					value="<%=procedimento.getAnnoNumeroProcedimento()%>" />
			</td>
			<td class="td_titolo_label"><spring:message
					code="procedimento.dataIscrizione" />
			</td>
			<td class="td_titolo"><fmt:formatDate
					value="<%=procedimento.getDataApertura()%>" type="date"
					pattern="dd/MM/yyyy" />
			</td>
		</tr>
		<tr>
			<td class="td_titolo_label"><spring:message
					code="procedimento.tipo" />
			</td>
			<td class="td_titolo" width="35%">
			<c:if test="${procedimento.tipoProcedimento!=null}">
				<c:out value="<%=procedimento.getTipoProcedimento().getDescrizione()%>" />
			</c:if>
			</td>
			<td class="td_titolo_label" width="15%"><spring:message
					code="procedimento.statoProcedimento" />
			</td>
			<td class="td_titolo">
			<c:if test="${procedimento.statoProcedimento!=null}">
				<c:out value="<%=procedimento.getStatoProcedimento().getDescrizione()%>" />
			</c:if>
					
			</td>
		</tr>
		<c:if test="${procedimento.numeroPadre!=null && procedimento.annoPadre!=null}">
		<tr>
			<td class="td_titolo_label" width="15%"><spring:message
					code="procedimento.appellativoProcedimentoPadre" />
			</td>
			<td colspan="3" class="td_titolo">
				<c:out value="${procedimento.annoNumeroPadre}" />
				<c:if test="${procedimento.ufficioPadre!=null}">
								&nbsp;<c:out value="${procedimento.ufficioPadre}" />
				</c:if>			
			</td>
		</tr>
		</c:if>
	</table>
</c:if>	

