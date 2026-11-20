<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<tiles:insertDefinition name="base.definition">
	<tiles:putAttribute name="body-content">
		<spring:url var="azione" value="/statistiche/visualizzaStatistica.htm" />
		<table width="100%">
			<tr>
				<td width="100%" valign="top">
					<p class="titolo_page">
						<spring:message code="${formTitle.code}" text="" argumentSeparator="${formTitle.argumentSeparator}" arguments="${formTitle.arguments}" />
					</p>
				</td>
			</tr>
		</table>
		<form:form method="post" action="${azione}" commandName="statisticheForm">
			<form:hidden id="hiddenIdStampa"  path="idStampa" />
			<form:hidden id="hiddenFiltroDataDal"  path="filtroDataDal" />
			<form:hidden id="hiddenFiltroDataAl"  path="filtroDataAl" />
			
			<table width="100%">
			<tr>
				<td align="right" valign="baseline">
					<a id="zoomOut" onclick="zoomIndietro()"  style="cursor:pointer; color:#1c5791;" >[Zoom Indietro]</a>
					<span id="currentZoom"></span>
					<a  id="zoomIn" onclick="zoomAvanti()" style="cursor:pointer; color:#1c5791;">[Zoom Avanti]</a>
				</td>
			</tr>
			</table>
			<iframe id="ifrm" name="ifrm"  allowfullscreen="true" frameborder="0" style="border: solid 1px #999999;" width="100%" height="370" scrolling="yes" src="${pageContext.request.contextPath}/statistiche/statisticaExporterHTML.htm"></iframe> 
  			<br />
			<br />
			<table class="tb_bottoni" align="center">
				<tr>
					<td class="td_tasto"><input id="scaricaStatisticaPDFButton" type="button" value="Scarica PDF" onclick="return scaricaStatisticaPDF(this)"></td>
					<td class="td_tasto"><input id="scaricaStatisticaEXCELButton" type="button" value="Scarica Excel" onclick="return scaricaStatisticaExcel(this)"></td>
					<td class="td_tasto"><input id="backButton" type="button" value="Indietro"></td>
				</tr>
			</table>
		</form:form>
		
		
		<script type="text/javascript">
			var currentZoom = 100;
			
			function scaricaStatisticaPDF(e) {
				window.location.href = "${pageContext.request.contextPath}/statistiche/statisticaExporterPDF.htm";
				setTimeout(function() {
					$('#waitDialog').dialog("close");
				}, 500);
				return false;
			}
			function scaricaStatisticaExcel(e) {
				window.location.href = "${pageContext.request.contextPath}/statistiche/statisticaExporterEXCEL.htm";
				setTimeout(function() {
					$('#waitDialog').dialog("close");
				}, 500);
				return false;
			}

			function zoomAvanti() {
				currentZoom = currentZoom +10;
				changeZoom();
			}

			function zoomIndietro() {
				currentZoom = currentZoom -10;
				changeZoom();
			}

			function changeZoom() {
				write_cookie("zoomValue",currentZoom,"/");
				changeZoomInFrame();
			}

			function changeZoomInFrame() {
				window.frames[0].document.body.style.zoom = currentZoom + "%";
			}			
				
			$(document).ready(function() {
				$('#zoomIn').disableSelection();
				$('#zoomOut').disableSelection();
				currentZoom=100;
				//alert(read_cookie("zoomValue"));
				try {
					currentZoom = parseInt(read_cookie("zoomValue"));
					if (isNaN(currentZoom)) currentZoom = 100;
					if (currentZoom>200) {
						currentZoom = 200;
					}
				} catch (e) {
					currentZoom = 100;
				}
				//alert(currentZoom);
				//alert('poo');
			});


			function write_cookie (name, value, path)
			{
			        // Build the expiration date string:
			        var expiration_date = new Date ();
			        expiration_date . setYear (expiration_date . getYear () + 1);
			        expiration_date = expiration_date . toGMTString ();

			        // Build the set-cookie string:
			        var cookie_string = escape (name) + "=" + escape (value) +
			                "; expires=" + expiration_date;
			        if (path != null)
			                cookie_string += "; path=" + path;

			        // Create/update the cookie:
			        document.cookie = cookie_string;
			}



			function read_cookie (key, skips)
			{
			        // Set skips to 0 if parameter was omitted:
			        if (skips == null)
			                skips = 0;

			        // Get cookie string and separate into individual cookie phrases:
			        var cookie_string = "" + document . cookie;
			        var cookie_array = cookie_string . split ("; ");

			        // Scan for desired cookie:
			        for (var i = 0; i < cookie_array . length; ++ i)
			        {
			                var single_cookie = cookie_array [i] . split ("=");
			                if (single_cookie . length != 2)
			                        continue;
			                var name  = unescape (single_cookie [0]);
			                var value = unescape (single_cookie [1]);

			                // Return cookie if found:
			                if (key == name && skips -- == 0)
			                        return value;
			        }

			        // Cookie was not found:
			        return null;
			}
		</script>
	
		<%@taglib prefix="smp" uri="/WEB-INF/smp.tld"%>
		<smp:back buttonId="backButton"></smp:back>
	</tiles:putAttribute>
</tiles:insertDefinition>