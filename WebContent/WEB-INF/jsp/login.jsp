<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>



<tiles:insertDefinition name="base.definition.login">
	<tiles:putAttribute name="body-content">

		<table style="width: 100%; padding: 0px; border-collapse: collapse;">
			<tr style="height: 30px">
				<td colspan="3" style="vertical-align: middle">&nbsp;</td>
			</tr>
			<tr style="height: 520px">
				<td
					style="background-image: url('${pageContext.request.contextPath}/images/content_filo-sx.jpg'); background-repeat: no-repeat; width: 12px;">&nbsp;</td>
				<td
					style="background-image: url('${pageContext.request.contextPath}/images/content_filo-bg.jpg'); background-repeat: repeat-x;">
					<div id="divBodyContent">
						<div id="divImgHome">
							<img
								src="${pageContext.request.contextPath}/images/immagineHOME-top.png" />
							<br /> <br /> <img
								src="${pageContext.request.contextPath}/images/immagineHOME-titolo.gif"
								style="margin-left: 40px;" />

							<logic:present name="customMessage">
								<font color="red"> <c:out value="${customMessage}"></c:out>
								</font>
							</logic:present>
							<spring:url var="azione" value="/login.htm" />
							<form:form method="post" action="${azione}"
								commandName="loginForm">
								<table style="margin-left: 460px; margin-top: -400px">
									<p class="titolo_1">
										<spring:message code="login.profilo" />
									</p>

									<c:forEach var="profiloTmp" items="${profili}">
										<tr>
											<td><form:radiobutton path="profilo.profiloString" value="${profiloTmp.profiloString}" />
											 <span id="profili" style="font-size: 10px;">
													<c:out value="${profiloTmp.ruolo}"></c:out> presso <c:out
														value="${profiloTmp.descrizioneUfficio}"></c:out> di <c:out
														value="${profiloTmp.citta}"></c:out>
											</span></td>
										</tr>
									</c:forEach>

								</table>

								<br />
								<br />

								<table class="tb_bottoni" align="center">
									<tr>
										<td class="td_tasto"><input type="submit" name="INVIA"
											title="Accedi col profilo selezionato" value="Accedi" />
										</td>
									</tr>
								</table>

							</form:form>
						</div>
					</div>
				</td>
				<td
					style="background-image: url('${pageContext.request.contextPath}/images/content_filo-dx.jpg'); background-repeat: no-repeat; width: 12px;">&nbsp;</td>
				<td style="width: 15px">&nbsp;</td>
			</tr>
		</table>
	</tiles:putAttribute>
</tiles:insertDefinition>