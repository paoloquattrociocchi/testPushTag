<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Strict//EN">
<html>
<meta http-equiv="X-UA-Compatible" content="IE=7,8"/>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<!doctype html>
<html>
 --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<meta http-equiv="X-UA-Compatible" content="IE=8"/> 
	<head>
	<title>SITmp</title>
	<meta http-equiv="Content-Type" content="application/xhtml; charset=iso-8859-1">
	<tiles:insertAttribute name="script" />
</head>
<body>
	<div class="contenitore">
		<div class="contenitore_parziale">
			<tiles:insertAttribute name="header" />
		</div>
		<div id="waitDialog" style="display: none;">
			<table>
				<tr>
					<td rowspan="2"><img src="${pageContext.request.contextPath}/images/spinner.gif" border="0" /></td>
				</tr>
				<tr>
					<td>Elaborazione in corso...</td>
				</tr>

			</table>

		</div>

		<div class="contenitore_parziale">
			<table class="tb_body">
				<tr>
					<td id="td_menu" style="display: block;" class="bodySX">

						<div id="div_menu">
							<tiles:insertAttribute name="menu" />
						</div>
					</td>
					<td class="bodyContent" align="left">
						<a href="#" onClick="showHideMenu()"><img id="img_Menu" src="${pageContext.request.contextPath}/images/icoPiu.png" border="0" title="Nascondi Navigatore Laterale"></a> 
<!-- 
						<div class="round-body" style="min-height: 0;">
							<span style="font-weight:normal;"> -> 
								<c:forEach items="${sessionScope['KEY_BREADCRUMB_MANUAL'].list}" var="item">
									${item} >> 
								</c:forEach>
							</span>
							<hr>
							 <div>
								
								Pagina JSP[${pageContext.request.requestURI }] 
								Mapping[ ${mappingUrl} ] disable[${disabledViewProc}]
								Callback [ ${sessionScope.procedimento.callbackUrl} ] <br>
								idProcedimento[${sessionScope.procedimento.idProcedimento}] TipoProc[${sessionScope.procedimento.tipoProcedimento.id}] statoProc[${sessionScope.procedimento.statoProcedimento.id}] <br>
								idAtto[${sessionScope.atto.codAtto}] statoAtto[${sessionScope.atto.statoAtto.id}] tipoAtto[${sessionScope.atto.tipoAtto.id }] categoriaAtto[${sessionScope.atto.categoriaAtto.id }]  <br>
								tipoUfficio[${sessionScope.profilo.idTipoUfficio }]
							</div> 
							
						</div>
	-->				
						<div id="id_messagesAutocomplete" class="error" style="visibility: hidden"></div> <logic:present name="customMessage">
							<div class="error">
								<div class="testo">
									<spring:message code="${customMessage.code}" text="" argumentSeparator="${customMessage.argumentSeparator}" arguments="${customMessage.arguments}" />
								</div>
								<c:remove var="customMessage" scope="session" />
							</div>
						</logic:present> 
						<logic:present name="successCustomMessage">
							<div class="success">
								<div class="testoSuccess">
									<spring:message code="${successCustomMessage.code}" text="" argumentSeparator="${successCustomMessage.argumentSeparator}" arguments="${successCustomMessage.arguments}" />
								</div>
								<c:remove var="successCustomMessage" scope="session" />
							</div>
						</logic:present> 



						<logic:notEmpty name="listaMessaggiErrori">
							<div class="error">
								<div class="testo">
									
										<c:forEach var="msg" items="${listaMessaggiErrori}">
											<spring:message code="${msg.code}" text="" argumentSeparator="${msg.argumentSeparator}" arguments="${msg.arguments}" />
											<br/>
										</c:forEach>
								
								</div>
								<c:remove var="listaMessaggiErrori" scope="session" />
							</div>
						</logic:notEmpty>

						
						<logic:notEmpty name="customMessageList">
							<div class="error">
								<div class="testo">
									
										<c:forEach var="msg" items="${customMessageList}">
											<spring:message code="${msg.code}" text="" argumentSeparator="${msg.argumentSeparator}" arguments="${msg.arguments}" />
											<br/>
										</c:forEach>
								
								</div>
								<c:remove var="customMessageList" scope="session" />
							</div>
						</logic:notEmpty>
						 
						<logic:notEmpty name="successCustomMessageList">
							<div class="success">
								<div class="testoSuccess">

										<c:forEach var="msg" items="${successCustomMessageList}">
												<spring:message code="${msg.code}" text="" argumentSeparator="${msg.argumentSeparator}" arguments="${msg.arguments}" />
											<br/>
										</c:forEach>

								</div>
								<c:remove var="successCustomMessageList" scope="session" />
							</div>
						</logic:notEmpty> <%--
						<div class="round-body">
							<tiles:getAsString name="body-content" />
						</div>
						--%>



						<table style="width: 99%; min-height: 200px; margin: 0px; margin-bottom: 20px; padding: 0px; border-collapse: collapse;">
							<tr height="7px">
								<td width="7px"><img src="${pageContext.request.contextPath}/images/bc_angolo1.png" /></td>
								<td style="background-image: url('${pageContext.request.contextPath}/images/bc_bg1.png'); background-repeat: repeat-x;"></td>
								<td width="7px"><img src="${pageContext.request.contextPath}/images/bc_angolo2.png" /></td>
							</tr>

							<tr>
								<td style="background-image: url('${pageContext.request.contextPath}/images/bc_bg4.png'); background-repeat: repeat-y;">&nbsp;</td>
								<td>
									<!-- <div id="divBodyContent">--> <tiles:getAsString name="body-content" /> <!-- </div>-->
								</td>
								<td style="background-image: url('${pageContext.request.contextPath}/images/bc_bg2.png'); background-repeat: repeat-y;">&nbsp;</td>
							</tr>
							<tr height="7px">
								<td width="7px"><img src="${pageContext.request.contextPath}/images/bc_angolo4.png" /></td>
								<td style="background-image: url('${pageContext.request.contextPath}/images/bc_bg3.png'); background-repeat: repeat-x;"></td>
								<td width="7px"><img src="${pageContext.request.contextPath}/images/bc_angolo3.png" /></td>
							</tr>
						</table></td>

					<td class="bodyDX">&nbsp;</td>
				</tr>
			</table>
		</div>

		<div class="contenitore_parziale">
			<tiles:insertAttribute name="footer" />
		</div>

	</div>

</body>
</html>
