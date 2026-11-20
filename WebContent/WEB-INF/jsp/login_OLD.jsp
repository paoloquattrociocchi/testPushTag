<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ page import="org.springframework.security.web.authentication.AbstractProcessingFilter" %>
<%@ page import="org.springframework.security.web.authentication.AuthenticationProcessingFilter" %>
<%@ page import="org.springframework.security.core.AuthenticationException" %> 

<%@ page import="it.giustizia.smp.model.TUtente"%>

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
							<br />
							<br /> <img
								src="${pageContext.request.contextPath}/images/immagineHOME-titolo.gif"
								style="margin-left: 40px;" />

 <c:if test="${not empty param.login_error}">
        <font color="red">
                 Credenziali di accesso non corrette
        </font>
</c:if>
<form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">
      <table style="margin-left: 460px; margin-top: -210px">
			<%@ include file="/WEB-INF/jsp/common/error.jsp"%>
         <tr>
         	<td><span class="testo_Campo">Nome Utente:</span></td>
         	<td><input type='text' name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'/></td>
         </tr>
         <tr>
         	<td><span class="testo_Campo">Password:</span>
         	<td><input type='password' name='j_password'></td>
         </tr>
         <tr>
         	<td colspan="2" align="right">
											<button name="Invia" value="Invia" type="submit">
												<img
													src="${pageContext.request.contextPath}/images/BTNlogin.jpg"
													border="0" />
											</button></td>
         </tr>
       </table>
</form> 

		</div>
	</div></td>
				<td
					style="background-image: url('${pageContext.request.contextPath}/images/content_filo-dx.jpg'); background-repeat: no-repeat; width: 12px;">&nbsp;</td>
				<td style="width: 15px">&nbsp;</td>
			</tr>
		</table>
	</tiles:putAttribute>
</tiles:insertDefinition>
