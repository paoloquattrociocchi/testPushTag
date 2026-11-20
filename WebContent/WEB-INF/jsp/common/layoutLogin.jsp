<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%--
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Strict//EN">
<html>
 		<meta http-equiv="X-UA-Compatible" content="IE=8"/>

 --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=8"/> 
		<title>SIT mp</title>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">



<link href="${pageContext.request.contextPath}/css/generale.css" media="all" rel="stylesheet" type="text/css" charset="utf-8" />
<style>
	#profili {
		font-color: #006699;
		vertical-align: middle;
		font-family: Calibri, tahoma;
		font-size: 15px;
		font-weight: bold;
		color: #1c5791;
		background-color: #EBEBEB;
		padding-left: 5px;
	}
</style>

</head>

<body>
	<div class="contenitore">
		<div class="contenitore_parziale">
			<table class="tb_header">
				<tr>
					<td align="left"><img
						src="${pageContext.request.contextPath}/images/heather_sx.jpg" /></td>
					<td align="left">&nbsp;</td>
					<td align="right">
						<div class="utenteLogin">


							<a href="#" title="clicca per terminare la sessione di lavoro"
								onclick="logoutIaa()"> <img
								src="${pageContext.request.contextPath}/images/bottoneLogout.png"
								align="absmiddle" border="0" />
							</a> &nbsp;&nbsp;

						</div>
					</td>
				</tr>
			</table>

			<table class="tb_header_tasti">
				<tr>
					<td class="tb_header_tasti_sx">&nbsp;</td>
					<td>&nbsp;</td>
					<td class="tb_header_tasti_dx">&nbsp;</td>
				</tr>
			</table>
		</div>

		<div class="contenitore_parziale">
			<table class="tb_body">
				<tr>
					<td class="bodySX"><br /> <img
						src="${pageContext.request.contextPath}/images/home_immagine.png"><br />
					</td>
					<td class="bodyContent" align="left"><tiles:getAsString
							name="body-content" /></td>

					<td class="bodyDX">&nbsp;</td>
				</tr>
			</table>
		</div>

		<div class="contenitore_parziale">
			<tiles:insertAttribute name="footer" />
		</div>

	</div>

	<script type="text/javascript">
	
	function logoutIaa() {		
		window.location.href = "<%=(String) request.getSession().getAttribute("logoutFromIAA")%>";
	}
	</script>

</body>

</html>
