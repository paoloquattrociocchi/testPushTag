<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ page
	import="org.springframework.security.core.AuthenticationException"%>

<tiles:insertDefinition name="base.definition">
<tiles:putAttribute name="body-content">

	<jsp:include page="/WEB-INF/jsp/procedimento/intestazione.jsp" />

	<br>
	<br>

	<form name="f" id="errorForm" action="null" method="POST">
		<table>
			<tr>
				<td width="50"><img
					src="${pageContext.request.contextPath}/images/error.png"
					width="50" height="50" /></td>
				<td style="vertical-align: middle;">
					<p class="titolo_1">
						<span style="color: red; font-size: 20px">Attenzione! Si è
							verificato un errore di sistema. Impossibile completare
							l'operazione. E' possibile continuare la navigazione da menu.</span>
					</p>
				</td>
			</tr>
		</table>

		<br> 
		<br>

		<table class="tb_bottoni" align="center">
			<tr>
				<td class="td_tasto"><input class="nowait" type="button" value="Dettagli" onclick="dettaglioErrore('${exception.class}')"
					title="Clicca per visualizzare i dettagli dell'errore"></td>
					<td class="td_tasto"><input type="button" id="backButton" value="Indietro" title="Indietro"></td>
			</tr>
		</table>

		<table>
			<tr>
				<td>
					<div id="id_divDettaglio" style="display: none;"
						title="Dettaglio errore">
						<div id="id_div_testoEccezione"
							style="overflow: scroll; height: 300px; width: auto">
							<c:forEach items="${exception.message}" var="element">
								<c:out value="${element}" />
							</c:forEach>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</form>
	
	<%@taglib prefix="smp" uri="/WEB-INF/smp.tld" %>
	<smp:back buttonId="backButton"></smp:back>
	
	
	<script type="text/javascript">
		function dettaglioErrore(title) {
			$("#id_divDettaglio").attr("style", "display:inline");
			$("#id_divDettaglio").dialog({
				height : 300,
				width : 800,
				modal : true,
				resizable : false,
	
				open : function(event, ui) {
					$("span.ui-dialog-title").text('Dettaglio errore: ' + title);
				},
	
				buttons : {
					Chiudi : function() {
						$(this).dialog("close");
					}
				}
	
			});
		}
	</script>
</tiles:putAttribute>
</tiles:insertDefinition>

