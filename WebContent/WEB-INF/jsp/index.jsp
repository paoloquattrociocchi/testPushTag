<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>



<tiles:insertDefinition name="base.definition.index">
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
						</div>
					</div></td>
				<td
					style="background-image: url('${pageContext.request.contextPath}/images/content_filo-dx.jpg'); background-repeat: no-repeat; width: 12px;">&nbsp;</td>
				<td style="width: 15px">&nbsp;</td>
			</tr>
		</table>
	</tiles:putAttribute>
</tiles:insertDefinition>
