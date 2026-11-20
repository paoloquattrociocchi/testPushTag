<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
#versione {
	vertical-align: middle;
	font-family: Calibri, tahoma;
	color: #1c5791;
	font-size: 13px;
	font-weight: bold;
	padding-left: 10px;
}
</style>


<table class="tb_footer" border="0">
	<tr>
		<td align="left"  width="7px">
			<img src="${pageContext.request.contextPath}/images/footer_bg-sx.jpg" /></td>
		<td align="right" style="padding-top: 7px;  padding-right: 3px" width="70%" > <!--  padding-left: 20%" -->
			<a href="#"><img src="${pageContext.request.contextPath}/images/LG_ministeroGiustizia.png" border="0" /></a>
		</td>
		<td align="left" style="padding-top: 10px;" width="21%" >
			<span id="versione"><i>ver&nbsp<c:out value="${softwareVersion}" /></i></span>
		</td>
		<td align="right" width="7px">
		 <img src="${pageContext.request.contextPath}/images/footer_bg-dx.jpg" /></td>
	</tr>
</table>
