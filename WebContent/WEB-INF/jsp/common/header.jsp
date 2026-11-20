<%@page import="it.giustizia.smp.model.TAnMenu"%>
<%@page import="it.giustizia.smp.model.TUtente"%>
<%@page import="it.giustizia.smp.form.ProfiloForm"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<%
	@SuppressWarnings("unchecked")
	ArrayList<TAnMenu> menu1liv = (ArrayList<TAnMenu>) request.getSession().getAttribute("menu");
%>
<div id="logoutDiv"></div>
<div id="dialog-message" title="Attenzione">
	<div id="id_dialogIcon"></div>
	<div id="id_textDialog" align="center" style="vertical-align: bottom"></div>
</div>
<table class="tb_header">
	<tr>
		<td align="left"><img
			src="${pageContext.request.contextPath}/images/heather_sx.jpg" /></td>
		
		<td align="right">
			<div class="utenteLogin">
				<%
					TUtente utente = (TUtente) request.getSession().getAttribute("utente");
				%>
				<%
					ProfiloForm profilo = (ProfiloForm) request.getSession().getAttribute("profilo");
					String manualeUtente = profilo.getDescrizioneUfficio().replaceAll("\\s+","");
					String idTipoUfficio = profilo.getIdTipoUfficio() + "";
				%>
				Salve, <span style="font-weight: bold">${sessionScope.utente.nome} ${sessionScope.utente.cognome}</span>

				<!-- ><a href="<spring:url value="/login.htm" />" title="clicca per cambiare il profilo"> <img
					src="${pageContext.request.contextPath}/images/utente.gif"
					align="absmiddle" border="0" />
				</a>-->
				&nbsp;
				${sessionScope.profilo.ruolo } - ${sessionScope.profilo.descDettaglioUfficio}
				&nbsp;&nbsp;
				<a href="<spring:url value="/logout.htm" />" />
					<img src="${pageContext.request.contextPath}/images/bottoneLogout.png"
						title="Termina la sessione di lavoro corrente" align="absmiddle" border="0" />
				</a>
				&nbsp;
				<a title="scarica manuale utente" href="${pageContext.request.contextPath}/ManualeUtente/<%=idTipoUfficio%>" target="_blank">
	               	<img src="${pageContext.request.contextPath}/images/icona_help.png" align="absmiddle" border="0" />
                </a>
                &nbsp;&nbsp;
			</div>
		</td>
	</tr>
</table>
<table class="tb_header_tasti">
	<tr>
		<td class="tb_header_tasti_sx">&nbsp;</td>
		<td>
			<table border="0" class="tb_header_link">
				<tr>
					<td>&nbsp;</td>
					<td class="link_sx">&nbsp;</td>
					<%
						int numeroTasti = 0;
						for (int i = 0; i < menu1liv.size(); i++) {
							if (menu1liv.get(i).getNumePadre().intValue()==0) {
								numeroTasti++;
							}
						}
						int contTasto = 0;
						int widthTasto = 90 / numeroTasti;
						String coloreTasto = "";
						String descFunzione = null;
						if( request.getSession().getAttribute("descr") != null ) {
						    descFunzione = new String( (String)request.getSession().getAttribute("descr") );
						    request.getSession().removeAttribute("descr");
						}
						if (request.getParameter("descr") != null)
						    descFunzione = (String)request.getParameter("descr");
						if (descFunzione != null) {
							request.getSession().setAttribute("fromTasto", descFunzione);
						}
						for (int i = 0; i < menu1liv.size(); i++) {
							if (menu1liv.get(i).getNumePadre().intValue()==0) {
								if (request.getSession().getAttribute("fromTasto") != null && request.getSession().getAttribute("fromTasto").equals(menu1liv.get(i).getDescVoce())) {
									coloreTasto = "style='color:#993300'";
								} else
									coloreTasto = "";
					%>
					<td class="link_tasto" style="width:<%=widthTasto%>%"><a
						href="${pageContext.request.contextPath}/home?idPadreMenu=<%=menu1liv.get(i).getCodiKIdMenu()%>&descr=<%=menu1liv.get(i).getDescVoce()%>"
						<%=coloreTasto%>><%=menu1liv.get(i).getDescVoce()%></a></td>
					<%
						contTasto++;
								if (contTasto < numeroTasti) {
					%>
					<td class="link_div">&nbsp;</td>
					<%
						}
							}
						}
					%>
				<td class="link_tasto" width="12%"><a href="/DMWeb/index.htm?p=${sessionScope.profilo.profiloString}&cp=${pageContext.request.contextPath}&name=${sessionScope.utente.nome}&surname=${sessionScope.utente.cognome}">Console</a></td>
					
					<td class="link_dx">&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
		</td>
		<td class="tb_header_tasti_dx">&nbsp;</td>
	</tr>
</table>